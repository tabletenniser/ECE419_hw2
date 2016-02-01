import java.util.concurrent.BlockingQueue;
import java.lang.Thread;


class ServerProjectileThread implements Runnable{
    private BlockingQueue eventQueue = null;
    private String name = null;
    private static final int MaxGen = 10;
    private int projectileID = -1;
    private final Thread thread;

    public ServerProjectileThread(BlockingQueue eq, String name, int pid){
        this.eventQueue = eq;
        this.name = name; 
        this.projectileID = pid;
        thread = new Thread(this);
    }

    public void run(){
        int i = 0;
        System.out.println("Client " + this.name + " issued FIRE");
        try {
			eventQueue.put(new MPacket(this.name, MPacket.ACTION, MPacket.FIRE, this.projectileID));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}           

        for (i = 0; i < MaxGen; i++){
            try{
                thread.sleep(200);
            }catch(Exception e){
                e.printStackTrace();
            }
            try {
				eventQueue.put(new MPacket(this.name, MPacket.ACTION, MPacket.PJ_UPDATE, this.projectileID));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}           
        }
    }
}

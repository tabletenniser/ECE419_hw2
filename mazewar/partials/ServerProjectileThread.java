import java.util.concurrent.BlockingQueue;

class ServerProjectileThread implements Runnable{
    private BlockingQueue eventQueue = null;
    private String name = null;
    private static final int MaxGen = 10;
    private int projectileID = -1;

    public ServerProjectileThread(BlockingQueue eq, String name, int pid){
        this.eventQueue = eq;
        this.name = name; 
        this.projectileID = pid;
    }

    public void run(){
        int i = 0;
        System.out.println("Client " + this.name + " issued FIRE")
        eventQueue.put(new MPacket(this.name, MPacket.ACTION, MPacket.FIRE, this.projectileID));           

        for (i = 0; i < MaxGen; i++){
            try{
                thread.sleep(200);
            }catch(Exception e){
                // nope
            }
            eventQueue.put(new MPacket(this.name, MPacket.ACTION, MPacket.PJ_UPDATE, this.projectileID));           
        }
    }
}
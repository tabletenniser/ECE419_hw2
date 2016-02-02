import java.util.concurrent.BlockingQueue;
import java.lang.Thread;


class ServerProjectileThread implements Runnable{
    private BlockingQueue eventQueue = null;
    private final Thread thread;

    public ServerProjectileThread(BlockingQueue eq){
        this.eventQueue = eq;
        thread = new Thread(this);
    }

    public void run(){
        while(true){
            try{
                thread.sleep(200);
            }catch(Exception e){
                e.printStackTrace();
            }

            try {
				eventQueue.put(new MPacket(MPacket.ACTION, MPacket.PJ_UPDATE));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}           
        }
    }
}

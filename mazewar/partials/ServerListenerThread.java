import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ServerListenerThread implements Runnable {

    private MSocket mSocket =  null;
    private BlockingQueue eventQueue = null;
    private int nextExpected = 0;
    private PriorityQueue<MPacket> packetPriorityQueue = 
        new PriorityQueue<MPacket>(50, new packetSequenceComparator());

    public ServerListenerThread( MSocket mSocket, BlockingQueue eventQueue){
        this.mSocket = mSocket;
        this.eventQueue = eventQueue;
    }

    public class packetSequenceComparator implements Comparator<MPacket>
    {
        @Override
        public int compare(MPacket x, MPacket y)
        {
            return x.sequenceNumber - y.sequenceNumber;
        }
    }

    public void run() {
        MPacket received = null;
        while(true){
            try{
                received = (MPacket) mSocket.readObject();
                System.out.println("ServerListener got #" + received.sequenceNumber + " expecting #" + this.nextExpected + " queuesize=" + this.packetPriorityQueue.size());
                if(received.type == MPacket.HELLO){
                    eventQueue.put(received);
                    continue;
                }
                this.packetPriorityQueue.offer(received);

                while (this.packetPriorityQueue.size() != 0 && this.packetPriorityQueue.peek().sequenceNumber == this.nextExpected){
                    this.nextExpected++;
                    received = this.packetPriorityQueue.poll();

                    System.out.println("Processing packet #" + received.sequenceNumber);
                    eventQueue.put(received);    
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            
        }
    }
}

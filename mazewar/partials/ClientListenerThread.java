import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Arrays;


public class ClientListenerThread implements Runnable {

    private MSocket mSocket  =  null;
    private Hashtable<String, Client> clientTable = null;
    private int nextExpected = 0;
    private PriorityQueue<MPacket> packetPriorityQueue = 
        new PriorityQueue<MPacket>(50, new packetSequenceComparator());

    public class packetSequenceComparator implements Comparator<MPacket>
    {
        @Override
        public int compare(MPacket x, MPacket y)
        {
            return x.sequenceNumber - y.sequenceNumber;
        }
    }

    public class objSequenceComparator implements Comparator<Object>
    {
        @Override
        public int compare(Object _x, Object _y)
        {
            MPacket x = (MPacket) _x;
            MPacket y = (MPacket) _y;
            return x.sequenceNumber - y.sequenceNumber;
        }
    }

    public ClientListenerThread( MSocket mSocket,
                                Hashtable<String, Client> clientTable){
        this.mSocket = mSocket;
        this.clientTable = clientTable;
        if(Debug.debug) System.out.println("Instatiating ClientListenerThread");
    }

    public void run() {
        MPacket received = null;
        Client client = null;
        if(Debug.debug) System.out.println("Starting ClientListenerThread");
        while(true){
            try{
                received = (MPacket) mSocket.readObject();
                this.packetPriorityQueue.offer(received);
                int headSeqNumer = this.packetPriorityQueue.peek().sequenceNumber;
                while (this.packetPriorityQueue.size() != 0 && this.packetPriorityQueue.peek().sequenceNumber == this.nextExpected){
                    this.nextExpected++;
                    received = this.packetPriorityQueue.poll();
                    System.out.println("Processing packet #" + received.sequenceNumber);

                    client = clientTable.get(received.name);
                    if(received.event == MPacket.UP){
                        client.forward();
                    }else if(received.event == MPacket.DOWN){
                        client.backup();
                    }else if(received.event == MPacket.LEFT){
                        client.turnLeft();
                    }else if(received.event == MPacket.RIGHT){
                        client.turnRight();
                    }else if(received.event == MPacket.FIRE){
                        client.fire();
                    }else if(received.event == MPacket.UPDATE_PROJECTILE){
                        client.updateProjectile();
                    }else{
                        throw new UnsupportedOperationException();
                    }    
                }
            }catch(IOException e){
                e.printStackTrace();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }            
        }
    }
}

import java.awt.List;
import java.io.Serializable;
import java.util.LinkedList;

public class BlockingQueue implements Serializable 
{

  private LinkedList<MessageTrial> queue = new LinkedList<MessageTrial>();
  private int  limit = 10;

  public BlockingQueue(int limit)
  {
    this.limit = limit;
  }


  public synchronized void enqueue(MessageTrial item) throws InterruptedException  
  {
    while(this.queue.size() == this.limit) 
    {
      wait();
    }
    if(this.queue.size() == 0) 
    {
      notifyAll();
    }
    this.queue.add(item);
  }


  public synchronized MessageTrial dequeue()
  throws InterruptedException{
    while(this.queue.size() == 0){
      wait();
    }
    if(this.queue.size() == this.limit){
      notifyAll();
    }

    return (MessageTrial) this.queue.remove(0);
  }

}
    
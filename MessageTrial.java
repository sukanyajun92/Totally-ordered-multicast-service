import java.io.Serializable;
import java.util.ArrayList;


public class MessageTrial implements Serializable
{
	String mess_id;
	int sequence_num;
	String content;
	int logicalCount;
	ArrayList<String> listOfdestinations=new ArrayList<String>();
	
	MessageTrial()
	{
		//do nothing
	}
	
	 public void setterMethod(String id,String messContents, int logicalCount)
	 {
		 this.mess_id=id;
		 this.content=messContents;
		 this.logicalCount=logicalCount;
		 
	 }
	 public void setSequenceNumber(int seq)
	 {
		 sequence_num=seq;
	 }
	 
	 public ArrayList<String> getRecipients()
	 {
		 return listOfdestinations;
	 }
	 
	 public int returnSizeOfArraylist()
	 {
		 return listOfdestinations.size();
	 }
	 
	 public String getId()
	 {
		 return mess_id;
	 }
	 
	 public String getConetent()
	 {
		 return content;
	 }
	 
	 public void printParameters()
	 {
		 System.out.println("My mesage id is "+mess_id);
		 System.out.println("My contents are "+content);
		 System.out.println("My sequence number is "+sequence_num);
	 }
	 
	 public int returnTimeStamp()
	 {
		 return logicalCount;
	 }
}

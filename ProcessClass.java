import java.awt.List;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class ProcessClass implements Serializable
{
	ServerSocket serverSocket;
	Socket clientSocket;
	ArrayList<String>mcRecipients=new ArrayList<String>();
	MessageTrial message=new MessageTrial();
	MessageTrial receivedMessage=new MessageTrial();
	int LogicalTime=0;
	Map<String,String>myMessageMap=new HashMap<String,String>();
	String myName;
			
	ProcessClass()
	{
		
	}
	
	public void mesageSetter(String id, String messContents, int logicalCount)
	{
		this.message.setterMethod(id, messContents, logicalCount);
	}
	
	public void printMessageConetents(MessageTrial mess)
	{
		mess.printParameters();
	}
	
	public void setName(String Name)
	{
		this.myName=Name;
	}

	public String getServerName()
	{
		return myName;
	}
	
	public void setTimeStamp(int LogicalTimeStamp)
	{
		this.LogicalTime=LogicalTimeStamp;
	}
	
	public void messageEnqueue(MessageTrial msg) throws InterruptedException
	{
		//this.buffer.enqueue(msg);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

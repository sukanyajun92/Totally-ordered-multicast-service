import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.text.html.ListView;


public class sendMessage implements Runnable, Serializable
{
	Socket socket;
	MessageTrial message;
	
	public sendMessage(Socket sock, MessageTrial message)
	{
		this.socket=sock;
		this.message=message;
	}
	
	public void run() 
	{
		System.out.println("Sending multicast messages to the intended recipients ");
		try
		{
			System.out.println("This is the message being written onto the output stream ");
			message.printParameters();
			ObjectOutputStream outStream=new ObjectOutputStream(socket.getOutputStream());
			outStream.writeObject(message);
			outStream.flush();
		}			
		catch(Exception e)
		{
			System.out.println("Problem send message");
			e.printStackTrace();
		}		
	}	
}



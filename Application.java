import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/*
 * This code is for concurrency
 */
public class Application implements Serializable
{
	public static void main(String[] args)throws IOException, ClassNotFoundException 
	{
		// TODO Auto-generated method stub
		ProcessClass process=new ProcessClass();
		
		String serverName=InetAddress.getLocalHost().getHostName();
		InetAddress myIp=InetAddress.getLocalHost();
		System.out.println("The server name of this node is "+serverName+" and ip address is "+myIp);
						
		process.setName(serverName);
		Thread servThread=new Thread(new ServerSocketThread(serverName,process));
		servThread.start();
		
		Thread cliThread=new Thread(new ClientThread(process));
		cliThread.start();
	}
}

class ServerSocketThread implements Runnable,Serializable
{
	String serverName;
	ProcessClass process=new ProcessClass();
	static int sequence_number=0;
		
	ServerSocketThread(String servName,ProcessClass pro)
	{
		this.serverName=servName;
		this.process=pro;
	}
	public void run()
	{
		//read from the config file
		Socket newSock=null;
		File readFile=new File("/home/004/s/sx/sxs136330/workspace/AOSNew/config1.txt");
		Scanner scan;
		try 
		{
			scan = new Scanner(readFile);
			while(scan.hasNextLine())
			{
				String line=scan.nextLine();
				String[ ] parts=line.split("\t");
				String sender=parts[1];
								 
				if(sender.equalsIgnoreCase(serverName))
				{
					 process.myMessageMap.put(parts[0], parts[2]);
					//readAndDelete the line
				}
			}	
			System.out.println("My map consists of the following contents"+process.myMessageMap);
		}
		catch(FileNotFoundException e1)
		{
			System.out.println("File not found error");
			e1.printStackTrace();
		}
		//create a TCP server socket
		Iterator it=process.myMessageMap.entrySet().iterator();
		while(it.hasNext())
		{
			@SuppressWarnings("unchecked")
			Map.Entry<String, String>entry=(Entry<String, String>) it.next();
			System.out.println("the key value pair is "+entry.getKey()+"  "+entry.getValue());
			String messId=entry.getKey();
			String destiList=entry.getValue();
			String contents=serverName+"'s message";
			process.mesageSetter(messId,contents,++process.LogicalTime);
			process.message.setSequenceNumber(++sequence_number);
			
			try 
			{
				process.serverSocket=new ServerSocket(4227);
				System.out.println("**********Server*************");
				while(true)
				{
					newSock=process.serverSocket.accept();
					String clientName=newSock.getInetAddress().getHostName();
					System.out.println("Established socket connection with the process "+clientName);
					if(destiList.contains(clientName))
					{
						Thread t=new Thread(new sendMessage(newSock,process.message));
						t.start();
					}
				} 
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				System.out.println("Inside server socket creation thread");
				e.printStackTrace();
			}
		}
	}
}

class ClientThread implements Runnable,Serializable
{
	ProcessClass clipro=new ProcessClass();
	
	ClientThread(ProcessClass pro)
	{
		this.clipro=pro;
	}
	
	public void run()
	{
		File newFile=new File("/home/004/s/sx/sxs136330/workspace/AOSNew/Connections.txt");
		Scanner scan;
		try 
		{
			scan = new Scanner(newFile);
			while(scan.hasNextLine())
			{
				String serverName=scan.nextLine();
				clipro.clientSocket=new Socket(serverName, 4227);
				System.out.println("************Client************");
				String conn=clipro.clientSocket.getInetAddress().getHostName();
				System.out.println("Established a socket connection with the server "+conn);
						
				try
				{
					ObjectInputStream inStream=new ObjectInputStream(clipro.clientSocket.getInputStream());
					clipro.receivedMessage=(MessageTrial)inStream.readObject();
					int maxTimeStamp=Math.max(clipro.message.logicalCount, clipro.LogicalTime);
					clipro.setTimeStamp(maxTimeStamp);
					clipro.printMessageConetents(clipro.receivedMessage);
				}
				
				catch(Exception e)
				{
					System.out.println("Problem reading from the inputStream");
				}
				
			}
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("file not found");
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("IOException ");
			//e.printStackTrace();
		} 
	}

	private Writer FileOutputStream(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}



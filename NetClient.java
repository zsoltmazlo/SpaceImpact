package SpaceImpact_package;


import java.io.*;
import java.net.*;


public class NetClient extends NetConnection
{
	
	public Space ClientSpace = new Space();
	private ShipControl ClientCnt;
	
	
	NetClient(ShipControl CntToSend)
	{
		ClientCnt = CntToSend;
	}
	
	
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	
	private class ReceiverThread implements Runnable
	{
		public void run() {
			System.out.println("Waiting for points...");
			try
			{
				while (true)
				{
					Space received = (Space) in.readObject();
					
					ClientSpace.myShipXpos = received.myShipXpos;
					ClientSpace.myShipYpos = received.myShipYpos;
					ClientSpace.myShipLaser = received.myShipLaser;
					
					ClientSpace.otherShipXpos = received.otherShipXpos;
					ClientSpace.otherShipYpos = received.otherShipYpos;
					ClientSpace.otherShipLaser = received.otherShipLaser;
					
					ClientSpace.alienShipXpos = received.alienShipXpos;
					ClientSpace.alienShipYpos = received.alienShipYpos;
					ClientSpace.alienShipLaser = received.alienShipLaser;
							
				}
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
			} 
			finally
			{
				disconnect();
			}
		}
	}
	
	
	public void connect(String ip)
	{
		disconnect();
		try
		{
			socket = new Socket(ip, 10007);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host");
		}
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection. ");
			//JOptionPane.showMessageDialog(null, "Cannot connect to server!");
		}
	}
	
	public void send()
	{
		if (out == null)
			return;
		try
		{
			ShipControl SendCnt = new ShipControl();
			
			SendCnt.ShipXdir = ClientCnt.ShipXdir;
			SendCnt.ShipYdir = ClientCnt.ShipYdir;
			SendCnt.ShipLaser = ClientCnt.ShipLaser;
			
			out.writeObject(SendCnt);
			out.flush();
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
			System.err.println("Send error.");
		}
	}
	
	public void disconnect()
	{
		try
		{
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		}
		catch (IOException ex)
		{
		
		}
	}
	
	
}
package SpaceImpact_package;


import java.io.*;
import java.net.*;


public class NetClient extends NetConnection
{
	
	private GraphicUI ClientGui;
	
	
	NetClient(GraphicUI GraphicUIToWrite)
	{
		ClientGui = GraphicUIToWrite;
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
					ClientGui.GuiSpace = received;
					
					//System.out.println(">ClientReceived> " +received.Aliens.firstElement().getAlienShipXpos());
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
	
	public void send( ShipControl CntToSend )
	{
		if (out == null)
			return;
		try
		{
			ShipControl SendCnt = new ShipControl();
			
			SendCnt.ShipXdir = CntToSend.ShipXdir;
			SendCnt.ShipYdir = CntToSend.ShipYdir;
			SendCnt.ShipLaser = CntToSend.ShipLaser;
			
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
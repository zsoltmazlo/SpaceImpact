package SpaceImpact_package;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NetServer extends NetConnection
{

	private GameCore ServerCore;
	
	
	NetServer(GameCore CoreToReceive)
	{
		ServerCore = CoreToReceive;
	}
	
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	private class ReceiverThread implements Runnable 
	{
		public void run()
		{
			System.out.println("--- Server Run ---");
			try 
			{
				System.out.println("Waiting for Client");
				clientSocket = serverSocket.accept();
				System.out.println("Client connected.");
			} 
			catch (IOException e)
			{
				System.err.println("Accept failed.");
				disconnect();
				return;
			}

			try
			{
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
			} 
			catch (IOException e)
			{
				System.err.println("Error while getting streams.");
				disconnect();
				return;
			}

			try
			{
				while (true)
				{
					ShipControl SrvTestControll  = (ShipControl) in.readObject();
					ServerCore.otherControl = SrvTestControll;
				}
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
				System.err.println("Client disconnected!");
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
			serverSocket = new ServerSocket(10007);

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		}
		catch (IOException e)
		{
			System.err.println("Could not listen on port: 10007");
		}
	}
	
	public void send(Space SpaceToSend) {
		if (out == null)
			return;
		try
		{
			Space SendSpace = new Space();
			
			SendSpace.myShipXpos = SpaceToSend.myShipXpos;
			SendSpace.myShipYpos = SpaceToSend.myShipYpos;
			SendSpace.myShipLaser = SpaceToSend.myShipLaser;
			
			SendSpace.otherShipXpos = SpaceToSend.otherShipXpos;
			SendSpace.otherShipYpos = SpaceToSend.otherShipYpos;
			SendSpace.otherShipLaser = SpaceToSend.otherShipLaser;
			
			SendSpace.alienShipXpos = SpaceToSend.alienShipXpos;
			SendSpace.alienShipYpos = SpaceToSend.alienShipYpos;
			SendSpace.alienShipLaser = SpaceToSend.alienShipLaser;
			
			for (int i=0; i < SpaceToSend.Meteors.size(); i++)
			{
				SendSpace.Meteors.addElement( SpaceToSend.Meteors.get(i) );
			}
			
			for (int i=0; i < SpaceToSend.Aliens.size(); i++)
			{
				SendSpace.Aliens.addElement( SpaceToSend.Aliens.get(i) );
			}
			
			//System.out.println(">ServerSend> " +SendSpace.Aliens.firstElement().getAlienShipXpos());
			
			out.writeObject(SendSpace);
			out.flush();
		}
		catch (IOException ex)
		{
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
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		}
		catch (IOException ex)
		{
			
		}
	}
	
}
package SpaceImpact_package;


import java.util.Timer;
import javax.swing.JOptionPane;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		//--- Java swing Option dialog ---
		Object[] options = {"Singleplayer",
		                    "Server",
		                    "Client"};
		int GameMode = JOptionPane.showOptionDialog(null,
		    "Please select the game mode!",
		    "Game mode",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[2]);
		
		
		
		//--- Creating the main objects of the game ---
		GraphicUI TheGraphicUI;
		GameCore  TheGameCore;
		NetServer TheNetServer;
		NetClient TheNetClient;
		
		//Creating Timer for the GameCore
	   	Timer CoreTimer     = new Timer();
	   	long CoreTimerDelay = 40; //0.04s = 1/25
	   	
		
		//--- Configuring the program for the selected game mode ---
		switch (GameMode)
		{
			// Singleplayer mode
			case 0: System.out.println("--- Singleplayer mod ---");
					
					TheGraphicUI = new GraphicUI();
					TheGameCore  = new GameCore(TheGraphicUI.GuiControl);
			
					TheGraphicUI.GraphicUIStart( TheGameCore.CoreSpace );
					CoreTimer.scheduleAtFixedRate(TheGameCore, CoreTimerDelay, CoreTimerDelay);
	        		
					break;
	        
	        // Multiplayer/Server mode
			case 1: System.out.println("--- Multiplayer/Sever mod ---");
					
					TheGraphicUI = new GraphicUI();
					TheGameCore  = new GameCore(TheGraphicUI.GuiControl);
					
					TheGraphicUI.GraphicUIStart(TheGameCore.CoreSpace);
					CoreTimer.scheduleAtFixedRate(TheGameCore, CoreTimerDelay, CoreTimerDelay);
					
					TheNetServer = new NetServer(TheGameCore);
					TheNetServer.connect("localhost");
					
					TheGameCore.setCoreServer(TheNetServer);
					
					break;
        
			// Multiplayer/Client mode
			case 2: System.out.println("--- Multiplayer/Client mod ---");
					
					TheGraphicUI = new GraphicUI();
					
					TheNetClient = new NetClient(TheGraphicUI);
					TheNetClient.connect("localhost");
					
					TheGraphicUI.GraphicUIStart(new Space());
					
					TheGraphicUI.setGuiClient(TheNetClient);
					
					break;
					
			default: System.out.println("--- Menu error ---");
                 	 
					break;
         }

	}
}

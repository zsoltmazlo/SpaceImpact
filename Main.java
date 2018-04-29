package SpaceImpact_package;


import java.util.Timer;
import javax.swing.JOptionPane;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		//Java swing Option dialog
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
		GraphicUI TheGraphicUI = new GraphicUI();
		
		NetServer Srv = new NetServer();
		NetClient Clt = new NetClient(TheGraphicUI.GuiControl);
		
		GameCore  TheGameCore  = new GameCore(TheGraphicUI.GuiControl, Srv.ServerCnt);
		
		Srv.SetServerSpace(TheGameCore.CoreSpace);
		
		
		//Creating Timer for the GameCore
	   	Timer CoreTimer     = new Timer();
	   	long CoreTimerDelay = 40; //0.04s = 1/2
	   	
		
		//--- Initialize game for the selected game mode ---
		switch (GameMode)
		{
			//Singleplayer
			case 0: System.out.println("--- Singleplayer mod ---");
					
					TheGraphicUI.GraphicUIStart( TheGameCore.CoreSpace );
					CoreTimer.scheduleAtFixedRate(TheGameCore, CoreTimerDelay, CoreTimerDelay);
	        		break;
	        
	        //Multiplayer/Server
			case 1: System.out.println("--- Multiplayer/Sever mod ---");
					
					TheGraphicUI.GraphicUIStart(TheGameCore.CoreSpace);
					CoreTimer.scheduleAtFixedRate(TheGameCore, CoreTimerDelay, CoreTimerDelay);
					Srv.connect("localhost");
					break;
        
			//Multiplayer/Client
			case 2: System.out.println("--- Multiplayer/Client mod ---");
					
					TheGraphicUI.GraphicUIStart(Clt.ClientSpace);
					Clt.connect("localhost");
					break;
					
			default: System.out.println("--- Menu error ---");
                 	 break;
         }
		

		//--- Main thread for network communication runs in every 0.04s ---
		while(true)
		{   			   
			
			if( GameMode == 1 )
			{
				//Server
				Srv.send();
			}
			
			if( GameMode == 2 )
			{
				//Client
				Clt.send();
			}
			
			try{Thread.sleep(20);}catch(InterruptedException e){System.out.println(e);}
		}

	}
}

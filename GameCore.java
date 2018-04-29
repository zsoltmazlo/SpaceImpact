package SpaceImpact_package;


import java.util.Random;
import java.util.TimerTask;



public class GameCore extends TimerTask
{
	
	// Creating Game Space in Core
	protected Space CoreSpace;
	
	// Control for local player
	private ShipControl myControl;
	
	// Control for remote player
	private ShipControl otherControl;
	
	// Counts elapsed time
	private int GameCoreCounter = 0;
	
	private int MeteorCounter = 0;
	
	private Random rand;
	
	
	//---Moving my ship---
	private void myShipMove()
	{
		
		if( 90 < CoreSpace.myShipXpos && CoreSpace.myShipXpos < 640)
			CoreSpace.myShipXpos = CoreSpace.myShipXpos + myControl.ShipXdir;
		
		if( 20 < CoreSpace.myShipYpos && CoreSpace.myShipYpos < 480)
			CoreSpace.myShipYpos = CoreSpace.myShipYpos + myControl.ShipYdir;
		
		if(myControl.ShipLaser == 1)
			CoreSpace.myShipLaser = 1;
		else
			CoreSpace.myShipLaser = -1;
		
	}
	
	//---Moving the other ship ---
	private void otherShipMove()
	{
		
		if( 90 < CoreSpace.otherShipXpos && CoreSpace.otherShipXpos < 640)
			CoreSpace.otherShipXpos = CoreSpace.otherShipXpos + otherControl.ShipXdir;
		
		if( 20 < CoreSpace.otherShipYpos && CoreSpace.otherShipYpos < 480)
			CoreSpace.otherShipYpos = CoreSpace.otherShipYpos + otherControl.ShipYdir;
		
		if(otherControl.ShipLaser == 1)
			CoreSpace.otherShipLaser = 1;
		else
			CoreSpace.otherShipLaser = -1;
		
	}
	
	//---Moving the alien ship ---
	private void alienShipMove()
	{
		
		if( GameCoreCounter == 50 )
		{
			//After 2sec the alien ship appears
			CoreSpace.alienShipXpos = 700;
			CoreSpace.alienShipYpos = 300;
		}
			
		
		if( 100 <= GameCoreCounter && GameCoreCounter <= 300)
		{
			//the alien ship moves
			CoreSpace.alienShipXpos = CoreSpace.alienShipXpos - 3;
		}
		
		if( 300 <= GameCoreCounter && GameCoreCounter <= 320)
		{
			//the alien ship turns on the laser
			CoreSpace.alienShipLaser = 1;
		}
		else
		{
			CoreSpace.alienShipLaser = -1;
		}
		
		
		if( GameCoreCounter == 350)
		{
			//the alien ship disappears
			CoreSpace.alienShipXpos = -1;
			CoreSpace.alienShipYpos = -1;
			CoreSpace.alienShipLaser = -1;
		}
		
	}
	
	//---Creating, moving and removing the meteors ---	
	private void meteorMove()
	{

		//Creating meteor in every 2sec
		if( MeteorCounter >= 50 && CoreSpace.Meteors.size() <= 10 )
		{
			Meteor TempMet = new Meteor();
			
			TempMet.writeMeteorXpos(650);
			TempMet.writeMeteorYpos(rand.nextInt(480));
			
			CoreSpace.Meteors.addElement(TempMet);
			
			
			System.out.println("> Creating meteor");
			System.out.println("Number of Meteors: "+CoreSpace.Meteors.size());
			
			MeteorCounter = 0;
		}
		MeteorCounter++;
		
		
		//Moving the meteors
		int meteorXPosL = 0;
		for (int i=0; i<CoreSpace.Meteors.size(); i++)
		{
			meteorXPosL = CoreSpace.Meteors.get(i).getMeteorXpos() - CoreSpace.Meteors.get(i).getMeteorSpeed();
			CoreSpace.Meteors.get(i).writeMeteorXpos(meteorXPosL);
		}
		
		
		//Removing meteor if it leaves the screen
		for (int i=0; i < CoreSpace.Meteors.size(); i++)
		{
			if ( CoreSpace.Meteors.get(i).getMeteorXpos() < -10 )
			{
				CoreSpace.Meteors.remove(i);
				
				System.out.println("> Removing meteor");
			}
		}
		
	}
	
	
	//This function runs in every 40ms
	public void run()
	{
		//Generating the Space in the game
		myShipMove();
		otherShipMove();
		alienShipMove();
		meteorMove();
		
		
		//Count elapsed time
		GameCoreCounter++;
		
		//Reset the counter in every X second (25*X)
		if(GameCoreCounter >= 350)
		{
			GameCoreCounter = 0;
		}
	}
	
	// GameCore constructor
	GameCore(ShipControl LocalControl, ShipControl RemoteControl)
	{
		CoreSpace = new Space();
		myControl = LocalControl;
		otherControl = RemoteControl;
		
		rand = new Random();
		
	}
	
}

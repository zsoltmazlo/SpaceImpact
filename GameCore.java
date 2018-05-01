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
	public ShipControl otherControl;
	
	private NetServer CoreServer;
	private int Multiplayer = 0;
	
	// Counts elapsed time
	private int GameCoreCounter = 0;
	
	// Counts elapsed time for meteors
	private int MeteorCounter = 0;
	
	// Counts elapsed time for alien ships
	private int AlienCounter = 0;
	
	// Variables depending on difficulty level
	private int MeteorWidth   = 0;
	private int MeteorHeight  = 0;
	private int MeteorSpeed   = 0;
	private int MaxMeteors    = 0;
	private int MaxAlienShips = 0;
	private int AlienSpawnTime= 0;
	private int AlienShipSpeed= 0;
	
	// Random number generator
	private Random rand;
	
	// Probe Difficulty lvl variable
	private int DiffLvl = 0;
	
	
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
		
		// Creating an alien ship every 4sec
		if( AlienCounter >= AlienSpawnTime && CoreSpace.Aliens.size() < MaxAlienShips )
		{
			//AlienShip TempAlien = new AlienShip();
			AlienShip TempAlien = new AlienShip( 600, rand.nextInt(480), -1 , AlienShipSpeed );
			
			CoreSpace.Aliens.addElement(TempAlien);
			
			AlienCounter = 0;
		}
		AlienCounter++;	
		

		// Generating direction of alien ship
		for (int i=0; i<CoreSpace.Aliens.size(); i++)
		{
			if( AlienCounter % 10 == 0 && CoreSpace.Aliens.get(i).getAlienShipYpos() < 460 && CoreSpace.Aliens.get(i).getAlienShipYpos() > 20)
			{
				CoreSpace.Aliens.get(i).writeAlienShipYDirection(rand.nextInt(2));
				
			}
			
			if( CoreSpace.Aliens.get(i).getAlienShipYpos() >= 460 )
			{
				CoreSpace.Aliens.get(i).writeAlienShipYDirection(0);
			}
			
			if( CoreSpace.Aliens.get(i).getAlienShipYpos() <= 20 )
			{
				CoreSpace.Aliens.get(i).writeAlienShipYDirection(1);
			}
		}
			
		int AlienShipXPosN = 0;
		int AlienShipYPosN = 0;
		for (int i=0; i<CoreSpace.Aliens.size(); i++)
		{
			// Horizontal movement of alien ship
			AlienShipXPosN = CoreSpace.Aliens.get(i).getAlienShipXpos() - CoreSpace.Aliens.get(i).getAlienShipSpeed();
			CoreSpace.Aliens.get(i).writeAlienShipXpos(AlienShipXPosN);
			
			// Vertical movement of alien ship
			switch (CoreSpace.Aliens.get(i).getAlienShipYDirection())
			{
				case -1: CoreSpace.Aliens.get(i).writeAlienShipYpos(CoreSpace.Aliens.get(i).getAlienShipYpos()); break;
				case  0: AlienShipYPosN = CoreSpace.Aliens.get(i).getAlienShipYpos() - 3;
				 		 CoreSpace.Aliens.get(i).writeAlienShipYpos(AlienShipYPosN); break;
				case  1: AlienShipYPosN = CoreSpace.Aliens.get(i).getAlienShipYpos() + 3;
		 				 CoreSpace.Aliens.get(i).writeAlienShipYpos(AlienShipYPosN); break;
			}
		}
		
		//Removing meteor if it leaves the screen
		for (int i=0; i < CoreSpace.Aliens.size(); i++)
		{
			if ( CoreSpace.Aliens.get(i).getAlienShipXpos() < -10 )
			{
				CoreSpace.Aliens.remove(i);
			}
		}
		
	}
	
	//---Creating, moving and removing the meteors ---	
	private void meteorMove()
	{

		//Creating meteor in every 2sec
		if( MeteorCounter >= 50 && CoreSpace.Meteors.size() < MaxMeteors )
		{
			//Meteor TempMet = new Meteor();
			Meteor TempMet = new Meteor( 650, rand.nextInt(480), MeteorWidth, MeteorHeight, MeteorSpeed );
			
			CoreSpace.Meteors.addElement(TempMet);
			
			
			//System.out.println("> Creating meteor");
			//System.out.println("Number of Meteors: "+CoreSpace.Meteors.size());
			
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
				
				//System.out.println("> Removing meteor");
			}
		}
		
	}
	
	
	// Variables depending on difficulty level
	public void diffMode()
	{
		switch (DiffLvl)
		{
		case 0:	MeteorWidth = 10;
				MeteorHeight = 10;
				MeteorSpeed = 1;
				MaxMeteors = 4; 
				MaxAlienShips = 3;
				AlienShipSpeed = 2;
				AlienSpawnTime = 100; break;
				
		case 1:	MeteorWidth = 15;
				MeteorHeight = 15;
				MeteorSpeed = 2;
				MaxMeteors = 6;
				MaxAlienShips = 4;
				AlienShipSpeed = 3;
				AlienSpawnTime = 50; break;
		
		case 2:	MeteorWidth = 20;
				MeteorHeight = 20;
				MeteorSpeed = 3;
				MaxMeteors = 8;
				MaxAlienShips = 5;
				AlienShipSpeed = 4;
				AlienSpawnTime = 25; break;
		}
	}
	
	
	//This function runs in every 40ms
	public void run()
	{
		//Generating the Space in the game
		diffMode();
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
		
		if(Multiplayer == 1)
		{
			CoreServer.send(CoreSpace);
		}
	}
	
	// GameCore constructor
	GameCore(ShipControl LocalControl)
	{
		CoreSpace = new Space();
		myControl = LocalControl;
		otherControl = new ShipControl();
		
		rand = new Random();
		
	}
	
	public void setCoreServer(NetServer ServerToSend)
	{
		CoreServer = ServerToSend;
		Multiplayer = 1;
	}
	
}

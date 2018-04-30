package SpaceImpact_package;


public class AlienShip
{
	private int AlienShipXpos;
	private int AlienShipYpos;
	private int AlienShipYDirection;
	private int AlienShipSpeed;
	
	public AlienShip()
	{
		this.AlienShipXpos = -1;
		this.AlienShipYpos = -1;
		this.AlienShipYDirection = -1;
		this.AlienShipSpeed = 3;
	}
	
	public AlienShip( int XPos, int YPos, int YDir, int Speed )
	{
		this.AlienShipXpos = XPos;
		this.AlienShipYpos = YPos;
		this.AlienShipYDirection = YDir;
		this.AlienShipSpeed = Speed;
	}
	
	public int getAlienShipXpos()
	{
		return AlienShipXpos;
	}
	
	public int getAlienShipYpos()
	{
		return AlienShipYpos;
	}
	
	public int getAlienShipYDirection()
	{
		return AlienShipYDirection;
	}
	
	public int getAlienShipSpeed()
	{
		return AlienShipSpeed;
	}
	
	public void writeAlienShipXpos(int XPos)
	{
		AlienShipXpos=XPos;
	}

	public void writeAlienShipYpos(int YPos)
	{
		AlienShipYpos=YPos;
	}
	
	public void writeAlienShipYDirection(int YDir)
	{
		AlienShipYDirection=YDir;
	}
	
	public void writeAlienShipSpeed(int Speed)
	{
		AlienShipSpeed=Speed;
	}
	

}

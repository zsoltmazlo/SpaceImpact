package SpaceImpact_package;



public class Meteor
{
	private int meteorXpos = -1;
	private int meteorYpos = -1;
	private int meteorWidth  = 10;
	private int meteorHeight = 10;
	private int meteorSpeed = 3;
	
	public int getMeteorXpos()
	{
		return meteorXpos;
	}
	
	public int getMeteorYpos()
	{
		return meteorYpos;
	}
	
	public int getMeteorWidth()
	{
		return meteorWidth;
	}
	
	public int getMeteorHeight()
	{
		return meteorHeight;
	}
	
	public int getMeteorSpeed()
	{
		return meteorSpeed;
	}
	
	public void writeMeteorXpos(int XPos)
	{
		meteorXpos=XPos;
	}

	public void writeMeteorYpos(int YPos)
	{
		meteorYpos=YPos;
	}
	
	public void writeMeteorWidth(int Width)
	{
		meteorWidth=Width;
	}

	public void writeMeteorHeight(int Height)
	{
		meteorHeight=Height;
	}
	
	public void writeMeteorSpeed(int Speed)
	{
		meteorSpeed=Speed;
	}
	

}

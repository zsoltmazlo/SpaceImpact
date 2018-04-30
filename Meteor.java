package SpaceImpact_package;


public class Meteor
{
	private int meteorXpos;
	private int meteorYpos;
	private int meteorWidth;
	private int meteorHeight;
	private int meteorSpeed;
	
	public Meteor()
	{
		this.meteorXpos = -1;
		this.meteorYpos = -1;
		this.meteorWidth = 10;
		this.meteorHeight = 10;
		this.meteorSpeed = 1;
	}
	
	public Meteor( int XPos, int YPos, int Width, int Height, int Speed )
	{
		this.meteorXpos = XPos;
		this.meteorYpos = YPos;
		this.meteorWidth = Width;
		this.meteorHeight = Height;
		this.meteorSpeed = Speed;
	}
	
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

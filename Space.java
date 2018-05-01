package SpaceImpact_package;

import java.util.Vector;



public class Space implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	
	//--- myShip ---
	public int myShipXpos  = 100;
	public int myShipYpos  = 100;
	public int myShipLaser = -1;
	
	//--- otherShip ---
	public int otherShipXpos  = 200;
	public int otherShipYpos  = 200;
	public int otherShipLaser = -1;
	
	//--- Enemy ---
	public int alienShipXpos  = -1;
	public int alienShipYpos  = -1;
	public int alienShipLaser = -1;
	
	//--- Meteors ---
	public Vector<Meteor> Meteors = new Vector<Meteor>(0);
	public Vector<AlienShip> Aliens = new Vector<AlienShip>(0);
	
	//--- Game border ---
	
}

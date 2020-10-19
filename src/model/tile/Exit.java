package model.tile;

/**
 * This class builds on the Tile abstract class to specifically model an Exit Tile.
 * 
 * The Mr. Jack Character can escape through the Exit Tiles.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class Exit extends Tile{
	
//---  Constant Values   ----------------------------------------------------------------------
	
	/** constant char value representing the identity/type of this Tile subclass by a single character*/
	private static final char IDENTITY = 'e';

//---  Instance Variables   -------------------------------------------------------------------
	
	/** boolean value representing whether this Exit object is 'blocked' or not.*/
	boolean blocked;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the Exit type that assigns values to the instance variables
	 * described in the extended abstract class Tile to denote the identity and that a Character
	 * can stand on it; it also defaults the nature of this object's being blocked as false.
	 * 
	 */
	
	public Exit(int loca) {
		identity = IDENTITY;
		shareable = true;
		blocked = false;
		location = loca;
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	/**
	 * Setter method that assigns a provided value as the new status of this Exit object's being blocked. 
	 * 
	 * @param val - boolean value representing the new status of this Exit object's being blocked or not.
	 */
	
	public void setBlocked(boolean val) {
		blocked = val;
	}

//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that requests the status of this Exit object's being blocked or not. 
	 * 
	 * @return - returns a boolean value representing the status of this Exit object's being blocked. 
	 */
	
	public boolean getBlocked() {
		return blocked;
	}
	
}

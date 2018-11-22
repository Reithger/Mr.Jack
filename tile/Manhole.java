package tile;

/**
 * This class builds on the Tile abstract class to specifically model a Manhole Tile.
 * 
 * Characters can move through and end on Manhole Tiles.
 * 
 * Characters can travel through the Manhole to teleport to other Manhole Tiles.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class Manhole extends Tile{
	
//---  Constant Values   ----------------------------------------------------------------------
	
	/** constant char value representing the identity/type of this Tile subclass by a single character*/
	private static final char IDENTITY = 'm';

//---  Instance Variables   -------------------------------------------------------------------
	
	/** boolean instance variable representing whether or not this Manhole object is blocked off or not*/
	boolean covered;
	
	Tile[] connectedManholes;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the Manhole type that assigns values to the instance variables
	 * described in the extended abstract class Tile to denote the identity and that a Character
	 * can stand on it; it also defaults the nature of this object's being covered as false.
	 * 
	 */
	
	public Manhole(int loc) {
		identity =  IDENTITY;
		shareable = true;
		covered = false;
		location = loc;
	}

//---  Operations   ---------------------------------------------------------------------------

	/**
	 * 
	 */
	
	@Override
	public int[] getNeighbors() {
		int[] out = super.getNeighbors();
		int[] reallyOut = new int[out.length + connectedManholes.length];
		for(int i = 0; i < out.length; i++)
			reallyOut[i] = out[i];
		for(int i = out.length; i < out.length + connectedManholes.length; i++)
			reallyOut[i] = connectedManholes[i - out.length].getLocation();
		return reallyOut;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that requests the status of this Manhole object's being covered or not.
	 * 
	 * @return - returns a boolean value representing the status of this Manhole object's being covered.
	 */
	
	public boolean getCovered() {
		return covered;
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	/**
	 * Setter method that assigns a value to specify the status of this Manhole object's being covered.
	 * 
	 * @param val - boolean value representing the new status of this Manhole object's being covered.
	 */
	
	public void setCovered(boolean val) {
		covered = val;
	}
	
	public void assignManholeNeighbors(Tile[] manholes) {
		connectedManholes = manholes;
	}
}

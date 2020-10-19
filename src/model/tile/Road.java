package model.tile;

public class Road extends Tile{

//---  Constant Values   ----------------------------------------------------------------------
	
	/** constant char value representing the identity/type of this Tile subclass by a single character*/
	private static final char IDENTITY = 'r';

//---  Constructors   -------------------------------------------------------------------------

	/**
	 * Constructor for objects of the Manhole type that assigns values to the instance variables
	 * described in the extended abstract class Tile to denote the identity and that a Character
	 * can stand on it; it also defaults the nature of this object's being covered as false.
	 * 
	 */
	
	public Road(int loc) {
		identity =  IDENTITY;
		shareable = true;
		location = loc;
	}

	
}

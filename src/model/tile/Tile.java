package model.tile;

/**
 * This class generically models a Tile object, of which the Board is composed.
 * 
 * There are many sub-types of Tiles
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public abstract class Tile {
	
	private final static int NUMBER_OF_NEIGHBORS = 6;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** int[] instance variable containing the indexes of the NUMBER_OF_NEIGHBORS neighbors, their index relating to relative location. */
	int[] neighbors;
	/** char instance variable representing the type of Tile for interpretation*/
	char identity;
	/** boolean instance variable representing whether this Tile can also contain a Character or not*/
	boolean shareable;
	/** int value describing the index of this Tile object in the Board's array of Tile objects; unique identifier*/
	int location;
	/** int value describing where a barricade is positioned by the index of the barricade around the tile; -1 if no barricade*/
	int barricade;
	
//---  Setter Methods   -----------------------------------------------------------------------

	/**
	 * This method assigns an integer array to the Tile object, describing which Tiles (by index)
	 * are adjacent to this Tile.
	 * 
	 * @param in - int[] array object containing the indexes of adjacent Tiles to this Tile object
	 */
	
	public void assignNeighbors(int[] in) {
		neighbors = in;
	}

	/**
	 * 
	 * @param index
	 */
	
	public void setBarricade(int index) {
		barricade = index;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * Getter method that returns the array of integer values representing the indexes of the
	 * Tile's neighbors in the Board.
	 * 
	 * @return - returns an int[] object containing the indexes of this Tile object's neighbors in the Board
	 */
	
	public int[] getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Getter method that returns a char representing the type that this Tile is.
	 * 
	 * @return - returns a char value representing what type of Tile this Tile object is.
	 */
	
	public char getIdentity() {
		return identity;
	}

	/**
	 * Getter method that returns a boolean value representing whether this Tile can be
	 * stood on by a Character or not.
	 * 
	 * @return - returns a boolean value informing whether or not a Character can stand on it.
	 */
	
	public boolean canShare() {
		return shareable;
	}

	/**
	 * Getter method that returns an int value representing the location of the Tile object
	 * in the array it is stored within for purposes of unique differentiating between them.
	 * 
	 * @return - Returns an int value representing the position of this Tile in the array it is stored within.
	 */
	
	public int getLocation() {
		return location;
	}

	public int getBarricade() {
		return barricade;
	}
	
}

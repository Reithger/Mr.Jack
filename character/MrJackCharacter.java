package character;

import tile.Tile;
import game.GameModel;

/**
 * This abstract class defines the framework for which all Characters in the Mr. Jack game
 * must abide in what and how they do things.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public abstract class MrJackCharacter implements GameCharacter{

//---  Instance Variables   -------------------------------------------------------------------
	
	/** int value representing which Tile in the Board's Tile[] this MrJackCharacter is on, by index reference*/
	Tile tileIndex;
	/** String object representing the name associated to this MrJackCharacter object*/
	String name;
	/** int value representing the number of moves this MrJackCharacter can make in one turn (how many spaces)*/
	int numMoves;
	/** boolean value representing whether or not this MrJackCharacter is still a suspect to the Players*/
	boolean isSuspect;
	/** boolean value representing whether or not this MrJackCharacter is currently 'lit'*/
	boolean isLit;
	/** */
	String shortName;
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This method determines whether, given the Tile they are moving to and how far they have moved so far,
	 * the MrJackCharacter can move onto that tile.
	 * 
	 * @param tile - Tile object representing the space on the Board this MrJackCharacter object is potentially entering
	 * @param distance - int value representing how far this MrJackCharacter has already traveled. 
	 * @return - Returns a boolean value representing whether or not we can enter the provided Tile space.
	 */

	public boolean canMove(Tile start, Tile end, int distance) {
		return distance <= numMoves && end.canShare() && start.getBarricade() != end.getLocation();
	}

	/**
	 * This method determines whether this MrJackCharacter object can interact with
	 * the provided of Tile or not.
	 * 
	 * @param tile - Tile object upon which we query whether or not this MrJackCharacter can interact.
	 * @return - Returns a boolean value representing whether or not this MrJackCharacter can interact with the Tile.
	 */
	
	public boolean canInteract(Tile tile) {
		
		return false;
	}

	/**
	 * This abstract method represents the performance of a character's special ability, such
	 * as Sir William Gull's teleportation or John Smith's exchanging of lanterns.
	 * 
	 * @param choice - int[] object containing the relevant information necessary to perform this ability.  
	 * @return - returns a boolean value representing whether or not performing this ability was successful/legal or not.
	 */
	
	public abstract boolean ability(Tile ... choice);
	
	/**
	 * This method parses the provided Board object to transform that data into
	 * a useful format for this MrJackCharacter's special ability.
	 * 
	 * @param model - GameModel object representing this instance of the Mr. Jack game.
	 */
	
	public abstract void deriveFromModel(GameModel model);
	
	/**
	 * 
	 * Format: [Name] [Location] [Is lit?] [Is Suspected?]
	 * 
	 * @return
	 */
	
	public String convertToOutboundFormat() {
		return shortName + " " + (tileIndex == null ? -1 : tileIndex.getLocation()) + " " + (isLit ? "1" : "0") + " " + (isSuspect ? "1" : "0") + "\n";
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * Getter method that requests the status of this MrJackCharacter's being 'lit' on the board.
	 * 
	 * @return - Returns a boolean value representing whether this MrJackCharacter is 'lit' or not.
	 */
	
	public boolean getLit() {
		return isLit;
	}
	
	/**
	 * Getter method that requests the status of this MrJackCharacter's being suspected or not.
	 * 
	 * @return - Returns a boolean value representing the status of this MrJackCharacter's being suspected.
	 */
	
	public boolean getSuspect() {
		return isSuspect;
	}
	
	/**
	 * Getter method that requests the index in the Tile[] used for the Board in describing which
	 * Tile the MrJackCharacter is on.
	 * 
	 * @return - Returns an int value representing the Tile object that this MrJackCharacter resides on.
	 */
	
	public int getLocation() {
		if(tileIndex == null)
			return -1;
		return tileIndex.getLocation();
	}
	
	public Tile getTileLocation() {
		return tileIndex;
	}
	
	/**
	 * Getter method that requests the name of this MrJackCharacter object.
	 * 
	 * @return - Returns a String object representing the name associated to this MrJackCharacter object.
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	
	public int getDistance() {
		return numMoves;
	}
	
	public String getShortName() {
		return shortName;
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	/**
	 * Setter method that assigns a value designating the status of this MrJackCharacter's being 'lit'.
	 * 
	 * @param in - boolean value representing the new status of this MrJackCharacter's being 'lit'.
	 */
	
	public void setLit(boolean in) {
		isLit = in;
	}
	
	/**
	 * Setter method that assigns a value designating the status of this MrJackCharacter's being suspected.
	 * 
	 * @param in - boolean value representing the new status of this MrJackCharacter's being suspected.
	 */

	public void setSuspect(boolean in) {
		isSuspect = in;
	}

	/**
	 * Setter method that assigns a value designating the location in the Tile[] in Board that this
	 * MrJackCharacter is in.
	 * 
	 * @param index - int value representing the new position in the Tile[] in Board that this MrJackCharacter resides in.
	 */
	
	public void setLocation(Tile index) {
		tileIndex = index;
	}
	
	
//---  Ability Queries   ----------------------------------------------------------------------
	
	/**
	 * This abstract method describes the number of required integer values to be able to perform
	 * the ability associated to this MrJackCharacter object.
	 * 
	 * @return - returns an int value describing the number of necessary values to perform the associated ability.
	 */

	public int requiredValuesForAbility() {
		return 0;
	}

	public boolean[] abilityPermissionArray() {
		boolean[] out = new boolean[5];
		out[0] = hasToDoAbility();
		out[1] = canDoAbilityBefore();
		out[2] = canDoAbilityDuring();
		out[3] = canDoAbilityAfter();
		out[4] = canMoveAfterAbility();
		return out;
	}
	
	/**
	 * This abstract method describes whether or not this MrJackCharacter is required to use their
	 * ability or not.
	 * 
	 * @return - returns a boolean value representing whether or not this MrJackCharacter has to do their ability.
	 */
	
	//public abstract boolean hasToDoAbility();
	public boolean hasToDoAbility() {
		return true;
	}

	/**
	 * This abstract method describes whether or not this MrJackCharacter can do their ability before
	 * they perform a move action.
	 * 
	 * @return - Returns a boolean action describing whether or not this MrJackCharacter can do their ability before moving.
	 */
	
	//public abstract boolean canDoAbilityBefore();
	public boolean canDoAbilityBefore() {
		return true;
	}

	/**
	 * This abstract method describes whether or not this MrJackCharacter can do their ability while
	 * they perform a move action.
	 * 
	 * @return - Returns a boolean action describing whether or not this MrJackCharacter can do their ability while moving.
	 */
		
//	public abstract boolean canDoAbilityDuring();
	public boolean canDoAbilityDuring() {
		return true;
	}

	/**
	 * This abstract method describes whether or not this MrJackCharacter can do their ability after
	 * they perform a move action.
	 * 
	 * @return - Returns a boolean action describing whether or not this MrJackCharacter can do their ability after moving.
	 */
	
//	public abstract boolean canDoAbilityAfter();
	public boolean canDoAbilityAfter() {
		return true;
	}

	/**
	 * 
	 * @return
	 */
	
	//public abstract boolean canMoveAfterAbility();
	public boolean canMoveAfterAbility() {
		return true;
	}
	
}

package character;

import tile.Building;
import tile.Tile;
import game.Board;
import game.GameModel;

import java.util.ArrayList;

public class MissStealthy extends MrJackCharacter{

//---  Constant Values   ----------------------------------------------------------------------

	/** Constant String object representing the name of this InspectorLestrade object*/
	private static final String NAME = "MissStealthy";
	/** Constant int value representing the total number of moves this InspectorLestrade object can do in one turn*/
	private static final int NUM_MOVES = 4;
	/** Constant String object representing the shorthand name for this MrJackCharacter object*/
	private static final String SHORT_NAME = "M.S";
	
	private static final char[] CANNOT_MOVE_THROUGH = new char[0];

// --- Instance Variables ---------------------------------------------------------------------
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the MissStealthy class, assigning the constant values for the
	 * object's name and number of moves. 
	 */
	
	public MissStealthy() {
		name = NAME;
		numMoves = NUM_MOVES;
		shortName = SHORT_NAME;
	}

//---  Operations   ---------------------------------------------------------------------------
	
	public boolean canMove(Tile tile, int dist) {
		if((this.ability(tile))&& dist <= numMoves){
			return true;
		} else return false;
	}

	@Override
	public boolean ability(Tile ... choice) {
		for(char c : CANNOT_MOVE_THROUGH){
			if (choice[0].getIdentity() == c)
				return false;
		}
		return true;
	}

	@Override
	public void deriveFromModel(GameModel model) {
		
	}
	
//---  Ability Queries   ----------------------------------------------------------------------

	@Override
	public int requiredValuesForAbility() {
		return 0;
	}

	@Override
	public boolean hasToDoAbility() {
		return false;
	}

	@Override
	public boolean canDoAbilityBefore() {
		return false;
	}

	@Override
	public boolean canDoAbilityDuring() {
		return true;
	}

	@Override
	public boolean canDoAbilityAfter() {
		return false;
	}

	@Override
	public boolean canMoveAfterAbility() {
		return true;
	}
}

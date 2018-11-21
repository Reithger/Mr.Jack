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
		Tile[] tempChoice = new Tile[1];
		tempChoice[0] = tile;
		if((this.ability(tempChoice))&& dist <= numMoves){
			return true;
		} else return false;
	}

	@Override
	public boolean ability(Tile[] choice) {
		//if it's a building tile, she can move through it!
		for(Tile tile: choice){
			if ((tile.getIdentity() == 'b')||(tile.getIdentity() == 'm')||(tile.getIdentity() == 't')){
				return true;
			}
		}
		return false;
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

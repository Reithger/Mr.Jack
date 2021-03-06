package model.character.implementation;

import model.GameModel;
import model.character.MrJackCharacter;
import model.game.Board;
import model.tile.Building;
import model.tile.Tile;

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
		this.setLocation(null);
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
	public boolean hasToDoAbility() {
		return false;
	}

	@Override
	public boolean canDoAbilityBefore() {
		return false;
	}

	@Override
	public boolean canDoAbilityAfter() {
		return false;
	}

}

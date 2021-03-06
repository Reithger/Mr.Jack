package model.character.implementation;

import java.util.ArrayList;

import model.GameModel;
import model.character.MrJackCharacter;
import model.game.Board;
import model.tile.Manhole;
import model.tile.Tile;


public class Madame extends MrJackCharacter{
//---  Constant Values   ----------------------------------------------------------------------

	/** Constant String object representing the name of this Madame object*/
	private static final String NAME = "Madame";
	/** Constant int value representing the total number of moves this Madame object can do in one turn*/
	private static final int NUM_MOVES = 6;
	/** Constant String object representing the shorthand name for this MrJackCharacter object*/
	private static final String SHORT_NAME = "MDM";

//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the Madame class, assigning constant values
	 * for the object's name and number of moves, and also initializing the array of relevant
	 * tiles (containing Exit tiles.)
	 */
	
	public Madame(){
		name = NAME;
		numMoves = NUM_MOVES;
		shortName = SHORT_NAME;
		this.setLocation(null);
	}

//---  Operations   ---------------------------------------------------------------------------

	@Override
	public boolean ability(Tile ... choice) {
		if(choice[0].getIdentity() == 'm' && choice[1].getIdentity() == 'm')
			return false;
		return true;
	}

	@Override
	public void deriveFromModel(GameModel model) {
		return;
	}
	
	@Override
	public boolean canMove(Tile start, Tile end, int distance) {
		boolean prelim = super.canMove(start, end, distance);
		return prelim && ability(start, end);
	}
	
//---  Ability Queries   ----------------------------------------------------------------------
	
	@Override
	public boolean hasToDoAbility() {
		//she must do ability at some point
		return false;
	}

	@Override
	public boolean canDoAbilityBefore() {
		//she can't do ability now
		return false;
	}

	@Override
	public boolean canDoAbilityAfter() {
		//she can't do ability now
			return false;
		}
	
}

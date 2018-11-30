package character;

import java.util.ArrayList;
import game.Board;
import tile.Tile;
import tile.Exit;
import game.GameModel;



public class InspectorAbberline extends MrJackCharacter {

//---  Constant Values   ----------------------------------------------------------------------
	
	/** Constant String object representing the name of this InspectorAbberline object*/
	private static final String NAME = "InspectorAbberline";
	/** Constant int value representing the total number of moves this InspectorAbberline object can do in one turn*/
	private static final int NUM_MOVES = 3;
	/** Constant String object representing the shorthand name for this MrJackCharacter object*/
	private static final String SHORT_NAME = "I.A";
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** ArrayList<<r>Exit> object describing the Tile objects associated to this InspectorAbberline object*/
	private ArrayList<Exit> relevantTiles;

//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the InspectorAbberline class, assigning constant values
	 * for the object's name and number of moves, and also initializing the array of relevant
	 * tiles (containing Exit tiles.)
	 */
	
	public InspectorAbberline(){
		name = NAME;
		numMoves = NUM_MOVES;
		relevantTiles = new ArrayList<Exit>();
		shortName = SHORT_NAME;
	}

//---  Operations   ---------------------------------------------------------------------------

	@Override
	public boolean ability(Tile[] choice) {
		//get neighbour tiles
		//check for characters on tiles
		//update those references with the restrictedmovementdec
		
		return false;
	}

	@Override
	public void deriveFromModel(GameModel model) {
		//get neighbour Tiles
	}
	
//---  Ability Queries   ----------------------------------------------------------------------
	
	@Override
	public int requiredValuesForAbility() {
		return 0;
	}

	@Override
	public boolean hasToDoAbility() {
		//he must do ability at some point
		return true;
	}

	@Override
	public boolean canDoAbilityBefore() {
		//he can do ability now
		return true;
	}

	@Override
	public boolean canDoAbilityDuring() {
		//he can not do ability now
		return false;
	}

	@Override
	public boolean canDoAbilityAfter() {
		//he can do ability now
		return true;
	}
	

	@Override
	public boolean canMoveAfterAbility() {
		return true;
	}
}



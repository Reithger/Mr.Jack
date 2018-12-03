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
	private GameCharacter[] charactersInPlay;

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
		this.setLocation(null);
	}

//---  Operations   ---------------------------------------------------------------------------

	@Override
	public boolean ability(Tile[] choice) {
		//get neighbor tiles
		int[] neighbors=getTileLocation().getNeighbors();
		//check for characters on tiles
		for(int tileLoc: neighbors) {
			for(GameCharacter m: charactersInPlay) {
				if(m.getLocation()==tileLoc) {
					m=new RestrictedMovementDec(m);
				}
			}
		}
		//update those references with the restrictedmovementdec
		
		return false;
	}

	@Override
	public void deriveFromModel(GameModel model) {
		charactersInPlay=model.getCharacters();
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



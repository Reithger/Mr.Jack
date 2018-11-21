package character;

import java.util.ArrayList;

import game.Board;
import game.GameModel;
import tile.Tile;
import tile.Lantern;

/**
 * 
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class JohnSmith extends MrJackCharacter {

//---  Constant Values   ----------------------------------------------------------------------

	/** Constant String object representing the name of this JohnSmith object*/
	private static final String NAME = "JohnSmith";
	/** Constant int value representing the total number of moves this JohnSmith object can do in one turn*/
	private static final int NUM_MOVES = 3;
	/** Constant String object representing the shorthand name for this MrJackCharacter object*/
	private static final String SHORT_NAME = "J.S";
	
//---  Instance Variables   -------------------------------------------------------------------

	/** ArrayList<<r>Exit> object describing the Tile objects associated to this InspectorLestrade object*/
	private ArrayList<Lantern> relevantTiles;

//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the JohnSmith class, assigning constant values for the
	 * object's name and number of moves, and also initializing the array of relevant
	 * tiles (containing Lantern tiles.)
	 */
	
	public JohnSmith() {
		name = NAME;
		numMoves = NUM_MOVES;
		relevantTiles = new ArrayList<Lantern>();
		shortName = SHORT_NAME;
	}

//---  Operations   ---------------------------------------------------------------------------
	
	@Override
	public boolean ability(Tile[] choice) {
		Lantern on = null;
		Lantern off = null;
		for(Lantern t : relevantTiles) {
			if(t.getLocation() == choice[0].getLocation()) {
				if(t.getLight())
					on = t;
				else
					off = t;
			}
			if(t.getLocation() == choice[1].getLocation()) {
				if(t.getLight())
					on = t;
				else
					off = t;
			}
		}
		if(on == null || off == null)
			return false;
		else {
			on.setLight(false);
			off.setLight(true);
			return true;
		}
	}
	
	@Override
	public void deriveFromModel(GameModel model) {
		//Find all lantern tiles
		Board gameBoard = model.getBoard();
		Tile [] exitTileSet = gameBoard.getTilesOfType('l');
		for (Tile tile: exitTileSet){
			relevantTiles.add((Lantern) tile);
		}
	}
	
//---  Ability Queries   ----------------------------------------------------------------------
	
	@Override
	public int requiredValuesForAbility() {
		return 2;
	}

	@Override
	public boolean hasToDoAbility() {
		return true;
	}

	@Override
	public boolean canDoAbilityBefore() {
		return true;
	}

	@Override
	public boolean canDoAbilityDuring() {
		return false;
	}

	@Override
	public boolean canDoAbilityAfter() {
		return true;
	}

	@Override
	public boolean canMoveAfterAbility() {
		return true;
	}
}
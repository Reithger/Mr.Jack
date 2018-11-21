package character;

import java.util.ArrayList;

import game.Board;
import game.GameModel;
import tile.Manhole;
import tile.Tile;


public class Madame extends MrJackCharacter{
	//---  Constant Values   ----------------------------------------------------------------------
	
		/** Constant String object representing the name of this Madame object*/
		private static final String NAME = "Madame";
		/** Constant int value representing the total number of moves this Madame object can do in one turn*/
		private static final int NUM_MOVES = 6;
		/** Constant String object representing the shorthand name for this MrJackCharacter object*/
		private static final String SHORT_NAME = "M";
		
	//---  Instance Variables   -------------------------------------------------------------------
		
		/** ArrayList<<r>Exit> object describing the Tile objects associated to this InspectorLestrade object*/
		private ArrayList<Manhole> relevantTiles;

		
	//---  Constructors   -------------------------------------------------------------------------
		
		/**
		 * Constructor for objects of the Madame class, assigning constant values
		 * for the object's name and number of moves, and also initializing the array of relevant
		 * tiles (containing Exit tiles.)
		 */
		
		public Madame(){
			name = NAME;
			numMoves = NUM_MOVES;
			relevantTiles = new ArrayList<Manhole>();
			shortName = SHORT_NAME;
		}

	//---  Operations   ---------------------------------------------------------------------------

		@Override
		public boolean ability(Tile[] choice) {
			//can't use manholes for traveling
			
			return true;
		}

		@Override
		public void deriveFromModel(GameModel model) {
			//Find all manhole tiles
			Board gameBoard = model.getBoard();
			Tile [] manholeTileSet = gameBoard.getTilesOfType('m');
			for (Tile tile: manholeTileSet){
				relevantTiles.add((Manhole) tile);
			}
		}
		
	//---  Ability Queries   ----------------------------------------------------------------------
		
		@Override
		public int requiredValuesForAbility() {
			return 2;
		}

		@Override
		public boolean hasToDoAbility() {
			//she must do ability at some point
			return true;
		}

		@Override
		public boolean canDoAbilityBefore() {
			//she can't do ability now
			return false;
		}

		@Override
		public boolean canDoAbilityDuring() {
			//she can not do ability now
			return true;
		}

		@Override
		public boolean canDoAbilityAfter() {
			//she can't do ability now
			return false;
		}
		

		@Override
		public boolean canMoveAfterAbility() {
			return false;
		}
}

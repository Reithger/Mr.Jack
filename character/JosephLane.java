package character;

import java.util.ArrayList;

import game.Board;
import game.GameModel;
import tile.Lantern;
import tile.Tile;

public class JosephLane extends MrJackCharacter{
	//---  Constant Values   ----------------------------------------------------------------------

		/** Constant String object representing the name of this JohnSmith object*/
		private static final String NAME = "JosephLane";
		/** Constant int value representing the total number of moves this JohnSmith object can do in one turn*/
		private static final int NUM_MOVES = 3;
		/** Constant String object representing the shorthand name for this MrJackCharacter object*/
		private static final String SHORT_NAME = "J.L";
		
	//---  Instance Variables   -------------------------------------------------------------------

		Tile barricadeOne;
		Tile barricadeTwo;

	//---  Constructors   -------------------------------------------------------------------------
		
		/**
		 * Constructor for objects of the JohnSmith class, assigning constant values for the
		 * object's name and number of moves, and also initializing the array of relevant
		 * tiles (containing Lantern tiles.)
		 */
		
		public JosephLane() {
			name = NAME;
			numMoves = NUM_MOVES;
			shortName = SHORT_NAME;
		}

	//---  Operations   ---------------------------------------------------------------------------
		
		@Override
		public boolean ability(Tile ... choice) {
			int locTileOne = choice[0].getLocation();
			boolean adjacent = false;
			for(int index : choice[1].getNeighbors()) {
				if(locTileOne == index)
					adjacent = true;
			}
			if(adjacent && choice[0].canShare() && choice[1].canShare()) {
				if(barricadeOne != null) {
					barricadeOne.setBarricade(-1);
					barricadeTwo.setBarricade(-1);
				}
				barricadeOne = choice[0];
				barricadeTwo = choice[1];
				barricadeOne.setBarricade(barricadeTwo.getLocation());
				barricadeTwo.setBarricade(barricadeOne.getLocation());
				return true;
			}
			return false;
		}
		
		@Override
		public void deriveFromModel(GameModel model) {
			return;
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

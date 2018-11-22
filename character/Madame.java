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
		private static final String SHORT_NAME = "MDM";
		
	//---  Instance Variables   -------------------------------------------------------------------
		
		@Override
		public boolean canMove(Tile start, Tile end, int distance) {
			boolean prelim = super.canMove(start, end, distance);
			return prelim && ability(start, end);
			
		}
		
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
		
	//---  Ability Queries   ----------------------------------------------------------------------
		
		@Override
		public int requiredValuesForAbility() {
			return 0;
		}

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
			return true;
		}
}

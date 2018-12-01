package character;

import tile.Tile;
import game.GameModel;

public class SirWilliamGull extends MrJackCharacter{
//---  Constant Values   ----------------------------------------------------------------------\

	/** Constant String object representing the name of this InspectorLestrade object*/
	private static final String NAME = "SirWilliamGull";
	/** Constant int value representing the total number of moves this InspectorLestrade object can do in one turn*/
	private static final int NUM_MOVES = 3;
	/** Constant String object representing the shorthand name for this MrJackCharacter object*/
	private static final String SHORT_NAME = "S.W.G";

//---  Instance Variables   -------------------------------------------------------------------

	/** ArrayList<<r>MrJackCharacter> object describing the Tile objects associated to this InspectorLestrade object*/
	private MrJackCharacter[] characters;

//---  Constructors   -------------------------------------------------------------------------

	/**
	 * Constructor for objects of the InspectorLestrade class, assigning the Board object a null
	 * which the InspectorLestrade class will query Exit statuses when prompted.
	 */

	public SirWilliamGull(){
		name = NAME;
		numMoves = NUM_MOVES;
		characters = new MrJackCharacter[4];
		shortName = SHORT_NAME;
		this.setLocation(null);
	}

//---  Operations   ---------------------------------------------------------------------------
	
	@Override
	public boolean ability(Tile[] choice) {
		for(int x = 0; x < characters.length; x++){
			//if the character on the tile is in the list of characters in play
			//then return true, else return false
			MrJackCharacter character = characters[x];
			if(character.getLocation() == choice[0].getLocation()) {
				Tile loc = character.getTileLocation();
				character.setLocation(tileIndex);
				tileIndex = loc;
				return true;
			}
		}
		return false;
	}

	@Override
	public void deriveFromModel(GameModel game) {
		//find all the locations of the characters in play
		characters = game.getCharacters();
	}

//---  Ability Queries   ----------------------------------------------------------------------
	
	@Override
	public int requiredValuesForAbility() {
		return 1;
	}

	@Override
	public boolean hasToDoAbility() {
		return false;
	}

	@Override
	public boolean canDoAbilityDuring() {
		return false;
	}

	@Override
	public boolean canDoAbilityAfter() {
		return false;
	}
	
	@Override
	public boolean canMoveAfterAbility() {
		return false;
	}

}

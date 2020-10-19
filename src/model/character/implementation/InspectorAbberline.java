package model.character.implementation;

import java.util.ArrayList;
import java.util.Arrays;

import model.GameModel;
import model.character.GameCharacter;
import model.character.MrJackCharacter;
import model.character.RestrictedMovementDec;
import model.game.Board;
import model.tile.Exit;
import model.tile.Tile;



public class InspectorAbberline extends MrJackCharacter {

//---  Constant Values   ----------------------------------------------------------------------
	
	/** Constant String object representing the name of this InspectorAbberline object*/
	private static final String NAME = "InspectorAbberline";
	/** Constant int value representing the total number of moves this InspectorAbberline object can do in one turn*/
	private static final int NUM_MOVES = 3;
	/** Constant String object representing the shorthand name for this MrJackCharacter object*/
	private static final String SHORT_NAME = "I.A";
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private GameCharacter[] charactersInPlay;
	private boolean[] restrictedMovementChars;

//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the InspectorAbberline class, assigning constant values
	 * for the object's name and number of moves, and also initializing the array of relevant
	 * tiles (containing Exit tiles.)
	 */
	
	public InspectorAbberline(){
		name = NAME;
		numMoves = NUM_MOVES;
		shortName = SHORT_NAME;
		this.setLocation(null);
	}

//---  Operations   ---------------------------------------------------------------------------

	@Override
	public boolean ability(Tile[] choice) {
		int[] abberlineNeighbourTileLocs=getTileLocation().getNeighbors();
		int numCharsInPlay=charactersInPlay.length;
		for(int tileLoc: abberlineNeighbourTileLocs) {
			for(int i=0; i<numCharsInPlay;i++) {
				if(charactersInPlay[i].getLocation()==tileLoc && restrictedMovementChars[i]==false && charactersInPlay[i].getLocation()!=getLocation()) {
					charactersInPlay[i]=new RestrictedMovementDec(charactersInPlay[i]);
					restrictedMovementChars[i]=true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void deriveFromModel(GameModel model) {
		charactersInPlay=model.getActiveCharacters();
		restrictedMovementChars=new boolean[charactersInPlay.length];
	}
	
	public void removeRestrictions() {
		int numCharsInPlay=charactersInPlay.length;
		for(int i=0; i<numCharsInPlay;i++) {
			if(restrictedMovementChars[i]==true) {
				charactersInPlay[i]=((RestrictedMovementDec)charactersInPlay[i]).removeDecorator();
				restrictedMovementChars[i] = false;
			}
		}
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



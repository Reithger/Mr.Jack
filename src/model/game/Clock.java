package model.game;

import model.tile.Lantern;
import model.tile.Tile;

/**
 * This class models the Clock keeping track of the game's state as the turn order progresses.
 * 
 * Controls the state of Lanterns, the order of play between Players.
 * 
 * TODO: Should really be an interface between this and GameModel
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class Clock {

//---  Instance Variables   -------------------------------------------------------------------
	
	/** Tile[] object containing references to all Lantern objects in the Mr. Jack game*/
	Tile[] lanterns;
	/** int value representing which turn the Mr. Jack game is currently in*/
	int turnValue;
	/** int value representing the maximum number of lanterns that should be turned off by the progression of turns*/
	int maxLanternsOff;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the Clock type, assigning the provided values for the associated
	 * Lanterns and number of Lanterns to turn off, defaulting the turn number to 0.
	 * 
	 * @param givenLanterns - Lantern[] object containing references to all Lanterns in the Mr. Jack game.
	 * @param lanternLimit - int value representing the number of Lanterns to turn off as the game progresses.
	 */
	
	public Clock(Tile[] givenLanterns, int lanternLimit, int lanternsOffStart) {
		lanterns = givenLanterns;
		turnValue = 0;
		maxLanternsOff = lanternLimit;
		lanternsOffStart = lanternsOffStart < 1 ? 1 : lanternsOffStart;
		for(int i = lanterns.length - 1; i >= lanterns.length - lanternsOffStart; i--) {
			((Lantern)(lanterns[i])).setLight(false);
		}
	}

//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This method iterates the turn counter, progressing the game onward.
	 * 
	 */
	
	public void iterateTurn() {
		turnValue++;
		manageLanterns();
	}
	
	public String convertToOutboundFormat() {
		return turnValue + "\n";
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * Getter method that requests the turn counter for the associated Mr. Jack game.
	 * 
	 * @return - Returns an int value representing the Mr. Jack game's turn value.
	 */
	
	public int getTurn() {
		return turnValue;
	}
		
//---  Helper Methods   -----------------------------------------------------------------------

	/**
	 * Helper method that interprets the current turn value for knowing how to handle
	 * the associated Lantern objects.
	 *  
	 */
	
	private void manageLanterns() {
		if(turnValue <= maxLanternsOff) {
			((Lantern)(lanterns[turnValue])).setLight(false);
		}
	}

}

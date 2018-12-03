package player;

import character.GameCharacter;
import java.util.ArrayList;

/**
 * This class models the Mr. Jack type of Player for the Mr. Jack game.
 * 
 * The key feature for Mr. Jack is knowing the Mr. Jack character and being able to have them escape.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class MrJack extends Player{
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for objects of the MrJack type that assigns a given MrJackCharacter as the saved
	 * entity representing Mr. Jack and initializes the ArrayList<<r>MrJackCharacter> alibis object.
	 * 
	 * @variable mrJack  - MrJackCharacter object describing which character in the game is Mr. Jack
	 * @variable alibis - List of MrJackCharacters who have been drawn from the alibi deck (and are therefore not Mr Jack)
	 */
	
	public MrJack() {
		mrJack = null;
		alibis = new ArrayList<GameCharacter>();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that informs the caller of who Mr. Jack is; i.e, which character
	 * is secretly Mr. Jack.
	 * 
	 * @return - Returns a MrJackCharacter object describing 
	 */
	
	public GameCharacter whoIsMrJack() {
		return mrJack;
	}

	/**
	 * This method queries whether the MrJack Player has won the game through waiting
	 * out the timer or not.
	 * 
	 * @param isOver - boolean value representing whether the game was found to have 'run out' the clock
	 * @return - Returns a boolean value representing the victory status of this MrJack object
	 */
	
	public boolean hasWonTimer(boolean isOver) {
		// TODO Auto-generated method stub
		return isOver;
	}

	/**
	 * This method queries whether the MrJack Player has won the game by having Mr. Jack
	 * escape from the Board; this is done by placing Mr. Jack on an exit and having the turn
	 * end with them not being 'lit'.
	 * 
	 * @param onExit - boolean value representing whether the Mr. Jack character is on an exit
	 * @return - Returns a boolean value describing whether the MrJack Player has won or not.
	 */
	
	public boolean hasWonEscape(boolean onExit, int turn) {
		//Check if the character is lit or not if is on exit
		if(turn == 0)
			return false;
		boolean litStatus = mrJack.getLit();
		if (!litStatus || onExit){
			return true;
		} else {
			return false;
		}
	}
	
}

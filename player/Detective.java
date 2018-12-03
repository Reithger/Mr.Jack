package player;

import java.util.ArrayList;

import character.GameCharacter;


/**
 * This class models the Detective type of Player for Mr. Jack.
 * 
 * The key feature for Detective is being able to accuse a Character.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class Detective extends Player{
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Detective() {
		mrJack = null;
		alibis = new ArrayList<GameCharacter>();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that queries whether the provided MrJackCharacter is the same as
	 * the predefined MrJackCharacter who is Mr. Jack; that is, when accusing a character
	 * in the game, was the correct person accused?
	 * 
	 * @param accused - MrJackCharacter object representing the character being accused
	 * @return - Returns a boolean value representing the result of the accusation; true if successful, false otherwise.
	 */
	
	public boolean hasWonAccusation(GameCharacter accused) {
		if(accused.equals(mrJack))
			return true;
		return false;
	}

}

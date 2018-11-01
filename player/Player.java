package player;

import character.MrJackCharacter;
import java.util.ArrayList;

/**
 * This class generically models the Players of the Mr. Jack game.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public abstract class Player {

//---  Instance Variables   -------------------------------------------------------------------

	/** MrJackCharacter object representing the MrJackCharacter character that is Mr. Jack in this instance of the game.*/
	MrJackCharacter mrJack;
	/** MrJackCharacter[] object representing the set of MrJackCharacters that have been proven innocent.*/
	ArrayList<MrJackCharacter> alibis;
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that returns the set of MrJackCharacter objects which that Player
	 * has knowledge of; their presence represents that they are known to be innocent
	 * to that Player.
	 * 
	 * @return - returns a MrJackCharacter[] object containing MrJackCharacters with alibis.
	 */
	
	public ArrayList<MrJackCharacter> getAlibis(){
		return alibis;
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	/**
	 * Setter method that assigns the provided MrJackCharacter as Mr. Jack for this Player object.
	 * 
	 * @param inJack - MrJackCharacter object representing Mr. Jack for this iteration of the game.
	 */
	
	public void assignMrJack(MrJackCharacter inJack) {
		mrJack = inJack;
	}
	
}
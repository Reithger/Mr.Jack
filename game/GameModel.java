package game;

import player.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import character.MrJackCharacter;
import java.util.Random;

/**
 * Central point for running/processing the Mr. Jack Game
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class GameModel {

//---  Constant Values   ----------------------------------------------------------------------
	
	/** int constant value representing the maximum number of MrJackCharacters that can be in a single game instance*/
	private static final int NUMBER_ACTIVE_CHARACTERS = 4;
	/** int constant value representing the number of lanterns that will be turned off*/
	private static final int LANTERN_LIMIT = 4;
	private static final int LANTERNS_OFF = 1;
	/** int constant value representing the number of turns in one game instance*/
	private static final int NUMBER_OF_TURNS = 2;
	/** String[] constant object representing the types of Players that can be active at any moment*/
	private static final String[] PLAYERS = {"Detective", "Mr.Jack"};
	
//---  Instance Variables   -------------------------------------------------------------------

	/** MrJackCharacter[] object representing all of the viable MrJackCharacters that can be used in this game*/
	MrJackCharacter[] allMrJackCharacters;
	/** MrJackCharacter[] object representing the subset of allMrJackCharacters that are being used in this instance of the game*/
	MrJackCharacter[] activeMrJackCharacters;
	/** MrJackCharacter[] object representing the subset of activeMrJackCharacters that have already been used in this round*/
	MrJackCharacter[] usedMrJackCharacters;
	/** MrJackCharacter representing the chosen character that the active player is using for their turn*/
	MrJackCharacter currentMrJackCharacter;
	/** HashSet<<r>Integer> object representing which MrJackCharacters in a turn have already been used by index*/
	ArrayList<Integer> selectedMrJackCharacters;
	/** Detective object representing one of the two players in this instance of the game*/
	Detective detective;
	/** MrJack object representing one of the two players in this instance of the game*/
	MrJack mrJack;
	/** Board object containing the set of Tiles that the game's characters are on; visual representation to the users*/
	Board board;
	/** Clock object that keeps track of the game's state via turn counter, also controlling the turning off of lanterns*/
	Clock clock;
	/** boolean instance variable representing whether or not the game has ended*/
	boolean gameOver;
	/** int value representing which player is the currently active player; who is controlling the currentMrJackCharacter?*/
	int player;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for GameModel objects that is provided a file describing the game's board and
	 * a list of potential Characters to use in an instance of the game.
	 * 
	 * @param structure - File object describing the nature of the game's board
	 * @param potentialMrJackCharacters - List of MrJackCharacter objects to be used in the game.
	 */
	
	public GameModel(File structure, MrJackCharacter ... potentialMrJackCharacters) {
		board = deriveBoard(structure);
		allMrJackCharacters = potentialMrJackCharacters;
		selectedMrJackCharacters = new ArrayList<Integer>();
	}
	
//---  Game Behaviors   -----------------------------------------------------------------------

	/**
	 * This method starts an instance of the Mr. Jack game, resetting/defaulting values
	 * that require such between distinct instances of the game.
	 * 
	 */
	
	public void startGame() {
		mrJack = new MrJack();
		detective = new Detective();
		player = 0;
		clock = deriveClock();
		activeMrJackCharacters = deriveMrJackCharacters(allMrJackCharacters);
		gameOver = false;
		selectedMrJackCharacters.clear();
		currentMrJackCharacter = null;
		usedMrJackCharacters = new MrJackCharacter[0];
		Random rand = new Random();
		HashSet<Integer> used = new HashSet<Integer>();
		for(MrJackCharacter mjc : activeMrJackCharacters) {
			mjc.setSuspect(true);
			mjc.setLit(false);
			mjc.deriveFromModel(this);
			int loc = rand.nextInt(board.getNumberOfTiles());
			while(used.contains(loc) || !board.getTileAtLocation(loc).canShare()) {
				loc = rand.nextInt(board.getNumberOfTiles());
			}
			mjc.setLocation(loc);
			used.add(loc);
		}
		mrJack.assignMrJack(activeMrJackCharacters[rand.nextInt(activeMrJackCharacters.length)]);
	}

	/**
	 * This method starts a turn in the current Mr. Jack game, getting the MrJackCharacters
	 * to be used during this turn and resetting the tracker for which have been used so far.
	 * 
	 */
	
	public void startTurn() {
		usedMrJackCharacters = characterSetPerTurn(usedMrJackCharacters);
		selectedMrJackCharacters = new ArrayList<Integer>();
		currentMrJackCharacter = null;
	}

	/**
	 * This method assigns the current MrJackCharacter that is being controlled by the active
	 * Player, informing the caller whether or not the choice was valid.
	 * 
	 * @param choice - int value representing the index in viable MrJackCharacters that the user wants to control
	 * @return - Returns a boolean value describing whether or not the user choice was legal
	 */
	
	public boolean chooseMrJackCharacter(int choice) {
		int loc = -1;
		for(MrJackCharacter mjc : usedMrJackCharacters) {
			loc++;
			if(selectedMrJackCharacters.contains(loc))
				continue;
			if(mjc.getLocation() == choice) {
				currentMrJackCharacter = mjc;
				break;
			}
		}
		if(currentMrJackCharacter == null)
			return false;
		selectedMrJackCharacters.add(loc);
		return true;
	}

	/**
	 * This method tentatively moves the currently selected MrJackCharacter to the specified
	 * location by index in the Board's array of Tiles.
	 * 
	 * @param choice - int value designating the new Tile location of the active MrJackCharacter by index in the Board
	 * @return - Returns a boolean value describing whether or not the chosen location was legal
	 */
	
	public boolean moveMrJackCharacter(int choice) {
		boolean[] reachable = board.getLegalMovements(currentMrJackCharacter, getCharacterLocations());
		if(reachable[choice]) {
			currentMrJackCharacter.setLocation(choice);
		}
		return reachable[choice];
	}

	/**
	 * This method tentatively performs the currently selected MrJackCharacter's special ability,
	 * taking an array of int values that contains all the necessary information to use their ability.
	 * 
	 * @param choice - int[] object describing the necessary information to conduct the MrJackCharacter's ability
	 * @return - Returns a boolean value describing whether the action was successful (viable) or not.
	 */
	
	public boolean characterAction(int[] choice) {
		if(choice.length == currentMrJackCharacter.requiredValuesForAbility()) {
			return (currentMrJackCharacter.ability(board.getTiles(choice)));
		}
		return false;
	}

	/**
	 * This method handles the case of the user accusing a character of being Mr. Jack,
	 * returning the result of that accusation based on whether it was accurate or not.
	 * 
	 * @param choice - int value designating the Tile location on which the accused MrJackCharacter lies
	 * @return - Returns a boolean value designating whether the accusation was correct or not.
	 */
	
	public boolean accuseCharacter(int choice) {
		MrJackCharacter accused = null;
		for(MrJackCharacter mjc : usedMrJackCharacters) {
			if(mjc.getLocation() == choice)
				accused = mjc;
		}
		gameOver = true;
		return detective.hasWonAccusation(accused);
	}

	public void clearCurrentCharacter() {
		currentMrJackCharacter = null;
	}
	
	/**
	 * This method handles the ending of a turn, setting up for the next batch of MrJackCharacters
	 * to be selected, iterating the turn counter, deriving which positions are 'lit' for deciding
	 * who is suspected, and informing the caller of whether or not a victory condition has been met.
	 * 
	 * @return - Returns a boolean value describing whether or not the game has ended on this round.
	 */
	
	public boolean endTurn() {
		clock.iterateTurn();
		activeMrJackCharacters = clock.getTurn() % 2 == 0 ? null : activeMrJackCharacters;	//and other stuff
		player = clock.getTurn() % 2;
		selectedMrJackCharacters.clear();
		currentMrJackCharacter = null;
		removeSuspects();
		
		if(mrJack.hasWonTimer(clock.getTurn() == NUMBER_OF_TURNS) || mrJack.hasWonEscape(board.getTileIdentity(mrJack.whoIsMrJack().getLocation()) == 'e')) {
			gameOver = true;
			return true;
		}
		else
			return false;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * This method compresses the game's state to be sharable and decompressable by
	 * a potential View that will display the Game by decoding the compressed version.
	 * 
	 * @return - Returns a String object containing the entire game in an encoded format.
	 */
	
	public String outputGameState() {
		
		//-- Tiles  -------------------------------------------
		
		String out = board.convertToOutboundFormat();	//Board tiles
		
		//-- MrJackCharacters  --------------------------------
		
		out += activeMrJackCharacters.length + "\n";
		
		for(MrJackCharacter mjc : activeMrJackCharacters) {	//Character locations
			out += mjc.convertToOutboundFormat();
		}
		
		//-- Clock Info  --------------------------------------
		
		out += clock.convertToOutboundFormat();		//Clock info
		
		//-- Current Player  ----------------------------------
		
		out += player + "\n";						//Current Player
		
		//-- Characters used so far  --------------------------
		
		out += usedMrJackCharacters.length + "\n";
		
		int count = 0;
		
		for(int i : selectedMrJackCharacters) {
			out += usedMrJackCharacters[i].getShortName() + "\n";
			count++;
		}
		
		while(count < usedMrJackCharacters.length) {
			count++;
			out += "-\n";
		}
				
		//-- Active Character  --------------------------------

		if(currentMrJackCharacter != null)
			out += currentMrJackCharacter.getName() + "\n" + currentMrJackCharacter.requiredValuesForAbility() + "\n";
		else
			out += "null\n-1\n";
		
		//-- Active Character Abilities  ----------------------
		
		if(currentMrJackCharacter != null) {
			boolean[] abilityPermissions = currentMrJackCharacter.abilityPermissionArray();
			out += abilityPermissions.length + "\n";
			for(int i = 0; i < abilityPermissions.length; i++) {
				out += (abilityPermissions[i] ? "1" : "0") + (i + 1 < abilityPermissions.length ? " " : "\n" );
			}
		}
		else {
			out += "null\n-1\n";
		}
		
		//-- Reachable Tiles  ---------------------------------
		
													//Active character, reach, ability
		boolean[] reach = board.getLegalMovements(currentMrJackCharacter, getCharacterLocations());
		

		
		for(int i = 0; i < reach.length; i++)
			out += (reach[i] ? 1 : 0) + (i + 1 == reach.length ? "\n" : " ");
		
		//-- Lit Tiles  ---------------------------------------
		
		boolean[] litten = board.getLitTiles(getCharacterLocations());
		for(int i = 0; i < litten.length; i++)
			out += (litten[i] == true ? "1" : "0") + (i + 1 == litten.length ? "\n" : " ");
		
	
		//-- Suspected Characters  ----------------------------
		
		for(int i = 0; i < activeMrJackCharacters.length; i++)
			out += (activeMrJackCharacters[i].getSuspect() == true ? "1" : "0") + (i + 1 == activeMrJackCharacters.length ? "\n" : " ");
		
		//-- Barricade  ---------------------------------------
		
		out += "2\n";
		for(int barricadeIndex : board.getBarricadeTiles()) {
			out += barricadeIndex + "\n";
		}

		return out;
		
	}

	/**
	 * This method informs the caller of who the current player of the game is; Mr. Jack or
	 * the Detective, based on the current turn value and how many MrJackCharacters have been
	 * used so far.
	 * 
	 * @return - Returns a String value containing the name of the active player.
	 */
	
	public String getWhoIsPlayer() {
		if(selectedMrJackCharacters.size() == 0 || selectedMrJackCharacters.size() == NUMBER_ACTIVE_CHARACTERS/2 - 1) {
			return PLAYERS[player];
		}
		else {
			return PLAYERS[(player + 1) % 2];
		}
	}

	/**
	 * This method informs the caller of how many integer values are required for
	 * the current MrJackCharacter object to do their ability.
	 * 
	 * @return - Returns an int value describing how many integer values need to be provided to do an ability.
	 */
	
	public int howManyValuesForAction() {
		return currentMrJackCharacter.requiredValuesForAbility();
	}

	/**
	 * This method informs the caller of whether the current turn has ended or not by requesting
	 * how many Characters have been used so far.
	 * 
	 * @return - Returns a boolean value describing whether the current turn has ended or not.
	 */
	
	public boolean roundOver() {
		return (selectedMrJackCharacters.size() == NUMBER_ACTIVE_CHARACTERS/2);
	}

	/**
	 * This method provides the full list of potential MrJackCharacter objects that can
	 * be used in this instance of the game.
	 * 
	 * @return - Returns a MrJackCharacter[] object containing all available MrJackCharacter objects.
	 */
	
	public MrJackCharacter[] getCharacters(){
		return allMrJackCharacters;
	}

	/**
	 * This method provides the caller with the Board object used by this instance of the game.
	 * 
	 * @return - Returns a Board object containing information about the game's board
	 */
	
	public Board getBoard(){
		return board;
	}
	
	/**
	 * 
	 * @return
	 */
	
	public int[] getCharacterLocations() {
		int[] out = new int[activeMrJackCharacters.length];
		int ind = 0;
		for(MrJackCharacter mjc : activeMrJackCharacters) {
			out[ind++] = mjc.getLocation();
		}
		return out;
	}
	
//---  Helper Methods   -----------------------------------------------------------------------

	/**
	 * This method converts a provided file object into the Board that the game will
	 * be using for its operations. 
	 * 
	 * @param structure - File object describing the configuration of a Board object.
	 * @return - Returns the generated Board object, composed of Tile objects.
	 */
	
	private Board deriveBoard(File structure) {
		try {
			Scanner sc = new Scanner(structure);
			ArrayList<String> input = new ArrayList<String>();
			while(sc.hasNextLine()) {
				input.add(sc.nextLine());
			}
			String[] fileBoardInput = input.toArray(new String[input.size()]);	//Parse the file to get this value
			Board newBoard = new Board(fileBoardInput);
			sc.close();
			return newBoard;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("File exception during Board Construction");
			return null;
		}
	}

	/**
	 * This method generates a new Clock object for a new instance of the Mr. Jack game,
	 * being assigned a set of the Lantern Tiles and how many to turn off over the
	 * duration of the game.
	 * 
	 * @return - Returns the generated Clock object to the caller.
	 */
	
	private Clock deriveClock() {
		Clock newClock = new Clock(board.getTilesOfType('l'), LANTERN_LIMIT, LANTERNS_OFF);
		return newClock;
	}

	/**
	 * This method derives a list of unused MrJackCharacters to use in this turn.
	 * 
	 * @param potential - MrJackCharacter[] object representing the list of already used MrJackCharacters for this turn.
	 * @return - Returns a MrJackCharacter[] object containing the the MrJackCharacters to use in this turn.
	 */
	
	private MrJackCharacter[] deriveMrJackCharacters(MrJackCharacter[] potential) {
		int maxSize = NUMBER_ACTIVE_CHARACTERS;
		MrJackCharacter[] toUse = new MrJackCharacter[maxSize];
		Random rand = new Random();
		int index = 0;
		
		while(toUse[maxSize-1] == null) {
			MrJackCharacter possible = potential[rand.nextInt(potential.length)];
			boolean present = false;
			for(MrJackCharacter mjc : toUse) {
				if(mjc != null && mjc.getName().equals(possible.getName())){
					present = true;
				}
			}
			if(!present) {
				toUse[index++] = possible;
			}
		}
		return toUse;
	}

	/**
	 * This method calculates whether each active MrJackCharacter is currently 'lit',
	 * and compares that against the MrJack MrJackCharacter to decide whether or not
	 * each MrJackCharacter remains as a suspect.
	 * 
	 */
	
	private void removeSuspects() {
		boolean[] shadows = board.getLitTiles(getCharacterLocations());
		boolean MrJackShadow = shadows[mrJack.whoIsMrJack().getLocation()];
		for(MrJackCharacter c : usedMrJackCharacters) {
			if(shadows[c.getLocation()] != MrJackShadow)
				c.setSuspect(false);
		}
	}
	
	/**
	 * This method derives a list of unused MrJackCharacters to use in this turn.
	 * 
	 * @param potential - MrJackCharacter[] object representing the list of already used MrJackCharacters for this turn.
	 * @return - Returns a MrJackCharacter[] object containing the the MrJackCharacters to use in this turn.
	 */
	
	private MrJackCharacter[] characterSetPerTurn(MrJackCharacter[] potential) {
		MrJackCharacter[] newMrJackCharacters = new MrJackCharacter[NUMBER_ACTIVE_CHARACTERS];
		int index = 0;
		Random rand = new Random();
		
		while(newMrJackCharacters[NUMBER_ACTIVE_CHARACTERS - 1] == null) {
			int ind = rand.nextInt(allMrJackCharacters.length);
			MrJackCharacter possible = allMrJackCharacters[ind];
			boolean result = true;
			if(potential != null) {
				for(MrJackCharacter mjc : potential)
					if(mjc != null && possible.getName().equals(mjc.getName())) {
						result = false;
						break;
					}
			}
			for(MrJackCharacter mjc : newMrJackCharacters) {
				if(mjc != null && mjc.getName().equals(possible.getName())) {
					result = false;
					break;
				}
			}
			if(result)
				newMrJackCharacters[index++] = possible;
		}
		return newMrJackCharacters;
	}

}
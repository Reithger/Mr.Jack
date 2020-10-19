package model;

import java.io.*;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashSet;

import model.character.GameCharacter;
import model.character.MrJackCharacter;
import model.character.implementation.InspectorAbberline;
import model.character.implementation.InspectorLestrade;
import model.character.implementation.JohnSmith;
import model.character.implementation.JosephLane;
import model.character.implementation.Madame;
import model.character.implementation.MissStealthy;
import model.character.implementation.SirWilliamGull;
import model.game.Board;
import model.game.Clock;
import model.player.Detective;
import model.player.MrJack;

import java.util.Random;

/**
 * Central point for running/processing the Mr. Jack Game
 * 
 * @author Ada Clevinger and Sarah MacEwan
 *
 */

public class GameModel {

//---  Constant Values   ----------------------------------------------------------------------
	
	private static final MrJackCharacter[] CHARACTERS = {new InspectorLestrade(),
														 new JohnSmith(),
														 new MissStealthy(),
														 new SirWilliamGull(),
														 new Madame(),
														 new JosephLane(),
														 new InspectorAbberline()};
	/** int constant value representing the maximum number of MrJackCharacters that can be in a single game instance*/
	private static final int NUMBER_ACTIVE_CHARACTERS = 4;
	/** int constant value representing the number of lanterns that will be turned off*/
	private static final int LANTERN_LIMIT = 4;
	/** */
	private static final int LANTERNS_OFF = 1;
	/** int constant value representing the number of turns in one game instance*/
	private static final int NUMBER_OF_TURNS = 2;
	/** String[] constant object representing the types of Players that can be active at any moment*/
	private static final String[] PLAYERS = {"Detective", "Mr.Jack"};
	
//---  Instance Variables   -------------------------------------------------------------------

	/** MrJackCharacter[] object representing all of the viable MrJackCharacters that can be used in this game*/
	GameCharacter[] allGameCharacters;
	/** MrJackCharacter[] object representing the subset of allMrJackCharacters that are being used in this instance of the game*/
	GameCharacter[] activeGameCharacters;
	/** MrJackCharacter[] object representing the subset of activeMrJackCharacters that have already been used in this round*/
	GameCharacter[] usedGameCharacters;
	/** MrJackCharacter representing the chosen character that the active player is using for their turn*/
	GameCharacter currentGameCharacter;
	/** HashSet<<r>Integer> object representing which MrJackCharacters in a turn have already been used by index*/
	ArrayList<Integer> selectedGameCharacters;
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
	
	String[] boardStructure;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for GameModel objects that is provided a file describing the game's board and
	 * a list of potential Characters to use in an instance of the game.
	 * 
	 * @param structure - File object describing the nature of the game's board
	 * @param potentialGameCharacters - List of MrJackCharacter objects to be used in the game.
	 */
	
	public GameModel(File structure) {
		Scanner sc = null;
		try {
			sc = new Scanner(structure);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> inRead = new ArrayList<String>();
		while(sc.hasNextLine()) {
			inRead.add(sc.nextLine());
		}
		boardStructure = new String[inRead.size()];
		for(int i = 0; i < inRead.size(); i++) {
			boardStructure[i] = inRead.get(i);
		}
		sc.close();
		allGameCharacters = CHARACTERS;
		selectedGameCharacters = new ArrayList<Integer>();
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
		
		board = deriveBoard(boardStructure);
		player = 0;
		clock = deriveClock();
		 
		gameOver = false;
		selectedGameCharacters.clear();
		currentGameCharacter = null;
		usedGameCharacters = new MrJackCharacter[0];
		
		activeGameCharacters = deriveGameCharacters(allGameCharacters);
		
		for(GameCharacter gC : activeGameCharacters) {
			gC.setLocation(null);
		}
		
		Random rand = new Random();
		initializeCharacters(activeGameCharacters, rand);//added helper methods
		mrJack.assignMrJack(activeGameCharacters[rand.nextInt(activeGameCharacters.length)]);
	}

	public boolean placeGameCharacter(int character, int tile) {
		if(character < 0 || character >= activeGameCharacters.length || tile == -1) {
			return false;
		}
		int count = 0;
		for(int i = 0; i < activeGameCharacters.length; i++) {
			if(activeGameCharacters[i].getLocation() != -1) {
				count++;
				continue;
			}
			if(i == character && board.getTileAtLocation(tile).canShare()) {
				activeGameCharacters[i].setLocation(board.getTileAtLocation(tile));
				count++;
			}
		}
		return (count == NUMBER_ACTIVE_CHARACTERS);
	}
		
	/**
	 * This method starts a turn in the current Mr. Jack game, getting the MrJackCharacters
	 * to be used during this turn and resetting the tracker for which have been used so far.
	 * 
	 */
	
	public void startTurn() {
		for(GameCharacter gc: activeGameCharacters) {
			if(gc.getName().equals("InspectorAbberline")) {
				gc.ability(null);
			}
		}
		usedGameCharacters = characterSetPerTurn(usedGameCharacters);
		selectedGameCharacters = new ArrayList<Integer>();
		currentGameCharacter = null;
	}

	/**
	 * This method assigns the current MrJackCharacter that is being controlled by the active
	 * Player, informing the caller whether or not the choice was valid.
	 * 
	 * @param choice - int value representing the index in viable MrJackCharacters that the user wants to control
	 * @return - Returns a boolean value describing whether or not the user choice was legal
	 */
	
	public boolean chooseGameCharacter(int choice) {
		int loc = -1;
		for(GameCharacter mjc : activeGameCharacters) {
			loc++;
			if(selectedGameCharacters.contains(loc))
				continue;
			if(mjc.getLocation() == choice) {
				currentGameCharacter = mjc;
				break;
			}
		}
		if(currentGameCharacter == null)
			return false;
		selectedGameCharacters.add(loc);
		resetCharacters();
		for(GameCharacter gc: activeGameCharacters) {
			if(gc.getName().equals("InspectorAbberline")) {
				gc.ability(null);
			}
		}
		return true;
	}

	/**
	 * This method tentatively moves the currently selected MrJackCharacter to the specified
	 * location by index in the Board's array of Tiles.
	 * 
	 * @param choice - int value designating the new Tile location of the active MrJackCharacter by index in the Board
	 * @return - Returns a boolean value describing whether or not the chosen location was legal
	 */
	
	public boolean moveGameCharacter(int choice) {
		boolean[] reachable = board.getLegalMovements(currentGameCharacter, getCharacterLocations());
		if(reachable[choice]) {
			currentGameCharacter.setLocation(board.getTileAtLocation(choice));	
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
		if(choice.length == currentGameCharacter.requiredValuesForAbility()) {
			return (currentGameCharacter.ability(board.getTiles(choice)));
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
		GameCharacter accused = null;
		for(GameCharacter mjc : activeGameCharacters) {
			if(mjc.getLocation() == choice)
				accused = mjc;
		}
		gameOver = true;
		return detective.hasWonAccusation(accused);
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
		activeGameCharacters = clock.getTurn() % 2 == 0 ? null : activeGameCharacters;	//and other stuff
		player = clock.getTurn() % 2;
		selectedGameCharacters.clear();
		currentGameCharacter = null;
		removeSuspects();
		resetCharacters();
		
		if(mrJack.hasWonTimer(clock.getTurn() == NUMBER_OF_TURNS) || mrJack.hasWonEscape(board.getTileIdentity(mrJack.whoIsMrJack().getLocation()) == 'e', clock.getTurn())) {
			gameOver = true;
			return true;
		}
		else
			return false;
	}

	public void resetCharacters() {
		for(GameCharacter gc: activeGameCharacters) {
			if(gc.getName().equals("InspectorAbberline")) {
				((InspectorAbberline)gc).removeRestrictions();
			}
		}
	}
	
	public GameCharacter[] getActiveCharacters() {
		return activeGameCharacters;
	}
	
//---  Setter Methods   -----------------------------------------------------------------------

	/**
	 * 
	 */
	
	public void clearCurrentCharacter() {
		currentGameCharacter = null;
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
		
		out += activeGameCharacters.length + "\n";
		
		for(GameCharacter mjc : activeGameCharacters) {	//Character locations
			out += mjc.convertToOutboundFormat();
		}
		
		//-- Clock Info  --------------------------------------
		
		out += clock.convertToOutboundFormat();		//Clock info
		
		//-- Current Player  ----------------------------------
		
		out += player + "\n";						//Current Player
		
		//-- Characters used so far  --------------------------
		
		out += activeGameCharacters.length + "\n";
		
		int count = 0;
		
		for(int i : selectedGameCharacters) {
			out += activeGameCharacters[i].getShortName() + "\n";
			count++;
		}
		
		while(count < activeGameCharacters.length) {
			count++;
			out += "-\n";
		}
				
		//-- Active Character  --------------------------------

		if(currentGameCharacter != null)
			out += currentGameCharacter.getName() + "\n" + currentGameCharacter.requiredValuesForAbility() + "\n";
		else
			out += "null\n-1\n";
		
		//-- Active Character Abilities  ----------------------
		
		if(currentGameCharacter != null) {
			boolean[] abilityPermissions = currentGameCharacter.abilityPermissionArray();
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
		boolean[] reach = board.getLegalMovements(currentGameCharacter, getCharacterLocations());
		

		
		for(int i = 0; i < reach.length; i++)
			out += (reach[i] ? 1 : 0) + (i + 1 == reach.length ? "\n" : " ");
		
		//-- Lit Tiles  ---------------------------------------
		
		boolean[] litten = board.getLitTiles(getCharacterLocations());
		for(int i = 0; i < litten.length; i++)
			out += (litten[i] == true ? "1" : "0") + (i + 1 == litten.length ? "\n" : " ");
		
	
		//-- Suspected Characters  ----------------------------
		
		for(int i = 0; i < activeGameCharacters.length; i++)
			out += (activeGameCharacters[i].getSuspect() == true ? "1" : "0") + (i + 1 == activeGameCharacters.length ? "\n" : " ");
		
		//-- Barricade  ---------------------------------------
		
		out += board.getBarricadeTiles().length + "\n";
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
		if(selectedGameCharacters.size() == 0 || selectedGameCharacters.size() == NUMBER_ACTIVE_CHARACTERS/2 - 1) {
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
		return currentGameCharacter.requiredValuesForAbility();
	}

	/**
	 * This method informs the caller of whether the current turn has ended or not by requesting
	 * how many Characters have been used so far.
	 * 
	 * @return - Returns a boolean value describing whether the current turn has ended or not.
	 */
	
	public boolean roundOver() {
		return (selectedGameCharacters.size() == NUMBER_ACTIVE_CHARACTERS/2);
	}

	/**
	 * This method provides the full list of potential MrJackCharacter objects that can
	 * be used in this instance of the game.
	 * 
	 * @return - Returns a MrJackCharacter[] object containing all available MrJackCharacter objects.
	 */
	
	public GameCharacter[] getCharacters(){
		return allGameCharacters;
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
		int[] out = new int[activeGameCharacters.length];
		int ind = 0;
		for(GameCharacter mjc : activeGameCharacters) {
			out[ind++] = mjc.getLocation();
		}
		return out;
	}
	
//---  Helper Methods   -----------------------------------------------------------------------

	/**
	 * This method sets the initial statuses and positions for the active MrJackCharacters
	 * 
	 * @param chars 
	 * @param random
	 */
	
	private void initializeCharacters(GameCharacter[] chars, Random random) {
		HashSet<Integer> used = new HashSet<Integer>();
		for(GameCharacter mjc : activeGameCharacters) {
			initializeCharStatus(mjc);
			mjc.deriveFromModel(this);
			//initializeCharLocation(mjc,random,used);	
		}
	}//initializeCharacters

	/**
	 * 
	 * @param m
	 */
	
	private void initializeCharStatus(GameCharacter m) {
		m.setSuspect(true);
		m.setLit(false);
	}//initializeCharStatus
	
	/**
	 * This method converts a provided file object into the Board that the game will
	 * be using for its operations. 
	 * 
	 * @param structure - File object describing the configuration of a Board object.
	 * @return - Returns the generated Board object, composed of Tile objects.
	 */
	
	private Board deriveBoard(String[] structure) {
		Board newBoard = new Board(structure);
		return newBoard;	
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
	
	private GameCharacter[] deriveGameCharacters(GameCharacter[] potential) {
		int maxSize = NUMBER_ACTIVE_CHARACTERS;
		GameCharacter[] toUse = new MrJackCharacter[maxSize];
		Random rand = new Random();
		int index = 0;
		
		while(toUse[maxSize-1] == null) {
			GameCharacter possible = potential[rand.nextInt(potential.length)];
			boolean present = false;
			for(GameCharacter mjc : toUse) {
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
		for(GameCharacter c : activeGameCharacters) {
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
	
	private GameCharacter[] characterSetPerTurn(GameCharacter[] potential) {
		GameCharacter[] newGameCharacters = new GameCharacter[NUMBER_ACTIVE_CHARACTERS];
		int index = 0;
		Random rand = new Random();
		
		while(newGameCharacters[NUMBER_ACTIVE_CHARACTERS - 1] == null) {
			int ind = rand.nextInt(activeGameCharacters.length);
			GameCharacter possible = allGameCharacters[ind];
			boolean result = true;
			if(potential != null) {
				for(GameCharacter mjc : potential)
					if(mjc != null && possible.getName().equals(mjc.getName())) {
						result = false;
						break;
					}
			}
			for(GameCharacter mjc : newGameCharacters) {
				if(mjc != null && mjc.getName().equals(possible.getName())) {
					result = false;
					break;
				}
			}
			if(result)
				newGameCharacters[index++] = possible;
		}
		return newGameCharacters;
	}

}
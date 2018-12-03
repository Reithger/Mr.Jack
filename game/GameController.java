package game;

import character.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Central point for communication between Model and View.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class GameController {
	
//---  Constant Values   ----------------------------------------------------------------------
	
	private static final MrJackCharacter[] CHARACTERS = {new InspectorLestrade(),
														 new JohnSmith(),
														 new MissStealthy(),
														 new SirWilliamGull(),
														 new Madame(),
														 new JosephLane(),
														 new InspectorAbberline()};
    private static final int SCREEN_WIDTH = 1222;
    private static final int SCREEN_HEIGHT = 854;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** */
	GameModel theGame;
	/** */
	GameView theView;
	
	boolean choseCharacter;
	boolean movedCharacter;
	boolean usedAbility;
	
//---  Constructors   -------------------------------------------------------------------------
	
    public GameController(File f){
    	JFrame frame = readyFrame();
        theGame = new GameModel(f, CHARACTERS);
        theView = new GameView(this);
        frame.add(theView);
    }
    
//---  Operations   ---------------------------------------------------------------------------

    public void startMrJack() {
    	theGame.startGame();
    	updateView();
    }
    
    public void startTurn() {
    	theGame.startTurn();
    	updateView();
    }
    
    public void restartGame() {
    	choseCharacter = false;
    	movedCharacter = false;
    	usedAbility = false;
    }
    
    public void updateView(){
    	theView.update(theGame.outputGameState());
    }
    
    public boolean placeCharacter(int character, int tile) {
    	if(tile < 0)
    		return false;
    	return theGame.placeGameCharacter(character, tile);
    }
    
    public boolean moveCharacter(int newTileLocation) {
    	if(movedCharacter || newTileLocation < 0) {
    		updateView();
    		return movedCharacter;
    	}
    	movedCharacter = theGame.moveGameCharacter(newTileLocation); 
    	updateView();
    	restartCharacterUse();
    	return movedCharacter;
    }
    
    public boolean useCharacterAbility(int[] relevantInformation) {
    	if(usedAbility) {
    		updateView();
    		return usedAbility;
    	}
    	usedAbility = theGame.characterAction(relevantInformation);
    	updateView();
    	restartCharacterUse();
    	return usedAbility;
    }
    
    public boolean chooseCharacter(int tilePosition) {
    	if(choseCharacter) {
    		updateView();
    		return choseCharacter;
    	}
    	choseCharacter = theGame.chooseGameCharacter(tilePosition);
    	System.out.println("Choose: " + tilePosition + " " + choseCharacter);
    	updateView();
    	return choseCharacter;
    }
    
    public void restartCharacterUse() {
    	if(endCharacterUse()) {
    		choseCharacter = false;
	    	movedCharacter = false;
	    	usedAbility = false;
	    	theGame.clearCurrentCharacter();
	    	if(theView.allCharactersUsed()) {
	    		boolean gameOver = theGame.endTurn();
	    		if(gameOver) {
	    			//end game
	    		}
	    		else {
	    			startTurn();
	    		}
	    	}
	    }
    }
    
    public void endTurn() { 
    	theGame.endTurn();
    	choseCharacter = false;
    	movedCharacter = false;
    	usedAbility = false;
    }
    
    private boolean endCharacterUse() {
    	return choseCharacter && movedCharacter && usedAbility;
    }
    
    public boolean canMove() {
    	return !movedCharacter;
    }
    
    public boolean getHasMovedCharacter() {
    	return movedCharacter;
    }
    
    public boolean getUsedAbility() {
    	return usedAbility;
    }
    
    public boolean getSelectedCharacter() {
    	return choseCharacter;
    }
    
    public void cannotMove() {
    	movedCharacter = true;
    }
    
    public void cannotDoAbility() {
    	usedAbility = true;
    }
    
//---  Helper Methods   -----------------------------------------------------------------------

    public static JFrame readyFrame(){
		JFrame frame = new JFrame();
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		return frame;
	}
    
}

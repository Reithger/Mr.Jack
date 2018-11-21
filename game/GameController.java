package game;

import character.*;
import java.io.*;

import javax.swing.JFrame;

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
														 new SirWilliamGull()};
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
    
    public boolean moveCharacter(int newTileLocation) {
    	if(movedCharacter || newTileLocation < 0) {
    		updateView();
    		return movedCharacter;
    	}
    	movedCharacter = theGame.moveMrJackCharacter(newTileLocation); 
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
    	choseCharacter = theGame.chooseMrJackCharacter(tilePosition);
    	updateView();
    	return choseCharacter;
    }
    
    public void restartCharacterUse() {
    	if(endCharacterUse()) {
    		choseCharacter = false;
	    	movedCharacter = false;
	    	usedAbility = false;
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
    
//---  Helper Methods   -----------------------------------------------------------------------

    public static JFrame readyFrame(){
		JFrame frame = new JFrame();
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setVisible(true);
		return frame;
	}
    
}

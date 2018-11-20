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
    
    public void updateView(){
    	theView.update(theGame.outputGameState());
    }
    
    public void moveCharacter(int newTileLocation) {
    	if(movedCharacter)
    		updateView();
    	movedCharacter = theGame.moveMrJackCharacter(newTileLocation); 
    	updateView();
    	restartCharacterUse();
    }
    
    public void useCharacterAbility(int[] relevantInformation) {
    	if(usedAbility)
    		updateView();
    	usedAbility = theGame.characterAction(relevantInformation);
    	updateView();
    	restartCharacterUse();
    }
    
    public void chooseCharacter(int tilePosition) {
    	if(choseCharacter)
    		updateView();
    	choseCharacter = theGame.chooseMrJackCharacter(tilePosition);
    	updateView();
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
    
//---  Helper Methods   -----------------------------------------------------------------------

    public static JFrame readyFrame(){
		JFrame frame = new JFrame();
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setVisible(true);
		return frame;
	}
    
}

package visualmechanics;

import java.util.TimerTask;
import game.GameView;

public class TimerRefresh extends TimerTask{

	private GameView parent;
	
	public TimerRefresh(GameView par){
		parent = par;
	}
	
	public void run(){
		parent.repaint();
		parent.controllerUpdate();
	}
}

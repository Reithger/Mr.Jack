package view.visualmechanics;

import java.util.TimerTask;

import view.GameView;

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

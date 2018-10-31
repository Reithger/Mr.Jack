package player;

/**
 * This class generically models the Players of the Mr. Jack game.
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public interface Player {
	
	public abstract boolean hasWon(boolean isOver, Boolean accusation);
	
}
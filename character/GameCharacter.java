package character;

import game.GameModel;
import tile.Tile;

public interface GameCharacter {
	
	public boolean canMove(Tile start, Tile end, int distance);
	public boolean canInteract(Tile tile);
	public boolean ability(Tile ... choice);
	public void deriveFromModel(GameModel model);
	public String convertToOutboundFormat();
	public boolean getLit();
	public boolean getSuspect();
	public int getLocation();
	public String getName();
	public int getDistance();
	public String getShortName();
	public void setLit(boolean in);
	public void setSuspect(boolean in);
	public void setLocation(int index);
	public abstract int requiredValuesForAbility();
	public abstract boolean hasToDoAbility();
	public abstract boolean canDoAbilityBefore();
	public abstract boolean canDoAbilityDuring();
	public abstract boolean canDoAbilityAfter();
	public abstract boolean canMoveAfterAbility();

}

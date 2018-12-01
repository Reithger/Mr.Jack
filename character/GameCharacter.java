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
	public void setLocation(Tile index);
	public int requiredValuesForAbility();
	public boolean hasToDoAbility();
	public boolean canDoAbilityBefore();
	public boolean canDoAbilityDuring();
	public boolean canDoAbilityAfter();
	public boolean canMoveAfterAbility();

}

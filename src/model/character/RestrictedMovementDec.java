package model.character;

import model.GameModel;
import model.tile.Tile;

public class RestrictedMovementDec extends MrJackCharacterDec{

	private int restrictedDist=1;
	
	public RestrictedMovementDec(GameCharacter mrJackChar) {
		super(mrJackChar);
	}

	@Override
	public boolean canMove(Tile start, Tile end, int distance) {
		return character.canMove(start, end, distance);
	}

	@Override
	public boolean canInteract(Tile tile) {
		return character.canInteract(tile);
	}

	@Override
	public boolean ability(Tile... choice) {
		return character.ability(choice);
	}

	@Override
	public void deriveFromModel(GameModel model) {
		character.deriveFromModel(model);
	}

	@Override
	public String convertToOutboundFormat() {
		return character.convertToOutboundFormat();
	}

	@Override
	public boolean getLit() {
		return character.getLit();
	}

	@Override
	public boolean getSuspect() {
		return character.getSuspect();
	}

	@Override
	public int getLocation() {
		return character.getLocation();
		
	}

	@Override
	public String getName() {
		return character.getName();
	}

	@Override
	public int getDistance() {
		return restrictedDist;
	}

	@Override
	public String getShortName() {
		return character.getShortName();
	}

	@Override
	public void setLit(boolean in) {
		character.setLit(in);
	}

	@Override
	public void setSuspect(boolean in) {
		character.setSuspect(in);
	}

	@Override
	public void setLocation(Tile index) {
		character.setLocation(index);
	}

	@Override
	public int requiredValuesForAbility() {
		return character.requiredValuesForAbility();
	}

	@Override
	public boolean hasToDoAbility() {
		return character.hasToDoAbility();
	}

	@Override
	public boolean canDoAbilityBefore() {
		return character.canDoAbilityBefore();
	}

	@Override
	public boolean canDoAbilityDuring() {
		return character.canDoAbilityDuring();
	}

	@Override
	public boolean canDoAbilityAfter() {
		return character.canDoAbilityAfter();
	}

	@Override
	public boolean canMoveAfterAbility() {
		return character.canMoveAfterAbility();
	}

	@Override
	public Tile getTileLocation() {
		return character.getTileLocation();
	}

	@Override
	public boolean[] abilityPermissionArray() {
		return character.abilityPermissionArray();
	}

}

package character;

public abstract class MrJackCharacterDec implements GameCharacter {

	protected GameCharacter character;
	
	public MrJackCharacterDec(GameCharacter mrJackChar) {
		character=mrJackChar;
	}
		
	public GameCharacter removeDecorator() {
		return character;
	}
}

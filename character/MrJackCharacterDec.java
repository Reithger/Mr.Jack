package character;

public abstract class MrJackCharacterDec extends MrJackCharacter {

	protected GameCharacter character;
	
	public MrJackCharacterDec(GameCharacter mrJackChar) {
		character=mrJackChar;
	}
		
	public GameCharacter removeDecorator() {
		return character;
	}
}

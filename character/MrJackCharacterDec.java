package character;

public abstract class MrJackCharacterDec implements GameCharacter {

	protected MrJackCharacter character;
	
	public MrJackCharacterDec(MrJackCharacter mrJackChar) {
		character=mrJackChar;
	}
		
	public MrJackCharacter removeDecorator() {
		return character;
	}
}

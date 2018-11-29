package character;

public abstract class MrJackCharacterDec {

	private MrJackCharacter character;
	
	public MrJackCharacterDec(MrJackCharacter mrJackChar) {
		character=mrJackChar;
	}
	
	public MrJackCharacter removeDecorator() {
		return character;
	}
}

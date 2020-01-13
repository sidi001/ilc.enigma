package enigma;


// Classe qui représente une machine à énigmes complète.
public class Machine {

	private Rotor leftRotor;
	private Rotor middleRotor;
	private Rotor rightRotor;
	private Reflector reflector;

	public void initRotors(Reflector reflector, Rotor left, Rotor middle, Rotor right) {
		this.reflector = reflector;
		leftRotor = left;
		middleRotor = middle;
		rightRotor = right;
	}

	public void setPositions(String setting) {
		char[] charSettings = setting.toCharArray();
		reflector.setPosition(Rotor.toIndex(charSettings[0]));
		leftRotor.setPosition(Rotor.toIndex(charSettings[1]));
		middleRotor.setPosition(Rotor.toIndex(charSettings[2]));
		rightRotor.setPosition(Rotor.toIndex(charSettings[3]));
	}
	
	public void configure(Reflector reflector, Rotor left, Rotor middle, Rotor right, String setting) {
		this.initRotors(reflector, left, middle, right);
		this.setPositions(setting);

	}

	public String convert(String msg) {
		msg = msg.toUpperCase();
		char[] msgChars = msg.toCharArray();
		String result = "";
		for (char c : msgChars) {
			result += convertChar(c);
		}
		return result;
	}

	char convertChar(char c) {
		advanceRotors();
		int charIndex = Rotor.toIndex(c);
		int output;
		output = rightRotor.convertForward(charIndex);
		output = middleRotor.convertForward(output);
		output = leftRotor.convertForward(output);
		output = reflector.convertForward(output);
		output = leftRotor.convertBackward(output);
		output = middleRotor.convertBackward(output);
		output = rightRotor.convertBackward(output);
		return Rotor.toLetter(output);

	}

	// Aide pour nous permettre d'avancer mes rotors et
	//le double pas est vérificateur ici.
	void advanceRotors() {

		boolean advanceMiddle = false;

		if (middleRotor.atNotch()) {
			leftRotor.advance();
			advanceMiddle= true;
		}

		if (rightRotor.atNotch()) {
			advanceMiddle= true;

		}
		rightRotor.advance();
		if(	advanceMiddle){
			middleRotor.advance();
		}


	}
}

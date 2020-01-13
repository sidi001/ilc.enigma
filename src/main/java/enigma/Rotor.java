package enigma;

public class Rotor {

    private int position;
    private int[] cipher = new int[26];
    private int[] bcipher = new int[26];
    private int notch1 = -1;
    private int notch2 = -1;

    public int getPosition() {
        return position;
    }

    public void setPosition(int posn) {
        position = posn;
    }


	public static Rotor rotorFactory(String str, String notches){
		char[] s = str.trim().replace(" ", "").toCharArray();
		int[] cipher = new int[26];
		for (int i = 0; i< 26; i++){
			cipher[i] = toIndex(s[i]);
		}
		s = notches.trim().replace(" and ", "").toCharArray();
		if (s.length == 2){
			return new Rotor(cipher, toIndex(s[0]), toIndex(s[1]));
		} else {
			return new Rotor(cipher, toIndex(s[0]));
		}
		
	}
	
	Rotor(int[] c, int notch1, int notch2) {
		this.notch1 = notch1;
		this.notch2 = notch2;
		cipher = c;
		createBCipher();
	}
	
	Rotor(int[] c, int notch1) {
		this.notch1 = notch1;
		cipher = c;
		createBCipher();
	}

	//on remarque l'utilisation courant de 26...lorsqu'on change le 26 et rexecuter le teste.
    // On remarque que le code ne fonctionne plus. cela peut être dû au fait que toute la logique du code est basé sur la taille 26
    // * Renvoie la conversion de P (un entier compris entre 0...26 - 1)
    public int convertForward(int p) {
        return ((cipher[((p+position)%26+26)%26]-position)%26+26)%26;
    }

    // * * Renvoie la conversion de E (un entier compris entre 0...26 - 1)
    public int convertBackward(int e) {
        return ((bcipher[((e+position)%26+26)%26]-position)%26+26)%26;
    }

    //recupère la position actuelle du rotor pour l'incrementer de 1
    //on s'assure toujours que la valeur soit dans [0,25]
    //il avance d'une position, si possible. sinon, ne fait rien. * /
    public void advance() {
        position = (position+1) % 26;
    }

    //atNotch() Renvoie vrai si je suis positionné pour laisser le rotor à ma gauche
    protected boolean atNotch() {
        return (position == notch1 || position == notch2);
    }

    protected static char toLetter(int p) {
        return (char)(p + 'A');
    }

    protected static int toIndex(char c) {
        return c - 'A';
    }
    
	private void createBCipher() {
		for(int i =0; i<26; i++)
			bcipher[cipher[i]] = i;
	}



}

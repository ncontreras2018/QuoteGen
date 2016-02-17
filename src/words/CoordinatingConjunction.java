package words;

public class CoordinatingConjunction extends Word {
	private String conj;
	public CoordinatingConjunction(String conj) {
		this.conj = conj;
	}
	public int getPartOfSpeech() {
		return Word.coordinatingConjunctions;
	}
	public String getWord(boolean plural) {
		return conj;
	}
}

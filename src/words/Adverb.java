package words;


public class Adverb extends Word{
	public String word;
	public Adverb(String word) { 
		this.word = word;
	}
	
	public int getPartOfSpeech() {
		return Word.adverb;
	}
	public String getWord(boolean isPlural){
		return word;
	}
}

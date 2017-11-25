import java.util.ArrayList;

public class Lexer {
    private ArrayList<Tokens> characterTokens = new ArrayList<>();
    private ArrayList<Tokens> keywordTokens = new ArrayList<>();

    public Lexer() {
    }

    public ArrayList<Tokens> getCharacterTokens() {
        return characterTokens;
    }

    public void setCharacterTokens(ArrayList<Tokens> characterTokens) {
        this.characterTokens = characterTokens;
    }

    public ArrayList<Tokens> getKeywordTokens() {
        return keywordTokens;
    }

    public void setKeywordTokens(ArrayList<Tokens> keywordTokens) {
        this.keywordTokens = keywordTokens;
    }
}

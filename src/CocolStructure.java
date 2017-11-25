import java.util.ArrayList;

public class CocolStructure {

    private String letter="a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z";
    private String digit="0|1|2|3|4|5|6|7|8|9";
    private String symbols = "°|!|#|\"|\\|%|'|/|@|&|=|¿|?|¡|[|]|-|+|_|<|>|:|;|~|{|}| ";
    private String any = symbols + "|" + letter + "|" + digit;
    private String anyButQuote = any.replace("|\"", "");
    private String anyButApostrophe = any.replace("|'", "");
    private String ident = "("+letter+")("+letter+"|"+digit+")*" ;
    private String number = "("+digit+")("+digit+")*";
    private String string = "\"("+anyButQuote+")*\"";
    private String charr = "'("+anyButApostrophe+")'";
    private String Char = "("+charr+")|(CHR("+number+")";
    private String basicSet = "("+string+")|("+ident+")|(("+Char+")|("+Char+".."+Char+"))";
    private String set=basicSet+"((+|-)"+basicSet+")*";
    //private String TokenDecl = "("+ident+")|("+ ident + "="+ TokenExpr +".)" ;
    //private String Symbol = ident + "|" + string + "|" + charr;
    //private String TokenExpr  = "("+TokenTerm +")(|"+ TokenTerm+")*";
    //private String TokenTerm =  "("+ TokenFactor +")("+TokenFactor+")*";
    //private String TokenFactor = Symbol + "|(" + TokenExpr + ")|[" + TokenExpr + "]|{" + TokenExpr + "}";
    private ArrayList<String> charactersContent = new ArrayList<>();
    private ArrayList<String> keywordsContent = new ArrayList<>();
    private ArrayList<String> tokensContent = new ArrayList<>();
    private ArrayList<Productions> productionsContent = new ArrayList<>();

    public CocolStructure() {
    }

    public String getLetter() {
        return letter;
    }

    public String getDigit() {
        return digit;
    }

    public String getSymbols() {
        return symbols;
    }

    public String getAny() {
        return any;
    }

    public String getAnyButQuote() {
        return anyButQuote;
    }

    public String getAnyButApostrophe() {
        return anyButApostrophe;
    }

    public String getIdent() {
        return ident;
    }

    public String getNumber() {
        return number;
    }

    public String getString() {
        return string;
    }

    public String getCharr() {
        return charr;
    }

    public String getChar() {
        return Char;
    }

    public String getBasicSet() {
        return basicSet;
    }

    public String getSet() {
        return set;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public ArrayList<String> getCharactersContent() {
        return charactersContent;
    }

    public void setCharactersContent(ArrayList<String> charactersContent) {
        this.charactersContent = charactersContent;
    }

    public ArrayList<String> getKeywordsContent() {
        return keywordsContent;
    }

    public void setKeywordsContent(ArrayList<String> keywordsContent) {
        this.keywordsContent = keywordsContent;
    }

    public ArrayList<String> getTokensContent() {
        return tokensContent;
    }

    public void setTokensContent(ArrayList<String> tokensContent) {
        this.tokensContent = tokensContent;
    }

    public ArrayList<Productions> getProductionsContent() {
        return productionsContent;
    }

    public void setProductionsContent(ArrayList<Productions> productionsContent) {
        this.productionsContent = productionsContent;
    }
}

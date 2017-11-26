import java.util.ArrayList;

public class ParserOperations {

    private ArrayList<String> terminals = new ArrayList<>();
    private ArrayList<String> nonterminals = new ArrayList<>();

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    public void setTerminalsAndNonterminals(ArrayList<Productions> productions, ArrayList<String> tokensContent) {
        ArrayList<String> t = new ArrayList<>();
        ArrayList<String> productionHeads = new ArrayList<>();
        ArrayList<String> tokens = new ArrayList<>();

        for (String s:tokensContent) {
            String[] parts = s.split("=");
            String token = parts[0];
            token = token.replace(" ", "");
            tokens.add(token);
        }

        System.out.println("Estos son los tokens: " + tokens);

        for (Productions p:productions) {
            String modified = p.getHead();
            modified = modified.replace(" ", "");
            nonterminals.add(modified);
        }

        for (Productions p:productions) {
            String body = p.getBody();
            //body = body.substring(0, body.length()-1);
            String[] bodyParts = body.split(" ");
            for (String s:bodyParts) {
                if(!nonterminals.contains(s)){
                    if(!s.equals("")){
                        String initCharacter = String.valueOf(s.charAt(0));
                        if(initCharacter.equals("'") || initCharacter.equals("\"")){
                            String c = s.substring(1, s.length()-1);
                            if(!terminals.contains(c)){
                                terminals.add(c);
                            }
                        }else{
                            if(!terminals.contains(s)){
                                terminals.add(s);
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> getNonterminals() {
        return nonterminals;
    }
}


/*
 * Universdidad del Valle de Guatemala
 * Diseño de lenguajes de programacion
 * Compiladores
 *
 * @author Sebastian Galindo, Carnet: 15452
 */

public class Automata {

    private Nodo nodoInicial = new Nodo();
    private Nodo nodoFinal = new Nodo(); 

    public Automata(String transicion){
        //agregando al nodo inicial del automata el nodo al que esta dirigido
        nodoInicial.agregar(transicion, nodoFinal);
    }
    
    public Automata( Nodo I, Nodo F){
        nodoInicial=I;
        nodoFinal=F;
    }

    public Nodo getNodoInicial() {
        return nodoInicial;
    }

    public void setNodoInicial(Nodo nodoInicial) {
        this.nodoInicial = nodoInicial;
    }

    public Nodo getNodoFinal() {
        return nodoFinal;
    }

    public void setNodoFinal(Nodo nodoFinal) {
        this.nodoFinal = nodoFinal;
    }
}

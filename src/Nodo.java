import java.util.ArrayList;

/*
 * Universdidad del Valle de Guatemala
 * Diseño de lenguajes de programacion
 * Compiladores
 *
 * @author Sebastian Galindo, Carnet: 15452
 */

public class Nodo {
    private int numeroEstado;
    private boolean esInicial;
    private boolean esFinal;
    private ArrayList<String> transiciones = new ArrayList<String>();
    private ArrayList<Nodo> elNodo = new ArrayList<Nodo>();

    public void agregar(String transicion, Nodo nodo){
        transiciones.add(transicion);
        elNodo.add(nodo);
    }

    public int getNumeroEstado() {
        return numeroEstado;
    }

    public void setNumeroEstado(int numeroEstado) {
        this.numeroEstado = numeroEstado;
    }

    public boolean isEsInicial() {
        return esInicial;
    }

    public void setEsInicial(boolean esInicial) {
        this.esInicial = esInicial;
    }

    public boolean isEsFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }

    public ArrayList<String> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<String> transiciones) {
        this.transiciones = transiciones;
    }

    public ArrayList<Nodo> getElNodo() {
        return elNodo;
    }

    public void setElNodo(ArrayList<Nodo> elNodo) {
        this.elNodo = elNodo;
    }

}

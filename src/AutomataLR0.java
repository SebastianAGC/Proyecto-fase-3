import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AutomataLR0 {
    //Variables del automata
    private Set<EstadoLR0> estados = new HashSet<>();
    private Set<DtranLR0> transiciones = new HashSet<>();

    private EstadoAFD estadoInicial;
    private EstadoAFD estadoFinal;

    public AutomataLR0() {
    }

    public Set<EstadoLR0> getEstados() {
        return estados;
    }

    public void setEstados(Set<EstadoLR0> estados) {
        this.estados = estados;
    }

    public Set<DtranLR0> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Set<DtranLR0> transiciones) {
        this.transiciones = transiciones;
    }

    public EstadoAFD getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(EstadoAFD estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public EstadoAFD getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(EstadoAFD estadoFinal) {
        this.estadoFinal = estadoFinal;
    }



}

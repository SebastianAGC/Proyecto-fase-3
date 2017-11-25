import java.util.HashSet;
import java.util.Set;

public class EstadoAFD {
    private Set<Nodo> conjuntoEstados = new HashSet<>();
    private int numeroEstadoDFA;
    private boolean marcado;
    private boolean isInicial;
    private boolean isFinal;

    public EstadoAFD(Set<Nodo> conjuntoEstados) {
        this.conjuntoEstados = conjuntoEstados;
    }

    public Set<Nodo> getConjuntoEstados() {
        return conjuntoEstados;
    }

    public void setConjuntoEstados(Set<Nodo> conjuntoEstados) {
        this.conjuntoEstados = conjuntoEstados;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public int getNumeroEstadoDFA() {
        return numeroEstadoDFA;
    }

    public void setNumeroEstadoDFA(int numeroEstadoDFA) {
        this.numeroEstadoDFA = numeroEstadoDFA;
    }

    public boolean isInicial() {
        return isInicial;
    }

    public void setInicial(boolean inicial) {
        isInicial = inicial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }


}

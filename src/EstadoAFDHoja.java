import java.util.HashSet;
import java.util.Set;

public class EstadoAFDHoja {

    private Set<Hoja> elSet = new HashSet<>();
    private int numeroEstadoDFA;
    private boolean marcado;
    private boolean isInicial;
    private boolean isFinal;

    public EstadoAFDHoja(Set<Hoja> elSet) {
        this.elSet = elSet;
    }

    public Set<Hoja> getElSet() {
        return elSet;
    }

    public void setElSet(Set<Hoja> elSet) {
        this.elSet = elSet;
    }

    public int getNumeroEstadoDFA() {
        return numeroEstadoDFA;
    }

    public void setNumeroEstadoDFA(int numeroEstadoDFA) {
        this.numeroEstadoDFA = numeroEstadoDFA;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
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

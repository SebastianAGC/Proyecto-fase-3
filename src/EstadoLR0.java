import java.util.HashSet;
import java.util.Set;

public class EstadoLR0 {
    private Set<Productions> productionsSet = new HashSet<>();
    private int numeroEstadoDFA;
    private boolean marcado;
    private boolean isInicial;
    private boolean isFinal;

    public EstadoLR0(Set<Productions> productionsSet) {
        this.productionsSet = productionsSet;
    }

    public Set<Productions> getProductionsSet() {
        return productionsSet;
    }

    public void setProductionsSet(Set<Productions> productionsSet) {
        this.productionsSet = productionsSet;
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

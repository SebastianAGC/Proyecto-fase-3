import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AFDminimizado {
    private EstadoAFD p;
    private EstadoAFD q;
    boolean marcado;
    private int numeroEstadoMin;
    private ArrayList<AFDminimizado> elArray;
    private Set<EstadoAFD> set = new HashSet<>();

    private ArrayList<DtranAFDMin> transiciones = new ArrayList<>();

    public AFDminimizado() {
    }

    public AFDminimizado(EstadoAFD p, EstadoAFD q, boolean marcado) {
        this.p = p;
        this.q = q;
        this.marcado = marcado;
    }

    public EstadoAFD getP() {
        return p;
    }

    public void setP(EstadoAFD p) {
        this.p = p;
    }

    public EstadoAFD getQ() {
        return q;
    }

    public void setQ(EstadoAFD q) {
        this.q = q;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public ArrayList<AFDminimizado> getElArray() {
        return elArray;
    }

    public void setElArray(ArrayList<AFDminimizado> elArray) {
        this.elArray = elArray;
    }

    public ArrayList<DtranAFDMin> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<DtranAFDMin> transiciones) {
        this.transiciones = transiciones;
    }

    public Set<EstadoAFD> getSet() {
        return set;
    }

    public void setSet(Set<EstadoAFD> set) {
        this.set = set;
    }

    public int getNumeroEstadoMin() {
        return numeroEstadoMin;
    }

    public void setNumeroEstadoMin(int numeroEstadoMin) {
        this.numeroEstadoMin = numeroEstadoMin;
    }
}

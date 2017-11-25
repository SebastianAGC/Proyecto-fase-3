import java.util.HashSet;
import java.util.Set;

public class Hoja {
    private Hoja hijoIzquierdo;
    private Hoja hijoDerecho;
    private Hoja hojaUnica;
    private String simbolo;
    private String caracter;
    private int numeroDeHoja;
    private Set<Hoja> firstpos = new HashSet<>();
    private boolean nullable;
    private Set<Hoja> lastpos = new HashSet<>();
    private Set<Hoja> followpos = new HashSet<>();
    private Set<Hoja> positions = new HashSet<>();

    public Hoja(String caracter, int numeroDeHoja ){
        this.caracter = caracter;
        this.numeroDeHoja = numeroDeHoja;
    }

    public Hoja(Hoja hojaUnica, String simbolo){
        this.hojaUnica = hojaUnica;
        this.simbolo = simbolo;
    }

    public Hoja(Hoja hijoIzquierdo, Hoja hijoDerecho, String simbolo) {
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.simbolo = simbolo;
    }

    public Hoja getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(Hoja hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public Hoja getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(Hoja hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public Hoja getHojaUnica() {
        return hojaUnica;
    }

    public void setHojaUnica(Hoja hojaUnica) {
        this.hojaUnica = hojaUnica;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public int getNumeroDeHoja() {
        return numeroDeHoja;
    }

    public void setNumeroDeHoja(int numeroDeHoja) {
        this.numeroDeHoja = numeroDeHoja;
    }

    public Set<Hoja> getFirstpos() {
        return firstpos;
    }

    public void setFirstpos(Set<Hoja> firstpos) {
        this.firstpos = firstpos;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Set<Hoja> getLastpos() {
        return lastpos;
    }

    public void setLastpos(Set<Hoja> lastpos) {
        this.lastpos = lastpos;
    }

    public Set<Hoja> getFollowpos() {
        return followpos;
    }

    public void setFollowpos(Set<Hoja> followpos) {
        this.followpos = followpos;
    }

    public Set<Hoja> getPositions() {
        return positions;
    }

    public void setPositions(Set<Hoja> positions) {
        this.positions = positions;
    }
}

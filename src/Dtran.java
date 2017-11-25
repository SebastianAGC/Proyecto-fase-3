
public class Dtran {
    private EstadoAFD origen;
    private String transicion;
    private EstadoAFD destino;


    public Dtran(EstadoAFD origen, String transicion, EstadoAFD destino) {
        this.origen = origen;
        this.transicion = transicion;
        this.destino = destino;
    }

    public EstadoAFD getOrigen() {
        return origen;
    }

    public void setOrigen(EstadoAFD origen) {
        this.origen = origen;
    }

    public String getTransicion() {
        return transicion;
    }

    public void setTransicion(String transicion) {
        this.transicion = transicion;
    }

    public EstadoAFD getDestino() {
        return destino;
    }

    public void setDestino(EstadoAFD destino) {
        this.destino = destino;
    }

}

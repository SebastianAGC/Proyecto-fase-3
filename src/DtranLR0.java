public class DtranLR0 {

    private EstadoLR0 origen;
    private String transicion;
    private EstadoLR0 destino;

    public DtranLR0(EstadoLR0 origen, String transicion, EstadoLR0 destino) {
        this.origen = origen;
        this.transicion = transicion;
        this.destino = destino;
    }

    public EstadoLR0 getOrigen() {
        return origen;
    }

    public void setOrigen(EstadoLR0 origen) {
        this.origen = origen;
    }

    public String getTransicion() {
        return transicion;
    }

    public void setTransicion(String transicion) {
        this.transicion = transicion;
    }

    public EstadoLR0 getDestino() {
        return destino;
    }

    public void setDestino(EstadoLR0 destino) {
        this.destino = destino;
    }
}

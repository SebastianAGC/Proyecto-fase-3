public class DtranHoja {

    private EstadoAFDHoja origen;
    private String transicion;
    private EstadoAFDHoja destino;


    public DtranHoja(EstadoAFDHoja origen, String transicion, EstadoAFDHoja destino) {
        this.origen = origen;
        this.transicion = transicion;
        this.destino = destino;
    }

    public EstadoAFDHoja getOrigen() {
        return origen;
    }

    public void setOrigen(EstadoAFDHoja origen) {
        this.origen = origen;
    }

    public String getTransicion() {
        return transicion;
    }

    public void setTransicion(String transicion) {
        this.transicion = transicion;
    }

    public EstadoAFDHoja getDestino() {
        return destino;
    }

    public void setDestino(EstadoAFDHoja destino) {
        this.destino = destino;
    }
}

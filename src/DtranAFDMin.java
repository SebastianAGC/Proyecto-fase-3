public class DtranAFDMin {


    private AFDminimizado origen;
    private String transicion;
    private AFDminimizado destino;


    public DtranAFDMin(AFDminimizado origen, String transicion, AFDminimizado destino) {
        this.origen = origen;
        this.transicion = transicion;
        this.destino = destino;
    }

    public AFDminimizado getOrigen() {
        return origen;
    }

    public void setOrigen(AFDminimizado origen) {
        this.origen = origen;
    }

    public String getTransicion() {
        return transicion;
    }

    public void setTransicion(String transicion) {
        this.transicion = transicion;
    }

    public AFDminimizado getDestino() {
        return destino;
    }

    public void setDestino(AFDminimizado destino) {
        this.destino = destino;
    }
}

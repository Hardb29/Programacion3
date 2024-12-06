package Model;

import java.io.Serializable;

public abstract class Transaccion implements Serializable {
    private double monto;
    private String descripcion;
    private String fecha;

    public Transaccion(double monto, String descripcion, String fecha) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

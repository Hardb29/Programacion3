package Model;

public class Gasto extends Transaccion {
    private String categoria;

    public Gasto(double monto, String descripcion, String fecha, String categoria) {
        super(monto, descripcion, fecha);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finanzas implements Serializable {
    private List<Ingreso> ingresos;
    private List<Gasto> gastos;

    public Finanzas() {
        this.ingresos = new ArrayList<>();
        this.gastos = new ArrayList<>();
    }

    public void agregarIngreso(Ingreso ingreso) {
        ingresos.add(ingreso);
    }

    public void agregarGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public double calcularTotalIngresos() {
        return ingresos.stream().mapToDouble(Ingreso::getMonto).sum();
    }

    public double calcularTotalGastos() {
        return gastos.stream().mapToDouble(Gasto::getMonto).sum();
    }

    public double calcularBalance() {
        return calcularTotalIngresos() - calcularTotalGastos();
    }

    public Map<String, Double> getIngresosPorTipo() {
        Map<String, Double> mapaIngresos = new HashMap<>();
        for (Ingreso ingreso : ingresos) {
            mapaIngresos.merge(ingreso.getDescripcion(), ingreso.getMonto(), Double::sum);
        }
        return mapaIngresos;
    }

    public Map<String, Double> getGastosPorCategoria() {
        Map<String, Double> mapaGastos = new HashMap<>();
        for (Gasto gasto : gastos) {
            mapaGastos.merge(gasto.getCategoria(), gasto.getMonto(), Double::sum);
        }
        return mapaGastos;
    }
    public void editarIngreso(int indice, Ingreso nuevoIngreso) {
        if (indice >= 0 && indice < ingresos.size()) {
            ingresos.set(indice, nuevoIngreso);
        }
    }

    public void eliminarIngreso(int indice) {
        if (indice >= 0 && indice < ingresos.size()) {
            ingresos.remove(indice);
        }
    }

    public void editarGasto(int indice, Gasto nuevoGasto) {
        if (indice >= 0 && indice < gastos.size()) {
            gastos.set(indice, nuevoGasto);
        }
    }

    public void eliminarGasto(int indice) {
        if (indice >= 0 && indice < gastos.size()) {
            gastos.remove(indice);
        }
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }
}
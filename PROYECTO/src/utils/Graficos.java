package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Graficos {

    /**
     * @wbp.parser.entryPoint
     */
    public static void mostrarGraficosCombinados(Map<String, Double> datosIngresos, Map<String, Double> datosGastos, String tituloVentana) {
        // Calcular el saldo total (ingresos - gastos)
        double totalIngresos = datosIngresos.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalGastos = datosGastos.values().stream().mapToDouble(Double::doubleValue).sum();
        double saldo = totalIngresos - totalGastos;

        // Crear panel para las gráficas
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(1, 2));

        // Crear y agregar los gráficos
        panelPrincipal.add(crearPanelGraficoBarras(datosIngresos, "Ingresos por Tipo", "Tipo", "Monto"));
        panelPrincipal.add(crearPanelGraficoTorta(datosGastos, "Gastos por Categoría"));

        // Crear el gráfico de saldo
        panelPrincipal.add(crearPanelGraficoSaldo(saldo, totalIngresos, totalGastos));

        // Crear la ventana
        JFrame ventana = new JFrame(tituloVentana);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.getContentPane().add(panelPrincipal);
        ventana.setSize(800, 400);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static JPanel crearPanelGraficoBarras(Map<String, Double> datos, String titulo, String categoriaX, String categoriaY) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Agregar datos al dataset
        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            dataset.addValue(entry.getValue(), categoriaY, entry.getKey());
        }

        // Crear gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(titulo, categoriaX, categoriaY, dataset, PlotOrientation.VERTICAL, false, true, false);

        // Cambiar colores dinámicamente
        int i = 0;
        int total = dataset.getColumnCount();
        for (Object key : dataset.getColumnKeys()) {
            float hue = (float) i / total;
            chart.getCategoryPlot().getRenderer().setSeriesPaint(i, Color.getHSBColor(hue, 0.7f, 0.9f));
            i++;
        }

        return new ChartPanel(chart);
    }

    public static JPanel crearPanelGraficoTorta(Map<String, Double> datos, String titulo) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Agregar datos al dataset
        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        // Crear gráfico de torta
        JFreeChart chart = ChartFactory.createPieChart(titulo, dataset, true, true, false);

        // Cambiar colores dinámicamente
        PiePlot plot = (PiePlot) chart.getPlot();
        int i = 0;
        int total = dataset.getItemCount();
        for (Object key : dataset.getKeys()) {
            float hue = (float) i / total;
            plot.setSectionPaint((Comparable<?>) key, Color.getHSBColor(hue, 0.7f, 0.9f));
            i++;
        }

        return new ChartPanel(chart);
    }

    public static JPanel crearPanelGraficoSaldo(double saldo, double totalIngresos, double totalGastos) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Agregar el saldo positivo (ingresos) y negativo (gastos)
        dataset.setValue("Saldo a favor (Ingresos)", totalIngresos);
        dataset.setValue("Saldo en contra (Gastos)", totalGastos);

        // Crear gráfico de torta para el saldo
        JFreeChart chart = ChartFactory.createPieChart("Saldo Total", dataset, true, true, false);

        // Cambiar colores dinámicamente
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Saldo a favor (Ingresos)", Color.GREEN);
        plot.setSectionPaint("Saldo en contra (Gastos)", Color.RED);

        return new ChartPanel(chart);
    }
}

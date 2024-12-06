package gui;

import Model.Finanzas;
import utils.ArchivoUtil;
import utils.Graficos;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VentanaPrincipal extends JFrame {
    private Finanzas finanzas;
    private static final String RUTA_DATOS = "finanzas.dat";

    public VentanaPrincipal() {
    	getContentPane().setBackground(new Color(213, 253, 249));
    	setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/image/Fondoo.png")));
        this.finanzas = ArchivoUtil.cargarFinanzas(RUTA_DATOS); // Cargar datos almacenados

        setTitle("Finanzas Personales");
        setSize(487, 299);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(null);
                
                        // Panel Superior
                        JLabel titulo = new JLabel("Gestión de Finanzas Personales", SwingConstants.CENTER);
                        titulo.setBounds(27, 11, 383, 22);
                        getContentPane().add(titulo);
                        titulo.setToolTipText("");
                        titulo.setFont(new Font("Arial Black", Font.ITALIC, 18));
                        JButton btnIngresos = new JButton("Registrar Ingreso");
                        btnIngresos.setForeground(new Color(255, 255, 255));
                        btnIngresos.setBackground(new Color(99, 209, 113));
                        btnIngresos.setBounds(278, 68, 170, 38);
                        getContentPane().add(btnIngresos);
                        JButton btnGastos = new JButton("Registrar Gasto");
                        btnGastos.setForeground(new Color(255, 255, 255));
                        btnGastos.setBackground(new Color(231, 109, 109));
                        btnGastos.setBounds(278, 117, 170, 38);
                        getContentPane().add(btnGastos);
                        JButton btnResumen = new JButton("Ver Resumen Financiero");
                        btnResumen.setForeground(new Color(255, 255, 255));
                        btnResumen.setBackground(new Color(55, 147, 176));
                        btnResumen.setBounds(258, 168, 190, 38);
                        getContentPane().add(btnResumen);
                        JButton btnSalir = new JButton("Salir");
                        btnSalir.setForeground(new Color(255, 255, 255));
                        btnSalir.setBackground(new Color(249, 60, 64));
                        btnSalir.setBounds(348, 224, 100, 22);
                        getContentPane().add(btnSalir);
                        
                        JLabel lblNewLabel = new JLabel("");
                        lblNewLabel.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/image/Fondoo.png")));
                        lblNewLabel.setBounds(10, 0, 458, 296);
                        getContentPane().add(lblNewLabel);
                        
                                btnSalir.addActionListener(e -> salirPrograma());
                        btnResumen.addActionListener(e -> {
                            Map<String, Double> ingresos = finanzas.getIngresosPorTipo();
                            Map<String, Double> gastos = finanzas.getGastosPorCategoria();

                            if (ingresos.isEmpty() && gastos.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "No hay datos para mostrar.");
                                return;
                            }

                            Graficos.mostrarGraficosCombinados(ingresos, gastos, "Resumen Financiero");
                        });
                        btnGastos.addActionListener(e -> registrarGasto());

        // Eventos de Botones
        btnIngresos.addActionListener(e -> registrarIngreso());

        setVisible(true);
    }

    private void registrarIngreso() {
        VentanaIngreso ventanaIngreso = new VentanaIngreso(finanzas, this);
        ventanaIngreso.setVisible(true);
    }

    private void registrarGasto() {
        VentanaGasto ventanaGasto = new VentanaGasto(finanzas, this);
        ventanaGasto.setVisible(true);
    }

    private void mostrarResumen() {
        Map<String, Double> ingresos = finanzas.getIngresosPorTipo();
        Map<String, Double> gastos = finanzas.getGastosPorCategoria();

        if (ingresos.isEmpty() && gastos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se han registrado ingresos ni gastos.");
            return;
        }

        JFrame ventanaResumen = new JFrame("Resumen Financiero");
        ventanaResumen.setSize(800, 600);
        ventanaResumen.getContentPane().setLayout(new GridLayout(1, 2)); // Panel dividido para dos gráficos

        // Gráfico de ingresos
        if (!ingresos.isEmpty()) {
            JPanel panelIngresos = Graficos.crearPanelGraficoBarras(ingresos, "Ingresos por Tipo", "Tipo", "Monto");
            ventanaResumen.getContentPane().add(panelIngresos);
        } else {
            ventanaResumen.getContentPane().add(new JLabel("No hay datos de ingresos disponibles.", SwingConstants.CENTER));
        }

        // Gráfico de gastos
        if (!gastos.isEmpty()) {
            JPanel panelGastos = Graficos.crearPanelGraficoTorta(gastos, "Gastos por Categoría");
            ventanaResumen.getContentPane().add(panelGastos);
        } else {
            ventanaResumen.getContentPane().add(new JLabel("No hay datos de gastos disponibles.", SwingConstants.CENTER));
        }

        // Configurar comportamiento al cerrar
        ventanaResumen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaResumen.setVisible(true);
    }


    private void salirPrograma() {
        ArchivoUtil.guardarFinanzas(finanzas, RUTA_DATOS);
        System.exit(0);
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
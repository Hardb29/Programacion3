package gui;

import Model.Finanzas;
import Model.Ingreso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaIngreso extends JFrame {
    private JList<String> listaIngresos;
    private DefaultListModel<String> modeloLista;
    private Finanzas finanzas;
    private int posX, posY;

    public VentanaIngreso(Finanzas finanzas, JFrame ventanaPrincipal) {
        this.finanzas = finanzas;

        setTitle("Gestionar Ingresos");
        setSize(500, 350);
        setUndecorated(true);

        // Panel superior personalizado
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBounds(0, 0, 500, 30);
        panelSuperior.setBackground(Color.DARK_GRAY);
        panelSuperior.setPreferredSize(new Dimension(500, 30));

        JLabel lblTitulo = new JLabel("GESTIÓN DE INGRESOS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        // Hacer la ventana movible
        panelSuperior.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
            }
        });

        // Panel para registrar ingreso
        JPanel panelCentral = new JPanel();
        panelCentral.setBounds(20, 42, 212, 269);
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(0, 38, 100, 28);
        JTextField txtDescripcion = new JTextField();
        txtDescripcion.setBounds(83, 38, 119, 28);
        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(0, 94, 100, 28);
        JTextField txtMonto = new JTextField();
        txtMonto.setBounds(83, 94, 119, 28);
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(0, 153, 100, 28);
        JTextField txtFecha = new JTextField();
        txtFecha.setBounds(83, 153, 119, 28);
        JButton btnGuardar = new JButton("Agregar");
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnGuardar.setBackground(new Color(103, 222, 132));
        btnGuardar.setBounds(39, 204, 116, 28);
        btnGuardar.setForeground(new Color(0, 0, 0));
        panelCentral.setLayout(null);

        panelCentral.add(lblDescripcion);
        panelCentral.add(txtDescripcion);
        panelCentral.add(lblMonto);
        panelCentral.add(txtMonto);
        panelCentral.add(lblFecha);
        panelCentral.add(txtFecha);
        JLabel label = new JLabel();
        label.setBounds(0, 342, 116, 104);
        panelCentral.add(label); // Espaciador
        panelCentral.add(btnGuardar);

        // Lista de ingresos
        modeloLista = new DefaultListModel<>();
        listaIngresos = new JList<>(modeloLista);
        actualizarListaIngresos();
        JScrollPane scrollPane = new JScrollPane(listaIngresos);
        scrollPane.setBounds(245, 70, 238, 170);

        // Panel inferior para acciones
        JPanel panelInferior = new JPanel(); // Ahora tiene tres botones
        panelInferior.setBounds(245, 246, 242, 65);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnEliminar.setBackground(new Color(233, 68, 73));
        btnEliminar.setBounds(0, 0, 106, 23);
        btnEliminar.setForeground(new Color(0, 0, 0));
        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnEditar.setBounds(130, 0, 106, 23);
        btnEditar.setForeground(new Color(0, 0, 0));
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnRegresar.setBackground(new Color(82, 196, 220));
        btnRegresar.setBounds(68, 31, 121, 23);
        btnRegresar.setForeground(new Color(0, 0, 0));
        panelInferior.setLayout(null);

        panelInferior.add(btnEliminar);
        panelInferior.add(btnEditar);
        panelInferior.add(btnRegresar);
        getContentPane().setLayout(null);

        // Añadir paneles a la ventana
        getContentPane().add(panelSuperior);
        getContentPane().add(panelCentral);
        getContentPane().add(scrollPane);
        getContentPane().add(panelInferior);

        // Acción para agregar ingreso
        btnGuardar.addActionListener(e -> {
            String descripcion = txtDescripcion.getText().trim();
            String montoStr = txtMonto.getText().trim();
            String fecha = txtFecha.getText().trim();

            if (descripcion.isEmpty() || montoStr.isEmpty() || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                double monto = Double.parseDouble(montoStr);
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
                    return;
                }

                // Agregar el ingreso a Finanzas
                finanzas.agregarIngreso(new Ingreso(monto, descripcion, fecha));

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(this, "Ingreso registrado correctamente.");

                // Limpiar campos
                txtDescripcion.setText("");
                txtMonto.setText("");
                txtFecha.setText("");

                // Actualizar la lista de ingresos
                actualizarListaIngresos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese un monto válido.");
            }
        });

        // Acción para eliminar ingreso
        btnEliminar.addActionListener(e -> {
            int seleccion = listaIngresos.getSelectedIndex();
            if (seleccion != -1) {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este ingreso?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    finanzas.getIngresos().remove(seleccion);
                    JOptionPane.showMessageDialog(this, "Ingreso eliminado correctamente.");
                    actualizarListaIngresos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un ingreso para eliminar.");
            }
        });

        // Acción para editar ingreso
        btnEditar.addActionListener(e -> {
            int seleccion = listaIngresos.getSelectedIndex();
            if (seleccion != -1) {
                Ingreso ingresoSeleccionado = finanzas.getIngresos().get(seleccion);
                txtDescripcion.setText(ingresoSeleccionado.getDescripcion());
                txtMonto.setText(String.valueOf(ingresoSeleccionado.getMonto()));
                txtFecha.setText(ingresoSeleccionado.getFecha());

                // Modificar el ingreso con los nuevos valores
                btnGuardar.setText("Actualizar");
                btnGuardar.addActionListener(e1 -> {
                    String nuevaDescripcion = txtDescripcion.getText().trim();
                    String montoStr2 = txtMonto.getText().trim();
                    String nuevaFecha = txtFecha.getText().trim();

                    if (nuevaDescripcion.isEmpty() || montoStr2.isEmpty() || nuevaFecha.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                        return;
                    }

                    try {
                        double nuevoMonto = Double.parseDouble(montoStr2);
                        if (nuevoMonto <= 0) {
                            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
                            return;
                        }

                        // Actualizar el ingreso
                        ingresoSeleccionado.setDescripcion(nuevaDescripcion);
                        ingresoSeleccionado.setMonto(nuevoMonto);
                        ingresoSeleccionado.setFecha(nuevaFecha);

                        // Confirmación de actualización
                        JOptionPane.showMessageDialog(this, "Ingreso actualizado correctamente.");

                        // Limpiar campos
                        txtDescripcion.setText("");
                        txtMonto.setText("");
                        txtFecha.setText("");

                        // Actualizar la lista de ingresos
                        actualizarListaIngresos();

                        // Cambiar el texto del botón de vuelta a "Agregar"
                        btnGuardar.setText("Agregar");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error: Ingrese un monto válido.");
                    }
                });

            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un ingreso para editar.");
            }
        });

        // Acción para regresar
        btnRegresar.addActionListener(e -> {
            setVisible(false);
            ventanaPrincipal.setVisible(true);
        });

        setVisible(true);
    }

    private void actualizarListaIngresos() {
        modeloLista.clear();
        List<Ingreso> ingresos = finanzas.getIngresos();
        for (Ingreso ingreso : ingresos) {
            modeloLista.addElement(String.format("%s - %.2f - %s", ingreso.getDescripcion(), ingreso.getMonto(), ingreso.getFecha()));
        }
    }
}

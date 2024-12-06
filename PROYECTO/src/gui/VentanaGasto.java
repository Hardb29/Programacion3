package gui;

import Model.Finanzas;
import Model.Gasto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaGasto extends JFrame {
    private JList<String> listaGastos;
    private DefaultListModel<String> modeloLista;
    private Finanzas finanzas;
    private int posX, posY;

    public VentanaGasto(Finanzas finanzas, JFrame ventanaPrincipal) {
        this.finanzas = finanzas;

        setTitle("Gestionar Gastos");
        setSize(500, 350);
        setUndecorated(true);

        // Panel superior personalizado
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBounds(0, 0, 500, 30);
        panelSuperior.setBackground(Color.DARK_GRAY);
        panelSuperior.setPreferredSize(new Dimension(500, 30));

        JLabel lblTitulo = new JLabel("GESTIÓN DE GASTOS");
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

        // Panel para registrar gasto
        JPanel panelCentral = new JPanel();
        panelCentral.setBounds(10, 41, 220, 282);
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(10, 24, 72, 28);
        JTextField txtDescripcion = new JTextField();
        txtDescripcion.setBounds(92, 24, 118, 28);
        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(10, 81, 72, 28);
        JTextField txtMonto = new JTextField();
        txtMonto.setBounds(92, 81, 118, 28);
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 137, 72, 28);
        JTextField txtFecha = new JTextField();
        txtFecha.setBounds(92, 137, 118, 28);
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(10, 194, 72, 28);
        JTextField txtCategoria = new JTextField();
        txtCategoria.setBounds(92, 194, 118, 28);
        JButton btnGuardar = new JButton("Agregar");
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnGuardar.setBackground(new Color(103, 222, 130));
        btnGuardar.setBounds(53, 243, 116, 28);
        panelCentral.setLayout(null);

        panelCentral.add(lblDescripcion);
        panelCentral.add(txtDescripcion);
        panelCentral.add(lblMonto);
        panelCentral.add(txtMonto);
        panelCentral.add(lblFecha);
        panelCentral.add(txtFecha);
        panelCentral.add(lblCategoria);
        panelCentral.add(txtCategoria);
        JLabel label = new JLabel();
        label.setBounds(0, 365, 116, 81);
        panelCentral.add(label); // Espaciador
        panelCentral.add(btnGuardar);

        // Lista de gastos
        modeloLista = new DefaultListModel<>();
        listaGastos = new JList<>(modeloLista);
        actualizarListaGastos();
        JScrollPane scrollPane = new JScrollPane(listaGastos);
        scrollPane.setBounds(245, 70, 238, 170);

        // Panel inferior para acciones
        JPanel panelInferior = new JPanel();
        panelInferior.setBounds(240, 251, 244, 57);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnEliminar.setBackground(new Color(233, 69, 73));
        btnEliminar.setBounds(10, 0, 103, 23);
        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnEditar.setBackground(new Color(136, 166, 157));
        btnEditar.setBounds(131, 0, 103, 23);
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnRegresar.setBackground(new Color(82, 196, 222));
        btnRegresar.setBounds(66, 34, 117, 23);
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

        // Acción para agregar gasto
        btnGuardar.addActionListener(event -> {
            String descripcion = txtDescripcion.getText().trim();
            String montoStr = txtMonto.getText().trim();
            String fecha = txtFecha.getText().trim();
            String categoria = txtCategoria.getText().trim();

            if (descripcion.isEmpty() || montoStr.isEmpty() || fecha.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                double monto = Double.parseDouble(montoStr);
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
                    return;
                }

                if (btnGuardar.getText().equals("Agregar")) {
                    // Si estamos agregando un nuevo gasto
                    finanzas.agregarGasto(new Gasto(monto, descripcion, fecha, categoria));
                    JOptionPane.showMessageDialog(this, "Gasto registrado correctamente.");
                } else {
                    // Si estamos actualizando un gasto existente
                    Gasto gastoSeleccionado = finanzas.getGastos().get(listaGastos.getSelectedIndex());
                    gastoSeleccionado.setDescripcion(descripcion);
                    gastoSeleccionado.setMonto(monto);
                    gastoSeleccionado.setFecha(fecha);
                    gastoSeleccionado.setCategoria(categoria);
                    JOptionPane.showMessageDialog(this, "Gasto actualizado correctamente.");
                }

                // Limpiar los campos después de agregar o actualizar
                txtDescripcion.setText("");
                txtMonto.setText("");
                txtFecha.setText("");
                txtCategoria.setText("");
                
                // Actualizar la lista de gastos
                actualizarListaGastos();

                // Cambiar el botón de nuevo a "Agregar"
                btnGuardar.setText("Agregar");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese un monto válido.");
            }
        });


        // Acción para eliminar gasto
        btnEliminar.addActionListener(e -> {
            int seleccion = listaGastos.getSelectedIndex();
            if (seleccion != -1) {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este gasto?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    finanzas.getGastos().remove(seleccion);
                    JOptionPane.showMessageDialog(this, "Gasto eliminado correctamente.");
                    actualizarListaGastos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un gasto para eliminar.");
            }
        });

        // Acción para editar gasto
        btnEditar.addActionListener(e -> {
            int seleccion = listaGastos.getSelectedIndex();
            if (seleccion != -1) {
                Gasto gastoSeleccionado = finanzas.getGastos().get(seleccion);
                txtDescripcion.setText(gastoSeleccionado.getDescripcion());
                txtMonto.setText(String.valueOf(gastoSeleccionado.getMonto()));
                txtFecha.setText(gastoSeleccionado.getFecha());
                txtCategoria.setText(gastoSeleccionado.getCategoria());

                btnGuardar.setText("Actualizar");
                btnGuardar.addActionListener(event -> {
                    String descripcion = txtDescripcion.getText().trim();
                    String montoStr = txtMonto.getText().trim();
                    String fecha = txtFecha.getText().trim();
                    String categoria = txtCategoria.getText().trim();

                    if (descripcion.isEmpty() || montoStr.isEmpty() || fecha.isEmpty() || categoria.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                        return;
                    }

                    try {
                        double monto = Double.parseDouble(montoStr);
                        if (monto <= 0) {
                            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
                            return;
                        }

                        gastoSeleccionado.setDescripcion(descripcion);
                        gastoSeleccionado.setMonto(monto);
                        gastoSeleccionado.setFecha(fecha);
                        gastoSeleccionado.setCategoria(categoria);

                        JOptionPane.showMessageDialog(this, "Gasto actualizado correctamente.");
                        txtDescripcion.setText("");
                        txtMonto.setText("");
                        txtFecha.setText("");
                        txtCategoria.setText("");
                        actualizarListaGastos();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error: Ingrese un monto válido.");
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un gasto para editar.");
            }
        });

        // Acción para regresar
        btnRegresar.addActionListener(e -> {
            setVisible(false);
            ventanaPrincipal.setVisible(true);
        });

        setVisible(true);
    }

    private void actualizarListaGastos() {
        modeloLista.clear();
        List<Gasto> gastos = finanzas.getGastos();
        for (Gasto gasto : gastos) {
            modeloLista.addElement(String.format("%s - %.2f - %s - %s", gasto.getDescripcion(), gasto.getMonto(), gasto.getFecha(), gasto.getCategoria()));
        }
    }
}

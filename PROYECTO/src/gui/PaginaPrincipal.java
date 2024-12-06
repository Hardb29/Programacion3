package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaPrincipal extends JFrame {

    public PaginaPrincipal() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(PaginaPrincipal.class.getResource("/image/ico.png")));
        // Configuración de la ventana
        setTitle("Inicio - Finanzas Personales");
        setSize(386, 283);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Desactiva la "X"
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón de Iniciar
        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setBackground(new Color(89, 206, 128));
        btnIniciar.setBounds(139, 197, 95, 34);
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaPrincipal().setVisible(true); // Abre la ventana principal
                dispose(); // Cierra la página principal
            }
        });

        // Botón de Autor
        JButton btnAutor = new JButton("Autores");
        btnAutor.setBackground(new Color(194, 197, 148));
        btnAutor.setBounds(26, 203, 88, 23);
        btnAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        PaginaPrincipal.this,
                        "Autores:\n[Edwin Celis]\n[Sebastian Borda]\n[Harrixon Barcenas]\nProyecto: Finanzas Personales\nETITC [S3B]\nVersión: 1.0",
                        "Acerca del Autor",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // Botón de Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(236, 108, 112));
        btnSalir.setBounds(258, 203, 87, 23);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                        PaginaPrincipal.this,
                        "¿Estás seguro de que deseas salir?",
                        "Salir",
                        JOptionPane.YES_NO_OPTION
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cierra la aplicación
                }
            }
        });
        panel.setLayout(null);

        // Añadir botones al panel
        panel.add(btnIniciar);
        panel.add(btnAutor);
        panel.add(btnSalir);

        // Añadir panel al frame
        getContentPane().add(panel);
        
        JLabel lblNewLabel = new JLabel("¡ Bienvenido !");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblNewLabel.setBounds(139, 11, 106, 23);
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Esta es una app financiera de uso personal.");
        lblNewLabel_1.setToolTipText("");
        lblNewLabel_1.setBounds(66, 41, 256, 14);
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(PaginaPrincipal.class.getResource("/image/Portadaa.png")));
        lblNewLabel_2.setBounds(123, 66, 126, 120);
        panel.add(lblNewLabel_2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PaginaPrincipal().setVisible(true);
        });
    }
}
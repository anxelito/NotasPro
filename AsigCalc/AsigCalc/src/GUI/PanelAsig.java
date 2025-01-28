package GUI;

import Main.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelAsig extends JFrame {
    private JPanel panel;
    private JScrollPane scrollPanel;
    private JTextArea etiquetaStatus;



    public PanelAsig(JFrame ventanaAnterior) {
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Asignaturas");
        setIconImage(new ImageIcon(getClass().getResource("/resources/black.png")).getImage());

        panel = new BackgroundPanel("/resources/black.png");
        panel.setLayout(null);
        this.setContentPane(panel);

        actualizarEtiqueta(Asignatura.asignaturas.toString());
        
        colocarEtiquetasAsig();
        colocarBotonesAsig(ventanaAnterior);
        mostrarTabla();
       
    }

    private void colocarEtiquetasAsig() {
        JLabel etiqueta = new JLabel("Asignaturas", SwingConstants.CENTER);
        etiqueta.setBounds(25, 30, 400, 65);
        etiqueta.setForeground(Color.white);
        etiqueta.setFont(new Font("arial", Font.BOLD, 40));
        panel.add(etiqueta);
    }

    private void colocarBotonesAsig(JFrame ventanaAnterior) {
        crearBoton("Añadir Asignatura", 20, 130, 140, 20, Color.blue, e -> añadirAsig());
        crearBoton("<-", 20, 360, 40, 40, Color.red, e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        });
        crearBoton("Salir y Guardar", 70, 360, 200, 40, Color.blue, e -> System.exit(0));
    }

    private JButton crearBoton(String texto, int x, int y, int with, int heigh, Color color, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, with, heigh); // 200, 40
        boton.setBackground(color);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.addActionListener(accion);
        panel.add(boton);
        return boton;
    }

    private void añadirAsig() {       
        JFrame nuevaVentana = new JFrame("Nueva Asignatura");
        nuevaVentana.setSize(400, 200);
        nuevaVentana.setLocationRelativeTo(null); 
        nuevaVentana.setResizable(false);
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setLayout(null); 

        // Nombre de la asignatura
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        nuevaVentana.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(120, 20, 200, 25);
        nuevaVentana.add(txtNombre);

        // Curso
        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setBounds(20, 60, 80, 25);
        nuevaVentana.add(lblCurso);

        JComboBox<Curso> cbCurso = new JComboBox<>(Curso.values());
        cbCurso.setBounds(120, 60, 200, 25);
        nuevaVentana.add(cbCurso);

        // ECTS
        JLabel lblECTS = new JLabel("ECTS:");
        lblECTS.setBounds(20, 100, 80, 25);
        nuevaVentana.add(lblECTS);

        JTextField txtECTS = new JTextField();
        txtECTS.setBounds(120, 100, 200, 25);
        nuevaVentana.add(txtECTS);

        // Añadir
        JButton btnAñadir = new JButton("Añadir");
        btnAñadir.setBounds(80, 140, 100, 30);
        btnAñadir.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                Curso curso = (Curso) cbCurso.getSelectedItem();
                int ects = Integer.parseInt(txtECTS.getText());

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(nuevaVentana, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear asignatura
                Asignatura nuevaAsignatura = new Asignatura(nombre, curso, ects);
                actualizarEtiqueta(Asignatura.asignaturas.toString()); // Hacer un for para que recorra todo y un get name...
                JOptionPane.showMessageDialog(nuevaVentana, "Asignatura añadida con éxito:\n" + "Nombre: " + nombre + "\nCurso: " + curso + "\nECTS: " + ects, "Asignatura Añadida", JOptionPane.INFORMATION_MESSAGE);
                nuevaVentana.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(nuevaVentana, "El valor de ECTS debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        nuevaVentana.add(btnAñadir);
        

        // Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 140, 100, 30);
        btnSalir.addActionListener(e -> nuevaVentana.dispose());
        nuevaVentana.add(btnSalir);

        nuevaVentana.setVisible(true);
    }
    
    private void mostrarTabla() {
        PanelTablaAsignaturas panelTabla = new PanelTablaAsignaturas();
        panelTabla.setBounds(20, 160, 400, 190); // Posición y tamaño dentro del panel principal
        panelTabla.setBackground(new Color(0, 0, 0, 150)); // Fondo translúcido opcional para contraste
        panelTabla.setOpaque(true);

        panel.add(panelTabla);

        panel.revalidate();
        panel.repaint();
    }
    
    public void actualizarEtiqueta(String contenido){
        limpiarPanel();
        etiquetaStatus = new JTextArea(contenido);
        etiquetaStatus.setEditable(false);
        etiquetaStatus.setLineWrap(true);
        etiquetaStatus.setWrapStyleWord(true);
        etiquetaStatus.setForeground(Color.white);
        etiquetaStatus.setFont(new Font("Arial", Font.BOLD, 17));
        etiquetaStatus.setOpaque(false);
        
        scrollPanel = new JScrollPane(etiquetaStatus);
        scrollPanel.setBounds(20, 150, 130, 86);
        scrollPanel.setOpaque(false);
        scrollPanel.getViewport().setOpaque(false);
        scrollPanel.setBorder(null);
        panel.add(scrollPanel);

        panel.revalidate();
        panel.repaint(); 
    } 

    private void limpiarPanel() {
        
    }
    
}

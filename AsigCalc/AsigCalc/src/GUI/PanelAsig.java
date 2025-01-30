package GUI;

import Main.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelAsig extends JFrame {
    private JPanel panel;
    public static PanelTablaAsignaturas panelTabla = new PanelTablaAsignaturas();
    public static PanelTablaPruebas panelExamenes = new PanelTablaPruebas();
    private Asignatura asignatura;

    public PanelAsig(JFrame ventanaAnterior) {
        setSize(650, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Asignaturas");
        setIconImage(new ImageIcon(getClass().getResource("/resources/black.png")).getImage());

        panel = new BackgroundPanel("/resources/black.png");
        panel.setLayout(null);
        this.setContentPane(panel);

        panelTabla.actualizarTabla();
        
        colocarEtiquetasAsig();
        colocarBotonesAsig(ventanaAnterior);
        mostrarTabla();
       
    }

    public PanelAsig(JFrame ventanaAnterior, List<Asignatura> asignaturasCargadas) {
        asignatura.asignaturas = asignaturasCargadas; 
        setSize(650, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Asignaturas");
        setIconImage(new ImageIcon(getClass().getResource("/resources/black.png")).getImage());

        panel = new BackgroundPanel("/resources/black.png");
        panel.setLayout(null);
        this.setContentPane(panel);

        panelTabla.actualizarTabla();
        actualizarNotaCreditos(asignatura.asignaturas.get(0));
        colocarEtiquetasAsig();
        colocarBotonesAsig(ventanaAnterior);
        mostrarTabla();
       
    }
    
    private void colocarEtiquetasAsig() {
        //Titulo
        JLabel etiqueta = new JLabel("Asignaturas", SwingConstants.CENTER);
        etiqueta.setBounds(90, 30, 400, 65);
        etiqueta.setForeground(Color.white);
        etiqueta.setFont(new Font("arial", Font.BOLD, 40));
        panel.add(etiqueta);
    }

    private void colocarBotonesAsig(JFrame ventanaAnterior) {
        crearBoton("Añadir Asig.", 15, 130, 180, 37, Color.white, e -> añadirAsig(), "/resources/AñadirAsig.png");
        crearBoton("Editar/Eliminar",220, 130, 180, 37, Color.pink, e -> {
            JFrame nuevaVentana = new JFrame("Nueva Asignatura");
            nuevaVentana.setSize(400, 100);
            nuevaVentana.setLocationRelativeTo(null); 
            nuevaVentana.setResizable(false);
            nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nuevaVentana.setLayout(null); 

        // Asignatura
            JButton btnAñadir = new JButton("Editar Asignatura");
            btnAñadir.setBounds(40, 20, 150, 30);
            btnAñadir.addActionListener(r -> editarAsig());
            nuevaVentana.add(btnAñadir);
       
        // Prueba
            JButton btnPrueba = new JButton("Editar Prueba");
            btnPrueba.setBounds(200, 20, 150, 30);
            btnPrueba.addActionListener(a -> editarPrueba());
            nuevaVentana.add(btnPrueba);

            nuevaVentana.setVisible(true);
        }, "/resources/Editar.png");
        
        crearBoton("Añadir Prueba", 420, 130, 180, 37, Color.cyan, e -> añadirPrueba(), "/resources/AñadirPrueba.png");
        crearBoton("<-", 20, 550, 40, 40, Color.red, e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        }, "/resources/atras.png");
        crearBoton("Salir y Guardar", 80, 545, 50, 50, Color.blue, e -> guardarUsuario(), "/resources/salir.png");
    }

    private JButton crearBoton(String texto, int x, int y, int width, int height, Color color, ActionListener accion, String ruta) {
        JButton boton = new JButton();
        boton.setBounds(x, y, width, height);
        boton.setBackground(color);
        boton.setOpaque(false);
        boton.setBorderPainted(false);
        boton.addActionListener(accion);

        if (ruta != null) { 
            boton.setIcon(escalarImagen(ruta,width,height));
        }
        panel.add(boton);
        return boton;
    }

    private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        try {
            BufferedImage imagenOriginal = ImageIO.read(getClass().getResource(ruta));

            Image imagenTemporal = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            BufferedImage imagenEscalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagenEscalada.createGraphics();
            g2d.drawImage(imagenTemporal, 0, 0, null);
            g2d.dispose();

            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }    
    
    private void añadirAsig() {       
        JFrame nuevaVentana = new JFrame("Nueva Asignatura");
        nuevaVentana.setSize(400, 250);
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
                panelTabla.actualizarTabla();
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
    
    private void editarAsig() {
        JFrame nuevaVentana = new JFrame("Editar/Eliminar Asignatura");
        nuevaVentana.setSize(400, 260);
        nuevaVentana.setLocationRelativeTo(null); 
        nuevaVentana.setResizable(false);
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setLayout(null); 

        // Asignatura
        JLabel lblAsig = new JLabel("Asignatura:");
        lblAsig.setBounds(20, 20, 80, 25);
        nuevaVentana.add(lblAsig);

        JComboBox<Asignatura> cbAsig = new JComboBox<>(Asignatura.asignaturas.toArray(new Asignatura[0]));
        cbAsig.setBounds(120, 20, 200, 25);
        nuevaVentana.add(cbAsig);
        
        // Nuevo nombre de la asignatura
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 80, 25);
        nuevaVentana.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 200, 25);
        nuevaVentana.add(txtNombre);

        // Curso
        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setBounds(20, 100, 80, 25);
        nuevaVentana.add(lblCurso);

        JComboBox<Curso> cbCurso = new JComboBox<>(Curso.values());
        cbCurso.setBounds(120, 100, 200, 25);
        nuevaVentana.add(cbCurso);

        // ECTS
        JLabel lblECTS = new JLabel("ECTS:");
        lblECTS.setBounds(20, 140, 80, 25);
        nuevaVentana.add(lblECTS);

        JTextField txtECTS = new JTextField();
        txtECTS.setBounds(120, 140, 200, 25);
        nuevaVentana.add(txtECTS);

        // Añadir
        JButton btnAñadir = new JButton("Actualizar");
        btnAñadir.setBounds(35, 180, 100, 30);
        btnAñadir.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                Curso curso = (Curso) cbCurso.getSelectedItem();
                int ects = Integer.parseInt(txtECTS.getText());
                Asignatura asig = (Asignatura) cbAsig.getSelectedItem();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(nuevaVentana, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Editamos asignatura
                asig.setNombre(nombre);
                asig.setCurso(curso);
                asig.setECTS(ects);
                panelTabla.actualizarTabla();
                JOptionPane.showMessageDialog(nuevaVentana, "Asignatura actualizada con éxito:\n" + "Nombre: " + nombre + "\nCurso: " + curso + "\nECTS: " + ects, "Asignatura Añadida", JOptionPane.INFORMATION_MESSAGE);
                nuevaVentana.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(nuevaVentana, "El valor de ECTS debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        nuevaVentana.add(btnAñadir);
        
        // Eliminar
        JButton btnElim = new JButton("Eliminar");
        btnElim.setBounds(140, 180, 100, 30);
        btnElim.addActionListener(e -> {
            Asignatura asig = (Asignatura) cbAsig.getSelectedItem();
    
            if (asig != null) {
                int confirm = JOptionPane.showConfirmDialog(nuevaVentana, 
                    "¿Estás seguro de que quieres eliminar la asignatura " + asig.getNombre() + "?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    Asignatura.asignaturas.remove(asig);
                    cbAsig.removeItem(asig); 
                    panelTabla.actualizarTabla();
                    JOptionPane.showMessageDialog(nuevaVentana, 
                        "Asignatura eliminada con éxito.", 
                        "Eliminación Exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(nuevaVentana, 
                    "No hay ninguna asignatura seleccionada.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        nuevaVentana.add(btnElim);
       
        // Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(245, 180, 100, 30);
        btnSalir.addActionListener(e -> nuevaVentana.dispose());
        nuevaVentana.add(btnSalir);

        nuevaVentana.setVisible(true);
    }
    
    private void editarPrueba() { 
        
        JFrame nuevaVentana = new JFrame("Editar/Eliminar Prueba");
        nuevaVentana.setSize(400, 340);
        nuevaVentana.setLocationRelativeTo(null); 
        nuevaVentana.setResizable(false);
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setLayout(null); 
        
        // Asignatura
        JLabel lblAsig = new JLabel("Asignatura:");
        lblAsig.setBounds(20, 20, 80, 25);
        nuevaVentana.add(lblAsig);

        JComboBox<Asignatura> cbAsig = new JComboBox<>(Asignatura.asignaturas.toArray(new Asignatura[0]));
        cbAsig.setBounds(120, 20, 200, 25);
        nuevaVentana.add(cbAsig);
        Asignatura asig = (Asignatura) cbAsig.getSelectedItem();
        
        // Prueba
        JLabel lblPrueba = new JLabel("Asignatura:");
        lblPrueba.setBounds(20, 60, 80, 25);
        nuevaVentana.add(lblPrueba);

        JComboBox<Prueba> cbPrueba = new JComboBox<>(asig.getPruebas().toArray(new Prueba[0]));
        cbPrueba.setBounds(120, 60, 200, 25);
        nuevaVentana.add(cbPrueba);

        // Nombre de la prueva
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 100, 80, 25);
        nuevaVentana.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(120, 100, 200, 25);
        nuevaVentana.add(txtNombre);
        
        // Nota Minima
        JLabel lblNotaMin = new JLabel("Nota Minima:");
        lblNotaMin.setBounds(20, 140, 80, 25);
        nuevaVentana.add(lblNotaMin);

        JTextField txtNotaMin = new JTextField();
        txtNotaMin.setBounds(120, 140, 200, 25);
        nuevaVentana.add(txtNotaMin);

        // Nota
        JLabel lblNota = new JLabel("Nota:");
        lblNota.setBounds(20, 180, 80, 25);
        nuevaVentana.add(lblNota);

        JTextField txtNota = new JTextField();
        txtNota.setBounds(120, 180, 200, 25);
        nuevaVentana.add(txtNota);

        // Ponderacion
        JLabel lblPon = new JLabel("Ponderación %:");
        lblPon.setBounds(20, 220, 100, 25);
        nuevaVentana.add(lblPon);

        JTextField txtPon = new JTextField();
        txtPon.setBounds(120, 220, 200, 25);
        nuevaVentana.add(txtPon);

        // Añadir
        JButton btnAñadir = new JButton("Actualizar");
        btnAñadir.setBounds(35, 260, 100, 30);
        btnAñadir.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                double nota = Double.parseDouble(txtNota.getText());
                double notaMin = Double.parseDouble(txtNotaMin.getText());
                int pon = Integer.parseInt(txtPon.getText());
                Prueba prueba = (Prueba) cbPrueba.getSelectedItem();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(nuevaVentana, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear prueba
                prueba.setNombre(nombre);
                prueba.setPonderacion(pon);
                prueba.setNotaMin(notaMin);
                prueba.setNota(nota);
                asig.notaFinal();
                asig.notaMedia();
                asig.calcularEstado();
                actualizarNotaCreditos(asig);
                panelTabla.actualizarTabla();
                JOptionPane.showMessageDialog(nuevaVentana, "Prueba actualizada con éxito:\n" + "Nombre: " + nombre + "\nNota: " + nota + "\nNota Minima: " + notaMin, "Asignatura Añadida", JOptionPane.INFORMATION_MESSAGE);
                nuevaVentana.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(nuevaVentana, "Numeros no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        nuevaVentana.add(btnAñadir);
        
        // Eliminar
        JButton btnElim = new JButton("Eliminar");
        btnElim.setBounds(140, 260, 100, 30);
        btnElim.addActionListener(e -> {
            Prueba prueba = (Prueba) cbPrueba.getSelectedItem();
    
            if (prueba != null) {
                int confirm = JOptionPane.showConfirmDialog(nuevaVentana, 
                    "¿Estás seguro de que quieres eliminar la prueba " + prueba.getNombre() + "?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    asig.pruebas.remove(prueba);
                    cbAsig.removeItem(asig); 
                    panelTabla.actualizarTabla();
                    JOptionPane.showMessageDialog(nuevaVentana, 
                        "Prueba eliminada con éxito.", 
                        "Eliminación Exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(nuevaVentana, 
                    "No hay ninguna prueba seleccionada.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        nuevaVentana.add(btnElim);
        
        
        // Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(245, 260, 100, 30);
        btnSalir.addActionListener(e -> nuevaVentana.dispose());
        nuevaVentana.add(btnSalir);

        nuevaVentana.setVisible(true);
    }
    
    private void añadirPrueba() {       
        JFrame nuevaVentana = new JFrame("Nueva Prueba");
        nuevaVentana.setSize(400, 300);
        nuevaVentana.setLocationRelativeTo(null); 
        nuevaVentana.setResizable(false);
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setLayout(null); 
        
        // Asignatura
        JLabel lblAsig = new JLabel("Asignatura:");
        lblAsig.setBounds(20, 20, 80, 25);
        nuevaVentana.add(lblAsig);

        JComboBox<Asignatura> cbAsig = new JComboBox<>(Asignatura.asignaturas.toArray(new Asignatura[0]));
        cbAsig.setBounds(120, 20, 200, 25);
        nuevaVentana.add(cbAsig);

        // Nombre de la prueva
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 80, 25);
        nuevaVentana.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 200, 25);
        nuevaVentana.add(txtNombre);
        
        // Nota Minima
        JLabel lblNotaMin = new JLabel("Nota Minima:");
        lblNotaMin.setBounds(20, 100, 80, 25);
        nuevaVentana.add(lblNotaMin);

        JTextField txtNotaMin = new JTextField();
        txtNotaMin.setBounds(120, 100, 200, 25);
        nuevaVentana.add(txtNotaMin);

        // Nota
        JLabel lblNota = new JLabel("Nota:");
        lblNota.setBounds(20, 140, 80, 25);
        nuevaVentana.add(lblNota);

        JTextField txtNota = new JTextField();
        txtNota.setBounds(120, 140, 200, 25);
        nuevaVentana.add(txtNota);

        // Ponderacion
        JLabel lblPon = new JLabel("Ponderación %:");
        lblPon.setBounds(20, 180, 100, 25);
        nuevaVentana.add(lblPon);

        JTextField txtPon = new JTextField();
        txtPon.setBounds(120, 180, 200, 25);
        nuevaVentana.add(txtPon);

        // Añadir
        JButton btnAñadir = new JButton("Añadir");
        btnAñadir.setBounds(80, 220, 100, 30);
        btnAñadir.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                double nota = Double.parseDouble(txtNota.getText());
                double notaMin = Double.parseDouble(txtNotaMin.getText());
                int pon = Integer.parseInt(txtPon.getText());
                Asignatura asig = (Asignatura) cbAsig.getSelectedItem();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(nuevaVentana, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear prueba
                asig.addExamen(new Prueba(nombre, pon, notaMin, nota));
                asig.notaFinal();
                asig.notaMedia();
                asig.calcularEstado();
                actualizarNotaCreditos(asig);
                panelTabla.actualizarTabla();
                panelExamenes.actualizarTabla(asig);
                JOptionPane.showMessageDialog(nuevaVentana, "Prueba añadida con éxito:\n" + "Nombre: " + nombre + "\nNota: " + nota + "\nNota Minima: " + notaMin, "Asignatura Añadida", JOptionPane.INFORMATION_MESSAGE);
                nuevaVentana.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(nuevaVentana, "Numeros no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        nuevaVentana.add(btnAñadir);
        

        // Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 220, 100, 30);
        btnSalir.addActionListener(e -> nuevaVentana.dispose());
        nuevaVentana.add(btnSalir);

        nuevaVentana.setVisible(true);
    }

    private void mostrarTabla() {
        panelTabla.setBounds(60, 200, 500, 190); // Posición y tamaño dentro del panel principal
        panelTabla.setBackground(new Color(0, 0, 0, 150)); // Fondo translúcido opcional para contraste
        panelTabla.setOpaque(true);

        panel.add(panelTabla);

        panel.revalidate();
        panel.repaint();
        mostrarTablaExamenes();
    }
    
    private void mostrarTablaExamenes(){
        panelExamenes.setBounds(60, 420, 500, 100); // Posición y tamaño dentro del panel principal
        panelExamenes.setBackground(new Color(0, 0, 0, 150)); // Fondo translúcido opcional para contraste
        panelExamenes.setOpaque(true);

        panel.add(panelExamenes);

        panel.revalidate();
        panel.repaint();
    }
    
    private JLabel etiquetaNotaMedia;
    private JLabel etiquetaCreditos;

    public void actualizarNotaCreditos(Asignatura asig) {
        asig.notaMedia();
        asig.creditos();
        
        if (etiquetaNotaMedia == null) {
            etiquetaNotaMedia = new JLabel("Nota Media: "+ asig.getNotaMedia() , SwingConstants.CENTER);
            etiquetaNotaMedia.setBounds(270, 550, 140, 20);
            etiquetaNotaMedia.setForeground(Color.white);
            etiquetaNotaMedia.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(etiquetaNotaMedia);
        } else {
            etiquetaNotaMedia.setText("Nota Media: " + asig.getNotaMedia());
        }
        
        if (etiquetaCreditos == null) {
            etiquetaCreditos = new JLabel("Creditos: "+ asig.getCreditos() , SwingConstants.CENTER);
            etiquetaCreditos.setBounds(270, 570, 140, 20);
            etiquetaCreditos.setForeground(Color.white);
            etiquetaCreditos.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(etiquetaCreditos);
        } else {
            etiquetaCreditos.setText("Creditos: " + asig.getCreditos());
        }

        panel.revalidate();
        panel.repaint();
    }
    
    private void guardarUsuario() {
        asignatura.asignaturas = asignatura.getAsignaturas();
        GuardarDatos.guardarAsignaturas(asignatura.asignaturas);
    }
    
}

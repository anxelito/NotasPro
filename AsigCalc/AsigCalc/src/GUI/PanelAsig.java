package GUI;

import Main.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelAsig extends JFrame {
    private JPanel panel;
    private static String usuario;
    public static PanelTablaAsignaturas panelTabla = new PanelTablaAsignaturas();
    public static PanelTablaPruebas panelExamenes = new PanelTablaPruebas();
    private Asignatura asignatura;

    public PanelAsig(JFrame ventanaAnterior) {
        nombreUsuario();
        setSize(650, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Asignaturas");
        setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());

        panel = new BackgroundPanel("/resources/fonodo6.png");
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
        setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());

        panel = new BackgroundPanel("/resources/fonodo6.png");
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
        JLabel etiqueta;
        if (usuario != null) {
            etiqueta = new JLabel("Bienvenido " + getUsuario(), SwingConstants.CENTER);
        } else {
            etiqueta = new JLabel("Bienvenido " + asignatura.asignaturas.get(0).getUsuario(), SwingConstants.CENTER);
        }

        etiqueta.setBounds(100, 30, 400, 65);
        etiqueta.setForeground(Color.white);
        etiqueta.setFont(new Font("Times New Roman", Font.BOLD, 40));
        panel.add(etiqueta);
        //Asignatura
        JLabel etiqueta1 = new JLabel("Asignaturas:", SwingConstants.CENTER);
        etiqueta1.setBounds(53, 180, 100, 20);
        etiqueta1.setForeground(Color.white);
        etiqueta1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        panel.add(etiqueta1);
        //Pruebas
        JLabel etiqueta2 = new JLabel("Pruebas:", SwingConstants.CENTER);
        etiqueta2.setBounds(40, 400, 100, 20);
        etiqueta2.setForeground(Color.white);
        etiqueta2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        panel.add(etiqueta2);
        
        JLabel etiqueta3 = new JLabel("anxelito.com ©", SwingConstants.CENTER);
        etiqueta3.setBounds(270, 590, 100, 20);
        etiqueta3.setForeground(Color.gray);
        etiqueta3.setFont(new Font("Times New Roman", Font.BOLD, 15));
        panel.add(etiqueta3);
    }

    private void colocarBotonesAsig(JFrame ventanaAnterior) {
        crearBoton("Añadir Asig.", 18, 130, 180, 37, Color.white, e -> añadirAsig(), "/resources/AñadirAsig.png");
        crearBoton("Editar/Eliminar",223, 130, 180, 37, Color.pink, e -> {
            JFrame nuevaVentana = new JFrame("Nueva Asignatura");
            nuevaVentana.setSize(400, 100);
            nuevaVentana.setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());
            nuevaVentana.setLocationRelativeTo(null); 
            nuevaVentana.setResizable(false);
            nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nuevaVentana.setLayout(null); 

        // Asignatura
            JButton btnAñadir = new JButton("Editar Asignatura");
            btnAñadir.setBounds(40, 20, 150, 30);
            btnAñadir.addActionListener(r -> {nuevaVentana.dispose();editarAsig();});
            nuevaVentana.add(btnAñadir);
       
        // Prueba
            JButton btnPrueba = new JButton("Editar Prueba");
            btnPrueba.setBounds(200, 20, 150, 30);
            btnPrueba.addActionListener(a -> {nuevaVentana.dispose();editarPrueba();});
            nuevaVentana.add(btnPrueba);

            nuevaVentana.setVisible(true);
        }, "/resources/Editar.png");
        
        crearBoton("Añadir Prueba", 423, 130, 180, 37, Color.cyan, e -> añadirPrueba(), "/resources/AñadirPrueba.png");
        crearBoton("<-", 20, 550, 30, 30, Color.red, e -> {
            ventanaAnterior.setVisible(true);
            this.dispose();
        }, "/resources/atras.png");
        crearBoton("Salir y Guardar", 80, 550, 30, 30, Color.blue, e -> guardarUsuario(), "/resources/salir.png");
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
        // Verificar si el recurso se encuentra
        URL url = getClass().getResource(ruta);
        if (url == null) {
            System.err.println("No se encontró el recurso: " + ruta);
            return null;
        }

        // Leer la imagen desde el recurso
        BufferedImage imagenOriginal = ImageIO.read(url);

        // Escalar la imagen
        Image imagenTemporal = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        // Crear la imagen escalada
        BufferedImage imagenEscalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagenEscalada.createGraphics();
        g2d.drawImage(imagenTemporal, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(imagenEscalada);
    } catch (IOException e) {
        // Imprimir error si la carga de la imagen falla
        System.err.println("Error al cargar la imagen: " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}

    
    public void nombreUsuario() {
        if (this.usuario == null) {
            String nombre = JOptionPane.showInputDialog(this, 
                "Introduce tu nombre de usuario:", 
                "Nuevo Usuario", 
                JOptionPane.PLAIN_MESSAGE); 

            if (nombre != null && !nombre.isEmpty()) {
                this.usuario = nombre;
            } else {
                this.usuario = null;
            }
        }
    }

    private void añadirAsig() {       
        JFrame nuevaVentana = new JFrame("Nueva Asignatura");
        nuevaVentana.setSize(400, 250);
        nuevaVentana.setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());
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
                Asignatura nuevaAsignatura = new Asignatura(nombre, curso, ects, usuario);
                nuevaAsignatura.calcularEstado();
                System.out.println(nuevaAsignatura.getEstado());
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
        nuevaVentana.setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());
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
        nuevaVentana.setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());
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
        JLabel lblPrueba = new JLabel("Prueba:");
        lblPrueba.setBounds(20, 60, 80, 25);
        nuevaVentana.add(lblPrueba);

        JComboBox<Prueba> cbPrueba = new JComboBox<>();
        cbPrueba.setBounds(120, 60, 200, 25);
        nuevaVentana.add(cbPrueba);

        // Método para actualizar el JComboBox de pruebas
        cbAsig.addActionListener(e -> {
            Asignatura asigSeleccionada = (Asignatura) cbAsig.getSelectedItem();
            cbPrueba.removeAllItems(); // Elimina las pruebas anteriores

            if (asigSeleccionada != null) {
                for (Prueba p : asigSeleccionada.getPruebas()) {
                    cbPrueba.addItem(p);
                }
            }
        });

        // Llenar el JComboBox de pruebas con la asignatura seleccionada inicialmente
        if (cbAsig.getSelectedItem() != null) {
            Asignatura asigInicial = (Asignatura) cbAsig.getSelectedItem();
            for (Prueba p : asigInicial.getPruebas()) {
                cbPrueba.addItem(p);
            }
        }


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
                String nombre = txtNombre.getText().trim();
                String strNota = txtNota.getText().trim();
                String strNotaMin = txtNotaMin.getText().trim();
                String strPonderacion = txtPon.getText().trim();

                if (nombre.isEmpty() || strNota.isEmpty() || strNotaMin.isEmpty() || strPonderacion.isEmpty()) {
                    JOptionPane.showMessageDialog(nuevaVentana, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double nota, notaMin;
                int pon;

                try {
                    nota = Double.parseDouble(strNota);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(nuevaVentana, "La nota debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    notaMin = Double.parseDouble(strNotaMin);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(nuevaVentana, "La nota mínima debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    pon = Integer.parseInt(strPonderacion);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(nuevaVentana, "La ponderación debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Crear prueba
                Prueba prueba = (Prueba) cbPrueba.getSelectedItem();
                prueba.setNombre(nombre);
                prueba.setPonderacion(pon);
                prueba.setNotaMin(notaMin);
                prueba.setNota(nota);
                prueba.setEstado();
                asig.notaFinal();
                asig.notaMedia();
                asig.calcularEstado();
                panelTabla.actualizarTabla();
                panelExamenes.actualizarTabla(asig);
                actualizarNotaCreditos(asig);
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
        nuevaVentana.setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());
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
                String nombre = txtNombre.getText().trim();
                String strNota = txtNota.getText().trim();
                String strNotaMin = txtNotaMin.getText().trim();
                String strPonderacion = txtPon.getText().trim();

                if (nombre.isEmpty() || strNota.isEmpty() || strNotaMin.isEmpty() || strPonderacion.isEmpty()) {
                    JOptionPane.showMessageDialog(nuevaVentana, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double nota, notaMin;
                int ponderacion;

                try {
                    nota = Double.parseDouble(strNota);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(nuevaVentana, "La nota debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    notaMin = Double.parseDouble(strNotaMin);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(nuevaVentana, "La nota mínima debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ponderacion = Integer.parseInt(strPonderacion);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(nuevaVentana, "La ponderación debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Asignatura asig = (Asignatura) cbAsig.getSelectedItem();

                asig.addExamen(new Prueba(nombre, ponderacion, notaMin, nota));
                asig.notaFinal();
                asig.notaMedia();
                asig.calcularEstado();
                actualizarNotaCreditos(asig);
                panelTabla.actualizarTabla();
                panelExamenes.actualizarTabla(asig);

                JOptionPane.showMessageDialog(nuevaVentana, "Prueba añadida con éxito:\n" + "Nombre: " + nombre + "\nNota: " + nota + "\nNota Mínima: " + notaMin, "Prueba Añadida", JOptionPane.INFORMATION_MESSAGE);
                nuevaVentana.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(nuevaVentana, "Ocurrió un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
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
            etiquetaNotaMedia = new JLabel("<html><u>Nota Media: "+ asig.getNotaMedia() + "</u></html>" , SwingConstants.CENTER);
            etiquetaNotaMedia.setBounds(150, 545, 200, 40);
            etiquetaNotaMedia.setForeground(Color.white);
            etiquetaNotaMedia.setFont(new Font("Times New Roman", Font.BOLD, 20));
            panel.add(etiquetaNotaMedia);
        } else {
            etiquetaNotaMedia.setText("<html><u>Nota Media: " + asig.getNotaMedia()+ "</u></html>");
        }
        
        if (etiquetaCreditos == null) {
            etiquetaCreditos = new JLabel("<html><u>Créditos: " + asig.getCreditos() + "</u></html>", SwingConstants.CENTER);
            etiquetaCreditos.setBounds(340, 545, 200, 40);
            etiquetaCreditos.setForeground(Color.white);
            etiquetaCreditos.setFont(new Font("Times New Roman", Font.BOLD, 20));
            panel.add(etiquetaCreditos);

        } else {
            etiquetaCreditos.setText("<html><u>Créditos: " + asig.getCreditos() + "</u></html>");
        }

        panel.revalidate();
        panel.repaint();
    }
    
    private void guardarUsuario() {
        asignatura.asignaturas = asignatura.getAsignaturas();
        GuardarDatos.guardarAsignaturas(asignatura.asignaturas);
    }
    
    public void setUsuario (String usu) {
        this.usuario = usu;
    }
    
    public String getUsuario (){
        return usuario;
    }
}

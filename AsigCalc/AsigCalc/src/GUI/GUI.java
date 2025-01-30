package GUI;

import Main.Asignatura;
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

public class GUI extends JFrame {
    private JPanel panel;
    private List<Asignatura> asignaturas;

    public GUI() {
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("NotasPro");
        setIconImage(new ImageIcon(getClass().getResource("/resources/logo.png")).getImage());

        colocarPanelMain();
    }

    private void colocarPanelMain() {
        panel = new BackgroundPanel("/resources/fondo.png");
        panel.setLayout(null);
        this.setContentPane(panel);

        colocarEtiquetasMain();
        colocarBotonesMain();
    }

    private void colocarEtiquetasMain() {
        JLabel etiqueta = new JLabel("NotasPro", SwingConstants.CENTER);
        etiqueta.setBounds(20, 40, 400, 65);
        etiqueta.setForeground(Color.white);
        etiqueta.setFont(new Font("Times New Roman", Font.BOLD, 60));
        panel.add(etiqueta);
    }

    private void colocarBotonesMain() {
        crearBoton("Nuevo Usuario", 125, 150, 200, 40, Color.cyan, e -> abrirPanelAsig(), "/resources/Nuevo.png");
        crearBoton("Cargar Usuario", 125, 200, 200, 40, Color.green, e -> cargarUsuario(), "/resources/Cargar.png");
        crearBoton("Salir", 125, 250, 200, 40, Color.red, e -> System.exit(0), "/resources/6.png");
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

    private void abrirPanelAsig() {
        // Crear el panel
        PanelAsig panelAsig = new PanelAsig(this);

        // Solo llamamos a nombreUsuario() antes de mostrar el panel
        panelAsig.nombreUsuario();

        // Verificar que el nombre de usuario no sea null
        if (panelAsig.getUsuario() != null && !panelAsig.getUsuario().isEmpty()) {
            // Si el nombre de usuario es válido, mostrar el panel
            this.setVisible(false);  // Ocultar el panel actual
            panelAsig.setVisible(true);  // Mostrar el panel de asignaturas
        } else {
            // Si no se ingresó un nombre válido, mostrar un mensaje de error
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre de usuario válido.");
        }
    }


    private void cargarUsuario() {
        asignaturas = CargarDatos.cargarAsignaturas();
        this.setVisible(false);
        PanelAsig panelAsig = new PanelAsig(this,asignaturas);
        panelAsig.setVisible(true);
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }
    
}

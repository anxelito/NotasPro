package GUI;

import static GUI.PanelAsig.panelExamenes;
import static GUI.PanelAsig.panelTabla;
import Main.Asignatura;
import Main.Prueba;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class GUI extends JFrame {
    private JPanel panel;
    private List<Asignatura> asignaturas;

    public GUI() {
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Uni");
        setIconImage(new ImageIcon(getClass().getResource("/resources/black.png")).getImage());

        colocarPanelMain();
    }

    private void colocarPanelMain() {
        panel = new BackgroundPanel("/resources/black.png");
        panel.setLayout(null);
        this.setContentPane(panel);

        colocarEtiquetasMain();
        colocarBotonesMain();
    }

    private void colocarEtiquetasMain() {
        JLabel etiqueta = new JLabel("Bienvenido", SwingConstants.CENTER);
        etiqueta.setBounds(25, 30, 400, 65);
        etiqueta.setForeground(Color.white);
        etiqueta.setFont(new Font("arial", Font.BOLD, 40));
        panel.add(etiqueta);
    }

    private void colocarBotonesMain() {
        crearBoton("Nuevo Usuario", 125, 150, Color.cyan, e -> abrirPanelAsig());
        crearBoton("Cargar Usuario", 125, 200, Color.green, e -> cargarUsuario());
        crearBoton("Salir", 125, 250, Color.red, e -> System.exit(0));
    }

    private JButton crearBoton(String texto, int x, int y, Color color, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 200, 40);
        boton.setBackground(color);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.addActionListener(accion);
        panel.add(boton);
        return boton;
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

package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JFrame {
    private JPanel panel;

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
        this.setVisible(false);
        PanelAsig panelAsig = new PanelAsig(this);
        panelAsig.setVisible(true);
    }

    private void cargarUsuario() {

    }
}

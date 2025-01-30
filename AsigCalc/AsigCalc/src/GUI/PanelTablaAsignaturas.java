package GUI;

import Main.Asignatura;
import static Main.Asignatura.asignaturas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTablaAsignaturas extends JPanel {
    String[] columnas = {"Nombre", "Curso", "Estado", "Creditos", "Nota media"};
    DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

    public PanelTablaAsignaturas() {
        setLayout(new BorderLayout());
        JTable tabla = new JTable(modeloTabla);

        actualizarTabla();

        // Estilo de la tabla (minimalista en blanco y negro)
        tabla.setRowHeight(20);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Color de fondo y color de texto de las celdas
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);

        // Estilo de las cabeceras
        tabla.getTableHeader().setBackground(Color.BLACK);
        tabla.getTableHeader().setForeground(Color.WHITE);

        // Bordes de las celdas y líneas horizontales
        tabla.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tabla.setShowGrid(true);  // Mostrar las líneas de la cuadrícula
        tabla.setGridColor(Color.BLACK);  // Color de las líneas de la cuadrícula

        // Eliminar las líneas entre celdas pero mantener los nombres de las columnas
        tabla.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, BorderLayout.NORTH);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada != -1) {
                    Asignatura asignaturaSeleccionada = Asignatura.asignaturas.get(filaSeleccionada);
                    System.out.println(asignaturaSeleccionada.getNombre());
                    PanelAsig.panelExamenes.actualizarTabla(asignaturaSeleccionada);

                    revalidate();
                    repaint();
                }
            }
        });
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0);

        if (asignaturas != null && !asignaturas.isEmpty()) {
            for (Asignatura asignatura : asignaturas) {
                asignatura.notaFinal();
                asignatura.calcularEstado();

                String nombre = asignatura.getNombre();
                String curso = asignatura.getCurso().name();
                String estado = asignatura.getEstado() != null ? asignatura.getEstado().name() : "Sin estado";
                int creditos = asignatura.getECTS();
                double notaMedia = asignatura.getNotaFinal();
                String notaMediaFormateada = String.format("%.2f", notaMedia);

                modeloTabla.addRow(new Object[]{nombre, curso, estado, creditos, notaMediaFormateada});
            }
            modeloTabla.fireTableDataChanged();
        } else {
            System.out.println("No hay asignaturas para mostrar.");
        }
    }
}

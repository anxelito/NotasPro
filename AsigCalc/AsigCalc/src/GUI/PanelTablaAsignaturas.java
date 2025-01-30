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

        tabla.setRowHeight(20);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

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

                modeloTabla.addRow(new Object[]{nombre, curso, estado, creditos, notaMedia});
            }
            modeloTabla.fireTableDataChanged();
        } else {
            System.out.println("No hay asignaturas para mostrar.");
        }
    }
}

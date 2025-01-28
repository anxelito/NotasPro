package GUI;

import Main.Asignatura;
import Main.Prueba;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTablaAsignaturas extends JPanel {
        String[] columnas = {"Nombre", "Curso", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

    public PanelTablaAsignaturas() {
        setLayout(new BorderLayout());

        for (Asignatura asignatura : Asignatura.asignaturas) {
            String nombre = asignatura.getNombre();
            String curso = asignatura.getCurso().name(); 
            asignatura.calcularEstado();
            String estado = asignatura.getEstado() != null ? asignatura.getEstado().name() : "Sin estado";
            modeloTabla.addRow(new Object[]{nombre, curso, estado});
        }

        JTable tabla = new JTable(modeloTabla);

        tabla.setRowHeight(20);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, BorderLayout.CENTER);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tabla.getSelectedRow(); 
                if (filaSeleccionada != -1) {
                    mostrarExamenes(filaSeleccionada, tabla, modeloTabla);
                }
            }
        });
    }

    private void mostrarExamenes(int filaSeleccionada, JTable tabla, DefaultTableModel modeloTabla) {
        Asignatura asignaturaSeleccionada = Asignatura.asignaturas.get(filaSeleccionada);

        String[] columnasExamenes = {"Nombre", "Nota", "Estado"};
        DefaultTableModel modeloExamenes = new DefaultTableModel(columnasExamenes, 0);

        for (Prueba examen : asignaturaSeleccionada.getPruebas()) {
            modeloExamenes.addRow(new Object[]{
                examen.getNombre(),
                examen.getNota(),
                examen.getEstado().toString()
            });
        }

        JTable tablaExamenes = new JTable(modeloExamenes);
        tablaExamenes.setRowHeight(18);
        tablaExamenes.setFont(new Font("Arial", Font.PLAIN, 11));

        JScrollPane scrollExamenes = new JScrollPane(tablaExamenes);
        scrollExamenes.setPreferredSize(new Dimension(380, 100));

        JPanel panelExamenes = new JPanel(new BorderLayout());
        panelExamenes.add(scrollExamenes, BorderLayout.CENTER);
        panelExamenes.setBorder(BorderFactory.createTitledBorder("Exámenes"));

        JOptionPane.showMessageDialog(
            this,
            panelExamenes,
            "Exámenes de " + asignaturaSeleccionada.getNombre(),
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0); 

        for (Asignatura asignatura : Asignatura.asignaturas) {
            String nombre = asignatura.getNombre();
            String curso = asignatura.getCurso().name(); 
            String estado = asignatura.getEstado() != null ? asignatura.getEstado().name() : "Sin estado";
        
            modeloTabla.addRow(new Object[]{nombre, curso, estado});
        }
    }
}

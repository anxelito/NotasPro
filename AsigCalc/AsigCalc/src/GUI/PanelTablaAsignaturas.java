package GUI;

import Main.Asignatura;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelTablaAsignaturas extends JPanel {

    public PanelTablaAsignaturas() {
        setLayout(new BorderLayout());

        String[] columnas = {"Nombre", "Curso", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        for (Asignatura asignatura : Asignatura.asignaturas) {
            String nombre = asignatura.getNombre();
            String curso = asignatura.getCurso().name(); // Convertimos el enum a String
            String estado = asignatura.getEstado() != null ? asignatura.getEstado().name() : "Sin estado";
            modeloTabla.addRow(new Object[]{nombre, curso, estado});
        }

        JTable tabla = new JTable(modeloTabla);

        tabla.setRowHeight(20); 
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12)); 
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(300, 150)); 

        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void actualizarTabla()
}

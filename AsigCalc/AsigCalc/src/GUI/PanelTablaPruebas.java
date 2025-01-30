package GUI;

import Main.Asignatura;
import Main.Prueba;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelTablaPruebas extends JPanel { 
    private DefaultTableModel modeloExamenes;
    private JTable tablaExamenes;

    public PanelTablaPruebas(Asignatura asignaturaSeleccionada) {
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(500, 150)); 
        setBackground(Color.DARK_GRAY); // Solo para ver si se dibuja

        String[] columnasExamenes = {"Nombre", "Nota", "Estado"};
        modeloExamenes = new DefaultTableModel(columnasExamenes, 0);
        tablaExamenes = new JTable(modeloExamenes);
        tablaExamenes.setRowHeight(18);
        tablaExamenes.setFont(new Font("Arial", Font.PLAIN, 11));

        JScrollPane scrollExamenes = new JScrollPane(tablaExamenes);
        add(scrollExamenes, BorderLayout.CENTER);

        actualizarTabla(asignaturaSeleccionada);
    }
    
    public PanelTablaPruebas() {
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(500, 150)); 
        setBackground(Color.DARK_GRAY); // Solo para ver si se dibuja

        String[] columnasExamenes = {"Nombre", "Nota", "Estado"};
        modeloExamenes = new DefaultTableModel(columnasExamenes, 0);
        tablaExamenes = new JTable(modeloExamenes);
        tablaExamenes.setRowHeight(18);
        tablaExamenes.setFont(new Font("Arial", Font.PLAIN, 11));

        JScrollPane scrollExamenes = new JScrollPane(tablaExamenes);
        add(scrollExamenes, BorderLayout.CENTER);
    }

    public void actualizarTabla(Asignatura asignaturaSeleccionada) {
        modeloExamenes.setRowCount(0);

        for (Prueba examen : asignaturaSeleccionada.getPruebas()) {
            modeloExamenes.addRow(new Object[]{
                examen.getNombre(),
                examen.getNota(),
                examen.getEstado().toString()
            });
        }
    }
}

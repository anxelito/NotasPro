package GUI;

import Main.Asignatura;
import Main.Prueba;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class PanelTablaPruebas extends JPanel {
    private DefaultTableModel modeloExamenes;
    private JTable tablaExamenes;

    public PanelTablaPruebas() {
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(500, 150)); 

        String[] columnasExamenes = {"Nombre","Ponderacion (%)", "Nota Minima", "Nota", "Estado"};
        modeloExamenes = new DefaultTableModel(columnasExamenes, 0);
        tablaExamenes = new JTable(modeloExamenes);
        tablaExamenes.setRowHeight(18);
        tablaExamenes.setFont(new Font("Arial", Font.PLAIN, 11));
        
        tablaExamenes.setRowHeight(20);
        tablaExamenes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaExamenes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        tablaExamenes.setBackground(Color.WHITE);
        tablaExamenes.setForeground(Color.BLACK);

        tablaExamenes.getTableHeader().setBackground(Color.BLACK);
        tablaExamenes.getTableHeader().setForeground(Color.WHITE);

        tablaExamenes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tablaExamenes.setShowGrid(true);  
        tablaExamenes.setGridColor(Color.BLACK);  

        tablaExamenes.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollExamenes = new JScrollPane(tablaExamenes);
        add(scrollExamenes, BorderLayout.CENTER);
    }
    
    public PanelTablaPruebas(Asignatura asignaturaSeleccionada) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 150)); 
        setBackground(Color.DARK_GRAY); // Solo para ver si se dibuja

        String[] columnasExamenes = {"Nombre","Ponderacion (%)", "Nota Minima", "Nota", "Estado"};
        modeloExamenes = new DefaultTableModel(columnasExamenes, 0);
        tablaExamenes = new JTable(modeloExamenes);
        tablaExamenes.setRowHeight(20);
        tablaExamenes.setFont(new Font("Arial", Font.PLAIN, 12));


        tablaExamenes.setBackground(Color.WHITE);
        tablaExamenes.setForeground(Color.BLACK);

        tablaExamenes.getTableHeader().setBackground(Color.BLACK);
        tablaExamenes.getTableHeader().setForeground(Color.WHITE);


        tablaExamenes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tablaExamenes.setShowGrid(true);  
        tablaExamenes.setGridColor(Color.BLACK);  
        
        tablaExamenes.setIntercellSpacing(new Dimension(0, 0));

        tablaExamenes.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRenderer());

        JScrollPane scrollExamenes = new JScrollPane(tablaExamenes);
        add(scrollExamenes, BorderLayout.CENTER);

        actualizarTabla(asignaturaSeleccionada);
        modeloExamenes.fireTableDataChanged();

    }

    public void actualizarTabla(Asignatura asignaturaSeleccionada) {
        modeloExamenes.setRowCount(0);

        for (Prueba examen : asignaturaSeleccionada.getPruebas()) {
            modeloExamenes.addRow(new Object[]{
                examen.getNombre(),
                examen.getPonderacion(),
                examen.getNotaMin(),
                examen.getNota(),
                examen.getEstado().toString()
            });
        }

        tablaExamenes.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRenderer());
        modeloExamenes.fireTableDataChanged();
    }


class EstadoCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            switch (value.toString()) {
                case "APROBADA":
                    setBackground(new Color(144, 238, 144));
                    break;
                case "SUSPENSA":
                    setBackground(new Color(255, 204, 203));
                    break;
                default:
                    setBackground(Color.WHITE);
                    break;
            }
        }

        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));

        return this;
    }
}

}

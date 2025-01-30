package GUI;

import Main.Asignatura;
import Main.Prueba;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PanelTablaPruebas extends JPanel {
    private DefaultTableModel modeloExamenes;
    private JTable tablaExamenes;

    public PanelTablaPruebas() {
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(500, 150)); 

        String[] columnasExamenes = {"Nombre", "Nota", "Estado"};
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

        String[] columnasExamenes = {"Nombre", "Nota", "Estado"};
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

        tablaExamenes.getColumnModel().getColumn(2).setCellRenderer(new EstadoCellRenderer());

        JScrollPane scrollExamenes = new JScrollPane(tablaExamenes);
        add(scrollExamenes, BorderLayout.CENTER);

        actualizarTabla(asignaturaSeleccionada);
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

    class EstadoCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setOpaque(true); // Necesario para que el cambio de color de fondo funcione

            // Cambiar el fondo de la celda dependiendo del estado
            if (value != null) {
                if (value.toString().equals("APROBADA")) {
                    label.setBackground(new Color(144, 238, 144)); // Fondo verde suave (Light Green)
                } else if (value.toString().equals("SUSPENSA")) {
                    label.setBackground(new Color(255, 204, 203)); // Fondo rojo suave (Light Red)
                } else {
                    label.setBackground(Color.WHITE); // Fondo blanco si no tiene estado
                }
            }

            // Establecer el color del texto
            label.setForeground(Color.BLACK);

            return label;
        }
    }
}

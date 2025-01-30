package GUI;

import Main.Asignatura;
import static Main.Asignatura.asignaturas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
        
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);

        tabla.getTableHeader().setBackground(Color.BLACK);
        tabla.getTableHeader().setForeground(Color.WHITE);

        tabla.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tabla.setShowGrid(true);  
        tabla.setGridColor(Color.BLACK); 

        tabla.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, BorderLayout.NORTH);

        // Establecer un renderizador personalizado para la columna "Estado"
        tabla.getColumnModel().getColumn(2).setCellRenderer(new EstadoCellRenderer());

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
                String estado = asignatura.getEstado().toString(); // Esto devuelve el estado
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

class EstadoCellRenderer extends JLabel implements TableCellRenderer {
    public EstadoCellRenderer() {
        setOpaque(true); 
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());

        if (value != null) {
            if (value.toString().equals("APROBADA")) {
                setBackground(new Color(144, 238, 144)); 
            } else if (value.toString().equals("SUSPENSA")) {
                setBackground(new Color(255, 204, 203)); 
            } else {
                setBackground(Color.WHITE); 
            }
        }

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK)); 
        setForeground(Color.BLACK);

        return this;
    }
}

}

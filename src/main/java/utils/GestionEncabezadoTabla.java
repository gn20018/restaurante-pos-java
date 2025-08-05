package utils;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


public class GestionEncabezadoTabla  implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        JComponent jcomponent = null;
        
        if( value instanceof String ) {
            jcomponent = new JLabel((String) value);
            Utilidades.cambiarTamanioFuente(jcomponent,14, Font.BOLD);
            ((JLabel)jcomponent).setHorizontalAlignment( SwingConstants.CENTER );
        }         
   
        //jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(221, 211, 211)));
        jcomponent.setBorder(javax.swing.BorderFactory.createLineBorder( new Color(0, 0, 0)));
        jcomponent.setOpaque(true);
        jcomponent.setBackground( new Color(189, 131, 255) );
        jcomponent.setToolTipText(value.toString());
        jcomponent.setForeground(Color.black);

        return jcomponent;
    }


}

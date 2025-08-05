package utils;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class GestionCeldas extends DefaultTableCellRenderer implements TableCellRenderer {

	private Font bold = new Font( "Amaranth",Font.PLAIN ,14 );

	public GestionCeldas(){
		
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {

		JComponent jcomponent = null;

		jcomponent = new JLabel(value.toString());
			((JLabel)jcomponent).setHorizontalAlignment( SwingConstants.CENTER );


        Color colorFondoPorDefecto=new Color(105, 252, 177);
        Color colorFondoSeleccion=new Color(255, 221, 64);


        if (selected) {
			jcomponent.setBorder(javax.swing.BorderFactory.createLineBorder( new Color(0, 0, 0)));
			jcomponent.setOpaque(true);
			jcomponent.setBackground( colorFondoSeleccion );
			jcomponent.setToolTipText(value.toString());
			jcomponent.setForeground(Color.black);
        }
        else
        {
			jcomponent.setBorder(javax.swing.BorderFactory.createLineBorder( new Color(0, 0, 0)));
			jcomponent.setOpaque(true);
			jcomponent.setBackground( colorFondoPorDefecto );
			jcomponent.setToolTipText(value.toString());
			jcomponent.setForeground(Color.black);
        }

		jcomponent.setFont(bold);


		return jcomponent;
	}
	
	
	
}

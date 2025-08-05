package com.saborperuano.sistema.Views.Principal.Ventas.Dialog;

import com.saborperuano.sistema.Views.Principal.FrmPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaVentas;
import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogEliminarVenta extends JDialog{

    private JLabel lblTitulo;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public JDialogEliminarVenta(JTable tbl) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(425,224));
        setPreferredSize(new Dimension(445,224));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes

        lblTitulo= new JLabel("<html><center>¿Estás seguro de eliminar la venta actual?<br>Se borrarán todos los productos agregados</center></html>");
        lblTitulo.setBounds(43,25,359,69);
        cambiarTamanioFuente(lblTitulo,16,Font.BOLD);
        add(lblTitulo);

        btnAceptar= new JButton("Aceptar");
        btnAceptar.setBounds(62,130,105,25);
        cambiarTamanioFuente(btnAceptar,12,Font.PLAIN);
        btnCancelar= new JButton("Cancelar");
        btnCancelar.setBounds(255,130,105,25);
        cambiarTamanioFuente(btnAceptar,12,Font.PLAIN);


        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dfm = (DefaultTableModel) tbl.getModel();
                if (dfm.getRowCount()==0){
                    dispose();
                    JOptionPane.showMessageDialog(null,"Debes agregar datos a la venta.");
                }else {
                    //Eliminando la venta actual de la lista de ventas
                    listaVentas.eliminarPorObjeto(listaVentas.obtenerUltimoObjeto());

                    //Vaciando la lista de detalles de venta
                    FrmPrincipal.listaDetallesVenta.vaciar();

                    //Vaciando la tabla
                    dfm.setRowCount(0);
                    dispose();
                }


            }
        });




        add(btnAceptar);
        add(btnCancelar);

        setVisible(true);
    }


}

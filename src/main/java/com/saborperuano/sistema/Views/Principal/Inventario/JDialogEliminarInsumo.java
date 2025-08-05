package com.saborperuano.sistema.Views.Principal.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogEliminarInsumo extends JDialog{

    private JLabel lblTitulo;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public JDialogEliminarInsumo( JTable tbl) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(425,224));
        setPreferredSize(new Dimension(445,224));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes

        lblTitulo= new JLabel("¿Estás seguro de eliminar el insumo?");
        lblTitulo.setBounds(43,25,359,69);
        cambiarTamanioFuente(lblTitulo,20,Font.BOLD);
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
                    JOptionPane.showMessageDialog(null,"Debes agregar datos a la tabla");
                }else {
                    dfm.setRowCount(0);
                    dispose();
                }
            }
        });
        add(btnCancelar);
        add(btnAceptar);
        setVisible(true);

    }


}

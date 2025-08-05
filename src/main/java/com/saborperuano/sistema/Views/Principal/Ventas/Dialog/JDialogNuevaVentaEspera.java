package com.saborperuano.sistema.Views.Principal.Ventas.Dialog;

import utils.RoundedButton;
import utils.RoundedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogNuevaVentaEspera extends JDialog {
   //atributos
    private JLabel lbltitulo;
    private JLabel lblNombreVenta;
    private JTextField txtNombreVenta;

    private JButton btnAgregarVentaEspera;


    //Constructor
    public JDialogNuevaVentaEspera() {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(450,300));
        setPreferredSize(new Dimension(450,300));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);


        //Inicializar componentes
        lbltitulo= new JLabel("Poner Venta en Espera");
        lbltitulo.setBounds(110,24,349,28);
        cambiarTamanioFuente(lbltitulo,28,Font.BOLD);
        add(lbltitulo);

        //Inicializar componentes
        lblNombreVenta= new JLabel("Nombre de Venta");
        lblNombreVenta.setBounds(40,110,150,15);
        cambiarTamanioFuente(lblNombreVenta,16,Font.PLAIN);
        add(lblNombreVenta);

        txtNombreVenta= new RoundedTextField(30,3,2);
        txtNombreVenta.setBounds(208,110,175,22);
        cambiarTamanioFuente(txtNombreVenta,16,Font.PLAIN);
        add(txtNombreVenta);


        //Inicializar componentes
        btnAgregarVentaEspera = new RoundedButton("Poner Venta en espera",3);
        btnAgregarVentaEspera.setBounds(100,200,240,25);
        cambiarTamanioFuente(btnAgregarVentaEspera,16,Font.PLAIN);
        btnAgregarVentaEspera.setForeground(Color.black);
        btnAgregarVentaEspera.setBackground(Color.green);

        btnAgregarVentaEspera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        add(btnAgregarVentaEspera);

        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public String obtenerNombreVenta(){
        return txtNombreVenta.getText();
    }

}

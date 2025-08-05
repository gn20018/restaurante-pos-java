package com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogMenu;

import utils.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogCrearMenuAdvertencia extends JDialog {
   //atributos
    private JLabel lbltitulo;
    private JLabel lblMensaje;
    private JLabel lblPregunta;

    private JButton btnAceptar;
    private JButton btnCancelar;


    //Constructor
    public JDialogCrearMenuAdvertencia() {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(657,300));
        setPreferredSize(new Dimension(657,248));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);


        //Inicializar componentes
        lbltitulo= new JLabel("¡¡¡Atención!!!");
        lbltitulo.setBounds(235,29,190,40);
        cambiarTamanioFuente(lbltitulo,28,Font.BOLD);
        add(lbltitulo);

        //Inicializar componentes
        lblMensaje= new JLabel("<html><center><p>Esta opción realiza el vaciado del menú actual para crear</p>" +
                "<p> un nuevo listado de platillos para el menú del día.</p></center></html>");
        lblMensaje.setBounds(91,92,475,40);
        cambiarTamanioFuente(lblMensaje,16,Font.BOLD);
        add(lblMensaje);


        //Inicializar componentes
        lblPregunta= new JLabel("¿Estás seguro de continuar?");
        lblPregunta.setBounds(225,149,219,30);
        cambiarTamanioFuente(lblPregunta,16,Font.BOLD);
        add(lblPregunta);


        //Inicializar componentes
        btnAceptar = new RoundedButton("Aceptar",3);
        btnAceptar.setBounds(171,187,149,40);
        cambiarTamanioFuente(btnAceptar,16,Font.PLAIN);
        btnAceptar.setForeground(Color.black);
        btnAceptar.setBackground(Color.green);

        //Inicializar componentes
        btnCancelar = new RoundedButton("Cancelar",3);
        btnCancelar.setBounds(367,187,149,40);
        cambiarTamanioFuente(btnCancelar,16,Font.PLAIN);
        btnCancelar.setForeground(Color.white);
        btnCancelar.setBackground(Color.RED);



        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null,"Aceptaste");
//                ProductoDTO p  = new ProductoDTO(productoListaEnlazadaGenerica.longitud,
//                        txtNombre.getText(),
//                        Categoria.valueOf(cbCargo.getSelectedItem().toString().toUpperCase()),
//                        Float.parseFloat(txtPrecioUnitario.getText()));
//
//                try {
//                    FrmPrincipal.menuDTO.agregar(p);
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//
//                if (productoListaEnlazadaGenerica.longitud == 0){
//                    productoListaEnlazadaGenerica.agregarInicio(p);
//                }else {
//                    productoListaEnlazadaGenerica.agregarAlFinal(p);
//                }
//
//                Object[] fila = {productoListaEnlazadaGenerica.longitud,
//                        txtNombre.getText(),
//                        Categoria.valueOf(cbCargo.getSelectedItem().toString().toUpperCase()),
//                        Float.parseFloat(txtPrecioUnitario.getText())};
//                agregarFila(fila,tbl,jsp);
//
//                System.out.println();
//                System.out.println("Lista Enlazada de Productos");
//                productoListaEnlazadaGenerica.mostrarLista();
//                System.out.println();

                dispose();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Cancelaste");
//                ProductoDTO p  = new ProductoDTO(productoListaEnlazadaGenerica.longitud,
//                        txtNombre.getText(),
//                        Categoria.valueOf(cbCargo.getSelectedItem().toString().toUpperCase()),
//                        Float.parseFloat(txtPrecioUnitario.getText()));
//
//                try {
//                    FrmPrincipal.menuDTO.agregar(p);
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//
//                if (productoListaEnlazadaGenerica.longitud == 0){
//                    productoListaEnlazadaGenerica.agregarInicio(p);
//                }else {
//                    productoListaEnlazadaGenerica.agregarAlFinal(p);
//                }
//
//                Object[] fila = {productoListaEnlazadaGenerica.longitud,
//                        txtNombre.getText(),
//                        Categoria.valueOf(cbCargo.getSelectedItem().toString().toUpperCase()),
//                        Float.parseFloat(txtPrecioUnitario.getText())};
//                agregarFila(fila,tbl,jsp);
//
//                System.out.println();
//                System.out.println("Lista Enlazada de Productos");
//                productoListaEnlazadaGenerica.mostrarLista();
//                System.out.println();

                dispose();
            }
        });

        add(btnAceptar);
        add(btnCancelar);
        setVisible(true);

    }


}

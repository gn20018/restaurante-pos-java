package com.saborperuano.sistema.Views.Principal.Inventario;

import com.saborperuano.sistema.Models.Insumo;
import utils.ListaEnlazadaGenerica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogModificarInsumo extends JDialog{
    //atributos
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblCategoria;
    private JComboBox cbCategoria;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JLabel lblUbicacion;
    private JComboBox cbUbicacion;
    private JButton btnModificarInsumo;

    //Constructor
    public JDialogModificarInsumo(ListaEnlazadaGenerica<Insumo> listaInventario) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(432,277));
        setPreferredSize(new Dimension(432,277));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes
        lblTitulo= new JLabel("Modificar Insumo");
        lblTitulo.setBounds(42,15,346,28);
        cambiarTamanioFuente(lblTitulo,28,Font.BOLD);
        add(lblTitulo);

        lblNombre= new JLabel("Nombre");
        lblNombre.setBounds(42,70,56,15);
        cambiarTamanioFuente(lblNombre,14,Font.PLAIN);
        add(lblNombre);

        txtNombre= new JTextField();
        txtNombre.setBounds(191,68,194,22);
        cambiarTamanioFuente(txtNombre,14,Font.PLAIN);
        add(txtNombre);

        lblCategoria= new JLabel("Categoria");
        lblCategoria.setBounds(42,98,68,15);
        cambiarTamanioFuente(lblCategoria,14,Font.PLAIN);
        add(lblCategoria);

        cbCategoria= new JComboBox();
        cbCategoria.setBounds(191,95,194,22);
        cambiarTamanioFuente(cbCategoria,14,Font.PLAIN);
        cbCategoria.addItem("verdura");
        cbCategoria.addItem("cereal");
        cbCategoria.addItem("carne");
        add(cbCategoria);

        lblCantidad= new JLabel("Cantidad");
        lblCantidad.setBounds(42,125,64,15);
        cambiarTamanioFuente(lblCantidad,14,Font.PLAIN);
        add(lblCantidad);

        txtCantidad= new JTextField();
        txtCantidad.setBounds(191,122,194,22);
        cambiarTamanioFuente(txtCantidad,14,Font.PLAIN);
        add(txtCantidad);

        lblUbicacion= new JLabel("Ubicación (estante)");
        lblUbicacion.setBounds(42,152,135,15);
        cambiarTamanioFuente(lblUbicacion,14,Font.PLAIN);
        add(lblUbicacion);

        cbUbicacion= new JComboBox();
        cbUbicacion.setBounds(191,149,194,22);
        cambiarTamanioFuente(cbUbicacion,14,Font.PLAIN);
        cbUbicacion.addItem("estanteA01");
        cbUbicacion.addItem("estanteA02");
        cbUbicacion.addItem("estantePrincipal");
        add(cbUbicacion);

        btnModificarInsumo= new JButton("Guardar Cambio");
        btnModificarInsumo.setBounds(142,195,144,25);
        cambiarTamanioFuente(btnModificarInsumo,12,Font.PLAIN);
        add(btnModificarInsumo);

        setVisible(true);
        btnModificarInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
                Insumo p = listaInventario.buscarPorCampo("Nombre",txtNombre.getText().toString());



                System.out.println();
                System.out.println("Lista Enlazada de Productos: Modificada");
                listaInventario.mostrarLista();
                System.out.println();

            }
        });
        add(btnModificarInsumo);
        setVisible(true);
    }

}



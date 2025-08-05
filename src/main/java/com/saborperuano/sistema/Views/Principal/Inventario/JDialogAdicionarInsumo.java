package com.saborperuano.sistema.Views.Principal.Inventario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogAdicionarInsumo extends JDialog{
    //atributos
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JTextField txtcodigo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblCategoria;
    private JComboBox cbCategoria;
    private JLabel lblPrecioUnitario;
    private JTextField txtPrecioUnitario;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JLabel lblUbicacion;
    private JComboBox cbUbicacion;
    private JButton btnAdicionarInsumo;

    //Constructor
    public JDialogAdicionarInsumo() {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(432,350));
        setPreferredSize(new Dimension(432,350));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes
        lblTitulo= new JLabel("Adicionar Mercancia");
        lblTitulo.setBounds(42,24,346,28);
        cambiarTamanioFuente(lblTitulo,28,Font.BOLD);
        add(lblTitulo);

        lblCodigo= new JLabel("Código");
        lblCodigo.setBounds(42,91,49,15);
        cambiarTamanioFuente(lblCodigo,14,Font.PLAIN);
        add(lblCodigo);

        txtcodigo= new JTextField();
        txtcodigo.setBounds(191,88,194,22);
        cambiarTamanioFuente(txtcodigo,14,Font.PLAIN);
        add(txtcodigo);

        lblNombre= new JLabel("Nombre");
        lblNombre.setBounds(42,118,56,15);
        cambiarTamanioFuente(lblNombre,14,Font.PLAIN);
        add(lblNombre);

        txtNombre= new JTextField();
        txtNombre.setBounds(191,115,194,22);
        cambiarTamanioFuente(txtNombre,14,Font.PLAIN);
        add(txtNombre);

        lblCategoria= new JLabel("Categoria");
        lblCategoria.setBounds(42,145,68,15);
        cambiarTamanioFuente(lblCategoria,14,Font.PLAIN);
        add(lblCategoria);

        cbCategoria= new JComboBox();
        cbCategoria.setBounds(191,142,194,22);
        cambiarTamanioFuente(cbCategoria,14,Font.PLAIN);
        cbCategoria.addItem("verdura");
        cbCategoria.addItem("cereal");
        cbCategoria.addItem("carne");
        add(cbCategoria);

        lblPrecioUnitario= new JLabel("Precio Unitario");
        lblPrecioUnitario.setBounds(42,172,101,15);
        cambiarTamanioFuente(lblPrecioUnitario,14,Font.PLAIN);
        add(lblPrecioUnitario);

        txtPrecioUnitario= new JTextField();
        txtPrecioUnitario.setBounds(191,169,194,22);
        cambiarTamanioFuente(txtPrecioUnitario,14,Font.PLAIN);
        add(txtPrecioUnitario);

        lblCantidad= new JLabel("Cantidad");
        lblCantidad.setBounds(42,199,64,15);
        cambiarTamanioFuente(lblCantidad,14,Font.PLAIN);
        add(lblCantidad);

        txtCantidad= new JTextField();
        txtCantidad.setBounds(191,196,194,22);
        cambiarTamanioFuente(txtCantidad,14,Font.PLAIN);
        add(txtCantidad);

        lblUbicacion= new JLabel("Ubicación (estante)");
        lblUbicacion.setBounds(42,226,135,15);
        cambiarTamanioFuente(lblUbicacion,14,Font.PLAIN);
        add(lblUbicacion);

        cbUbicacion= new JComboBox();
        cbUbicacion.setBounds(191,223,194,22);
        cambiarTamanioFuente(cbUbicacion,14,Font.PLAIN);
        cbUbicacion.addItem("estanteA01");
        cbUbicacion.addItem("estanteA02");
        cbUbicacion.addItem("estantePrincipal");
        add(cbUbicacion);

        btnAdicionarInsumo= new JButton("Adicinar Mercancia");
        btnAdicionarInsumo.setBounds(144,272,144,25);
        cambiarTamanioFuente(btnAdicionarInsumo,12,Font.PLAIN);
        add(btnAdicionarInsumo);

        setVisible(true);
        btnAdicionarInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

}



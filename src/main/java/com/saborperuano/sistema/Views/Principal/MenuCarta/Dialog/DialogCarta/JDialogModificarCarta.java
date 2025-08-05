package com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogCarta;

import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.Enums.Categoria;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import com.saborperuano.sistema.Views.Principal.Ventas.JPanelVentas;
import utils.ListaEnlazadaGenerica;
import utils.RoundedButton;
import utils.RoundedTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogModificarCarta extends JDialog {
    private final JLabel lblCategoria;
    private final JLabel lblDescripcion;
    private final JTextField txtDescripcion;
    private final JComboBox cbCategoriaPlatillo;
    private final JLabel lblTipoPlatillo;
    private final JComboBox cbTipoPlatillo;
    private final JButton btnCancelar;
    private final JLabel lblEstado;
    private final JComboBox cbEstado;
    //atributos
    private JLabel lbltitulo;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblCargo;
    private JComboBox cbCargo;
    private JLabel lblPrecioUnitario;
    private JTextField txtPrecioUnitario;

    private JButton btnModificarPlatillo;


    //Constructor
    public JDialogModificarCarta(ListaEnlazadaGenerica<CartaDTO> cartaDTOListaEnlazadaGenerica, CartaDTO cartaDTO, JScrollPane jsp, JTable tbl) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(450,400));
        setPreferredSize(new Dimension(450,350));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);


        //Inicializar componentes
        lbltitulo= new JLabel("Modificar Platillo");
        lbltitulo.setBounds(110,24,349,28);
        cambiarTamanioFuente(lbltitulo,28,Font.BOLD);
        add(lbltitulo);


        //Inicializar componentes
        lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(36,96,45,15);
        cambiarTamanioFuente(lblNombre,12,Font.PLAIN);
        add(lblNombre);

        txtNombre = new RoundedTextField(30,3,2);
        txtNombre.setBounds(166,90,240,25);
        cambiarTamanioFuente(txtNombre,12,Font.PLAIN);
        txtNombre.setText(cartaDTO.getNombre());
        add(txtNombre);

        //Inicializar componentes
        lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setBounds(36,128,65,15);
        cambiarTamanioFuente(lblDescripcion,12,Font.PLAIN);
        add(lblDescripcion);

        txtDescripcion = new RoundedTextField(30,3,2);
        txtDescripcion.setBounds(166,122,240,25);
        cambiarTamanioFuente(txtDescripcion,12,Font.PLAIN);
        txtDescripcion.setText(cartaDTO.getDescripcion());
        add(txtDescripcion);


        //Inicializar componentes
        lblCategoria= new JLabel("Categoría de Platillo");
        lblCategoria.setBounds(36,160,120,15);
        cambiarTamanioFuente(lblCategoria,12,Font.PLAIN);
        add(lblCategoria);


        cbCategoriaPlatillo= new JComboBox();
        cbCategoriaPlatillo.setBounds(166,154,240,25);
        cambiarTamanioFuente(cbCategoriaPlatillo,12,Font.PLAIN);
        cbCategoriaPlatillo.addItem("Entradas");
        cbCategoriaPlatillo.addItem("Segundos");
        cbCategoriaPlatillo.addItem("Postres");
        cbCategoriaPlatillo.addItem("Bebidas");
        cbCategoriaPlatillo.setSelectedItem(cartaDTO.getCategoria().toString());

        add(cbCategoriaPlatillo);

        //Inicializar componentes
        lblTipoPlatillo = new JLabel("Tipo de Platillo");
        lblTipoPlatillo.setBounds(36,192,120,15);
        cambiarTamanioFuente(lblTipoPlatillo,12,Font.PLAIN);
        add(lblTipoPlatillo);

        cbTipoPlatillo= new JComboBox();
        cbTipoPlatillo.setBounds(166,186,240,25);
        cambiarTamanioFuente(cbCategoriaPlatillo,12,Font.PLAIN);
        cbTipoPlatillo.addItem("Ejecutivo");
        cbTipoPlatillo.addItem("Económico");
        cbTipoPlatillo.setSelectedItem(cartaDTO.getTipoPlatillo().toString());

        add(cbTipoPlatillo);



        //Inicializar componentes
        lblPrecioUnitario = new JLabel("Precio Unitario");
        lblPrecioUnitario.setBounds(36,224,120,15);
        cambiarTamanioFuente(lblPrecioUnitario,12,Font.PLAIN);
        add(lblPrecioUnitario);

        txtPrecioUnitario = new RoundedTextField(30,3,2);
        txtPrecioUnitario.setBounds(166,218,240,25);
        cambiarTamanioFuente(txtPrecioUnitario,12,Font.PLAIN);
        txtPrecioUnitario.setText(String.valueOf(cartaDTO.getPrecioUnitario()));
        add(txtPrecioUnitario);

        //Inicializar componentes
        lblEstado = new JLabel("Estado");
        lblEstado.setBounds(36,256,120,15);
        cambiarTamanioFuente(lblEstado,12,Font.PLAIN);
        add(lblEstado);

        cbEstado= new JComboBox();
        cbEstado.setBounds(166,250,240,25);
        cambiarTamanioFuente(cbEstado,12,Font.PLAIN);
        cbEstado.addItem(EstadoRegistro.Habilitado.toString());
        cbEstado.addItem(EstadoRegistro.Deshabilitado.toString());
        cbEstado.setSelectedItem(cartaDTO.getEstado().toString());

        add(cbEstado);


        //Inicializar componentes
        btnModificarPlatillo = new RoundedButton("Guardar Cambios",3);
        btnModificarPlatillo.setBounds(75,290,150,35);
        cambiarTamanioFuente(btnModificarPlatillo,14,Font.PLAIN);
        btnModificarPlatillo.setForeground(Color.black);
        btnModificarPlatillo.setBackground(Color.green);

        btnCancelar = new RoundedButton("Cancelar",3);
        btnCancelar.setBounds(239,290,150,35);
        cambiarTamanioFuente(btnCancelar,14,Font.PLAIN);
        btnCancelar.setForeground(Color.white);
        btnCancelar.setBackground(Color.red);


        btnModificarPlatillo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cartaDTO.setNombre(txtNombre.getText());
                cartaDTO.setDescripcion(txtDescripcion.getText());
                cartaDTO.setTipoPlatillo(cbTipoPlatillo.getSelectedItem().toString());
                cartaDTO.setCategoria(Categoria.valueOf(cbCategoriaPlatillo.getSelectedItem().toString()));
                cartaDTO.setPrecioUnitario(Float.parseFloat(txtPrecioUnitario.getText()));
                cartaDTO.setEstado(EstadoRegistro.valueOf(cbEstado.getSelectedItem().toString()));

                try {
                    FrmPrincipal.cartaController.modificar(cartaDTO);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


                Object[] fila = {cartaDTO.getCodigo(),
                        cartaDTO.getNombre(),
                        cartaDTO.getDescripcion(),
                        cartaDTO.getTipoPlatillo(),
                        cartaDTO.getCategoria(),
                        cartaDTO.getPrecioUnitario(),
                        cartaDTO.getEstado().toString()};

                insertarFila(tbl.getSelectedRow(),fila,tbl,jsp);

                System.out.println();
                System.out.println("Lista Enlazada de Productos");
                cartaDTOListaEnlazadaGenerica.mostrarLista();
                System.out.println();

                JPanelVentas.obtenerNombresPlatillos();

                dispose();
            }
        });


        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });






        add(btnModificarPlatillo);
        add(btnCancelar);
        setVisible(true);

    }

    private void agregarFila(Object[] fila, JTable tbl, JScrollPane jsp) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.addRow(fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl,jsp);
    }

    private void insertarFila(int nroFila, Object[] fila, JTable tbl, JScrollPane jsp) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.removeRow(nroFila);
        model.insertRow(nroFila,fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl,jsp);
    }

    private void ajustarAnchoTabla(JTable tbl, JScrollPane jsp) {
        int alturaPreferida = jsp.getHeight();
        int alturaActual =  tbl.getRowCount()*tbl.getRowHeight();
        if (alturaActual >= alturaPreferida){
            tbl.setFillsViewportHeight(true);
            tbl.setPreferredSize(new Dimension(tbl.getPreferredSize().width,alturaActual));
            tbl.revalidate();
            tbl.repaint();
            jsp.setPreferredSize(tbl.getPreferredSize());
            jsp.revalidate();
            jsp.repaint();
        }
    }


}

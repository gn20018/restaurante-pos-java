package com.saborperuano.sistema.Views.Principal.Inventario;

import com.saborperuano.sistema.Models.Insumo;
import com.toedter.calendar.JDateChooser;
import utils.ListaEnlazadaGenerica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogNuevoInsumo extends JDialog {
    //atributos
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblCategoria;
    private JLabel lblPrecioUnitario;
    private JLabel lblCantidad;
    private JLabel lblStockMinimo;
    private JLabel lblFechaCompra;
    private JLabel lblFechaVencimiento;
    private JTextField txtStockMinimo;
    private JTextField txtNombre;
    private JComboBox cbCategoria;
    private JTextField txtPrecioUnitario;
    private JTextField txtCantidad;
    private JDateChooser dtFechaVencimiento;
    private JDateChooser dtFechaCompra;
    private JButton btnNuevoInsumo;

    //Constructor
    public JDialogNuevoInsumo(ListaEnlazadaGenerica<Insumo> listaInventario, JScrollPane jsp, JTable tbl) {

        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(432,500));
        setPreferredSize(new Dimension(432,300));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);


        //Inicializar componentes
        lblTitulo= new JLabel("Nuevo Insumo");
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

        lblPrecioUnitario= new JLabel("Precio Unitario");
        lblPrecioUnitario.setBounds(42,125,101,15);
        cambiarTamanioFuente(lblPrecioUnitario,14,Font.PLAIN);
        add(lblPrecioUnitario);


        txtPrecioUnitario= new JTextField();
        txtPrecioUnitario.setBounds(190,125,194,22);
        cambiarTamanioFuente(txtPrecioUnitario,14,Font.PLAIN);
        add(txtPrecioUnitario);


        lblCantidad= new JLabel("Cantidad");
        lblCantidad.setBounds(42,150,64,15);
        cambiarTamanioFuente(lblCantidad,14,Font.PLAIN);
        add(lblCantidad);

        txtCantidad= new JTextField();
        txtCantidad.setBounds(190,152,194,22);
        cambiarTamanioFuente(txtCantidad,14,Font.PLAIN);
        add(txtCantidad);

        lblFechaCompra= new JLabel("Fecha Compra");
        lblFechaCompra.setBounds(42,175,135,15);
        cambiarTamanioFuente(lblFechaCompra,14,Font.PLAIN);
        add(lblFechaCompra);

        dtFechaCompra= new JDateChooser();
        dtFechaCompra.setBounds(190,175,194,22);
        cambiarTamanioFuente(dtFechaCompra,14,Font.PLAIN);
        add(dtFechaCompra);

        lblFechaVencimiento= new JLabel("Fecha Vencimiento");
        lblFechaVencimiento.setBounds(42,300,145,15);
        cambiarTamanioFuente(lblFechaVencimiento,14,Font.PLAIN);
        add(lblFechaVencimiento);

        dtFechaVencimiento = new JDateChooser();
        dtFechaVencimiento.setBounds(190,300,194,22);
        cambiarTamanioFuente(dtFechaVencimiento,14,Font.PLAIN);
        add(dtFechaVencimiento);

        lblStockMinimo= new JLabel("Stock mínimo");
        lblStockMinimo.setBounds(42,325,135,15);
        cambiarTamanioFuente(lblStockMinimo,14,Font.PLAIN);
        add(lblStockMinimo);

        txtStockMinimo= new JTextField();
        txtStockMinimo.setBounds(190,325,194,22);
        cambiarTamanioFuente(txtStockMinimo,14,Font.PLAIN);
        add(txtStockMinimo);

        btnNuevoInsumo= new JButton("Crear Nuevo Insumo");
        btnNuevoInsumo.setBounds(142,370,144,25);
        cambiarTamanioFuente(btnNuevoInsumo,12,Font.PLAIN);


        btnNuevoInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //miuler
//                Inventario p  = new Inventario(
//                        listaInventario.longitud,
//                        txtNombre.getText(),
//                        cbCategoria.getSelectedItem().toString(),
//                        Integer.parseInt(txtCantidad.getText()),
//                        Integer.parseInt(txtStockMinimo.getText()),
//                        dtFechaCompra.getDate(),
//                        dtFechaVencimiento.getDate()
//                        );
//                if (listaInventario.longitud == 0){
//                    listaInventario.agregarInicio(p);
//                }else {
//                    listaInventario.agregarAlFinal(p);
//                }

                Object[] fila = {
                        listaInventario.longitud,
                        txtNombre.getText(),
                        cbCategoria.getSelectedItem().toString(),
                        Integer.parseInt(txtCantidad.getText()),
                        Integer.parseInt(txtStockMinimo.getText()),
                        dtFechaVencimiento.getDate()
                        };

                agregarFila(fila,tbl,jsp);

                System.out.println();
                System.out.println("Lista Enlazada de Productos");
                listaInventario.mostrarLista();
                System.out.println();
                dispose();
            }
        });
        add(btnNuevoInsumo);
        setVisible(true);

    }


    private void agregarFila(Object[] fila, JTable tbl, JScrollPane jsp) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.addRow(fila);

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



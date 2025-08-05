package com.saborperuano.sistema.Views.Principal.Inventario;

import com.saborperuano.sistema.Models.Insumo;
import utils.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaInventario;
import static utils.Utilidades.cambiarTamanioFuente;
import static utils.Utilidades.filtrarTabla;

public class JPanelInventario extends JPanel {

    //Declarar atributors
    private JLabel lblTitulo;
    private JLabel lblBuscar;
    private JTextField txtBuscar;
    private String[] cabecerasTablas;
    private JTable tblInventario;
    private  JScrollPane jspTblInventario;
    private JButton btnModificarInsumo;
    private JButton btnActualizarTabla;
    private JButton btnAgregarInsumo;
    private JButton btnActualizarInsumo;
    private JButton btnAdicionarMercancia;
    private JButton btnEliminarInsumo;
    private Object[][] dataTable;
    private ErrorLog errorLog;

    //Creas el constructor
    public JPanelInventario() {

        //Para colocarle el tamaño al JPanel
        setMinimumSize(new Dimension(1325,900));
        setPreferredSize(new Dimension(1325,900));
        setLayout(null);

        try {
            errorLog = new ErrorLog("src/main/resources/error.log");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Inicializar componentes
        lblTitulo= new JLabel("Inventario de Insumos");
        lblTitulo.setBounds(443,80,493,40);
        cambiarTamanioFuente(lblTitulo,40,Font.BOLD);
        add(lblTitulo);

        lblBuscar= new JLabel("Buscar");
        lblBuscar.setBounds(494,145,60,18);
        cambiarTamanioFuente(lblBuscar,18,Font.PLAIN);
        add(lblBuscar);

        txtBuscar= new RoundedTextField(30,3,5);
        txtBuscar.setBounds(594,140,289,30);
        cambiarTamanioFuente(txtBuscar,20,Font.PLAIN);
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar,tblInventario);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar,tblInventario);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar,tblInventario);
            }
        });

        //Inicializando cabeceras para la tabla
        //miuler
        cabecerasTablas = new String[]{"Codigo Insumo", "Nombre", "Categoría",
                "Precio", "Cantidad", "Estante"};

        //Preparando la tabla con todo lo necesario
        prepararTablaInventario(cabecerasTablas, new Dimension(1200,450));
        jspTblInventario.setBounds(70,200,1200,450);
        jspTblInventario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(jspTblInventario);



        //Botones
        btnActualizarTabla= new RoundedButton("Actualizar Tabla",3);
        btnActualizarTabla.setBounds(571,660,200,30);
        cambiarTamanioFuente(btnActualizarTabla,14,Font.BOLD);
        btnActualizarTabla.setBackground(Color.PINK);
        btnActualizarTabla.setForeground(Color.black);
        add(btnActualizarTabla);

        btnModificarInsumo= new RoundedButton("Modificar Insumo",3);
        btnModificarInsumo.setBounds(280, 700,200,30);
        cambiarTamanioFuente(btnModificarInsumo,14,Font.BOLD);
        btnModificarInsumo.setBackground(Color.ORANGE);
        btnModificarInsumo.setForeground(Color.black);
        add(btnModificarInsumo);

        btnAgregarInsumo= new RoundedButton("Agregar Nuevo Insumo",3);
        btnAgregarInsumo.setBounds(571,700,200,30);
        cambiarTamanioFuente(btnAgregarInsumo,14,Font.BOLD);
        btnAgregarInsumo.setBackground(Color.green);
        btnAgregarInsumo.setForeground(Color.black);
        add(btnAgregarInsumo);

        btnAdicionarMercancia= new RoundedButton("Adicionar Mercancia",3);
        btnAdicionarMercancia.setBounds(850,700,200,30);
        cambiarTamanioFuente(btnAdicionarMercancia,14,Font.BOLD);
        btnAdicionarMercancia.setBackground(Color.cyan);
        btnAdicionarMercancia.setForeground(Color.black);
        add(btnAdicionarMercancia);


        btnEliminarInsumo= new RoundedButton("Eliminar Insumo",3);
        btnEliminarInsumo.setBounds(571,740,200,30);
        cambiarTamanioFuente(btnEliminarInsumo,14,Font.BOLD);
        btnEliminarInsumo.setBackground(Color.red);
        btnEliminarInsumo.setForeground(Color.white);


        btnAgregarInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogNuevoInsumo jDialogNuevoInsumo = new JDialogNuevoInsumo(
                        listaInventario,jspTblInventario,tblInventario);
                //miuler

            }
        });


        btnModificarInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogModificarInsumo jDialogModificarInsumo = new JDialogModificarInsumo(listaInventario);
            }
        });

        btnEliminarInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogEliminarInsumo jDialogEliminarInsumo = new JDialogEliminarInsumo(tblInventario);
            }
        });

        btnAdicionarMercancia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogAdicionarInsumo jDialogAdicionarInsumo = new JDialogAdicionarInsumo();
            }
        });
        btnActualizarTabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Recargar data
            }
        });


        //miuler
        add(txtBuscar);
        add(btnAgregarInsumo);
        add(btnEliminarInsumo);
        add(btnActualizarTabla);
        add(btnModificarInsumo);
        add(btnAdicionarMercancia);
    }


    private void prepararTablaInventario(String[] cabecerasTablas, Dimension tamanioTabla){
        tblInventario = new JTable() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        //Personalizando las celdas y agregando las cabeceras a la tabla
//        construirTabla(cabecerasTablas, tblInventario);

        //Estableciendo propiedades a la tabla
        tblInventario.setPreferredSize(tamanioTabla);
        tblInventario.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        tblInventario.setOpaque(false);

        //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
        jspTblInventario = new JScrollPane(tblInventario);

        //Aplicando propiedades al JScrollPane de la tabla
        jspTblInventario.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        //Cambiando colores del fondo y de la barra de scroll
        jspTblInventario.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
        jspTblInventario.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical

    }




    private void construirTabla(String[] titulos, Object[][] data, JTable table) {

        ModeloTabla modelo = new ModeloTabla(data, titulos);

        tblInventario.setDefaultRenderer(Object.class, new GestionCeldas());
        tblInventario.setForeground(Color.white);
        tblInventario.setModel(modelo);

        tblInventario.getTableHeader().setReorderingAllowed(false);
        tblInventario.getTableHeader().setResizingAllowed(false);
        tblInventario.setRowHeight(25);
        tblInventario.setGridColor(new Color(0, 0, 0));


        tblInventario.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblInventario.getColumnModel().getColumn(1).setPreferredWidth(300);
        tblInventario.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblInventario.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblInventario.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblInventario.getColumnModel().getColumn(5).setPreferredWidth(200);


        //personaliza el encabezado
        JTableHeader jtableHeader = table.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        cambiarTamanioFuente(jtableHeader,20,Font.BOLD);
        table.setTableHeader(jtableHeader);

    }
    //miuler

    private void ajustarAnchoTabla(JTable tbl) {
        int alturaPreferida = jspTblInventario.getHeight();
        int alturaActual =  tblInventario.getRowCount()*tblInventario.getRowHeight();
        if (alturaActual >= alturaPreferida){
            tblInventario.setFillsViewportHeight(true);
            tblInventario.setPreferredSize(new Dimension(tblInventario.getPreferredSize().width,alturaActual));
            tblInventario.revalidate();
            tblInventario.repaint();
            jspTblInventario.setPreferredSize(tblInventario.getPreferredSize());
            jspTblInventario.revalidate();
            jspTblInventario.repaint();
        }
    }
    private void agregarFila(Object[] fila, JTable tbl) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tblInventario.getModel();
        model.addRow(fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl);
    }

}

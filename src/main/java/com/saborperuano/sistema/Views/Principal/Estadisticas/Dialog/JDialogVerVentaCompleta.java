package com.saborperuano.sistema.Views.Principal.Estadisticas.Dialog;

import com.saborperuano.sistema.Models.DTO.DetalleVentaDTO;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import utils.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogVerVentaCompleta extends JDialog {

    private JLabel lblTituloVenta;
    private String[] cabecerasTablaVenta;

    private JTable tblVentaActual;
    private ModeloTabla modelo;
    private JScrollPane jspTblActual;

    public JDialogVerVentaCompleta(int idVenta) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(1280,700));
        setPreferredSize(new Dimension(1280,700));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes
        lblTituloVenta= new JLabel("Venta #"+idVenta);
        lblTituloVenta.setBounds(600,25,210,30);
        cambiarTamanioFuente(lblTituloVenta,28,Font.BOLD);
        add(lblTituloVenta);

        String[] cabecerasTablas = new String[]{"N° Producto",  "Código del Producto",
                "Nombre del Producto","Categoría",
                "Cantidad","Precio Unitario","Precio Total", "Descuento","Importe a Pagar"};

        preparaTabla(cabecerasTablas, new Dimension(1070,450));
        jspTblActual.setBounds(37,76,1155,440);
        jspTblActual.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(jspTblActual);

        ListaEnlazadaGenerica<DetalleVentaDTO> listaDetalles = FrmPrincipal.detalleVentaController.listar(idVenta);

        listaDetalles.mostrarLista();
        Utilidades.llenarJTable(listaDetalles,tblVentaActual);

        setVisible(true);
    }

    private void preparaTabla(String[] cabecerasTablas, Dimension tamanioTabla){

        //Inicializando la matriz de filas de la tabla
        Object[][] dataTable = new Object[0][cabecerasTablas.length];

        //Creando la tabla sobreescribiendo el método que colorea el componente para personalizarlo
        tblVentaActual = new JTable() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        //Personalizando las celdas y agregando las cabeceras a la tabla
        construirTablaVacia(cabecerasTablas, tblVentaActual);

        //Estableciendo propiedades a la tabla
        tblVentaActual.setMinimumSize(tamanioTabla);
        tblVentaActual.setPreferredSize(tamanioTabla);
        tblVentaActual.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        tblVentaActual.setOpaque(false);
        tblVentaActual.setPreferredScrollableViewportSize(null);
        tblVentaActual.setFillsViewportHeight(true);

        //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
        jspTblActual = new JScrollPane(tblVentaActual);

        //Aplicando propiedades al JScrollPane de la tabla
        jspTblActual.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        //Cambiando colores del fondo y de la barra de scroll
        jspTblActual.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
        jspTblActual.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical

    }

    private void construirTablaVacia(String[] titulos, JTable table) {

        Object[][] data = new Object[0][0];
        modelo=new ModeloTabla(data, titulos);
        if (table == tblVentaActual) {
            //se asigna el modelo a la tabla

            tblVentaActual.setDefaultRenderer(Object.class, new GestionCeldas());
            tblVentaActual.setForeground(Color.white);
            tblVentaActual.setModel(modelo);

            tblVentaActual.getTableHeader().setReorderingAllowed(false);
            tblVentaActual.getTableHeader().setResizingAllowed(false);
            tblVentaActual.setRowHeight(25);
            tblVentaActual.setGridColor(new Color(0, 0, 0));


            tblVentaActual.getColumnModel().getColumn(Utilidades.NroProducto).setPreferredWidth(90);
            tblVentaActual.getColumnModel().getColumn(Utilidades.CodigoProducto).setPreferredWidth(150);
            tblVentaActual.getColumnModel().getColumn(Utilidades.NombreProducto).setPreferredWidth(230);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Categoria).setPreferredWidth(100);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Cantidad).setPreferredWidth(80);
            tblVentaActual.getColumnModel().getColumn(Utilidades.PrecioUnitario).setPreferredWidth(120);
            tblVentaActual.getColumnModel().getColumn(Utilidades.PrecioTotal).setPreferredWidth(100);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Descuento).setPreferredWidth(100);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Importe).setPreferredWidth(120);


        }

        //personaliza el encabezado
        JTableHeader jtableHeader = table.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        cambiarTamanioFuente(jtableHeader,20,Font.BOLD);
        table.setTableHeader(jtableHeader);


    }
}

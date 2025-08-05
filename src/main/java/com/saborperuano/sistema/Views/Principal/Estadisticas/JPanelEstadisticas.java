package com.saborperuano.sistema.Views.Principal.Estadisticas;

import com.saborperuano.sistema.DAO.ConexionBD;
import com.saborperuano.sistema.Views.Principal.Estadisticas.Dialog.JDialogEliminarVenta;
import com.saborperuano.sistema.Views.Principal.Estadisticas.Dialog.JDialogVerVentaCompleta;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaVentas;
import static utils.Utilidades.cambiarTamanioFuente;

public class JPanelEstadisticas extends JPanel {

    //Declarar atributos

    private JLabel lblNumerodeVenta;
    private JTextField txtNumerodeVenta;
    private JLabel lblCantidaddeProducto;
    private JTextField txtCantidaddeProducto;
    private JLabel lblMetododePago;
    private JTextField txtMetododePago;

    private JLabel lblMontoTotal;
    private JTextField txtMontoTotal;
    private JLabel lblDescuento;
    private JTextField txtDescuento;
    private JLabel lblMontoFinal;
    private JTextField txtMontoFinal;
    private JLabel lblGanancia;
    private JTextField txtGanancia;
    private JPanel jpContenido;
    private JPanel jpHistorialVentas;
    private JButton btnVerventaCompleta;
    private JButton btnReimprimirVoucher;
    private JButton btnEliminarVenta;
    private JButton btnGenerarreporte;
    private JCalendar JCalendar;

    boolean tituloCapturado;
    String[] titulosCapturados;


    private Object[] dataTable;
    private ModeloTabla modelo;
    private int filasTabla;
    private int columnasTabla;
    private String[] cabecerasTablas;

    private Dimension []tamanioTabla;
    private JTable prepararTablaFormatoLista;
    private JScrollPane jspTblLista;
    private JScrollPane jspTblFormatoLista;
    private JLabel lblTituloPanel;
    private static JTable tblVentasFormatoLista;
    private JDateChooser dateChooser;
    private JDateChooser jCalendarVentasInicio;
    private JDateChooser jCalendarVentasFin;
    private JDateChooser jCalendarComprasInicio;
    private JDateChooser jCalendarComprasFin;
    private JLabel lblRangoFechasVentas;
    private JLabel lblRangoFechasVentasA;
    private JLabel lblRangoFechasCompras;
    private JLabel lblRangoFechasComprasA;
    private JButton btnFiltrarVentas;
    private JButton btnFiltrarCompras;
    private JLabel lblOrdenarVentas;
    private JLabel lblOrdenarCompras;
    private JComboBox<String> cbOrdenarVentas;
    private JComboBox<String> cbOrdenarCompras;
    private ButtonGroup btnGrpOrdenVentas;
    private JRadioButton rbtnAscendenteVentas;
    private JRadioButton rbtnDescendenteVentas;
    private JButton btnGenerarReporteVenta;
    private static JScrollPane jspTblHistorialVentas;
    private static JTable tblHistorialVentas;
    private ErrorLog errorLog;
    private JButton btnLimpiarFiltro;
    private JButton btnActualizarTabla;

    //Declarar Constructor
    public JPanelEstadisticas(){
        //1. Inicializar los componentes

        //Inicializar de afuera hacia adentro
        setMinimumSize(new Dimension(1325, 900));
        setPreferredSize(new Dimension(1325, 900));
        setLayout(null);

        try {
            errorLog = new ErrorLog("src/main/resources/error.log");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //JLabel para el Titulo del JPanel
        lblTituloPanel = new JLabel("Historial de Ventas");
        lblTituloPanel.setBounds(450,60,500,55);
        cambiarTamanioFuente(lblTituloPanel, 55, Font.BOLD);
        lblTituloPanel.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTituloPanel);





//      JPanel para el contenido
        jpContenido = new JPanel();
        jpContenido.setBounds(54,194,1225,680);
        jpContenido.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpContenido.setLayout(null);
        add(jpContenido);




        // JPanel para el historial compras de insumos
        jpHistorialVentas= new JPanel();
        jpHistorialVentas.setBounds(0,0,1225,680);
        jpHistorialVentas.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpHistorialVentas.setBackground(Color.white);
        jpHistorialVentas.setLayout(null);
        String[] cabecerasVentas = {"Nro Venta","Empleado","Tipo de Comprobante","Fecha de Venta","Cant. de Productos","Descuento Total","Monto Total","Estado"};
        inicializarPanel(listaVentas,cabecerasVentas,true);


        jpContenido.add(jpHistorialVentas);
        jpContenido.revalidate();
        jpContenido.repaint();




    }

    private void bloquearFechasPosteriores() {
        Date fechaSeleccionada = dateChooser.getDate();
        Date fechaActual = new Date();

        if (fechaSeleccionada != null && fechaSeleccionada.after(fechaActual)) {
            dateChooser.setDate(fechaActual);
        }
    }

    //region[Método para Inicializar componentes de Ventas en Espera]
    private void inicializarPanel(ListaEnlazadaGenerica listaEnlazada, String[] cabecerasTablas, boolean esVentas) {

        if (esVentas){
            //Barra de Búsqueda de Ventas
            //JLabel de titulo para el codigo o producto
            lblRangoFechasVentas = new JLabel("Rango de Fechas  De:");
            lblRangoFechasVentas.setBounds(189,29,170,20);
            cambiarTamanioFuente(lblRangoFechasVentas, 16, Font.PLAIN);
            jpHistorialVentas.add(lblRangoFechasVentas);

            //JDateChooser para ingreso de fecha de inicio
            jCalendarVentasInicio = new JDateChooser();
            jCalendarVentasInicio.setBounds(390,23,145,30);
            jCalendarVentasInicio.setMaxSelectableDate(new Date());
            cambiarTamanioFuente(jCalendarVentasInicio, 16, Font.PLAIN);
            jpHistorialVentas.add(jCalendarVentasInicio);

            //JLabel de titulo para el codigo o producto
            lblRangoFechasVentasA  = new JLabel("A");
            lblRangoFechasVentasA.setBounds(574,29, 10,20);
            cambiarTamanioFuente(lblRangoFechasVentasA, 16, Font.PLAIN);
            jpHistorialVentas.add(lblRangoFechasVentasA);

            //JDateChooser para ingreso de fecha de inicio
            jCalendarVentasFin = new JDateChooser();
            jCalendarVentasFin.setBounds(613,23,145,30);
            jCalendarVentasFin.setMaxSelectableDate(new Date());
            cambiarTamanioFuente(jCalendarVentasFin, 16, Font.PLAIN);
            jpHistorialVentas.add(jCalendarVentasFin);

            //Inicializar componentes
            btnFiltrarVentas = new RoundedButton("Filtrar", 3);
            btnFiltrarVentas.setBounds(791,20,160, 35);
            cambiarTamanioFuente(btnFiltrarVentas, 16, Font.BOLD);
            btnFiltrarVentas.setBackground(new Color(0xC4FFA0));
            btnFiltrarVentas.setForeground(Color.black);

            btnLimpiarFiltro = new RoundedButton("Limpiar Filtro", 3);
            btnLimpiarFiltro.setBounds(791,70,160, 35);
            cambiarTamanioFuente(btnLimpiarFiltro, 16, Font.BOLD);
            btnLimpiarFiltro.setBackground(new Color(0xFF9D1A));
            btnLimpiarFiltro.setForeground(Color.black);

            //Preparando la tabla con todo lo necesario
            preparaTabla(cabecerasTablas, new Dimension(1150,387),0);

            //Creando filtro a tabla
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(tblHistorialVentas.getModel());

            // Personalizar el ordenamiento para la columna del contador
            sorter.setComparator(0, new CustomComparator());

            //Aplicando filtro a tabla
            tblHistorialVentas.setRowSorter(sorter);

            btnFiltrarVentas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (validarFechasSeleccionadas(jCalendarVentasInicio,jCalendarVentasFin)){
                        filtrarTablaPorFechas(tblHistorialVentas,jCalendarVentasInicio,jCalendarVentasFin,sorter);
                    }
                }
            });

            btnLimpiarFiltro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarFiltro(sorter,jCalendarVentasInicio,jCalendarVentasFin);
                }
            });



            //Alimentando la tabla de los datos extraídos de la BD
            Utilidades.llenarJTable(listaVentas,tblHistorialVentas);


            //Posicionando el container Scrolleable de la tabla en el JPanel
            jspTblHistorialVentas.setBounds(50,124, tblHistorialVentas.getPreferredSize().width, tblHistorialVentas.getPreferredSize().height);

            //Ajustanco el largo de la tabla por la inserción de los registros
            ajustarAnchoTabla(tblHistorialVentas,jspTblHistorialVentas);


            //Inicializar componentes
            btnVerventaCompleta = new RoundedButton("Ver Venta Completa", 3);
            btnVerventaCompleta.setBounds(50,532,230, 35);
            cambiarTamanioFuente(btnVerventaCompleta, 16, Font.BOLD);
            btnVerventaCompleta.setBackground(new Color(0xFFE600));
            btnVerventaCompleta.setForeground(Color.black);

            btnVerventaCompleta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (tblHistorialVentas.getSelectedRow()!= -1){
                        int idVenta = listaVentas.obtenerObjetoPorCampo(listaVentas,"nroVenta",Integer.parseInt(String.valueOf(modelo.getValueAt(tblHistorialVentas.getSelectedRow(),0)))).getNroVenta();
                        System.out.println("idVenta = " + idVenta);
                        //Crear dialog de Venta Completa para ver detalles de venta
                        JDialogVerVentaCompleta jDialogVerVentaCompleta = new JDialogVerVentaCompleta(idVenta);
                    }else {
                        JOptionPane.showMessageDialog(null,"Debe seleccionar una venta");
                    }

                }
            });

            btnReimprimirVoucher = new RoundedButton("Reimprimir Váucher", 3);
            btnReimprimirVoucher.setBounds(310,532,230  , 35);
            cambiarTamanioFuente(btnReimprimirVoucher, 16, Font.BOLD);
            btnReimprimirVoucher.setBackground(new Color(0xC4FFA0));
            btnReimprimirVoucher.setForeground(Color.black);

            btnEliminarVenta = new RoundedButton("Eliminar Venta", 3);
            btnEliminarVenta.setBounds(570,532,230, 35);
            cambiarTamanioFuente(btnEliminarVenta, 16, Font.BOLD);
            btnEliminarVenta.setBackground(new Color(0xFF0000));
            btnEliminarVenta.setForeground(Color.black);
            btnEliminarVenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tblHistorialVentas.getSelectedRow()!= -1){
                        int idVenta = listaVentas.obtenerObjetoPorCampo(listaVentas,"nroVenta",Integer.parseInt(String.valueOf(modelo.getValueAt(tblHistorialVentas.getSelectedRow(),0)))).getNroVenta();
                        //Crear dialog de Venta Completa para ver detalles de venta
                        JDialogEliminarVenta jDialogVerVentaCompleta = new JDialogEliminarVenta(tblHistorialVentas,idVenta);
                    }else {
                        JOptionPane.showMessageDialog(null,"Debe seleccionar una venta");
                    }
                }
            });


            btnActualizarTabla = new RoundedButton("Actualizar Tabla", 3);
            btnActualizarTabla.setBounds(50,577,230, 35);
            cambiarTamanioFuente(btnActualizarTabla, 16, Font.BOLD);
            btnActualizarTabla.setBackground(new Color(0x74EEBF));
            btnActualizarTabla.setForeground(Color.black);
            btnActualizarTabla.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listaVentas = FrmPrincipal.ventaController.listar();
                    Utilidades.llenarJTable(listaVentas,tblHistorialVentas);
                    ajustarAnchoTabla(tblHistorialVentas,jspTblHistorialVentas);
                }
            });


            btnGenerarReporteVenta = new RoundedButton("Generar Reporte PDF", 3);
            btnGenerarReporteVenta.setBounds(970,521,230,50);
            cambiarTamanioFuente(btnGenerarReporteVenta, 16, Font.BOLD);
            btnGenerarReporteVenta.setBackground(new Color(0xFFB23F));
            btnGenerarReporteVenta.setForeground(Color.black);

            btnGenerarReporteVenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        Connection con = ConexionBD.getConnection();

                        JasperReport reporte = null;

                        HashMap<String, Object> parametros = new HashMap();

                        Date fechaInicio = jCalendarVentasInicio.getDate();
                        Date fechaFin = jCalendarVentasFin.getDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String fechaInicioFormated = sdf.format(fechaInicio);
                        String fechaFinFormated = sdf.format(fechaFin);

                        InputStream logoInputStream = this.getClass().getClassLoader().getResourceAsStream("img/frmLogin/logoEmpresa.png");


                        parametros.put("fechaInicio",fechaInicioFormated);
                        parametros.put("fechaFin",fechaFinFormated);
                        parametros.put("logoEmpresa",logoInputStream);

                        // Obtiene la ruta del directorio "resources"
                        String resourcesPath = Draft.class.getClassLoader().getResource("pdf/reporte.jasper").getPath();

                        File file = new File(resourcesPath);


                        reporte = (JasperReport) JRLoader.loadObject(file);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parametros,con);
                        JasperViewer view = new JasperViewer(jasperPrint,false);
                        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        view.setVisible(true);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            //Agregando la tabla al JPanel de Ventas en Espera
            jpHistorialVentas.add(jspTblHistorialVentas);
            jpHistorialVentas.add(btnFiltrarVentas);
            jpHistorialVentas.add(btnLimpiarFiltro);
            jpHistorialVentas.add(btnVerventaCompleta);
            jpHistorialVentas.add(btnEliminarVenta);
            jpHistorialVentas.add(btnActualizarTabla);
            jpHistorialVentas.add(btnGenerarReporteVenta);



        }



    }

    //endregion

    // Clase auxiliar para personalizar el ordenamiento del contador
    private static class CustomComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer num1, Integer num2) {
            return Integer.compare(num1, num2);
        }
    }

    // Método para limpiar el filtro y mostrar todas las filas nuevamente
    private void limpiarFiltro(TableRowSorter sorter,JDateChooser inicio, JDateChooser fin) {
        sorter.setRowFilter(null);
        inicio.setDate(null);
        fin.setDate(null);
    }
    private boolean validarFechasSeleccionadas(JDateChooser inicio, JDateChooser fin) {
        // Obtener las fechas de los JDateChooser
        Date fechaInicio = inicio.getDate();
        Date fechaFin = fin.getDate();

        // Verificar si ambas fechas están seleccionadas
        if (fechaInicio == null || fechaFin == null) {
            // Lanzamos un JOptionPane con el mensaje de error
            JOptionPane.showMessageDialog(this, "Por favor, seleccione ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Si ambas fechas están seleccionadas, retornar true indicando que la validación es correcta
        return true;
    }

    private void filtrarTablaPorFechas(JTable tbl,JDateChooser inicio, JDateChooser fin, TableRowSorter rowSorter) {
        // Obtener las fechas seleccionadas en los dateChooser
        Date fechaInicio = inicio.getDate();
        Date fechaFin = fin.getDate();

        if (fechaInicio == null || fechaFin == null) {
            // Si alguna de las fechas es nula, no se realiza el filtro
            rowSorter.setRowFilter(null);
        } else {
            // Crear el RowFilter para filtrar por el rango de fechas
            RowFilter<DefaultTableModel, Integer> filter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int row = entry.getIdentifier();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date fechaRegistro = null;
                    try {
                        fechaRegistro = dateFormat.parse((String) entry.getModel().getValueAt(row, 3));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    // Obtener la parte de la fecha sin la hora, minutos, segundos y milisegundos
                    Calendar calFechaRegistro = Calendar.getInstance();
                    calFechaRegistro.setTime(fechaRegistro);
                    calFechaRegistro.set(Calendar.HOUR_OF_DAY, 0);
                    calFechaRegistro.set(Calendar.MINUTE, 0);
                    calFechaRegistro.set(Calendar.SECOND, 0);
                    calFechaRegistro.set(Calendar.MILLISECOND, 0);

                    // Obtener la parte de la fecha sin la hora, minutos, segundos y milisegundos para la fecha de inicio
                    Calendar calFechaInicio = Calendar.getInstance();
                    calFechaInicio.setTime(fechaInicio);
                    calFechaInicio.set(Calendar.HOUR_OF_DAY, 0);
                    calFechaInicio.set(Calendar.MINUTE, 0);
                    calFechaInicio.set(Calendar.SECOND, 0);
                    calFechaInicio.set(Calendar.MILLISECOND, 0);

                    // Obtener la parte de la fecha sin la hora, minutos, segundos y milisegundos para la fecha de fin
                    Calendar calFechaFin = Calendar.getInstance();
                    calFechaFin.setTime(fechaFin);
                    calFechaFin.set(Calendar.HOUR_OF_DAY, 0);
                    calFechaFin.set(Calendar.MINUTE, 0);
                    calFechaFin.set(Calendar.SECOND, 0);
                    calFechaFin.set(Calendar.MILLISECOND, 0);

                    // Verificar si la fecha está dentro del rango seleccionado o es igual al día seleccionado
                    return (calFechaRegistro.after(calFechaInicio) || calFechaRegistro.equals(calFechaInicio)) &&
                            calFechaRegistro.before(calFechaFin) || calFechaRegistro.equals(calFechaFin);
                }

            };

            // Aplicar el RowFilter al TableRowSorter
            rowSorter.setRowFilter(filter);
        }
    }

    public static void recargarVentas(){
        Utilidades.llenarJTable(listaVentas, tblHistorialVentas);
        ajustarAnchoTabla(tblHistorialVentas,jspTblHistorialVentas);
    }



    private void preparaTabla(String[] cabecerasTablas, Dimension tamanioTabla, int tblIndex){

        if (tblIndex == 0){
            tblHistorialVentas = new JTable() {
                @Override
                public void paintComponent(Graphics g) {
                    g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };

            //Personalizando las celdas y agregando las cabeceras a la tabla
            construirTabla(cabecerasTablas, tblHistorialVentas,0);

            //Estableciendo propiedades a la tabla
            tblHistorialVentas.setPreferredSize(tamanioTabla);
            tblHistorialVentas.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
            tblHistorialVentas.setOpaque(false);
            tblHistorialVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
            jspTblHistorialVentas = new JScrollPane(tblHistorialVentas);


            //Aplicando propiedades al JScrollPane de la tabla
            jspTblHistorialVentas.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
            //Cambiando colores del fondo y de la barra de scroll
            jspTblHistorialVentas.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
            jspTblHistorialVentas.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical
        }else {

        }

    }



    private void construirTabla(String[] titulos, JTable table, int tblIndex) {

        Object[][] data = new Object[0][0];
        modelo=new ModeloTabla(data, titulos);

        table.setDefaultRenderer(Object.class, new GestionCeldas());
        table.setForeground(Color.white);
        table.setModel(modelo);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowHeight(25);
        table.setGridColor(new Color(0, 0, 0));

        //Si tblIndex es 0, significa que es la tabla de historial de ventas, sino ,es el de compras
        if (tblIndex == 0){

            table.getColumnModel().getColumn(0).setMinWidth(80);
            table.getColumnModel().getColumn(1).setMinWidth(200);
            table.getColumnModel().getColumn(2).setMinWidth(140);
            table.getColumnModel().getColumn(3).setMinWidth(180);
            table.getColumnModel().getColumn(4).setMinWidth(130);
            table.getColumnModel().getColumn(5).setMinWidth(100);
            table.getColumnModel().getColumn(6).setMinWidth(100);
            table.getColumnModel().getColumn(7).setMinWidth(80);

        }else {
            table.getColumnModel().getColumn(0).setMinWidth(80);
            table.getColumnModel().getColumn(1).setMinWidth(200);
            table.getColumnModel().getColumn(2).setMinWidth(140);
            table.getColumnModel().getColumn(3).setMinWidth(180);
            table.getColumnModel().getColumn(4).setMinWidth(130);
            table.getColumnModel().getColumn(5).setMinWidth(100);
            table.getColumnModel().getColumn(6).setMinWidth(100);
            table.getColumnModel().getColumn(7).setMinWidth(80);
        }


        //personaliza el encabezado
        JTableHeader jtableHeader = table.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        cambiarTamanioFuente(jtableHeader,20,Font.BOLD);


    }


    private static void ajustarAnchoTabla(JTable tbl, JScrollPane jsp) {
        int alturaPreferida = jsp.getHeight();
        int alturaActual =  tbl.getRowCount() * tbl.getRowHeight() + 50;
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

    // Método para agregar una nueva fila a la tabla



}




















package com.saborperuano.sistema.Views.Principal.Ventas;

import com.saborperuano.sistema.DAO.ConexionBD;
import com.saborperuano.sistema.Models.*;
import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.DTO.DetalleVentaDTO;
import com.saborperuano.sistema.Models.DTO.MenuDTO;
import com.saborperuano.sistema.Models.DTO.VentaDTO;
import com.saborperuano.sistema.Models.Enums.Estado;
import com.saborperuano.sistema.Models.Enums.TipoComprobante;
import com.saborperuano.sistema.Views.Principal.Estadisticas.Draft;
import com.saborperuano.sistema.Views.Principal.Estadisticas.JPanelEstadisticas;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import com.saborperuano.sistema.Views.Principal.MenuCarta.JPanelMenuCarta;
import com.saborperuano.sistema.Views.Principal.Ventas.Dialog.JDialogEliminarVenta;
import com.saborperuano.sistema.Views.Principal.Ventas.Dialog.JDialogNuevaVentaEspera;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.joda.time.DateTime;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.*;
import static utils.Utilidades.cambiarTamanioFuente;
import static utils.Utilidades.llenarJTable;

public class JPanelVentas extends JPanel {

    // region[Atributos]
    private JLabel lblTituloPanel;
    private JPanel jpContenido;
    private JPanel jpVentaActual;
    private JPanel jpVentasEnEspera;
    private JLabel lblTituloNombreProducto;
    private JTextField txtNombreProducto;
    private JLabel lblTituloCantidad;
    private JTextField txtCantidad;
    private JLabel lblTituloDescuentoProducto;
    private JTextField txtDescuentoPorcentajeProducto;
    private JLabel lblPorcentajeSimboloProducto;
    private JTextField txtDescuentoSolesProducto;
    private JLabel lblSolesSimbolo;
    private JButton btnAgregarProducto;
    private JButton btnLimpiarEntradas;
    private JButton btnVentaActual;
    private JButton btnVentaEnEspera;
    private JScrollPane jspTblActual;
    private JScrollPane jspTblVentasEnEspera;
    private JTable tblVentaActual;
    private JTable tblVentasEnEspera;
    private DefaultTableModel dtmTblActual;
    private JButton btnImprimirVaucher;
    private JButton btnCancelarVenta;
    private JButton btnAplicarDescuento;
    private JLabel lblMontoTotal;
    private JLabel lblDescuentoTotal;
    private JLabel lblMontoAPagar;
    private JButton btnRealizarVenta;
    private ModeloTabla modelo;

    private int filasTabla;
    private int columnasTabla;
    private JLabel lblTituloDescuentoTotal;
    private JTextField txtDescuentoPorcentajeTotal;
    private JLabel lblPorcentajeSimboloTotal;
    private JTextField txtDescuentoSolesTotal;
    private String[] cabecerasTablas;
    private Object[] dataTable;

    private JLabel lblTituloBusquedaVentas;
    private JTextField txtBusquedaVentas;
    private Stack<VentasEspera> ventasEspera;
    public JList listaSugerencias;
    float precioTotal = 0;
    float descuentoTotal=0;
    float importePagar = 0;
    boolean tituloCapturado;
    String[] titulosCapturados;
    private ErrorLog errorLog;
    private int ventasEsperando;
    private static List<String> nombres;
    private DefaultTableModel modeloVentaActual;
    private JButton btnEditarProducto;
    private JButton btnEliminarProducto;

    //endregion

//TODO VALIDAR VENTA EN ESPERA
    //region [Constructor]

    public JPanelVentas(JFrame frame) {
        try {
            errorLog = new ErrorLog("src/main/resources/error.log");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //region [Inicializando Componentes]
        setPreferredSize(new Dimension(1325,900));

        //Inicializando Fuente para todos los componentes
        Font fuente = getFont();

        //JLabel para el Titulo del JPanel
        lblTituloPanel = new JLabel("Venta de Productos");
        lblTituloPanel.setBounds(496,59,308,35);
        lblTituloPanel.setFont(fuente);
        cambiarTamanioFuente(lblTituloPanel, 30, Font.BOLD);
        lblTituloPanel.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTituloPanel);




        //JPanel para el contenido
        jpContenido = new JPanel();
        jpContenido.setBounds(36,152,1245,738);
        jpContenido.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpContenido.setLayout(null);
        jpContenido.setBackground(Color.white);
        add(jpContenido);


        //JPanel para la pestaña de Ventas en Espera
        jpVentasEnEspera = new JPanel();
        jpVentasEnEspera.setBounds(0,0,1245,738);
        jpVentasEnEspera.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpVentasEnEspera.setLayout(null);
        jpVentasEnEspera.setBackground(Color.white);
        inicializarPanelVentasEnEspera();

        //JPanel para la pestaña de Venta Actual
        jpVentaActual = new JPanel();
        jpVentaActual.setBounds(0,0,1245,738);
        jpVentaActual.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpVentaActual.setLayout(null);
        jpVentaActual.setBackground(Color.white);
        inicializarPanelVentaActual();


        //Mostrar en el JPanel de Contenido la pestaña de Venta Actual por defecto
        jpContenido.add(jpVentaActual);
        jpContenido.revalidate();
        jpContenido.repaint();


        //endregion

        //region [Agregando eventos a los botones]

        //region [Eventos para Ventana Venta Actual]


        //Agregando evento de acción para el botón Agregar Producto para enlistarlo en la tabla
        btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //Verificar que los campos estén correctamente llenados
                if (validarCamposPlatillos()) {

                    //Crear una venta e ingresarla en la lista enlazada de ventas
                    double precioTotalDetalle = 0, descuentoTotalDetalle = 0, importePagarDetalle = 0;

                    if (tblVentaActual.getRowCount() == 0) {


                        String nombreEmpleado = String.format("%s %s %s", FrmPrincipal.empleadoActual.getNombre(),
                                FrmPrincipal.empleadoActual.getApellidoPaterno(), FrmPrincipal.empleadoActual.getApellidoMaterno());

                        // Obtener la fecha actual con Joda-Time
                        DateTime fechaActual = DateTime.now();

                        // Extraer la hora exacta
                        int hora = fechaActual.getHourOfDay();
                        int minutos = fechaActual.getMinuteOfHour();
                        int segundos = fechaActual.getSecondOfMinute();

                        // Crear una nueva fecha con la misma fecha que la original pero con hora exacta
                        DateTime fechaConHoraExacta = fechaActual.withTime(hora, minutos, segundos, 0);

                        int nroVenta = -1;
                        if (listaVentas.longitud == 0) {
                            nroVenta = 1;
                        } else {
                            nroVenta = listaVentas.obtenerUltimoObjeto().getNroVenta() + 1;
                        }

                        ventaActual = new VentaDTO(nroVenta,
                                nombreEmpleado,
                                TipoComprobante.Boleta,
                                fechaConHoraExacta,
                                0,
                                0,
                                0,
                                "");

                        //Agregando venta a la lista
                        if (listaVentas.longitud == 0) {
                            listaVentas.agregarInicio(ventaActual);
                        } else {
                            listaVentas.agregarAlFinal(ventaActual);
                        }

                    }

                    //Verificar la tabla
                     modeloVentaActual = (DefaultTableModel) tblVentaActual.getModel();

                    int cantidad = Integer.parseInt(txtCantidad.getText());


                    if (modeloVentaActual.getRowCount() == 0){
                        listaDetallesVenta = new ListaEnlazadaGenerica<>();

                        MenuDTO menuDTO = FrmPrincipal.listaMenu.obtenerObjetoPorCampo(FrmPrincipal.listaMenu,
                                "Nombre",txtNombreProducto.getText());
                        DetalleVentaDTO nuevoDetalleVenta =  null;

                        if (menuDTO != null){

                            precioTotalDetalle = cantidad * menuDTO.getPrecioUnitario();

                            descuentoTotalDetalle = hallarDescuentoTotalPorPlatillo(menuDTO.getPrecioUnitario(), cantidad);

                            importePagarDetalle = precioTotalDetalle - descuentoTotalDetalle;

                            nuevoDetalleVenta = new DetalleVentaDTO(
                                    tblVentaActual.getRowCount()+1,
                                    menuDTO.getCodigo(),
                                    menuDTO.getNombre(),
                                    menuDTO.getCategoria().name(),
                                    Integer.parseInt(txtCantidad.getText()),
                                    menuDTO.getPrecioUnitario(),
                                    precioTotalDetalle,
                                    descuentoTotalDetalle,
                                    importePagarDetalle);



                        }else {
                            CartaDTO cartaDTO = FrmPrincipal.listaCarta.obtenerObjetoPorCampo(FrmPrincipal.listaCarta,
                                    "Nombre",txtNombreProducto.getText());
                            if (cartaDTO != null){
                                precioTotalDetalle = cantidad * cartaDTO.getPrecioUnitario();

                                descuentoTotalDetalle = hallarDescuentoTotalPorPlatillo(cartaDTO.getPrecioUnitario(), cantidad);

                                importePagarDetalle = precioTotalDetalle - descuentoTotalDetalle;

                                nuevoDetalleVenta = new DetalleVentaDTO(
                                        tblVentaActual.getRowCount()+1,
                                        cartaDTO.getCodigo(),
                                        cartaDTO.getNombre(),
                                        cartaDTO.getCategoria().name(),
                                        Integer.parseInt(txtCantidad.getText()),
                                        cartaDTO.getPrecioUnitario(),
                                        precioTotalDetalle,
                                        descuentoTotalDetalle,
                                        importePagarDetalle);

                            }else {
                                JOptionPane.showMessageDialog(null,"Ocurrió un error al crear" +
                                        " el detalle de venta para la tabla.");
                            }
                        }

                        //Agregando nodo a la lista detalles de venta
                        listaDetallesVenta.agregarInicio(nuevoDetalleVenta);


                        //Mostrando nodo en la tabla
                        String[] fila = {
                                String.valueOf(nuevoDetalleVenta.getCodigoDetalleVenta()),
                                String.valueOf(nuevoDetalleVenta.getCodigoProducto()),
                                nuevoDetalleVenta.getNombreProducto(),
                                nuevoDetalleVenta.getCategoria().toString(),
                                String.valueOf(nuevoDetalleVenta.getCantidad()),
                               String.format("%.2f",nuevoDetalleVenta.getPrecioUnitario()),
                               String.format("%.2f",nuevoDetalleVenta.getPrecioTotal()),
                               String.format("%.2f",nuevoDetalleVenta.getDescuento()),
                               String.format("%.2f",nuevoDetalleVenta.getImporteAPagar())
                        };

                        agregarFila(fila,tblVentaActual);

                    }else {
                        //Verificar si el platillo a ingresar ya se encuentra en la lista

                        DetalleVentaDTO detalleVentaDTO =
                                listaDetallesVenta.obtenerObjetoPorCampo(listaDetallesVenta,
                                "NombreProducto",
                                txtNombreProducto.getText());


                        if (detalleVentaDTO != null){
                            detalleVentaDTO.setCantidad(detalleVentaDTO.getCantidad()+cantidad);

                            precioTotalDetalle =   detalleVentaDTO.getPrecioUnitario() * detalleVentaDTO.getCantidad();
                            detalleVentaDTO.setPrecioTotal(precioTotalDetalle);

                            descuentoTotalDetalle = detalleVentaDTO.getDescuento() + hallarDescuentoTotalPorPlatillo(detalleVentaDTO.getPrecioUnitario(),cantidad);
                            detalleVentaDTO.setDescuento(descuentoTotalDetalle);

                            importePagarDetalle = precioTotalDetalle - descuentoTotalDetalle;
                            detalleVentaDTO.setImporteAPagar(importePagarDetalle);


                            //Mostrando nodo en la tabla
                            String[] fila = {
                                    String.valueOf(detalleVentaDTO.getCodigoDetalleVenta()),
                                    String.valueOf(detalleVentaDTO.getCodigoProducto()),
                                    detalleVentaDTO.getNombreProducto(),
                                    detalleVentaDTO.getCategoria().toString(),
                                    String.valueOf(detalleVentaDTO.getCantidad()),
                                    String.format("%.2f",detalleVentaDTO.getPrecioUnitario()),
                                    String.format("%.2f",detalleVentaDTO.getPrecioTotal()),
                                    String.format("%.2f",detalleVentaDTO.getDescuento()),
                                    String.format("%.2f",detalleVentaDTO.getImporteAPagar())
                            };

                            insertarFila(detalleVentaDTO.getCodigoDetalleVenta()-1,fila,tblVentaActual,jspTblActual);


                        }else {
                            MenuDTO menuDTO = FrmPrincipal.listaMenu.obtenerObjetoPorCampo(FrmPrincipal.listaMenu,
                                    "Nombre",txtNombreProducto.getText());
                            DetalleVentaDTO nuevoDetalleVenta =  null;

                            if (menuDTO != null){

                                precioTotalDetalle = cantidad * menuDTO.getPrecioUnitario();

                                descuentoTotalDetalle = hallarDescuentoTotalPorPlatillo(menuDTO.getPrecioUnitario(), cantidad);

                                importePagarDetalle = precioTotalDetalle - descuentoTotalDetalle;

                                nuevoDetalleVenta = new DetalleVentaDTO(
                                        tblVentaActual.getRowCount()+1,
                                        menuDTO.getCodigo(),
                                        menuDTO.getNombre(),
                                        menuDTO.getCategoria().name(),
                                        Integer.parseInt(txtCantidad.getText()),
                                        menuDTO.getPrecioUnitario(),
                                        precioTotalDetalle,
                                        descuentoTotalDetalle,
                                        importePagarDetalle);



                            }else {
                                CartaDTO cartaDTO = FrmPrincipal.listaCarta.obtenerObjetoPorCampo(FrmPrincipal.listaCarta,
                                        "Nombre",txtNombreProducto.getText());
                                if (cartaDTO != null){
                                    precioTotalDetalle = cantidad * cartaDTO.getPrecioUnitario();

                                    descuentoTotalDetalle = hallarDescuentoTotalPorPlatillo(cartaDTO.getPrecioUnitario(), cantidad);

                                    importePagarDetalle = precioTotalDetalle - descuentoTotalDetalle;

                                    nuevoDetalleVenta = new DetalleVentaDTO(
                                            tblVentaActual.getRowCount()+1,
                                            cartaDTO.getCodigo(),
                                            cartaDTO.getNombre(),
                                            cartaDTO.getCategoria().name(),
                                            Integer.parseInt(txtCantidad.getText()),
                                            cartaDTO.getPrecioUnitario(),
                                            precioTotalDetalle,
                                            descuentoTotalDetalle,
                                            importePagarDetalle);

                                }else {
                                    JOptionPane.showMessageDialog(null,"Ocurrió un error al crear" +
                                            " el detalle de venta para la tabla.");
                                }
                            }

                            //Agregando nodo a la lista detalles de venta
                            listaDetallesVenta.agregarAlFinal(nuevoDetalleVenta);


                            //Mostrando nodo en la tabla
                            String[] fila = {
                                    String.valueOf(nuevoDetalleVenta.getCodigoDetalleVenta()),
                                    String.valueOf(nuevoDetalleVenta.getCodigoProducto()),
                                    nuevoDetalleVenta.getNombreProducto(),
                                    nuevoDetalleVenta.getCategoria().toString(),
                                    String.valueOf(nuevoDetalleVenta.getCantidad()),
                                    String.format("%.2f",nuevoDetalleVenta.getPrecioUnitario()),
                                    String.format("%.2f",nuevoDetalleVenta.getPrecioTotal()),
                                    String.format("%.2f",nuevoDetalleVenta.getDescuento()),
                                    String.format("%.2f",nuevoDetalleVenta.getImporteAPagar())
                            };

                            agregarFila(fila,tblVentaActual);

                        }

                    }


                    listaDetallesVenta.mostrarLista();
                    System.out.println();


                    actualizarMontos(false);
                    System.out.println("precioTotal = " + precioTotal);;
                    System.out.println("descuentoTotal = " + descuentoTotal);
                    System.out.println("importePagar = " + importePagar);
                }else {
                    JOptionPane.showMessageDialog(null,"Ha rellenado los campos de forma incorrecta.");
                }


            }
        });


        btnRealizarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Actualizar los datos de la Venta Actual con los datos del detalle de Venta
                if (ventaActual != null && tblVentaActual.getRowCount() != 0){
                    // Obtener la fecha actual con Joda-Time
                    DateTime fechaActual = DateTime.now();

                    // Extraer la hora exacta
                    int hora = fechaActual.getHourOfDay();
                    int minutos = fechaActual.getMinuteOfHour();
                    int segundos = fechaActual.getSecondOfMinute();

                    // Crear una nueva fecha con la misma fecha que la original pero con hora exacta
                    DateTime fechaConHoraExacta = fechaActual.withTime(hora, minutos, segundos, 0);

                    //Captar tipo de comprobante de pequeño jdialog
                    String tipoComprobante = "Boleta";


                    int cantidadPlatillos= 0;
                    double montoTotal = 0;

                    List<DetalleVentaDTO> detalleVentaDTOS = listaDetallesVenta.getObjetos();

                    // Recorrer la lista de objetos y extraer la información de cada objeto
                    for (DetalleVentaDTO detalleVentaDTO : detalleVentaDTOS) {
                       cantidadPlatillos += detalleVentaDTO.getCantidad();
                       montoTotal += detalleVentaDTO.getImporteAPagar();
                    }


                    ventaActual.setFecha_venta(fechaConHoraExacta);
                    ventaActual.setTipoComprobante(TipoComprobante.valueOf(tipoComprobante));
                    ventaActual.setCantidadProductos(cantidadPlatillos);
                    ventaActual.setDescuento_total(descuentoTotal);
                    ventaActual.setMontoTotal(importePagar);
                    ventaActual.setEstado(Estado.PAGADA.toString());

                    try {
                        ventaController.agregar(ventaActual);
                        JOptionPane.showMessageDialog(null,"Subida de Venta exitoso");

                        for (DetalleVentaDTO detalleVentaDTO : detalleVentaDTOS) {
                            detalleVentaController.agregar(detalleVentaDTO);

                        }

                        JOptionPane.showMessageDialog(null,"Subida de detalles de ventaa exitosa");

                        listaVentas = ventaController.listar();
                        modeloVentaActual.setRowCount(0);

                        limpiarEntradas();
                        txtDescuentoPorcentajeTotal.setText("");
                        txtDescuentoSolesTotal.setText("");

                        actualizarMontos(false);

                        JPanelEstadisticas.recargarVentas();


                        Connection con = ConexionBD.getConnection();

                        JasperReport reporte = null;

                        HashMap<String, Object> parametros = new HashMap();


                        InputStream logoInputStream = this.getClass().getClassLoader().getResourceAsStream("img/frmLogin/logoEmpresa.png");

                        int idVenta = listaVentas.obtenerUltimoObjeto().getNroVenta();
                        parametros.put("idVenta",idVenta);
                        parametros.put("logoEmpresa",logoInputStream);

                        // Obtiene la ruta del directorio "resources"
                        String resourcesPath = Draft.class.getClassLoader().getResource("pdf/recibo.jasper").getPath();

                        File file = new File(resourcesPath);


                        reporte = (JasperReport) JRLoader.loadObject(file);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parametros,con);
                        JasperViewer view = new JasperViewer(jasperPrint,false);
                        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        view.setVisible(true);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,"Ocurrió un error al agregar la venta a la bd: "+
                                ex.getMessage());
                        throw new RuntimeException(ex);
                    }

                }else {
                    JOptionPane.showMessageDialog(null,
                            "Tiene que añadir platillos para realizar una venta");
                }


            }
        });




        //Agregando evento de acción para el botón de las Entradas para vaciar los campos de productos
        btnLimpiarEntradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarEntradas();
            }
        });





        btnCancelarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogEliminarVenta jDialogEliminarVenta = new JDialogEliminarVenta(tblVentaActual);
                limpiarEntradas();
                actualizarMontos(false);
            }
        });



        //endregion



        //endregion

        // Agregar el MouseListener a la tabla para detectar clics en las cabeceras


        tblVentaActual.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tblVentaActual.isEditing()) {
                    TableCellEditor cellEditor = tblVentaActual.getCellEditor();
                    tblVentaActual.getCellEditor().stopCellEditing();
                    int cantidad = (int) modeloVentaActual.getValueAt(tblVentaActual.getSelectedRow(),4);
                    double precioTotal = Integer.parseInt(cellEditor.getCellEditorValue().toString())* cantidad;
                    modeloVentaActual.setValueAt(precioTotal,tblVentaActual.getSelectedRow(),6);
                }
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tblVentaActual.isEditing()) {
                    int editedRow = tblVentaActual.getEditingRow();
                    int editedColumn = tblVentaActual.getEditingColumn();
                    if (editedRow != -1 && editedColumn != -1) {
                        Rectangle cellRect = tblVentaActual.getCellRect(editedRow, editedColumn, false);
                        if (!cellRect.contains(e.getPoint())) {
                            // Detener la edición de la celda y guardar el valor editado
                            TableCellEditor cellEditor = tblVentaActual.getCellEditor();
                            if (cellEditor != null) {
                                cellEditor.stopCellEditing();
                                int cantidad = (int) modeloVentaActual.getValueAt(tblVentaActual.getSelectedRow(),4);
                                double precioTotal = Integer.parseInt(cellEditor.getCellEditorValue().toString())* cantidad;
                                modeloVentaActual.setValueAt(precioTotal,tblVentaActual.getSelectedRow(),6);
                                actualizarMontos(true);
                            }
                        }
                    }
                }
            }
        });

        jpVentaActual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tblVentaActual.isEditing()) {
                    int editedRow = tblVentaActual.getEditingRow();
                    int editedColumn = tblVentaActual.getEditingColumn();
                    if (editedRow != -1 && editedColumn != -1) {
                        Rectangle cellRect = tblVentaActual.getCellRect(editedRow, editedColumn, false);
                        if (!cellRect.contains(e.getPoint())) {
                            // Detener la edición de la celda y guardar el valor editado
                            TableCellEditor cellEditor = tblVentaActual.getCellEditor();
                            if (cellEditor != null) {
                                cellEditor.stopCellEditing();

                                int cantidad = (int) modeloVentaActual.getValueAt(tblVentaActual.getSelectedRow(),4);
                                double precioTotal = Integer.parseInt(cellEditor.getCellEditorValue().toString())* cantidad;
                                modeloVentaActual.setValueAt(precioTotal,tblVentaActual.getSelectedRow(),6);

                                actualizarMontos(true);
                            }
                        }
                    }
                }
            }
        });




    }

    private double hallarDescuentoTotalPorPlatillo(double precioUnitario, int cantidad) {
        double dsctoFinal = 0;
        try{
            double dsctoSolesPlatillo = 0;
            if (!txtDescuentoSolesProducto.getText().isEmpty()){
                dsctoSolesPlatillo =  Double.parseDouble(txtDescuentoSolesProducto.getText());
            }

            double dsctoPorcentajePlatillo = 0;
            if (!txtDescuentoPorcentajeProducto.getText().isEmpty()){
                dsctoPorcentajePlatillo = (Double.parseDouble(txtDescuentoPorcentajeProducto.getText()))/100;
            }


            dsctoFinal = dsctoSolesPlatillo + (dsctoPorcentajePlatillo * precioUnitario);

            dsctoFinal*=cantidad;
        }catch (Exception e){
            System.out.println("Metodo hallar descuentototal"+e.getMessage());
        }

        return dsctoFinal;

    }

    private void hallarDescuentoPorMontoTotal() {
        double dsctoFinal = 0;

        actualizarMontos(false);

        try{

            double dsctoSolesTotal = 0;
            if (!txtDescuentoSolesTotal.getText().isEmpty()){
                dsctoSolesTotal =  Double.parseDouble(txtDescuentoSolesTotal.getText());
            }

            double dsctoPorcentajeTotal = 0;
            if (!txtDescuentoPorcentajeTotal.getText().isEmpty()){
                dsctoPorcentajeTotal = (Double.parseDouble(txtDescuentoPorcentajeTotal.getText()))/100;
            }


            dsctoFinal = dsctoSolesTotal + (dsctoPorcentajeTotal * precioTotal);

            descuentoTotal += dsctoFinal;

            importePagar = precioTotal - descuentoTotal;

            actualizarMontos(true);

        }catch (Exception e){
            System.out.println("Metodo hallar descuentototal"+e.getMessage());
        }

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

    private boolean validarCamposPlatillos() {

        int cantidad=-1;
        try{
            cantidad = Integer.parseInt(txtCantidad.getText());
        }catch (Exception ex){
            System.out.println("Cantidad de Platos Inválida");
        }

        if (nombres.contains(txtNombreProducto.getText())&&
            cantidad > 0){
            return  true;
        }

        return false;
    }


    private void limpiarEntradas() {
        txtNombreProducto.setText("");
        txtCantidad.setText("");
        txtDescuentoPorcentajeProducto.setText("");
        txtDescuentoSolesProducto.setText("");
    }


    // Métodos Fuera del Constructor



    //region[Método para Inicializar componentes de Venta Actual]
    private void inicializarPanelVentaActual() {
        //Componentes para la pestaña de Venta Actual
        jpVentaActual.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow(); // Establecer el enfoque en el JPanel
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        //Barra de Búsqueda de Productos

        //JLabel de titulo para el codigo o producto
        lblTituloNombreProducto = new JLabel("Código o Nombre del Producto");
        lblTituloNombreProducto.setBounds(49,42,212,15);
        cambiarTamanioFuente(lblTituloNombreProducto, 15, Font.PLAIN);
        jpVentaActual.add(lblTituloNombreProducto);

        //JTextfield para ingreso de codigo o producto
        txtNombreProducto = new RoundedTextField(30, 3, 5);
        txtNombreProducto.setBounds(260, 31, 250, 35);
        cambiarTamanioFuente(txtNombreProducto, 15, Font.PLAIN);

        txtNombreProducto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_BACK_SPACE : break;
                    case KeyEvent.VK_ENTER:
                        txtNombreProducto.setText(txtNombreProducto.getText());
                        break;
                    case KeyEvent.VK_CAPS_LOCK:
                        break;
                    default:
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                String txt = txtNombreProducto.getText();
                                autoComplete(txt,txtNombreProducto);
                            }
                        });
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        obtenerNombresPlatillos();

        jpVentaActual.add(txtNombreProducto);



        //JLabel de titulo para la cantidad
        lblTituloCantidad = new JLabel("Cantidad:");
        lblTituloCantidad.setBounds(520,42,64,15);
        cambiarTamanioFuente(lblTituloCantidad, 15, Font.PLAIN);
        jpVentaActual.add(lblTituloCantidad);


        //JTextfield para ingreso de la cantidad
        txtCantidad = new RoundedTextField(10,3,5);
        txtCantidad.setBounds(585,31,62,35);
        cambiarTamanioFuente(txtCantidad, 15, Font.PLAIN);
        jpVentaActual.add(txtCantidad);

        //JLabel de titulo para el descuento del producto
        lblTituloDescuentoProducto = new JLabel("Descuento:");
        lblTituloDescuentoProducto.setBounds(660,40,103,17);
        cambiarTamanioFuente(lblTituloDescuentoProducto, 15, Font.PLAIN);
        jpVentaActual.add(lblTituloDescuentoProducto);


        //JTextfield para ingreso del descuento del producto por Porcentaje
        txtDescuentoPorcentajeProducto = new RoundedTextField(10,3,2);
        txtDescuentoPorcentajeProducto.setBounds(755,31,53,30);
        cambiarTamanioFuente(txtDescuentoPorcentajeProducto, 15, Font.PLAIN);
        jpVentaActual.add(txtDescuentoPorcentajeProducto);

        //JLabel de titulo para el símbolo del descuento del producto por Porcentaje
        lblPorcentajeSimboloProducto = new JLabel("% o");
        lblPorcentajeSimboloProducto.setBounds(815,38,40,16);
        cambiarTamanioFuente(lblPorcentajeSimboloProducto, 15, Font.PLAIN);
        jpVentaActual.add(lblPorcentajeSimboloProducto);


        //JTextfield para ingreso del descuento del producto por Soles
        txtDescuentoSolesProducto = new RoundedTextField(10,3,2);
        txtDescuentoSolesProducto.setBounds(855,31,48,30);
        cambiarTamanioFuente(txtDescuentoSolesProducto, 15, Font.PLAIN);
        jpVentaActual.add(txtDescuentoSolesProducto);

        //JLabel de titulo para el símbolo del descuento del producto por Soles
        lblSolesSimbolo = new JLabel("Soles");
        lblSolesSimbolo.setBounds(925,40,45,15);
        cambiarTamanioFuente(lblSolesSimbolo, 15, Font.PLAIN);
        jpVentaActual.add(lblSolesSimbolo);

        //JButton para Agregar el Producto
        btnAgregarProducto = new RoundedButton("Agregar Producto",3);
        btnAgregarProducto.setBounds(1000,31,200,30);
        cambiarTamanioFuente(btnAgregarProducto, 18, Font.BOLD);
        btnAgregarProducto.setBackground(new Color(0x53FF83));
        btnAgregarProducto.setForeground(Color.BLACK);
        jpVentaActual.add(btnAgregarProducto);

        //JButton para Limpiar Entradas
        btnLimpiarEntradas = new RoundedButton("Limpiar Entradas",3);
        btnLimpiarEntradas.setBounds(1000,74,200,30);
        cambiarTamanioFuente(btnLimpiarEntradas, 18, Font.BOLD);
        btnLimpiarEntradas.setBackground(new Color(0x73F7FF));
        btnLimpiarEntradas.setForeground(Color.BLACK);
        jpVentaActual.add(btnLimpiarEntradas);

        //JLabel de titulo de Monto total
        lblMontoTotal = new JLabel(String.format("%-24s %5s", "Monto Total:","S/."));
        lblMontoTotal.setBounds(930,585,280,20);
        cambiarTamanioFuente(lblMontoTotal, 18, Font.BOLD);
        jpVentaActual.add(lblMontoTotal);

        //JLabel de titulo de Descuento Total
        lblDescuentoTotal = new JLabel(String.format("%-21s %5s", "Descuento Total:","S/."));
        lblDescuentoTotal.setBounds(930,615,280,20);
        cambiarTamanioFuente(lblDescuentoTotal, 18, Font.BOLD);
        jpVentaActual.add(lblDescuentoTotal);


        //JLabel de titulo de Monto total
        lblMontoAPagar = new JLabel(String.format("%-21s %5s", "Monto A Pagar:","S/."));
        lblMontoAPagar.setBounds(930,645,280,23);
        cambiarTamanioFuente(lblMontoAPagar, 18, Font.BOLD);
        jpVentaActual.add(lblMontoAPagar);

        //Método que Prepara y muestra la Tabla de Venta Actual por Defecto
        //Inicializando los titulos de las columnas de la tabla
        cabecerasTablas = new String[]{"N° Producto",  "Código del Producto",
                "Nombre del Producto","Categoría",
                "Cantidad","Precio Unitario","Precio Total", "Descuento","Importe a Pagar"};



        tituloCapturado = false;
        titulosCapturados = new String[3];
        prepararTablaVentaActual(cabecerasTablas, new Dimension(1155,440));
        jspTblActual.setBounds(47,129,1155,440);
        jspTblActual.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jpVentaActual.add(jspTblActual);




        //JButton para Poner en espera una venta
        btnEliminarProducto = new RoundedButton("Eliminar Producto",5);
        btnEliminarProducto.setBounds(46,70,160,48);
        cambiarTamanioFuente(btnEliminarProducto, 15, Font.BOLD);
        btnEliminarProducto.setBackground(new Color(0xFF2F00));
        btnEliminarProducto.setForeground(Color.BLACK);
        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tblVentaActual.getRowCount() > 0 && tblVentaActual.getSelectedRow() != -1){


                    System.out.println("Mostrando lista antes de eliminar proucto");
                    listaDetallesVenta.mostrarLista();
                    System.out.println();

                    if (listaDetallesVenta.longitud == 1 ){
                        listaDetallesVenta.vaciar();
                        modeloVentaActual.removeRow(tblVentaActual.getSelectedRow());
                    }else {

                        int codProducto=Integer.parseInt(String.valueOf(tblVentaActual.getValueAt(tblVentaActual.getSelectedRow(),1)));
                        int filaSeleccionada = Integer.parseInt(String.valueOf(listaDetallesVenta.obtenerObjetoPorCampo(listaDetallesVenta,"CodigoDetalleVenta",
                                Integer.parseInt(String.valueOf(tblVentaActual.getValueAt(tblVentaActual.getSelectedRow(),0)))).getCodigoDetalleVenta()));



                        listaDetallesVenta.eliminarPorObjeto(listaDetallesVenta.obtenerObjetoPorCampo(listaDetallesVenta,
                                "CodigoProducto", codProducto));



                        int contador = filaSeleccionada+1;
                        for (int i = 0 ; i < (listaDetallesVenta.longitud+1-filaSeleccionada); i++) {
                            listaDetallesVenta.buscarPorCampo("CodigoDetalleVenta",contador).setCodigoDetalleVenta(
                                    listaDetallesVenta.buscarPorCampo("CodigoDetalleVenta",contador).getCodigoDetalleVenta() - 1);
                            contador++;
                        }

                        modeloVentaActual.removeRow(tblVentaActual.getSelectedRow());
                        llenarJTable(listaDetallesVenta,tblVentaActual);

                        if (tblVentaActual.getSelectedRow()+1 != tblVentaActual.getRowCount()){
                        }
                    }

                    System.out.println("Mostrando lista luego de eliminar producto");
                    listaDetallesVenta.mostrarLista();
                    System.out.println();
                }else {
                    JOptionPane.showMessageDialog(null,"Debe tener datos en la tabla para eliminar");
                }

            }
        });




        jpVentaActual.add(btnEliminarProducto);



        //JButton para Cancelar una Venta
        btnCancelarVenta = new RoundedButton("Cancelar Venta",5);
        btnCancelarVenta.setBounds(46,657,160,48);
        cambiarTamanioFuente(btnCancelarVenta, 15, Font.BOLD);
        btnCancelarVenta.setBackground(new Color(0xFF0000));
        btnCancelarVenta.setForeground(Color.white);
        jpVentaActual.add(btnCancelarVenta);

        //JButton para Imprimir Váucher
        btnImprimirVaucher = new RoundedButton("Imprimir Váucher",5);
        btnImprimirVaucher.setBounds(46,598,160,48);
        cambiarTamanioFuente(btnImprimirVaucher, 15, Font.BOLD);
        btnImprimirVaucher.setBackground(new Color(0xC4FFA0));
        btnImprimirVaucher.setForeground(Color.black);
        jpVentaActual.add(btnImprimirVaucher);

        //JLabel de titulo para el descuento del total
        lblTituloDescuentoTotal = new JLabel("Descuento al Monto Total:");
        lblTituloDescuentoTotal.setBounds(400,610,200,17);
        cambiarTamanioFuente(lblTituloDescuentoTotal, 15, Font.PLAIN);
        jpVentaActual.add(lblTituloDescuentoTotal);


        //JTextfield para ingreso del descuento del total por Porcentaje
        txtDescuentoPorcentajeTotal = new RoundedTextField(10,3,2);
        txtDescuentoPorcentajeTotal.setBounds(580,605,45,30);
        cambiarTamanioFuente(txtDescuentoPorcentajeTotal, 15, Font.PLAIN);
        jpVentaActual.add(txtDescuentoPorcentajeTotal);

        //JLabel de titulo para el símbolo del descuento del total por Porcentaje
        lblPorcentajeSimboloTotal = new JLabel("% o");
        lblPorcentajeSimboloTotal.setBounds(630,610,40,16);
        cambiarTamanioFuente(lblPorcentajeSimboloTotal, 15, Font.PLAIN);
        jpVentaActual.add(lblPorcentajeSimboloTotal);


        //JTextfield para ingreso del descuento del total por Porcentaje
        txtDescuentoSolesTotal = new RoundedTextField(10,3,2);
        txtDescuentoSolesTotal.setBounds(665,605,45,30);
        cambiarTamanioFuente(txtDescuentoSolesTotal, 15, Font.PLAIN);
        jpVentaActual.add(txtDescuentoSolesTotal);

        //JLabel de titulo para el símbolo del descuento del total por Soles
        lblSolesSimbolo = new JLabel("Soles");
        lblSolesSimbolo.setBounds(715,610,45,15);
        cambiarTamanioFuente(lblSolesSimbolo, 15, Font.PLAIN);
        jpVentaActual.add(lblSolesSimbolo);

        //JButton para Aplicar Descuento
        btnAplicarDescuento = new RoundedButton("Aplicar Descuento",5);
        btnAplicarDescuento.setBounds(400,657,350,48);
        cambiarTamanioFuente(btnAplicarDescuento, 15, Font.BOLD);
        btnAplicarDescuento.setBackground(new Color(0x83F8FF));
        btnAplicarDescuento.setForeground(Color.black);
        btnAplicarDescuento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hallarDescuentoPorMontoTotal();
            }
        });

        jpVentaActual.add(btnAplicarDescuento);


        //JButton para realizar la venta
        btnRealizarVenta = new RoundedButton("Realizar Venta",5);
        btnRealizarVenta.setBounds(930,675,260,50);
        cambiarTamanioFuente(btnRealizarVenta, 20, Font.BOLD);
        btnRealizarVenta.setBackground(new Color(0xFFB23F));
        btnRealizarVenta.setForeground(Color.black);
        jpVentaActual.add(btnRealizarVenta);


        //Mostrando montos según la tabla
        actualizarMontos(false);



    }



    public static void obtenerNombresPlatillos() {
        nombres = new ArrayList<>();
        for (int i = 0; i < JPanelMenuCarta.tblCarta.getRowCount(); i++) {
            nombres.add(JPanelMenuCarta.tblCarta.getValueAt(i, 1).toString());
        }
    }

    private void autoComplete(String txt,JTextField txtField){
        String complete="";
        int start= txt.length();
        int last = txt.length();
        int a;

        for ( a = 0; a < nombres.size(); a++) {
            if (nombres.get(a).toString().startsWith(txt)){
                complete = nombres.get(a).toString();
                last = complete.length();
                break;
            }
        }

        if (last>start){
            txtField.setText(complete);
            txtField.setCaretPosition(last);
            txtField.moveCaretPosition(start);
        }

    }

    //endregion

    //region[Método para Inicializar componentes de Ventas en Espera]
    private void inicializarPanelVentasEnEspera() {
        //Inicializando componentes del JPanel de Ventas en espera

        ventasEsperando =0;
        ventasEspera = new Stack<>();


        //Barra de Búsqueda de Productos
        //JLabel de titulo para el codigo o producto
        lblTituloBusquedaVentas = new JLabel("Buscar por Código o Nombre");
        lblTituloBusquedaVentas.setBounds(350,20,250,30);
        cambiarTamanioFuente(lblTituloBusquedaVentas, 18, Font.PLAIN);
        jpVentasEnEspera.add(lblTituloBusquedaVentas);

        //JTextfield para ingreso de codigo o producto
        txtBusquedaVentas = new RoundedTextField(30,3,5);
        txtBusquedaVentas.setBounds(600,20,250,35);
        cambiarTamanioFuente(txtBusquedaVentas, 18, Font.PLAIN);
        jpVentasEnEspera.add(txtBusquedaVentas);

        //Inicializando cabeceras para la tabla
        cabecerasTablas = new String[]{"N° Venta", "Nombre de Venta","Cantidad de Productos", "Monto Total",
                "Descuento","Monto a Pagar", "Acciones"};

        //Preparando la tabla con todo lo necesario
        prepararTablaVentasEnEspera(cabecerasTablas, new Dimension(1155,jpContenido.getSize().height-100));


        //Posicionando el container Scrolleable de la tabla en el JPanel
        jspTblVentasEnEspera.setBounds(55,60, tblVentasEnEspera.getPreferredSize().width, tblVentasEnEspera.getPreferredSize().height);

        //Agregando la tabla al JPanel de Ventas en Espera
        jpVentasEnEspera.add(jspTblVentasEnEspera);




    }

    //endregion


    //region [Métodos para el constructor]

    // Clase para el editor de celdas personalizado
    private class CustomCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener  {
        private JTextField textField;

        public CustomCellEditor() {
            textField = new JTextField();
            textField.addActionListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            return textField.getText();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            textField.setText(value.toString());
            return textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Mostrar el mensaje al salir del modo de edición
            stopCellEditing();
            int nuevaCantidad = Integer.parseInt(String.valueOf(getCellEditorValue()));
            double precioUnitario = Double.parseDouble(String.valueOf(modeloVentaActual.getValueAt(tblVentaActual.getSelectedRow(),5)));
            double precioTotal = nuevaCantidad * precioUnitario;
            double descuentoProducto = hallarDescuentoTotalPorPlatillo(precioUnitario, nuevaCantidad);
            double importeFinal = precioTotal - descuentoProducto;
            modeloVentaActual.setValueAt(precioTotal,tblVentaActual.getSelectedRow(),6);
            modeloVentaActual.setValueAt(descuentoProducto,tblVentaActual.getSelectedRow(),7);
            modeloVentaActual.setValueAt(importeFinal,tblVentaActual.getSelectedRow(),8);

            listaDetallesVenta.mostrarLista();
            DetalleVentaDTO detalle = listaDetallesVenta.obtenerObjetoPorCampo(listaDetallesVenta,"CodigoProducto",
                    Integer.parseInt(String.valueOf(tblVentaActual.getValueAt(tblVentaActual.getSelectedRow(),1))));

            System.out.println("detalle = " + detalle);

            detalle.setCantidad(nuevaCantidad);
            detalle.setPrecioTotal(precioTotal);
            detalle.setDescuento(descuentoProducto);
            detalle.setImporteAPagar(importeFinal);



            actualizarMontos(false);
        }





    }
    private void prepararTablaVentaActual(String[] cabecerasTablas, Dimension tamanioTabla) {

        //Inicializando la matriz de filas de la tabla
        dataTable = new Object[0][cabecerasTablas.length];

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

    private void ajustarAnchoTabla(JTable tbl) {
        int alturaPreferida = jspTblActual.getHeight();
        int alturaActual =  tblVentaActual.getRowCount()*tblVentaActual.getRowHeight() + 25;
        if (alturaActual >= alturaPreferida){
            tblVentaActual.setFillsViewportHeight(true);
            tblVentaActual.setPreferredSize(new Dimension(tblVentaActual.getPreferredSize().width,alturaActual));
            tblVentaActual.revalidate();
            tblVentaActual.repaint();
            jspTblActual.setPreferredSize(tblVentaActual.getPreferredSize());
            jspTblActual.revalidate();
            jspTblActual.repaint();
        }
    }

    // Método para agregar una nueva fila a la tabla
    private void agregarFila(Object[] fila, JTable tbl) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.addRow(fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl);
    }

    private void agregarFilaVentaEspera(Object[] fila, JTable tbl) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.insertRow(0, fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl);
    }


    private void prepararTablaVentasEnEspera(String[] cabecerasTablas, Dimension tamanioTabla){
        tblVentasEnEspera = new JTable() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        //Personalizando las celdas y agregando las cabeceras a la tabla
        construirTablaVacia(cabecerasTablas, tblVentasEnEspera);

        //Estableciendo propiedades a la tabla
        tblVentasEnEspera.setPreferredSize(tamanioTabla);
        tblVentasEnEspera.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        tblVentasEnEspera.setOpaque(false);

        //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
        jspTblVentasEnEspera = new JScrollPane(tblVentasEnEspera);

        //Aplicando propiedades al JScrollPane de la tabla
        jspTblVentasEnEspera.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        //Cambiando colores del fondo y de la barra de scroll
        jspTblVentasEnEspera.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
        jspTblVentasEnEspera.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical

    }


    private void construirTablaVacia(String[] titulos, JTable table) {

        Object[][] data = new Object[0][0];
        modelo=new ModeloTabla(data, titulos){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == Utilidades.Cantidad;
            }
        };
        if (table == tblVentaActual) {
            //se asigna el modelo a la tabla

            tblVentaActual.setDefaultRenderer(Object.class, new GestionCeldas());
            tblVentaActual.setForeground(Color.white);
            tblVentaActual.setModel(modelo);

            filasTabla = tblVentaActual.getRowCount();
            columnasTabla = tblVentaActual.getColumnCount();

            tblVentaActual.getTableHeader().setReorderingAllowed(false);
            tblVentaActual.getTableHeader().setResizingAllowed(false);
            tblVentaActual.setRowHeight(25);
            tblVentaActual.setGridColor(new Color(0, 0, 0));

            // Usar un editor de celdas personalizado para la columna "Nombre" (columna 0)
            tblVentaActual.getColumnModel().getColumn(Utilidades.Cantidad).setCellEditor(new CustomCellEditor());

            // Agregar un listener para capturar el evento cuando se modifica una celda
            tblVentaActual.getModel().addTableModelListener(e -> {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 0) {
                    String newValue = table.getValueAt(row, column).toString();
                }
            });

            tblVentaActual.getColumnModel().getColumn(Utilidades.NroProducto).setPreferredWidth(90);
            tblVentaActual.getColumnModel().getColumn(Utilidades.CodigoProducto).setPreferredWidth(150);
            tblVentaActual.getColumnModel().getColumn(Utilidades.NombreProducto).setPreferredWidth(230);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Categoria).setPreferredWidth(100);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Cantidad).setPreferredWidth(80);
            tblVentaActual.getColumnModel().getColumn(Utilidades.PrecioUnitario).setPreferredWidth(120);
            tblVentaActual.getColumnModel().getColumn(Utilidades.PrecioTotal).setPreferredWidth(100);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Descuento).setPreferredWidth(100);
            tblVentaActual.getColumnModel().getColumn(Utilidades.Importe).setPreferredWidth(120);


        }else {
            //se asigna el modelo a la tabla

            tblVentasEnEspera.setDefaultRenderer(Object.class, new GestionCeldas());
            tblVentasEnEspera.setForeground(Color.white);
            tblVentasEnEspera.setModel(modelo);

            filasTabla= tblVentasEnEspera.getRowCount();
            columnasTabla= tblVentasEnEspera.getColumnCount();

            tblVentasEnEspera.getTableHeader().setReorderingAllowed(false);
            tblVentasEnEspera.getTableHeader().setResizingAllowed(false);
            tblVentasEnEspera.setRowHeight(25);
            tblVentasEnEspera.setGridColor(new Color(0, 0, 0));

            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.NombreVenta).setPreferredWidth(200);
            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.CantidadProductos).setPreferredWidth(200);
            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.MontoTotal).setPreferredWidth(150);
            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.DescuentoVenta).setPreferredWidth(150);
            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.MontoAPagar).setPreferredWidth(150);
            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.NroVenta).setPreferredWidth(100);
            tblVentasEnEspera.getColumnModel().getColumn(Utilidades.Acciones).setPreferredWidth(200);

        }

        //personaliza el encabezado
        JTableHeader jtableHeader = table.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        cambiarTamanioFuente(jtableHeader,20,Font.BOLD);
        table.setTableHeader(jtableHeader);


    }

    private void actualizarMontos(boolean descuentoTotalAplicado) {


        String[] titulos = {String.format("%-24s %5s", "Monto Total:", "S/. "),
                            String.format("%-21s %5s", "Descuento Total:", "S/. "),
                            String.format("%-21s %5s", "Monto A Pagar:", "S/. ")};

        if (txtDescuentoPorcentajeTotal.getText().isEmpty() && txtDescuentoSolesTotal.getText().isEmpty() || !descuentoTotalAplicado){
            precioTotal = 0;
            descuentoTotal = 0;
            importePagar = 0;

            if (tblVentaActual.getRowCount() != 0) {

                for (int i = 0; i < tblVentaActual.getRowCount(); i++) {
                    precioTotal += Float.parseFloat(String.valueOf(modeloVentaActual.getValueAt(i, 6)));
                    descuentoTotal += Float.parseFloat(String.valueOf(modeloVentaActual.getValueAt(i, 7)));
                    importePagar += Float.parseFloat(String.valueOf(modeloVentaActual.getValueAt(i, 8)));
                }

            }
        }



        lblMontoTotal.setText(titulos[0].concat(String.valueOf(String.format("%.2f", precioTotal))));
        lblDescuentoTotal.setText(titulos[1].concat(String.valueOf(String.format("%.2f", descuentoTotal))));
        lblMontoAPagar.setText(titulos[2].concat(String.valueOf(String.format("%.2f", importePagar))));
    }


}

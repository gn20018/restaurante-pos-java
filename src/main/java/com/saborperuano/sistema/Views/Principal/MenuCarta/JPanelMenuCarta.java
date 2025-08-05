package com.saborperuano.sistema.Views.Principal.MenuCarta;

import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.DTO.MenuDTO;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogCarta.JDialogEliminarCarta;
import com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogCarta.JDialogModificarCarta;
import com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogCarta.JDialogNuevaCarta;
import com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogMenu.JDialogCrearMenuAdvertencia;
import utils.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.Utilidades.cambiarTamanioFuente;
import static utils.Utilidades.filtrarTabla;

public class JPanelMenuCarta extends JPanel {
    //Declarar atributors
    private JLabel lbltitulo;
    private JButton btnMenu;
    private JButton btnCarta;
    private JPanel jpContenido;
    private JPanel jpMenu;
    private JPanel jpCarta;
    private JLabel lblBuscarMenu;
    private JTextField txtBuscarMenu;
    private JLabel lblBuscarCarta;
    private JTextField txtBuscarCarta;
    private JButton btnActualizarTablaMenu;
    private JButton btnModificarPlatilloMenu;
    private JButton btnCrearNuevoMenu;

    private JButton btnAgregarNuevoPlatilloMenu;
    private JButton btnEliminarPlatilloMenu;

    private JButton btnActualizarTablaCarta;
    private JButton btnModificarPlatilloCarta;
    private JButton btnCrearNuevoCarta;

    private JButton btnAgregarNuevoPlatilloCarta;
    private JButton btnEliminarPlatilloCarta;
    private JTable tblMenu;
    public static JTable tblCarta;
    
    private JScrollPane jspTblMenu;

    private JScrollPane jspTblCarta;
    private ModeloTabla modelo;
    private Object[][] dataTable;
    private String[] cabecerasTablas;
    private ListaEnlazadaGenerica<MenuDTO> listaMenu;
    private ListaEnlazadaGenerica<CartaDTO> listaCarta;

    private ErrorLog errorLog;
    //Creas el constructor

    public JPanelMenuCarta() {

        //Para colocarle el tamaño al JPanel
        setMinimumSize(new Dimension(1325, 900));
        setPreferredSize(new Dimension(1325, 900));
        setLayout(null);

        try {
            errorLog = new ErrorLog("src/main/resources/error.log");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.listaMenu = FrmPrincipal.listaMenu;
        this.listaCarta= FrmPrincipal.listaCarta;


        //Inicializar componentes
        lbltitulo = new JLabel("Menú y Carta");
        lbltitulo.setBounds(475, 44, 314, 50);
        cambiarTamanioFuente(lbltitulo, 50, Font.BOLD);
        add(lbltitulo);

        //JButton para mostrar Table de Menú del día
        btnMenu = new RoundedButton("Menú del Día",0);
        btnMenu.setBounds(459,112,171,40);
        cambiarTamanioFuente(btnMenu, 20, Font.BOLD);
        btnMenu.setBackground(Color.white);
        btnMenu.setForeground(Color.BLACK);
        add(btnMenu);

        //JButton para mostrar Tabla de Carta
        btnCarta = new RoundedButton("Carta",0);
        btnCarta.setBounds(630,112,182,40);
        cambiarTamanioFuente(btnCarta, 18, Font.BOLD);
        btnCarta.setBackground(new Color(0xD9D9D9));
        btnCarta.setForeground(Color.BLACK);
        add(btnCarta);

        //JPanel para el contenido
        jpContenido = new JPanel();
        jpContenido.setBounds(36,152,1245,723);
        jpContenido.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpContenido.setLayout(null);
        jpContenido.setBackground(Color.white);
        add(jpContenido);

        //JPanel para la pestaña de Menu
        jpMenu = new JPanel();
        jpMenu.setBounds(0,0,1245,738);
        jpMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpMenu.setLayout(null);
        jpMenu.setBackground(Color.white);
        String[] cabecerasMenu = {"Nro de Plato","Nombre","Descripción","Categoría","Precio","Descuento"};
        inicializarPanel(listaMenu,cabecerasMenu,true);

        //JPanel para la pestaña de Carta
        jpCarta = new JPanel();
        jpCarta.setBounds(0,0,1245,738);
        jpCarta.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        jpCarta.setLayout(null);
        jpCarta.setBackground(Color.white);
        String[] cabecerasCarta = {"Nro de Plato","Nombre","Descripción","Tipo Platillo","Categoría","Precio","Estado"};
        inicializarPanel(listaCarta,cabecerasCarta,false);


        //Mostrar en el JPanel de Contenido la pestaña de Venta Actual por defecto
        jpContenido.add(jpMenu);
        jpContenido.revalidate();
        jpContenido.repaint();



        //region [Eventos para Ventana Menú]
        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Removiendo y vaciando el JPanel del Contenido
                jpContenido.removeAll();
                jpContenido.repaint();

                //Cambiando de panel a jpVentaActual
                jpContenido.add(jpMenu);

                //Cambiando los colores de los botones para darle foco al botón de Venta Actual
                btnMenu.setBackground(Color.white);
                btnCarta.setBackground(new Color(0xD9D9D9));
            }


        });
        //endregion




        //region [Eventos para Ventana Carta]
        btnCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Removiendo y vaciando el JPanel del Contenido
                jpContenido.removeAll();
                jpContenido.repaint();

                //Cambiando de panel a jpVentaActual
                jpContenido.add(jpCarta);

                //Cambiando los colores de los botones para darle foco al botón de Venta Actual
                btnCarta.setBackground(Color.white);
                btnMenu.setBackground(new Color(0xD9D9D9));
            }


        });








        btnAgregarNuevoPlatilloMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        btnModificarPlatilloMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnEliminarPlatilloMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });


    }


    //region[Método para Inicializar componentes de Ventas en Espera]
    private void inicializarPanel(ListaEnlazadaGenerica listaEnlazada, String[] cabecerasTablas, boolean esMenu) {

        if (esMenu){
            //Barra de Búsqueda de Productos
            //JLabel de titulo para el codigo o producto
            lblBuscarMenu = new JLabel("Buscar por Código o Nombre");
            lblBuscarMenu.setBounds(209,34,322,40);
            cambiarTamanioFuente(lblBuscarMenu, 24, Font.PLAIN);
            jpMenu.add(lblBuscarMenu);

            //JTextfield para ingreso de codigo o producto
            txtBuscarMenu = new RoundedTextField(30,3,5);
            txtBuscarMenu.setBounds(545,26,289,40);
            cambiarTamanioFuente(txtBuscarMenu, 18, Font.PLAIN);
            txtBuscarMenu.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filtrarTabla(txtBuscarCarta,tblCarta);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filtrarTabla(txtBuscarCarta,tblCarta);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filtrarTabla(txtBuscarCarta,tblCarta);
                }
            });




            //Preparando la tabla con todo lo necesario
            preparaTabla(cabecerasTablas, new Dimension(942,jpMenu.getSize().height-200),0);

            //Alimentando la tabla de los datos extraídos de la BD
//            cargarRegistros(listaMenu);

            //Posicionando el container Scrolleable de la tabla en el JPanel
            jspTblMenu.setBounds(50,104, tblMenu.getPreferredSize().width, tblMenu.getPreferredSize().height);

            //Inicializar componentes
            btnCrearNuevoMenu = new RoundedButton("Crear Nuevo Menú", 3);
            btnCrearNuevoMenu.setBounds(1004,104,230, 48);
            cambiarTamanioFuente(btnCrearNuevoMenu, 16, Font.BOLD);
            btnCrearNuevoMenu.setBackground(new Color(0x28FE05));
            btnCrearNuevoMenu.setForeground(Color.black);

            btnCrearNuevoMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialogCrearMenuAdvertencia jdCrearMenu = new JDialogCrearMenuAdvertencia();
                }
            });

            btnAgregarNuevoPlatilloMenu = new RoundedButton("Agregar Nuevo Platillo", 3);
            btnAgregarNuevoPlatilloMenu.setBounds(1004,166,230, 48);
            cambiarTamanioFuente(btnAgregarNuevoPlatilloMenu, 16, Font.BOLD);
            btnAgregarNuevoPlatilloMenu.setBackground(new Color(0x28FE05));
            btnAgregarNuevoPlatilloMenu.setForeground(Color.black);

            btnModificarPlatilloMenu = new RoundedButton("Modificar Platillo", 3);
            btnModificarPlatilloMenu.setBounds(1004,228,230, 48);
            cambiarTamanioFuente(btnModificarPlatilloMenu, 16, Font.BOLD);
            btnModificarPlatilloMenu.setBackground(new Color(0xFFCB67));
            btnModificarPlatilloMenu.setForeground(Color.black);

            btnEliminarPlatilloMenu = new RoundedButton("Eliminar Platillo", 3);
            btnEliminarPlatilloMenu.setBounds(1004,290,230, 48);
            cambiarTamanioFuente(btnEliminarPlatilloMenu, 16, Font.BOLD);
            btnEliminarPlatilloMenu.setBackground(new Color(0xFF0000));
            btnEliminarPlatilloMenu.setForeground(Color.black);

            btnActualizarTablaMenu = new RoundedButton("Actualizar Tabla", 3);
            btnActualizarTablaMenu.setBounds(1004,352,230, 48);
            cambiarTamanioFuente(btnActualizarTablaMenu, 16, Font.BOLD);
            btnActualizarTablaMenu.setBackground(new Color(0x69FFF6));
            btnActualizarTablaMenu.setForeground(Color.black);


            //Agregando la tabla al JPanel de Ventas en Espera
            jpMenu.add(jspTblMenu);
            jpMenu.add(txtBuscarMenu);
            jpMenu.add(btnCrearNuevoMenu);
            jpMenu.add(btnAgregarNuevoPlatilloMenu);
            jpMenu.add(btnModificarPlatilloMenu);
            jpMenu.add(btnEliminarPlatilloMenu);
            jpMenu.add(btnActualizarTablaMenu);

            //Cambiando los colores de los botones para darle foco al botón de Ventas en Espera
            btnMenu.setBackground(Color.white);
            btnCarta.setBackground(new Color(0xD9D9D9));




        }else {




            //Barra de Búsqueda de Productos
            //JLabel de titulo para el codigo o producto
            lblBuscarCarta = new JLabel("Buscar por Código o Nombre");
            lblBuscarCarta.setBounds(209,34,322,40);
            cambiarTamanioFuente(lblBuscarCarta, 24, Font.PLAIN);
            jpCarta.add(lblBuscarCarta);

            //JTextfield para ingreso de codigo o producto
            txtBuscarCarta = new RoundedTextField(30,3,5);
            txtBuscarCarta.setBounds(545,26,289,40);
            cambiarTamanioFuente(txtBuscarCarta, 18, Font.PLAIN);


            //Preparando la tabla con todo lo necesario
            preparaTabla(cabecerasTablas, new Dimension(1300,jpCarta.getSize().height-200),1);



            //Alimentando la tabla de los datos extraídos de la BD
            Utilidades.llenarJTable(listaCarta,tblCarta);

            //Posicionando el container Scrolleable de la tabla en el JPanel
            jspTblCarta.setBounds(50,104, 942, tblCarta.getPreferredSize().height);

            //Inicializar componentes
            btnAgregarNuevoPlatilloCarta = new RoundedButton("Agregar Nuevo Platillo", 3);
            btnAgregarNuevoPlatilloCarta.setBounds(1004,104,230, 48);
            cambiarTamanioFuente(btnAgregarNuevoPlatilloCarta, 16, Font.BOLD);
            btnAgregarNuevoPlatilloCarta.setBackground(new Color(0x28FE05));
            btnAgregarNuevoPlatilloCarta.setForeground(Color.black);
            btnAgregarNuevoPlatilloCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialogNuevaCarta jDialogNuevaCarta = new JDialogNuevaCarta(listaCarta,jspTblCarta,tblCarta);
                    actualizarTablaCarta();

                }
            });


            btnModificarPlatilloCarta = new RoundedButton("Modificar Platillo", 3);
            btnModificarPlatilloCarta.setBounds(1004,166,230, 48);
            cambiarTamanioFuente(btnModificarPlatilloCarta, 16, Font.BOLD);
            btnModificarPlatilloCarta.setBackground(new Color(0xFFCB67));
            btnModificarPlatilloCarta.setForeground(Color.black);
            btnModificarPlatilloCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (tblCarta.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay filas por modificar");
                    }else if (tblCarta.getSelectedRow() == -1){
                        JOptionPane.showMessageDialog(null,"Debe elegir un registro para modificar");
                    }else {
                        DefaultTableModel modeloCarta = (DefaultTableModel) tblCarta.getModel();
                        Object codigo = modeloCarta.getValueAt(tblCarta.getSelectedRow(),0);
                        CartaDTO carta = listaCarta.obtenerObjetoPorCampo(listaCarta,"Codigo",codigo);

                        JDialogModificarCarta jDialogModificarCarta = new JDialogModificarCarta(listaCarta,carta,
                                jspTblCarta,tblCarta);
                        listaCarta = FrmPrincipal.cartaController.listar();
                        actualizarTablaCarta();

                    }
                }
            });



            btnEliminarPlatilloCarta = new RoundedButton("Eliminar Platillo", 3);
            btnEliminarPlatilloCarta.setBounds(1004,228,230, 48);
            cambiarTamanioFuente(btnEliminarPlatilloCarta, 16, Font.BOLD);
            btnEliminarPlatilloCarta.setBackground(new Color(0xFF0000));
            btnEliminarPlatilloCarta.setForeground(Color.black);

            btnEliminarPlatilloCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tblCarta.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Debe agregar datos a la tabla");
                    }else if (tblCarta.getSelectedRow() == -1){
                        JOptionPane.showMessageDialog(null,"Debe elegir un registro para eliminar");
                    }else {
                        JDialogEliminarCarta jDialogEliminarCarta = new JDialogEliminarCarta(tblCarta,FrmPrincipal.cartaController);
                        actualizarTablaCarta();
                    }

                }
            });

            btnActualizarTablaCarta = new RoundedButton("Actualizar Tabla", 3);
            btnActualizarTablaCarta.setBounds(1004,290,230, 48);
            cambiarTamanioFuente(btnActualizarTablaCarta, 16, Font.BOLD);
            btnActualizarTablaCarta.setBackground(new Color(0x69FFF6));
            btnActualizarTablaCarta.setForeground(Color.black);

            btnActualizarTablaCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    actualizarTablaCarta();
                }
            });

            txtBuscarCarta.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filtrarTabla(txtBuscarCarta,tblCarta);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filtrarTabla(txtBuscarCarta,tblCarta);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filtrarTabla(txtBuscarCarta,tblCarta);
                }
            });

            //Agregando la tabla al JPanel de Carta
            jpCarta.add(jspTblCarta);
            jpCarta.add(txtBuscarCarta);
            jpCarta.add(btnAgregarNuevoPlatilloCarta);
            jpCarta.add(btnModificarPlatilloCarta);
            jpCarta.add(btnEliminarPlatilloCarta);
            jpCarta.add(btnActualizarTablaCarta);
        }



    }

    private void actualizarTablaCarta() {
        listaCarta = FrmPrincipal.cartaController.listar();
        Utilidades.llenarJTable(listaCarta,tblCarta);
    }

    //endregion

    private void preparaTabla(String[] cabecerasTablas, Dimension tamanioTabla, int tblIndex){
        
        if (tblIndex == 0){
            tblMenu = new JTable() {
                @Override
                public void paintComponent(Graphics g) {
                    g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };

            //Personalizando las celdas y agregando las cabeceras a la tabla
            construirTabla(cabecerasTablas, tblMenu,0);

            //Estableciendo propiedades a la tabla
            tblMenu.setPreferredSize(tamanioTabla);
            tblMenu.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
            tblMenu.setOpaque(false);
            tblMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
            jspTblMenu = new JScrollPane(tblMenu);

            //Aplicando propiedades al JScrollPane de la tabla
            jspTblMenu.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
            //Cambiando colores del fondo y de la barra de scroll
            jspTblMenu.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
            jspTblMenu.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical
        }else {
            tblCarta = new JTable() {
                @Override
                public void paintComponent(Graphics g) {
                    g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };

            //Personalizando las celdas y agregando las cabeceras a la tabla
            construirTabla(cabecerasTablas, tblCarta,1);

            //Estableciendo propiedades a la tabla
            tblCarta.setPreferredSize(tamanioTabla);
            tblCarta.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
            tblCarta.setOpaque(false);
            tblCarta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
            jspTblCarta = new JScrollPane(tblCarta);

            //Aplicando propiedades al JScrollPane de la tabla
            jspTblCarta.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
            //Cambiando colores del fondo y de la barra de scroll
            jspTblCarta.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
            jspTblCarta.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical
        }
        

    }



    private void construirTabla(String[] titulos, JTable table, int tblIndex) {

        Object[][] data = new Object[0][0];
        modelo=new ModeloTabla(data, titulos);

        table.setDefaultRenderer(Object.class, new GestionCeldas());
        table.setForeground(Color.white);
        table.setModel(modelo);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);
        table.setRowHeight(25);
        table.setGridColor(new Color(0, 0, 0));


        if (tblIndex == 0){

            table.getColumnModel().getColumn(0).setMinWidth(110);
            table.getColumnModel().getColumn(1).setMinWidth(188);
            table.getColumnModel().getColumn(2).setMinWidth(223);
            table.getColumnModel().getColumn(3).setMinWidth(157);
            table.getColumnModel().getColumn(4).setMinWidth(133);
            table.getColumnModel().getColumn(5).setMinWidth(117);

        }else {
            table.getColumnModel().getColumn(0).setMinWidth(100);
            table.getColumnModel().getColumn(1).setMinWidth(175);
            table.getColumnModel().getColumn(2).setMinWidth(200);
            table.getColumnModel().getColumn(3).setMinWidth(110);
            table.getColumnModel().getColumn(4).setMinWidth(110);
            table.getColumnModel().getColumn(5).setMinWidth(80);
            table.getColumnModel().getColumn(6).setMinWidth(150);

        }


        //personaliza el encabezado
        JTableHeader jtableHeader = table.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        cambiarTamanioFuente(jtableHeader,20,Font.BOLD);
        table.setTableHeader(jtableHeader);

    }


    private void ajustarAnchoTabla(JTable tbl, JScrollPane jsp) {
        int alturaPreferida = jsp.getHeight();
        int alturaActual =  tbl.getRowCount()* tbl.getRowHeight();
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
    private void agregarFila(Object[] fila, JTable tbl, JScrollPane jsp) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.addRow(fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl,jsp);
    }

}

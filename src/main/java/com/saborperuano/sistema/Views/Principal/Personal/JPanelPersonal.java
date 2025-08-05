package com.saborperuano.sistema.Views.Principal.Personal;

import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogCarta.JDialogModificarCarta;
import com.saborperuano.sistema.Views.Principal.Personal.Dialog.JDialogEliminarTrabajador;
import com.saborperuano.sistema.Views.Principal.Personal.Dialog.JDialogModificarTrabajador;
import com.saborperuano.sistema.Views.Principal.Personal.Dialog.JDialogNuevoTrabajador;
import utils.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaPersonal;
import static utils.Utilidades.cambiarTamanioFuente;
import static utils.Utilidades.filtrarTabla;

public class JPanelPersonal extends JPanel {
    //Declarar atributors
    private JLabel lbltitulo;
    private JLabel lblBuscar;
    private JTextField txtBuscar;
    private JButton btnActualizarTabla;
    private JButton btnModificarTrabajador;
    private JButton btnAgregarTrabajador;
    private JButton btnEliminarTrabajador;
    private JTable tblPersonal;
    private JScrollPane jspTblPersonal;
    private ModeloTabla modelo;
    private Object[][] dataTable;
    private String[] cabecerasTablas;
    private ErrorLog errorLog;
    private DefaultTableModel model;

    //Creas el constructor

    public JPanelPersonal() {

        //Para colocarle el tamaño al JPanel
        setMinimumSize(new Dimension(1325, 900));
        setPreferredSize(new Dimension(1325, 900));
        setLayout(null);

        try {
            errorLog = new ErrorLog("src/main/resources/error.log");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Inicializar componentes
        lbltitulo = new JLabel("Personal");
        lbltitulo.setBounds(591, 44, 197, 35);
        cambiarTamanioFuente(lbltitulo, 45, Font.BOLD);
        add(lbltitulo);

        //Inicializar componentes
        lblBuscar = new JLabel("Buscar");
        lblBuscar.setBounds(494, 151, 76, 24);
        cambiarTamanioFuente(lblBuscar, 24, Font.PLAIN);
        add(lblBuscar);

        txtBuscar = new RoundedTextField(30, 3, 5);
        txtBuscar.setBounds(594, 143, 289, 40);
        cambiarTamanioFuente(txtBuscar, 24, Font.PLAIN);
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar,tblPersonal);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar,tblPersonal);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar,tblPersonal);
            }
        });

        //Inicializando cabeceras para la tabla
        cabecerasTablas = new String[]{"ID Trabajador", "Usuario","Nombres", "Apellido Paterno",
                "Apellido Materno","Teléfono", "Correo", "Cargo","Estado"};

        //Preparando la tabla con todo lo necesario
        prepararTablaPersonal(cabecerasTablas, new Dimension(1200,450));
        jspTblPersonal.setBounds(70,200,1200,450);
        jspTblPersonal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(jspTblPersonal);

        model = (DefaultTableModel) tblPersonal.getModel();

        llenarTblPersonal(model);

        //Alimentando la tabla de los datos extraídos de la BD


        //Inicializar componentes
        btnActualizarTabla = new RoundedButton("Actualizar Tabla", 3);
        btnActualizarTabla.setBounds(550, 687, 200, 48);
        cambiarTamanioFuente(btnActualizarTabla, 16, Font.BOLD);
        btnActualizarTabla.setBackground(Color.PINK);
        btnActualizarTabla.setForeground(Color.black);
        add(btnActualizarTabla);

        //Inicializar componentes
        btnModificarTrabajador = new RoundedButton("Modificar Trabajador", 3);
        btnModificarTrabajador.setBounds(210, 747, 200, 48);
        cambiarTamanioFuente(btnModificarTrabajador, 16, Font.BOLD);
        btnModificarTrabajador.setBackground(Color.ORANGE);
        btnModificarTrabajador.setForeground(Color.black);
        add(btnModificarTrabajador);

        //Inicializar componentes
        btnAgregarTrabajador = new RoundedButton("Agregar Trabajador", 3);
        btnAgregarTrabajador.setBounds(550, 747, 200, 48);
        cambiarTamanioFuente(btnAgregarTrabajador, 16, Font.BOLD);
        btnAgregarTrabajador.setBackground(Color.green);
        btnAgregarTrabajador.setForeground(Color.black);

        //Inicializar componentes
        btnEliminarTrabajador = new RoundedButton("Eliminar Trabajador", 3);
        btnEliminarTrabajador.setBounds(850, 747, 200, 48);
        cambiarTamanioFuente(btnEliminarTrabajador, 16, Font.BOLD);
        btnEliminarTrabajador.setBackground(Color.red);
        btnEliminarTrabajador.setForeground(Color.white);



        btnAgregarTrabajador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogNuevoTrabajador jDialogNuevoTrabajador = new JDialogNuevoTrabajador(jspTblPersonal,tblPersonal);
            }
        });


        btnModificarTrabajador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tblPersonal.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay filas por modificar");
                }else if (tblPersonal.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(null,"Debe elegir un registro para modificar");
                }else {
                    DefaultTableModel modeloPersonal = (DefaultTableModel) tblPersonal.getModel();
                    Object codigo = modeloPersonal.getValueAt(tblPersonal.getSelectedRow(),0);
                    EmpleadoDTO empleadoDTO = listaPersonal.obtenerObjetoPorCampo(listaPersonal,"codigo",codigo);

                    JDialogModificarTrabajador jDialogModificarCarta = new JDialogModificarTrabajador(empleadoDTO,
                            jspTblPersonal,tblPersonal);
                }            }
        });

        btnEliminarTrabajador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogEliminarTrabajador jDialogEliminarTrabajador = new JDialogEliminarTrabajador(tblPersonal);
            }
        });

        btnActualizarTabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablaPersonal();
            }
        });



        add(txtBuscar);
        add(btnAgregarTrabajador);
        add(btnEliminarTrabajador);
    }

    private void llenarTblPersonal(DefaultTableModel model) {
        // Obtener los objetos de la lista
        List<EmpleadoDTO> objetos = listaPersonal.getObjetos();

        //Vaciar la tabla
        model.setRowCount(0);

        // Llenar el modelo con los datos de la lista de objetos
        for (EmpleadoDTO objeto : objetos) {
            List<Object> fila = new ArrayList<>();

            fila.add(objeto.getCodigo());
            fila.add(objeto.getUsuario());
            fila.add(objeto.getNombre());
            fila.add(objeto.getApellidoPaterno());
            fila.add(objeto.getApellidoMaterno());
            fila.add(objeto.getTelefono());
            fila.add(objeto.getCorreo());
            fila.add(objeto.getCargo().name());
            fila.add(objeto.getEstado());


            model.addRow(fila.toArray());
        }
    }

    private void actualizarTablaPersonal() {
        listaPersonal = FrmPrincipal.empleadoController.listar();
        llenarTblPersonal(model);
    }

    private void prepararTablaPersonal(String[] cabecerasTablas, Dimension tamanioTabla){
        tblPersonal = new JTable() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(new Color(255, 128, 0)); // Cambiar el color de fondo de la tabla
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        //Personalizando las celdas y agregando las cabeceras a la tabla
        construirTabla(cabecerasTablas,tblPersonal);

        //Estableciendo propiedades a la tabla
        tblPersonal.setPreferredSize(tamanioTabla);
        tblPersonal.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        tblPersonal.setOpaque(false);
        tblPersonal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //Creando el JScrollPane en donde se colocará el JTable para poder ver todos los datos de manera scrolleable
        jspTblPersonal = new JScrollPane(tblPersonal);

        //Aplicando propiedades al JScrollPane de la tabla
        jspTblPersonal.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        //Cambiando colores del fondo y de la barra de scroll
        jspTblPersonal.getViewport().setBackground(new Color(255, 128, 0)); // Cambiar el color de fondo del viewport
        jspTblPersonal.getVerticalScrollBar().setBackground(new Color(0, 255, 4)); // Cambiar el color de fondo de la barra de desplazamiento vertical

    }




    private void construirTabla(String[] titulos,  JTable table) {

        Object[][] data = new Object[0][0];
        modelo=new ModeloTabla(data, titulos);

        tblPersonal.setDefaultRenderer(Object.class, new GestionCeldas());
        tblPersonal.setForeground(Color.white);
        tblPersonal.setModel(modelo);

        tblPersonal.getTableHeader().setReorderingAllowed(false);
        tblPersonal.getTableHeader().setResizingAllowed(false);
        tblPersonal.setRowHeight(25);
        tblPersonal.setGridColor(new Color(0, 0, 0));


        tblPersonal.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblPersonal.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblPersonal.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblPersonal.getColumnModel().getColumn(3).setPreferredWidth(140);
        tblPersonal.getColumnModel().getColumn(4).setPreferredWidth(140);
        tblPersonal.getColumnModel().getColumn(5).setPreferredWidth(80);
        tblPersonal.getColumnModel().getColumn(6).setPreferredWidth(200);
        tblPersonal.getColumnModel().getColumn(7).setPreferredWidth(130);


        //personaliza el encabezado
        JTableHeader jtableHeader = table.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        cambiarTamanioFuente(jtableHeader,20,Font.BOLD);
        table.setTableHeader(jtableHeader);

    }


    private void ajustarAnchoTabla(JTable tbl) {
        int alturaPreferida = jspTblPersonal.getHeight();
        int alturaActual =  tblPersonal.getRowCount()*tblPersonal.getRowHeight();
        if (alturaActual >= alturaPreferida){
            tblPersonal.setFillsViewportHeight(true);
            tblPersonal.setPreferredSize(new Dimension(tblPersonal.getPreferredSize().width,alturaActual));
            tblPersonal.revalidate();
            tblPersonal.repaint();
            jspTblPersonal.setPreferredSize(tblPersonal.getPreferredSize());
            jspTblPersonal.revalidate();
            jspTblPersonal.repaint();
        }
    }

    // Método para agregar una nueva fila a la tabla
    private void agregarFila(Object[] fila, JTable tbl) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tblPersonal.getModel();
        model.addRow(fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl);
    }

}

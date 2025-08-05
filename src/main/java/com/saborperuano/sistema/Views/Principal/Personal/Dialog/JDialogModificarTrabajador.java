package com.saborperuano.sistema.Views.Principal.Personal.Dialog;

import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Models.Enums.Cargo;
import com.saborperuano.sistema.Models.Enums.Categoria;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import utils.RoundedButton;
import utils.RoundedTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaPersonal;
import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogModificarTrabajador extends JDialog {
    private  JTextField txtNumerodeTelefono;
    private  JTextField txtDireccion;
    private  JLabel lblDatosIngreso;
    private  JTextField txtSalario;
    private  JLabel lblSalario;
    private  JLabel lblDatosPersonales;
    private  JLabel lblNombre;
    private  JTextField txtNombre;
    private  JLabel lblApellidoPaterno;
    private  JTextField txtApellidoPaterno;
    private  JLabel lblApellidoMaterno;
    private  JTextField txtApellidoMaterno;
    private  JLabel lblDNI;
    private  JTextField txtDNI;
    private  JLabel lblDireccion;
    //atributos
    private JLabel lbltitulo;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JLabel lblContrasena;
    private JTextField txtContrasena;

    private JLabel lblNumerodeTelefono;

    private JTextField txtCorreoElectronico;
    private JLabel lblCorreoElectronico;
    private JLabel lblCargo;
    private JComboBox cbCargo;
    private JButton btAgregarNuevoTrabajador;

    //Constructor
    public JDialogModificarTrabajador(EmpleadoDTO empleado, JScrollPane jsp, JTable tbl) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(520,700));
        setPreferredSize(new Dimension(520,700));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);


        //Inicializar componentes
        lbltitulo= new JLabel("Modificar Trabajador");
        lbltitulo.setBounds(150,23,250,30);
        cambiarTamanioFuente(lbltitulo,24,Font.BOLD);
        add(lbltitulo);


        //Inicializar componentes
        lblDatosPersonales= new JLabel("Datos Personales");
        lblDatosPersonales.setBounds(31,82,116,15);
        cambiarTamanioFuente(lblDatosPersonales,14,Font.PLAIN);
        add(lblDatosPersonales);

        //Inicializar componentes
        lblNombre= new JLabel("Nombre");
        lblNombre.setBounds(57,113,125,20);
        cambiarTamanioFuente(lblNombre,14,Font.PLAIN);
        add(lblNombre);

        txtNombre= new RoundedTextField(30,3,2);
        txtNombre.setBounds(208,108,243,25);
        cambiarTamanioFuente(txtNombre,16,Font.PLAIN);
        txtNombre.setText(empleado.getNombre());
        add(txtNombre);

        //Inicializar componentes
        lblApellidoPaterno= new JLabel("Apellido Paterno");
        lblApellidoPaterno.setBounds(57,147,125,20);
        cambiarTamanioFuente(lblApellidoPaterno,14,Font.PLAIN);
        add(lblApellidoPaterno);

        txtApellidoPaterno= new RoundedTextField(30,3,2);
        txtApellidoPaterno.setBounds(208,142,243,25);
        cambiarTamanioFuente(txtApellidoPaterno,16,Font.PLAIN);
        txtApellidoPaterno.setText(empleado.getApellidoPaterno());
        add(txtApellidoPaterno);

        //Inicializar componentes
        lblApellidoMaterno= new JLabel("Apellido Materno");
        lblApellidoMaterno.setBounds(57,182,125,20);
        cambiarTamanioFuente(lblApellidoMaterno,14,Font.PLAIN);
        add(lblApellidoMaterno);

        txtApellidoMaterno= new RoundedTextField(30,3,2);
        txtApellidoMaterno.setBounds(208,177,243,25);
        cambiarTamanioFuente(txtApellidoMaterno,16,Font.PLAIN);
        txtApellidoMaterno.setText(empleado.getApellidoMaterno());
        add(txtApellidoMaterno);

        //Inicializar componentes
        lblDNI= new JLabel("Documento de Identidad(DNI)");
        lblDNI.setBounds(57,215,125,20);
        cambiarTamanioFuente(lblDNI,14,Font.PLAIN);
        add(lblDNI);

        txtDNI= new RoundedTextField(30,3,2);
        txtDNI.setBounds(208,210,243,25);
        cambiarTamanioFuente(txtDNI,16,Font.PLAIN);
        txtDNI.setText(String.valueOf(empleado.getDni()));
        add(txtDNI);


        //Inicializar componentes
        lblDireccion= new JLabel("Dirección Domiciliaria");
        lblDireccion.setBounds(57,253,125,20);
        cambiarTamanioFuente(lblDireccion,14,Font.PLAIN);
        add(lblDireccion);

        txtDireccion= new RoundedTextField(30,3,2);
        txtDireccion.setBounds(208,243,243,25);
        cambiarTamanioFuente(txtDireccion,16,Font.PLAIN);
        txtDireccion.setText(empleado.getDireccion());
        add(txtDireccion);

        //Inicializar componentes
        lblCorreoElectronico= new JLabel("Correo Electrónico");
        lblCorreoElectronico.setBounds(57,286,125,20);
        cambiarTamanioFuente(lblCorreoElectronico,14,Font.PLAIN);
        add(lblCorreoElectronico);

        txtCorreoElectronico= new RoundedTextField(30,3,2);
        txtCorreoElectronico.setBounds(208,281,243,25);
        cambiarTamanioFuente(txtCorreoElectronico,16,Font.PLAIN);
        txtCorreoElectronico.setText(empleado.getCorreo());
        add(txtCorreoElectronico);


        //Inicializar componentes
        lblNumerodeTelefono= new JLabel("Teléfono");
        lblNumerodeTelefono.setBounds(57,319,125,20);
        cambiarTamanioFuente(lblNumerodeTelefono,14,Font.PLAIN);
        add(lblNumerodeTelefono);

        txtNumerodeTelefono= new RoundedTextField(30,3,2);
        txtNumerodeTelefono.setBounds(208,314,243,25);
        cambiarTamanioFuente(txtNumerodeTelefono,16,Font.PLAIN);
        txtNumerodeTelefono.setText(String.valueOf(empleado.getTelefono()));
        add(txtNumerodeTelefono);

        //Inicializar componentes
        lblSalario= new JLabel("Salario");
        lblSalario.setBounds(57,354,125,20);
        cambiarTamanioFuente(lblSalario,14,Font.PLAIN);
        add(lblSalario);

        txtSalario= new RoundedTextField(30,3,2);
        txtSalario.setBounds(208,349,243,25);
        cambiarTamanioFuente(txtSalario,16,Font.PLAIN);
        txtSalario.setText(String.valueOf(empleado.getSalario()));
        add(txtSalario);


        //Inicializar componentes
        lblCargo= new JLabel("Cargo");
        lblCargo.setBounds(57,390,125,20);
        cambiarTamanioFuente(lblCargo,14,Font.PLAIN);
        add(lblCargo);


        cbCargo= new JComboBox();
        cbCargo.setBounds(208,385,243,23);
        cambiarTamanioFuente(cbCargo,16,Font.PLAIN);
        Cargo[] cargos = Cargo.values();
        for (int i = 0; i < cargos.length; i++) {
            cbCargo.addItem(cargos[i]);
        }
        cbCargo.setSelectedIndex(empleado.getCargo().ordinal());
        add(cbCargo);


        //Inicializar componentes
        lblDatosIngreso= new JLabel("Datos de Ingreso");
        lblDatosIngreso.setBounds(31,447,125,20);
        cambiarTamanioFuente(lblDatosIngreso,14,Font.PLAIN);
        add(lblDatosIngreso);

        lblUsuario= new JLabel("Usuario");
        lblUsuario.setBounds(57,493,125,20);
        cambiarTamanioFuente(lblUsuario,14,Font.PLAIN);
        add(lblUsuario);

        txtUsuario= new RoundedTextField(30,3,2);
        txtUsuario.setBounds(208,488,243,25);
        cambiarTamanioFuente(txtUsuario,16,Font.PLAIN);
        txtUsuario.setText(empleado.getUsuario());
        add(txtUsuario);


        lblContrasena = new JLabel("Contraseña");
        lblContrasena.setBounds(57,525,125,20);
        cambiarTamanioFuente(lblContrasena,14,Font.PLAIN);
        add(lblContrasena);

        txtContrasena = new RoundedTextField(30,3,2);
        txtContrasena.setBounds(208,520,243,25);
        cambiarTamanioFuente(txtContrasena,16,Font.PLAIN);
        txtContrasena.setText(empleado.getContrasena());
        add(txtContrasena);



        //Inicializar componentes
        btAgregarNuevoTrabajador= new RoundedButton("Modificar Trabajador",3);
        btAgregarNuevoTrabajador.setBounds(159,587,200,38);
        cambiarTamanioFuente(btAgregarNuevoTrabajador,16,Font.PLAIN);
        btAgregarNuevoTrabajador.setForeground(Color.black);
        btAgregarNuevoTrabajador.setBackground(Color.orange);
        btAgregarNuevoTrabajador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //1. Capturar data y modificar al Empleado DTO
                if (camposValidos()){


                            empleado.setUsuario(txtUsuario.getText());
                            empleado.setContrasena(txtContrasena.getText());
                            empleado.setNombre(txtNombre.getText());
                            empleado.setApellidoPaterno(txtApellidoPaterno.getText());
                            empleado.setApellidoMaterno(txtApellidoMaterno.getText());
                            empleado.setDireccion(txtDireccion.getText());
                            empleado.setContrasena(txtContrasena.getText());
                            empleado.setDni(Integer.parseInt(txtDNI.getText()));
                            empleado.setTelefono(Integer.parseInt(txtNumerodeTelefono.getText()));
                            empleado.setSalario(Double.parseDouble(txtSalario.getText()));
                            empleado.setCargo(Cargo.valueOf(Objects.requireNonNull(cbCargo.getSelectedItem()).toString()));


                    //2.Mandar esa data a la bd mediante el controller

                    try {
                        FrmPrincipal.empleadoController.modificar(empleado);

                        //4. Mandar sus atributos a un arreglo y añadirlo como fila
                        Object[] fila = {
                                empleado.getCodigo(),
                                empleado.getUsuario(),
                                empleado.getNombre(),
                                empleado.getApellidoPaterno(),
                                empleado.getApellidoMaterno(),
                                empleado.getTelefono(),
                                empleado.getCorreo(),
                                empleado.getCargo(),
                                empleado.getEstado()
                        };

                       insertarFila(tbl.getSelectedRow(), fila,tbl,jsp);



                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,"Ocurrió un error al agregar el nuevo empleado: "+ ex.getMessage());
                    }

                    System.out.println();
                    System.out.println("Lista Enlazada de Personal Actualizada");
                    listaPersonal.mostrarLista();
                    System.out.println();

                    dispose();

                }else {
                    JOptionPane.showMessageDialog(null,"Debe completar todos los campos");
                }


            }
        });




        add(btAgregarNuevoTrabajador);
        setVisible(true);
    }

    private boolean camposValidos() {
        if (
                !txtNumerodeTelefono.getText().isEmpty() &&
                        !txtDireccion.getText().isEmpty() &&
                        !txtSalario.getText().isEmpty() &&
                        !txtNombre.getText().isEmpty() &&
                        !txtApellidoMaterno.getText().isEmpty() &&
                        !txtApellidoPaterno.getText().isEmpty() &&
                        !txtDNI.getText().isEmpty() &&
                        !txtUsuario.getText().isEmpty() &&
                         !txtContrasena.getText().isEmpty() &&
                        !txtCorreoElectronico.getText().isEmpty() &&
                        cbCargo.getSelectedIndex() != -1
        ){
            return true;
        }
        return false;
    }

    private void insertarFila(int nroFila, Object[] fila, JTable tbl, JScrollPane jsp) {
        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.removeRow(nroFila);
        model.insertRow(nroFila,fila);

        //Ajustando tamaño de la tabla
        ajustarAnchoTabla(tbl,jsp);
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

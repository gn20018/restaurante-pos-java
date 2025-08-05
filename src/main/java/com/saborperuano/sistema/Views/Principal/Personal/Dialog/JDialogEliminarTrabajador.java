package com.saborperuano.sistema.Views.Principal.Personal.Dialog;

import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.*;
import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogEliminarTrabajador extends JDialog{

    private JLabel lblTitulo;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public JDialogEliminarTrabajador(JTable tbl) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(500,224));
        setPreferredSize(new Dimension(500,224));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes

        lblTitulo= new JLabel("¿Estás seguro de eliminar al trabajador seleccionado?");
        lblTitulo.setBounds(40,25,450,69);
        cambiarTamanioFuente(lblTitulo,16,Font.BOLD);
        add(lblTitulo);

        btnAceptar= new JButton("Aceptar");
        btnAceptar.setBounds(62,130,105,25);
        cambiarTamanioFuente(btnAceptar,12,Font.PLAIN);
        btnCancelar= new JButton("Cancelar");
        btnCancelar.setBounds(255,130,105,25);
        cambiarTamanioFuente(btnAceptar,12,Font.PLAIN);


        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //1. Validar usuario seleccionado

                if (tbl.getSelectedRow() != -1 && tbl.getRowCount() != 0){
                    //2. Extraer id
                    int filaSeleccionada = tbl.getSelectedRow();
                    try {
                        DefaultTableModel model = (DefaultTableModel) tbl.getModel();

                        //3. Deshabilitarlo de la bd
                        int idEmpleado = Integer.parseInt(String.valueOf(model.getValueAt(filaSeleccionada,0)));
                        empleadoController.eliminar(idEmpleado);



                        //4. actualizar tabla

                        EmpleadoDTO registroEliminado = listaPersonal.obtenerObjetoPorCampo(listaPersonal,"codigo",idEmpleado);

                        registroEliminado.setEstado(String.valueOf(EstadoRegistro.Deshabilitado));

                        model.setValueAt(registroEliminado.getEstado(),filaSeleccionada,tbl.getColumnCount()-1);


                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,"Ocurrió un error: "+ex.getMessage());
                        throw new RuntimeException(ex);
                    }

                }

            }
        });




        add(btnAceptar);
        add(btnCancelar);

        setVisible(true);
    }


}

package com.saborperuano.sistema.Views.Principal.MenuCarta.Dialog.DialogCarta;

import com.saborperuano.sistema.Controllers.CartaController;
import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaCarta;
import static utils.Utilidades.cambiarTamanioFuente;

public class JDialogEliminarCarta extends JDialog{

    private JLabel lblTitulo;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public JDialogEliminarCarta(JTable tbl, CartaController cartaController) {
        //Para colcoarle el tamaño al JPanel
        setMinimumSize(new Dimension(500,224));
        setPreferredSize(new Dimension(500,224));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        //Inicializar componentes

        lblTitulo= new JLabel("¿Estás seguro de eliminar el registro seleccionado?");
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

                DefaultTableModel model = (DefaultTableModel) tbl.getModel();
                int filaSeleccionada = -1;
                filaSeleccionada = tbl.getSelectedRow();

                if (filaSeleccionada != -1){
                    try {
                        int codigoCarta = Integer.parseInt(String.valueOf(model.getValueAt(filaSeleccionada,0)));
                        cartaController.eliminar(codigoCarta);
                        CartaDTO registroEliminado = listaCarta.obtenerObjetoPorCampo(listaCarta,"Codigo",codigoCarta);

                        registroEliminado.setEstado(EstadoRegistro.Deshabilitado);

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

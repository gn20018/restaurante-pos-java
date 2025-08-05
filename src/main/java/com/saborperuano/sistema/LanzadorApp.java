package com.saborperuano.sistema;

import com.saborperuano.sistema.Views.Login.FrmLogin;

import javax.swing.*;

public class LanzadorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear una instancia del JFrame en el hilo de despacho de eventos de Swing
            FrmLogin frame = new FrmLogin();
        });
    }
}

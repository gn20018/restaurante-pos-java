package com.saborperuano.sistema.Views.Principal.Configuracion;

import com.saborperuano.sistema.DAO.ConexionBD;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import utils.RoundedButton;
import utils.RoundedTextField;
import utils.Utilidades;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class JPanelConfiguracion extends JPanel {
    private JLabel lblTitulo;
    private JLabel lblNombreTienda;
    private JLabel lblDireccion;
    private JLabel lblLogoEmpresa;
    private JLabel lblColorFondo;
    private JLabel lblColorBarraLateral;
    private JLabel lblLogo;
    private JTextField txtNombreTienda;
    private JTextField txtDireccion;
    private JTextField txtColorFondo;
    private JTextField txtColorBarra;
    private JButton btnLogoEmpresa;
    private JButton btnColorFondo;
    private JButton btnColorBarra;
    private JButton btnRealizarCopiaSeguridad;
    private JButton btnGuardarCambios;
    private JButton btnRestaurarCopiaSeguridad;

    public JPanelConfiguracion() {
        setPreferredSize(new Dimension(1325, 900));
        setLayout(null);

        //Inicializar los elementos

        lblTitulo = new JLabel("Configuración");
        Utilidades.cambiarTamanioFuente(lblTitulo, 50, Font.BOLD);
        lblTitulo.setBounds(486, 60, 346, 60);

        lblNombreTienda = new JLabel("Nombre del restaurante: ");
        Utilidades.cambiarTamanioFuente(lblNombreTienda, 30, Font.PLAIN);
        lblNombreTienda.setBounds(104, 252, 330, 40);

        lblDireccion = new JLabel("Dirección: ");
        Utilidades.cambiarTamanioFuente(lblDireccion, 30, Font.PLAIN);
        lblDireccion.setBounds(104, 300, 180, 40);

        lblLogoEmpresa = new JLabel("Logo de la Empresa: ");
        Utilidades.cambiarTamanioFuente(lblLogoEmpresa, 30, Font.PLAIN);
        lblLogoEmpresa.setBounds(104, 351, 330, 40);

        lblLogo = new JLabel("Logo.png");
        Utilidades.cambiarTamanioFuente(lblLogo, 15, Font.PLAIN);
        lblLogo.setBounds(655, 351, 330, 35);

        lblColorFondo = new JLabel("Color de Fondo: ");
        Utilidades.cambiarTamanioFuente(lblColorFondo, 30, Font.PLAIN);
        lblColorFondo.setBounds(104, 402, 280, 40);

        lblColorBarraLateral = new JLabel("Color de Barra Lateral: ");
        Utilidades.cambiarTamanioFuente(lblColorBarraLateral, 30, Font.PLAIN);
        lblColorBarraLateral.setBounds(104, 455, 300, 40);


        txtNombreTienda = new RoundedTextField(FrmPrincipal.nombreEmpresa, 5);
        Utilidades.cambiarTamanioFuente(txtNombreTienda, 15, Font.PLAIN);
        txtNombreTienda.setBounds(500, 252, 359, 35);

        txtDireccion = new RoundedTextField(FrmPrincipal.direccionEmpresa, 5);
        Utilidades.cambiarTamanioFuente(txtDireccion, 15, Font.PLAIN);
        txtDireccion.setBounds(500, 300, 514, 35);

        txtColorFondo = new RoundedTextField("#" + FrmPrincipal.colorFondoPaneles, 5);
        Utilidades.cambiarTamanioFuente(txtColorFondo, 15, Font.PLAIN);
        txtColorFondo.setBounds(500, 402, 120, 35);

        txtColorBarra = new RoundedTextField("#" + FrmPrincipal.colorBarra, 5);
        Utilidades.cambiarTamanioFuente(txtColorBarra, 15, Font.PLAIN);
        txtColorBarra.setBounds(500, 455, 120, 35);


        btnLogoEmpresa = new RoundedButton("Buscar Imagen", 5);
        Utilidades.cambiarTamanioFuente(btnLogoEmpresa, 15, Font.PLAIN);
        btnLogoEmpresa.setBounds(500, 351, 140, 35);

        btnColorFondo = new RoundedButton("Editar Fondo", 5);
        Utilidades.cambiarTamanioFuente(btnColorFondo, 15, Font.PLAIN);
        btnColorFondo.setBounds(650, 402, 140, 35);

        btnColorBarra = new RoundedButton("Editar Barra Lateral", 5);
        Utilidades.cambiarTamanioFuente(btnColorBarra, 15, Font.PLAIN);
        btnColorBarra.setBounds(650, 455, 180, 35);

        btnRealizarCopiaSeguridad = new RoundedButton("Realizar Copia de Seguridad", 5);
        Utilidades.cambiarTamanioFuente(btnRealizarCopiaSeguridad, 15, Font.PLAIN);
        btnRealizarCopiaSeguridad.setBounds(250, 550, 250, 30);
        btnRealizarCopiaSeguridad.setBackground(new Color(0x28FE05));

        btnGuardarCambios = new RoundedButton("Guardar Cambios", 5);
        Utilidades.cambiarTamanioFuente(btnGuardarCambios, 15, Font.PLAIN);
        btnGuardarCambios.setBounds(600, 650, 160, 30);
        btnGuardarCambios.setBackground(new Color(0x28FE05));

        btnRestaurarCopiaSeguridad = new RoundedButton("Restaurar Copia de Seguridad", 5);
        Utilidades.cambiarTamanioFuente(btnRestaurarCopiaSeguridad, 15, Font.PLAIN);
        btnRestaurarCopiaSeguridad.setBounds(850, 550, 250, 30);
        btnRestaurarCopiaSeguridad.setBackground(new Color(0x28FE05));


        add(lblTitulo);
        add(lblNombreTienda);
        add(lblDireccion);
        add(lblLogoEmpresa);
        add(lblColorFondo);
        add(lblLogo);
        add(lblColorBarraLateral);

        add(txtNombreTienda);
        add(txtDireccion);
        add(txtColorFondo);
        add(txtColorBarra);


        btnLogoEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Ruta = "";

                JFileChooser jFileChooser = new JFileChooser();

                FileNameExtensionFilter filtrado = new FileNameExtensionFilter("PNG", "png");
                jFileChooser.setFileFilter(filtrado);

                int respuesta = jFileChooser.showOpenDialog(null);

                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    Ruta = jFileChooser.getSelectedFile().getPath();

                    Image mImagen = new ImageIcon(Ruta).getImage();
                    ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(FrmPrincipal.lblImagenEmpleado.getWidth(),
                            FrmPrincipal.lblImagenEmpleado.getHeight(), Image.SCALE_DEFAULT));
                    lblLogo.setText(Ruta);
                    FrmPrincipal.lblImagenEmpleado.setIcon(mIcono);
                }
            }
        });


        btnColorBarra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color barra = JColorChooser.showDialog(null, "Seleccione Color", Color.black);
                FrmPrincipal.colorBarraLateral = barra;
                FrmPrincipal.repintarPanel(FrmPrincipal.jpBarraLateral, FrmPrincipal.colorBarraLateral);
                txtColorBarra.setText(obtenerCodigoHexadecimal(barra));

            }
        });

        btnColorFondo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color fondo = JColorChooser.showDialog(null, "Seleccione Color", Color.black);
                FrmPrincipal.colorFondoPanel = fondo;
                setBackground(fondo);
                revalidate();
                repaint();

                txtColorFondo.setText(obtenerCodigoHexadecimal(fondo));
            }
        });


        btnGuardarCambios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Properties prop = new Properties();
                try (FileInputStream fis = new FileInputStream("src/main/resources/properties.properties")) {
                    prop.load(fis);

                    prop.setProperty("empresa", txtNombreTienda.getText());
                    prop.setProperty("direccion", txtDireccion.getText());
                    prop.setProperty("colorFondo", txtColorFondo.getText().substring(1));
                    prop.setProperty("colorBarraLateral", txtColorBarra.getText().substring(1));
                    prop.setProperty("imagenLogo", lblLogo.getText());

                    try (FileOutputStream fos = new FileOutputStream("src/main/resources/properties.properties")) {
                        prop.store(fos, "Cambios Guardados");
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnRealizarCopiaSeguridad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerCopiaSeguridad();
            }
        });

        btnRestaurarCopiaSeguridad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurarCopiaSeguridad();
            }
        });


        add(btnLogoEmpresa);
        add(btnColorFondo);
        add(btnColorBarra);
        add(btnRealizarCopiaSeguridad);
        add(btnGuardarCambios);
        add(btnRestaurarCopiaSeguridad);
    }

    public static String obtenerCodigoHexadecimal(Color color) {
        String codigoHex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        return codigoHex;
    }

    public static void hacerCopiaSeguridad() {
        try {

            //Cargando propiedades del sistemas
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/properties.properties");
            prop.load(fis);

            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            String dbname = prop.getProperty("db.name");
            String rutaArchivoBackup = prop.getProperty("db.backup");
            String mysqldumpPath = prop.getProperty("db.mysqldumpPath");


            Process process = Runtime.getRuntime().exec(mysqldumpPath+"mysqldump -u "+username+" -p"+password+" "+dbname);

            // Redirigir entrada (enviar la contraseña)
            OutputStream outputStream = process.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.newLine();
            writer.flush();
            writer.close();

            // Redirigir salida (guardar en el archivo)
            InputStream inputStream = process.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(rutaArchivoBackup);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                JOptionPane.showMessageDialog(null,"Copia de seguridad completada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null,"Error al hacer la copia de seguridad. Código de salida: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void restaurarCopiaSeguridad() {
        try {
            Properties prop = new Properties();
            try (FileInputStream fis = new FileInputStream("src/main/resources/properties.properties")) {
                prop.load(fis);

                String username = prop.getProperty("db.username");
                String password = prop.getProperty("db.password");
                String dbname = prop.getProperty("db.name");
                String rutaArchivoBackup = prop.getProperty("db.backup");
                String mysqldumpPath = prop.getProperty("db.mysqldumpPath");

                String command = String.format(mysqldumpPath+"mysql -u %s -p%s %s < "+rutaArchivoBackup,username,password,dbname);
                Process process = Runtime.getRuntime().exec(command);

                // Esperar a que termine el proceso
                process.waitFor();

                JOptionPane.showMessageDialog(null,"Copia de seguridad restaurada correctamente.");

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
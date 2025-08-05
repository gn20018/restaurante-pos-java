package com.saborperuano.sistema.Views.Login;

import com.formdev.flatlaf.*;
import com.saborperuano.sistema.Controllers.EmpleadoController;
import com.saborperuano.sistema.DAO.ConexionBD;
import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import utils.ListaEnlazadaGenerica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.HashMap;

public class FrmLogin extends JFrame {
    private  JTextField userTextField;
    private  JPasswordField passwordField;
    private ConexionBD conectarbd;
    private boolean conectadaBD;
    private JLayeredPane jpPrincipal;
    private EmpleadoController empleadoController = new EmpleadoController();
    private ListaEnlazadaGenerica<EmpleadoDTO> listaEmpleados;

    public FrmLogin() {

        conectadaBD = false;

        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf() );
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/fonts/Amaranth/Amaranth-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        UIManager.put( "Button.arc",10);
        UIManager.put( "TextComponent.arc", 10 );


            conectarbd = new ConexionBD();
            conectadaBD = conectarbd.isConectionSuccess();

            if (conectadaBD){
                setUndecorated(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBackground(new Color(0, 0, 0, 0));

                jpPrincipal = new JLayeredPane(){
                    @Override
                    public void paint(Graphics g) {
                        // Dibuja el borde redondeado alrededor del JLayeredPane.
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Pinta el contorno con un borde redondeado.
                        RoundRectangle2D roundedBorderShape = new RoundRectangle2D.Float(0, 0,
                                getWidth() - 1 ,getHeight()-1 , 30 * 2 ,30 * 2 );

                        // Dibuja el borde alrededor de la forma redondeada.
                        g.setColor(getForeground());
                        ((Graphics2D)g).draw(roundedBorderShape);
                        g.setClip(roundedBorderShape);

                        //Extrayendo imagen de carpeta de recursos y dibujandola como fondo
                        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/frmLogin/fongoLogin.png"));
                        g.drawImage(imagen.getImage(),0,0,getWidth(),getHeight(),this);
                        setOpaque(false);


                        super.paint(g);

                    }
                };
                jpPrincipal.setPreferredSize(new Dimension(700,400));

                // Etiqueta de Frase
                JLabel frase = new JLabel();
                frase.setText("<html><center>¡Descubre los mejores sabores de Perú en <br> cada bocado!</center></html>");
                frase.setHorizontalTextPosition(JLabel.CENTER);
                frase.setVerticalTextPosition(JLabel.CENTER);
                frase.setVerticalAlignment(JLabel.CENTER);
                frase.setHorizontalAlignment(JLabel.CENTER);
                frase.setFont(new Font("Amaranth", Font.BOLD, 16));
                frase.setForeground(Color.white);
                frase.setBounds(20,320,400,50);
                jpPrincipal.add(frase);


                //Creando JPanel con el degradado de Rojo y blanco y posicionandolo a la derecha
                JPanel overlayPanel = new DegradadoFondo();
                overlayPanel.setBounds(400,0,300,400);

                //Creadno JPanel para los elementos del formulario y posicionandolo encima del degradado
                JPanel formulario  = new JPanel();
                formulario.setBounds(400,0,300,400);
                formulario.setOpaque(false);
                formulario.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                formulario.add( Box.createRigidArea(new Dimension(200,40)));



                // Botón de cerrar personalizado
                JButton btnCerrar = new JButton();
                btnCerrar.setFont(new Font("Amaranth", Font.BOLD, 16));
                btnCerrar.setText("X");
                btnCerrar.setBackground(new Color(0,0,0,0));
                formulario.add(btnCerrar);

                // Icono de la empresa
                JLabel logo = new JLabel();
                logo.setIcon(new ImageIcon(getClass().getResource("/img/frmLogin/logoEmpresa.png")));
                formulario.add(logo);
                formulario.add( Box.createRigidArea(new Dimension(300,0)));



                // Etiqueta "Iniciar sesion"
                JLabel titulo = new JLabel("Iniciar Sesión");
                titulo.setFont(new Font("Amaranth", Font.BOLD, 16));
                formulario.add(titulo);
                formulario.add( Box.createRigidArea(new Dimension(300,15)));

                // Etiqueta "Usuario"
                JLabel userLabel = new JLabel("Usuario");
                userLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
                formulario.add(userLabel);
                formulario.add(Box.createRigidArea(new Dimension(300,10)));


                // TextField para el nombre de usuario
                userTextField = new JTextField(15);
                formulario.add(userTextField);
                formulario.add( Box.createRigidArea(new Dimension(300,10)));


                // Etiqueta "Contraseña"
                JLabel passwordLabel = new JLabel("Contraseña");
                passwordLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
                formulario.add(passwordLabel);
                formulario.add(Box.createRigidArea(new Dimension(300,10)));

                // TextField para la contraseña
                passwordField = new JPasswordField(15);
                formulario.add(passwordField);
                formulario.add(Box.createRigidArea(new Dimension(300,15)));

                // Botón "Ingresar"
                JButton loginButton = new JButton("Ingresar");
                loginButton.setFont(new Font("Amaranth", Font.PLAIN, 15));
                loginButton.setPreferredSize(new Dimension(125,30));
                formulario.add(loginButton);

                jpPrincipal.add(formulario, JLayeredPane.PALETTE_LAYER);
                jpPrincipal.add(overlayPanel, JLayeredPane.PALETTE_LAYER);



                listaEmpleados =  empleadoController.listar();



                btnCerrar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btnCerrar.setBackground(Color.red);
                        btnCerrar.setForeground(Color.white);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        btnCerrar.setBackground(new Color(0,0,0,0));
                        btnCerrar.setForeground(Color.black);
                    }
                });

                btnCerrar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });


                loginButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (verificarCredenciales()){

                            dispose();

                            UIManager.put("Label.font", new Font("Amaranth", Font.PLAIN, 14));
                            UIManager.put("Button.font", new Font("Amaranth", Font.PLAIN, 14));
                            FrmPrincipal frm = new FrmPrincipal();
                        }else {
                            JOptionPane.showMessageDialog(null,"Usuario o Contraseña Incorrectos");
                        }

                    }
                });



                addDraggableMouseListener(jpPrincipal);

                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/img/frmLogin/logoEmpresa.png"));
                setTitle("Login Sabor Peruano");
                setIconImage(icon.getImage());

                jpPrincipal.repaint();
                setContentPane(jpPrincipal);
                pack();
                setLocationRelativeTo(null);
                setResizable(false);
                setVisible(true);
            }else {
                JOptionPane.showMessageDialog(this,"Ocurrió un error al conectarse a la BD");
            }


    }

    private boolean verificarCredenciales() {
        HashMap<String,String> credenciales =  listaEmpleados.obtenerHashMapUsuariosContrasenas("usuario","contrasena");

        for (String usuario : credenciales.keySet()) {
            String contrasena = credenciales.get(usuario);


            char[] passwordChars = passwordField.getPassword();

            // Convertir el array de caracteres a una cadena de texto
            String password = new String(passwordChars);


            if (usuario.equals(userTextField.getText().strip()) && contrasena.equals(password.strip())){

                FrmPrincipal.empleadoActual = listaEmpleados.obtenerObjetoPorCampo(listaEmpleados,"usuario",usuario);

                return true;
            }
        }
        return false;
    }


    public void paint(Graphics g) {
        // Dibuja primero todos los componentes del marco.
        super.paint(g);

        // Dibuja el borde redondeado alrededor del JLayeredPane.
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pinta el contorno con un borde redondeado.
        RoundRectangle2D roundedBorderShape = new RoundRectangle2D.Float(0, 0,
                getWidth() - 1 ,getHeight()-1 , 30 * 2 ,30 * 2 );

        // Dibuja el borde alrededor de la forma redondeada.
        g.setColor(getForeground());
        ((Graphics2D)g).draw(roundedBorderShape);

    }
    private void addDraggableMouseListener(Component component) {
        MouseAdapter draggableMouseAdapter = new MouseAdapter() {
            private int xMouse, yMouse;

            @Override
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();

                setLocation(x - xMouse, y - yMouse);
            }
        };

        component.addMouseListener(draggableMouseAdapter);
        component.addMouseMotionListener(draggableMouseAdapter);

    }
    public class DegradadoFondo extends JPanel{

        private Color color1 = new Color(255,255,255,(int) Math.round((73.0 / 100.0) * 255));
        private Color color2 = new Color(252,62,62,(int) Math.round((96.0 / 100.0) * 255));

        public DegradadoFondo(){
            setOpaque(false);
        }

        @Override
        public void paint(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0,0,color1,getWidth(),getHeight(),color2);
            Rectangle2D rectangle = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
            g2d.setPaint(gp);
            g2d.fill(rectangle);
        }

    }

}

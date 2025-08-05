package com.saborperuano.sistema.Views.Principal;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.saborperuano.sistema.Controllers.CartaController;
import com.saborperuano.sistema.Controllers.DetalleVentaController;
import com.saborperuano.sistema.Controllers.EmpleadoController;
import com.saborperuano.sistema.Controllers.VentaController;
import com.saborperuano.sistema.Models.*;
import com.saborperuano.sistema.Models.DTO.*;
import com.saborperuano.sistema.Views.Login.FrmLogin;
import com.saborperuano.sistema.Views.Principal.Configuracion.JPanelConfiguracion;
import com.saborperuano.sistema.Views.Principal.Estadisticas.JPanelEstadisticas;
import com.saborperuano.sistema.Views.Principal.Inventario.JPanelInventario;
import com.saborperuano.sistema.Views.Principal.Personal.JPanelPersonal;
import com.saborperuano.sistema.Views.Principal.MenuCarta.JPanelMenuCarta;
import com.saborperuano.sistema.Views.Principal.Ventas.JPanelVentas;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FrmPrincipal extends JFrame {
    public static JPanel jpPrincipal;
    public static JPanel jpBarraLateral;
    private JPanel jpOpcVentas;
    private JPanel jpOpcProductos;
    private JPanel jpOpcPersonal;
    private JPanel jpOpcEstadisticas;
    private JPanel jpOpcConfiguracion;
    private JPanel[] contenidos;
    private JLabel lblOpcVentas;
    private JLabel lblOpcProductos;
    private JLabel lblOpcPersonal;
    private JLabel lblOpcEstadisticas;
    private JLabel lblOpcConfiguracion;
    public static JPanel jpContenido;
    public static JLabel lblImagenEmpleado;
    private JLabel lblNombreEmpleado;
    private JLabel lblCargo;

    private RoundedButton btnCerrarTurno;
    private JButton btnMinimizar;
    private JButton btnMaximizar;
    private JButton btnCerrar;

    private int OpcSeleccionada;
    private int anteriorOpcSeleccionada;
    public static Color colorBarraLateral;
    public static Color colorFondoPanel;
    private boolean isMaximized = false;
    private int previousState;


    //Declarando Variables Globales
    private ErrorLog errorLog;
    public static EmpleadoController empleadoController;
    public static CartaController cartaController;
    public static VentaController ventaController;
    public static DetalleVentaController detalleVentaController;
    public static ListaEnlazadaGenerica<MenuDTO> listaMenu;
    public static ListaEnlazadaGenerica<CartaDTO> listaCarta;

    public static ListaEnlazadaGenerica<EmpleadoDTO> listaPersonal;
    public static ListaEnlazadaGenerica<Insumo> listaInventario;
    public static ListaEnlazadaGenerica<VentaDTO> listaVentas;
    public static ListaEnlazadaGenerica<DetalleVentaDTO> listaDetallesVenta;

    public static VentaDTO ventaActual;
    public static EmpleadoDTO empleadoActual;

    public static String nombreEmpresa;
    public static String direccionEmpresa;
    public static String colorFondoPaneles;
    public static String colorBarra;

    public FrmPrincipal() {

        //Estableciendo el LookAndFeel de la librería FlatLaf
        try {
            //Aplicando Look and feel
            UIManager.setLookAndFeel(new FlatIntelliJLaf());

            //Aplicando fuente personalizada a todo el proyecto
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/fonts/Amaranth/Amaranth-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            setTitle("SISTEMA SABOR PERUANO");

        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/img/frmLogin/logoEmpresa.png"));
        setTitle("Sistema Sabor Peruano");
        setIconImage(icon.getImage());


        //Inicializando Log de Errores
        try {
            errorLog = new ErrorLog("src/main/resources/error.log");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Cargando propiedades del sistemas
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/properties.properties")) {
            prop.load(fis);

            nombreEmpresa = prop.getProperty("empresa");
            direccionEmpresa = prop.getProperty("direccion");
            colorFondoPaneles= prop.getProperty("colorFondo");
            colorBarra= prop.getProperty("colorBarraLateral");

        } catch (IOException e) {
            e.printStackTrace();
        }




        //Inicializar Variables Globales
        listaMenu = new ListaEnlazadaGenerica<>();
        listaInventario=new ListaEnlazadaGenerica<>();

        empleadoController = new EmpleadoController();
        cartaController = new CartaController();
        ventaController = new VentaController();
        detalleVentaController = new DetalleVentaController();

        listaCarta = cartaController.listar();
        listaPersonal = empleadoController.listar();
        listaVentas = ventaController.listar();
        ventaActual = null;

//      


// InsumosDAO insumoDao = new InsumoDAO();
//   listaInventario =   insumoDao.listarInsumos()

        //Estableciendo propiedas del JFrame
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        setMinimumSize(new Dimension(1600, 900));
        setPreferredSize(new Dimension(1600, 900));

        //Inicializando componentes
        //Inicializando JPanel Principal
        jpPrincipal = new JPanel();
        jpPrincipal.setMinimumSize(new Dimension(1600, 900));
        jpPrincipal.setPreferredSize(new Dimension(1600, 900));
        jpPrincipal.setLayout(null);


        //Inicializando JPanel de la Barra Lateral
        jpBarraLateral = new JPanel();
        jpBarraLateral.setBounds(0, 0, 278, 900);
        jpBarraLateral.setLayout(null);
        colorBarraLateral =  Color.decode("#" + colorBarra);
        jpBarraLateral.setBackground(colorBarraLateral);

        //Logo de Empresa
        ImageIcon iconoEmpleado = new ImageIcon("src/main/resources/img/frmLogin/logoEmpresa.png");
        Image originalImage = iconoEmpleado.getImage();
        Image resizedImage = originalImage.getScaledInstance(177, 173, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        lblImagenEmpleado = new JLabel(resizedIcon);
        lblImagenEmpleado.setBounds(0, 26, 275, 173);
        lblImagenEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
        jpBarraLateral.add(lblImagenEmpleado);

        //Nombre del empleado
        String nombreEmpleado = empleadoActual.getNombre() + " "+empleadoActual.getApellidoPaterno() + " "+ empleadoActual.getApellidoMaterno();
        lblNombreEmpleado = new JLabel(nombreEmpleado);
        lblNombreEmpleado.setBounds(0, 222, 275, 25);
        lblNombreEmpleado.setFont(new Font("Amaranth", Font.BOLD, 16));
        lblNombreEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
        jpBarraLateral.add(lblNombreEmpleado);

        //Cargo del empleado
        lblCargo = new JLabel(empleadoActual.getCargo().name());
        lblCargo.setBounds(0, 268, 275, 25);
        lblCargo.setFont(new Font("Amaranth", Font.BOLD, 16));
        lblCargo.setHorizontalAlignment(SwingConstants.CENTER);
        jpBarraLateral.add(lblCargo);

        int espacioEntreOpciones = 65;
        int posicionY= 400;

        //Opción de Ventas
        jpOpcVentas = new JPanel();
        jpOpcVentas.setBounds(0, posicionY, 275, 46);
        jpOpcVentas.setOpaque(false);
        jpOpcVentas.setBackground(new Color(0, 0, 0, 0));

        ImageIcon iconoVentas = new ImageIcon("src/main/resources/img/frmPrincipal/iconoVentas.png");
        lblOpcVentas = new JLabel("Ventas", iconoVentas, SwingConstants.LEFT);
        lblOpcVentas.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblOpcVentas.setIconTextGap(28);
        jpOpcVentas.setLayout(new BoxLayout(jpOpcVentas, BoxLayout.X_AXIS));

        posicionY+= espacioEntreOpciones;

        //Opción de Productos
        jpOpcProductos = new JPanel();
        jpOpcProductos.setBounds(0, posicionY, 275, 46);
        jpOpcProductos.setOpaque(false);

        ImageIcon iconoProductos = new ImageIcon("src/main/resources/img/frmPrincipal/iconoProductos.png");
        lblOpcProductos = new JLabel("Menú y Carta", iconoProductos, SwingConstants.LEADING);
        lblOpcProductos.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblOpcProductos.setIconTextGap(26);
        jpOpcProductos.setLayout(new BoxLayout(jpOpcProductos, BoxLayout.X_AXIS));

        posicionY+= espacioEntreOpciones;


        //Opción de Personal
        jpOpcPersonal = new JPanel();
        jpOpcPersonal.setBounds(0, posicionY, 275, 46);
        jpOpcPersonal.setOpaque(false);

        ImageIcon iconoPersonal = new ImageIcon("src/main/resources/img/frmPrincipal/iconoPersonal.png");
        lblOpcPersonal = new JLabel("Personal", iconoPersonal, SwingConstants.LEFT);
        lblOpcPersonal.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblOpcPersonal.setIconTextGap(26);
        jpOpcPersonal.setLayout(new BoxLayout(jpOpcPersonal, BoxLayout.X_AXIS));

        posicionY+= espacioEntreOpciones;

        //Opción de Estadisticas
        jpOpcEstadisticas = new JPanel();
        jpOpcEstadisticas.setBounds(0, posicionY, 275, 46);
        jpOpcEstadisticas.setOpaque(false);

        ImageIcon iconoEstadisticas = new ImageIcon("src/main/resources/img/frmPrincipal/iconoEstadisticas.png");
        lblOpcEstadisticas = new JLabel("Estadísticas", iconoEstadisticas, SwingConstants.LEFT);
        lblOpcEstadisticas.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblOpcEstadisticas.setIconTextGap(26);
        jpOpcEstadisticas.setLayout(new BoxLayout(jpOpcEstadisticas, BoxLayout.X_AXIS));

        posicionY+= espacioEntreOpciones;

        //Opción de Configuración
        jpOpcConfiguracion = new JPanel();
        jpOpcConfiguracion.setBounds(0, posicionY, 275, 46);
        jpOpcConfiguracion.setOpaque(false);

        ImageIcon iconoConfig = new ImageIcon("src/main/resources/img/frmPrincipal/iconoConfiguracion.png");
        lblOpcConfiguracion = new JLabel("Configuración", iconoConfig, SwingConstants.LEFT);
        lblOpcConfiguracion.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblOpcConfiguracion.setIconTextGap(26);
        jpOpcConfiguracion.setLayout(new BoxLayout(jpOpcConfiguracion, BoxLayout.X_AXIS));


        //Botón de Cerrar Turno
        btnCerrarTurno = new RoundedButton("Cerrar Turno", 10);
        btnCerrarTurno.setBounds(56, 828, 170, 55);
        btnCerrarTurno.setFont(new Font("Amaranth", Font.PLAIN, 21));
        btnCerrarTurno.setHorizontalAlignment(SwingConstants.CENTER);
        btnCerrarTurno.setBackground(Color.RED);
        btnCerrarTurno.setForeground(Color.white);
        jpBarraLateral.add(btnCerrarTurno);


        colorFondoPanel =  Color.decode("#" + colorFondoPaneles);

        //Inicializando JPanel de Contenido y botones
        jpContenido = new JPanel();
        jpContenido.setBounds(275, 0, 1325, 900);
        jpContenido.setLayout(null);
        jpContenido.setBackground(colorFondoPanel);


        //Botón de Minimizar personalizado
        btnMinimizar = new JButton("_");
        btnMinimizar.setFont(new Font("Amaranth", Font.BOLD, 16));
        btnMinimizar.setBackground(new Color(0, 0, 0, 1));
        btnMinimizar.setBounds(1205, 4, 35, 35);

        //Botón de Maximizar personalizado
        ImageIcon iconoMaximizar = new ImageIcon("src/main/resources/img/frmPrincipal/iconoMaximizar.png");
        btnMaximizar = new JButton(iconoMaximizar);
        btnMaximizar.setFont(new Font("Amaranth", Font.BOLD, 16));
        btnMaximizar.setBackground(new Color(0, 0, 0, 1));
        btnMaximizar.setBounds(1245, 4, 35, 35);

        //Botón de cerrar personalizado
        btnCerrar = new JButton("x");
        btnCerrar.setFont(new Font("Amaranth", Font.BOLD, 16));
        btnCerrar.setBackground(new Color(0, 0, 0, 1));
        btnCerrar.setBounds(1285, 4, 35, 35);


        //Inicializando paneles intercambiables
        contenidos = new JPanel[5];

        contenidos[1] = new JPanelMenuCarta();
        contenidos[3] = new JPanelEstadisticas();
        contenidos[4] = new JPanelConfiguracion();
        contenidos[2] = new JPanelPersonal();
        contenidos[0] = new JPanelVentas(this);

        for (int i = 0; i < contenidos.length; i++) {
            contenidos[i].setBounds(0, 0, 1325, 900);
            contenidos[i].setLayout(null);
            contenidos[i].setBackground(colorFondoPanel);

        }

        jpOpcVentas.add(Box.createHorizontalStrut(13));
        jpOpcVentas.add(lblOpcVentas);
        jpOpcProductos.add(Box.createHorizontalStrut(15));
        jpOpcProductos.add(lblOpcProductos);
        jpOpcPersonal.add(Box.createHorizontalStrut(23));
        jpOpcPersonal.add(lblOpcPersonal);
        jpOpcEstadisticas.add(Box.createHorizontalStrut(23));
        jpOpcEstadisticas.add(lblOpcEstadisticas);
        jpOpcConfiguracion.add(Box.createHorizontalStrut(23));
        jpOpcConfiguracion.add(lblOpcConfiguracion);



        //Dejando seleccionada por defecto el JPanel de Ventas
        OpcSeleccionada = 0;
        anteriorOpcSeleccionada = -1;
        colocarFocoOpcion(OpcSeleccionada);


        //Asignando eventos a los JPanels de Opciones
        jpOpcVentas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                OpcSeleccionada = 0;
                colocarFocoOpcion(OpcSeleccionada);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (OpcSeleccionada != 0){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startEnterTimer(lblOpcVentas, tamanioDeseado, tamanioOriginal);
                }

                setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (OpcSeleccionada!=0){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startExitTimer(lblOpcVentas, tamanioDeseado, tamanioOriginal );
                }

                setCursor(Cursor.DEFAULT_CURSOR);

            }
        });


        jpOpcProductos.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                OpcSeleccionada = 1;
                colocarFocoOpcion(OpcSeleccionada);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (OpcSeleccionada != 1){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startEnterTimer(lblOpcProductos, tamanioDeseado, tamanioOriginal);

                }
                setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (OpcSeleccionada!=1){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startExitTimer(lblOpcProductos, tamanioDeseado, tamanioOriginal );
                }
                setCursor(Cursor.DEFAULT_CURSOR);
            }
        });



        jpOpcPersonal.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                OpcSeleccionada = 2;
                colocarFocoOpcion(OpcSeleccionada);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (OpcSeleccionada != 2){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startEnterTimer(lblOpcPersonal, tamanioDeseado, tamanioOriginal);

                }


                setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (OpcSeleccionada!=2){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startExitTimer(lblOpcPersonal, tamanioDeseado, tamanioOriginal );
                }

                setCursor(Cursor.DEFAULT_CURSOR);
            }
        });



        jpOpcEstadisticas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                OpcSeleccionada = 3;
                colocarFocoOpcion(OpcSeleccionada);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (OpcSeleccionada != 3){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startEnterTimer(lblOpcEstadisticas, tamanioDeseado, tamanioOriginal);

                }
                setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (OpcSeleccionada!=3){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startExitTimer(lblOpcEstadisticas, tamanioDeseado, tamanioOriginal );
                }

                setCursor(Cursor.DEFAULT_CURSOR);
            }
        });


        jpOpcConfiguracion.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                OpcSeleccionada = 4;
                colocarFocoOpcion(OpcSeleccionada);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (OpcSeleccionada != 4){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startEnterTimer(lblOpcConfiguracion, tamanioDeseado, tamanioOriginal);

                }
                setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (OpcSeleccionada!=4){
                    int tamanioDeseado = 29;
                    final int[] tamanioOriginal = {24};

                    startExitTimer(lblOpcConfiguracion, tamanioDeseado, tamanioOriginal );
                }

                setCursor(Cursor.DEFAULT_CURSOR);
            }
        });


        ///Agregando eventos a los Botones

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnCerrar.setBackground(Color.red);
                btnCerrar.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnCerrar.setBackground(new Color(0, 0, 0, 0));
                btnCerrar.setForeground(Color.BLACK);
            }
        });

        btnMaximizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TODO: Implementar método para redimensionar componentes al maximizar la pantalla
                if (isMaximized) {
                    // Restaurar el tamaño anterior de la ventana
                    setExtendedState(previousState);
                } else {
                    // Maximizar la ventana
                    previousState = getExtendedState();
                    setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
                }
                isMaximized = !isMaximized;
            }
        });


        btnMinimizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(Frame.ICONIFIED);
            }
        });



        btnCerrarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FrmLogin frmLogin = new FrmLogin();
            }
        });



        //Agregando Componentes a la barra lateral
        jpBarraLateral.add(jpOpcVentas);
        jpBarraLateral.add(jpOpcProductos);
        jpBarraLateral.add(jpOpcPersonal);
        jpBarraLateral.add(jpOpcEstadisticas);
        jpBarraLateral.add(jpOpcConfiguracion);


        //Agregando componentes al JPanel Principal
        jpPrincipal.add(jpContenido);
        jpPrincipal.add(jpBarraLateral);

        addDraggableMouseListener(this);

        setContentPane(jpPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void repintarPanel(JPanel jPanel, Color color){
        jPanel.setBackground(color);
        jPanel.revalidate();
        jPanel.repaint();
    }

    private void cambiarPanel(JPanel jPanel, Color color) {
        jpContenido.removeAll();
        jpContenido.repaint();
        jPanel.setBackground(color);
        jpContenido.add(jPanel);
        agregarBotonesSuperiores(jPanel);
    }

    private void agregarBotonesSuperiores(JPanel jp) {
        jp.add(btnMinimizar);
        jp.add(btnMaximizar);
        jp.add(btnCerrar);
    }


    //Métodos Fuera del Constructor

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

    private void startEnterTimer(JLabel lbl,int tamanioDeseado, final int[] tamanioOriginal) {

        Timer enterTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamanioOriginal[0] += 1; // Incremento gradual
                if (tamanioOriginal[0] >= tamanioDeseado) {
                    ((Timer) e.getSource()).stop(); // Detener el temporizador cuando se alcanza el tamaño deseado
                }
                Font newFont = lbl.getFont().deriveFont(Font.BOLD, tamanioOriginal[0]);
                lbl.setFont(newFont);
            }
        });
        enterTimer.start();
    }

    private void startExitTimer(JLabel lbl,int tamanioDeseado, final int[] tamanioOriginal) {

        Timer exitTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamanioOriginal[0] -= 1; // Decremento gradual
                if (tamanioOriginal[0] <= tamanioDeseado) {
                    ((Timer) e.getSource()).stop(); // Detener el temporizador cuando se alcanza el tamaño deseado
                }
                Font newFont = lbl.getFont().deriveFont(Font.BOLD, tamanioOriginal[0]);
                lbl.setFont(newFont);
            }
        });
        exitTimer.start();
    }

    private void colocarFocoOpcion(int Selected) {
        JLabel[] jlabels = {lblOpcVentas,lblOpcProductos, lblOpcPersonal,lblOpcEstadisticas,lblOpcConfiguracion};
        JPanel[] jpanels = {jpOpcVentas,jpOpcProductos,jpOpcPersonal,jpOpcEstadisticas,jpOpcConfiguracion};

        if (anteriorOpcSeleccionada != Selected) {
            for (int i = 0; i < jlabels.length; i++) {
                if (i == Selected) {
                    jlabels[i].setFont(new Font("Amaranth", Font.BOLD, 29));
                    setOpaqueLabel(jlabels[i], 1); // Opacidad del 100%
                    jpanels[i].setOpaque(true);
                    jpanels[i].setBackground(new Color(255, 236, 169));
                } else {
                    if (anteriorOpcSeleccionada!= -1){
                        int tamanioDeseado = 24;
                        final int[] tamanioOriginal = new int[]{29};

                        startExitTimer(jlabels[anteriorOpcSeleccionada], tamanioDeseado, tamanioOriginal );
                    }

                    jlabels[i].setFont(new Font("Amaranth", Font.BOLD, 24));
                    jpanels[i].setOpaque(false);
                    jpanels[i].setBackground(new Color(0,0,0,0));
                    setOpaqueLabel(jlabels[i], 0.4f); // Opacidad original
                }
            }
        }

        anteriorOpcSeleccionada = Selected;


        cambiarPanel(contenidos[Selected],colorFondoPanel);
    }

    public static void setOpaqueLabel(JLabel label, float opacity) {
        // Obtener el icono original del JLabel
        Icon originalIcon = (Icon) label.getClientProperty("originalIcon");

        // Si el icono original no se ha almacenado, guardarlo
        if (originalIcon == null) {
            originalIcon = label.getIcon();
            label.putClientProperty("originalIcon", originalIcon);
        }

        // Crear un nuevo ImageIcon opacado a partir del icono original
        ImageIcon opacIcon = getOpaqueIcon(originalIcon, opacity);

        // Establecer el nuevo icono opacado al JLabel
        label.setIcon(opacIcon);

        // Aplicar la opacidad al color del texto del JLabel
        Color originalForeground = label.getForeground();
        Color opacForeground = getOpaqueColor(originalForeground, opacity);
        label.setForeground(opacForeground);
    }

    public static Color getOpaqueColor(Color color, float opacity) {
        int alpha = Math.round(opacity * 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static ImageIcon getOpaqueIcon(Icon icon, float opacity) {
        // Obtener el ancho y alto del icono
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        // Crear una imagen compatible con transparencia
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Obtener el objeto Graphics2D para dibujar en la imagen
        Graphics2D g2d = bufferedImage.createGraphics();

        // Aplicar la opacidad al objeto Graphics2D
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        // Dibujar el icono en la imagen opacada
        icon.paintIcon(null, g2d, 0, 0);

        // Liberar los recursos
        g2d.dispose();

        return new ImageIcon(bufferedImage);
    }

}

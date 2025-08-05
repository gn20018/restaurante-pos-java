package utils;


import com.saborperuano.sistema.Models.Insumo;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class Utilidad2 {

    public static final int Codigo=0;
    public static final int Nombre=1;
    public static final int Categoria=2;
    public static final int precioUnitario=3;
    public static final int Cantidad=4;
    public static final int Estante=5;


    public static void cambiarTamanioFuente(JComponent jComponent, float nuevoTamanio, int estiloFuente) {
        Font fuenteActual = jComponent.getFont();
        Font nuevaFuente = fuenteActual.deriveFont(estiloFuente,nuevoTamanio);
        jComponent.setFont(nuevaFuente);
    }



    public static int getNroRegistros(String Path_File, ErrorLog errorLog){

        int nroRegistrosTotales = 0;
        try {
            String []  Registros = readlinesAsArray(Path_File, "\r\n" );
            nroRegistrosTotales = Registros.length;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validación1", JOptionPane.ERROR_MESSAGE );
            try {
                String  event = errorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            }catch (Exception ex){
                System.out.print("Error al registrar logs");
            }
        }

        return nroRegistrosTotales;
    }
    public static String read(String filename) throws IOException {
        try(BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(filename))
        ){
            String data = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return data;
        } catch (IOException e) {
            throw e;
        }
    }
    public static String[] readlinesAsArray(String filename, String delimitador) throws IOException {
        String data = read(filename);
        String[] res = new String[]{};
        if (data.length() > 0){
            res = data.split(delimitador);
        }
        return res;
    }

    public static <T> void getRegistrosCorrectos(String Path_File, ErrorLog errorLog,
                                                 ListaEnlazadaGenerica Inventario, Class<T> clase, String patron){


        //Procesamiento, Validación y Extracción de la Información Correcta
        try {
            String []  Registros = Utilidades.readlinesAsArray(Path_File, "\r\n");

            for (int i = 0; i < Registros.length; i++) {
                boolean esRegistroValido= false;
                int erroresRegistro = 0;
                try{
                    //Revisando que las líneas del registro tengan contenido
                    Validador.esRegistroValido(Registros[i], patron);

                    //Quitando Espacios al inicio y final de los Registros
                    Registros[i] = Registros[i].strip();

                    try {

                        //Separando el registro en cada una de sus partes
                        String[] atributos = Registros[i].split(":");

                        //Verificación de existencia de atributos
                        for (int j = 0; j < atributos.length; j++) {
                            if (atributos[j].isEmpty()){
                                erroresRegistro++;
                            }
                        }

                        //Validando registro
                        if (erroresRegistro == 0) esRegistroValido = true;

                        //Agregando elementos a la lista enlazada
                        if (esRegistroValido){
                            T elemento = createInstance(clase,atributos);
                            if ((i == 0)) {
                                Inventario.agregarInicio(elemento);
                            } else {
                                Inventario.agregarAlFinal(elemento);
                            }

                        }else {
                            throw new Exception("No se han ingresado bien los datos.");
                        }


                    }catch (Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Validación", JOptionPane.ERROR_MESSAGE );
                        try {
                            String  event = errorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
                            System.out.print("event = " + event);
                        }catch (Exception ex){
                            System.out.print("Error al registrar logs");
                        }
                    }

                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Registro #"+(i+1)+" - "+e.getMessage(), "Validaciónp", JOptionPane.ERROR_MESSAGE );
                    try {
                        String  event = errorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
                        System.out.print("event = " + event);
                    }catch (Exception ex){
                        System.out.print("Error al registrar logs");
                    }
                }

            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validación2", JOptionPane.ERROR_MESSAGE );
            try {
                String  event = errorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
                System.out.print("event = " + event);
            }catch (Exception ex){
                System.out.print("Error al registrar logs");
            }
        }
    }



    //Utilizando reflexión para crear objetos dinámicamente
    private static <T> T createInstance(Class<T> clase, String[] atributos) throws Exception {
        T instancia = clase.getDeclaredConstructor().newInstance(); // Crear una instancia de la clase T

        Field[] fields = clase.getDeclaredFields(); // Obtener todos los campos (atributos) de la clase T

        // Verificar que la cantidad de atributos coincida con la cantidad de elementos en el array 'atributos'
        if (fields.length != atributos.length) {
            throw new Exception("La cantidad de atributos no coincide con la cantidad de valores proporcionados.");
        }

        // Establecer los valores de los atributos en la instancia
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true); // Hacer que el campo sea accesible (incluso si es privado)

            // Obtener el tipo de dato del campo
            Class<?> fieldType = field.getType();

            // Convertir el valor del atributo al tipo de dato correspondiente
            Object valorAtributo = convertirAtributo(atributos[i], fieldType);

            // Establecer el valor del atributo en la instancia
            field.set(instancia, valorAtributo);
        }

        return instancia;
    }

    // Método para convertir un valor de atributo representado como cadena al tipo de dato correspondiente
    private static Object convertirAtributo(String valorAtributo, Class<?> fieldType) throws Exception {
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(valorAtributo);
        } else if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(valorAtributo);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(valorAtributo);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(valorAtributo);
        } else if (fieldType == String.class) {
            return valorAtributo;
        } else if (fieldType == com.saborperuano.sistema.Models.Enums.Categoria.class) {
            return com.saborperuano.sistema.Models.Enums.Categoria.valueOf(valorAtributo.toUpperCase());
        }else {
            // Manejar otros tipos de datos según sea necesario
            throw new Exception("Tipo de dato no compatible: " + fieldType.getName());
        }
    }


    public static void cargarData (ErrorLog errorLog, ListaEnlazadaGenerica<Insumo> listaInventario,
                                   String ruta, String patron) {

        try {

            //Verificando que existan Registros Correctos, Obteniendolos y Almacenando errores en un log
            Utilidad2.getRegistrosCorrectos(ruta, errorLog,listaInventario, Insumo.class, patron);

            listaInventario.mostrarLista();

            //Impresión de Validación Correcta
            JOptionPane.showMessageDialog(null, "Datos Validados Correctamente.");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validación3", JOptionPane.ERROR_MESSAGE);
            try {
                String event = errorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
                System.out.println("event = " + event);
            } catch (Exception ex) {
                System.out.println("Error al registrar logs");
            }

        }

    }



    public static void actualizarSugerencias(JPanel jp){

    }


}

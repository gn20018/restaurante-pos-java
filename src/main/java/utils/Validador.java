package utils;

public class Validador {

    //Validador de Ruta
    public static boolean esRutaValida(String ruta) throws Exception {
        if (ruta.isEmpty()) {
            String msg = "Excepción en Ruta: No se ha especificado la ruta de un archivo para cargar datos";
            throw new Exception(msg);
        }
        return true;
    }


    //Validador de Existencia de Información en Archivo
    public static boolean esArchivoValido(int registros_totales, String ruta, ErrorLog errorLog) throws Exception {

        //Verificando que existan registros dentro del archivo
        if (registros_totales == 0) {
            String msg = "Excepción en el archivo : No se han encontrado registros.";
            throw new Exception(msg);
        }

        return true;
    }

    //Validador de Existencia de Registro en la línea
    public static boolean esRegistroValido(String registro,String patron) throws Exception {
        if (registro.isEmpty()) {
            String msg = "Excepción en el registro: La línea correspondiente a este registro está vacía.";
            throw new Exception(msg);
        } else if (!registro.matches(patron)) {
            String msg = "Excepción en el registro: No se cumple el formato establecido.";
            throw new Exception(msg);
        }

        return true;
    }




}
package com.saborperuano.sistema.Views.Principal.Estadisticas;

import java.io.File;
import java.net.URL;

public class Draft {
    public static void main(String[] args) {
        // Obtiene la ruta del directorio "resources"
        String resourcesPath = Draft.class.getClassLoader().getResource("pdf/reporte.jasper").getPath();

        System.out.println("resourcesPath = " + resourcesPath);

        File file = new File(resourcesPath);

        // Verifica si el archivo existe antes de proceder
        if (file.exists()) {
            System.out.println("El archivo reporte.jasper existe en la ruta: " + file.getAbsolutePath());
            // Aquí puedes usar el archivo 'file' como desees
        } else {
            System.out.println("El archivo reporte.jasper no existe.");
        }
    }
}

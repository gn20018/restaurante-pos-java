package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class GeneradorPDF {

    public static void convertHtmlToPdf(String inputHtmlFile, String outputPdfFile) {
        try {
            // Lee el archivo HTML de entrada utilizando JSoup
            File htmlFile = new File(inputHtmlFile);
            Document htmlDocument = Jsoup.parse(htmlFile, "UTF-8");

            // Crea un documento PDF utilizando iTextRenderer
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlDocument.toString());
            renderer.layout();

            // Genera el archivo PDF
            try (FileOutputStream fos = new FileOutputStream(outputPdfFile)) {
                renderer.createPDF(fos);
            }

        } catch (IOException | com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "src/main/resources/pdf/prueba.html";
        String outputFile = "src/main/resources/pdf/output.pdf";
        convertHtmlToPdf(inputFile, outputFile);
    }
}

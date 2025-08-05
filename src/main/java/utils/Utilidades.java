package utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.regex.PatternSyntaxException;

public class Utilidades {

	public static final int NroProducto=0;
	public static final int CodigoProducto=1;
	public static final int NombreProducto=2;
	public static final int Categoria=3;
	public static final int Cantidad=4;
	public static final int PrecioUnitario=5;
	public static final int PrecioTotal=6;
	public static final int Descuento=7;
	public static final int Importe=8;
	public static final int NroVenta=0;
	public static final int NombreVenta=1;
	public static final int CantidadProductos=2;
	public static final int MontoTotal=3;
	public static final int DescuentoVenta=4;
	public static final int MontoAPagar=5;
	public static final int Acciones=6;


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
			JOptionPane.showMessageDialog(null, e.getMessage(), "Validación", JOptionPane.ERROR_MESSAGE );
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

	public static <T> void getRegistrosCorrectos(String Path_File, ErrorLog errorLog, ListaEnlazadaGenerica productos, Class<T> clase, String patron){


		//Procesamiento, Validación y Extracción de la Información Correcta
		try {
			String []  Registros = Utilidades.readlinesAsArray(Path_File, "\r\n");

			for (int i = 0; i < Registros.length; i++) {
				boolean esRegistroValido= false;
				int erroresRegistro = 0;
				try{
					//Revisando que las líneas del registro tengan contenido
					Validador.esRegistroValido(Registros[i],patron);

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
									productos.agregarInicio(elemento);
								} else {
									productos.agregarAlFinal(elemento);
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
					JOptionPane.showMessageDialog(null, "Registro #"+(i+1)+" - "+e.getMessage(), "Validación", JOptionPane.ERROR_MESSAGE );
					try {
						String  event = errorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
						System.out.print("event = " + event);
					}catch (Exception ex){
						System.out.print("Error al registrar logs");
					}
				}

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




	public static <T> void llenarJTable(ListaEnlazadaGenerica<T> lista, JTable jTable) {
		DefaultTableModel model = (DefaultTableModel) jTable.getModel();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");

		model.setRowCount(0);

		// Obtener los objetos de la lista
		List<T> objetos = lista.getObjetos();

		// Llenar el modelo con los datos de la lista de objetos
		for (T objeto : objetos) {
			List<Object> fila = new ArrayList<>();
			Field[] campos = objeto.getClass().getDeclaredFields();
			for (Field campo : campos) {
				campo.setAccessible(true);
				try {
					for (int i = 0; i < fila.size(); i++) {
						Object elemento = fila.get(i);
						if (elemento instanceof DateTime) {
							// Si el elemento es un DateTime de Joda-Time, lo convertimos a un formato legible
							DateTime fecha = (DateTime) elemento;
							String fechaLegible = formatter.print(fecha);
							fila.set(i, fechaLegible); // Reemplazamos el objeto DateTime con la fecha legible
						}
					}

					fila.add(campo.get(objeto));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			model.addRow(fila.toArray());
		}
	}

	public static void filtrarTabla(JTextField txtFiltro,JTable tabla ) {
		String textoFiltro = txtFiltro.getText();

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabla.getModel());
		tabla.setRowSorter(sorter);

		if (textoFiltro.trim().length() == 0) {
			sorter.setRowFilter(null);
		} else {
			// Crear un RowFilter para filtrar las filas basado en el texto ingresado en el JTextField
			RowFilter<TableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + textoFiltro); // El flag "(?i)" hace que el filtro ignore mayúsculas y minúsculas

			try {
				sorter.setRowFilter(rowFilter);
			} catch (PatternSyntaxException ex) {
				JOptionPane.showMessageDialog(null,"Ocurrió un error al filtrar la tabla: "+ex.getMessage());
			}
		}
	}




}

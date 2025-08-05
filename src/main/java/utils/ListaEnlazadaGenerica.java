package utils;


import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaEnlazadaGenerica<T> {
    private class Nodo<T>{
        private T t;
        private Nodo<T> siguiente;

        public Nodo(T t) {
            this.t = t;
            this.siguiente = null;
        }

        public Nodo(T t, Nodo<T> siguiente) {
            this.t = t;
            this.siguiente = siguiente;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }

    }
    public Nodo<T> inicio, fin;
    public int longitud;

    public ListaEnlazadaGenerica() {
        this.inicio = null;
        this.fin = null;
        this.longitud = 0;
    }

    public void agregarInicio(T dato){
        //Creando el primer nodo
        inicio = new Nodo<T>(dato,inicio);
        if (fin == null){
            fin = inicio;
        }
        longitud++;
    }

    public void mostrarLista(){
        Nodo<T> recorredor = inicio;
        System.out.println();
        while (recorredor != null){
            System.out.print("[" + recorredor.getT() + "]---->");
            recorredor = recorredor.siguiente;
        }
    }


    public void agregarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }

        longitud++;
    }

    public T borrarDelInicio(){
        T t = inicio.getT();
        if (inicio == fin){
            inicio = fin = null;
        }else {
            inicio = inicio.siguiente;
        }

        longitud--;

        return t;
    }

    public T borrarAlFinal(){
        //Extranyendo data
        T t = fin.getT();
        //Si la lista es de 1 solo elemento, se elimina la lista
        if (inicio == fin){
            inicio = fin = null;
        }else {
            //Si la lista es de más de 1 elemento, se recorre hasta encontrar el elemento penúltimo
            //Y a su puntero siguiente se le asigna nulo para eliminar el último y se reasigna el fin

            Nodo temporal = inicio;
            while (temporal.siguiente != fin){
                temporal = temporal.siguiente;
            }
            fin = temporal;
            fin.siguiente = null;
        }

        longitud--;

        return t;
    }

    public T obtenerUltimoObjeto() {
        if (fin != null) {
            return fin.getT();
        }
        return null; // Si la lista está vacía, retornar null
    }

    public T buscarPorCampo(String nombreCampo, Object valorBuscado) {
        Nodo<T> temporal = inicio;
        while (temporal != null) {
            T objeto = temporal.getT();

            try {
                // Obtener el campo correspondiente al nombre especificado
                Field campo = objeto.getClass().getDeclaredField(nombreCampo);
                campo.setAccessible(true); // Acceder a campos privados si es necesario

                System.out.println("objeto = " + objeto);

                // Obtener el valor del campo en el objeto actual
                Object fieldValue = campo.get(objeto);

                System.out.println("fieldValue = " + fieldValue);

                try {
                    int valor = (Integer.parseInt((String) valorBuscado));

                    if (fieldValue != null && fieldValue == valorBuscado) {
                        return objeto;
                    }

                    System.out.println("PASE POR AQUÍ Integer");

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    // Verificar si el valor del campo coincide con el valor buscado
                    if (fieldValue != null && fieldValue.equals(valorBuscado)) {
                        return objeto;
                    }
                    System.out.println("PASE POR AQUÍ Error");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            temporal = temporal.getSiguiente();
        }

        return null; // No se encontró el objeto con el valor buscado en el campo especificado
    }
    public T obtenerObjetoPorIndice(ListaEnlazadaGenerica<T> lista, int indice) {
        T obj;

        Nodo<T> actual = lista.inicio;
        for (int i = 0; i < indice && actual.siguiente !=null; i++) {
            actual = actual.getSiguiente();
        }

        obj = actual.getT(); // Almacena el objeto del nodo en el parámetro objeto

        return obj;
    }

    public T obtenerObjetoPorCampo(ListaEnlazadaGenerica<T> lista, String campo, Object valor) {
        Nodo<T> actual = lista.inicio;

        while (actual != null) {
            T objeto = actual.getT();

            // Obtener el valor del campo usando reflexión
            try {
                Field campoField = objeto.getClass().getDeclaredField(campo);
                campoField.setAccessible(true);
                Object valorCampo = campoField.get(objeto);

                if (valorCampo != null && valorCampo.equals(valor)) {
                    return objeto; // Retornar el objeto si se encontró el valor en el campo especificado
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            actual = actual.getSiguiente();
        }

        return null; // Retornar null si no se encontró el objeto con el valor en el campo especificado
    }

    public void eliminar(Nodo actual){
        //Creando un puntero al nodo anterior del actual
        Nodo anterior = inicio;
        //Buscando el nodo anterior
        while (anterior.siguiente!=actual && anterior.siguiente != null){
             anterior = anterior.siguiente;
        }
        //2. Elimina el nodo si existe
        if (actual != null){
            if (anterior == actual ){
                inicio = actual.siguiente;
            }else {
                anterior.siguiente = actual.siguiente;
            }
        }

        longitud--;
    }

    public void eliminarPorObjeto(T objetoAEliminar) {
        Nodo<T> actual = inicio;
        Nodo<T> anterior = null;

        while (actual != null) {
            T objetoActual = actual.getT();

            if (objetoActual.equals(objetoAEliminar)) {
                if (anterior == null) {
                    inicio = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                if (actual == fin) {
                    fin = anterior;
                }

                longitud--;
                return;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }
    }



    public HashMap<String, String> obtenerHashMapUsuariosContrasenas(String atributoUsuario, String atributoContrasena) {
        HashMap<String, String> usuariosContrasenas = new HashMap<>();
        Nodo<T> nodoActual = inicio;

        while (nodoActual != null) {
            T objeto = nodoActual.getT();

            // Obtener el valor de los atributos "usuario" y "contrasena" usando reflexión
            try {
                Field usuarioField = objeto.getClass().getDeclaredField(atributoUsuario);
                Field contrasenaField = objeto.getClass().getDeclaredField(atributoContrasena);
                usuarioField.setAccessible(true);
                contrasenaField.setAccessible(true);
                String usuario = (String) usuarioField.get(objeto);
                String contrasena = (String) contrasenaField.get(objeto);
                usuariosContrasenas.put(usuario, contrasena);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            nodoActual = nodoActual.getSiguiente();
        }

        return usuariosContrasenas;
    }

    public List<T> getObjetos() {
        List<T> objetos = new ArrayList<>();
        Nodo<T> nodoActual = inicio;
        while (nodoActual != null) {
            objetos.add(nodoActual.getT());
            nodoActual = nodoActual.getSiguiente();
        }
        return objetos;
    }

    public void vaciar() {
        inicio = null;
        fin = null;
        longitud = 0;
    }


}


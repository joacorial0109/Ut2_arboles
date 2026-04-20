package ucu.edu.aed.tda;

import java.util.function.Consumer;
import java.util.StringJoiner;

public class ArbolBinario<T extends Comparable<T>> implements TDAArbolBinario<T> {

    protected TDAElemento<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    @Override
    public T buscar(Comparable<T> criterioBusqueda) {
        if (esVacio() || criterioBusqueda == null) {
            return null;
        }

        TDAElemento<T> encontrado = raiz.buscar(criterioBusqueda);

        if (encontrado != null) {
            return encontrado.getDato();
        }

        return null;
    }

    @Override
    public int buscarConContador(Comparable<T> criterioBusqueda) {
        if (esVacio() || criterioBusqueda == null) {
            return -1;
        }

        return raiz.buscarConContador(criterioBusqueda);
    }

    @Override
    public TDAElemento<T> obtenerRaiz() {
        return raiz;
    }

    @Override
    public boolean eliminar(Comparable<T> criterioBusqueda) {
        if (esVacio() || criterioBusqueda == null) {
            return false;
        }

        if (raiz.buscar(criterioBusqueda) == null) {
            return false;
        }

        raiz = raiz.eliminar(criterioBusqueda);
        return true;
    }

    @Override
    public boolean insertar(Comparable<T> dato) {
        if (dato == null) {
            return false;
        }

        @SuppressWarnings("unchecked")
        T valor = (T) dato;

        if (esVacio()) {
            raiz = new Elemento<>(valor);
            return true;
        }

        return raiz.insertar(dato);
    }

    public int insertarConContador(Comparable<T> dato) {
        if (dato == null) {
            System.out.println(0);
            return 0;
        }

        @SuppressWarnings("unchecked")
        T valor = (T) dato;

        if (esVacio()) {
            raiz = new Elemento<>(valor);
            System.out.println(1);
            return 1;
        }

        int[] contador = {0};
        boolean insertado = ((Elemento<T>) raiz).insertarConContador(dato, contador);

        if (!insertado) {
            System.out.println(0);
            return 0;
        }

        System.out.println(contador[0]);
        return contador[0];
    }

    @Override
    public void inOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.inOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public String inOrden() {
        StringJoiner resultado = new StringJoiner(" ");
        inOrder(dato -> resultado.add(String.valueOf(dato)));
        return resultado.toString();
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.preOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    public String preOrden() {
        StringJoiner resultado = new StringJoiner(" ");
        preOrder(dato -> resultado.add(String.valueOf(dato)));
        return resultado.toString();
    }

    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.postOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public String postOrden() {
        StringJoiner resultado = new StringJoiner(" ");
        postOrder(dato -> resultado.add(String.valueOf(dato)));
        return resultado.toString();
    }

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public int cantidadNodos() {
        if (esVacio()) {
            return 0;
        }

        return raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas() {
        if (esVacio()) {
            return 0;
        }

        return raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos() {
        if (esVacio()) {
            return 0;
        }

        return raiz.cantidadNodosInternos();
    }
}
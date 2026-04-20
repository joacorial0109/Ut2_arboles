package ucu.edu.aed.tda;

import java.util.function.Consumer;
import java.util.StringJoiner;

public class Elemento<T extends Comparable<T>> implements TDAElemento<T> {

    private TDAElemento<T> izquierdo;
    private TDAElemento<T> derecho;
    private T dato;

    public Elemento(T dato) {
        this.izquierdo = null;
        this.derecho = null;
        this.dato = dato;
    }

    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) {
        this.izquierdo = hijoIzquierdo;
    }

    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) {
        this.derecho = hijoDerecho;
    }

    @Override
    public TDAElemento<T> getHijoIzquierdo() {
        return izquierdo;
    }

    @Override
    public TDAElemento<T> getHijoDerecho() {
        return derecho;
    }

    @Override
    public void setDato(T dato) {
        this.dato = dato;
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return null;
        }

        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion < 0) {
            if (izquierdo != null) {
                return izquierdo.buscar(criterioBusqueda);
            }
            return null;
        }

        if (comparacion > 0) {
            if (derecho != null) {
                return derecho.buscar(criterioBusqueda);
            }
            return null;
        }

        return this;
    }

    @Override
    public int buscarConContador(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return -1;
        }

        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion == 0) {
            return 1;
        }

        TDAElemento<T> siguiente = comparacion < 0 ? izquierdo : derecho;

        if (siguiente == null) {
            return -1;
        }

        int contadorHijo = siguiente.buscarConContador(criterioBusqueda);
        int contadorTotal = 1 + Math.abs(contadorHijo);

        if (contadorHijo > 0) {
            return contadorTotal;
        }

        return -contadorTotal;
    }

    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return this;
        }

        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion < 0) {
            if (izquierdo != null) {
                izquierdo = izquierdo.eliminar(criterioBusqueda);
            }
            return this;
        }

        if (comparacion > 0) {
            if (derecho != null) {
                derecho = derecho.eliminar(criterioBusqueda);
            }
            return this;
        }

        return quitarNodo();
    }

    @Override
    public boolean insertar(Comparable<T> nuevoDato) {
        if (nuevoDato == null) {
            return false;
        }

        int comparacion = nuevoDato.compareTo(this.dato);

        if (comparacion < 0) {
            if (izquierdo == null) {
                @SuppressWarnings("unchecked")
                T valor = (T) nuevoDato;
                izquierdo = new Elemento<>(valor);
                return true;
            }

            return izquierdo.insertar(nuevoDato);
        }

        if (comparacion > 0) {
            if (derecho == null) {
                @SuppressWarnings("unchecked")
                T valor = (T) nuevoDato;
                derecho = new Elemento<>(valor);
                return true;
            }

            return derecho.insertar(nuevoDato);
        }

        return false;
    }

    public boolean insertarConContador(Comparable<T> nuevoDato, int[] contador) {
        contador[0]++;

        if (nuevoDato == null) {
            return false;
        }

        int comparacion = nuevoDato.compareTo(this.dato);

        if (comparacion < 0) {
            if (izquierdo == null) {
                @SuppressWarnings("unchecked")
                T valor = (T) nuevoDato;
                izquierdo = new Elemento<>(valor);
                return true;
            }

            return ((Elemento<T>) izquierdo).insertarConContador(nuevoDato, contador);
        }

        if (comparacion > 0) {
            if (derecho == null) {
                @SuppressWarnings("unchecked")
                T valor = (T) nuevoDato;
                derecho = new Elemento<>(valor);
                return true;
            }

            return ((Elemento<T>) derecho).insertarConContador(nuevoDato, contador);
        }

        return false;
    }

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        if (izquierdo != null) {
            izquierdo.inOrder(consumidor);
        }

        consumidor.accept(this);

        if (derecho != null) {
            derecho.inOrder(consumidor);
        }
    }

    @Override
    public String inOrden() {
        StringJoiner resultado = new StringJoiner(" ");
        inOrder(nodo -> resultado.add(String.valueOf(nodo.getDato())));
        return resultado.toString();
    }

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);

        if (izquierdo != null) {
            izquierdo.preOrder(consumidor);
        }

        if (derecho != null) {
            derecho.preOrder(consumidor);
        }
    }

    public String preOrden() {
        StringJoiner resultado = new StringJoiner(" ");
        preOrder(nodo -> resultado.add(String.valueOf(nodo.getDato())));
        return resultado.toString();
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        if (izquierdo != null) {
            izquierdo.postOrder(consumidor);
        }

        if (derecho != null) {
            derecho.postOrder(consumidor);
        }

        consumidor.accept(this);
    }

    @Override
    public String postOrden() {
        StringJoiner resultado = new StringJoiner(" ");
        postOrder(nodo -> resultado.add(String.valueOf(nodo.getDato())));
        return resultado.toString();
    }

    @Override
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    @Override
    public int cantidadHojas() {
        if (esHoja()) {
            return 1;
        }

        int cantidad = 0;

        if (izquierdo != null) {
            cantidad += izquierdo.cantidadHojas();
        }

        if (derecho != null) {
            cantidad += derecho.cantidadHojas();
        }

        return cantidad;
    }

    @Override
    public int cantidadNodosInternos() {
        if (esHoja()) {
            return 0;
        }

        int cantidad = 1;

        if (izquierdo != null) {
            cantidad += izquierdo.cantidadNodosInternos();
        }

        if (derecho != null) {
            cantidad += derecho.cantidadNodosInternos();
        }

        return cantidad;
    }

    @Override
    public int cantidadNodos() {
        int cantidad = 1;

        if (izquierdo != null) {
            cantidad += izquierdo.cantidadNodos();
        }

        if (derecho != null) {
            cantidad += derecho.cantidadNodos();
        }

        return cantidad;
    }

    @Override
    public int altura() {
        int alturaIzquierda = 0;
        int alturaDerecha = 0;

        if (izquierdo != null) {
            alturaIzquierda = izquierdo.altura();
        }

        if (derecho != null) {
            alturaDerecha = derecho.altura();
        }

        return 1 + Math.max(alturaIzquierda, alturaDerecha);
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return -1;
        }

        int comparacion = criterioBusqueda.compareTo(this.dato);

        if (comparacion == 0) {
            return 0;
        }

        TDAElemento<T> siguiente = comparacion < 0 ? izquierdo : derecho;

        if (siguiente == null) {
            return -1;
        }

        int nivelHijo = siguiente.obtenerNivel(criterioBusqueda);

        if (nivelHijo == -1) {
            return -1;
        }

        return nivelHijo + 1;
    }

    private TDAElemento<T> quitarNodo() {
        if (this.izquierdo == null) {
            return this.derecho;
        } else if (this.derecho == null) {
            return this.izquierdo;
        } else {
            TDAElemento<T> elHijo = this.izquierdo;
            TDAElemento<T> elPadre = this;

            while (elHijo.getHijoDerecho() != null) {
                elPadre = elHijo;
                elHijo = elHijo.getHijoDerecho();
            }

            if (elPadre != this) {
                elPadre.setHijoDerecho(elHijo.getHijoIzquierdo());
                elHijo.setHijoIzquierdo(izquierdo);
            }

            elHijo.setHijoDerecho(derecho);
            return elHijo;
        }
    }
}
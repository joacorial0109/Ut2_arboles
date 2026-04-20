package ucu.edu.aed.tda;

import junit.framework.TestCase;

public class ArbolBinarioTest extends TestCase {

    private ArbolBinario<Integer> arbol;

    @Override
    protected void setUp() {
        arbol = new ArbolBinario<>();
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);
    }

    public void testBuscarElementoExistente() {
        assertEquals(Integer.valueOf(40), arbol.buscar(40));
    }

    public void testBuscarElementoInexistente() {
        assertNull(arbol.buscar(35));
    }

    public void testBuscarConContadorExistente() {
        assertEquals(1, arbol.buscarConContador(50));
        assertEquals(3, arbol.buscarConContador(40));
    }

    public void testBuscarConContadorInexistente() {
        assertEquals(-3, arbol.buscarConContador(35));
        assertEquals(-3, arbol.buscarConContador(90));
    }

    public void testRecorridoInOrden() {
        assertEquals("20 30 40 50 60 70 80", arbol.inOrden());
    }

    public void testRecorridoPostOrden() {
        assertEquals("20 40 30 60 80 70 50", arbol.postOrden());
    }
}

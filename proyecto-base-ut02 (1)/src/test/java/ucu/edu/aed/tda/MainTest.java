package ucu.edu.aed.tda;

import junit.framework.TestCase;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class MainTest extends TestCase {

    public void testProcesarConsultasEscribeContadores() throws Exception {
        ArbolBinario<Integer> arbol = new ArbolBinario<>();
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);

        Path salida = Files.createTempFile("resultado-consultas", ".txt");

        try {
            Main.procesarConsultas(
                    arbol,
                    Arrays.asList("40", "35", "50"),
                    salida,
                    Integer::valueOf);

            List<String> resultado = Files.readAllLines(salida, StandardCharsets.UTF_8);

            assertEquals(Arrays.asList("40,3", "35,-3", "50,1"), resultado);
        } finally {
            Files.deleteIfExists(salida);
        }
    }
}

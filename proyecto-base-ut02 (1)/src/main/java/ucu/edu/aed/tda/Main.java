package ucu.edu.aed.tda;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {

    private static final Path ARCHIVO_CLAVES = Paths.get("claves.txt");
    private static final Path ARCHIVO_CONSULTAS = Paths.get("consultas.txt");
    private static final Path ARCHIVO_RESULTADO = Paths.get("resultado-consultas.txt");
    private static final Path ARCHIVO_INSERCIONES = Paths.get("resultado-inserciones.txt");

    public static void main(String[] args) throws IOException {
        Path archivoClaves = args.length > 0 ? Paths.get(args[0]) : ARCHIVO_CLAVES;
        Path archivoConsultas = args.length > 1 ? Paths.get(args[1]) : ARCHIVO_CONSULTAS;
        Path archivoResultado = args.length > 2 ? Paths.get(args[2]) : ARCHIVO_RESULTADO;
        Path archivoInserciones = args.length > 3 ? Paths.get(args[3]) : ARCHIVO_INSERCIONES;

        List<String> claves = leerLineasNoVaciasSiExiste(archivoClaves);
        List<String> consultas = leerLineasNoVaciasSiExiste(archivoConsultas);

        if (debeUsarEnteros(claves, consultas)) {
            ArbolBinario<Integer> arbol = new ArbolBinario<>();

            procesarInserciones(arbol, claves, archivoInserciones, Integer::valueOf);
            procesarConsultas(arbol, consultas, archivoResultado, Integer::valueOf);

            System.out.println("Preorden: " + arbol.preOrden());
        } else {
            ArbolBinario<String> arbol = new ArbolBinario<>();

            procesarInserciones(arbol, claves, archivoInserciones, valor -> valor);
            procesarConsultas(arbol, consultas, archivoResultado, valor -> valor);

            System.out.println("Preorden: " + arbol.preOrden());
        }
    }

    public static <T extends Comparable<T>> void procesarInserciones(
            ArbolBinario<T> arbol,
            List<String> claves,
            Path archivoInserciones,
            Function<String, T> convertirClave) throws IOException {

        List<String> lineasResultado = new ArrayList<>();

        for (String claveTexto : claves) {
            int contador;

            try {
                T clave = convertirClave.apply(claveTexto);
                contador = arbol.insertarConContador(clave);
            } catch (RuntimeException e) {
                contador = 0;
            }

            lineasResultado.add(claveTexto + "," + contador);
        }

        Path carpetaResultado = archivoInserciones.getParent();
        if (carpetaResultado != null) {
            Files.createDirectories(carpetaResultado);
        }

        Files.write(archivoInserciones, lineasResultado, StandardCharsets.UTF_8);
    }

    public static <T extends Comparable<T>> void procesarConsultas(
            ArbolBinario<T> arbol,
            List<String> consultas,
            Path archivoResultado,
            Function<String, T> convertirClave) throws IOException {

        List<String> lineasResultado = new ArrayList<>();

        for (String consulta : consultas) {
            int contador;

            try {
                T clave = convertirClave.apply(consulta);
                contador = arbol.buscarConContador(clave);
            } catch (RuntimeException e) {
                contador = -1;
            }

            lineasResultado.add(consulta + "," + contador);
        }

        Path carpetaResultado = archivoResultado.getParent();
        if (carpetaResultado != null) {
            Files.createDirectories(carpetaResultado);
        }

        Files.write(archivoResultado, lineasResultado, StandardCharsets.UTF_8);
    }

    private static List<String> leerLineasNoVaciasSiExiste(Path archivo) throws IOException {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        return leerLineasNoVacias(archivo);
    }

    private static List<String> leerLineasNoVacias(Path archivo) throws IOException {
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        List<String> resultado = new ArrayList<>();

        for (String linea : lineas) {
            String limpia = linea.trim();

            if (!limpia.isEmpty()) {
                resultado.add(limpia);
            }
        }

        return resultado;
    }

    private static boolean debeUsarEnteros(List<String> claves, List<String> consultas) {
        if (!claves.isEmpty()) {
            return sonEnteros(claves);
        }

        return sonEnteros(consultas);
    }

    private static boolean sonEnteros(List<String> valores) {
        if (valores.isEmpty()) {
            return false;
        }

        for (String valor : valores) {
            try {
                Integer.valueOf(valor);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }
}
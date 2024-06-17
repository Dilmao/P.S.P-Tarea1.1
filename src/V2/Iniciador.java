package V2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Iniciador {
    public static final int NUMERO_HILOS = 3;
    public static final int LIMITE_MAXIMO = 500;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // COMENTARIO.
        int l_Contador = 0;

        // Crear un ThreadPoolExecutor con un número fijo de hilos.
        ThreadPoolExecutor l_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMERO_HILOS);

        // Listas para almacenar las tareas de MultiplosS y MultiplosC.
        List<MultiplosS> l_ListaMultiplosS = new ArrayList<>();
        List<MultiplosC> l_ListaMultiplosC = new ArrayList<>();
        List<Integer> l_ResultadosS = new ArrayList<>();
        List<Integer> l_ResultadosC = new ArrayList<>();

        // Población de listas de tareas.
        for (l_Contador = 0; l_Contador <= LIMITE_MAXIMO; l_Contador++) {
            // COMENTARIO.
            MultiplosS multiploS = new MultiplosS(l_Contador);
            MultiplosC multiploC = new MultiplosC(l_Contador);

            // Añadir a la lista de MultiplosS solo si el número es múltiplo de 5.
            if (l_Contador % 5 == 0) {
                l_ListaMultiplosS.add(multiploS);
                l_ListaMultiplosC.add(multiploC);
            }
        }

        // Ejecutar todas las tareas y obtener los resultados.
        List<Future<Integer>> l_ListaResultMultiplosS = l_Executor.invokeAll(l_ListaMultiplosS);
        List<Future<Integer>> l_ListaResultMultiplosC = l_Executor.invokeAll(l_ListaMultiplosC);

        // Cerrar el ThreadPoolExecutor.
        l_Executor.shutdown();

        // Extraer los resultados de las tareas y almacenarlos en listas.
        for (l_Contador = 0; l_Contador < l_ListaResultMultiplosS.size(); l_Contador++) {
            l_ResultadosS.add(l_ListaResultMultiplosS.get(l_Contador).get());
        }

        for (l_Contador = 0; l_Contador < l_ListaResultMultiplosC.size(); l_Contador++) {
            l_ResultadosC.add(l_ListaResultMultiplosC.get(l_Contador).get());
        }

        // Imprimir los resultados comunes a ambas listas.
        for (int l_Result : l_ResultadosS) {
            if (l_ResultadosC.contains(l_Result)) {
                System.out.println(l_Result);
            }
        }
    }
}

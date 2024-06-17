package V1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Familia5 {
    public static final int NUMERO_HILOS = 3;
    public static final int LIMITE_MAXIMO = 500;

    public static void main(String[] args) throws Exception {
        // Inicialización del executor para manejar hilos
        ThreadPoolExecutor l_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMERO_HILOS);

        // Listas para almacenar los resultados y los objetos MultiplosS y MultiplosC
        List<MultiplosS> l_ListaMultiplosS = new ArrayList<>();
        List<MultiplosC> l_ListaMultiplosC = new ArrayList<>();
        List<Integer> l_ResultadosS = new ArrayList<>();
        List<Integer> l_ResultadosC = new ArrayList<>();

        int l_Contador = 0;

        // Bucle para generar instancias de MultiplosS y MultiplosC para números hasta LIMITE_MAXIMO
        while (l_Contador <= LIMITE_MAXIMO) {
            MultiplosS multiploS = new MultiplosS(l_Contador);
            MultiplosC multiploC = new MultiplosC(l_Contador);

            // Verifica si el número es multiplo simple de 5 y lo agrega a la lista correspondiente
            if (multiploS.a_Operador % 5 == 0) {
                l_ListaMultiplosS.add(multiploS);
            }

            // Agrega el objeto MultiplosC a la lista sin verificar múltiplos complejos
            l_ListaMultiplosC.add(multiploC);

            l_Contador++;
        }

        // Invocación de tareas en el executor para los cálculos definidos en las clases MultiplosS y MultiplosC
        List<Future<Integer>> l_ListaResultMultiplosS = l_Executor.invokeAll(l_ListaMultiplosS);
        List<Future<Integer>> l_ListaResultMultiplosC = l_Executor.invokeAll(l_ListaMultiplosC);
        l_Executor.shutdown();

        // Procesamiento de los resultados obtenidos en las tareas invocadas
        for (l_Contador = 0; l_Contador < l_ListaResultMultiplosS.size(); l_Contador++) {
            l_ResultadosS.add(l_ListaResultMultiplosS.get(l_Contador).get());
        }

        for (l_Contador = 0; l_Contador < l_ListaResultMultiplosC.size(); l_Contador++) {
            l_ResultadosC.add(l_ListaResultMultiplosC.get(l_Contador).get());
        }

        // Imprime por pantalla los números que cumplen con ambas oondiciones
        for (int l_Result : l_ResultadosS) {
            if (l_ResultadosC.contains(l_Result)) {
                System.out.println(l_Result);
            }
        }
    }   // main()

    // Clase para calcular múltiplos simples de 5 (el numero base es multiplo de 5)
    static class MultiplosS implements Callable<Integer> {
        private final int a_Operador;

        // Constructor de múltiplos simples
        public MultiplosS(int p_Contador) {
            this.a_Operador = p_Contador;
        }

        @Override
        public Integer call() {
            // Verifica si el número es múltiplo simple de 5
            if (a_Operador % 5 == 0) {
                return a_Operador;
            }

            return 0;
        }

    }   // MultiplosS()

    // Clase para calcular múltiplos complejos de 5 (la suma de los dígitos del número es múltiplo de 5)
    static class MultiplosC implements Callable<Integer> {
        private final int a_Operador;

        // Constructor de multiplos complejos
        public MultiplosC(int p_Contador) {
            this.a_Operador = p_Contador;
        }

        @Override
        public Integer call() {
            int l_NumeroTesteo = a_Operador;
            int l_Acumulador = 0;

            // Calcula la suma de los dígitos del número
            while (l_NumeroTesteo > 0) {
                l_Acumulador += l_NumeroTesteo % 10;
                l_NumeroTesteo /= 10;
            }

            // Verifica si la suma de dígitos es múltiplo de 5
            if (l_Acumulador % 5 == 0) {
                return a_Operador;
            }
            return 0;
        }   // call()
    }   // MultiplosC
}
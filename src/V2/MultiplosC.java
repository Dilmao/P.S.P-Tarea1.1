package V2;

import java.util.concurrent.Callable;

public class MultiplosC implements Callable<Integer> {
    private final int a_Operador;

    public MultiplosC(int p_Contador) {
        this.a_Operador = p_Contador;
    }

    @Override
    public Integer call() {
        // Variables.
        int l_Operador = a_Operador;
        int l_Acumulador = 0;

        // Sumar los dígitos del número.
        while (l_Operador > 0) {
            l_Acumulador += l_Operador % 10;
            l_Operador /= 10;
        }

        // Retorna el número solo si la suma de sus dígitos es múltiplo de 5, de lo contrario retorna 0.
        if (l_Acumulador % 5 == 0) l_Operador = a_Operador;
        return l_Operador;
    }
}

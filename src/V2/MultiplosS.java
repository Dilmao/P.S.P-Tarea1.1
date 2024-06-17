package V2;

import java.util.concurrent.Callable;

public class MultiplosS implements Callable<Integer> {
    private final int a_Operador;

    public MultiplosS(int p_Contador) {
        this.a_Operador = p_Contador;
    }

    @Override
    public Integer call() {
        // Variables.
        int l_Operador = 0;

        // Retorna el número solo si es múltiplo de 5, de lo contrario retorna 0.
        if (a_Operador % 5 == 0) l_Operador = a_Operador;
        return l_Operador;
    }
}

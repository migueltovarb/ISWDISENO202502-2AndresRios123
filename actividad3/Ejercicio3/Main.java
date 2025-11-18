public class Main {
    /**
     * Metodo principal que prueba las operaciones de la clase Account.
     *
     * @param args argumentos de linea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        Account cuentaA = new Account("A001", "Cuenta Corriente", 1000);
        Account cuentaB = new Account("B002", "Cuenta Ahorro", 500);

        System.out.println("Estado inicial:");
        mostrarCuenta(cuentaA);
        mostrarCuenta(cuentaB);

        System.out.println("\nAcreditando 300 a Cuenta A:");
        cuentaA.credit(300);
        mostrarCuenta(cuentaA);

        System.out.println("\nIntentando debitar 2000 de Cuenta A (mayor al saldo actual):");
        cuentaA.debit(2000);
        mostrarCuenta(cuentaA);

        System.out.println("\nTransfiriendo 700 de Cuenta A a Cuenta B:");
        cuentaA.transferTo(cuentaB, 700);
        mostrarCuenta(cuentaA);
        mostrarCuenta(cuentaB);

        System.out.println("\nEstado final:");
        mostrarCuenta(cuentaA);
        mostrarCuenta(cuentaB);
    }

    /**
     * Muestra la informacion relevante de la cuenta.
     *
     * @param account cuenta a mostrar
     */
    private static void mostrarCuenta(Account account) {
        System.out.println(account);
    }
}

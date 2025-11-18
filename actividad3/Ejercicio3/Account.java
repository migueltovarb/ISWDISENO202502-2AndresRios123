public class Account {
    private final String id;
    private final String name;
    private int balance;

    /**
     * Crea una cuenta con saldo inicial en cero.
     *
     * @param id identificador de la cuenta
     * @param name nombre asociado a la cuenta
     */
    public Account(String id, String name) {
        this(id, name, 0);
    }

    /**
     * Crea una cuenta con un saldo inicial definido.
     *
     * @param id identificador de la cuenta
     * @param name nombre asociado a la cuenta
     * @param balance saldo inicial
     */
    public Account(String id, String name, int balance) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede estar vacio.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    /**
     * Devuelve el identificador de la cuenta.
     *
     * @return identificador
     */
    public String getID() {
        return id;
    }

    /**
     * Devuelve el nombre de la cuenta.
     *
     * @return nombre de la cuenta
     */
    public String getName() {
        return name;
    }

    /**
     * Informa el saldo disponible.
     *
     * @return saldo actual
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Agrega fondos a la cuenta y devuelve el nuevo saldo.
     *
     * @param amount monto a acreditar
     * @return saldo actualizado
     */
    public int credit(int amount) {
        validarMontoPositivo(amount);
        balance += amount;
        return balance;
    }

    /**
     * Debita fondos si hay saldo suficiente; si no, informa el error.
     *
     * @param amount monto a debitar
     * @return saldo despues de la operacion (sin cambio si no hubo fondos)
     */
    public int debit(int amount) {
        validarMontoPositivo(amount);
        if (amount > balance) {
            System.out.println("Amount exceeded balance");
            return balance;
        }
        balance -= amount;
        return balance;
    }

    /**
     * Transfiere fondos a otra cuenta si el saldo es suficiente.
     *
     * @param another cuenta destino de la transferencia
     * @param amount monto a transferir
     * @return saldo restante tras la operacion (sin cambio si no hubo fondos)
     */
    public int transferTo(Account another, int amount) {
        if (another == null) {
            throw new IllegalArgumentException("La cuenta destino no puede ser nula.");
        }
        validarMontoPositivo(amount);
        if (amount > balance) {
            System.out.println("Amount exceeded balance");
            return balance;
        }
        balance -= amount;
        another.balance += amount;
        return balance;
    }

    @Override
    public String toString() {
        return "Account[id=" + id + ",name=" + name + ",balance=" + balance + "]";
    }

    /**
     * Verifica que el monto sea positivo.
     *
     * @param amount monto a evaluar
     */
    private void validarMontoPositivo(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
    }
}

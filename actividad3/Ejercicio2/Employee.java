public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private int salary;

    /**
     * Crea un empleado con datos basicos y salario mensual.
     *
     * @param id identificador unico del empleado
     * @param firstName nombre del empleado
     * @param lastName apellido del empleado
     * @param salary salario mensual en unidades monetarias
     */
    public Employee(int id, String firstName, String lastName, int salary) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio.");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo.");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    /**
     * Devuelve el identificador del empleado.
     *
     * @return identificador unico
     */
    public int getID() {
        return id;
    }

    /**
     * Devuelve el nombre del empleado.
     *
     * @return nombre
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Devuelve el apellido del empleado.
     *
     * @return apellido
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Obtiene el nombre completo en el formato "nombre apellido".
     *
     * @return nombre completo del empleado
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    /**
     * Muestra el salario mensual actual.
     *
     * @return salario mensual
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Actualiza el salario mensual comprobando que no sea negativo.
     *
     * @param salary nuevo salario mensual
     */
    public void setSalary(int salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo.");
        }
        this.salary = salary;
    }

    /**
     * Calcula el salario anual multiplicando el salario mensual por 12.
     *
     * @return salario anual
     */
    public int getAnnualSalary() {
        return salary * 12;
    }

    /**
     * Incrementa el salario en el porcentaje indicado y devuelve el nuevo salario.
     *
     * @param percent porcentaje de aumento (0 o mayor)
     * @return salario actualizado tras el aumento
     */
    public int raiseSalary(int percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("El porcentaje no puede ser negativo.");
        }
        int incremento = (int) Math.round(salary * percent / 100.0);
        salary += incremento;
        return salary;
    }

    @Override
    public String toString() {
        return "Employee[id=" + id + ",name=" + getName() + ",salary=" + salary + "]";
    }
}

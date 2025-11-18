public class TestEmployee {
    /**
     * Metodo principal que crea un empleado y prueba sus operaciones publicas.
     *
     * @param args argumentos de linea de comandos sin uso
     */
    public static void main(String[] args) {
        Employee employee = new Employee(1, "Laura", "Gomez", 2500);

        System.out.println("Datos iniciales:");
        mostrarEmpleado(employee);
        System.out.println("Salario anual: " + employee.getAnnualSalary());

        int nuevoSalario = employee.raiseSalary(15);
        System.out.println("\nSalario tras aumento del 15%: " + nuevoSalario);
        mostrarEmpleado(employee);

        employee.setSalary(3000);
        System.out.println("\nSalario fijado manualmente a 3000.");
        mostrarEmpleado(employee);
    }

    /**
     * Imprime el estado completo del empleado usando sus getters.
     *
     * @param employee empleado que se desea mostrar
     */
    private static void mostrarEmpleado(Employee employee) {
        System.out.println(employee);
        System.out.println("ID: " + employee.getID());
        System.out.println("Nombre: " + employee.getFirstName());
        System.out.println("Apellido: " + employee.getLastName());
        System.out.println("Nombre completo: " + employee.getName());
        System.out.println("Salario mensual: " + employee.getSalary());
    }
}

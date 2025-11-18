public class TestCircle {
    /**
     * Punto de entrada para ejercitar el comportamiento de Circle.
     *
     * @param args argumentos del programa sin uso
     */
    public static void main(String[] args) {
        Circle defaultCircle = new Circle();
        Circle customCircle = new Circle(5.5);

        displayCircle("Default circle", defaultCircle);
        displayCircle("Custom circle", customCircle);

        customCircle.setRadius(2.75);
        displayCircle("Updated custom circle", customCircle);
    }

    /**
     * Imprime los detalles del circulo y sus medidas derivadas.
     *
     * @param label descripcion que identifica al circulo
     * @param circle instancia de circulo que se mostrara
     */
    private static void displayCircle(String label, Circle circle) {
        System.out.println(label + ":");
        System.out.println("  " + circle);
        System.out.println("  Area: " + circle.getArea());
        System.out.println("  Circumference: " + circle.getCircumference());
        System.out.println();
    }
}

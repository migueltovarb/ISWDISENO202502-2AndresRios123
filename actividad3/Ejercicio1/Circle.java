public class Circle {
    private double radius = 1.0;

    /**
     * Construye un circulo con el radio por defecto 1.0.
     */
    public Circle() {
        // radio inicializado con valor por defecto
    }

    /**
     * Construye un circulo con un radio personalizado.
     *
     * @param radius radio que se asignara al circulo
     */
    public Circle(double radius) {
        setRadius(radius);
    }

    /**
     * Devuelve el valor del radio actual.
     *
     * @return radio del circulo
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Actualiza el radio asegurando que sea positivo.
     *
     * @param radius nuevo valor del radio
     */
    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("El radio debe ser mayor que cero.");
        }
        this.radius = radius;
    }

    /**
     * Calcula el area usando la formula PI * r^2.
     *
     * @return area del circulo
     */
    public double getArea() {
        return Math.PI * radius * radius;
    }

    /**
     * Calcula la circunferencia usando la formula 2 * PI * r.
     *
     * @return circunferencia del circulo
     */
    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Circle[radius=%.2f]", radius);
    }
}

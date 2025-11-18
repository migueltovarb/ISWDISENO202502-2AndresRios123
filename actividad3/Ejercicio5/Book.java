public class Book {
    private final String name;
    private final Author author;
    private double price;
    private int qty;

    /**
     * Crea un libro sin cantidad especificada (por defecto 0).
     *
     * @param name nombre del libro
     * @param author autor responsable del libro
     * @param price precio actual del libro
     */
    public Book(String name, Author author, double price) {
        this(name, author, price, 0);
    }

    /**
     * Crea un libro con todos sus datos incluidos.
     *
     * @param name nombre del libro
     * @param author autor responsable del libro
     * @param price precio actual del libro
     * @param qty cantidad disponible en inventario
     */
    public Book(String name, Author author, double price, int qty) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (author == null) {
            throw new IllegalArgumentException("El autor no puede ser nulo.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (qty < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }

    /**
     * Devuelve el nombre del libro.
     *
     * @return nombre del libro
     */
    public String getName() {
        return name;
    }

    /**
     * Entrega la referencia al autor del libro.
     *
     * @return autor asociado
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Muestra el precio actual del libro.
     *
     * @return precio
     */
    public double getPrice() {
        return price;
    }

    /**
     * Actualiza el precio del libro.
     *
     * @param price nuevo precio
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.price = price;
    }

    /**
     * Obtiene la cantidad disponible del libro.
     *
     * @return cantidad en inventario
     */
    public int getQty() {
        return qty;
    }

    /**
     * Actualiza la cantidad disponible.
     *
     * @param qty nueva cantidad
     */
    public void setQty(int qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Book[name=" + name + "," + author + ",price=" + price + ",qty=" + qty + "]";
    }
}

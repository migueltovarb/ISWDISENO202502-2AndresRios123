public class Main {
    /**
     * Metodo principal que demuestra el uso de Book y Author.
     *
     * @param args argumentos de linea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        Author author = new Author("Isabel Allende", "isabel@example.com", 'f');
        Book book = new Book("La Casa de los Espiritus", author, 19.99, 50);

        System.out.println("Nombre del libro: " + book.getName());
        System.out.println("Autor: " + book.getAuthor());
        System.out.println("Precio: " + book.getPrice());
        System.out.println("Cantidad: " + book.getQty());

        book.setPrice(22.5);
        book.setQty(40);

        System.out.println("\nEstado actualizado: " + book);
    }
}

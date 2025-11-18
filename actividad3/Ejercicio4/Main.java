public class Main {
    /**
     * Metodo principal que demuestra el uso de la clase Author.
     *
     * @param args argumentos de linea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        Author author = new Author("Gabriel Garcia", "gabriel@example.com", 'm');

        System.out.println("Nombre: " + author.getName());
        System.out.println("Email: " + author.getEmail());
        System.out.println("Genero: " + author.getGender());

        author.setEmail("gabo@example.com");
        System.out.println("Email actualizado: " + author.getEmail());

        System.out.println("\nRepresentacion completa: " + author);
    }
}

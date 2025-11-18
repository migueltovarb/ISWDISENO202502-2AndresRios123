public class Author {
    private final String name;
    private String email;
    private final char gender;

    /**
     * Crea un autor con nombre, correo y genero.
     *
     * @param name nombre del autor
     * @param email correo electronico del autor
     * @param gender genero del autor, solo se acepta 'm' o 'f'
     */
    public Author(String name, String email, char gender) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        validarEmail(email);
        if (gender != 'm' && gender != 'f') {
            throw new IllegalArgumentException("El genero debe ser 'm' o 'f'.");
        }
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    /**
     * Devuelve el nombre del autor.
     *
     * @return nombre completo
     */
    public String getName() {
        return name;
    }
    /**
     * Obtiene el correo electronico actual.
     *
     * @return correo electronico
     */
    public String getEmail() {
        return email;
    }

    /**
     * Actualiza el correo electronico.
     *
     * @param email nuevo correo electronico
     */
    public void setEmail(String email) {
        validarEmail(email);
        this.email = email;
    }

    /**
     * Devuelve el genero del autor.
     *
     * @return genero ('m' o 'f')
     */
    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Author[name=" + name + ",email=" + email + ",gender=" + gender + "]";
    }

    /**
     * Valida que el correo sea una cadena no vacia.
     *
     * @param email correo a validar
     */
    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacio.");
        }
    }
}

package bl.Entities;
import java.time.*;
import java.util.*;
/**
 * Representa un usuario dentro del sistema de subastas.
 *
 * Un usuario posee información personal como nombre, correo electrónico,
 * fecha de nacimiento, contraseña y rol dentro del sistema.
 * Además mantiene una puntuación de reputación y, en el caso de los
 * coleccionistas, una colección de objetos.
 *
 * Los roles posibles son:
 * 1 - Coleccionista
 * 2 - Vendedor
 * 3 - Moderador
 *
 * Cada usuario recibe automáticamente un identificador único
 * generado mediante un contador estático.
 */
public class Usuario {

    private static int contador = 1;
    private static int contadorPuntuacion = 0; // Controla la puntuacion de los usuarios (Moderador se queda en 0 porque no participa)

    //Atributo
    private String nombreCompleto;
    private int id;
    private Period edad;
    private LocalDate fechaNacimiento;
    private String clave;
    private String correo;
    private int rol;
    private int puntuacion; //reputacion del usuario
    private ArrayList<Objeto> coleccion; //coleccion de objetos del coleccionista

    //Constructores
    /**
     * Constructor vacío de la clase Usuario.
     * Permite crear un usuario sin inicializar sus atributos.
     */
    public Usuario() {
    }
    /**
     * Crea un nuevo usuario con la información especificada.
     *
     * @param nombreCompleto nombre completo del usuario
     * @param fechaNacimiento fecha de nacimiento del usuario
     * @param clave contraseña del usuario
     * @param correo correo electrónico del usuario
     * @param rol rol asignado dentro del sistema
     */

    public Usuario(String nombreCompleto, LocalDate fechaNacimiento, String clave, String correo, int rol){
        this.nombreCompleto = nombreCompleto;
        this.id = contador++; //el contador aumenta por cada registro de usuario (ej: si ya hay un usuario con ID#1, el contador ahora tiene el valor de 2)
        this.edad = calcularEdad(fechaNacimiento);
        this.fechaNacimiento = fechaNacimiento;
        this.clave = clave;
        this.correo = correo;
        this.rol = rol;
        this.puntuacion = contadorPuntuacion;
        this.coleccion = new ArrayList<>();
    }

    //Getters y Setters
    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombreCompleto nuevo nombre completo
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    /**
     * Obtiene el identificador único del usuario.
     *
     * @return identificador del usuario
     */
    public int getId() {
        return id;
    }
    /**
     * Obtiene la fecha de nacimiento del usuario.
     *
     * @return fecha de nacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    /**
     * Modifica la fecha de nacimiento del usuario.
     *
     * @param fechaNacimiento nueva fecha de nacimiento
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña
     */
    public String getClave() {
        return clave;
    }
    /**
     * Modifica la contraseña del usuario.
     *
     * @param clave nueva contraseña
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo del usuario
     */
    public String getCorreo() {
        return correo;
    }
    /**
     * Modifica el correo electrónico del usuario.
     *
     * @param correo nuevo correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    /**
     * Obtiene el rol del usuario representado como texto.
     *
     * @return rol del usuario
     */
    public String getRol() {
        switch(rol){
            case 1:
                return "Coleccionista";
            case 2:
                return "Vendedor";
            case 3:
                return "Moderador";
            default:
                return "Tipo de usuario invalido";
        }
    }
    /**
     * Obtiene la puntuación de reputación del usuario.
     *
     * @return puntuación del usuario
     */
    public int getPuntuacion(){
        return puntuacion;
    }
    /**
     * Modifica la puntuación del usuario.
     *
     * @param puntuacion nueva puntuación
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    /**
     * Obtiene la colección de objetos del usuario.
     *
     * @return lista de objetos
     */
    public ArrayList<Objeto> getColeccion() {
        return coleccion;
    }
    /**
     * Modifica la colección de objetos del usuario.
     *
     * @param coleccion nueva colección
     */
    public void setColeccion(ArrayList<Objeto> coleccion) {
        this.coleccion = coleccion;
    }
    /**
     * Modifica el rol del usuario.
     *
     * @param rol nuevo rol
     */
    public void setRol(int rol) {
        this.rol = rol;
    }
    /**
     * Obtiene la edad del usuario calculada a partir de su fecha de nacimiento.
     *
     * @return periodo de edad
     */
    public Period getEdad() {
        return calcularEdad(fechaNacimiento);
    }
    /**
     * Calcula la edad del usuario utilizando la fecha de nacimiento.
     *
     * @param fechaNacimiento fecha de nacimiento
     * @return periodo de tiempo representando la edad
     */
    //Metodo para calcular edad
    public Period calcularEdad(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now(); //hoy representa la fecha actual
       return Period.between(fechaNacimiento, hoy); //Period.between analiza la cantidad entre la fecha de nacimiento y la fecha actual.
    }
    /**
     * Devuelve una representación en texto del usuario con sus datos principales.
     *
     * @return representación del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "Nombre completo: " + nombreCompleto +
                ", ID: #" + id +
                ", Edad: " + edad.getYears() + //Retorna los años actual del persona.
                ", Fecha de nacimiento: " + fechaNacimiento +
                ", Correo: " + correo +
                ", Rol: " + getRol() + //getRol() para que nos imprima el rol y no el numero ingresado para rol.
                ", Puntuacion total: " + puntuacion +
                ", Coleccion: " + coleccion +
                '}';
    }
}
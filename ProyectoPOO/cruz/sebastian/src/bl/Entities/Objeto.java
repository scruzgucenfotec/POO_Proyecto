package bl.Entities;
import java.time.*;
/**
 * Clase que representa un objeto que puede ser subastado dentro del sistema.
 *
 * Cada objeto posee información como nombre, código, descripción, estado,
 * fecha de compra y antigüedad. También cuenta con un identificador único
 * que se asigna automáticamente mediante un contador estático.
 */
public class Objeto {

    private static int contador = 1;

    //Atributos
    private int id; //Para identificar los objetos
    private String nombre;
    private String codigo;
    private String descripcion;
    private int estado;
    private LocalDate fechaCompra;
    private LocalDate antiguedad;

    //Constructores
    /**
     * Constructor vacío de la clase Objeto.
     * Permite crear un objeto sin inicializar sus atributos.
     */
    public Objeto(){}
    /**
     * Constructor que inicializa todos los atributos principales del objeto.
     * El identificador del objeto se genera automáticamente.
     *
     * @param nombre nombre del objeto
     * @param descripcion descripción del objeto
     * @param codigo código único del objeto
     * @param estado estado del objeto (nuevo, usado o antiguo sin abrir)
     * @param fechaCompra fecha en la que el objeto fue adquirido
     * @param antiguedad fecha utilizada para calcular la antigüedad del objeto
     */
    public Objeto(String nombre, String descripcion, String codigo, int estado, LocalDate fechaCompra, LocalDate antiguedad) {
        this.id = contador++;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
        this.antiguedad = antiguedad;
    }

    //Getters y Setters
    /**
     * Obtiene el nombre del objeto.
     *
     * @return nombre del objeto
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre del objeto.
     *
     * @param nombre nuevo nombre del objeto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene el código identificador del objeto.
     *
     * @return código del objeto
     */

    public String getCodigo() {
        return codigo;
    }
    /**
     * Establece el código del objeto.
     *
     * @param codigo nuevo código del objeto
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    /**
     * Obtiene la descripción del objeto.
     *
     * @return descripción del objeto
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establece la descripción del objeto.
     *
     * @param descripcion nueva descripción del objeto
     */

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Obtiene el estado del objeto en formato de texto.
     * Convierte el valor numérico almacenado en una descripción legible.
     *
     * @return estado del objeto como texto
     */
    public String getEstado() {
        switch(estado){
            case 1:
                return "Nuevo";
            case 2:
                return "Usado";
            case 3:
                return "Antiguo sin abrir";
            default:
                return "Registro no valido";
        }
    }
    /**
     * Establece el estado del objeto.
     *
     * @param estado valor numérico del estado del objeto
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    /**
     * Obtiene la fecha de compra del objeto.
     *
     * @return fecha de compra
     */
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }
    /**
     * Establece la fecha de compra del objeto.
     *
     * @param fechaCompra nueva fecha de compra
     */
    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    /**
     * Obtiene la antigüedad del objeto calculada desde la fecha indicada.
     *
     * @return periodo de antigüedad
     */
    public Period getAntiguedad() {
        return calcularAntiguedad(antiguedad);
    }

    //Metodo para calcular antigüedad
    /**
     * Calcula la antigüedad del objeto con respecto a la fecha actual.
     *
     * @param antiguedad fecha base para calcular antigüedad
     * @return objeto Period con la diferencia entre fechas
     */
    public Period calcularAntiguedad(LocalDate antiguedad) {
        LocalDate hoy = LocalDate.now();
        return Period.between(antiguedad, hoy);
    }

    /**
     * Devuelve una representación en texto del objeto
     * incluyendo sus principales atributos.
     *
     * @return información del objeto en formato String
     */
    @Override
    public String toString() {
        return "Objeto{" +
                "ID: #" + id +
                "Nombre: " + nombre +
                ", Codigo: " + codigo +
                ", Descripcion: " + descripcion +
                ", Estado: " + getEstado() +
                ", Fecha de compra: " + getFechaCompra() +
                ", Antiguedad: " + getAntiguedad().getYears() + " años, " + getAntiguedad().getMonths() + " meses y " + getAntiguedad().getDays() + " dias" +
                '}';
    }
}
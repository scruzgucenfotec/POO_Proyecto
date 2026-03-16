package bl.Entities;
import java.time.*;
import java.util.ArrayList;
/**
 * Clase que representa una subasta dentro del sistema.
 *
 * Una subasta contiene información sobre el usuario que la creó,
 * la fecha de vencimiento, el precio mínimo requerido para participar,
 * los objetos incluidos en la subasta y el estado actual de la misma.
 */
public class Subasta {

    static private int contador = 1;

    //Atributos
    private int id;
    private LocalDateTime fechaVencimiento;
    private Usuario usuarioCreador;
    private double precioMinimo;
    private ArrayList<Objeto> objetos;
    private int estado;
    private Objeto fechaCompra;

    //Constructores
    /**
     * Constructor vacío de la clase Subasta.
     * Permite crear una subasta sin inicializar sus atributos.
     */

    public Subasta(){}
    /**
     * Constructor que inicializa los atributos principales de una subasta.
     * El identificador de la subasta se genera automáticamente.
     *
     * @param fechaVencimiento fecha y hora en que finaliza la subasta
     * @param usuarioCreador usuario que creó la subasta
     * @param precioMinimo precio mínimo requerido para participar
     * @param objetos lista de objetos incluidos en la subasta
     * @param estado estado actual de la subasta
     * @param puntuacion puntuación del creador (parámetro recibido pero no utilizado)
     */

    public Subasta(LocalDateTime fechaVencimiento, Usuario usuarioCreador, double precioMinimo, ArrayList<Objeto> objetos, int estado, int puntuacion) {
            this.id = contador++;
            this.fechaVencimiento = fechaVencimiento;
            this.usuarioCreador = usuarioCreador;
            this.precioMinimo = precioMinimo;
            this.objetos = objetos;
            this.estado = estado;
    }

    //Getters y Setters
    /**
     * Obtiene el identificador de la subasta.
     *
     * @return id de la subasta
     */

    public int getId() {
        return id;
    }
    /**
     * Obtiene la fecha de vencimiento de la subasta.
     *
     * @return fecha y hora de vencimiento
     */

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }
    /**
     * Establece la fecha de vencimiento de la subasta.
     *
     * @param fechaVencimiento nueva fecha de vencimiento
     */
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    /**
     * Obtiene el usuario que creó la subasta.
     *
     * @return usuario creador
     */
    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }
    /**
     * Establece el usuario creador de la subasta.
     *
     * @param usuarioCreador nuevo usuario creador
     */
    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
    /**
     * Obtiene el precio mínimo requerido para participar en la subasta.
     *
     * @return precio mínimo
     */

    public double getPrecioMinimo() {
        return precioMinimo;
    }
    /**
     * Establece el precio mínimo de la subasta.
     *
     * @param precioMinimo nuevo precio mínimo
     */
    public void setPrecioMinimo(double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }
    /**
     * Obtiene la lista de objetos incluidos en la subasta.
     *
     * @return lista de objetos
     */
    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }
    /**
     * Establece la lista de objetos que se subastarán.
     *
     * @param objetos nueva lista de objetos
     */
    public void setObjetos(ArrayList<Objeto> objetos) {
        this.objetos = objetos;
    }
    /**
     * Obtiene el estado de la subasta en formato de texto.
     *
     * @return estado de la subasta
     *
     */
    public String getEstado() {
        switch(estado){
            case 1:
                return "Activo";
            default:
                return "Terminado";
        }
    }

    /**
     * Calcula el tiempo restante para que la subasta llegue a su fecha de vencimiento.
     *
     * El cálculo se realiza comparando la fecha y hora actual del sistema
     * con la fecha de vencimiento de la subasta. A partir de esa diferencia
     * se obtienen los días, horas, minutos y segundos restantes.
     *
     * En caso de que la fecha actual sea posterior a la fecha de vencimiento,
     * se considera que la subasta ya ha finalizado.
     *
     * @return una cadena de texto que indica el tiempo restante expresado
     * en días, horas, minutos y segundos, o un mensaje indicando que la
     * subasta ha finalizado.
     */
    public String getTiempoRestante(){

        LocalDateTime ahora = LocalDateTime.now(); //Duration permite completar la tarea de calcular tiempo restante
        Duration duracion = Duration.between(ahora, fechaVencimiento);

        if(duracion.isNegative()){ //Si el tiempo restante es menor a 0
            return "Subasta finalizada";
        }

        long segundos = duracion.getSeconds(); //long se encarga de guardar numeros enteros muy grandes

        long dias = segundos / (24 * 3600);
        segundos %= (24 * 3600);

        long horas = segundos / 3600;
        segundos %= 3600;

        long minutos = segundos / 60;
        long seg = segundos % 60;

        return dias + " dias, " + horas + " horas, " + minutos + " minutos, " + seg + " segundos";
    }

    /**
     * Devuelve una representación en texto de la subasta
     * incluyendo su información principal.
     *
     * @return información de la subasta en formato String
     */
    @Override
    public String toString() {
        return "Subasta{" +
                "ID: " + id +
                ", Fecha de vencimiento: " + fechaVencimiento +
                ", Tiempo restante: " + getTiempoRestante() +
                ", Usuario Creador: " + usuarioCreador +
                ", Precio minimo: " + precioMinimo +
                ", Objetos subastados: " + objetos +
                ", Estado: '" + getEstado() + '\'' +
                '}';
    }
}
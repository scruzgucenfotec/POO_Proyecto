package bl.Entities;
import java.time.*;
import java.util.*;

/**
 * Clase que representa una orden generada después de finalizar una subasta.
 *
 * Una orden contiene información sobre el usuario ganador de la subasta,
 * la fecha en que se genera la orden, los objetos adjudicados al ganador
 * y el monto total correspondiente a los objetos obtenidos.
 */

public class Orden {
    private Usuario ganador;
    private LocalDate fechaOrden;
    private ArrayList<Objeto> objetosAdjudicados;
    private int montoTotal;
    /**
     * Constructor vacío de la clase Orden.
     * Permite crear una orden sin inicializar sus atributos.
     */
    public Orden(){}
    /**
     * Constructor que inicializa todos los atributos de la orden.
     *
     * @param ganador usuario que ganó la subasta
     * @param fechaOrden fecha en que se genera la orden
     * @param objetosAdjudicados lista de objetos obtenidos por el ganador
     * @param montoTotal monto total correspondiente a los objetos adjudicados
     */
    public Orden(Usuario ganador, LocalDate fechaOrden, ArrayList<Objeto> objetosAdjudicados, int montoTotal) {
        this.ganador = ganador;
        this.fechaOrden = fechaOrden;
        this.objetosAdjudicados = objetosAdjudicados;
        this.montoTotal = montoTotal;
    }
    /**
     * Obtiene el usuario ganador de la subasta.
     *
     * @return usuario ganador
     */
    public Usuario getGanador() {
        return ganador;
    }
    /**
     * Establece el usuario ganador de la subasta.
     *
     * @param ganador nuevo usuario ganador
     */
    public void setGanador(Usuario ganador) {
        this.ganador = ganador;
    }
    /**
     * Obtiene la fecha en la que se generó la orden.
     *
     * @return fecha de la orden
     */
    public LocalDate getFechaOrden() {
        return fechaOrden;
    }
    /**
     * Establece la fecha en la que se genera la orden.
     *
     * @param fechaOrden nueva fecha de la orden
     */
    public void setFechaOrden(LocalDate fechaOrden) {
        this.fechaOrden = fechaOrden;
    }
    /**
     * Obtiene la lista de objetos adjudicados al ganador.
     *
     * @return lista de objetos adjudicados
     */
    public ArrayList<Objeto> getObjetosAdjudicados() {
        return objetosAdjudicados;
    }
    /**
     * Establece la lista de objetos adjudicados al ganador.
     *
     * @param objetosAdjudicados nueva lista de objetos adjudicados
     */
    public void setObjetosAdjudicados(ArrayList<Objeto> objetosAdjudicados) {
        this.objetosAdjudicados = objetosAdjudicados;
    }
    /**
     * Obtiene el monto total asociado a la orden.
     *
     * @return monto total de la orden
     */
    public int getMontoTotal() {
        return montoTotal;
    }
    /**
     * Establece el monto total de la orden.
     *
     * @param montoTotal nuevo monto total
     */
    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }
    /**
     * Devuelve una representación en texto de la orden
     * incluyendo el ganador, fecha, objetos adjudicados y monto total.
     *
     * @return información de la orden en formato String
     */
    @Override
    public String toString() {
        return "Orden{" +
                "Ganador: " + ganador +
                ", FechaOrden" + fechaOrden +
                ", Objetos adjudicados: " + objetosAdjudicados +
                ", Monto total: " + montoTotal +
                '}';
    }
}

package bl.Entities;
/**
 * Clase que representa una oferta realizada dentro de una subasta.
 *
 * Una oferta está asociada a un usuario oferente y contiene el precio
 * que dicho usuario está dispuesto a pagar por el objeto subastado.
 * Cada oferta posee un identificador único generado automáticamente.
 */
public class Oferta {

    //Constante
    static private int contador = 1;

    //Atributos
    private Usuario oferente;
    private int id;
    private double precioOfertado;

    //Constructores
    /**
     * Constructor vacío de la clase Oferta.
     * Permite crear una oferta sin inicializar sus atributos.
     */
    public Oferta(){}
    /**
     * Constructor que crea una nueva oferta asociada a un usuario.
     * El identificador de la oferta se genera automáticamente.
     *
     * @param oferente usuario que realiza la oferta
     * @param precioOfertado valor monetario ofrecido por el objeto
     */
    public Oferta(Usuario oferente, double precioOfertado) {
        this.oferente = oferente;
        this.id = contador++;
        this.precioOfertado = precioOfertado;
    }

    //Getters y Setters
    /**
     * Obtiene el usuario que realizó la oferta.
     *
     * @return usuario oferente
     */

    public Usuario getOferente() {
        return oferente;
    }
    /**
     * Establece el usuario que realizó la oferta.
     *
     * @param oferente nuevo usuario oferente
     */

    public void setOferente(Usuario oferente) {
        this.oferente = oferente;
    }

    /**
     * Obtiene el identificador de la oferta.
     *
     * @return id de la oferta
     */
    public int getId() {
        return id;
    }
    /**
     * Obtiene el precio ofertado por el usuario.
     *
     * @return precio ofertado
     */
    public double getPrecioOfertado() {
        return precioOfertado;
    }
    /**
     * Establece el precio ofertado.
     *
     * @param precioOfertado nuevo valor de la oferta
     */
    public void setPrecioOfertado(double precioOfertado) {
        this.precioOfertado = precioOfertado;
    }
    /**
     * Obtiene la puntuación del usuario que realizó la oferta.
     *
     * @return puntuación del oferente
     */
    public int getPuntuacionOferente() {
        return oferente.getPuntuacion();
    }
    /**
     * Devuelve una representación en texto de la oferta
     * incluyendo información del oferente, precio y puntuación.
     *
     * @return información de la oferta en formato String
     */
    @Override
    public String toString() {
        return "Oferta{" +
                "Oferente: " + oferente.getNombreCompleto() +
                ", ID: " + id +
                ", Precio ofertado: " + precioOfertado +
                ", Puntuacion: " + oferente.getPuntuacion() +
                '}';
    }
}
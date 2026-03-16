package dl;
import bl.Entities.*;
import java.util.ArrayList;

/**
 * Clase que simula la capa de acceso a datos del sistema.
 *
 * Esta clase funciona como una base de datos en memoria donde se almacenan
 * los usuarios, objetos subastados, subastas y ofertas registradas en el sistema.
 *
 * Proporciona métodos para registrar y consultar estos datos.
 */
public class Data {

    static Data db = new Data();
    private ArrayList<Usuario> usuarios;private ArrayList<Objeto> objetosSubastados;
    private ArrayList<Subasta> subastas;
    private ArrayList<Oferta> ofertas;
    /**
     * Constructor de la clase Data.
     * Inicializa las listas utilizadas para almacenar la información del sistema.
     */
    public Data(){
        usuarios = new ArrayList<>();
        objetosSubastados = new ArrayList<>();
        subastas = new ArrayList<>();
        ofertas = new ArrayList<>();
    }

    //----------------------------------------------------------------------------------------------------------------
    //METODOS USUARIO
    //----------------------------------------------------------------------------------------------------------------
    /**
     * Registra un usuario en el sistema si no existe previamente.
     *
     * @param u objeto de tipo Usuario que se desea registrar
     */
    public void registrarUsuario(Usuario u){
        if (!existeUsuarios(u.getCorreo())){
            usuarios.add(u);
        }
    }
    /**
     * Verifica si existe un usuario registrado con el correo indicado.
     *
     * @param correo correo del usuario a buscar
     * @return true si el usuario existe, false en caso contrario
     */

    public Boolean existeUsuarios(String correo){

        for (Usuario u: usuarios){
            if (u.getCorreo().equals(correo)){
                return true;
            }
        }
        return false;
    }
    /**
     * Obtiene la lista completa de usuarios registrados.
     *
     * @return lista de usuarios
     */
    public ArrayList<Usuario> getUsuarios(){
        return usuarios;
    }

    //----------------------------------------------------------------------------------------------------------------
    //METODOS DATA DE SUBASTA
    //----------------------------------------------------------------------------------------------------------------

    /**
     * Registra una nueva subasta si no existe otra con el mismo ID.
     *
     * @param s objeto Subasta que se desea registrar
     */
    public void registrarSubasta(Subasta s){
        if (!existeSubasta(s.getId())){
            subastas.add(s);
        }
    }

    /**
     * Verifica si una subasta con el ID indicado ya existe.
     *
     * @param id identificador de la subasta
     * @return true si existe, false si no existe
     */
    public Boolean existeSubasta(int id){

        for (Subasta s: subastas){
            if (s.getId() == id){
                return true;
            }
        }
        return false;
    }
    /**
     * Obtiene la lista de subastas registradas.
     *
     * @return lista de subastas
     */
    public ArrayList<Subasta> getSubastas(){
        return subastas;
    }

    //----------------------------------------------------------------------------------------------------------------
    //METODOS DATA DE OBJETOS
    //----------------------------------------------------------------------------------------------------------------

    /**
     * Registra un objeto que será utilizado dentro de una subasta.
     *
     * @param o objeto a registrar
     */

    public void registrarObjetos(Objeto o){
        objetosSubastados.add(o);
    }
    /**
     * Obtiene la lista de objetos registrados para subasta.
     *
     * @return lista de objetos subastados
     */
    public ArrayList<Objeto> getObjetosSubastados(){
        return objetosSubastados;
    }

    //----------------------------------------------------------------------------------------------------------------
    //METODOS DATA DE OFERTAS
    //----------------------------------------------------------------------------------------------------------------

    /**
     * Registra una oferta dentro de la subasta correspondiente
     * si el código del objeto coincide con uno de los objetos subastados.
     *
     * @param o objeto Oferta que se desea registrar
     * @param codigo código del objeto al que se realiza la oferta
     */
    public void registrarOferta(Oferta o, String codigo){
        for(Subasta s : subastas){ //Recorre subastas registradas
            for(Objeto obj : s.getObjetos()){ //Recorre objetos registrados

                if(obj.getCodigo().equals(codigo)){ //Si el codigo existe
                    ofertas.add(o); //Registra la oferta
                }
            }
        }
    }

    /**
     * Obtiene la lista de todas las ofertas registradas en el sistema.
     *
     * @return lista de ofertas
     */
    public ArrayList<Oferta> getOfertas(){
        return ofertas;
    }
}
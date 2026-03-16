package bl.Logic;
import dl.Data;
import bl.Entities.*;
import java.util.*;
import java.time.*;
/**
 * Clase encargada de gestionar la lógica de negocio del sistema de subastas.
 *
 * Esta clase funciona como intermediaria entre la interfaz de usuario
 * y la capa de acceso a datos. Aquí se procesan las validaciones,
 * reglas del sistema y operaciones principales relacionadas con:
 * usuarios, subastas, objetos, ofertas y finalización de subastas.
 */
public class Service {

    /**
     * Instancia de la capa de datos utilizada para almacenar la información.
     */
    private Data db;

    /**
     * Constructor por defecto de la clase Service.
     */
    public Service(){}

    //------------------------------------------------------------------------------------------------------------------
    //METODOS GENERALES
    //------------------------------------------------------------------------------------------------------------------

    //METODO PARA VERIFICAR BASE DE DATOS
    /**
     * Inicializa la base de datos si aún no ha sido creada.
     * Garantiza que exista una instancia válida antes de realizar operaciones.
     */
    public void setDb(){
        if (db == null){
            db = new Data();
        }
    }
    /**
     * Interpreta una respuesta de confirmación del usuario.
     *
     * @param opcion texto ingresado por el usuario
     * @return true si la respuesta es afirmativa, false en cualquier otro caso
     */
    //METODO PARA CONFIRMAR SI O NO
    public Boolean confirmacion (String opcion){
        if (opcion.equalsIgnoreCase("Si")){
            return true;
        }
        if(opcion.equalsIgnoreCase("No")){
            return false;
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    //METODOS USUARIO
    //------------------------------------------------------------------------------------------------------------------

    //METODO PARA LISTAR USUARIOS
    /**
     * Obtiene la lista de usuarios registrados en el sistema.
     *
     * @return lista de representaciones en texto de los usuarios
     */
    public ArrayList<String> listarUsuarios(){
        setDb(); //verifica si no hay base de datos activa

        ArrayList<String> lista = new ArrayList<>();

        if (db.getUsuarios().size() > 0) {
            for (Usuario u : db.getUsuarios()){
                lista.add(u.toString());
            }
        }
        else {
            lista.add("No existen usuarios registrados");
        }
        return lista;
    }

    //METODO PARA INSERTAR DATOS DE USUARIO
    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nombre nombre completo
     * @param fechaNacimiento fecha de nacimiento
     * @param clave contraseña
     * @param correo correo electrónico
     * @param rol rol del usuario
     * @return true si el registro fue exitoso
     */
    public boolean insertarUsuario(String nombre, LocalDate fechaNacimiento, String clave, String correo, int rol){

        setDb();

        if(!verificarEdad(fechaNacimiento)){
            return false;
        }

        if(!existeMod(rol)){
            return false;
        }

        if(rol == 3 && capacidadMod()){
            return false;
        }

        Usuario tmpUsuario = new Usuario(nombre, fechaNacimiento, clave, correo, rol);
        db.registrarUsuario(tmpUsuario);

        return true;
    }

    //METODO PARA VERIFICAR EDAD DE USUARIO
    /**
     * Verifica si un usuario cumple con la edad mínima requerida.
     *
     * @param fechaNacimiento fecha de nacimiento del usuario
     * @return true si el usuario es mayor o igual a 18 años
     */
    public boolean verificarEdad(LocalDate fechaNacimiento){

        LocalDate hoy = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, hoy);

        if (edad.getYears() >= 18){ //Si el usuario tiene una edad mayor o igual a 18, retorna verdadero y continua el proceso de registro.
            return true;
        }
        return false;
    }

    //METODO PARA BUSCAR USUARIO
    /**
     * Verifica si un usuario existe en el sistema utilizando su correo.
     *
     * @param correo correo electrónico del usuario
     * @return true si el usuario existe
     */

    public boolean buscarUsuario(String correo){
        setDb(); //Verifico si hay base de datos activa
        return db.existeUsuarios(correo); //Retorna el resultado de buscar el correo del usuario
    }

    //METODO PARA OBTENER USUARIO POR CORREO
    /**
     * Obtiene un usuario a partir de su correo electrónico.
     *
     * @param correo correo del usuario
     * @return objeto Usuario correspondiente o null si no existe
     */
    public Usuario obtenerUsuario(String correo){
        setDb(); //Verifico si hay base de datos activa

        for(Usuario u : db.getUsuarios()){ //Recore usuarios registrados
            if(u.getCorreo().equals(correo)){
                return u; //Si se obtiene el correo del usuario, retorna ese valor
            }
        }
        return null; //No retorna nada si no existe el dato.
    }
    //------------------------------------------------------------------------------------------------------------------
    //METODOS MODERADOR
    //------------------------------------------------------------------------------------------------------------------

    //METODO PARA VALIDAR REGISTRO SEGUN MODERADOR
    /**
     * Valida si el registro de un usuario cumple con las reglas
     * relacionadas con el moderador del sistema.
     *
     * @param rol rol del usuario que intenta registrarse
     * @return true si el registro es permitido
     */
    public boolean existeMod(int rol){

        setDb(); //Verifica si hay base de datos activa

        boolean existeModerador = false;

        for(Usuario u : db.getUsuarios()){ //Recorre usuarios registrados
            if(u.getRol().equals("Moderador")){
                existeModerador = true;
                break;
            }
        }

        // Si NO hay moderador en el sistema
        if(!existeModerador){
            if(rol != 3){ // Si el usuario que intenta registrarse NO es moderador
                return false; // Bloquea registro
            }
        }

        // Si YA existe moderador
        if(existeModerador && rol == 3){
            return false; // Bloquea registrar otro moderador
        }

        return true; // Registro permitido
    }

    //METODO PARA VERIFICAR CAPACIDAD DE MODERADOR (SOLO PUEDE HABER UNO)
    /**
     * Determina si ya existe un moderador registrado en el sistema.
     *
     * @return true si ya hay un moderador
     */
    public boolean capacidadMod(){

        setDb(); //Verifico si no hay base de datos activa

        for(Usuario u : db.getUsuarios()){ //recorre los usuarios para ver si hay un moderador registrado
            if(u.getRol().equals("Moderador")){
                return true; //confirma que ya hay un moderador
            }
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    //METODOS SUBASTA
    //------------------------------------------------------------------------------------------------------------------

    //METODO QUE PERMITIR ACCESO AL USUARIO PARA REALIZAR SUBASTA O OFERTA SI NO ES MODERADOR
    /**
     * Verifica si el usuario puede participar en subastas u ofertas.
     *
     * @param correo correo del usuario
     * @return true si el usuario no es moderador
     */
    public Boolean obtenerRol(String correo){

        Boolean esModedaror = false;
        setDb(); //Verifica si hay base de datos activa

        for (Usuario u: db.getUsuarios()){ //Recorre los usuarios registrados
            if (u.getCorreo().equals(correo)){ //Si el correo ingresado existe registrado en el sistema

                if (!u.getRol().equals("Moderador")){
                    esModedaror = true; //Retorna falso, porque moderador no puede hacer subastas ni ofertas
                }
            }
        }
        return esModedaror;
    }

    //METODO PARA CONFIRMAR ROL; SI ES VENDEDOR O COLECCIONISTA
    /**
     * Determina si el usuario posee rol de vendedor.
     *
     * @param correo correo del usuario
     * @return true si es vendedor
     */
    public Boolean confirmarRol(String correo){

        setDb(); //Verifica si hay base de datos activa

        for (Usuario u: db.getUsuarios()){ //Recorre los usuarios registrados
            if (u.getCorreo().equals(correo)){ //Si el correo ingresado existe en el sistema
                if (u.getRol().equals("Vendedor")){ //Si el usuario de ese correo es vendedor
                    return true; //Confirma que es vendedor
                }
                return false; //Es coleccionista
            }
        }
        return false;
    }

    //METODO PARA LISTAR SUBASTA
    /**
     * Obtiene una lista de subastas registradas en el sistema.
     *
     * @return lista de subastas en formato texto
     */
    public ArrayList<String> listarSubastas(){
        setDb(); //Verifica si hay base de datos activa

        ArrayList<String> lista = new ArrayList<>();

        if (db.getSubastas().size() > 0) {
            for (Subasta s : db.getSubastas()){
                lista.add(s.toString());
            }
        }
        else {
            lista.add("No existen subastas registradas");
        }
        return lista;
    }

    //METODO PARA INSERTAR SUBASTA
    /**
     * Registra una nueva subasta en el sistema.
     *
     * @param fechaVencimiento fecha de cierre de la subasta
     * @param correoCreador correo del usuario creador
     * @param precioMinimo precio mínimo para participar
     */
    public void insertarSubasta(LocalDateTime fechaVencimiento, String correoCreador, double precioMinimo){

        setDb();

        Usuario creador = obtenerUsuario(correoCreador);

        if(creador != null){

            ArrayList<Objeto> objetos = db.getObjetosSubastados();

            if (objetos.isEmpty()){
                return;
            }
            Subasta tmpSubasta = new Subasta(fechaVencimiento, creador, precioMinimo, objetos, 1, creador.getPuntuacion());

            db.registrarSubasta(tmpSubasta);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //METODOS COLECCION
    //------------------------------------------------------------------------------------------------------------------

    //METODO PARA VALIDAR SI COLECCION EXISTE

    /**
     * Verifica si un usuario posee objetos en su colección.
     *
     * @param correo correo del usuario
     * @return true si posee objetos
     */
    public boolean existeColeccion(String correo){

        Usuario u = obtenerUsuario(correo); //Toma el valor del correo encontrado en el sistema

        if(u != null){ //Si hay dato asignado en u
            if(u.getColeccion() != null && u.getColeccion().size() > 0){
                return true; //Si la coleccion no esta vacia, retorna verdadero.
            }
        }
        return false; //La coleccion esta vacia.
    }

    //METODO PARA LISTAR COLECCION DE USUARIO
    /**
     * Imprime en consola los objetos de la colección de un usuario.
     *
     * @param correo correo del usuario
     */
    public void imprimirColeccionUsuario(String correo){

        Usuario u = obtenerUsuario(correo); //Toma el valor del correo encontrado en el sistema

        if(u != null && u.getColeccion()!=null){ //Si la coleccion no esta vacia

            for(Objeto o : u.getColeccion()){ //Recorre todos los objetos de la coleccion del usuario
                System.out.println(o.toString());
            }
        }
    }

    //METODO PARA OBTENER OBJETO DE COLECCION
    /**
     * Permite seleccionar un objeto de la colección del usuario
     * para agregarlo a una subasta.
     *
     * @param correo correo del usuario
     * @param indice posición del objeto en la colección
     */
    public void seleccionarObjetoColeccion(String correo, int indice){

        Usuario u = obtenerUsuario(correo); //Toma el valor del correo encontrado en el sistema

        if(u != null && u.getColeccion()!=null){ //Si la coleccion no esta vacia

            if(indice >=0 && indice < u.getColeccion().size()){ //Si indice es mayor a 0 y menor a la cantidad de objetos

                Objeto obj = u.getColeccion().get(indice); //obj toma la opcion seleccionada

                db.registrarObjetos(obj); //Envia el objeto a Data
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //METODOS OBJETOS
    //------------------------------------------------------------------------------------------------------------------

    //METODO PARA INSERTAR OBBJETO
    /**
     * Registra un objeto que será utilizado dentro de una subasta.
     *
     * @param nombre nombre del objeto
     * @param descripcion descripción
     * @param codigo código identificador
     * @param estado estado del objeto
     * @param fechaCompra fecha de compra
     * @param antiguedad fecha utilizada para calcular antigüedad
     */
    public void insertarObjeto(String nombre, String descripcion, String codigo, int estado, LocalDate fechaCompra, LocalDate antiguedad){
        setDb(); //Verifica si hay base de datos activa
        Objeto tmpObjeto = new Objeto(nombre, descripcion, codigo, estado,fechaCompra,antiguedad);
        db.registrarObjetos(tmpObjeto);
    }

    //METODO PARA VALIDAR SI OBJETO EXISTE
    /**
     * Verifica si un objeto se encuentra registrado en alguna subasta.
     *
     * @param codigo código del objeto
     * @return true si el objeto existe
     */
    public boolean existeObjeto(String codigo){

        setDb(); //Verifica si hay base de datos activa

        for(Subasta s : db.getSubastas()){ //Recorre las subastas registradas
            for(Objeto o : s.getObjetos()){ //Recore los objetos registrados
                if(o.getCodigo().equals(codigo)){ //Si el codigo coincide con el objeto registrado
                    return true; //Si existe
                }
            }
        }
        return false; //No existe
    }

    //------------------------------------------------------------------------------------------------------------------
    //METODOS OFERTA
    //------------------------------------------------------------------------------------------------------------------

    //METODO PARA VALIDAR SI EL OFERENTE ES COLECCIONISTA
    /**
     * Determina si un usuario posee el rol de coleccionista.
     *
     * @param correo correo del usuario
     * @return true si es coleccionista
     */
    public boolean esColeccionista(String correo){

        Usuario u = obtenerUsuario(correo); //Obtiene el correo ingresado por el usuario

        if(u != null){ //Si no esta vacio
            if(u.getRol().equals("Coleccionista")){ //Si el usuario del correo es coleccionista
                return true; //Confirma que es coleccionista
            }
        }
        return false; //Confirma que NO es coleccionista
    }

    //METODO PARA VERIFICAR QUE EL CREADOR NO OFERTE EN SU SUBASTA
    /**
     * Verifica si un usuario es el creador de una subasta específica.
     *
     * @param codigo código del objeto subastado
     * @param correo correo del usuario
     * @return true si es el creador de la subasta
     */
    public boolean esCreadorSubasta(String codigo,String correo){

        setDb(); //Verifica que hay base de datos activa

        for(Subasta s : db.getSubastas()){ //Recorre subastas registradas
            for(Objeto o : s.getObjetos()){ //Recorre objetos registrados

                if(o.getCodigo().equals(codigo)){ //Si el codigo del objeto coincide con el codigo ingresado

                    if(s.getUsuarioCreador().getCorreo().equals(correo)){ //Si el correo es del coleccionista
                        return true; //Si es coleccionista
                    }
                }
            }
        }
        return false; //NO es coleccionista
    }

    //METODO PARA REGISTRAR OFERTA
    /**
     * Registra una nueva oferta en el sistema.
     *
     * @param correo correo del oferente
     * @param codigo código del objeto subastado
     * @param precio precio ofertado
     */

    public void insertarOferta(String correo, String codigo, double precio){

        setDb(); //Verifica si hay base de datos activa

        Usuario tmpOferente = obtenerUsuario(correo); //Obtiene el correo del usuario para asignarlo

        Oferta nuevaOferta = new Oferta(tmpOferente,precio);

        db.registrarOferta(nuevaOferta,codigo); //Se envia a Data
    }

    //METODO PARA LISTAR OFERTAS
    /**
     * Obtiene la lista de ofertas registradas.
     *
     * @return lista de ofertas en formato texto
     */

    public ArrayList<String> listarOfertas(){
        setDb(); //Verifica si hay base de datos activa

        ArrayList<String> lista = new ArrayList<>();

        if (db.getOfertas().size() > 0) {
            for (Oferta o : db.getOfertas()){
                lista.add(o.toString());
            }
        }
        else {
            lista.add("No existen ofertas registradas");
        }
        return lista;
    }

    //------------------------------------------------------------------------------------------------------------------
    //METODOS ORDEN
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Procesa la finalización de una subasta y determina el resultado.
     *
     * Este método identifica la mejor oferta, aplica penalizaciones
     * o recompensas a los usuarios involucrados y asigna los objetos
     * al ganador si ambas partes cumplen con el proceso de entrega
     * y pago.
     *
     * @param id identificador de la subasta
     * @param vendedorEntrega indica si el vendedor entregó el objeto
     * @param ganadorPaga indica si el ganador realizó el pago
     * @return true si la finalización fue procesada correctamente
     */
    public Boolean procesarFinalizacionSubasta(int id, boolean vendedorEntrega, boolean ganadorPaga){

        setDb(); //Verifica que haya base de datos activa

        for(Subasta s : db.getSubastas()){ //Recorre subastas registradas

            if(s.getId() == id){ //Revisa si el ID de la subasta coincide con la subasta

                Oferta mejorOferta = null;

                for(Oferta o : db.getOfertas()){
                    if(mejorOferta == null || o.getPrecioOfertado() > mejorOferta.getPrecioOfertado()){ //Si hay una oferta mayor
                        mejorOferta = o; //mejorOferta toma ese valor
                    }
                }

                if(mejorOferta == null){
                    return false; //No hay ofertas
                }

                Usuario vendedor = s.getUsuarioCreador(); //El creador de la subasta
                Usuario ganador = mejorOferta.getOferente(); //El oferente con la mayor oferta

                // Penalizaciones (Si el vendedor no entrega objetos o el coleccionista no paga, pierden puntos)
                if(!vendedorEntrega){
                    vendedor.setPuntuacion(vendedor.getPuntuacion() - 1);
                }

                if(!ganadorPaga){
                    ganador.setPuntuacion(ganador.getPuntuacion() - 1);
                }

                // Si ambos cumplen
                if(vendedorEntrega && ganadorPaga){

                    vendedor.setPuntuacion(vendedor.getPuntuacion() + 1);
                    ganador.setPuntuacion(ganador.getPuntuacion() + 1);

                    // agregar objetos al coleccionista
                    if(ganador.getColeccion() == null){ //Si no tiene coleccion (o esta vacia)
                        ganador.setColeccion(new ArrayList<>()); //Crea un ArrayList para guardar el objeto
                    }
                    ganador.getColeccion().addAll(s.getObjetos()); //Guarda los objetos
                }
                return true; //Se logro finalizar la subasta
            }
        }
        return false;
    }
}


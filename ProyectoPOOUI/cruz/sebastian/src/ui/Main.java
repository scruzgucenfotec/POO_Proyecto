package ui;
import java.io.*;
import bl.Entities.*;
import java.util.ArrayList;
import bl.Logic.Service;
import dl.Data;
import java.time.LocalDate;
import java.time.*;

/**
 * Clase principal del sistema de subastas.
 * Permite interactuar con el usuario mediante un menú
 * para registrar usuarios, crear subastas y registrar ofertas.
 */
public class Main {

    static PrintStream out = System.out;
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Service myService = new Service();

    //MAIN
    /**
     * Método principal que inicia el programa y mantiene
     * el menú activo de manera continua.
     */
    public static void main(String[] args) throws IOException {
        while (true) {
            mostrarMenu();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //RUTINAS MENU
    //------------------------------------------------------------------------------------------------------------------

    //RUTINA PARA MOSTRAR MENU
    /**
     * Muestra el menú principal del sistema
     * y solicita una opción al usuario.
     */
    public static void mostrarMenu() throws IOException {

        int opcion = -1;

        do {
            out.println("----------MENU----------");
            out.println("1. Registro de usuarios");
            out.println("2. Listado de usuarios");
            out.println("3. Creación de subastas");
            out.println("4. Listado de subastas");
            out.println("5. Creación de ofertas");
            out.println("6. Listado de ofertas");
            out.println("0. Cerrar Sesion");
            out.println("------------------------");
            out.println("Digite la opción: ");

            opcion = Integer.parseInt(in.readLine());
            procesarOpcion(opcion);

        } while (opcion != 0);
    }

    //RUTINA PARA PROCESAR OPCION
    /**
     * Ejecuta la acción correspondiente según la opción elegida.
     * @param opcion número de opción seleccionada
     */
    public static void procesarOpcion(int opcion) throws IOException {
        switch (opcion) {
            case 1:
                registrarUsuario();
                break;
            case 2:
                listarUsuario();
                break;
            case 3:
                registrarSubasta();
                break;
            case 4:
                listarSubasta();
                break;
            case 5:
                registrarOferta();
                break;
            case 6:
                listarOfertas();
                break;
            case 0:
                out.println("Fin del programa...");
                System.exit(opcion);
            default:
                out.println("Por favor, ingrese una opcion valida");
                break;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    //RUTINAS USUARIO
    //------------------------------------------------------------------------------------------------------------------

    //RUTINA PARA REGISTRAR USUARIO
    /**
     * Permite registrar un nuevo usuario en el sistema
     * solicitando los datos necesarios desde la consola.
     */
    public static void registrarUsuario() throws IOException {

        out.println("Ingrese su nombre: ");
        String nombre = in.readLine();

        out.println("Ingrese su fecha de nacimiento (yyyy-mm-dd): ");
        LocalDate fechaNacimiento = LocalDate.parse(in.readLine());

        out.println("Ingrese contraseña: ");
        String clave = in.readLine();

        out.println("Ingrese correo: ");
        String correo = in.readLine();

        out.println("1. Coleccionista\n2. Vendedor\n3. Moderador\nIngrese un rol para su usuario");
        int rol = Integer.parseInt(in.readLine());

        boolean resultado = myService.insertarUsuario(nombre, fechaNacimiento, clave, correo, rol);

        if(resultado){
            out.println("Usuario registrado correctamente.");
        }else{
            out.println("No se pudo registrar el usuario. Es muy probable que ya existe un moderador. Si no es asi, asegura de que todos los usuarios sean mayores de 17.");
        }
    }


    //RUTINA PARA LISTAR USUARIO
    /**
     * Muestra la lista de usuarios registrados.
     */

    public static void listarUsuario() {

        out.println("--LISTA DE USUARIOS--");
        for (String dato : myService.listarUsuarios()) {
            out.println(dato.toString());
        }
        out.println("");
    }

    //------------------------------------------------------------------------------------------------------------------
    //RUTINAS SUBASTA
    //------------------------------------------------------------------------------------------------------------------

    //RUTINA PARA REGISTRAR SUBASTA
    /**
     * Permite crear una nueva subasta en el sistema.
     */
    public static void registrarSubasta() throws IOException {

        out.println("Ingrese su correo: ");
        String correoCreador = in.readLine();

        boolean verificacion = myService.buscarUsuario(correoCreador); //Recibe el resultado de Service
        if (!verificacion){ //Si la condicion es falsa (Si no se encuentra el usuario ingresado).
            out.println("El usuario no esta registrado en el sistema.");
            return;
        }

        boolean validarRol = myService.obtenerRol(correoCreador); //Recibe el resultado de Service
        if (!validarRol){ //Si la condicion es falsa (Si es moderador).
            out.println("El moderador no puede registar una subasta.");
            return;
        }

        //Si el usuario es coleccionista o vendedor, procede a registrar los datos

        boolean confirmarRol = myService.confirmarRol(correoCreador); //Recibe resultado de Service

        //Se ingresa le fecha de la siguiente manera:
        // (año-mes-diaThora:minuto) -> T significa tiempo o hora. -> fecha T hora
        out.println("Ingrese la fecha de vencimiento de la siguiente manera(yyyy-mm-ddThh:mm): ");
        LocalDateTime fechaVencimiento = LocalDateTime.parse(in.readLine());

        out.println("Ingrese el precio minimo para participar ($00.00): ");
        double precioMinimo = Double.parseDouble(in.readLine());

        if (confirmarRol){ //Si es vendedor, llama rutina para registrar objeto a sistema.
            registrarObjetos();
        }
        else{ //Si es coleccionista, verifica que tenga una coleccion para seleccionar un objeto.
            if (!seleccionarObjetoColeccion(correoCreador)){
                return;
            }
        }

        //Envia los datos a Service
        myService.insertarSubasta(fechaVencimiento, correoCreador, precioMinimo);
    }

    //RUTINA PARA REGISTRAR OBJETO A SUBASTA COMO VENDEDOR
    /**
     * Permite registrar objetos que serán incluidos en una subasta.
     */
    public static void registrarObjetos() throws IOException{

        while (true){ // El ciclo asegura que el vendedor pueda ingresar mas de un objeto a la subasta.
            out.println("Ingrese el nombre del objeto: ");
            String nombre = in.readLine();
            out.println("Ingrese una descripcion sobre el objeto: ");
            String descripcion = in.readLine();
            out.println("Ingrese un codigo para el objeto: ");
            String codigo = in.readLine();
            out.println("Ingrese el estado del objeto (1. Nuevo | 2. Usado | 3. Antiguo sin abrir): ");
            int estado = Integer.parseInt(in.readLine());
            out.println("Ingrese la fecha de compra del objeto (yyyy-mm-dd): ");
            LocalDate fechaCompra = LocalDate.parse(in.readLine());
            out.println("Ingrese la antiguedad del objeto (yyyy-mm-dd): ");
            LocalDate antiguedad = LocalDate.parse(in.readLine());

            //Envia los datos a Service.
            myService.insertarObjeto(nombre, descripcion, codigo, estado, fechaCompra, antiguedad);

            out.println("Su objeto ha sido registrado a la subasta. Desea agregar otro objeto (escriba Si o No)?");

            String opcion = in.readLine();

            //REcibe resultado de service
            boolean confirmacion = myService.confirmacion(opcion);

            if (!confirmacion){ //Si el usuario dice que No, se registra la subasta y se imprime el mensaje
                out.println("La subasta ha sido registrada.");
                return;
            }
        }
    }

    //RUTINA PARA QUE EL COLECCIONISTA SELECCIONE UN OBJETO DE SU COLECCION
    /**
     * Permite a un coleccionista seleccionar un objeto
     * de su colección para subastarlo.
     */
    public static Boolean seleccionarObjetoColeccion(String correo) throws IOException{

        boolean existe = false;
        existe = myService.existeColeccion(correo); //Recibe el resultado de Service.

        if(!existe){ //Si el resultado es false (Si no hay objetos en la coleccion);
            out.println("El coleccionista no posee objetos en su coleccion.");
            return existe; //Retorna falso y vuelve al menu.
        } else {
            existe = true; //(Existen objetos en la coleccion)
            out.println("Seleccione el objeto para subastarlo:");

            //Llama a Service para imprimir coleccion
            myService.imprimirColeccionUsuario(correo);

            int opcion = Integer.parseInt(in.readLine());

            //Envia a Service el resultado de seleccionar opcion.
            myService.seleccionarObjetoColeccion(correo, opcion);
        }
        return existe;
    }

    //RUTINA PARA LISTAR SUBASTAS
    /**
     * Muestra todas las subastas registradas en el sistema.
     */
    public static void listarSubasta() {

        out.println("--LISTA DE SUBASTAS--");
        for (String dato : myService.listarSubastas()) {
            out.println(dato.toString());
        }
        out.println("");
    }

    //------------------------------------------------------------------------------------------------------------------
    //RUTINAS OFERTA
    //------------------------------------------------------------------------------------------------------------------

    //RUTINA PARA REGISTRAR OFERTA
    /**
     * Permite a un coleccionista registrar una oferta
     * sobre un objeto dentro de una subasta.
     */
    public static void registrarOferta() throws IOException {

        listarSubasta(); //Lista las subastas con los objetos disponibles

        out.println("Digite el codigo del objeto a ofertar: ");
        String codigo = in.readLine();

        boolean existeObjeto = myService.existeObjeto(codigo); //Recibe el resultado de Service; verifica si existe el codigo.

        if(!existeObjeto){ //Si no existe el codigo, no se puede ofertar el objeto.
            out.println("El objeto no esta siendo subastado.");
            return;
        }

        out.println("Ingrese su correo: ");
        String correo = in.readLine();

        boolean existeUsuario = myService.buscarUsuario(correo); //Recibe el resultado de Service; verifica si existe el correo.

        if(!existeUsuario){ //Si el resultado es false (Si no existe el correo)
            out.println("El usuario no esta registrado.");
            return;
        }

        boolean esColeccionista = myService.esColeccionista(correo); //Recibe el resultado de Service; verifica si es coleccionista.

        if(!esColeccionista){ //Si el usuario NO es coleccionista
            out.println("Solo los coleccionistas pueden ofertar.");
            return;
        }

        boolean esCreador = myService.esCreadorSubasta(codigo,correo); //Recibe el resultado de Service; verifica si es el creador de la subasta.

        if(esCreador){ //Si el resultado es true (Si es el mismo creador)
            out.println("El creador de la subasta no puede ofertar en su propia subasta.");
            return;
        }

        out.println("Ingrese el precio ofertado ($00.00): ");
        double precio = Double.parseDouble(in.readLine());

        myService.insertarOferta(correo,codigo,precio); //Envia los datos a Service.

        out.println("Oferta registrada correctamente.");

        out.println("Vendedor, desea finalizar la subasta (Si / No)? "); //Aqui cambia al rol de vendedor
        String opcion = in.readLine();

        if (myService.confirmacion(opcion)){ //Confirma si el usuario ingreso si o no.
            finalizarSubasta(); //Si ingresa "Si", procede a los ultimos pasos para pasar el objeto al ganador.
        }
    }

    //RUTINA PARA LISTAR OFERTAS
    /**
     * Muestra todas las ofertas registradas.
     */
    public static void listarOfertas() {

        out.println("--LISTA DE OFERTAS--");
        for (String dato : myService.listarOfertas()) {
            out.println(dato.toString());
        }
        out.println("");
    }

    //RUTINA PARA FINALIZAR SUBASTA
    /**
     * Permite finalizar una subasta y procesar
     * el resultado entre vendedor y ganador.
     */
    public static void finalizarSubasta() throws IOException {

        out.println("Digite el ID de la subasta finalizada (presione enter despues de digitar el ID):");
        int id = Integer.parseInt(in.readLine());

        out.println("El vendedor entregó el objeto? (Si/No)");
        String vendedor = in.readLine().trim(); //Si el usuario ingresa espacios, trim se encarga de eliminarlos, enviando solo un si o no

        out.println("El ganador pagó el objeto? (Si/No)");
        String ganador = in.readLine().trim();

        boolean vendedorEntrega = myService.confirmacion(vendedor);
        boolean ganadorPaga = myService.confirmacion(ganador);

        boolean resultado = myService.procesarFinalizacionSubasta(id, vendedorEntrega, ganadorPaga);

        if(resultado){
            out.println("Proceso de subasta finalizado.");
        } else {
            out.println("No se pudo finalizar la subasta. Verifique el ID.");
        }
    }
}
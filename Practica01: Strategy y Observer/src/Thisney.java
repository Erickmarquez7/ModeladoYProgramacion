import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Thisney implements Sujeto{
    private ArrayList<Cliente> clientes;
    private String[] catalogo = {"Senisienta", "Los Avengadores", "Ratontuille",
    "101 perritos", "Kruella", "Emncamntados", "Mountros inc", 
    "Mountros inc: Sufriendo en el trabajo", "El rey tigre"};

    //Atributo auxiliar para ir guardando los meses
    private HashMap<Cliente, Integer> historial;

    //Atributo que nos dice de qué manera va a cobrar
    private CobroThisney tipoCobro;
    
    /**
     * Método constructor, incializa la lista de clientes y el historial
     */
    public Thisney(){
        this.clientes = new ArrayList<Cliente>();
        this.historial = new HashMap<Cliente, Integer>();
    }


    /**
     * Cambia la manera en como va a cobrar.
     * @param tipoCobro la nueva manera en que va a cobrar
     */
    public void setCobro(CobroThisney tipoCobro){
        this.tipoCobro = tipoCobro;
    }


    /**
     * Agrega un cliente al servicio junto con el tipo de servicio
     * @param cliente el cliente a agregar
     * @param tipo el tipo de servicio
     */
    public void agregar(Cliente cliente, int tipo){
        //verificamos si el cliente ya ha sido agregado antes
        if(!contiene(cliente)) {
            clientes.add(cliente);
        } 
 
        /*agregamos al historial de cliente, este sirve para cuando
        hayan anulado su suscripcion y quieran volver.
        No es necesario verificar si ya está o no, ya que de estarlo solo lo sobreescribe*/
        historial.put(cliente, tipo);
        System.out.println("Bienvenido, " + cliente.getNombre() + " disfruta la magia de Thisney!");
    }
    
    
    /**
     * Verifica si el cliente esta en la lista de cliente
     * @return true si el cliente a buscar coincide con el cliente c de la lista
     * @return false en otro caso
     */
    private boolean contiene(Cliente cliente){    
        for(Cliente c: clientes){
            if(c == cliente){
            return true;
            }
        }
        return false;

    }


    /**
     * Elimina al cliente del servicio pero NO del historial, solo lo sobreescribe 
     * a sin servicio, tipo = 0
     * @param cliente el cliente a eliminar
     */
    public void eliminar(Cliente cliente){
        int i = clientes.indexOf(cliente);
        if(i>0){
            clientes.remove(i);
            System.out.println("No tendras otra opcion que regresar a Thisney cuando compremos todo en el mundo!");
        }
    }

    /** 
     * Cobra a todos los clientes por servicio
     * que implementa al sujeto
     * @param cliente la lista de clientes a cobrar
     */
    public void cobrar(){    
        System.out.println("\u001B[34m"+"Servicio de cobro de Thisney");
        
        Iterator<Cliente> it = clientes.iterator();
        while(it.hasNext()){
            Cliente cliente = it.next();
            Integer tipo= historial.get(cliente);
            //podemos asegurar que tipo nunca será igual a 0, ya que toma los clientes
            //de la lista de clientes
            if (tipo <= 3)
            setCobro(new ThisneyTresMeses());
            if (tipo >= 4)
            setCobro(new ThisneyCuatroMeses());
            //Manda a llamar le método cobro
            boolean sigue = tipoCobro.cobrar(cliente);
            //verificamos que siga teniendo suficiente dinero para seguir pagando
            if (!sigue){
                //el iterador se encarga de eliminar el cliente
                it.remove();
                
            }
            Integer nuevoMes = tipo+1;
            historial.put(cliente, nuevoMes);

        }
        System.out.println("Se ha cobrado el servicio de Thisney a nuestros usuarios");
        System.out.println(recomendaciones()+"\u001B[0m");
    }

    /**
     * Recomienda lo que el usuario puede ver del catalogo de Thisney
     * @return las recomendaciones
     */
    public String recomendaciones(){
        Random r = new Random();
        String recomendaciones = "Thisney dice que veas: ";
        for (int i =0; i<=2; i++){
            int indiceAzar = r.nextInt(catalogo.length);
            String elementoAzar = catalogo[indiceAzar];
            recomendaciones += elementoAzar +", ";
        }

        return recomendaciones;
    }
    
}

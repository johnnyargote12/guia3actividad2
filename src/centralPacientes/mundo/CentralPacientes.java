/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogot� - Colombia)
 * Departamento de Tecnolog�as de la Informaci�n y Comunicaciones
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Central de Pacientes.
 * Adaptado de CUPI2 (Uniandes)
 * Fecha: Febrero 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package centralPacientes.mundo;

import java.util.ArrayList;
import java.util.Iterator;






/**
 * Esta clase representa una central en la que se maneja una lista de pacientes
 */
public class CentralPacientes {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Primer paciente de la lista
     */
    private Paciente primero;

    /**
     * N�mero de pacientes en la central
     */
    private int numPacientes;

    /**
     * Vector de cl�nicas manejadas por la central
     */
    private ArrayList<String> listaClinicas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea una nueva central sin pacientes y con una lista predefinida de cl�nicas
     */
    public CentralPacientes() {
        primero = null;
        numPacientes = 0;

        listaClinicas = new ArrayList<>();
        listaClinicas.add("Cl�nica del Country");
        listaClinicas.add("Cl�nica Palermo");
        listaClinicas.add("Cl�nica Reina Sof�a");
        listaClinicas.add("Cl�nica El Bosque");
        listaClinicas.add("Cl�nica San Ignacio");
        listaClinicas.add("Otra");
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el n�mero de pacientes de la cl�nica
     *
     * @return El n�mero de pacientes de la cl�nica
     */
    public int darNumeroPacientes() {
        return numPacientes;
    }

    /**
     * Adiciona un paciente al principio de la lista
     *
     * @param pac El paciente a ser agregado al comienzo de la lista. <br>
     *            pac!=null y no existe un paciente con c�digo igual a pac.codigo
     */
    public void agregarPacienteAlComienzo(Paciente pac) {
        if (primero == null) // Crea la cabeza si no existe
        {
            primero = pac;
        }
        else
        // Realiza la adici�n antes del paciente que est� al inicio de la lista
        {
            pac.cambiarSiguiente(primero);
            primero = pac;
        }
        numPacientes++;
    }

    /**
     * Adiciona un paciente al final de la lista. Si la lista est� vac�a el paciente queda de primero
     *
     * @param pac El paciente a ser agregado al final la lista. <br>
     *            pac!=null y no existe un paciente con c�digo igual a pac.codigo
     */
    public void agregarPacienteAlFinal(Paciente pac) {
        if (primero == null) // Si la cabeza no existe adiciona de primero el paciente
        {
            primero = pac;
        }
        else {
            // Busca el �ltimo paciente de la lista
            Paciente p = localizarUltimo();

            // Adiciona el paciente despu�s del �ltimo paciente de la lista
            p.insertarDespues(pac);
        }
        numPacientes++;
    }

    /**
     * Adiciona un paciente a la lista de pacientes antes del paciente con el c�digo especificado. <br>
     */
    public void agregarPacienteAntesDe(int cod, Paciente pac) throws NoExisteException {
        if (primero == null) {
            throw new NoExisteException(cod);
        }
        else if (cod == primero.darCodigo()) {
            pac.cambiarSiguiente(primero);
            primero = pac;
        }
        else {
            Paciente anterior = localizarAnterior(cod);
            if (anterior == null) {
                throw new NoExisteException(cod);
            }
            anterior.insertarDespues(pac);
        }
        numPacientes++;
    }

    /**
     * Adiciona un paciente a la lista de pacientes despu�s del paciente con el c�digo especificado.
     */
    public void agregarPacienteDespuesDe(int cod, Paciente pac) throws NoExisteException {
        Paciente anterior = localizar(cod);

        if (anterior == null) // Si no existe el paciente despu�s del que se desea realizar la adici�n se arroja la excepci�n
        {
            throw new NoExisteException(cod);
        }
        else
        // Se adiciona el paciente
        {
            anterior.insertarDespues(pac);
        }
        numPacientes++;
    }

    /**
     * Busca el paciente con el c�digo dado en la lista de pacientes.
     */
    public Paciente localizar(int codigo) {
        Paciente actual = primero;
        while (actual != null && actual.darCodigo() != codigo) {
            actual = actual.darSiguiente();
        }
        return actual;
    }

    /**
     * Busca el paciente anterior al paciente con el c�digo especificado
     */
    public Paciente localizarAnterior(int cod) {
        Paciente anterior = null;
        Paciente actual = primero;

        while (actual != null && actual.darCodigo() != cod) {
            anterior = actual;
            actual = actual.darSiguiente();
        }

        return actual != null ? anterior : null;
    }

    /**
     * Retorna el �ltimo paciente de la lista
     */
    public Paciente localizarUltimo() {
        Paciente actual = primero;

        if (actual != null) {
            while (actual.darSiguiente() != null) {
                actual = actual.darSiguiente();
            }
        }
        return actual;
    }
    
    public int conocerCantidadHombres() {
    	Paciente actual = primero;
    	int contadorHombre = 0;
    	
        if (actual != null) {
            while (actual.darSiguiente() != null) {
            	if(actual.darSexo() == 1) {
            		contadorHombre++;
            	}
                actual = actual.darSiguiente();
            }
        }       
        return contadorHombre;
    }


    private int conocerCantidadMujeres() {
        Paciente actual = primero;
        int contadorMujer = 0;

        if (actual != null) {
            while (actual.darSiguiente() != null) {
                if(actual.darSexo() == 2) {
                    contadorMujer++;
                }
                actual = actual.darSiguiente();
            }
        }
        return contadorMujer;
    }


    /**
     * Elimina el paciente con el c�digo especificado.
     */
    public void eliminarPaciente(int cod) throws NoExisteException {
        if (primero == null) {
            throw new NoExisteException(cod);
        }
        else if (cod == primero.darCodigo()) {
            // El paciente es el primero de la lista
            primero = primero.darSiguiente();
        }
        else {
            // El paciente es un elemento intermedio de la lista
            Paciente anterior = localizarAnterior(cod);
            if (anterior == null) {
                throw new NoExisteException(cod);
            }
            anterior.desconectarSiguiente();
        }
        numPacientes--;
    }

    /**
     * Devuelve una lista con los pacientes de la central
     */
    public ArrayList<Paciente> darPacientes() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        Paciente actual = primero;
        while (actual != null) {
            pacientes.add(actual);
            actual = actual.darSiguiente();
        }
        return pacientes;
    }

    /**
     * Retorna la lista de cl�nicas manejadas por la central
     */
    public ArrayList<String> darListaClinicas() {
        return listaClinicas;
    }

    /**
     * Retorna la longitud de la lista
     */
    private int darLongitud() {
        Paciente actual = primero;
        int longitud = 0;

        while (actual != null) {
            longitud++;
            actual = actual.darSiguiente();
        }
        return longitud;
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * Retorna la cantidad de hombres que hay en la lista
     * 
     */
    

    
  
    
    public int cantHombres() {

        int cantSexHombre = 1;

        if(conocerCantidadHombres() != 0) {
            cantSexHombre += conocerCantidadHombres() ;
            return cantSexHombre ;
        }else {
            return conocerCantidadHombres();
        }



    }

    /**
     * Retorna la cantidad de mujeres que hay en la lista
     */
    public int cantMujeres() {

        int cantSexMujer= 0;
        cantSexMujer += conocerCantidadMujeres() ;

        return cantSexMujer ;

    }

    /**
     * M�todo para la extensi�n3
     *
     * @return respuesta3
     */
    public String metodo3() {
        return "Respuesta 3";
    }

    /**
     * M�todo para la extensi�n4
     *
     * @return respuesta4
     */
    public String metodo4() {
        return "Respuesta 4";
    }

    /**
     * M�todo para la extensi�n2
     *
     * @return respuesta5
     */
    public String metodo5() {
        return "Respuesta 5";
    }
    
    public static void main(String[] args) {
		CentralPacientes central = new CentralPacientes();
		
		System.out.print(central.cantHombres());
	}
}

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

package centralPacientes.interfaz;

import centralPacientes.mundo.CentralPacientes;
import centralPacientes.mundo.NoExisteException;
import centralPacientes.mundo.Paciente;
import centralPacientes.mundo.YaExisteException;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Esta es la ventana principal de la aplicaci�n.
 */
public class InterfazCentralPacientes extends JFrame {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para adicionar antes
     */
    public static final int ANTES = 0;

    /**
     * Constante para adicionar despu�s
     */
    public static final int DESPUES = 1;

    /**
     * Constante para adicionar al comienzo
     */
    public static final int COMIENZO = 2;

    /**
     * Constante para adicionar al final
     */
    public static final int FINAL = 3;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo
     */
    private CentralPacientes central;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Panel con las extensiones
     */
    private PanelExtension panelExtension;

    /**
     * Panel con la lista de pacientes
     */
    private PanelListaPacientes panelLista;

    /**
     * Panel con la imagen decorativa
     */
    private PanelImagen panelImagen;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    
    
    /**
     * Constructor de la ventana. <br>
     */
    public InterfazCentralPacientes() {
        // Crea la clase principal
        central = new CentralPacientes();

        // Construye la forma
        setLayout(new GridBagLayout());
        setSize(370, 347);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Central de Pacientes");
        setResizable(false);

        // Paneles
        panelImagen = new PanelImagen();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelImagen, gbc);

        panelLista = new PanelListaPacientes(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelLista, gbc);

        panelExtension = new PanelExtension(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelExtension, gbc);

        setLocationRelativeTo(null);

        pack();
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Muestra el di�logo para agregar un paciente.
     * @param formaAdicion El punto en el que va ser adicionado el paciente (de primero, �ltimo, antes de otro paciente o despu�s de otro paciente). formaAdicion pertenece a
     *        {ANTES, DESPUES, COMIENZO, FINAL}
     * @param codigo El c�digo del paciente con relaci�n al cual se va a realizar la adici�n. Su valor s�lo es considerado si formaAdicion es ANTES o DESPUES
     */
    public void mostrarDialogoInsertarPaciente(int formaAdicion, int codigo) {
        DialogoInsertarPaciente dialogo = new DialogoInsertarPaciente(this, formaAdicion, codigo);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    /**
     * Si la lista de pacientes no est� vac�a muestra el di�logo para que el usuario seleccione la forma en la que desea realizar la adici�n del paciente. Si la lista de
     * pacientes est� vac�a se muestra el di�logo para crear un paciente
     *
     */
    public void mostrarDialogoOpcionesAgregarPaciente() {
        if (central.darNumeroPacientes() > 0) {
            DialogoOpcionesInsertar dialogo = new DialogoOpcionesInsertar(this);
            dialogo.setLocationRelativeTo(this);
            dialogo.setVisible(true);
        }
        else {
            mostrarDialogoInsertarPaciente(COMIENZO, -1);
        }
    }

    /**
     * Agrega un paciente a la lista de pacientes
     */
    public void agregarPaciente(int formaAdicion, int codPaciente, int elCodigo, String elNombre, String laClinica, String laHistoriaClinica, int elSexo) throws NoExisteException, YaExisteException {
        Paciente paciente = new Paciente(elCodigo, elNombre, laClinica, laHistoriaClinica, elSexo);

        // Se verifica que no exista un paciente con el c�digo del nuevo paciente
        if (central.localizar(elCodigo) != null) {
            throw new YaExisteException(elCodigo);
        }
        else {
            switch (formaAdicion) {
                case ANTES:
                    central.agregarPacienteAntesDe(codPaciente, paciente);
                    break;
                case DESPUES:
                    central.agregarPacienteDespuesDe(codPaciente, paciente);
                    break;
                case COMIENZO:
                    central.agregarPacienteAlComienzo(paciente);
                    break;
                case FINAL:
                    central.agregarPacienteAlFinal(paciente);
                    break;
            }

        }
    }

    /**
     * Retorna la lista de cl�nicas que maneja la central
     * @return La lista de cl�nicas que maneja la central
     */
    public ArrayList<String> darClinicasCentral() {
        return central.darListaClinicas();
    }

    /**
     * Actualiza la lista de pacientes
     *
     */
    public void refrescarListaPacientes() {
        panelLista.refrescarLista(central.darPacientes());
    }

    /**
     * Pide el c�digo del paciente que se desea buscar, si lo encuentra muestra su informaci�n en un dialogo. Si no se encuentra el paciente se muestra un mensaje indic�ndolo.
     */
    public void buscarPaciente() {
        String codigo = JOptionPane.showInputDialog(this, "C�digo:", "B�squeda Pacientes", JOptionPane.QUESTION_MESSAGE);
        try {
            if (codigo != null) {
                int cod = Integer.parseInt(codigo);

                Paciente paciente = central.localizar(cod);

                if (paciente != null) {
                    mostrarInformacionPaciente(paciente);
                }
                else {
                    JOptionPane.showMessageDialog(this, "El paciente con c�digo " + cod + " no se encuentra registrado", "B�squeda de Pacientes", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El c�digo del paciente debe ser un valor num�rico", "B�squeda de Pacientes", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pide la c�dula del paciente que se desea eliminar, si lo elimina lo indica. Si no se elimina el paciente se muestra un mensaje indic�ndolo.
     */
    public void eliminarPaciente() {
        String codigo = JOptionPane.showInputDialog(this, "C�digo:", "Eliminaci�n Pacientes", JOptionPane.QUESTION_MESSAGE);
        try {
            if (codigo != null) {
                int cod = Integer.parseInt(codigo);
                central.eliminarPaciente(cod);
                refrescarListaPacientes();
                JOptionPane.showMessageDialog(this, "El paciente fue eliminado", "Eliminaci�n Pacientes", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El c�digo del paciente debe ser un valor num�rico", "Eliminaci�n Pacientes", JOptionPane.ERROR_MESSAGE);
        }
        catch (NoExisteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Eliminaci�n Pacientes", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra en un di�logo la informaci�n del paciente dado
     * @param paciente El paciente al que se le va a mostrar la informaci�n. paciente!=null.
     */
    public void mostrarInformacionPaciente(Paciente paciente) {
        DialogoMostrarInformarcionPaciente dialogo = new DialogoMostrarInformarcionPaciente(this, paciente);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1
     */
    public void reqFuncOpcion1() {
        int resultado = central.cantHombres();
        JOptionPane.showMessageDialog(this, "La cantidad de hombres en la lista es: " + resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * M�todo para la extensi�n 2
     */
    public void reqFuncOpcion2() {
        int resultado = central.cantMujeres();
        JOptionPane.showMessageDialog(this,"La cantidad de mujeres en la lista es: " + resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * M�todo para la extensi�n 3
     */
    public void reqFuncOpcion3() {
        System.exit(0);
    }

    /**
     * M�todo para la extensi�n 4
     */
    public void reqFuncOpcion4() {
        String resultado = central.metodo4();
        JOptionPane.showMessageDialog(this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * M�todo para la extensi�n 5
     */
    public void reqFuncOpcion5() {
        String resultado = central.metodo5();
        JOptionPane.showMessageDialog(this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }

    // -----------------------------------------------------------------
    // Programa principal
    // -----------------------------------------------------------------

    /**
     * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz
     * @param args Son los argumentos de la aplicaci�n. No se requiere ninguno.
     */
    public static void main(String[] args) {
        InterfazCentralPacientes interfaz = new InterfazCentralPacientes();
        interfaz.setVisible(true);
    }
}
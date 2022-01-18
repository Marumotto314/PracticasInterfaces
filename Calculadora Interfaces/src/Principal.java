import static java.awt.Font.PLAIN;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Principal extends JFrame {

    class Ventana extends JFrame {
        public Ventana() {
            super("Ejemplo 1");
            this.getContentPane().setLayout(new GridBagLayout());
        }

    }

    // Display para mostrar los n�meros
    JLabel display;
    JFrame ventanaError;
    // Cantidad de botones de calculadora
    int numBotones = 19;
    // Array de botones para n�meros y operaciones
    JButton botones[] = new JButton[numBotones];
    
    // Array de strings para las etiquetas de los botones
    String textoBotones[] = { "Resultado", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", ".",
            "+", "Help", "Manual" };
    // Array de posiciones en X de cada bot�n
    int xBotones[] = { 15, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210 , 15, 145};
    // Array de posiciones en Y de cada bot�n
    int yBotones[] = { 90, 155, 155, 155, 155, 220, 220, 220, 220, 285, 285, 285, 285, 350, 350, 350, 350, 415, 415 };
    // Array de �ndices del array de botones que corresponden a n�meros (en el �rden
    // en el que se pintar�n)
    int numerosBotones[] = { 14, 9, 10, 11, 5, 6, 7, 1, 2, 3 };
    // Array de �ndices del array de botones que corresponden a operaciones (en el
    // �rden en el que se pintar�n)
    int[] operacionesBotones = { 16, 12, 8, 4};
    // Alto y ancho de cada bot�n
    int anchoBoton = 50;
    int altoBoton = 50;
    // Para indicar que he terminado de escribir d�gitos un n�mero y que voy a
    // a�adir el siguiente
    boolean nuevoNumero = true;
    // Para indicar si ya he utilizado el punto decimal en ese n�mero (solo puede
    // haber uno)
    boolean puntoDecimal = false;
    // Para almacenas los resultados parciales y totales de las operaciones
    // realizadas
    double operando1 = 0;
    double operando2 = 0;
    double resultado = 0;
    // Para almacenar el string de la operaci�n realizada (+, -, *, /)
    String operacion = "";

    public Principal() {

        initDisplay(); // Display de la calculadora
        initBotones(); // Botones de la calculadora
        initPantalla(); // Opciones del JFrame
        eventosNumeros(); // Eventos asociados a los botones de n�meros de la calculadora
        eventoDecimal(); // Eventos asociados al bot�n decimal "." de la calculadora
        eventosOperaciones(); // Eventos asociados a los botones de operaciones (+,-,*,/, Help, Manual) de la calculadora
        eventoHelp();// Evento asociado a el boton Help
        eventoResultado(); // Eventos asociados al bot�n resultado de la calculadora
        eventoLimpiar(); // Eventos asociados al bot�n de limpiar "C" de la calculadora

    }

    private void eventoHelp()
    {
    	/*
    	 * El bot�n Help est� contenido en botones[17]
    	 */
		try	{
			// Carga el fichero de ayuda
			File fichero = new File("./help/help_set.hs");
			URL hsURL = fichero.toURI().toURL();
			
			// Crea el HelpSet
			HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
			
			HelpBroker hb = helpset.createHelpBroker();
			
			// Ayuda al hacer click en el JMenuItem itemAyuda.
			hb.enableHelpOnButton(botones[17], "aplicacion", helpset);
			 
			// Ayuda al pulsar F1 sobre la ventana principal
			hb.enableHelpKey(getContentPane(), "aplicacion", helpset);
		} catch (Exception e){
			e.printStackTrace();
		}
    }
    
    private void initDisplay() {

        display = new JLabel("0"); // Inicio JLabel
        display.setBounds(15, 15, 245, 60); // Posici�n y dimensiones
        display.setOpaque(true); // Para poder darle un color de fondo
        display.setBackground(Color.BLACK); // Color de fondo
        display.setForeground(Color.GREEN); // Color de fuente
        display.setBorder(new LineBorder(Color.DARK_GRAY)); // Borde
        display.setFont(new Font("MONOSPACED", PLAIN, 24)); // Fuente
        display.setHorizontalAlignment(SwingConstants.RIGHT); // Alineamiento horizontal derecha
        add(display); // A�ado el JLabel al JFrame
    }

    private void initBotones() {//

        for (int i = 0; i < numBotones; i++) {
            botones[i] = new JButton(textoBotones[i]); // Inicializo JButton
            int size = (i == 0) ? 24 : 16; // EL bot�n de Resultado tendr� un tama�o de fuente menor que todos los dem�s
//            int ancho = (i == 0) ? 245 : anchoBoton; // EL bot�n de Resultado ser� m�s ancho que todos los dem�s
            /*
             * Modificamos la l�nea superior para que nos cambie el tama�o de los botones Resultado, Help y Manual
             */
            int ancho;
            if(i == 0)
            	ancho = 245;
            else if(i == 18 || i == 17)
            	ancho = 115;
            else
            	ancho = anchoBoton;
            botones[i].setBounds(xBotones[i], yBotones[i], ancho, altoBoton); // Posici�n y dimensiones
            botones[i].setFont(new Font("MONOSPACED", PLAIN, size)); // Fuente
            botones[i].setOpaque(true); // Para poder darle un color de fondo
            botones[i].setFocusPainted(false); // Para que no salga una recuadro azul cuando tenga el foco
            botones[i].setBackground(Color.DARK_GRAY); // Color de fondo
            botones[i].setForeground(Color.WHITE); // Color de fuente
            botones[i].setBorder(new LineBorder(Color.DARK_GRAY)); // Borde
            add(botones[i]); // A�ado el JButton al JFrame
        }
    }

    private void initPantalla() {//cambiar el layout

        setLayout(null); // Layout absoluto
        setTitle("Calculadora"); // T�tulo del JFrame
        setSize(290, 515); // Dimensiones del JFrame
        setResizable(false); // No redimensionable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar proceso al cerrar ventana
        getContentPane().setBackground(Color.BLACK); // Color de fondo
        setVisible(true); // Mostrar JFrame
    }

    private void eventosNumeros() {

        for (int i = 0; i < 10; i++) {
            int numBoton = numerosBotones[i];
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Si es un nuevo n�mero y no es 0, sustituyo el valor del display
                    if (nuevoNumero) {
//                        if (!textoBotones[numBoton].equals("0")) 
//                        {
                            display.setText(textoBotones[numBoton]);
                            nuevoNumero = false; // Ya no es un nuevo n�mero
//                        }
                    }
                    // Si no, lo a�ado a los d�gitos que ya hubiera
                    else {
                        display.setText(display.getText() + textoBotones[numBoton]);
                    }
                }
            });
        }

    }

    private void eventoDecimal() {
        botones[15].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si todav�a no he a�adido el punto decimal al n�mero actual
                if (!puntoDecimal) {
                    display.setText(display.getText() + textoBotones[15]);
                    puntoDecimal = true; // Ya no puedo a�adir el punto decimal en este n�mero
                    nuevoNumero = false; // Por si empiezo el n�mero con punto decimal (por ejemplo, .537)
                }
            }
        });

    }

    private void eventosOperaciones() {
        for (int numBoton : operacionesBotones) { // Es la versi�n optimizada de for (int i = 0; i <
                                                  // operacionesBotones.length; i++){
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Si no ten�a ninguna operaci�n pendiente de realizar
                    if (operacion.equals("")) {
                        // Asocio la operaci�n del bot�n a la variable
                        operacion = textoBotones[numBoton];
                        // Asigno a operando2 el valor del display (como double)
                        operando2 = Double.parseDouble(display.getText());
                        // Reseteo para poder introducir otro n�mero y otro decimal
                        nuevoNumero = true;
                        puntoDecimal = false;
                        // Si ten�a alguna pendiente, calculo el resultado de la anterior y luego me
                        // guardo la actual
                    } else {
                        operando2 = resultado(); // Se almacena en operando2 para poder encadenar operaciones
                                                 // posteriores
                        operacion = textoBotones[numBoton];
                    }
                    // SOUT para comprobar que estoy guardando los valores adecuados

                }
            });
        }

    }

    /* A�ADIR COMO NUEVOS M�TODOS */

    // Calcula el resultado en funci�n de la operaci�n seleccionada y lo devuelve
    // formateado en el display
    private double resultado() {

        // recojo el valor del display
        operando1 = Double.parseDouble(display.getText());

        // Selecciono y realizo operaci�n
        switch (operacion) {

            case "+":
                resultado = operando2 + operando1;
                break;
            case "-":
                resultado = operando2 - operando1;
                break;
            case "*":
                resultado = operando2 * operando1;
                break;
            case "/":
            	if(operando1 == 0)
            		JOptionPane.showMessageDialog(ventanaError, "Indeterminaci�n", "Divisi�n entre cero", JOptionPane.ERROR_MESSAGE);
            	else
            		resultado = operando2 / operando1;
                break;

        }
        // Formateo y muestro en el display
        Locale localeActual = Locale.GERMAN;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(localeActual);
        simbolos.setDecimalSeparator('.');
        DecimalFormat formatoResultado = new DecimalFormat("#.######", simbolos);
        display.setText(String.valueOf(formatoResultado.format(resultado)));

        // Limpio variables para poder continuar
        limpiar();

        // Devuelvo el valor del resultado
        return resultado;

    }
    
    // Resetea los valores de la calculadora para poder continuar haciendo
    // operaciones
    private void limpiar() {

        operando1 = operando2 = 0;
        operacion = "";
        nuevoNumero = true;
        puntoDecimal = false;

    }

    private void eventoResultado() {
        botones[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pulsar el bot�n de resultado, directamente lo calculo y reseteo la
                // calculadora,
                // sin necesidad de almacenar el resultado para futuras operaciones
                resultado();

            }
        });

    }

    private void eventoLimpiar() {
        botones[13].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pulsar el bot�n de limpiar, se resetean el display y las variables de la
                // calculadora,
                display.setText("0");
                limpiar();
            }
        });

    }

    public static void main(String[] args) {
        new Principal();
    }

}
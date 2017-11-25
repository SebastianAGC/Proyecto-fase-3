
import java.text.DecimalFormat;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;
/*
 * Universdidad del Valle de Guatemala
 * Diseño de lenguajes de programacion
 * Compiladores
 *
 * @author Sebastian Galindo, Carnet: 15452
 */
public class MainLabProyecto{

    public static void main(String[] args) {
        Operaciones operacion = new Operaciones();
        RegExConverter sC = new RegExConverter();
        CocolStructure structure = new CocolStructure();
        String regexp="";
        String regexpPF;
        String cadenaExtendida;
        String regexPFestendida;

/* ***********************************Construccion del automata de ident************************************************/

        //Creando la cadena que se ingresara para crear el automata de ident
        regexp = structure.getIdent();

        //Creando cadena Extendida para la generación directa de AFD's y convirtiendola a formato Postfix
        regexpPF = sC.infixToPostfix(regexp);
        cadenaExtendida="("+regexp+")#";
        regexPFestendida=sC.infixToPostfix(cadenaExtendida);

        //Obteniedno la hoja final del arbol sintactico
        Hoja n = operacion.generarArbolSintactico(regexPFestendida);

        //Creando el automata
        AutomataDFA ident = new AutomataDFA();
        ArrayList<String> alfabetoIdent = operacion.generateAlphabet(regexpPF);
        operacion.construccionDirecta(ident, n, alfabetoIdent);
        operacion.nombrarNodos(ident);
        operacion.estadoFinal(ident);

/* ***********************************Construccion del automata de Set***********************************************/
/*
        //Creando la cadena que se ingresara para crear el automata de number
        regexp = structure.getSet();


        //Creando cadena Extendida para la generación directa de AFD's y convirtiendola a formato Postfix
        regexpPF = sC.infixToPostfix(regexp);

        cadenaExtendida="("+regexp+")#";
        regexPFestendida=sC.infixToPostfix(cadenaExtendida);
        //Obteniedno la hoja final del arbol sintactico
        n = operacion.generarArbolSintactico(regexPFestendida);

        //Creando el automata
        AutomataDFA set = new AutomataDFA();
        ArrayList<String> alfabetoSet = operacion.generateAlphabet(regexpPF);
        operacion.construccionDirecta(set, n, alfabetoSet);
        operacion.nombrarNodos(set);
        operacion.estadoFinal(set);


/* ****************************************Construccion del automata de String******************************************/

        //Creando la cadena que se ingresara para crear el automata de number
        regexp = structure.getString();

        //Creando cadena Extendida para la generación directa de AFD's y convirtiendola a formato Postfix
        regexpPF = sC.infixToPostfix(regexp);
        cadenaExtendida="("+regexp+")#";
        regexPFestendida=sC.infixToPostfix(cadenaExtendida);

        //Obteniedno la hoja final del arbol sintactico
        n = operacion.generarArbolSintactico(regexPFestendida);

        //Creando el automata
        AutomataDFA string = new AutomataDFA();
        ArrayList<String> alfabetoString = operacion.generateAlphabet(regexpPF);
        operacion.construccionDirecta(string, n, alfabetoString);
        operacion.nombrarNodos(string);
        operacion.estadoFinal(string);

/* ****************************************Construccion del automata de charV******************************************/

        //Creando la cadena que se ingresara para crear el automata de charV
        regexp = structure.getCharr();

        //Creando cadena Extendida para la generación directa de AFD's y convirtiendola a formato Postfix
        regexpPF = sC.infixToPostfix(regexp);
        cadenaExtendida="("+regexp+")#";
        regexPFestendida=sC.infixToPostfix(cadenaExtendida);

        //Obteniedno la hoja final del arbol sintactico
        n = operacion.generarArbolSintactico(regexPFestendida);

        //Creando el automata
        AutomataDFA charV = new AutomataDFA();
        ArrayList<String> alfabetoCharV = operacion.generateAlphabet(regexpPF);
        operacion.construccionDirecta(charV, n, alfabetoCharV);
        operacion.nombrarNodos(charV);
        operacion.estadoFinal(charV);

/* ****************************************Construccion del automata de number******************************************/

        //Creando la cadena que se ingresara para crear el automata de charV
        regexp = structure.getNumber();

        //Creando cadena Extendida para la generación directa de AFD's y convirtiendola a formato Postfix
        regexpPF = sC.infixToPostfix(regexp);
        cadenaExtendida="("+regexp+")#";
        regexPFestendida=sC.infixToPostfix(cadenaExtendida);

        //Obteniedno la hoja final del arbol sintactico
        n = operacion.generarArbolSintactico(regexPFestendida);

        //Creando el automata
        AutomataDFA number = new AutomataDFA();
        ArrayList<String> alfabetoNumber = operacion.generateAlphabet(regexpPF);
        operacion.construccionDirecta(number, n, alfabetoNumber);
        operacion.nombrarNodos(number);
        operacion.estadoFinal(number);
/* ******************************************LEYENDO EL ARCHIVO TXT*****************************************************/
/*
        String path = System.getProperty("user.dir") + "\\cocol.txt";
        ArrayList<String> fileContent = operacion.fileReader(path);

/* ******************************************RECORRIENDO EL ARCHIVO*****************************************************/
/*

        operacion.Errors(operacion.recorrido(fileContent, ident, set,string));
        //operacion.Errors(operacion.recorrido(fileContent, ident, number, string, charr));

/* ******************************************Ingresando el archivo**************************************************************/
        //Solicitando al usuario que ingrese la expresion regular
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo con la especificación léxica en Cocol: ");
        String nombreDelArchivo=scanner.nextLine();

        String path1 = System.getProperty("user.dir") + "/"+nombreDelArchivo+"";
        System.out.println(path1);
        ArrayList<String> fileContent2 = operacion.fileReader(path1);
        ErrorType error = operacion.recorrido(fileContent2, ident, string, charV, number, structure);
        int errorNumber = error.getErrorNumber();
        int errorLine = error.getErrorLine();

        operacion.Errors(errorNumber, errorLine);
        if(errorNumber==0){
            operacion.lexerGenerator(structure);

        }

/* *******************************************************************************************************/

        System.out.println("Ahora se estan creando el arreglo de terminales y no terminales.");
        ParserOperations parserOperations = new ParserOperations();
        parserOperations.setTerminalsAndNonterminals(structure.getProductionsContent(), structure.getTokensContent());
        System.out.println("Estos son los terminales: "+parserOperations.getTerminals());
        System.out.println("Estos son los no terminales: "+parserOperations.getNonterminals());

        Scanner sc = new Scanner(System.in);
        Parser theParser = new Parser();
        System.out.println("1. First\n2. Follow\nIngrese la opcion que desea ejecutar: ");
        String input = sc.nextLine();
        if(input.equals("1")){
            System.out.println("Ingrese una cadena de simbolos: ");
            input = sc.nextLine();
            theParser.firstCalculator(parserOperations.getTerminals(), parserOperations.getNonterminals(), structure.getProductionsContent(), input);
        }else{
            if(input.equals("2")){
                System.out.println("Ingrese una simbolo no terminal: ");
                input = sc.nextLine();
                theParser.follow(input, parserOperations.getNonterminals(), parserOperations.getTerminals(), structure.getProductionsContent());
            }
        }
        //String cadena = theParser.correrPunto(". a");
        //System.out.println(cadena);
        ArrayList<Productions> newProductions = theParser.fixProductions(structure.getProductionsContent());
        structure.setProductionsContent(newProductions);
        AutomataLR0 nier = theParser.creacionAutomata(structure.getProductionsContent());
        theParser.nombrarEstados(nier);
        System.out.println("Se ha creado un automata.");

        BufferedWriter bw = null;
        FileWriter fw = null;


        try {

            PrintWriter writer = new PrintWriter("Descripcion Automata LR(0).txt");
            writer.println(theParser.descipcion(nier));
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        //theParser.creadorTablaParseo(nier, parserOperations.getTerminals(), parserOperations.getNonterminals(), structure.getProductionsContent());

    }
}

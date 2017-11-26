import com.sun.javafx.binding.StringFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Parser {

    private ArrayList<String> symbols = new ArrayList<>();
    private ArrayList<String> followSymbols = new ArrayList<>();

    public ArrayList<String> firstCalculator(ArrayList<String> terminals, ArrayList<String> nonterminals, ArrayList<Productions> productions,  String input){

        //Separando el input que realiza el usuario.
        String[] inputs = input.split(" ");
        String s = inputs[0];
        s = s.replace("'", "");
        if(terminals.contains(s)){
            if(!symbols.contains(s)){
                symbols.add(s);
            }
        }else{
            if(nonterminals.contains(s)){
                first("", s, productions, terminals, nonterminals);
            }else{
                System.out.println("No existe el simbolo '" + s + "' en la gramatica.");
            }
        }
        System.out.println("Resultado: " + symbols);
        return symbols;
    }

    public void first(String headAnterior, String headNueva, ArrayList<Productions> productions, ArrayList<String> terminals, ArrayList<String> nonterminals){

        if(!headAnterior.equals(headNueva)){
            //Revisando entre todas las producciones, la produccion que contenga como cabeza el input de first.
            for (Productions p:productions) {
                //Verificando que el input sea igual a la cabeza de la produccion
                String cabeza = p.getHead();
                cabeza = cabeza.replace(" ", "");
                if(cabeza.equals(headNueva)){
                    String body = p.getBody(); //Si la cabeza coincide obtiene el cuerpo de esa produccion.
                    if(body.endsWith(".")){
                        body = body.substring(0, body.length()-1);
                    }
                    body = body.replace("'", "");
                    if(body.contains("|")){ //Si el cuerpo de la produccion contiene | lo separa en dos partes
                        String[] bodyParts = body.split("\\|");
                        String part1 = bodyParts[0];
                        part1 = part1.substring(1);
                        String part2 = bodyParts[1];
                        part2 = part2.substring(1);

                        //Separando cada una de las partes en los distintos simbolos
                        String[] parts1 = part1.split(" ");
                        String[] parts2 = part2.split(" ");

                        //Calculando el first de la primera parte del or
                        if(terminals.contains(parts1[0])){
                            if(!symbols.contains(parts1[0])){
                                symbols.add(parts1[0]);
                            }
                        }else{
                            if(nonterminals.contains(parts1[0])){
                                //Calcular el first de ese simbolo
                                first(headNueva, parts1[0], productions, terminals, nonterminals);
                            }
                        }

                        //Calculando el first de la segunda parte del or
                        if(terminals.contains(parts2[0])){
                            if(!symbols.contains(parts2[0])){
                                symbols.add(parts2[0]);
                            }
                        }else{
                            if(nonterminals.contains(parts2[0])){
                                //Calcular el first de ese simbolo
                                first(headNueva, parts2[0], productions, terminals, nonterminals);
                            }
                        }

                    }else{
                        //Haciendo esta parte si no posee un or el cuerpo de la produccion
                        String[] parts = body.split(" ");

                        //Calculando el first de la segunda parte del or
                        if(terminals.contains(parts[0])){
                            if(!symbols.contains(parts[0])){
                                symbols.add(parts[0]);
                            }
                        }else{
                            if(nonterminals.contains(parts[0])){
                                //Calcular el first de ese simbolo
                                first(headNueva, parts[0], productions, terminals, nonterminals);
                            }
                        }
                    }
                }
            }
        }
    }

    public void follow(String input, ArrayList<String> nonterminals, ArrayList<String> terminals, ArrayList<Productions> productions){
        input = input.replace(" ", "");
        if(nonterminals.contains(input)){
            String c = productions.get(0).getHead();
            c = c.replace(" ", "");
            if(input.equals(c)){
                if(!followSymbols.contains("@")){
                    followSymbols.add("@");
                }
            }
                for (Productions p:productions) {

                    //Obteniendo el cuerpo de cada produccion para verificar si contienen el input
                    String body = p.getBody();
                    body = bodyC(body);
                    if(body.contains("|")){
                        String[] bodyParts = body.split("\\|");
                        for (String s:bodyParts) {
                            s = bodyC(s);
                            s = s.replace("'", "");
                            String[] parts = s.split(" ");
                            for(int i=0; i<parts.length; i++){
                                String cadena = parts[i];
                                if (cadena.equals(input)) {
                                    if(i==parts.length-1){
                                        //Si es el ultimo elemento de la produccion calcula el follow de la cabeza
                                        follow(p.getHead(), nonterminals, terminals, productions);
                                    }else{
                                        //Cacula el first del siguiente elemento del cuerpo
                                        ArrayList<String> resultado = firstCalculator(terminals, nonterminals, productions, parts[i+1]);
                                        for (String r:resultado) {
                                            if(!followSymbols.contains(r)){
                                                followSymbols.add(r);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }else{
                        //Hacer aqui si el cuerpo de la produccion no tiene or "|".
                        String[] parts = body.split(" ");
                        for(int i=0; i<parts.length; i++){
                            String cadena = parts[i];
                            if (cadena.equals(input)) {
                                if(i==parts.length-1){
                                    //Si es el ultimo elemento de la produccion calcula el follow de la cabeza
                                    follow(p.getHead(), nonterminals, terminals, productions);
                                }else{
                                    //Cacula el first del siguiente elemento del cuerpo
                                    ArrayList<String> resultado = firstCalculator(terminals, nonterminals, productions, parts[i+1]);
                                    for (String r:resultado) {
                                        if(!followSymbols.contains(r)){
                                            followSymbols.add(r);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            System.out.println("Resultado: " + followSymbols);
        }else{
            System.out.println("El símbolo ingresado no es un símbolo no-terminal.");
        }
    }

    public EstadoLR0 Cloure(EstadoLR0 estado, ArrayList<Productions> productions){
        //Obteniendo el conjunto de producciones que se encuentran en el estado.
        Set<Productions> set = estado.getProductionsSet();
        Stack<Productions> stack = new Stack<>();
        stack.addAll(set);
        //Ciclo que recorre todas las producciones  del estado
        boolean siEsta = false;

        while(!stack.isEmpty()){
            Productions p = stack.pop();
            String body =  p.getBody(); //Obtiene el cuerpo de la produccion para verificar el punto
            String[] parts = body.split(" ");
            for (int i=0; i<parts.length;i++){
                if(parts[i].equals(".")){ //verifica si es el punto
                    if(i!=parts.length-1){ //mientras que no sea el ultimo elemento de la produccion
                        for (Productions prod :productions) {
                            String cabeza = prod.getHead();
                            cabeza = cabeza.replace(" ", "");
                            if(parts[i+1].equals(cabeza)){
                                String cuerpo = prod.getBody();
                                cuerpo = "."+ cuerpo;
                                Productions newP = new Productions(cabeza, cuerpo);
                                for (Productions laPro:set) {
                                    if(laPro.getBody().equals(cuerpo)){
                                        siEsta=true;
                                    }
                                }
                                if(!siEsta){
                                    set.add(newP);
                                    stack.add(newP);
                                }
                            }
                            siEsta = false;
                        }
                    }
                }
            }
        }
        estado.setProductionsSet(set);
        //Devuelve el estado actualizado
        return estado;
    }

    public EstadoLR0 Goto(EstadoLR0 estado, String transicion, ArrayList<Productions> productions){
        Set<Productions> newSet = new HashSet<>();
        Set<Productions> set = estado.getProductionsSet();
        EstadoLR0 elEstado = null;


        for (Productions p:set) {
            String body = p.getBody();
            String[] parts = body.split(" ");
            for (int i=0; i< parts.length;i++) {
                String s = parts[i];
                if(s.equals(".")){
                    if(i!=parts.length-1){
                        if(parts[i+1].equals(transicion)){
                            Productions prod = new Productions(p.getHead(), correrPunto(body));
                            newSet.add(prod);
                            //return Cloure(elEstado, productions);
                        }
                    }
                }
            }
        }
        elEstado = new EstadoLR0(newSet);
        return Cloure(elEstado, productions);
    }

    public String correrPunto(String body){
        String newBody = "";
        String s;
        String[] parts = body.split(" ");
        for(int i=0; i<parts.length;i++){
            s = parts[i];
            if(s.equals(".")){
                for(int x=0; x<i;x++){
                    newBody+= parts[x] +" ";
                }
                newBody += parts[i+1] +" . ";
                for(int x=i+2; x<parts.length;x++){
                    newBody+= parts[x] + " ";
                }
            }
        }
        return newBody;
    }

    public AutomataLR0 creacionAutomata(ArrayList<Productions> productions){
        System.out.println("Ha iniciado la creacion del automata");
        AutomataLR0 nier = new AutomataLR0();
        Set<EstadoLR0> T = new HashSet<>();
        Set<DtranLR0> E = new HashSet<>();

        //Creando el estado inicial del automata
        Set<Productions> setInit = new HashSet<>();
        //Creando la produccion inicial extendida
        String h = "S'";
        String b = ". "+ productions.get(0).getHead() + " @";
        Productions initialProduction = new Productions(h,b);
        setInit.add(initialProduction);
        EstadoLR0 init = new EstadoLR0(setInit);
        //Obteniendo el closure del estado inicial
        init = Cloure(init, productions);

        //Inicializando el conjunto de estados
        T.add(init);
        Stack<EstadoLR0> stack = new Stack<>();
        stack.addAll(T);
        while(!stack.isEmpty()){
            EstadoLR0 I = stack.pop();
            for (Productions p:I.getProductionsSet()) {
                String body = p.getBody();
                String[] parts = body.split(" ");
                for (int i=0; i<parts.length;i++) {
                    String part = parts[i];
                    if(i!=parts.length-1){
                        if(parts[i].equals(".")){
                            if(!parts[i+1].equals("@")){
                                EstadoLR0 J = Goto(I, parts[i+1],productions);
                                EstadoLR0 res = yaExisteEstado(J, T);
                                if(res.equals(J)){
                                    T.add(J);
                                    stack.add(J);
                                }
                                DtranLR0 newTransicion = new DtranLR0(I, parts[i+1],res);
                                if(!yaExisteTrans(newTransicion, E)){
                                    E.add(newTransicion);
                                }
                            }
                        }
                    }
                }
            }
        }

        //Asignando al automata los estados y transiciones creadas
        nier.setEstados(T);
        nier.setTransiciones(E);
        return nier;
    }

    public ArrayList<Productions> fixProductions(ArrayList<Productions> productions){
        ArrayList<Productions> newProductions = new ArrayList<>();
        for (Productions p:productions) {
            String head = p.getHead();
            String body = p.getBody();
            //body = body.substring(0, body.length()-1);
            if(body.contains("|")){
                String[] parts = body.split("\\|");
                for (String s:parts) {
                    Productions newP = new Productions(head, s);
                    newProductions.add(newP);
                }
            }else{
                newProductions.add(p);
            }
        }
        return newProductions;
    }

    public boolean yaExisteTrans(DtranLR0 trans, Set<DtranLR0> E){
        boolean yaExiste = false;
        EstadoLR0 estadoOrigen = trans.getOrigen();
        EstadoLR0 estadoDestino = trans.getDestino();
        String t = trans.getTransicion();
        for (DtranLR0 transicion:E) {
            EstadoLR0 estadoOrigenT = transicion.getOrigen();
            EstadoLR0 estadoDestinoT = transicion.getDestino();
            String tT = transicion.getTransicion();
            if(estadoOrigenT.equals(estadoOrigen)){
                if(estadoDestinoT.equals(estadoDestino)){
                    if(tT.equals(t)){
                        yaExiste=true;
                    }
                }
            }
        }
        return yaExiste;
    }

    public EstadoLR0 yaExisteEstado(EstadoLR0 revisar, Set<EstadoLR0> T){
        EstadoLR0 e = null;
        boolean esDiferenteTodos = false;

        Set<Productions> setRevisar = revisar.getProductionsSet();

        for (EstadoLR0 state:T) {
            int contProduccionesIguales = 0;
            Set<Productions> setState = state.getProductionsSet();
            for (Productions pRevisar: setRevisar) {
                String head = pRevisar.getHead();
                String body = pRevisar.getBody();
                for (Productions pState: setState) {
                    String headState = pState.getHead();
                    String bodyState = pState.getBody();
                    if(head.equals(headState) && body.equals(bodyState)){
                        contProduccionesIguales++;
                    }
                }
            }
            if(contProduccionesIguales!=state.getProductionsSet().size()){
                esDiferenteTodos=true;
            }else{
                esDiferenteTodos=false;
                e = state;
                return e;
            }
        }

        //Si es verdadero que el estado a revisar es diferente a los existentes...
        if(esDiferenteTodos){
            return revisar; //regresa el mismo estado
        }else{
            return e; //de lo contrario, regresa el estado al que es igual.
        }
    }

    public void nombrarEstados(AutomataLR0 nier){
        int cont = 0;
        for (EstadoLR0 e:nier.getEstados()) {
            e.setNumeroEstadoDFA(cont);
            cont++;
        }
    }

    public String descipcion(AutomataLR0 a){
        String descripcion = "Estados: \n";
        for (EstadoLR0 e:a.getEstados()) {
            descripcion+= "Estado #"+ e.getNumeroEstadoDFA() + ": \nProducciones: \n";
            for (Productions p:e.getProductionsSet()) {
                descripcion+= p.toString() + "\n";
            }


        }
        descripcion += "\n\nTransiciones: \n";
        for (DtranLR0 d:a.getTransiciones()) {
            descripcion+= "Origen: " + d.getOrigen().getNumeroEstadoDFA() + ", " + d.getTransicion() + ", Destino: " + d.getDestino().getNumeroEstadoDFA() +"\n";
        }
        return descripcion;
    }

    public void parsingTable(AutomataLR0 nier, ArrayList<String> nonterminals, ArrayList<String> terminals, ArrayList<Productions> productions){
        Set<EstadoLR0> set = nier.getEstados();
        Set<DtranLR0> trans = nier.getTransiciones();
        int numeroProduccion = 0;

        //Creando un arraylist de todos los estados
        ArrayList<EstadoLR0> T = new ArrayList<>();
        T.addAll(set);

        //Creando un arraylist de todos los simbolos
        ArrayList<String> simbolos = new ArrayList<>();
        simbolos.addAll(nonterminals);
        simbolos.add("@");
        simbolos.addAll(terminals);
        int cantidad = simbolos.size();
        System.out.println(cantidad);
        String[][] tabla = new String[T.size()][cantidad];

        for(int i=0;i<T.size();i++){
            for(int j = 0; j<cantidad;j++){
                tabla[i][j]="";
            }
        }


        for (EstadoLR0 I: T) {
            for (DtranLR0 d:trans) {
                if(d.getOrigen().equals(I)){
                    String t = d.getTransicion();
                    t = t.replace("'", "");
                    if(terminals.contains(t)){
                        if(tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(t)].equals("")){
                            tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(t)] = "s " + d.getDestino().getNumeroEstadoDFA();
                        }else{
                            String posibleShiftReduce =tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(t)];
                            String[] parts =posibleShiftReduce.split(" ");
                            if(parts[0].equals("r")){
                                System.out.println("Existe un problema shift-reduce en el estado " + I.getNumeroEstadoDFA() + " con el simbolo " + d.getTransicion());
                            }
                        }
                    }
                    if(nonterminals.contains(t)){
                        tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(t)] = "g " + d.getDestino().getNumeroEstadoDFA();
                    }
                }
            }
            for (Productions p:I.getProductionsSet()) {
                String body = p.getBody();
                String[] parts = body.split(" ");
                if(parts[parts.length-1].equals("@")){
                    if(parts[parts.length-2].equals(".")){
                        tabla[I.getNumeroEstadoDFA()][simbolos.indexOf("@")]="accept";
                    }
                }else if(parts[parts.length-1].equals(".")){
                    //Obteniendo el numero de la produccion que se esta reduciendo.
                    body = body.substring(0, body.length()-2);
                    body = bodyC(body);
                    for (Productions pr:productions) {
                        String prBody = bodyC(pr.getBody());
                        if(prBody.equals(body)){
                            numeroProduccion = productions.indexOf(pr);
                        }
                    }
                    //calculando el follow de la cabeza de la producción
                    String cabeza = p.getHead();
                    symbols.clear();
                    followSymbols.clear();
                    follow(cabeza, nonterminals, terminals, productions);
                    for (String s:followSymbols) {
                        if(tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(s)].equals("")){
                            tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(s)]="r " + numeroProduccion;
                        }else{
                            String posibleShiftReduce =tabla[I.getNumeroEstadoDFA()][simbolos.indexOf(s)];
                            String[] partss =posibleShiftReduce.split(" ");
                            if(partss[0].equals("r")){
                                System.out.println("Existe un problema reduce-reduce en el estado " + I.getNumeroEstadoDFA() + " con el simbolo " + s);
                            }
                        }
                    }
                }
            }
        }

        String cadena = "";
        for(int j=0;j<cantidad;j++){
            cadena+=simbolos.get(j)+ "              ";
        }
        cadena+="\n";
        for(int i=0;i<T.size();i++){
            for(int j=0;j<cantidad;j++){
                cadena+=tabla[i][j]+ "          ";
            }
            cadena+="\n";
        }

        System.out.println("Se ha creado la tabla de parseo.\n" + cadena);
    }

    public String bodyC(String body){
        if(body.startsWith(" ")){
            body = body.substring(1);
        }
        if(body.endsWith(" ")){
            body = body.substring(0,body.length()-1);
        }
        return body;
    }

}

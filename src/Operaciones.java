
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
/*
 * Universdidad del Valle de Guatemala
 * Diseño de lenguajes de programacion
 * Compiladores
 *
 * @author Sebastian Galindo, Carnet: 15452
 */

public class Operaciones {
    
    public static ArrayList<Nodo> miArrayNodos = new ArrayList<>();
    private ArrayList<String> alfabeto = new ArrayList<>();

    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<String> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Automata concatenacion(Automata automataA, Automata automataB){

        //asignando los nodos del inicial de b al final de a
        ArrayList<Nodo> losNodos = automataB.getNodoInicial().getElNodo();
        automataA.getNodoFinal().setElNodo(losNodos);

        //Asiganando las transiciones del inicial al final
        ArrayList<String> trans = automataB.getNodoInicial().getTransiciones();
        automataA.getNodoFinal().setTransiciones(trans);

        Automata automataAB = new Automata(automataA.getNodoInicial(), automataB.getNodoFinal());

        return automataAB;
    }
    
    public Automata or(Automata a, Automata b){
         
        //Creando un nodo inicial que sera en comun entre los dos automatas.
        Nodo nuevoNodoInicial = new Nodo();
        
        /*Agregando a nuevoNodoInicial una nueva transicion, por medio de 
        epsilon "$", hacie el nodo inicial del automata a. */
        nuevoNodoInicial.agregar("$", a.getNodoInicial());
        
        /*Agregando a nuevoNodoInicial una nueva transicion, por medio de 
        epsilon "$", hacie el nodo inicial del automata b. */
        nuevoNodoInicial.agregar("$", b.getNodoInicial());
        
        //Creando un nodo final
        Nodo nuevoNodoFinal = new Nodo();
        
        /*Asignando la transicion desde los nodos finales de a,b 
        al nuevoNodoFinal*/
        a.getNodoFinal().agregar("$", nuevoNodoFinal);
        b.getNodoFinal().agregar("$", nuevoNodoFinal);
        
        //Creando el nuevo automataAorB
        Automata automataAorB = new Automata(nuevoNodoInicial, nuevoNodoFinal);
        
        return automataAorB;
    }
    
    public Automata kleene(Automata a){
        
        //Creando un nuevo nodo inicial
        Nodo nuevoNodoInicial = new Nodo();
        
        //Creando un nuevo nodo final
        Nodo nuevoNodoFinal = new Nodo();
        
        //Relacionando el nuevoNodoInicial con el automata a
        nuevoNodoInicial.agregar("$", a.getNodoInicial());
        
        //Relacionando el nuevoNodoFinal con el nodo final de a
        a.getNodoFinal().agregar("$", nuevoNodoFinal);
        
        //Relacionando el nodo final de a con el nodo incial de a.
        a.getNodoFinal().agregar("$", a.getNodoInicial());
        
        //Relacionando el nuevoNodoFinal con el nuevoNodoInicial
        nuevoNodoInicial.agregar("$", nuevoNodoFinal);
        
        Automata automataK = new Automata(nuevoNodoInicial, nuevoNodoFinal);
        return automataK;
    }

    public Automata kleenemas(Automata a){

        //Creando un nuevo nodo inicial
        Nodo nuevoNodoInicial = new Nodo();

        //Creando un nuevo nodo final
        Nodo nuevoNodoFinal = new Nodo();

        //Relacionando el nuevoNodoInicial con el automata a
        nuevoNodoInicial.agregar("$", a.getNodoInicial());

        //Relacionando el nuevoNodoFinal con el nodo final de a
        a.getNodoFinal().agregar("$", nuevoNodoFinal);

        //Relacionando el nodo final de a con el nodo incial de a.
        a.getNodoFinal().agregar("$", a.getNodoInicial());

        Automata automataK = new Automata(nuevoNodoInicial, nuevoNodoFinal);
        return automataK;
    }

    public String alfabeto(ArrayList<String> Alf){
        String alfabeto="{";
        for(int i=0; i<Alf.size();i++){
            alfabeto+=Alf.get(i) + ", ";
        }
        alfabeto+="}";
        return alfabeto;
    }

    public String transiciones(){
        String transiciones="";
        ArrayList transicionesN;
        Nodo nodo;
        for(int i=0; i<miArrayNodos.size();i++){
            //Obteniendo el nodo-i.
            nodo = miArrayNodos.get(i);
            //Obteniendo las transisiones desde el nodo-i a los nodos conectados.
            transicionesN = nodo.getTransiciones();
            for(int j=0; j<nodo.getElNodo().size();j++){
                transiciones+="(" + nodo.getNumeroEstado() + "-" + transicionesN.get(j) + "-" + nodo.getElNodo().get(j).getNumeroEstado() +"), ";
            }
        }
        return transiciones;
    }

    public void getArrayNodos(Nodo nodo){
        if(!miArrayNodos.contains(nodo)){
            miArrayNodos.add(nodo);
            ArrayList<Nodo> listadoDeNodos = nodo.getElNodo();
            for (Nodo nodoR: listadoDeNodos) {
                getArrayNodos(nodoR);
            }
        }

    }

    public void nombrarNodos(){
        for (int i = 0; i<miArrayNodos.size();i++) {
            miArrayNodos.get(i).setNumeroEstado(i);
        }

    }
    public String listadoDeNodos(){
        String cadena="{";
        for (int i = 0; i<miArrayNodos.size();i++) {
            cadena+=miArrayNodos.get(i).getNumeroEstado() + ", ";
        }
        cadena+="}";
        return cadena;
    }

    public void simulacionAFN(Automata automata, String cadena){
        Set<Nodo> setInicial = new HashSet<>();
        setInicial.add(automata.getNodoInicial());
        Set<Nodo> S = eClosure(setInicial);
        String c;
        int x = 0, y = 0;

        while(x < cadena.length()){
            c=String.valueOf(cadena.charAt(x));
            S=eClosure(move(S, c));
            x++;
        }
        for (Nodo n: S) {
            if(n.isEsFinal()){
                y=1;
            }
        }
        if(y==1){
            System.out.println("La cadena SI es aceptada.");
        }else{
            System.out.println("La cadena NO es aceptada.");
        }

    }


    public Automata crearAFN(String regexpPF, Stack<Automata> miStack){


        for (int x=0;x<regexpPF.length();x++){
            String caracter = String.valueOf(regexpPF.charAt(x));
            if(caracter.equals("*") || caracter.equals("|") || caracter.equals("?") || caracter.equals(".") || caracter.equals("+")){
                if(caracter.equals(".")){
                    //Hacer aqui la concatenacion
                    if(miStack.size()>=2){
                        Automata automataB=miStack.pop();
                        Automata automataA=miStack.pop();
                        Automata automataAB = concatenacion(automataA, automataB);
                        miStack.push(automataAB);
                    }else{
                        System.out.println("La cadena ingresada no es una regex.");
                        System.exit(0);
                    }
                }
                if(caracter.equals("|")){
                    //OR
                    if(miStack.size()>=2){
                        Automata automataB=miStack.pop();
                        Automata automataA=miStack.pop();
                        Automata automataAorB=or(automataA, automataB);
                        miStack.push(automataAorB);
                    }else{
                        System.out.println("La cadena ingresada no es una regex.");
                        System.exit(0);
                    }
                }
                if(caracter.equals("*")){
                    //Kleene
                    if(miStack.size()>=1) {
                        Automata automataA = miStack.pop();
                        Automata automataK = kleene(automataA);
                        miStack.push(automataK);
                    }else{
                        System.out.println("La cadena ingresada no es una regex.");
                        System.exit(0);
                    }
                }
                if(caracter.equals("?")){
                    //Abreviatura ?
                    if(miStack.size()>=1) {
                        Automata X = miStack.pop();
                        Automata e = new Automata("$");
                        Automata automataOrEpsilon = or(X, e);
                        miStack.push(automataOrEpsilon);
                    }else{
                        System.out.println("La cadena ingresada no es una regex.");
                        System.exit(0);
                    }

                }
                if(caracter.equals("+")){
                    //Cerradura Positiva
                    if(miStack.size()>=1) {
                        Automata a = miStack.pop();
                        Automata automataCerradura = kleenemas(a);
                        miStack.push(automataCerradura);
                    }else{
                        System.out.println("La cadena ingresada no es una regex.");
                        System.exit(0);
                    }
                }
            }else{
                //Ciclo if que verifica si el ArrayList del alfabeto ya contiene el caractér
                if(!alfabeto.contains(caracter) && !caracter.equals("$") && !caracter.equals("")){
                    alfabeto.add(caracter);
                }
                //Creando el automata básico
                Automata elAutomata = new Automata(caracter);
                miStack.push(elAutomata);
            }
        }

        Automata elAutomatota = miStack.pop();

        //Creando un ArrayList que contiene todos
        getArrayNodos(elAutomatota.getNodoInicial());

        //Nombrando a los nodos
        nombrarNodos();
        elAutomatota.getNodoFinal().setEsFinal(true);


        return elAutomatota;
    }
    /* *************************************OPERACIONES PARA AFD******************************************/

    public Set<Nodo> eClosure(Set<Nodo> T){
        Set<Nodo> eclosureT = new HashSet<>();
        Stack<Nodo> Stack = new Stack<>();


        Stack.addAll(T);
        eclosureT.addAll(T);
        while(!Stack.isEmpty()){
            Nodo t = Stack.pop();
            ArrayList<String> transicionesDet = t.getTransiciones();
            ArrayList<Nodo> nodosDet = t.getElNodo();
            for (int i=0;i<transicionesDet.size();i++) {
                String a = transicionesDet.get(i);
                if(a.equals("$")){
                    Nodo u = nodosDet.get(i);
                    if(!eclosureT.contains(u)){
                        eclosureT.add(u);
                        Stack.add(u);
                    }
                }
            }
        }
        return eclosureT;
    }

    public Set<Nodo> move(Set<Nodo> T, String a){
        Set<Nodo> moveTA = new HashSet<>();
        for (Nodo n: T) {
            ArrayList<Nodo> nodosDeN = n.getElNodo();
            ArrayList<String> transicionesDeN = n.getTransiciones();
            for(int i=0; i<transicionesDeN.size();i++){
                if(transicionesDeN.get(i).equals(a)){
                    moveTA.add(nodosDeN.get(i));
                }
            }
        }
        return moveTA;
    }

    public void subsetConstruction(Nodo nodoInicial, ArrayList<String> alfabeto, AutomataDFA afd){
        //Creando el conjunto inicial de Dstates
        Set<Nodo>  conjuntoS0 = new HashSet<>();
        conjuntoS0.add(nodoInicial);

        EstadoAFD miEstadoAFD = new EstadoAFD(eClosure(conjuntoS0));
        miEstadoAFD.setInicial(true);

        //añadiendo el conjunto inicial con el que empezará el conjunto de conjuntos Dstates
        afd.getDstatesAFD().add(miEstadoAFD);
        afd.setEstadoInicial(miEstadoAFD);

        //empezando el ciclo que verifica los conjuntos marcados en Dstates;
        while(isUnmarkedState(afd)){
            afd.getT().setMarcado(true);//Marcando el estado T como marcado
            EstadoAFD T = afd.getT();//Obteniendo el estado T
            for (String a: alfabeto) {   //Empezando a recorrer el alfabeto
                EstadoAFD U = new EstadoAFD(eClosure(move(T.getConjuntoEstados(), a)));
                if(!yaexisteDFA(afd, U)){
                    U.setMarcado(false);
                    afd.getDstatesAFD().add(U);
                    afd.setU(U);
                }
                //Creando la nueva transicion Dtran que contiene los datos de la transicion en el DFA
                Dtran nuevaTransicion = new Dtran(T, a, afd.getU());
                afd.getTransicionesAFD().add(nuevaTransicion);
            }
        }
    }

    public boolean isUnmarkedState(AutomataDFA automata){
        boolean hayDesmarcadoAun=false;
        for (EstadoAFD conjunto: automata.getDstatesAFD()){
            if (!conjunto.isMarcado()) {
                hayDesmarcadoAun = true;
                automata.setT(conjunto);
            }
        }
        return hayDesmarcadoAun;
    }

    /*
    * Método para nombrar todos los estados del arrayList de estados del AFD
     */
    public void nombrarNodosDFA(AutomataDFA automata){
        for(int i=0; i<automata.getDstatesAFD().size();i++){
            automata.getDstatesAFD().get(i).setNumeroEstadoDFA(i);
        }
        for (EstadoAFD e: automata.getDstatesAFD()) {
            for (Nodo n: e.getConjuntoEstados()) {
                if(n.isEsFinal()){
                    e.setFinal(true);
                }
            }
        }
    }

    /*
     * Metodo para obtener la descripcion del AFD convertido
     */
    public String descripcionAFD(AutomataDFA afd, ArrayList<String> alfabeto){
        String descripcion="";
        descripcion+="Lista de nodos: {";
        for(EstadoAFD c: afd.getDstatesAFD()){
            descripcion+=c.getNumeroEstadoDFA()+", ";
        }
        descripcion+="}\nAlfabeto: {";
        for (String a: alfabeto) {
            descripcion+=a+", ";
        }
        descripcion+="}\nEstado incial: " + afd.getEstadoInicial().getNumeroEstadoDFA() + "\nEstado final: ";
        for (EstadoAFD e: afd.getDstatesAFD()) {
            if(e.isFinal()){
                descripcion+=e.getNumeroEstadoDFA() +", ";
            }
        }

        descripcion+="\nTransiciones: ";
        for (Dtran d: afd.getTransicionesAFD()) {
            descripcion+="("+ d.getOrigen().getNumeroEstadoDFA()+"-"+d.getTransicion()+"-"+d.getDestino().getNumeroEstadoDFA()+"), ";
        }
        return descripcion;
    }


    public boolean yaexisteDFA(AutomataDFA automata, EstadoAFD U){
        boolean yaesta = false;
        for (EstadoAFD e: automata.getDstatesAFD()) {
            if(e.getConjuntoEstados().equals(U.getConjuntoEstados())){
                automata.setU(e);
                yaesta=true;
            }
        }

        return yaesta;
    }

    public EstadoAFD moveSimulacion(EstadoAFD estado, String a, AutomataDFA automata){
        EstadoAFD moveTA = null;
        ArrayList<Dtran> transiciones = automata.getTransicionesAFD();
        for (Dtran d: transiciones) {
            if(d.getOrigen().equals(estado)){
                if(d.getTransicion().equals(a)){
                    moveTA=d.getDestino();
                }
            }
        }
        return moveTA;
    }

    public void simulacionAFD(AutomataDFA automata, String cadena){
        EstadoAFD e = automata.getEstadoInicial();
        String c;
        int x = 0, y=0;

        while(x<cadena.length()){
            c=String.valueOf(cadena.charAt(x));
            e = moveSimulacion(e, c, automata);
            x++;
            if(e==null){
                y=0;
                break;
            }else{
                if(e.isFinal()){
                    y=1;
                }else{
                    y=0;
                }
            }

        }

        if(y==1){
            System.out.println("La cadena SI es aceptada.");
        }else{
            System.out.println("La cadena NO es aceptada.");
        }

    }

    /* *********************************GENERACION DE AFD DIRECTA********************************************************/

    public Hoja generarArbolSintactico(String regexExtendidaPF){
        int cont=1;
        Stack<Hoja> arbolSintactico = new Stack<>();
        for(int x = 0;x<regexExtendidaPF.length(); x++){
            String caracter = String.valueOf(regexExtendidaPF.charAt(x));
            if(caracter.equals("|") || caracter.equals(".")){
                Hoja hojaDerecha = arbolSintactico.pop();
                Hoja hojaIzquierda = arbolSintactico.pop();
                Hoja hojaOr = new Hoja(hojaIzquierda, hojaDerecha, caracter);

                //Calculando las operaciones nullable, fistpos, lastpos y followpos de la hoja creada
                hojaOr.setNullable(nullableComplejo(hojaOr));
                hojaOr.setFirstpos(firstposComplejo(hojaOr));
                hojaOr.setLastpos(lastposComplejo(hojaOr));
                followpos(hojaOr);
                arbolSintactico.push(hojaOr);

            }else if(caracter.equals("*")){
                Hoja hojaUnica = arbolSintactico.pop();
                Hoja hojaKleene = new Hoja(hojaUnica, caracter);

                //Calculando las operaciones nullable, fistpos, lastpos y followpos de la hoja creada
                hojaKleene.setNullable(nullableComplejo(hojaKleene));
                hojaKleene.setFirstpos(firstposComplejo(hojaKleene));
                hojaKleene.setLastpos(lastposComplejo(hojaKleene));
                followpos(hojaKleene);


                arbolSintactico.push(hojaKleene);
            }else{
                Hoja hojaBasica = new Hoja(caracter, cont);
                //Seteando el valor del nullable de la hoja básica
                hojaBasica.setNullable(nullableBasico(hojaBasica));

                //Obteniendo el first pos de la hoja básica
                hojaBasica.setFirstpos(firstposBasico(hojaBasica));

                //Obteniendo el last pos de la hoja basica
                hojaBasica.setLastpos(lastposBasico(hojaBasica));

                //Pusheando la hoja basica al stack del arbol
                arbolSintactico.push(hojaBasica);
                cont++;
            }
        }
        return arbolSintactico.pop();
    }

    public boolean nullableBasico(Hoja hojaBasica){
        boolean nullable;
        if(hojaBasica.getCaracter().equals("$")){
            nullable = true;
        }else{
            nullable = false;
        }
        return nullable;
    }

    public boolean nullableComplejo(Hoja hojaCompuesta){
        boolean nullable=false;
        String simbolo = hojaCompuesta.getSimbolo();
        Hoja c1 = hojaCompuesta.getHijoIzquierdo();
        Hoja c2 = hojaCompuesta.getHijoDerecho();
        if(simbolo.equals("|")){
            nullable = c1.isNullable() | c2.isNullable();
        }else if(simbolo.equals(".")){
            nullable = c1.isNullable() & c2.isNullable();
        }else if(simbolo.equals("*")){
            nullable = true;
        }
        return nullable;
    }

    public Set<Hoja> firstposBasico(Hoja hojaBasica){
        Set<Hoja> firstpos = new HashSet<>();
        if(hojaBasica.getCaracter().equals("$")){
            firstpos = firstpos;
        }else{
            firstpos.add(hojaBasica);
        }
        return firstpos;
    }

    public Set<Hoja> firstposComplejo(Hoja hojaCompuesta){
        Set<Hoja> firstpos = new HashSet<>();

        String simbolo = hojaCompuesta.getSimbolo();
        Hoja c1 = hojaCompuesta.getHijoIzquierdo();
        Hoja c2 = hojaCompuesta.getHijoDerecho();
        Hoja c = hojaCompuesta.getHojaUnica();

        if(simbolo.equals("|")){
            firstpos.addAll(c1.getFirstpos());
            firstpos.addAll(c2.getFirstpos());
        }else if(simbolo.equals(".")){
            if(c1.isNullable()){
                firstpos.addAll(c1.getFirstpos());
                firstpos.addAll(c2.getFirstpos());
            }else{
                firstpos.addAll(c1.getFirstpos());
            }
        }else if(simbolo.equals("*")){
            firstpos.addAll(c.getFirstpos());
        }
        return firstpos;
    }

    public Set<Hoja> lastposBasico(Hoja hojaBasica){
        Set<Hoja>lastpos = new HashSet<>();
        if(hojaBasica.getCaracter().equals("$")){
            lastpos = lastpos;
        }else{
            lastpos.add(hojaBasica);
        }
        return lastpos;
    }

    public Set<Hoja> lastposComplejo(Hoja hojaCompuesta){
        Set<Hoja> lastpos = new HashSet<>();

        String simbolo = hojaCompuesta.getSimbolo();
        Hoja c1 = hojaCompuesta.getHijoIzquierdo();
        Hoja c2 = hojaCompuesta.getHijoDerecho();
        Hoja c = hojaCompuesta.getHojaUnica();

        if (simbolo.equals("|")) {
            for (Hoja h : c1.getLastpos()) {
                lastpos.add(h);
            }
            for (Hoja h : c2.getLastpos()) {
                lastpos.add(h);
            }

        } else if (simbolo.equals(".")) {
            if (c2.isNullable()) {
                for (Hoja h : c1.getLastpos()) {
                    lastpos.add(h);
                }
                for (Hoja h : c2.getLastpos()) {
                    lastpos.add(h);
                }
            } else {
                lastpos = c2.getLastpos();
            }

        } else if (simbolo.equals("*")) {
            lastpos = c.getLastpos();

        }
        return lastpos;
    }

    public void followpos(Hoja hojaCompuesta){
        String simbolo = hojaCompuesta.getSimbolo();
        Hoja c1 = hojaCompuesta.getHijoIzquierdo();
        Hoja c2 = hojaCompuesta.getHijoDerecho();

        if(simbolo.equals(".")){
            for (Hoja i: c1.getLastpos()) {
                i.getFollowpos().addAll(c2.getFirstpos());
                //i.setFollowpos(c2.getFirstpos());
            }
        }else if(simbolo.equals("*")){
            for (Hoja i: hojaCompuesta.getLastpos()) {
                i.getFollowpos().addAll(hojaCompuesta.getFirstpos());
                //i.setFollowpos(hojaCompuesta.getFirstpos());
            }
        }

    }
    public void estadoFinal(AutomataDFA a){
        for (EstadoAFDHoja e: a.getDstates()) {
            for (Hoja h: e.getElSet()) {
                if(h.getCaracter().equals("#")){
                    a.setEstadoFinalHoja(e);
                }
            }
        }
        for (EstadoAFDHoja e: a.getDstates()) {
            if(e.equals(a.getEstadoFinalHoja())){
                e.setFinal(true);
            }
        }
    }

    public void construccionDirecta(AutomataDFA automata,Hoja n, ArrayList<String> alfabeto){

        //Creando una nueva instancia del objeto EstadoAFDHoja  que contiene como atributo el firstpos de la hoja n.
        EstadoAFDHoja elEstado = new EstadoAFDHoja(n.getFirstpos());

        //Inicializando Dstates con el EstadoAFDHOja sin marcar
        automata.getDstates().add(elEstado);
        automata.setEstadoInicialHoja(elEstado);

        while(hayDesmarcado(automata)){
            //Marcando el estado S como verdadero
            automata.getS().setMarcado(true);
            //Obteniendo el conjunto firstpos de S
            EstadoAFDHoja S = automata.getS();

            //Empezando a recorrer el alfabeto
            for(String a : alfabeto){
                Set<Hoja> setDeU2 = new HashSet<>();
                for (Hoja h: S.getElSet()) {
                    if(h.getCaracter().equals(a)){
                        setDeU2.addAll(h.getFollowpos());
                    }
                }
                EstadoAFDHoja U2 = new EstadoAFDHoja(setDeU2);
                if(!yaexiste(automata,U2)){
                    U2.setMarcado(false);
                    automata.getDstates().add(U2);
                    automata.setU2(U2);
                }
                DtranHoja nuevaTransicion = new DtranHoja(S, a, automata.getU2());
                automata.getTransicionesAFDHoja().add(nuevaTransicion);
            }
        }
    }

    public boolean yaexiste(AutomataDFA automata, EstadoAFDHoja U){
        boolean yaesta = false;
        for (EstadoAFDHoja e: automata.getDstates()) {
            if(e.getElSet().equals(U.getElSet())){
                automata.setU2(e);
                yaesta=true;
            }
        }

        return yaesta;
    }

    public void nombrarNodos(AutomataDFA automata){
        for(int x=0; x<automata.getDstates().size(); x++){
            automata.getDstates().get(x).setNumeroEstadoDFA(x);
        }
        for (EstadoAFDHoja e: automata.getDstates()) {
            if(e.equals(automata.getEstadoFinalHoja())){
                e.setFinal(true);
            }
        }
    }


    public boolean hayDesmarcado(AutomataDFA automata){
        boolean hayDesmarcado=false;
        for (EstadoAFDHoja e:automata.getDstates()) {
            if(!e.isMarcado()){
                hayDesmarcado = true;
                automata.setS(e);
            }
        }
        return hayDesmarcado;
    }


    //Metodo que genera la descripcion del automata AFD generado directamente
    public String descripcionAFDdirecto(AutomataDFA automata, ArrayList<String> alfabeto){

        String descripcion="AFD Construccion Directa: \n\nEstados: {";
        for (EstadoAFDHoja e:automata.getDstates()) {
            descripcion += e.getNumeroEstadoDFA() +", ";
        }
        descripcion += "}\nAlfabeto: {";
        for (String a:alfabeto) {
            descripcion += a +", ";
        }
        estadoFinal(automata);
        descripcion+="}\nEstado inicial: " + automata.getEstadoInicialHoja().getNumeroEstadoDFA()+"\nEstado Final: "+ automata.getEstadoFinalHoja().getNumeroEstadoDFA()+"\nTransiciones: ";
        for (DtranHoja d:automata.getTransicionesAFDHoja()) {
            descripcion+="("+d.getOrigen().getNumeroEstadoDFA()+"-"+d.getTransicion()+"-"+d.getDestino().getNumeroEstadoDFA() +"), ";
        }

        descripcion+="}";
        for (EstadoAFDHoja e: automata.getDstates()) {
            if(e.equals(automata.getEstadoFinalHoja())){
                e.setFinal(true);
            }
        }
        return descripcion;
    }

    public EstadoAFDHoja moveSimulacionDirecto(EstadoAFDHoja estado, String a, AutomataDFA automata){
        EstadoAFDHoja moveTA = null;
        ArrayList<DtranHoja> transiciones = automata.getTransicionesAFDHoja();
        for (DtranHoja d: transiciones) {
            if(d.getOrigen().equals(estado)){
                if(d.getTransicion().equals(a)){
                    moveTA=d.getDestino();
                }
            }
        }
        return moveTA;
    }

    public boolean simulacionAFDdirecto(AutomataDFA automata, String cadena){
        //System.out.println(cadena);
        EstadoAFDHoja e = automata.getEstadoInicialHoja();
        String c;
        int x = 0, y=0;

        while(x<cadena.length()){
            c=String.valueOf(cadena.charAt(x));
            e = moveSimulacionDirecto(e, c, automata);
            x++;
            if(e==null){
                y=0;
                break;
            }else{
                if(e.isFinal()){
                    y=1;
                }else{
                    y=0;
                }
            }
        }
        if(y==1){
            //System.out.println("La cadena si es aceptada.");
            return true;
        }else{
            //System.out.println("La cadena no es aceptada.");
            return false;
        }
    }

    public ArrayList<String> generateAlphabet(String cadena) {

        ArrayList<String> alfabeto = new ArrayList<>();
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (!alfabeto.contains(caracter) && !caracter.equals("*") && !caracter.equals("|") && !caracter.equals(".")  && !caracter.equals("$")) {
                alfabeto.add(caracter);
            }
        }
        return alfabeto;

    }
/* ********************************************METODOS PARA VERIFICACION DE SINTAXIS*****************************************/
    public ArrayList<String> fileReader(String path){
        ArrayList<String> text = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String temp="";
            String bfRead;
            while((bfRead = bf.readLine())!= null){
                if(!bfRead.equals("")){
                    text.add(bfRead);
                }
            }
        }catch (Exception ex){
            System.err.println("No se encontró ningun archivo :v");
            System.exit(0);
        }
        return text;
    }

    public ErrorType recorrido(ArrayList<String> fileContent, AutomataDFA ident, AutomataDFA string, AutomataDFA charV, AutomataDFA number, CocolStructure structure){
        ErrorType error = new ErrorType(0,0);
        int errorLine=0; int j=0; int b=0;
        String compilerIdent="";
        String endCompilerIdent="";
        int result = 0; int tokensInit=0; int ignoreLine = 0;
        String com = "";
        String end = "";
        String cadena = "";
        String temp = "";
        int productionsIndex = 0;

        //Obteniendo la PRIMERA linea de la estructura de Coco/l
        String start = fileContent.get(0);
        //Obteniendo la ULTIMA linea de la estructura de Coco/l
        String finale = fileContent.get(fileContent.size()-1);

        //Comprobando que tenga el inicio correcto*/
        for(int i = 0; i<start.length();i++){
            String caracter = String.valueOf(start.charAt(i));
            if(i<=8){
                com+=caracter; //Acumulando la cadena que contiene la seccion "COMPILER "
            }
            if(i==9){
                if(!com.equals("COMPILER ")){
                    //result=1;
                    error = new ErrorType(1, 0);
                    return error;
                }
            }
            if(i>=9){
                compilerIdent+=caracter;
            }

        }
        if(!simulacionAFDdirecto(ident, compilerIdent)){
            //result=2;
            error = new ErrorType(2, 0);
            return error;
        }

        //Comprobando que tenga el final correcto
        for (int i = 0;i<finale.length();i++){
            String caracter = String.valueOf(finale.charAt(i));
            if(i<=3){
                end+=caracter;
            }
            if(i==4){
                if(!end.equals("END ")){
                   result=3;
                    error = new ErrorType(3, fileContent.size()-1);
                   return error;
                }
            }
            if(i>=4){
                endCompilerIdent+=caracter;
            }
        }
        if(!endCompilerIdent.equals(compilerIdent+".")){
            //result=4;
            error = new ErrorType(4, fileContent.size()-1);
            return error;
        }

        //Comprobando que se encuentre la palabra clave CHARACTERS
        //Metodo para obtener el indice de la linea de Characters
        for (int i=0; i<fileContent.size();i++) {
            String s = fileContent.get(i);
            if(s.contains("CHARACTERS")){
                cadena=fileContent.get(i);
                errorLine=i;
                j=i+1;
                b=j-1;
            }

        }
       // cadena = fileContent.get(charactersIndex);
        for(int i=0; i<cadena.length(); i++){
            String caracter = String.valueOf(cadena.charAt(i));
            temp+=caracter;
        }
        if(!cadena.equals("CHARACTERS")){
            //result=5;
            error = new ErrorType(5, errorLine);
            return error;
        }

        //Obteniendo el contenido del segmento de CHARACTERS.
        cadena="";
        temp="";
        int keywInit=0; int ignoreIndex=0;
        boolean bandera=false;
        //ArrayList<String> charactersContent = new ArrayList<>();

        while(!bandera){
            if(j==fileContent.size()-1){
                bandera =true; //se alcanzó el final del documento
            }else{
                cadena = fileContent.get(j);

                for(int i=0; i<cadena.length(); i++){
                    String caracter = String.valueOf(cadena.charAt(i));
                    temp+=caracter;
                }
                if(cadena.equals("KEYWORDS")){
                    keywInit=j;
                    bandera=true;
                }else if(cadena.equals("TOKENS")){
                    tokensInit=j;
                    bandera=true;
                }else if(cadena.contains("IGNORE")) {
                    ignoreIndex=j;
                    bandera=true;
                }else if(cadena.contains("PRODUCTIONS")){
                    productionsIndex = j;
                    bandera = true;
                }else{
                    //añadiendo el contenido de Characters.
                    cadena=cadena.replace(" ", "");
                    structure.getCharactersContent().add(cadena);
                }
                j++;
            }
        }

        //Verificando el contenido de characters
        int cont=0;
        for (String s: structure.getCharactersContent()) {
            cont++;
            if(!s.contains("=")){
                //result = 7;
                error = new ErrorType(7,b+cont);
                return error;
            }else if(!(String.valueOf(s.charAt(s.length()-1))).equals(".")){
                //result = 8;
                error= new ErrorType(8, b+cont);
                return error;
            }else{
                String[] parts = s.split("=");
                if(!simulacionAFDdirecto(ident, parts[0])){
                    //result = 6;
                    error = new ErrorType(6, b+cont);
                    return error;
                }

                parts[1]=parts[1].substring(0,parts[1].length()-1);
                //Comprobando que funcionen los Sets.!simulacionAFDdirecto(set, parts[1])
                if(!Set(parts[1], string, ident, charV, number)){
                    //result = 9;
                    error = new ErrorType(9, b+cont);
                    return error;
                }
            }
        }

        if(keywInit!=0){
            //Verificando la parte de KEYWORDS
            //ArrayList<String> keywordsContent = new ArrayList<>();
            if(!fileContent.get(keywInit).equals("KEYWORDS")){
                result = 10;
                error = new ErrorType(10, keywInit);
                return error;
            }else{
                for (int i=0; i<fileContent.size();i++) {
                    if(i>keywInit && i<fileContent.size()-1){
                        if(fileContent.get(i).equals("TOKENS")){
                            tokensInit = i;
                            i=fileContent.size();
                        }else{
                            structure.getKeywordsContent().add(fileContent.get(i));
                            //keywordsContent.add(fileContent.get(i));
                        }
                    }
                }
                int contt=0;
                //Recopilando cada seccion de keywords
                for (String s: structure.getKeywordsContent()) {
                    contt++;
                    s=s.replace(" ", "");
                    if(!s.contains("=")){
                        //result = 11;
                        error = new ErrorType(11, contt+keywInit);
                        return error;
                    }else if(!(String.valueOf(s.charAt(s.length()-1))).equals(".")){
                        //result = 12;
                        error = new ErrorType(12, contt+keywInit);
                        return error;
                    }else{
                        String[] parts = s.split("=");
                        if(!simulacionAFDdirecto(ident, parts[0])){
                            //result = 13;
                            error = new ErrorType(13, contt+keywInit);
                            return error;
                        }
                        parts[1]=parts[1].replace(".", "");
                        if(!simulacionAFDdirecto(string, parts[1])){
                            //result = 14;
                            error = new ErrorType(14, contt+keywInit);
                            return error;
                        }
                    }
                }
            }
        }


        //Verificando la seccion de Tokens
        //ArrayList<String>  tokensContent = new ArrayList<>();
        if(tokensInit!=0){
            if(!fileContent.get(tokensInit).equals("TOKENS")){
                //result = 15;
                error = new ErrorType(15, tokensInit);
                return error;
            }
            //Obteniendo el contenido de tokens
            for (int i=tokensInit; i<fileContent.size();i++) {
                if(i>tokensInit && i<fileContent.size()-1){
                    if(fileContent.get(i).contains("IGNORE")){
                        ignoreLine=i;
                        i=fileContent.size();
                    }else{
                        structure.getTokensContent().add(fileContent.get(i));
                        //tokensContent.add(fileContent.get(i));
                    }
                }
            }

            //Verificando cada Token
            //
            int contadorTokens=0;
            for (String s: structure.getTokensContent()) {
                s=s.replace(" ", "");
                contadorTokens++;
                if(s.contains("=")){
                    String[] tokenParts = s.split("=");
                    //Verificando el ident del token
                    if(!simulacionAFDdirecto(ident, tokenParts[0])){
                        //result = 16;
                        error= new ErrorType(16, contadorTokens+tokensInit);
                        return error;
                    }
                    if(!tokenParts[1].contains(".")){
                        //result = 17;
                        error= new ErrorType(17, contadorTokens+tokensInit);
                        return error;
                    }else{
                        tokenParts[1]=tokenParts[1].replace(".", "");
                        if(!TokenExpr(tokenParts[1],string, ident, charV)){
                            error = new ErrorType(18, contadorTokens+tokensInit);
                            return error;
                        }
                    }
                }else{
                    //Entra aqui si el token solamente es un identificador
                    if(!simulacionAFDdirecto(ident, s)){
                        //result = 16;
                        error= new ErrorType(16, contadorTokens+tokensInit);
                        return error;
                    }
                }
            }//
        }


        ///*
        //Obteniendo los ignores
        if(ignoreLine!=0){
            String ignore  = fileContent.get(ignoreLine);
            String ignoreContent = "";
            if(!String.valueOf(ignore.charAt(ignore.length()-1)).equals(".")){
                error = new ErrorType(19, ignoreLine);
                return error;
            }else{
                ignore=ignore.replace(".", "");
                for (int i=7; i<ignore.length();i++){
                    ignoreContent+=String.valueOf(ignore.charAt(i));
                }

                /*if(!Set(ignoreContent, string, ident, charV, number)){
                    error = new ErrorType(20, ignoreLine);
                    return error;
                }*/
            }
        }

        for(int i=ignoreLine; i<fileContent.size();i++){
            String verifyProductions = fileContent.get(i);
            verifyProductions = verifyProductions.replace(" ", "");
            if(verifyProductions.equals("PRODUCTIONS")){
                productionsIndex=i;
            }
        }
        if(productionsIndex!=0){
            for(int i=productionsIndex+1;i<fileContent.size()-1;i++){
                //Getting the whole string of the production.
                String productionLine = fileContent.get(i);
                //Removing blank spaces from the production.
                //productionLine = productionLine.replace(" ", "");
                //Splits productionLine into head and body.
                String[] parts = productionLine.split("=");
                String head = parts[0];
                head = head.replace(" ", "");
                String body = parts[1];
                if(body.endsWith(".")){
                    body = body.substring(0, body.length()-1);
                }
                //Creates a new Productions type Object and adds it to ProductionsContent ArrayList.
                Productions theProduction = new Productions(head, body);

                structure.getProductionsContent().add(theProduction);
            }
        }


        return error;
    }

 /* *-**************************METODOS PARA ELEMENTOS NO TERMINALES DE LA GRAMATICA************************/

    public boolean Set(String cadena, AutomataDFA string, AutomataDFA ident, AutomataDFA charV, AutomataDFA number){
        boolean correcto=false;
        String caracter="";
        String cadenaBasicSet="";
        int indicador =0;
        for(int i=0; i<cadena.length();i++){
            caracter=String.valueOf(cadena.charAt(i));
            //Verificando si el caracter que se esta leyendo es un + o -
            //Mientras el caracter no sea un simbolo +|- los une en una cadena
            if(!caracter.equals("+") && !caracter.equals("-")){
                cadenaBasicSet+=caracter;
                indicador =i;
            }else{
                String parte1 = cadena.substring(0, indicador+1);
                String parte2 = cadena.substring(indicador+2);
                if(BasicSet(parte1, string, ident, charV, number) && Set(parte2,string, ident, charV, number)){
                    correcto=true;
                }
            }
        }
        if(BasicSet(cadenaBasicSet, string, ident, charV, number)){
            correcto=true;
        }
        return correcto;
    }

    public boolean BasicSet(String cadena, AutomataDFA string, AutomataDFA ident, AutomataDFA charV, AutomataDFA number){
        boolean correcto =false;
        if(String(cadena, string) | Ident(cadena, ident) | CHAR(cadena, charV, number)){
            correcto=true;
        }
        return correcto;
    }

    public boolean String(String cadena, AutomataDFA string){
        boolean correcto = false;
        if(simulacionAFDdirecto(string, cadena)){
            correcto=true;
        }
        return correcto;
    }
    public boolean Ident(String cadena, AutomataDFA ident){
        boolean correcto = false;
        if(simulacionAFDdirecto(ident, cadena)){
            correcto=true;
        }
        return correcto;
    }
    public boolean CHAR(String cadena, AutomataDFA charV, AutomataDFA number){
        String caracter="";
        String cadenaChar="";
        String separador="..";
        boolean correcto = false;

        if(cadena.contains("..")){
            String[] parts = cadena.split("\\.\\.");
            if(Char(parts[0], charV, number) && Char(parts[1], charV, number)){
                correcto=true;
            }
        }else{
            if(Char(cadena, charV, number)){
                correcto=true;
            }
        }
        return correcto;
    }

    public boolean Char(String cadena, AutomataDFA charV, AutomataDFA number){
        boolean correcto = false;
        if(charV(cadena, charV) | charN(cadena, number)){
            correcto=true;
        }
        return correcto;
    }

    public boolean charV(String cadena, AutomataDFA charV){
        boolean correcto = false;
        if(simulacionAFDdirecto(charV, cadena)){
            correcto=true;
        }
        return correcto;
    }

    public boolean charN(String cadena, AutomataDFA number){
        boolean correcto = false;
        String caracter="";
        String c="";
        String comprobacion="";
        String cadenaNumber="";
        if(cadena.length()>4){
            for (int i=0; i<4; i++){
                caracter=String.valueOf(cadena.charAt(i));
                comprobacion+=caracter;
            }
            if(comprobacion.equals("CHR(")){
                if(String.valueOf(cadena.charAt(cadena.length()-1)).equals(")")){
                    for(int i=4; i<cadena.length();i++){
                        c=String.valueOf(cadena.charAt(i));
                        cadenaNumber+=c;
                    }
                    if(number(cadenaNumber, number)){
                        correcto=true;
                    }
                }
            }
        }

        return correcto;
    }

    public boolean number(String cadena, AutomataDFA number){
        boolean correcto = false;
        if(simulacionAFDdirecto(number, cadena)){
            correcto=true;
        }
        return correcto;
    }

    public boolean llavesIguales(String cadena){
        boolean correcto=false;
        int contLlaves=0;
        int contCorchete=0;
        int contParentesis=0;
        for(int i=0; i<cadena.length();i++){
            String caracter=String.valueOf(cadena.charAt(i));
            if(caracter.equals("(")){
                contParentesis++;
            }
            if(caracter.equals("{")){
                contLlaves++;
            }
            if(caracter.equals("[")){
                contCorchete++;
            }
            if(caracter.equals(")")){
                contParentesis--;
            }
            if(caracter.equals("}")){
                contLlaves--;
            }
            if(caracter.equals("]")){
                contCorchete--;
            }
        }

        if(contCorchete==0 && contParentesis==0 && contLlaves==0){
            correcto=true;
        }
        return correcto;
    }

    public boolean TokenExpr(String linea, AutomataDFA string, AutomataDFA ident, AutomataDFA charV){
        boolean correcto = false;
        String caracter="";
        String cadenaTT="";
        int indicador =0;
        for(int i=0; i<linea.length();i++){
            caracter=String.valueOf(linea.charAt(i));
            //Verificando si el caracter que se esta leyendo es un + o -
            //Mientras el caracter no sea un simbolo +|- los une en una cadena
            if(!caracter.equals("|")){
                cadenaTT+=caracter;
                indicador =i;
                if(i==linea.length()-1){
                    if(TokenTerm(cadenaTT, string, ident, charV)){
                        correcto = true;
                    }
                }
            }else{
                if(llavesIguales(cadenaTT)){
                    String parte1 = linea.substring(0, indicador+1);
                    String parte2 = linea.substring(indicador+2, linea.length());
                    if(TokenTerm(parte1, string, ident, charV) && TokenTerm(parte2, string, ident, charV)){
                        correcto=true;
                    }
                }else{
                    if(TokenTerm(linea, string, ident, charV )){
                        correcto=true;
                        return correcto;
                    }
                }
            }
        }

        return correcto;
    }

    public boolean TokenTerm(String cadena, AutomataDFA string, AutomataDFA ident, AutomataDFA charV){
        boolean correcto = false;
        String firstTokenFactor="";
        String secondTokenFactor="";
        String caracter="";
        int indicador=0;
        for (int i=0; i<cadena.length();i++){
            caracter=String.valueOf(cadena.charAt(i));
            if(!caracter.equals("(") && !caracter.equals("{") && !caracter.equals("[")){
                firstTokenFactor+=caracter;
                indicador=i;
            }else{
                String parte1 = cadena.substring(0, indicador+1);
                String parte2 = cadena.substring(indicador+1);
                if(TokenFactor(parte1, string, ident, charV) && TokenFactor(parte2, string, ident, charV)){
                    correcto=true;
                }
            }
        }
        if(TokenFactor(cadena, string, ident, charV)){
            correcto=true;
        }
        return correcto;
    }

    public boolean TokenFactor(String cadena, AutomataDFA string, AutomataDFA ident, AutomataDFA charV){
        String firstCharacter = String.valueOf(cadena.charAt(0));
        String lastCharacter = String.valueOf(cadena.charAt(cadena.length()-1));
        String subString = cadena.substring(1, cadena.length()-1);
        boolean correcto = false;

        if(firstCharacter.equals("(") && lastCharacter.equals(")")){
            if(TokenExpr(subString, string, ident, charV)){
                correcto=true;
            }
        }else if(firstCharacter.equals("[") && lastCharacter.equals("]")){
            if(TokenExpr(subString, string, ident, charV)){
                correcto=true;
            }
        }else if(firstCharacter.equals("{") && lastCharacter.equals("}")){
            if(TokenExpr(subString, string, ident, charV)){
                correcto=true;
            }
        }else{
            if(symbol(cadena, string, ident, charV)){
                correcto=true;
            }
        }

        return correcto;
    }

    public boolean symbol(String cadena, AutomataDFA string, AutomataDFA ident, AutomataDFA charV){
        boolean correcto=false;
        if(String(cadena, string) | Ident(cadena, ident) | charV(cadena, charV)){
            correcto=true;
        }
        return correcto;
    }

    public void tokenR(String cadena, AutomataDFA ident){
        String E="";
        String indexElement="";
        String temp="";
        int index = 0;
        int length = cadena.length();

        while(index<length){
            indexElement=String.valueOf(cadena.charAt(index));
            if(indexElement.equals("\"")){
                //será un String
            }else if(indexElement.equals("\'")){
                //será char
            }else{
                E+=indexElement;
                if(simulacionAFDdirecto(ident, E)){
                    temp=E; //Si el automata acepta la cadena lo guarda en una temporal.
                }
            }
        }
    }

 /* ****************************************************************************************************/
    public void Errors(int numberOfError, int errorLine){
        errorLine++;
        switch (numberOfError){
            case 1:
                System.err.println("Error de Sintaxis #1: No se encuentra la palabra inicial COMPILER, en la linea " + errorLine + ".");
                break;
            case 2:
                System.err.println("Error de Sintaxis #2: Identificador no valido para compiler, en la linea " + errorLine + ".");
                break;
            case 3:
                System.err.println("Error de Sintaxis #3: No se encuentra la palabra de cierre END, en la linea "+errorLine+".");
                break;
            case 4:
                System.err.println("Error de Sintaxis #4: Identificador final debe ser igual al identificador incial, en la linea " + errorLine+".");
                break;
            case 5:
                System.err.println("Error de Sintaxis #5: No se encuentra la palabra de inicio del segmento CHARACTERS.");
                break;
            case 6:
                System.err.println("Error de Sintaxis #6: Identificador incorrecto dentro segmento CHARACTERS, en la linea "+errorLine+".");
                break;
            case 7:
                System.err.println("Error de Sintaxis #7: No se encuentra el simbolo \"=\" en una definicion de CHARACTERS, en la linea "+errorLine+".");
                break;
            case 8:
                System.err.println("Error de Sintaxis #8: No se encuentra el simbolo \".\" en una definicion de CHARACTERS. en la linea "+errorLine+".");
                break;
            case 9:
                System.err.println("Error de Sintaxis #9: Set mal declarado en una definicion de CHARACTERS, en la linea "+errorLine+".");
                break;
            case 10:
                System.err.println("Error de Sintaxis #10: No se encuentra la palabra de inicio del segmento KEYWORDS.");
                break;
            case 11:
                System.err.println("Error de Sintaxis #11: No se encuentra el simbolo \"=\" en una definicion de KEYWORDS, en la linea "+errorLine+".");
                break;
            case 12:
                System.err.println("Error de Sintaxis #12: No se encuentra el simbolo \".\" en una definicion de KEYWORDS, en la linea "+errorLine+".");
                break;
            case 13:
                System.err.println("Error de Sintaxis #13: Identificador incorrecto dentro de KEYWORDS, en la linea "+errorLine+".");
                break;
            case 14:
                System.err.println("Error de Sintaxis #14: String mal declarado en una definicion de KEYWORDS, en la linea "+errorLine+".");
                break;
            case 15:
                System.err.println("Error de Sintaxis #15: No se encuentra la palabra de inicio del segmento TOKENS.");
                break;
            case 16:
                System.err.println("Error de Sintaxis #16: Identificador para token incorrecto dentro de Tokens, en la linea "+errorLine+".");
                break;
            case 17:
                System.err.println("Error de Sintaxis #17: Token no termina con '.' ,  dentro de Tokens, en la linea "+errorLine+".");
                break;
            case 18:
                System.err.println("Error de Sintaxis #18: TokenExpression no válida dentro de Tokens, en la linea "+errorLine+".");
                break;
            case 19:
                System.err.println("Error de Sintaxis #19: Seccion de Ignore no termina con '.' , en la linea "+errorLine+".");
                break;
            case 20:
                System.err.println("Error de Sintaxis #18: Contenido de ignore no valido, en la linea "+errorLine+".");
                break;
            default:
                System.out.println("No se encontraron errores. CORRECTO");
                break;
        }
    }

/* ************************************* GENERADOR DEL LEXER ***************************************/

        public  void lexerGenerator(CocolStructure structure){
            Lexer elLexer = new Lexer();

            String tokens = "";

            //Creando los tokens para characters
            for (String s: structure.getCharactersContent()) {
                s=s.replace("\"", "");
                s=s.replace(".", "");
                String[] parts = s.split("=");
                String identificador=parts[0];
                Tokens elToken;
                //s=s.replace("\"", "");
                for (Tokens t: elLexer.getCharacterTokens()) {
                    parts[1]=parts[1].replace("+", "");
                    if(parts[1].contains(t.getId())){
                        parts[1]=t.getValue()+parts[1];
                    }
                }
                for (Tokens t: elLexer.getCharacterTokens()) {
                    if(parts[1].contains(t.getId())){
                        parts[1]=parts[1].replace(t.getId(),"");
                    }
                }
                for (int i=0; i<parts[1].length(); i++) {
                    String value = String.valueOf(parts[1].charAt(i));
                    elToken = new Tokens(identificador, value);
                    tokens += identificador+","+value+"\n";
                    elLexer.getCharacterTokens().add(elToken);
                }
            }

            for (String s: structure.getKeywordsContent()) {
                s=s.replace(" ", "");
                s=s.replace("\"", "");
                s=s.replace(".", "");
                String[] parts = s.split("=");
                String identificador = parts[0];
                String value = parts[1];
                tokens += identificador+","+value+"\n";

                Tokens elToken = new Tokens(identificador, value);
                elLexer.getKeywordTokens().add(elToken);

            }

            System.out.println("El lexer ha sido generado.");


            /*
            ArrayList<String> tokensAL = new ArrayList<>();
            try {
                BufferedReader bf = new BufferedReader(new FileReader("tokens.txt"));
                String temp="";
                String bfRead;
                while((bfRead = bf.readLine())!= null){
                    tokensAL.add(bfRead);
                }
            }catch (Exception ex){
                System.err.println("No se encontró ningun archivo :v");
            }


            for (String s:text) {
                String resultado="";
                String[] elArray = s.split(" ");
                for (String E: elArray) {
                    boolean existe=false;
                    for (String B: tokensAL) {
                        if(!B.isEmpty()){
                           System.out.println("Estoy vacio.");
                            String[] tokenParts = B.split(",");
                            String tokenName = tokenParts[0];
                            String tokenValue = tokenParts[1];
                            if(tokenValue.equals(E)){
                                resultado+="<"+tokenName+", \""+E+"\">, ";
                                existe=true;
                            }
                        }
                    }
                    if(!existe){
                        for (int i=0;i<E.length();i++){
                            String caracter = String.valueOf(E.charAt(i));
                            for (String B: tokensAL) {
                                if(!B.isEmpty()){
                                    String[] tokenParts = B.split(",");
                                    String tokenName = tokenParts[0];
                                    String tokenValue = tokenParts[1];
                                    if(tokenValue.equals(caracter)){
                                        resultado+="<"+tokenName+", \""+caracter+"\">, ";
                                        existe=true;
                                    }
                                }
                            }
                        }
                    }
                    if(!existe){
                        System.out.println("Segun mi gramatica no hay tokens que definan \""+E+"\", lo lamento.");
                    }
                }
                System.out.println(s);
                System.out.println(resultado);
            }
            */

            //Generando archivo.txt con los tokens
            BufferedWriter bw1 = null;
            FileWriter fw1 = null;
            try {

                PrintWriter writer = new PrintWriter("tokens.txt");
                writer.println(tokens);
                writer.close();

            } catch (Exception e) {

                e.printStackTrace();

            }


            //Generando el archivo lexer.java
            BufferedWriter bw = null;
            FileWriter fw = null;

            String contenido="";
            String imports ="import java.util.*;\nimport java.io.*;\n\n";
            String cabeza="public class lexer{\n";
            String metodo=" public static void main(String[] args) {\n";
            String codigo ="        Scanner scanner = new Scanner(System.in);\n" +
                    "        System.out.println(\"Ingrese el nombre del archivo que desea lexear: \");\n" +
                    "        String nombreDelArchivo=scanner.nextLine();\n"+
                    "        String path = System.getProperty(\"user.dir\") + \"\\\\\"+nombreDelArchivo+\"\";"+
                    "        ArrayList<String> text = new ArrayList<>();\n" +
                    "        try {\n" +
                    "            BufferedReader bf = new BufferedReader(new FileReader(path));\n" +
                    "            String temp=\"\";\n" +
                    "            String bfRead;\n" +
                    "            while((bfRead = bf.readLine())!= null){\n" +
                    "                text.add(bfRead);\n" +
                    "            }\n" +
                    "        }catch (Exception ex){\n" +
                    "            System.err.println(\"No se encontró ningun archivo :v\");\n" +
                    "        }"+
                    "        System.err.println(text);\n" +
                    "        ArrayList<String> tokensAL = new ArrayList<>();\n" +
                    "            try {\n" +
                    "                BufferedReader bf = new BufferedReader(new FileReader(\"tokens.txt\"));\n" +
                    "                String temp=\"\";\n" +
                    "                String bfRead;\n" +
                    "                while((bfRead = bf.readLine())!= null){\n" +
                    "                    tokensAL.add(bfRead);\n" +
                    "                }\n" +
                    "            }catch (Exception ex){\n" +
                    "                System.err.println(\"No se encontró ningun archivo :v\");\n" +
                    "            }\n" +
                    "\n" +
                    "\n" +
                    "            for (String s:text) {\n" +
                    "                String resultado=\"\";\n" +
                    "                String[] elArray = s.split(\" \");\n" +
                    "                for (String E: elArray) {\n" +
                    "                    boolean existe=false;\n" +
                    "                    for (String B: tokensAL) {\n" +
                    "                        if(!B.isEmpty()){\n" +
                    "                            String[] tokenParts = B.split(\",\");\n" +
                    "                            String tokenName = tokenParts[0];\n" +
                    "                            String tokenValue = tokenParts[1];\n" +
                    "                            if(tokenValue.equals(E)){\n" +
                    "                                resultado+=\"<\"+tokenName+\", \\\"\"+E+\"\\\">, \";\n" +
                    "                                existe=true;\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    }\n" +
                    "                    if(!existe){\n" +
                    "                        for (int i=0;i<E.length();i++){\n" +
                    "                            String caracter = String.valueOf(E.charAt(i));\n" +
                    "                            for (String B: tokensAL) {\n" +
                    "                                if(!B.isEmpty()){\n" +
                    "                                    String[] tokenParts = B.split(\",\");\n" +
                    "                                    String tokenName = tokenParts[0];\n" +
                    "                                    String tokenValue = tokenParts[1];\n" +
                    "                                    if(tokenValue.equals(caracter)){\n" +
                    "                                        resultado+=\"<\"+tokenName+\", \\\"\"+caracter+\"\\\">, \";\n" +
                    "                                        existe=true;\n" +
                    "                                    }\n" +
                    "                                }\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    }\n" +
                    "                    if(!existe){\n" +
                    "                        System.out.println(\"Segun mi gramatica no hay tokens que definan \\\"\"+E+\"\\\", lo lamento.\");\n" +
                    "                    }\n" +
                    "                }\n" +
                    "                System.out.println(s);\n" +
                    "                System.out.println(resultado);\n" +
                    "            }";
            String llaveFinal ="\n  }\n}";
            contenido=imports+cabeza+metodo+codigo+llaveFinal;
            try {

                PrintWriter writer = new PrintWriter("lexer.java");
                writer.println(contenido);
                writer.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }
}

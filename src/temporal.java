import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class temporal {

    Stack<String> symbols = new Stack<>();
    Stack<String> states = new Stack<>();

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el String que desea parsear: ");
        String input=scanner.nextLine();


        ArrayList<String> contenidoTabla = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("tabla de parseo.txt"));
            String temp="";
            String bfRead;
            while((bfRead = bf.readLine())!= null){
                contenidoTabla.add(bfRead);
            }
        }catch (Exception ex){
            System.err.println("No se encontró ningun archivo :v");
        }

        int x = contenidoTabla.size();
        String[] parts = contenidoTabla.get(0).split(",");
        int y = parts.length;

        String[][] tabla = new String[x][y];

        for (int i=0;i<contenidoTabla.size();i++) {
            parts = contenidoTabla.get(i).split(",");
            for(int j=0; j<parts.length;j++){
                tabla[i][j] = parts[j];
            }
        }

        ArrayList<String> producciones = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("producciones.txt"));
            String temp="";
            String bfRead;
            while((bfRead = bf.readLine())!= null){
                producciones.add(bfRead);
            }
        }catch (Exception ex){
            System.err.println("No se encontró ningun archivo :v");
        }


        ArrayList<String> simbolos = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("simbolos.txt"));
            String temp="";
            String bfRead;
            while((bfRead = bf.readLine())!= null){
                simbolos.add(bfRead);
            }
        }catch (Exception ex){
            System.err.println("No se encontró ningun archivo :v");
        }

        int k =1;
        String[] inputParts = input.split(" ");
        //Creando un stack de inputs
        Stack<String> inputStack = new Stack<>();
        Stack<Integer> stack = new Stack<>();
        Stack<String> simbols = new Stack<>();
        stack.push(1);

        while(k==1){

            inputParts = input.split(" ");

            int estado = stack.peek();

            int simbolo = simbolos.indexOf(inputParts[0]);

            String action = tabla[estado][simbolo];
            String[] actionParts = action.split(" ");
            if(actionParts[0].equals("s")){
                //Es un shift
                //Si es un shift pushea al stack de input la primera parte del input
                inputStack.push(inputParts[0]);
                stack.push(Integer.parseInt(actionParts[1])+1);
                input="";
                for(int i=1;i<inputParts.length;i++){
                    input+=inputParts[i] + " ";
                }
            }else if(actionParts[0].equals("g")){
                //Es un goto
                stack.push(Integer.parseInt(actionParts[1])+1);
                input="";
                for(int i=1;i<inputParts.length;i++){
                    input+=inputParts[i] + " ";
                }
            }else if(actionParts[0].equals("r")){
                //Es un reduce
                String p = producciones.get(Integer.parseInt(actionParts[1]));
                String[] partsP = p.split("=");
                String head = partsP[0];
                String body = partsP[1];
                body = body.replace("'", "");
                if(body.startsWith(" ")){
                    body = body.substring(1);
                }
                if(!body.endsWith(" ")){
                    body+=" ";
                }
                String cadena="";
                while(!body.equals(cadena)){
                    cadena =inputStack.pop() + " " + cadena;
                }
                inputStack.push(head);
                stack.pop();
                input= head + " " + input;
            }
        }
    }
}

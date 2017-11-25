import java.util.*;
import java.io.*;

public class lexer{
 public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo que desea lexear: ");
        String nombreDelArchivo=scanner.nextLine();
        String path = System.getProperty("user.dir") + "\\"+nombreDelArchivo+"";        ArrayList<String> text = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String temp="";
            String bfRead;
            while((bfRead = bf.readLine())!= null){
                text.add(bfRead);
            }
        }catch (Exception ex){
            System.err.println("No se encontró ningun archivo :v");
        }        System.err.println(text);
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
  }
}

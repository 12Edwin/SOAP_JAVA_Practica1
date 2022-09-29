import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;
import java.util.Random;
import java.util.Scanner;

@WebService(name = "Service",targetNamespace = "utez")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Service {
    static Scanner teclado = new Scanner(System.in);
    @WebMethod(operationName = "responseMessage")
    public String responseMessage(@WebParam(name = "num")String num){

        int numAleatorio = Integer.parseInt(num);
        String response= AleatorioN(numAleatorio);
        return response;
    }
    @WebMethod(operationName = "responseMessage2")
    public String responseMessage2(@WebParam(name = "palabra")String word){

        String palabra = (word);
        String response = consonantes(palabra);
        return response;
    }

    @WebMethod(operationName = "responseMessage3")
    public String responseMessage3(@WebParam(name = "nombre")String nombre,@WebParam(name = "ap1")String ap1,
                                   @WebParam(name = "ap2")String ap2,@WebParam(name = "dia")String dia,
                                   @WebParam(name = "mes")String mes,@WebParam(name = "anio")String anio){

        String response = rfc(nombre,ap1,ap2,dia,mes,anio);
        return response;
    }

    public static void main(String[] args) {
        System.out.println("Initializing sever...");
        Endpoint.publish("http://localhost:8080/Service", new Service());
        System.out.println("Waiting requests...");
    }
    public String AleatorioN (int n){
        Random random= new Random();
        int aleatorio;
        aleatorio = random.nextInt(11);
        if (aleatorio == n){
            return "Resultado correcto el valor era: "+aleatorio;
        }else {
            return "No es correcto, el valor era: "+aleatorio;
        }
    }
    private int count=0;
    private String word="";
    public String consonantes (String palabra){
        if (count == palabra.length()){
            return word;
        }else {
            String p = palabra.toLowerCase();
            if (!(p.charAt(count)=='a'|p.charAt(count) == 'e'| p.charAt(count)=='i'
                    | p.charAt(count)=='o'|p.charAt(count)=='u')){
                word += palabra.charAt(count);
            }
            count++;
            return consonantes(palabra);
        }
    }
    public String rfc (String nombre,String ap1,String ap2,String dia,String mes,String anio){
        String rfc;
        rfc = ap1.charAt(0)+""+ap1.charAt(1)+""+ap2.charAt(0)+""+nombre.charAt(0)+anio+mes+dia;
        for (int i=10;i<13;i++){
            String SALTCHARS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 18){
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            rfc += salt.toString();

        }
        String result="";
        for (int i=0;i<13;i++){
            result += rfc.charAt(i);
        }
        return result.toUpperCase();
    }
}

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RodarClient {
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String... args) {
        clearScreen();
        System.out.println("*** Bem-vindo ao somador de números 3.0, edição SOAP **");


        String soapEndpointUrl = "http://localhost:8080/estoquews?wsdl";
        String soapAction = "http://localhost:8080/estoquews";


        int numero1 = 0;
        int numero2 = 0;

        try (var scanner = new Scanner(System.in)) {
            System.out.print("Por favor insira o primeiro número: ");
            numero1 = Integer.parseInt(scanner.nextLine());
            System.out.print("Por favor insira o segundo numero: ");
            numero2 = Integer.parseInt(scanner.nextLine());
        }

        var arguments = List.of(
                new Pair<>("numero1", numero1),
                new Pair<>("numero2", numero2)
        );

        new SoapClient().callSoapWebservice(soapEndpointUrl, soapAction, arguments);
    }
}

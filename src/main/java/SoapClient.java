import jakarta.xml.soap.*;

import java.io.IOException;
import java.util.List;

public class SoapClient {

    public static final String NAMESPACE_URI = "http://ws.estoque.caelum.com.br/";
    public static final String PREFIX = "ws";
    public static final String OPERATION = "Somar";

    public void callSoapWebservice(String soapEndpointUrl, String soapAction, List<Pair<String, Integer>> arguments) {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, arguments), soapEndpointUrl);

            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);

            soapConnection.close();

        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static SOAPMessage createSOAPRequest(String soapAction, List<Pair<String, Integer>> arguments) throws SOAPException, IOException {
        // criar mensagem
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, soapAction);
        createSoapBody(soapMessage, arguments);

        soapMessage.saveChanges();

        // exibir mensagem
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        return soapMessage;
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage, String soapAction) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // preencher envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(PREFIX, NAMESPACE_URI);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
    }

    private static void createSoapBody(SOAPMessage soapMessage, List<Pair<String, Integer>> arguments) throws SOAPException {
        SOAPBody soapBody = soapMessage.getSOAPBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(OPERATION, PREFIX);

        for (var pair : arguments) {
            soapBodyElem.addChildElement(pair.getValue1()).addTextNode(pair.getValue2().toString());
        }
    }

}

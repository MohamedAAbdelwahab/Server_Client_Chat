import java.io.IOException;


public class MyClient {

    public static void main(String[] args) throws IOException {

        String host = "localhost";
        int port = 889;
        String command = "GET";

        // Method Check GET or PUT
        Client client = new Client();
        client.CreateSocket(host,port);
        client.Register("Mohamed123", "12345");
        client.Login("Mohamed123", "1w2345");
        client.Close();

    }

}
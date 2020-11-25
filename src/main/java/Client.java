import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    DataOutputStream request;
    DataInputStream response;
    Socket clientSocket = null;

    public void CreateSocket(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);

        System.out.println("======================================");
        System.out.println("Connected");
        System.out.println("======================================");

        // Declare a writer to this url
        request = new DataOutputStream (clientSocket.getOutputStream());

        // Declare a listener to this url
        response = new DataInputStream(
                clientSocket.getInputStream());
    }

    public void Login(String userName, String Password)
            throws IOException {

        // Sending request to the server
        request.writeUTF("Login,UserName:" + userName + ",Password:" + Password);
        System.out.println("Login,UserName:" + userName + ",Password:" + Password);
        request.flush();
        System.out.println("Request Sent!");
        System.out.println("======================================");
        // Receiving response from server
        String responseLine;
        responseLine = response.readUTF();
        if(responseLine.equals("404"))
        System.out.println("User Not Found");
        else if(responseLine.equals("401"))
            System.out.println("Password isn't correct");
        else
            System.out.println(responseLine);
        System.out.println("======================================");
        System.out.println("Response Recieved!!");
        System.out.println("======================================");
    }

    public void Register(String userName, String Password)
            throws IOException {
        // Sending request to the server
        // Building HTTP request header
        request.writeUTF("Register,UserName:" + userName + ",Password:" + Password);
        System.out.println("Register,UserName:" + userName + ",Password:" + Password);
        request.flush();
        System.out.println("Request Sent!");
        System.out.println("======================================");
        // Receiving response from server
        String responseLine;
        responseLine = response.readUTF();
            System.out.println(responseLine);

        System.out.println("======================================");
        System.out.println("Response Recieved!!");
        System.out.println("======================================");

    }

    public void Close()
            throws IOException {


        // Sending request to the server
        // Building HTTP request header
        request.writeUTF("Close");
        request.flush();
        // Receiving response from server

        System.out.println("Connection Close");
        request.close();
        response.close();
        clientSocket.close();
    }


}
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientHandler implements Runnable {

    private Socket client;
    String UserNameFile;
    String PasswordFile;
    DataInputStream request;
    DataOutputStream response;
    public SocketClientHandler(Socket client) throws IOException {
        this.client = client;
         request = new DataInputStream(
                client.getInputStream());
         response = new DataOutputStream((client.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread started with name:"
                    + Thread.currentThread().getName());
            readResponse();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readResponse() throws IOException, InterruptedException {

        try {

             while (true){
               if(!client.isClosed()) {
                String Request = request.readUTF();
                System.out.println(Request);
                String[] RequstFields = Request.split(",");
                switch (RequstFields[0]) {
                    case "Login": {
                        String[] FirstArg = RequstFields[1].split(":");
                        String UserName = FirstArg[1];
                        String[] SecondArgs = RequstFields[2].split(":");
                        String Password = SecondArgs[1];

                        try {
                            // assuming we have 1 user only for now for simplicity
                            File myObj = new File("filename.txt");
                            Scanner myReader = new Scanner(myObj);

                            UserNameFile = myReader.nextLine();
                            PasswordFile = myReader.nextLine();
                            myReader.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                        boolean condition1=UserName.equals(UserNameFile);
                        boolean condition2=Password.equals(PasswordFile);
                        if (condition1 && condition2 ) {
                            response.writeUTF("Login Successfully");
                            System.out.println("Login successfully");
                        }
                        if (!condition2)
                        {
                            response.writeUTF("401");
                        }
                        if(!condition1)
                        {
                            response.writeUTF("404");
                        }
                        response.flush();
                        break;
                    }
                    case "Register": {
                        String[] FirstArg = RequstFields[1].split(":");
                        String UserName = FirstArg[1];
                        String[] SecondArgs = RequstFields[2].split(":");
                        String Password = SecondArgs[1];

                        try {
                            FileWriter myWriter = new FileWriter("filename.txt");
                            myWriter.write(UserName);
                            myWriter.write("\n");
                            myWriter.write(Password);
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                            response.writeUTF("Registered successfully");
                            response.flush();
                            System.out.println(client.isConnected());
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            response.writeUTF("Registered Failed");
                            response.flush();
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "Close":
                        request.close();
                        response.close();
                        client.close();
                        break;
                }
            } }}catch(Exception e){
                e.printStackTrace();
            }

            }

}
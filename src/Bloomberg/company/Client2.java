package Bloomberg.company;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client2
{
    private int ClientPort;
    public Client2(int port) throws IOException
    {
        ClientPort=port;
        //serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);
    }

    public void RunClient()
    {
        try
        {
            int clientNumberOne, clientNumberTwo, serverNumber;
            Scanner clientScanner = new Scanner(System.in);
            Socket clientSocket = new Socket("127.0.0.1", ClientPort);
            System.out.println("Enter first integer");
            clientNumberOne = clientScanner.nextInt();
            System.out.println("Enter second integer");
            clientNumberTwo = clientScanner.nextInt();

            //Sent client numbers to server
            PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
            printStream.println(clientNumberOne);
            printStream.println(clientNumberTwo);

            //Get result from server
            Scanner serverScanner = new Scanner(clientSocket.getInputStream());
            serverNumber = serverScanner.nextInt();
            System.out.println("Multiplyig the two integers we get:");
            System.out.println(serverNumber);
            clientSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws UnknownHostException,IOException
    {
        int ClientPort=1342;
        Client2 client2 =new Client2(ClientPort);
        for(int i=0;i<5;i++)
            client2.RunClient();
    }
}

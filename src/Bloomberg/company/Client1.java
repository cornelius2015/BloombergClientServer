package Bloomberg.company;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client1
{
    private int ClientPort;
    public Client1(int port) throws IOException
    {
        ClientPort=port;
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
        Integer ClientPort = 1342;

        new Thread(() ->
        {
            try
            {
                ClientFacingServer.main(null);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

       Client1 client1 =new Client1(ClientPort);
       for(int i=0;i<5;i++)
           client1.RunClient();
    }
}

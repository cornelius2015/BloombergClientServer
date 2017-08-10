package Bloomberg.company;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client1
{
    //private ServerSocket serverSocket;
    //remember to guard against race conditions
    public Client1(int port) throws IOException
    {
        //serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);
    }

    public void RunClient()
    {
        try
        {
            int clientNumberOne, clientNumberTwo, serverNumber;
            Scanner clientScanner = new Scanner(System.in);
            Socket clientSocket = new Socket("127.0.0.1", 1342);
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
        int ClientPort = 1342;
        int FunctionServerPort = 1343;

        new Thread(() ->
        {
            try
            {
                ClientFacingServer.main(args);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

        //Thread.sleep(100);

       Client1 client1 =new Client1(1342);
       for(int i=0;i<5;i++)
           client1.RunClient();
    }
}

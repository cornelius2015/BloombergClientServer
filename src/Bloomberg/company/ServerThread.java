package Bloomberg.company;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable
{
    private Socket server=null;
    private int FunctionServerPort=0;

    public ServerThread(Socket serv,int functionServerPort)
    {
        FunctionServerPort=functionServerPort;
        server=serv;
    }

    public void run()
    {
        try
        {
            Scanner scanner;
            int clientNumberOne;
            int clientNumberTwo;
            int functionServerResult;
            scanner = new Scanner(server.getInputStream());
            clientNumberOne = scanner.nextInt();
            clientNumberTwo = scanner.nextInt();

            //Send client numbers to Function Server to be multiplied
            Socket functionServerSocket = new Socket("127.0.0.1", FunctionServerPort);
            PrintStream printStream = new PrintStream(functionServerSocket.getOutputStream());
            printStream.println(clientNumberOne);
            printStream.println(clientNumberTwo);

            //Get result back from functionServer
            Scanner serverScanner = new Scanner(functionServerSocket.getInputStream());
            functionServerResult = serverScanner.nextInt();
            //
            //Send result back to client
            //serverResult=clientNumberOne*clientNumberTwo;
            PrintStream printStream1 = new PrintStream(server.getOutputStream());
            printStream1.println(functionServerResult);
            //DataOutputStream out = new DataOutputStream(server.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

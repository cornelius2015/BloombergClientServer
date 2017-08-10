package Bloomberg.company;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class FunctionServer extends Thread
{
    private ServerSocket serverSocket=null;
    private final static Logger LOGGER = Logger.getLogger(FunctionServer.class.getName());
    //private int FunctionServerPort = 0;
    public FunctionServer(int functionServerPort) throws IOException
    {
         serverSocket = new ServerSocket(functionServerPort);
        LOGGER.setUseParentHandlers(false);
    }

    public void run()
    {
        //Connect to fuctionServer
        while(true)
        {
            try {
                int clientNumberOne, clientNumberTwo, functionServerResult;

                LOGGER.info("Waiting for clientFacingServer on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                LOGGER.info("Just connected to " + server.getLocalSocketAddress());
                Scanner scanner = new Scanner(server.getInputStream());
                clientNumberOne = scanner.nextInt();
                clientNumberTwo = scanner.nextInt();
                functionServerResult = clientNumberOne * clientNumberTwo;
                PrintStream printStream = new PrintStream(server.getOutputStream());
                printStream.println(functionServerResult);

                //DataOutputStream out = new DataOutputStream(server.getOutputStream());
                LOGGER.info("Closing connection to server: " + server.getLocalSocketAddress() + "\n");
                server.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws IOException
    {
        int FunctionServerPort=1343;
        try
        {
            Thread t = new FunctionServer(FunctionServerPort);
            t.start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

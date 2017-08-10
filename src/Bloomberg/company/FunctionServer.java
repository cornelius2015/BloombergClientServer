package Bloomberg.company;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Logger;

public class FunctionServer extends Thread
{
    private ServerSocket serverSocket=null;
    private final static Logger LOGGER = Logger.getLogger(FunctionServer.class.getName());
    public FunctionServer(int functionServerPort,boolean LoggerFlag) throws IOException
    {
        serverSocket = new ServerSocket(functionServerPort);
        serverSocket.setSoTimeout(100000);
        LOGGER.setUseParentHandlers(LoggerFlag);
    }

    public void run()
    {
        //Connect to fuctionServer
        Socket server=null;
        while(true)
        {
            try {
                int clientNumberOne, clientNumberTwo, functionServerResult;

                LOGGER.info("Waiting for clientFacingServer on port " + serverSocket.getLocalPort() + "...");
                server = serverSocket.accept();

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
            catch (SocketTimeoutException s)
            {
                LOGGER.info("Socket timed out!");
                try {
                    LOGGER.info("Closing connection to server: " + server.getLocalSocketAddress() + "\n");
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                break;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void mainStart(int FunctionServerPort,boolean LoggerFlag) throws IOException
    {

        try
        {
            Thread t = new FunctionServer(FunctionServerPort,LoggerFlag);
            t.start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

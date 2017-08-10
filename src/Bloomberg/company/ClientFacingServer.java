package Bloomberg.company;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientFacingServer extends Thread
{
    private ServerSocket serverSocket;
    private int FunctionServerPort=0;
    private Socket server=null;
    private final static Logger LOGGER = Logger.getLogger(ClientFacingServer.class.getName());

    public ClientFacingServer(int clientPort,int functionServerPort,boolean LoggerFlag) throws IOException
    {
        serverSocket = new ServerSocket(clientPort);
        FunctionServerPort=functionServerPort;
        serverSocket.setSoTimeout(100000);
        LOGGER.setUseParentHandlers(LoggerFlag);
    }

    public void StartServer()
    {

        Scanner scanner=null;

        while(true)
        {
            try
            {
                LOGGER.info("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                server = serverSocket.accept();
                new Thread(new ServerThread(server,FunctionServerPort)).start();
                LOGGER.info("Just connected to " + server.getLocalSocketAddress());
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

    public static void main(String args[]) throws IOException
    {
        Integer ClientPort = 1342;
        Integer FunctionServerPort = 1343;
        Boolean LoggerFlag=false;

        new Thread(() ->
        {
            try
            {
                ClientFacingServer clientFacingServer = new ClientFacingServer(ClientPort, FunctionServerPort,LoggerFlag);
                clientFacingServer.StartServer();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(() ->
        {
            try
            {
                FunctionServer.mainStart(FunctionServerPort,LoggerFlag);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
}

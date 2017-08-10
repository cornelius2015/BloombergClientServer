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
    //private Socket functionServerSocket
    private int FunctionServerPort=0;
    private Socket server=null;
    private final static Logger LOGGER = Logger.getLogger(ClientFacingServer.class.getName());

    public ClientFacingServer(int clientPort,int functionServerPort) throws IOException
    {
        serverSocket = new ServerSocket(clientPort);
        FunctionServerPort=functionServerPort;
        serverSocket.setSoTimeout(100000);
        LOGGER.setUseParentHandlers(false);
    }

    public void StartServer()
    {

        Scanner scanner=null;
        /*try
        {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            server = serverSocket.accept();
            System.out.println("Just connected to " + server.getLocalSocketAddress());
            //scanner = new Scanner(server.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/

        while(true)
        {
            try
            {
                //int clientFirstNumber, clientNumberTwo, functionServerResult;
                LOGGER.info("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                server = serverSocket.accept();
                //new ServerThread(server,FunctionServerPort);
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
        //int port = Integer.parseInt(args[0]);
        int ClientPort = 1342;
        int FunctionServerPort = 1343;
        //ClientFacingServer clientFacingServer = new ClientFacingServer(ClientPort,FunctionServerPort);
        //clientFacingServer.StartServer();

        new Thread(() ->
        {
            try
            {
                ClientFacingServer clientFacingServer = new ClientFacingServer(ClientPort,FunctionServerPort);
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
                FunctionServer.main(args);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
}

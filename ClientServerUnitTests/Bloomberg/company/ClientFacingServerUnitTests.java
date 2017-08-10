package Bloomberg.company;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ClientFacingServerUnitTests extends TestCase
{
    private int clientTwoFirstInt=6;
    private int clientTwoSecondInt=7;

    public void setUp() throws Exception {

        super.setUp();
        String args[] = null;
        //new Thread.Thread(System.out.println(1)).start();
        //new Thread(() ->
        //{
        try {
            ClientFacingServer.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}).start();

        //Thread.sleep(100);
        //FunctionServer.main(args);
            /*new Thread(() ->
            {
                try
                {
                    FunctionServer.main(args);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }).start();*/
        //Thread.sleep(100);
    }

    public void tearDown() throws Exception
    {
    }

    private int callClient(int clientFirstInt,int clientSecondInt) throws IOException
    {
        int serverReturnNumber;
        Socket clientSocket = new Socket("127.0.0.1", 1342);

        //Sent the numbers '3' and '4' numbers to server and expecting to get back 3 * 4 =12 as the result.
        PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
        printStream.println(clientFirstInt);
        printStream.println(clientSecondInt);

        //Get result from server
        Scanner serverScanner = new Scanner(clientSocket.getInputStream());
        serverReturnNumber = serverScanner.nextInt();
        clientSocket.close();
        return serverReturnNumber;
    }
    public void testStartServer() throws Exception
    {
        Assert.assertEquals(callClient(3, 4),12);
        Assert.assertEquals(callClient(5, 5),25);
        Assert.assertEquals(callClient(5, 6),30);
    }


    public void testStartServerForConcurrency() throws Exception
    {

        final int[] result = new int[3];
        final CountDownLatch latch1 = new CountDownLatch(1);
        new Thread(() ->
        {
            try
            {
                result[0]=callClient(5, 5);
                latch1.countDown();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();


        final CountDownLatch latch2 = new CountDownLatch(1);
        new Thread(() ->
        {
            try
            {
                result[1]=callClient(6, 6);
                latch2.countDown();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

        final CountDownLatch latch3 = new CountDownLatch(1);
        new Thread(() ->
        {
            try
            {
                result[2]=callClient(7, 7);
                latch3.countDown();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

        //Wait for threads to complete and then assert that the results are correct
        latch1.await();
        Assert.assertEquals(result[0],25);
        latch2.await();
        Assert.assertEquals(result[1],36);
        latch3.await();
        Assert.assertEquals(result[2],49);

    }
}
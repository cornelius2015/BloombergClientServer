# BloombergClientServer
The application was developed in Java using IntelliJ.
To start the application just run "Client1". This will automatically start Client1, the ClientFacingServer and the FunctionServer. You will then be prompted to enter two integers and the result from the multiplication of the two integers will appear e.g:

“Enter first integer:

5

Enter second integer: 

5

Multiplying the two integers we get:

25”

By running Client2 one can test concurrency.

The ClientFacingServer can also be started on its own and to test it you can run Client2.
The unit tests are in the file “ClientFacingServerUnitTests.java”, there are tests for concurrency and  tests to see if the FunctionServer returns the correct results when the two integers are multiplied.    
The LoggerFlag for the server can be set in the “main” function of the “ClientFacingServer” file.
The average test code coverage for the files ClientFacingServer,FunctionServer and ServerThread is about 81%.

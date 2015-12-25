package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {

    private static final int BUFSIZE = 32; // Size (in bytes) of I/O buffer
    private Socket clntSock; // Socket connect to client
    private Logger logger; // Server logger

    public EchoProtocol(Socket clntSock, Logger logger) {
        this.clntSock = clntSock;
        this.logger = logger;
    }

    public static void handleEchoClient(Socket clntSock, Logger logger) {
        try {
            // Get the input and output I/O streams from socket
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            int recvMsgSize; // Size of received message
            byte[] receiveBuf = new byte[BUFSIZE]; // Receive buffer
            int totalBytesEchoed = 0; // Bytes received from client
            byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer

            // Receive until client closes connection, indicated by -1
            while ((recvMsgSize = in.read(echoBuffer)) != -1) {
                out.write(echoBuffer, 0, recvMsgSize);
                totalBytesEchoed += recvMsgSize;
            }

            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            logger.info("Client " + clientAddress + ", echoed "+ totalBytesEchoed + " bytes.");

            // Receive until client closes connection, indicated by -1 return
            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf, 0, recvMsgSize);
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Exception in echo protocol", ex);
        } finally {
            try {
                clntSock.close();
            } catch (IOException e) {
            }
        }
    }

    public void run() {
        handleEchoClient(clntSock, logger);
    }
}

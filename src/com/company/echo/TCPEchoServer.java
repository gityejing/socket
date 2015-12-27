package com.company.echo;

import com.company.echo.EchoProtocol;

import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream
import java.util.logging.Logger;

public class TCPEchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket servSock = new ServerSocket(12345);
        while (true) {
            new Thread(new EchoProtocol(servSock.accept(), Logger.getLogger("server"))).start();
        }
    }
}
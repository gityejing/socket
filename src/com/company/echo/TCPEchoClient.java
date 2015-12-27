package com.company.echo;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TCPEchoClient {

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 100 ; i++) {
            InetAddress server = InetAddress.getLocalHost();
            byte[] data = (i+":yejing").getBytes();

            // Create socket that is connected to server on specified port
            Socket socket = new Socket(server, 12345);
            System.out.println("Connected to server...sending echo string");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            out.write(data); // Send the encoded string to the server

            // Receive the same string back from the server
            int totalBytesRcvd = 0; // Total bytes received so far
            int bytesRcvd; // Bytes received in last read
            while (totalBytesRcvd < data.length) {
                if ((bytesRcvd = in.read(data, totalBytesRcvd,data.length - totalBytesRcvd)) == -1)
                    throw new SocketException("Connection closed prematurely");
                totalBytesRcvd += bytesRcvd;
            } // data array is full

            System.out.println("Received: " + new String(data));
            socket.close(); // Close the socket and its streams
        }
    }
}

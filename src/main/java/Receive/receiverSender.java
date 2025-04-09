package Receive;

import Send.Sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class receiverSender {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String ip = "239.0.1.2";
    InetAddress iadr = InetAddress.getByName(ip);
    int port = 20480;
    MulticastSocket socket = new MulticastSocket();
    DatagramPacket packet;
    byte[] data;
    String message;
    public receiverSender() throws IOException {
        while (true) {
            message = in.readLine();
            if (message.equals("bye")) System.exit(0);
            data = message.getBytes();
            packet = new DatagramPacket(data, data.length, iadr, port);
            socket.send(packet);
        }
    }

    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        Sender sender = new Sender();
    }
}

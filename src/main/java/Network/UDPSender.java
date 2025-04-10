package Network;

import GUI.readPanels.chatPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UDPSender {
    private static final String IP = "239.0.1.2";
    private static final int PORT = 20480;

    private MulticastSocket socket;
    private InetAddress iadr;
    private BufferedReader in;
    private String message;
    private volatile boolean running = true;
    private chatPanel chatPanel;
    Pattern pattern = Pattern.compile("^login-(\\S+)");
    ArrayList<String> allUsers = new ArrayList<String>();

    public UDPSender(chatPanel chatPanel) throws IOException {
        this.chatPanel = chatPanel;
        socket = new MulticastSocket(PORT);
        iadr = InetAddress.getByName(IP);
        in = new BufferedReader(new InputStreamReader(System.in));

        new Thread(new Receiver(socket)).start();

    }

    public void sendMessage(String message) throws IOException {
        if (message != null && !message.isEmpty()) {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, iadr, PORT);
            socket.send(packet);
        }
    }

    public void stop() {
        running = false;
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        chatPanel chatPanel = new chatPanel();
        UDPSender udpSender = new UDPSender(chatPanel);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            udpSender.stop();
            System.out.println("UDP connection closed.");
        }));
    }

    public void sendAllUsers() throws IOException {
        StringBuilder message = new StringBuilder("current-users:");
        for (int i = 0; i < allUsers.size(); i++) {
            message.append(allUsers.get(i));
            if (i < allUsers.size() - 1) {
                message.append(",");
            }
        }
        sendMessage(message.toString());
    }
    private class Receiver implements Runnable {
        private MulticastSocket socket;

        public Receiver(MulticastSocket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                NetworkInterface nwi = NetworkInterface.getByInetAddress(iadr);
                InetSocketAddress isa = new InetSocketAddress(iadr, PORT);
                socket.joinGroup(isa, nwi);

                while (true) {
                    byte[] buffer = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String receivedMessage = new String(packet.getData(), 0, packet.getLength());

                    if (receivedMessage.startsWith("login-")) {
                        Matcher matcher = pattern.matcher(receivedMessage);
                        if (matcher.find()) {
                            String username = matcher.group(1);
                            if (!allUsers.contains(username)) {
                                allUsers.add(username);
                                sendAllUsers();
                            }
                        }
                    } else if (receivedMessage.startsWith("logoff-")) {
                        String username = receivedMessage.split("-")[1];
                        if (allUsers.remove(username)) {
                            sendAllUsers();
                        }
                    }else if (receivedMessage.equals("request-users")) {
                        sendAllUsers();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.leaveGroup(iadr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

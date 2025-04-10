package Network;

import GUI.readPanels.chatPanel;
import User.User;
import GUI.readPanels.usersPanel;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPReceiver extends Thread {
    private static final String IP = "239.0.1.2";
    private static final int PORT = 20480;

    private MulticastSocket socket;
    private InetAddress iadr;
    private BufferedReader in;
    private String message;
    private volatile boolean running = true;
    private chatPanel chatPanel;
    private usersPanel usersPanel;
    User currentUser;

    public UDPReceiver(chatPanel chatPanel, User user, usersPanel usersPanel) throws IOException {
        this.chatPanel = chatPanel;
        socket = new MulticastSocket(PORT);
        iadr = InetAddress.getByName(IP);
        in = new BufferedReader(new InputStreamReader(System.in));
        currentUser = user;
        this.usersPanel = usersPanel;
        new Thread(new Receiver(socket)).start();

        this.sendMessage("login-" + user.getName());
        try {
            Thread.sleep(500);
            sendMessage("request-users");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (running) {
                    message = in.readLine();
                    sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sendMessage("logoff-" + currentUser.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public void sendMessage(String message) throws IOException {
        if (message != null && !message.isEmpty()) {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, iadr, PORT);
            socket.send(packet);
        }
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
                    System.out.println("Received message: " + receivedMessage);
                    if(receivedMessage.contains("login")){
                        String username = receivedMessage.split("-")[1];
                        User newUser = new User(username);
                        usersPanel.addUser(newUser);
                        continue;
                    }

                    if (receivedMessage.startsWith("current-users:")) {
                        String users = receivedMessage.split("current-users:")[1];
                        String[] restOfUsers = users.split(",");

                        SwingUtilities.invokeLater(() -> {
                            usersPanel.removeAll();
                            for (String name : restOfUsers) {
                                if (!name.trim().isEmpty()) {
                                    usersPanel.addUser(new User(name));
                                }
                            }
                            usersPanel.repaint();
                            usersPanel.revalidate();
                        });
                        continue;
                    }

                    SwingUtilities.invokeLater(() -> {
                        chatPanel.addMessage(receivedMessage);
                    });
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

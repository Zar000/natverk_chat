package GUI;

import GUI.Buttons.exitButton;
import GUI.Buttons.sendButton;
import GUI.readPanels.chatPanel;
import GUI.readPanels.usersPanel;
import GUI.writePanels.messagePanel;
import Network.UDPReceiver;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Chat extends JFrame {
    exitButton exitButton = new exitButton();
    chatPanel chatPanel = new chatPanel();
    usersPanel usersPanel = new usersPanel();
    messagePanel messagePanel = new messagePanel();
    UDPReceiver udpReceiver;
    User newUser;

    public Chat(String userName) throws IOException {
        this.setLayout(new BorderLayout());
        this.setTitle("Chat Room");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        newUser = new User(userName);
        udpReceiver = new UDPReceiver(chatPanel, newUser, usersPanel);
        messagePanel.setPreferredSize(new Dimension(600, 100));
        chatPanel.setPreferredSize(new Dimension(200, 400));
        usersPanel.setPreferredSize(new Dimension(150, 400));

        this.add(exitButton, BorderLayout.NORTH);
        this.add(messagePanel, BorderLayout.SOUTH);
        this.add(chatPanel, BorderLayout.WEST);
        this.add(usersPanel.getScrollPane(), BorderLayout.EAST);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        messagePanel.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendMessageToUdp();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        messagePanel.getTextArea().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        sendMessageToUdp();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        this.setVisible(true);
    }

    public void sendMessageToUdp() throws IOException {
        JTextArea textArea = (JTextArea) messagePanel.getMessageArea().getViewport().getView();
        udpReceiver.sendMessage(newUser.getName() + ": " + textArea.getText());
        messagePanel.clearMessage();
        messagePanel.getTextArea().setCaretPosition(0);

    }


    public sendButton getSendButton() {
        return messagePanel.getSendButton();
    }

    public exitButton getExitButton() {
        return exitButton;
    }

    public chatPanel getChatPanel() {
        return chatPanel;
    }

    public usersPanel getUsersPanel() {
        return usersPanel;
    }

    public messagePanel getMessagePanel() {
        return messagePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String username = "User";
                    new Chat(username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


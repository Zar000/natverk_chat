package GUI;

import GUI.Buttons.exitButton;
import GUI.readPanels.chatPanel;
import GUI.readPanels.usersPanel;
import GUI.writePanels.messagePanel;

import javax.swing.*;
import java.awt.*;

public class Chat extends JFrame {
    exitButton exitButton = new exitButton();
    chatPanel chatPanel = new chatPanel();
    usersPanel usersPanel = new usersPanel();
    messagePanel messagePanel = new messagePanel();

    public Chat(String userName){
        this.setLayout(new BorderLayout());
        this.setTitle("Chat Room");
        this.setDefaultCloseOperation(0);
        this.setVisible(true);

        this.add(exitButton, BorderLayout.NORTH);
        this.add(messagePanel, BorderLayout.SOUTH);
        this.add(chatPanel, BorderLayout.WEST);
        this.add(usersPanel, BorderLayout.EAST);
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
    }
    public exitButton getExitButton(){
        return exitButton;
    }

    public chatPanel getChatPanel(){
        return chatPanel;
    }

    public usersPanel getUsersPanel(){
        return usersPanel;
    }

    public messagePanel getMessagePanel(){
        return messagePanel;
    }

}

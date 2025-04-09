package GUI.writePanels;

import GUI.Buttons.sendButton;

import javax.swing.*;
import java.awt.*;

public class messagePanel extends JPanel {
    JTextArea textArea = new JTextArea(); // content goes here
    JScrollPane messageArea = new JScrollPane(textArea);
    sendButton sendButton = new sendButton();

    public messagePanel() {
        this.setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messageArea, sendButton);

        splitPane.setResizeWeight(1);
        splitPane.setDividerSize(4);
        splitPane.setOneTouchExpandable(true);

        this.add(splitPane, BorderLayout.CENTER);
        this.add(sendButton, BorderLayout.EAST);
    }

    public JScrollPane getMessageArea() {
        return messageArea;
    }

    public sendButton getSendButton() {
        return sendButton;
    }
}

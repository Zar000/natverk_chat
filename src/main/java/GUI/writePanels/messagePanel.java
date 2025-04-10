package GUI.writePanels;

import GUI.Buttons.sendButton;

import javax.swing.*;
import java.awt.*;

public class messagePanel extends JPanel {
    public JTextArea textArea = new JTextArea();
    JScrollPane messageArea = new JScrollPane(textArea);
    sendButton sendButton = new sendButton();

    public messagePanel() {
        this.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messageArea, sendButton);
        splitPane.setResizeWeight(0.9);
        splitPane.setDividerSize(4);
        splitPane.setOneTouchExpandable(true);

        this.add(splitPane, BorderLayout.CENTER);
    }


    public void clearMessage(){
        textArea.setText("");
        textArea.revalidate();
        textArea.repaint();
    }
    public JScrollPane getMessageArea() { return messageArea; }

    public sendButton getSendButton() { return sendButton; }
    public JTextArea getTextArea() { return textArea; }
}

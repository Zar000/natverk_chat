package GUI.readPanels;

import javax.swing.*;
import java.awt.*;

public class chatPanel extends JTextArea {

    JScrollPane scrollPane = new JScrollPane(this);

    public chatPanel() {
        this.setEditable(false);
        this.setLineWrap(true);
        this.setCaretColor(Color.WHITE);
        this.setVisible(true);
    }

    public void addMessage(String message) {
        if (message != null) {
            this.append(message + "\n");  // Append the formatted message
            this.setCaretPosition(this.getDocument().getLength());  // Ensure the scroll is at the bottom
            this.revalidate();  // Revalidate the component
            this.repaint();  // Repaint the component
        }
    }
}

package GUI.readPanels;

import User.Message;

import javax.swing.*;
import java.awt.*;

public class chatPanel extends JTextArea {

    JScrollPane scrollPane = new JScrollPane(this);
    public chatPanel(){
        this.setEditable(false);
        this.setLineWrap(true);
        this.setCaretColor(Color.LIGHT_GRAY);
    }

    public void addMessage(Message message){
        String tempMsg;
        if(message.getUser().isOnline()){
            tempMsg = message.getUser().getName() + " " + message.getDate() + ": " + message.getMessage() + "\n";
        }else {
            tempMsg = message.getMessage();
        }
        this.append(tempMsg);
        this.setCaretPosition(this.getDocument().getLength());
        this.revalidate();
        this.repaint();
    }

}

package GUI.writePanels;

import javax.swing.*;

public class UsernameInput {

    private String username;

    public UsernameInput() {
        this.username = JOptionPane.showInputDialog(null, "Enter your username:", "Username", JOptionPane.QUESTION_MESSAGE);
        while (username == null || username.trim().isEmpty()) {
            this.username = JOptionPane.showInputDialog(null, "Please enter a valid username:", "Username", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public String getUsername() {
        return username;
    }
}

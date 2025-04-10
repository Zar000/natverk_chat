package GUI.readPanels;

import User.Users;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class usersPanel extends JPanel {
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private ArrayList<String> users = new ArrayList<>();

    public usersPanel() {
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setCaretColor(Color.WHITE);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(150, 300));

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateUsers(Users users) {
        this.users.clear();
        for (User user : users.getUsers()) {
            this.users.add(user.getName());
        }
        draw();
    }

    public void addUser(User user) {
        users.add(user.getName());
        draw();
    }

    public void removeUser(User user) {
        users.remove(user.getName());
        draw();
    }

    public void removeAll(){
        users.clear();
        this.textArea.setText("");
    }

    private void draw() {
        StringBuilder sb = new StringBuilder();
        sb.append("Users online:\n");
        for (String user : users) {
            sb.append(user).append("\n");
        }
        textArea.setText(sb.toString());
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}

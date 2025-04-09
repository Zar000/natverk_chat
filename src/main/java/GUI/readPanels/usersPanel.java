package GUI.readPanels;

import User.Users;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class usersPanel extends JTextArea {
    JScrollPane scrollPane = new JScrollPane(this);
    ArrayList<String> users = new ArrayList<>();

    public usersPanel(){
        this.setEditable(false);
        this.setLineWrap(true);
        this.setCaretColor(Color.GRAY);
    }
    public void draw(){
        this.setText("Users online:\n");

        for(String user: users) {
            this.append(user + "\n");
        }

        this.setCaretPosition(this.getDocument().getLength());
        this.revalidate();
        this.repaint();
    }
    public void updateUsers(Users users){
        this.setText("");
        this.setText("Users online:\n");
        this.users.clear();
        for(User user : users.getUsers()){
            this.users.add(user.getName());
        }
        this.draw();
    }

    public void addUser(User user){
        users.add(user.getName());
        this.draw();
    }
    public void removeUser(User user){
        users.remove(user.getName());
        this.draw();
    }

//    public ArrayList<String> getUsers{
//        return users;
//    }

}

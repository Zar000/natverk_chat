import GUI.Chat;
import GUI.writePanels.UsernameInput;
import User.User;

import javax.swing.*;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String username = null;
                    UsernameInput usernameInput = new UsernameInput();
                    username = usernameInput.getUsername();
                    Chat chat = new Chat(username);
                    User user = new User(username);
                    chat.getUsersPanel().addUser(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

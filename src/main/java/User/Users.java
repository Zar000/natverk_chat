package User;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> users = new ArrayList<>();

    public Users() {}

    public void addUser(User user) {
        if(user != null) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        if(user != null) {
            users.remove(user);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}

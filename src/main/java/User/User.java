package User;

import java.util.Objects;

public class User {
    private String name;
    private boolean isOnline;

    public User(String name){
        this.name = name;
        this.isOnline = true;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public boolean isOnline(){
        return isOnline;
    }
    public void setOnline(boolean online){
        isOnline = online;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(this.name, user.name);
    }
}

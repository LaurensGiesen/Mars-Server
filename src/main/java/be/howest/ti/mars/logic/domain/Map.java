package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;

public class Map {
    private List<User> users;

    public Map() {
        this.users = new LinkedList<>();
    }

    public List<User> getUsers() {
        return users;
    }
}

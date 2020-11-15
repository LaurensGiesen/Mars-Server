package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.User;

import java.util.List;

public interface UsersRepository {
    void addUser(User user);
    List<User> getUsers();
    List<User> findUser(User user); //maybe String name
}

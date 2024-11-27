package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
//        userService.createUsersTable();
//        userService.cleanUsersTable();
//        userService.saveUser("AZAT","KUSH", (byte) 24);
//        userService.saveUser("Ivan","Ivanov", (byte) 35);
//        userService.cleanUsersTable();
//        userService.removeUserById(2);
        List<User> users = userService.getAllUsers();
       users.forEach(System.out::println);
//       userService.dropUsersTable();

    }
}

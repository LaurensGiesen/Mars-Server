package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.User;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
MBL: this is only a starter class to use a H2 database.
To make this class useful, please complete it with the topics seen in the module OOA & SD
- Make sure the conf/config.json properties are correct.
- The h2 web console is available at http://localhost:9000
- The h2 database file is located at ~/mars-db
- Hint:
  - Mocking this repository is not needed. Create database creating and population script in plain SQL.
    Use the @Before or @Before each (depending on the type of test) to quickly setup a fully populated db.
 */
public class MarsRepository {

    private static final UsersRepository database = new DatabaseUsersRepository();

    private static final MarsRepository INSTANCE = new MarsRepository();
    private Server dbWebConsole;
    private String username; //= "group-14"; // = "group-14"
    private String password;//= "t3sfe1k3nUe"; // = "t3sfe1k3nUe"
    private String url;  //= "jdbc:h2:~/mars-db"; //jdbc:h2:/opt/group-14/db-14 //jdbc:h2:~/mars-db

    private MarsRepository() { }

    public static MarsRepository getInstance() {
        return INSTANCE;
    }

    public void cleanUp() {
        dbWebConsole.stop();
    }

    public static void configure(String url, String username, String password, int console)
            throws SQLException {
        INSTANCE.username = username;
        INSTANCE.password = password;
        INSTANCE.url = url;
        INSTANCE.dbWebConsole = Server.createWebServer(
                "-ifNotExists",
                "-webPort", String.valueOf(console)).start();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getInstance().url, getInstance().username, getInstance().password);
    }

    public Boolean createUser(User user){
        return database.addUser(user);
    }

}

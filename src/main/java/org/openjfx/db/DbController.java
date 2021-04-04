package org.openjfx.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.models.Subject;
import org.openjfx.models.Teacher;
import org.openjfx.models.TimeTable;
import org.openjfx.models.User;


public class DbController {

    private static DbController dbController;
    
    //timetable is the name of the database
    private final String DB = "jdbc:mysql://localhost/timetable";
    
    //username for the mysql db
    private final String USERNAME = "root";
    
    //password for the mysql db
    private final String PASSWORD = "root";

    private Connection connection;
    private PreparedStatement preparedStatement;    

    public DbController() {

        try {
            //Register JDBC driver            
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            //Open a connection
            connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("Problem in establishing connection to database");
            e.printStackTrace();
        }

    }

    //use this method to get instance of this db class anywhere in the project
    public static DbController getInstance() {
        if (dbController == null) {
            dbController = new DbController();
        }
        return dbController;
    }

    public boolean addUser(User user) {
        try {
            //create query
            String query = "insert into users values(?,?,?)";
            
            //create prepared statement
            preparedStatement = connection.prepareStatement(query);
            
            //add values in the query
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            
            //execute the query
            preparedStatement.executeUpdate();

            System.out.println("Added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //return true if the db operation was successful
        return true;
    }

    public ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            //create query
            String query = "SELECT * FROM users";
            
            //create prepared statement
            preparedStatement = connection.prepareStatement(query);

            //create a resultset to stoer query result
            ResultSet resultSet = preparedStatement.executeQuery();

            //iterate through the result set
            while (resultSet.next()) {
                //get the values from result set
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                
                //create the user object
                User user = new User(username, password, role);
                
                //add the object to the list
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return the list
        return userList;
    }

    public boolean deleteUser(String username) {
        try {
            //create query
            String query = "delete from users where username=?";
            
            //create prepared statement
            preparedStatement = connection.prepareStatement(query);
            
            //add values in the query
            preparedStatement.setString(1, username);
            
            //execute the delete command
            preparedStatement.executeUpdate();

            System.out.println("Delete from the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean updateUser(String username, String password) {
        try {
            String query = "UPDATE users SET password = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);

            //add values in the query
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            
            //execute the update command
            preparedStatement.executeUpdate();

            System.out.println("Updated in the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean addSubject(Subject subject) {
        try {
            String query = "insert into subjects values(?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setString(2, subject.getColor());            
            preparedStatement.executeUpdate();

            System.out.println("Added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public ObservableList<Subject> getAllSubjects() {
        ObservableList<Subject> subjectList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM subjects";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //System.out.println("Name - " + resultSet.getString("First_name"));
                String name = resultSet.getString("name");
                String color = resultSet.getString("color");                
                Subject subject = new Subject(name, color);
                subjectList.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectList;
    }

    public boolean deleteSubject(String name) {
        try {
            String query = "delete from subjects where name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            System.out.println("Delete from the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean addTeacher(Teacher teacher) {
        try {
            String query = "insert into teachers values(?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getColor());            
            preparedStatement.setString(3, teacher.getTimetable());            
            preparedStatement.executeUpdate();

            System.out.println("Added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public ObservableList<Teacher> getAllTeachers() {
        ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM teachers";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //System.out.println("Name - " + resultSet.getString("First_name"));
                String name = resultSet.getString("name");
                String color = resultSet.getString("color");
                String timetable = resultSet.getString("timetable");
                Teacher teacher = new Teacher(name, color, timetable);
                teacherList.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacherList;
    }

    public boolean deleteTeacher(String name) {
        try {
            String query = "delete from teachers where name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            System.out.println("Delete from the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean updateTeacherTimeTable(String name, String timetable) {
        try {
            String query = "UPDATE teachers SET timetable = ? WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, timetable);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();

            System.out.println("Updated in the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean addTimeTable(TimeTable timeTable) {
        try {
            String query = "insert into timetables values(?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, timeTable.getName());
            preparedStatement.setString(2, timeTable.getData());            
            preparedStatement.executeUpdate();

            System.out.println("Added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public ObservableList<TimeTable> getAllTimetables() {
        ObservableList<TimeTable> timetableList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM timetables";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //System.out.println("Name - " + resultSet.getString("First_name"));
                String name = resultSet.getString("name");
                String data = resultSet.getString("data");                
                TimeTable timeTable = new TimeTable(name, data);
                timetableList.add(timeTable);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timetableList;
    }

    public boolean deleteTimetable(String name) {
        try {
            String query = "delete from timetables where name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            System.out.println("Delete from the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean updateTimetable(String name, String data) {
        try {
            String query = "UPDATE timetables SET data = ? WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, data);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();

            System.out.println("Updated in the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}

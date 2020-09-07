import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Main {
    private static String password = "";

    public final static String url = "jdbc:sqlserver://test-my-server.database.windows.net:1433;" +
            "database=Test;" +
            "user=admin123@test-my-server;" +
            "password= passwordHere" +
            "encrypt=true;" +
            "trustServerCertificate=false;" +
            "hostNameInCertificate=*.database.windows.net;" +
            "loginTimeout=30;";

    private final static String createTable = "CREATE TABLE tableName (" +
            "name VARCHAR(255)," +
            "description VARCHAR(255)," +
            "date VARCHAR(255)," +
            "done BIT )";

    Connection connection = null;

    private static final int ADD_NEW_TASK = 1;
    private static final int DELETE_TASK = 2;
    private static final int SHOW_ALL_TASKS = 3;
    private static final int SHOW_ALL_NOT_COMPLETED = 4;
    private static final int SHOW_ALL_COMPLETED = 5;
    private static final int COMPLETE_A_TASK = 6;
    private static final int INCOMPLETE_A_TASK = 7;
    private static final int SHOW_TASKS_ACCORDING_TO_PRIORITY = 8;
    private static final int EXIT = 0;

    public static void main(String[] args) throws SQLException, ParseException{
        TextUI ui = new TextUI();
        boolean online = true;

        while(online){
            ui.mainMenu();

            Scanner scanner = new Scanner(System.in);
            int userInput = scanner.nextInt();

            switch (userInput){
                case ADD_NEW_TASK:
                    ui.addNewTask();
                    break;

                case DELETE_TASK:
                    ui.deleteTask();
                    break;

                case SHOW_ALL_TASKS:
                    ui.showAllTasks();
                    break;

                case SHOW_ALL_NOT_COMPLETED:
                    ui.showAllNotCompleted();
                    break;

                case SHOW_ALL_COMPLETED:
                    ui.showAllCompleted();
                    break;

                case COMPLETE_A_TASK:
                    ui.completeTask();
                    break;

                case INCOMPLETE_A_TASK:
                    ui.incompleteTask();
                    break;

                case SHOW_TASKS_ACCORDING_TO_PRIORITY:
                    ui.showTasksAccordingToPriority();
                    break;

                case EXIT:
                    online = false;
                    break;

                default:
                    System.out.println("Invalid input");
            }

        }
    }


    public static void createTable(){
        Connection conn = null;
        Statement statement = null;

        try{
            conn = DriverManager.getConnection(url);
            statement = conn.createStatement();

            statement.executeUpdate(createTable);

            System.out.println("Table Created!");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if(statement != null){
                    statement.close();
                } if(conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean insertData(Task task) throws SQLException {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement insert = connection.prepareStatement("INSERT INTO tableName " +
                    "(name, description, date, done) VALUES (?, ?, ?, ?);");

            insert.setString(1, task.getTaskName());
            insert.setString(2, task.getTaskDescription());
            insert.setString(3, task.getDueDate().toString());
            insert.setBoolean(4, task.isComplete());
            if(insert.executeUpdate() > 0){
                return true;
            }

            return false;
    }

    public boolean deleteData(Task task) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tableName " +
                                                                    "WHERE name = '" + task.getTaskName() + "'");
        boolean deleted = statement.execute();

        return deleted;
    }

    public Task getTask(String taskName) throws SQLException, ParseException {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement statement = connection.prepareStatement("SELECT name, description, date, done FROM tableName " +
                "WHERE name = '" + taskName + "';");

        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.next()){
            System.out.println("NO DATA");
            return null;
        }

        Task task = new Task();
        task.setTaskName(resultSet.getString("name"));
        task.setTaskDescription(resultSet.getString("description"));
        String date = resultSet.getString("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        Date newDate = dateFormat.parse(date);

        task.setDueDate(newDate.toString());
        task.setComplete(resultSet.getBoolean("done"));

        return task;
    }

    public void completeATask(String taskName) throws SQLException {
        String query = "UPDATE tableName SET done = 'true' WHERE name = '" + taskName +  "';";
        connection = DriverManager.getConnection(url);

        PreparedStatement update = connection.prepareStatement(query);
        update.execute();
    }

    public void IncompleteATask(String taskName) throws SQLException {
        String query = "UPDATE tableName SET done = 'false' WHERE name = '" + taskName +  "';";
        connection = DriverManager.getConnection(url);

        PreparedStatement update = connection.prepareStatement(query);
        update.execute();
    }
}

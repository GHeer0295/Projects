import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class TextUI {
    private List<Task> allTasks = new ArrayList<>();
    private Main main = new Main();

    public TextUI() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        String password = scanner.next();
        allTasks = getData(password);
    }
    public boolean authenticate(String pass){

    }
    public void mainMenu(){
        System.out.print("**********MAIN MENU*************\n");
        System.out.println("1 : Add new Task");
        System.out.println("2 : Delete a Task");
        System.out.println("3 : Show all Tasks");
        System.out.println("4 : Show all task(s) to be completed");
        System.out.println("5 : Show all completed task(s)");
        System.out.println("6 : complete a task");
        System.out.println("7 : Incomplete a task");
        System.out.println("8 : Show Tasks according to deadline");
        System.out.print("> ");
    }

    public void addNewTask() throws ParseException, SQLException {
        Task newTask = new Task();

        System.out.println("Add new task :");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Task name : ");
        String taskName = scanner.next();
        newTask.setTaskName(taskName);

        System.out.print("\nEnter Task description :");
        String taskDescription = scanner.next();
        taskDescription += scanner.nextLine();
        newTask.setTaskDescription(taskDescription);

        System.out.print("\nEnter due date in dd/MM/yyyy format : ");
        String dateString = scanner.next();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(dateString);
        newTask.setDueDate(date.toString());

        boolean added = addTask(newTask);

        if (added){
            System.out.println("Data added!");
            updateData();
        } else{
            System.err.println("Data could not be added!");
        }

    }

    public void deleteTask() throws SQLException, ParseException {
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        Task taskToBeDeleted = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a task to delete : ");

        for(Task task : allTasks){
            System.out.println(task.getTaskName());
        }
        System.out.print("> ");
        String toDelete = scanner.next();

        taskToBeDeleted = main.getTask(toDelete);
        boolean deleting = false;

        if(taskToBeDeleted == null){
            System.err.println("Error : Task not Found!");
        }else{
            deleting = true;
        }

        while(deleting){
            System.out.println("Deleting task " + toDelete + "..." );
            deleting = main.deleteData(taskToBeDeleted);
            if(!deleting){
                System.out.println("Task " + toDelete + " deleted!");
            } else{
                System.err.println("Error : Task " + toDelete + " could not be deleted!");
            }
        }

        updateData();
    }
    public List getData(String password) throws SQLException {
        Connection connection = DriverManager.getConnection(new Main().url);

        PreparedStatement read = connection.prepareStatement("SELECT * FROM tableName");
        ResultSet resultSet = read.executeQuery();

        while(resultSet.next()){
            Task task = new Task();
            task.setTaskName(resultSet.getString("name"));
            task.setTaskDescription(resultSet.getString("description"));

            task.setDueDate(resultSet.getString("date"));
            task.setComplete(resultSet.getBoolean("done"));

            allTasks.add(task);
        }

        return allTasks;
    }

    public void showAllTasks(){
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        for(Task task : allTasks){
            System.out.println(task.toString());
        }
        System.out.println();
    }

    public void showAllCompleted(){
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        for(Task task : allTasks){
            if(task.isComplete()){
                System.out.println(task.toString());
            }
        }
        System.out.println();
    }

    public void showAllNotCompleted(){
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        for(Task task : allTasks){
            if(!task.isComplete()){
                System.out.println(task.toString());
            }
        }
        System.out.println();
    }

    public void updateData() throws SQLException{
        allTasks.clear();

        allTasks = getData();
        Collections.sort(allTasks);
    }

    public void completeTask() throws SQLException{
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a task : ");
        showAllNotCompleted();

        String toComplete = scanner.next();

        main.completeATask(toComplete);
        updateData();

        System.out.println("Task : " + toComplete + "is marked as complete!");
    }

    public void incompleteTask() throws SQLException{
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a task : ");
        showAllNotCompleted();

        String toComplete = scanner.next();

        main.IncompleteATask(toComplete);
        updateData();

        System.out.println("Task : " + toComplete + "is marked as incomplete!");
    }

    public boolean addTask(Task newTask) throws SQLException {
        boolean adding = false;
        int numberOfTries = 1;

        System.out.println("Adding data....");

        while(!adding) {
            if (main.insertData(newTask)) {
                adding = true;
                break;
            }  else if(numberOfTries  > 1 && numberOfTries < 3){
                System.out.println("Trying to add task...");
                numberOfTries++;
            } else{
                break;
            }
        }

        return adding;
    }

    public void showTasksAccordingToPriority(){
        if(allTasks == null || allTasks.size() == 0){
            System.out.println("No Tasks to show!");
            return;
        }

        Collections.sort(allTasks);

        for(Task task : allTasks){
            System.out.println(task.getDueDate());
        }
    }
}

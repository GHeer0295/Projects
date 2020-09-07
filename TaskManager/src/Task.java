import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task implements Comparable<Task>{
    private String taskName;
    private String taskDescription;
    private boolean isComplete;
    private String dueDate;

    public Task(){

    }

    public Task(String taskName, String taskDescription, Date dueDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.isComplete = false;
        this.dueDate = dueDate.toString();
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", isComplete=" + isComplete +
                ", dueDate='" + dueDate + '\'' +
                '}';
    }

    @Override
    public int compareTo(Task task) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date thisDate = null;
        Date newDate = null;

        try {
            thisDate =  dateFormat.parse(this.dueDate);
            newDate = dateFormat.parse(task.getDueDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return thisDate.compareTo(newDate);
    }
}

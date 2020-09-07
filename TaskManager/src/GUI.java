//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.*;
//
//public class GUI implements ActionListener {
//
//    private JLabel label;
//    private JFrame frame;
//    private JPanel panel;
//    private JButton add;
//    private JButton delete;
//    private JButton showAll;
//    private JButton showCompleted;
//    private JButton showNotCompleted;
//
//    public GUI() {
//        frame = new JFrame();
//        panel = new JPanel();
//
//        add = new JButton("Add new Task");
//        add.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                JFrame frame = new JFrame();
//                JPanel panel = new JPanel();
//                JList list;
//
//                frame.setSize(500, 400);
//                try {
//                    list = addData();
//                    frame.add(panel);
//                    frame.add(list);
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//
//                frame.setSize(500,400);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setVisible(true);
//            }
//        });
//        delete = new JButton("Delete a task");
//        showAll = new JButton("Show all tasks");
//        showAll.addActionListener(this::actionPerformed);
//        showCompleted = new JButton("show all completed tasks");
//        showNotCompleted = new JButton("Show all tasks not completed");
//
//        JTextField userText = new JTextField(2);
//        panel.add(add);
//        panel.add(Box.createHorizontalGlue());
//        panel.add(delete);
//        panel.add(showAll);
//        panel.add(showCompleted);
//        panel.add(showNotCompleted);
//
//        frame.setSize(100,100);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(panel, BorderLayout.NORTH);
//
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args){
//        new GUI();
//    }
//
////    @Override
////    public void actionPerformed(ActionEvent actionEvent) {
////        JFrame frame = new JFrame();
////        JPanel panel = new JPanel();
////        JList list;
////
////        frame.setSize(500, 400);
////        try {
////            list = addData();
////            frame.add(panel);
////            frame.add(list);
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////        }
////        //JLabel nameTextBox = new JLabel("Name");
////        //panel.add(nameTextBox);
////        //panel.add(name);
////
////        frame.setSize(500,400);
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setVisible(true);
////
//
//    }
//
////    public JList addData() throws SQLException {
////        DefaultListModel model = new DefaultListModel();
////
////        try(Connection connection = DriverManager.getConnection(new Main().url)){
////            ResultSet set;
////            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tableName");
////
////            set = statement.executeQuery();
////
////            while(set.next()){
////                Task task = new Task();
////                task.setTaskName(set.getString("name"));
////                task.setTaskDescription(set.getString("description"));
////                task.setDueDate(set.getString("date"));
////                task.setComplete(set.getBoolean("done"));
////
////                model.addElement(task);
////            }
////        }
////
////        return new JList(model);
////    }
//}

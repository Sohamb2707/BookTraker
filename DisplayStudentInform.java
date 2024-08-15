package package1;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayStudentInform extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;

    public DisplayStudentInform() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Add columns to the table model
        tableModel.addColumn("Library_Id");
        tableModel.addColumn("Student_Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Branch");

        // Retrieve data from the "studentinform" table in the database and add it to the table model
        retrieveDataFromDatabase();

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
		 setTableColors();

          setSize(670,600);
        setVisible(true);
		setLocation(465,130);

    }

    private void retrieveDataFromDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/miniproject"; // Use the correct database name
            String username = "root";
            String password = "272004@soham";
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM studentinform"; // Replace with your table name
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String column1 = resultSet.getString("Library_Id");
                String column2 = resultSet.getString("Student_Name");
                String column3 = resultSet.getString("Phone");
                String column4 = resultSet.getString("Branch");
                tableModel.addRow(new Object[]{column1, column2, column3, column4});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	 private void setTableColors() {
        // Create a custom cell renderer to set cell colors
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.yellow);
        renderer.setForeground(Color.black);
        
        // Apply the renderer to all cells in the table
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisplayStudentInform());
    }
}

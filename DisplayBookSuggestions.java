
package package1;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayBookSuggestions extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;

    public DisplayBookSuggestions() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Add columns to the table model
        tableModel.addColumn("From_Teachers");
        tableModel.addColumn("From_Students");
      

       
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
            String query = "SELECT * FROM booksuggestions"; // Replace with your table name
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String column1 = resultSet.getString("From_Teachers");
                String column2 = resultSet.getString("From_Students");
                
                tableModel.addRow(new Object[]{column1, column2});
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
        SwingUtilities.invokeLater(() -> new DisplayBookSuggestions());
    }
}

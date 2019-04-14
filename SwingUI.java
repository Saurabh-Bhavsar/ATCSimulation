
// Packages to import 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SwingUI {
	// frame
	JFrame f;
	// Table
	JTable j;

	// Constructor
	public static DefaultTableModel model;

	public SwingUI(int n) {
		// Frame initiallization
		f = new JFrame();

		// Frame Title
		f.setTitle("Air Traffic Controller ");

		// Data to be displayed in the JTable
		String[][] data = new String[n][2];
		// Column Names
		String[] columnNames = { "Airplane Id", "Current State" };

		for(int i = 0; i < n; i++)
		{
			data[i][0] = Integer.toString(i);
		}
		// Initializing the JTable
		
		model = new DefaultTableModel(data, columnNames);
		j = new JTable(model);
		j.setBounds(30, 40, 200, 300);

		// adding it to JScrollPane
		JScrollPane sp = new JScrollPane(j);
		f.add(sp);
		// Frame Size
		f.setSize(500, 200);
		// Frame Visible = true
		f.setVisible(true);
	}

	// Driver method
	public static void main(String[] args) {
		// new SwingUI();
	}
}
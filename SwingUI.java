
// Packages to import 
import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SwingUI {
	// frame
	JFrame f;
	// Table
	JTable j;
	JTable j1;

	// Constructor
	public static DefaultTableModel model;
	public static DefaultTableModel model1;

	public SwingUI(int n) {
		// Frame initiallization
		f = new JFrame();

		// Frame Title
		f.setTitle("Air Traffic Controller ");
		String[][] data = new String[n][6];
		// Column Names
		String[] columnNames = { "Airplane Id", "Start Time", "Priority", "Current State", "Next State", "End Time" };

		for (int i = 0; i < n; i++) {
			data[i][0] = Integer.toString(i);
		}

		// Initializing the JTable

		String[][] data1 = new String[1][3];
		for (int i = 0; i < 3; i++) {
			data1[0][i] = "--";
		}
		String[] resourceColumnNames = { "Runway Resource", "Gate Resouce 1", "Gate Resource 2" };
		model = new DefaultTableModel(data, columnNames);
		JLabel label1 = new JLabel("Table 1: Live state update    Table 2: Resource utilization");
		JPanel panel = new JPanel();
		panel.add(label1);
		model1 = new DefaultTableModel(data1, resourceColumnNames);

		j = new JTable(model);
		j1 = new JTable(model1);
		j.setBounds(400, 200, 400, 700);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) j.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableCellRenderer renderer1 = (DefaultTableCellRenderer) j1.getDefaultRenderer(Object.class);
		renderer1.setHorizontalAlignment(SwingConstants.CENTER);

		panel.add(label1);
		panel.add(j);

		// adding it to JScrollPane
		JScrollPane sp = new JScrollPane(j);
		JScrollPane sp1 = new JScrollPane(j1);
		f.add(panel, BorderLayout.NORTH);
		f.add(sp, BorderLayout.CENTER);
		// f.add(panel2, BorderLayout.CENTER);
		f.add(sp1, BorderLayout.SOUTH);
		// f.add(label1);
		// Frame Size
		f.setSize(1000, 600);
		// Frame Visible = true
		f.setVisible(true);
	}

	public static void updateGUIState(String curr_state, String next_state, int row) {
		model.setValueAt(curr_state, row, 3);
		model.setValueAt(next_state, row, 4);
	}

	public static void updateStartTimeAndPriority(String time, int priority, int row) {
		// System.out.println(time);
		model.setValueAt(time, row, 1);
		model.setValueAt(priority, row, 2);
	}

	public static void updateEndTime(Date time, int row) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		model.setValueAt(dateFormat.format(time), row, 5);
	}

	public static void updateResourceUsedBy(String resource, int id) {
		if (resource.equals("R")) {
			model1.setValueAt(id, 0, 0);
		} else if (resource.equals("G1")) {
			model1.setValueAt(id, 0, 1);
		} else {
			model1.setValueAt(id, 0, 2);
		}
	}

	public static void resetResourceTable(int resource) {
		model1.setValueAt("--", 0, resource);
	}
}
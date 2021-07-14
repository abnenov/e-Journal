import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

class Reports {

	protected JPanel rPanel = new JPanel(new BorderLayout());
	private DefaultTableModel model = new DefaultTableModel();
	private JPanel rPanel1 = new JPanel();
	private JTable jtbl = new JTable(model) {
		private static final long serialVersionUID = 7671605912319021378L;

		@Override
		public boolean isCellEditable(int row, int column) {
			// ��������� �� �� �� ����� �� �� ���������� �������� � ���������
			return false;
		}
	};
	ListSelectionModel cellSelectionModel = jtbl.getSelectionModel();
	// create a TableRowSorter and pass your tableModel
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		// attach it to your JTable
	protected JScrollPane pg1 = new JScrollPane(jtbl);
	private JButton button1 = new JButton("�������");
	private JButton button2 = new JButton("������ ����� �� ����");
	private JButton button3 = new JButton("��� ������ ����� �� �������");
	private JButton button4 = new JButton("������ ����� �� ������� �� ����");
	private JTextField search = new JTextField();

	Reports() {
		jtbl.setRowSorter(sorter);
		rPanel1.setLayout(new BoxLayout(rPanel1, BoxLayout.X_AXIS));
		rPanel1.add(button2);
		rPanel1.add(button3);
		rPanel1.add(button4);
		rPanel1.add(button1);
		rPanel1.add(search);
		search.setMaximumSize(new Dimension(396,30));
		rPanel.add(rPanel1, BorderLayout.PAGE_START);
		rPanel.add(pg1, BorderLayout.CENTER);
		cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		rPanel.add(pg1);
		jtbl.setEnabled(true);

		jtbl.addMouseListener(new MouseAdapter() {
			// @SuppressWarnings("unused")
			public void mouseClicked(MouseEvent me) {
				// if (me.getClickCount() == 2) { // �������� ��������� ��������
				// JTable target = (JTable) me.getSource();
				// int row = target.getSelectedRow(); // select a row
				// int column = target.getSelectedColumn(); // select a column
				// Global.logWriter(Global.title11);
				// Object[] options = { "�������", "�����" };
				// int dialog = JOptionPane.showOptionDialog(rPanel,
				// Global.inqMessage4 + jtbl.getValueAt(row, 0) + "?", Global.title20,
				// JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				// options[0]);
				// if (dialog == JOptionPane.YES_OPTION) {
				// if (jtbl.getValueAt(row, 5).equals("��")) {
				// jtbl.setValueAt("��", row, 5);
				// System.out.println(jtbl.getValueAt(row, 5));
				// }
				// showUsers();
				// JOptionPane.setDefaultLocale(null);
				//
				// } else {
				// dialog = JOptionPane.CANCEL_OPTION;
				// }
				// }
			}
		});
		
		button1.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				sorter.setRowFilter(RowFilter.regexFilter(search.getText()));
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				if (getTblIds().size() > 0) {
					Global.logWriter(Global.title58);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"�������� ����� �� ����� � "
									+ Math.round(DBData.getAvgCourse(getRowSelectedId()) * 100) / 100f,
							Global.title58, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				} else {
					Global.logWriter(Global.title58);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"����, �������� ����, �� ����� �� ���� �������� ������ �����!", Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				}
			}
		});

		button3.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				if (getTblIds().size() > 0) {
					Global.logWriter(Global.title59);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"������ �������� ����� �� �������� � "
									+ Math.round(DBData.getAvgStudent(getRowSelectedId()) * 100) / 100f,
							Global.title59, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				} else {
					Global.logWriter(Global.title59);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"����, �������� �������, �� ����� �� ���� �������� ������ �����!", Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				}
			}
		});

		button4.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				if (getTblIds().size() > 0) {
					Global.logWriter(Global.title60);
					Object[] options = { "OK" };
					int dialog = JOptionPane
							.showOptionDialog(rPanel,
									"�������� ����� �� �������� �� ����� � " + Math.round(DBData.getAvgCourseStudent(
											DBData.getCoursesStudentsById(getRowSelectedId())[0],
											DBData.getCoursesStudentsById(getRowSelectedId())[1]) * 100) / 100f,
									Global.title59, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
									options, options[0]);
				} else {
					Global.logWriter(Global.title60);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"����, �������� ������� / ����, �� ����� �� ���� �������� ������ �����!", Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				}
			}
		});

	}

	// ����� ������ � id-���� �� ������������, ������������� ������ � ��������� ��
	// ������. �� �� �������� � ������ ������
	// ����� �� ���������� �� ����������� ������, ����� �� ����� ��������� ���
	// �����������
	// ����� ID-�� �� ��� �� ���������, ����� � ����������
	protected int getRowSelectedId() {
		return (int) jtbl.getModel().getValueAt((int) jtbl.getSelectedRow(), 0);
	}

	protected ArrayList<Integer> getTblIds() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (!jtbl.getSelectionModel().isSelectionEmpty()) {
			int[] selectedRow = jtbl.getSelectedRows();
			for (int i = 0; i < selectedRow.length; i++) {
				ids.add((Integer) jtbl.getModel().getValueAt(selectedRow[i], 0));
			}
		}
		return ids;
	}

	protected ArrayList<Integer> getTblIds(DefaultTableModel model, JTable jtbl) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (!jtbl.getSelectionModel().isSelectionEmpty()) {
			int[] selectedRow = jtbl.getSelectedRows();
			for (int i = 0; i < selectedRow.length; i++) {
				ids.add((Integer) jtbl.getModel().getValueAt(selectedRow[i], 0));
			}
		}
		return ids;
	}

	protected ArrayList<String> getTblFirst() {
		ArrayList<String> ids = new ArrayList<String>();
		if (!jtbl.getSelectionModel().isSelectionEmpty()) {
			int[] selectedRow = jtbl.getSelectedRows();
			for (int i = 0; i < selectedRow.length; i++) {
				ids.add((String) jtbl.getModel().getValueAt(selectedRow[i], 0));
			}
		}
		return ids;
	}

	// ���������� ������� �� ������. ��� true - dd.mm.yyyy - > yyyy-mm-dd
	protected String convDateBG(String date, boolean BG) {
		String result = "";
		if (BG) {
			result = date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);
		} else {
			result = date.substring(8, 10) + "." + date.substring(5, 7) + "." + date.substring(0, 4);
		}
		return result;
	}

	protected void showLectors(boolean singleSel) {
		if (singleSel) {
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		else {
			cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		button2.setVisible(false);
		button3.setVisible(false);
		button4.setVisible(false);
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("�����");
		model.addColumn("���");
		model.addColumn("�������");
		model.addColumn("��.����");
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		DBData.getLectors(model);
	}

	protected void showLectors(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("�����");
		model.addColumn("���");
		model.addColumn("�������");
		// model.addColumn("�����");
		model.addColumn("��.����");
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		DBData.getLectors(model);
	}

	protected void showUsers() {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("����������");
		model.addColumn("����");
		model.addColumn("�����");
		model.addColumn("��������");
		model.addColumn("��.����");
		model.addColumn("�������");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		DBData.getUsers(model);
	}

	protected void showCourses(boolean singleSel) {
		if (singleSel) {
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		else {
			cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		button2.setVisible(true);
		button3.setVisible(false);
		button4.setVisible(false);
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("������������");
		model.addColumn("���� �� ���������");
		model.addColumn("���� �� �����������");
		model.addColumn("������");
		model.addColumn("�������");
		model.addColumn("��.����");
		model.addColumn("lector_ID");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(6)); // lector_id �������� �� �� ����������� �� ������
		DBData.getCourses(model);
	}

	protected void showCourses(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("������������");
		model.addColumn("���� �� ���������");
		model.addColumn("���� �� �����������");
		model.addColumn("������");
		model.addColumn("�������");
		model.addColumn("��.����");
		model.addColumn("lector_ID");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(6)); // lector_id �������� �� �� ����������� �� ������
		DBData.getCourses(model);
	}

	protected void showStudents(boolean singleSel) {
		button2.setVisible(false);
		button3.setVisible(true);
		button4.setVisible(false);
		if (singleSel) {
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		else {
			cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("�����");
		model.addColumn("���");
		model.addColumn("�������");
		model.addColumn("�����");
		model.addColumn("��.����");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		DBData.getStudents(model);
	}

	protected void showStudents(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("�����");
		model.addColumn("���");
		model.addColumn("�������");
		model.addColumn("�����");
		model.addColumn("��.����");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		DBData.getStudents(model);
	}

	protected void showGrades(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("�������");
		model.addColumn("���");
		model.addColumn("����");
		model.addColumn("������");
		model.addColumn("����");
		model.addColumn("������ �������� ��������");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		DBData.getGrades(model);
	}

	protected void showGrades(boolean singleSel) {
		if (singleSel) {
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		else {
			cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		button2.setVisible(false);
		button3.setVisible(false);
		button4.setVisible(false);
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("�������");
		model.addColumn("���");
		model.addColumn("����");
		model.addColumn("������");
		model.addColumn("����");
		model.addColumn("������ �������� ��������");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		DBData.getGrades(model);
	}

	protected void showCoursesStudents(boolean singleSel) {
		if (singleSel) {
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		else {
			cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		button2.setVisible(false);
		button3.setVisible(false);
		button4.setVisible(true);
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("����");
		model.addColumn("���� �� ���������");
		model.addColumn("���� �� �����������");
		model.addColumn("�������");
		model.addColumn("��� �� ��������");
		model.addColumn("������");
		model.addColumn("��� �� �������");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID �������� �� �� ����������� �� ������
		DBData.getCoursesStudents(model);
	}

	protected void showRoles() {
		model.setColumnCount(0);
		model.addColumn("����");
		model.addColumn("������ �� ���� \"��������� �� �����\"");
		model.addColumn("������ �� ���� \"�������\"");
		model.addColumn("������ �� ���� \"��������������\"");
		model.addColumn("�������");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		DBData.getRoles(model);
	}

}

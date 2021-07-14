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
			// настройка за да не могат да се редактират клетките в таблицата
			return false;
		}
	};
	ListSelectionModel cellSelectionModel = jtbl.getSelectionModel();
	// create a TableRowSorter and pass your tableModel
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		// attach it to your JTable
	protected JScrollPane pg1 = new JScrollPane(jtbl);
	private JButton button1 = new JButton("Търсене");
	private JButton button2 = new JButton("Среден успех на курс");
	private JButton button3 = new JButton("Общ среден успех на студент");
	private JButton button4 = new JButton("Среден успех на студент за курс");
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
				// if (me.getClickCount() == 2) { // прихваща двукратно кликване
				// JTable target = (JTable) me.getSource();
				// int row = target.getSelectedRow(); // select a row
				// int column = target.getSelectedColumn(); // select a column
				// Global.logWriter(Global.title11);
				// Object[] options = { "Промени", "Отказ" };
				// int dialog = JOptionPane.showOptionDialog(rPanel,
				// Global.inqMessage4 + jtbl.getValueAt(row, 0) + "?", Global.title20,
				// JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				// options[0]);
				// if (dialog == JOptionPane.YES_OPTION) {
				// if (jtbl.getValueAt(row, 5).equals("Да")) {
				// jtbl.setValueAt("Не", row, 5);
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
							"Средният успех на курса е "
									+ Math.round(DBData.getAvgCourse(getRowSelectedId()) * 100) / 100f,
							Global.title58, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				} else {
					Global.logWriter(Global.title58);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"Моля, посочете курс, за който да бъде изчислен среден успех!", Global.title3,
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
							"Общият средният успех на студента е "
									+ Math.round(DBData.getAvgStudent(getRowSelectedId()) * 100) / 100f,
							Global.title59, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				} else {
					Global.logWriter(Global.title59);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"Моля, посочете студент, за който да бъде изчислен среден успех!", Global.title3,
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
									"Средният успех на студента за курса е " + Math.round(DBData.getAvgCourseStudent(
											DBData.getCoursesStudentsById(getRowSelectedId())[0],
											DBData.getCoursesStudentsById(getRowSelectedId())[1]) * 100) / 100f,
									Global.title59, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
									options, options[0]);
				} else {
					Global.logWriter(Global.title60);
					Object[] options = { "OK" };
					int dialog = JOptionPane.showOptionDialog(rPanel,
							"Моля, посочете студент / курс, за които да бъде изчислен среден успех!", Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				}
			}
		});

	}

	// Връща списък с id-тата на информацията, визуализирана текущо в таблицата на
	// екрана. Те се съдържат в скрита колона
	// Служи за определяне на маркираните редове, които ще бъдат изтривани или
	// редактирани
	// Връща ID-то на ред от таблицата, който е селектиран
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

	// Конвертира формата на датата. При true - dd.mm.yyyy - > yyyy-mm-dd
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
		model.addColumn("Имена");
		model.addColumn("ЕГН");
		model.addColumn("Телефон");
		model.addColumn("Ел.поща");
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		DBData.getLectors(model);
	}

	protected void showLectors(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("Имена");
		model.addColumn("ЕГН");
		model.addColumn("Телефон");
		// model.addColumn("Адрес");
		model.addColumn("Ел.поща");
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		DBData.getLectors(model);
	}

	protected void showUsers() {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("Потребител");
		model.addColumn("Роля");
		model.addColumn("Имена");
		model.addColumn("Длъжност");
		model.addColumn("Ел.поща");
		model.addColumn("Активен");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
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
		model.addColumn("Наименование");
		model.addColumn("Дата на започване");
		model.addColumn("Дата на приключване");
		model.addColumn("Лектор");
		model.addColumn("Телефон");
		model.addColumn("Ел.поща");
		model.addColumn("lector_ID");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(6)); // lector_id колоната не се визуализира на екрана
		DBData.getCourses(model);
	}

	protected void showCourses(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("Наименование");
		model.addColumn("Дата на започване");
		model.addColumn("Дата на приключване");
		model.addColumn("Лектор");
		model.addColumn("Телефон");
		model.addColumn("Ел.поща");
		model.addColumn("lector_ID");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(6)); // lector_id колоната не се визуализира на екрана
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
		model.addColumn("Имена");
		model.addColumn("ЕГН");
		model.addColumn("Телефон");
		model.addColumn("Адрес");
		model.addColumn("Ел.поща");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		DBData.getStudents(model);
	}

	protected void showStudents(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("Имена");
		model.addColumn("ЕГН");
		model.addColumn("Телефон");
		model.addColumn("Адрес");
		model.addColumn("Ел.поща");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		DBData.getStudents(model);
	}

	protected void showGrades(DefaultTableModel model, JTable jtbl) {
		model.setColumnCount(0);
		model.addColumn("ID");
		model.addColumn("Студент");
		model.addColumn("ЕГН");
		model.addColumn("Курс");
		model.addColumn("Оценка");
		model.addColumn("Дата");
		model.addColumn("Лектор поставил оценката");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
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
		model.addColumn("Студент");
		model.addColumn("ЕГН");
		model.addColumn("Курс");
		model.addColumn("Оценка");
		model.addColumn("Дата");
		model.addColumn("Лектор поставил оценката");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
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
		model.addColumn("Курс");
		model.addColumn("Дата на започване");
		model.addColumn("Дата на приключване");
		model.addColumn("Студент");
		model.addColumn("ЕГН на студента");
		model.addColumn("Лектор");
		model.addColumn("ЕГН на лектора");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		jtbl.removeColumn(jtbl.getColumnModel().getColumn(0)); // ID колоната не се визуализира на екрана
		DBData.getCoursesStudents(model);
	}

	protected void showRoles() {
		model.setColumnCount(0);
		model.addColumn("Роля");
		model.addColumn("Достъп до меню \"Въвеждане на данни\"");
		model.addColumn("Достъп до меню \"Справки\"");
		model.addColumn("Достъп до меню \"Администриране\"");
		model.addColumn("Активна");
		((DefaultTableModel) jtbl.getModel()).setNumRows(0);
		DBData.getRoles(model);
	}

}

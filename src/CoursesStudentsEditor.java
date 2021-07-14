import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

class CoursesStudentsEditor extends Reports {

	private String[] dialogParams = new String[8];

	protected JPanel csPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();

	private DefaultTableModel model2 = new DefaultTableModel();
	private JTable jtbl2 = new JTable(model2) {
		  /**
		 * 
		 */
		private static final long serialVersionUID = -2985206406077961417L;

		@Override
		   public boolean isCellEditable(int row, int column) {
		    // настройка за да не могат да се редактират клетките в таблицата
		    return false;
		   }
	};
	private JScrollPane pg2 = new JScrollPane(jtbl2);
	//
	private DefaultTableModel model3 = new DefaultTableModel();
	private JTable jtbl3 = new JTable(model3) {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 8422357524012237688L;

		@Override
		   public boolean isCellEditable(int row, int column) {
		    // настройка за да не могат да се редактират клетките в таблицата
		    return false;
		   }
	};
	private JScrollPane pg3 = new JScrollPane(jtbl3);

	private JButton insert = new JButton("Добави");
	private JButton clear = new JButton("Изчисти");
	private JPanel csPanel1 = new JPanel();
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	private JButton button1 = new JButton(Global.title36);
	private JButton button2 = new JButton(Global.title37);
	private JButton button3 = new JButton(Global.title38);
	private JLabel courseLabel = new JLabel("Списък с курсове");
	private JLabel studentLabel = new JLabel("Списък със студенти");

	CoursesStudentsEditor() {

		csPanel1.setLayout(new BoxLayout(csPanel1, BoxLayout.X_AXIS));
		csPanel1.add(button1);
		csPanel1.add(button2);
		csPanel1.add(button3);
		csPanel.add(csPanel1, BorderLayout.PAGE_START);
		csPanel.add(pg1, BorderLayout.CENTER);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

		panel1.add(courseLabel);
		panel2.add(studentLabel);
		panel3.add(insert);
		panel3.add(clear);

		panel.add(panel1);
		panel.add(pg2);
		panel.add(panel2);
		panel.add(pg3);
		panel.add(panel3);

		mainDialog.add(panel);
		mainDialog.setSize(1000, 400);

		mainDialog.setResizable(true);
		mainDialog.setLocationRelativeTo(null);

		jtbl2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dialogParams[0] = Global.title36;
				dialogParams[5] = Global.title9;
				dialogParams[6] = "0";
				showCourses(model2, jtbl2);
				showStudents(model3, jtbl3);
				mainDialog.setTitle(dialogParams[0]);
				insert_editCoursesStudentsDialog(dialogParams);
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				String[] result = new String[5];

				ArrayList<Integer> result1 = new ArrayList<Integer>();
				result1 = getTblIds();
				if (result1.size() > 1) {
					Global.logWriter(Global.errMesssage16);
					Object[] options = { Global.title12 };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else {
					if (!result1.isEmpty()) {
						dialogParams[0] = Global.title37;
						dialogParams[5] = Global.title10;
						dialogParams[6] = "1";
						showCourses(model2, jtbl2);
						showStudents(model3, jtbl3);
						setRowSelectedCourses(DBData.getCourseIdCS(getRowSelectedId()));
						setRowSelectedStudents(DBData.getStudentsIdsCS(DBData.getCourseIdCS(getRowSelectedId())));
						insert_editCoursesStudentsDialog(dialogParams);
						Global.logWriter(Global.title37);
					} else {
						Global.logWriter(Global.errMesssage17);
						Object[] options = { "Разбрах" };
						int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage3, Global.title3,
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					}
				}
			}

		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteCoursesStudentsDialog();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jtbl2.getSelectionModel().clearSelection();
				jtbl3.getSelectionModel().clearSelection();
			}
		});

		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insert_edit(dialogParams);
			}
		});

	}

	// маркира ред от таблица курсове във формата за редакция
	private void setRowSelectedCourses(int course_id) {
		jtbl2.clearSelection();
		for (int i = 0; i < jtbl2.getRowCount(); i++) {
			if (jtbl2.getModel().getValueAt(i, 0).equals(course_id)) {
				jtbl2.setRowSelectionInterval(i, i);
				break;
			}
		}

	}

	private void setRowSelectedStudents(ArrayList<Integer> students_id) {
		jtbl3.clearSelection();
		for (int i = 0; i < jtbl3.getRowCount(); i++) {
			if (students_id.contains(jtbl3.getModel().getValueAt(i, 0))) {
				jtbl3.addRowSelectionInterval(i, i);
			}
		}

	}

	private void insert_edit(String[] dialogParams) {
		boolean noAdded = false;
		boolean added = false;
		try {
			if (dialogParams[6] == "0") {
				ArrayList<Integer> result = new ArrayList<Integer>();
				result = getTblIds(model3, jtbl3);
				if (getTblIds(model3, jtbl3).isEmpty() || getTblIds(model2, jtbl2).isEmpty() ) {
					throw new Exception("3");
				}
				for (int i : result) {
					if (!DBData.checkCoursesStudents(i, getTblIds(model2, jtbl2).get(0))) {
						DBData.insertCoursesStudents(i, getTblIds(model2, jtbl2).get(0));
						added = true;
					} else
						noAdded = true;
				}
				showCoursesStudents(false);
				if (noAdded) {
					if (added) {
						throw new Exception("1");
					} else
						throw new Exception("2");
				}
			} else {

				ArrayList<Integer> result = new ArrayList<Integer>();
				result = getTblIds(model3, jtbl3);
				if (getTblIds(model3, jtbl3).isEmpty() || getTblIds(model2, jtbl2).isEmpty() ) {
					throw new Exception("3");
				}
				for (int i : result) {
					if (!DBData.checkCoursesStudents(i, getTblIds(model2, jtbl2).get(0))) {
						DBData.insertCoursesStudents(i, getTblIds(model2, jtbl2).get(0));
						added = true;
					} else
						noAdded = true;
				}
				showCoursesStudents(false);
				if (noAdded) {
					if (added) {
						throw new Exception("1");
					} else
						throw new Exception("2");
				}
			}
		} catch (Exception e) {
			if (e.getMessage().equals("1")) {
				Global.logWriter("Опит, за добавяне на добавени вече студенти към курс.");
				JOptionPane.showMessageDialog(mainDialog,
						"Един или повече студенти са били добавени предходно към избраният курс! Добавени са само новите.",
						"Информация!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("2")) {
				Global.logWriter("Опит, за добавяне на добавени вече студенти към курс.");
				JOptionPane.showMessageDialog(mainDialog, "Всички избрани студенти вече са били добавени предходно.",
						"Информация!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("3")) {
				Global.logWriter("Опит, за добавяне/редактиране на студенти към курс без да са избрани записи в таблиците.");
				JOptionPane.showMessageDialog(mainDialog, Global.actMesssage9,
						"Информация!", JOptionPane.WARNING_MESSAGE);
			}
			// if (e.getMessage().equals("3")) {
			// Global.logWriter("Невалиден телефонен номер!");
			// JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валиден телефонен
			// номер!", "Проблем!",
			// JOptionPane.WARNING_MESSAGE);
			// }
			// if (e.getMessage().equals("4")) {
			// Global.logWriter("Невалиден адрес на електронна поща!");
			// JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валиден адрес на
			// електронна поща!",
			// "Проблем!", JOptionPane.WARNING_MESSAGE);
			// }
			// if (e.getMessage().equals("5")) {
			// Global.logWriter("Вече съществува този лектор в базата данни!");
			// JOptionPane.showMessageDialog(mainDialog, "Опитвате се да въведете
			// съществуващ в базата данни студент!",
			// "Проблем!", JOptionPane.WARNING_MESSAGE);
			// }
			// if (e.getMessage().equals("6")) {
			// Global.logWriter("Вече съществува този лектор в базата данни!");
			// JOptionPane.showMessageDialog(mainDialog, "Моля, въведете невъществуващ в
			// базата данни лектор!",
			// "Проблем!", JOptionPane.WARNING_MESSAGE);
			// }
		}
	}

	private void insert_editCoursesStudentsDialog(String[] dialogParams) {
		mainDialog.setTitle(dialogParams[0]);
		insert.setText(dialogParams[5]);
		mainDialog.setModal(true);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteCoursesStudentsDialog() {
		String messages = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = getTblIds();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title39);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (Integer i : result) {
					DBData.deleteCoursesStudents(i);
				}
				showCoursesStudents(false);
			}
		} else {
			Global.logWriter(Global.errMesssage15);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
}
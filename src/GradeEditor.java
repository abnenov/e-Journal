import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

class GradeEditor extends Reports {

	private String[] dialogParams = new String[8];
	private boolean insert_edit = false;

	protected JPanel gPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();

	// Курсове
	private DefaultTableModel model2 = new DefaultTableModel();
	private JTable jtbl2 = new JTable(model2) {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6825982586125445468L;

		@Override
		public boolean isCellEditable(int row, int column) {
			// настройка за да не могат да се редактират клетките в таблицата
			return false;
		}
	};
	private JScrollPane pg2 = new JScrollPane(jtbl2);

	// Студенти
	private DefaultTableModel model3 = new DefaultTableModel();
	private JTable jtbl3 = new JTable(model3) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3026745327869384607L;

		@Override
		public boolean isCellEditable(int row, int column) {
			// настройка за да не могат да се редактират клетките в таблицата
			return false;
		}
	};
	private JScrollPane pg3 = new JScrollPane(jtbl3);

	// Лектори
	private DefaultTableModel model4 = new DefaultTableModel();
	private JTable jtbl4 = new JTable(model4) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2130561372856737088L;

		@Override
		public boolean isCellEditable(int row, int column) {
			// настройка за да не могат да се редактират клетките в таблицата
			return false;
		}
	};
	private JScrollPane pg4 = new JScrollPane(jtbl4);

	private JButton insert = new JButton("Добави");
	private JButton clear = new JButton("Изчисти");
	private JPanel gPanel1 = new JPanel();
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JPanel panel3 = new JPanel();

	private JButton button1 = new JButton(Global.title46);
	private JButton button2 = new JButton(Global.title47);
	private JButton button3 = new JButton(Global.title48);
	private JLabel courseLabel = new JLabel("Списък с курсове");
	private JLabel studentLabel = new JLabel("Списък със студенти");
	private JLabel lectorLabel = new JLabel("Списък с лектори");
	private JLabel gradeLabel = new JLabel("  Оценка  ");
	private JLabel dateLabel = new JLabel("  Дата на поставяне на оценката  ");
	private JComboBox<String> grade = new JComboBox<String>(new String[] { "--", "1", "2", "3", "4", "5", "6" });
	private Dimension buttonDimensions = new Dimension(18, 24);
	private Dimension textFieldDimensions = new Dimension(90, 25);
	private String gradeInitText;
	private DateTextField gradeDate = new DateTextField("--", gradeInitText, textFieldDimensions, buttonDimensions);

	GradeEditor() {

		gPanel1.setLayout(new BoxLayout(gPanel1, BoxLayout.X_AXIS));
		gPanel1.add(button1);
		gPanel1.add(button2);
		gPanel1.add(button3);
		gPanel.add(gPanel1, BorderLayout.PAGE_START);
		gPanel.add(pg1, BorderLayout.CENTER);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER));

		panel1.add(courseLabel);
		panel2.add(studentLabel);
		panel4.add(lectorLabel);

		panel3.add(dateLabel);
		panel3.add(gradeDate.dateField);
		panel3.add(gradeLabel);
		panel3.add(grade);

		panel3.add(insert);
		panel3.add(clear);

		panel.add(panel1);
		panel.add(pg2);
		panel.add(panel2);
		panel.add(pg3);
		panel.add(panel4);
		panel.add(pg4);
		panel.add(panel3);

		mainDialog.add(panel);
		mainDialog.setSize(1200, 600);

		mainDialog.setResizable(true);
		mainDialog.setLocationRelativeTo(null);

		jtbl2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbl3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbl4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insert_edit = false;
				dialogParams[0] = Global.title52;
				dialogParams[5] = Global.title9;
				dialogParams[6] = "0";
				showCourses(model2, jtbl2);
				showStudents(model3, jtbl3);
				showLectors(model4, jtbl4);
				mainDialog.setTitle(dialogParams[0]);
				insert_editCoursesStudentsDialog(dialogParams);
			}
		});

		gradeDate.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String result = new DatePicker(mainDialog).setPickedDate();
				if (result != "") {
					gradeDate.textField.setText(result);
				}
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				String[] result = new String[5];
				insert_edit = true;

				ArrayList<Integer> result1 = new ArrayList<Integer>();
				result1 = getTblIds();
				if (result1.size() > 1) {
					Global.logWriter(Global.errMesssage20);
					Object[] options = { Global.title12 };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else {
					if (!result1.isEmpty()) {
						dialogParams[0] = Global.title53;
						dialogParams[1] = DBData.getGradeInfoById(getTblIds().get(0))[2];
						dialogParams[1] = convDateBG(dialogParams[1], false);
						dialogParams[2] = DBData.getGradeInfoById(getTblIds().get(0))[3];
						dialogParams[5] = Global.title10;
						dialogParams[6] = "1";
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
				deleteGradesDialog();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				grade.setSelectedIndex(0);
				gradeDate.setText("");
				jtbl2.getSelectionModel().clearSelection();
				jtbl3.getSelectionModel().clearSelection();
				jtbl4.getSelectionModel().clearSelection();
			}
		});

		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insert_edit(dialogParams);
			}
		});

		jtbl2.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (!insert_edit) {
					((DefaultTableModel) jtbl3.getModel()).setNumRows(0);
					grade.setSelectedIndex(0);
					gradeDate.setText("");
					jtbl4.getSelectionModel().clearSelection();
					DBData.getStudentsByCourseIdSQL(model3,
							(int) jtbl2.getModel().getValueAt((int) jtbl2.getSelectedRow(), 0));
				} else {
					setRowSelectedCourses(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[0]));
					setRowSelectedStudent(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[1]));
					setRowSelectedLector(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[4]));
				}
			}
		});

		jtbl3.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (insert_edit) {
					setRowSelectedCourses(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[0]));
					setRowSelectedStudent(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[1]));
					setRowSelectedLector(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[4]));
				}
			}
		});

	}

	// маркира ред от таблица курсове във формата за редакция
	private void setRowSelectedCourses(int course_id) {
		jtbl2.getSelectionModel().clearSelection();
		for (int i = 0; i < jtbl2.getRowCount(); i++) {
			if (jtbl2.getModel().getValueAt(i, 0).equals(course_id)) {
				jtbl2.setRowSelectionInterval(i, i);
				break;
			}
		}

	}

	private void setRowSelectedStudent(int students_id) {
		jtbl3.getSelectionModel().clearSelection();
		for (int i = 0; i < jtbl3.getRowCount(); i++) {
			if (jtbl3.getModel().getValueAt(i, 0).equals(students_id)) {
				jtbl3.setRowSelectionInterval(i, i);
			}
		}

	}

	private void setRowSelectedLector(int lector_id) {
		jtbl4.getSelectionModel().clearSelection();
		for (int i = 0; i < jtbl4.getRowCount(); i++) {
			if (jtbl4.getModel().getValueAt(i, 0).equals(lector_id)) {
				jtbl4.setRowSelectionInterval(i, i);
			}
		}

	}

	private void insert_edit(String[] dialogParams) {
		// boolean noAdded = false;
		boolean added = false;
		try {
			if (getTblIds(model3, jtbl3).isEmpty() || getTblIds(model2, jtbl2).isEmpty()
					|| getTblIds(model4, jtbl4).isEmpty()) {
				throw new Exception("3");
			}
			if (gradeDate.textField.getText().equals("")) {
				throw new Exception("4");
			}
			if (grade.getSelectedItem().equals("--")) {
				throw new Exception("5");
			}
			if (dialogParams[6] == "0") {
				Integer[] result = new Integer[3];
				result[0] = getTblIds(model2, jtbl2).get(0);
				result[1] = getTblIds(model3, jtbl3).get(0);
				result[2] = getTblIds(model4, jtbl4).get(0);
				if (DBData.checkGradeExists(result[0], result[1], convDateBG(gradeDate.textField.getText(), true))) {
					added = true;
				}
				if (added) {
					Object[] options = { "Добави", "Отказ" };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage5, Global.title55,
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (dialog == JOptionPane.YES_OPTION) {
						DBData.insertGrades(result[0], result[1], convDateBG(gradeDate.textField.getText(), true),
								String.valueOf(grade.getSelectedItem()), result[2]);
					}
				} else {
					DBData.insertGrades(result[0], result[1], convDateBG(gradeDate.textField.getText(), true),
							String.valueOf(grade.getSelectedItem()), result[2]);
				}
				showGrades(false);
			} else {
				Integer[] result = new Integer[3];
				result[0] = getTblIds(model2, jtbl2).get(0);
				result[1] = getTblIds(model3, jtbl3).get(0);
				result[2] = getTblIds(model4, jtbl4).get(0);
				Object[] options = { "Промени", "Отказ" };
				int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage6, Global.title57,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (dialog == JOptionPane.YES_OPTION) {
					DBData.updateGrades(result[0], result[1], convDateBG(gradeDate.textField.getText(), true),
							String.valueOf(grade.getSelectedItem()), result[2], getTblIds().get(0));
					showGrades(false);
					mainDialog.dispose();
				}

			}
		} catch (Exception e) {
			if (e.getMessage().equals("3")) {
				Global.logWriter("Опит, за добавяне/редактиране на оценки без да са избрани записи в таблиците.");
				JOptionPane.showMessageDialog(mainDialog, Global.actMesssage10, "Информация!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("4")) {
				Global.logWriter("Опит, за добавяне/редактиране на оценки без да е избрана дата на оценката.");
				JOptionPane.showMessageDialog(mainDialog, Global.actMesssage11, "Информация!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("5")) {
				Global.logWriter("Опит, за добавяне/редактиране на оценки без да е избрана оценка.");
				JOptionPane.showMessageDialog(mainDialog, Global.actMesssage12, "Информация!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void insert_editCoursesStudentsDialog(String[] dialogParams) {
		if (dialogParams[6].equals("0")) {
			grade.setSelectedIndex(0);
			gradeDate.setText("");
			jtbl2.getSelectionModel().clearSelection();
			jtbl3.getSelectionModel().clearSelection();
			jtbl4.getSelectionModel().clearSelection();
		} else {
			gradeDate.textField.setText(dialogParams[1]);
			grade.setSelectedItem(dialogParams[2]);
			showCourses(model2, jtbl2);
			showStudents(model3, jtbl3);
			showLectors(model4, jtbl4);
			setRowSelectedCourses(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[0]));
			setRowSelectedStudent(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[1]));
			setRowSelectedLector(Integer.parseInt(DBData.getGradeInfoById(getTblIds().get(0))[4]));

		}
		mainDialog.setTitle(dialogParams[0]);
		insert.setText(dialogParams[5]);
		mainDialog.setModal(true);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteGradesDialog() {
		String messages = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = getTblIds();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title56);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (Integer i : result) {
					DBData.deleteGrades(i);
				}
				showGrades(false);
			}
		} else {
			Global.logWriter(Global.errMesssage19);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
}
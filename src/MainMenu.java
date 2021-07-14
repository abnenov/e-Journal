import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MainMenu implements ActionListener {

	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private CardLayout cardLayout = new CardLayout();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Профил на потребителя");
	protected JMenu menu1 = new JMenu("Въвеждане на данни");
	protected JMenu menu2 = new JMenu("Справки");
	protected JMenu menu3 = new JMenu("Администриране");
	private JMenuItem profile = new JMenuItem("Потребителски профил");
	private JMenuItem changePass = new JMenuItem("Смяна на парола");
	private JMenuItem different = new JMenuItem("Вход в Електронен дневник");
	private JMenuItem quit = new JMenuItem("Изход");
	private JMenuItem inputCourses = new JMenuItem("Курсове");
	private JMenuItem inputLectors = new JMenuItem("Лектори");
	private JMenuItem inputStudents = new JMenuItem("Студенти");
	private JMenuItem inputCoursesStudents = new JMenuItem("Курсове/Студенти");
	private JMenuItem inputGrades = new JMenuItem("Оценки");
	private JMenuItem listCourses = new JMenuItem("Курсове");
	private JMenuItem listLectors = new JMenuItem("Лектори");
	private JMenuItem listStudents = new JMenuItem("Студенти");
	private JMenuItem listCoursesStudents = new JMenuItem("Курсове/Студенти");
	private JMenuItem listGrades = new JMenuItem("Оценки");
	private JMenuItem listUsers = new JMenuItem("Потребители");
	private JMenuItem listRoles = new JMenuItem("Роли");
	private JMenuItem listLogs = new JMenuItem("Лог записи");

	private Reports reports;
	private CourseEditor courseEditor;
	private LectorEditor lectorEditor;
	private StudentEditor studentEditor;
	private CoursesStudentsEditor coursesStudentsEditor;
	private GradeEditor gradeEditor;
	private LogInOut logInOut;
	private ChangePwd changePwd;
	private UserEditor userEditor;
	private RoleEditor roleEditor;
	private Log log;
	private JFrame mainForm;

	MainMenu(JFrame mForm, Reports reports, CourseEditor courseEditor, LectorEditor lectorEditor,
			StudentEditor studentEditor, CoursesStudentsEditor coursesStudentsEditor, GradeEditor gradeEditor, LogInOut logInOut,
			ChangePwd changePwd, UserEditor userEditor, RoleEditor roleEditor, Log log) {
		this.mainForm = mForm;
		this.reports = reports;
		this.courseEditor = courseEditor;
		this.lectorEditor = lectorEditor;
		this.studentEditor = studentEditor;
		this.coursesStudentsEditor = coursesStudentsEditor;
		this.gradeEditor = gradeEditor;
		this.logInOut = logInOut;
		this.changePwd = changePwd;
		this.userEditor = userEditor;
		this.roleEditor = roleEditor;
		this.log = log;
		mainForm.setJMenuBar(menuBar);
		menuBar.add(menu);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		profile.addActionListener(this);
		menu.add(profile);
		changePass.addActionListener(this);
		menu.add(changePass);
		different.addActionListener(this);
		menu.add(different);
		quit.addActionListener(this);
		menu.add(quit);

		menu1.add(inputCourses);
		inputCourses.setActionCommand("Въвеждане на данни/Курсове");
		inputCourses.addActionListener(this);
		menu1.add(inputLectors);
		inputLectors.setActionCommand("Въвеждане на данни/Лектори");
		inputLectors.addActionListener(this);
		menu1.add(inputStudents);
		inputStudents.setActionCommand("Въвеждане на данни/Студенти");
		inputStudents.addActionListener(this);
		menu1.add(inputCoursesStudents);
		inputCoursesStudents.setActionCommand("Въвеждане на данни/Курсове/Студенти");
		inputCoursesStudents.addActionListener(this);
		menu1.add(inputGrades);
		inputGrades.setActionCommand("Въвеждане на данни/Оценки");
		inputGrades.addActionListener(this);

		menu2.add(listCourses);
		listCourses.setActionCommand("Справки/Курсове");
		listCourses.addActionListener(this);
		menu2.add(listLectors);
		listLectors.setActionCommand("Справки/Лектори");
		listLectors.addActionListener(this);
		menu2.add(listStudents);
		listStudents.setActionCommand("Справки/Студенти");
		listStudents.addActionListener(this);
		menu2.add(listCoursesStudents);
		listCoursesStudents.setActionCommand("Справки/Курсове/Студенти");
		listCoursesStudents.addActionListener(this);
		menu2.add(listGrades);
		listGrades.setActionCommand("Справки/Оценки");
		listGrades.addActionListener(this);

		menu3.add(listUsers);
		listUsers.setActionCommand("Администриране/Потребители");
		listUsers.addActionListener(this);
		menu3.add(listRoles);
		listRoles.setActionCommand("Администриране/Роли");
		listRoles.addActionListener(this);
		menu3.add(listLogs);
		listLogs.setActionCommand("Администриране/Лог записи");
		listLogs.addActionListener(this);

		panel.setLayout(cardLayout);
		panel.add(panel1, "main_form");
		panel.add(reports.rPanel, "reports");
		panel.add(courseEditor.cPanel, "courseEditor");
		panel.add(lectorEditor.lPanel, "lectorEditor");
		panel.add(studentEditor.sPanel, "studentEditor");
		panel.add(lectorEditor.lPanel, "lectorEditor");
		panel.add(coursesStudentsEditor.csPanel, "coursesStudentsEditor"); 
		panel.add(gradeEditor.gPanel, "gradeEditor");
		panel.add(userEditor.uPanel, "userEditor");
		panel.add(roleEditor.rPanel, "roleEditor");
		panel.add(log.lPanel, "log");

		mainForm.add(panel, BorderLayout.CENTER);
		mainForm.setTitle("Електронен дневник");
		mainForm.setSize(800, 600);
		mainForm.setResizable(true);
		mainForm.setLocationRelativeTo(null);
		menuBar.setVisible(true);
		mainForm.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		cardLayout.show(panel, "main_form");
		Global.logWriter("Визуализация на основната формата.");
	}

	protected void setMenuRights() {
		this.menu1.setVisible(logInOut.getUserRights()[0]);
		this.menu2.setVisible(logInOut.getUserRights()[1]);
		this.menu3.setVisible(logInOut.getUserRights()[2]);
		cardLayout.show(panel, "main_form");
	}

	protected void handleClosing() {
		Global.logWriter("Визуализация на диалогов прозоред - \"Наистина ли желаете изход от програмата?\"");
		ImageIcon icon = new ImageIcon("src/images/question.png");
		int answer = JOptionPane.showConfirmDialog(new MainForm(""), "Наистина ли желаете изход от програмата?",
				"Потвърждение", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

		switch (answer) {
		case JOptionPane.YES_OPTION:
			DBData.logLogout();
			Global.logWriter("Електронния дневник е спрян.");
			System.exit(0);
			try {
				if (Global.conn.isClosed() == false) {
					Global.conn.close();
				}
			} catch (SQLException e) {
				Global.logWriter(e, this.getClass().getName() + " - "
						+ Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
						"Системна грешка.Моля, разгледайте лог файла!");
			}
			break;
		case JOptionPane.NO_OPTION:
			break;
		}
	}

	private void handleDifferentUser() {
		if (Global.logged) {
			ImageIcon icon = new ImageIcon("src/images/question.png");
			int answer = JOptionPane.showConfirmDialog(new MainForm(""),
					"Наистина ли желаете да влезете с друг потребител?", "Потвърждение", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			Global.logWriter(
					"Визуализация на диалогов прозоред -\"Наистина ли желаете да влезете с друг потребител?\"");

			switch (answer) {
			case JOptionPane.YES_OPTION:
				DBData.logLogout();
				cardLayout.show(panel, "main_form");
				Global.logWriter("Извикване на login формата с цел смяна на потребител");
				logInOut.showLoginDialog();
				setMenuRights();
				break;
			case JOptionPane.NO_OPTION:
				break;
			}
		} else {
			DBData.logLogout();
			Global.logWriter("Извикване на login формата с с цел смяна на потребител");
			logInOut.showLoginDialog();
			setMenuRights();
		}
	}
	
	private void changePassword() {
			Global.logWriter("Визуализация на диалогов прозоред -\"Смяна на парола\"");
			cardLayout.show(panel, "main_form");
			changePwd.showChangePassDialog();
	}

	private void noUserLogMessage(String message) {
		Global.logWriter(message);
		JOptionPane.showMessageDialog(mainForm, message);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		String choice = ae.getActionCommand();
		// System.out.println(ae.getActionCommand());

		switch (choice) {
		case "Вход в Електронен дневник":
			Global.logWriter("Меню - \"Вход в Електронен дневник\"");
			handleDifferentUser();
			break;

		case "Изход":
			Global.logWriter("Меню - \"Изход\"");
			handleClosing();
			break;

		case "Потребителски профил":
			if (Global.logged) {
				Global.logWriter("Меню - \"Потребителски профил\"");
				String[] usrData = DBData.getLoggedUser(Global.user);
				if (usrData[0].equals("-1")) {
					JOptionPane.showMessageDialog(mainForm, "Потребителско име: " + usrData[1] + "\nРоля: " + usrData[2]
							+ "\nИмена: " + usrData[3] + "\nДлъжност: " + usrData[4] + "\nЕл.поща: " + usrData[5]);
				}
			} else {
				noUserLogMessage(Global.errMesssage2);
			}
			break;
			
		case "Смяна на парола":
			if (Global.logged) {
				cardLayout.show(panel, "main_form");
				changePassword();
				Global.logWriter("Меню - \"Смяна на парола\"");
//				String[] usrData = DBData.getLoggedUser(Global.user);
//				if (usrData[0].equals("-1")) {
//					
//				}
			} else {
				noUserLogMessage(Global.errMesssage2);
			}
			break;

		case "Администриране/Потребители":
			if (Global.logged) {
				userEditor.showUsers();
				cardLayout.show(panel, "userEditor");
				Global.logWriter("Меню - \"Администриране/Потребители\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Администриране/Роли":
			if (Global.logged) {
				roleEditor.showRoles();
				cardLayout.show(panel, "roleEditor");
				Global.logWriter("Меню - \"Администриране/Роли\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;
			
		case "Администриране/Лог записи":
			if (Global.logged) {
				log.readLog("");
				cardLayout.show(panel, "log");
				Global.logWriter("Меню - \"Администриране/Лог записи\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Справки/Студенти":
			if (Global.logged) {
				reports.showStudents(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("Меню - \"Справки/Студенти\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Справки/Лектори":
			if (Global.logged) {
				reports.showLectors(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("Меню - \"Справки/Лектори\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Справки/Курсове":
			if (Global.logged) {
				reports.showCourses(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("Меню - \"Справки/Курсове\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Справки/Оценки":
			if (Global.logged) {
				reports.showGrades(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("Меню - \"Справки/Оценки\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Справки/Курсове/Студенти":
			if (Global.logged) {
				reports.showCoursesStudents(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("Меню - \"Справки/Курсове/Студенти\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Въвеждане на данни/Курсове":
			if (Global.logged) {
				courseEditor.showCourses(false);
				cardLayout.show(panel, "courseEditor");
				Global.logWriter("Меню - \"Въвеждане на данни/Курсове\"");
				Global.logWriter("Визуализация на input_courses формата.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Въвеждане на данни/Лектори":
			if (Global.logged) {
				lectorEditor.showLectors(false);
				cardLayout.show(panel, "lectorEditor");
				Global.logWriter("Меню - \"Въвеждане на данни/Лектори\"");
				Global.logWriter("Визуализация на input_lectors формата.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Въвеждане на данни/Студенти":
			if (Global.logged) {
				studentEditor.showStudents(false);
				cardLayout.show(panel, "studentEditor");
				Global.logWriter("Меню - \"Въвеждане на данни/Студенти\"");
				Global.logWriter("Визуализация на input_students формата.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "Въвеждане на данни/Курсове/Студенти":
			if (Global.logged) {
				coursesStudentsEditor.showCoursesStudents(false);
				cardLayout.show(panel, "coursesStudentsEditor");
				Global.logWriter("Меню - \"Въвеждане на данни/Курсове/Студенти\"");
				Global.logWriter("Визуализация на input_courses_students формата.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;
			
		case "Въвеждане на данни/Оценки":
			if (Global.logged) {
				gradeEditor.showGrades(false);
				cardLayout.show(panel, "gradeEditor");
				Global.logWriter("Меню - \"Въвеждане на данни/Оценки\"");
				Global.logWriter("Визуализация на input_grades формата.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		}

	}

}

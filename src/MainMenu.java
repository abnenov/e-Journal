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
	private JMenu menu = new JMenu("������ �� �����������");
	protected JMenu menu1 = new JMenu("��������� �� �����");
	protected JMenu menu2 = new JMenu("�������");
	protected JMenu menu3 = new JMenu("��������������");
	private JMenuItem profile = new JMenuItem("������������� ������");
	private JMenuItem changePass = new JMenuItem("����� �� ������");
	private JMenuItem different = new JMenuItem("���� � ���������� �������");
	private JMenuItem quit = new JMenuItem("�����");
	private JMenuItem inputCourses = new JMenuItem("�������");
	private JMenuItem inputLectors = new JMenuItem("�������");
	private JMenuItem inputStudents = new JMenuItem("��������");
	private JMenuItem inputCoursesStudents = new JMenuItem("�������/��������");
	private JMenuItem inputGrades = new JMenuItem("������");
	private JMenuItem listCourses = new JMenuItem("�������");
	private JMenuItem listLectors = new JMenuItem("�������");
	private JMenuItem listStudents = new JMenuItem("��������");
	private JMenuItem listCoursesStudents = new JMenuItem("�������/��������");
	private JMenuItem listGrades = new JMenuItem("������");
	private JMenuItem listUsers = new JMenuItem("�����������");
	private JMenuItem listRoles = new JMenuItem("����");
	private JMenuItem listLogs = new JMenuItem("��� ������");

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
		inputCourses.setActionCommand("��������� �� �����/�������");
		inputCourses.addActionListener(this);
		menu1.add(inputLectors);
		inputLectors.setActionCommand("��������� �� �����/�������");
		inputLectors.addActionListener(this);
		menu1.add(inputStudents);
		inputStudents.setActionCommand("��������� �� �����/��������");
		inputStudents.addActionListener(this);
		menu1.add(inputCoursesStudents);
		inputCoursesStudents.setActionCommand("��������� �� �����/�������/��������");
		inputCoursesStudents.addActionListener(this);
		menu1.add(inputGrades);
		inputGrades.setActionCommand("��������� �� �����/������");
		inputGrades.addActionListener(this);

		menu2.add(listCourses);
		listCourses.setActionCommand("�������/�������");
		listCourses.addActionListener(this);
		menu2.add(listLectors);
		listLectors.setActionCommand("�������/�������");
		listLectors.addActionListener(this);
		menu2.add(listStudents);
		listStudents.setActionCommand("�������/��������");
		listStudents.addActionListener(this);
		menu2.add(listCoursesStudents);
		listCoursesStudents.setActionCommand("�������/�������/��������");
		listCoursesStudents.addActionListener(this);
		menu2.add(listGrades);
		listGrades.setActionCommand("�������/������");
		listGrades.addActionListener(this);

		menu3.add(listUsers);
		listUsers.setActionCommand("��������������/�����������");
		listUsers.addActionListener(this);
		menu3.add(listRoles);
		listRoles.setActionCommand("��������������/����");
		listRoles.addActionListener(this);
		menu3.add(listLogs);
		listLogs.setActionCommand("��������������/��� ������");
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
		mainForm.setTitle("���������� �������");
		mainForm.setSize(800, 600);
		mainForm.setResizable(true);
		mainForm.setLocationRelativeTo(null);
		menuBar.setVisible(true);
		mainForm.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		cardLayout.show(panel, "main_form");
		Global.logWriter("������������ �� ��������� �������.");
	}

	protected void setMenuRights() {
		this.menu1.setVisible(logInOut.getUserRights()[0]);
		this.menu2.setVisible(logInOut.getUserRights()[1]);
		this.menu3.setVisible(logInOut.getUserRights()[2]);
		cardLayout.show(panel, "main_form");
	}

	protected void handleClosing() {
		Global.logWriter("������������ �� �������� �������� - \"�������� �� ������� ����� �� ����������?\"");
		ImageIcon icon = new ImageIcon("src/images/question.png");
		int answer = JOptionPane.showConfirmDialog(new MainForm(""), "�������� �� ������� ����� �� ����������?",
				"������������", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

		switch (answer) {
		case JOptionPane.YES_OPTION:
			DBData.logLogout();
			Global.logWriter("����������� ������� � �����.");
			System.exit(0);
			try {
				if (Global.conn.isClosed() == false) {
					Global.conn.close();
				}
			} catch (SQLException e) {
				Global.logWriter(e, this.getClass().getName() + " - "
						+ Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
						"�������� ������.����, ����������� ��� �����!");
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
					"�������� �� ������� �� ������� � ���� ����������?", "������������", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			Global.logWriter(
					"������������ �� �������� �������� -\"�������� �� ������� �� ������� � ���� ����������?\"");

			switch (answer) {
			case JOptionPane.YES_OPTION:
				DBData.logLogout();
				cardLayout.show(panel, "main_form");
				Global.logWriter("��������� �� login ������� � ��� ����� �� ����������");
				logInOut.showLoginDialog();
				setMenuRights();
				break;
			case JOptionPane.NO_OPTION:
				break;
			}
		} else {
			DBData.logLogout();
			Global.logWriter("��������� �� login ������� � � ��� ����� �� ����������");
			logInOut.showLoginDialog();
			setMenuRights();
		}
	}
	
	private void changePassword() {
			Global.logWriter("������������ �� �������� �������� -\"����� �� ������\"");
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
		case "���� � ���������� �������":
			Global.logWriter("���� - \"���� � ���������� �������\"");
			handleDifferentUser();
			break;

		case "�����":
			Global.logWriter("���� - \"�����\"");
			handleClosing();
			break;

		case "������������� ������":
			if (Global.logged) {
				Global.logWriter("���� - \"������������� ������\"");
				String[] usrData = DBData.getLoggedUser(Global.user);
				if (usrData[0].equals("-1")) {
					JOptionPane.showMessageDialog(mainForm, "������������� ���: " + usrData[1] + "\n����: " + usrData[2]
							+ "\n�����: " + usrData[3] + "\n��������: " + usrData[4] + "\n��.����: " + usrData[5]);
				}
			} else {
				noUserLogMessage(Global.errMesssage2);
			}
			break;
			
		case "����� �� ������":
			if (Global.logged) {
				cardLayout.show(panel, "main_form");
				changePassword();
				Global.logWriter("���� - \"����� �� ������\"");
//				String[] usrData = DBData.getLoggedUser(Global.user);
//				if (usrData[0].equals("-1")) {
//					
//				}
			} else {
				noUserLogMessage(Global.errMesssage2);
			}
			break;

		case "��������������/�����������":
			if (Global.logged) {
				userEditor.showUsers();
				cardLayout.show(panel, "userEditor");
				Global.logWriter("���� - \"��������������/�����������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "��������������/����":
			if (Global.logged) {
				roleEditor.showRoles();
				cardLayout.show(panel, "roleEditor");
				Global.logWriter("���� - \"��������������/����\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;
			
		case "��������������/��� ������":
			if (Global.logged) {
				log.readLog("");
				cardLayout.show(panel, "log");
				Global.logWriter("���� - \"��������������/��� ������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "�������/��������":
			if (Global.logged) {
				reports.showStudents(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("���� - \"�������/��������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "�������/�������":
			if (Global.logged) {
				reports.showLectors(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("���� - \"�������/�������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "�������/�������":
			if (Global.logged) {
				reports.showCourses(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("���� - \"�������/�������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "�������/������":
			if (Global.logged) {
				reports.showGrades(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("���� - \"�������/������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "�������/�������/��������":
			if (Global.logged) {
				reports.showCoursesStudents(true);
				cardLayout.show(panel, "reports");
				Global.logWriter("���� - \"�������/�������/��������\"");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "��������� �� �����/�������":
			if (Global.logged) {
				courseEditor.showCourses(false);
				cardLayout.show(panel, "courseEditor");
				Global.logWriter("���� - \"��������� �� �����/�������\"");
				Global.logWriter("������������ �� input_courses �������.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "��������� �� �����/�������":
			if (Global.logged) {
				lectorEditor.showLectors(false);
				cardLayout.show(panel, "lectorEditor");
				Global.logWriter("���� - \"��������� �� �����/�������\"");
				Global.logWriter("������������ �� input_lectors �������.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "��������� �� �����/��������":
			if (Global.logged) {
				studentEditor.showStudents(false);
				cardLayout.show(panel, "studentEditor");
				Global.logWriter("���� - \"��������� �� �����/��������\"");
				Global.logWriter("������������ �� input_students �������.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		case "��������� �� �����/�������/��������":
			if (Global.logged) {
				coursesStudentsEditor.showCoursesStudents(false);
				cardLayout.show(panel, "coursesStudentsEditor");
				Global.logWriter("���� - \"��������� �� �����/�������/��������\"");
				Global.logWriter("������������ �� input_courses_students �������.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;
			
		case "��������� �� �����/������":
			if (Global.logged) {
				gradeEditor.showGrades(false);
				cardLayout.show(panel, "gradeEditor");
				Global.logWriter("���� - \"��������� �� �����/������\"");
				Global.logWriter("������������ �� input_grades �������.");
			} else {
				noUserLogMessage(Global.errMesssage3);
			}
			break;

		}

	}

}

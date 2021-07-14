import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

class MainForm extends JFrame {

	private static final long serialVersionUID = -1935322869439928082L;

	public MainForm(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		if (Global.firstTimeStart) {
			Global.firstTimeStart = false;
			Global.logWriter("Електронният дневник е стартиран.");
		}

	}

	public static void main(String[] args) {
		MainForm myApp = new MainForm("Електронен дневник");
		Reports reports = new Reports();
		CourseEditor courseEditor = new CourseEditor();
		LectorEditor lectorEditor = new LectorEditor();
		StudentEditor studentEditor = new StudentEditor();
		CoursesStudentsEditor coursesStudentsEditor = new CoursesStudentsEditor();
		GradeEditor gradeEditor = new GradeEditor();
		LogInOut logInOut = new LogInOut();
		UserEditor userEditor = new UserEditor();
		RoleEditor roleEditor = new RoleEditor();
		ChangePwd changePwd = new ChangePwd();
		Log log = new Log();
		MainMenu mainMenu = new MainMenu(myApp, reports, courseEditor, lectorEditor, studentEditor,
				coursesStudentsEditor, gradeEditor, logInOut, changePwd, userEditor, roleEditor, log);
		myApp.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainMenu.handleClosing();
			}
		});
		myApp.setVisible(true);
		logInOut.showLoginDialog();
		if (!Global.user.trim().equals("")&Global.logged) {
			mainMenu.setMenuRights();
		}
	}

}
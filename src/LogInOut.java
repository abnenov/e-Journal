import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class LogInOut {
//	protected String Global.user = "";
	protected String pwd = "";

	protected JDialog dialog = new JDialog();
	private JPanel panel2 = new JPanel(new GridLayout(3, 2));
	private JLabel userLabel = new JLabel("    Потребител", SwingConstants.LEFT);
	private JLabel passwordLabel = new JLabel("    Парола", SwingConstants.LEFT);
	private JTextField userTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("Вход");
	private JButton resetButton = new JButton("Изчисти");
	private boolean[] userRigths = { false, false, false };

	LogInOut() {
		dialog.setTitle("Вход в Електронен дневник");
		dialog.setModal(true);
		panel2.add(userLabel);
		panel2.add(userTextField);
		panel2.add(passwordLabel);
		panel2.add(passwordField);
		panel2.add(loginButton);
		panel2.add(resetButton);
		// dialog.setDefaultCloseOperation(0);
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				if (Global.logged == false) {
					Global.user = userTextField.getText();
					pwd = passwordField.getText();
//					pwd = "Nn123456";
//					Global.user = "admin";
//					Global.user = user;
					if (DBData.checkDriver()) {
						if (DBData.login(Global.user, pwd)) {
							if (!DBData.getUserStatus(Global.user) || !DBData.getRoleStatus(DBData.getLoggedUser(Global.user)[2])) {
								JOptionPane.showMessageDialog(panel2, Global.errMesssage7);
							} else {
								JOptionPane.showMessageDialog(panel2, "Успешно влизане.");
								Global.logWriter("Действие - \"Успешно влизане\"");
								Global.logged = true;
								userTextField.setText("");
								passwordField.setText("");
								dialog.dispose();
							}
						}
					}
				}
			}
		});

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				userTextField.setText("");
				passwordField.setText("");
				clearCredentials();
				Global.logWriter("Действие - \"Изчисти полета потребител и парола\"");
			}
		});

		dialog.add(panel2);
		dialog.setSize(400, 130);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
	}

	private void clearCredentials() {
		Global.user = "";
//		Global.user = user;
		pwd = "";
	}

	protected boolean[] getUserRights() {
		String[] temp = new String[3];
		temp = DBData.readUserRights(Global.user);
		for (int i = 0; i < 3; i++) {
			if (temp[i].toLowerCase().equals("да")) {
				this.userRigths[i] = true;
			} else {
				this.userRigths[i] = false;
			}
		}
		return this.userRigths;
	}

	protected void showLoginDialog() {
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}
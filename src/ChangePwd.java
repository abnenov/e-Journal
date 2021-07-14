import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

class ChangePwd {
	// protected String Global.user = "";
	protected String pwd = "";

	protected JDialog dialog = new JDialog();
	private JPanel panel2 = new JPanel(new GridLayout(4, 2));
	private JLabel oldPwdLabel = new JLabel("    Стара парола", SwingConstants.LEFT);
	private JLabel newPwdLabel = new JLabel("    Нова парола", SwingConstants.LEFT);
	private JLabel repeatNewPwdLabel = new JLabel("    Повторете новата парола", SwingConstants.LEFT);
	private JPasswordField oldPwdField = new JPasswordField(10);
	private JPasswordField newPwdField = new JPasswordField(10);
	private JPasswordField repeatNewPwdField = new JPasswordField(10);
	private JButton changeButton = new JButton("Промяна");
	private JButton resetButton = new JButton("Изчисти");
	private boolean[] userRigths = { false, false, false };

	ChangePwd() {
		dialog.setTitle("Смяна на парола");
		dialog.setModal(true);
		panel2.add(oldPwdLabel);
		panel2.add(oldPwdField);
		panel2.add(newPwdLabel);
		panel2.add(newPwdField);
		panel2.add(repeatNewPwdLabel);
		panel2.add(repeatNewPwdField);

		panel2.add(changeButton);
		panel2.add(resetButton);
		// dialog.setDefaultCloseOperation(0);

		changeButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				if (DBData.checkOldPassword(Global.user, oldPwdField.getText())) {
					if (newPwdField.getText().equals(repeatNewPwdField.getText())) {
						if (isValidPassword(newPwdField.getText())) {
							DBData.changeUserPassword(Global.user,newPwdField.getText());
							JOptionPane.showMessageDialog(panel2, "Паролата Ви е сменена успешно.");
							Global.logWriter("Действие - \"Успешна смяна на парола\"");
							dialog.dispose();
						} else {
							JOptionPane.showMessageDialog(panel2,
									"Паролата трябва да съдържа минимум 10 символа, поне една главна, малка букви и поне една цифра!");
							Global.logWriter("Действие - \"Паролите не съвпадат при смяна на парола\"");
						}
					}
					else {
						JOptionPane.showMessageDialog(panel2, "Паролите не съвпадат");
						Global.logWriter("Действие - \"Паролите не съвпадат при смяна на парола\"");
					}

				} 

			}
		});

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearFields();
			}
		});

		dialog.add(panel2);
		dialog.setSize(500, 160);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
	}

	// at least 10 digits {10,}
	// at least one number (?=.*\d)
	// at least one lowercase (?=.*[a-z])
	// at least one uppercase (?=.*[A-Z])
	// at least one special character (?=.*[@#$%^&+=])
	// No space [^\s]
	private boolean isValidPassword(String name) {
		String nameRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$";
		return name.matches(nameRegex);
	}

	private void clearFields() {
		newPwdField.setText("");
		repeatNewPwdField.setText("");
		oldPwdField.setText("");
		Global.logWriter("Действие - \"Изчисти полета потребител и парола\"");
	}

	private void clearCredentials() {
		Global.user = "";
		// Global.user = user;
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

	protected void showChangePassDialog() {
		clearFields();
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}

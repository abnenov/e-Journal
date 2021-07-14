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
	private JLabel oldPwdLabel = new JLabel("    ����� ������", SwingConstants.LEFT);
	private JLabel newPwdLabel = new JLabel("    ���� ������", SwingConstants.LEFT);
	private JLabel repeatNewPwdLabel = new JLabel("    ��������� ������ ������", SwingConstants.LEFT);
	private JPasswordField oldPwdField = new JPasswordField(10);
	private JPasswordField newPwdField = new JPasswordField(10);
	private JPasswordField repeatNewPwdField = new JPasswordField(10);
	private JButton changeButton = new JButton("�������");
	private JButton resetButton = new JButton("�������");
	private boolean[] userRigths = { false, false, false };

	ChangePwd() {
		dialog.setTitle("����� �� ������");
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
							JOptionPane.showMessageDialog(panel2, "�������� �� � ������� �������.");
							Global.logWriter("�������� - \"������� ����� �� ������\"");
							dialog.dispose();
						} else {
							JOptionPane.showMessageDialog(panel2,
									"�������� ������ �� ������� ������� 10 �������, ���� ���� ������, ����� ����� � ���� ���� �����!");
							Global.logWriter("�������� - \"�������� �� �������� ��� ����� �� ������\"");
						}
					}
					else {
						JOptionPane.showMessageDialog(panel2, "�������� �� ��������");
						Global.logWriter("�������� - \"�������� �� �������� ��� ����� �� ������\"");
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
		Global.logWriter("�������� - \"������� ������ ���������� � ������\"");
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
			if (temp[i].toLowerCase().equals("��")) {
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

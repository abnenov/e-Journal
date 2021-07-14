import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

class UserEditor extends Reports {

	private String[] dialogParams = new String[9];
	private String comboFirstItem = "Моля, изберете роля - >";
	private String emptyPassword = "____________";
	private String beforeUser = "";

	protected JPanel uPanel = new JPanel(new BorderLayout());
	private JPanel passPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();
	private JPanel panel = new JPanel(new GridLayout(8, 2));

	private JLabel userNameLabel = new JLabel("      Потребителско име");
	private JLabel passwordLabel = new JLabel("      Случайно генерирана парола");
	private JPasswordField password = new JPasswordField(generatePassword(), 10);
	private JTextField userName = new JTextField();
	private JLabel userNamesLabel = new JLabel("      Имена на потребителя");
	private JTextField userNames = new JTextField();
	private JLabel userPositionLabel = new JLabel("      Длъжност");
	private JTextField userPosition = new JTextField();
	private JLabel userEmailLabel = new JLabel("      Електронна поща");
	private JTextField userEmail = new JTextField();
	private JLabel userRoleLabel = new JLabel("      Роля");
	private JComboBox<String> userRole = new JComboBox<String>();
	private JLabel activeUserLabel = new JLabel("      Активен");
	private JComboBox<String> activeUser = new JComboBox<String>();
	private JButton generate = new JButton("Генерирай");
	private JButton show = new JButton("Покажи");
	private JCheckBox change = new JCheckBox("Ресет");
	private JButton insert = new JButton();
	private JPanel uPanel1 = new JPanel();
	private JButton button1 = new JButton(Global.title40);
	private JButton button2 = new JButton(Global.title41);
	private JButton button3 = new JButton(Global.title42);
	private JButton clear = new JButton("Изчисти");

	UserEditor() {
		userName.setDocument(new JTextFieldLimit(30));
		userNames.setDocument(new JTextFieldLimit(100));
		userPosition.setDocument(new JTextFieldLimit(40));
		userEmail.setDocument(new JTextFieldLimit(30));
		password.putClientProperty("JPasswordField.cutCopyAllowed", true);
		password.setBorder(new LineBorder(Color.gray, 1, true));
		password.setBackground(Color.white);
		password.setEditable(false);
		uPanel1.setLayout(new BoxLayout(uPanel1, BoxLayout.X_AXIS));
		passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.X_AXIS));
		uPanel1.add(button1);
		uPanel1.add(button2);
		uPanel1.add(button3);
		uPanel.add(uPanel1, BorderLayout.PAGE_START);
		uPanel.add(pg1, BorderLayout.CENTER);

		panel.add(userNameLabel);
		panel.add(userName);
		panel.add(passwordLabel);
		panel.add(passPanel);
		panel.add(userNamesLabel);
		panel.add(userNames);
		panel.add(userPositionLabel);
		panel.add(userPosition);
		panel.add(userEmailLabel);
		panel.add(userEmail);
		panel.add(userRoleLabel);
		panel.add(userRole);
		panel.add(activeUserLabel);
		panel.add(activeUser);
		panel.add(insert);
		panel.add(clear);

		activeUser.addItem("Да");
		activeUser.addItem("Не");
		activeUser.setSelectedIndex(1);
		userRole.addItem("Моля, изберете роля - >");
		userRole.setSelectedIndex(0);

		mainDialog.add(panel);
		mainDialog.setSize(700, 210);
		mainDialog.setResizable(false);
		mainDialog.setLocationRelativeTo(null);

		userRole.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// System.out.println(e.getSource());
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// System.out.println(e.getSource());
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				fillRoles(comboFirstItem);
			}
		});

		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showButtonAction();
			}
		});

		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				password.setText(generatePassword());
			}
		});

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (Global.logged) {
					clearFields();
					dialogParams[0] = Global.title40;
					dialogParams[1] = "";
					dialogParams[2] = "";
					dialogParams[3] = "";
					dialogParams[4] = "";
					dialogParams[5] = Global.title9;
					dialogParams[6] = "0";
					insert_editUserDialog(dialogParams);
				} else {
					noUserLogMessage(Global.errMesssage3);
				}
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				if (Global.logged) {
					change.setSelected(false);
					show.setText("Покажи");
					password.setEchoChar('*');
					String[] result = new String[6];
					ArrayList<Integer> result1 = new ArrayList<Integer>();
					result1 = getTblIds();
					if (result1.size() > 1) {
						Global.logWriter(Global.errMesssage18);
						Object[] options = { Global.title12 };
						int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title3,
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					} else {
						if (!result1.isEmpty()) {
							result = DBData.getUserById(result1.get(0));
							beforeUser = DBData.getUserById(getTblIds().get(0))[0];
							dialogParams[0] = Global.title41;
							dialogParams[1] = result[0];
							dialogParams[2] = result[1];
							dialogParams[3] = result[2];
							dialogParams[4] = result[3];
							dialogParams[8] = result[4];
							dialogParams[7] = result[5];
							dialogParams[5] = Global.title10;
							dialogParams[6] = "1";
							password.setText(emptyPassword);
							insert_editUserDialog(dialogParams);
							Global.logWriter(Global.title44);
						} else {
							Global.logWriter(Global.errMesssage18);
							Object[] options = { "Разбрах" };
							int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage3, Global.title3,
									JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
						}
					}
				} else {
					noUserLogMessage(Global.errMesssage3);
				}
			}
		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (Global.logged) {
					deleteUserDialog();
				} else {
					noUserLogMessage(Global.errMesssage3);
				}
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearFields();
			}
		});

		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insert_edit(dialogParams);
			}
		});

		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (change.isSelected()) {
					password.setText(generatePassword());
				} else {
					password.setText(emptyPassword);
				}
			}
		});

	}

	private void showButtonAction() {
		if (show.getText().equals("Покажи")) {
			show.setText("Скрий");
			password.setEchoChar((char) 0);
		} else {
			show.setText("Покажи");
			password.setEchoChar('*');
		}
	}

	private String generatePassword() {
		int length = 12;
		// String symbol = "-/.^&*_!@%=+>)";
		String symbol = "-._!)@";
		String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String small_letter = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String finalString = cap_letter + small_letter + numbers + symbol;
		Random random = new Random();
		char[] password = new char[length];
		for (int i = 0; i < length; i++) {
			password[i] = finalString.charAt(random.nextInt(finalString.length()));

		}
		return String.valueOf(password);
	}

	private void clearFields() {
		show.setText("Покажи");
		password.setEchoChar('*');
		userName.setText("");
		userNames.setText("");
		userPosition.setText("");
		userEmail.setText("");
		userRole.setSelectedIndex(0);
		List<Component> componentList = Arrays.asList(panel.getComponents());
		if (!componentList.contains(change)) {
			change.setSelected(false);
			password.setText(emptyPassword);
		} else {
			password.setText(generatePassword());
		}
		activeUser.setSelectedIndex(1);
	}

	private void fillRoles(String comboFirstItem) {
		this.userRole.removeAllItems();
		this.userRole.addItem(comboFirstItem);
		for (String i : DBData.getRoles()) {
			userRole.addItem(i);
		}
		userRole.setSelectedIndex(0);
	}

	private boolean isNamePosition(String name) {
		String nameRegex = "[a-zA-Zа-яА-Я -]+";
		return name.matches(nameRegex);
	}

	private boolean isUser(String name) {
		String nameRegex = "^[A-Za-z]{3,}[A-Za-z0-9]";
		return name.matches(nameRegex);
	}

	private void noUserLogMessage(String message) {
		Global.logWriter(message);
		JOptionPane.showMessageDialog(uPanel, message);
	}

	private boolean isEmail(String email) {
		String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}"
				+ "~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\"
				+ "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
				+ "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)"
				+ "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0"
				+ "c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		return email.matches(emailRegex);
	}

	private void insert_edit(String[] dialogParams) {
		try {
			if (!isUser(userName.getText())) {
				throw new Exception("1");
			}
			if (!isNamePosition(userNames.getText())) {
				throw new Exception("2");
			}
			if (!isNamePosition(userPosition.getText())) {
				throw new Exception("3");
			}
			if (!isEmail(userEmail.getText())) {
				throw new Exception("4");
			}
			if (userRole.getSelectedItem().equals(comboFirstItem)) {
				throw new Exception("5");
			}
			if (dialogParams[6] == "0") {
				if (DBData.checkUserExists(userName.getText())) {
					throw new Exception("6");
				}
				DBData.insertUsers(userName.getText(), String.valueOf(userRole.getSelectedItem()), userNames.getText(),
						userPosition.getText(), userEmail.getText(), String.valueOf(activeUser.getSelectedItem()),
						password.getText());
				showUsers();
			} else {
				ArrayList<Integer> result = new ArrayList<Integer>();
				result = getTblIds();
				if (!DBData.checkOtherUsers(result.get(0), userName.getText()).equals("")) {
					throw new Exception("7");
				}
				Global.logWriter(Global.title44);
				Object[] options = { "Промени", "Отказ" };
				int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage3, Global.title22,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (dialog == JOptionPane.YES_OPTION) {
					if (DBData.getUserById(result.get(0))[0].equals(Global.user)) {
						Global.logWriter("Редактиране на логнат потребител!");
						JOptionPane.showMessageDialog(mainDialog, "Редактирате логнат в момента потребител: "
								+ Global.user
								+ ". За да е функционална системата е необходимо е да се логнете отново след това.",
								"Проблем!", JOptionPane.WARNING_MESSAGE);
						Global.logged = false;
						uPanel.setVisible(false);
					}
					if (change.isSelected()) {
						DBData.updateUser(userName.getText(), String.valueOf(userRole.getSelectedItem()),
								userNames.getText(), userPosition.getText(), userEmail.getText(),
								String.valueOf(activeUser.getSelectedItem()), password.getText(), result.get(0),
								beforeUser);
					} else {
						DBData.updateUser(userName.getText(), String.valueOf(userRole.getSelectedItem()),
								userNames.getText(), userPosition.getText(), userEmail.getText(),
								String.valueOf(activeUser.getSelectedItem()), "", result.get(0), beforeUser);
					}

					showUsers();
					mainDialog.dispose();
				}
			}
		} catch (Exception e) {
			if (e.getMessage().equals("1")) {
				Global.logWriter("Невалидно потребителско име!");
				JOptionPane.showMessageDialog(mainDialog,
						"Моля, въведете валидно потребителско име.Допускат се латински букви и цифри.Трябва да започва с поне 4 букви!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("2")) {
				Global.logWriter("Невалидни имена!");
				JOptionPane.showMessageDialog(mainDialog,
						"Моля, въведете валидни имена(допускат се букви интервали и \"-\")!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("3")) {
				Global.logWriter("Невалидена длъжност!");
				JOptionPane.showMessageDialog(mainDialog, "Невалидно длъжност(допускат се букви интервали и \"-\")!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("4")) {
				Global.logWriter("Невалиден адрес на електронна поща!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валиден адрес на електронна поща!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("5")) {
				Global.logWriter("Не е избрана роля от падащият списък при редактиране на потребители!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, изберете роля от падащия списък!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("6")) {
				Global.logWriter("Опит за въвеждане на съществуващ потребител!");
				JOptionPane.showMessageDialog(mainDialog, "Вече съществува потребител с това потребителско име!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("7")) {
				Global.logWriter("Опит за въвеждане на съществуващ потребител!");
				JOptionPane.showMessageDialog(mainDialog,
						"Вече съществува потребител с това потребителско име в базата!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void insert_editUserDialog(String[] dialogParams) {
		mainDialog.setTitle(dialogParams[0]);
		mainDialog.setModal(true);
		userName.setText(dialogParams[1]);
		userNames.setText(dialogParams[2]);
		userPosition.setText(dialogParams[3]);
		userEmail.setText(dialogParams[4]);
		if (dialogParams[6].equals("1")) {
			userRole.getModel().setSelectedItem(dialogParams[8]);
			activeUser.getModel().setSelectedItem(dialogParams[7]);
			passPanel.add(password);
			passPanel.add(show);
			passPanel.add(generate);
			passPanel.add(change);
		} else {
			userRole.setSelectedIndex(0);
			passPanel.add(password);
			passPanel.add(show);
			passPanel.add(generate);
			List<Component> componentList = Arrays.asList(panel.getComponents());
			if (!componentList.contains(change)) {
				passPanel.remove(change);
			}
		}
		insert.setText(dialogParams[5]);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteUserDialog() {
		String messages = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = getTblIds();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title45);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (Integer i : result) {
					if (DBData.getUserById(i)[0].equals(Global.user)) {
						Global.logWriter("Опит за изтриване на логнат потребител!");
						JOptionPane.showMessageDialog(mainDialog,
								"Потребител: " + Global.user + " не може да се изтрие в момента, тъй като е логнат!",
								"Проблем!", JOptionPane.WARNING_MESSAGE);
					} else {
						DBData.deleteUser(i);
					}
				}
				showUsers();
			}
		} else {
			Global.logWriter(Global.errMesssage9);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
}
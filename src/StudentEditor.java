import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class StudentEditor extends Reports {

	private String[] dialogParams = new String[8];

	protected JPanel sPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();
	private JPanel panel = new JPanel(new GridLayout(6, 2));

	private JLabel studentNameLabel = new JLabel("      Имена");
	private JTextField studentName = new JTextField();
	private JLabel studentEGNLabel = new JLabel("      ЕГН");
	private JTextField studentEGN = new JTextField();
	private JLabel studentPhoneLabel = new JLabel("      Телефон");
	private JTextField studentPhone = new JTextField();
	private JLabel studentAddressLabel = new JLabel("      Адрес");
	private JTextField studentAddress = new JTextField();
	private JLabel studentEmailLabel = new JLabel("      Електронна поща");
	private JTextField studentEmail = new JTextField();
	private JButton insert = new JButton();
	private JPanel sPanel1 = new JPanel();
	private JButton button1 = new JButton(Global.title29);
	private JButton button2 = new JButton(Global.title30);
	private JButton button3 = new JButton(Global.title31);
	private JButton clear = new JButton("Изчисти");

	StudentEditor() {
		studentName.setDocument(new JTextFieldLimit(100));
		studentEGN.setDocument(new JTextFieldLimit(10));
		studentAddress.setDocument(new JTextFieldLimit(100));
		sPanel1.setLayout(new BoxLayout(sPanel1, BoxLayout.X_AXIS));
		sPanel1.add(button1);
		sPanel1.add(button2);
		sPanel1.add(button3);
		sPanel.add(sPanel1, BorderLayout.PAGE_START);
		sPanel.add(pg1, BorderLayout.CENTER);

		panel.add(studentNameLabel);
		panel.add(studentName);
		panel.add(studentEGNLabel);
		panel.add(studentEGN);
		panel.add(studentPhoneLabel);
		panel.add(studentPhone);
		panel.add(studentAddressLabel);
		panel.add(studentAddress);
		panel.add(studentEmailLabel);
		panel.add(studentEmail);
		panel.add(insert);
		panel.add(clear);

		mainDialog.add(panel);
		mainDialog.setSize(700, 210);
		mainDialog.setResizable(false);
		mainDialog.setLocationRelativeTo(null);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dialogParams[0] = Global.title29;
				dialogParams[1] = "";
				dialogParams[2] = "";
				dialogParams[3] = "";
				dialogParams[4] = "";
				dialogParams[5] = Global.title9;
				dialogParams[6] = "0";
				insert_editStudentDialog(dialogParams);
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				String[] result = new String[5];

				ArrayList<Integer> result1 = new ArrayList<Integer>();
				result1 = getTblIds();
				if (result1.size() > 1) {
					Global.logWriter(Global.errMesssage10);
					Object[] options = { Global.title12 };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else {
					if (!result1.isEmpty()) {
						result = DBData.getStudentById(String.valueOf(result1.get(0)));
						dialogParams[0] = Global.title35;
						dialogParams[1] = result[0];
						dialogParams[2] = result[1];
						dialogParams[3] = result[2];
						dialogParams[4] = result[3];
						dialogParams[7] = result[4];
						dialogParams[5] = Global.title10;
						dialogParams[6] = "1";
						insert_editStudentDialog(dialogParams);
						Global.logWriter(Global.title34);
					} else {
						Global.logWriter(Global.errMesssage14);
						Object[] options = { "Разбрах" };
						int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage3, Global.title3,
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					}
				}
			}

		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteStudentDialog();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				studentName.setText("");
				studentEGN.setText("");
				studentPhone.setText("");
				studentAddress.setText("");
				studentEmail.setText("");
			}
		});

		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insert_edit(dialogParams);
			}
		});

	}

	private void insert_edit(String[] dialogParams) {
		try {
			if (!Global.isName(studentName.getText())) {
				throw new Exception("1");
			}
			if (!Global.isEGN(studentEGN.getText())) {
				throw new Exception("2");
			}
			if (!Global.isMobilePhone(studentPhone.getText())) {
				throw new Exception("3");
			}
			if (!Global.isEmail(studentEmail.getText())) {
				throw new Exception("4");
			}
			if (dialogParams[6] == "0") {
				if (!DBData.checkStudent(studentEGN.getText()).equals("")) {
					throw new Exception("5");
				}
				DBData.insertStudents(studentName.getText(), studentEGN.getText(), studentPhone.getText(),
						studentAddress.getText(), studentEmail.getText());
				showStudents(false);
			} else {
				ArrayList<Integer> result = new ArrayList<Integer>();
				result = getTblIds();
				if (!DBData.checkOtherStudents(result.get(0), studentEGN.getText()).equals("")) {
					throw new Exception("6");
				}
				Global.logWriter(Global.title19);
				Object[] options = { "Промени", "Отказ" };
				int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage3, Global.title22,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (dialog == JOptionPane.YES_OPTION) {
					DBData.updateStudent(result.get(0), studentName.getText(), studentEGN.getText(),
							studentPhone.getText(), studentAddress.getText(), studentEmail.getText());
					showStudents(false);
					mainDialog.dispose();
				}
			}
		} catch (Exception e) {
			if (e.getMessage().equals("1")) {
				Global.logWriter("Невалидно име на студент!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валидно име на студент!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("2")) {
				Global.logWriter("Невалидно ЕГН!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валидно ЕГН!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("3")) {
				Global.logWriter("Невалиден телефонен номер!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валиден телефонен номер!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("4")) {
				Global.logWriter("Невалиден адрес на електронна поща!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валиден адрес на електронна поща!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("5")) {
				Global.logWriter("Вече съществува този лектор в базата данни!");
				JOptionPane.showMessageDialog(mainDialog, "Опитвате се да въведете съществуващ в базата данни студент!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("6")) {
				Global.logWriter("Вече съществува този лектор в базата данни!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете невъществуващ в базата данни лектор!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void insert_editStudentDialog(String[] dialogParams) {
		mainDialog.setTitle(dialogParams[0]);
		mainDialog.setModal(true);
		studentName.setText(dialogParams[1]);
		studentEGN.setText(dialogParams[2]);
		studentPhone.setText(dialogParams[3]);
		studentAddress.setText(dialogParams[4]);
		if (dialogParams[6].equals("0")) {
			studentEmail.setText(dialogParams[4]);	
		}
		else {
		studentEmail.setText(dialogParams[7]);}
		insert.setText(dialogParams[5]);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteStudentDialog() {
		String messages = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = getTblIds();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title34);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (Integer i : result) {
					DBData.deleteStudent(i);
				}
				showStudents(false);
			}
		} else {
			Global.logWriter(Global.errMesssage9);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
}
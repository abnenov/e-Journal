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

class LectorEditor extends Reports {

	private String[] dialogParams = new String[8];

	protected JPanel lPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();
	private JPanel panel = new JPanel(new GridLayout(5, 2));

	private JLabel lectorNameLabel = new JLabel("      Имена");
	private JTextField lectorName = new JTextField();
	private JLabel lectorEGNLabel = new JLabel("      ЕГН");
	private JTextField lectorEGN = new JTextField();
	private JLabel lectorPhoneLabel = new JLabel("      Телефон");
	private JTextField lectorPhone = new JTextField();
	private JLabel lectorEmailLabel = new JLabel("      Електронна поща");
	private JTextField lectorEmail = new JTextField();
	private JButton insert = new JButton();
	private JPanel lPanel1 = new JPanel();
	private JButton button1 = new JButton(Global.title15);
	private JButton button2 = new JButton(Global.title16);
	private JButton button3 = new JButton(Global.title17);
	private JButton clear = new JButton("Изчисти");

	LectorEditor() {
		lectorName.setDocument(new JTextFieldLimit(100));
		lectorEGN.setDocument(new JTextFieldLimit(10));
		lPanel1.setLayout(new BoxLayout(lPanel1, BoxLayout.X_AXIS));
		lPanel1.add(button1);
		lPanel1.add(button2);
		lPanel1.add(button3);
		lPanel.add(lPanel1, BorderLayout.PAGE_START);
		lPanel.add(pg1, BorderLayout.CENTER);

		panel.add(lectorNameLabel);
		panel.add(lectorName);
		panel.add(lectorEGNLabel);
		panel.add(lectorEGN);
		panel.add(lectorPhoneLabel);
		panel.add(lectorPhone);
		panel.add(lectorEmailLabel);
		panel.add(lectorEmail);
		panel.add(insert);
		panel.add(clear);

		mainDialog.add(panel);
		mainDialog.setSize(700, 210);
		mainDialog.setResizable(false);
		mainDialog.setLocationRelativeTo(null);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dialogParams[0] = Global.title15;
				dialogParams[1] = "";
				dialogParams[2] = "";
				dialogParams[3] = "";
				dialogParams[4] = "";
				dialogParams[5] = Global.title9;
				dialogParams[6] = "0";
				insert_editLectorDialog(dialogParams);
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				String[] result = new String[4];

				ArrayList<Integer> result1 = new ArrayList<Integer>();
				result1 = getTblIds();
				if (result1.size() > 1) {
					Global.logWriter(Global.errMesssage10);
					Object[] options = { Global.title12 };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else {
					if (!result1.isEmpty()) {
						result = DBData.getLectorById(String.valueOf(result1.get(0)));
						dialogParams[0] = Global.title16;
						dialogParams[1] = result[0];
						dialogParams[2] = result[1];
						dialogParams[3] = result[2];
						dialogParams[4] = result[3];
						dialogParams[5] = Global.title10;
						dialogParams[6] = "1";
						insert_editLectorDialog(dialogParams);
						Global.logWriter(Global.title19);
					} else {
						Global.logWriter(Global.errMesssage5);
						Object[] options = { "Разбрах" };
						int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage3, Global.title3,
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					}
				}
			}

		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteLectorDialog();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				lectorName.setText("");
				lectorEGN.setText("");
				lectorPhone.setText("");
				lectorEmail.setText("");
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
			if (!Global.isName(lectorName.getText())) {
				throw new Exception("1");
			}
			if (!Global.isEGN(lectorEGN.getText())) {
				throw new Exception("2");
			}
			if (!Global.isMobilePhone(lectorPhone.getText())) {
				throw new Exception("3");
			}
			if (!Global.isEmail(lectorEmail.getText())) {
				throw new Exception("4");
			}
			if (dialogParams[6] == "0") {
				if (!DBData.checkLector(lectorEGN.getText()).equals("")) {
					throw new Exception("5");
				}
				DBData.insertLectors(lectorName.getText(), lectorEGN.getText(), lectorPhone.getText(),
						lectorEmail.getText());
				showLectors(false);
			} else {
				ArrayList<Integer> result = new ArrayList<Integer>();
				result = getTblIds();
				if (!DBData.checkOtherLectors(result.get(0), lectorEGN.getText()).equals("")) {
					throw new Exception("6");
				}
				Global.logWriter(Global.title19);
				Object[] options = { "Промени", "Отказ" };
				int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage3,
						Global.title22, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
				if (dialog == JOptionPane.YES_OPTION) {
					DBData.updateLector(result.get(0), lectorName.getText(), lectorEGN.getText(), lectorPhone.getText(),
							lectorEmail.getText());
					showLectors(false);
					mainDialog.dispose();
				}
			}
		} catch (Exception e) {
			if (e.getMessage().equals("1")) {
				Global.logWriter("Невалидно име на лектор!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете валидно име на лектор!", "Проблем!",
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
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете невъществуващ в базата данни лектор!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("6")) {
				Global.logWriter("Вече съществува този лектор в базата данни!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете невъществуващ в базата данни лектор!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void insert_editLectorDialog(String[] dialogParams) {
		mainDialog.setTitle(dialogParams[0]);
		mainDialog.setModal(true);
		lectorName.setText(dialogParams[1]);
		lectorEGN.setText(dialogParams[2]);
		lectorPhone.setText(dialogParams[3]);
		lectorEmail.setText(dialogParams[4]);
		insert.setText(dialogParams[5]);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteLectorDialog() {
		String messages = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = getTblIds();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title18);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (Integer i : result) {
					DBData.deleteLector(i);
				}
				showLectors(false);
			}
		} else {
			Global.logWriter(Global.errMesssage9);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
}

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class RoleEditor extends Reports {

	private String[] dialogParams = new String[8];

	protected JPanel rPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();
	private JPanel panel = new JPanel(new GridLayout(6, 2));

	private JLabel roleNameLabel = new JLabel("      Наименование на роля");
	private JTextField roleName = new JTextField();
	private JLabel menuDataAccessLabel = new JLabel("      Достъп до меню \"Въвеждане на данни\"");
	private JCheckBox menuDataAccess = new JCheckBox("  отбелязано = ДА / неотбелязано = НЕ");
	private JLabel menuReportAccessLabel = new JLabel("      Достъп до меню \"Справки\"");
	private JCheckBox menuReportAccess = new JCheckBox("  отбелязано = ДА / неотбелязано = НЕ");
	private JLabel menuAdminAccessLabel = new JLabel("      Достъп до меню \"Администриране\"");
	private JCheckBox menuAdminAccess = new JCheckBox("  отбелязано = ДА / неотбелязано = НЕ");
	private JLabel activeLabel = new JLabel("      Активна");
	private JCheckBox active = new JCheckBox("  отбелязано = ДА / неотбелязано = НЕ");
	private JPanel rPanel1 = new JPanel();
	private JButton button1 = new JButton(Global.title23);
	private JButton button2 = new JButton(Global.title24);
	private JButton button3 = new JButton(Global.title25);
	private JButton clear = new JButton("Изчисти");
	private JButton insert = new JButton();

	RoleEditor() {
		roleName.setDocument(new JTextFieldLimit(30));
		rPanel1.setLayout(new BoxLayout(rPanel1, BoxLayout.X_AXIS));
		rPanel1.add(button1);
		rPanel1.add(button2);
		rPanel1.add(button3);
		rPanel.add(rPanel1, BorderLayout.PAGE_START);
		rPanel.add(pg1, BorderLayout.CENTER);

		panel.add(roleNameLabel);
		panel.add(roleName);
		panel.add(menuDataAccessLabel);
		panel.add(menuDataAccess);
		panel.add(menuReportAccessLabel);
		panel.add(menuReportAccess);
		panel.add(menuAdminAccessLabel);
		panel.add(menuAdminAccess);
		panel.add(activeLabel);
		panel.add(active);
		panel.add(insert);
		panel.add(clear);

		mainDialog.add(panel);
		mainDialog.setSize(600, 210);
		mainDialog.setResizable(false);
		mainDialog.setLocationRelativeTo(null);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dialogParams[0] = Global.title26;
				dialogParams[1] = "";
				dialogParams[2] = "";
				dialogParams[3] = "";
				dialogParams[4] = "";
				dialogParams[5] = Global.title9;
				dialogParams[6] = "0";
				insert_editRoleDialog(dialogParams);
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				String[] result = new String[5];

				ArrayList<String> result1 = new ArrayList<String>();
				result1 = getTblFirst();
				if (result1.size() > 1) {
					Global.logWriter(Global.errMesssage11);
					Object[] options = { Global.title12 };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title26,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else {
					if (!result1.isEmpty()) {
						result = DBData.getRolesByName(result1.get(0));
						dialogParams[0] = Global.title26;
						dialogParams[1] = result[1];
						dialogParams[2] = result[2];
						dialogParams[3] = result[3];
						dialogParams[4] = result[4];
						dialogParams[5] = Global.title10;
						dialogParams[6] = "1";
						dialogParams[7] = result[0];
						insert_editRoleDialog(dialogParams);
						Global.logWriter(Global.title27);
					} else {
						Global.logWriter(Global.errMesssage11);
						Object[] options = { "Разбрах" };
						int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage3, Global.title3,
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					}
				}
			}

		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteRoleDialog();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				roleName.setText("");
				menuDataAccess.setSelected(false);
				menuReportAccess.setSelected(false);
				menuAdminAccess.setSelected(false);
				active.setSelected(false);
			}
		});

		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insert_edit(dialogParams);
			}
		});

	}

	private boolean isName(String name) {
		String nameRegex = "[a-zA-Zа-яА-Я ]+";
		return name.matches(nameRegex);
	}


	private void insert_edit(String[] dialogParams) {
		try {
			if (!isName(roleName.getText())) {
				throw new Exception("1");
			}
			if (dialogParams[6] == "0") {
				if (!DBData.checkRoles(roleName.getText()).equals("")) {
					throw new Exception("2");
				}

				DBData.insertRoles(roleName.getText(), menuDataAccess.isSelected() ? "Да" : "Не" , menuReportAccess.isSelected() ? "Да" : "Не",
						menuAdminAccess.isSelected() ? "Да" : "Не", active.isSelected() ? "Да" : "Не");
				showRoles();
			} else {
				ArrayList<String> result = new ArrayList<String>();
				result = getTblFirst();
				if (!DBData.checkOtherRoles(roleName.getText(), result.get(0)).equals("")) {
					throw new Exception("2");
				}
				Global.logWriter(Global.title27);
				Object[] options = { "Промени", "Отказ" };
				int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage3,
						Global.title22, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
				if (dialog == JOptionPane.YES_OPTION) {
					DBData.updateRole(roleName.getText(), menuDataAccess.isSelected() ? "Да" : "Не" , menuReportAccess.isSelected() ? "Да" : "Не",
					menuAdminAccess.isSelected() ? "Да" : "Не", active.isSelected() ? "Да" : "Не", result.get(0));
					showRoles();
					mainDialog.dispose();
			}
				}
			}
            catch (Exception e) {
			if (e.getMessage().equals("1")) {
				Global.logWriter("Невалидно име на роля. Моля, изпозлвайте само букви за наименование на роля!");
				JOptionPane.showMessageDialog(mainDialog, "Невалидно име на роля. Моля, изпозлвайте само букви за наименование на роля!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("2")) {
				Global.logWriter("Вече съществува роля с такова наименование в базата данни!");
				JOptionPane.showMessageDialog(mainDialog, "Опитвате се да въведете съществуваща роля!!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void insert_editRoleDialog(String[] dialogParams) {
		mainDialog.setTitle(dialogParams[0]);
		mainDialog.setModal(true);
		if (dialogParams[6] == "0") {
			roleName.setText(dialogParams[1].trim());
		}
		else 
		{
			roleName.setText(dialogParams[7].trim());
		}
		
		if (dialogParams[1].toLowerCase().equals("да")) {
			menuDataAccess.setSelected(true);
		}
		else menuDataAccess.setSelected(false);
	
		if (dialogParams[2].toLowerCase().equals("да")) {
			menuReportAccess.setSelected(true);
		}
		else menuReportAccess.setSelected(false);
		
		if (dialogParams[3].toLowerCase().equals("да")) {
			menuAdminAccess.setSelected(true);
		}
		else menuAdminAccess.setSelected(false);
		
		if (dialogParams[4].toLowerCase().equals("да")) {
			active.setSelected(true);
		}
		else active.setSelected(false);
		
		insert.setText(dialogParams[5]);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteRoleDialog() {
		String messages = "";
		ArrayList<String> result = new ArrayList<String>();
		result = getTblFirst();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title28);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (String i : result) {
					DBData.deleteRole(i);
				}
				showRoles();
			}
		} else {
			Global.logWriter(Global.errMesssage12);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
	
}

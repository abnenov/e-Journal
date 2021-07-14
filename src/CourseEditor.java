import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

class CourseEditor extends Reports {

	private Dimension buttonDimensions = new Dimension(18, 28);
	private Dimension textFieldDimensions = new Dimension(370, 30);
	private String comboFirstItem;
	private String startDateInitText;
	private String endDateInitText;
	private String comboLectorId = "-1";
	private String[] dialogParams = new String[8];
	
	protected JPanel cPanel = new JPanel(new BorderLayout());
	private JDialog mainDialog = new JDialog();
	private JPanel panel = new JPanel(new GridLayout(5, 2));
	private LinkedHashMap<String, String> comboItems = new LinkedHashMap<String, String>();
	private JComboBox<String> lectors = new JComboBox<String>();
	private DateTextField startDate = new DateTextField("--", startDateInitText, textFieldDimensions, buttonDimensions);
	private DateTextField endDate = new DateTextField("--", startDateInitText, textFieldDimensions, buttonDimensions);

	private JLabel courseNameLabel = new JLabel("    Наименование на курса");
	private JTextField courseName = new JTextField();
	private JLabel startDateLabel = new JLabel("    Дата на започване на курса");
	private JLabel endDateLabel = new JLabel("    Дата на приключване на курса");
	private JButton insert = new JButton();
	private JPanel cPanel1 = new JPanel();
	private JButton button1 = new JButton(Global.title5);
	private JButton button2 = new JButton(Global.title4);
	private JButton button3 = new JButton(Global.title13);
	private JLabel lectorLabel = new JLabel("    Лектор");
	private JButton clear = new JButton("Изчисти");

	CourseEditor() {
		courseName.setDocument(new JTextFieldLimit(100));
		cPanel1.setLayout(new BoxLayout(cPanel1, BoxLayout.X_AXIS));
		cPanel1.add(button1);
		cPanel1.add(button2);
		cPanel1.add(button3);
		cPanel.add(cPanel1, BorderLayout.PAGE_START);
		cPanel.add(pg1, BorderLayout.CENTER);
		
		panel.add(courseNameLabel);
		panel.add(courseName);
		panel.add(startDateLabel);
		panel.add(startDate.dateField);
		panel.add(endDateLabel);
		panel.add(endDate.dateField);
		panel.add(lectorLabel);
		panel.add(lectors);
		panel.add(insert);
		panel.add(clear);

		mainDialog.add(panel);
		mainDialog.setSize(800, 210);
		mainDialog.setResizable(false);
		mainDialog.setLocationRelativeTo(null);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dialogParams[0] = Global.title5;
				dialogParams[1] = "";
				dialogParams[2] = Global.title6;
				dialogParams[3] = Global.title7;
				dialogParams[4] = Global.title8;
				dialogParams[5] = Global.title9;
				dialogParams[6] = "0";
				insert_editCourseDialog(dialogParams);
			}
		});

		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				String[] result = new String[4];

				ArrayList<Integer> result1 = new ArrayList<Integer>();
				result1 = getTblIds();
				if (result1.size() > 1) {
					Global.logWriter(Global.errMesssage5);
					Object[] options = { Global.title12 };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage4, Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else {
					if (!result1.isEmpty()) {
						result = DBData.getCourseById(result1.get(0));
						Global.logWriter(Global.title11);
						dialogParams[0] = Global.title4;
						dialogParams[1] = result[0];
						dialogParams[2] = convDateBG(result[1], false);
						dialogParams[3] = convDateBG(result[2], false);
						String[] temp1 = new String[3];
						temp1 = DBData.getLectorById(result[3]);
						dialogParams[4] = Global.actMesssage5;
						dialogParams[7] = temp1[0] + " / " + temp1[2] + " / " + temp1[3];
						dialogParams[5] = Global.title10;
						dialogParams[6] = "1";
						insert_editCourseDialog(dialogParams);
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
				deleteCourseDialog();
			}
		});

		lectors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (lectors.getItemCount() > 0) {
					if (!lectors.getSelectedItem().equals(comboFirstItem)) {
						String selected = (String) lectors.getSelectedItem();
						comboLectorId = comboItems.get(selected);
					} else {
						comboLectorId = "-1";
					}
				}
			}
		});
		
		lectors.addPopupMenuListener(new PopupMenuListener() {

		      @Override
		      public void popupMenuCanceled(PopupMenuEvent e) {
//		        System.out.println(e.getSource());
		      }

		      @Override
		      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//		        System.out.println(e.getSource());
		      }

		      @Override
		      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		    	  addComboItems(comboFirstItem);
		      }
		    });

		startDate.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String result = new DatePicker(mainDialog).setPickedDate();
				if (result != "") {
					startDate.textField.setText(result);
				}
			}
		});

		endDate.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String result = new DatePicker(mainDialog).setPickedDate();
				if (result != "") {
					endDate.textField.setText(result);
				}
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				courseName.setText("");
				startDate.textField.setText(Global.title6);
				endDate.textField.setText(Global.title6);
				lectors.setSelectedIndex(0);
			}
		});
		
		insert.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent ae) {
				insert_edit(dialogParams);
			}
		});
		
	}
	
	@SuppressWarnings("unused")
	private void insert_edit(String [] dialogParams) {
		try {
			if (courseName.getText().trim().equals("")) {
				throw new Exception("1");
			}
			java.util.Date start = new SimpleDateFormat("dd.MM.yyyy").parse(startDate.textField.getText());
			java.util.Date end = new SimpleDateFormat("dd.MM.yyyy").parse(endDate.textField.getText());
			if (start.after(end)) {
				throw new Exception("2");
			}
			if (lectors.getSelectedItem().equals(comboFirstItem)) {
				throw new Exception("3");
			}
			// Проверка за съществуващ курс при добавяне
			if (dialogParams[6] == "0") {
				if (!DBData.checkCourse(courseName.getText(), convDateBG(startDate.textField.getText(), true),
						convDateBG(endDate.textField.getText(), true), comboLectorId).equals("")) {
					throw new Exception("4");
				}
			}
			// Проверка за съществуващ курс при редакция
			else {
				if (!DBData.checkOtherCourses(getTblIds().get(0), courseName.getText(),
						convDateBG(startDate.textField.getText(), true),
						convDateBG(endDate.textField.getText(), true), comboLectorId).equals("")) {
					throw new Exception("4");
				}
			}
			// Добавяне на нов курс при операция добавяне
			if (dialogParams[6] == "0") {
				DBData.insertCourse(courseName.getText(), convDateBG(startDate.textField.getText(), true),
						convDateBG(endDate.textField.getText(), true), comboLectorId);
			} else {
				ArrayList<Integer> result = new ArrayList<Integer>();
				result = getTblIds();
				if (!result.isEmpty()) {
					Global.logWriter(Global.title11);
					Object[] options = { "Промени", "Отказ" };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.inqMessage3, Global.title4,
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (dialog == JOptionPane.YES_OPTION) {
						DBData.updateCourse(result.get(0), courseName.getText(),
								convDateBG(startDate.textField.getText(), true),
								convDateBG(endDate.textField.getText(), true), comboLectorId);
						showCourses(false);
						JOptionPane.setDefaultLocale(null);
						mainDialog.dispose();
					}
					else {dialog = JOptionPane.CANCEL_OPTION;}
				} else {
					Global.logWriter(Global.errMesssage4);
					Object[] options = { "Разбрах" };
					int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage3, Global.title3,
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				}

			}
			if (dialogParams[6] == "0") {
				showCourses(false);
			}

		} catch (Exception e) {
			if (e.getMessage().equals("1")) {
				Global.logWriter("Не е въведено наименование на курса!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, въведете наименование на курса!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if ((e.getMessage().equals("Unparseable date: \"" + startDateInitText + "\""))
					|| (e.getMessage().equals("Unparseable date: \"" + endDateInitText + "\""))) {
				Global.logWriter("Не са избрани дати за курса!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, изберете дати за курса!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("2")) {
				Global.logWriter("Началната дата на курса е след датата на завършването му!");
				JOptionPane.showMessageDialog(mainDialog, "Началната дата на курса е след датата на завършването му!",
						"Проблем!", JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("3")) {
				Global.logWriter("Не е избран лектор за курса!");
				JOptionPane.showMessageDialog(mainDialog, "Моля, изберете лектор за курса!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
			if (e.getMessage().equals("4")) {
				Global.logWriter("Вече има въведен такъв курс!");
				JOptionPane.showMessageDialog(mainDialog, "Вече има въведен такъв курс!", "Проблем!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

	private void addComboItems(String comboFirstItem) {
		this.lectors.removeAllItems();
		this.lectors.addItem(comboFirstItem);
		this.comboItems = DBData.getLectors();
		for (String i : comboItems.keySet()) {
			lectors.addItem(i);
		}
	}

	private void insert_editCourseDialog(String[] dialogParams) {

		mainDialog.setTitle(dialogParams[0]);
		mainDialog.setModal(true);
		startDateInitText = dialogParams[2];
		endDateInitText = dialogParams[3];
		startDate.setText(startDateInitText);
		endDate.setText(endDateInitText);

		this.comboFirstItem = dialogParams[4];
		courseName.setText(dialogParams[1]);
		addComboItems(this.comboFirstItem);
		
		if (dialogParams[6] == "0") {
			lectors.setSelectedItem(dialogParams[4]);
		} else {
			lectors.setSelectedItem(dialogParams[7]);
		}
		lectors.setEditable(false);
		insert.setText(dialogParams[5]);
		mainDialog.setAlwaysOnTop(true);
		mainDialog.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void deleteCourseDialog() {
		String messages = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = getTblIds();
		if (result.size() == 1) {
			messages = Global.inqMessage1;
		} else {
			messages = Global.inqMessage2;
		}
		if (!result.isEmpty()) {
			Global.logWriter(Global.title1);
			Object[] options = { "Изтрий", "Отказ" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, messages, Global.title2, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (dialog == JOptionPane.YES_OPTION) {
				for (Integer i : result) {
					DBData.deleteCourse(i);
				}
				showCourses(false);
			}
		} else {
			Global.logWriter(Global.errMesssage4);
			Object[] options = { "Разбрах" };
			int dialog = JOptionPane.showOptionDialog(mainDialog, Global.actMesssage1, Global.title3,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		}
	}
}

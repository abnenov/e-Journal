import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Log {
	protected JPanel lPanel = new JPanel(new BorderLayout());
	private JDialog dialog = new JDialog();
	private JPanel lPanel1 = new JPanel();
	private JLabel pathLabel = new JLabel();
	private JButton getDate = new JButton("Избор на дата на лог файл");
	private final JTextArea edit = new JTextArea();
	private JScrollPane scroll = new JScrollPane(edit, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private LocalDateTime now;
	private String date;

	Log() {
		lPanel1.setLayout(new BoxLayout(lPanel1, BoxLayout.X_AXIS));
		lPanel1.add(getDate);
		lPanel1.add(pathLabel);
		lPanel.add(scroll, BorderLayout.CENTER);
		lPanel.add(lPanel1, BorderLayout.PAGE_START);

		getDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String result = new DatePicker(dialog).setPickedDate();
				if (result != "") {
					date = result.replace(".", "_");
					readLog(date);
				}
			}
		});
	}

	protected void readLog(String date) {
		String fileName = "";
		edit.selectAll();
		edit.replaceSelection("");
		String log_path = Paths.get("").toAbsolutePath().toString() + "\\src\\logs\\";
		if (date.equals("")) {
			now = LocalDateTime.now();
			fileName = log_path + Global.convDate(now, "dd_MM_yyyy") + "_log.txt";
		} else {
			fileName = log_path + date + "_log.txt";
		}
		pathLabel.setText("    " + fileName);
		try {
			File file = new File(fileName);
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				edit.append(data + "\n\r");
			}
			myReader.close();
			edit.requestFocus();
		} catch (Exception e) {
			Global.logWriter("ОПит за прочитане на несъществуващ лог файл.");
		}
	}
}

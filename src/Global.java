import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

class Global {
	protected static String user = "";
	protected static String driver = "com.mysql.cj.jdbc.Driver";
	protected static String connString1 = "jdbc:mysql://localhost/classbook?user=";
	protected static String connString2 = "&password=";
	protected static String connString3 = "&characterEncoding=utf8";
	protected static Connection conn;
	protected static boolean firstTimeStart = true;
	private static LocalDateTime now;
	protected static int openedSubForm = -1;
	protected static boolean logged = false;
	protected static String errMesssage1 = "�������� ������. ����, ����������� ��� �����!";
	protected static String errMesssage2 = "���� ������ ����������!";
	protected static String errMesssage3 = "���� ������ ���������� �� ���� ��������";
	protected static String errMesssage4 = "���� �� ��������� ��� �� �� ������� ������ � ������� �������!";
	protected static String errMesssage5 = "�� � ������ ����� �� ����������� � ������� courses!";
	protected static String errMesssage6 = "��������� ���������� ��� ������!";
	protected static String errMesssage7 = "������������ � ���������. ����, �������� �� ��� ��������� �������������!";
	protected static String errMesssage8 = "������ �� ����������� � ������������. ����, �������� �� ��� ��������� �������������!";
	protected static String errMesssage9 = "���� �� ��������� ��� �� �� ������� ������ � ������� �������!";
	protected static String errMesssage10 = "�� � ������ ����� �� ����������� � ������� lectors!";
	protected static String errMesssage11 = "�� � ������ ����� �� ����������� � ������� user_roles!";
	protected static String errMesssage12 = "���� �� ��������� ��� �� �� ������� ������ � ������� ����!";
	protected static String errMesssage13 = "�� � ������ ����� �� ����������� � ������� students!";
	protected static String errMesssage14 = "���� �� ��������� ��� �� �� ������� ������ � ������� ��������!";
	protected static String errMesssage15 = "���� �� ��������� ��� �� �� ������� ������ � ������� �������-��������!";
	protected static String errMesssage16 = "�� � ������ ����� �� ����������� � ������� students_courses!";
	protected static String errMesssage17 = "���� �� ����������� ��� �� � ������ ������ � ������� �������-��������!";
	protected static String errMesssage18 = "�� � ������ ����� �� ����������� � ������� users!";
	protected static String errMesssage19 = "���� �� ��������� ��� �� �� ������� ������ � ������� ������!";
	protected static String errMesssage20 = "�� � ������ ����� �� ����������� � ������� grades!";
	protected static String errMesssage21 = "������ �������� ������!";

	protected static String inqMessage1 = "����������� �� ��������� ����� �� ������ �� ��������� �� �������� ������ � ����� ������� �� ������. �������� �� ������� �� �������� ����������� �����?";
	protected static String inqMessage2 = "����������� �� ��������� ������ �� ������ �� ��������� �� �������� ������ � ����� ������� �� ������. �������� �� ������� �� �������� ����������� ������?";
	protected static String inqMessage3 = "������� �� ������� �� �������� ���������?";
	protected static String inqMessage4 = "������� �� ������� �� ����������/������������ ���������� ";
	protected static String inqMessage5 = "��������� ������� ���� ��� ��������� ������ �� ������ � �����, ����� ��� ��������! ������� �� �� �������� � ���� ������?";
	protected static String inqMessage6 = "�������� �� ������� �� �������� ����������?";

	protected static String actMesssage1 = "����, ���������� �����/������ �� ��������� �� ���������!";
	protected static String actMesssage2 = "������ ����";
	protected static String actMesssage3 = "����, ���������� ����� �� ����������� � ���������!";
	protected static String actMesssage4 = "�� �� �� ��������� ������ ���������� ����, ���������� ���� ���� �����!";
	protected static String actMesssage5 = "����, �������� ������";
	protected static String actMesssage6 = "������ ������";
	protected static String actMesssage7 = "������� ����";
	protected static String actMesssage8 = "������ ������� �� ����";
	protected static String actMesssage9 = "����, �������� ���� ���� ���� � ��������!";
	protected static String actMesssage10 = "����, �������� ���� �� ���� ����� �� ����� �� ����� �������!";
	protected static String actMesssage11 = "����, �������� ���� �� ��������� �� ��������!";
	protected static String actMesssage12 = "����, �������� ������!";

	protected static String title1 = "�������� �������� �� ��������� �� ����";
	protected static String title2 = "������������ �� ���������";
	protected static String title3 = "���������";
	protected static String title4 = "����������� �� �������";
	protected static String title5 = "�������� �� �������";
	protected static String title6 = " ����, �������� ������� ���� ->";
	protected static String title7 = " ����, �������� ���� �� ����������� ->";
	protected static String title8 = "����, �������� ������";
	protected static String title9 = "������";
	protected static String title10 = "�������";
	protected static String title11 = "�������� �������� �� �������� �� ����";
	protected static String title12 = "�������";
	protected static String title13 = "��������� �� �������";
	protected static String title14 = "������!";
	protected static String title15 = "�������� �� �������";
	protected static String title16 = "����������� �� �������";
	protected static String title17 = "��������� �� �������";
	protected static String title18 = "�������� �������� �� ��������� �� ������";
	protected static String title19 = "�������� �������� �� �������� �� ������";
	protected static String title20 = "����������/������������ �� ����������";
	protected static String title22 = "������������ �� �����������";
	protected static String title23 = "�������� �� ����";
	protected static String title24 = "����������� �� ����";
	protected static String title25 = "��������� �� ����";
	protected static String title26 = "����������� �� ����";
	protected static String title27 = "�������� �������� �� �������� �� ����";
	protected static String title28 = "�������� �������� �� ��������� �� ����";
	protected static String title29 = "�������� �� ��������";
	protected static String title30 = "����������� �� ��������";
	protected static String title31 = "��������� �� ��������";
	protected static String title32 = "�������� �������� �� �������� �� �������";
	protected static String title33 = "�������� �������� �� �������� �� �������";
	protected static String title34 = "�������� �������� �� ��������� �� �������";
	protected static String title35 = "����������� �� ��������";
	protected static String title36 = "�������� �� �������� ��� �������";
	protected static String title37 = "����������� �� �������� � �������";
	protected static String title38 = "��������� �� �������� �� �������";
	protected static String title39 = "�������� �������� �� ��������� �� ������� �� ����";
	protected static String title40 = "�������� �� �����������";
	protected static String title41 = "����������� �� ����������";
	protected static String title42 = "��������� �� �����������";
	protected static String title43 = "�������� �������� �� �������� �� �����������";
	protected static String title44 = "�������� �������� �� �������� �� �����������";
	protected static String title45 = "�������� �������� �� ��������� �� �����������";
	protected static String title46 = "�������� �� ������";
	protected static String title47 = "����������� �� ������";
	protected static String title48 = "��������� �� ������";
	protected static String title49 = "�������� �������� �� �������� �� ������";
	protected static String title50 = "�������� �������� �� �������� �� ������";
	protected static String title51 = "�������� �������� �� ��������� �� ������";
	protected static String title52 = "�������� �� ������";
	protected static String title53 = "����������� �� ������";
	protected static String title54 = "��������� �� ������";
	protected static String title55 = "������������ �� ��������";
	protected static String title56 = "�������� �������� �� ��������� �� ������";
	protected static String title57 = "������������ �� ��������";
	protected static String title58 = "������ ����� �� ����";
	protected static String title59 = "������ ����� �� �������";
	protected static String title60 = "������ ����� �� ������� �� ����";

	protected static String convDate(LocalDateTime dt, String model) {
		return DateTimeFormatter.ofPattern(model).format(dt);
	}

	protected static void logWriter(SQLException sqlE, String classMethod, String Message) {
		String log_path = Paths.get("").toAbsolutePath().toString() + "\\src\\logs\\";
		now = LocalDateTime.now();
		String fileName = log_path + convDate(now, "dd_MM_yyyy") + "_log.txt";
		FileWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new FileWriter(fileName, true);
			// fr.write("Date/Time: " +
			// LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy
			// HH:mm:ss")) + "\r\n");
			fr.write("Date/Time: " + convDate(now, "dd.MM.yyyy HH:mm:ss.SSS") + "\r\n");
			fr.write("������ ��� ���������� �� �����: " + classMethod + "\r\n");
			fr.write("SQLException: " + sqlE.getMessage() + "\r\n");
			fr.write("SQLState: " + sqlE.getSQLState() + "\r\n");
			fr.write("VendorError: " + sqlE.getErrorCode() + "\r\n");
			fr.write("---------------------------------------------------" + "\r\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Message != null) {
			JOptionPane optionPane = new JOptionPane(Message, JOptionPane.ERROR_MESSAGE);
			JDialog dialog = optionPane.createDialog(title14);
			dialog.setAlwaysOnTop(dialog.isAlwaysOnTopSupported());
			dialog.setVisible(true);
		}
	}

	protected static void logWriter(ClassNotFoundException ex, String classMethod, String errMessage) {
		String log_path = Paths.get("").toAbsolutePath().toString() + "\\src\\logs\\";
		now = LocalDateTime.now();
		String fileName = log_path + convDate(now, "dd_MM_yyyy") + "_log.txt";
		FileWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new FileWriter(fileName, true);
			fr.write("Date/Time: " + DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS").format(now) + "\r\n");
			fr.write("������ ��� ���������� �� �����: " + classMethod + "\r\n");
			fr.write("������� � ����������� �� JDBC ��������!" + "\r\n");
			fr.write("---------------------------------------------------" + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (errMessage != null) {
				JOptionPane optionPane = new JOptionPane(errMessage, JOptionPane.ERROR_MESSAGE);
				JDialog dialog = optionPane.createDialog(title14);
				dialog.setAlwaysOnTop(dialog.isAlwaysOnTopSupported());
				dialog.setVisible(true);
				System.exit(0);
			}
		}
	}

	protected static void logWriter(String logMessage) {
		String log_path = Paths.get("").toAbsolutePath().toString() + "\\src\\logs\\";
		now = LocalDateTime.now();
		String fileName = log_path + convDate(now, "dd_MM_yyyy") + "_log.txt";
		FileWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new FileWriter(fileName, true);
			fr.write("Date/Time: " + convDate(now, "dd.MM.yyyy HH:mm:ss.SSS") + "\r\n");
			fr.write(logMessage + "\r\n");
			fr.write("---------------------------------------------------" + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static boolean isName(String egn) {
		boolean result = false;
		String nameRegexStrings[] = { "^[A-Z][a-z]{3,20}\\s[A-Z][a-z]{3,30}$", "^[�-�][�-�]{3,20}\\s[�-�][�-�]{3,30}$",
				"^[A-Z][a-z]{3,20}\\s[A-Z][a-z]{5,30}\\s[A-Z][a-z]{5,30}$", "^[�-�][�-�]{3,20}\\s[�-�][�-�]{3,30}\\s[�-�][�-�]{3,30}$", 
				"^[A-Z][a-z]{3,13}\\s[A-Z][a-z]{5,29}\\s[A-Z][a-z]{5,29}[-]{1}[A-Z][a-z]{5,29}$",
				"^[�-�][�-�]{3,13}\\s[�-�][�-�]{5,29}\\s[�-�][�-�]{5,29}[-]{1}[�-�][�-�]{5,29}$" };
		for (int i = 0; i < nameRegexStrings.length; i++) {
			if (egn.matches(nameRegexStrings[i])) {
				result = true;
			}
		}
		return result;
	}
	
	static boolean isEGN(String egn) {
		boolean result = false;
		String egnRegexStrings[] = { "[0-9]{2}[0,1,2,4][0-9][0-9]{2}[0-9]{4}", "^((.)\\2{9})$" };
			if (egn.matches(egnRegexStrings[0])) {
				result = true;
			}
			if (egn.matches(egnRegexStrings[1])) {
				result = false;
			}
		return result;
	}

	static boolean isMobilePhone(String phone) {
		String mobilePhoneRegex = "^(\\+359|0)\\s?[8,9](\\d{2}\\s\\d{3}\\d{3}|[789]\\d{7})$";
		return phone.matches(mobilePhoneRegex);
	}

	static boolean isEmail(String �mail) {
		String EMAIL_REGIX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(EMAIL_REGIX);
		Matcher matcher = pattern.matcher(�mail);
		return ((!�mail.isEmpty()) && (�mail != null) && (matcher.matches()));
	}

}

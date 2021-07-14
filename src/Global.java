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
	protected static String errMesssage1 = "Системна грешка. Моля, прегледайте лог файла!";
	protected static String errMesssage2 = "Няма логнат потребител!";
	protected static String errMesssage3 = "Няма логнат потребител за тази операция";
	protected static String errMesssage4 = "Опит за изтриване без да са избрани записи в таблица курсове!";
	protected static String errMesssage5 = "Не е избран запис за редактиране в таблица courses!";
	protected static String errMesssage6 = "Невалиден потребител или парола!";
	protected static String errMesssage7 = "Потребителят е неактивен. Моля, обърнете се към системния администратор!";
	protected static String errMesssage8 = "Ролята на потребителя е деактивирана. Моля, обърнете се към системния администратор!";
	protected static String errMesssage9 = "Опит за изтриване без да са избрани записи в таблица лектори!";
	protected static String errMesssage10 = "Не е избран запис за редактиране в таблица lectors!";
	protected static String errMesssage11 = "Не е избран запис за редактиране в таблица user_roles!";
	protected static String errMesssage12 = "Опит за изтриване без да са избрани записи в таблица роли!";
	protected static String errMesssage13 = "Не е избран запис за редактиране в таблица students!";
	protected static String errMesssage14 = "Опит за изтриване без да са избрани записи в таблица студенти!";
	protected static String errMesssage15 = "Опит за изтриване без да са избрани записи в таблица курсове-студенти!";
	protected static String errMesssage16 = "Не е избран запис за редактиране в таблица students_courses!";
	protected static String errMesssage17 = "Опит за редактиране без да е избран записи в таблица курсове-студенти!";
	protected static String errMesssage18 = "Не е избран запис за редактиране в таблица users!";
	protected static String errMesssage19 = "Опит за изтриване без да са избрани записи в таблица оценки!";
	protected static String errMesssage20 = "Не е избран запис за редактиране в таблица grades!";
	protected static String errMesssage21 = "Грешна настояща парола!";

	protected static String inqMessage1 = "Изтриването на избраният запис ще доведе до изтриване на свързани записи в други таблици на базата. Наистина ли желаете да изтриете маркираният запис?";
	protected static String inqMessage2 = "Изтриването на избраните записи ще доведе до изтриване на свързани записи в други таблици на базата. Наистина ли желаете да изтриете маркираните записи?";
	protected static String inqMessage3 = "Нистина ли желаете да запишете промяната?";
	protected static String inqMessage4 = "Нистина ли желаете да активирате/деактивирате потребител ";
	protected static String inqMessage5 = "Избраният студент вече има поставена оценка за датата и курса, които сте посочили! Желаете ли да добавите и тази оценка?";
	protected static String inqMessage6 = "Наистина ли желаете да запишете корекцията?";

	protected static String actMesssage1 = "Моля, маркирайте запис/записи за изтриване от таблицата!";
	protected static String actMesssage2 = "Изтрит курс";
	protected static String actMesssage3 = "Моля, маркирайте запис за редактиране в таблицата!";
	protected static String actMesssage4 = "За да се осъществи упешно редакцията моля, маркирайте само един запис!";
	protected static String actMesssage5 = "Моля, изберете лектор";
	protected static String actMesssage6 = "Изтрит лектор";
	protected static String actMesssage7 = "Изтрита роля";
	protected static String actMesssage8 = "Изтрит студент от курс";
	protected static String actMesssage9 = "Моля, изберете поне един курс и струдент!";
	protected static String actMesssage10 = "Моля, изберете поне по един запис от всяка от трите таблици!";
	protected static String actMesssage11 = "Моля, изберете дата на поставяне на оценката!";
	protected static String actMesssage12 = "Моля, изберете оценка!";

	protected static String title1 = "Диалогов процорец за изтриване на курс";
	protected static String title2 = "Потвърждение за изтриване";
	protected static String title3 = "Пояснение";
	protected static String title4 = "Редактиране на курсове";
	protected static String title5 = "Добавяне на курсове";
	protected static String title6 = " Моля, изберете начална дата ->";
	protected static String title7 = " Моля, изберете дата на приключване ->";
	protected static String title8 = "Моля, изберете лектор";
	protected static String title9 = "Добави";
	protected static String title10 = "Промени";
	protected static String title11 = "Диалогов процорец за редакция на курс";
	protected static String title12 = "Разбрах";
	protected static String title13 = "Изтриване на курсове";
	protected static String title14 = "Грешка!";
	protected static String title15 = "Добавяне на лектори";
	protected static String title16 = "Редактиране на лектори";
	protected static String title17 = "Изтриване на лектори";
	protected static String title18 = "Диалогов процорец за изтриване на лектор";
	protected static String title19 = "Диалогов процорец за редакция на лектор";
	protected static String title20 = "Активиране/деактивиране на потребител";
	protected static String title22 = "Потвърждение за редактиране";
	protected static String title23 = "Добавяне на роля";
	protected static String title24 = "Редактиране на роля";
	protected static String title25 = "Изтриване на роля";
	protected static String title26 = "Редактиране на роли";
	protected static String title27 = "Диалогов процорец за редакция на роля";
	protected static String title28 = "Диалогов процорец за изтриване на роля";
	protected static String title29 = "Добавяне на студенти";
	protected static String title30 = "Редактиране на студенти";
	protected static String title31 = "Изтриване на студенти";
	protected static String title32 = "Диалогов процорец за редакция на студент";
	protected static String title33 = "Диалогов процорец за добавяне на студент";
	protected static String title34 = "Диалогов процорец за изтриване на студент";
	protected static String title35 = "Редактиране на студенти";
	protected static String title36 = "Добавяне на студенти към курсове";
	protected static String title37 = "Редактиране на студенти в курсове";
	protected static String title38 = "Изтриване на студенти от курсове";
	protected static String title39 = "Диалогов процорец за изтриване на студент от курс";
	protected static String title40 = "Добавяне на потребители";
	protected static String title41 = "Редактиране на потребител";
	protected static String title42 = "Изтриване на потребители";
	protected static String title43 = "Диалогов процорец за добавяне на потребители";
	protected static String title44 = "Диалогов процорец за редакция на потребители";
	protected static String title45 = "Диалогов процорец за изтриване на потребители";
	protected static String title46 = "Добавяне на оценка";
	protected static String title47 = "Редактиране на оценка";
	protected static String title48 = "Изтриване на оценки";
	protected static String title49 = "Диалогов процорец за добавяне на оценки";
	protected static String title50 = "Диалогов процорец за редакция на оценки";
	protected static String title51 = "Диалогов процорец за изтриване на оценки";
	protected static String title52 = "Добавяне на оценки";
	protected static String title53 = "Редактиране на оценки";
	protected static String title54 = "Изтриване на оценки";
	protected static String title55 = "Потвърждение за добавяне";
	protected static String title56 = "Диалогов процорец за изтриване на оценки";
	protected static String title57 = "Потвърждение на корекция";
	protected static String title58 = "Среден успех на курс";
	protected static String title59 = "Среден успех на студент";
	protected static String title60 = "Среден успех на студент за курс";

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
			fr.write("Грешка при изпълнение на метод: " + classMethod + "\r\n");
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
			fr.write("Грешка при изпълнение на метод: " + classMethod + "\r\n");
			fr.write("Проблем с откриването на JDBC драйвера!" + "\r\n");
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
		String nameRegexStrings[] = { "^[A-Z][a-z]{3,20}\\s[A-Z][a-z]{3,30}$", "^[А-Я][а-я]{3,20}\\s[А-Я][а-я]{3,30}$",
				"^[A-Z][a-z]{3,20}\\s[A-Z][a-z]{5,30}\\s[A-Z][a-z]{5,30}$", "^[А-Я][а-я]{3,20}\\s[А-Я][а-я]{3,30}\\s[А-Я][а-я]{3,30}$", 
				"^[A-Z][a-z]{3,13}\\s[A-Z][a-z]{5,29}\\s[A-Z][a-z]{5,29}[-]{1}[A-Z][a-z]{5,29}$",
				"^[А-Я][а-я]{3,13}\\s[А-Я][а-я]{5,29}\\s[А-Я][а-я]{5,29}[-]{1}[А-Я][а-я]{5,29}$" };
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

	static boolean isEmail(String еmail) {
		String EMAIL_REGIX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(EMAIL_REGIX);
		Matcher matcher = pattern.matcher(еmail);
		return ((!еmail.isEmpty()) && (еmail != null) && (matcher.matches()));
	}

}

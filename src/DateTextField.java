import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.Border;

class DateTextField {
	protected JButton button = new JButton();
	protected JFormattedTextField textField = new JFormattedTextField();
	protected JPanel dateField = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 1));

	protected DateTextField(String btnLabel, String fieldText, Dimension textFieldSize, Dimension btnSize) {
		button.setText(btnLabel);
		button.setPreferredSize(btnSize);
		textField.setText(fieldText);
		textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textField.setPreferredSize(textFieldSize);
		textField.setEditable(false);
		Border lowered_bevelborder = BorderFactory.createLoweredBevelBorder();
		dateField.setBorder(lowered_bevelborder);
		dateField.add(textField);
		dateField.add(button);
	}
	
	protected void setText(String text) {
		textField.setText(text);	
	}
}
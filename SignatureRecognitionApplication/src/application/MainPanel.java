package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class MainPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private ClassificationSystemFacade classificationSystemFacade;
	
	public MainPanel() {
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(220, 11, 369, 295);
		add(tabbedPane);
		
		JPanel testPanel = new JPanel();
		tabbedPane.addTab("Test", null, testPanel, null);
		testPanel.setLayout(null);
		
		JButton btnPredict = new JButton("Recognize");
		btnPredict.setBounds(114, 233, 141, 23);
		testPanel.add(btnPredict);
		
		JPanel loadImagePanel = new JPanel();
		loadImagePanel.setBorder(new TitledBorder(null, "Load file", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		loadImagePanel.setBounds(4, 11, 356, 75);
		testPanel.add(loadImagePanel);
		loadImagePanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 16, 340, 48);
		loadImagePanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(9, 14, 26, 14);
		panel.add(lblPath);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(257, 10, 67, 23);
		panel.add(btnBrowse);
		
		textField = new JTextField();
		textField.setBounds(45, 11, 202, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Classifier", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(4, 86, 350, 135);
		testPanel.add(panel_3);
		panel_3.setLayout(null);
		
		JRadioButton radioButton = new JRadioButton("Artificial Neural Network");
		radioButton.setBounds(6, 105, 141, 23);
		panel_3.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("K-nearest neighbors");
		radioButton_1.setBounds(6, 79, 133, 23);
		panel_3.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("SVM");
		radioButton_2.setBounds(6, 53, 109, 23);
		panel_3.add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("Decision Tree");
		radioButton_3.setBounds(6, 27, 109, 23);
		panel_3.add(radioButton_3);
		
		JPanel trainPanel = new JPanel();
		tabbedPane.addTab("Train", null, trainPanel, null);
		trainPanel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Load file", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 11, 356, 75);
		trainPanel.add(panel_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(10, 16, 340, 48);
		panel_1.add(panel_4);
		
		JLabel label = new JLabel("Path:");
		label.setBounds(9, 14, 26, 14);
		panel_4.add(label);
		
		JButton button = new JButton("Browse");
		button.setBounds(257, 10, 67, 23);
		panel_4.add(button);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(45, 11, 202, 20);
		panel_4.add(textField_1);
		
		JButton btnNewButton = new JButton("Train");
		btnNewButton.setBounds(88, 97, 187, 42);
		trainPanel.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(10, 45, 200, 92);
		add(panel_2);
		
		JLabel lblPodpisDoWeryfikacji = new JLabel("Signature to verify:");
		lblPodpisDoWeryfikacji.setBounds(10, 31, 200, 14);
		add(lblPodpisDoWeryfikacji);
		
		JLabel lblNewLabel = new JLabel("Prediction result:");
		lblNewLabel.setBounds(10, 178, 88, 14);
		add(lblNewLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(108, 172, 68, 20);
		add(textPane);
		setVisible(true);
	}
}

package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import java.awt.Color;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;

public class MainPanel extends JPanel {
	private JTextField textField;
	private ClassificationSystemFacade classificationSystemFacade;
	private JTextField textField_2;
	
	public MainPanel() {
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(220, 11, 493, 340);
		add(tabbedPane);
		
		JPanel trainPanel = new JPanel();
		tabbedPane.addTab("Train", null, trainPanel, null);
		trainPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Train");
		btnNewButton.setBounds(150, 259, 187, 42);
		trainPanel.add(btnNewButton);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Load exisiting training set", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(10, 11, 468, 237);
		trainPanel.add(panel_5);
		
		JLabel label_1 = new JLabel("Path:");
		label_1.setBounds(10, 27, 47, 25);
		panel_5.add(label_1);
		
		JButton button_1 = new JButton("Browse");
		button_1.setBounds(354, 27, 104, 25);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseForFile(textField_2, "ARFF (*.arff)", "arff");
			}
		});
		panel_5.add(button_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(67, 26, 264, 25);
		panel_5.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Training set:");
		lblNewLabel_1.setBounds(10, 68, 146, 22);
		panel_5.add(lblNewLabel_1);
		
		JPanel testPanel = new JPanel();
		tabbedPane.addTab("Test", null, testPanel, null);
		testPanel.setLayout(null);
		
		JPanel loadImagePanel = new JPanel();
		loadImagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Load signature image", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		loadImagePanel.setBounds(10, 11, 468, 92);
		testPanel.add(loadImagePanel);
		loadImagePanel.setLayout(null);
		
		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(10, 22, 52, 25);
		loadImagePanel.add(lblPath);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(371, 21, 87, 25);
		btnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseForFile(textField, "PNG (*.png)", "png");
			}
		});
		loadImagePanel.add(btnBrowse);
		
		textField = new JTextField();
		textField.setBounds(63, 22, 297, 25);
		loadImagePanel.add(textField);
		textField.setColumns(10);
		
		JButton btnLoadImage = new JButton("Load image");
		btnLoadImage.setBounds(129, 58, 173, 23);
		loadImagePanel.add(btnLoadImage);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Classifier", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 114, 468, 134);
		testPanel.add(panel_3);
		panel_3.setLayout(null);
		
		JRadioButton radioButton = new JRadioButton("Artificial Neural Network");
		radioButton.setBounds(6, 104, 240, 23);
		panel_3.add(radioButton);
		
		JRadioButton rdbtnNaiveBayesClassifier = new JRadioButton("Naive Bayes classifier");
		rdbtnNaiveBayesClassifier.setBounds(6, 65, 240, 23);
		panel_3.add(rdbtnNaiveBayesClassifier);
		
		JRadioButton radioButton_3 = new JRadioButton("Decision Tree");
		radioButton_3.setBounds(6, 27, 240, 23);
		panel_3.add(radioButton_3);
		
		JButton btnRecognize = new JButton("Recognize");
		btnRecognize.setBounds(140, 259, 187, 42);
		testPanel.add(btnRecognize);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 45, 200, 92);
		panel_2.setBackground(Color.WHITE);
		add(panel_2);
		
		JLabel lblPodpisDoWeryfikacji = new JLabel("Signature to verify:");
		lblPodpisDoWeryfikacji.setBounds(10, 31, 200, 14);
		add(lblPodpisDoWeryfikacji);
		
		JLabel lblNewLabel = new JLabel("Prediction result:");
		lblNewLabel.setBounds(10, 299, 200, 14);
		add(lblNewLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 317, 200, 34);
		add(textPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 162, 200, 92);
		add(panel_1);
		
		JLabel lblSignatureAfterPreprocessing = new JLabel("Signature after preprocessing:");
		lblSignatureAfterPreprocessing.setBounds(10, 148, 200, 14);
		add(lblSignatureAfterPreprocessing);
		setVisible(true);
	}

	private void browseForFile(JTextField textField, String extsDesc, String exts ) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(extsDesc, exts);
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION)
			textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
	}
	
	
}

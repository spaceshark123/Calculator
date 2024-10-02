import javax.swing.*;
import java.awt.*;

public class Calculator {
	private int fontSize = 24;
	private final static int WIDTH = 300;
	private final static int HEIGHT = 300;

	JButton[] numberButtons = new JButton[10];
	JFrame frame = new JFrame("Calculator");
	JTextField text = new JTextField("");
	JPanel buttons = new JPanel();

	public Calculator() {
		// call the other constructor with default font size
		this(24);
	}

	public Calculator(int fontSize) {
		this.fontSize = fontSize;
		// set up the containers and buttons
		setupContainers();
		setupButtons();
		createFrame();
	}
	
	private void setupContainers() {
		// set text field and button panel properties
		text.setEditable(false);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setPreferredSize(new Dimension(WIDTH, 50));
		text.setFont(new Font("Arial", Font.PLAIN, fontSize));
		buttons.setLayout(new GridLayout(5, 4));
	}
	
	private void setupButtons() {
		// create number buttons
		for (int i = 0; i < 10; i++) {
			numberButtons[i] = new JButton(Integer.toString(i));
			numberButtons[i].setFont(new Font("Arial", Font.PLAIN, fontSize));
		}
		// create operator buttons
		JButton addButton = new JButton("+");
		JButton subtractButton = new JButton("-");
		JButton multiplyButton = new JButton("*");
		JButton divideButton = new JButton("÷");
		JButton equalsButton = new JButton("=");
		JButton clearButton = new JButton("C");
		JButton clearEntryButton = new JButton("CE");
		JButton decimalButton = new JButton(".");
		JButton sqrtButton = new JButton("√");
		JButton powButton = new JButton("x²");
		// set fonts for operator buttons
		addButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		subtractButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		multiplyButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		divideButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		equalsButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		clearButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		clearEntryButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		decimalButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		sqrtButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		powButton.setFont(new Font("Arial", Font.PLAIN, fontSize));

		// arrange button grid layout
		buttons.add(clearButton);
		buttons.add(clearEntryButton);
		buttons.add(sqrtButton);
		buttons.add(powButton);
		buttons.add(numberButtons[7]);
		buttons.add(numberButtons[8]);
		buttons.add(numberButtons[9]);
		buttons.add(divideButton);
		buttons.add(numberButtons[4]);
		buttons.add(numberButtons[5]);
		buttons.add(numberButtons[6]);
		buttons.add(multiplyButton);
		buttons.add(numberButtons[1]);
		buttons.add(numberButtons[2]);
		buttons.add(numberButtons[3]);
		buttons.add(subtractButton);
		buttons.add(numberButtons[0]);
		buttons.add(decimalButton);
		buttons.add(equalsButton);
		buttons.add(addButton);
	}

	private void createFrame() {
		// add text and buttons panel to frame
		frame.add(text, BorderLayout.NORTH);
		frame.add(buttons);
		// finalize frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
	}
}

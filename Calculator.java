import javax.swing.*;
import java.awt.*;

public class Calculator {
	private int fontSize = 24;
	final static int WIDTH = 300;
	final static int HEIGHT = 300;

	public Calculator() {
		// call the other constructor with default font size
		this(24);
	}

	public Calculator(int fontSize) {
		// set font size
		this.fontSize = fontSize;

		// frame setup
		JFrame frame = new JFrame("Calculator");

		// text display and button container setup
		JTextField text = new JTextField("");
		text.setEditable(false);
		text.setHorizontalAlignment(JTextField.RIGHT);
		// set height of text field
		text.setPreferredSize(new Dimension(WIDTH,50));
		text.setFont(new Font("Arial", Font.PLAIN, fontSize));
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(5, 4));
		
		// button creation
		JButton[] numberButtons = new JButton[10];
		for (int i = 0; i < 10; i++) {
			numberButtons[i] = new JButton(Integer.toString(i));
			numberButtons[i].setFont(new Font("Arial", Font.PLAIN, fontSize));
		}
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

		// button addition in order
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

		// text display and button container addition
		frame.add(text, BorderLayout.NORTH);
		frame.add(buttons);

		// frame finalization
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
	}
}

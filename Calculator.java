import javax.swing.*;
import java.awt.*;

public class Calculator {
	// GUI properties
	private int fontSize = 24;
	private final static int WIDTH = 300;
	private final static int HEIGHT = 300;

	// GUI components
	JButton[] numberButtons = new JButton[10];
	JFrame frame = new JFrame("Calculator");
	JTextField text = new JTextField("");
	JPanel buttons = new JPanel();

	// enums for operator types and calculator states
	private enum Operator {
		NONE,
		ADD,
		SUBTRACT,
		MULTIPLY,
		DIVIDE,
		SQRT,
		POW
	}
	private enum State {
		FIRST_OPERAND,
		SECOND_OPERAND
	}
	
	// state variables
	private Operator operator = Operator.NONE;
	private State state = State.FIRST_OPERAND;
	private double firstOperand = 0;
	private double secondOperand = 0;

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
		updateText();
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
			final int number = i;
			numberButtons[i] = new JButton(Integer.toString(i));
			numberButtons[i].setFont(new Font("Arial", Font.PLAIN, fontSize));
			numberButtons[i].addActionListener(e -> {
				if (state == State.FIRST_OPERAND) {
					// add the digit while keeping the sign
					firstOperand = (Double.compare(firstOperand, 0.0) == 0) ? number
             					 : (Double.compare(firstOperand, -0.0) == 0) ? -number
              					 : firstOperand * 10 + number;
					updateText();
				} else if (state == State.SECOND_OPERAND) {
					// add the digit while keeping the sign
					secondOperand = (Double.compare(secondOperand, 0.0) == 0) ? number
             					 : (Double.compare(secondOperand, -0.0) == 0) ? -number
              					 : secondOperand * 10 + number;
					updateText();
				}
			});
		}
		// create operator buttons
		JButton addButton = new JButton("+");
		JButton subtractButton = new JButton("-");
		JButton multiplyButton = new JButton("x");
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
		// add action listeners for operator buttons
		addButton.addActionListener(e -> {
			if(state != State.FIRST_OPERAND) {
				return;
			}
			operator = Operator.ADD;
			state = State.SECOND_OPERAND;
			text.setText("+");
		});
		subtractButton.addActionListener(e -> {
			// handle negative numbers
			if (handleNegative()) {
				return;
			}
			// handle subtraction
			operator = Operator.SUBTRACT;
			state = State.SECOND_OPERAND;
			text.setText("-");
		});
		multiplyButton.addActionListener(e -> {
			if(state != State.FIRST_OPERAND) {
				return;
			}
			operator = Operator.MULTIPLY;
			state = State.SECOND_OPERAND;
			text.setText("x");
		});
		divideButton.addActionListener(e -> {
			if(state != State.FIRST_OPERAND) {
				return;
			}
			operator = Operator.DIVIDE;
			state = State.SECOND_OPERAND;
			text.setText("÷");
		});
		sqrtButton.addActionListener(e -> {
			if(state == State.FIRST_OPERAND) {
				firstOperand = Math.sqrt(firstOperand);
			} else if (state == State.SECOND_OPERAND) {
				secondOperand = Math.sqrt(secondOperand);
			}
			updateText();
			operator = Operator.NONE;
			state = State.FIRST_OPERAND;
		});
		powButton.addActionListener(e -> {
			if(state == State.FIRST_OPERAND) {
				firstOperand = Math.pow(firstOperand, 2);
			} else if (state == State.SECOND_OPERAND) {
				secondOperand = Math.pow(secondOperand, 2);
			}
			updateText();
			operator = Operator.NONE;
			state = State.FIRST_OPERAND;
		});
		equalsButton.addActionListener(e -> {
			if(operator == Operator.NONE) {
				return;
			}
			double result = 0;
			switch (operator) {
				case ADD:
					result = firstOperand + secondOperand;
					break;
				case SUBTRACT:
					result = firstOperand - secondOperand;
					break;
				case MULTIPLY:
					result = firstOperand * secondOperand;
					break;
				case DIVIDE:
					result = firstOperand / secondOperand;
					break;
				default:
					break;
			}
			firstOperand = result;
			secondOperand = 0;
			state = State.FIRST_OPERAND;
			operator = Operator.NONE;
			updateText();
		});
		clearButton.addActionListener(e -> {
			firstOperand = 0;
			secondOperand = 0;
			state = State.FIRST_OPERAND;
			operator = Operator.NONE;
			updateText();
		});
		clearEntryButton.addActionListener(e -> {
			if (state == State.FIRST_OPERAND) {
				firstOperand = 0;
			} else if (state == State.SECOND_OPERAND) {
				secondOperand = 0;
			}
			updateText();
		});
		// todo: implement decimal button

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

	private void updateText() {
		if (state == State.FIRST_OPERAND) {
			// fix rounding errors
			firstOperand = Math.round(firstOperand * 1000000000) / 1000000000.0;
			text.setText(Double.toString(firstOperand).replace(".0", ""));
		} else if (state == State.SECOND_OPERAND) {
			// fix rounding errors
			secondOperand = Math.round(secondOperand * 1000000000) / 1000000000.0;
			text.setText(Double.toString(secondOperand).replace(".0", ""));
		} else {
			text.setText("Error");
		}
	}

	// handle negative numbers
	private boolean handleNegative() {
		if (state == State.FIRST_OPERAND && Double.compare(firstOperand, 0d) == 0) {
			firstOperand = -0d;
			updateText();
			return true;
		}
		if(state == State.SECOND_OPERAND && Double.compare(secondOperand, 0d) == 0) {
			secondOperand = -0d;
			updateText();
			return true;
		}
		return false;
	}
}

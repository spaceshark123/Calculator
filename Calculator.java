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
	private boolean addDecimal = false;
	private double placeValue = 1;
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
				double operand = state == State.FIRST_OPERAND ? firstOperand : secondOperand;
				// check if the operand is zero (sign matters)
				int zero = Double.compare(operand, 0.0) == 0 ? 1 : Double.compare(operand, -0.0) == 0 ? -1 : 0;
				placeValue = addDecimal ? placeValue * 0.1 : 1;
				int sign = zero != 0 ? zero : (int) Math.signum(operand);
				if (zero != 0) {
					// handle positive and negative zeros
					operand = sign * placeValue * number;
				} else {
					// calculate the new number based on the place value
					operand = addDecimal ? operand + placeValue * number * sign : operand * 10 + number * sign;
				}
				// update the operand based on the state
				if (state == State.FIRST_OPERAND) {
					firstOperand = operand;
				} else {
					secondOperand = operand;
				}
				updateText();
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
			addDecimal = false;
			placeValue = 1;
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
			addDecimal = false;
			placeValue = 1;
			text.setText("-");
		});
		multiplyButton.addActionListener(e -> {
			if(state != State.FIRST_OPERAND) {
				return;
			}
			operator = Operator.MULTIPLY;
			state = State.SECOND_OPERAND;
			addDecimal = false;
			placeValue = 1;
			text.setText("x");
		});
		divideButton.addActionListener(e -> {
			if(state != State.FIRST_OPERAND) {
				return;
			}
			operator = Operator.DIVIDE;
			state = State.SECOND_OPERAND;
			addDecimal = false;
			placeValue = 1;
			text.setText("÷");
		});
		sqrtButton.addActionListener(e -> {
			firstOperand = Math.sqrt(state == State.FIRST_OPERAND ? firstOperand : secondOperand);
			reset();
		});
		powButton.addActionListener(e -> {
			firstOperand = Math.pow(state == State.FIRST_OPERAND ? firstOperand : secondOperand, 2);
			reset();
		});
		// defines behavior for binary operators
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
			reset();
		});
		clearButton.addActionListener(e -> {
			firstOperand = 0;
			reset();
		});
		clearEntryButton.addActionListener(e -> {
			if (state == State.FIRST_OPERAND) {
				firstOperand = 0;
			} else if (state == State.SECOND_OPERAND) {
				secondOperand = 0;
			}
			addDecimal = false;
			placeValue = 1;
			updateText();
		});
		decimalButton.addActionListener(e -> {
			if (addDecimal) {
				return;
			}
			addDecimal = true;
			updateText();
		});

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

	// update text field based on number
	private void updateText() {
		double rawNumber = state == State.FIRST_OPERAND ? firstOperand : secondOperand;
		if(Double.isNaN(rawNumber) || Double.isInfinite(rawNumber)) {
			text.setText("Error");
			return;
		}
		double number = fixRoundingErrors(rawNumber, 9);
		if (number != rawNumber) {
			updateDecimal(number);
		}
		String textValue = Double.toString(number);
		if (number % 1 == 0 && addDecimal && placeValue == 1) {
			textValue = textValue.replace(".0", ".");
		} else if (number % 1 == 0 && placeValue == 1) {
			textValue = textValue.replace(".0", "");
		} else if(addDecimal) {
			// add trailing zeros until placeValue
			while (textValue.length() - textValue.indexOf(".") - 1 < Math.log10(1 / placeValue)) {
				textValue += "0";
			}
		}
		text.setText(textValue);
	}

	// update placeValue and addDecimal based on operand
	private void updateDecimal(double operand) {
		addDecimal = operand % 1 != 0;
		// if addDecimal, set placeValue to least significant decimal place (0.1, 0.01, etc.) or else set to 1
		if(addDecimal) {
			int multiplier = 1;
			while ((Math.abs(firstOperand) * multiplier) % 1 > 1e-10) {
				multiplier *= 10;
			}
			placeValue = 1d / multiplier;
		} else {
			placeValue = 1;
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

	private double fixRoundingErrors(double value, int decimalPlaces) {
		if (value == 0 || Math.abs(value) >= Math.pow(10, decimalPlaces)) {
			return value;
		}
        // Define the factor for rounding to the specified number of decimal places
        double factor = Math.pow(10, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

	// reset calculator state
	private void reset() {
		state = State.FIRST_OPERAND;
		operator = Operator.NONE;
		addDecimal = false;
		secondOperand = 0;
		updateDecimal(firstOperand);
		updateText();
	}
}

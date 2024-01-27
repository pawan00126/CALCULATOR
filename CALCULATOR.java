import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

class CALCULATOR extends JFrame {

    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton equalsButton;
    private JButton clearButton;

    private String currentInput;

    public CALCULATOR() {
        setTitle("Improved Simple Calculator");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentInput = "";

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setPreferredSize(new Dimension(400, 50));
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            numberButtons[i].addActionListener(new NumberButtonListener());
            buttonPanel.add(numberButtons[i]);
        }

        operationButtons = new JButton[4];
        operationButtons[0] = new JButton("+");
        operationButtons[1] = new JButton("-");
        operationButtons[2] = new JButton("*");
        operationButtons[3] = new JButton("/");
        for (int i = 0; i < 4; i++) {
            operationButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            operationButtons[i].addActionListener(new OperationButtonListener());
            buttonPanel.add(operationButtons[i]);
        }

        equalsButton = new JButton("=");
        equalsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        equalsButton.addActionListener(new EqualsButtonListener());
        buttonPanel.add(equalsButton);

        clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.addActionListener(new ClearButtonListener());
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        // Allow keyboard input
        displayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();

                if (Character.isDigit(keyChar) || keyChar == '.' || keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
                    currentInput += keyChar;
                    displayField.setText(currentInput);
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (!currentInput.isEmpty()) {
                        currentInput = currentInput.substring(0, currentInput.length() - 1);
                        displayField.setText(currentInput);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performOperation();
                    currentInput = "";
                }
            }
        });
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();
            currentInput += buttonText;
            displayField.setText(currentInput);
        }
    }

    private class OperationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();
            currentInput += buttonText;
            displayField.setText(currentInput);
        }
    }

    private class EqualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            performOperation();
            currentInput = "";
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayField.setText("");
            currentInput = "";
        }
    }

    private void performOperation() {
        try {
            String[] parts = currentInput.split("[+\\-*/]");
            double operand1 = Double.parseDouble(parts[0]);
            double operand2 = Double.parseDouble(parts[1]);
            char operator = currentInput.charAt(parts[0].length());

            double result;

            switch (operator) {
                case '+':
                    result = operand1 + operand2;
                    break;
                case '-':
                    result = operand1 - operand2;
                    break;
                case '*':
                    result = operand1 * operand2;
                    break;
                case '/':
                    result = operand1 / operand2;
                    break;
                default:
                    result = 0;
            }

            displayField.setText(currentInput + "=" + result);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            displayField.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CALCULATOR().setVisible(true));
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MortgageUI().createAndShowGUI());
    }
}

class MortgageUI {
    private JTextField principalField;
    private JTextField annualInterestField;
    private JTextField yearsField;
    private JTextArea resultArea;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Mortgage Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);  // Larger window size
        frame.setLocationRelativeTo(null);  // Center the window


        JPanel contentPanel = new RoundedPanel();
        contentPanel.setLayout(new BorderLayout());
        frame.add(contentPanel);


        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("Mortgage Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Cool font
        titleLabel.setForeground(Color.BLACK);
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.add(titleLabel);
        contentPanel.add(headerPanel, BorderLayout.NORTH);


        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(new JLabel("Principal:"));
        principalField = new JTextField();
        principalField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputPanel.add(principalField);

        inputPanel.add(new JLabel("Annual Interest Rate (%):"));
        annualInterestField = new JTextField();
        annualInterestField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputPanel.add(annualInterestField);

        inputPanel.add(new JLabel("Years:"));
        yearsField = new JTextField();
        yearsField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputPanel.add(yearsField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calculateButton.setBackground(new Color(200, 200, 200));
        calculateButton.setForeground(Color.BLACK);
        calculateButton.setFocusPainted(false);
        inputPanel.add(calculateButton);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Cool font
        resultArea.setBackground(Color.WHITE);
        resultArea.setForeground(Color.BLACK); // Black text
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        contentPanel.add(inputPanel, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int principal = Integer.parseInt(principalField.getText());
                    float annualInterest = Float.parseFloat(annualInterestField.getText());
                    byte years = Byte.parseByte(yearsField.getText());

                    MortgageCalculator calculator = new MortgageCalculator(principal, annualInterest, years);

                    StringBuilder result = new StringBuilder();
                    result.append("Mortgage Summary:\n");
                    result.append(String.format("Your monthly payment is: %s\n\n", String.format("$%.2f", calculator.calculateMortgage())));
                    result.append("Payment Schedule:\n");
                    for (int month = 0; month < calculator.getRemainingBalances().length; month++) {
                        result.append(String.format("Month %d: %s\n", month + 1, String.format("$%.2f", calculator.getRemainingBalances()[month])));
                    }
                    resultArea.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}

class RoundedPanel extends JPanel {
    private static final int ARC_WIDTH = 30;
    private static final int ARC_HEIGHT = 30;

    public RoundedPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
        g2.dispose();
        super.paintComponent(g);
    }
}

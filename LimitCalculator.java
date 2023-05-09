import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.mariuszgromada.math.mxparser.Function;

public class LimitCalculator extends JFrame implements ActionListener {
    private JTextField functionField, xField;
    private JLabel resultLabel;

    public LimitCalculator() {
        setTitle("Calculadora de Limites");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.setBackground(Color.BLUE);
        inputPanel.add(new JLabel("Função:")).setForeground(Color.WHITE);
        functionField = new JTextField(20);
        inputPanel.add(functionField);

        inputPanel.add(new JLabel("Aproximação do x:")).setForeground(Color.WHITE);
        xField = new JTextField(20);
        inputPanel.add(xField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE);
        JButton calculateButton = new JButton("Calcular");
        calculateButton.setBackground(Color.BLACK);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(this);
        buttonPanel.add(calculateButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(Color.BLUE);
        resultPanel.setForeground(Color.WHITE);
        JLabel resultTextLabel = new JLabel("Limite - Resultado:");
        resultTextLabel.setForeground(Color.WHITE);
        resultTextLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        resultPanel.add(resultTextLabel, BorderLayout.NORTH);

        resultLabel = new JLabel("");
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Calcular")) {
            String function = functionField.getText();
            double x = Double.parseDouble(xField.getText());

            double result = calculateLimit(function, x);

            resultLabel.setText(Double.toString(result));
        }
    }

    private double calculateLimit(String function, double x) {
        // Verifica se a função tem um termo com denominador que se anula quando x = 0
        if (function.contains("/x") && x == 0) {
            return Double.NaN; // Retorna NaN caso o denominador seja 0 e x = 0
        }

        // Define a função f(x) a ser calculada
        Function f = new Function("f(x) = " + function);

        // Define os valores do intervalo [x-0.001, x+0.001] para calcular o limite
        double leftLimit = f.calculate(x - 0.001);
        double rightLimit = f.calculate(x + 0.001);

        // Verifica se o limite existe e é numérico
        if (Double.isNaN(leftLimit) || Double.isNaN(rightLimit)) {
            return Double.NaN; // caso o limite não exista
        } else {
            return (leftLimit + rightLimit) / 2.0;
        }
    }

    public static void main(String[] args) {
        LimitCalculator calculator = new LimitCalculator();
    }
}

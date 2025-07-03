import javax.swing.*; //components
import java.awt.*; //BG
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class PasswordGenerator extends JFrame {
    private JTextField lengthField;
    private JCheckBox upperCaseCheckBox;
    private JCheckBox lowerCaseCheckBox;
    private JCheckBox numbersCheckBox;
    private JCheckBox specialCharsCheckBox;
    private JTextArea passwordArea;

    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public PasswordGenerator() {
        setTitle("Password Generator");//title
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());//position

        // Input panel
        JPanel inputPanel = new JPanel(); 
        inputPanel.setLayout(new GridLayout(5, 2));//row,column

        inputPanel.add(new JLabel("Password Length:"));
        lengthField = new JTextField("8");
        inputPanel.add(lengthField); //display 

        upperCaseCheckBox = new JCheckBox("Include Uppercase Letters(ABC)");
        inputPanel.add(upperCaseCheckBox);

        lowerCaseCheckBox = new JCheckBox("Include Lowercase Letters(abc)");
        inputPanel.add(lowerCaseCheckBox);

        numbersCheckBox = new JCheckBox("Include Numbers(123)");
        inputPanel.add(numbersCheckBox);

        specialCharsCheckBox = new JCheckBox("Include Special Characters(#*%!)");
        inputPanel.add(specialCharsCheckBox);

        JButton generateButton = new JButton("Generate Password");
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.NORTH);//placing of row & col at the top

        // Password area
        passwordArea = new JTextArea();
        passwordArea.setEditable(false);
        add(new JScrollPane(passwordArea), BorderLayout.CENTER);

        // Action listener for the button
        generateButton.addActionListener(new ActionListener() {  // Add b
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });
    }

    private void generatePassword() {
        int length;
        try { //non-numeric & <=0
            length = Integer.parseInt(lengthField.getText());
            if (length <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid length.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        StringBuilder characterSet = new StringBuilder();
        if (upperCaseCheckBox.isSelected()) {
            characterSet.append(UPPER_CASE);
        }
        if (lowerCaseCheckBox.isSelected()) {
            characterSet.append(LOWER_CASE);
        }
        if (numbersCheckBox.isSelected()) {
            characterSet.append(NUMBERS);
        }
        if (specialCharsCheckBox.isSelected()) {
            characterSet.append(SPECIAL_CHARS);
        }

        if (characterSet.length() == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one character type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) { //build pass char - char
            int index = random.nextInt(characterSet.length()); 
            password.append(characterSet.charAt(index)); //append char to pass
        }

        passwordArea.setText(password.toString()); 
    }   //Converts SB string & sets in text area.

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { //Ensures GUI runs onThread.
            PasswordGenerator generator = new PasswordGenerator();
            generator.setVisible(true); //Jframe visibility
        });
    }
}
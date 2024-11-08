package view;

import controller.MemberController;
import model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberRegistrationForm extends JFrame {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JPasswordField password;
    private JTextField txtAge;
    private JButton btnRegister;
    private JButton btnlogin;
    private JPanel panel;
    private JLabel label;

    public MemberRegistrationForm() {
        setTitle("Member Registration");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        // Initializing components
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        password = new JPasswordField();
        txtAge = new JTextField();
        btnRegister = new JButton("Register");
        btnlogin = new JButton("Login");  // Initialize btnlogin

        // Set button color
        btnRegister.setBackground(Color.GREEN);
        btnRegister.setForeground(Color.BLACK);

        // Label for the form title
        label = new JLabel("Register now");
        label.setBounds(150, 50, 300, 30);
        label.setForeground(Color.GREEN);
        label.setFont(new Font("Arial", Font.ITALIC, 22));
        add(label);

        // Panel for input fields
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBounds(50, 100, 350, 160);
        panel.setBackground(Color.BLACK);

        // Labels and input fields
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.WHITE);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JLabel ageLabel = new JLabel("Enter Age:");
        ageLabel.setForeground(Color.WHITE);

        panel.add(firstNameLabel);
        panel.add(txtFirstName);
        panel.add(lastNameLabel);
        panel.add(txtLastName);
        panel.add(emailLabel);
        panel.add(txtEmail);
        panel.add(passwordLabel);
        panel.add(password);
        panel.add(ageLabel);
        panel.add(txtAge);

        btnRegister.setBounds(50, 300, 150, 30);
        btnlogin.setBounds(250, 300, 150, 30);

        add(panel);
        add(btnRegister);
        add(btnlogin);

        // Action listener for Register button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String firstName = txtFirstName.getText();
                    String lastName = txtLastName.getText();
                    String email = txtEmail.getText();
                    String pass = new String(password.getPassword());
                    int age = Integer.parseInt(txtAge.getText());
                    double registrationFee = 1000;
                    double totalShares = 0;

                    if (age < 18 || age > 35) {
                        JOptionPane.showMessageDialog(null, "Your age doesn't qualify you to be a member");
                        return;
                    }

                    int id = MemberController.getLastMemberId();

                    Member member = new Member(id,age, firstName, lastName, email, pass, registrationFee, totalShares);
                    Member registerMember =  MemberController.registerMember(member);
                    JOptionPane.showMessageDialog(null, "Member registered successfully!");
                    clearFields();
                    dispose();
                    new LoanApplicationForm(registerMember);
                }
            }
        });

        // Action listener for Login button
        btnlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLoginFields()) {
                    String email = txtEmail.getText();
                    String pass = new String(password.getPassword());

                    Member member = MemberController.login(email, pass);
                    boolean isNullMember = member == null;

                    if ( !isNullMember){
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        clearFields();
                        dispose();
                        new LoanApplicationForm(member);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email or password ,PLEASE REGISTER IS NOT REGISTRED", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setVisible(true);
    }

    // Validation method for registration fields
    private boolean validateFields() {
        if (txtFirstName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First Name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtLastName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Last Name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Invalid email format", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.getPassword().length < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtAge.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Age must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int age = Integer.parseInt(txtAge.getText());
        if (age < 18 || age > 35) {
            JOptionPane.showMessageDialog(null, "Age must be between 18 and 35", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Validation method for login fields
    private boolean validateLoginFields() {
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Invalid email format", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        password.setText("");
        txtAge.setText("");
    }
}

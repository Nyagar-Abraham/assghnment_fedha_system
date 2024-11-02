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
    private JTextField txtAge;
    private JButton btnRegister;
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
        txtAge = new JTextField();
        btnRegister = new JButton("Register");

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
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBounds(50, 100, 350, 120);
        panel.setBackground(Color.BLACK);

        // Labels and input fields
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.WHITE);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);
        JLabel ageLabel = new JLabel("Enter Age:");
        ageLabel.setForeground(Color.WHITE);

        panel.add(firstNameLabel);
        panel.add(txtFirstName);
        panel.add(lastNameLabel);
        panel.add(txtLastName);
        panel.add(ageLabel);
        panel.add(txtAge);

        btnRegister.setBounds(150, 260, 150, 30);
        add(panel);
        add(btnRegister);

        // Action listener for button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String firstName = txtFirstName.getText();
                    String lastName = txtLastName.getText();
                    int age = Integer.parseInt(txtAge.getText());
                    double registrationFee = 1000;
                    double totalShares = 0;

                    if (age < 18 || age > 35) {
                        JOptionPane.showMessageDialog(null, "Your age doesn't qualify you to be a member");
                        return;
                    }

                    Member member = new Member(1, age, firstName, lastName, registrationFee, totalShares);
                    MemberController.registerMember(member);
                    JOptionPane.showMessageDialog(null, "Member registered successfully!");
                    clearFields();
                    dispose();
                    new LoanApplicationForm();
                }
            }
        });

        setVisible(true);
    }

    // Validation method for input fields
    private boolean validateFields() {
        if (txtFirstName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First Name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtLastName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Last Name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!txtFirstName.getText().matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "First Name can only contain letters", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!txtLastName.getText().matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Last Name can only contain letters", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAge.setText("");
    }
}

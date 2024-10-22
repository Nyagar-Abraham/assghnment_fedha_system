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
    private JButton btnRegister;
    private JPanel panel;
    private JLabel label;

    public MemberRegistrationForm() {
        setTitle("Member Registration");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        btnRegister = new JButton("Register");

        label = new JLabel("Register now");
        label.setBounds(150, 50, 300, 30);
        label.setForeground(Color.green);
        label.setFont(new Font("Arial", Font.ITALIC, 22));
        add(label);

        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBounds(50, 100, 350, 90);

        panel.add(new JLabel("First Name:"));
        panel.add(txtFirstName);
        panel.add(new JLabel("Last Name:"));
        panel.add(txtLastName);

        btnRegister.setBounds(150, 210, 150, 30);
        add(panel);
        add(btnRegister);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String firstName = txtFirstName.getText();
                    String lastName = txtLastName.getText();
                    double registrationFee = 1000;
                    double totalShares = 0;

                    // Assuming Member ID is set to 1 for now
                    Member member = new Member(1, firstName, lastName, registrationFee, totalShares);
                    // MemberController.registerMember(member);
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
    }
}

package view;

import controller.LoanController;
import controller.MemberController;
import model.Loan;
import model.Member;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class LoanApplicationForm extends JFrame {
    private Member member;
    private JTextField txtMemberId;
    private JComboBox<String> cmbLoanType;
    private JComboBox<String> cmbGuarantors;
    private JTextField txtAmount;
    private JButton btnApply;
    private JPanel panel;
    private JLabel label;
    private JButton btnDelete;

    //  GET GUARANTORS
    ArrayList<Member> guarantors = (ArrayList<Member>)  MemberController.getAllUsers();

   for (Member guarantor : guarantors) {
        // Perform operations with each guarantor
        System.out.println("Guarantor Name: " + guarantor.getFirstName() + " " + guarantor.getLastName());
    }



    private final String[] loanCategories = {
            "Emergency Loan",
            "Short Loan (2 years)",
            "Short Loan (3 years)",
            "Short Loan (4 years)"
    };

    public LoanApplicationForm(Member member) {
        this.member = member;
        setTitle("Loan Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        txtMemberId = new JTextField();
        cmbLoanType = new JComboBox<>(loanCategories);  // Replaced with JComboBox
        txtAmount = new JTextField();
        btnApply = new JButton("Apply for Loan");

        // Set button color
        btnApply.setBackground(Color.GREEN);
        btnApply.setForeground(Color.BLACK);


        label = new JLabel("\uD83D\uDC4B Welcome " +member.getFirstName() );
        label.setBounds(150, 50, 300, 30);
        label.setForeground(Color.green);
        label.setFont(new Font("Arial", Font.ITALIC, 22));
        add(label);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBounds(50, 100, 350, 130);
        panel.setBackground(Color.BLACK);

        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setForeground(Color.WHITE);
        JLabel LoanTypeLabel = new JLabel("Loan Type:");
        LoanTypeLabel.setForeground(Color.WHITE);
        JLabel amountLabel = new JLabel("Amount :");
        amountLabel.setForeground(Color.WHITE);

        // Initialize the delete button
        btnDelete = new JButton("Delete User");
        btnDelete.setBounds(320, 240, 150, 30);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);

        panel.add(memberIdLabel);
        panel.add(txtMemberId);
        panel.add(LoanTypeLabel);
        panel.add(cmbLoanType);
        panel.add(amountLabel);
        panel.add(txtAmount);

        btnApply.setBounds(150, 240, 150, 30);
        add(panel);
        add(btnDelete);
        add(btnApply);

        // Action listener for Delete button
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    int memberId = Integer.parseInt(txtMemberId.getText());

                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        MemberController.deleteAllMembers();
                        JOptionPane.showMessageDialog(null, "User deleted successfully!");
                        clearFields();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    int memberId = Integer.parseInt(txtMemberId.getText());
                    String loanType = (String) cmbLoanType.getSelectedItem();
                    double amount = Double.parseDouble(txtAmount.getText());

                    // Set interest rate and repayment period based on the selected loan type
                    double interestRate;
                    int repaymentPeriod;

                    switch (loanType) {
                        case "Emergency Loan":
                            interestRate = 0.3;
                            repaymentPeriod = 12;
                            break;
                        case "Short Loan (2 years)":
                            interestRate = 0.6;
                            repaymentPeriod = 24;
                            break;
                        case "Short Loan (3 years)":
                            interestRate = 1.0;
                            repaymentPeriod = 36;
                            break;
                        case "Short Loan (4 years)":
                            interestRate = 1.4;
                            repaymentPeriod = 48;
                            break;
                        default:
                            interestRate = 0;
                            repaymentPeriod = 0;
                            break;
                    }

                    Loan loan = new Loan(0, memberId, loanType, amount, interestRate, repaymentPeriod);
//                    LoanController.applyForLoan(loan);
                    JOptionPane.showMessageDialog(null, "Loan application submitted successfully!");
                    clearFields();
                }
            }
        });

        setVisible(true);
    }

    // Validation method for input fields
    private boolean validateFields() {

        if (txtMemberId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Member ID cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(txtMemberId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Member ID must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (txtAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Amount cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double amount = Double.parseDouble(txtAmount.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(null, "Amount must be greater than 0", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Amount must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtMemberId.setText("");
        cmbLoanType.setSelectedIndex(0);  // Reset combo box
        txtAmount.setText("");
    }
}

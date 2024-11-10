package view;

import controller.LoanController;
import model.Loan;
import model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoanDetailFrame extends JFrame {
    private Member member;
    LoanDetailFrame(Loan loan, Member member) {
        this.member = member;
        setTitle("Loan Details");
        setSize(600, 500);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for displaying loan details with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);  // Set the background color to black
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        int row = 0;

        // Back Button at the top-left
        JButton btnBack = new JButton("Back");
        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setPreferredSize(new Dimension(70, 30));  // Button size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridwidth = 2;
        panel.add(btnBack, gbc);


        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new LoanApplicationForm(member);
            }
        });

        row++;


        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel loanTypeLabel = new JLabel("Loan Type:  ");
        loanTypeLabel.setForeground(Color.WHITE);
        panel.add(loanTypeLabel, gbc);


        gbc.gridx = 1;
        JTextField txtLoanType = new JTextField(loan.getLoanType(), 20);  // Make text field larger
        txtLoanType.setEditable(false); // Disabled for editing
        txtLoanType.setBackground(Color.LIGHT_GRAY);  // Change background to make it more visible
        panel.add(txtLoanType, gbc);
        row++;

        // Amount Label and Field (Disabled for editing)
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel amountLabel = new JLabel("Amount:  ");
        amountLabel .setForeground(Color.WHITE);
        panel.add(amountLabel, gbc);


        gbc.gridx = 1;
        JTextField txtAmount = new JTextField(String.valueOf(loan.getAmount()), 20);  // Make text field larger
        txtAmount.setEditable(false); // Disabled for editing
        txtAmount.setBackground(Color.LIGHT_GRAY);  // Change background to make it more visible
        panel.add(txtAmount, gbc);
        row++;

        // Grouping Guarantor Name and Guarantor Contact Close to Each Other
        JPanel guarantorPanel = new JPanel();
        guarantorPanel.setBackground(Color.BLACK);
        guarantorPanel.setLayout(new GridLayout(1, 2, 10, 0));  // Group labels closer with horizontal spacing
        JLabel guarantorLabel = new JLabel("Guarantor:  ");
        guarantorLabel.setForeground(Color.WHITE);
        JTextField txtGuarantor = new JTextField(loan.getGuarantorName(), 10);  // Make text field larger
        txtGuarantor.setBackground(Color.WHITE);
        txtGuarantor.setEditable(false);
        guarantorPanel.add(guarantorLabel);
        guarantorPanel.add(txtGuarantor);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(guarantorPanel, gbc);  // Add the grouped components
        row++;

        // Loan Date Label and Field (Disabled for editing)
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel loanDateLabel = new JLabel("Loan Date:  ");
        loanDateLabel.setForeground(Color.WHITE);
        panel.add(loanDateLabel, gbc);

        gbc.gridx = 1;
        JTextField txtLoanDate = new JTextField(String.valueOf(loan.getRepaymentPeriod()), 20);  // Larger text field
        txtLoanDate.setEditable(false); // Disabled for editing
        txtLoanDate.setBackground(Color.LIGHT_GRAY);  // Background color change
        panel.add(txtLoanDate, gbc);
        row++;

        // Interest Rate Label and Field (Disabled for editing)
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel interestLabel = new JLabel("Interest Rate:  ");
        interestLabel.setForeground(Color.WHITE);
        panel.add(interestLabel, gbc);

        gbc.gridx = 1;
        JTextField txtInterestRate = new JTextField(String.valueOf(loan.getInterestRate()), 20);
        txtInterestRate.setEditable(false);
        txtInterestRate.setBackground(Color.LIGHT_GRAY);
        panel.add(txtInterestRate, gbc);
        row++;

        // Loan Term Label and Field (Editable if necessary)
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel repaymentLabel = new JLabel("Repayment Period:  ");
        repaymentLabel.setForeground(Color.WHITE);
        panel.add(repaymentLabel, gbc);

        gbc.gridx = 1;
        JTextField txtRepaymentPeriod = new JTextField(String.valueOf(loan.getRepaymentPeriod()), 20);
        panel.add( txtRepaymentPeriod, gbc);
        row++;

        // Status Label and Field (Disabled for editing)
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel amountGuaranteedLabel = new JLabel("Amount Guaranteed:  ");
        amountGuaranteedLabel.setForeground(Color.WHITE);
        panel.add(amountGuaranteedLabel, gbc);
        gbc.gridx = 1;

        JTextField txtAmountGuaranteed = new JTextField(String.valueOf(loan.getGuaranteedAmount()), 20);
        txtAmountGuaranteed.setEditable(true);
        txtAmountGuaranteed.setBackground(Color.LIGHT_GRAY);
        panel.add(txtAmountGuaranteed, gbc);
        row++;

        // Delete Button with red background and larger size
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(btnDelete, gbc);

        // Update Button with blue background and larger size
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBackground(Color.BLUE);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setPreferredSize(new Dimension(150, 40));  // Larger button size
        gbc.gridx = 1;
        panel.add(btnUpdate, gbc);

        // Action listeners for Delete and Update buttons
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming loanId is the ID of the loan to be deleted
                int loanId = loan.getId(); // Replace with the actual loan ID from the loan object

                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION){
                    return;
                }
                // Call the delete method
                boolean isDeleted = LoanController.deleteLoanById(loanId);

                // Show appropriate message based on the deletion result
                if (isDeleted) {
                    JOptionPane.showMessageDialog(null, "Loan deleted successfully!");

                    dispose();
                    new LoanApplicationForm(member);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete loan.");
                }
            }
        });


// Action Listener for Update Button
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get and validate the updated values
                try {
                    int updatedRepaymentPeriod = Integer.parseInt( txtRepaymentPeriod.getText().trim());
                    double updatedGuaranteedAmount = Double.parseDouble(txtAmountGuaranteed.getText().trim());

                    if (updatedRepaymentPeriod <= 0) {
                        JOptionPane.showMessageDialog(null, "Repayment period must be positive.");
                        return;
                    }
                    if (updatedGuaranteedAmount < 0) {
                        JOptionPane.showMessageDialog(null, "Guaranteed amount cannot be negative.");
                        return;
                    }

                    // Update the loan object with new values
                    loan.setRepaymentPeriod(updatedRepaymentPeriod);
                    loan.setGuaranteedAmount(updatedGuaranteedAmount);

                    // Call the updateLoan method
                    Loan updatedLoan = LoanController.updateLoan(loan);

                    if (updatedLoan != null) {
                        JOptionPane.showMessageDialog(null, "Loan updated successfully!");
                        dispose();
                        new LoanDetailFrame(updatedLoan,member);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update loan.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values for Repayment Period and Guaranteed Amount.");
                }
            }
        });

        // Add the panel to the frame inside a JScrollPane
        add(new JScrollPane(panel));

        // Make the frame visible
        setVisible(true);
    }
}

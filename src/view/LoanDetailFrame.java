package view;

import model.Loan;

import javax.swing.*;
import java.awt.*;

class LoanDetailFrame extends JFrame {
    LoanDetailFrame(Loan loan) {
        setTitle("Loan Details");
        setSize(400, 300);  // Adjust as needed
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for displaying loan details
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Display the loan details
        JLabel loanTypeLabel = new JLabel("Loan Type: " + loan.getLoanType());
        JLabel amountLabel = new JLabel("Amount: " + loan.getAmount());
        JLabel guarantorLabel = new JLabel("Guarantor: " + loan.getGuarantorName());

        loanTypeLabel.setForeground(Color.BLACK);
        amountLabel.setForeground(Color.BLACK);
        guarantorLabel.setForeground(Color.BLACK);

        // Add the labels to the panel
        panel.add(loanTypeLabel);
        panel.add(amountLabel);
        panel.add(guarantorLabel);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }
}


package view;

import controller.DividendController;
import model.Dividend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DividendDistributionForm extends JFrame {
    private JTextField txtMemberId;
    private JTextField txtAmount;
    private JButton btnDistribute;

    public DividendDistributionForm() {
        setTitle("Dividend Distribution");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        txtMemberId = new JTextField();
        txtAmount = new JTextField();
        btnDistribute = new JButton("Distribute Dividend");

        add(new JLabel("Member ID:"));
        add(txtMemberId);
        add(new JLabel("Dividend Amount:"));
        add(txtAmount);
        add(btnDistribute);

        btnDistribute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int memberId = Integer.parseInt(txtMemberId.getText());
                double amount = Double.parseDouble(txtAmount.getText());

                Dividend dividend = new Dividend(0, memberId, amount);
                DividendController.distributeDividend(dividend);
                JOptionPane.showMessageDialog(null, "Dividend distributed successfully!");
                clearFields();
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        txtMemberId.setText("");
        txtAmount.setText("");
    }
}

package view;

import controller.FixedDepositController;
import model.FixedDeposit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FixedDepositForm extends JFrame {
    private JTextField txtMemberId;
    private JTextField txtDepositAmount;
    private JButton btnCreateDeposit;

    public FixedDepositForm() {
        setTitle("Fixed Deposit Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        txtMemberId = new JTextField();
        txtDepositAmount = new JTextField();
        btnCreateDeposit = new JButton("Create Fixed Deposit");

        add(new JLabel("Member ID:"));
        add(txtMemberId);
        add(new JLabel("Deposit Amount:"));
        add(txtDepositAmount);
        add(btnCreateDeposit);

        btnCreateDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int memberId = Integer.parseInt(txtMemberId.getText());
                double depositAmount = Double.parseDouble(txtDepositAmount.getText());

                FixedDeposit deposit = new FixedDeposit(0, memberId, depositAmount);
                FixedDepositController.createFixedDeposit(deposit);
                JOptionPane.showMessageDialog(null, "Fixed deposit created successfully!");
                clearFields();
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        txtMemberId.setText("");
        txtDepositAmount.setText("");
    }
}

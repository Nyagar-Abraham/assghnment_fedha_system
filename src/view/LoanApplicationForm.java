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
import java.util.List;


public class LoanApplicationForm extends JFrame {
    private static boolean isLoans = false;
    private Member member;
    private JTextField txtGuarateedAmount;
    private JComboBox<String> cmbLoanType;
    private JComboBox<String> cmbGuarantors;
    private JTextField txtAmount;
    private JButton btnApply;
    private JButton backBtn;
    private JPanel panel;
    private JLabel label;
    private JButton btnLogout;
    private ArrayList<String[]> guarantorDetails = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    ComboBoxModel<String> model;
    private LoanDisplay loanDisplay;

    //loan selection
    private String selectedGuarantorName;
    private int selectedGuarantorId;
    private double interestRate;
    private int repaymentPeriod;
    private double maxLoanAmount;



    private final String[] loanCategories = {
            "Emergency Loan",
            "Short Loan (2 years)",
            "Short Loan (3 years)",
            "Short Loan (4 years)"
    };

    public LoanApplicationForm(Member member) {
        this.member = member;
        isLoans = !LoanController.getUserLoansWithGuarantor(member.getId()).isEmpty();
        setTitle("Loan Application");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        // GET GUARANTORS
        ArrayList<Member> guarantors = MemberController.getAllUsers();

        // Iterate through each guarantor
        for (Member guarantor : guarantors) {
            if (guarantor.getId() == member.getId()) {
                continue;
            }

            // Create an array for each guarantor with their id and first name
            String[] details = new String[2];
            details[0] = String.valueOf(guarantor.getId());
            details[1] = guarantor.getFirstName();

            // Add the array to the list
            guarantorDetails.add(details);
            names.add(guarantor.getFirstName());
        }

        // Combo box setup
        txtGuarateedAmount = new JTextField();
        cmbLoanType = new JComboBox<>(loanCategories);
        model = new DefaultComboBoxModel<>(names.toArray(new String[0]));
        cmbGuarantors = new JComboBox<>(model);
        txtAmount = new JTextField();
        btnApply = new JButton("Apply for Loan");
        backBtn = new JButton("back");
        btnLogout = new JButton("log out");
        btnLogout.setBounds(600, 20, 120, 30);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);

        // Set button color
        btnApply.setBackground(Color.GREEN);
        btnApply.setForeground(Color.BLACK);

        // Set button color
        backBtn.setBackground(Color.lightGray);
        backBtn.setForeground(Color.BLACK);

        label = new JLabel("\uD83D\uDC4B Welcome " + member.getFirstName());
        label.setBounds(300, 50, 300, 30);
        label.setForeground(Color.green);
        label.setFont(new Font("Arial", Font.ITALIC, 26));
        add(label);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBounds(200, 100, 400, 250);
        panel.setBackground(Color.BLACK);

        JLabel lblGuanranteedAmount = new JLabel("Guaranteed Amount:");
        lblGuanranteedAmount.setForeground(Color.WHITE);
        JLabel LoanTypeLabel = new JLabel("Loan Type:");
        LoanTypeLabel.setForeground(Color.WHITE);
        JLabel guarantorsLabel = new JLabel("Select Guarantor :");
        guarantorsLabel.setForeground(Color.WHITE);
        JLabel amountLabel = new JLabel("Amount :");
        amountLabel.setForeground(Color.WHITE);



        panel.add(amountLabel);
        panel.add(txtAmount);
        panel.add(LoanTypeLabel);
        panel.add(cmbLoanType);
        panel.add(guarantorsLabel);
        panel.add(cmbGuarantors);


        btnApply.setBounds(300, 650, 150, 30);
        backBtn.setBounds(20,20,80 ,30);

        add(backBtn);
        add(panel);
        if(isLoans) {
            loanDisplay = new LoanDisplay(LoanController.getUserLoansWithGuarantor(member.getId()));
            loanDisplay.setBounds(100, 400, 600, 220);  // Set bounds for layout consistency
            add(loanDisplay);
        }
        add(btnLogout);
        add(btnApply);


        //back action
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MemberRegistrationForm();
                JOptionPane.showMessageDialog(null, "Please LogIn again", "Alert", JOptionPane.PLAIN_MESSAGE);

            }
        });

        // Action listener for Delete button (Log out)
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Prompt for confirmation before proceeding
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Call the method to log out (delete) the member from the database
                        boolean success = MemberController.logoutMemberById(member.getId());

                        if (success) {
                            clearFields();
                            dispose();
                           new MemberRegistrationForm();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to logout user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Handle invalid Member ID input
                    JOptionPane.showMessageDialog(null, "Please enter a valid Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Add event listener for loan type combo box
        cmbLoanType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loanType = (String) cmbLoanType.getSelectedItem();
                switch (loanType) {
                    case "Emergency Loan":
                        interestRate = 0.3;
                        repaymentPeriod = 12;
                        maxLoanAmount = member.getRegistrationFee() + member.getTotalShares();
                        break;
                    case "Short Loan (2 years)":
                        interestRate = 0.6;
                        repaymentPeriod = 24;
                        maxLoanAmount = 2 * (member.getRegistrationFee() + member.getTotalShares());
                        break;
                    case "Short Loan (3 years)":
                        interestRate = 1.0;
                        repaymentPeriod = 36;
                        maxLoanAmount = 3 * (member.getRegistrationFee() + member.getTotalShares());
                        break;
                    case "Short Loan (4 years)":
                        interestRate = 1.4;
                        repaymentPeriod = 48;
                        maxLoanAmount = 4 * (member.getRegistrationFee() + member.getTotalShares());
                        break;
                    default:
                        interestRate = 0;
                        repaymentPeriod = 0;
                        maxLoanAmount = 0;
                        break;
                }
            }
        });

        // Add event listener for guarantor combo box
        cmbGuarantors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cmbGuarantors.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < guarantorDetails.size()) {
                    String[] selectedGuarantor = guarantorDetails.get(selectedIndex);
                    selectedGuarantorName = selectedGuarantor[1]; // Store the name of the selected guarantor
                    selectedGuarantorId = Integer.parseInt(selectedGuarantor[0]);
                    panel.add(lblGuanranteedAmount);
                    panel.add(txtGuarateedAmount);
                }
            }
        });

        // Apply button action
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    double guaranteedAmount = Double.parseDouble(txtGuarateedAmount.getText());
                    String loanType = (String) cmbLoanType.getSelectedItem();
                    double amount = Double.parseDouble(txtAmount.getText());

                    Loan loan = new Loan(member.getId(), loanType, amount, interestRate, repaymentPeriod, selectedGuarantorId, guaranteedAmount);
                    Loan appliedLoan = LoanController.applyForLoan(loan);

                    JOptionPane.showMessageDialog(null, "Loan application submitted successfully!");
                    clearFields();

                    // Check if LoanDisplay already exists, otherwise create and add it
                    if (loanDisplay != null) {
                        remove(loanDisplay);
                    }
                    loanDisplay = new LoanDisplay(LoanController.getUserLoansWithGuarantor(member.getId()));
                    loanDisplay.setBounds(100, 400, 600, 220);
                    add(loanDisplay);
                    revalidate();
                    repaint();
                }
            }
        });


        setVisible(true);
    }


    // Validation method for input fields
    private boolean validateFields() {
        if (txtGuarateedAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Guaranteed Amount cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtGuarateedAmount.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount format", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Amount cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtAmount.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount format", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    private void clearFields() {
        txtGuarateedAmount.setText("");
        cmbLoanType.setSelectedIndex(0);
        cmbGuarantors.setSelectedIndex(0);
        txtAmount.setText("");
    }
}




class LoanDisplay extends JScrollPane {
    private List<Loan> loans;

    LoanDisplay(List<Loan> loans) {
        this.loans = loans;

        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBounds(100, 400, 800, 220);

        // Panel to hold the loans in a 4-column layout
        JPanel panel = new JPanel(new GridLayout(0, 4, 10, 5));
        panel.setBackground(Color.BLACK);

        for (Loan loan : loans) {
            // Create labels for each loan property to display in columns
            JLabel loanTypeLabel = new JLabel("Loan Type: " + loan.getLoanType());
            JLabel amountLabel = new JLabel("Amount: " + loan.getAmount());
            JLabel guarantorLabel = new JLabel("Guarantor: " + loan.getGuarantorName());

            loanTypeLabel.setForeground(Color.WHITE);
            amountLabel.setForeground(Color.WHITE);
            guarantorLabel.setForeground(Color.WHITE);

            // Create a 'View' button for each loan
            JButton viewButton = new JButton("View");
            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open a new frame to display loan details
                    LoanDetailFrame detailFrame = new LoanDetailFrame(loan);
                    detailFrame.setVisible(true);
                }
            });

            // Add components to the panel in a single row (loan information + button)
            panel.add(loanTypeLabel);
            panel.add(amountLabel);
            panel.add(guarantorLabel);
            panel.add(viewButton);
        }

        // Set the preferred size to enforce a 30px row height per loan
        panel.setPreferredSize(new Dimension(800, loans.size() * 30));
        setViewportView(panel);
    }
}




package KlaraProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class NewRegistrations extends JFrame {
    private JTextField textField_fname;   // Όνομα
    private JTextField textField_lname;   // Επώνυμο
    private JComboBox<String> comboSpecialty; // Ειδικότητα

    public NewRegistrations() {
        setTitle("Νέα Εγγραφή Γιατρού"); // Τίτλος παραθύρου
        setBounds(100, 100, 400, 260);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // Ετικέτα: Όνομα
        JLabel lblFname = new JLabel("Όνομα:");
        lblFname.setBounds(50, 30, 80, 20);
        getContentPane().add(lblFname);

        // Πεδίο: Όνομα
        textField_fname = new JTextField();
        textField_fname.setBounds(150, 30, 180, 20);
        getContentPane().add(textField_fname);

        // Ετικέτα: Επώνυμο
        JLabel lblLname = new JLabel("Επώνυμο:");
        lblLname.setBounds(50, 60, 80, 20);
        getContentPane().add(lblLname);

        // Πεδίο: Επώνυμο
        textField_lname = new JTextField();
        textField_lname.setBounds(150, 60, 180, 20);
        getContentPane().add(textField_lname);

        // Ετικέτα: Ειδικότητα
        JLabel lblSpecialty = new JLabel("Ειδικότητα:");
        lblSpecialty.setBounds(50, 100, 80, 20);
        getContentPane().add(lblSpecialty);

        // ComboBox με ειδικότητες
        comboSpecialty = new JComboBox<>(new String[] {
            "Καρδιολόγος", "Παιδίατρος", "Ορθοπεδικός", "Γυναικολόγος",
            "Οφθαλμίατρος", "ΩΡΛ", "Νευρολόγος", "Οδοντίατρος",
            "Πνευμονολόγος", "Δερματολόγος", "Ογκολόγος"
        });
        comboSpecialty.setBounds(150, 100, 180, 22);
        getContentPane().add(comboSpecialty);

        // Κουμπί: Καταχώρηση
        JButton btnSave = new JButton("Καταχώρηση");
        btnSave.setBounds(135, 160, 120, 25);
        getContentPane().add(btnSave);

        // ActionListener κουμπιού
        btnSave.addActionListener(e -> {
            String fname = textField_fname.getText().trim();
            String lname = textField_lname.getText().trim();
            String specialty = comboSpecialty.getSelectedItem().toString();

            if (fname.isEmpty() || lname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Συμπληρώστε όλα τα πεδία.");
                return;
            }

            // Έλεγχος χαρακτήρων
            if (!fname.matches("[\\p{L}]+")) {
                JOptionPane.showMessageDialog(this, "Το όνομα πρέπει να περιέχει μόνο γράμματα.");
                return;
            }

            if (!lname.matches("[\\p{L}]+")) {
                JOptionPane.showMessageDialog(this, "Το επώνυμο πρέπει να περιέχει μόνο γράμματα.");
                return;
            }

            try (Connection conn = DBConnections.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Αποτυχία σύνδεσης με τη βάση.");
                    return;
                }

                String sql = "INSERT INTO doctors (fname, lname, specialty) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, fname);
                stmt.setString(2, lname);
                stmt.setString(3, specialty);

                int rows = stmt.executeUpdate(); // Εκτέλεση καταχώρησης

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Η εγγραφή καταχωρήθηκε!");
                    textField_fname.setText("");
                    textField_lname.setText("");
                    comboSpecialty.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Αποτυχία καταχώρησης.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Σφάλμα: " + ex.getMessage());
            }
        });
    }
}

package KlaraProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DoctorManager extends JFrame {
    private JTextField textLname;

    public DoctorManager() { // Κατασκευαστής
        setTitle("Διαχείριση Γιατρών"); // Τίτλος παραθύρου
        setSize(522, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel label = new JLabel("Εισάγετε Επώνυμο (μόνο Ελληνικά γράμματα):");
        label.setBounds(0, 15, 219, 23);
        getContentPane().add(label);

        textLname = new JTextField(20); // Πεδίο για πληκτρολόγηση επωνύμου
        textLname.setBounds(229, 16, 166, 20);
        getContentPane().add(textLname);

        JButton btnSearch = new JButton("Αναζήτηση"); // Κουμπί για Αναζήτηση
        btnSearch.setBounds(405, 15, 85, 23);
        getContentPane().add(btnSearch);

        JButton btnRegistration = new JButton("Νέα εγγραφή"); // Κουμπί για νέα εγγραφή γιατρού
        btnRegistration.addActionListener(new ActionListener() { // Σε αυτό το σημείο τοποθετείται
            public void actionPerformed(ActionEvent e) {
                new NewRegistration().setVisible(true); // Άνοιγμα νέου παραθύρου
                dispose(); // Κλείσιμο τρέχον παραθύρου
            }
        });
        btnRegistration.setBounds(174, 85, 116, 23);
        getContentPane().add(btnRegistration);

        btnSearch.addActionListener(e -> {
            String searchLname = textLname.getText().trim(); // Παίρνει τιμή από το πεδίο

            if (searchLname.isEmpty()) { // Έλεγχος αν το πεδίο είναι κενό
                JOptionPane.showMessageDialog(this, "Το πεδίο επώνυμο είναι κενό.");
                return;
            }

            if (!searchLname.matches("^[\\p{IsGreek}]+$")) { // Έλεγχος αν το πεδίο έχει Ελληνικά γράμματα
                JOptionPane.showMessageDialog(this, "Το επώνυμο πρέπει να περιέχει μόνο Ελληνικά γράμματα. Παρακαλώ ξαναπροσπαθήστε.");
                return;
            }

            try (Connection conn = DBConnections.getConnection()) { // Σύνδεση με βάση δεδομένων
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Αδυναμία σύνδεσης με τη βάση.");
                    return;
                }

                String sql = "SELECT * FROM doctors WHERE lname = ?"; // Query για αναζήτηση lname με συγκεκριμένο lname
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, searchLname);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");

                    apotelesmataDoctors resultWindow = new apotelesmataDoctors(id, fname, lname );
                    resultWindow.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Δεν βρέθηκε γιατρός με αυτό το επώνυμο.");
                }

            } catch (Exception ex) { // Αν υπάρχει σφάλμα να το εκτυπώσει
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αναζήτηση: " + ex.getMessage());
            }
        });
    }
}

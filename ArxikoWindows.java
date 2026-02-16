package KlaraProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class ArxikoWindows extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane; // Το κύριο panel που περιέχει όλα τα στοιχεία

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ArxikoWindows frame = new ArxikoWindows(); // Δημιουργία παραθύρου
                    frame.setVisible(true); // Εμφάνιση παραθύρου
                } catch (Exception e) {
                    e.printStackTrace(); // Εκτύπωση σε περίπτωση σφάλματος
                }
            }
        });
    }

    public ArxikoWindows() {
        setTitle("Αρχικό Παράθυρο"); // Τίτλος παραθύρου
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300); // Θέση και μέγεθος παραθύρου
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("ΓΙΑΤΡΟΙ"); // Κουμπί που γράφει ΓΙΑΤΡΟΙ
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DoctorManager().setVisible(true); // Άνοιγμα νέου παραθύρου
                dispose(); // Κλείσιμο τρέχοντος παραθύρου
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(116, 74, 185, 66);
        contentPane.add(btnNewButton);
    }
}

package KlaraProject;

import javax.swing.*;
import java.awt.*;

public class NewRegistration extends JFrame {

    public NewRegistration() { // κατασκευαστής

        setTitle("Διαχείριση Γιατρών"); // Τίτλος παραθύρου
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Κλείνει μόνο το παράθυρο
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titleLabel = new JLabel("Μενού Διαχείρισης Γιατρών", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        getContentPane().add(titleLabel);

        JButton btnNew = new JButton("+ Νέα Εγγραφή");
        btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNew.addActionListener(e -> {
            new NewRegistrations().setVisible(true); // Ανοίγει τη φόρμα εγγραφής
            dispose();
        });

        getContentPane().add(btnNew);
    }
}

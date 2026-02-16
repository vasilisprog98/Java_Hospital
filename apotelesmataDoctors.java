package KlaraProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class apotelesmataDoctors extends JFrame {

    private JPanel contentPane;
    private JTextField txtId, txtFname, txtLname;
    private JComboBox<String> comboSpecialty;
    private JTable table;
    private JScrollPane scrollPane;

    public apotelesmataDoctors() { // Κατασκευαστής (default)
        setTitle("ΑΠΟΤΕΛΕΣΜΑΤΑ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(40, 20, 100, 20);
        lblId.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 150, 20);
        txtId.setEditable(false); // ID είναι auto-increment
        contentPane.add(txtId);

        JLabel lblFname = new JLabel("Όνομα:");
        lblFname.setBounds(45, 55, 100, 20);
        lblFname.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(lblFname);

        txtFname = new JTextField();
        txtFname.setBounds(130, 55, 200, 20);
        contentPane.add(txtFname);

        JLabel lblLname = new JLabel("Επώνυμο:");
        lblLname.setBounds(45, 90, 100, 20);
        lblLname.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(130, 90, 200, 20);
        contentPane.add(txtLname);

        JLabel lblSpecialty = new JLabel("Ειδικότητα:");
        lblSpecialty.setBounds(45, 125, 100, 20);
        lblSpecialty.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(lblSpecialty);

        comboSpecialty = new JComboBox<>(new String[]{
            "Καρδιολόγος", "Παθολόγος", "Παιδίατρος",
            "Ορθοπεδικός", "Νευρολόγος", "Γυναικολόγος",
            "Οδοντίατρος", "ΩΡΛ", "Δερματολόγος", "Ογκολόγος"
        });
        comboSpecialty.setBounds(130, 125, 200, 22);
        contentPane.add(comboSpecialty);

        // Κουμπιά
        JButton btnUpdate = new JButton("Ενημέρωση");
        btnUpdate.setBounds(45, 160, 120, 25);
        btnUpdate.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("Διαγραφή");
        btnDelete.setBounds(180, 160, 120, 25);
        btnDelete.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(btnDelete);

        JButton btnViewAll = new JButton("Εμφάνιση Όλων");
        btnViewAll.setBounds(320, 160, 160, 25);
        btnViewAll.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        contentPane.add(btnViewAll);

        // Πίνακας
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 600, 140);
        contentPane.add(scrollPane);

        // Ενέργειες κουμπιών
        btnUpdate.addActionListener(e -> updateDoctor());
        btnDelete.addActionListener(e -> deleteDoctor());
        btnViewAll.addActionListener(e -> loadAllDoctors());

        // Επιλογή γραμμής στον πίνακα
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0 && table.getColumnCount() >= 4) {
                    txtId.setText(table.getValueAt(row, 0).toString());
                    txtFname.setText(table.getValueAt(row, 1).toString());
                    txtLname.setText(table.getValueAt(row, 2).toString());
                    comboSpecialty.setSelectedItem(table.getValueAt(row, 3).toString());
                }
            }
        });
    }

    // Κατασκευαστής με δεδομένα
    public apotelesmataDoctors(int id, String fname, String lname) {
        this();
        setTitle("Αποτελέσματα - Dr. " + fname + " " + lname);
        txtId.setText(String.valueOf(id));
        txtFname.setText(fname);
        txtLname.setText(lname);
    }

    // Ενημέρωση γιατρού
    private void updateDoctor() {
        if (txtFname.getText().trim().isEmpty() || txtLname.getText().trim().isEmpty()) {
            showMessage("Συμπλήρωσε όλα τα πεδία.");
            return;
        }

        try (Connection conn = DBConnections.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE doctors SET fname = ?, lname = ?, specialty = ? WHERE id = ?"
            );
            stmt.setString(1, txtFname.getText().trim());
            stmt.setString(2, txtLname.getText().trim());
            stmt.setString(3, comboSpecialty.getSelectedItem().toString());
            stmt.setInt(4, Integer.parseInt(txtId.getText()));

            int rows = stmt.executeUpdate();
            showMessage(rows > 0 ? "Η εγγραφή ενημερώθηκε." : "Η ενημέρωση απέτυχε.");

        } catch (Exception ex) {
            showMessage("Σφάλμα: " + ex.getMessage());
        }
    }

    // Διαγραφή γιατρού
    private void deleteDoctor() {
        int confirm = JOptionPane.showConfirmDialog(
            this, "Διαγραφή γιατρού;", "Επιβεβαίωση", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DBConnections.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM doctors WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(txtId.getText()));

            int rows = stmt.executeUpdate();
            showMessage(rows > 0 ? "Ο γιατρός διαγράφηκε." : "Η διαγραφή απέτυχε.");
            clearFields();
            loadAllDoctors();

        } catch (Exception ex) {
            showMessage("Σφάλμα: " + ex.getMessage());
        }
    }

    // Εμφάνιση όλων
    private void loadAllDoctors() {
        try (Connection conn = DBConnections.getConnection()) {
            String sql = "SELECT id, fname, lname, specialty FROM doctors";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            String[] columns = {"ID", "Όνομα", "Επώνυμο", "Ειδικότητα"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getString("specialty")
                };
                model.addRow(row);
            }

            table.setModel(model);
        } catch (SQLException ex) {
            showMessage("Σφάλμα βάσης: " + ex.getMessage());
        }
    }

    // Μήνυμα διαλόγου
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // Καθαρισμός πεδίων
    private void clearFields() {
        txtId.setText("");
        txtFname.setText("");
        txtLname.setText("");
        comboSpecialty.setSelectedIndex(0);
    }
}

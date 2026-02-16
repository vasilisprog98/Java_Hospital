package KlaraProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class apotelesmataDoctor extends JFrame {

    
    private JPanel contentPane;
    private JTextField txtId, txtFname, txtLname;
    private JComboBox<String> comboSpeciality;
    private JTable table;
    private JScrollPane scrollPane;

    public apotelesmataDoctor() {//Κατασκευαστής//
        setTitle("ΑΠΟΤΕΛΕΣΜΑΤΑ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 460);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblId.setBounds(40, 20, 100, 20);
        contentPane.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 150, 20);
        txtId.setEditable(false);//Δεν αφήνουμε να επεξεργάσει το id γιατι το βάζει αυτόματα
        contentPane.add(txtId);//η βάση δεδομένων//

        JLabel lblFname = new JLabel("Όνομα:");
        lblFname.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblFname.setBounds(40, 55, 100, 20);
        contentPane.add(lblFname);

        txtFname = new JTextField();
        txtFname.setBounds(130, 55, 150, 20);
        contentPane.add(txtFname);

        JLabel lblLname = new JLabel("Επίθετο:");
        lblLname.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLname.setBounds(40, 90, 100, 20);
        contentPane.add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(130, 90, 150, 20);
        contentPane.add(txtLname);

        JLabel lblSpeciality = new JLabel("Ειδικότητα:");
        lblSpeciality.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSpeciality.setBounds(40, 125, 100, 20);
        contentPane.add(lblSpeciality);

        comboSpeciality = new JComboBox<>(new String[]{
            "Καρδιολόγος", "Παθολόγος", "Παιδίατρος", "Ορθοπεδικός", "Νευρολόγος"
        });
        comboSpeciality.setModel(new DefaultComboBoxModel(new String[] {"Καρδιολόγος", "Παθολόγος", "Παιδίατρος", "Ορθοπεδικός", "Νευρολόγος", "Ορθοδοντικός", "Παιδοδοντίατρος", "Γενικός Ιατρος", "Ουρολογος", "Δερματολόγος ", "Νεφρολόγος", "Αιματολόγος ", "Ογκολόγος ", "Ενδοκρινολόγος", "Γαστρεντερολόγος", "Γυναικολόγος", "Οφθαλμίατρος", "Ακτινολόγος ", "Ψυχολόγος"}));
        comboSpeciality.setBounds(130, 125, 150, 22);
        contentPane.add(comboSpeciality);

        JButton btnUpdate = new JButton("Ενημέρωση");//Κουμπί Ενημέρωση//
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnUpdate.setBounds(40, 160, 120, 25);
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("Διαγραφή");//Κουμπί Διαγραφή//
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.setBounds(180, 160, 120, 25);
        contentPane.add(btnDelete);

        JButton btnViewAll = new JButton("Εμφάνιση Όλων");//Κουμπί Εμφάνιση Όλων//
        btnViewAll.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnViewAll.setBounds(314, 160, 130, 25);
        contentPane.add(btnViewAll);
      
        //Πίνακας εμφάνισης γιατρών //
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 440, 200);
        contentPane.add(scrollPane);

        // Ενέργειες κουμπιών
        btnUpdate.addActionListener(e -> updateDoctor());
        btnDelete.addActionListener(e -> deleteDoctor());
        btnViewAll.addActionListener(e -> loadAllDoctors());

        // Επιλογή γραμμής πίνακα
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1 && table.getColumnCount() >= 4) {
                    txtId.setText(table.getValueAt(row, 0).toString());
                    txtFname.setText(table.getValueAt(row, 1).toString());
                    txtLname.setText(table.getValueAt(row, 2).toString());
                    comboSpeciality.setSelectedItem(table.getValueAt(row, 3).toString());
                }
            }
        });
    }

    // Constructor με δεδομένα
    public apotelesmataDoctor(int id, String fname, String lname) {
        this();
        setTitle("Αποτελέσματα - Dr. " + fname + " " + lname);
        txtId.setText(String.valueOf(id));
        txtFname.setText(fname);
        txtLname.setText(lname);
    }
     //Ενημέρωση//
    private void updateDoctor() {
        if (txtFname.getText().trim().isEmpty() || txtLname.getText().trim().isEmpty()) {
            showMessage("Συμπλήρωσε όλα τα πεδία.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE doctors SET fname = ?, lname = ?, specialty = ? WHERE id = ?");
            stmt.setString(1, txtFname.getText().trim());
            stmt.setString(2, txtLname.getText().trim());
            stmt.setString(3, comboSpeciality.getSelectedItem().toString());
            stmt.setInt(4, Integer.parseInt(txtId.getText()));
            int rows = stmt.executeUpdate();
            showMessage(rows > 0 ? "Η εγγραφή ενημερώθηκε." : "Η ενημέρωση απέτυχε.");
            loadAllDoctors();
        } catch (Exception ex) {
            showMessage("Σφάλμα: " + ex.getMessage());
        }
    }
       //Διαγραφή γιατρού//
    private void deleteDoctor() {
        int confirm = JOptionPane.showConfirmDialog(this, "Διαγραφή γιατρού;", "Επιβεβαίωση", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM doctors WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            int rows = stmt.executeUpdate();
            showMessage(rows > 0 ? "Ο γιατρός διαγράφηκε." : "Η διαγραφή απέτυχε.");
            if (rows > 0) {
                clearFields();
                loadAllDoctors();
            }
        } catch (Exception ex) {
            showMessage("Σφάλμα: " + ex.getMessage());
        }
    }

    private void loadAllDoctors() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, fname, lname, specialty FROM doctors";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            String[] columnNames = {"ID", "Όνομα", "Επίθετο", "Ειδικότητα"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

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

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void clearFields() {  //καθορισμός πεδίων//
        txtId.setText("");
        txtFname.setText("");
        txtLname.setText("");
        comboSpeciality.setSelectedIndex(0);
    }
}

package KlaraProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ArxikoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;  // To κύριο panel που περιέχει όλα τα στοιχεία //

	public static void main(String[] args) {  
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArxikoWindow frame = new ArxikoWindow(); //Δημιουργία παραθύρου //
					frame.setVisible(true); //Εμφάνιση παραθύρου //
				} catch (Exception e) {
					e.printStackTrace(); //Try catch , εκτυπώνουμε σε περίπτωση σφάλματος//
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ArxikoWindow() {
		setTitle("Aρχικό Παράθυρο"); //Τίτλος παραθύρου //
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 450, 300); //Θέση και μέγεθος παραθύρου//
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("ΓΙΑΤΡΟΙ"); //Κουμπί που γράφει Γιατροί//
		btnNewButton.addActionListener(new ActionListener() {//Με το addAction Listener δίνουμε την //
			public void actionPerformed(ActionEvent e) {     //Δυνατότητα να πάμε σε άλλο παράθυρο //
			 new DoctorManager().setVisible(true);// Άνοιγμα νέου παραθύρου//
			 dispose(); //Κλείνει το παράθυρο//
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(116, 74, 185, 66);
		contentPane.add(btnNewButton);
	}
}

package KlaraProject;

public class Doctor {
	
		private int id ;
		private String fname;
		private String lname;
		private String specialty;
		

		//κατασκευαστης//
		public Doctor (int id , String fname , String lname , String specialty ) {
			this.id = id;
			this.fname=fname;
			this.lname=lname;
			this.specialty=specialty;
			
		}
		public int getId () {
			return id ;
		}
		public String getFname() {
			return fname;
		}
		public String getLname() {
			return lname;
		}
		public String getSpeciality () {
			return specialty ;
		}
		
		

		public void setFname(String fname) { this.fname = fname; }
		public void setLname(String lname) { this.lname = lname; }
		public void setSpecialty(String specialty) { this.specialty = specialty; }
	

		@Override
		public String toString() {
		    return id + ": " + fname + " " + lname + " (" + specialty + ")";
		}
		}



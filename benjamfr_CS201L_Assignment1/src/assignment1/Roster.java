package assignment1;


import java.util.ArrayList;
import java.util.Collections;

public class Roster {
	ArrayList<Student> students;

	
	// Getters and Setters
	public ArrayList<Student> getRoster() {
		return students;
	}

	public void setRoster(ArrayList<Student> roster) {
		this.students = roster;
	}
	
	// Print students with their GPA
	public void print() {
		if (students.size() == 0) {
			System.out.println("(There are no students in this roster)");
		}
		for (int i = 0; i < students.size(); i++) {
			students.get(i).print();
			System.out.println("\n");
		}
	}
	
	// Print students in list format
	public void printlist() {
		System.out.println();
		for (int i = 0; i < students.size(); i++) {
			System.out.print("\t" + (i + 1) + ") ");
			students.get(i).printname();
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	// Adding new student to roster
	public void add(String fname, String lname) {
		Student student = new Student(fname, lname);
		students.add(student);
		System.out.println("New student added");
	}
	
	// Removing student from roster
	public void remove(int val) {
		students.remove(val);
	}
	
	// Setting a grade for a student
	public void setGrade(int val, int grade) {
		students.get(val).setGrade(grade);
	}
	
	// Initializing the fullname variable for sorting later
	public void setup() {
		for (int i = 0; i < students.size(); i++) {
			students.get(i).setup();
		}
	}
	
	// Sorting the roster
	public void sort(int type) {
		// Sort Alphabetically
		if (type == 1) {
			Collections.sort(students, new OrderByName());
		}
		// Sort by GPA
		else {
			Collections.sort(students, new OrderByGPA());
		}
	}

}

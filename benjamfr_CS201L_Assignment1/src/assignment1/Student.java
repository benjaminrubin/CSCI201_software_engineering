package assignment1;

import java.util.Comparator;

public class Student {
	private Name name;
	private String fullname;
	private int numGrades;
	private int average;

	// Default Constructor
	public Student() {
		// leave empty
	}
	
	// Constructor with parameter variables
	public Student(String fname, String lname) {
		name = new Name(fname, lname);
		average = 0;
		numGrades = 0;
		fullname = name.getFullName();
	}
	
	// Initializing all fullname variables after reading from JSON
	// fullname is later used for sorting students alphabetically
	public void setup() {
		fullname = name.getFullName();
	}
	
	// Getters and Setters
	public String getFullName() {
		return fullname;
	}

	public int getAverage() {
		return average;
	}
	
	public void setGrade(int grade) {
		average = ((average * numGrades) + grade) / (++numGrades);
	}
	
	// Print functions
	// Menu option 1
	public void print() {
		this.name.print();
		System.out.print(" " + average);
	}
	
	// For other menu options
	public void printname() {
		this.name.print();
	}
}


// Comparators for sorting the list of students

class OrderByGPA implements Comparator<Student> {
	@Override
	public int compare(Student s1, Student s2) {
		if(s1.getAverage() == s2.getAverage()) {
			return s1.getFullName().compareTo(s2.getFullName());
		}
		return s2.getAverage() - s1.getAverage();
	}
}

class OrderByName implements Comparator<Student> {
	@Override
	public int compare(Student s1, Student s2) {
		if(s1.getFullName().toLowerCase().equals(s2.getFullName().toLowerCase())){
			return s1.getFullName().compareTo(s2.getFullName());
		}
		else {
			return s1.getFullName().toLowerCase().compareTo(s2.getFullName().toLowerCase());
		}
	}
}
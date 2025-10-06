import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L; 

    int studentId;
    String name;
    double gpa;
    transient String password; 

    public Student(int studentId, String name, double gpa, String password) {
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
               "\nName: " + name +
               "\nGPA: " + gpa +
               "\nPassword: " + password;
    }
}

public class Assignment5 {
    public static void main(String[] args) {
        Student student = new Student(101, "Sidhant Bhure", 9, "mySecret123");

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            out.writeObject(student);
            System.out.println(" Student object has been serialized and saved to student.ser");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }

        Student loadedStudent = null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.ser"))) {
            loadedStudent = (Student) in.readObject();
            System.out.println("\n Student object has been deserialized from student.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }

        if (loadedStudent != null) {
            System.out.println("\n--- Loaded Student Details ---");
            System.out.println(loadedStudent);
            System.out.println("\nNote: The password field is null because it was marked as transient.");
        }
    }
}

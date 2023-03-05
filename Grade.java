
/**
 * Clasa pentru notele studentilor
 */

public class Grade implements Comparable, Cloneable {
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade(){
        partialScore = 0.0;
        examScore = 0.0;
        student = new Student();
        course = "";
    }

    public Grade(String course, Student student){
        this.partialScore = 0.0;
        this.examScore = 0.0;
        this.student = student;
        this.course = course;
    }

    public Grade(Double partialScore, Double examScore, String course, Student student) {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.course = course;
        this.student = student;
    }

    // Getters and setters
    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    //Returneaza nota finala, de tip Grade
    public Double getTotal() {
        return partialScore + examScore;
    }

    public String toString() {
        return getTotal().toString();
    }


    //Metoda compareTo pentru a putea sorta notele
    @Override
    public int compareTo(Object o) {
        Grade grade = (Grade) o;
        double total = getTotal();
        return Double.compare(total, grade.getTotal());
    }

    //Metoda clone pentru a putea face backup la note
    @Override
    public Object clone() throws CloneNotSupportedException {
        Grade grade1 = new Grade();
        Double newExamscore = this.examScore;
        grade1.setExamScore(examScore);
        Double newPartialScore = this.partialScore;
        grade1.setPartialScore(newPartialScore);
        String newCourse = this.course;
        grade1.setCourse(newCourse);
        grade1.setStudent(new Student(this.student.getFirstName(), this.student.getLastName()));
        return grade1;
    }
}


public class Notification {
    private Grade grade;

    public Notification(Grade grade) {
        this.grade = grade;
    }
    public Double getExamGrade() {
        return grade.getExamScore();
    }

    public Double getPartialGrade() {
        return grade.getPartialScore();
    }

    public String getCourse() {
        return grade.getCourse();
    }

    public Double getGrade() {
        return grade.getTotal();
    }

    public String toString() {
        return "Copilul dumneavoastra a primit la cursul:  " + grade.getCourse() + "\n"  + "Nota partiala: " + grade.getPartialScore() + "\n" + "Nota examen: " + grade.getExamScore() + "\n" + "Nota finala: " + grade.getTotal().toString().substring(0,4) + "\n";
    }

}
import java.util.TreeSet;

/**
 * Clasa care implementeaza metoda din interfata
 * Strategy si returneaza studentul cu
 * nota maxima la examenul partial
 */

public class BestPartialScore implements Strategy {

    // Metoda compute, care returneaza studentul cu cea mai mare
    // nota la partial
    @Override
    public Student compute(TreeSet<Grade> grades) {
        Student ans = null;
        Double value = 0.0;

        for (Grade grade : grades) {
            if (grade.getPartialScore() > value) {
                value = grade.getPartialScore();
                ans = grade.getStudent();
            }
        }

        return ans;
    }
}

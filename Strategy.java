import java.util.TreeSet;

/**
 * Interfata Strategy
 */
public interface Strategy {

    /** Metoda compute, care va fi implementata in clasele
     * care implementeaza Strategy
     */
    public Student compute(TreeSet<Grade> grades);
}

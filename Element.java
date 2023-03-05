/**
 * Interfata pentru sablonul de proiectare Visitor
 */
public interface Element {
    void accept(Visitor visitor);
}

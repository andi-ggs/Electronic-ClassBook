/**
 * Interfata pentru sablonul de proiectare Visitor
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Grade grade);
}

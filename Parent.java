import java.util.ArrayList;

/**
 * Clasa pentru parintii studentilor, care mosteneste clasa Person
 */
public class Parent extends User implements Observer{
    private final ArrayList<Notification> notifications;

    public Parent(){
        notifications = new ArrayList<>();
    }
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        notifications = new ArrayList<>();
    }

    //Implementeaza metoda update, specifica clasei Observer
    //intrucat elementele de tip Parent sunt observatorii catalogului
    public void update(Notification notification) {
        notifications.add(notification);
        //System.out.println("New classbook activity: " + notification.getMessage());
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public String toString() {
        return "Parent: " + super.toString();
    }
}

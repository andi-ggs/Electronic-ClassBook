import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

/**
 * Pagina destinata parintilor pentru a vedea activitatile copiilor lor
 */

public class ParentPage extends JFrame implements ActionListener {

    private JPanel panel;

    private JButton button;

    private final Parent parent;

    private JTextArea cursArea, notapartialaArea, notafinalaArea, notaexamenArea;

    private JLabel curs, notapartiala, notafinala, notaexamen;

    public ParentPage(String title, Parent parent) {
        super(title);
        this.parent = parent;
        setGUI();
        update();
    }

    public void setGUI() {
        setSize(800, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panel = new JPanel();

        curs = new JLabel("Curs");
        cursArea = new JTextArea(1, 12);

        notapartiala = new JLabel("Nota partiala");
        notapartialaArea = new JTextArea(1, 3);

        notafinala = new JLabel("Nota finala");
        notafinalaArea = new JTextArea(1, 3);

        notaexamen = new JLabel("Nota examen");
        notaexamenArea = new JTextArea(1, 3);


        //textArea = new JTextArea(10, 20);
        button = new JButton("Check notification");
        button.addActionListener(this);
        panel.add(curs);
        panel.add(cursArea);
        panel.add(notapartiala);
        panel.add(notapartialaArea);
        panel.add(notaexamen);
        panel.add(notaexamenArea);
        panel.add(notafinala);
        panel.add(notafinalaArea);
        panel.add(button, BorderLayout.SOUTH);
        //panel.add(textArea);
        add(panel);
    }

    public void update() {
        revalidate();
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            Catalog catalog = Catalog.getInstance();
            ArrayList<Notification> notifications = parent.getNotifications();
            cursArea.setText(notifications.get(0).getCourse());
            notapartialaArea.setText(notifications.get(0).getPartialGrade().toString());
            notaexamenArea.setText(notifications.get(0).getExamGrade().toString());
            notafinalaArea.setText(notifications.get(0).getGrade().toString().substring(0,4));
        }
        //button.setEnabled(false);
        //button.setText("No new notifications");
    }

}


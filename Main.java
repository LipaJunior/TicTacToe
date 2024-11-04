import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    public Main() {
        // Ustawienia okna głównego
        setTitle("TitTacToe");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Wycentruj okno

        // Tworzenie przycisków
        JButton button1 = new JButton("3x3");
        JButton button2 = new JButton("4x4");

        // Ustawienia layoutu
        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        add(panel);

        // Akcja dla pierwszego przycisku
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tworzenie nowego okna
                Window3x3 newWindow = new Window3x3();
                newWindow.setVisible(true);

                // Zamknięcie obecnego okna
                dispose();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window4x4 newWindow = new Window4x4();
                newWindow.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        // Uruchomienie programu
        SwingUtilities.invokeLater(() -> {
            Main mainFrame = new Main();
            mainFrame.setVisible(true);
        });
    }
}

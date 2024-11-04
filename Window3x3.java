import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window3x3 extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private boolean playerTurn = true;
    private String playerMark = "X";
    private String botMark = "O";
    private boolean gameOngoing = true;

    public Window3x3() {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel2.setSize(600, 50);
        panel1.setSize(600, 600);
        panel1.setAlignmentX(0);
        panel1.setAlignmentY(200);
        panel2.setBackground(new Color(255, 0, 0));
        JButton start = new JButton();
        panel2.add(start);
        setTitle("3x3");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set the layout for the 3x3 grid
        panel1.setLayout(new GridLayout(3, 3));

        // Create buttons and add them to the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60)); // Set larger font size
                buttons[i][j].setFocusPainted(false); // Disable button border after clicking
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                panel1.add(buttons[i][j]);
            }
        }
        add(panel2);
        add(panel1);
        setLayout(new GridLayout(2, 1));
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOngoing && playerTurn) {
                JButton button = buttons[row][col];
                if (button.getText().equals("")) {
                    button.setText(playerMark);
                    if (checkWinner(playerMark)) {
                        JOptionPane.showMessageDialog(null, "Player wins!");
                        gameOngoing = false;
                        return;
                    }
                    playerTurn = false;
                    if (!isBoardFull()) {
                        BestMove(botMark, playerMark);
                        if (checkWinner(botMark)) {
                            JOptionPane.showMessageDialog(null, "Bot wins!");
                            gameOngoing = false;
                            return;
                        }
                        playerTurn = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "It's a draw!");
                        gameOngoing = false;
                    }
                }
            }
        }
    }

    public boolean checkWinner(String mark) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(mark) && buttons[i][1].getText().equals(mark)
                    && buttons[i][2].getText().equals(mark)) {
                return true;
            }
            if (buttons[0][i].getText().equals(mark) && buttons[1][i].getText().equals(mark)
                    && buttons[2][i].getText().equals(mark)) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(mark) && buttons[1][1].getText().equals(mark)
                && buttons[2][2].getText().equals(mark)) {
            return true;
        }
        if (buttons[0][2].getText().equals(mark) && buttons[1][1].getText().equals(mark)
                && buttons[2][0].getText().equals(mark)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void BestMove(String botMark, String playerMark) {
        int bestScore = Integer.MIN_VALUE;
        int bestI = -1;
        int bestJ = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText(botMark);
                    int score = minimax(0, false, botMark, playerMark);
                    buttons[i][j].setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }

        if (bestI != -1 && bestJ != -1) {
            buttons[bestI][bestJ].setText(botMark);
        }
    }

    public int minimax(int depth, boolean isMaximizing, String botMark, String playerMark) {
        if (checkWinner(botMark)) {
            return 100;
        }
        if (checkWinner(playerMark)) {
            return -100;
        }
        if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setText(botMark);
                        int score = minimax(depth + 1, false, botMark, playerMark);
                        buttons[i][j].setText("");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setText(playerMark);
                        int score = minimax(depth + 1, true, botMark, playerMark);
                        buttons[i][j].setText("");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window3x3 window = new Window3x3();
            window.setVisible(true);
        });
    }
}

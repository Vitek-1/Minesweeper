import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EndingWindow extends JDialog {

    private String playersName = "";
    private int finalMin;
    private int finalSec;

    public EndingWindow(int finalMin, int finalSec) {
        this.finalMin = finalMin;
        this.finalSec = finalSec;
    }

    public void TheEnd() {
        setTitle("You won the game!");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(new Color(192, 192, 192));
        setModal(true);

        JLabel message = new JLabel("    Please type your name: ");
        message.setFont(new Font("Times New Roman", Font.BOLD, 25));
        message.setPreferredSize(new Dimension(300, 50));
        add(message, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/2));
        Border emptySpace = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        bottomPanel.setBorder(emptySpace);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.BOLD, 30));
        messageText.setPreferredSize(new Dimension(300, 150));
        bottomPanel.add(messageText,  BorderLayout.NORTH);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        submitButton.setFocusPainted(false);
        bottomPanel.add(submitButton, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e ->{
            Clicked(messageText);
        });

        messageText.addKeyListener(new  KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Clicked(messageText);
                }
            }
        });

        setVisible(true);
    }

    public void Clicked(JTextField messageText) {
        playersName = messageText.getText();
        if (playersName.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!");
            messageText.setText("");
        } else if (playersName.contains(";")) {
            JOptionPane.showMessageDialog(this, "You can't use {;}");
            messageText.setText("");
        }else{
            int totalS = finalMin*60 + finalSec;
            new Player(playersName, totalS, String.format("%02d:%02d", finalMin, finalSec)).UpdateLeaderBoard(finalMin, finalSec);
            dispose();
        }
    }
}

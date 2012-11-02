package Views;

import DiscWars.Board;
import DiscWars.Game;

import javax.swing.*;
import java.awt.*;

// Made by ollol646
public class View extends JFrame {
    private final BoardComponent gameComponent;
    private final JPanel panel;
    private final JLabel pauseLabel, gameOverLabel;
    private final StatusTextManager statusTextManager;

    //----- Public methods --------
    public View(Board board) {
        super("Disc Wars!");

        panel = new JPanel();
        gameComponent = new BoardComponent(board);
        statusTextManager = new StatusTextManager();
        pauseLabel = new JLabel();
        gameOverLabel = new JLabel();

        init();
        board.addListener(gameComponent);
        board.addListener(statusTextManager);
        setResizable(false);
        add(panel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void endGameOverView() {
        panel.remove(gameOverLabel);
    }

    public BoardComponent getGameComponent() {
        return gameComponent;
    }

    public void setPausedView() {
        panel.remove(statusTextManager.getPanel());
        panel.add(pauseLabel);
        packAndPaint();
    }

    public void setGameOnView() {
        panel.remove(pauseLabel);
        panel.add(statusTextManager.getPanel());
        packAndPaint();
    }

    public void setGameOverView() {
        panel.remove(statusTextManager.getPanel());
        panel.add(gameOverLabel);
        packAndPaint();
    }

    //----- Private methods -------
    private void init() {
        pauseLabel.setForeground(Color.GREEN);
        pauseLabel.setFont(new Font("Serif", Font.BOLD, Game.TEXT_FONT_SIZE));
        pauseLabel.setText("PAUSED");
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Serif", Font.BOLD, Game.TEXT_FONT_SIZE));
        gameOverLabel.setText("GAME OVER");
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.black);
        panel.add(gameComponent);
    }

    private void packAndPaint() {
        pack();
        repaint();
    }
}

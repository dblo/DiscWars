package Views;

import DiscWars.Game;

import javax.swing.*;
import java.awt.*;

// Made by ollol646
class StatusTextManager implements BoardListener {
    private final JPanel textPanel;
    private final JLabel lifes, score, level;

    //----- Public methods --------
    @Override
    public void gameChanged() {
        updateScore();
        updateLevel();
        updateLifes();
    }

    //----- Package methods -------
    StatusTextManager() {
        Font font = new Font("Serif", Font.BOLD, Game.TEXT_FONT_SIZE);

        lifes = new JLabel();
        lifes.setForeground(Color.GREEN);
        lifes.setFont(font);
        updateLifes();

        score = new JLabel();
        score.setForeground(Color.GREEN);
        score.setFont(font);
        updateScore();

        level = new JLabel();
        level.setForeground(Color.GREEN);
        level.setFont(font);
        updateLevel();

        //1/6 of gameWidth used as empty space between labels
        textPanel = new JPanel(new GridLayout(1, 3, Game.getSgameWidth() / 6, 0));
        textPanel.add(lifes);
        textPanel.add(score);
        textPanel.add(level);
        textPanel.setBackground(Color.BLACK);
    }

    JPanel getPanel() {
        return textPanel;
    }

    //----- Private methods -------
    private void updateLifes() {
        lifes.setText("Lifes: " + Game.getSlifes());
    }

    private void updateLevel() {
        level.setText("Level: " + Game.getSlevel());
    }

    private void updateScore() {
        score.setText("Score: " + Game.getSscore());
    }
}

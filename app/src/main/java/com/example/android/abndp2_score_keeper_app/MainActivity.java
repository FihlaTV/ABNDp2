package com.example.android.abndp2_score_keeper_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int setNum = 1;    // is 1-5 for match(individual) Olympic archery, since games have 5 sets.
    int scoreA = 0;    // target score for this arrow for archer A.
    int scoreB = 0;    // target score for this arrow for archer B.
    int scoreACumulative = 0;    // cumulative target score (for this set -- there are 5 sets max) for archer A.
    int scoreBCumulative = 0;    // cumulative target score (for this set -- there are 5 sets max) for archer B.
    int setScoreA = 0; // total set points for archer A; winner is 1st to 6, unless 5-5 tie.
    int setScoreB = 0; // total set points for archer B; winner is 1st to 6, unless 5-5 tie.
    int started = 0;   // 1 after player A shoots and the game has started.
    int gameState = 0;  // 0 if ongoing; 1 if 5-5 tie; 2 if an archer wins with 6 set points.
    int lastSide = 1;  // indicates last side to have shot.
    int arrow = 1;     // 1-6 for each arrow shot in the set.

    // Scoring is simple:
    // Shooters shoot 3 arrows per set, for 5 sets max, alternating turns per shot.
    // After the 3 arrows for each set, the archer with highest cumulative set score
    //      is awarded 2 set points for this set (and loser is awarded 0).
    // Or, if their cumulative score's tie for that set (after the 3 arrows),
    //      both archers are awarded 1 set point.
    // The game is complete when an archer reaches 6 set points.
    //    If both archers reach 5 set points (5-5 tie) it's a shoot-off and
    //    closest next shot to bullseye wins match (this app just says "5-5 tie").
    // It is possible and okay for an archer to have a larger cumulative target score and still tie the game.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Displays the given score for player A.
     */
    public void displayScoreA() {
        TextView out = (TextView) findViewById(R.id.scoreA);
        if (started == 0) {
            out.setText(String.valueOf("[0]"));
        } else if (scoreA == 0) {
            out.setText(String.valueOf(String.valueOf(scoreACumulative)));
        } else {
            if (scoreACumulative != 0) {
                out.setText(String.valueOf("[") + String.valueOf(scoreACumulative) +
                        " +" + String.valueOf(scoreA) + String.valueOf("]"));
            } else {
                out.setText(String.valueOf("[ +") + String.valueOf(scoreA) + String.valueOf("]"));
            }
        }
    }

    /**
     * Displays the given score for player B.
     */
    public void displayScoreB() {
        TextView out = (TextView) findViewById(R.id.scoreB);
        if (started == 0) {
            out.setText(String.valueOf("0"));
        } else if (scoreB == 0) {
            out.setText(String.valueOf(String.valueOf(scoreBCumulative)));
        } else {
            if (scoreBCumulative != 0) {
                out.setText(String.valueOf("[") + String.valueOf(scoreBCumulative) +
                        " +" + String.valueOf(scoreB) + String.valueOf("]"));
            } else {
                out.setText(String.valueOf("[ +") + String.valueOf(scoreB) + String.valueOf("]"));
            }
        }
    }

    /**
     * Displays the set and arrow # for player A.
     */
    public void displayArrowA() {
        String s = "Set " + setNum;
        if (arrow == 1) s += ", arrow 1";
        if (arrow == 3) s += ", arrow 2";
        if (arrow == 5) s += ", arrow 3";
        if (gameState == 1) s = "tie game";
        if (gameState == 2) s = "winner";
        if (started == 0) s = "Set 1, begin";

        TextView out = (TextView) findViewById(R.id.readoutA);
        out.setText(String.valueOf(s));
    }

    /**
     * Displays the set and arrow # for player B.
     */
    public void displayArrowB() {
        String s = "Set " + setNum;
        if (arrow == 2) s += ", arrow 1";
        if (arrow == 4) s += ", arrow 2";
        if (arrow == 6) s += ", arrow 3";
        if (gameState == 1) s = "tie game";
        if (gameState == 2) s = "winner";
        if (started == 0) s = "";

        TextView out = (TextView) findViewById(R.id.readoutB);
        out.setText(String.valueOf(s));
    }

    /**
     * Displays the set scores for both players.
     */
    public void displaySetScores() {
        TextView out = (TextView) findViewById(R.id.setScore);
        out.setText(String.valueOf(setScoreA) + " - " + String.valueOf(setScoreB));
    }

    /**
     * Check if game completed.
     */
    public boolean gameOver() {
        if (scoreACumulative > scoreBCumulative) setScoreA += 2;
        if (scoreBCumulative > scoreACumulative) setScoreB += 2;
        if (scoreACumulative == scoreBCumulative) {
            setScoreA += 1;
            setScoreB += 1;
        }
        scoreACumulative = scoreBCumulative = 0;
        displaySetScores();

        if (setScoreA == 5 && setScoreB == 5) {
            gameState = 1;
            displayArrowA();
            displayArrowB();
            return true;
        }
        if (setScoreA >= 6 && setScoreA > setScoreB) {
            gameState = 2;
            displayArrowA();
            return true;
        }
        if (setScoreB >= 6 && setScoreB > setScoreA) {
            gameState = 2;
            displayArrowB();
            return true;
        }
        return false;
    }

    /**
     * Last move was by player A.
     * Called by player-B buttons (right vertical LinearLayout).
     */
    public void checkLastMoveWasA() {
        if (lastSide == 1) {
            arrow++;
            scoreACumulative += scoreA;
            scoreA = 0;
            displayScoreA();
        }
    }

    /**
     * Last move was by player B.
     * Returns true if game over.
     * Called by player-A buttons (left vertical LinearLayout).
     */
    public boolean lastMoveWasB() {
        if (lastSide == 2) {
            arrow++;
            scoreBCumulative += scoreB;
            scoreB = 0;
            displayScoreB();
            if (arrow == 7) {
                arrow = 1;
                setNum++;
                if (gameOver()) return true; // check if game over.
            }
        }
        return false;
    }

    /**
     * Updates player A's score.
     */
    public void updateScoreA(int min, int max) {
        if (scoreA == max) {
            scoreA = 0;
        } else if (scoreA == min) {
            scoreA = max;
        } else {
            scoreA = min;
        }
    }

    /**
     * Updates player B's score.
     */
    public void updateScoreB(int min, int max) {
        if (scoreB == max) {
            scoreB = 0;
        } else if (scoreB == min) {
            scoreB = max;
        } else {
            scoreB = min;
        }
    }

    /**
     * Get player A's shot.
     */
    public void submitClickA(View v) {
        if (gameState != 0) return;      // abort if game isn't ongoing.
        if (started == 0) started = 1;   // game now underway.
        if (lastMoveWasB()) return;      // process valid move by archer B.
        lastSide = 1;                    // player A moved last.
        int shot = Integer.parseInt(v.getTag().toString()); // convert passed tag to int.
        updateScoreA(shot, shot + 1);
        displayArrowA();
        displayScoreA();
    }

    /**
     * Get player B's shot.
     */
    public void submitClickB(View v) {
        if (gameState != 0) return; // abort if game isn't ongoing.
        if (started == 0) return;   // player A must shoot first to start the game.
        checkLastMoveWasA();
        lastSide = 2;               // player B moved last.
        int shot = Integer.parseInt(v.getTag().toString()); // convert passed tag to int.
        updateScoreB(shot, shot + 1);
        displayArrowB();
        displayScoreB();
    }

    /**
     * Reset game.
     */
    public void reset(View view) {
        gameState = started = 0;
        lastSide = setNum = arrow = 1;
        scoreA = scoreB = 0;
        scoreACumulative = scoreBCumulative = 0;
        setScoreA = setScoreB = 0;

        displayArrowA();
        displayScoreA();
        displayArrowB();
        displayScoreB();
        displaySetScores();
    }
}

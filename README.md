# Android Basics Nanodegree project 2 

We had to build a single-screen app in this project to score a game, so I built an app to score individual match-play target archery as played at the
[Rio 2016 Olympics](https://www.rio2016.com/en/archery).

#### Scoring is simple:

* Shooters shoot 3 arrows per set, for 5 sets max, alternating turns per shot.
* After the 3 arrows for each set, the archer with highest cumulative set score
          is awarded 2 set points for this set (and loser is awarded 0).
* Or, if their cumulative set score's tie for that set (after the 3 arrows),
          both archers are awarded 1 set point.
* The game is complete when an archer reaches 6 set points.
      If both archers reach 5 set points (5-5 tie) it's a shoot-off and
        closest next shot to bullseye wins match (this app just says "5-5 tie").
* It is possible and okay for an archer to have a larger cumulative target score and still tie the game.

###### Table of contents
1. [Preview images](#preview-images)
2. [Coding notes](#coding-notes)
3. [Layout notes](#layout-notes)


# 
## Preview images

# 
#### Nexus 4 preview


![image](https://raw.githubusercontent.com/devted/ABNDp2/master/app/src/main/res/layout/project-notes/app_preview_archery_scoring_Nexus4.png)


# 
#### Nexus 5X preview
![image](https://raw.githubusercontent.com/devted/ABNDp2/master/app/src/main/res/layout/project-notes/app_preview_archery_scoring_Nexus5X.png)


# 
#### Nexus 10 tablet landscape preview
![image](https://raw.githubusercontent.com/devted/ABNDp2/master/app/src/main/res/layout/project-notes/app_preview_archery_scoring_Nexus10.png)


# 
## Coding notes

- When a scoring button is pressed, the **updateScoreA** or **updateScoreB** MainActivity methods run, and access the view's "tag" element to know which exact button was pressed.

- Code [is here](https://github.com/devted/ABNDp2/blob/master/app/src/main/java/com/example/android/abndp2_score_keeper_app/MainActivity.java).

- Built in Android Studio with Build --> Rebuild Project. 


# 
## Layout notes
 
- The root ViewGroup is a RelativeLayout parent so the bottom-center reset button and bullseye ImageView may be positioned precisely. Layout [is here](https://github.com/devted/ABNDp2/blob/master/app/src/main/res/layout/activity_main.xml).

- The topmost scorebar uses a nested LinearLayout-->RelativeLayout tree; the LinearLayout has a vertical orientation so a horizontal line can be added below the scorebar; the scorebar uses RelativeLayout view group so the horizontally-centered "# - #" set score may be precicely positioned beneath the scorebar.

- The score buttons are a nested tree of LinearLayouts with horizontal-->vertical nesting; this lets me precisely place two vertical LinearLayouts side-by-side for the scoring buttons and a vertical View "line".



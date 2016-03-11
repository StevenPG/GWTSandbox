Steven Gantz
3/10/2016
Project Link: http://acad.kutztown.edu/~sgant869/memory.html
JavaDoc Link: http://acad.kutztown.edu/~sgant869/doc/index.html

HOWTO:
1. Begin by selecting the total number of players.
   1a. Choose whether to enable cheats.
2. Enter each player's name and whether they are a human or AI
3. Begin the game!

GAMEPLAY:
If you are playing solo, select two cards and the game will
automagically check for a match. If there is a match, you will
earn a match point and the cards will stay within view. If you
do not get a match, the cards will flip back over.

There are general instructions on the right side of the board.

You may use cheats if you'd like, they are located in the top right corner.

Information on each player is available on the left sidebar.

Note:
  For some reason, GWT will not always run the Window.Location.reload()
  command when the game ends. I have not been able to figure out why this
  bug occurs, but the game normally reloads successfully when OK is pressed.

IMPLEMENTATION_DETAILS:
- The game is controlled through game state phases.
  - Phase 0: User uses mouse to click on the first card
  - Phase 1: User uses mouse to click on the second card
  - Phase 2: Do not allow user to select cards while processing
  - Phase 3: AI is making decisions, do not allow user to select cards

- All of the visual generation is done within the client class
  provided by GWT on project creation.

- Player control is done using a polymorphic class called MemoryPlayer. This
  is extended by two classes, HumanPlayer and ComputerPlayer. These classes
  have distinct methods that are called through the MemoryPlayer layer. All
  three of these classes implement the Player interface, which holds the
  contract that a player must always have a name available for retrieval.

- Universal data is saved within a pair of singleton classes that are fields
  of the MemoryGameDriver class. GameData contains relevant game data like
  current phase and cards selected. Appdata contains information such as
  the window size and the hardcoded locations of image files.

- GWTCollections contains the OpenJDK implementation of java.util.Collections.shuffle
  and a few dependency methods. This was created to combat the lack of shuffle
  available in the GWT framework.

PANEL_STRUCTURE:
- The game board and surrounding panels are independent of eachother and are situated
  within a DockLayoutPanel.
  - GamePanel
    - VerticalPanel
      - HorizontalPanel (x5)

  - InformationPanel
    - VerticalPanel
      - HorizontalPanel (x3)

  - ScorePanel
    - TabLayoutPanel
      - HorizontalPanel (x1)

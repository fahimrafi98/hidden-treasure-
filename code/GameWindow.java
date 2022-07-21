import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.lang.*;
import java.lang.Object;
import javax.swing.border.*;


public class GameWindow extends JFrame implements ActionListener, MouseListener
{
  private JPanel topPanel, bottomPanel;
  private JLabel instructionLabel,gridLabel;
  private JButton startButton;
  public GridSquare [][] gridSquares;
  private int rows,columns,player,treasurex,treasurey;
  private EmptyBorder margin;
  private boolean gameInPlay;
  private boolean played;

  public GameWindow(int rows, int columns)
  {
    this.rows = rows;
		this.columns = columns;
    int treasurex = 0;
    int treasurey = 0;
    this.setSize(600,600);
    boolean gameInPlay = false;
    //gameInPlay variable to help determine if a game is currently in play
    //grid squares shouldn't be able to be clicked on if no game is in play

    topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());

    bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(5,5));

    instructionLabel = new JLabel("Find the treasure! Click New Game to begin.");
    startButton = new JButton("New Game");
    startButton.addActionListener(this);

    topPanel.add(instructionLabel);
    topPanel.add(startButton);

    //creating the grid
    gridSquares = new GridSquare [rows][columns];
    for (int x=0; x<rows; x++)
		{
			for (int y=0; y <columns; y++)
			{
        gridSquares[x][y] = new GridSquare(x, y);
        gridSquares[x][y].setSize(30, 30);
        gridSquares[x][y].setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gridSquares[x][y].addMouseListener(this);
        bottomPanel.add(gridSquares[x][y]);
      }
    }

    //assembling the frame and adding panels
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(topPanel, BorderLayout.NORTH);
    getContentPane().add(bottomPanel, BorderLayout.CENTER);

    //setting the frame's behaviour and making it visible
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);

  }

  //actionEvent for "New Game" button
  public void actionPerformed(ActionEvent aevt)
  {
    Object selected = aevt.getSource();
    if(selected.equals(startButton))
    {
      startGame();
    }
  }

  //program to run when new game is started
  public void startGame()
  {
    gameInPlay=true;

    for (int x=0; x<rows; x++)
		{
			for (int y=0; y <columns; y++)
			{
        gridSquares[x][y].resetSquare();
      }
    }
    Random rand = new Random();
    int player=rand.nextInt(); //Random int, player%2 will give us Player 1 or 2
    setPlayer(player);
    hideTreasure();

  }

  //Randomly generate an x and y value to hide the treasure
  public void hideTreasure()
  {
    Random rand = new Random();
    treasurex=rand.nextInt(5);
    treasurey=rand.nextInt(5);
  }

  //set the top label to display whose turn it is
  public void setPlayer(int player)
  {
    player = player;
    if (player%2==0)
      { instructionLabel.setText("Player 1's Turn"); }
    else
      { instructionLabel.setText("Player 2's Turn"); }
  }

  //mouse listener
  public void mouseClicked(MouseEvent mevt)
  {
    if(gameInPlay) //squares should only be clickable if a game has started
    {
      Object selected = mevt.getSource(); //get object selected by mouse

      if (selected instanceof GridSquare) //only need to perform action if a gridsquare is clicked
      {
        GridSquare square = (GridSquare) selected;
        boolean played = square.checkPlayed(); //ensure this square hasn't already been clicked this game
        if(!played)
        {
          int x = square.getXcoord();
          int y = square.getYcoord();
          square.clickSquare(x,y,treasurex,treasurey,player);
          gameInPlay=!(square.gameOver());

          if(!gameInPlay) //change top label if game is won
            { if (player%2==0)
              { instructionLabel.setText("Game over! Player 1 found the treasure. Click 'New Game' to play again."); }
              else{ instructionLabel.setText("Game over! Player 2 found the treasure. Click 'New Game' to play again."); }
            }

          else
          {
            //Next player
            player++;
            setPlayer(player);
          }
        }
      }
    }
  }

  // need to keep these here for MouseListener
  public void mouseEntered(MouseEvent arg0){}
  public void mouseExited(MouseEvent arg0) {}
  public void mousePressed(MouseEvent arg0) {}
  public void mouseReleased(MouseEvent arg0) {}
}

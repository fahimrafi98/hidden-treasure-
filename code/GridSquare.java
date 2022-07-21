import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.border.EmptyBorder;
import java.lang.*;
import java.awt.Color;

public class GridSquare extends JPanel
{
  private int xcoord, ycoord, treasurex, treasurey, player;
  private JLabel value;
  private int steps;
  private boolean played, gameOver;

  public GridSquare(int xcoord, int ycoord)
	{
    //creating initial empty grid when program is Launched
		super();
		this.setSize(50,50);
    this.setBackground(Color.white);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
    value=new JLabel("");
    value.setAlignmentX(JLabel.CENTER);
    value.setFont(new Font("Arial",Font.PLAIN,50));
    this.add(value);
    boolean played=false;
    boolean gameOver=false;
	}


    public void clickSquare(int xcoord, int ycoord, int treasurex, int treasurey, int player)
    {
        xcoord=xcoord;
        ycoord=ycoord;
        treasurex=treasurex;
        treasurey=treasurey;
        player=player;

        if (xcoord==treasurex && ycoord==treasurey) //if player found the treasure
        {
          this.setBackground(Color.green);
          value=new JLabel("X");
          value.setFont(new Font("Arial",Font.PLAIN,50));
          value.setBorder(BorderFactory.createEmptyBorder(20,0,0,10));
          this.add(value);
          gameOver=true;
        }

        else //if treasure is still hidden
        {
          if(player%2==0)
          {this.setBackground(Color.orange);}
          else
          {this.setBackground(Color.pink);}

          //next line caluclates # of steps to treasure
          //absolute value so that we don't get negative steps
          steps=(Math.abs(treasurex-xcoord)+Math.abs(treasurey-ycoord));
          value=new JLabel(""+steps);
          value.setFont(new Font("Arial",Font.PLAIN,50));
          value.setBorder(BorderFactory.createEmptyBorder(20,0,0,10));
          this.add(value);
          this.setPlayed();
        }
    }

    //indicate that the treasure has been found and the game is over
    public boolean gameOver()
    {
      return gameOver;
    }

    //indicating a square has already been played (clicked)
    public void setPlayed()
    {
      played=true;
    }

    //telling GameWindow that a square has been already played
    public boolean checkPlayed()
    {
      if(played)
      {
        return true;
      }
      else
      {
        return false;
      }
    }

    //for when a game ends and a new one is started
    //to remove colours and steps/"X" label
    public void resetSquare()
    {
      this.setBackground(Color.white);
      value.setText("");
      played=false;
      gameOver=false;

    }

    //getter method to return x and y coordinates
    public int getXcoord()
    {
      return xcoord;
    }
    public int getYcoord()
    {
      return ycoord;
    }
}

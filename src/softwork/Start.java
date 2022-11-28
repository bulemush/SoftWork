package softwork;

import sixth.MineClearance;
import maines.newuser;
import Five.ChessMap;
import task.Startgame;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start {
    public static String word=null;
    public static void main(String[] args) {
        JFrame f=new JFrame("Game");
        Startgame startgame=new Startgame();
        //Tetris tetris=new Tetris();
        JDialog dialog =new JDialog(f,"Dialog");
        dialog.setSize(220,150);
        dialog.setLocation(350, 150);
        dialog.setLayout(new FlowLayout());
        Label label=new Label();
        dialog.add(label);
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        f.setBounds((width-800)/2, (height-670)/2, 800, 670);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        JPanel game=new GamePanel();
        game.setLayout(null);
        JButton choice01=new JButton("游戏一");
        choice01.setBounds(110, 500,110,60 );
        JButton choice02=new JButton("游戏二");
        choice02.setBounds(345, 500,110,60 );
        JButton choice03=new JButton("游戏三");
        choice03.setBounds(580, 500,110,60 );
        JButton getin= new JButton("登录");
        getin.setBounds(345, 250,110,60 );
        game.add(choice01);
        game.add(choice02);
        game.add(choice03);
        game.add(getin);
        f.add(game);
        getin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Getin getin1=new Getin();
                try {
                    getin1.getin();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        choice01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                    Startgame.work();
            }
        });

        choice02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ChessMap five=new ChessMap();
            }
        });
        choice03.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MineClearance mineClearance=new MineClearance();
            }
        });

    }
}

class GamePanel extends JPanel{
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Show.backimg.paintIcon(this, g, 0, 0);
    }
}
package softwork;
import Five.src.maines.newuser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static java.lang.Thread.sleep;

public class Getin extends JFrame {
    static  String id ;
    static  String passwords ;
    public static StringBuffer ID=new StringBuffer();
    public static StringBuffer password=new StringBuffer();
    public static void getin() throws InterruptedException {
        IDword iDword=new IDword();
        JFrame f=new JFrame();
        JDialog dialog=new JDialog(f,"succeed");
        dialog.setSize(220,150);
        dialog.setLocation(350, 150);
        dialog.setLayout(new FlowLayout());

        JTextField text1,text2;
        f.setTitle("登录");
        f.setLayout(new GridLayout(3, 1));
        f.setSize(250, 200);
        f.setLocation(550, 200);
        JPanel panel= new JPanel();
        JPanel panel1= new JPanel();
        JPanel panel2= new JPanel();
        JButton b1 =new JButton("登录");
        panel.add(b1);
        Label lable1 = new Label("ID：");
        Label lable2 = new Label("Password：");
        text1 =new JTextField(13);
        text2 =new JPasswordField(14);
        panel1.add(lable1);
        panel1.add(text1);
        panel2.add(lable2);
        panel2.add(text2);
        f.add(panel1);
        f.add(panel2);
        f.add(panel,BorderLayout.SOUTH);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            //窗体点击关闭时，要做的事
            @Override
            public void windowClosing(WindowEvent e) {
                //结束程序
                f.dispose();
            }
        });
        text1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                int keycode=e.getKeyCode();
                String s=KeyEvent.getKeyText(keycode);
                if (keycode>=49&keycode<=57){
                    ID.append(s);
                    System.out.println("输入内容："+s+",");
                    System.out.println(ID);
                    id = ID.toString();
                }
                else if (keycode>=65&keycode<=89){
                    ID.append(s);
                    System.out.println("输入内容："+s+",");
                    System.out.println(ID);
                    id = ID.toString();
                }
                else if (keycode==8) {
                    ID.deleteCharAt(ID.length()-1);
                    System.out.println("输入内容："+s+",");
                    System.out.println(ID);
                    id = ID.toString();
                }
            }
        });
        iDword.ID=ID.toString();

        text2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keycode=e.getKeyCode();
                String s=KeyEvent.getKeyText(keycode);
                if (keycode>=49&keycode<=57){
                    password.append(s);
                    System.out.println("输入内容："+s+",");
                    System.out.println(password);
                    passwords = password.toString();
                }
                else if (keycode>=65&keycode<=89){
                    password.append(s);
                    System.out.println("输入内容："+s+",");
                    System.out.println(password);
                    passwords = password.toString();
                }
                else if (keycode==8) {
                    password.deleteCharAt(password.length()-1);
                    System.out.println("输入内容："+s+",");
                    System.out.println(password);
                    passwords = password.toString();
                }
            }
        });
        iDword.password=password.toString();
        System.out.println(iDword.ID+","+iDword.password);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newuser nu = new newuser();
//                nu.newus(iDword.ID, iDword.password);
                nu.newus(id,passwords);

//                JButton btn3=new JButton("sure");
                    dialog.setModal(true);
                    Label label=new Label("标签");
                    label.setText("succeed!");
//                dialog.add(btn3);
                    dialog.add(label);
                    //修改标签内容
                    dialog.setVisible(true);
                    dialog.dispose();

//                btn3.addMouseListener(new MouseAdapter() {
//                    public void mouseClicked(MouseEvent e) {
//                        dialog.dispose();
//                    }
//                });
            }
        });
    }

}
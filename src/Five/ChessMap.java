package Five;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ChessMap extends JFrame {

	private ImageIcon map;				//���̱���λͼ
	private ImageIcon blackchess;		//����λͼ
	private ImageIcon whitechess;		//����λͼ
	private ChessPanel cp;				//����
	private JPanel east;
	private JPanel west;
	private static final int FINAL_WIDTH = 450;
	private static final int FINAL_HEIGHT = 500;
	//����Ϊ�����˵�
	private JMenuBar menubar;			
	private JMenu[] menu={new JMenu("��ʼ"),new JMenu("����"),new JMenu("����")};
	private JMenuItem[] menuitem1={new JMenuItem("���¿�ʼ"),new JMenuItem("����"),new JMenuItem("�˳�")};
	private JMenuItem[] menuitem2={new JMenuItem("����ѡ��"),new JMenuItem("�˻�����"),new JMenuItem("���˶���")};
	private JMenuItem[] menuitem3={new JMenuItem("����"),new JMenuItem("����")};
	private boolean haveai=true;		//�������»�����������£�true�������
	Mouseclicked mouseclicked=new Mouseclicked();
	MouseMoved mousemoved=new MouseMoved();
	Menuitemclicked menuclicked=new Menuitemclicked();
	
	//���캯��
	public ChessMap(){
		//�ı�ϵͳĬ������
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		setTitle("������ ");
		setSize(FINAL_WIDTH,FINAL_HEIGHT);
		setResizable(false);
		init();
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
				- FINAL_WIDTH / 2, Toolkit.getDefaultToolkit()
				.getScreenSize().height
				/ 2 - FINAL_HEIGHT / 2);
		addWindowListener(new  WindowAdapter() {
			//窗体点击关闭时，要做的事
			@Override
			public void windowClosing(WindowEvent e) {
				//结束程序
				dispose();
			}
		});
		cp.reset();	
		setVisible(true);
	}
	
	//��ʼ����Ĭ��ֵ
 	public void init() 
 	{
 		
		map=new ImageIcon(getClass().getResource("bg.jpg"));
		blackchess=new ImageIcon(getClass().getResource("blackchess.gif"));
		whitechess=new ImageIcon(getClass().getResource("whitechess.gif"));
		cp=new ChessPanel(map,blackchess,whitechess);
		menubar=new JMenuBar();
		menuitem1[0].setActionCommand("Restart");
		menuitem1[1].setActionCommand("Rollback");
		menuitem1[2].setActionCommand("Exit");
		menuitem2[0].setActionCommand("Forbid");
		menuitem2[1].setActionCommand("Robot");
		menuitem2[2].setActionCommand("Human");
		menuitem3[0].setActionCommand("Rule");
		menuitem3[1].setActionCommand("About");
		for(int i=0;i<3;i++)
			menu[0].add(menuitem1[i]);
		for(int i=0;i<3;i++)
			menu[1].add(menuitem2[i]);
		for(int i=0;i<2;i++)
			menu[2].add(menuitem3[i]);
		for(int i=0;i<3;i++)
			menubar.add(menu[i]);
		Container p = getContentPane();
		setJMenuBar(menubar);
		east = new JPanel();
		west = new JPanel();
		p.add(east, "East");
		p.add(west, "West");
		p.add(cp, "Center");
		cp.addMouseListener(mouseclicked);
		cp.addMouseMotionListener(mousemoved);
		menuitem1[0].addActionListener(menuclicked);
		menuitem1[1].addActionListener(menuclicked);
		menuitem1[2].addActionListener(menuclicked);
		menuitem2[0].addActionListener(menuclicked);
		menuitem2[1].addActionListener(menuclicked);
		menuitem2[2].addActionListener(menuclicked);
		menuitem3[0].addActionListener(menuclicked);
		menuitem3[1].addActionListener(menuclicked);
	
	}
	class Mouseclicked extends MouseAdapter		//�ж���������֪ͨ���̺͵���
	{
		public void mouseClicked(MouseEvent e)
		{
		  if(cp.win==false){
			    if(haveai){           //�͵��Բ���
    		              Point p1=new Point();
    		              p1=cp.getPoint(e.getX(),e.getY());
    		              int x=p1.x;
    		              int y=p1.y;
                          // �����λ���Ѿ���������
    		              System.out.println("x="+x+",y="+y);
                          if (cp.isChessOn[x][y] != 2)
                                     return;
                          // ���Ϊ����,���ǽ���
                          if( cp.able_flag && cp.bw == 0) {
             	                 int type = cp.getType(x,y,cp.bw);
             	                 String str = null;
             	                 switch(type){
             		             case 20: 
             			               str = "�ڳ�������!��ѡ������λ������!";
             			               break;
             		             case 21:
             			               str = "�����Ľ���!��ѡ������λ������!";
             			               break;
             		             case 22: 
             			               str = "����������!��ѡ������λ������!";
             			               break;
             		             default : break;
             	                 }
             	                 if(str != null) {
             		                    JOptionPane.showMessageDialog(null,str);
             		                     return;
             	                 }
     		              }
                          boolean flag=cp.haveWin(x, y, cp.bw);
                          cp.update( x, y );
                          cp.putVoice();  //��������
                          // ��һ����,���ʼ�����ñ߽�ֵ
                         if( cp.chess_num == 1){  
                      	 if(x-1>=0)
                     	          cp.x_min = x-1;
                         if(x-1<=15)
                     	          cp.x_max = x+1;
                         if(y-1>=0)
                     	          cp.y_min = y-1;
                         if(y-1<=15)
                     	          cp.y_max = y+1;
                  }
                 else 
                 	cp.resetMaxMin(x,y);
                 if (flag) {
                     cp.wined(1 - cp.bw);
                     return;
                 }
                 cp.putOne(cp.bw);
			}else{                                        //���˲���
				Point p1=new Point();
 		        p1=cp.getPoint(e.getX(),e.getY());
 		        int x=p1.x;
 		        int y=p1.y;
                 // �����λ���Ѿ���������
 		        System.out.println("x="+x+",y="+y);
                 if (cp.isChessOn[x][y] != 2)
                             return;
                 // ���Ϊ����,���ǽ���
                 if( cp.able_flag && cp.bw == 0) {
          	           int type = cp.getType(x,y,cp.bw);
          	           String str = null;
          	           switch(type){
          		       case 20: 
          			       str = "�ڳ�������!��ѡ������λ������!";
          			       break;
          		       case 21:
          			       str = "�����Ľ���!��ѡ������λ������!";
          			       break;
          		       case 22: 
          			       str = "����������!��ѡ������λ������!";
          			       break;
          		       default : break;
          	           }
          	           if(str != null) {
          		               JOptionPane.showMessageDialog(null,str);
          		               return;
          	           }
  		       }
                boolean flag=cp.haveWin(x, y, cp.bw);
                cp.update( x, y );
                cp.putVoice();  //��������
                cp.repaint();
              // ��һ����,���ʼ�����ñ߽�ֵ
              if( cp.chess_num == 1){  
              	if(x-1>=0)
                  	cp.x_min = x-1;
                  if(x-1<=15)
                  	cp.x_max = x+1;
                  if(y-1>=0)
                  	cp.y_min = y-1;
                  if(y-1<=15)
                  	cp.y_max = y+1;
              }
              else 
              	cp.resetMaxMin(x,y);
              if (flag) {
                  cp.wined(1 - cp.bw);
                  return;
              }
			}
    	} 
		}
	}
	class MouseMoved implements MouseMotionListener		//�����ã�������λ��
	{
		public void mouseMoved(MouseEvent e)
    	{
    		cp.showMousePos(e.getPoint());
    	}
    	public void mouseDragged(MouseEvent e)
    	{}
	}
	class Menuitemclicked implements ActionListener		//�˵���Ϣ����
	{
		public void actionPerformed(ActionEvent e) 
		{
      		JMenuItem target = (JMenuItem)e.getSource();
      		String actionCommand = target.getActionCommand();
      		if(actionCommand.equals("Restart")){ 		//�ؿ�һ��
        	   cp.reset();	
        	   if(cp.sbw==cp.WHITE_ONE)
        		   cp.update(7, 7); 
        	   //player=cp.BLACK_ONE;
      		}
      		if(actionCommand.equals("Rollback")){ 		//����
      			if(cp.win) {
        			JOptionPane.showMessageDialog(null,"����Ѿ�����,���ܻ���!�����¿�ʼ�µ����!");
        			return;
                }
        		// ��ǰ�ֵ��������,ȡ������  ����,ȡ��һ��
        		if(cp.chess_num >= 2 && cp.bw == cp.sbw){
        			cp.isChessOn[cp.pre[cp.chess_num-1][0]][cp.pre[cp.chess_num-1][1]] = 2;
        			cp.isChessOn[cp.pre[cp.chess_num-2][0]][cp.pre[cp.chess_num-2][1]] = 2;
        			cp.chess_num -= 2;
        			cp.repaint();
        		}
        		else if(cp.chess_num >= 1 && cp.bw == 1-cp.sbw){
        			cp.isChessOn[cp.pre[cp.chess_num-1][0]][cp.pre[cp.chess_num-1][1]] = 2;
        			cp.chess_num --;
       				cp.repaint();
       			}
      		}
      		else if(actionCommand.equals("Exit")){ 		//�˳�
        		System.exit(1);	
      		}
      		else if(actionCommand.equals("Forbid")){     //����ѡ��
        		Object[] options = { "�޽���", "�н���" };
        		int sel = JOptionPane.showOptionDialog(
          				null, "���ѡ��", "����ѡ��",
          				JOptionPane.DEFAULT_OPTION,
          				JOptionPane.QUESTION_MESSAGE, null,
          				options, options[0]);
          		if(sel==1){
                        cp.able_flag=true;
                        System.out.println("�н���");
          		}else{
          			    cp.able_flag=false;
                        System.out.println("�޽���");
          		}
          	}
      		else if(actionCommand.equals("Robot")){            //�˻�����
      			haveai=true;
      			Object[] options = { "��������", "��������" };
        		int sel = JOptionPane.showOptionDialog(
          				null, "���ѡ��", "����ѡ��",
          				JOptionPane.DEFAULT_OPTION,
          				JOptionPane.QUESTION_MESSAGE, null,
          				options, options[0]);
          		if(sel==1){       //��������
          			    cp.sbw=cp.WHITE_ONE;
          			    cp.update(7, 7);
          			    System.out.println("��������");
         			    
          		}else{             //������
          			    //player=cp.BLACK_ONE;
          			    cp.sbw=cp.BLACK_ONE;
          			    System.out.println("������");
          		}
      		}
          	else if(actionCommand.equals("Human")){ 		//���˲���
        		haveai=false;	
        		cp.setHumanhuman(true);
      		}else if(actionCommand.equals("Rule")){          //����
      			JOptionPane.showConfirmDialog(null,
      			"1���޽��֣�" +"\n"+
				"   �ڰ�˫���������ӣ���һ�������������γ����������(���������)���ӵ�һ��Ϊʤ��" +"\n"+
				"2���н��֣����߽��־��䣬���ֲ������ӣ�" +"\n"+
				"   �����޽��ֹ�������ʤ�����ǲ��ϲ���һЩ�������ƺ������е����ƣ���ƽ��ڰ�˫������ʽ��" +"\n"+
				"   ������Ժ���ĸ��ֽ������γɡ�" +"\n"+
				"   ������Ҫ��Ϊ���¼��ࣺ" +"\n"+
				"   (1)�ڳ������֣�������������������ͬ�����ӡ�" +"\n"+
				"   (2)���������֣��������ϵĻ�����" + "\n"+
				"   (3)�����Ľ��֣��������ϵ��ġ�" + "\n"+
				"   ��������Ժ�����Եģ�����û���κν��֡�" ,"����",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
      		}
      		else if(actionCommand.equals("About")){ 		//��Ȩ�����
        		JOptionPane.showConfirmDialog(null,"�Ŷӳ�Ա��\n" +
        				                                      "�½���   07061229\n" +
        				                                      "�ľ�       07061225\n" +
        				                                      "������   07061230\n" +
        				                                      "������   07061207\n","����",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);	
      		}
    	}

	}
  public static void Five() {
	    new ChessMap();	
  }
} 

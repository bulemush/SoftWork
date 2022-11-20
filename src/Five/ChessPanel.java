package Five;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class ChessPanel extends JPanel{
	private ImageIcon map;					//���̱���λͼ
  	private ImageIcon blackchess;			//����λͼ
  	private ImageIcon whitechess;			//����λͼ
  	public int isChessOn [][];				//���
    protected boolean win = false;          // �Ƿ��Ѿ��ֳ�ʤ��
    protected int win_bw;                   // ʤ����ɫ
    protected int deep = 3, weight = 7;    // ����������Լ����
    public int drawn_num = 110;           // ���岽��
    int chess_num = 0;                      // ��������Ŀ
    public int[][] pre = new int[drawn_num + 1][2];    // ��¼������x,y����   ��� (drawn_num + 1) ��
    public int sbw = 0;                          //�����ɫ��ɫ0����ɫ1
    public int bw = 0;                           // ��ǰӦ���µ���ɫ  0����ɫ(Ĭ��)�� 1����ɫ
  	  // �߽�ֵ,�����ٶ��Ż�
    protected int x_max = 15, x_min = 0;
    protected int y_max = 15, y_min = 0;
    protected boolean able_flag = true;       // �Ƿ�ѡ����ֱ�־ 0:�޽���  1:�н���(Ĭ��
  	private int h;							//���ӳ�
 	private int w;							//���ӿ�
 	private int insx;						//�������ӵ�λ��
 	private int insy;
 	private Point mousePoint;				//��굱ǰλ��
 	private int winer;						//��ʤ��
    private boolean humanhuman=false;       //�Ƿ������˶���
 	private int plast=0;					//���˼����ˣ�
 	public int BLACK_ONE;					//0�����
 	public int WHITE_ONE;					//1�����
 	public int NONE_ONE;					//2������
 	public int N;							//���̱߳�
 
 	//-------����
 	 String[] choics = { "put.wav", "win.wav","lost.wav" }; //�����ļ�������
 	 URL file1 = getClass().getResource(choics[0]); //���������ļ�
 	 URL file2 = getClass().getResource(choics[1]); //��ʤ�����ļ�
 	 URL file3 = getClass().getResource(choics[2]); //ʧ�������ļ�
 	 AudioClip soundPut = java.applet.Applet.newAudioClip(file1); //����������������
 	 AudioClip soundWin = java.applet.Applet.newAudioClip(file2); //��ʤ������������
 	 AudioClip soundLost = java.applet.Applet.newAudioClip(file3); //ʧ��������������
 	
 	public ChessPanel(){}
 	public ChessPanel(ImageIcon r_map,ImageIcon r_blackchess,ImageIcon r_whitechess) {
 		
 		N=15;
 		map=new ImageIcon();
 		blackchess=new ImageIcon();
 		whitechess=new ImageIcon();
  		map=r_map;
  		blackchess=r_blackchess;
  		whitechess=r_whitechess;
  		NONE_ONE=2;
  		BLACK_ONE=0;
  		WHITE_ONE=1;
  		winer=NONE_ONE;
   		isChessOn=new int[N][N];
    	h=blackchess.getIconHeight()*(N-1);
    	w=blackchess.getIconWidth()*(N-1);
    	insx=0;
    	insy=0;
    	mousePoint=new Point();
    	
    }
	
    public void reset(){							//�ؿ�һ��
  		winer=NONE_ONE;
  		for(int i=0;i<N;i++)
  			for(int j=0;j<N;j++){
  				isChessOn[i][j]=NONE_ONE;
  			}
  		chess_num = 0;  
  		win = false; 
  		win_bw=2;
  		bw = 0;
  		x_max = 15; x_min = 0;
  	    y_max = 15;y_min = 0;
  		repaint();
    }
    public void showMousePos(Point p){				//�����ã���ʾ���λ��
  	    int cw;
  	    cw=h/N;
  	    mousePoint.x=p.x/cw;
  	    mousePoint.y=p.y/cw;
  	    repaint();
    }
    public Point getPoint(int x,int y){
    	int cw;
  	    insx=x;
  	    insy=y;
  	    cw=h/N;
  	  Point r=new Point(x/cw,y/cw);
  	  return r;
    }
  public void gameOver(int r_winer){			//��Ϸʤ���ѷ�
  	winer=r_winer;
  }
  public void paint(Graphics g){				//���岼��
    super.paint(g);
    paintChessMap(g); 
    paintChess(g);
    if(winer==BLACK_ONE){
    	g.drawString(new String("��Ϸ�����������ʤ��"),500,200);
    	
    }
    else if(winer==WHITE_ONE){
    	g.drawString(new String("��Ϸ�����������ʤ��"),500,200);
    }
  }
  private void paintChessMap(Graphics g){		//������
  	map.paintIcon(this,g,10,10);
  	int j;
    g.setColor(Color.BLACK);
    for(j=0;j<N;j++){							//����
    	g.drawLine(h/N/2,h/N*j+h/N/2,w-w/N+(N%2)*(h/N/2),h/N*j+h/N/2);
    	g.drawLine(w/N*j+h/N/2,h/N/2,w/N*j+h/N/2,h-h/N+(N%2)*(h/N/2));
    }
    g.fillRect(w/N*7+h/N/2-3,h/N*7+h/N/2-3,6,6);//��5���ڷ���
    g.fillRect(w/N*3+h/N/2-3,h/N*3+h/N/2-3,6,6);
    g.fillRect(w/N*11+h/N/2-3,h/N*3+h/N/2-3,6,6);
    g.fillRect(w/N*3+h/N/2-3,h/N*11+h/N/2-3,6,6);
    g.fillRect(w/N*11+h/N/2-3,h/N*11+h/N/2-3,6,6);
  }
  private void paintChess(Graphics g){			//������
  		int i,j;
  		for(i=0;i<N;i++)
  			for(j=0;j<N;j++){
  				if(isChessOn[i][j]==BLACK_ONE){
  					blackchess.paintIcon(this,g,w/N*i,h/N*j);
  				}
  				else if(isChessOn[i][j]==WHITE_ONE){
  					whitechess.paintIcon(this,g,w/N*i,h/N*j);
  				}	
  			}
  }
  //-------------------------------������������-------------------------------------------------
  
  //��������
  public void putVoice(){
		soundPut.play();     
  }
  //��ʤ����
  public void winVoice(){
	   soundWin.play();
  }
  //ʧ������
  public void lostVoice(){
	  soundLost.play();
  }
  
   //----------------------��������-------------------------------//
  public void  putOne(int bwf ) {  //bwf ��ɫ 0:��ɫ 1����ɫ
      int x, y, mx = -100000000;
      x = y = -1;
      // �������������
      int[][] bests = getBests( bwf );
      for (int k = 0; k < bests.length; k++) {
          int i = bests[k][0];
          int j = bests[k][1];
          // �г�5,��ֱ������,���˳�ѭ��..û��,��˼���Է����
          if (getType(i, j, bwf) == 1) {
              x = i;
              y = j;
              break;
          }
          if (getType(i, j,1 - bwf) == 1) {
              x = i;
              y = j;
              break;
          }
          // Ԥ�浱ǰ�߽�ֵ
          int temp1=x_min,temp2=x_max,temp3=y_min,temp4=y_max;
          // Ԥ�輺������,�����±߽�ֵ
          isChessOn[i][j] = bwf;
          resetMaxMin(i,j);
          // Ԥ��δ��
          int t = findMin(-100000000, 100000000, deep);
          // ��ԭԤ������λ���Լ��߽�ֵ
          isChessOn[i][j] = 2;
          x_min=temp1;
          x_max=temp2;
          y_min=temp3;
          y_max=temp4;
          // ���С��1000��50%�������ѡȡ
          //System.out.println("��       :" + i + "," + j + "  mx:" + mx + "  t:" + t);
          if (t - mx > 1000 || Math.abs(t - mx)<1000 && randomTest(3)) {
              x = i;
              y = j;
              mx = t;
              //System.out.println(i + "," + j + "  mx:" + mx + "  t:" + t);
          }
         
      }
      System.out.println("x="+x+",y="+y);
     // addChess(x,y,(bwf+1)%2,true);
     // repaint();
      int step=0;
		step++;
		System.out.println("step "+step+":-----------------------------------------------");
		for(int i=0;i<15;i++,System.out.print("\n"))
			for(int j=0;j<15;j++)
				{
					if(isChessOn[j][i]!=2)System.out.print(isChessOn[j][i]);
					else	System.out.print(isChessOn[j][i]);
				}	
  	// �ж��Ƿ��ѷ�ʤ��
   	boolean flag = haveWin(x, y, bwf);
       //��¼
      update( x, y );
      repaint();
      // ����߽�ֵ
      resetMaxMin(x,y);
     //  ʤ���ѷ�
      if (flag) 
          wined(bwf);
      if (!flag && chess_num >= drawn_num) {
          win = true;
          String str = drawn_num + "��û��ʤ��,�к���!";
          JOptionPane.showMessageDialog(null,str);
          return;
      }
         
  }
  
  //---------������ǰ����״̬����ֵ--------------------------------//
  //alpha ���Ƚڵ�õ��ĵ�ǰ��С���ֵ������alpha ��֦
  //beta  ���Ƚڵ�õ��ĵ�ǰ�����Сֵ������beta ��֦��
  //step  ��Ҫ�����Ĳ���
  //return ��ǰ������������ֵ
  protected int findMax(int alpha, int beta, int step) {
  	int max = alpha;
      if (step == 0) {
          return evaluate();
      }
      int[][] rt = getBests(1 - sbw);
      for (int i = 0; i < rt.length; i++) {
          int x = rt[i][0];
      	int y = rt[i][1];
      	if (getType(x, y, 1 - sbw) == 1)   //���Կ�ȡʤ
      		return 100 * ( getMark(1) + step*1000 );
          isChessOn[x][y] = 1 - sbw;
          // Ԥ�浱ǰ�߽�ֵ
          int temp1=x_min,temp2=x_max,temp3=y_min,temp4=y_max;
          resetMaxMin(x,y);
          int t = findMin(max, beta, step - 1);
          isChessOn[x][y] = 2;
          // ��ԭԤ��߽�ֵ
          x_min=temp1;
          x_max=temp2;
          y_min=temp3;
          y_max=temp4;
          if (t > max)
          	max = t;
          //beta ��֦
          if (max >= beta) 
              return max;
      }
      return max;
  }
  

   //-----------------------������ǰ����״̬��Сֵ---------------------------------//
   //alpha ���Ƚڵ�õ��ĵ�ǰ��С���ֵ������alpha ��֦
  //beta  ���Ƚڵ�õ��ĵ�ǰ�����Сֵ������beta ��֦
  //step  ��Ҫ�����Ĳ���
 //return ��ǰ����������Сֵ��
  protected int findMin(int alpha, int beta, int step) {
  	int min = beta;
      if (step == 0) {
          return evaluate();
      }
      int[][] rt = getBests(sbw);
      for (int i = 0; i < rt.length; i++) {
          int x = rt[i][0];
          int y = rt[i][1];
          int type = getType(x, y, sbw);
          if (type == 1)     					  			//��ҳ�5
              return -100 * ( getMark(1) + step*1000 );
          // Ԥ�浱ǰ�߽�ֵ
          int temp1=x_min,temp2=x_max,temp3=y_min,temp4=y_max;
          isChessOn[x][y] = sbw;
          resetMaxMin(x,y);
          int t = findMax( alpha, min, step - 1 );
          isChessOn[x][y] = 2;
          // ��ԭԤ��߽�ֵ
          x_min=temp1;
          x_max=temp2;
          y_min=temp3;
          y_max=temp4;
          if (t < min)
          	min = t;
          //alpha ��֦
          if (min <= alpha) {
              return min;
          }
      }
      return min;
  }


   //-----------------ѡȡ�ֲ����ŵļ������ӵ���Ϊ��һ����չ�Ľڵ�---------//
   //bwf ��ɫ 0������ 1������
   //return ѡ�����Ľڵ�����
  private int[][] getBests(int bwf) {

      int i_min=(x_min==0 ? x_min:x_min-1);
      int j_min=(y_min==0 ? y_min:y_min-1);
      int i_max=(x_max==15 ? x_max:x_max+1);
      int j_max=(y_max==15 ? y_max:y_max+1);
      int n = 0;
      int type_1,type_2;
      int[][] rt = new int[(i_max-i_min) * (j_max-j_min)][3];
      for ( int i = i_min; i < i_max; i++) 
      	for (int j = j_min; j < j_max; j++)
      		if (isChessOn[i][j] == 2) {
                  type_1 = getType(i, j, bwf);
                  type_2 = getType(i, j, 1 - bwf);
                  if(able_flag && bwf==0 && (type_1 == 20 || type_1 == 21 || type_1 == 22)) // ������λ��,����¼
                  	continue;
                  rt[n][0] = i;
                  rt[n][1] = j;
                  rt[n][2] = getMark(type_1) + getMark(type_2);
                  n++;
      }
      // �Զ�ά��������
      Arrays.sort(rt, new ArrComparator());
      int size = weight > n? n:weight;
      int[][] bests = new int[size][3];
      System.arraycopy(rt, 0, bests, 0, size);
      return bests;
  }

   //----------------------------����ָ����λ�ϵ�����-------------------//
   // x,y �����߻�׼һ�㡣
   //ex,ey ָ�����򲽽�������
   // k ������ɫ��0����ɫ��1����ɫ
   // �÷����ϵ�������Ŀ �Լ� ���
  private int[] count(int x, int y, int ex, int ey, int bwf) {
  	// �÷���û����,����0
      if( !makesense(x, y, ex, ey, bwf))
          return new int[] {0, 1};
      
      // ������ �Լ� ���������Ӹ���
  	int rt_1 = 1,rt_2 = 1;
  	// �����Ӹ���
  	int rt = 1;
  	// ������ �Լ� ���������ӵĻ��
      int ok_1 = 0,ok_2 =0;
      // �ܻ��
      int ok = 0;
      // �����м����޿ո�
      boolean flag_mid1 =false,flag_mid2 = false;
      // �����м�ո��λ��
      int flag_i1 = 1,flag_i2 = 1;
      
      if (isChessOn[x][y] != 2) {
          throw new IllegalArgumentException("position x,y must be empty!..");
      }
      int i;
      // ������������
      for (i = 1; x + i * ex < 15 && x + i * ex >= 0 && y + i * ey < 15 && y + i * ey >= 0; i++) {
          if (isChessOn[x + i * ex][y + i * ey] == bwf)
              rt_1++;
      	// λ��Ϊ��,���пձ�־Ϊfalse,���Ϊ�пղ���������  ����,break
          else if(isChessOn[x + i * ex][y + i * ey] == 2) {
          		if(!flag_mid1) {
          			flag_mid1 = true;
          			flag_i1 = i;
          		}
          		else 
          			break;
          	}
          // λ��Ϊ�Է�����
          else    
          	break;
      }
      // ������������,,
      // ���һ��λ�ò������߽�
      if (x + i * ex < 15 && x + i * ex >= 0 && y + i * ey < 15 && y + i * ey >= 0) {
      	// ���һ��λ��Ϊ��λ +1��
      	if( isChessOn[x + i * ex][y + i * ey] == 2) {
      		ok_1++;
      		// ������β����⵽�����Ŀո���˳�����,�������п�
              if(rt_1 == flag_i1)
      			flag_mid1 = false;
              // ���пյ�λ����4���� �� ������>=4,����һ�ߵ�4�ǻ�
              if(flag_mid1 && rt_1 > 3 && flag_i1 < 4) {
              	ok_1--;
              }
      	}
      	// ���һ��λ�ò��ǿո�,��������2������,��ǰһ���ǿո�,  �����п�,��Ϊ��ı�
      	else if( isChessOn[x + i * ex][y + i * ey] != bwf && i >= 2) 
          	if(isChessOn[x + (i-1) * ex][y + (i-1) * ey] == 2) {
          		ok_1++;
          		flag_mid1 = false;
          	}
      }
      // ���һ��λ���Ǳ߽�  ������2������,��ǰһ���ǿո�,  �����п�,��Ϊ��ı�
      else if(i >= 2 && isChessOn[x + (i-1) * ex][y + (i-1) * ey] == 2) {
      	ok_1++;
      	flag_mid1 = false;
      }
      
      // ������������        
      for (i = 1; x - i * ex >= 0 && x - i * ex < 15 && y - i * ey >= 0 && y - i * ey < 15; i++) {
          if (isChessOn[x - i * ex][y - i * ey] == bwf)
              rt_2++;
          else if(isChessOn[x - i * ex][y - i * ey] == 2) {
          		if(!flag_mid2) {
          			flag_mid2 = true;
          			flag_i2 = i;
          		}
          		else
          			break;
          	}
          else
              break;
      }
      // ���㷴������
      if (x - i * ex < 15 && x - i * ex >= 0 && y - i * ey < 15 && y - i * ey >= 0) {
      	if( isChessOn[x - i * ex][y - i * ey] == 2) {
      		ok_2++;
      		if(rt_2 == flag_i2)
      			flag_mid2 = false;
      	    if(flag_mid2 && rt_2 > 3 && flag_i2 < 4) {
              	ok_2--;
              }
      	}
      	else if( isChessOn[x - i * ex][y - i * ey] != bwf && i >= 2 ) 
      		if(isChessOn[x - (i-1) * ex][y - (i-1) * ey] == 2) {
      			ok_2++;
      			flag_mid2 = false;
      		}
      }
      else if(i >= 2 && isChessOn[x - (i-1) * ex][y - (i-1) * ey] == 2) {
      	ok_2++;
  		flag_mid2 = false;
      }
      
      //------------------������������
      // ���߶�û�п�,ֱ�Ӻϳ�
      if( !flag_mid1 && !flag_mid2 ) {
      	rt = rt_1 + rt_2 - 1;
      	ok = ok_1 + ok_2;
      	return new int[] {rt, ok};
      }
      // ���߶����п�
      else if( flag_mid1 && flag_mid2 ){
      	int temp = flag_i1 + flag_i2 - 1;
      	// �ж��м�Ĵ�������,��5����,ֱ�ӷ���;  Ϊ4,���ػ�4;  
      	if(temp >= 5)
      		return new int[] {temp, 2};
      	if(temp == 4) 
      		return new int[] {temp, 2};
      	// �ȿ���û��4,�ٿ���û��3,ʣ��ֻ������3
      	if(rt_1 + flag_i2 - 1 >= 4 || rt_2 + flag_i1 - 1 >= 4) 
      		return new int[] {4, 1};
      	if(rt_1+flag_i2-1 == 3 && ok_1 > 0 || rt_2+flag_i1-1 == 3 && ok_2 > 0)
      		return new int[] {3, 2};
      	return new int[] {3, 1};
      }
      // ��һ�����п�
      else {
      	// ������������5,ֱ�Ӻϳ�
      	if( rt_1 + rt_2 - 1 < 5 )
      		return new int[] {rt_1 + rt_2 - 1, ok_1 + ok_2};
      	// ����5,���ҳ�5,���һ�4,ʣ�µ�ֻ������4
      	else {
      		if(flag_mid1 && rt_2 + flag_i1 - 1 >= 5) 
      			return new int[] {rt_2 + flag_i1 - 1, ok_2 + 1};
      		if(flag_mid2 && rt_1 + flag_i2 - 1 >= 5) 
      			return new int[] {rt_1 + flag_i2 - 1, ok_1 + 1};
      		
      		if(flag_mid1 && (rt_2 + flag_i1 - 1 == 4 && ok_2 == 1 || flag_i1 == 4) )
      			return new int[] {4, 2};
      		if(flag_mid2 && (rt_1 + flag_i2 - 1 == 4 && ok_1 == 1 || flag_i2 == 4) )
      			return new int[] {4, 2};
      		
      		return new int[] {4, 1};
      	}
      }
  }

   //----------------------------�ж�ָ�����������Ƿ�������,�������ܵ��������Ƿ� >=5-------------------------------//
   // x,y �����Ļ�׼��
   // ex,ey ��������
   // k ��ɫ
   // true:������ false:û����
  private Boolean makesense(int x, int y, int ex, int ey, int bwf) {

      int rt = 1;
      for (int i = 1; x + i * ex < 15 && x + i * ex >= 0 && y + i * ey < 15 && y + i * ey >= 0 && rt < 5; i++)
          if (isChessOn[x + i * ex][y + i * ey] != 1 - bwf)
              rt++;
          else
              break;

      for (int i = 1; x - i * ex >= 0 && x - i * ex < 15 && y - i * ey >= 0 && y - i * ey < 15 && rt < 5; i++)
          if (isChessOn[x - i * ex][y - i * ey] != 1 - bwf)
              rt++;
          else
              break;
      return (rt >= 5);
  }

   //------------------------------------ �����б�-------------------------------------//
   // x,y ����λ��
   // bwf ��ɫ  0�����ӣ�1������
   // ��Ӧ�����ͣ� ���ʹ����Ӧ���£�
   //             1����5
   //             2���ɻ�4������˫��4��������4��3
   //             3����˫��3
   //             4������3��3
   //             5������4
   //             6������3
   //             7����˫��2
  //             8������3
   //            9������2��2
   //            10���ɻ�2
   //             11������2
   //             12: ����
   //             20: ��������
   //             21: ˫�Ľ���
   //            22: ˫��������

  protected int getType(int x, int y, int bwf) {
  	if (isChessOn[x][y] != 2)
          return -1;
  	int[][] types = new int[4][2];
  	types[0] = count(x, y, 0, 1, bwf);   // ��ֱ
      types[1] = count(x, y, 1, 0, bwf);   // ����
      types[2] = count(x, y, -1, 1, bwf);  // б��
      types[3] = count(x, y, 1, 1, bwf);   // б��
      // �������͵ķ������Ŀ
      int longfive = 0;
      int five_OR_more = 0;
      int four_died = 0, four_live = 0;
      int three_died = 0, three_live = 0;
      int two_died  = 0, two_live = 0;
      // �����������͵��б�
      for (int k = 0; k < 4; k++) {
      	if (types[k][0] > 5) {  
      		longfive++;              // ����
      		five_OR_more++;
      	}
      	else if (types[k][0] == 5)
      		five_OR_more++;          // ��5
          else if (types[k][0] == 4 && types[k][1] == 2)
          	four_live++;             // ��4
          else if (types[k][0] == 4 && types[k][1] != 2)
          	four_died++;             // ��4
          else if (types[k][0] == 3 && types[k][1] == 2)
          	three_live ++;           // ��3
          else if (types[k][0] == 3 && types[k][1] != 2)
          	three_died++;            // ��3
          else if (types[k][0] == 2 && types[k][1] == 2)
          	two_live++;              // ��2
          else if (types[k][0] == 2 && types[k][1] != 2)
          	two_died++;              // ��2
          else
              ;
      }
      // �����͵��б�
      if(bwf == 0 && able_flag) {  		// ������ѡ���н���
      	if (longfive != 0)        		// ��������
      		return 20;
      	if (four_live + four_died >=2)  // ˫4����
      		return 21;
      	if (three_live  >=2)        	// ˫��������
      		return 22;
      }
      if (five_OR_more != 0)
          return 1;   // ��5
      if (four_live != 0 || four_died >= 2 || four_died != 0 && three_live  != 0)
          return 2;   // �ɻ�4������˫��4��������4��3
      if (three_live  >= 2)
          return 3;   // ��˫��3
      if (three_died != 0 && three_live  != 0)
          return 4;   // ����3��3
      if (four_died != 0)
          return 5;   // ����4
      if (three_live  != 0)
          return 6;   // ����3
      if (two_live >= 2)
          return 7;   // ��˫��2
      if (three_died != 0)
          return 8;   // ����3
      if (two_live != 0 && two_died != 0)
          return 9;   // ����2��2
      if (two_live != 0)
          return 10;  // �ɻ�2
      if (two_died != 0)
          return 11;  // ����2
      return 12;
  }

   //--------------------------�Ե�ǰ������д��------------------------------------------------------------//

  protected int evaluate() {
  	int rt = 0, mt_c = 1, mt_m = 1;
  	if(bw == sbw)
  		mt_m = 2;
  	else
  		mt_c = 2;
  	int i_min=(x_min==0 ? x_min:x_min-1);
      int j_min=(y_min==0 ? y_min:y_min-1);
      int i_max=(x_max==15 ? x_max:x_max+1);
      int j_max=(y_max==15 ? y_max:y_max+1);
      for (int i = i_min; i < i_max; i++)
          for (int j = j_min; j < j_max; j++)
              if (isChessOn[i][j] == 2) {
              	// �����������
                  int type = getType(i, j, 1 - sbw );
                  if(type == 1)      // ����1,����2�Լ�����3,��Ȩ.  ��ֹ"4��˫��3"�ľִַ���"1��˫��"֮��Ĵ������
                  	rt += 30 * mt_c * getMark(type);
                  else if(type == 2)					
                  	rt += 10 * mt_c * getMark(type);
                  else if(type == 3)
                  	rt += 3 * mt_c * getMark(type);
                  else
                  	rt += mt_c * getMark(type);
                  // ����������
                  type = getType(i, j, sbw );
                  if(type == 1)
                  	rt -= 30 * mt_m * getMark(type);
                  else if(type == 2)					
                  	rt -= 10 * mt_m * getMark(type);
                  else if(type == 3)
                  	rt -= 3 * mt_m * getMark(type);
                  else
                  	rt -= mt_m * getMark(type);
              }
      return rt;
  }

   //--------------------------------�����,������Ϣ-----------------------------//
  void update(int x,int y) {
  	isChessOn[x][y] = bw;
      bw = 1 - bw;
      pre[chess_num][0] = x;
      pre[chess_num][1] = y;
      chess_num++;
  }
  
   //-------------------------------------- �����,����߽�ֵ------------------------------//
   // x ��ǰ����λ�õ�x����
   // y ��ǰ����λ�õ�y����

  public void resetMaxMin(int x,int y){
		if(x-1>=0)
      	x_min = (x_min<x-1 ? x_min:x-1);
      if(x+1<=15)
      	x_max = (x_max>x+1 ? x_max:x+1);
      if(y-1>=0)
      	y_min = (y_min<y-1 ? y_min:y-1);
      if(y+1<=15)
      	y_max = (y_max>y+1 ? y_max:y+1);
  
  }
  

   //------------------------------------------�Է�����ͬ�����ӵ㣬���ѡȡ-------------------//
   //   kt ������� ֵԽС����ѡȡ�ĸ���Խ��
   //  return �Ƿ�ѡ���λ��

  private boolean randomTest(int kt) {
      Random rm = new Random();
      return rm.nextInt() % kt == 0;
  }


   //------------------------------------- ��ͬ���Ͷ�Ӧ����---------------------------------
   // k ���ʹ���
   //return ��Ӧ����
  private int getMark(int k) {
      switch (k) {
      case 1:                   
          return 100000;
      case 2:                   
          return 30000;
      case 3:
          return 5000;
      case 4:
          return 1000;
      case 5:
          return 500;
      case 6:
          return 200;
      case 7:
          return 100;
      case 8:
          return 50;
      case 9:
          return 10;
      case 10:
          return 5;
      case 11:
          return 3;
      case 12:
       	  return 2;
      default:                     //��������
          return 0;
      }
  }

   //--------------------------------------- �ж��Ƿ��ѷֳ�ʤ��---------------------------------------------
   // x ���ӵ�x����    y ���ӵ�y����
   // bwf ��ɫ 0:��ɫ 1����ɫ
   // return true:�ֳ�ʤ�� false:δ�ֳ�ʤ��

  public boolean haveWin(int x, int y, int bwf) {
      boolean flag = false;
      if (count(x, y, 1, 0, bwf)[0] >= 5)
          flag = true;
      if (!flag && count(x, y, 0, 1, bwf)[0] >= 5)
          flag = true;
      if (!flag && count(x, y, 1, 0, bwf)[0] >= 5)
          flag = true;
      if (!flag && count(x, y, 1, -1, bwf)[0] >= 5)
          flag = true;
      if (!flag && count(x, y, 1, 1, bwf)[0] >= 5)
          flag = true;
      // ������,������д���,��������Ӯ..   flag = false;
      return flag;
  }

  public void wined(int bw) {
	  boolean hh=getHumanhuman();
	  if(!hh){           //�������˶���
  	       win = true;
           win_bw = bw;
           String str = (bw == sbw ? "��ϲ����Ӯ�ˣ�" : "����Ӯ��,�㻹Ҫ����Ŭ������");
           if(bw==sbw)
    	        winVoice();
           else
    	        lostVoice();
           JOptionPane.showMessageDialog(null,str);
	  }
	  else{             //���˶���
		  win = true;
          win_bw = bw;
          String str = (bw == BLACK_ONE ? "��ϲ�������ʤ��" : "��ϲ�������ʤ��");
   	      winVoice();
          JOptionPane.showMessageDialog(null,str);
	  }
  }
public void setHumanhuman(boolean humanhuman) {
	this.humanhuman = humanhuman;
}
public boolean getHumanhuman() {
	return humanhuman;
}
}
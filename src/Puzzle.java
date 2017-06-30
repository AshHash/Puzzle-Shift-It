import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Puzzle extends JFrame implements ActionListener{
	class MyButton extends JButton{
		int i, j,num;
		Color c[]={new Color(144,238,144),new Color(240,230,140)};
		public MyButton(int num,int i, int j){
			super(""+num);
			this.num=num;
			this.i=i;this.j=j;
			setMyBackground();
			if(i==(SIZE-1) && j==(SIZE-1)){
				setBackground(Color.white);
			}
		}

		public void setMyBackground(){
			if(getText().equals(""))
				super.setBackground(Color.white);
			else
				super.setBackground(c[(i+j)%2]);
		}

	}
	private MyButton btn[][];
	private final int SIZE=4;
	private int x,y;
	private Border r,l;
	
	public Puzzle(){
		super("Puzzle");
		JMenuBar m = new JMenuBar();
		JMenu menu = new JMenu("Tools");
		JMenuItem mi = new JMenuItem("Shuffle");
		menu.add(mi);
		m.add(menu);
		setJMenuBar(m);
		x=y=3;
		r=BorderFactory.createRaisedBevelBorder();
		l=BorderFactory.createLoweredSoftBevelBorder();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		mi.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){shuffle();}});
		buildGUI();
		setResizable(false);
		setVisible(true);
		setSize(500,500);
	}
	private void buildGUI(){
		JPanel p = new JPanel(); p.setLayout(new GridLayout(SIZE,SIZE));
		btn=new MyButton[SIZE][SIZE];
		int i,j,k=1;
		
		java.util.ArrayList<Integer> lst = new java.util.ArrayList<Integer>();
		for(k=1;k<SIZE*SIZE;k++)lst.add(k);
		
		Font btnF=new Font("Broadway",Font.PLAIN,30);
		for(i=0;i<SIZE;i++){
			for(j=0;j<SIZE;++j){
				try{
				k=(int)(Math.random()*lst.size());
				k=lst.remove(k);
				}catch(Exception e){k=16;}
				btn[i][j]=new MyButton(k,i,j);
				p.add(btn[i][j]);
				btn[i][j].addActionListener(this);
				btn[i][j].setFont(btnF);
				btn[i][j].setBorder(r);
			}
		}
		btn[SIZE-1][SIZE-1].setText("");
		btn[SIZE-1][SIZE-1].setBorder(l);
		add(p);
	}
	public void actionPerformed(ActionEvent ae){
		MyButton b = (MyButton)ae.getSource();
		int m = b.i; int n = b.j;
		int i,j,num=b.num;
		if(m==x&&n==y)return;
		if(m==x){
			if(n<y){	
				for(i=y-1;i>=n;--i){
					btn[m][i+1].setText(btn[m][i].getText());
				}
			}
			else{
				for(i=y+1;i<=n;++i){
					btn[m][i-1].setText(""+btn[m][i].getText());
				}
			}
			btn[m][n].setText("");
			btn[m][n].setBorder(l);
			btn[x][y].setBorder(r);
			
			y=n;
		}
		else if(n==y){
			if(m<x){	
				for(i=x-1;i>=m;--i){
					btn[i+1][n].setText(btn[i][n].getText());
				}
			}
			else{
				for(i=x+1;i<=m;++i){
					btn[i-1][n].setText(""+btn[i][n].getText());
				}
			}
			btn[m][n].setText("");
			btn[m][n].setBorder(l);
			btn[x][y].setBorder(r);
			x=m;
		}
		if(x==y&& y==SIZE-1)check();

		for(MyButton temp[]: btn) for(MyButton t : temp) t.setMyBackground();
	}
	private void check(){
		int k=1,count=0;
		for(int i=0;i<SIZE-1;i++)
			for(int j=0;j<SIZE-1;j++)
				if(Integer.parseInt(btn[i][j].getText())==k++)count++;
		if(count==15)JOptionPane.showMessageDialog(this,"Winner");
	}
	private void shuffle(){
		java.util.ArrayList<Integer> lst = new java.util.ArrayList<Integer>();
		int k;
		for(k=1;k<SIZE*SIZE;k++)lst.add(k);
		for(int i=0;i<SIZE;i++)
			for(int j=0;j<SIZE;++j){
				try{
				k=(int)(Math.random()*lst.size());
				k=lst.remove(k);
				}catch(Exception e){k=16;}
				btn[i][j].setText(""+k);
				btn[i][j].setBorder(r);
			}
		btn[SIZE-1][SIZE-1].setText("");
		btn[SIZE-1][SIZE-1].setBorder(l);
	}
	public static void main(String args[]){
		new Puzzle();
	}
}
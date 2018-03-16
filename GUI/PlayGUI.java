package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MAP.Map;

/*
 * 推箱子游戏界面
 */

@SuppressWarnings("serial")
public class PlayGUI extends JPanel  implements KeyListener
{
	int level;
	//temp_map为临时此位置     map为原地图上此位置
	int temp_map[][],map[][];
	int manx,many; //保存人的当前坐标     manx即为y坐标    many为x坐标
	public final static int MAXLEVEL = 5;
	Stack<Integer> step;  
	//保存每一步操作的方向
	/* LEFT = 1(推了箱子 = 1;没推箱子 = 11)
	 * RIGHT = 3(推了箱子 = 3;没推箱子 = 31)
	 * UP = 2(推了箱子 = 2;没推箱子 = 21)
	 * DOWN = 4(推了箱子 = 4;没推箱子 = 41)
	 * 
	 */
	
	public PlayGUI()
	{
		level = 1;
		step = new Stack<Integer>();
		copydata(Map.data1);
		init(level);
		addKeyListener(this);
		//获取焦点
		requestFocus();
	}
	
	public void init(int level)
	{
		this.level = level;
		switch(level)
		{
			case 1:
				copydata(Map.data1);
				break;
			case 2:
				copydata(Map.data2);
				break;
			case 3:
				copydata(Map.data3);
				break;
			case 4:
				copydata(Map.data4);
				break;
			case 5:
				copydata(Map.data5);
				break;
		}
		step.clear(); //初始化
		repaint();   //调用update->调用paint
	}
	
	//拷贝数据
	void copydata(int data[][])
	{
		map = data; 
		temp_map = new int [20][20];
		manx = many = 0;
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				temp_map[i][j] = data[i][j];
				if(data[i][j]==5)
				{
					manx = i;
					many = j;
				}
			}
		}
	}
	
	public  void Sel(String opr)
	{
		if(opr.equals("First"))
		{
			level = 1;
		}
		else if (opr.equals("Prev"))
		{
			if(level > 1)
			{
				level --;
			}
			else 
			{
				level = 1;
			}
		}
		else if (opr.equals("Next"))
		{
			if(level >= MAXLEVEL)
			{
				level = MAXLEVEL;
			}
			else
			{
				level ++;
			}
		}
		else if (opr.equals("Last"))
		{
			level = MAXLEVEL;
		}
		init(level);
	}
	
	@Override
	public void paint(Graphics g)
	{
		int left,top;
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)
			{
				left = j*30;
				top = i*30;
				/*  
				 * 1.显示的图片
				 * 2.图片左上角的X坐标
				 * 3.图片左上角的Y坐标
				 * 4.图片的宽度
				 * 5.图片的高度
				 * 6.要显示的控件
				 */
				g.drawImage(Map.pic[temp_map[i][j]],left,top,30,30,this);
			}
		//显示当前关
		g.setColor(new Color(255,255,0));
		g.setFont(new Font("楷体",Font.BOLD,30));
		g.drawString("第",260,40);
		g.drawString(String.valueOf(level),300,40);
		g.drawString("关",320,40);
	}

	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT://按左方向键
				goLeft();
				break;
			case KeyEvent.VK_RIGHT://按右方向键
				goRight();
				break;
			case KeyEvent.VK_UP://按上方向键
				goUp();
				break;
			case KeyEvent.VK_DOWN://按下方向键
				goDown();
				break;
		}
		repaint();
		if(isWin())
		{
			if(level==MAXLEVEL)
			{
				JOptionPane.showMessageDialog(null, "！！！祝贺您通过最后一关！！！");
				return;
			}
			else
			{
				int choice=0;
				choice=JOptionPane.showConfirmDialog(null,"恭喜您通过第"+level+"关!\n是否要进入下一关？","过关",JOptionPane.YES_NO_OPTION);
				if(choice==0)
				{
					Sel("Next"); //进入下一关
				}
				if(choice==1);
				{
					return;
				}
			}
		}
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public boolean  isWin()
	{
		boolean bok = true;
		for(int i=0;i<20;i++)
			 for(int j=0;j<20;j++)
			{
				if((map[i][j]==4 || map[i][j]==9) && temp_map[i][j] != 9)
				{
					bok = false;
				}
			}
		return bok;
	}
	
	//判断人原来的地方是草地或放箱子的地方
	public void manoldPos() 
	{
		if(map[manx][many]==4 || map[manx][many]==9)
		{
			temp_map[manx][many]=4;
		}
		else
		{
			temp_map[manx][many]=2;
		}
	}
	
	public void goLeft()
	{
		//左边有箱子
		if(temp_map[manx][many-1]==3 || temp_map[manx][many-1]==9)
		{
			//左边第二个位置没有箱子
			if(temp_map[manx][many-2]==2)
			{
				temp_map[manx][many-2] = 3;
				temp_map[manx][many-1] = 6;
				manoldPos();
				many--;
				step.push(1);  //左走
			}
			else if(temp_map[manx][many-2]==4)
			{
				temp_map[manx][many-2] = 9;
				temp_map[manx][many-1] = 6;
				manoldPos();
				many--;
				step.push(1);  //左走
			}
		}
		else  if(temp_map[manx][many-1]==2 || temp_map[manx][many-1]==4)//左边是草地或者目标位置
		{
			temp_map[manx][many-1] = 6;
			manoldPos();
			many--;
			step.push(11);  //左走
		}
	}
	
	public void goRight()
	{
		//右边有箱子
		if(temp_map[manx][many+1]==3 || temp_map[manx][many+1]==9)
		{
			//右边第二个位置没有箱子
			if(temp_map[manx][many+2]==2)
			{
				temp_map[manx][many+2] = 3;
				temp_map[manx][many+1] = 7;
				manoldPos();
				many++;
				step.push(3);  //右走
			}
			else if(temp_map[manx][many+2]==4)
			{
				temp_map[manx][many+2] = 9;
				temp_map[manx][many+1] = 7;
				manoldPos();
				many++;
				step.push(3);  //右走
			}
		}
		else  if(temp_map[manx][many+1]==2 || temp_map[manx][many+1]==4)//右边是草地
		{
			temp_map[manx][many+1] = 7;
			manoldPos();
			many++;
			step.push(31);  //右走
		}
	}
	
	public void goUp()
	{
		//上边有箱子
		if(temp_map[manx-1][many]==3 || temp_map[manx-1][many]==9)
		{
			//上边第二个位置没有箱子
			if(temp_map[manx-2][many]==2)
			{
				temp_map[manx-2][many] = 3;
				temp_map[manx-1][many] = 8;
				manoldPos();
				manx--;
				step.push(2);  //上走
			}
			else if(temp_map[manx-2][many]==4)
			{
				temp_map[manx-2][many] = 9;
				temp_map[manx-1][many] = 8;
				manoldPos();
				manx--;
				step.push(2);  //上走
			}
		}
		else  if(temp_map[manx-1][many]==2 || temp_map[manx-1][many]==4)//上边是草地
		{
			temp_map[manx-1][many] = 8;
			manoldPos();
			manx--;
			step.push(21);  //上走
		}
	}
	
	public void goDown()
	{
		//上边有箱子
		if(temp_map[manx+1][many]==3 || temp_map[manx+1][many]==9)
		{
			//上边第二个位置没有箱子
			if(temp_map[manx+2][many]==2)
			{
				temp_map[manx+2][many] = 3;
				temp_map[manx+1][many] = 5;
				manoldPos();
				manx++;
				step.push(4);  //下走
			}
			else if(temp_map[manx+2][many]==4)
			{
				temp_map[manx+2][many] = 9;
				temp_map[manx+1][many] = 5;
				manoldPos();
				manx++;
				step.push(4);  //下走
			}
		}
		else  if(temp_map[manx+1][many]==2 || temp_map[manx+1][many]==4)//上边是草地
		{
			temp_map[manx+1][many] = 5;
			manoldPos();
			manx++;
			step.push(41);  //下走
		}
	}
	
	//往左悔一步
	public void backLeft(int direction)
	{
		//人的后面能否走
		if(temp_map[manx][many+1]==2 || temp_map[manx][many+1]==4)
		{
			//人的前面是否有箱子
			if(temp_map[manx][many-1]==3 || temp_map[manx][many-1]==9)
			{
				temp_map[manx][many+1] = 6;
				if(direction==11)  //没推箱子
				{
					if(map[manx][many]==4)
					{
						temp_map[manx][many]=4;
					}
					else
					{
						temp_map[manx][many]=2;
					}
				}
				else  //推了箱子
				{
					if(map[manx][many]==4 || map[manx][many]==9)
					{
						temp_map[manx][many]=9;
					}
					else
					{
						temp_map[manx][many]=3;
					}
					if(map[manx][many-1]==4 || map[manx][many-1]==9)
					{
						temp_map[manx][many-1] = 4;
					}
					else
					{
						temp_map[manx][many-1] = 2;
					}
				}
				many++;
			}
			else
			{
				temp_map[manx][many+1] = 6;
				if(map[manx][many]==4)
				{
					temp_map[manx][many] = 4;
				}
				else
				{
					temp_map[manx][many] = 2;
				}
				many++;
			}
		}			
	}
	
	//往右悔一步
	public void backRight(int direction)
	{
		//人的后面能否走
		if(temp_map[manx][many-1]==2 || temp_map[manx][many-1]==4)
		{
			//人的前面是否有箱子
			if(temp_map[manx][many+1]==3 || temp_map[manx][many+1]==9)
			{
				temp_map[manx][many-1] = 7;
				if(direction==31)  //没推箱子
				{
						if(map[manx][many]==4)
					{
						temp_map[manx][many]=4;
					}
					else
					{
						temp_map[manx][many]=2;
					}
				}
				else  //推了箱子
				{
					if(map[manx][many]==4 || map[manx][many]==9)
					{
						temp_map[manx][many]=9;
					}
					else
					{
						temp_map[manx][many]=3;
					}
					if(map[manx][many+1]==4 || map[manx][many+1]==9)
					{
						temp_map[manx][many+1] = 4;
					}
					else
					{
						temp_map[manx][many+1] = 2;
					}
				}
				many--;
			}
			else
			{				
				temp_map[manx][many-1] = 7;
				if(map[manx][many]==4)
				{
					temp_map[manx][many] = 4;
				}
				else
				{
					temp_map[manx][many] = 2;
				}
				many--;
			}
		}			
	}
	
	//往上悔一步
	public void backUp(int direction)
	{
		//人的后面能否走
		if(temp_map[manx+1][many]==2 || temp_map[manx+1][many]==4)
		{
			//人的前面是否有箱子
			if(temp_map[manx-1][many]==3 || temp_map[manx-1][many]==9)
			{
				temp_map[manx+1][many] = 8;
				if(direction==21)  //没推箱子
				{
					if(map[manx][many]==4)
					{
						temp_map[manx][many]=4;
					}
					else
					{
						temp_map[manx][many]=2;
					}
				}
				else  //推了箱子
				{
					if(map[manx][many]==4 || map[manx][many]==9)
					{
						temp_map[manx][many]=9;
					}
					else
					{
						temp_map[manx][many]=3;
					}
					if(map[manx-1][many]==4 || map[manx-1][many]==9)
					{
						temp_map[manx-1][many] = 4;
					}
					else
					{
						temp_map[manx-1][many] = 2;
					}
				}
				manx++;
			}
			else
			{
				temp_map[manx+1][many] = 8;
				if(map[manx][many]==4)
				{
					temp_map[manx][many] = 4;
				}
				else
				{
					temp_map[manx][many] = 2;
				}
				manx++;
			}
		}			
	}
	
	//往下悔一步
	public void backDown(int direction)
	{
		//人的后面能否走
		if(temp_map[manx-1][many]==2 || temp_map[manx-1][many]==4)
		{
			//人的前面是否有箱子
			if(temp_map[manx+1][many]==3 || temp_map[manx+1][many]==9)
			{
				temp_map[manx-1][many] = 5;
				if(direction==41)  //没推箱子
				{
					if(map[manx][many]==4)
					{
						temp_map[manx][many]=4;
					}
					else
					{
						temp_map[manx][many]=2;
					}
				}
				else  //推了箱子
				{
					if(map[manx][many]==4 || map[manx][many]==9)
					{
						temp_map[manx][many]=9;
					}
					else
					{
						temp_map[manx][many]=3;
					}
					if(map[manx+1][many]==4 || map[manx+1][many]==9)
					{
						temp_map[manx+1][many] = 4;
					}
					else
					{
						temp_map[manx+1][many] = 2;
					}
				}
				manx--;
			}
			else
			{
				temp_map[manx-1][many] = 5;
				if(map[manx][many]==4)
				{
					temp_map[manx][many] = 4;
				}
				else
				{
					temp_map[manx][many] = 2;
				}
				manx--;
			}
		}			
	}
	
	public void back()  //悔一步
	{
		if(step.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "您还没有进行移动，不能悔一步!","提示", JOptionPane.WARNING_MESSAGE);
			requestFocus();
		}
		int st = step.pop();
		switch(st)
		{
		    case 1://左
			case 11: 
				backLeft(st);
				break;
			case 3: //右
			case 31:
				backRight(st);
				break;
			case 2:  
			case 21: //上
				backUp(st);
				break;
			case 4:  //下
			case 41:
				backDown(st);
				break; 
		}
		repaint();
	}
	
}

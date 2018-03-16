package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Music.Sound;

/*
 * 推箱子菜单界面
 */

@SuppressWarnings("serial")
public class MenuGUI  extends JFrame  implements ActionListener,ItemListener
{
	JLabel lblTitle;    
	//按钮定义
	public static JButton btnReset;
	public static JButton btnBack;
	public static JButton btnFirst;
	public static JButton btnPrev;
	public static JButton btnNext;
	public static JButton btnLast;
	public static JButton btnSelect;
	public static JButton btnMusic;
	public static JButton btnHelp; 
	public static JButton btnAbout; 
	@SuppressWarnings("rawtypes")    
	public static JComboBox cbMusic;
	JPanel eastPanel;
	PlayGUI playgui;
	private String[] musicFile = {"nor.mid","popo.mid","guang.mid","qin.mid"};
	private String[] smusic = {"默认","泡泡堂","灌篮高手","琴箫合奏"};
	Sound sound;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MenuGUI()
	{
		//窗口图标
		Image icon = new ImageIcon("pic/3.gif").getImage();
		setIconImage(icon);
		//窗口标题
		setTitle("推箱子2017 v1.0");
		//容器初始化
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		lblTitle = new JLabel("推箱子游戏 v1.0",JLabel.CENTER);
		lblTitle.setFont(new Font("楷体",Font.BOLD,36));
		lblTitle.setForeground(Color.BLUE);
		add(lblTitle,BorderLayout.NORTH); 
		//定义菜单栏界面 ，使用Grid布局
		eastPanel = new JPanel(new GridLayout(15,1,10,10));
		btnReset = new JButton("重来");
		btnBack = new JButton("悔一步");
		btnFirst = new JButton("第一关");
		btnPrev = new JButton("上一关"); 
		btnNext = new JButton("下一关");
		btnLast = new JButton("最后一关");
		btnSelect = new JButton("选关");
		btnMusic = new JButton("音乐关");
		btnAbout = new JButton("关于");
		cbMusic = new JComboBox(smusic);
		btnHelp = new JButton("帮助");
		eastPanel.add(btnReset);
		eastPanel.add(btnBack);
		eastPanel.add(btnFirst);
		eastPanel.add(btnPrev);
		eastPanel.add(btnNext);
		eastPanel.add(btnLast);
		eastPanel.add(btnSelect);
		eastPanel.add(btnMusic);
		eastPanel.add(cbMusic);
		eastPanel.add(btnHelp);
		eastPanel.add(btnAbout);
		MenuGUI.btnReset.addActionListener(this);
		MenuGUI.btnBack.addActionListener(this);
		MenuGUI.btnFirst.addActionListener(this);
		MenuGUI.btnPrev.addActionListener(this);
		MenuGUI.btnNext.addActionListener(this);
		MenuGUI.btnLast.addActionListener(this);
		MenuGUI.btnSelect.addActionListener(this);
		MenuGUI.btnMusic.addActionListener(this);
		MenuGUI.btnHelp.addActionListener(this);
		MenuGUI.btnAbout.addActionListener(this);
		MenuGUI.cbMusic.addItemListener(this);
		add(eastPanel,BorderLayout.EAST);
		playgui = new PlayGUI();
		add(playgui);
		setSize(650, 670);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sound = new Sound();
		playgui.requestFocus();
	}

	//主函数
	public static void main(String[] args)
	{
		new MenuGUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		if(e.getSource()==MenuGUI.btnReset)
		{
			playgui.init(playgui.level);
		}
		else if (e.getSource()==MenuGUI.btnBack)
		{
			playgui.back();
		}
		else if (e.getSource()==MenuGUI.btnFirst)
		{
			playgui.Sel("First");
		}
		else if (e.getSource()==MenuGUI.btnPrev)
		{
			playgui.Sel("Prev");
		}
		else if (e.getSource()==MenuGUI.btnNext)
		{
			playgui.Sel("Next");
		}
		else if (e.getSource()==MenuGUI.btnLast)
		{
			playgui.Sel("Last");
		}
		else if (e.getSource()==MenuGUI.btnSelect)
		{
			String str = "请选择关卡(1~"+PlayGUI.MAXLEVEL+"),当前是第"+playgui.level+"关";
			int temp = playgui.level;
			/*输入对话框
			 * 1.父窗口
			 * 2.界面上提示内容
			 * 3.弹出窗口的标签
			 * 4.弹出窗口显示的按钮
			 * 
			 */
			 //获取输入的值
			String s = JOptionPane.showInputDialog(null,str,"选关",JOptionPane.YES_NO_OPTION);
			try
			{
				playgui.level = Integer.parseInt(s);
			}
			catch(Exception ex)
			{
				//没输入任何东西时
				if(s==null)
				{
					return;
				}
				JOptionPane.showMessageDialog(null, "输入错误,请重新输入!","提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(playgui.level>PlayGUI.MAXLEVEL || playgui.level<1)
			{
				JOptionPane.showMessageDialog(null, "对不起,没有这一关！","提示", JOptionPane.ERROR_MESSAGE);
				playgui.level = temp;
			}
			else
			{
				playgui.init(playgui.level);
			}
		}
		else if (e.getSource()==MenuGUI.btnMusic)
		{
			//获取按钮的标题
			String title = btnMusic.getText();
			if(title.equals("音乐关"))
			{
				sound.mystop(); //关闭音乐
				btnMusic.setText("音乐开");
			}
			else
			{
				sound.loadSound();//开启音乐
				btnMusic.setText("音乐关");
			}
		}
		else if (e.getSource()==MenuGUI.btnHelp)
		{
			JOptionPane.showMessageDialog(null, "推箱子游戏帮助: \n 1、游戏操作：↑↓←→键操作游戏人物移动。\n 2、通关判定：所有箱子都被推入指定位置即可通关。\n 3、游戏其他功能：选关、第一关、最后一关、悔一步、重来、选择背景音乐。\n 4、屏幕上方显示当前的关数，您可以方便地选关进行游戏。","帮助",JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource()==MenuGUI.btnAbout)
		{
			JOptionPane.showMessageDialog(null, " 开发者：高一航、马啸天\n 开发时间：2017年6月\n 开发地点：黑龙江大学软件实验室\n 版本：v1.0","关于",JOptionPane.PLAIN_MESSAGE);
		}
	    playgui.requestFocus();
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		//获取选择项
		int index = cbMusic.getSelectedIndex();
		sound.setMusic(musicFile[index]); //设置文件名给Sound类
		if(sound.isplay())
		{
			sound.mystop();  //停止播放
		}
		String title = btnMusic.getText();
		if(title.equals("音乐开"))
		{
			return;
		}
		sound.loadSound();
	}
	
}

package Music;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Sound 
{
	String path=new String("musics\\");   //目录
	String  file=new String("nor.mid");  //默认音乐
	Sequence seq;
    Sequencer midi;
	boolean sign;  //标志  true 正在播放  false 没在播放
	
	public Sound()
	{
		loadSound();
	}
	
	public void loadSound()   //播放音乐
	{
		try {
            seq=MidiSystem.getSequence(new File(path+file));
            midi=MidiSystem.getSequencer();
            midi.open();
            midi.setSequence(seq);
			midi.start();
			midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        }
        catch (Exception ex) 
		{
        	ex.printStackTrace();
        }
		sign=true;
	}
	
	public void mystop()    //音乐停止
	{
		midi.stop();
		midi.close();
		sign=false;
	}
	
	public boolean isplay()  //判断是否在播放
	{
		return sign;
	}
	
	public void setMusic(String e)  //获取文件名称
	{
		file=e;
	}
	
}

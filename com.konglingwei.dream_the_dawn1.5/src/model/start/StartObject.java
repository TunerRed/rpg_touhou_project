package model.start;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import view.Control;
/**
 * ��ʼ����
 * ��������Ϸ��ʼ������������ѡ��Ľ���
 * */
public class StartObject extends GameObject{
	
	private static Image bac,help,pointers[];
	private int x=400,y=260,pointer=0;
	/**
	 * ��¼��ǰָ�뵽���ĸ�ѡ���ǰ�棬�滭������ȷ�ϼ����º��ѡ��
	 * */
	private int step = 0;
	static{
		pointers = new Image[25];
		try {
			bac = ImageIO.read(StartObject.class.getClassLoader().getResourceAsStream("source/start/title.png"));
			help = ImageIO.read(StartObject.class.getClassLoader().getResourceAsStream("source/start/help.png"));
			for(int i=0;i<pointers.length;i++){
				pointers[i] = ImageIO.read(StartObject.class.
						getClassLoader().getResourceAsStream("source/start/pointer/pointer"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * ����ʱ�Զ���������
	 * */
	public StartObject(){
		super.musicStart("source/start/title.wav");
		super.clearStatic();
	}
	
	/**
	 * ����ʵ�ֻ滭�ķ������������������������ת
	 *  @param g ����
	 * */
	public void paint(Graphics g){
		g.drawImage(bac, 0, 0, null);
		g.drawImage(pointers[pointer++], x, y+step*75, null);
		if(pointer==25)
			pointer = 0;
		if(Control.isPressed){
			g.drawImage(help, 0, 0, null);
		}
	}
	
	/**
	 * �����̰�������ʱ���������е�int����step�仯��
	 * �����̰���Zʱ��ִ��die����
	 * */
	@Override
	protected void keyResponse() {
		if(Control.UP&&super.checkTimeGap(60)){
			step--;
			if(step<0)
				step = 3;
			super.change();
		}
		else if(Control.DOWN&&super.checkTimeGap(60)){
			step++;
			if(step > 3)
				step = 0;
			super.change();
		}
		else if(Control.Z&&super.checkTimeGap(100))
			die();
	}
	
	/**
	 * ��Ӧ���̲��滭
	 * */
	@Override
	public  void draw(Graphics g) {
		keyResponse();
		paint(g);
	}

	/**
	 * ���ݵ�ǰѡ���ѡ�ͬ
	 * ������Ӧ�Ĳ�����
	 * */
	@Override
	public void die() {
		
		if(super.checkStartGap(200))
		switch(step){
			case 0:
				super.click();
				musicStop();
				new LoadingObject(null);
				break;
			case 1:
				super.click();
				Game.getInstance().setSaving(true);
				break;
			case 2:
				super.click();
				System.exit(0);
			case 3:
				super.click();
				new ProblemObject();
		}
	}	

}

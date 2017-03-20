package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Properties;

import model.rpg.RpgObject;
import model.rpg.map.MapObjects.MapObject;
import model.start.LoadObject;
import view.Control;
import view.Lib;
/**
 * ����ģʽ�����°����Ż���ʾ��һ����Ϣ��֪��ȫ����Ϣ��ʾ��ϡ�
 * */
public abstract class Story extends Auto {
	protected Properties properties = new Properties();
	/**
	 * String����initial������properties�ж�ȡ���ݵĸı�
	 * �磺
	 * �ı�initialΪ��S�����������Ϣ���ȡS0��S1��S2�ȵ�
	 * */
	protected String initial = null;
	/**
	 * ���췽�������������ļ���
	 * ��ʾ���¾����
	 * @param map ����ģʽ��Ҫ�ػ��ĵ�ͼ
	 * */
	public Story(MapObject[][] map) {
		super(map);
		try {
			properties.load(Story.class.getClassLoader().getResourceAsStream("source/rpg/story.properties"));
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paint(Graphics g) {
		// TODO �Զ����ɵķ������
		if(step > super.DISTANCE_TO_DIE){
			die();
			return;
		}
		g.drawString(properties.getProperty(initial+step), 240, 540);
		if(Control.Z && super.checkTimeGap(300)){
			step++;
		}
		
	}
	
	public void draw(Graphics g){
		super.draw(g);
		g.setColor(Color.red);
		g.setFont(Lib.terror);
		paint(g);
	}
	
	public void die(){
		new RpgObject(LoadObject.read(3),0);
	}

}

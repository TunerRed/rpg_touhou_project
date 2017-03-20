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
 * 故事模式，按下按键才会显示下一条信息，知道全部信息显示完毕。
 * */
public abstract class Story extends Auto {
	protected Properties properties = new Properties();
	/**
	 * String类型initial负责在properties中读取内容的改变
	 * 如：
	 * 改变initial为“S”，则故事信息会读取S0、S1、S2等等
	 * */
	protected String initial = null;
	/**
	 * 构造方法，载入配置文件，
	 * 显示故事剧情等
	 * @param map 剧情模式下要重画的地图
	 * */
	public Story(MapObject[][] map) {
		super(map);
		try {
			properties.load(Story.class.getClassLoader().getResourceAsStream("source/rpg/story.properties"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paint(Graphics g) {
		// TODO 自动生成的方法存根
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

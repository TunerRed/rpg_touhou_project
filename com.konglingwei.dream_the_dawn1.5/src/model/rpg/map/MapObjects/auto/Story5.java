package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;

import model.GameObject;
import model.over.BadEndObject;
import model.rpg.map.MapObjects.MapObject;
import view.Lib;
/**��ͨ���ǰ����鲿��*/
public class Story5 extends Story {
	
	public Story5(MapObject[][] map) {
		super(map);
		// TODO �Զ����ɵĹ��캯�����
		super.initial = "F";
		super.DISTANCE_TO_DIE = 10;
		GameObject.musicStop();
		super.musicStart("source/end/badend.wav");
	}

	protected void paint(Graphics g){
		g.clearRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		super.paint(g);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(Lib.regular);
		paint(g);
	}
	
	@Override
	public void die(){
		new BadEndObject();
	}
}

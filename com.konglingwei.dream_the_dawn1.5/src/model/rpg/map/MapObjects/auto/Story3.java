package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;

import model.rpg.RpgObject;
import model.rpg.map.MapObjects.MapObject;
import model.start.LoadObject;
import view.Lib;
/**��ƪ�Ľ��ܣ�����������*/
public class Story3 extends Story {

	public Story3(MapObject[][] map) {
		super(map);
		// TODO �Զ����ɵĹ��캯�����
		super.initial = "s";
		super.DISTANCE_TO_DIE = 5;
	}
	
	protected void paint(Graphics g){
		g.setColor(Color.white);
		g.setFont(Lib.regular);
		super.paint(g);
	}
	
	public void die(){
		new RpgObject(LoadObject.read(3),0);
	}
}

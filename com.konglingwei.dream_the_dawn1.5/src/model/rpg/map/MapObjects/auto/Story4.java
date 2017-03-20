package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;

import model.over.GameEndObject;
import model.rpg.map.MapObjects.MapObject;
import view.Lib;
/**�ý��ǰ����鲿��*/
public class Story4 extends Story {

	public Story4(MapObject[][] map) {
		super(map);
		// TODO �Զ����ɵĹ��캯�����
		super.initial = "f";
		super.DISTANCE_TO_DIE = 9;
		super.musicStop();
		super.musicStart("source/end/ĺɫ��Ȼ.au");
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
		new GameEndObject();
	}
}

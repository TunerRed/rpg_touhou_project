package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;

import model.over.GameEndObject;
import model.rpg.map.MapObjects.MapObject;
import view.Lib;
/**好结局前奏剧情部分*/
public class Story4 extends Story {

	public Story4(MapObject[][] map) {
		super(map);
		// TODO 自动生成的构造函数存根
		super.initial = "f";
		super.DISTANCE_TO_DIE = 9;
		super.musicStop();
		super.musicStart("source/end/暮色苍然.au");
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

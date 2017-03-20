package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;

import model.rpg.RpgObject;
import view.Lib;
/**��Ϸ��ʼǰ�Ĺ���*/
public class Story2 extends Story {

	public Story2() {
		super(null);
		// TODO �Զ����ɵĹ��캯�����
		super.initial = "S";
		super.DISTANCE_TO_DIE = 4;
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
		new RpgObject();
	}

}

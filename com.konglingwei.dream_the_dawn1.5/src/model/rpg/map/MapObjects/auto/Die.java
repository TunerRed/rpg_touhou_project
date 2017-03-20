package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;

import model.GameObject;
import model.over.GameOverObject;
import model.rpg.map.MapObjects.MapObject;
/**
 * ���ﵽ��ĳ��������ʱ�Ĵ���Ϊ������
 * */
public abstract class Die extends Auto {

	public Die(MapObject[][] map) {
		super(map);
		// TODO �Զ����ɵĹ��캯�����
		GameObject.musicStop();
	}
	
	public void draw(Graphics g){
		super.draw(g);
		if(DISTANCE_TO_DIE+3 <= step){
			die();
		}
	}
	
	public void die(){
		new GameOverObject();
	}
	
}

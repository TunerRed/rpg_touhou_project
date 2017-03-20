package model.over;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import model.start.StartObject;
import view.Control;
/**
 * �����������ֵ�GameObject
 * */
public class GameOverObject extends GameObject {
	private static Image gameover;
	static{
		try {
			gameover=ImageIO.read(GameOverObject.class.getClassLoader().getResourceAsStream("source/gameover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ֹͣ��Ϸ�������֣�
	 * ���Ҳ���������Ч��
	 * */
	public GameOverObject(){
		super.musicStop();
		super.tempSoundPlay("source/rpg/die.wav");
		super.clearStatic();
	}
	
	/**
	 * Z����ʱִ��die��������GameOverObject
	 * */
	@Override
	protected void keyResponse() {
		// TODO Auto-generated method stub
		if(Control.Z){
			die();
		}
			
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		keyResponse();
		g.drawImage(gameover, 0, 0, null);
	}
	
	/**
	 * ��Ϸ����������ֺ�һ�밴��Z������Ӧ
	 * */
	@Override
	public void die() {
		if(super.checkStartGap(1000)){
			Game.getInstance().setCurrent(new StartObject());
		}
	}

}

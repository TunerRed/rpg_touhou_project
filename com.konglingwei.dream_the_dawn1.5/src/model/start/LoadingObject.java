package model.start;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameObject;
import model.over.GameOverObject;
import model.rpg.RpgObject;
import model.rpg.SaveData;
import model.rpg.map.MapObjects.auto.Story2;
/**
 * ���幦�ܣ��ڼ���RpgObject��֮ǰʹ�ô��࣬������Ϸ����ȥ�俨������
 * */
public class LoadingObject extends GameObject {
	private int wait = 0;
	private static Image gameover;
	private SaveData save;

	static {
		try {
			gameover = ImageIO.read(GameOverObject.class.getClassLoader().getResourceAsStream("source/loading.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���췽����
	 *  @param save Ҫ��ȡ�Ĵ浵��������nullʱ����Ϊ���µĿ�ʼ��û�д浵
	 * */
	public LoadingObject(SaveData save) {
		this.save = save;
	}

	@Override
	protected void keyResponse() {
	}
	
	/**
	 * ����int�������������ﵽ����ʱ��
	 * ˵��Load�������㹻ʱ�䣬
	 * ��new��RpgObject
	 * @param g ����
	 * */
	@Override
	public void draw(Graphics g) {
		wait++;
		if (wait == 20) {
			die();
		}
		g.drawImage(gameover, 0, 0, null);
	}
	
	/**
	 * �ж�Ŀǰ�Ƕ��������µ���Ϸ��Ȼ��ִ�в�ͬ����
	 * */
	@Override
	public void die() {
		if(save == null)
			new Story2();
		else
			new RpgObject(save);
	}

}

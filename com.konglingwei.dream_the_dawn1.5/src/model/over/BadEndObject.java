package model.over;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import model.start.StartObject;
import view.Control;
import view.Lib;

public class BadEndObject extends GameObject {
	
	int step = 0,imgBounds=Lib.gameHEIGHT;
	
	Image roll;
	
	public BadEndObject(){
		try {
			roll = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("source/end/roll2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.clearStatic();
	}
	
	@Override
	protected void keyResponse() {
		// TODO �Զ����ɵķ������
		if(step<999)
			step++;
		if(step >960&&(Control.Z||Control.X||Control.isPressed)){
			die();
		}
				
	}

	@Override
	public void draw(Graphics g) {
		// TODO �Զ����ɵķ������
		keyResponse();
		g.clearRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		if(imgBounds > -roll.getHeight(null)+0.5*Lib.gameHEIGHT)
			imgBounds -= 3;
		g.drawImage(roll, 100, imgBounds, null);
	}

	@Override
	public void die() {
		// TODO �Զ����ɵķ������
		musicStop();
		Game.getInstance().setCurrent(new StartObject());;
	}

}

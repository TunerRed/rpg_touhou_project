package model.start;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import model.GameObject;
import view.Control;
import view.ImageSets;
/**
 * �ṩ���ⷴ������
 * */
public class ProblemObject extends GameObject {
	Image back;
	public ProblemObject(){
		back = ImageSets.getImg(65);
	}

	@Override
	public void keyResponse() {
		// TODO �Զ����ɵķ������
		if((Control.Z|Control.X|Control.C)&&super.checkStartGap(100)){
			die();
			return;
		}
		if(Control.isPressed && (Control.clickX>241&&Control.clickX<581)&&(Control.clickY>56&&Control.clickY<126)){
			if(super.checkTimeGap(1000)){
				super.tempSoundPlay("source/start/problemobject.wav");
				try{
					Runtime.getRuntime().exec("cmd /c start http://user.qzone.qq.com/1287111966");
				}catch(IOException e){
					e.printStackTrace();
					new File("error").mkdir();
				}
			}
			
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO �Զ����ɵķ������
		
		g.drawImage(back, 0, 0, null);
		if(Control.isPressed && (Control.clickX>241&&Control.clickX<581)&&(Control.clickY>56&&Control.clickY<126)){
			g.setColor(Color.BLUE);
			g.drawRect(241, 56, 340, 70);
			g.drawRect(242, 57, 338, 68);
		}
		keyResponse();
	}

	@Override
	public void die() {
		// TODO �Զ����ɵķ������
		super.musicStop();
		new StartObject();
	}

}

package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;

import model.GameObject;
import model.rpg.Player;
import model.rpg.map.MapObjects.MapObject;
import view.ImageSets;
import view.Lib;


/**
 * 地图中的剧情模块，
 * 可能是死亡，也可能是故事。
 * 此时人物的动作收到限制。
 * new出此类前，临时存档到publicData中，结束后恢复RpgObject并读取publicData存档
 * */
public abstract class Auto extends GameObject{
	protected MapObject[][] map;
	/**
	 * DISTANCE_TO_DIE 即当前进度距离此场景结束还有多少距离
	 * 距离可能是根据键盘相应缩短
	 * 也有可能是系统自动计算并缩短
	 * */
	protected int DISTANCE_TO_DIE = 10;
	protected int step = 0;
	public Auto(MapObject[][] map){
		this.map = map;
	}
	@Override
	protected void keyResponse() {
		// TODO 自动生成的方法存根
		
	}
	
	/**
	 * 抽象的paint方法，
	 * 让子类实现。
	 * */
	protected abstract void paint(Graphics g);
	
	/**
	 * 设置默认的draw方法，把地图、人物和阴影画出来
	 * */
	@Override
	public void draw(Graphics g) {
		// TODO 自动生成的方法存根
		g.clipRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		for (int i = 0; i < Lib.clipY; i++)
			for (int j = 0; j < Lib.clipX; j++) {
				g.drawImage(ImageSets.getImg(map[i][j]), Lib.boundsX + j * Lib.boundsPerImg,
						Lib.boundsY + i * Lib.boundsPerImg, null);
			}
		paint(g);
		//若Player的Direction未初始化的话，此处画不出人物来
		Player.getInstance().draw(g, Player.getInstance().getDirection());
		g.drawImage(ImageSets.getImg(52),0,0,null );
	}
	
	@Override
	public abstract void die();
}

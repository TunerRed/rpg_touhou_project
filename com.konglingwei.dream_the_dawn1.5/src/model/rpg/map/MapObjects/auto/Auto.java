package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;

import model.GameObject;
import model.rpg.Player;
import model.rpg.map.MapObjects.MapObject;
import view.ImageSets;
import view.Lib;


/**
 * ��ͼ�еľ���ģ�飬
 * ������������Ҳ�����ǹ��¡�
 * ��ʱ����Ķ����յ����ơ�
 * new������ǰ����ʱ�浵��publicData�У�������ָ�RpgObject����ȡpublicData�浵
 * */
public abstract class Auto extends GameObject{
	protected MapObject[][] map;
	/**
	 * DISTANCE_TO_DIE ����ǰ���Ⱦ���˳����������ж��پ���
	 * ��������Ǹ��ݼ�����Ӧ����
	 * Ҳ�п�����ϵͳ�Զ����㲢����
	 * */
	protected int DISTANCE_TO_DIE = 10;
	protected int step = 0;
	public Auto(MapObject[][] map){
		this.map = map;
	}
	@Override
	protected void keyResponse() {
		// TODO �Զ����ɵķ������
		
	}
	
	/**
	 * �����paint������
	 * ������ʵ�֡�
	 * */
	protected abstract void paint(Graphics g);
	
	/**
	 * ����Ĭ�ϵ�draw�������ѵ�ͼ���������Ӱ������
	 * */
	@Override
	public void draw(Graphics g) {
		// TODO �Զ����ɵķ������
		g.clipRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		for (int i = 0; i < Lib.clipY; i++)
			for (int j = 0; j < Lib.clipX; j++) {
				g.drawImage(ImageSets.getImg(map[i][j]), Lib.boundsX + j * Lib.boundsPerImg,
						Lib.boundsY + i * Lib.boundsPerImg, null);
			}
		paint(g);
		//��Player��Directionδ��ʼ���Ļ����˴�������������
		Player.getInstance().draw(g, Player.getInstance().getDirection());
		g.drawImage(ImageSets.getImg(52),0,0,null );
	}
	
	@Override
	public abstract void die();
}

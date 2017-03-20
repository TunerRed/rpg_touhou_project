package model.rpg.map.MapObjects;

import java.io.Serializable;
import java.util.ArrayList;

import model.rpg.Direction;
import model.rpg.map.Map;
import model.rpg.map.MapKind;
/**
 * ��ʵ�����л��������д��浵�ĵ�ͼԪ����
 * */
public class MapObject implements Serializable{
	public int toWhich,playerX=5, playerY=5;
	private boolean canGo;
	private int imgType=0;
	
	protected Direction direction;
	protected MapKind kind;
	
	/**
	 * ���췽����
	 * �����ж����Ԫ���ǲ����������̤��ȥ��
	 * @param kind Map������
	 * @param imgType ѡ���ImageSets�е�ͼƬ
	 * */
	public MapObject(MapKind kind,int imgType){
		this.kind = kind;
		this.imgType = imgType;
		
		if(kind==MapKind.BARY||kind==MapKind.MIRROR||kind==MapKind.SHELL)
			canGo = false;
		else
			canGo = true;
	}
	
	public boolean canGo(){
		return canGo;
	}
	protected void setCanGo(boolean cango){
		canGo = cango;
	}
	
	/**
	 * ���ͼƬ����
	 * @return
	 * */
	public int getType(){
		return imgType;
	}
	/**
	 * ���MapKind
	 * @return
	 * */
	public MapKind getKind(){
		return kind;
	}
	/**
	 * ��ô����¼���Ҫ�ķ���
	 * @return
	 * */
	public Direction getDirection(){
		return direction;
	}
	public void editMapList(ArrayList<Map> mapList){}
	
	public ArrayList<String> getInfo(){
		return null;
	}
	public void die(MapObject[][] map){
	}
}

package model.rpg;

import java.io.Serializable;
import java.util.ArrayList;

import model.rpg.map.Map;
/**
 * �浵�࣬ʵ����Ϸ�浵�Ͷ������ܡ���Ϸ����Auto״̬ʱҲ������һ�������publicData.dat�浵
 * ��Autoģ������󼴽���һ�ζ�����������Ȼ��ȡpublicData.dat�浵��
 * */
public class SaveData implements Serializable{
	private ArrayList<Map> map = null;
	private ArrayList<Item> items = null;
	private Map currentMap = null;
	private long lastTime = 0;
	private int x,y;
	private Direction direction;
	public SaveData (){
	}
	/**
	 * ��ʽʹ�õĹ��췽�����洢�����ݰ�����
	 * @param mapList ȫ���ĵ�ͼArrayList
	 * @param currentMap ���ڵĵ�ͼ���ĸ�
	 * @param itemList �������Ʒ��
	 * @param x ����������X����
	 * @param y ����������Y����
	 * @param direction ����ķ���
	 * @param lastTime ��Ϸ�����˶೤ʱ�䣨������Autoʹ�õ�ʱ�䣩
	 * */
	public SaveData (ArrayList<Map> mapList , ArrayList<Item> itemList , Map currentMap ,int x,int y, Direction direction,long lastTime){
		map = mapList;
		items = itemList;
		this.currentMap = currentMap;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.lastTime = lastTime;
	}
	
	public ArrayList<Map> getMaps(){
		return map;
	}
	public ArrayList<Item> getItem(){
		return items;
	}
	public Map getCurrent(){
		return currentMap;
	}
	public Direction getDirection(){
		return direction;
	}
	public long getLastTime(){
		return lastTime;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}

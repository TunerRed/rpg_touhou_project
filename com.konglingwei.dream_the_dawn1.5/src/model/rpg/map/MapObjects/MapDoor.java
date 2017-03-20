package model.rpg.map.MapObjects;

import java.util.ArrayList;

import model.rpg.Item;
import model.rpg.Player;
import model.rpg.map.MapKind;
/**
 * �ź͵�ͼ֮���ת��
 * */
public class MapDoor extends MapObject {
	private Item item = null;
	public MapDoor(int toWhich, int playerX, int playerY, int imgType) {
		super(MapKind.DOOR, imgType);
		super.toWhich = toWhich;
		super.playerX = playerX;
		super.playerY = playerY;
	}
	
	/**��
	 * ���췽��
	 * @param toWhich ����һ����ͼ��RpgObject��ArrayList Map �еĵڼ�����
	 * @param playerX ���������
	 * @param playerY ����������
	 * @param imgType ��ͼƬ���ͣ�
	 * @param cango ����Ŀǰ�Ƿ����߶���
	 * @param item ����ͨ�������ſ�����Ҫ����Ʒ
	 * */
	public MapDoor(int toWhich, int playerX, int playerY, int imgType,boolean cango,Item item){
		this(toWhich, playerX, playerY, imgType);
		this.item = item;
		super.setCanGo(cango);
	}
	
	/**
	 * �鿴Ŀǰ�������ܲ����ߣ����Ƿ������ĳ������
	 * */
	@Override
	public boolean canGo(){
		if(!super.canGo() && checkItem(Player.getInstance().getItems())){
			super.setCanGo(true);
		}
		return super.canGo();
	}
	/**
	 * ������������Ƿ����ĳ����Ʒ
	 * */
	private boolean checkItem(ArrayList<Item> items){
		for(int i = 0; i < items.size() ; i++){
			if(items.get(i).equals(item)){
				//Player.getInstance().removeItem(i);
				return true;
			}
		}
		return false;
	}
}

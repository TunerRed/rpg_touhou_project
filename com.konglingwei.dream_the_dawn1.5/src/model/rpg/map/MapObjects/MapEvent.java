package model.rpg.map.MapObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.Player;
import model.rpg.map.MapKind;

public class MapEvent extends MapObject {
	protected static Properties properties = new Properties();
	protected static ArrayList<String> Null = new ArrayList<String>();
	static {
		try {
			properties.load(MapEvent.class.getClassLoader().getResourceAsStream("source/rpg/maps/eventInfo.properties"));
			Null.add("...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private ArrayList<String> info = new ArrayList<String>();
	
	private boolean hasShown = false;
	protected Item item = null;
	
	/**
	 * ���ֹ��췽��
	 * Ĭ��ͼƬΪ����
	 * Ĭ��û����ӵ���Ʒ��ͼƬ����
	 * ������Ʒ�Ĺ��췽��
	 * ������Ʒ����ͼƬ���Ա༭�Ĺ��췽��
	 * @param infoNum ������Ϣ����EventInfo.properties�в���
	 * @param direction ʱ�䴥���ķ���
	 * */
	
	public MapEvent(int[] infoNum,Direction direction) {
		super(MapKind.EVENT,36);
		init(infoNum,direction);
	}
	public MapEvent(int[] infoNum,Direction direction,int imgType) {
		super(MapKind.EVENT,imgType);
		init(infoNum,direction);
	}
	public MapEvent(int[] infoNum,Direction direction,Item item){
		this(infoNum,direction);
		this.item = item;
	}
	public MapEvent(int[] infoNum,Direction direction,Item item,int imgType){
		this(infoNum,direction,imgType);
		this.item = item;
	}
	private void init(int[] infoNum,Direction direction){
		for(int i = 0 ; i < infoNum.length ; i++){
			info.add(properties.getProperty("I"+infoNum[i],"..."));
		}
		super.direction = direction;
	}
	/**
	 * �ı����Ԫ�ص�MapKind��������ʹ�á�
	 * @param kind Map������
	 * */
	protected void changeKind(MapKind kind){
		super.kind = kind;
	}
	
	/**
	 * ��������¼��ڵ����Ӧ����ʾ����Ϣ
	 * �������������Ʒ��Ӧ��Ϊ���������Ʒ��
	 * ����boolean���ò�������ӵڶ���
	 * */
	public ArrayList<String> getInfo(){
		if(item != null && !hasShown){
			Player.getInstance().addItem(item);
			hasShown = true;
		}else if(item !=null && hasShown){
			return Null;
		}
		return info;
	}
}

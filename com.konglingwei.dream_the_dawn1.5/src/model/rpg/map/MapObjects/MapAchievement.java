package model.rpg.map.MapObjects;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.map.Map;
import model.rpg.map.MapKind;

/**
 * ������ĳ����Ϣ֮��
 * ���ĳ�����������ɾͣ�
 * ��ʱ��ͼ��ĳ���ط������ı�
 * �Ӷ���������һ����仯�ĵ�ͼ��̽��
 * */

public class MapAchievement extends MapEvent {
	private String changeSomewhere,no;
	private ArrayList<String> Temp = Null;
	private Map temp;
	private boolean showingDialog = false,opened = false;
	public MapAchievement(int[] infoNum, Direction direction,int changeMethod) {
		super(infoNum, direction);
		if(infoNum[0] == -1){
			no = "I"+infoNum[1];
		}
		super.changeKind(MapKind.ACHIEVEMENT);
		changeSomewhere = properties.getProperty("C"+changeMethod);
	}
	public MapAchievement(int[] infoNum, Direction direction,int changeMethod,int imgType) {
		super(infoNum, direction,imgType);
		if(infoNum[0] == -1){
			no = "I"+infoNum[1];
		}	
		super.changeKind(MapKind.ACHIEVEMENT);
		changeSomewhere = properties.getProperty("C"+changeMethod);
	}
	
	public MapAchievement(int[] infoNum,Direction direction,Item item,int changeMethod,int imgType) {
		this(infoNum,direction,changeMethod,imgType);
		super.item = item;
		super.changeKind(MapKind.ACHIEVEMENT);
		// TODO �Զ����ɵĹ��캯�����
	}
	/**
	 * ���ݵ�ͼ��Ϣ��ͬ��ִ�в�ͬ�������ı��ͼĳ���ط���״̬
	 * */
	public void editMapList(ArrayList<Map> mapList){
		temp = mapList.get(Integer.parseInt(changeSomewhere.split("_")[0]));
		switch(Integer.parseInt(changeSomewhere.split("_")[0])){
		case 4:
			Temp = super.getInfo();
			if(Integer.parseInt(changeSomewhere.split("_")[1])==6)
				temp.edit(6, 4,new MapDoor(5,6,9,36));
			else if(Integer.parseInt(changeSomewhere.split("_")[1])==3){
				temp.edit(5, 12,new MapEvent(new int[]{0}, Direction.U));
				temp.edit(3, 12, new MapObject(MapKind.BARY,66));
			}
				
			break;
		case 0:
			if(!no.isEmpty()&& !showingDialog && !opened){
				//��������
				showingDialog = true;
				if(JOptionPane.showInputDialog(properties.getProperty(no)).equals("1000")){
					super.getInfo().remove(0);
					super.getInfo().remove(0);
					Temp = super.getInfo();
					opened = true;
				}
			}
			break;
		default:
				break;
		}	
	}
	
	/**
	 * ������Ϣ������ǽ����ص������������Ƿ��ɺ�ѡ���Ƿ����ý��
	 * */
	@Override
	public ArrayList<String> getInfo(){
		showingDialog = false;
		if(opened){
			//�ý�ּ�¼
			temp.edit(Integer.parseInt(changeSomewhere.split("_")[1]), Integer.parseInt(changeSomewhere.split("_")[2]),
					new MapObject(MapKind.FLOOR,0));
		}
		return Temp;
	}
}

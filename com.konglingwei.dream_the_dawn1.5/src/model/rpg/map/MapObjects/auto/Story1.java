package model.rpg.map.MapObjects.auto;

import java.util.ArrayList;

import model.rpg.RpgObject;
import model.rpg.SaveData;
import model.rpg.map.Map;
import model.rpg.map.MapKind;
import model.rpg.map.MapObjects.MapAuto;
import model.rpg.map.MapObjects.MapObject;
import model.start.LoadObject;
/**׷��սǰ��*/
public class Story1 extends Story {
	private ArrayList<Map> maps;
	public Story1(MapObject[][] map) {
		super(map);
		// TODO �Զ����ɵĹ��캯�����
		super.DISTANCE_TO_DIE = 6;
		super.initial = "A";
		super.musicStop();
		super.musicStart("source/rpg/chase.wav");
		maps = LoadObject.read(3).getMaps();
		maps.get(0).edit(6, 7, new MapObject(MapKind.BARY,38));
		//�˴������ţ����������ͨ����
		//��ɳɾ�ʱ������ͼ���Ͻ����Ա�Ϊfloor���˴����飬�����floor��˵���Ѿ�������ֵ�����
		if(maps.get(0).getSomewhere(0, 0).canGo())
			maps.get(0).edit(16, 14, new MapAuto(36,4));
		else
			maps.get(0).edit(16, 14, new MapAuto(36,5));
		LoadObject.read(3).getCurrent();
		LoadObject.read(3).getCurrent();
		LoadObject.write(new SaveData(maps , 
				LoadObject.read(3).getItem() , LoadObject.read(3).getCurrent(),Map.playerX,Map.playerY , LoadObject.read(3).getDirection(),LoadObject.read(3).getLastTime()));
	}
	
	@Override
	public void die(){
		new RpgObject(LoadObject.read(3),0);
		Map.startChase = true;
	}
	
}

package model.rpg.map.MapObjects;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import model.rpg.map.MapKind;
import model.start.LoadObject;

/**
 * �������ߵ�����ط�ʱ
 * ִ������ط���die����
 * ����������ʽ�������ļ�������һЩ������������
 * */

public class MapAuto extends MapObject {
	private String autoStyle;
	private boolean trigger = false;
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(MapAuto.class.getClassLoader().getResourceAsStream("source/rpg/maps/Auto.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public MapAuto(int imgType,int autoStyle) {
		super(MapKind.AUTO, imgType);
		this.autoStyle = properties.getProperty("D"+autoStyle);
	}
	
	/**�Ѿ��������ĵط������ٴ����ڶ���
	 * �����;���ģʽ��Ȼ��ͬ
	 * ������ÿ�ξ���ʱ��Ҫ����
	 * ������һ����Ϸ��ֻ����һ��
	 * ����ֻҪ����һ�Σ���ͼ�ͻ�ָ���ԭ����״̬
	 * ����ʵ�����Ƿ񴥷�����Boolean����ֻ�ǶԾ���ģ������������
	 * 
	 * ʹ�÷���new��Auto���󣬲�����ʱ�浵��������һ������ģ�飬���������Ӵ˴�������Ϸ
	 * */
	public void die(MapObject[][] map){
		try {
			if(!trigger){
				//ֻ����һ��
				trigger = true;
				
				LoadObject.write(3);
				//����ʹ���в����Ĺ��췽�����������Ϊʲô���о��档�޸ĺ�û���˾��浫�ǳ��򱨴�
				Class.forName(autoStyle).getConstructor(MapObject[][].class).newInstance(map);
			}
		}
		//����֪��catch��ʲô����
		catch (ClassNotFoundException |
				NoSuchMethodException | 
				SecurityException | 
				InstantiationException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

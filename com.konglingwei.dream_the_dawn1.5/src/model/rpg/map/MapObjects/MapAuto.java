package model.rpg.map.MapObjects;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import model.rpg.map.MapKind;
import model.start.LoadObject;

/**
 * 当人物走到这个地方时
 * 执行这个地方的die方法
 * 具体死亡方式由配置文件和其他一些更具体的类决定
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
	
	/**已经触发过的地方不会再触发第二遍
	 * 死亡和剧情模式虽然不同
	 * 死亡在每次经过时都要触发
	 * 剧情在一次游戏中只触发一次
	 * 但是只要死亡一次，地图就会恢复到原来的状态
	 * 所以实际上是否触发过的Boolean属性只是对剧情模块有限制作用
	 * 
	 * 使用反射new出Auto对象，并且临时存档，可能是一个剧情模块，剧情结束后从此处继续游戏
	 * */
	public void die(MapObject[][] map){
		try {
			if(!trigger){
				//只触发一次
				trigger = true;
				
				LoadObject.write(3);
				//反射使用有参数的构造方法，并不清楚为什么会有警告。修改后没有了警告但是程序报错
				Class.forName(autoStyle).getConstructor(MapObject[][].class).newInstance(map);
			}
		}
		//并不知道catch了什么玩意
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

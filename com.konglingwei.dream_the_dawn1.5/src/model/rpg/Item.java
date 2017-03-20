package model.rpg;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
/**
 * �������Ʒ
 * */
public class Item implements Serializable{
	private static Properties properties = new Properties();
	static{
		try {
			properties.load(Item.class.getClassLoader().getResourceAsStream("source/rpg/player/Item.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String name;
	private int number;
	private Item other;
	/**
	 * ���췽��
	 * �������ֺ������ļ�Ϊ������ӵ���
	 * @param number ��Item.properties�и�������ѡ����Ʒ
	 * */
	public Item(int number){
		this.name = properties.getProperty("I"+number);
		this.number = number;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * ��дequals���������ж����ߵ�number�����ΪID���Ƿ���ͬ
	 * */
	public boolean equals(Object obj){
		other = (Item) obj;
		if(other.number == this.number)
			return true;
		else
			return false;
	}
}

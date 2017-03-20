package model.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import model.rpg.RpgObject;
import model.rpg.SaveData;
import view.Control;

public class LoadObject extends GameObject {
	private static Image image, pointers[];
	private int pointer = 0, step = 0;
	private static File save;
	private static SaveData temp,saveData[] = new SaveData[3];
	static {
		try {
			image = ImageIO.read(LoadObject.class.getClassLoader().getResourceAsStream("source/sl.png"));
			pointers = new Image[25];
			for (int i = 0; i < pointers.length; i++) {
				pointers[i] = ImageIO.read(LoadObject.class.getClassLoader()
						.getResourceAsStream("source/start/pointer/pointer" + i + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LoadObject(){
		for(int i = 0 ; i < 3 ; i++){
			if(saveExists(i))
				saveData[i] = read(i);
		}
	}

	@Override
	protected void keyResponse() {
		if (Control.UP  && super.checkTimeGap(100)) {
			step--;
			if(step<0)
				step = 3;
			super.change();
		} else if (Control.DOWN && super.checkTimeGap(100)) {
			step++;
			if(step > 3)
				step = 0;
			super.change();
		} else if ((Control.X || Control.Z) && super.checkTimeGap(150))
			die();
	}
	
	/**
	 * ����滭��
	 * ����ָ��ʹ浵����Ϸ ʱ��
	 * @param g ����
	 * */
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);

		g.setColor(Color.RED);
		g.setFont(new Font("Vivaldi", 1, 30));
		for(int i = 0 ; i < 3 ; i++){
			if(saveExists(i))
				g.drawString(
							((saveData[i].getLastTime()/1000)/3600)+":"+
						   (((saveData[i].getLastTime()/1000)%3600)/60)+":"+
							((saveData[i].getLastTime()/1000)%3600%60),
							500, 140+i*160);
		}
			
		if (step == 3)
			g.drawImage(pointers[pointer++], 700, 525, null);
		else
			g.drawImage(pointers[pointer++], 700, 80 + step * 150, null);
		if (pointer == 25)
			pointer = 0;
	}

	@Override
	public void draw(Graphics g) {
		keyResponse();
		paint(g);
	}
	
	/**
	 * ���ݵ�ǰ״̬�Ĳ�ͬ��
	 * �����Ǵ浵���Ƕ�����
	 * һ����ԣ�
	 * ��Ϸ��ʱΪ�浵���ܣ���ʼ������Ϊ��������
	 * */
	@Override
	public void die() {
		if (super.checkStartGap(150)) {
			if (Control.Z && (Game.getInstance().getCurrent() instanceof StartObject)) {
				// ������״̬�¶�������
				super.click();
				if(step == 3)
					//���ر��⻭��
					Game.getInstance().setSaving(false);
				else {
					//�����������д浵��û�д浵�����
					if(saveExists(step)){
						GameObject.musicStop();
						Game.getInstance().setSaving(false);
						new LoadingObject(read(step));
					}
				}
			} else if (Control.Z && (Game.getInstance().getCurrent() instanceof RpgObject)) {
				// rpg״̬�´浵����,�Ͷ�������ͬ
				super.click();
				if (step == 3) {
					//���ر��⻭��
					Game.getInstance().setSaving(false);
					Game.getInstance().getCurrent().die();
					Game.getInstance().setCurrent(new StartObject());
				} else {
					//�浵
					write(step);
				}
			} else if (Control.X || (Control.C && (Game.getInstance().getCurrent() instanceof RpgObject))) {
				// ȡ���浵����
				super.click();
				Game.getInstance().setSaving(false);
			}
		}
	}
	
	/**
	 * �浵д��SaveData����
	 * ÿ�δ浵��Ҫ���¶���һ��
	 * ȷ��LoadObject�д浵ʱ����ʾ��ȷ
	 * @param step Ҫд���ĸ��浵
	 * */
	public static void write(int step) {
		try {
			//���ȼ���ļ����Ƿ����
			save = new File(".saveData");
			if(!save.exists())
				save.mkdirs();
			//����ģʽ����ʱ�洢��ǰ״̬
			if(step == 3){
				save = new File(save.getPath()+"/publicData.dat");
			}else{
				save = new File(save.getPath()+"/saveData"+step+".dat");
			}
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(save));
			output.writeObject(Game.getInstance().getCurrent().getNowStatus());
			read(step);
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����ģʽ����ʱ�洢��ǰ״̬��ר�÷���
	 * @param savedata Ҫд��publicData�Ĵ浵
	 * */
	public static void write(SaveData savedata) {
		try {
			//���ȼ���ļ����Ƿ����
			save = new File(".saveData");
			if(!save.exists())
				save.mkdirs();
			
			save = new File(save.getPath()+"/publicData.dat");
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(save));
			output.writeObject(savedata);
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ���浵�Ƿ����
	 * @param step ����step���浵�Ƿ����
	 * @return
	 * */
	public static boolean saveExists(int step){
		//���ȼ���ļ����Ƿ����
		save = new File(".saveData");
		if(!save.exists())
			save.mkdirs();
		//����ģʽ����ʱ�洢��ǰ״̬
		if(step == 3){
			save = new File(save.getPath()+"/publicData.dat");
		}else{
			save = new File(save.getPath()+"/saveData"+step+".dat");
		}
		if(save.exists())
			return true;
		else
			return false;
	}
	/**
	 * �������ֽ��в�ͬ�浵�Ķ�ȡ
	 * @param step Ҫ��ȡ�ĸ��浵
	 * @return
	 * */
	public static SaveData read(int step){
		ObjectInputStream input = null;
		try {
			if(step == 3){
				//����ģʽ����ʱ�洢��ǰ״̬
				input = new ObjectInputStream(new FileInputStream(save));
				temp = (SaveData)(input.readObject());
				input.close();
				return temp;
			}else{
				input = new ObjectInputStream(new FileInputStream(save));
				saveData[step] = (SaveData)(input.readObject());
				input.close();
				return saveData[step];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}

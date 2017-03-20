package model;

import java.applet.AudioClip;
import java.awt.Graphics;

import model.rpg.Player;
import model.rpg.SaveData;
import model.rpg.map.Map;
import model.start.ItemObject;
import model.start.LoadObject;
import view.Control;
import view.Lib;
/**
 * Game������ʱ�ػ������������Ķ���
 * */
public abstract class GameObject {
	/**
	 * ��Ч
	 * */
	private static AudioClip click,change,sound;
	/**
	 * ��������
	 * */
	private static AudioClip music = null;
	private double startTime = 0;
	private static long mill = 0;

	static {
		try {
			click = java.applet.Applet.newAudioClip(GameObject.class.getClassLoader().getResource("source/start/ȷ��.wav"));
			change = java.applet.Applet.newAudioClip(GameObject.class.getClassLoader().getResource("source/start/�л�.wav"));
		} catch (Exception ef) {
		}
	}
	
	/**
	 * ���췽����new��ʱ�򽫱��Զ����µ�Game�е�currentObject��
	 * */
	public GameObject() {
		startTime = System.currentTimeMillis();
		if (!(this instanceof LoadObject || this instanceof ItemObject)) {
			Game.getInstance().setCurrent(this);
		}
	}
	
	/**
	 * ѭ�����ű�������
	 * @param music �������ֵ�·��
	 * */
	public void musicStart(String music) {
		try {
			GameObject.music = java.applet.Applet.newAudioClip(this.getClass().getClassLoader().getResource(music));
			GameObject.music.loop();// ѭ������
		} catch (Exception ef) {
			javax.swing.JOptionPane.showMessageDialog(null, "���ֲ���ʧ��");
		}
	}

	protected abstract void keyResponse();

	public abstract void draw(Graphics g);

	public abstract void die();
	
	/**
	 * Ϊ�˱�����keyPressed������������ɵĲ��㣬��ĳЩ�ط���Ҫ�����ΰ��º��ϴΰ���֮���Ƿ���ʱ���������Ƿ��ɿ�����
	 * @param gap �ɿ��밴��֮���ʱ����
	 * */
	public static boolean checkTimeGap(int gap) {
		boolean timeGap = false;
		if (Math.abs(mill - System.currentTimeMillis()) > gap) {
			timeGap = true;
		}
		mill = System.currentTimeMillis();
		return timeGap;
	}
	
	/**Ϊ�˱�����keyPressed������������ɵĲ��㣬��Ҫ�����ΰ��º͸ո�����֮���Ƿ���ʱ���������Ƿ�new֮��û�м�ʱ����*/
	protected boolean checkStartGap(int gap) {
		long mill = System.currentTimeMillis();
		if (mill - startTime > gap)
			return true;
		return false;
	}

	public GameObject getType() {
		return this;
	}
	
	/**
	 * Rpgר��
	 * */
	public SaveData getNowStatus(){
		return new SaveData();
	}
	
	/**
	 * ������Ч�Ĳ���
	 * @param path �����ļ���·��
	 * */
	public void tempSoundPlay(String path){
		try {
			sound = java.applet.Applet.newAudioClip(this.getClass().getClassLoader().getResource(path));
			sound.play();
		} catch (Exception ef) {
		}
	}
	/**�����Ǿ�̬����ֻҪ��Ϸ���رմ��ڣ��õ���Item�Ͳ��ᶪʧ�����Ա������*/
	protected void clearStatic(){
		
				Player.getInstance().getItems().clear();
				Map.startChase = false;
				Map.chaseReady = 0;
				Control.chaseX = 6;
				Control.chaseY = 6;
				Map.readyForDoor = false;
				Map.playerX = 6- Lib.clipX / 2 - 1;
				Map.playerY = 6- Lib.clipY / 2 - 1;
				Player.getInstance().clearDirection();
	}
	/**
	 * �����Ч
	 * */
	public void click(){
		click.play();
	}
	/**
	 * �����л���Ч
	 * */
	public void change(){
		change.play();
	}
	/**
	 * ��������ֹͣ
	 * */
	public static void musicStop() {
		music.stop();
	}
}

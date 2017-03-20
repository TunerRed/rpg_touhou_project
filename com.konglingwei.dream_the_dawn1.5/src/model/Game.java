package model;

import java.awt.Graphics;

import model.start.LoadObject;
import model.start.StartObject;

public class Game {
	private static Game game = new Game();
	private boolean isSaving = false;
	private GameObject currentObject,loadObject;
	/**
	 * ����һ����̬��Game����
	 * */
	public static Game getInstance(){
		return game;
	}
	/**
	 * ��������
	 * */
	public void init(){
		new StartObject();
		loadObject = new LoadObject();
	}
	/**
	 * ��õ�ǰ�����ػ���GameObject����������Ʒ���ʹ浵��������
	 * */
	public GameObject getCurrent(){
		return currentObject;
	}
	/**
	 * ���õ�ǰGameObject
	 * @param object ����Ŀǰ��
	 * */
	public void setCurrent(GameObject object){
		currentObject = object;
	}
	/**
	 * ���ô浵���������Ƿ����
	 *  @param isSaving �Ƿ����浵��������
	 * */
	public void setSaving(boolean isSaving){
		this.isSaving = isSaving;
		if(isSaving)
			loadObject = new LoadObject(); 
	}
	/**
	 * �жϴ浵���������Ƿ��Ѿ�����
	 * */
	public boolean isSaving(){
		return isSaving;
	}
	
	/**
	 * ����������ػ���ǰ��GameObject��
	 * ���浵�������ʱ�ػ��浵����
	 *  @param g ����
	 * */
	public void paint(Graphics g){
		if(isSaving)
			loadObject.draw(g);
		else{
			currentObject.draw(g);
		}
			
	}
	
}
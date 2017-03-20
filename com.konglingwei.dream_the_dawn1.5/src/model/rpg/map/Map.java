package model.rpg.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import model.Game;
import model.GameObject;
import model.over.GameOverObject;
import model.rpg.Direction;
import model.rpg.Player;
import model.rpg.map.MapObjects.MapObject;
import view.Control;
import view.ImageSets;
import view.Lib;

public class Map implements Serializable {

	/*
	 * playerX,playerY�����������Ƕ�player��map����Զ�λ���൱�������Ԫ��
	 * ÿ��ȡ����ʱ��playerX,playerYΪ��׼ȡclip
	 * ���ߵ�ֵ��player�������е�λ��Ϊ���ݣ������Ӧ��������clip���Ͻ�Ϊelements����ĵڼ��еڼ���
	 * ���ʼ��Ӧ�ɱ���ͼ��MapKind.DOOR���ڵ�λ�þ���
	 */
	
	/* �趨��ά�����ͼʱ���Ͻ�λ��,XY�ֱ��Ǻ������򳤶�*/
	public static int wait = 0,playerX = 6- Lib.clipX / 2 - 1, playerY = 6- Lib.clipY / 2 - 1;
	private MapObject[][] elements;
	
	/**׷��ս��ʼ�ı�־������������Ҫ�ǵð�����ط���״̬�ı�*/
	public static boolean startChase = false;
	
	private boolean eventInfoEnd = true;
	private int eventInfoStep = 0;
	private ArrayList<String> eventInfo = new ArrayList<String>();
	public boolean showInfo = false;

	public Map(int sizeX, int sizeY) {
		elements = new MapObject[sizeX][sizeY];
		showInfo = false;
		init();
	}

	private void init() {
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[i].length; j++)
				elements[i][j] = new MapObject(MapKind.BARY, 0);
		Control.chaseX = 6;
		Control.chaseY = 6;
		chaseReady = 0;
	}
	
	/**�����½����ͼʱ��ʼ��λ�ã�����������elements�е�λ�ã�
	 * �ı�ѡ������clip������,
	 * ע�⴫��X,Y������������λ�ã�
	 * �ı��X,Y��������꣬���߸պ��෴
	 * ����ûд�ã��������
	 * @param playerX ��������
	 * @param playerY ��������
	 * */
	public void setLocation(int playerX,int playerY){
		Map.playerX = playerY - Lib.clipX / 2 - 1;
		Map.playerY = playerX - Lib.clipY / 2 - 1;
	}
	
	/**�ı��ά������ĳ��λ�õ���Ϣ
	 * @param x ��������
	 * @param y ��������
	 * @param object ����һ��Ŀ��MapObject
	 * */
	public void edit(int x, int y, MapObject object) {
		elements[x][y] = object;
	}
	
	/**�����ƶ�����ֹ����Խ��
	 * @param dir Ҫ�ƶ��ķ���
	 * */
	public void move(Direction dir) {
		if (dir == Direction.U) {
			if (playerY > 0 && elements[playerY + Lib.clipY / 2][playerX + Lib.clipX / 2 + 1].canGo()) {
				playerY--;
			}
		} else if (dir == Direction.D && elements[playerY + Lib.clipY / 2 + 2][playerX + Lib.clipX / 2 + 1].canGo()) {
			if (playerY + Lib.clipY < elements.length) {
				playerY++;
			}
		} else if (dir == Direction.L) {
			if (playerX > 0 && elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2].canGo()) {
				playerX--;
			}
		} else if (dir == Direction.R) {
			if (playerX + Lib.clipX < elements[0].length
					&& elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 2].canGo()) {
				playerX++;
			}
		}
	}
	
	/**��������Ϊ�¼�ʱ����ʾ��Ʒ��Ϣ
	 * @param g ����
	 * @param direction �����ƶ�����
	 * */
	public void keyResponse(Graphics g, Direction direction) {
		//�����Ƿ�ʼ��ʾ��Ϣ������Ϣ��ʼ��ʾ����һ����Ӧ������������
		if (	!showInfo && Control.Z 
				&& ((elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.EVENT)
					||(elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.ACHIEVEMENT))
				&& elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getDirection() == direction
				&& eventInfoEnd && GameObject.checkTimeGap(100)) {
			eventInfo = elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getInfo();
			showInfo = true;
		}
		//���ж�ȡ��Ϣ�����ж���Ϣ�Ƿ��Ѿ����꣬����Ϣ�������һ���ֲ���������
		else if (showInfo) {
			//ǿ��ת��Graphics2D���ò�͸����
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(0.5f));
			g.setColor(Color.WHITE);
			g.setFont(Lib.regular);
			g.fillRect(Lib.gameWIDTH / 11, Lib.gameHEIGHT - Lib.gameWIDTH / 7, Lib.gameWIDTH - 2 * Lib.gameWIDTH / 11,
					Lib.gameHEIGHT / 7);
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(1.0f));
			g.setColor(Color.BLACK);
			g.drawString(eventInfo.get(eventInfoStep),
					Lib.gameWIDTH / 10, (int) (Lib.gameHEIGHT * (9.0 / 10)));
			//��ȡ��һ����Ϣ
			if(Control.Z && GameObject.checkTimeGap(200)){
				eventInfoStep++;
				//ȫ����ȡ���
				if(eventInfoStep == eventInfo.size()){
					eventInfoStep = 0;
					showInfo = false;
				}
			}
		}
	}
	
	/**���ﲻ��ʱ�Ļ�ͼ����
	 * @param g ����
	 * */
	public void paint(Graphics g) {
		for (int i = 0; i < Lib.clipY; i++)
			for (int j = 0; j < Lib.clipX; j++) {
				g.drawImage(ImageSets.getImg(elements[playerY + i][playerX + j]), Lib.boundsX + j * Lib.boundsPerImg,
						Lib.boundsY + i * Lib.boundsPerImg, null);
			}
		//׷��ս����
		if(startChase){
			chase(g);
		}
	}
	/**�����ƶ�ʱ�Ļ�ͼ����
	 * @param g ����
	 * @param direction �ƶ�����
	 * @param isRunning �Ƿ��ڱ��ܣ����ڱ�����Ϊtrue������Ϊfalse
	 * */
	public void paint(Graphics g, Direction direction, boolean isRunning) {
			if (isRunning) {
				wait++;
				if (wait >= 2) {
					move(direction);
					wait = 0;
				}
			} else {
				wait++;
				if (wait >= 3) {
					move(direction);
					wait = 0;
				}
			}
		paint(g);
	}
	
	/**��������Ϸ�Ԫ���Ƿ��о��ӣ�����У���Ҫ�ػ�����
	 * @param g ����
	 * @param direction �������ڵķ���
	 * */
		public void checkMirror(Graphics g,Direction direction){
			//���·�
			if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.MIRROR && direction == Direction.U){
				g.drawImage(ImageSets.getImg(48), Player.getInstance().getPaintX()+20,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.MIRROR && direction == Direction.L){
				g.drawImage(ImageSets.getImg(49), Player.getInstance().getPaintX()+20,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.MIRROR && direction == Direction.R){
				g.drawImage(ImageSets.getImg(50), Player.getInstance().getPaintX()+20,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.MIRROR && direction == Direction.D){
				g.drawImage(ImageSets.getImg(51), Player.getInstance().getPaintX()+20,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
			//���Ϸ�
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2 + 3].getKind() == MapKind.MIRROR && direction == Direction.L){
				g.drawImage(ImageSets.getImg(46), Player.getInstance().getPaintX()+20+2*Lib.boundsPerImg,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
			//���Ϸ�
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2-1].getKind() == MapKind.MIRROR && direction == Direction.R){
				g.drawImage(ImageSets.getImg(47), Player.getInstance().getPaintX()+20-2*Lib.boundsPerImg,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
		}
	/**��������·�Ԫ���Ƿ�Ϊ��ܣ�����ǣ���Ҫ�ػ������°���
	 * @param g ����
	 * */
	public void checkShell(Graphics g){
		if(elements[playerY + Lib.clipY / 2 + 2 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.SHELL){
			g.drawImage(ImageSets.getImg(elements[playerY + Lib.clipY / 2 + 1 ][playerX + Lib.clipX / 2 + 1]), Player.getInstance().getPaintX()+20,
					Lib.gameHEIGHT / 2+7, null);
		}
	}
	/**������ڵ��Ƿ�ΪAuto����
	 * @param g ����
	 * */
	public void checkAuto(Graphics g){
		if(elements[playerY + Lib.clipY / 2 + 1 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.AUTO){
			MapObject[][] temp = new MapObject[Lib.clipY][Lib.clipX];
			for (int i = 0; i < Lib.clipY; i++)
				for (int j = 0; j < Lib.clipX; j++) {
					temp[i][j] = elements[playerY + i][playerX + j];
				}
			elements[playerY + Lib.clipY / 2 + 1 ][playerX + Lib.clipX / 2 + 1].die(temp);
		}
	}
	/**����ƶ�λ�õĵ���MapObject*/
	public MapObject getUnderfoot(){
		return elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1];
	}
	public MapObject getSomewhere(int x,int y){
		return elements[x][y];
	}
	/**��һ���ǲ�����
	 * @param direction �����ƶ��ķ���
	 * */
	public boolean isDoor(Direction direction){
		switch(direction){
			case U:
				if(elements[playerY + Lib.clipY / 2 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.DOOR 
					&&elements[playerY + Lib.clipY / 2 ][playerX + Lib.clipX / 2 + 1].canGo()){
					if(startChase){	//����׷��ս
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY + Lib.clipY / 2 ][playerX + Lib.clipX / 2 + 1];
					}
					return true;
				}
				break;	
			case D:
				if(elements[playerY + Lib.clipY / 2 +2][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.DOOR
						&&elements[playerY + Lib.clipY / 2 +2][playerX + Lib.clipX / 2 + 1].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY + Lib.clipY / 2 +2][playerX + Lib.clipX / 2 + 1];
					}
					return true;
				}
				break;	
			case L:
				if(elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 ].getKind() == MapKind.DOOR
						&&elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 ];
					}
					return true;
				}	
				break;
			case R:
				if(elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 2].getKind() == MapKind.DOOR
						&&elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 2].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 2];
					}
					return true;
				}
				break;
			default:	
		}
		return false;
	}
	/**��ü��������λ�õ���
	 * @param direction �������ڵķ���
	 * */
	public MapObject getDoor(Direction direction){
		switch(direction){
		case U:
			return elements[playerY + Lib.clipY / 2 ][playerX + Lib.clipX / 2 + 1];
		case D:
			return elements[playerY + Lib.clipY / 2 +2][playerX + Lib.clipX / 2 + 1];
		case L:
			return elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2];
		case R:
			return elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 2];
		default:
			return null;
		}
	
	
	}

	/**
	 * �����Ƿ�Ϊ���Դ�ɳɾ͵ĵ�
	 * @param direction �������ڵķ���
	 * */
	public boolean isAchievement(Direction direction){
		if(elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.ACHIEVEMENT 
			&& direction == elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1].getDirection()){
			return true;
		}
		return false;
	}
	/**
	 * �༭������ͼ
	 * @param mapList ȫ���ĵ�ͼ����
	 * */
	public void editMapList(ArrayList<Map> mapList){
		elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1].editMapList(mapList);
	}
	
	/*
	 * �˴�����ȫ��Ϊ׷��սר�õķ���
	 * ��chaseΪ����
	 * �����������ķ���
	 * ����׷����㷨
	 * �ŵĴ����ת��
	 * �ȵ�
	 * */
	private static int meY ,meX ;
	public static int chaseReady = 0;
	private static int speed = 3,chaseWait = 0;
	public static boolean readyForDoor = false;
	private static MapObject chaseDoor;
	private Direction direction;
	/*
	 * �ҷ�������meY
	 * �ҷ�������meX
	 * elements[meY][meX]
	 * ����������Control.chaseY
	 * ���˺�����Control.chaseX
	 * elements[Control.chaseY][Control.chaseX]
	 * */
	/**
	 * ׷��ս���¾�̬����
	 * */
	public static boolean initChase(){
		//ÿ�θ�������ֵ
		meY=playerY + Lib.clipY / 2 + 1 ;
		meX=playerX + Lib.clipX / 2 + 1 ;
		//�ŵ�ת����ȴ�
		if(readyForDoor){
			Control.chaseX = chaseDoor.playerY;
			Control.chaseY = chaseDoor.playerX;
			if(chaseReady++>15){
				readyForDoor = false;
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * ׷��ս���������������룬������ͼ
	 * @param g ����
	 * */
	public void chase(Graphics g){
		if(!initChase()){
			chaseWait++;
				if(meX > Control.chaseX && elements[Control.chaseY][Control.chaseX+1].canGo()){
					//�жϸò����ƶ�
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX++;
					}
					//�����Ƿ��ƶ����������ƻ���Ҫ���ֳ����ģ�
					direction = Direction.R;
				}else if(meX < Control.chaseX && elements[Control.chaseY][Control.chaseX-1].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX--;
					}
					direction = Direction.L;
				}else if(meY > Control.chaseY && elements[Control.chaseY+1][Control.chaseX].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseY++;
					}
					direction = Direction.D;
				}else if(meY < Control.chaseY && elements[Control.chaseY-1][Control.chaseX].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseY--;
					}
					direction = Direction.U;
				}else{
					direction = Direction.U;
				}
				if(meX == Control.chaseX && meY == Control.chaseY){
					Game.getInstance().setCurrent(new GameOverObject());
				}
				//��������ڵȴ�����Ӧ����ô��û�б�Ҫ�����ˣ��ѻ�ͼ���ַŽ��ж���
				switch(direction){
				case U:
					g.drawImage(ImageSets.getImg(61), 
							Player.getInstance().getPaintX()-(meX-Control.chaseX)*Lib.boundsPerImg+20, Lib.gameHEIGHT/2-(meY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case D:
					g.drawImage(ImageSets.getImg(62), 
							Player.getInstance().getPaintX()-(meX-Control.chaseX)*Lib.boundsPerImg+20, Lib.gameHEIGHT/2-(meY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case L:
					g.drawImage(ImageSets.getImg(63), 
							Player.getInstance().getPaintX()-(meX-Control.chaseX)*Lib.boundsPerImg+20, Lib.gameHEIGHT/2-(meY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case R:
					g.drawImage(ImageSets.getImg(64), 
							Player.getInstance().getPaintX()-(meX-Control.chaseX)*Lib.boundsPerImg+20, Lib.gameHEIGHT/2-(meY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				default:
					g.drawImage(ImageSets.getImg(61), 
							Player.getInstance().getPaintX()-(meX-Control.chaseX)*Lib.boundsPerImg+20, Lib.gameHEIGHT/2-(meY-Control.chaseY)*Lib.boundsPerImg-20, null);
				}
		}
		
		
	}
	
	
}
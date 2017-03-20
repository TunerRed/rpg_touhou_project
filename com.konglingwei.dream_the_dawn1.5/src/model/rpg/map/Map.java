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
	 * playerX,playerY不画出来但是对player和map间相对定位有相当大帮助的元素
	 * 每次取数组时以playerX,playerY为标准取clip
	 * 两者的值以player在数组中的位置为数据，计算出应画的数组clip左上角为elements数组的第几行第几列
	 * 其初始化应由本地图的MapKind.DOOR所在的位置决定
	 */
	
	/* 设定二维数组地图时左上角位置,XY分别是横向、纵向长度*/
	public static int wait = 0,playerX = 6- Lib.clipX / 2 - 1, playerY = 6- Lib.clipY / 2 - 1;
	private MapObject[][] elements;
	
	/**追逐战开始的标志，人物死亡后要记得把这个地方的状态改变*/
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
	
	/**人物新进入地图时初始化位置，传入人物在elements中的位置，
	 * 改变选定数组clip的区域,
	 * 注意传入X,Y的是数组坐标位置，
	 * 改变的X,Y是面板坐标，两者刚好相反
	 * 这里没写好，不想改了
	 * @param playerX 数组行数
	 * @param playerY 数组列数
	 * */
	public void setLocation(int playerX,int playerY){
		Map.playerX = playerY - Lib.clipX / 2 - 1;
		Map.playerY = playerX - Lib.clipY / 2 - 1;
	}
	
	/**改变二维数组中某个位置的信息
	 * @param x 数组行数
	 * @param y 数组列数
	 * @param object 传入一个目标MapObject
	 * */
	public void edit(int x, int y, MapObject object) {
		elements[x][y] = object;
	}
	
	/**人物移动并防止数组越界
	 * @param dir 要移动的方向
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
	
	/**所在区域为事件时，显示物品信息
	 * @param g 画笔
	 * @param direction 人物移动方向
	 * */
	public void keyResponse(Graphics g, Direction direction) {
		//决定是否开始显示信息，当信息开始显示后这一部分应当不再起作用
		if (	!showInfo && Control.Z 
				&& ((elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.EVENT)
					||(elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.ACHIEVEMENT))
				&& elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getDirection() == direction
				&& eventInfoEnd && GameObject.checkTimeGap(100)) {
			eventInfo = elements[playerY + Lib.clipY / 2 + 1][playerX + Lib.clipX / 2 + 1].getInfo();
			showInfo = true;
		}
		//进行读取信息，并判断信息是否已经读完，当信息读完后，这一部分不再起作用
		else if (showInfo) {
			//强制转换Graphics2D设置不透明度
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(0.5f));
			g.setColor(Color.WHITE);
			g.setFont(Lib.regular);
			g.fillRect(Lib.gameWIDTH / 11, Lib.gameHEIGHT - Lib.gameWIDTH / 7, Lib.gameWIDTH - 2 * Lib.gameWIDTH / 11,
					Lib.gameHEIGHT / 7);
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(1.0f));
			g.setColor(Color.BLACK);
			g.drawString(eventInfo.get(eventInfoStep),
					Lib.gameWIDTH / 10, (int) (Lib.gameHEIGHT * (9.0 / 10)));
			//读取下一条信息
			if(Control.Z && GameObject.checkTimeGap(200)){
				eventInfoStep++;
				//全部读取完毕
				if(eventInfoStep == eventInfo.size()){
					eventInfoStep = 0;
					showInfo = false;
				}
			}
		}
	}
	
	/**人物不动时的画图方法
	 * @param g 画笔
	 * */
	public void paint(Graphics g) {
		for (int i = 0; i < Lib.clipY; i++)
			for (int j = 0; j < Lib.clipX; j++) {
				g.drawImage(ImageSets.getImg(elements[playerY + i][playerX + j]), Lib.boundsX + j * Lib.boundsPerImg,
						Lib.boundsY + i * Lib.boundsPerImg, null);
			}
		//追逐战过程
		if(startChase){
			chase(g);
		}
	}
	/**人物移动时的画图方法
	 * @param g 画笔
	 * @param direction 移动方向
	 * @param isRunning 是否在奔跑，若在奔跑则为true，否则为false
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
	
	/**检查人物上方元素是否有镜子，如果有，需要重画镜子
	 * @param g 画笔
	 * @param direction 人物现在的方向
	 * */
		public void checkMirror(Graphics g,Direction direction){
			//正下方
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
			//右上方
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2 + 3].getKind() == MapKind.MIRROR && direction == Direction.L){
				g.drawImage(ImageSets.getImg(46), Player.getInstance().getPaintX()+20+2*Lib.boundsPerImg,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
			//左上方
			else if(elements[playerY + Lib.clipY / 2 - 1 ][playerX + Lib.clipX / 2-1].getKind() == MapKind.MIRROR && direction == Direction.R){
				g.drawImage(ImageSets.getImg(47), Player.getInstance().getPaintX()+20-2*Lib.boundsPerImg,
						Lib.gameHEIGHT / 2+7-2*Lib.boundsPerImg, null);
			}
		}
	/**检查人物下方元素是否为书架，如果是，需要重画人物下半身
	 * @param g 画笔
	 * */
	public void checkShell(Graphics g){
		if(elements[playerY + Lib.clipY / 2 + 2 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.SHELL){
			g.drawImage(ImageSets.getImg(elements[playerY + Lib.clipY / 2 + 1 ][playerX + Lib.clipX / 2 + 1]), Player.getInstance().getPaintX()+20,
					Lib.gameHEIGHT / 2+7, null);
		}
	}
	/**检查所在地是否为Auto属性
	 * @param g 画笔
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
	/**获得制定位置的单个MapObject*/
	public MapObject getUnderfoot(){
		return elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1];
	}
	public MapObject getSomewhere(int x,int y){
		return elements[x][y];
	}
	/**下一步是不是门
	 * @param direction 人物移动的方向
	 * */
	public boolean isDoor(Direction direction){
		switch(direction){
			case U:
				if(elements[playerY + Lib.clipY / 2 ][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.DOOR 
					&&elements[playerY + Lib.clipY / 2 ][playerX + Lib.clipX / 2 + 1].canGo()){
					if(startChase){	//启动追逐战
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
	/**获得即将到达的位置的门
	 * @param direction 人物现在的方向
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
	 * 检验是否为可以达成成就的点
	 * @param direction 人物现在的方向
	 * */
	public boolean isAchievement(Direction direction){
		if(elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1].getKind() == MapKind.ACHIEVEMENT 
			&& direction == elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1].getDirection()){
			return true;
		}
		return false;
	}
	/**
	 * 编辑整个地图
	 * @param mapList 全部的地图对象
	 * */
	public void editMapList(ArrayList<Map> mapList){
		elements[playerY + Lib.clipY / 2 +1][playerX + Lib.clipX / 2 + 1].editMapList(mapList);
	}
	
	/*
	 * 此处以下全部为追逐战专用的方法
	 * 以chase为基本
	 * 可能有其他的方法
	 * 包括追逐的算法
	 * 门的处理和转换
	 * 等等
	 * */
	private static int meY ,meX ;
	public static int chaseReady = 0;
	private static int speed = 3,chaseWait = 0;
	public static boolean readyForDoor = false;
	private static MapObject chaseDoor;
	private Direction direction;
	/*
	 * 我方纵坐标meY
	 * 我方横坐标meX
	 * elements[meY][meX]
	 * 敌人纵坐标Control.chaseY
	 * 敌人横坐标Control.chaseX
	 * elements[Control.chaseY][Control.chaseX]
	 * */
	/**
	 * 追逐战更新静态变量
	 * */
	public static boolean initChase(){
		//每次更新坐标值
		meY=playerY + Lib.clipY / 2 + 1 ;
		meX=playerX + Lib.clipX / 2 + 1 ;
		//门的转换与等待
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
	 * 追逐战分析坐标拉近距离，并负责画图
	 * @param g 画笔
	 * */
	public void chase(Graphics g){
		if(!initChase()){
			chaseWait++;
				if(meX > Control.chaseX && elements[Control.chaseY][Control.chaseX+1].canGo()){
					//判断该不该移动
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX++;
					}
					//无论是否移动，方向（趋势还是要表现出来的）
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
				//如果现在在等待门相应，那么久没有必要画敌人，把画图部分放进判断里
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
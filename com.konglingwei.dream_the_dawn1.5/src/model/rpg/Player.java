package model.rpg;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import view.Lib;
/**
 * �����࣬�����������õ���Ʒ
 * */
public class Player {
	/**ʹ�þ�̬Player��ʹ��Playerʱֻʹ�ô˶���*/
	private static Player player = new Player();
	//����ͼƬ���������̫�����õ�wait
	private int imgChange=0,walkWait=0,runWait=0;
	//�������������
	private int x,y;
	//�����״̬
	private Direction direction = Direction.R;
	private ArrayList<Item> items = new ArrayList<Item>();
	//ͼƬ���飬��������ͼ���ֳ�32��Сͼ
	private BufferedImage walk,run;
	private BufferedImage[] walku = new BufferedImage[4];
	private BufferedImage[] walkd = new BufferedImage[4];
	private BufferedImage[] walkl = new BufferedImage[4];
	private BufferedImage[] walkr = new BufferedImage[4];
	private BufferedImage[] runu = new BufferedImage[4];
	private BufferedImage[] rund = new BufferedImage[4];
	private BufferedImage[] runl = new BufferedImage[4];
	private BufferedImage[] runr = new BufferedImage[4];
		
	
	/**���ؾ�̬player��ȷ������������Ķ���ͬһ��Player����*/
	public static Player getInstance(){
		return player;
	}
	/**
	 * ���췽����
	 * ��ȡͼƬ��ͬʱ��ζ����������ܱ��浵д���ļ���
	 * */
	public Player(){
		try {
			walk = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("source/rpg/player/walk.png"));
			run = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("source/rpg/player/run.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0;i<walku.length;i++){
			walku[i] = walk.getSubimage(i*(walk.getWidth()/4), 3*(walk.getHeight()/4), walk.getWidth()/4, walk.getHeight()/4);
		}
		for(int i = 0;i<walkd.length;i++){
			walkd[i] = walk.getSubimage(i*(walk.getWidth()/4), 0, walk.getWidth()/4, walk.getHeight()/4);
		}
		for(int i = 0;i<walkl.length;i++){
			walkl[i] = walk.getSubimage(i*(walk.getWidth()/4), walk.getHeight()/4, walk.getWidth()/4, walk.getHeight()/4);
		}
		for(int i = 0;i<walkr.length;i++){
			walkr[i] = walk.getSubimage(i*(walk.getWidth()/4), walk.getHeight()/2, walk.getWidth()/4, walk.getHeight()/4);
		}
		
		for(int i = 0;i<runu.length;i++){
			runu[i] = run.getSubimage(i*(run.getWidth()/4), 3*(run.getHeight()/4), run.getWidth()/4, run.getHeight()/4);
		}
		for(int i = 0;i<rund.length;i++){
			rund[i] = run.getSubimage(i*(run.getWidth()/4), 0, run.getWidth()/4, run.getHeight()/4);
		}
		for(int i = 0;i<runl.length;i++){
			runl[i] = run.getSubimage(i*(run.getWidth()/4), run.getHeight()/4, run.getWidth()/4, run.getHeight()/4);
		}
		for(int i = 0;i<runr.length;i++){
			runr[i] = run.getSubimage(i*(run.getWidth()/4), run.getHeight()/2, run.getWidth()/4, run.getHeight()/4);
		}
		x = Lib.gameWIDTH/2-walk.getWidth()/8;
		y = Lib.gameHEIGHT/2-walk.getHeight()/8;
	}
	/**
	 * ����Item
	 * @param items Ҫ���µ���Ʒ��
	 * */
	public void updateItems(ArrayList<Item> items){
		this.items = items;
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	public void addItem(Item item){
		items.add(item);
	}
	/**��������removeArrayList����Ӧ����Ŀ*/
	public void removeItem(int i){
		items.remove(i);
	}
	/**Map�滭ʱȡ�滭��������Ϊ��(x,Lib.gameHEIGHT/2)Ϊ���Ͻǣ��߳�ΪLib.boundsPerImg�ķ���*/
	public int getPaintX(){
		return x;
	}
	
	/**�����ƶ�ʱ�Ļ��ƣ������ǲ����ڱ���
	 *   @param g ����
	 *   @param isRun �Ƿ��ڱ��ܣ�trueΪ�ܶ���falseΪ�߶�
	 *   @param playerDirection ����ķ���
	 * */
	public void draw(Graphics g,boolean isRun,Direction playerDirection) {
		direction = playerDirection;
		if(!isRun){
			if(direction==Direction.U)
				g.drawImage(walku[imgChange], x, y, null);
			if(direction==Direction.D)
				g.drawImage(walkd[imgChange], x, y, null);
			if(direction==Direction.L)
				g.drawImage(walkl[imgChange], x, y, null);
			if(direction==Direction.R)
				g.drawImage(walkr[imgChange], x, y, null);
			walkWait++;
			if(walkWait==3){
				walkWait = 0;
				imgChange++;
			}
		}
		else{
			if(direction==Direction.U)
				g.drawImage(runu[imgChange], x, y, null);
			if(direction==Direction.D)
				g.drawImage(rund[imgChange], x, y, null);
			if(direction==Direction.L)
				g.drawImage(runl[imgChange], x, y, null);
			if(direction==Direction.R)
				g.drawImage(runr[imgChange], x, y, null);
			runWait++;
			if(runWait==2){
				runWait = 0;
				imgChange++;	
			}
		}
		if(imgChange==4)
			imgChange = 0;
	}
	/**���ﾲֹ����ʱ�Ļ���*/
	public void draw(Graphics g, Direction direction) {
		if(direction==Direction.U)
			g.drawImage(walku[0], x, y, null);
		if(direction==Direction.D)
			g.drawImage(walkd[0], x, y, null);
		if(direction==Direction.L)
			g.drawImage(walkl[0], x, y, null);
		if(direction==Direction.R)
			g.drawImage(walkr[0], x, y, null);
	}
	
	public Direction getDirection(){
		return direction;
	}
	public void clearDirection(){
		direction = Direction.R;
	}
}

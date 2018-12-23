package util;

import engine.AudioPlayer;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public final class Sprites {

	private Sprites() {}

	public static BufferedImage RedKnightSprite;
	public static BufferedImage BluKnightSprite;
	public static BufferedImage TowerSprite;
	public static AudioPlayer ClassicMusic;
	public static BufferedImage Lava;

	public static BufferedImage Wall1;
	public static BufferedImage Wall2;
	public static BufferedImage Wall3;
	public static BufferedImage Wall4;
	public static BufferedImage Wall5;
	public static BufferedImage Wall6;

	public static BufferedImage Kenobi;
	public static BufferedImage Grievous;
	public static BufferedImage starwars;
	public static AudioPlayer starwarsMusic;

	public static BufferedImage Frodo;
	public static BufferedImage Gollum;
	public static BufferedImage LotrBack;
	public static AudioPlayer LotrMusic;

	public static BufferedImage Scooby;
	public static BufferedImage Monster;
	public static BufferedImage ScoobyBack;
	public static AudioPlayer ScoobyMusic;

	public static BufferedImage Pig;
	public static BufferedImage Wolf;
	public static BufferedImage PigBack;
	public static AudioPlayer PigMusic;

	public static BufferedImage ClassicBack;
	public static BufferedImage ButtonIcon;

	public static BufferedImage Coin;
	public static BufferedImage Diamond;
	public static BufferedImage Chest;

	public static BufferedImage MenuScreen;

	public static AudioPlayer placeKnightSound;
	public static AudioPlayer removeSound;
	public static AudioPlayer victorySound;
	public static AudioPlayer MenuBack;





	static {
		try {
			RedKnightSprite = ImageIO.read(Sprites.class.getResourceAsStream("/red_knight.png"));
			BluKnightSprite = ImageIO.read(Sprites.class.getResourceAsStream("/blu_knight.png"));
			Wall1 =  ImageIO.read(Sprites.class.getResourceAsStream("/Wall1.PNG"));
			Wall2 =  ImageIO.read(Sprites.class.getResourceAsStream("/Wall2.PNG"));
			Wall3 =  ImageIO.read(Sprites.class.getResourceAsStream("/Wall3.PNG"));
			Wall4 =  ImageIO.read(Sprites.class.getResourceAsStream("/Wall4.PNG"));
			Wall5 =  ImageIO.read(Sprites.class.getResourceAsStream("/Wall5.PNG"));
			Wall6 =  ImageIO.read(Sprites.class.getResourceAsStream("/Wall6.PNG"));

			Kenobi = ImageIO.read(Sprites.class.getResourceAsStream("/starwars/kenobi.png"));
			Grievous = ImageIO.read(Sprites.class.getResourceAsStream("/starwars/grievous.png"));
			starwars = ImageIO.read(Sprites.class.getResourceAsStream("/starwars/battle.png"));

			Scooby = ImageIO.read(Sprites.class.getResourceAsStream("/scoobydoo/scooby.png"));
			Monster = ImageIO.read(Sprites.class.getResourceAsStream("/scoobydoo/zom.png"));
			ScoobyBack = ImageIO.read(Sprites.class.getResourceAsStream("/scoobydoo/dungeon.png"));

			Frodo = ImageIO.read(Sprites.class.getResourceAsStream("/lotr/Frodo.png"));
			Gollum = ImageIO.read(Sprites.class.getResourceAsStream("/lotr/gollum.png"));
			LotrBack = ImageIO.read(Sprites.class.getResourceAsStream("/lotr/lotr.jpg"));

			Pig = ImageIO.read(Sprites.class.getResourceAsStream("/threelittlepigs/pig.png"));
			Wolf = ImageIO.read(Sprites.class.getResourceAsStream("/threelittlepigs/wolf.png"));
			PigBack = ImageIO.read(Sprites.class.getResourceAsStream("/threelittlepigs/pigback.jpg"));


			TowerSprite = ImageIO.read(Sprites.class.getResourceAsStream("/tower.png"));
			Lava = ImageIO.read(Sprites.class.getResourceAsStream("/water.png"));
			ClassicBack = ImageIO.read(Sprites.class.getResourceAsStream("/classicgame/classicback.jpg"));
			ButtonIcon = ImageIO.read(Sprites.class.getResourceAsStream("/but.png"));

			Coin = ImageIO.read(Sprites.class.getResourceAsStream("/coin.png"));
			Diamond = ImageIO.read(Sprites.class.getResourceAsStream("/diamond.png"));

			starwarsMusic =new AudioPlayer("/starwars/starwars.wav");
			ClassicMusic= new AudioPlayer("/classicgame/classic.wav");

			LotrMusic= new AudioPlayer("/lotr/ring.wav");
			ScoobyMusic= new AudioPlayer("/scoobydoo/Scooby.wav");
			PigMusic= new AudioPlayer("/threelittlepigs/pigs.wav");
			placeKnightSound= new AudioPlayer("/sound_effects/putKnight.wav");
			removeSound = new AudioPlayer("/sound_effects/maledeath2.wav");
			victorySound = new AudioPlayer("/sound_effects/victory.wav");
			MenuBack = new AudioPlayer("/classicgame/wolf.wav");
			MenuScreen = ImageIO.read(Sprites.class.getResourceAsStream("/wiki.jpg"));
			Chest = ImageIO.read(Sprites.class.getResourceAsStream("/chest.png"));

		} catch(IOException ex) {
			JOptionPane.showMessageDialog(null, "Asset missing!\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}


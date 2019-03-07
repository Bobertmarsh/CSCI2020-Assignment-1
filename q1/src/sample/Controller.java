package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Controller {

	public ImageView card1, card2, card3;
	//runs on startup

	//Randomly generates a card
	void pickCard(ImageView card){
		Random rng = new Random();
		//turn random number into string
		String cardNum = Integer.toString(1 + rng.nextInt(54));
		String path = "image/cards/" + cardNum + ".png";
		System.out.println(path);
		//Set card image based on rng
		card.setImage(new Image(path));
	}

	public void initialize()
	{
		//pick 3 cards
		pickCard(card1);
		pickCard(card2);
		pickCard(card3);
	}
}

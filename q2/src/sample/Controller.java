package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
	public TextField txtAmount, txtYears, txtInterest, txtFuture;
	public Button btnCalculate;

	public void handleButton()
	{
		//futureValue = investmentAmount * (1 + monthlyInterestRate)^(years*12)
		double investmentAmount = Double.parseDouble(txtAmount.getText());
		//Interest divided by 1200 because it needs to be divided by 12 for monthly from yearly and 100 for percentage
		double monthlyInterestRate = Double.parseDouble(txtInterest.getText()) / 1200;
		double years = Double.parseDouble(txtYears.getText());
		double futureValue = investmentAmount * Math.pow((1 + monthlyInterestRate),(years * 12));
		txtFuture.setText(String.format("%.2f",futureValue));   //Formnats string to 2 decimal places
	}
}

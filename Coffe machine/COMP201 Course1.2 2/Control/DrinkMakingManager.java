package Control;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.simulate.Hoppers;
import ac.liv.csc.comp201.simulate.Cup;

public class DrinkMakingManager {
	
	private static final String[] BLACK_COFFEE= new String[] {"101","5101","6101"};
	private static final String[] BLACK_COFFEE_WITH_SUGAR= new String[] {"102","5102","6102"};
	private static final String[] WHITE_COFFEE= new String[] {"201","5201","6201"};
	private static final String[] WHITE_COFFEE_WITH_SUGAR= new String[] {"202","5202","6202"};
	private static final String[] HOT_CHOCOLATE= new String[] {"300","5300","6303"};
	
	
	private double[] coffee= new double[] {2.00, 2.64 , 3.29};
	private double[] milk= new double[] {3.00, 3.97 , 4.94};
	private double[] sugar= new double[] {5.00, 6.61 , 8.23};
	private double[] chocolate = new double [] {28.00, 37.05, 46.11};
	private boolean Heater=true;
	private double Tempreture = 0;
	
	private IMachine machine;	
	public DrinkMakingManager(IMachine machine) {
		this.machine = machine; 

	}
	
	
	/*.................................
	 * Valid the ingredients stock;
	 * check the stock vale
	 **/
	
	public boolean validIngredients(String keypadInput) {		
		for(int i = 0; i <3; i++) {
			if(keypadInput.equals(BLACK_COFFEE[i])&& machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE) > coffee[i]) {
				return true;			
			}else if(keypadInput.equals(BLACK_COFFEE_WITH_SUGAR[i])&& machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE) > coffee[i]
					&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>sugar[i]) {
				return true;				
			}else if(keypadInput.equals(WHITE_COFFEE[i])&& machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE) > coffee[i]
					&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>milk[i]) {
				return true;				
			}else if(keypadInput.equals(WHITE_COFFEE_WITH_SUGAR[i])&& machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE) > coffee[i]
					&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>milk[i]
					&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>sugar[i]) {
				return true;				
			}else if(keypadInput.equals(HOT_CHOCOLATE[i])&& machine.getHoppers().getHopperLevelsGrams(Hoppers.CHOCOLATE) > chocolate[i]) {
				return true;	
			}				
		}	
		return false;
	}
	
	
	/*.................................
	 * according to the input drinkBumber
	 * get the ingredients to make the drink	  
	 **/
	
	public double[] getIngredients(String keypadInput) {
		Cup cup = machine.getCup();
		double[]ingredients= {0,0,0,0};
		for(int i = 0; i <3; i++) {		
			if(keypadInput.equals(BLACK_COFFEE[i])) {
				ingredients[0]=coffee[i];			
			}else if(keypadInput.equals(BLACK_COFFEE_WITH_SUGAR[i])){
				ingredients[0]=coffee[i];
				ingredients[1]=sugar[i];
			}else if(keypadInput.equals(WHITE_COFFEE[i])){
				ingredients[0]=coffee[i];
				ingredients[2]=milk[i];
			}else if(keypadInput.equals(WHITE_COFFEE_WITH_SUGAR[i])){
				ingredients[0]=coffee[i];
				ingredients[1]=sugar[i];
				ingredients[2]=milk[i];
			}else if(keypadInput.equals(HOT_CHOCOLATE[i])) {
				ingredients[3]=chocolate[i];			
			}
		}
	    return ingredients;
	}
	
	
	/*.................................
	 * input the ingredients to make the drink	  
	 **/
	
	public void InputIngredients(double[]getIngredients) {
		Cup cup = machine.getCup();	
		if(cup.getCoffeeGrams()< getIngredients[0]) {
			machine.getHoppers().setHopperOn(Hoppers.COFFEE);
		}else {
				machine.getHoppers().setHopperOff(Hoppers.COFFEE);
			}
	    if(cup.getSugarGrams() < getIngredients[1]) {		
				machine.getHoppers().setHopperOn(Hoppers.SUGAR);
	    }else{   				
				machine.getHoppers().setHopperOff(Hoppers.SUGAR);
			}
	    if(cup.getMilkGrams()< getIngredients[2]) {				
				machine.getHoppers().setHopperOn(Hoppers.MILK);
	    }else{			
				machine.getHoppers().setHopperOff(Hoppers.MILK);
			}
		if( cup.getChocolateGrams() < getIngredients[3]) {
			machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
		}else {
				machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
			}	
	    }
	
		
	/*.................................
	 * according to the input first drinkBumber
	 * get the cup size liter to make the drink	  
	 **/
	
	public double getcupLiter(int[]drinkNum) {
		double  cupLiter= 0;
		if(drinkNum[0] == 1|drinkNum[0] == 2|drinkNum[0] == 3) {
			cupLiter=0.34;
		}					
		if(drinkNum[0] == 5) {
			cupLiter=0.45;
		}
		if(drinkNum[0] == 6) {
			cupLiter=0.56;
		}		
		return cupLiter;	
	}
	
	
	/*.................................
	 * according to the input drinkBumber
	 * get drink making temperature to make the drink	  
	 **/
	
	public double getTempreture(String keypadInput) {
		for(int j = 0; j <3; j++) {
			if(keypadInput.equals(BLACK_COFFEE[j])|keypadInput.equals(BLACK_COFFEE_WITH_SUGAR[j])
					|keypadInput.equals(WHITE_COFFEE[j])|keypadInput.equals(WHITE_COFFEE_WITH_SUGAR[j])) {
				Tempreture=95.9;			
			}else if(keypadInput.equals(HOT_CHOCOLATE[j])){
				Tempreture=90.0;
		}
		}	
		return Tempreture;
	
	}
	
	
	/*.................................
	 * input water to make the drink	  
	 **/
	
	public void InputWater(double getTemperature,double getcupLiter) {
		Cup cup = machine.getCup();
		WaterHeaterManager water = new WaterHeaterManager(machine);			
		float watertemperature = machine.getWaterHeater().getTemperatureDegreesC();
		machine.getCoinHandler().lockCoinHandler();
		
		if(Heater==true) {	
			if(watertemperature<=getTemperature) {
				water.temperature(Heater);	
				machine.getWaterHeater().setHeaterOn();				
			}else{
				machine.getWaterHeater().setHotWaterTap(true);
			}
		}
		
		if(cup.getWaterLevelLitres()>0 &&cup.getWaterLevelLitres()< getcupLiter*0.2) {
			machine.getWaterHeater().setHotWaterTap(true);
			if(machine.getWaterHeater().getTemperatureDegreesC()>=getTemperature) {
				machine.getWaterHeater().setHotWaterTap(true);
			}else {
				machine.getWaterHeater().setHotWaterTap(false);
			}
		}
		if(cup.getWaterLevelLitres()>getcupLiter*0.2&&cup.getWaterLevelLitres()<getcupLiter) {
			if(cup.getTemperatureInC()>=80) {			
				machine.getWaterHeater().setColdWaterTap(true);
				machine.getWaterHeater().setHotWaterTap(false);
			}else {
				machine.getWaterHeater().setHeaterOn();
				machine.getWaterHeater().setColdWaterTap(false);
				machine.getWaterHeater().setHotWaterTap(true);
			}
		}
	
		if(cup.getWaterLevelLitres()>=getcupLiter) {
			machine.getWaterHeater().setHotWaterTap(false);
			machine.getWaterHeater().setColdWaterTap(false);
			machine.getCoinHandler().unlockCoinHandler();
			
			Heater = false;
		
		}
	}
	}
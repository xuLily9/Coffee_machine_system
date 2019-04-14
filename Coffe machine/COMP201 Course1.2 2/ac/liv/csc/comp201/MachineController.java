package ac.liv.csc.comp201;


import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import Control.CoinHandlingManager;
import Control.DrinkMakingManager;
import Control.ValidBlalanceManager;
import Control.WaterHeaterManager;
import ac.liv.csc.comp201.model.IMachine;

public class MachineController  extends Thread implements IMachineController {
	
	private boolean running=true;
	
	private IMachine machine;
	private CoinHandlingManager coin;
	private ValidBlalanceManager balance;
	
	private static final String version="1.22";
	private StringBuffer drinkCode = new StringBuffer();
	private int []drinkNum = {-1,-1,-1,-1};
	private double[]getIngredients = {0,0,0,0};
	private double getTemperature;
	private double getcupLiter;
	private String keyNum = "";
	private int pressLength = 0;
	private boolean waterHeater = true;
	private boolean locked = false;
	
	public void startController(IMachine machine) {
		this.machine=machine;				
		machine.getKeyPad().setCaption(0, "0");
		machine.getKeyPad().setCaption(1, "1");
		machine.getKeyPad().setCaption(2, "2");
		machine.getKeyPad().setCaption(3, "3");
		machine.getKeyPad().setCaption(4, "4");
		machine.getKeyPad().setCaption(5, "5");
		machine.getKeyPad().setCaption(6, "6");
		machine.getKeyPad().setCaption(7, "7");
		machine.getKeyPad().setCaption(8, "8");
		machine.getKeyPad().setCaption(9, "Return change");
		super.start();
	}
	
	
	public MachineController() {
					
	}
	
	
	private synchronized void runStateMachine() {
		/*.................................
		 * create new object 
		 **/
		
		WaterHeaterManager waterTemperature = new WaterHeaterManager(machine);
		Cup cup = machine.getCup();	
		coin = new CoinHandlingManager(machine);
		balance = new ValidBlalanceManager(machine);
		DrinkMakingManager drinkmaking = new DrinkMakingManager(machine);
		
		/*.................................
		 * start heating water, 
		 * control heater temperature before making drink
		 **/	
		
		waterTemperature.temperature(waterHeater);
		
		/*.................................
		 * get the inserted coin code
		 * calculate the balance 
		 **/
		
		String coinCode=machine.getCoinHandler().getCoinKeyCode();
		this.insertCoins(coinCode);
		/*.................................
		 * Input drink number to select drinks
		 * valid the drink making condition 
		 **/
		
		int keyCode=machine.getKeyPad().getNextKeyCode();
        if(locked== false) {
			if (keyCode != -1) {		
				if (keyCode == 9) {
					coin.change();		
					System.out.print("Return changes successfully!");	
					machine.getDisplay().setTextString("Return changes successfully!");
				}else {	
					
					if(pressLength < drinkNum.length) {					
						drinkNum[pressLength++] = keyCode;
					}
					int keyLen = 3;
					if (drinkNum[0] == 5|drinkNum[0] == 6) { //oder medium cup or large cup					
						keyLen = 4;
					}
					machine.getDisplay().setTextString("Press number.");
					
					if (pressLength >= keyLen) {					
						for (int i = 0; i < keyLen; i++) {
							drinkCode.append(drinkNum[i]);
						}
						keyNum = drinkCode.toString();						
						System.out.println("Drink order number is "+ keyNum);
						
						
						boolean balanceValid = balance.BalanceAvailable(keyNum);
						boolean produceValid = drinkmaking.validIngredients(keyNum); 
						boolean drinkNumberValid = balance.PressValid(keyNum);
						
						getIngredients = drinkmaking.getIngredients(keyNum);
						getTemperature = drinkmaking.getTempreture(keyNum);
					    getcupLiter = drinkmaking.getcupLiter(drinkNum);
						
						this.validConditions(drinkNumberValid, balanceValid, produceValid);
					}
				}
			}
        }
        
        /*.................................
		 * if the cup is not null
		 * input water and ingredients to make drinks
		 **/
        
		if(cup != null ) {			
			drinkmaking.InputIngredients(getIngredients);		
			drinkmaking.InputWater(getTemperature, getcupLiter);
		}
		
	}
	
	/*.................................
	 * get the inserted coin code
	 * calculate the balance 
	 **/
	
	public void insertCoins(String coinCode) {			
		if (coinCode!=null) {
			System.out.println("Got coin code: "+coinCode);
			machine.getDisplay().setTextString("Got coin code: "+coinCode);
			double amount = coin.calculationBalance(coinCode);
			machine.getDisplay().setTextString("Now coin: " + (amount / 100));
		}
	}
	
	/*.............................................................
	 * valid input drink number, if invalid delete and insert again
	 * if the balance and ingredient are both enough
	 * decide the drink size
	 **/
	
	public void validConditions(boolean drinkNumberValid,boolean balanceValid,boolean produceValid) {	
		if(drinkNumberValid) {
			if(balanceValid) {
		    	machine.getDisplay().setTextString("Enough balance, Select drinks!");
		    	balance.BalanceDeduct(keyNum);
		    	if(produceValid) {
		    		if(drinkNum[0] == 1|drinkNum[0] == 2|drinkNum[0] == 3) {
						machine.vendCup(Cup.SMALL_CUP);
					}
					if(drinkNum[0] == 5) {
						machine.vendCup(Cup.MEDIUM_CUP);
					}
					if(drinkNum[0] == 6) {
						machine.vendCup(Cup.LARGE_CUP);
					}
					machine.getDisplay().setTextString("Start making drink!");
		    	}else {
					machine.getDisplay().setTextString("Not enough Ingredient!");
				}
			}else {
				machine.getDisplay().setTextString("Paying first before selecting!");
			}
		}else {
			machine.getDisplay().setTextString("invalid input, input again");
		}		
		drinkCode.delete(0, drinkCode.length());
		pressLength = 0; 
		keyNum = "";				
	}	
	
	
	
	public void run() {
		// Controlling thread for coffee machine
		int counter=1;
		while (running) {
			//machine.getDisplay().setTextString("Running drink machine controller "+counter);
			counter++;
			try {
				Thread.sleep(10);		// Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
		}		
	}
	
	public void updateController() {
		//runStateMachine();
	}
	
	public void stopController() {
		running=false;
	}


	

}

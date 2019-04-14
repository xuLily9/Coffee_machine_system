package Control;

import ac.liv.csc.comp201.model.IMachine;

public class ValidBlalanceManager{
	private IMachine machine;
	private final static int[] smallPrice = new int[] {120, 130, 120, 130, 110};
	private final static String[] smallDrink = new String[] {"101","102","201","201","300"};
	
	
	private final static int[] mediumPrice = new int[]{140, 150, 140, 150, 130};
	private final static String[] mediumDrink = new String[] {"5101","5102","5201","5201","5300"};
    
	private final static int[] largePrice = new int[] {145, 155, 145, 155, 135};
	private final static String[] largeDrink = new String[] {"6101","6102","6201","6201","6300"};
    
	public ValidBlalanceManager(IMachine machine) {
		this.machine = machine;	
	}
	
	
	/*.................................
	 *check the balance level
	 *make sure your balance is greater than drink price
	 **/
	
	public boolean BalanceAvailable(String keypadInput) {		
			for (int i = 0;i < 5; i++) {
				if(keypadInput.equals(smallDrink[i]) && machine.getBalance() >= smallPrice[i]) {
					return true;				
				}else if(keypadInput.equals(mediumDrink[i]) && machine.getBalance() >= mediumPrice[i]) {
					return true;
					
				}else if(keypadInput.equals(largeDrink[i]) && machine.getBalance() >= largePrice[i]) {			
					return true;
				}	
				
			}
			return false;		
		}
	
	/*.................................
	 * Calculate the charge 
	 * reduce the balance
	 **/
	
	public void BalanceDeduct(String keypadInput) {
		for (int i = 0;i < 5; i++) {
			if(keypadInput.equals(smallDrink[i]) && machine.getBalance() >= smallPrice[i]) {			
				machine.setBalance(machine.getBalance() - smallPrice[i]);
				
			}else if(keypadInput.equals(mediumDrink[i]) && machine.getBalance() >= mediumPrice[i]) {
				machine.setBalance(machine.getBalance() - mediumPrice[i]);
				
			}else if(keypadInput.equals(largeDrink[i]) && machine.getBalance() >= largePrice[i]) {
				machine.setBalance(machine.getBalance() - largePrice[i]);
				
			}			
		}			
		String balacne = String.valueOf(machine.getBalance());
		machine.getDisplay().setTextString(balacne);
	}
	
	
	/*.................................
	 * valid the press drink number
	 **/
	
	public boolean PressValid(String keypadInput) {
		for(int i = 0; i < 6; i++) {
			if (keypadInput.equals(smallDrink[i])|keypadInput.equals(mediumDrink[i])|keypadInput.equals(largeDrink[i]) ) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}	
}

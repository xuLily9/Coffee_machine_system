package Control;


import ac.liv.csc.comp201.model.IMachine;
public class CoinHandlingManager {
	private IMachine machine;   		
    private final static int[] value = new int[]{1, 5, 10, 20, 50, 100};
    private final static String coinCodes[]={"ab","ac","ba","bc","bd","ef","zz" };
	public CoinHandlingManager(IMachine machine) {
		this.machine = machine;
	}
	
/*.................................
 * Valid the input item;
 * calculate the insert coins and 
 * update the balance 
 **/		
	public double calculationBalance(String coinCode) {	
		 int amount= machine.getBalance();
	     if (coinCode.equals("ab")) {
		       amount += 1.00;
		   } else if (coinCode.equals("ac")) {
		       amount += 5.00;
		   } else if (coinCode.equals("ba")) {
		       amount += 10.00; 
		   } else if (coinCode.equals("bc")) {
		       amount += 20.00; 
		   } else if (coinCode.equals("bd")) {
		       amount += 50.00; 
		   } else if (coinCode.equals("ef")) {
		       amount += 100.00; 
		       
		   }  else if (!coinCode.equals("ab") && !coinCode.equals("ac") && !coinCode.equals("ba")
		             && !coinCode.equals("bc") && !coinCode.equals("ef") &&
		            !coinCode.equals("zz")) {
			   System.out.println("Invalid coin insert");
		   }
	     System.out.println("Balance: "+amount);
	     machine.setBalance(amount);
	     machine.getDisplay().setTextString("Balance: "+machine.getBalance());
		    
	     return amount;
	   
		}
	/*................................
	 * Return the change and 
	 * show the current credit**/
	public void change() {
		int ChangeOut = machine.getBalance();	
		for(int i = value.length-1;i>=0; i--) {		
			while(ChangeOut/value[i]>0 && machine.getCoinHandler().coinAvailable(coinCodes[i])) {
				machine.getCoinHandler().dispenseCoin(coinCodes[i]);
				ChangeOut = ChangeOut-value[i];
				System.out.println("Current credit: "+ChangeOut);
				machine.setBalance(ChangeOut);
				
			}
		}
	}
}


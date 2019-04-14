package Control;

import ac.liv.csc.comp201.model.IMachine;

public class WaterHeaterManager {
	private IMachine machine;
	private boolean HeaterController;
	
	public  WaterHeaterManager(IMachine machine) {
		this .machine= machine;				
	}
	
	/*............................................
	 * Start heating the water
	 * control the temperature between 80-96
	 * safety consideration: not allow to reach 100C**/	
	
	public void WaterTemperature() {
		float Water_Temperature = machine.getWaterHeater().getTemperatureDegreesC();	
		if(Water_Temperature>=96){
            machine.getWaterHeater().setHeaterOff();
        }else if(Water_Temperature <80){
            machine.getWaterHeater().setHeaterOn();
        }else if(Water_Temperature >=98&&machine.getWaterHeater().getHeaterOnStatus()==true){
            machine.shutMachineDown();
        }
    }
		
	
	/*............................................
	 * If the heater on and off are lose control
	 * machine shut down 
	 * display error message **/
	
	public void loseControll() {
		HeaterController = machine.getWaterHeater().getHeaterOnStatus();
		
		if(HeaterController = false) {
			if(HeaterController = true) {
				machine.shutMachineDown();
				String text="Water heater lost controll.Machine is going tp shut down.";
				machine.getDisplay().setTextString(text);
			}
		}
	}
	
	
	/*.............................................
	 * if heater is true then start
	 * control the temperature **/
	
	public void temperature(boolean waterHeater) {
		if(waterHeater==true) {
			this.WaterTemperature();
			this.loseControll();
		}
				
}
	
	
}
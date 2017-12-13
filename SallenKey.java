import java.util.ArrayList;


public class SallenKey {
	/**
	 * list of all the resistors in kit provided
	 * list of all the capacitors in the kit provided
	 */
	private static double[] resistors = {10000,12000,15000,18000,22000,27000,33000,39000,47000,56000,68000,82000,
			100000,120000,150000,180000,220000,270000,330000,390000,470000,560000,680000,820000,1000000};
	private static double[] capacitors = {.001*Math.pow(10, -6),.0047*Math.pow(10, -6),.01*Math.pow(10, -6),.047*Math.pow(10, -6),.1*Math.pow(10, -6),
			4.7*Math.pow(10, -10),.002*Math.pow(10, -6),.02*Math.pow(10, -6),1*Math.pow(10, -6),4.7*Math.pow(10, -6),10*Math.pow(10, -6),
			22*Math.pow(10, -6),47*Math.pow(10, -6),100*Math.pow(10, -6),220*Math.pow(10, -6),1000*Math.pow(10, -6),4.7*Math.pow(10, -12),10*Math.pow(10, -12),
			47*Math.pow(10, -12),100*Math.pow(10, -12),220*Math.pow(10, -12),330*Math.pow(10, -12)};
	
	/**
	 * method to calculate the actual frequecy
	 */
	public static double calcFreq(double r, double c, double resistorRatio, double capacitorRatio) {
		return 1/(2*Math.PI*r*c*Math.sqrt(resistorRatio*capacitorRatio));
	}
	
	/**
	 * method to calculate the low pass Q point
	 */
	public static double calcQLow(double resistorRatio, double capacitorRatio) {
		return Math.sqrt(resistorRatio*capacitorRatio)/(resistorRatio + 1);
	}
	
	/**
	 * method to calculate the high pass Q point
	 */
	public static double calcQHigh(double resistorRatio, double capacitorRatio) {
		return Math.sqrt(resistorRatio*capacitorRatio)/(capacitorRatio + 1);
	}
	
	/**
	 * method to calculate the low pass resistor and capacitor values
	 */
	public static ArrayList<Double> lowPassButterworth(double breakFreq) {
		double resistorRatio,capacitorRatio,actFreq,actQ,r,c,printQ = 0,printFreq = 0;
		ArrayList<Double> combo = new ArrayList<Double>();
		double currentFreqDiff = 100;
		double currentQDiff = 100;
		double goalFreq = breakFreq;
		double goalQ = Math.sqrt(2)/2;
		for(double cap : capacitors) {
			for(double cap2 : capacitors) {
				if(cap >= cap2) {
					capacitorRatio = cap/cap2;
					c = cap2;
				}
				else {
					capacitorRatio = cap2/cap;
					c = cap;
				}
				for(double r1 : resistors) {
					for(double r2 : resistors) {
						if(r1 >= r2) {
							resistorRatio = r1/r2;
							r = r2;
						}
						else {
							resistorRatio = r2/r1;
							r = r1;
						}
						actFreq = calcFreq(r,c,resistorRatio,capacitorRatio);
						actQ = calcQLow(resistorRatio,capacitorRatio);
						if(Math.abs(actFreq - goalFreq) + (breakFreq*Math.abs(actQ - goalQ)) <= currentFreqDiff + (breakFreq*currentQDiff)) {
							currentFreqDiff = Math.abs(actFreq - goalFreq);
							currentQDiff = Math.abs(actQ - goalQ);
							combo.clear();
							combo.add(r1);
							combo.add(r2);
							combo.add(cap);
							combo.add(cap2);
							printQ = actQ;
							printFreq = actFreq;
						}
					}
				}
			}
		}
		System.out.println("Q  =  " + printQ);
		System.out.println("Break Frequency  =  " + printFreq);
		System.out.print("[r1, r2, cap1, cap2]  =  ");
		return combo;
	}
	
	/**
	 * method to calculate the high pass resistor and capacitor values
	 */
	public static ArrayList<Double> highPassButterworth(double breakFreq) {
		double resistorRatio,capacitorRatio,actFreq,actQ,r,c,printQ = 0,printFreq = 0;
		ArrayList<Double> combo = new ArrayList<Double>();
		double currentFreqDiff = 100;
		double currentQDiff = 100;
		double goalFreq = breakFreq;
		double goalQ = Math.sqrt(2)/2;
		for(double cap : capacitors) {
			for(double cap2 : capacitors) {
				if(cap >= cap2) {
					capacitorRatio = cap/cap2;
					c = cap2;
				}
				else {
					capacitorRatio = cap2/cap;
					c = cap;
				}
				for(double r1 : resistors) {
					for(double r2 : resistors) {
						if(r1 >= r2) {
							resistorRatio = r1/r2;
							r = r2;
						}
						else {
							resistorRatio = r2/r1;
							r = r1;
						}
						actFreq = calcFreq(r,c,resistorRatio,capacitorRatio);
						actQ = calcQHigh(resistorRatio,capacitorRatio);
						if(Math.abs(actFreq - goalFreq) + (breakFreq*Math.abs(actQ - goalQ)) <= currentFreqDiff + (breakFreq*currentQDiff)) {
							currentFreqDiff = Math.abs(actFreq - goalFreq);
							currentQDiff = Math.abs(actQ - goalQ);
							combo.clear();
							combo.add(r1);
							combo.add(r2);
							combo.add(cap);
							combo.add(cap2);
							printQ = actQ;
							printFreq = actFreq;
						}
					}
				}
			}
		}
		System.out.println("Q  =  " + printQ);
		System.out.println("Break Frequency  =  " + printFreq);
		System.out.print("[r1, r2, cap1, cap2]  =  ");
		return combo;
	}
	
	public static void main(String[] args) {
		/**
		 * Example outputs, main method testing.
		 */
		System.out.println(lowPassButterworth(500));
		System.out.println(lowPassButterworth(200));
		System.out.println(highPassButterworth(20));
		System.out.println(highPassButterworth(350));
	}
	
}

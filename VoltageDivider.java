public class VoltageDivider {
	public static Double vt(double r1, double r2, double Vin) {
		return Vin*(r2/(r1+r2));
	}
	
	public static void main(String[] args) {
		System.out.println(vt(100, 200, 8));
	}
}

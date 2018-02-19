/**
 * 
 */
package Main;

import java.io.PrintWriter;

;
/**
 * @author guydw
 *
 */
public class PrintToSystem {
	private static long currentTime;
	private static boolean done;
	static double[] a;
	private static double[] b;
	PrintWriter writer;
	int i;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		done = false;
		currentTime = System.nanoTime();
		long currentTimeTwo = System.currentTimeMillis();
		System.out.println("Running Nano time "+currentTime);
		System.out.println("Running Mills "+currentTimeTwo);
		System.out.println("Running Nano time plus 100 "+(currentTime +100));
		//Test(currentTime);
		if(System.nanoTime() >=  2*(currentTime + 1000000000)){
			System.out.println("It Worked!");
			done = true;
			
		}
		//OI.rec.whenPressed(new Record());
		/*for(long i = 0; i >= 100000000; i++){
			if(System.nanoTime() >=  currentTime + 1000){
				System.out.println("It Worked!");
				//Test(currentTime);
				done = true;
			}
		}*/
	}
	public static void Test(long Time){
		if(done){
			System.out.println();
		}else {
			if(System.nanoTime() >=  Time + 1000){
				System.out.println("It Worked!" + done);
				done = true;
				Test(currentTime);
			}
	}
	

}}

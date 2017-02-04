import java.lang.Math;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
public class AutoGenerate {
	static double distance = 5; 
	static double maxAcc = 3.5;
	static double T = Math.sqrt((2*Math.PI*distance)/maxAcc);
	static double kOne = (2*Math.PI)/T;
	static double kTwo = maxAcc/kOne;
	static double kThree = 1/kOne;
	static double position; 
	static double vel;
	static double time;
	static double timeMilis = 0;
	static Timer timer = new Timer();
	static int loopTimes = 0;
	static ArrayList<Double> positionArr = new ArrayList<Double>();
	static ArrayList<Double> velocityArr = new ArrayList<Double>();
	public static void main(String[] args) {
		//if(loopTimes < 100){\
		 
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
		    	if( position < distance){
		    		vel = kTwo*(1-Math.cos(kOne*time));
		    		velocityArr.add(vel);
		    		//------------------------------
		    		position = kTwo*((time)-(kThree*Math.sin(kOne*(time))));   
		    		positionArr.add(position);
		    		//------------------------------
		    		timeMilis = (timeMilis + 20);
		    		time = timeMilis/1000;
		    	}
		    }
		}, 0, 20);
		/*
			timeNano = System.nanoTime();
			timeMilis = System.currentTimeMillis();//TimeUnit.NANOSECONDS.toMillis((long) timeNano);
			position = kTwo*(3-(kThree*Math.sin(kOne*3)));
			*/
			//positionArr.add(position);
			
			//loopTimes++;
		//}
		//System.out.println(position);
		/*
		if(loopTimes >= 100){
			for(int i = 0; i < positionArr.size(); i++){
			System.out.println(positionArr.get(loopTimes));
			}
		}
		*/
	}

}

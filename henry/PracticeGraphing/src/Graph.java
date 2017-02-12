import java.awt.Color;

public class Graph {
	static FalconLinePlot fig1;
	static FalconPathPlanner path;
	static double[][] waypoints = new double[][]{
		{1, 1},
	    {5, 1},
	    {6, 1},
	    {7, 2},
	    {7.5,3},
	    //{8,5}
	    //{7, 6},
	    //{7, 12}//19,12
	}; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double totalTime = 8; //max seconds we want to drive the path
		double timeStep = 0.1; //period of control loop on Rio, seconds
		double robotTrackWidth = 2.25; //distance between left and right wheels, feet

	    path = new FalconPathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);
		fig1 = new FalconLinePlot(path.nodeOnlyPath,Color.blue,Color.green);
		fig1.yGridOn();
		fig1.xGridOn();
		fig1.setYLabel("Y (feet)");
		fig1.setXLabel("X (feet)");
		fig1.setTitle("Top Down View of FRC Field (24ft x 27ft) \n shows global position of robot path, along with left and right wheel trajectories");

		//force graph to show 1/2 field dimensions of 24ft x 27 feet
		fig1.setXTic(0, 27, 1);
		fig1.setYTic(0, 24, 1);
		fig1.addData(path.smoothPath, Color.red, Color.blue);
		fig1.addData(path.leftPath, Color.magenta);
		fig1.addData(path.rightPath, Color.magenta);

	}

}

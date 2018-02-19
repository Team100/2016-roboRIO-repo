import java.io.*;
import javax.*;
import java.lang.*;
import java.util.ArrayList;
import java.*;
/**
 * Data Logger Class
 * @author Jeremy Germita
 */
public class PrintToSystem {
	
public static void main(String[] args) throws Exception {

    // initialize
    ArrayList<String[]> list = new ArrayList<String[]>();
    list.add(new String[] {"Vial1","Dest1"});
    list.add(new String[] {"Vial2","Dest2"});
    list.add(new String[] {"Vial3","Dest3"});
    list.add(new String[] {"Vial4","Dest4"});

    // writer
    FileWriter writer = new FileWriter("output.txt"); 

    // headers
    writer.write("VialNo,DestinationNo\n");
    writer.flush();

    // data
    for(String[] arr: list) {
        String appender = "";
        for(String s : arr){
            writer.write(appender + s);
            appender = ",";
        }
        writer.write("\n");
        writer.flush();
    }
    writer.close();
}
}
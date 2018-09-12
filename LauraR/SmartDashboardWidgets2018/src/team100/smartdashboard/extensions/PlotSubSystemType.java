package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.types.NamedDataType;

public class PlotSubSystemType extends NamedDataType{
    public static final String LABEL = "PlotSubSystem";
    
    private PlotSubSystemType() {
        super(LABEL, OmniPlotWidget.class);
    }
    
    public static NamedDataType get() {
        if(NamedDataType.get(LABEL) != null){
            return NamedDataType.get(LABEL);
        }else{
            return new PlotSubSystemType();
        }
    }	
}

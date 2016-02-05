package team100.smartdashboard.extensions;

public class Pointtype {

}public class Pointtype extends NamedDataType {
	public static final String LABEL = "Point";
	
	private Pointtype() {
		super(LABEL, PointEditor.class);
	}
	public static NamedDataType get() {
		if (NamedDataType.get(LABEL) !=null) {
			return NamedDataType.get(LABEL);
		}else{
			return new Pointtype();
		}
	}
}
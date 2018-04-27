package edu.wpi.first.shuffleboard.sbplugin;

import org.fxmisc.easybind.EasyBind;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

@Description(name = "Team100Point2D", dataTypes = Team100Point2DType.class)
@ParametrizedController("Team100Point2D.fxml")
public class Team100Point2DWidget extends SimpleAnnotatedWidget<Team100Point2DData> {

	  @FXML
	  private Pane root;
	  @FXML
	  private TextField XField;
	  @FXML
	  private TextField YField;

	  @FXML
	  private void initialize() {
		  System.out.println("in widget initialize");
	    XField.textProperty().bind(
	        EasyBind.monadic(dataProperty())
	            .map(Team100Point2DData::getX)
	            .map(Object::toString));
	    YField.textProperty().bind(
	        EasyBind.monadic(dataProperty())
	            .map(Team100Point2DData::getY)
	            .map(Object::toString));
	  }

	  @Override
	  public Pane getView() {
		  System.out.println("in widget getView");
	    return root;
	}
}

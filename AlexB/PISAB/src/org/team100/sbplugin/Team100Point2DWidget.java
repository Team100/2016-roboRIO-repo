package org.team100.sbplugin;

import javafx.scene.control.ComboBox;
import org.fxmisc.easybind.EasyBind;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * @author Team 100
 * @version 1.0.0
 *
 * Code to Render connector
 *
 */
@Description(name = "Team100Point2D", dataTypes = Team100Point2DType.class)
@ParametrizedController("Team100Point2D.fxml")
public class Team100Point2DWidget extends SimpleAnnotatedWidget<Team100Point2DData> {
	/**
	 * Creation of fields for JavaFX
	 *
	 * The FXML file was created using the visual options in IntelliJ IDEA CE
	 */
	  @FXML
	  private Pane root;
	  @FXML
	  private TextField kPField;
	  @FXML
	  private TextField kIField;
	  @FXML
	  private TextField kDField;
	  @FXML
	  private TextField kFField;
	  @FXML
	  private ComboBox PIDSelect;

	  @FXML
	  private void initialize() {
	  	/*
	    XField.textProperty().bind(
	        EasyBind.monadic(dataProperty())
	            .map(Team100Point2DData::getX)
	            .map(Object::toString));
	    YField.textProperty().bind(
	        EasyBind.monadic(dataProperty())
	            .map(Team100Point2DData::getY)
	            .map(Object::toString));
	    kPField.textProperty().bind(
	    		EasyBind.monadic(dataProperty())
					.map(Team100Point2DData::)
		);
		*/
	  	kPField.textProperty().bind(
	  			EasyBind.monadic(dataProperty())
					.map(Team100Point2DData::getkP)
					.map(Object::toString)
		);
	  	
	  	
	  	kIField.textProperty().bind(
	  			EasyBind.monadic(dataProperty())
					.map(Team100Point2DData::getkI)
					.map(Object::toString)
		);
	  	kDField.textProperty().bind(
	  			EasyBind.monadic(dataProperty())
					.map(Team100Point2DData::getkD)
					.map(Object::toString)
		);
	  	kFField.textProperty().bind(
	  			EasyBind.monadic(dataProperty())
					.map(Team100Point2DData::getkF)
					.map(Object::toString)
		);
	  	PIDSelect.getItems().clear();
	  	PIDSelect.getItems().addAll("PREF2018DrivetrainPIDF","PREF2018ElevatorPIDF", "PREF2018WinchPIDF"); /**Set options for PID COMBO BOX --- Must follow Preference format */
	  }

	  @Override
	  public Pane getView() {
	    return root;
	}
}

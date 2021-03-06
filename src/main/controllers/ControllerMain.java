package main.controllers;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import animatefx.animation.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.application.Main;
import main.utils.FXResizeHelper;
import main.utils.SimplerSchoolUtil;

public class ControllerMain {
	private final int HAMMENUSIZE = 230;
	@FXML
	private AnchorPane menuPane;
	@FXML
	private Pane menuShadowPane;
	@FXML
	private Pane gridShadowPane;
	@FXML
	private VBox menuVBox;
	@FXML
	private JFXHamburger hamMenu;
	@FXML
	private HBox weekdayHeader;
	@FXML
	private GridPane calendarGrid;
	@FXML
	private ImageView profilePaneAvatar;
	@FXML
	private Circle cccc;
	@FXML
	private JFXTabPane tabPane;
	@FXML
	private JFXButton settingsButton;
	@FXML
	private JFXButton profileButton;
	@FXML
	private JFXButton closeButton;
	@FXML
	private ImageView image;

	@FXML
	private TextInputDialog inputSubject;
	@FXML
	public void hamclicked(MouseEvent event) {
		if (menuPane.getPrefWidth() == 300) {
			hamMenu.setPrefSize(hamMenu.getPrefWidth() - HAMMENUSIZE, hamMenu.getPrefHeight());
			hamMenu.setPadding(new Insets(0, 0, 0, 0));

			menuVBox.setPrefSize(menuVBox.getPrefWidth() - HAMMENUSIZE, menuVBox.getPrefHeight());

			settingsButton.setPrefSize(settingsButton.getPrefWidth() - HAMMENUSIZE, settingsButton.getPrefHeight());
			settingsButton.setText("");

			profileButton.setPrefSize(profileButton.getPrefWidth() - HAMMENUSIZE, profileButton.getPrefHeight());
			profileButton.setText("");

			closeButton.setPrefSize(closeButton.getPrefWidth() - HAMMENUSIZE, closeButton.getPrefHeight());
			closeButton.setText("");

			hamMenuAnimation(menuPane, menuPane.getPrefWidth() - HAMMENUSIZE);
			hamMenuAnimation(menuShadowPane, menuShadowPane.getPrefWidth() - HAMMENUSIZE);
		} else if (menuPane.getPrefWidth() == 70) {
			hamMenu.setPrefSize(hamMenu.getPrefWidth() + HAMMENUSIZE, hamMenu.getPrefHeight());
			hamMenu.setPadding(new Insets(0, HAMMENUSIZE, 0, 0));

			menuVBox.setPrefSize(menuVBox.getPrefWidth() + HAMMENUSIZE, menuVBox.getPrefHeight());

			settingsButton.setPrefSize(settingsButton.getPrefWidth() + HAMMENUSIZE, settingsButton.getPrefHeight());
			settingsButton.setText("Settings");

			profileButton.setPrefSize(profileButton.getPrefWidth() + HAMMENUSIZE, profileButton.getPrefHeight());
			profileButton.setText("Profile");

			closeButton.setPrefSize(closeButton.getPrefWidth() + HAMMENUSIZE, closeButton.getPrefHeight());
			closeButton.setText("Exit");

			hamMenuAnimation(menuPane, menuPane.getPrefWidth() + HAMMENUSIZE);
			hamMenuAnimation(menuShadowPane, menuShadowPane.getPrefWidth() + HAMMENUSIZE);
		}
		new Wobble(cccc).play();
	}

	public void initialize() {
		System.out.println("init menu gui");
		initHamMenu();
		initCalendarWeekDayHeader();
		initCalendarGrid();
		initProfilePane();
	}

	public void initProfilePane() {
		cccc.setFill(new ImagePattern(profilePaneAvatar.getImage()));
	}
	
	public void initHamMenu() {
		HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamMenu);
		transition.setRate(-1);
		hamMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();
		});
		VBox.setVgrow(menuPane, Priority.ALWAYS);
	}

	public void initTabPane() {

	}

	public void hamMenuAnimation(Pane pane, double width) {
		hamMenu.setDisable(true);
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200),
				new KeyValue(pane.prefWidthProperty(), width, Interpolator.EASE_BOTH)));
		timeline.play();
		timeline.setOnFinished(event -> {
			hamMenu.setDisable(false);
		});

	}

	public void initCalendarWeekDayHeader() {
		int weekdays = 7;
		String[] weekDays = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"};
		for (int i = 0; i < weekdays; i++) {
			StackPane pane = new StackPane();
			pane.getStyleClass().add("weekday-header");
			HBox.setHgrow(pane, Priority.ALWAYS);
			pane.setMaxWidth(Double.MAX_VALUE);
			pane.setMinWidth(weekdayHeader.getPrefWidth() / weekdays);
			weekdayHeader.getChildren().add(pane);
			pane.getChildren().add(new Label(weekDays[i]));
		}
	}

	public void initCalendarGrid() {
		int rows = 11;
		int cols = 7;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				VBox vPane = new VBox();
				vPane.getStyleClass().add("calendar_pane");
				vPane.setMinWidth(weekdayHeader.getPrefWidth() / cols);
				vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
					System.out.println("grid");
				/*	inputSubject = new TextInputDialog();
					inputSubject.setContentText("Insert the subject");
					inputSubject.setTitle(null);
					inputSubject.setHeaderText(null);
					inputSubject.setGraphic(null);
					DialogPane dialogPane = inputSubject.getDialogPane();
					dialogPane.getStylesheets().add(
							getClass().getResource("../resources/gui/css/dialog.css").toExternalForm());
					dialogPane.getStyleClass().add("myDialog");


					inputSubject.show();*/
				});
				GridPane.setVgrow(vPane, Priority.ALWAYS);
				calendarGrid.add(vPane, j, i);

			}
		}
		for (int i = 0; i < cols; i++) {
			RowConstraints row = new RowConstraints();
			calendarGrid.getRowConstraints().add(row);
		}
	}
	
	@FXML
	public void openSettingsWindow(MouseEvent event) {
		System.out.println("Opening settings window");
		Stage settings = SimplerSchoolUtil.loadWindow("settingsFXML", "settingsIconPath","Settings",
				(Stage)((Node)event.getSource()).getScene().getWindow());
		settings.setMinHeight(Integer.parseInt(Main.prop.getProperty("minHeightSettings")));
		settings.setMinWidth(Integer.parseInt(Main.prop.getProperty("minWidthSettings")));
		new FXResizeHelper(settings,5,5);
	}
}

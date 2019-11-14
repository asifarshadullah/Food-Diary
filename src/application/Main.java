package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;




public class Main extends Application {
	
	
	public static Stage window;
	public static ObservableList<Menu> bill = FXCollections.observableArrayList();
	public static int cusID;
	public static int resID;
	public static Restaurant currRes;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window= primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/application/Welcome.fxml"));
			Scene scene = new Scene(root,600,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

import controller.FXMLNotasController;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

public class Main extends Application {

	private Model model;

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/FXMLNotas.fxml"));
		Parent raiz = (Parent) loader.load();

		model = new Model();

		FXMLNotasController controller = loader.getController();
		controller.setModel(model);

		Scene scene = new Scene(raiz);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

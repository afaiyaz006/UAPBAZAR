package uapbazar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SceneSwitcher {

    private static Map<View, Parent> cache = new HashMap<>();

    private static Scene scene;

    public static void setScene(Scene scene) {
        SceneSwitcher.scene = scene;
    }

    public static void switchTo(View view){
        if (scene == null) {
            System.out.println("No scene was set");
            return;
        }

        try {
            Parent root;

            if (cache.containsKey(view)) {
                System.out.println("Loading from cache");

                root = cache.get(view);
            } else {
                System.out.println("Loading from FXML");

                root = FXMLLoader.load(
                        SceneSwitcher.class.getResource(view.getFileName())
                );

                cache.put(view,root);
               
            }

            scene.setRoot(root);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void switchTo(View view,boolean c){
        if (scene == null) {
            System.out.println("No scene was set");
            return;
        }

        try {
            Parent root;

            if (cache.containsKey(view) && c==true) {
                System.out.println("Loading from cache");

                root = cache.get(view);
            } else {
                System.out.println("Loading from FXML");

                root = FXMLLoader.load(
                        SceneSwitcher.class.getResource(view.getFileName())
                );

                cache.put(view,root);
            }

            scene.setRoot(root);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
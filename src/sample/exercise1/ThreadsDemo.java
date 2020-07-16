package sample.exercise1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ThreadsDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final TextArea textArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {

        textArea.setEditable(false);
        textArea.setWrapText(true);

        Scene scene = new Scene(new ScrollPane(textArea));
        primaryStage.setScene(scene);
        primaryStage.setTitle("ThreadsDemo");
        primaryStage.show();

        Thread thread1 = new Thread(() -> writeCharacter('a'));
        Thread thread2 = new Thread(() -> writeCharacter('b'));
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100; i++)
                synchronized (textArea) {
                    textArea.setText(textArea.getText() + " " + i);
                }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    private void writeCharacter(char a) {
        for (int i = 0; i < 100; i++)
            synchronized (textArea) {
                textArea.setText(textArea.getText() + " " + a);
            }
    }
}

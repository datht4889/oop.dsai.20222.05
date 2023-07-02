package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Controller implements Initializable {
    @FXML
    private ImageView myCylinder;
    @FXML
    private Slider mySlider;
    @FXML
    private ImageView mySurface;
    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private Button resetButton;
    
    private static final double SCENE_WIDTH = 1600;
    private static final double SURFACE_WIDTH = SCENE_WIDTH;
    double a = 0;
    double friction = -2;
    double v = 0.0;
    double max_v = 50;
    double min_v = -50;
    TranslateTransition slideTransition1 = new TranslateTransition();
    RotateTransition rotate = new RotateTransition();
    KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
        Label1.setText("a:" + Double.toString(round(a, 0)) + "m/s²");
        Label2.setText("v:" + Double.toString(round(v, 0)) + "m/s");
        if (a != 0) {
            if (v > 0) {
                v += (a + friction) * 0.2;
            }
            else {
                v += (a - friction) * 0.2;
            }
        }
        else {
            if (round(v, 0) > 0) {
                v += friction  * 0.2;
            }

            else if (round(v, 0) < 0) {
                v += -friction * 0.2;

            }
            else {
                v = 0;
            }
        }
        
        if (v > max_v) {
            v = max_v;
            mySlider.setValue(0);
        }
        else if (v < min_v) {
            v = min_v;
            mySlider.setValue(0);
        }
        rotate.setRate(v);
            
        slideTransition1.setRate(v);
        
                  
    });

    Timeline accelerationTimeline = new Timeline(frame);



    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Label1.setText("a:" + Double.toString(round(a, 1)) + "m/s²");
        Label2.setText("v:" + Double.toString(round(v, 1)) + "m/s");
        mySlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                a = (double) mySlider.getValue();
                

                setRotate(myCylinder);
                setRoad(mySurface);
                
                }
                        
        });
            
        mySlider.setOnMouseReleased(event -> {
            mySlider.setValue(0);
        });
    }

    public void setRotate(ImageView rec) {

        rotate.setNode(rec);
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setDuration((Duration.millis(5000.0)));
        rotate.setByAngle(360.0);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(v);
        accelerationTimeline.setCycleCount(Animation.INDEFINITE);
        accelerationTimeline.play();
        rotate.play();
    }

    public void setRoad(ImageView r) {
        slideTransition1.setDuration((Duration.millis(5000.0)));
        slideTransition1.setByX(0);
        slideTransition1.setFromX(SURFACE_WIDTH);
        slideTransition1.setToX(0);
        slideTransition1.setNode(r);
        slideTransition1.setOnFinished(event -> {
            // Reset the position of surface1 when it slides off the screen
            slideTransition1.play();
            
        });
        slideTransition1.setInterpolator(Interpolator.LINEAR);
        slideTransition1.setRate(v);
        slideTransition1.setNode(mySurface);
        slideTransition1.play();  
    }
    
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void resetVar() {
        a = 0;
        v = 0;
        mySlider.setValue(0);
    }

}


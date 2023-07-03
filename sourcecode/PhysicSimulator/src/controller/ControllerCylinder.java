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
import model.object.Cylinder;

public class ControllerCylinder implements Initializable {
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

    @FXML
    private ImageView myBackground;
    
    private static final double SCENE_WIDTH = 1600;
    private static final double SURFACE_WIDTH = SCENE_WIDTH;
    double a = 0;
    double friction = -2;
    double v = 0;
    double max_v = 50;
    double min_v = -50;
    TranslateTransition surfaceTransition = new TranslateTransition();
    TranslateTransition backgroundTransition = new TranslateTransition();
    RotateTransition rotate = new RotateTransition();
    private Cylinder cylinder;

    
    public void initCylinder() {
        try {
            cylinder = new Cylinder(50, 1);
        } catch (Exception e) {
            // Handle the exception appropriately
        }
    }



    KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {

        Label1.setText("a:" + Double.toString(round(cylinder.getAcceleration(), 0)) + "m/s²");
        Label2.setText("v:" + Double.toString(round(cylinder.getVelocity(), 0)) + "m/s");
        
        cylinder.update(friction,0.2);
        
        rotate.setRate(cylinder.getVelocity());
        surfaceTransition.setRate(cylinder.getVelocity());
        backgroundTransition.setRate(cylinder.getVelocity()/20);
        
    });

    Timeline accelerationTimeline = new Timeline(frame);
  
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initCylinder();
        Label1.setText("a:" + Double.toString(round(cylinder.getAcceleration(), 1)) + "m/s²");
        Label2.setText("v:" + Double.toString(round(cylinder.getVelocity(), 1)) + "m/s");
        mySlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                cylinder.setAcceleration(mySlider.getValue());
                // myCylinder.setScaleX(1.5);
                // myCylinder.setScaleY(1.5);
                // myCylinder.setX(0);
                
                setRotate(myCylinder,cylinder.getVelocity());
                setSurfaceMovement(mySurface, cylinder.getVelocity());
                setBackgroundMovement(myBackground, cylinder.getVelocity()/20);
                
                }
                        
        });
            
        mySlider.setOnMouseReleased(event -> {
            mySlider.setValue(0);
        });
    }

    public void setRotate(ImageView obj, double rate) {

        rotate.setNode(obj);
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setDuration((Duration.millis(5000.0)));
        rotate.setByAngle(360.0);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(rate);
        accelerationTimeline.setCycleCount(Animation.INDEFINITE);
        accelerationTimeline.play();
        rotate.play();
    }

    public void setSurfaceMovement(ImageView r, double rate) {
        surfaceTransition.setDuration((Duration.millis(5000.0)));
        surfaceTransition.setByX(0);
        surfaceTransition.setFromX(SURFACE_WIDTH);
        surfaceTransition.setToX(0);
        surfaceTransition.setNode(r);
        surfaceTransition.setOnFinished(event -> {
            // Reset the position of surface1 when it slides off the screen
            surfaceTransition.play();

        });
        surfaceTransition.setInterpolator(Interpolator.LINEAR);
        surfaceTransition.setRate(rate);
        surfaceTransition.play();
    }
    
    public void setBackgroundMovement(ImageView r,double rate) {
        backgroundTransition.setDuration((Duration.millis(5000.0)));
        backgroundTransition.setByX(0);
        backgroundTransition.setFromX(SURFACE_WIDTH);
        backgroundTransition.setToX(0);
        backgroundTransition.setNode(r);
        backgroundTransition.setOnFinished(event -> {
            // Reset the position of surface1 when it slides off the screen
            backgroundTransition.play();
            
        });
        backgroundTransition.setInterpolator(Interpolator.LINEAR);
        backgroundTransition.setRate(rate);
        backgroundTransition.play();  
    }
    
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void resetVar() {
        cylinder.setAcceleration(0);
        cylinder.setVelocity(0);
    }

    public void pause() {
        surfaceTransition.pause();
        backgroundTransition.pause();
        rotate.pause();
        accelerationTimeline.pause();
    }

    public void play() {
        surfaceTransition.play();
        backgroundTransition.play();
        rotate.play();
        accelerationTimeline.play();
    }

    public void close() {
        System.exit(0);
    }


}


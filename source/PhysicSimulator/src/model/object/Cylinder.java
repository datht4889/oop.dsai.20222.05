package model.object;

public class Cylinder extends MainObject {
    public static final double MAX_RADIUS = 1.0;
    public static final double MIN_RADIUS = 0.2;
    private double radius = 0;
    private double angularPos = 0;
    private double angularVel = 0;
    private double gamma;

    public Cylinder() throws Exception {
        super();

    }

    public Cylinder(double mass) throws Exception {
        super(mass);

    }

    public Cylinder(double mass, double radius) throws Exception {
        super(mass);
        try{
        setRadius(radius);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        if (radius > MAX_RADIUS) {
            this.radius = MAX_RADIUS;
        }
        else if (radius < MIN_RADIUS) {
            this.radius = MIN_RADIUS;
        }
    }

    public double getAngularPos() {
        return angularPos;
    }

    public void setAngularPos(double angularPos) {
        this.angularPos = angularPos;
    }

    public double getAngularVel() {
        return angularVel;
    }

    public void setAngularVel(double angularVel) {
        this.angularVel = angularVel;
    }
    
    public double getGamma(){
        return this.gamma;
    }


    public void setGamma(double friction) {
        this.gamma = friction / (1/2*getMass() * Math.pow(getRadius(),2));
    }

}

package model.object;

public abstract class MainObject {
    
    public static final double MASS_DEFAULT = 100.0;
    private double mass = MASS_DEFAULT;
    private double pos = 0;
    private double accel = 0;
    private double vel = 0;

    public MainObject() throws Exception {
    }

    public MainObject(double mass) throws Exception {
        setMass(mass);
    }
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getAcceleration() {
        return accel;
    }


    public void setAcceleration(double accel) {
        this.accel = accel;
    }

    public double getVelocity() {
        return vel;
    }

    public void setVelocity(double vel) {
        this.vel = vel;
    }

    public void updateVelocity(double delta_t) {
        this.vel += this.accel * delta_t;
    }

    public double getPos() {
        return pos;
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    public void updatePos(double delta_t) {
        this.pos += this.vel * delta_t;
    }


}

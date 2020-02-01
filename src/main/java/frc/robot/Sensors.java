package frc.robot;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
/**
 * Add your docs here.
 */
public class Sensors {
    // online import link: http://maven.highcurrent.io/vendordeps/ADIS16448.json
    public final ADXRS450_Gyro imu = new ADXRS450_Gyro();
    public double zeroOffset = -0;
public double getAxis(){
    //System.out.println(imu.getAngle());
    return imu.getAngle() + zeroOffset;
}

public void callibrate() {
    imu.calibrate();
    imu.reset(); 
}
public void setGyro(double offset){
    zeroOffset = offset;
}
public double getRawAxis() {
return imu.getAngle();
}
}
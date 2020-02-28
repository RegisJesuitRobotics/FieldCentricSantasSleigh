package frc.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;

//import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/*
 * Add your docs here.
 */
public class Sensors {
    // online import link: http://maven.highcurrent.io/vendordeps/ADIS16448.json
    public final ADIS16448_IMU gyro = new ADIS16448_IMU();
    //public final ADXRS450_Gyro imu = new ADXRS450_Gyro();
    public double zeroOffset = -0;
    public Timer timer = new Timer();
public double getAxis(){
    //System.out.println(imu.getAngle());
    SmartDashboard.putNumber("Gyro: ", gyro.getAngle());
    return gyro.getAngle();
}
public void autoCallibrate(){
    gyro.calibrate();
    gyro.reset();
}
public void setGyro(double offset){
    zeroOffset = offset;
}
}
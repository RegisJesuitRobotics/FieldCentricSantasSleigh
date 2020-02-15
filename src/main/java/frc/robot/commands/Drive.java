//----------------------------------------------------------------------------//Copyright(c)2019 FIRST.All Rights Reserved.//Open Source Software-may be modified and shared by FRC teams.The code//must be accompanied by the FIRST BSD license file in the root directory of//the project.//----------------------------------------------------------------------------/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
//import frc.robot.RobotMap;
// This is a comment from Garrison
import frc.robot.RobotMap;

/*An example command.You can replace me with your own command.*/

public class Drive extends Command {
  // Joystick Angle Variables
  double[] joystickValues = new double[3];
  double joystickAngle = 0;
  double joystickAdder = 0;
  // Gyro Variables
  double gyro = 0;
  double rawGyro = 0;
  // Joystick/Gyro Calculation Variables
  double difference = 0;
  int flip = 1;
  // Power Variables
  double limiter = 0.2;
  double sLimiter = 0.5;
  double hypotonuse = 0;
  // Execute Variables
  double leftSpeed;
  double rightSpeed;
  double increment;
  double modifier;
  double sPower;
  int direction;
  double absDistance;
  double internetDistance;
  double curveM;

  public Drive() {
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {

    // Calculate Variable Values(See Bottom)-----------------------
    setHypotonuse();
    setLimiter();
    findAngle();
    findGyro();
    // getDifference(); // calls get difference which calls find angle, which calls
    // quadrantFactor
    // Initialize Execute Variables
    // leftSpeed = 0;
    // rightSpeed = 0;
    increment = hypotonuse * limiter * flip;
    // modifier = 0;
    sPower = hypotonuse * sLimiter * .6;
    // lukesMethod();

    // // Evaluate Variables for Direction---------------------------

    // Nick's Method Calls
    // if ((joystickAngle - 10) % 360 < gyro && (joystickAngle + 10) % 360 > gyro) {
    // if (difference > 0) {
    // modifier = (1 + 2 (difference / RobotMap.BIG_ANGLE));
    // leftSpeed = -increment;
    // rightSpeed = modifier increment;
    // } else if (difference < 0) {
    // modifier = (1 - 2 (difference / RobotMap.BIG_ANGLE));
    // leftSpeed = increment;
    // rightSpeed = -modifier increment;
    // } else {
    // leftSpeed = -sPower;
    // rightSpeed = sPower;
    // }
    // } else {

    // leftSpeed = nicksMethod() .6;
    // rightSpeed = nicksMethod() .6;
    // System.out.println(nicksMethod());
    // }
    // if (Math.abs(difference) > RobotMap.BIG_ANGLE) {// if there is significant
    // difference
    // if (difference < 0) {// if difference is negative
    // // turn towards negative
    // leftSpeed = -increment;
    // rightSpeed = -increment;
    // // SmartDashboard.putString("Direction: ", "Increment: " + increment);
    // // SmartDashboard.putString("Direction: ", "NEGATIVE");
    // SmartDashboard.putString("Direction: ", "Negative Big20");
    // } else {// if difference is positive
    // // turn towards positive
    // leftSpeed = increment;
    // rightSpeed = increment;
    // // SmartDashboard.putString("Direction: ", "Increment: " + increment);
    // // SmartDashboard.putString("Direction: ", "POSITIVE");
    // SmartDashboard.putString("Direction: ", "Positive Big20");
    // }
    // } else if (Math.abs(difference) > 5) {// if there is a little difference
    // if (difference < 0) {// if difference is negative
    // // turn towards negative
    // modifier = (1 + 2 (difference / RobotMap.BIG_ANGLE));
    // leftSpeed = -increment;
    // rightSpeed = modifier increment;
    // // SmartDashboard.putString("Direction: ", "Modifier: " + modifier);
    // // SmartDashboard.putString("Direction: ", "Increment: " + increment);
    // // SmartDashboard.putString("Direction: ", "negative");
    // SmartDashboard.putString("Direction: ", "Negative Lil20");
    // } else {// if difference is positive;
    // // turn towards positive.
    // modifier = (1 - 2 (difference / RobotMap.BIG_ANGLE));
    // leftSpeed = increment;
    // rightSpeed = -modifier increment;
    // // SmartDashboard.putString("Direction: ", "Modifier: " + modifier);
    // // SmartDashboard.putString("Direction: ", "Increment: " + increment);
    // // SmartDashboard.putString("Direction: ", "positive" + "gyro" + gyro);
    // SmartDashboard.putString("Direction: ", "Positive Lil20");
    // }
    // } else {// if there is negligible difference
    // // go straight.%
    // SmartDashboard.putString("Direction: ", "Straight");
    // leftSpeed = -sPower;
    // rightSpeed = sPower;
    // if (sPower < 0) {
    // SmartDashboard.putString("Direction: ", "backCommand");
    // }

    // Turn/Drive according to evaluated
    // variables------------------------------------------

    // System.out.println("w" + joystickAngle);
    // System.out.println("R" + gyro);
    final double oppositeGyro = (gyro + 180) % 360;
    // System.out.println("O " + oppositeGyro + " G " + gyro);

    final int direction;

    if (joystickAngle <= gyro + 50 && joystickAngle >= gyro - 50) {
      // // modifier = (1 - 2 * (difference / RobotMap.BIG_ANGLE));
      // modifier = 0.05;
      // internetDistance = internetDistance();
      // absDistance = Math.abs(internetDistance);
      // curveM = internetDistance * modifier;
      // if (internetDistance < 0) {
      // Robot.driveTrain.moveLeft(sPower);
      // Robot.driveTrain.moveRight(sPower * (1 - absDistance/50));
      // System.out.println("Left " + internetDistance);

      // } else if (internetDistance > 0) {
      // Robot.driveTrain.moveLeft(increment);
      // Robot.driveTrain.moveRight(modifier * increment);
      // System.out.println("Right " + internetDistance);

      // } else if (internetDistance == 0) {
      // System.out.println("Straight " + internetDistance);
      // } else {
      // System.out.println("Error " + internetDistance);
      // }
      modifier = (1 - 2 * (internetDistance() / 50));
      if (joystickAngle <= gyro + 20 && joystickAngle >= gyro - 20) {
        Robot.driveTrain.moveLeft(-sPower);
        Robot.driveTrain.moveRight(sPower);
        // }

      } else if (joystickAngle <= gyro + 50 && joystickAngle >= joystickAngle - 50) {
        Robot.driveTrain.moveLeft(-sPower * modifier);
        Robot.driveTrain.moveRight(sPower);
      } else {
        
        Robot.driveTrain.moveLeft(-sPower);
        Robot.driveTrain.moveRight(sPower * modifier);
      }
    } else {
      Robot.driveTrain.moveLeft(nicksMethod() * sPower);
      Robot.driveTrain.moveRight(nicksMethod() * sPower);
    }

  }
  // Luke's method calls
  // public void lukesMethod() {
  // final double difference = joystickAngle - gyro;
  // final double counterDiff = 360 - Math.abs(difference);
  // SmartDashboard.putNumber("power ", sPower);
  // if (difference < 0) {
  // if (Math.abs(difference) > Math.abs(counterDiff)) {
  // Robot.driveTrain.moveLeft(sPower);
  // Robot.driveTrain.moveRight(sPower);
  // SmartDashboard.putString("Turn Direction ", "1");
  // } else if (Math.abs(difference) < Math.abs(counterDiff)) {
  // Robot.driveTrain.moveRight(-sPower);
  // Robot.driveTrain.moveLeft(-sPower);
  // SmartDashboard.putString("Turn Direction ", "2");
  // } else {
  // Robot.driveTrain.moveLeft(-sPower);
  // Robot.driveTrain.moveRight(sPower);
  // SmartDashboard.putString("Turn Direction ", "3");
  // }
  // } else if (difference > 0) {
  // if (Math.abs(difference) < Math.abs(counterDiff)) {
  // Robot.driveTrain.moveLeft(sPower);
  // Robot.driveTrain.moveRight(sPower);
  // SmartDashboard.putString("Turn Direction ", "4");
  // } else if (Math.abs(difference) > Math.abs(counterDiff)) {
  // Robot.driveTrain.moveRight(-sPower);
  // Robot.driveTrain.moveLeft(-sPower);
  // SmartDashboard.putString("Turn Direction ", "5");
  // } else {
  // Robot.driveTrain.moveLeft(-sPower);
  // Robot.driveTrain.moveRight(sPower);
  // SmartDashboard.putString("Turn Direction ", "6");
  // }
  // }
  // else {
  // Robot.driveTrain.moveLeft(-sPower);
  // Robot.driveTrain.moveRight(sPower);

  // }
  // }

  {
    // Straight
  }

  public int nicksMethod() {
    int direction = 0;
    findGyro();
    findAngle();
    if (gyro > joystickAngle) {
      direction = -1;
    } else if (gyro < joystickAngle) {
      direction = 1;
    } else {
      direction = 0;
    }
    return direction;
  }

  public double internetDistance() {
    findGyro();
    findAngle();
    final double proxyDistance = Math.abs(joystickAngle - gyro) % 360;
    double distance = proxyDistance > 180 ? 360 - proxyDistance : proxyDistance;
    final int sign = (joystickAngle - gyro >= 0 && joystickAngle - gyro <= 180)
        || (joystickAngle - gyro <= -180 && joystickAngle - gyro > - -360) ? 1 : -1;
    distance *= sign;
    // System.out.println("Joystick: " + joystickAngle + "\nGyro: " + gyro +
    // "\nDistance: " + distance + "\nSign: " + sign);
    // return distance == 0 ? 0 : sign;
    return distance;
  }

  public int WendysMethod() {
    final double my_Difference = joystickAngle - gyro;
    final double counterDiff = 360 - joystickAngle + gyro;
    final double dMCD = my_Difference - counterDiff;
    final int direction = 0;
    final double straightZone = 5;
    // if (dMCD % 360 == 0) {
    // if ((dMCD / 360) % 2 == 0) {
    // return(1);//Backwards, default clockwise.
    // } else {
    // return(0);// Straight forward
    // }
    // } else
    // System.out.println(dMCD);
    if ((dMCD <= (0 /*- straightZone /*-5*/) && dMCD > (-360 + straightZone /*-355*/))
        || (dMCD > (-1080 + straightZone /*-1075*/) && dMCD <= (-720 /*- straightZone /*-725*/))) {
      return (1);// Clockwise
    } else if ((dMCD < (360 - straightZone /* 355 */) && dMCD >= (0 /* + straightZone /*5 */)
        || (dMCD < (-360 - straightZone /*-365*/) && dMCD >= (-720 /* + straightZone /*-715 */)))) {
      return (-1);// Counter Clockwise
    } else {
      return (0);// Straight/Backwards/Error
    }
  }

  public void setHypotonuse() {
    joystickValues = Robot.m_oi.getAxiis();
    hypotonuse = Math.sqrt(Math.pow(joystickValues[0], 2) + Math.pow(joystickValues[1], 2));
    // maxHypotenuse = 1/(Math.abs(Math.cos(joystickAngle Math.PI / 180)) 180 /
    // Math.PI );
    // hypotonuse = hypotonuse/maxHypotenuse;
    if (hypotonuse < 0.3) {// deadZone
      hypotonuse = 0;
    }
    if (hypotonuse > 1) {
      hypotonuse = 1;
    }
    // SmartDashboard.putString("Direction: ", "Power: " + hypotonuse);
  }

  public void setLimiter() {
    limiter = ((-joystickValues[3] + 2) / 2) * .3;
    sLimiter = ((-joystickValues[3] + 2) / 2);
  }

  public void findGyro() {
    gyro = Robot.gyro.getAxis();
    gyro = Math.abs(gyro % 360);
    SmartDashboard.putNumber("Gyro: ", gyro);
  }

  public void findAngle() {
    joystickValues = Robot.m_oi.getAxiis();
    joystickAdder = quadrantFactor(joystickValues[0], joystickValues[1]);
    joystickAngle = Math.atan(joystickValues[1] / joystickValues[0]) * (180 / Math.PI) + joystickAdder;
    SmartDashboard.putNumber("Angle: ", joystickAngle);
    SmartDashboard.putNumber("30", 30);
  }

  public int quadrantFactor(final double x, final double y) {
    // Returns difference between initial joystick values and the desired 0-360
    // output
    if (x > 0 && y >= 0) {// quadrant 1, or 0 to 90(origin at the right)
      return 90;
    } else if (x <= 0 && y > 0) {// quadrant 2, or 90 to 180
      return 270;
    } else if (x < 0 && y <= 0) {// quadrant 3, or 180 to 270
      return 270;
    } else {// quadrant 4, or 270 to 360/0
      return 90;
    }
  }

  // // Make this return true when this Command no longer needs to run execute()
  // @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.moveRight(0);
    Robot.driveTrain.moveLeft(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

  // Calculate Variables----------------------------------------

  // public void setMotorFlip() {
  // getDifference();
  // if (difference > 180) {
  // flip = -1;

  // } else {
  // flip = 1;
  // }
  // }
  // public void getDifference() {
  // findAngle();
  // findGyro();
  // SmartDashboard.putNumber("Counterclockwise Difference",
  // counterClockwiseDif());
  // SmartDashboard.putNumber("Clockwise Difference", clockwiseDif());
  // // if(clockwiseDif() - counterClockwiseDif() <= 20){
  // // difference = -counterClockwiseDif();
  // // SmartDashboard.putString("Turn Direction: ", "Counterclockwise");
  // // }else
  // if (clockwiseDif() < counterClockwiseDif()) {
  // difference = -clockwiseDif();
  // SmartDashboard.putString("Turn Direction: ", "Clockwise");
  // } else {
  // difference = counterClockwiseDif();
  // SmartDashboard.putString("Turn Direction: ", "Counterclockwise");
  // }
  // // SmartDashboard.putString("Direction: ", "Gyro: " + gyro);
  // // SmartDashboard.putString("Direction: ", "Difference:" + difference);
  // }

  // public double clockwiseDif() {
  // return Math.abs(joystickAngle - gyro);
  // }

  // public double counterClockwiseDif() {
  // return Math.abs((360 - joystickAngle) + gyro);
  // }

  // public void NewMethod() { final double counterDiff = 360 -
  // Math.abs(difference); if (difference < 0){ if (Math.abs(difference) >
  // Math.abs(counterDiff )){ Robot.driveTrain.moveLeft(sPower);
  // Robot.driveTrain.moveRight(sPower); } else if (Math.abs (difference)<
  // Math.abs (counterDiff){ Robot.driveTrain.moveRight(sPower);
  // Robot.driveTrain.moveLeft(sPower); } else { Robot.driveTrain.moveLeft
  // (sPower); Robot.driveTrain.moveRight(sPower); } } else if (difference > 0){
  // if (Math.abs (difference)<Math.abs (counterDiff)) {
  // Robot.driveTrain.moveLeft(sPower); Robot.driveTrain.moveRight(sPower); } else
  // if (Math.abs (difference)>Math.abs (counterDiff)){
  // Robot.driveTrain.moveRight(sPower); Robot.driveTrain.moveLeft(sPower); } else
  // { Robot.driveTrain.moveLeft(sPower); Robot.driveTrain.moveRight(sPower); } }
  // }else

  // { //Straight
  // }

}

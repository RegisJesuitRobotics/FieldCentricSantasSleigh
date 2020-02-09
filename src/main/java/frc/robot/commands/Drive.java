/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
//import frc.robot.RobotMap;
// This is a comment from Garrison
import frc.robot.RobotMap;

/**
 * An example command. You can replace me with your own command.
 */
public class Drive extends Command {
  //Joystick Angle Variables
  double[] joystickValues = new double[3];
  double joystickAngle = 0;
  double joystickAdder = 0;
  //Gyro Variables
  double gyro = 0;
  double rawGyro = 0;
  //Joystick/Gyro Calculation Variables
  double difference = 0;
  int flip = 1;
  //Power Variables
  double limiter = 0.2;
  double sLimiter = 0.5;
  double hypotonuse = 0;
  //Execute Variables
  double leftSpeed;
  double rightSpeed;
  double increment;
  double modifier;
  double sPower;
   
  public Drive() {
    requires(Robot.driveTrain);
  }

  
  @Override
  protected void initialize() {
  }
 
 
  @Override
  protected void execute() {

//Calculate Variable Values(See Bottom)-----------------------
    setHypotonuse(); 
    setLimiter();
    getDifference(); //calls get difference which calls find angle, which calls quadrantFactor
//Initialize Execute Variables
    leftSpeed = 0;
    rightSpeed = 0;
    increment = hypotonuse * limiter * flip;
    modifier = 0;
    sPower = hypotonuse * sLimiter;
//Evaluate Variables for Direction---------------------------
    if (Math.abs(difference) > RobotMap.BIG_ANGLE) {// if there is significant difference
      if (difference < 0) {// if difference is negative
        // turn towards negative
        leftSpeed = -increment;
        rightSpeed = -increment;
         // SmartDashboard.putString("Direction: ", "Increment: " + increment);
         // SmartDashboard.putString("Direction: ", "NEGATIVE");
        SmartDashboard.putString("Direction: ", "Negative Big20");
      } else {// if difference is positive
        // turn towards positive
        leftSpeed = increment;
        rightSpeed = increment;
        // SmartDashboard.putString("Direction: ", "Increment: " + increment);
        // SmartDashboard.putString("Direction: ", "POSITIVE");
        SmartDashboard.putString("Direction: ", "Positive Big20");
      }
    }else if (Math.abs(difference) > 5) {// if there is a little difference
      if (difference < 0) {// if difference is negative
        // turn towards negative
        modifier = (1 + 2 * (difference / RobotMap.BIG_ANGLE));
        leftSpeed = -increment;
        rightSpeed = modifier * increment;
         // SmartDashboard.putString("Direction: ", "Modifier: " + modifier);
        // SmartDashboard.putString("Direction: ", "Increment: " + increment);
         // SmartDashboard.putString("Direction: ", "negative");
         SmartDashboard.putString("Direction: ", "Negative Lil20");
      } else {// if difference is positive;
        // turn towards positive.     
        modifier = (1 - 2 * (difference / RobotMap.BIG_ANGLE));
        leftSpeed = increment;
        rightSpeed = -modifier * increment;
         // SmartDashboard.putString("Direction: ", "Modifier: " + modifier);
        // SmartDashboard.putString("Direction: ", "Increment: " + increment);
        // SmartDashboard.putString("Direction: ", "positive" + "gyro" + gyro);
          SmartDashboard.putString("Direction: ", "Positive Lil20");
      }
    } else {// if there is negligible difference
      // go straight.%
      SmartDashboard.putString("Direction: ", "Straight");
      leftSpeed = -sPower;
      rightSpeed = sPower;
      if (sPower < 0) {
        SmartDashboard.putString("Direction: ", "backCommand");
      }
    }
    //Turn/Drive according to evaluated variables------------------------------------------
    Robot.driveTrain.moveRight(rightSpeed);
    Robot.driveTrain.moveLeft(leftSpeed);
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

//Calculate Variables----------------------------------------

  public void setHypotonuse() {
    joystickValues = Robot.m_oi.getAxiis();
    hypotonuse = Math.sqrt(Math.pow(joystickValues[0], 2) + Math.pow(joystickValues[1], 2));
    // maxHypotenuse = 1/(Math.abs(Math.cos(joystickAngle * Math.PI / 180)) * 180 /
    // Math.PI );
    // hypotonuse = hypotonuse/maxHypotenuse;
    if (hypotonuse < 0.3) {// deadZone
      hypotonuse = 0;
    }
    if (hypotonuse > 1) {
      hypotonuse = 1;
    }
    //SmartDashboard.putString("Direction: ", "Power: " + hypotonuse);
  }

  public void setLimiter() {
    limiter = ((-joystickValues[3] + 2) / 2) * .3;
    sLimiter = ((-joystickValues[3] + 2) / 2);
  }

  // public void setMotorFlip() {
  //   getDifference();
  //   if (difference > 180) {
  //     flip = -1;

  //   } else {
  //     flip = 1;
  //   }
  // }
  public void getDifference(){
    findAngle();
    findGyro();
    SmartDashboard.putNumber("Counterclockwise Difference", counterClockwiseDif());
    SmartDashboard.putNumber("Clockwise Difference", clockwiseDif());
    // if(clockwiseDif() - counterClockwiseDif() <= 20){
    //   difference = -counterClockwiseDif();
    //   SmartDashboard.putString("Turn Direction: ", "Counterclockwise");
    // }else
     if(clockwiseDif() < counterClockwiseDif()){
      difference = -clockwiseDif();
      SmartDashboard.putString("Turn Direction: ", "Clockwise");
    }else{
      difference = counterClockwiseDif();
      SmartDashboard.putString("Turn Direction: ", "Counterclockwise");
    }
        //SmartDashboard.putString("Direction: ", "Gyro: " + gyro);
       // SmartDashboard.putString("Direction: ", "Difference:" + difference);
  }
  
  public double clockwiseDif() {
     return Math.abs(joystickAngle - gyro);
  }
  public double counterClockwiseDif() {
      return Math.abs((360 - joystickAngle) + gyro);
  }

  public void findGyro(){
    gyro = Robot.gyro.getAxis();
    gyro = Math.abs(gyro % 360);
    SmartDashboard.putNumber("Gyro: ", gyro);
  }
  public void findAngle() {
    joystickValues = Robot.m_oi.getAxiis();
    joystickAdder = quadrantFactor(joystickValues[0], joystickValues[1]);
    joystickAngle = Math.atan(joystickValues[1] / joystickValues[0]) * (180 / Math.PI) + joystickAdder;
    SmartDashboard.putNumber("Angle: ", joystickAngle);
  }
  public int quadrantFactor(final double x, final double y) {
    //Returns difference between initial joystick values and the desired 0-360 output
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
}

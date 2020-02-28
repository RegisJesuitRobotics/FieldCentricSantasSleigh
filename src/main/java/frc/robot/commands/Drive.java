//----------------------------------------------------------------------------//Copyright(c)2019 FIRST.All Rights Reserved.//Open Source Software-may be modified and shared by FRC teams.The code//must be accompanied by the FIRST BSD license file in the root directory of//the project.//----------------------------------------------------------------------------/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
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
  // Power Variables
  double limiter = 0.2;
  double sLimiter = 0.5;
  double hypotonuse = 0;
  // Execute Variables
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

    // Calculate Variable Values(See Bottom)-----------------------
    setHypotonuse();
    setLimiter();
    findAngle();
    findGyro();
    sPower = hypotonuse * sLimiter * .6;

    if (Math.abs(distance()) < 10) {
      //Straight
      Robot.driveTrain.moveLeft(-sPower);
      Robot.driveTrain.moveRight(sPower);
      SmartDashboard.putNumber("Direction", 0);
    } else if (Math.abs(distance()) < 40) {
      //Curve
      SmartDashboard.putNumber("modifier", modifier());
      if (distance() < 0) {
        //Cureve Negative
        Robot.driveTrain.moveLeft(-sPower * modifier());
        Robot.driveTrain.moveRight(sPower); 
        SmartDashboard.putNumber("Direction", 0.5);
      } else {
        //Curve Positive
        Robot.driveTrain.moveLeft(-sPower);
        Robot.driveTrain.moveRight(sPower * modifier());
        SmartDashboard.putNumber("Direction", -0.5);
      }
    } else {
      //PIVOT
        SmartDashboard.putNumber("Direction", direction()); 
        Robot.driveTrain.moveLeft(sPower);
        Robot.driveTrain.moveRight(sPower);
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
   
    //JOYSTICK
      public void findAngle() {
        joystickValues = Robot.m_oi.getAxiis();
        joystickAdder = quadrantFactor(joystickValues[0], joystickValues[1]);
        joystickAngle = Math.atan(joystickValues[1] / joystickValues[0]) * (180 / Math.PI) + joystickAdder;
        SmartDashboard.putNumber("Angle: ", joystickAngle);
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

    //GYRO
      public void findGyro() {
        gyro = Robot.gyro.getAxis();
        gyro = Math.abs(gyro % 360);
        SmartDashboard.putNumber("Gyro: ", gyro);
      }

    //DIFFERENCE
      public double distance(){
        if (gyro > joystickAngle) {
          if (gyro - joystickAngle < 180) {
            return gyro - joystickAngle;
          } else {
            return 360 - (gyro - joystickAngle);
          }
        } else {
          if (joystickAngle - gyro < 180) {
            return joystickAngle - gyro;
          } else {
            return 360 - (joystickAngle - gyro);
          }
        }
      }
    
      public int direction() {
        if (gyro > joystickAngle) {
          if (gyro - joystickAngle < 180) {
            return 1;
          } else {
            return -1;
          }
        } else {
          if (joystickAngle - gyro < 180) {
            return 1;
          } else {
            return -1;
          }
        }
      }
    
      public double modifier() {
        return Math.abs(1-Math.abs((gyro-joystickAngle)/180));
      }
}

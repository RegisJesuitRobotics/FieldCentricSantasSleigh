/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
//import frc.robot.RobotMap;

/**
 * An example command. You can replace me with your own command.
 */
public class Drive extends Command {
  private double[] joystickValues = new double[3];
  private double joystickAngle = 0;
  private double difference = 0;
  private double gyro = 0;
  private double limiter = 0.2;
  private double sLimiter = 0.5;
  private double hypotonuse = 0;
  private boolean forwards = true;
  private int flip = 1;
  private double rawGyro = 0;

  public Drive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    getDifference();
    if (Math.abs(difference) > 20) {// if there is significant difference
      if (difference < 0) {// if difference is negative
        // turn towards negative
        //System.out.println("NEGATIVE");
        System.out.println("Negative Big20");
        double increment = -hypotonuse * limiter * flip;
        //System.out.println("Increment: " + increment);
        Robot.driveTrain.moveRight(increment);
        Robot.driveTrain.moveLeft(increment);
      } else {// if difference is positive
        // turn towards positive
        // System.out.println("POSITIVE");
        //System.out.println("POSITIVE");
        System.out.println("Positive Big20");
        double increment = hypotonuse * limiter * flip;
        //System.out.println("Increment: " + increment);
        Robot.driveTrain.moveRight(increment);
        Robot.driveTrain.moveLeft(increment);
      }
    }
    if (Math.abs(difference) > 5) {// if there is a little difference
      if (difference < 0) {// if difference is negative
        // turn towards negative
        //System.out.println("negative");
        System.out.println("Negative Lil20");
        double increment = hypotonuse * limiter * flip;
        //System.out.println("Increment: " + increment);
        double modifier = -(1 + 2 * (difference / 20));
        //System.out.println("Modifier: " + modifier);
        Robot.driveTrain.moveRight(increment);
        Robot.driveTrain.moveLeft(modifier * increment);
      } else {// if difference is positive;
        // turn towards positive.
        System.out.println("Positive Lil20");
        //System.out.println("positive" + "gyro" + gyro);
        double increment = limiter * hypotonuse * flip;
        //System.out.println("Increment: " + increment);
        double modifier = -(1 - 2 * (difference / 20));
        //System.out.println("Modifier: " + modifier);
        Robot.driveTrain.moveRight(modifier * increment);
        Robot.driveTrain.moveLeft(increment);
      }
    } else {// if there is negligible difference
      // go straight.%
System.out.println("Straight");
      Robot.driveTrain.moveRight(Math.abs(hypotonuse * sLimiter));
      Robot.driveTrain.moveLeft(-Math.abs(hypotonuse * sLimiter));
      if (hypotonuse * sLimiter < 0) {
        System.out.println("backCommand");
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
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

  public void getDifference() {
    joystickValues = Robot.m_oi.getAxiis();
    gyro = Robot.gyro.getAxis();
    joystickAngle = 0;
    joystickAngle = Math.atan(joystickValues[1] / joystickValues[0]) * (180 / Math.PI);
    if (joystickAngle < 0) {
      joystickAngle += 90;
    } else {
      joystickAngle -= 90;
    }

    if (joystickValues[1] > 0 && forwards) {
      //Robot.gyro.setGyro(-180);// flip view
      forwards = false;
      System.out.println("flip backwards");

    } else if (joystickValues[1] < 0 && !forwards) {
      Robot.gyro.setGyro(0);// flip view
      forwards = true;
      System.out.println("Flip forwards");
    }

    gyro = Robot.gyro.getAxis();
    rawGyro = Robot.gyro.getRawAxis();
    difference = joystickAngle - gyro;

    hypotonuse = Math.sqrt(Math.pow(joystickValues[0], 2) + Math.pow(joystickValues[1], 2));
    if ((Math.abs(rawGyro) > 90 && forwards) || (Math.abs(rawGyro) < 90 && !forwards)) {
      flip = -1;
    } else {
      flip = 1;
    }
    if (hypotonuse < 0.3) {// deadZone
      hypotonuse = 0;
    }
    limiter = ((-joystickValues[3] + 2) / 2) * .3;
    sLimiter = ((-joystickValues[3] + 2) / 2);
    //System.out.println("Angle: " + angle + " Gyro: " + gyro + "Difference: " + difference + "flip" + flip);
    // System.out.println(limiter);
  }
}

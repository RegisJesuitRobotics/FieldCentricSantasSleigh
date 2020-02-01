/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class autoDrive extends Command {
  double m_leftSpeed, m_rightSpeed;

  public autoDrive(double leftSpeed, double rightSpeed) {
    
    // Use requires() here to declare subsystem dependencies
    m_leftSpeed = leftSpeed;
    m_rightSpeed = rightSpeed;

    // eg. requires(chassis);
    requires(Robot.driveTrain);
    

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.moveLeft(m_leftSpeed);
    Robot.driveTrain.moveRight(m_rightSpeed);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  Robot.driveTrain.moveLeft(0);
  Robot.driveTrain.moveRight(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  this.end();
}
}

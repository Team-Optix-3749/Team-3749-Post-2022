// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utilities.*;
// import frc.robot.commands.*;
// import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}

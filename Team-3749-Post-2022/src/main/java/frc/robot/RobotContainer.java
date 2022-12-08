// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.LowerShoot;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
// import frc.robot.commands.*;
// import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  Shooter shooter = new Shooter();

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    XboxController pilot = new XboxController(1);

    JoystickButton A = new JoystickButton(pilot, Button.kA.value);

    A.whenPressed(new LowerShoot(shooter))
      .whenReleased(new InstantCommand(() -> {shooter.set(0);}, shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}

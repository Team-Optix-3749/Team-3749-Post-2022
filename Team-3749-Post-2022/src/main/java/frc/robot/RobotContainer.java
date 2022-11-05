// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utilities.*;
import frc.robot.commands.def.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.utilities.AutoGroups;

public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain();

  private final Shooter m_shooter = new Shooter();

  private final Intake m_intake = new Intake();

  private final Shintake m_shintake = new Shintake();

  // private final Elevator m_elevator = new Elevator();

  Xbox Pilot;
  Xbox Operator;
  POV PiPOV;
  POV OpPOV;

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    Pilot = new Xbox(0);
    Operator = new Xbox(1);

    PiPOV = new POV(new GenericHID(0));
    OpPOV = new POV(new GenericHID(1));

    // Pilot.y().whenPressed(new InstantCommand(m_elevator::rawClimbUp, m_elevator))
    //     .whenReleased(new InstantCommand(m_elevator::stopClimb, m_elevator));
    // Pilot.b().whenPressed(new InstantCommand(m_elevator::rawClimbDown, m_elevator))
    //     .whenReleased(new InstantCommand(m_elevator::stopClimb, m_elevator));

    Pilot.a().toggleWhenPressed(new InstantCommand(m_drivetrain::setCoast));
    Pilot.x().toggleWhenPressed(new InstantCommand(m_drivetrain::setBrake));

    m_drivetrain.setDefaultCommand(
        new Driving(m_drivetrain, Pilot::getLeftY, Pilot::getRightX));
    m_shooter.setDefaultCommand(
        new Shooting(m_shooter, Pilot, Operator, OpPOV));
    m_shintake.setDefaultCommand(
        new Shintaking(m_shintake, m_shooter, Pilot, Operator, PiPOV));
    m_intake.setDefaultCommand(
        new Intaking(m_intake, Pilot));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    AutoGroups autoGroup = new AutoGroups(m_drivetrain, m_intake, m_shooter, m_shintake);

    m_drivetrain.setBrake();
    return autoGroup.getOneBatb();
  }
}

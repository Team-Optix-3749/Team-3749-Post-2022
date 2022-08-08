// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utilities.*;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.elevator.*;
import frc.robot.oi.HandheldOI;
import frc.robot.oi.OISelector;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.drivetrain.DrivetrainIOTalonFX;
import frc.robot.subsystems.elevator.*;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class RobotContainer {

  private final Elevator m_elevator;

  private final Drivetrain m_drivetrain;

  private HandheldOI handheldOI;

  XboxController m_pilot = new XboxController(0);

  public RobotContainer() {

    m_drivetrain = new Drivetrain(new DrivetrainIOTalonFX() {});

    m_elevator = new Elevator(new ElevatorIONeo() {});

    updateOI();
  }

  public void updateOI() {
    CommandScheduler.getInstance().clearButtons();

    handheldOI = OISelector.findHandheldOI();

    handheldOI.getRaiseElevatorButton()
      .whenHeld(new RaiseElevator(m_elevator))
      .whenReleased(new StopElevator(m_elevator));
      
    handheldOI.getLowerElevatorButton()
      .whenHeld(new LowerElevator(m_elevator))
      .whenReleased(new StopElevator(m_elevator));
    

    m_drivetrain.setDefaultCommand(
      new TankDrive(
        m_drivetrain, handheldOI::getLeftX, handheldOI::getLeftY,
        handheldOI::getRightX, handheldOI::getRightY));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}

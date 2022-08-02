// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utilities.*;
import frc.robot.oi.HandheldOI;
import frc.robot.oi.OISelector;
// import frc.robot.commands.*;
import frc.robot.subsystems.elevator.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private final Elevator m_elevator;
  
  private HandheldOI handheld = new HandheldOI() {};

  XboxController m_pilot = new XboxController(0);

  public RobotContainer() {

    m_elevator = new Elevator(new ElevatorIO() {});

    updateOI();
  }

  public void updateOI() {
    CommandScheduler.getInstance().clearButtons();

    handheld = OISelector.findHandheldOI();

    handheld.getRaiseElevatorButton()
      .whenActive(new InstantCommand(m_elevator::raiseSlow, m_elevator));

    handheld.getLowerElevatorButton()
      .whenActive(new InstantCommand(m_elevator::lowerSlow, m_elevator));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}

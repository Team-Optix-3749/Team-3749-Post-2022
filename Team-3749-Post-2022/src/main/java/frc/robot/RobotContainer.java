// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utilities.*;
import frc.robot.commands.LowerElevator;
import frc.robot.commands.RaiseElevator;
import frc.robot.commands.StopElevator;
import frc.robot.oi.HandheldOI;
import frc.robot.oi.OISelector;
import frc.robot.oi.OverrideOI;
import frc.robot.subsystems.elevator.*;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private final Elevator m_elevator;

  private HandheldOI handheldOI;

  XboxController m_pilot = new XboxController(0);

  public RobotContainer() {

    m_elevator = new Elevator(new ElevatorIONeo() {});

    updateOI();
  }

  public void updateOI() {
    CommandScheduler.getInstance().clearButtons();

    // JoystickButton A = new JoystickButton(m_pilot, Button.kA.value);
    // JoystickButton B = new JoystickButton(m_pilot, Button.kB.value);

    // A.whenHeld(new RaiseElevator(m_elevator))
    //   .whenReleased(new StopElevator(m_elevator));

    // B.whenHeld(new LowerElevator(m_elevator))
    //   .whenReleased(new StopElevator(m_elevator));

    handheldOI = OISelector.findHandheldOI();

    handheldOI.getRaiseElevatorButton()
      .whenHeld(new RaiseElevator(m_elevator));
      
    handheldOI.getLowerElevatorButton()
      .whenHeld(new LowerElevator(m_elevator));

  }

  public Command getAutonomousCommand() {
    return null;
  }
}

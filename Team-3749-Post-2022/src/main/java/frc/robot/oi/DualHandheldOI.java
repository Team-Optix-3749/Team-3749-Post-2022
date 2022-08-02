// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.oi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/** Class for controlling the robot with two Xbox controllers. */
public class DualHandheldOI implements HandheldOI {
  private final XboxController driverController;
  private final XboxController operatorController;

  public DualHandheldOI(int driverPort, int operatorPort) {
    driverController = new XboxController(driverPort);
    operatorController = new XboxController(operatorPort);
  }

  @Override
  public Trigger getRaiseElevatorButton() {
    return new Trigger(operatorController::getAButton);
  }

  @Override
  public Trigger getLowerElevatorButton() {
    return new Trigger(operatorController::getBButton);
  }
}
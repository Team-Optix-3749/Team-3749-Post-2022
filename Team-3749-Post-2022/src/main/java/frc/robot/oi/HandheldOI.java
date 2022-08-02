// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Interface for all driver and operator controls (either single or dual Xbox).
 */
public interface HandheldOI {

  public default Trigger getRaiseElevatorButton() {
    return new Trigger();
  }

  public default Trigger getLowerElevatorButton() {
    return new Trigger();
  }
  
}
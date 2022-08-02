// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface ElevatorIO {

  public static class ElevatorIOInputs implements LoggableInputs {
    public double positionRad = 0.0;
    public double velocityRadPerSec = 0.0;
    public double appliedVolts = 0.0;
    public double currentAmps = 0.0;
    public double veloRadPerSec = 0.0;

    public void toLog(LogTable table) {
      table.put("Position Radians", positionRad);
      table.put("Velo (r/s)", velocityRadPerSec);
      table.put("Applied Volts", appliedVolts);
      table.put("Current Amps", currentAmps);
      table.put("Velocity in Radians/Sec", veloRadPerSec);
    }

    public void fromLog(LogTable table) {
      positionRad = table.getDouble("Position Radians", positionRad);
      velocityRadPerSec = table.getDouble("Velo (r/s)", velocityRadPerSec);
      appliedVolts = table.getDouble("Applied Volts", appliedVolts);
      currentAmps = table.getDouble("Current Amps", currentAmps);
      veloRadPerSec = table.getDouble("Velocity in Radians/Sec", veloRadPerSec);
    }
  }

  public default void updateInputs(ElevatorIOInputs inputs) {
  }

  public default void setVoltage(double voltage) {
  }

  public default void setPercent(double percent) {
    
  }

  public default void setBrakeMode(boolean enable) {
  }
}

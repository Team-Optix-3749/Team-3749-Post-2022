package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorIO.ElevatorIOInputs;

public class Elevator extends SubsystemBase {

  private final ElevatorIO io;
  private final ElevatorIOInputs inputs = new ElevatorIOInputs();

  public Elevator(ElevatorIO io) {
    this.io = io;

    io.setBrakeMode(true);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.getInstance().processInputs("Elevator", inputs);
  }

  public void raiseSlow() {
    io.setVoltage(1);
  }

  public void lowerSlow() {
    io.setVoltage(-1);
  }

}

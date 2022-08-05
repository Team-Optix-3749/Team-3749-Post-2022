package frc.robot.commands;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.elevator.Elevator;

public class StopElevator extends CommandBase  {

  private final Elevator m_elevator;

  public StopElevator( Elevator elevator ) {
    this.m_elevator = elevator;

    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    m_elevator.setPercent(0);
  }

  @Override
  public void execute() {
    Logger.getInstance().recordOutput("ActiveCommands/RaiseElevator", false);
    Logger.getInstance().recordOutput("ActiveCommands/LowerElevator", false);

    m_elevator.setPercent(.0);
  }

  @Override
  public void end(boolean isFinished) {
    m_elevator.setPercent(0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}


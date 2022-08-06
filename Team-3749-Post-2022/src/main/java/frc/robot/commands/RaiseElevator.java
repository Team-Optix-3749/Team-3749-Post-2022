package frc.robot.commands;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.elevator.Elevator;

public class RaiseElevator extends CommandBase  {

  private final Elevator m_elevator;

  public RaiseElevator( Elevator elevator ) {
    this.m_elevator = elevator;

    addRequirements(elevator);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    Logger.getInstance().recordOutput("ActiveCommands/RaiseElevator", true);

    m_elevator.setPercent(.1);
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


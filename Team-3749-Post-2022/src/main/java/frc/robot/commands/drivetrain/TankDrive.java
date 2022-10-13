package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class TankDrive extends CommandBase {
  
  private final Drivetrain m_drivetrain;

  private final DoubleSupplier leftY, rightY;

  public TankDrive( Drivetrain drivetrain, 
      DoubleSupplier leftY, DoubleSupplier rightY ) {

    this.leftY = leftY;
    this.rightY = rightY;

    this.m_drivetrain = drivetrain;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    m_drivetrain.driveRaw(leftY.getAsDouble() * 0.5, rightY.getAsDouble() * 0.5);
  }

  @Override
  public void end(boolean isFinished) {

  }

  @Override 
  public boolean isFinished() {
    return false;
  }

}

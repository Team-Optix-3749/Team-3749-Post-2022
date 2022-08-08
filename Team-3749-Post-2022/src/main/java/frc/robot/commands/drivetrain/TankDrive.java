package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class TankDrive extends CommandBase {
  
  private final Drivetrain m_drivetrain;

  private final DoubleSupplier leftX, leftY, rightX, rightY;

  public TankDrive( Drivetrain drivetrain, DoubleSupplier leftX, DoubleSupplier leftY,
      DoubleSupplier rightX, DoubleSupplier rightY ) {

    this.leftX = leftX;
    this.leftY = leftY;
    this.rightX = rightX;
    this.rightY = rightY;

    this.m_drivetrain = drivetrain;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    m_drivetrain.drivePercent(leftY.getAsDouble(), rightY.getAsDouble());
  }

  @Override
  public void end(boolean isFinished) {

  }

  @Override 
  public boolean isFinished() {
    return false;
  }

}

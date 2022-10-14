package frc.robot.commands.def;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shooting extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private Shooter m_shooter;

  private Xbox Pilot;
  private Xbox Operator;
  private POV OpPOV;

  public Shooting(Shooter shooter, Xbox pilot, Xbox operator, POV opPOV) {
    m_shooter = shooter;
    Pilot = pilot;
    Operator = operator;
    OpPOV = opPOV;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    m_shooter.stopMotor();
    m_shooter.stopTurret();
    SmartDashboard.putNumber("TEST", 100);
  }

  @Override
  public void execute() {

    double turretControl = Constants.round(Operator.getRightX());

    // Op right joystick = turret control (setTurretControl) 
    if (Math.abs(turretControl) >= .1) {
      m_shooter.setTurretMotor(turretControl * Constants.Shooter.turretSpeed);
    
    // Op right bumper = vision align (visionAlign) 
    } else if (Operator.rightBumper().get()) {
      m_shooter.visionAlign();

    // Op left bumper = reset turret (resetTurret)
    } else if (Operator.leftBumper().get()) {
      m_shooter.resetTurret();
    
    } else {
      m_shooter.stopTurret();

    }
    
    // Op right trigger = SHOOT upper hub
    if (Pilot.getRightTrigger()) {
      m_shooter.setRPM(Constants.Shooter.upperRPM);
    
    // Op left trigger = SHOOT lower hub 
    } else if (Operator.getLeftTrigger()) {
      m_shooter.setRPM(Constants.Shooter.lowerRPM);
    
    // Pilot left bumper = SHOOT lower hub
    } else if (Pilot.leftBumper().get()) {
      m_shooter.setRPM(Constants.Shooter.lowerRPM);
    
    // Op Dpad up = RUN shooter upper
    } else if (OpPOV.up().get()) {
      m_shooter.setRPM(Constants.Shooter.upperRPM);
    
    // Op Dpad down = RUN shooter lower
    } else if (OpPOV.down().get()) {
      m_shooter.setRPM(Constants.Shooter.lowerRPM);
      
    } else {
      m_shooter.stopMotor();
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

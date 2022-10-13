package frc.robot.commands.def;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shintaking extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private Shintake m_shintake;
  private Shooter m_shooter;

  private Xbox Pilot;
  private Xbox Operator;
  private POV PiPOV;

  public Shintaking(Shintake shintake, Shooter shooter,
      Xbox pilot, Xbox operator, POV piPOV) {
    Pilot = pilot;
    m_shooter = shooter;
    m_shintake = shintake;
    Operator = operator;
    PiPOV = piPOV;
    addRequirements(shintake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    // Operator A = shintake PID
    if (Operator.a().get()) {
      m_shintake.setShintakePID();

      // Pilot right trigger = set shintake (upper hub)
    } else if (Pilot.getRightTrigger()) {
      if (m_shooter.getRPM() > Constants.Shooter.upperRPM) {
        m_shintake.setShintake();
      } else {
        m_shintake.stopShintake();
      }

      // Pilot left trigger = set shintake (lower hub)
    } else if (Operator.getLeftTrigger()) {
      if (m_shooter.getRPM() > Constants.Shooter.lowerRPM - 15) {
        m_shintake.setShintake();
      } else {
        m_shintake.stopShintake();
      }

      // Pilot left POV = hold shintake
    } else if (PiPOV.left().get()) {
      m_shintake.holdShintake();

      // Pilot right POV = reverse shintake
    } else if (PiPOV.right().get()) {
      m_shintake.setShintakeReverse();

      // Pilot left trigger = hold shintake (intake)
    } else if (Pilot.getLeftTrigger()) {
      m_shintake.holdShintake();

      // Pilot left trigger = hold shintake (intake)
    } else if (Pilot.getRightTrigger()) {
      m_shintake.setShintakeReverse();

      // Pilot left bumper = lower hub (setShintake)
    } else if (Pilot.leftBumper().get()) {
      m_shintake.setShintake();

      // Pilot right bumper = slow outtake (setShintake)
    } else if (Pilot.rightBumper().get()) {
      m_shintake.setShintake();

    } else {
      m_shintake.stopShintake();

    }
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
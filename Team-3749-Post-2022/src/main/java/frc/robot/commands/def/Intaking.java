package frc.robot.commands.def;

import frc.robot.subsystems.*;
import frc.robot.utilities.Xbox;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Intaking extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private Intake m_intake;

  private Xbox Pilot;

  public Intaking(Intake intake,
      Xbox pilot) {
    m_intake = intake;
    Pilot = pilot;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    // Pilot right trigger = set intake (setIntake & intakeFwd)
    if (Pilot.getLeftTrigger()) {
      m_intake.setIntake();
      m_intake.intakeFwd();

    // Pilot left trigger = outtake (setIntakeReverse & intakeFwd)
    // } else if (Pilot.getRightTrigger()) {
    //   m_intake.setIntakeReverse();
    //   m_intake.intakeFwd();

    // Pilot right bumper = slow outtake (setIntakeHalfReverse & intakeFwd)
    } else if (Pilot.rightBumper().get()) {
      m_intake.setIntakeHalfReverse();
      m_intake.intakeFwd();

    } else {
      m_intake.intakeRev();
      m_intake.stopIntake();
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
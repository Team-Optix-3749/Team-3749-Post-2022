package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Intake extends SubsystemBase {
  private CANSparkMax m_intakeMotor = new CANSparkMax(Constants.Intake.intakeMotor, MotorType.kBrushless);

  private Compressor m_comp = new Compressor(0, PneumaticsModuleType.CTREPCM);
  private DoubleSolenoid m_rightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
      Constants.Intake.kSolenoidForwardChannel[0],
      Constants.Intake.kSolenoidReverseChannel[0]);
  private DoubleSolenoid m_leftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
      Constants.Intake.kSolenoidForwardChannel[1],
      Constants.Intake.kSolenoidReverseChannel[1]);

  public Intake() {
    m_intakeMotor.setIdleMode(IdleMode.kBrake);
    m_intakeMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    startCompressor();
  }

  public void setIntake() {
    m_intakeMotor.set(-Constants.Intake.kIntakeSpeed);
  }

  public void setIntakeReverse() {
    m_intakeMotor.set(-Constants.Intake.kIntakeSpeed);
  }

  public void setIntakeHalfReverse() {
    m_intakeMotor.set(-Constants.Intake.kIntakeSpeed * 0.25);
  }

  public void stopMotors() {
    m_intakeMotor.set(0);
  }

  public void stopIntake() {
    m_intakeMotor.stopMotor();
  }

  public void startCompressor() {
    m_comp.enableDigital();
  }

  public void stopCompressor() {
    m_comp.disable();
  }

  public void intakeFwd() {
    m_rightPiston.set(DoubleSolenoid.Value.kForward);
    m_leftPiston.set(DoubleSolenoid.Value.kForward);
  }

  public void intakeRev() {
    m_rightPiston.set(DoubleSolenoid.Value.kReverse);
    m_leftPiston.set(DoubleSolenoid.Value.kReverse);
  }
}

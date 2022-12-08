package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

public class Shooter extends SubsystemBase {
  private WPI_TalonFX m_leftShooterMotor = new WPI_TalonFX(Constants.Shooter.leftShooterMotor);
  private WPI_TalonFX m_rightShooterMotor = new WPI_TalonFX(Constants.Shooter.rightShooterMotor);

  public Shooter() {
    m_leftShooterMotor.setInverted(true);
    m_leftShooterMotor.setNeutralMode(NeutralMode.Coast);
    m_rightShooterMotor.setNeutralMode(NeutralMode.Coast);
  }

  public void set(double speed) {
    m_leftShooterMotor.set(speed);
    m_rightShooterMotor.set(speed);
  }

  @Override
  public void periodic() {
    System.out.println(Constants.Shooter.lowerSpeed.get().doubleValue());
  }

}

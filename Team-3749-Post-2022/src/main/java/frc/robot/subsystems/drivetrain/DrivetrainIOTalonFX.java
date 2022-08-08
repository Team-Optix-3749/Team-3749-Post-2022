
package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.utilities.Constants;

public class DrivetrainIOTalonFX implements DrivetrainIO {
  private final WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.Drivetrain.leftFront);
  private final WPI_TalonFX m_leftRear = new WPI_TalonFX(Constants.Drivetrain.leftBack);

  private final WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.Drivetrain.rightFront);
  private final WPI_TalonFX m_rightRear = new WPI_TalonFX(Constants.Drivetrain.rightBack);
  
  private static final double encoderTicksPerRev = 2048.0;
  private static final double radiansPerTick = (2.0 * Math.PI) / encoderTicksPerRev;
  private static final boolean reverseOutputLeft = false;
  private static final boolean reverseOutputRight = true;
  private static final boolean reverseSensorLeft = false;
  private static final boolean reverseSensorRight = false;

  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  public DrivetrainIOTalonFX() {
    m_leftFront.configFactoryDefault();
    m_leftRear.configFactoryDefault();
    m_rightRear.configFactoryDefault();
    m_rightFront.configFactoryDefault();

    m_leftFront.configVoltageCompSaturation(12);
    m_rightFront.configVoltageCompSaturation(12);

    m_leftFront.setInverted(reverseOutputLeft);
    m_leftRear.setInverted(InvertType.FollowMaster);
    m_rightFront.setInverted(reverseOutputRight);
    m_rightRear.setInverted(InvertType.FollowMaster);

    m_leftFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 100);
    m_rightFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 100);
    m_leftFront.setSelectedSensorPosition(0);
    m_rightFront.setSelectedSensorPosition(0);
    m_leftFront.setSensorPhase(reverseSensorLeft);
    m_rightFront.setSensorPhase(reverseSensorRight);

    m_rightRear.follow(m_rightFront);
    m_leftRear.follow(m_leftFront);

    m_gyro.zeroYaw();
  }

  @Override
  public void updateInputs(DrivetrainIOInputs inputs) {
    inputs.leftPositionRad = m_leftFront.getSelectedSensorPosition() * radiansPerTick;
    inputs.rightPositionRad = m_rightFront.getSelectedSensorPosition() * radiansPerTick;
    inputs.leftVelocityRadPerSec = m_leftFront.getSelectedSensorVelocity() * radiansPerTick * 10;
    inputs.rightVelocityRadPerSec = m_rightFront.getSelectedSensorVelocity() * radiansPerTick * 10;
    inputs.leftCurrentAmps = new double[] { m_leftFront.getStatorCurrent() };
    inputs.rightCurrentAmps = new double[] { m_rightFront.getStatorCurrent() };
    inputs.gyroPositionRad = Math.toRadians(m_gyro.getAngle());
    inputs.gyroVelocityRadPerSec = Math.toRadians(m_gyro.getRawGyroZ());
  }

  @Override
  public void setVoltage(double leftVoltage, double rightVoltage) {
    m_leftFront.set(ControlMode.PercentOutput, leftVoltage / 12);
    m_rightFront.set(ControlMode.PercentOutput, rightVoltage / 12);
  }

  @Override
  public void setVelocity(double leftVeloRPS, double rightVeloRPS, 
      double leftFFVolts, double rightFFVolts) {
    double leftTicksPer100Ms = Units.radiansToRotations(leftVeloRPS)
      * encoderTicksPerRev / 10.0;
    double rightTicksPer100Ms = Units.radiansToRotations(rightVeloRPS)
      * encoderTicksPerRev / 10.0;
    m_leftFront.set(ControlMode.Velocity, leftTicksPer100Ms,
        DemandType.ArbitraryFeedForward, leftFFVolts / 12.0);
    m_rightFront.set(ControlMode.Velocity, rightTicksPer100Ms,
        DemandType.ArbitraryFeedForward, rightFFVolts / 12.0);
  }

  @Override
  public void setBrakeMode(boolean enable) {
    NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
    m_leftFront.setNeutralMode(mode);
    m_leftRear.setNeutralMode(mode);
    m_rightFront.setNeutralMode(mode);
    m_rightRear.setNeutralMode(mode);
  }

  @Override
  public void configurePID(double kp, double ki, double kd) {
    m_leftFront.config_kP(0, kp);
    m_leftFront.config_kI(0, ki);
    m_leftFront.config_kD(0, kd);
    m_rightFront.config_kP(0, kp);
    m_rightFront.config_kI(0, ki);
    m_rightFront.config_kD(0, kd);
  }
}
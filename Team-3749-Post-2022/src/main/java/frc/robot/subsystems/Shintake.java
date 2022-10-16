package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

public class Shintake extends SubsystemBase {
  
  private CANSparkMax m_shintakeFront = new CANSparkMax(Constants.Shintake.shintakeFront, MotorType.kBrushless);
  private CANSparkMax m_shintakeBack = new CANSparkMax(Constants.Shintake.shintakeBack, MotorType.kBrushless);

  private SparkMaxPIDController m_shintakeFrontPIDController;
  private SparkMaxPIDController m_shintakeBackPIDController;
  private RelativeEncoder m_shintakeFrontEncoder;
  private RelativeEncoder m_shintakeBackEncoder;

  public Shintake() {
    m_shintakeFront.setIdleMode(IdleMode.kBrake);
    m_shintakeBack.setIdleMode(IdleMode.kBrake);

    m_shintakeFrontEncoder = m_shintakeFront.getEncoder();
    m_shintakeFrontPIDController = m_shintakeFront.getPIDController();
    m_shintakeFrontPIDController.setFeedbackDevice(m_shintakeFrontEncoder);
    m_shintakeFrontPIDController.setP(Constants.Shintake.kP);
    m_shintakeFrontPIDController.setI(Constants.Shintake.kI);
    m_shintakeFrontPIDController.setD(Constants.Shintake.kD);
    m_shintakeFrontPIDController.setOutputRange(-1, 1);

    m_shintakeBackEncoder = m_shintakeBack.getEncoder();
    m_shintakeBackPIDController = m_shintakeBack.getPIDController();
    m_shintakeBackPIDController.setFeedbackDevice(m_shintakeBackEncoder);
    m_shintakeBackPIDController.setP(Constants.Shintake.kP);
    m_shintakeBackPIDController.setI(Constants.Shintake.kI);
    m_shintakeBackPIDController.setD(Constants.Shintake.kD);
    m_shintakeBackPIDController.setOutputRange(-1, 1);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("SHINTAKE FRONT ENCODER", m_shintakeFrontEncoder.getPosition());
    SmartDashboard.putNumber("SHINTAKE BACK ENCODER", m_shintakeBackEncoder.getPosition());
  }

  public void holdShintake() {
    m_shintakeFront.set(Constants.Shintake.kShintakeSpeed);
    m_shintakeBack.set(-Constants.Shintake.kShintakeSpeed);
  }
  
  public void holdShintakeFix() {
    m_shintakeFront.set(-Constants.Shintake.kShintakeSpeed);
    m_shintakeBack.set(-Constants.Shintake.kShintakeSpeed);
  }

  public void setShintake() {
    m_shintakeFront.set(Constants.Shintake.kShintakeSpeed + 0.01);
    m_shintakeBack.set(Constants.Shintake.kShintakeSpeed);
  }

  public void setShintakeFix() {
    m_shintakeFront.set(-Constants.Shintake.kShintakeSpeed + 0.01);
    m_shintakeBack.set(Constants.Shintake.kShintakeSpeed);
  }

  public void setShintake(double powerCorrection) {
    m_shintakeFront.set(Constants.Shintake.kShintakeSpeed + 0.01 + powerCorrection);
    m_shintakeBack.set(Constants.Shintake.kShintakeSpeed + powerCorrection);
  }

  public void set24() {
    m_shintakeBack.set(0.5);
  }

  public void setShintakePID() {
    // PIDController frontPIDController = m_frontEncoder.getVelocity() >
    // Constants.Shintake.targetRPM - 1500 ? m_pidControllerLow :
    // m_pidControllerHigh;
    // double frontVel = frontPIDController.calculate(m_frontEncoder.getVelocity(),
    // Constants.Shintake.targetRPM);
    // m_shintakeFront.set(frontVel);

    // PIDController backPIDController = m_backEncoder.getVelocity() >
    // Constants.Shintake.targetRPM - 1500 ? m_pidControllerLow :
    // m_pidControllerHigh;
    // double backVel = backPIDController.calculate(m_backEncoder.getVelocity(),
    // Constants.Shintake.targetRPM);
    // m_shintakeBack.set(backVel);

    // if (m_frontEncoder.getVelocity() < Constants.Shintake.targetRPM - 500)
    // m_shintakeFront.set(frontVel);
    // if (m_backEncoder.getVelocity() < Constants.Shintake.targetRPM - 500)
    // m_shintakeBack.set(backVel);

    // **************** LAR SPEC PID CODE ****************
    // double frontVel = m_pidControllerHigh.calculate(m_frontEncoder.getVelocity(),
    // Constants.Shintake.targetRPM);
    // double backVel = m_pidControllerHigh.calculate(m_backEncoder.getVelocity(),
    // Constants.Shintake.targetRPM);
    // m_shintakeFront.set(frontVel);
    // m_shintakeBack.set(backVel);

    // **************** CHAMPS SPEC PID CODE ****************
    m_shintakeFrontPIDController.setReference(Constants.Shintake.targetRPM, ControlType.kVelocity);
    m_shintakeBackPIDController.setReference(Constants.Shintake.targetRPM, ControlType.kVelocity);
  }

  public void setShintakeReverse() {
    m_shintakeFront.set(-Constants.Shintake.kShintakeSpeed);
    m_shintakeBack.set(-Constants.Shintake.kShintakeSpeed);
  }

  public void stopShintake() {
    m_shintakeFront.stopMotor();
    m_shintakeBack.stopMotor();
  }

  public void setShintakeVoltage(double volts) {
    m_shintakeFront.setVoltage(volts);
    m_shintakeBack.setVoltage(volts);

  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;

public class ElevatorIONeo implements ElevatorIO {

  private final CANSparkMax m_chain = new CANSparkMax(25, MotorType.kBrushless);
  private final RelativeEncoder m_chainEncoder = m_chain.getEncoder();

  public ElevatorIONeo() {
    m_chain.restoreFactoryDefaults();

    m_chain.setSmartCurrentLimit(40);

    m_chain.enableVoltageCompensation(12.0);

    m_chain.setCANTimeout(0);

    m_chain.setInverted(false);

  }

  public void updateInputs(ElevatorIOInputs inputs) {
    inputs.positionRad = Units.rotationsToRadians(m_chainEncoder.getPosition());
    inputs.velocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(m_chainEncoder.getVelocity());
    inputs.appliedVolts = m_chain.getBusVoltage() * RobotController.getBatteryVoltage();
    inputs.currentAmps = m_chain.getOutputCurrent();
  }

  @Override
  public void setVoltage(double voltage) {
    m_chain.setVoltage(voltage);
  }

  @Override
  public void setBrakeMode(boolean enable) {
    m_chain.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }
  
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.oi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** Class for controlling the robot with a single Xbox controller. */
public class SingleHandheldOI implements HandheldOI {
  private final XboxController controller;

  public SingleHandheldOI(int port) {
    controller = new XboxController(port);
  }

  @Override
  public JoystickButton getRaiseElevatorButton() {
    return new JoystickButton(controller, Button.kA.value);
  }

  @Override
  public JoystickButton getLowerElevatorButton() {
    return new JoystickButton(controller, Button.kB.value);
  }

  @Override
  public double getLeftX() {
    return controller.getLeftX();
  }
  
  @Override
  public double getLeftY() {
    return controller.getLeftY();
  }
  
  @Override
  public double getRightX() {
    return controller.getRightX();
  }

  @Override
  public double getRightY() {
    return controller.getRightY();
  }
}
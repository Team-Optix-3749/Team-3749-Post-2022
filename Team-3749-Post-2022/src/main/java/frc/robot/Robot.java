// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedNetworkTables;
import org.littletonrobotics.junction.io.ByteLogReceiver;
import org.littletonrobotics.junction.io.ByteLogReplay;
import org.littletonrobotics.junction.io.LogSocketServer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends LoggedRobot  {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    setUseTiming(isReal()); // Run as fast as possible during replay
    LoggedNetworkTables.getInstance().addTable("/SmartDashboard"); // Log & replay "SmartDashboard" values (no tables are logged by default).
    Logger.getInstance().recordMetadata("Team Optix Post Season 2022", ""); // Set a metadata value

    if (isReal()) {
      Logger.getInstance().addDataReceiver(new ByteLogReceiver("/media/sda1/")); // Log to USB stick (name will be selected automatically)
      Logger.getInstance().addDataReceiver(new LogSocketServer(5800)); // Provide log data over the network, viewable in Advantage Scope.
    } else {
      String path = ByteLogReplay.promptForPath(); // Prompt the user for a file path on the command line
      Logger.getInstance().setReplaySource(new ByteLogReplay(path)); // Read log file for replay
      Logger.getInstance().addDataReceiver(new ByteLogReceiver(ByteLogReceiver.addPathSuffix(path, "_sim"))); // Save replay results to a new log with the "_sim" suffix
    }

    Logger.getInstance().start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.

    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    m_robotContainer.updateOI();
    CommandScheduler.getInstance().run();

    Logger.getInstance().recordOutput("SineWave",
        Math.sin(Logger.getInstance().getTimestamp() / 2) * 10);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}

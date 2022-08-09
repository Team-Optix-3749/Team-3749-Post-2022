
package frc.robot.subsystems.drivetrain;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N5;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import frc.robot.utilities.SmartData;
import frc.robot.subsystems.drivetrain.DrivetrainIO.DrivetrainIOInputs;

public class Drivetrain extends SubsystemBase {
  private final double wheelRadiusMeters;
  private final double maxVelocityMetersPerSec;
  private final double maxAccelerationMetersPerSecSq;
  private final double trackWidthMeters;
  private final SmartData<Double> kP = new SmartData<Double>("kP", Constants.Auto.kPDriveVel);
  private final SmartData<Double> kI = new SmartData<Double>("kI", 0.0);
  private final SmartData<Double> kD = new SmartData<Double>("kD", 0.0);
  private final double leftKS;
  private final double leftKV;
  private final double leftKA;
  private final double rightKS;
  private final double rightKV;
  private final double rightKA;

  private final DrivetrainIO io;
  private final DrivetrainIOInputs inputs = new DrivetrainIOInputs();

  private final SimpleMotorFeedforward leftFFModel;
  private final SimpleMotorFeedforward rightFFModel;

  private final Matrix<N5, N1> stateStdDev =
      new MatBuilder<>(Nat.N5(), Nat.N1()).fill(0.02, 0.02, 0.01, 0.02, 0.02);
  private final Matrix<N3, N1> localMeasurementStdDevs =
      new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01);
  private final Matrix<N3, N1> globalMeasurementStdDevs =
      new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01);

  private double lastKP = 0.0;
  private double lastKI = 0.0;
  private double lastKD = 0.0;

  private DifferentialDrivePoseEstimator odometry;
  private Field2d field2d = new Field2d();
  private double baseDistanceLeft = 0.0;
  private double baseDistanceRight = 0.0;

  /** Creates a new DriveTrain. */
  public Drivetrain(DrivetrainIO io) {
    this.io = io;
    wheelRadiusMeters = Units.inchesToMeters(3.0);
    maxVelocityMetersPerSec = 1.0;
    maxAccelerationMetersPerSecSq = 1.0;
    trackWidthMeters = 1.0;
    leftKS = 0.0;
    leftKV = 0.0;
    leftKA = 0.0;
    rightKS = 0.0;
    rightKV = 0.0;
    rightKA = 0.0;

    io.setBrakeMode(true);
    io.configurePID(kP.get(), kI.get(), kD.get());
    lastKP = kP.get();
    lastKI = kI.get();
    lastKD = kD.get();

    leftFFModel = new SimpleMotorFeedforward(leftKS, leftKV, leftKA);
    rightFFModel = new SimpleMotorFeedforward(rightKS, rightKV, rightKA);
    SmartDashboard.putData("Odometry", field2d);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    Logger.getInstance().processInputs("DriveTrain", inputs);

    Logger.getInstance().recordOutput("DriveTrain/LeftVelocityMetersPerSec",
        getLeftVelocityMetersPerSec());
    Logger.getInstance().recordOutput("DriveTrain/RightVelocityMetersPerSec",
        getRightVelocityMetersPerSec());

    if (odometry == null) {
      odometry = new DifferentialDrivePoseEstimator(
          new Rotation2d(inputs.gyroPositionRad * -1), new Pose2d(),
          stateStdDev, localMeasurementStdDevs, globalMeasurementStdDevs);
      baseDistanceLeft = getLeftPositionMeters();
      baseDistanceRight = getRightPositionMeters();
    } else {
      Pose2d pose = odometry.updateWithTime(Timer.getFPGATimestamp(),
          new Rotation2d(inputs.gyroPositionRad * -1),
          new DifferentialDriveWheelSpeeds(getLeftVelocityMetersPerSec(),
              getRightVelocityMetersPerSec()),
          getLeftPositionMeters() - baseDistanceLeft,
          getRightPositionMeters() - baseDistanceRight);

      Logger.getInstance().recordOutput("Odometry/Robot", new double[] {
          pose.getX(), pose.getY(), pose.getRotation().getRadians()});

      field2d.setRobotPose(pose);
    }

  }

  public void driveVoltage(double leftVolts, double rightVolts) {
    io.setVoltage(leftVolts, rightVolts);
  }

  public void drivePercent(double leftPercent, double rightPercent) {
    driveVelocity(leftPercent * maxVelocityMetersPerSec,
        rightPercent * maxVelocityMetersPerSec);
  }

  /* sets drivetrain to velocity in meters/second */
  public void driveVelocity(double leftVelo, double rightVelo) {
    double leftVeloRPS = leftVelo / wheelRadiusMeters;
    double rightVeloRPS =
        rightVelo / wheelRadiusMeters;
    double leftVolts = leftFFModel.calculate(leftVeloRPS);
    double rightVolts = rightFFModel.calculate(rightVeloRPS);

    if (Constants.Drivetrain.openLoop) {
      io.setVoltage(leftVolts, rightVolts);
    } else {
      io.setVelocity(leftVeloRPS, rightVeloRPS, leftVolts,
          rightVolts);
    }
  }

  public void stop() {
    if (Constants.Drivetrain.openLoop) {
      io.setVoltage(0.0, 0.0);
    } else {
      io.setVelocity(0.0, 0.0, 0.0, 0.0);
    }
  }

  /**
   * Returns the current odometry position.
   */
  public Pose2d getPose() {
    return odometry.getEstimatedPosition();
  }

  /**
   * Reset the current odometry position.
   */
  public void setPose(Pose2d pose) {
    odometry.resetPosition(pose, new Rotation2d(inputs.gyroPositionRad * -1));
    baseDistanceLeft = getLeftPositionMeters();
    baseDistanceRight = getRightPositionMeters();
  }

  /**
   * Adds a new vision measurement to update odometry.
   */
  public void addVisionMeasurement(Pose2d pose, double timestamp) {
    Logger.getInstance().recordOutput("Odometry/Ghost", new double[] {
        pose.getX(), pose.getY(), pose.getRotation().getRadians()});

    Logger.getInstance().recordOutput("Odometry/Vision",
        new double[] {0.0, 0.0});

    odometry.addVisionMeasurement(pose, timestamp);
  }

  /**
   * Returns the position of the left drive in meters.
   */
  public double getLeftPositionMeters() {
    return inputs.leftPositionRad * wheelRadiusMeters;
  }

  /**
   * Returns the position of the right drive in meters.
   */
  public double getRightPositionMeters() {
    return inputs.rightPositionRad * wheelRadiusMeters;
  }

  /**
   * Returns the velocity of the left drive in meters per second.
   */
  public double getLeftVelocityMetersPerSec() {
    return inputs.leftVelocityRadPerSec * wheelRadiusMeters;
  }

  /**
   * Returns the velocity of the right drive in meters per second.
   */
  public double getRightVelocityMetersPerSec() {
    return inputs.rightVelocityRadPerSec * wheelRadiusMeters;
  }

  /** Returns the maximum velocity (meters/second) */
  public double getMaxVelocityMetersPerSec() {
    return maxVelocityMetersPerSec;
  }

  /** Returns the maximum acceleration (meters/second^2) */
  public double getMaxAccelerationMetersPerSecSq() {
    return maxAccelerationMetersPerSecSq;
  }

  /** Returns the empirical track width (meters) */
  public double getTrackWidthMeters() {
    return trackWidthMeters;
  }

  /** Returns the average kS value. */
  public double getKS() {
    return (leftKS + rightKS) / 2;
  }

  /** Returns the average kV value (meters). */
  public double getKV() {
    return ((leftKV + rightKV) / 2) * wheelRadiusMeters;
  }

  /** Returns the average kA value (meters). */
  public double getKA() {
    return ((leftKA + rightKA) / 2) * wheelRadiusMeters;
  }

}

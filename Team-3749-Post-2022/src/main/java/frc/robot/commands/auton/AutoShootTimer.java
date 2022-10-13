package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

public class AutoShootTimer extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final Shintake m_shintake;
    private final Timer t = new Timer();
    private final int m_addRPM;

    public AutoShootTimer(Shooter shooter, Shintake shintake, int addedRPM) {
        m_shooter = shooter;
        m_shintake = shintake;
        m_addRPM = addedRPM;
        addRequirements(shooter, shintake);
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
    }

    @Override
    public void execute() {
        // m_shooter.visionAlign();
        m_shooter.setRPM(Constants.Shooter.upperRPM + m_addRPM);
        
        if (t.get() > 2.5) m_shintake.setShintake(0.015);

    }

    @Override
    public void end(boolean interrupted) {
        // m_shooter.stopTurret();
        m_shooter.stopMotor();
        m_shintake.stopShintake();
        t.reset();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (t.get() >= 5);
    }
}

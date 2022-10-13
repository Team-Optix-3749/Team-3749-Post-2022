package frc.robot.commands.auton;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final Shintake m_shintake;
    private final Timer t = new Timer();

    public AutoShoot(Shooter shooter, Shintake intake) {
        m_shooter = shooter;
        m_shintake = intake;
        addRequirements(shooter, m_shintake);
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
    }

    @Override
    public void execute() {
        m_shooter.setRPM(Constants.Shooter.upperRPM);
        
        if (m_shooter.getRPM() > Constants.Shooter.upperRPM - 7) 
            m_shintake.setShintakePID();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopTurret();
        m_shooter.stopMotor();
        m_shintake.stopShintake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

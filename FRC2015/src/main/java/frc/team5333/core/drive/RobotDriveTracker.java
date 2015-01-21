package frc.team5333.core.drive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.StateTracker;
import frc.team5333.lib.Ports;
import frc.team5333.lib.RobotState;
import frc.team5333.lib.StateListener;

/**
 * Responsible for driving the robot's motors
 *
 * @author Jaci
 */
public class RobotDriveTracker implements StateListener {

    static double left;
    static double right;

    static RobotDrive drive;
    static Talon leftMotor;
    static Talon rightMotor;

    public static void prestart() {
        leftMotor = new Talon(Ports.LEFT_DRIVE.getPort());
        rightMotor = new Talon(Ports.RIGHT_DRIVE.getPort());
        drive = new RobotDrive(leftMotor, rightMotor);
        StateTracker.addTicker(new RobotDriveTracker());
    }

    public static void setLeft(double d) {
        left = d;
    }

    public static void setRight(double d) {
        right = d;
    }

    public static void setBoth(double d) {
        setLeft(d);
        setRight(d);
    }

    public static double getLeft() {
        return left;
    }

    public static double getRight() {
        return right;
    }

    public static void update() {
        drive.tankDrive(left, right);
    }

    @Override
    public void tickState(RobotState state) {
        if (state != RobotState.DISABLED)
            update();
    }

    @Override
    public void transitionState(RobotState state, RobotState oldState) {
        if (state == RobotState.DISABLED)
            setBoth(0D);
    }
}

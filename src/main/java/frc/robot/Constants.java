package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public class Constants {

    public static final class InputDevices {

        public static final int GAMEPAD_PORT = 0;

    }

    public static final class DriveConstants {

        static public final double WHEEL_BASE_WIDTH_M = Units.inchesToMeters(22);
        static public final double WHEEL_RADIUS_M = 0.0508; //Units.inchesToMeters(4.0/2.0); //four inch (diameter) wheels
        static public final double ROBOT_MASS_kg = Units.lbsToKilograms(80);
        static public final double ROBOT_MOI_KGM2 = 1.0/12.0 * ROBOT_MASS_kg * Math.pow((WHEEL_BASE_WIDTH_M*1.1),2) * 2; //Model moment of intertia as a square slab slightly bigger than wheelbase with axis through center
        // Drivetrain Performance Mechanical limits
        static public final double MAX_FWD_REV_SPEED_MPS = Units.feetToMeters(19.0);
        static public final double MAX_STRAFE_SPEED_MPS = Units.feetToMeters(19.0);
        static public final double MAX_ROTATE_SPEED_RAD_PER_SEC = Units.degreesToRadians(720.0);
        static public final double MAX_TRANSLATE_ACCEL_MPS2 = MAX_FWD_REV_SPEED_MPS/0.125; //0-full time of 0.25 second
        static public final double MAX_ROTATE_ACCEL_RAD_PER_SEC_2 = MAX_ROTATE_SPEED_RAD_PER_SEC/0.25; //0-full time of 0.25 second
        
    // HELPER ORGANIZATION CONSTANTS
        static public final int FL = 0; // Front Left Module Index
        static public final int FR = 1; // Front Right Module Index
        static public final int BL = 2; // Back Left Module Index
        static public final int BR = 3; // Back Right Module Index
        static public final int NUM_MODULES = 4;

        // Internal objects used to track where the modules are at relative to
        // the center of the robot, and all the implications that spacing has.
        static private double HW = WHEEL_BASE_WIDTH_M/2.0;

        public enum ModuleConstants {
            FL("FL", 4, 3, 6, 0.590, HW, HW),
            FR("FR", 6, 5, 0, 0.084, HW, -HW),
            BL("BR", 8, 7, 2, 0.504, -HW, HW),
            BR("FL", 2, 1, 4, 0.009, -HW, -HW);
    
            public final String name;
            public final int driveMotorID;
            public final int rotationMotorID;
            public final int magEncoderID;
            /**
             * absolute encoder offsets for the wheels
             * 180 degrees added to offset values to invert one side of the robot so that it doesn't spin in place
             */
            public final double magEncoderOffset;
            public final Translation2d centerOffset;
            private ModuleConstants(String name, int driveMotorID, int rotationMotorID, int magEncoderID, double magEncoderOffset, double xOffset, double yOffset) {
                this.name = name;
                this.driveMotorID = driveMotorID;
                this.rotationMotorID = rotationMotorID;
                this.magEncoderID = magEncoderID;
                this.magEncoderOffset = magEncoderOffset;
                centerOffset = new Translation2d(xOffset, yOffset);
    
            }
        }

        static public final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
            ModuleConstants.FL.centerOffset,
            ModuleConstants.FR.centerOffset,
            ModuleConstants.BL.centerOffset,
            ModuleConstants.BR.centerOffset
        );

        
        public static final double WHEEL_REVS_PER_ENC_REV = 1.0/6.86;
        public static final double AZMTH_REVS_PER_ENC_REV = 1.0/12.8;

        public static final double rotationMotorMaxSpeedRadPerSec = 1.0;
        public static final double rotationMotorMaxAccelRadPerSecSq = 1.0;

        //kv: (12 volts * 60 s/min * 1/5.14 WRevs/MRevs * wheel rad * 2pi  / (6000 MRPM *
        /** ks, kv, ka */ 
        public static final double[] DRIVE_FF = {0.11452, 1.9844, 0.31123};

        public static final SimpleMotorFeedforward driveFeedForward = new SimpleMotorFeedforward(DRIVE_FF[0], DRIVE_FF[1], DRIVE_FF[2]);
        

        public static final double MAX_MODULE_SPEED_FPS = 19;
        public static final double teleopTurnRateDegPerSec = 540; //Rate the robot will spin with full rotation command

        public static final int ENC_PULSE_PER_REV = 1;
        public static final double WHEEL_ENC_COUNTS_PER_WHEEL_REV = ENC_PULSE_PER_REV/ WHEEL_REVS_PER_ENC_REV;  //Assume 1-1 gearing for now
        public static final double AZMTH_ENC_COUNTS_PER_MODULE_REV = ENC_PULSE_PER_REV / AZMTH_REVS_PER_ENC_REV; //Assume 1-1 gearing for now
        public static final double WHEEL_ENC_WHEEL_REVS_PER_COUNT  = 1.0/((double)(WHEEL_ENC_COUNTS_PER_WHEEL_REV));
        public static final double AZMTH_ENC_MODULE_REVS_PER_COUNT = 1.0/((double)(AZMTH_ENC_COUNTS_PER_MODULE_REV));

        public static final TrapezoidProfile.Constraints X_DEFAULT_CONSTRAINTS = new TrapezoidProfile.Constraints(2, 2);
        public static final TrapezoidProfile.Constraints Y_DEFAULT_CONSTRAINTS = new TrapezoidProfile.Constraints(2, 2);

        public static final TrapezoidProfile.Constraints NO_CONSTRAINTS = new TrapezoidProfile.Constraints(Integer.MAX_VALUE, Integer.MAX_VALUE);
        public static final TrapezoidProfile.Constraints THETA_DEFAULT_CONSTRAINTS = new TrapezoidProfile.Constraints(4*Math.PI, 16*Math.PI);
    

    }

    public static final class AutoConstants {

        public static final double maxVelMetersPerSec = 2;
        public static final double maxAccelMetersPerSecondSq = 1;
        
    }
}

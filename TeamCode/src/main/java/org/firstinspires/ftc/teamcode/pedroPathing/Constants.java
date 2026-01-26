package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*
* TODO:
*  List of steps...
*  1. Fill in mass
*  2. Set offset, don't set yaw as it is automatic
*  3. Check names of motors and pinpoint computer
*  4. Run localization tuner
*  5. Run automatic tuners
*  6. Run manual/PID tuners
*  7. If time, run the tests
* */
public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .forwardZeroPowerAcceleration(-28.495093589825387) // TODO: Run ForwardZeroPowerAccelerationTuner and check
            .lateralZeroPowerAcceleration(-31.885836425241894) // TODO: Run LateralZeroPowerAccelerationTuner and check
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0.01, 0.021))
            .headingPIDFCoefficients(new PIDFCoefficients(1,0,0.03,0.03))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.2,0,0.001,0,0.01))
            .centripetalScaling(0.0005)

            .mass(11); // TODO: Change when Robot is finished (in kg)

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }

    public static MecanumConstants driveConstants = new MecanumConstants()
            .xVelocity(60.71848182978592) // TODO: Run ForwardVelocityTuner and check
            .yVelocity(53.26731956662155) // TODO: Run LateralVelocityTuner and check
            .maxPower(1) // For both auto and teleop
            .rightFrontMotorName("rf") // TODO: Ensure these are named on the control hub
            .rightRearMotorName("rr")
            .leftRearMotorName("lr")
            .leftFrontMotorName("lf")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE);

    public static PinpointConstants localizerConstants = new PinpointConstants() // TODO: Test localizer, moving forward increases x and strafing left increases y
            .forwardPodY(1.5) // TODO: Modify both offsets, consult https://pedropathing.com/docs/pathing/tuning/localization/pinpoint
            .strafePodX(1)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD) // TODO: Check encoder directions, run localization test in tuning
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);
}

package org.firstinspires.ftc.teamcode.pedroPathing;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.function.Supplier;

@Configurable
@TeleOp
public class BlueTeleOp extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private boolean automatedDrive;
    private Supplier<PathChain> pathChain;
    private TelemetryManager telemetryM;
    private boolean slowMode = true;
    private double slowModeMultiplier = 0.35;
    private double topPower = 1;
    private double intakePower = 1;

    private boolean rtRunning;
    private boolean ltRunning;

    DcMotor lt; // Top two motors
    DcMotor rt;
    CRServo intake; // Not real Sevo, is connector for intakePos...
    Servo intakeServo;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose); // Takes current position from autonomous...
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        lt = hardwareMap.get(DcMotor.class, "lt");
        rt = hardwareMap.get(DcMotor.class, "rt");
        intake = hardwareMap.get(CRServo.class, "intake");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");

        lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lt.setDirection(DcMotorSimple.Direction.REVERSE); // Left reversed or right...

        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(105, 34))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(90), 0.8))
                .build();
    }

    @Override
    public void start() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();
        telemetry.update();

        if (!automatedDrive) {
            //Make the last parameter false for field-centric
            //In case the drivers want to use a "slowMode" you can scale the vectors

            //This is the normal version to use in the TeleOp
            if (!slowMode) follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    true // Robot Centric
            );

                //This is how it looks with slowMode on
            else follower.setTeleOpDrive(
                    -gamepad1.left_stick_y * slowModeMultiplier,
                    -gamepad1.left_stick_x * slowModeMultiplier,
                    -gamepad1.right_stick_x * slowModeMultiplier,
                    true // Robot Centric
            );
        }


        //Automated PathFollowing stuff!
        if (gamepad1.aWasPressed()) {
            follower.followPath(pathChain.get());
            automatedDrive = true;
        }

        //Stop automated following if the follower is done
        if (automatedDrive && (gamepad1.xWasPressed() || !follower.isBusy())) {
            follower.startTeleopDrive();
            automatedDrive = false;
        }

        //Slow Mode
        if (gamepad1.bWasPressed()) {
            slowMode = !slowMode;
        }

        if (gamepad2.y) {
            rt.setPower(topPower);
        } else {
            rt.setPower(0);
        }

        if (gamepad2.y) {
            lt.setPower(topPower);
        } else {
            lt.setPower(0);
        }



        if (gamepad2.dpad_up) {
            intake.setPower(intakePower);
        }

        if (gamepad2.dpad_right) {
            intakeServo.setPosition(1);
        }

        if (gamepad2.dpad_left) {
            intakeServo.setPosition(-1);
        }


        if (gamepad2.dpad_down) {
            intake.setPower(-intakePower);
        }

        if (gamepad2.dpadUpWasReleased() || gamepad1.dpadDownWasReleased()) {
            intake.setPower(0);
        }

        telemetryM.debug("position", follower.getPose());
        telemetryM.debug("velocity", follower.getVelocity());
        telemetryM.debug("automatedDrive", automatedDrive);

        telemetry.addData("Slow Mode: ", slowMode);
        telemetry.addData("Starting Coordinates", startingPose);
        telemetry.addData("Returning to base! ", automatedDrive);
        telemetry.addData("Top Power: ", topPower);
        telemetry.addData("Servo:", intakeServo.getConnectionInfo());
    }
}
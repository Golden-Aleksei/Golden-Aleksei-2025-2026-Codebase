package org.firstinspires.ftc.teamcode.pedroPathing;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;


@Configurable
@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends OpMode {
    private Follower follower;
    private TelemetryManager telemetryM;
    private boolean slowMode = false;

    private Limelight3A Limelight;

    private int OuttakeRunning, ServoRunning, LightRunning = 0;

    private boolean LightIsBlinking = false;

    public static double LongVelocity = 890;
    public static double ShortVelocity = 585;

    public static PIDFCoefficients PIDF = new PIDFCoefficients(125,0,0.01,12.9); // Default for Outtake motors TODO: Change if necessary

    private double headingError, robotHeading;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
        Limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        Limelight.start();
        Limelight.pipelineSwitch(4);

    }

    @Override
    public void loop() {
        follower.update();
        telemetryM.update();
        telemetry.update();

        DcMotorEx LOuttake = hardwareMap.get(DcMotorEx.class, "LOuttake");
        DcMotorEx ROuttake = hardwareMap.get(DcMotorEx.class, "ROuttake");

        CRServo servo1 = hardwareMap.get(CRServo.class, "Servo1");
        CRServo servo2 = hardwareMap.get(CRServo.class, "Servo2");
        CRServo servo3 = hardwareMap.get(CRServo.class, "Servo3");
        Servo light = hardwareMap.get(Servo.class, "Light");
        Servo limelightLight = hardwareMap.get(Servo.class, "LimelightLight");


        LOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(PIDF));
        ROuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(PIDF));

        LOuttake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ROuttake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //This is the normal version to use in the TeleOp
        double slowModeMultiplier = 0.5; // TODO: Change if needed
        if (!slowMode) follower.setTeleOpDrive(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x * slowModeMultiplier,
                true // Robot Centric
        );

            //This is how it looks with slowMode on
        else follower.setTeleOpDrive(
                -gamepad1.left_stick_y * slowModeMultiplier,
                -gamepad1.left_stick_x * slowModeMultiplier,
                -gamepad1.right_stick_x * 0.25,
                true // Robot Centric
        );

        //Slow Mode
        if (gamepad1.bWasPressed()) {
            slowMode = !slowMode;
        }


        // Intake System
        if (gamepad2.rightBumperWasPressed()) {
            ServoRunning = 2;
            if (!((OuttakeRunning == 2) || (OuttakeRunning == 3))){
                OuttakeRunning = 1;
            }
        }
        if (gamepad2.rightBumperWasReleased()) {
            ServoRunning = 0;
            if (!((OuttakeRunning == 2) || (OuttakeRunning == 3))) {
                OuttakeRunning = 0;
            }
        }

        if (gamepad2.leftBumperWasPressed()) {
            ServoRunning = 1;
        }
        if (gamepad2.leftBumperWasReleased()) {
            ServoRunning = 0;
        }

        if (gamepad2.right_trigger >= 0.75) {
            OuttakeRunning = 0;
        }
        // Outtake system
        if (gamepad2.y) {
            OuttakeRunning = 2;
        }

        if (gamepad2.a) {
            OuttakeRunning = 3;
        }

        // Experimental AimAssist
        if (gamepad1.xWasPressed()) {
            if ((headingError > 0.1) || (headingError < -0.1)) { // Not 0 because good chance Limelight is not 100% accurate
                follower.setHeading(Math.toRadians(robotHeading));
                follower.turnTo(Math.toRadians(robotHeading-headingError)); // TODO: If you want to make aim assist better add or subtract here
            }

        }
        if (gamepad1.xWasReleased()) {
            follower.startTeleopDrive();
        }

        // Servo 1
        switch (ServoRunning) {
            case 0:
                servo1.setPower(0);
                break;
            case 1:
                servo1.setPower(-0.25);
                break;
            case 2:
                servo1.setPower(1);
                break;
        }

        // Servo 2
        switch (ServoRunning) {
            case 0:
                servo2.setPower(0);
                break;
            case 1:
                servo2.setPower(-0.25);
                break;
            case 2:
                servo2.setPower(1);
                break;
        }

        // Servo 3
        switch (ServoRunning) {
            case 0:
                servo3.setPower(0);
                break;
            case 1:
                servo3.setPower(-0.25);
                break;
            case 2:
                servo3.setPower(1);
                break;
        }

        // Outtake System
        switch (OuttakeRunning) {
            case 0:
                LOuttake.setDirection(DcMotorSimple.Direction.FORWARD);
                LOuttake.setVelocity(0);
                break;
            case 1:
                LOuttake.setDirection(DcMotorSimple.Direction.REVERSE);
                LOuttake.setVelocity(20); // Strangely they don't like velocity being negative
                break;
            case 2:
                LOuttake.setVelocity(LongVelocity);
                break;
            case 3:
                LOuttake.setVelocity(ShortVelocity);
                break;
            case 4:
                LOuttake.setPower(1);
        }
        switch (OuttakeRunning) {
            case 0:
                ROuttake.setDirection(DcMotorSimple.Direction.REVERSE);
                ROuttake.setVelocity(0);
                LightRunning = 0;
                break;
            case 1:
                ROuttake.setDirection(DcMotorSimple.Direction.FORWARD);
                ROuttake.setVelocity(20);
                break;
            case 2:
                ROuttake.setVelocity(LongVelocity);
                // This system is currently fine and if the left does not make it than there is another issue
                if (ROuttake.getVelocity() >= (LongVelocity - 20)) {
                    LightRunning = 1;
                }
                break;
            case 3:
                ROuttake.setVelocity(ShortVelocity);
                if (ROuttake.getVelocity() >= (ShortVelocity - 20)) {
                    LightRunning = 2;
                }
                break;
            case 4:
                ROuttake.setPower(1);
        }

        // Light System
        switch (LightRunning) {
            case 0:
                light.setPosition(0);
                break;
            case 1:
                light.setPosition(1);
                break;
            case 2:
                if (LightIsBlinking) {
                    light.setPosition(1);
                    LightIsBlinking = !LightIsBlinking;
                } else if (!LightIsBlinking) {
                    light.setPosition(0);
                    LightIsBlinking = !LightIsBlinking;
                }
                break;
        }

        telemetry.addData("position", follower.getPose());
        telemetry.addData("velocity", follower.getVelocity());
        telemetry.addData("Outtake Running", OuttakeRunning);

        telemetry.addData("Left velocity", LOuttake.getVelocity());
        telemetry.addData("Right velocity", ROuttake.getVelocity());

        telemetryM.addData("PIDF values: ", PIDF);
        telemetryM.addData("Left velocity", LOuttake.getVelocity());
        telemetryM.addData("Right velocity", ROuttake.getVelocity());

        LLResult result = Limelight.getLatestResult();
        if (result != null && result.isValid()) {
            headingError = result.getTx();
            robotHeading = result.getBotpose().getOrientation().getYaw();
            telemetry.addData("Heading Error", headingError);

            // LimelightLight system
            if ((5 >= headingError) && (headingError>= -5)){ // Probably will have to be different values
                limelightLight.setPosition(1);
                telemetry.addData("LimelightLight on?", true);
            } else {
                limelightLight.setPosition(0);
                telemetry.addData("LimelightLight on?", false);
            }
        }

    }
}

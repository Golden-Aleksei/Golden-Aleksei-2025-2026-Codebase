package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
     _______. __  .___  ___. .______    __       _______         ___      __    __  .___________.  ______
    /       ||  | |   \/   | |   _  \  |  |     |   ____|       /   \    |  |  |  | |           | /  __  \
   |   (----`|  | |  \  /  | |  |_)  | |  |     |  |__         /  ^  \   |  |  |  | `---|  |----`|  |  |  |
    \   \    |  | |  |\/|  | |   ___/  |  |     |   __|       /  /_\  \  |  |  |  |     |  |     |  |  |  |
.----)   |   |  | |  |  |  | |  |      |  `----.|  |____     /  _____  \ |  `--'  |     |  |     |  `--'  |
|_______/    |__| |__|  |__| | _|      |_______||_______|   /__/     \__\ \______/      |__|      \______/
Does not include strafing mechanics, built mostly similar for tank drive currently
*/

@Autonomous(name = "Simple Auto (not Roadrunner)", group = "Auto", preselectTeleOp = "TeleOp")
public class simpleauto extends LinearOpMode {

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    //Convert from the counts per revolution of the encoder to counts per inch
    static final double HD_COUNTS_PER_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 20.15293;
    static final double WHEEL_CIRCUMFERENCE_MM = 90 * Math.PI;
    static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
    static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;

    public String status;

    private void drive(double power, double leftInches, double rightInches) {
        int rightTarget;
        int leftTarget;

        if (opModeIsActive()) {
            rightTarget = frontRight.getCurrentPosition() + (int)(rightInches * DRIVE_COUNTS_PER_IN);
            leftTarget = frontLeft.getCurrentPosition() + (int)(leftInches * DRIVE_COUNTS_PER_IN);

            frontLeft.setTargetPosition(leftTarget);
            backLeft.setTargetPosition(leftTarget);
            frontRight.setTargetPosition(rightTarget);
            frontLeft.setTargetPosition(rightTarget);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);

            while (opModeIsActive() && (frontLeft.isBusy() || backLeft.isBusy() || frontRight.isBusy() || backRight.isBusy())) {
                // Blank for purposes to not set power at the wrong time, wish I could use an if statement or an !while statement...
            }

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
        }
    }

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // TODO: Make sure this is the right direction D4LM!
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (opModeIsActive()) {
            drive(0.75, 10,10); // Yay, it moved forward... now to write rotation...
            drive(0.5, -5, 5); // Rotation?
        }
    }
}

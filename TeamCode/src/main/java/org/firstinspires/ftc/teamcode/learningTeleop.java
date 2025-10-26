package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class learningTeleop extends OpMode{
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;


    public double currentSpeed = 2; // 50% speed...
    public boolean encodersNeeded = true;


    @Override
    public void init(){
        telemetry.addData("Ready!", "Please press start!");

        // TODO: Make sure names match on Driver Hub and Control Hub
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        backRight = hardwareMap.get(DcMotor.class, "rightBack");
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");

        // TODO: Check if encoders are needed/installed on the motors
        if (encodersNeeded) {
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // TODO: Check if motor directions are set up properly, uncomment ones that are needed
        // frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        // backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        // Note: Comments are single line as each motor is different

    }
    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Forwards and backwards movement
        double x = gamepad1.left_stick_x * 1.1; // For strafing, 1.1 is to counteract imperfect strafing
        double rx = gamepad1.right_stick_x; // For turning(rx)


        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = (y - x + rx) / denominator;
        double leftBackPower = (y - x + rx) / denominator;
        double rightFrontPower = (y + x - rx) / denominator;
        double rightBackPower = (y + x - rx) / denominator;
    }
}
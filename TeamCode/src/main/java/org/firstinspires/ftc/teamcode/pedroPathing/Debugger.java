package org.firstinspires.ftc.teamcode.pedroPathing;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Configurable
@TeleOp
public class Debugger extends OpMode {
    private DcMotor rightFront, rightRear, leftRear, leftFront, LOuttake, ROuttake;
    private CRServo Servo1, Servo2, Servo3;
    private Servo Light;
    private Limelight3A Limelight;
    private GoBildaPinpointDriver Pinpoint;
    private DcMotorController LOuttakeController,ROuttakeController;

    private boolean LightIsBlinking;

    @Override
    public void init() {
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        rightRear = hardwareMap.get(DcMotor.class, "rr");
        leftRear = hardwareMap.get(DcMotor.class, "lr");
        leftFront = hardwareMap.get(DcMotor.class, "lf");
        LOuttake = hardwareMap.get(DcMotor.class, "LOuttake");
        ROuttake = hardwareMap.get(DcMotor.class, "ROuttake");
        ROuttakeController = hardwareMap.get(DcMotorController.class, "ROuttake");
        LOuttakeController = hardwareMap.get(DcMotorController.class, "LOuttake");

        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        ROuttake.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo1 = hardwareMap.get(CRServo.class, "Servo1");
        Servo2 = hardwareMap.get(CRServo.class, "Servo2");
        Servo3 = hardwareMap.get(CRServo.class, "Servo3");
        Light = hardwareMap.get(Servo.class, "Light");

        Limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        Limelight.start();
        Limelight.pipelineSwitch(0);


    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            rightFront.setPower(1);
        } else {
            rightFront.setPower(0);
        }

        if (gamepad1.dpad_left) {
            leftFront.setPower(1);
        } else {
            leftFront.setPower(0);
        }

        if (gamepad1.dpad_right) {
            rightRear.setPower(1);
        } else {
            rightRear.setPower(0);
        }

        if (gamepad1.dpad_down) {
            leftRear.setPower(1);
        } else {
            leftRear.setPower(0);
        }

        if (gamepad1.left_bumper) {
            LOuttake.setPower(1);
        } else {
            LOuttake.setPower(0);
        }

        if (gamepad1.right_bumper) {
            ROuttake.setPower(1);
        } else {
            ROuttake.setPower(0);
        }

        if (gamepad1.y) {
            if (LightIsBlinking) {
                Light.setPosition(1);
                LightIsBlinking = !LightIsBlinking;
            } else if (!LightIsBlinking) {
                Light.setPosition(0);
                LightIsBlinking = !LightIsBlinking;
            }
        } else {
            Light.setPosition(0);
        }

        if (gamepad1.x) {
            Servo1.setPower(1);
        } else {
            Servo1.setPower(0);
        }

        if (gamepad1.a) {
            Servo2.setPower(1);
        } else {
            Servo2.setPower(0);
        }

        if (gamepad1.b) {
            Servo3.setPower(1);
        } else {
            Servo3.setPower(0);
        }
    }

}

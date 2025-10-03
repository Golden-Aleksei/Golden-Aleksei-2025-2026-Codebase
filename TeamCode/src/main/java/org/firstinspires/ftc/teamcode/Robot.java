// Robot.java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/*
 __  .___  ___. .______     ______   .______     .___________.    ___      .__   __. .___________. __
|  | |   \/   | |   _  \   /  __  \  |   _  \    |           |   /   \     |  \ |  | |           ||  |
|  | |  \  /  | |  |_)  | |  |  |  | |  |_)  |   `---|  |----`  /  ^  \    |   \|  | `---|  |----`|  |
|  | |  |\/|  | |   ___/  |  |  |  | |      /        |  |      /  /_\  \   |  . `  |     |  |     |  |
|  | |  |  |  | |  |      |  `--'  | |  |\  \----.   |  |     /  _____  \  |  |\   |     |  |     |__|
|__| |__|  |__| | _|       \______/  | _| `._____|   |__|    /__/     \__\ |__| \__|     |__|     (__)
* When modifying the robot significantly, please read and do all of the TODO items!
*
 */

public abstract class Robot extends OpMode {
    // Define all Motors
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    // TODO: Set speeds of the robot
    public double currentSpeed;
    public double speedOne = 1;
    public double speedTwo = 0.5;

    // TODO: Check if encoders are needed
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
}

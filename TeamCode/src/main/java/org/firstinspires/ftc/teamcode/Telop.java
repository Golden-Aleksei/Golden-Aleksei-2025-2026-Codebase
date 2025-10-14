package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name="TeleOp", group = "TeleOp")
public class Telop extends Robot{
    @Override
    public void loop(){
        // Define all controllers used:
        /*
        * If you do not understand what controls what check:
        * https://gm0.org/en/latest/docs/software/tutorials/gamepad.html
        */
        double y = -gamepad1.left_stick_y; // Forwards and backwards movement
        double x = gamepad1.left_stick_x * 1.1; // For strafing, 1.1 is to counteract imperfect strafing
        double rx = gamepad1.right_stick_x; // For turning(rx)

        // Changes speed when pressing button b
        if (gamepad1.b) {
            if (currentSpeed == speedOne){
                currentSpeed = speedTwo;
            } else {
                currentSpeed = speedOne;
            }
        }

        /* Math section over here, you do need to understand much (hard to explain) but think of it
            as a xy graph and also because of joystick controls
         */
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = (y + x + rx) / denominator;
        double leftBackPower = (y - x + rx) / denominator;
        double rightFrontPower = (y + x - rx) / denominator;
        double rightBackPower = (y + x - rx) / denominator;

        // Set power section:
        frontLeft.setPower(leftFrontPower / currentSpeed);
        backLeft.setPower(leftBackPower / currentSpeed);
        backRight.setPower(rightBackPower / currentSpeed);
        frontRight.setPower(rightFrontPower / currentSpeed);

        telemetry.addLine("Running!");
        telemetry.addData("Current Speed: ", currentSpeed);
        telemetry.update();
    }
}

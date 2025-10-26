// Hello World Program for FTC


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="HelloWorld",group = "Introduction")
public class HelloWorld extends OpMode{
    // Called when the driver presses INIT
    @Override
    public void init(){
        // This ends to the driver station
        telemetry.addData("Hello", "World");
    }

    // Called repeatedly while OpMode is playing
    @Override
    public void loop() {}
}

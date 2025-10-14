package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;

import org.firstinspires.ftc.robotcontroller.external.samples.UtilityOctoQuadConfigMenu;

@Autonomous(name = "Rotate", group="Autonomous", preselectTeleOp = "TeleOp")
public class rotate extends LinearOpMode {
    @Override

    public void runOpMode(){
        waitForStart();
        Pose2d initalPose = new Pose2d(0,0,Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initalPose);
        if (opModeIsActive()) {
            drive.actionBuilder(initalPose)
                    .turn(Math.toRadians(180))
                    .lineToX(20)
                    .strafeTo(new Vector2d(20, 30))
                    .build();
        }
    }
}

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "RED_SIDE", group = "Autonomous",preselectTeleOp = "TeleOp")
public class Auto extends LinearOpMode {
    @Override
    public void runOpMode(){
        Pose2d initialPose = new Pose2d(58,11, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-35,35,Math.toRadians(135)),Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(58,11,Math.toRadians(90)), Math.toRadians(60))
                .setTangent(Math.toRadians(90))
                .lineToY(55)
                .waitSeconds(5)
                .lineToYLinearHeading(11,Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-35,35,Math.toRadians(135)),Math.toRadians(135))
                .splineToLinearHeading(new Pose2d(58,11,Math.toRadians(90)), Math.toRadians(60))
                .build();
    }
}

package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Move Forward A Little Bit", preselectTeleOp = "Main TeleOp")
public class MoveForwardALittleBit extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private boolean done = false;
    private Servo Light;


    private final Pose startPose = new Pose(72,72, Math.toRadians(90));
    private final Pose lastPose = new Pose(72,74, Math.toRadians(90));

    private Path move;

    public void buildPaths() {
        move = new Path(new BezierLine(startPose, lastPose));
        move.setLinearHeadingInterpolation(startPose.getHeading(), lastPose.getHeading());
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

        Light = hardwareMap.get(Servo.class,"Light");
    }

    public void start(){
        opmodeTimer.resetTimer();
    }

    public void loop() {
        follower.update();
        telemetry.update();
        if (!done) {
            follower.followPath(move,true);
            done = !done;
        }
        if (done) {
            Light.setPosition(1);
        }
        telemetry.addData("Is it done? ", done);
    }

}

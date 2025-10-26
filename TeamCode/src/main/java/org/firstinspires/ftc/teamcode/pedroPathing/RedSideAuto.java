package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "RedSideAuto", group = "Praying", preselectTeleOp = "RedTeleOp")
public class RedSideAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(88, 8, Math.toRadians(90));

    private final Pose point2 = new Pose(88, 88, Math.toRadians(40));
    /*
    private final Pose point3 = new Pose(88,72, Math.toRadians(180));
    private final Pose point4 = new Pose(125,72,Math.toRadians(180));
*/

    private Path scorePreload;

    // private PathChain line2, line3;

    // private PathChain grabPickup1


    public void buildPaths(){
        scorePreload = new Path(new BezierLine(startPose, point2));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), point2.getHeading());
        /*
        line2 = follower.pathBuilder()
                .addPath(new BezierLine(point2,point3))
                .setLinearHeadingInterpolation(point2.getHeading(), point3.getHeading())
                .build();

        line3 = follower.pathBuilder()
                .addPath(new BezierLine(point3, point4))
                .setLinearHeadingInterpolation(point3.getHeading(), point4.getHeading())
                .build();

         */
    }

    /*
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    */


    @Override
    public void loop() {
        follower.update();
        // autonomousPathUpdate();

        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        // setPathState(0);
        // Temporary until there are more paths...
        follower.followPath(scorePreload);

    }

    // Do not use this, won't work...
    @Override
    public void stop() {}
}
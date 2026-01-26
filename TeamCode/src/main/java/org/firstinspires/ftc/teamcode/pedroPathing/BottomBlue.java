package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Bottom Blue", group = "Blue")
public class BottomBlue extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;
    private boolean done = false;

    private final Pose startPose = new Pose(58, 7, Math.toRadians(90));
    private final Pose preloadShootPose = new Pose(60,15, Math.toRadians(115));
    private final Pose collectHumanPlayerPose1 = new Pose(30,22,Math.toRadians(0));
    private final Pose collectHumanPlayerPose2 = new Pose(15,22,Math.toRadians(0));
    private final Pose shootHumanPlayerPose = new Pose(60,15, Math.toRadians(115));
    private final Pose collectGPPControlPoint = new Pose(72,48);
    private final Pose collectGPPPose = new Pose(17,37, Math.toRadians(0));
    private final Pose shootGPPPose = new Pose(60,15, Math.toRadians(115));

    private Path scorePreload;
    private PathChain collectHumanPlayer1, collectHumanPlayer2, shootHumanPlayer, collectGPP, shootGPP;

    public void buildPaths() {
        scorePreload = new Path(new BezierLine(startPose, preloadShootPose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), preloadShootPose.getHeading());

        collectHumanPlayer1 = follower.pathBuilder()
                .addPath(new BezierLine(preloadShootPose, collectHumanPlayerPose1))
                .setLinearHeadingInterpolation(preloadShootPose.getHeading(), collectHumanPlayerPose1.getHeading())
                .build();

        collectHumanPlayer2 = follower.pathBuilder()
                .addPath(new BezierLine(collectHumanPlayerPose1, collectHumanPlayerPose2))
                .setLinearHeadingInterpolation(collectHumanPlayerPose1.getHeading(), collectHumanPlayerPose2.getHeading())
                .build();

        shootHumanPlayer = follower.pathBuilder()
                .addPath(new BezierLine(collectHumanPlayerPose2, shootHumanPlayerPose))
                .setLinearHeadingInterpolation(collectHumanPlayerPose2.getHeading(), shootHumanPlayerPose.getHeading())
                .build();

        collectGPP = follower.pathBuilder()
                .addPath(new BezierCurve(shootHumanPlayerPose, collectGPPControlPoint, collectGPPPose))
                .setLinearHeadingInterpolation(shootHumanPlayerPose.getHeading(), collectGPPPose.getHeading())
                .build();

        shootGPP = follower.pathBuilder()
                .addPath(new BezierLine(collectGPPPose, shootGPPPose))
                .setLinearHeadingInterpolation(collectGPPPose.getHeading(), shootGPPPose.getHeading())
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(collectHumanPlayer1);
                    setPathState(2);
                    break;
                }
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(collectHumanPlayer2);
                    setPathState(3);
                    break;
                }
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(shootHumanPlayer);
                    setPathState(4);
                    break;
                }
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(collectGPP);
                    setPathState(5);
                    break;
                }
            case 5:
                if (!follower.isBusy() && !done) {
                    follower.followPath(shootGPP);
                    setPathState(6);
                    done = !done;
                }

                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.update();

        telemetry.addData("path state", pathState);
        telemetry.addData("Robot Position", follower.getPose());
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
        setPathState(0);
    }

    @Override
    public void stop(){}
}

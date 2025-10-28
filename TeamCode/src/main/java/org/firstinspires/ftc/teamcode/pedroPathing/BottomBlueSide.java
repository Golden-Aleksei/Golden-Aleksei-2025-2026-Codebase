package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "BlueSideAuto", group = "Praying", preselectTeleOp = "BlueTeleOp")
public class BottomBlueSide extends OpMode {

    /*
    DcMotor lt;
    DcMotor rt;
    CRServo intake;
    */

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;


    private final Pose startPose = new Pose(56, 8, Math.toRadians(90));
    private PathChain PreloadScore, Grabball, Score, ParkingSpot;

    // private boolean scoring;

    public void buildPaths(){
        PreloadScore = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56, 8), new Pose(56, 87))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(135))
                .build();

        Grabball = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56, 87),
                                new Pose(100, 10),
                                new Pose(132, 10)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .build();

        Score = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(132, 10),
                                new Pose(100, 10),
                                new Pose(56,87)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .build();

        ParkingSpot = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56,87), new Pose(105,34))
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(90))
                .build();
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(PreloadScore);
                setPathState(1);
                break;
            case 1:

            /* You could check for
            - Follower State: "if(!follower.isBusy()) {}"
            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
            - Robot Position: "if(follower.getPose().getX() > 36) {}"
            */
                /*
                scoring = true; // Make sure the robot does not move at the same time...
                while (pathTimer.getElapsedTimeSeconds() > 5.1) {
                    if (pathTimer.getElapsedTimeSeconds() > 3) {
                        intake.setPower(1);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > 5) {
                        rt.setPower(0.83);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > 5) {
                        lt.setPower(0.83);
                    }
                }

                intake.setPower(0); // Stops intake and everything else...
                rt.setPower(0);
                lt.setPower(0);
                */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */

                if (!follower.isBusy()) {
                    /* Score Preload */

                    follower.followPath(Grabball, true);
                    setPathState(2);
                }

                break;
            case 2:
                if (!follower.isBusy()) {

                    follower.followPath(Score, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(ParkingSpot, true);
                }
            }

        }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        // Load in motors from teleop
        /*
        lt = hardwareMap.get(DcMotor.class, "lt");
        rt = hardwareMap.get(DcMotor.class, "rt");
        intake = hardwareMap.get(CRServo.class, "intake");

        lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lt.setDirection(DcMotorSimple.Direction.REVERSE); // Left reversed or right...
        */
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);


    }

}

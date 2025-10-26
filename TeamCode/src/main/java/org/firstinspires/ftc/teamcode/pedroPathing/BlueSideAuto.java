package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "BlueSideAuto", group = "Praying", preselectTeleOp = "BlueTeleOp")
public class BlueSideAuto extends OpMode {

    // Servo and motor fromt teleop defined here
    /*
    DcMotor lt;
    DcMotor rt;
    CRServo intake;
     */

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(56, 8, Math.toRadians(90));
    private final Pose point2 = new Pose(56, 87, Math.toRadians(135));
    /*
    private final Pose point3 = new Pose(56,72, Math.toRadians(0)); // Technically I could just put 0 as 0 degrees = 0 randians...
    private final Pose point4 = new Pose(19,72,Math.toRadians(0));
     */


    private Path scorePreload;
    // private PathChain line2, line3;

    private boolean scoring;

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(startPose, point2));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), point2.getHeading());

        /*
        line2 = follower.pathBuilder()
                .addPath(new BezierLine(point2, point3))
                .setLinearHeadingInterpolation(point2.getHeading(), point3.getHeading())
                .build();

        line3 = follower.pathBuilder()
                .addPath(new BezierLine(point3, point4))
                .setLinearHeadingInterpolation(point3.getHeading(), point4.getHeading())
                .build();

         */
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
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
                /*
                if (!follower.isBusy()) {
                    /* Score Preload *
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                /*
                    follower.followPath(line2, true);
                    setPathState(2);
                }*/
                /*
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
        /*
        if (!follower.isBusy()) {
                    /* Parks */

                /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
        /*
                    follower.followPath(line3, true);
                    setPathState(3);
                }
                break;
        }


    }
    */
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
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        //  setPathState(0);
        follower.followPath(scorePreload);


    }

    // Do not use this, won't work...
    @Override
    public void stop() {}
}

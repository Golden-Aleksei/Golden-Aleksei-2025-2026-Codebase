# README!


## Rest of files that are not labeled TeleOp are for autonomous

## Configuration
* "leftFront"
* "leftBack"
* "rightBack"
* "rightFront"
* "imu"

## Notes
* Roadrunner has barely been tuned due to robot not being ready yet

## Autonomous was tuned with the assumption that it will be used with Mecanum wheels, not tank drive
```
.
├── Drawing.java
├── Localizer.java
├── MecanumDrive.java
├── messages
│   ├── DriveCommandMessage.java
│   ├── MecanumCommandMessage.java
│   ├── MecanumLocalizerInputsMessage.java
│   ├── PoseMessage.java
│   ├── TankCommandMessage.java
│   ├── TankLocalizerInputsMessage.java
│   ├── ThreeDeadWheelInputsMessage.java
│   └── TwoDeadWheelInputsMessage.java
├── OTOSLocalizer.java
├── PinpointLocalizer.java
├── README.md
├── Robot.java # TeleOp
├── TankDrive.java
├── Telop.java # TeleOp
├── ThreeDeadWheelLocalizer.java
├── tuning
│   ├── LocalizationTest.java
│   ├── ManualFeedbackTuner.java
│   ├── SplineTest.java
│   └── TuningOpModes.java
└── TwoDeadWheelLocalizer.java

```
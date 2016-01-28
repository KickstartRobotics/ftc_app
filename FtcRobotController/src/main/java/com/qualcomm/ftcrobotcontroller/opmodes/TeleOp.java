package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by kickstart on 11/30/15.
 */
public class TeleOp extends OpMode{

    //private final double ARM_POWER = .25;
    private final double LIFT_POWER = .2;

    private boolean rightArmOut = false;
    private boolean rbPressed = false;
    private boolean leftArmOut = false;
    private boolean lbPressed = false;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorArm;
    DcMotor rampLift;
    Servo rightFlipper;
    Servo leftFlipper;

    public void init()
    {
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorArm = hardwareMap.dcMotor.get("motorArm");
        rampLift = hardwareMap.dcMotor.get("rampLift");
        leftFlipper = hardwareMap.servo.get("leftFlipper");
        rightFlipper = hardwareMap.servo.get("rightFlipper");



        leftFlipper.setPosition(.2);
        rightFlipper.setPosition(.4);

    }

    public void loop()
    {
        // Get values from joysticks
        float rawLeft = -gamepad1.right_stick_y;
        float rawRight = gamepad1.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        float right = Range.clip(rawRight, -1, 1);
        float left = Range.clip(rawLeft, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float) scaleInput(right);
        left = (float) scaleInput(left);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        climberArm();
        rightArm();
        leftArm();
        backLift();
    }

    public void backLift()
    {
        if (gamepad1.y)
        {
            rampLift.setDirection(DcMotor.Direction.FORWARD);
            rampLift.setPower(LIFT_POWER);
        }
        else if (gamepad1.x)
        {
            rampLift.setDirection(DcMotor.Direction.REVERSE);
            rampLift.setPower(LIFT_POWER);
        }
        else
        {
            rampLift.setPower(0);
        }

    }

    private void climberArm()
    {

            // Get values from joystick
            float rawLift = -gamepad2.right_stick_y;

            // clip the right/left value so that the value never exceeds +/- 1
            double lift;

            if(rawLift > .15) {
                lift = .15;
            }
            else if(rawLift < -.15) {
                lift = -.15;
            }
            else {
                lift = rawLift;
            }

            // scale the joystick value to make it easier to control
            // the robot more precisely at slower speeds.
            //lift = (float) scaleInput(lift);

            //motorArm.setDirection(DcMotor.Direction.FORWARD);

            motorArm.setPower(lift);
    }

    private void leftArm()
    {
        //Toggled flipper code
        if (gamepad2.left_bumper)
        {
            if (!lbPressed)
            {
                if (leftArmOut)
                {
                    leftFlipper.setPosition(1);
                    leftArmOut = false;
                }

                else
                {
                    leftFlipper.setPosition(.6);
                    leftArmOut = true;
                }
            }

            lbPressed = true;

        } else {
            lbPressed = false;
        }
    }

    private void rightArm()
    {
        //Toggled flipper code
        if (gamepad2.right_bumper)
        {
            if (!rbPressed)
            {
                if (rightArmOut)
                {
                    rightFlipper.setPosition(.4);
                    rightArmOut = false;
                }
                else
                {
                    rightFlipper.setPosition(0);
                    rightArmOut = true;
                }
            }

            rbPressed = true;

        }

        else
        {
            rbPressed = false;
        }

        /*
        //Non trigger flipper code
        if (gamepad1.left_bumper)
        {
            leftFlipper.setPosition(0);
        }
        else
        {
            leftFlipper.setPosition(1);
        }

        if (gamepad1.right_bumper)
        {
            leftFlipper.setPosition(0);
        }

        else
        {
            leftFlipper.setPosition(1);
        }
        */

    }

    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}

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
    private final double SCOOPER_POWER = .2;

    private boolean rightArmOut = false;
    private boolean rbPressed = false;
    private boolean bucketOpen = false;
    private boolean lbPressed = false;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorScooper;
    DcMotor motorBucketLift;
    Servo bucketFlipper;

    public void init()
    {
        motorBucketLift = hardwareMap.dcMotor.get( "motorBucketLift" );
        motorRight = hardwareMap.dcMotor.get( "motorRight" );
        motorLeft = hardwareMap.dcMotor.get( "motorLeft" );
        motorScooper = hardwareMap.dcMotor.get( "motorScooper" );
        bucketFlipper = hardwareMap.servo.get( "bucketFlipper" );



        bucketFlipper.setPosition( .2 );

    }

    public void loop()
    {
        // Get values from joysticks
        float rawLeft = -gamepad1.right_stick_y;
        float rawRight = gamepad1.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        float right = Range.clip( rawRight, -1, 1 );
        float left = Range.clip( rawLeft, -1, 1 );

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = ( float ) scaleInput( right );
        left = ( float ) scaleInput( left );

        // write the values to the motors
        motorRight.setPower( right );
        motorLeft.setPower( left );

        bucketLift();
        scoopDebris();
        bucketDump();
    }

    public void bucketLift()
    {
        if (gamepad1.dpad_up)
        {
            motorBucketLift.setDirection(DcMotor.Direction.FORWARD);
            motorBucketLift.setPower(LIFT_POWER);
        }
        else if (gamepad1.dpad_down)
        {
            motorBucketLift.setDirection(DcMotor.Direction.REVERSE);
            motorBucketLift.setPower(LIFT_POWER);
        }
        else
        {
            motorBucketLift.setPower(0);
        }
    }

    public void bucketDump()
    {
        if (gamepad1.right_bumper)
        {
            if(!rbPressed)
            {
                if (bucketOpen)
                {
                    bucketFlipper.setPosition(.6);
                    bucketOpen = false;
                }
                else
                {
                    bucketFlipper.setPosition(0);
                    bucketOpen = true;
                }
            }
            rbPressed = true;
        }
        else
        {
            rbPressed = false;
        }
    }

    public void scoopDebris()
    {
        if ( gamepad1.a )
        {
            motorScooper.setDirection( DcMotor.Direction.FORWARD );
            motorScooper.setPower( SCOOPER_POWER );
        }

    }

    public void stop()
    {

    }

    double scaleInput( double dVal )
    {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = ( int ) ( dVal * 16.0 );

        // index should be positive.
        if ( index < 0 )
        {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if ( index > 16 )
        {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if ( dVal < 0 )
        {
            dScale = -scaleArray[index];
        }
        else
        {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousLowRamp extends OpMode

{
    DcMotor motorRight;
    DcMotor motorLeft;

    public AutonomousLowRamp()

    {

    }

    public void init ()
    {
        motorRight = hardwareMap.dcMotor.get( "motorRight" );
        motorLeft = hardwareMap.dcMotor.get( "motorLeft" );
        motorLeft.setDirection( DcMotor.Direction.REVERSE );
    }

    public void loop ()
    {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch ( v_state )
        {
            case 0:
                motorLeft.setPower( -0.5 );
                motorRight.setPower( -0.5 );

                v_state++;
                break;
            case 1:
                long time = 0;
                long originalTime = System.currentTimeMillis();

                while( time < 1500 )
                {
                    time = System.currentTimeMillis() - originalTime;
                }
                v_state++;
                break;
            case 2:
                motorLeft.setPower( -0.5 );
                motorRight.setPower( 0.5 );

                v_state++;
                break;
            case 3:
                time = 0;
                originalTime = System.currentTimeMillis();

                while( time < 500 )
                {
                    time = System.currentTimeMillis() - originalTime;
                }
                v_state++;
                break;
            case 4:
                motorLeft.setPower( 0.5 );
                motorRight.setPower( 0.5 );

                v_state++;
                break;
            case 5:
                time = 0;
                originalTime = System.currentTimeMillis();

                while( time < 500 )
                {
                    time = System.currentTimeMillis() - originalTime;
                }
                v_state++;
                break;
            //
            // Perform no action - stay in this case until the OpMode is stopped.
            // This method will still be called regardless of the state machine.
            //
            default:
                motorLeft.setPower( 0 );
                motorRight.setPower( 0 );
                break;
        }
        motorLeft.setDirection( DcMotor.Direction.REVERSE );

        telemetry.addData( "18", "State: " + v_state );

    }
    private int v_state = 0;

}

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousParkZone extends OpMode

{
    DcMotor motorRight;
    DcMotor motorLeft;

    public AutonomousParkZone()

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

                while( time < 5000 )
                {
                    time = System.currentTimeMillis() - originalTime;
                }
                v_state++;
            break;

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

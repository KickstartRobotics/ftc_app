package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ClimberDropAutonomous extends OpMode
{
    TouchSensor touchSensorRight;
    TouchSensor touchSensorLeft;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorArm;

    public ClimberDropAutonomous()
    {}

    public void init ()
    {
        motorRight = hardwareMap.dcMotor.get( "motorRight" );
        motorLeft = hardwareMap.dcMotor.get( "motorLeft" );
        motorArm = hardwareMap.dcMotor.get( "motorArm" );

        motorLeft.setDirection( DcMotor.Direction.REVERSE );

        touchSensorRight = hardwareMap.touchSensor.get( "right" );
        touchSensorLeft = hardwareMap.touchSensor.get( "left" );
    }

    public void loop ()
    {
        switch ( v_state )
        {
            case 0:
                motorLeft.setPower( -0.5 );
                motorRight.setPower( -0.5 );
                while( true )
                {
                    if ( touchSensorLeft.isPressed() )
                    {
                        v_state = 1;
                        DbgLog.msg( "Right is pressed" );
                        break;

                    }
                    else if ( touchSensorRight.isPressed() )
                    {
                        v_state = 2;
                        DbgLog.msg( "Left is pressed" );
                        break;
                    }
                }
                break;
            case 1:
                motorRight.setPower( 0 );
                while( true )
                {
                    if( touchSensorRight.isPressed() )
                    {
                        DbgLog.msg( "Left is pressed" );
                        v_state = 3;
                        break;
                    }
                }
                break;
            case 2:
                motorLeft.setPower( 0 );
                while( true )
                {
                    if ( touchSensorLeft.isPressed() )
                    {
                        DbgLog.msg( "Right is pressed" );
                        v_state = 3;
                        break;
                    }
                }
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
    @Override
    public void stop()
    {
        motorLeft.setPower( 0 );
        motorRight.setPower( 0 );
    }
}
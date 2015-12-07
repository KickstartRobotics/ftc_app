package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousParkZone extends OpMode

{
    DcMotor motorRight;
    DcMotor motorLeft;

    public AutonomousParkZone()

    {

    } // PushBotAuto

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    public void init ()
    {
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }
    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    public void loop ()
    {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
        //
        // Synchronize the state machine and hardware.
        //
        case 0:
                motorLeft.setPower(-0.5);
                motorRight.setPower(-0.5);

                v_state++;
            break;
        case 1:
                long time = 0;
                long originalTime = System.currentTimeMillis();

                while(time < 3250)
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
                motorLeft.setPower(0);
                motorRight.setPower(0);
            break;
        }

        //
        // Send telemetry data to the driver station.
        //

        telemetry.addData ("18", "State: " + v_state);

    } // loop

    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int v_state = 0;

} // PushBotAuto

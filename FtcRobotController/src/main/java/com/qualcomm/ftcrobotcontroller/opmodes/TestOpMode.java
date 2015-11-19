package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by kickstart on 11/12/15.
 */
public class TestOpMode extends OpMode{
    DcMotor Motor;

    public void init() {

        Motor = hardwareMap.dcMotor.get("leftMotor");
        Motor.setDirection(DcMotor.Direction.REVERSE);

    }
    public void loop()
    {

    }

    public void start()
    {
        Motor.setPower(0.5);
    }
    public void stop()
    {

    }
}


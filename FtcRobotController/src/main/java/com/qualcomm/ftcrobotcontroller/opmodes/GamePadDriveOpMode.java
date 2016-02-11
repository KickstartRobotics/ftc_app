package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by kickstart on 11/19/15.
 */

public class GamePadDriveOpMode extends OpMode
{
    DcMotor motorRight;
    DcMotor motorLeft;
    public void init()
    {
    motorRight = hardwareMap.dcMotor.get("rightMotor");
    motorLeft = hardwareMap.dcMotor.get("leftMotor");
    }
    public void loop()
    {
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;
        motorRight.setPower(right);
        motorLeft.setPower(left);

    }
}

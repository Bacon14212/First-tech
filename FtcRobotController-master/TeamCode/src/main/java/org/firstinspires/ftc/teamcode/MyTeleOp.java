package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "AwesomeTeleOp", group = "Linear Opmode")
public class MyTeleOp extends LinearOpMode {
    //Declare Opmode classes
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialize variables
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftBack = hardwareMap.get(DcMotor.class, "left_back");
        rightBack = hardwareMap.get(DcMotor.class, "right_back");

        //Reversing motors that run backwards

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD );

        // wait for driver to press (play)
        waitForStart();
        runtime.reset();

        //This is to run opmode for the entirity of the match
        while (opModeIsActive()) {


            //Variable for each motor to save power in telemetry
            double leftPower;
            double rightPower;
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            //Strafing
            leftDrive.setPower(y + x + rx);
            leftBack.setPower(y - x + rx);
            rightDrive.setPower(y - x - rx);
            rightBack.setPower(y + x - rx);

            /* Pov drive mode */
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            leftPower = Range.clip(drive + turn, -1, 1);
            rightPower = Range.clip(drive - turn, -1, 1);

            //Give power to wheels
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            leftBack.setPower(leftPower);
            rightBack.setPower(rightPower);

            //show elapsed time and power that each wheel is getting
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();


        }


    }
}


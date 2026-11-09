package first.robot

import org.littletonrobotics.junction.LoggedRobot
import org.wpilib.command3.Command
import org.wpilib.command3.Scheduler
import org.wpilib.driverstation.DriverStation
import org.wpilib.system.DataLogManager

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
class Robot : LoggedRobot() {
    private var autonomousCommand: Command? = null

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    private val robotContainer: RobotContainer = RobotContainer()

//      This function is run when the robot is first started up and should be used for any
//      initialization code.

    init {

        // Start recording to data log
        DataLogManager.start()

        // Record DS control and joystick data.
        // Change to `false` to not record joystick data.
        DriverStation.startDataLog(DataLogManager.getLog(), true)
    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     *
     * This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        Scheduler.getDefault().run()
    }

    /** This function is called once each time the robot enters Disabled mode.  */
    override fun disabledInit() {}

    override fun disabledPeriodic() {}

    /** This autonomous runs the autonomous command selected by your [RobotContainer] class.  */
    override fun autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            Scheduler.getDefault().schedule(autonomousCommand)
        }
    }

    /** This function is called periodically during autonomous.  */
    override fun autonomousPeriodic() {}

    override fun teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            Scheduler.getDefault().cancel(autonomousCommand)
        }
    }

    /** This function is called periodically during operator control.  */
    override fun teleopPeriodic() {}

    override fun utilityInit() {
        // Cancels all running commands at the start of utility mode.
        Scheduler.getDefault().cancelAll()
    }

    /** This function is called periodically during utility mode.  */
    override fun utilityPeriodic() {}
}

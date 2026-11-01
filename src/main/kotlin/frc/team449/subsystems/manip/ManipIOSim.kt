package frc.team449.subsystems.manip

class ManipIOSim : ManipIOHardware() {
    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        inputs.motor1Velocity = 0.0
        inputs.motor1Position=0.0
        inputs.motor1AppliedVoltage=0.0
    }
}
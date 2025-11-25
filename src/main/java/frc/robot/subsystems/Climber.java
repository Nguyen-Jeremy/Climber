package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.CANdi;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.S1StateValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
TalonFX moteurBras = new TalonFX(45);
TalonFX moteurCage = new TalonFX(46);
DigitalOutput LimitSwitch = new DigitalOutput(9);
public CANcoder CANCoder = new CANcoder(1);
CANcoderConfigurator configCAN = new CANcoderConfigurator(null);


private final CANdi _CANdi;
public Climber(){
    this._CANdi = new CANdi(35);
    boolean actif = this._CANdi.getS1State().getValue() == S1StateValue.Low;

    
}

public void climb(){
    moteurBras.setVoltage(4);
}

public void stopBras(){
    moteurBras.setVoltage(0);
}

public void intakeCage(){
    moteurCage.setVoltage(4);
}

public void grimperCage(){
    moteurCage.setVoltage(4);
}

public void stopCage(){
    moteurCage.setVoltage(0);
}
public Angle getAngle(){
   return CANCoder.getPosition().getValue();
}
}

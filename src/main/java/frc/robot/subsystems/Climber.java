package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.CANdi;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.S1StateValue;

import edu.wpi.first.units.CurrentUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
private TalonFX moteurBras;
private TalonFX moteurCage;
private CANcoder CANCoder = new CANcoder(1);
private boolean actif;

private final CANdi _CANdi;
public Climber(){
    moteurBras = new TalonFX(45);
    moteurCage = new TalonFX(46);
    this._CANdi = new CANdi(35);
     actif = this._CANdi.getS1State().getValue() == S1StateValue.Low;
  
}

public void raiseArm(double v){
    moteurBras.setVoltage(v);
}

public void stopArm(){
    moteurBras.setVoltage(0);
}

public void intakeCage(double v){
    moteurCage.setVoltage(v);
}

public void stopCage(){
    moteurCage.setVoltage(0);
   
}

public Angle getAngle(){
   return CANCoder.getPosition().getValue();
}
public Command raiseArmCommand(){
    return run(() -> raiseArm(4)).until(() -> getAngle().gte(Degrees.of(Constants.angleBrasMax))).finallyDo(() -> stopArm());
}
public Command intakeCageCommand(){
    return run(() -> intakeCage(4)).until(() -> moteurCage.getSupplyCurrent().getValue().gte(Current.ofBaseUnits(Constants.supplyCurrentLimit, Amp)));
}
public Command raiseRobot(){
    return run(() -> stopArm()).until(() -> actif == true).finallyDo(() -> raiseArm(Constants.constantV));
}
public Command climbCommand(){
 return new SequentialCommandGroup(
    raiseArmCommand(), intakeCageCommand(), raiseRobot()
 );
}

}

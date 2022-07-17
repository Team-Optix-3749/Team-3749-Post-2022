package frc.robot.utilities;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;

public class SmartData<T> {
    T defaultVal;
    NetworkTableEntry entry;

    public SmartData (String name, T defaultVal) {
        this.defaultVal = defaultVal;
        entry = NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry(name);
        entry.setValue(defaultVal);
    }

    @SuppressWarnings("unchecked") 
    public T get() {
        Object value = entry.getValue();
        if (value == NetworkTableType.kUnassigned) {
            return defaultVal;
        } 
        return (T) value;
    }
    
    public void set(T val) {
        entry.setValue(val);
    }
}

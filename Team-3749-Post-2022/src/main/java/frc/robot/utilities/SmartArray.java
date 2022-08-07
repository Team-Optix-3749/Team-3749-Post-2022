package frc.robot.utilities;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;

/***
 * @author Rohan Juneja
 * 
 * Stores data array that can be seen/edited in Smart Dashboard
 * @param <T> Type of data: Supported types include String, Double, and Boolean
 */
public class SmartArray<T> {
  ArrayList<T> defaultVal = new ArrayList<T>();
  NetworkTableEntry entry;

  public SmartArray (String name) {
      entry = NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry(name);
      entry.setValue(defaultVal.toArray());
  }

  public SmartArray (String name, ArrayList<T> defaultVal) {
    this.defaultVal = defaultVal;
    entry = NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry(name);
    entry.setValue(defaultVal.toArray());
  }

  public void setDefault (ArrayList<T> defaultVal) {
      this.defaultVal = defaultVal;
  }

  @SuppressWarnings("unchecked") 
  public ArrayList<T> get() {
      Object value = entry.getValue();
      if (value == NetworkTableType.kUnassigned) {
          return defaultVal;
      } 
      return (ArrayList<T>) value;
  }

  public void set(T[] val) {
      entry.setValue(val);
  }

  public void addValue(T val) {
      ArrayList<T> current = get();

      current.add(val);

      entry.setValue(current.toArray());
  }
}

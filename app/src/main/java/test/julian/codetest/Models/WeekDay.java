package test.julian.codetest.Models;

/**
 * Created by JulianStack on 12/07/2017.
 */

public class WeekDay {

    String Day;
    String Temp;
    Integer Icon;

    public WeekDay(String day, String temp, Integer icon) {
        Day = day;
        Temp = temp;
        Icon = icon;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public Integer getIcon() {
        return Icon;
    }

    public void setIcon(Integer icon) {
        Icon = icon;
    }
}

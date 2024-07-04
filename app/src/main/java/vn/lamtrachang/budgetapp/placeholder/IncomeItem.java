package vn.lamtrachang.budgetapp.placeholder;

import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class IncomeItem implements Serializable {
    public  String name;  //content| tên item
    public  int  state; //details | trạng thái income hay expenses
    public  int type; // loại (cash, bank)
    public  String time;
    public  String detail;
    public String money;



    public IncomeItem( String name, int  state, int type, String money) {
        this.name = name;
        this.state = state;
        this.type = type;
        this.time = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm-dd.M yyyy")));
        this.money = money;
    }

    public IncomeItem( String name, int  state, int type, String money, String detail) {
        this.name = name;
        this.state = state;
        this.type = type;
        this.time = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy")));
        this.money = money;
        this.detail = detail;
    }


    public String getName() {
        return name;
    }

    public int  getState() {
        return state;
    }

    public int getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public String getDetail() {
        return detail;
    }

}

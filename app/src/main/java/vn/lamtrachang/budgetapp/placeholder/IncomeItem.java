package vn.lamtrachang.budgetapp.placeholder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IncomeItem implements Serializable {
    public  String name;  //content| tên item
    public  int  state; //details | trạng thái income hay expenses
    public  int type; // loại (cash, bank)
    public  String time;
    public  String detail;
    public String money;
    int id;

    public IncomeItem()
    {
    }

    public IncomeItem( String name, String money,  int type,int  state, String time) {
        this.name = name;
        this.state = state;
        this.type = type;
        this.time = time;
        this.money = money;
    }

    public IncomeItem( String name, String money, String detail,  int type,int  state, String time) {
        this.name = name;
        this.state = state;
        this.type = type;
        this.time = time;
        this.money = money;
        this.detail = detail;

    }

    public String toString() {
        return  name + '\'' +
                money +'\''+
                detail + '\''
                + type +'\''
                + state +'\''
                + time ;
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

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMoney(String money) {

        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

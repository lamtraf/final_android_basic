package vn.lamtrachang.budgetapp.placeholder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DB_INCOMES";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "TB_income";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MONNEY = "money";
    public static final String KEY_DETAIL = "detail";
    public static final String KEY_TYPE = "type";
    public static final String KEY_STATE = "state";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_TIME = "time";
    
//    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            +KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +KEY_NAME +" TEXT, "
            +KEY_MONNEY +" TEXT, "
            +KEY_DETAIL +" TEXT, "
            +KEY_TYPE +" INTEGER, "
            +KEY_STATE +" INTEGER, "
            +KEY_CATEGORY +" INTEGER, "
            +KEY_TIME +" TEXT)";

    public SQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_table = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(drop_table);
        onCreate(db);
    }

    public void addIncome(IncomeItem incomeItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,incomeItem.getName());
        contentValues.put(KEY_MONNEY, incomeItem.getMoney());
        contentValues.put(KEY_DETAIL,incomeItem.getDetail());
        contentValues.put(KEY_TYPE,incomeItem.getType());
        contentValues.put(KEY_STATE,incomeItem.getState());
        contentValues.put(KEY_CATEGORY,incomeItem.getCategory());
        contentValues.put(KEY_TIME,incomeItem.getTime());
        db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<IncomeItem> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<IncomeItem> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            IncomeItem incomeItem = new IncomeItem();
            incomeItem.setId(cursor.getInt(0));
            incomeItem.setName(cursor.getString(1));
            incomeItem.setMoney(cursor.getString(2));
            incomeItem.setDetail(cursor.getString(3));
            incomeItem.setType(cursor.getInt(4));
            incomeItem.setState(cursor.getInt(5));
            incomeItem.setCategory(cursor.getInt(6));
            incomeItem.setTime(cursor.getString(7));
            list.add(incomeItem);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
        
    }

    public ArrayList<IncomeItem> getAllIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<IncomeItem> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_STATE+" = 0";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            IncomeItem incomeItem = new IncomeItem();
            incomeItem.setId(cursor.getInt(0));
            incomeItem.setName(cursor.getString(1));
            incomeItem.setMoney(cursor.getString(2));
            incomeItem.setDetail(cursor.getString(3));
            incomeItem.setType(cursor.getInt(4));
            incomeItem.setState(cursor.getInt(5));
            incomeItem.setCategory(cursor.getInt(6));
            incomeItem.setTime(cursor.getString(7));
            list.add(incomeItem);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<IncomeItem> getAllExpenses(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<IncomeItem> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_STATE+" = 1";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            IncomeItem incomeItem = new IncomeItem();
            incomeItem.setId(cursor.getInt(0));
            incomeItem.setName(cursor.getString(1));
            incomeItem.setMoney(cursor.getString(2));
            incomeItem.setDetail(cursor.getString(3));
            incomeItem.setType(cursor.getInt(4));
            incomeItem.setState(cursor.getInt(5));
            incomeItem.setCategory(cursor.getInt(6));
            incomeItem.setTime(cursor.getString(7));
            list.add(incomeItem);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public void deleteIncome(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateIncome(IncomeItem oldItem, IncomeItem newItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,newItem.getName());
        contentValues.put(KEY_MONNEY, newItem.getMoney());
        contentValues.put(KEY_DETAIL,newItem.getDetail());
        contentValues.put(KEY_TYPE,newItem.getType());
        contentValues.put(KEY_STATE,newItem.getState());
        contentValues.put(KEY_CATEGORY,newItem.getCategory());
        contentValues.put(KEY_TIME,newItem.getTime());
        db.update(TABLE_NAME, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(oldItem.getId())});
    }



    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }


    public String sumIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
         String sql = "SELECT SUM("+KEY_MONNEY+") FROM "+TABLE_NAME+" WHERE "+KEY_STATE+" = 0";
         Cursor cursor = db.rawQuery(sql,null);
         cursor.moveToFirst();
         if(cursor.getString(0) == null){
             return "0";
         }
            return cursor.getString(0);

    }

    public String sumExpenses(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT SUM("+KEY_MONNEY+") FROM "+TABLE_NAME+" WHERE "+KEY_STATE+" = 1";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        if(cursor.getString(0) == null){
            return "0";
        }
        return cursor.getString(0);
//        return currencyFormat.format(cursor.getString(0));
    }

    public String getSavings(){
        int income = Integer.parseInt(sumIncome());
        int expenses = Integer.parseInt(sumExpenses());
        return String.valueOf(income - expenses);
    }

}

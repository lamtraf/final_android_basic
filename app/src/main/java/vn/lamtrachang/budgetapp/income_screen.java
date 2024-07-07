package vn.lamtrachang.budgetapp;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.IncomeItemAdapter;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class income_screen extends AppCompatActivity {
    private ArrayList<IncomeItem> mItems;
    private RecyclerView mRecyclerItem;
    private IncomeItemAdapter mItemAdapter;
    private TextView mTextTitle;
    private TextView mTextMoney;
    private SQLiteHelper dataSource = new SQLiteHelper(this);
    private ImageView button_filter;
    private ImageView button_search;
    private int filterCategory =-1;
    private int filterType =-1;
    EditText edit_text_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mTextTitle = findViewById(R.id.title);
        mTextMoney = findViewById(R.id.value);
        ImageView back = findViewById(R.id.back_icon);
        back.setOnClickListener(v -> finish());
        //getExtras()

        mRecyclerItem = findViewById(R.id.list_income);
        String str = getIntent().getStringExtra("type");


        if (str.equals("income")) {
            mTextTitle.setText("Income");
            mTextMoney.setText(dataSource.sumIncome());
            mItems = dataSource.getAllIncome();
            genListItems(mItems);
            //income
        } else {
            mTextTitle.setText("Expenses");
            mTextMoney.setText(dataSource.sumExpenses());
            mItems = dataSource.getAllExpenses();
            genListItems(mItems);
            //expenses
        }

        button_filter = findViewById(R.id.button_filter);
        button_search = findViewById(R.id.button_search);
        edit_text_search = findViewById(R.id.text_search);


        button_filter.setOnClickListener(v -> {
            PopupMenu popupFilter = new PopupMenu(this, button_filter);
            popupFilter.inflate(R.menu.filter_menu);
            Menu menu = popupFilter.getMenu();
            popupFilter.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menuItem_food) {
                         genListItems(getAllItemByCategory(0, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 0;
                        //Do something
                    } else if (id == R.id.menuItem_transport) {
                         genListItems(getAllItemByCategory(1, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 1;
                        //Do something
                    } else if (id == R.id.menuItem_shopping) {
                         genListItems(getAllItemByCategory(2, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 2;
                        //Do something
                    } else if (id == R.id.menuItem_health) {
                         genListItems(getAllItemByCategory(3, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 3;
                        //Do something
                    } else if (id == R.id.menuItem_entertainment) {
                         genListItems(getAllItemByCategory(4, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 4;
                        //Do something
                    } else if (id == R.id.menuItem_education) {
                         genListItems(getAllItemByCategory(5, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 5;
                        //Do something
                    } else if (id == R.id.menuItem_bill) {
                         genListItems(getAllItemByCategory(6, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 6;
                        //Do something
                    } else if (id == R.id.menuItem_other) {
                         genListItems(getAllItemByCategory(7, getAllItemByType(filterType, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterCategory = 7;
                        //Do something
                    } else if (id == R.id.menuItem_non)
                    {
                    //Do something
                     genListItems(getAllItemByName(edit_text_search.getText().toString().trim(),mItems));
                    filterCategory = -1;
                    }

                    if (id == R.id.menuItem_bank) {
                         genListItems(getAllItemByType(1, getAllItemByType(filterCategory, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterType = 1;
                        //Do something
                    } else if (id == R.id.menuItem_cash) {
                         genListItems(getAllItemByType(0, getAllItemByType(filterCategory, getAllItemByName(edit_text_search.getText().toString().trim(),mItems))));
                        filterType = 0;
                        //Do something
                    } else if (id == R.id.menuItem_non)
                    {
                        //Do something
                         genListItems(getAllItemByName(edit_text_search.getText().toString().trim(),mItems));
                        filterType = -1;
                    }
                    return true;
                }

            });
            popupFilter.show();
        });

        edit_text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Gọi ngay khi có sự thay đổi
                String name = edit_text_search.getText().toString().trim();
                genListItems(getAllItemByName(name, getAllItemByCategory(filterCategory, getAllItemByType(filterType, mItems))));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Gọi sau khi thay đổi
            }
        });

        button_search.setOnClickListener(v -> {
            edit_text_search.clearFocus();
            String name = edit_text_search.getText().toString();
            genListItems(getAllItemByName(name, getAllItemByCategory(filterCategory, getAllItemByType(filterType, mItems))));
        });

    }

    public ArrayList<IncomeItem> getAllItemByName(String name, ArrayList<IncomeItem> mItems) {
        ArrayList<IncomeItem> items = new ArrayList<>();
        for (IncomeItem item : mItems) {
            if (item.getName().contains(name)) {
                items.add(item);
            }
        }
        return items;
    }

    public ArrayList<IncomeItem> getAllItemByCategory(int category, ArrayList<IncomeItem> mItems) {
        if (category == -1) {
            return mItems;
        }
        ArrayList<IncomeItem> items = new ArrayList<>();
        for (IncomeItem item : mItems) {
            if (item.getCategory() == category) {
                items.add(item);
            }
        }
        return items;
    }

    public ArrayList<IncomeItem> getAllItemByType(int type, ArrayList<IncomeItem> mItems) {
        if (type == -1) {
            return mItems;
        }
        ArrayList<IncomeItem> items = new ArrayList<>();
        for (IncomeItem item : mItems) {
            if (item.getType() == type) {
                items.add(item);
            }
        }
        return items;
    }

    public ArrayList<IncomeItem> getAllItemByDateFrom(String date1, String date2, ArrayList<IncomeItem> mItems) {
        ArrayList<IncomeItem> items = new ArrayList<>();
        for (IncomeItem item : mItems) {
            if (item.getTime().compareTo(date1) >= 0 && item.getTime().compareTo(date2) <= 0) {
                items.add(item);
            }
        }
        return items;
    }

    // public ArrayList<IncomeItem> getAllItemByDateTo(String date1, String date2,
    // ArrayList<IncomeItem> mItems)
    // {
    // ArrayList<IncomeItem> items = new ArrayList<>();
    // for (IncomeItem item : mItems) {
    // if (item.getTime().compareTo(date1) <= 0 && item.getTime().compareTo(date2)
    // >= 0) {
    // items.add(item);
    // }
    // }
    // return items;
    // }

    public void genListItems(ArrayList<IncomeItem> ListItems) {
        if (ListItems.isEmpty()) {
            findViewById(R.id.no_task).setVisibility(View.VISIBLE);
            mRecyclerItem.setAdapter(null);
        } else {
            findViewById(R.id.no_task).setVisibility(View.GONE);
            mItemAdapter = new IncomeItemAdapter(this, ListItems);
            mRecyclerItem.setAdapter(mItemAdapter);

        }

    }
}
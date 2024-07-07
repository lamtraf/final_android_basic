package vn.lamtrachang.budgetapp.placeholder;



import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.lamtrachang.budgetapp.DetailActivity;
import vn.lamtrachang.budgetapp.R;

public class IncomeItemAdapter extends RecyclerView.Adapter<IncomeItemAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<IncomeItem> mIncomeItems;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
//    private SQLiteHelper dataSource= new SQLiteHelper(mContext);

    public  IncomeItemAdapter(Context mContext, ArrayList<IncomeItem> mIncomeItems) {
                    this.mContext = mContext;
                    this.mIncomeItems = mIncomeItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IncomeItem hero = mIncomeItems.get(position);
        if (hero == null) {
            return;
        }
        holder.mTextName.setText(hero.getName());

        if (hero.getType() == 0) {
            holder.mTextType.setText("Cash");
        } else {
            holder.mTextType.setText("Bank");
        }
        holder.mTextTime.setText(hero.getTime());
        if(hero.getCategory() == 0){
            holder.mTextCategory.setText("Food");
        }else if(hero.getCategory() == 1){
            holder.mTextCategory.setText("Transport");
        }else if(hero.getCategory() == 2){
            holder.mTextCategory.setText("Shopping");
        }else if(hero.getCategory() == 3){
            holder.mTextCategory.setText("Health");
        }else if(hero.getCategory() == 4){
            holder.mTextCategory.setText("Entertainment");
        }else if(hero.getCategory() == 5){
            holder.mTextCategory.setText("Education");
        }else if(hero.getCategory() == 6){
            holder.mTextCategory.setText("Bill");
        }
        else if(hero.getCategory() == 7){
            holder.mTextCategory.setText("Other");
        }

        holder.mTextMoney.setText(hero.getMoney());
        if (hero.getState() == 0) {
            holder.mImageState.setImageResource(R.drawable.income);
        } else {
            holder.mImageState.setImageResource(R.drawable.expenses);
        }
        
        holder.mItem.setOnClickListener(v -> {
//                onClickItem(hero);
            Intent intent = new Intent(mContext, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", hero);
            intent.putExtras(bundle);
             mContext.startActivity(intent);
        });
//       holder.mItem.setOnLongClickListener(new View.OnLongClickListener() {
//           @Override
//           public boolean onLongClick(View v) {
//               dataSource.deleteIncome(mIncomeItems.get(position).getId());
//               removeItem(position);
//
//               return true;
//           }
//       });
    }
//
//    public void removeItem(int position) {
//        mIncomeItems.remove(position);
//        notifyItemRemoved(position);
//    }


    @Override
    public int getItemCount() {
        return mIncomeItems == null ? 0 : mIncomeItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private  LinearLayout mItem;
        private TextView mTextName;
        private ImageView mImageState;
        private TextView mTextType;
        private TextView mTextTime;
        private TextView mTextCategory;
        private TextView mTextMoney;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.layout_income);
            mTextName = itemView.findViewById(R.id.name);
            mImageState = itemView.findViewById(R.id.state);
            mTextType = itemView.findViewById(R.id.type);
            mTextTime = itemView.findViewById(R.id.text_time);
            mTextCategory = itemView.findViewById(R.id.text_category);
            mTextMoney = itemView.findViewById(R.id.money);
        }
    }

}
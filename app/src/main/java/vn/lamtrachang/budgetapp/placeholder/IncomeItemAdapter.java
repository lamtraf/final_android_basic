package vn.lamtrachang.budgetapp.placeholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.lamtrachang.budgetapp.DetailActivity;
import vn.lamtrachang.budgetapp.R;

public class IncomeItemAdapter extends RecyclerView.Adapter<IncomeItemAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<IncomeItem> mIncomeItems;

    public IncomeItemAdapter(Context mContext, ArrayList<IncomeItem> mIncomeItems) {
        this.mContext = mContext;
        this.mIncomeItems = mIncomeItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.fragment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IncomeItem hero = mIncomeItems.get(position);
//        Glide.with(mContext)
//                .load(hero.getImage())
//                .into(holder.mImageHero);
        if (hero == null) {
            return;
        }
        holder.mTextName.setText(hero.getName());

        if (hero.getType() == 1) {
            holder.mTextType.setText("Cash");
        } else {
            holder.mTextType.setText("Bank");
        }
        holder.mTextTime.setText(hero.getTime());
        holder.mTextMoney.setText(hero.getMoney());
        if (hero.getState() == 1) {
            holder.mImageState.setImageResource(R.drawable.income);
        } else {
            holder.mImageState.setImageResource(R.drawable.expenses);
        }
        holder.mTextDetail.setText(hero.getDetail());
        holder.mItem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(hero);
            }
        });
    }

    private void onClickItem(IncomeItem hero) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", hero);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mIncomeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mItem;
        private TextView mTextName;
        private ImageView mImageState;
        private TextView mTextType;
        private TextView mTextTime;
        private TextView mTextDetail;
        private TextView mTextMoney;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.layout_income);
            mTextName = itemView.findViewById(R.id.name);
            mImageState = itemView.findViewById(R.id.state);
            mTextType = itemView.findViewById(R.id.type);
            mTextTime = itemView.findViewById(R.id.text_time);
            mTextDetail = itemView.findViewById(R.id.detal);
            mTextMoney = itemView.findViewById(R.id.money);
        }
    }
}
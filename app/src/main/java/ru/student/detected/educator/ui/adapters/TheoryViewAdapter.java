package ru.student.detected.educator.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.TheoryItemBinding;


public class TheoryViewAdapter extends RecyclerView.Adapter<TheoryViewAdapter.TheoryViewHolder>{
    List<Theory> data;

    @NonNull
    @Override
    public TheoryViewAdapter.TheoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TheoryItemBinding binding = TheoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()));
        return new TheoryViewHolder(binding.getRoot());
    }
    public TheoryViewAdapter(List<Theory> data){
        this.data = data;
    }
    public TheoryViewAdapter(){
        this.data = new ArrayList<>();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Theory> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TheoryViewAdapter.TheoryViewHolder holder, int position) {
        holder.binding.description.setText(data.get(position).getTheoryDescription());
        holder.binding.theoryName.setText(data.get(position).getName());
        holder.binding.img.setImageDrawable(ContextCompat.
                getDrawable(holder.itemView.getContext(), data.get(position).getImg()));
        holder.binding.item.setImageDrawable(ContextCompat.
                getDrawable(holder.itemView.getContext(), data.get(position).getItemImg()));
        holder.binding.item.setOnClickListener(v ->{
                holder.binding.bookmark.setImageDrawable(ContextCompat.
                        getDrawable(holder.itemView.getContext(),R.drawable.bookmark_checked));
                v.setOnClickListener(null);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class TheoryViewHolder extends RecyclerView.ViewHolder{
        public TheoryItemBinding binding;

        public TheoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}

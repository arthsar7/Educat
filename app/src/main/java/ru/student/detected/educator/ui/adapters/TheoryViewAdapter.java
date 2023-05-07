package ru.student.detected.educator.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.educator.ui.interfaces.OnTheoryClickListener;
import ru.student.detected.educator.viewmodel.TheoryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.TheoryItemBinding;


public abstract class TheoryViewAdapter extends RecyclerView.Adapter<TheoryViewAdapter.TheoryViewHolder>{
    private List<Theory> data;
    private final OnTheoryClickListener listener;
    TheoryItemBinding binding;
    @NonNull
    @Override
    public TheoryViewAdapter.TheoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         binding = TheoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()));
        return new TheoryViewHolder(binding.getRoot(), listener);
    }
    public TheoryViewAdapter(List<Theory> data, OnTheoryClickListener listener){
        this.data = data;
        this.listener = listener;
    }
    public TheoryViewAdapter(OnTheoryClickListener listener){
        this.data = new ArrayList<>();
        this.listener = listener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Theory> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TheoryViewAdapter.TheoryViewHolder holder, int position) {
        holder.binding.description.setText(data.get(position).getTheoryDescription());
        SharedPreferences preferences = holder.itemView.getContext().getSharedPreferences("theory", Context.MODE_PRIVATE);
        if (data.get(position).isChecked()) {
            preferences.edit().putBoolean(data.get(position).getName(), true).apply();
        }
        boolean isChecked = preferences.getBoolean(data.get(position).getName(), false);
        if(isChecked) {
            data.get(position).setChecked(true);
            holder.binding.bookmark.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),
                    R.drawable.bookmark_checked));
        }
        holder.binding.playVideo.setOnClickListener(v->playSelected(position));
        holder.binding.theoryName.setText(data.get(position).getName());
        holder.binding.img.setImageDrawable(ContextCompat.
                getDrawable(holder.itemView.getContext(), data.get(position).getImg()));
        holder.binding.item.setImageDrawable(ContextCompat.
                getDrawable(holder.itemView.getContext(), data.get(position).getItemImg()));
    }

    public abstract void playSelected(int position);

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class TheoryViewHolder extends RecyclerView.ViewHolder{
        public TheoryItemBinding binding;
        public TheoryViewHolder(@NonNull View itemView, OnTheoryClickListener listener) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            if (binding != null) {
                binding.item.setOnClickListener(v -> {
                    if(listener != null) {
                        int position = getBindingAdapterPosition();
                        listener.onTheoryClick(position, binding.bookmark);
                    }
                });
            }
        }

    }
}

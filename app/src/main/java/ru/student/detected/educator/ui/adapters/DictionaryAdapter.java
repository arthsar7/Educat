package ru.student.detected.educator.ui.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.DictItemBinding;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {
    private List<Pair> pairs;
    private DictItemBinding binding;
    @NonNull
    @Override
    public DictionaryAdapter.DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DictItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new DictionaryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapter.DictionaryViewHolder holder, int position) {
        holder.binding.textBack.setText(pairs.get(position).getRusWord());
        holder.binding.textFront.setText(pairs.get(position).getEngWord());
        float scale = holder.binding.cardFront.getContext().getResources().getDisplayMetrics().density;
        holder.binding.cardFront.setCameraDistance(8000*scale);
        holder.binding.cardBack.setCameraDistance(8000*scale);
        setDifficultyColor(holder, position);
        binding.cardFront.setOnClickListener(v -> {
            AnimatorSet front_anim = (AnimatorSet) AnimatorInflater
                    .loadAnimator(holder.binding.cardFront.getContext(), R.animator.front_animator);
            AnimatorSet back_anim = (AnimatorSet) AnimatorInflater
                    .loadAnimator(holder.binding.cardFront.getContext(),R.animator.back_animator);
            if(!front_anim.isRunning() && !back_anim.isRunning()) {
                if (pairs.get(position).isFront()) {
                    front_anim.setTarget(holder.binding.cardFront);
                    back_anim.setTarget(holder.binding.cardBack);
                    front_anim.start();
                    back_anim.start();
                    pairs.get(position).setFront(false);
                } else {
                    front_anim.setTarget(holder.binding.cardBack);
                    back_anim.setTarget(holder.binding.cardFront);
                    front_anim.start();
                    back_anim.start();
                    pairs.get(position).setFront(true);
                }
            }
        });

    }

    private void setDifficultyColor(@NonNull DictionaryViewHolder holder, int position) {
        switch (pairs.get(position).getDifficulty()){
            case 1:
                holder.binding.textBack.setBackgroundColor(Color.parseColor("#003430"));
                holder.binding.textFront.setBackgroundColor(Color.parseColor("#008577"));
                holder.binding.difficulty.setText(R.string.easy);
                break;
            case 2:
                holder.binding.textBack.setBackgroundColor(Color.parseColor("#FF8C00"));
                holder.binding.textFront.setBackgroundColor(Color.parseColor("#FFA500"));
                holder.binding.difficulty.setText(R.string.medium);
                break;
            case 3:
                holder.binding.textBack.setBackgroundColor(Color.parseColor("#B22222"));
                holder.binding.textFront.setBackgroundColor(Color.parseColor("#DC143C"));
                holder.binding.difficulty.setText(R.string.hard);
                break;
            default:
                break;
        }
    }

    public DictionaryAdapter(){
        this.pairs = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return pairs.size();
    }
    public DictionaryAdapter(List<Pair> pairs){
        this.pairs = pairs;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Pair> pairs){
        this.pairs = pairs;
        notifyDataSetChanged();
    }
    public static class DictionaryViewHolder extends RecyclerView.ViewHolder {
        private DictItemBinding binding;
        public DictionaryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}

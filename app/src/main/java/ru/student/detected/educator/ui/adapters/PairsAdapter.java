package ru.student.detected.educator.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.PairsItemBinding;

public class PairsAdapter extends RecyclerView.Adapter<PairsAdapter.PairsViewHolder> {
    private List<String> data;
    private final ClickListener listener;
    PairsItemBinding binding;
    public PairsAdapter(List<String> data, ClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PairsAdapter.PairsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PairsItemBinding.inflate(
                LayoutInflater.from(parent.getContext()));
        return new PairsViewHolder(binding.getRoot(), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PairsAdapter.PairsViewHolder holder, int position) {
        String item = data.get(position);
        TextView text = holder.itemView.findViewById(R.id.theory_name);
        text.setText(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PairsViewHolder extends RecyclerView.ViewHolder {
        public PairsViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            View item = itemView.findViewById(R.id.item);
            TextView text = itemView.findViewById(R.id.theory_name);
            item.setOnClickListener(v -> listener.onItemClick(v, getBindingAdapterPosition(), text));
        }

    }
    public interface ClickListener{
        void onItemClick(View view, int position, TextView text);
    }
}

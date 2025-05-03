package com.example.assignment3sqliteandsharedpreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifViewHolder> {

    private List<String[]> notificationList;

    public NotificationAdapter(List<String[]> notificationList) {
        this.notificationList = notificationList;
    }

    public static class NotifViewHolder extends RecyclerView.ViewHolder {
        TextView notifMessage, notifTime;

        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);
            notifMessage = itemView.findViewById(R.id.notifMessage);
            notifTime = itemView.findViewById(R.id.notifTime);
        }
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotifViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        String[] notif = notificationList.get(position);
        holder.notifMessage.setText(notif[0]);
        holder.notifTime.setText(notif[1]);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}

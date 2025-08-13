package com.example.rnroomrent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomCardAdapter extends RecyclerView.Adapter<RoomCardAdapter.RoomViewHolder> {

    private Context context;
    private List<RoomCardModel> roomList;

    public RoomCardAdapter(Context context, List<RoomCardModel> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_room_layout, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomCardModel room = roomList.get(position);

        holder.tvRoomName.setText(room.getRoom_name());
        holder.tvRentAmount.setText(String.valueOf(room.getCustom_rent()));
        holder.tvTenantName.setText(room.getTenant_name());
        holder.tvRentStatus.setText(room.getRent_status());
        holder.tvUnitPaid.setText(room.getUnit_paid_up_to());
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoomName, tvRentAmount, tvTenantName, tvRentStatus, tvUnitPaid;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRentAmount = itemView.findViewById(R.id.tvRoomRent);
            tvTenantName = itemView.findViewById(R.id.tvTenantName);
            tvRentStatus = itemView.findViewById(R.id.tvRentStatus);
            tvUnitPaid = itemView.findViewById(R.id.tvUnitPaid);
        }
    }
}

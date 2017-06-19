package d4tekkom.presensiuas.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import d4tekkom.presensiuas.R;
import d4tekkom.presensiuas.data.model.Jadwal;

/**
 * Created by doy on 19/06/17.
 */

public class MainVH extends RecyclerView.ViewHolder {

    public TextView txtNama1, txtNama2, txtNip1, txtNip2, txtGedung, txtRuang;
    public ImageView imgAbsen1, imgAbsen2;
    public LinearLayout layoutHeader;

    public MainVH(View itemView) {
        super(itemView);
        txtGedung = itemView.findViewById(R.id.txt_gedung);
        txtRuang = itemView.findViewById(R.id.txt_ruangan);
        txtNama1 = itemView.findViewById(R.id.txt_nama1);
        txtNip1 = itemView.findViewById(R.id.txt_nip1);
        txtNama2 = itemView.findViewById(R.id.txt_nama2);
        txtNip2 = itemView.findViewById(R.id.txt_nip2);
        imgAbsen1 = itemView.findViewById(R.id.img_absen1);
        imgAbsen2 = itemView.findViewById(R.id.img_absen2);
        layoutHeader = itemView.findViewById(R.id.linearLayout2);
    }

    public void onBind(Jadwal jadwal, Context context){
        int absen = 0;
        txtGedung.setText(jadwal.getLokasi_ruangan());
        txtRuang.setText(jadwal.getTipe_ruangan() + " " + jadwal.getNama_ruangan());
        txtNama1.setText(jadwal.getPengawas().get(0).getNama());
        txtNip1.setText(String.valueOf(jadwal.getPengawas().get(0).getNip()));
        txtNama2.setText(jadwal.getPengawas().get(1).getNama());
        txtNip2.setText(String.valueOf(jadwal.getPengawas().get(1).getNip()));
        if (jadwal.getPengawas().get(0).getAbsen() != 0){
            absen += 1;
            imgAbsen1.setImageResource(R.drawable.ic_check_circle_black_24dp);
        }
        if (jadwal.getPengawas().get(1).getAbsen() != 0){
            absen += 1;
            imgAbsen2.setImageResource(R.drawable.ic_check_circle_black_24dp);
        }
        if (absen == 0 ){
            layoutHeader.setBackgroundColor(Color.parseColor("#F44336"));
        } else if (absen == 1){
            layoutHeader.setBackgroundColor(Color.parseColor("#FFC107"));
        } else {
            layoutHeader.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }
}

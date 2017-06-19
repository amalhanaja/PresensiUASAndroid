package d4tekkom.presensiuas.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import d4tekkom.presensiuas.R;
import d4tekkom.presensiuas.data.model.Jadwal;

/**
 * Created by doy on 19/06/17.
 */

public class MainVH extends RecyclerView.ViewHolder {

    public TextView txtNama1, txtNama2, txtNip1, txtNip2, txtGedung, txtRuang;

    public MainVH(View itemView) {
        super(itemView);
        txtGedung = itemView.findViewById(R.id.txt_gedung);
        txtRuang = itemView.findViewById(R.id.txt_ruangan);
        txtNama1 = itemView.findViewById(R.id.txt_nama1);
        txtNip1 = itemView.findViewById(R.id.txt_nip1);
        txtNama2 = itemView.findViewById(R.id.txt_nama2);
        txtNip2 = itemView.findViewById(R.id.txt_nip2);
    }

    public void onBind(Jadwal jadwal, Context context){
        txtGedung.setText(jadwal.getLokasi_ruangan());
        txtRuang.setText(jadwal.getTipe_ruangan() + " " + jadwal.getNama_ruangan());
        txtNama1.setText(jadwal.getPengawas().get(0).getNama());
        txtNip1.setText(String.valueOf(jadwal.getPengawas().get(0).getNip()));
        txtNama2.setText(jadwal.getPengawas().get(1).getNama());
        txtNip2.setText(String.valueOf(jadwal.getPengawas().get(1).getNip()));
        if (jadwal.getPengawas().get(0).getAbsen() != 0){
            txtNama1.setCompoundDrawables(null, context.getResources().getDrawable(R.mipmap.ic_launcher_round), null, null);
        }
        if (jadwal.getPengawas().get(1).getAbsen() != 0){
            txtNama2.setCompoundDrawables(null, context.getResources().getDrawable(R.mipmap.ic_launcher_round), null, null);
        }
    }
}

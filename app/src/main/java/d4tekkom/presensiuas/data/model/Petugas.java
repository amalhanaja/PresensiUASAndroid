package d4tekkom.presensiuas.data.model;

import javax.annotation.Generated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

@Generated("com.robohorse.robopojogenerator")
public class Petugas{

	@JSONField(name="nama")
	private String nama;

	@JSONField(name="nip")
	private int nip;

	@JSONField(name="id_petugas")
	private int idPetugas;

	public static Petugas objectFromString(String str){
		return JSON.parseObject(str, Petugas.class);
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNip(int nip){
		this.nip = nip;
	}

	public int getNip(){
		return nip;
	}

	public void setIdPetugas(int idPetugas){
		this.idPetugas = idPetugas;
	}

	public int getIdPetugas(){
		return idPetugas;
	}

	@Override
 	public String toString(){
		return 
			"Petugas{" + 
			"nama = '" + nama + '\'' + 
			",nip = '" + nip + '\'' + 
			",id_petugas = '" + idPetugas + '\'' + 
			"}";
		}
}
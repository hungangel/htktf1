package htkt.model.donhang;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import htkt.model.nhanvien.Nhanvien;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class Hoadon {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int maHoadon;
	@ManyToOne(targetEntity = Khachhang.class)
	private Khachhang khachhang;
	@ManyToOne(targetEntity = Nhanvien.class)
	private Nhanvien nhanvien;
	private String loaiHoadon;
	private String ngayLap;
	private String ngayThanhtoan;
	private String daThanhtoan;
	private String mota;
	private long tongTien;
	private long chietKhau;
	private String donvitinh;
	@OneToMany(mappedBy = "hoadon", cascade = CascadeType.ALL)
	private List<ChitietHoadon> chitietHoadons;

	public Hoadon(Khachhang khachhang,Nhanvien nhanvien, String loaiHoadon, String ngayLap,String ngayThanhtoan, String daThanhtoan, String mota,
			long tongTien, long chietKhau, String donvitinh, List<ChitietHoadon> chitietHoadons) {
		this.khachhang = khachhang;
		this.nhanvien= nhanvien; 
		this.loaiHoadon = loaiHoadon;
		this.ngayLap = ngayLap;
		this.ngayThanhtoan=ngayThanhtoan;
		this.daThanhtoan = daThanhtoan;
		this.mota = mota;
		this.tongTien = tongTien;
		this.chietKhau = chietKhau;
		this.donvitinh = donvitinh;
		for (ChitietHoadon cthd : chitietHoadons)
			cthd.setHoadon(this);
		this.chitietHoadons = chitietHoadons;
	}
}

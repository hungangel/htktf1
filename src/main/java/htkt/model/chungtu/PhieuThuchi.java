package htkt.model.chungtu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class PhieuThuchi {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int maPhieu;
	private String nguoiNop_nhan;
	@OneToOne(targetEntity = Nhanvien.class)
	private Nhanvien nhanvien;
	private String loaiPhieu;
	private String lido;
	private String thoiGian;
	private long soTien;
	private String donviTinh;
}

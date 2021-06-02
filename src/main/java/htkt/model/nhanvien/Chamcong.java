package htkt.model.nhanvien;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
public class Chamcong {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int maChamcong;
	private String thoigianTao;
	@ManyToOne(targetEntity = Nhanvien.class)
	private Nhanvien nhanvien;
	private String phongBan;
	private String ngayCong;//" 1,2,3,4,5,6,,8,8,9,,"
	private String thangLuong;
	private int soCong;//12

}

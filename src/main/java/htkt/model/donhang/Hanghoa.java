package htkt.model.donhang;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Hanghoa {
	@Id
	private String maHH;
	private String tenHang;
	private String mota;
	private long donGiaNhap;
	private long donGiaXuat;

}

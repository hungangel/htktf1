package htkt.model.nhanvien;
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
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
public class Phongban {
	@Id
	private String maPhongban;
	private String mota;
	private String vitriVP;
}

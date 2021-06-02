package htkt.model.nhanvien;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


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
public class Taikhoan {
	@Id
	private String username;
	private String password;
	@OneToOne(targetEntity = Nhanvien.class)
	private Nhanvien nhanvien;
}

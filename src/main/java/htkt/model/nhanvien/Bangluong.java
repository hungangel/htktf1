package htkt.model.nhanvien;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class Bangluong {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int maBL;
	private String thogianTao;
	@OneToOne(targetEntity = Chamcong.class)
	private Chamcong chamcong;
	private int socong;
	private long thuclinh;
}

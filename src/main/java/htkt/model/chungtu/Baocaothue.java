package htkt.model.chungtu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Baocaothue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int masoBaocao;
	private String masothue;
	private String thoigian;
	
	private long thueNokitruoc;
	
	private long doanhsoMua;
	private long thueMua;
	
	private long doanhsoBan;
	private long thueKinay;
	private long thuecantra;
	
	private long thueNolai;
}

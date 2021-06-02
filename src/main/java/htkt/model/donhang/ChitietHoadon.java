package htkt.model.donhang;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ChitietHoadon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnore
	@ManyToOne(targetEntity = Hoadon.class)
	@JoinColumn(name = "maHoadon")
	private Hoadon hoadon;

	@ManyToOne(targetEntity = Hanghoa.class)
	@JoinColumn(name = "maHH")
	private Hanghoa hanghoa;

	private int soluong;
	private long dongia;

	public ChitietHoadon(Hanghoa hanghoa, int soluong, long dongia) {
		this.hanghoa = hanghoa;
		this.soluong = soluong;
		this.dongia = dongia;
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(hoadon.getMaHoadon(), hanghoa.getMaHH(), soluong, dongia);
//	}
}

package htkt.controller.pjcontroller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.nhanvien.Taikhoan;
import htkt.repository.nhanvien.TaikhoanRepository;

@Controller
@RequestMapping("/taikhoan")
public class TaiKhoanCtrl {
	private TaikhoanRepository taikhoanRepository;
	public TaiKhoanCtrl(TaikhoanRepository taikhoanRepository) {
		this.taikhoanRepository = taikhoanRepository ;
			}
	@PostMapping("/doimatkhau")
	public @ResponseBody Taikhoan doimatkhau(@RequestParam String taikhoan ,@RequestParam String matkhau) {
		Optional<Taikhoan> tk = taikhoanRepository.findById(taikhoan);
		Taikhoan tk1 = new Taikhoan();
		if(tk.isPresent()) {
			tk1=tk.get();
			tk1.setPassword(matkhau);
		}
		taikhoanRepository.save(tk1);
		return tk1 ; 
	}
	
}

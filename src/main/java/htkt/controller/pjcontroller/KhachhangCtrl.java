package htkt.controller.pjcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.donhang.Khachhang;
import htkt.repository.donhang.KhachhangRepository;

@Controller
@RequestMapping("/khachhang")
public class KhachhangCtrl {
	private KhachhangRepository khachhangRepository;

	public KhachhangCtrl(KhachhangRepository khachhangRepository) {
		this.khachhangRepository = khachhangRepository;
	}

	@PostMapping(value = "/tatcakhachhang", produces = "application/json")
	public @ResponseBody List<Khachhang> tatcakhachhang() {
		try {
			List<Khachhang> dskhachhang = (List<Khachhang>) khachhangRepository.findAll();
			return dskhachhang;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/themkhachhang", produces = "application/json")
	public @ResponseBody String themkhachhang(@RequestParam("tenKH") String tenKH,
			@RequestParam("diaChi") String diaChi, @RequestParam("masoThue") String masoThue,
			@RequestParam("sdt") String sdt, @RequestParam("email") String email) {
		Khachhang nvmoi = new Khachhang(0, tenKH, diaChi, masoThue, sdt, email);
		khachhangRepository.save(nvmoi);
		return "success";
	}

	@GetMapping("/edit/{maKH}")
	public @ResponseBody Khachhang edit(@PathVariable("maKH") int maKH) {
		Optional<Khachhang> nv = khachhangRepository.findById(maKH);
		Khachhang khachhang = new Khachhang();
		if (nv.isPresent()) {
			khachhang = nv.get();
		}
		return khachhang;
	}

	@PutMapping("/suakhachhang/{maKH}")
	public @ResponseBody void suakhachhang(@PathVariable("maKH") int maKH, @RequestParam("tenKH") String tenKH,
			@RequestParam("diaChi") String diaChi, @RequestParam("masoThue") String masoThue,
			@RequestParam("sdt") String sdt, @RequestParam("email") String email) {
		System.out.print(maKH);
		Khachhang nvql = new Khachhang();
		nvql.setMaKH(maKH);
		nvql.setTenKH(tenKH);
		nvql.setDiaChi(diaChi);
		nvql.setMasoThue(masoThue);
		nvql.setSdt(sdt);
		nvql.setEmail(email);
		khachhangRepository.save(nvql);
	}

	@GetMapping(value = "/timkhachhang")
	public @ResponseBody List<Khachhang> timkhachhang(@RequestParam("tenKH") String tenKH) {
		List<Khachhang> tcnv = (List<Khachhang>) khachhangRepository.findAll();
		List<Khachhang> dsnv = new ArrayList<>();
		for (int i = 0; i < tcnv.size(); i++) {
			Khachhang nv = tcnv.get(i);
			if (nv.getTenKH().toLowerCase().contains(tenKH.toLowerCase())) {
				dsnv.add(nv);
			}
		}
		return dsnv;
	}

}

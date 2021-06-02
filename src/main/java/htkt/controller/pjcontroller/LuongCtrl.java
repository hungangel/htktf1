package htkt.controller.pjcontroller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.nhanvien.*;
import htkt.repository.nhanvien.BangLuongCbRepository;
import htkt.repository.nhanvien.BangchamcongRepository;
import htkt.repository.nhanvien.BangluongRepository;

@Controller
@RequestMapping("/bangluong")
public class LuongCtrl {
	private BangluongRepository bangluongRepository;
	private BangchamcongRepository bangchamcongRepository;
	private BangLuongCbRepository bangLuongCbRepository;

	public LuongCtrl(BangluongRepository bangluongRepository, BangchamcongRepository bangchamcongRepository,
			BangLuongCbRepository bangLuongCbRepository) {
		this.bangluongRepository = bangluongRepository;
		this.bangchamcongRepository = bangchamcongRepository;
		this.bangLuongCbRepository = bangLuongCbRepository;
	}

	@GetMapping("/tinhluonghtml")
	public String gethtml() {
		return "Luong";
	}

	@PostMapping("/tinhluong")
	public @ResponseBody Optional<Bangluong> getBangLuong(@RequestParam String phongban, @RequestParam String thang) {
		List<Bangluong> listBl = (List<Bangluong>) bangluongRepository.findAll();
		for (Bangluong i : listBl) {
			if (i.getChamcong().getThangLuong().equals(thang) && i.getChamcong().getPhongBan().equals(phongban)) {
				bangluongRepository.delete(i);
			}
		}
		List<Chamcong> list = (List<Chamcong>) bangchamcongRepository.findAll();
		// List<Chamcong> list1 = new ArrayList<>();
		for (Chamcong i : list) {
			if (i.getPhongBan().equals(phongban) && i.getThangLuong().equals(thang)) {
				System.out.print(phongban);
				Bangluong bl = new Bangluong();
				bl.setChamcong(i);
				long millis=System.currentTimeMillis();
				Date date = new Date(millis);
				String datestring = date.toString();
				bl.setThogianTao(datestring);
				Optional<BangLuongCB> bangLuongCB = bangLuongCbRepository.findById(i.getNhanvien().getChucVu());
				if (bangLuongCB.isPresent()) {
					long thuclinh = (bangLuongCB.get().getLuongCoBan() + bangLuongCB.get().getPhuCap()) / 26* (i.getSoCong());
					bl.setThuclinh(thuclinh);
				}
				bl.setSocong(i.getSoCong());
				bangluongRepository.save(bl);
			}
		}
		return bangluongRepository.findById(2);
	}

	@GetMapping("/xembangluong")
	public @ResponseBody List<Bangluong> getAll(@RequestParam String phongban, @RequestParam String thang) {
		List<Bangluong> list = (List<Bangluong>) bangluongRepository.findAll();
		List<Bangluong> rs = new ArrayList<>();
		for (Bangluong i : list) {
			if (i.getChamcong().getThangLuong().equals(thang) && i.getChamcong().getPhongBan().equals(phongban)) {
				rs.add(i);
			}
		}
		return rs;
	}
	@DeleteMapping("/delete/{id}")
	public @ResponseBody void deletebl(@PathVariable("id") int id) {
		bangluongRepository.deleteById(id);
	}
}

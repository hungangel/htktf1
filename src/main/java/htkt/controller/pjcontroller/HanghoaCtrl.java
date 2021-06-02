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

import htkt.model.donhang.Hanghoa;
import htkt.repository.hanghoa.HanghoaRepository;

@Controller
@RequestMapping("/hanghoa")
public class HanghoaCtrl {
	private HanghoaRepository hanghoaRepo;

	public HanghoaCtrl(HanghoaRepository hanghoaRepository) {
		this.hanghoaRepo = hanghoaRepository;
	}

	@PostMapping("/tatcahanghoa")
	public @ResponseBody List<Hanghoa> tatcahoadon() {
		try {
			List<Hanghoa> dshanghoa= (List<Hanghoa>) hanghoaRepo.findAll();
			return dshanghoa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@PostMapping(value = "/themhanghoa", produces = "application/json")
	public @ResponseBody String themnhanvien(@RequestParam("maHH") String maHH, @RequestParam("tenHang") String tenHang,
			@RequestParam("mota") String mota, @RequestParam("donGiaNhap") Long donGiaNhap,
			@RequestParam("donGiaXuat") Long donGiaXuat) {
		Hanghoa hhmoi = new Hanghoa(maHH, tenHang, mota, donGiaNhap, donGiaXuat);
		hanghoaRepo.save(hhmoi);
		return "success";
	}
	@GetMapping("/edit/{maHH}")
	public @ResponseBody Hanghoa edit(@PathVariable("maHH") String maHH) {
		Optional<Hanghoa> hh = hanghoaRepo.findById(maHH);
		Hanghoa hanghoa = new Hanghoa();
		if(hh.isPresent()) {
			hanghoa = hh.get();
		}
		return hanghoa;
	}
	@PutMapping("/suahanghoa/{maHH}")
	public @ResponseBody void suahanghoa(@PathVariable("maHH") String maHH, @RequestParam("tenHang") String tenHang,
			@RequestParam("mota") String mota, @RequestParam("donGiaNhap") Long donGiaNhap,
			@RequestParam("donGiaXuat") Long donGiaXuat) {
			System.out.print(maHH);
			Hanghoa hh1 = new Hanghoa();
			hh1.setMaHH(maHH);
			hh1.setTenHang(tenHang);
			hh1.setMota(mota);
			hh1.setDonGiaNhap(donGiaNhap);
			hh1.setDonGiaXuat(donGiaXuat);
			hanghoaRepo.save(hh1);
		}
	@GetMapping(value = "/timhanghoa")
	public @ResponseBody List<Hanghoa> timhanghoa(@RequestParam("maHH") String maHH,@RequestParam("tenHang") String tenHang) {
		List<Hanghoa> tchh = (List<Hanghoa>) hanghoaRepo.findAll();
		List<Hanghoa> dshh = new ArrayList<>();
		for(int i=0 ; i<tchh.size(); i++) {
			Hanghoa hh = tchh.get(i);
			if(hh.getMaHH().toLowerCase().contains(maHH.toLowerCase())) {
				dshh.add(hh);
			}
			if(hh.getTenHang().toLowerCase().contains(tenHang.toLowerCase())) {
				dshh.add(hh);
			}
		}
		return dshh;
	}


}

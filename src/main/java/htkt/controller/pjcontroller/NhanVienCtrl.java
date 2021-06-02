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

import htkt.model.nhanvien.BangLuongCB;
import htkt.model.nhanvien.Nhanvien;
import htkt.model.nhanvien.Phongban;
import htkt.repository.nhanvien.BangLuongCbRepository;
import htkt.repository.nhanvien.BangchamcongRepository;
import htkt.repository.nhanvien.NhanvienRepository;
import htkt.repository.nhanvien.PhongbanRepository;
import htkt.repository.nhanvien.TaikhoanRepository;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienCtrl {
	private TaikhoanRepository taikhoanRepository;
	private NhanvienRepository nhanvienRepository;
	private BangchamcongRepository chamcongRepo;
	private PhongbanRepository phongbanRepo;
	private BangLuongCbRepository bangLuongCbRepo;

	public NhanVienCtrl(BangLuongCbRepository bangLuongCbRepo, TaikhoanRepository taikhoanRepository,
			NhanvienRepository nhanvienRepository, PhongbanRepository phongbanRepo,
			BangchamcongRepository chamcongRepo) {
		this.taikhoanRepository = taikhoanRepository;
		this.nhanvienRepository = nhanvienRepository;
		this.phongbanRepo = phongbanRepo;
		this.chamcongRepo = chamcongRepo;
		this.bangLuongCbRepo = bangLuongCbRepo;
	}

	@GetMapping("/doimatkhau")
	public String html() {
		return "DoiMatKhau";
	}

	@GetMapping("/dangxuat")
	public String dangxuat() {
		return "redirect:/";
	}

	@GetMapping("/trangchu")
	public String trangchu() {
		return "redirect:/nhanvienketoan";
	}

	@PostMapping(value = "/tatcanhanvien", produces = "application/json")
	public @ResponseBody List<Nhanvien> tatcanhanvien() {
		try {
			List<Nhanvien> dsnhanvien = (List<Nhanvien>) nhanvienRepository.findAll();
			return dsnhanvien;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/themnhanvien", produces = "application/json")
	public @ResponseBody String themnhanvien(@RequestParam("tenNV") String tenNV,
			@RequestParam("ngaySinh") String ngaySinh, @RequestParam("ngayVao") String ngayVao,
			@RequestParam("diaChi") String diaChi, @RequestParam("phongBan") String phongBan,
			@RequestParam("chucVu") String chucVu) {
		Phongban pbnv = phongbanRepo.findById(phongBan).get();
		List<BangLuongCB> lcb = (List<BangLuongCB>) bangLuongCbRepo.findAll();
		long luongCB=0;
		for (BangLuongCB bl : lcb) {
			if (bl.getChucVu().equals(chucVu)) {
				luongCB = bl.getLuongCoBan();
			}
		}
		Nhanvien nvmoi = new Nhanvien(0, tenNV, ngaySinh, ngayVao, diaChi, pbnv, luongCB, chucVu);
		nhanvienRepository.save(nvmoi);
		return "success";
	}

	@GetMapping("/edit/{maNV}")
	public @ResponseBody Nhanvien edit(@PathVariable("maNV") int maNV) {
		Optional<Nhanvien> nv = nhanvienRepository.findById(maNV);
		Nhanvien nhanvien = new Nhanvien();
		if (nv.isPresent()) {
			nhanvien = nv.get();
		}
		return nhanvien;
	}

	@PutMapping("/suanhanvien/{maNV}")
	public @ResponseBody void suanhanvien(@PathVariable("maNV") int maNV, @RequestParam("tenNV") String tenNV,
			@RequestParam("ngaySinh") String ngaySinh, @RequestParam("ngayVao") String ngayVao,
			@RequestParam("diaChi") String diaChi, @RequestParam("phongBan") String phongBan,
			@RequestParam("chucVu") String chucVu) {
		System.out.print(maNV);
		long luongCB=0;
		Phongban pbnv1 = phongbanRepo.findById(phongBan).get();
		List<BangLuongCB> lcb = (List<BangLuongCB>) bangLuongCbRepo.findAll();
		for (BangLuongCB bl : lcb) {
			if (bl.getChucVu().equals(chucVu)) {
				luongCB = bl.getLuongCoBan();
			}
		}
		Nhanvien nvql = new Nhanvien();
		nvql.setMaNV(maNV);
		nvql.setTenNV(tenNV);
		nvql.setNgaySinh(ngaySinh);
		nvql.setNgayVao(ngayVao);
		nvql.setDiaChi(diaChi);
		nvql.setPhongban(pbnv1);
		nvql.setLuongCB(luongCB);
		nvql.setChucVu(chucVu);
		nhanvienRepository.save(nvql);
	}

	@GetMapping(value = "/timnhanvien")
	public @ResponseBody List<Nhanvien> timnhanvien(@RequestParam("tenNV") String tenNV) {
		List<Nhanvien> tcnv = (List<Nhanvien>) nhanvienRepository.findAll();
		List<Nhanvien> dsnv = new ArrayList<>();
		for (int i = 0; i < tcnv.size(); i++) {
			Nhanvien nv = tcnv.get(i);
			if (nv.getTenNV().toLowerCase().contains(tenNV.toLowerCase())) {
				dsnv.add(nv);
			}
		}
		return dsnv;
	}
	/*
	 * @PostMapping("/doimatkhau") public Taikhoan doimatkhau(@RequestParam String
	 * taikhoanString , @RequestParam String matkhau) {
	 * 
	 * }
	 */

}

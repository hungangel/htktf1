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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.chungtu.PhieuThuchi;
import htkt.model.chungtu.Quy;
import htkt.model.nhanvien.Nhanvien;
import htkt.repository.donhang.PhieuThuchiRepository;
import htkt.repository.nhanvien.NhanvienRepository;
import htkt.repository.nhanvien.QuyRepository;

@Controller
@RequestMapping("/phieu")
public class PhieuThuChiCtrl {
	private PhieuThuchiRepository phieuThuchiRepository;
	private NhanvienRepository nhanvienRepository;
	private QuyRepository quyRepository;
	public PhieuThuChiCtrl(PhieuThuchiRepository phieuThuchiRepository,NhanvienRepository nhanvienRepository,QuyRepository quyRepository) {
		this.phieuThuchiRepository = phieuThuchiRepository;
		this.nhanvienRepository = nhanvienRepository;
		this.quyRepository = quyRepository;
	}
	@PostMapping("/taophieu")
	public @ResponseBody void luu( @RequestParam("loaiphieu") String loaiphieu , @RequestParam("sotien") long sotien , @RequestParam("nguoinhannop") String nguoinhannop,@RequestParam("lido") String lido , @RequestParam("manv") int manv) {
		Optional<Quy> quy = quyRepository.findById(1);
		if(quy.isPresent()) {
			if(loaiphieu.equals("Thu")) {
				quy.get().setSodu(quy.get().getSodu()+sotien);
			}
			else quy.get().setSodu(quy.get().getSodu()-sotien);
			quyRepository.save(quy.get());
		}
		Optional<Nhanvien> nhanvien = nhanvienRepository.findById(manv);
		if(nhanvien.isPresent()) {
			PhieuThuchi phieu = new PhieuThuchi();
			phieu.setLoaiPhieu(loaiphieu);
			phieu.setNguoiNop_nhan(nguoinhannop);
			phieu.setSoTien(sotien);
			long millis=System.currentTimeMillis();
			Date date = new Date(millis);
			String datestring = date.toString();
			phieu.setThoiGian(datestring);
			phieu.setNhanvien(nhanvien.get());
			phieu.setLido(lido);
			phieu.setDonviTinh("vnd");
			phieuThuchiRepository.save(phieu);
			
		}
		
	}
	@GetMapping("/xemphieu")
	public @ResponseBody List<PhieuThuchi> showPhieu(@RequestParam("loaiphieu") String loaiphieu){
		List<PhieuThuchi> list = (List<PhieuThuchi>) phieuThuchiRepository.findAll();
		List<PhieuThuchi> list1 = new ArrayList<>();
		for(PhieuThuchi i : list) {
			if(i.getLoaiPhieu().equals(loaiphieu)) {
				list1.add(i);
			}
		}
		return list1;
	}
	@DeleteMapping("/delete/{id}")
	public @ResponseBody void delete(@PathVariable int id) {
		Optional<PhieuThuchi> phieu = phieuThuchiRepository.findById(id);
		if(phieu.isPresent()) {
			if(phieu.get().getLoaiPhieu().equals("Thu")) {
			quyRepository.findById(1).get().setSodu(quyRepository.findById(1).get().getSodu()-phieu.get().getSoTien());
			}
			else quyRepository.findById(1).get().setSodu(quyRepository.findById(1).get().getSodu()+phieu.get().getSoTien());
		}
		phieuThuchiRepository.deleteById(id);
	}
	@GetMapping("edit/{id}")
	public @ResponseBody PhieuThuchi edit(@PathVariable int id) {
		Optional<PhieuThuchi> phieu = phieuThuchiRepository.findById(id);
		PhieuThuchi phieuThuchi = new PhieuThuchi();
		if(phieu.isPresent()) {
			phieuThuchi = phieu.get();
		}
		return phieuThuchi;
	}
	@PutMapping("update/{id}")
	public @ResponseBody  void updatePhieu(@PathVariable int id,@RequestParam("loaiphieu") String loaiphieu , @RequestParam("sotien") long sotien , @RequestParam("nguoinhannop") String nguoinhannop,@RequestParam("lido") String lido , @RequestParam("manv") int manv) {
		System.out.print(loaiphieu);
		Optional<PhieuThuchi> phieuThuchi = phieuThuchiRepository.findById(id);
		Optional<Quy> quy = quyRepository.findById(1);
		PhieuThuchi phieuThuchi2 = new PhieuThuchi();
		if(phieuThuchi.isPresent()) {
			phieuThuchi2 = phieuThuchi.get();
		}
		if(phieuThuchi2.getLoaiPhieu().equals("Thu")&& loaiphieu.equals("Thu")) {
			quy.get().setSodu(quy.get().getSodu()-phieuThuchi2.getSoTien()+sotien);
		}
		else if(phieuThuchi2.getLoaiPhieu().equals("Thu")&& loaiphieu.equals("Chi")) {
			quy.get().setSodu(quy.get().getSodu()-phieuThuchi2.getSoTien()-sotien);
		}
		else if(phieuThuchi2.getLoaiPhieu().equals("Chi")&& loaiphieu.equals("Chi")) {
			quy.get().setSodu(quy.get().getSodu()+phieuThuchi2.getSoTien()-sotien);
		}
		else if(phieuThuchi2.getLoaiPhieu().equals("Chi")&& loaiphieu.equals("Thu")) {
			quy.get().setSodu(quy.get().getSodu()+phieuThuchi2.getSoTien()+sotien);
		}
		quyRepository.save(quy.get());
		Optional<Nhanvien> nhanvien = nhanvienRepository.findById(manv);
		phieuThuchi2.setLoaiPhieu(loaiphieu);
		phieuThuchi2.setSoTien(sotien);
		phieuThuchi2.setNguoiNop_nhan(nguoinhannop);
		phieuThuchi2.setLido(lido);
		long millis=System.currentTimeMillis();
		Date date = new Date(millis);
		String datestring = date.toString();
		phieuThuchi2.setThoiGian(datestring);
		phieuThuchi2.setNhanvien(nhanvien.get());
		phieuThuchiRepository.save(phieuThuchi2);
		
	}
	
}

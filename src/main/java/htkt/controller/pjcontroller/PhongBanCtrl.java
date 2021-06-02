package htkt.controller.pjcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.nhanvien.Phongban;
import htkt.repository.nhanvien.PhongbanRepository;

@Controller
@RequestMapping("/phongban")
public class PhongBanCtrl {
	private PhongbanRepository phongbanRepository;

	public PhongBanCtrl(PhongbanRepository phongbanRepository) {
		this.phongbanRepository = phongbanRepository;
	}
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		List<Phongban> pbList = (List<Phongban>) phongbanRepository.findAll();
		return new ResponseEntity<Object>(pbList, HttpStatus.OK);
	}
	@PostMapping("/tatcaphongban")
	public @ResponseBody List<Phongban> tatcaphongban() {
		try {
			List<Phongban> dsphongban = (List<Phongban>) phongbanRepository.findAll();
			return dsphongban;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@PostMapping(value = "/themphongban", produces = "application/json")
	public @ResponseBody String themphongban(@RequestParam("maPhongban") String maPhongban, @RequestParam("mota") String mota,
			@RequestParam("vitriVP") String vitriVP) {
		Phongban pbmoi = new Phongban(maPhongban, mota, vitriVP);
		phongbanRepository.save(pbmoi);
		return "success";
	}
	@GetMapping("/edit/{maPhongban}")
	public @ResponseBody Phongban edit(@PathVariable("maPhongban") String maPhongban) {
		Optional<Phongban> pb = phongbanRepository.findById(maPhongban);
		Phongban phongban = new Phongban();
		if(pb.isPresent()) {
			phongban = pb.get();
		}
		return phongban;
	}
	@PutMapping("/suaphongban/{maPhongban}")
	public @ResponseBody void suahanghoa(@PathVariable("maPhongban") String maPhongban,  @RequestParam("mota") String mota,
			@RequestParam("vitriVP") String vitriVP) {
			System.out.print(maPhongban);
			Phongban pb1 = new Phongban();
			pb1.setMaPhongban(maPhongban);
			pb1.setMota(mota);
			pb1.setVitriVP(vitriVP);
			phongbanRepository.save(pb1);
		}
	@GetMapping(value = "/timphongban")
	public @ResponseBody List<Phongban> timphongban(@RequestParam("maPhongban") String maPhongban) {
		List<Phongban> tcpb = (List<Phongban>) phongbanRepository.findAll();
		List<Phongban> dspb = new ArrayList<>();
		for(int i=0 ; i<tcpb.size(); i++) {
			Phongban pb2 = tcpb.get(i);
			if(pb2.getMaPhongban().toLowerCase().contains(maPhongban.toLowerCase())) {
				dspb.add(pb2);
			}
		}
		return dspb;
	}
}

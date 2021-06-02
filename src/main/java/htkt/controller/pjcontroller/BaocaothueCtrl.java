package htkt.controller.pjcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.chungtu.Baocaothue;
import htkt.repository.donhang.BaocaothueRepository;

@Controller
@RequestMapping("/baocaothue")
public class BaocaothueCtrl {
	private BaocaothueRepository baocaothueRepo;

	public BaocaothueCtrl(BaocaothueRepository baocaothueRepository) {
		this.baocaothueRepo = baocaothueRepository;
	}

	@GetMapping("/tatcabaocao")
	public ResponseEntity<List<Baocaothue>> tatcahoadon() {
		try {
			List<Baocaothue> dsbaocao = new ArrayList<Baocaothue>();
			baocaothueRepo.findAll().forEach(dsbaocao::add);
			if (dsbaocao.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			return new ResponseEntity<>(dsbaocao, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/xembaocaothuethang/{thangnam}")
	public @ResponseBody Baocaothue baocaothang(@PathVariable("thangnam") String thangnam) {
		try {
			List<Baocaothue> dsbaocao = new ArrayList<Baocaothue>();
			baocaothueRepo.findAll().forEach(dsbaocao::add);
			for (Baocaothue bct : dsbaocao) {
				if (Subfunction.thangcungthang(bct.getThoigian(), thangnam)) {
					return bct;
				}
			}
			return new Baocaothue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/luutokhaithue/{thangnam}")
	public @ResponseBody String taobaocaothang(@PathVariable("thangnam") String thangnam,
			@RequestParam("thueNokitruoc") long thueNokitruoc, @RequestParam("doanhsoMua") long doanhsoMua,
			@RequestParam("thueMua") long thueMua, @RequestParam("doanhsoBan") long doanhsoBan,@RequestParam("thueKinay") long thueKinay,
			@RequestParam("thuecantra") long thuecantra,@RequestParam("thueNolai") long thueNolai) {
		try {
			List<Baocaothue> dsBaocaothues= (List<Baocaothue>) baocaothueRepo.findAll();
			for( Baocaothue bc: dsBaocaothues) {
				if(bc.getThoigian().equals(thangnam)) {
					baocaothueRepo.deleteById(bc.getMasoBaocao());
				}
			}
			Baocaothue bct = new Baocaothue(0, "1234", thangnam, thueNokitruoc, doanhsoMua, thueMua, doanhsoBan, thueKinay, thuecantra, thueNolai);
			baocaothueRepo.save(bct);
			return "Đã lưu tờ khai thuế";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}

	@GetMapping("/xoabaocaothuethang/{thangnam}")
	public ResponseEntity<List<Baocaothue>> xoabaocaothuethang(@PathVariable("thangnam") String thangnam) {
		try {
			List<Baocaothue> dsbaocao = new ArrayList<Baocaothue>();
			baocaothueRepo.findAll().forEach(dsbaocao::add);
			if (dsbaocao.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			for(int i=0; i< dsbaocao.size(); i++) {
				Baocaothue bct= dsbaocao.get(i);
				if(Subfunction.thangcungthang(bct.getThoigian(), thangnam)) {
					baocaothueRepo.deleteById(bct.getMasoBaocao());
					dsbaocao.remove(i);
					break;
				}
			}
			return new ResponseEntity<>(dsbaocao, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

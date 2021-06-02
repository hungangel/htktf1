package htkt.controller.menucontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nvtq")
public class MenuThuquyCtrl {
	
	@GetMapping("/nhatkithuchihtml")
	public String nhatkichihtml() {
		return"NhatKhiThuChi";
	}
	@GetMapping("/baocaocongnothuhtml")
	public String congnothuhtml() {
		return"baocaocongnothu";
	}
	@GetMapping("/baocaocongnotrahtml")
	public String congnotrahtml() {
		return"baocaocongnotra";
	}

}

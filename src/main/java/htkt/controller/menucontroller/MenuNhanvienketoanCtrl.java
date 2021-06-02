package htkt.controller.menucontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nvkt")
public class MenuNhanvienketoanCtrl {
	@GetMapping("/kekhaidonmuavaohtml")
	public String kekhaidonmuavaohtml() {
		return"kekhaidonmuavao";
	}
	@GetMapping("/kekhaidonbanrahtml")
	public String kekhaidonbanrahtml() {
		return"kekhaidonbanra";
	}

	@GetMapping("/baocaodoanhthuhtml")
	public String baocaodoanhthuhtml(Model model) {
		return"baocaodoanhthu";
	}
	
	@GetMapping("/danhmuchangbanhtml")
	public String danhmuchangbanhtml(Model model) {
		return"danhmuchangban";
	}
	@GetMapping("/danhmuchangmuahtml")
	public String danhmuchangmuahtml(Model model) {
		return"danhmuchangmua";
	}
	@GetMapping("/xemtruoctokhaithuehtml")
	public String xemtruoctokhaithuehtml() {
		return"xemtruoctokhaithue";
	}
	@GetMapping("/xemtokhaithuehtml")
	public String xemtokhaithuehtml() {
		return"xemtokhaithue";
	}
}

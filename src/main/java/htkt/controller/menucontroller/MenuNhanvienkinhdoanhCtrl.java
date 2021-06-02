package htkt.controller.menucontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nvkd")
public class MenuNhanvienkinhdoanhCtrl {
	@GetMapping("/xemhoadon")
	public String getAll() {
		return "xemhoadon";
	}
	@GetMapping("/themhoadon")
	public String themhoadon() {
		return "themhoadon";
	}
	@GetMapping("/xemkhachhang1html")
	public String xemkhachhang1() {
		return "xemkhachhang1";
	}
}

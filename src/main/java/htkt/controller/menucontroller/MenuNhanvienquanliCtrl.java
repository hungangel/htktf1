package htkt.controller.menucontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nvql")
public class MenuNhanvienquanliCtrl {
	@GetMapping("/nhanvienhtml")
	public String trave() {
		return "Xemnhanvien";
	}

	@GetMapping("/hanghoahtml")
	public String trave1() {
		return "Xemhanghoa";
	}
	@GetMapping("/phongbanhtml")
	public String trave2() {
		return "Xemphongban";
	}
	@GetMapping("/xemkhachhanghtml")
	public String xemkhachhanghtml() {
		return "xemkhachhang";
	}
}

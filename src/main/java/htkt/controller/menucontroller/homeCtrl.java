package htkt.controller.menucontroller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import htkt.model.nhanvien.Taikhoan;
import htkt.repository.nhanvien.TaikhoanRepository;


@Controller
public class homeCtrl {
	private TaikhoanRepository taikhoanRepository;
	public homeCtrl(TaikhoanRepository taikhoanRepository) {
		this.taikhoanRepository = taikhoanRepository ;
	}
	@GetMapping("/nhanvienketoan")
	public String home(HttpSession session,Model model) {
		return "NhanVienKeToanHome";
	}
	@GetMapping("/nhanvienkinhdoanh")
	public String NVKDhome(HttpSession session,Model model) {
		return "nvkinhdoanhhome";
	}
	@GetMapping("/nhanvienthuquy")
	public String NVTQhome(HttpSession session,Model model) {
		return "NhanVienThuQuyHome";
	}
	@GetMapping("/admin")
	public String adminhome(HttpSession session,Model model) {
		return "nvquanlihome";
	}
	@GetMapping
	public String login() {
		return "Login";
	}
	@GetMapping("/info")
	public String info() {
		return "XemTTCN";
	}
	@GetMapping("/doimatkhau")
	public String mk() {
		return "DoiMatKhau";
	}
	@GetMapping("/trangchuhtml")
		public String trangchu() {
		return "trangchu";
	}
	@PostMapping("/home")
    public RedirectView checkLogin(RedirectAttributes redir,HttpSession ss,@RequestParam(name = "taikhoan")String taikhoan , @RequestParam(name ="matkhau")String matkhau ) {
		RedirectView redirectView =new RedirectView("/",true);
		Optional<Taikhoan> tk= taikhoanRepository.findById(taikhoan);
		if(tk.isPresent()) {
			if(tk.get().getPassword().equals(matkhau)) {
				//model.addAttribute("user",tk.get().getNhanvien());
				 redir.addFlashAttribute("user",tk.get().getNhanvien());
				 Taikhoan tkTaikhoan = tk.get();
				 ss.setAttribute("nguoidung", tkTaikhoan);
				// redir.addFlashAttribute("user",ss);
			if(tk.get().getNhanvien().getChucVu().equals("NVKT")) {
					redirectView= new RedirectView("/nhanvienketoan",true);		
				    return redirectView;
				}
			else if(tk.get().getNhanvien().getChucVu().equals("NVKD")) {
				redirectView= new RedirectView("/nhanvienkinhdoanh",true);		
			    return redirectView;
			}
			else if(tk.get().getNhanvien().getChucVu().equals("NVTQ")) {
				redirectView= new RedirectView("/nhanvienthuquy",true);		
			    return redirectView;
			}
			else {
				redirectView= new RedirectView("/admin",true);		
			    return redirectView;
			}
				}
				
				
			}
		
		return redirectView;	
	}
}

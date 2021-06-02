package htkt.controller.pjcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import htkt.model.chungtu.Quy;
import htkt.repository.nhanvien.QuyRepository;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/quy")
public class QuyCtrl {
		private QuyRepository quyRepository ; 
		public QuyCtrl(QuyRepository quyRepository) {
			this.quyRepository = quyRepository;
		}
		@GetMapping("/sodu")
		public @ResponseBody Quy sodu() {
			return quyRepository.findById(1).get();
		}
}

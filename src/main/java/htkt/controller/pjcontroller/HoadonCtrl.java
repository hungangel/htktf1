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
import htkt.model.donhang.ChitietHoadon;
import htkt.model.donhang.Hanghoa;
import htkt.model.donhang.Hoadon;
import htkt.model.donhang.Khachhang;
import htkt.model.nhanvien.Nhanvien;
import htkt.repository.donhang.HoadonRepository;
import htkt.repository.donhang.KhachhangRepository;
import htkt.repository.hanghoa.HanghoaRepository;
import htkt.repository.nhanvien.NhanvienRepository;

@Controller
@RequestMapping("/hoadon")
public class HoadonCtrl {
	private HoadonRepository hoadonRepo;
	private KhachhangRepository khachhangRepo;
	private NhanvienRepository nhanvienRepo;
	private HanghoaRepository hanghoaRepo;

	public HoadonCtrl(HoadonRepository hoadonRepo, KhachhangRepository khachhangRepo, NhanvienRepository nhanvienRepo,
			HanghoaRepository hanghoaRepository) {
		this.hoadonRepo = hoadonRepo;
		this.khachhangRepo = khachhangRepo;
		this.nhanvienRepo = nhanvienRepo;
		this.hanghoaRepo = hanghoaRepository;
	}

	@GetMapping("/testfc")
	public ResponseEntity<List<Hoadon>> te() {
		try {
			String ngaydau = "17-10-2020";
			String ngaycuoi = "01-01-2022";
			String d3 = "24-05-2021";
			System.out.println(Subfunction.namgiua(ngaydau, d3, ngaycuoi));
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			hoadonRepo.findAll().forEach(dshoadon::add);
			if (dshoadon.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			return new ResponseEntity<>(dshoadon, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//Tất cả hóa đơn
	@PostMapping(value = "/tatcahoadon", produces = "application/json")
	public @ResponseBody List<Hoadon> tatcahoadon() {
		try {
			List<Hoadon> dshoadon = (List<Hoadon>) hoadonRepo.findAll();
			return dshoadon;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@PostMapping(value = "/xoahoadon/{maHoadon}", produces = "application/json")
	public @ResponseBody Hoadon xoahoadon(@PathVariable("maHoadon") int maHoadon) {
		try {
			List<Hoadon> dshoadon =  (List<Hoadon>) hoadonRepo.findAll();
			for(int i=0; i< dshoadon.size(); i++) {
				if(dshoadon.get(i).getMaHoadon()== maHoadon) {
					if(!Subfunction.ngaycungthang(dshoadon.get(i).getNgayLap(), "2021-06")) {
						return dshoadon.get(i);
					}else {
						hoadonRepo.deleteById(maHoadon);
						
					}
				}
			}

			return new Hoadon();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@PostMapping(value = "/timhoadon/{tukhoa}", produces = "application/json")
	public @ResponseBody List<Hoadon> timhoadon(@PathVariable("tukhoa") String tukhoa) {
		try {
			List<Hoadon> dshoadon = (List<Hoadon>) hoadonRepo.findAll();
			List<Hoadon> kq = new ArrayList<>();
 			for(Hoadon hd: dshoadon) {
				if(hd.getKhachhang().getTenKH().toLowerCase().contains(tukhoa.toLowerCase())) {
					kq.add(hd);
				}
			}
			if(kq.size()>0) {
				return kq;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@PostMapping(value = "/luuhoadon", produces = "application/json")
	public @ResponseBody String luuhoadon(@RequestParam("makh") String maKH, @RequestParam("manv") String maNV,
			@RequestParam("ngaylap") String ngayLap, @RequestParam("loaihoadon") String loaiHoadon,
			@RequestParam("mota") String mota, @RequestParam("tongtien") long tongTien,
			@RequestParam("chietkhau") long chietKhau, @RequestParam("donvitinh") String donvitinh,
			@RequestParam("dshanghoa") ArrayList<ArrayList<Object>> dshanghoa) {
//		System.out.println(
//				"res:" + maKH + maNV + ngayLap + loaiHoadon + mota + tongTien + chietKhau + donvitinh + dshanghoa);
//		for (int i = 0; i < dshanghoa.size(); i++) {
//			System.out.print("_"+i+"_");
//			for(int j=0;j < dshanghoa.get(i).size(); j++) {
//				System.out.println(dshanghoa.get(i).get(j));;
//			}
//		}
		Khachhang khachhang = khachhangRepo.findById(Integer.parseInt(maKH)).get();
		Nhanvien nhanvien = nhanvienRepo.findById(Integer.parseInt(maNV)).get();
		List<ChitietHoadon> chitietHoadons = new ArrayList<>();

		for (int i = 1; i < dshanghoa.size(); i++) {
			System.out.println(dshanghoa.size());
			System.out.println(dshanghoa.get(i).size());
			ArrayList<Object> cthanghoa = dshanghoa.get(i);
			System.out.println("" + cthanghoa.get(0));
			System.out.println("" + cthanghoa.get(1));
			System.out.println("" + cthanghoa.get(2));
			System.out.println("" + cthanghoa.get(3));
			Hanghoa hanghoa = hanghoaRepo.findById((String) cthanghoa.get(0)).get();

			int soluong = Integer.parseInt((String) cthanghoa.get(2));
			long dongia = Long.parseLong((String) cthanghoa.get(3));
			ChitietHoadon cthd = new ChitietHoadon(hanghoa, soluong, dongia);
			chitietHoadons.add(cthd);
		}

		Hoadon newhd = new Hoadon(khachhang, nhanvien, loaiHoadon, ngayLap, "2000-01-01", "Chưa thanh toán", mota,
				tongTien, chietKhau, donvitinh, chitietHoadons);
		hoadonRepo.save(newhd);
		return "ok";

	}

//ds hóa đơn mua vào + tổng giá trị
	@PostMapping(value = "/kekhaidonmuavao", produces = "application/json")
	public @ResponseBody List<Object> kekhaidonmuavao(@RequestParam(value = "thangnam") String thangnam) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonmua = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			long thue = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("Mua") && Subfunction.ngaycungthang(hd.getNgayLap(), thangnam)) {
					tonggiatri += hd.getTongTien();
					dshoadonmua.add(hd);
				}
			}
			thue = tonggiatri / 10;
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonmua);
			dulieu.add(tonggiatri);
			dulieu.add(thue);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@PostMapping(value = "/lochoadonmua", produces = "application/json")
	public @ResponseBody List<Object> lochoadonmua(@RequestParam(value = "ngaydau") String ngaydau,@RequestParam(value = "ngaycuoi") String ngaycuoi) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonmua = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			long thue = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("Mua") && Subfunction.namgiua(ngaydau, hd.getNgayLap(), ngaycuoi)) {
					tonggiatri += hd.getTongTien();
					dshoadonmua.add(hd);
				}
			}
			thue = tonggiatri / 10;
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonmua);
			dulieu.add(tonggiatri);
			dulieu.add(thue);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@PostMapping(value = "/lochoadonban", produces = "application/json")
	public @ResponseBody List<Object> lochoadonban(@RequestParam(value = "ngaydau") String ngaydau,@RequestParam(value = "ngaycuoi") String ngaycuoi) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonmua = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			long thue = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("Bán") && Subfunction.namgiua(ngaydau, hd.getNgayLap(), ngaycuoi)) {
					tonggiatri += hd.getTongTien();
					dshoadonmua.add(hd);
				}
			}
			thue = tonggiatri / 10;
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonmua);
			dulieu.add(tonggiatri);
			dulieu.add(thue);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@PostMapping(value = "/kekhaidonbanra", produces = "application/json")
	public @ResponseBody List<Object> kekhaidonbanra(@RequestParam(value = "thangnam") String thangnam) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonban = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			long thue = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			System.out.println("HD:" + dshoadon.size() + " hoadon");
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("Bán") && Subfunction.ngaycungthang(hd.getNgayLap(), thangnam)) {
					tonggiatri += hd.getTongTien();
					dshoadonban.add(hd);
				}
			}
			thue = tonggiatri / 10;
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonban);
			dulieu.add(tonggiatri);
			dulieu.add(thue);
			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping(value = "/baocaodoanhthu", produces = "application/json")
	public @ResponseBody List<Object> baocaodoanhthu(@RequestParam(value = "thangnam") String thangnam) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonban = new ArrayList<Hoadon>();
			String tieuchi[] = { "Số đơn hàng", "Tổng giá trị", "Doanh thu", "Khách hàng còn nợ", "Tiền thuế" };
			long thangnay[] = { 0, 0, 0, 0, 0 };
			long thangtruoc[] = { 0, 0, 0, 0, 0 };

			hoadonRepo.findAll().forEach(dshoadon::add);
			System.out.println("HD:" + dshoadon.size() + " hoadon");
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("Bán") && Subfunction.ngaycungthang(hd.getNgayLap(), thangnam)) {
					thangnay[0]++;
					thangnay[1] += hd.getTongTien();
					dshoadonban.add(hd);
					if (hd.getDaThanhtoan().toLowerCase().contains("đ"))
						thangnay[2] += hd.getTongTien();
				}
			}
			thangnay[3] = thangnay[1] - thangnay[2];
			thangnay[4] = thangnay[1] / 10;

			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("Bán") && Subfunction.thangtruoc(hd.getNgayLap(), thangnam)) {
					thangtruoc[0]++;
					thangtruoc[1] += hd.getTongTien();
					if (hd.getDaThanhtoan().toLowerCase().contains("đ"))
						thangtruoc[2] += hd.getTongTien();
				}
			}
			thangtruoc[3] = thangtruoc[1] - thangtruoc[2];
			thangtruoc[4] = thangtruoc[1] / 10;
			String danhgia[] = { Subfunction.danhgia(thangnay[0], thangtruoc[0]),
					Subfunction.danhgia(thangnay[1], thangtruoc[1]), Subfunction.danhgia(thangnay[2], thangtruoc[2]),
					"", "" };

			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonban);
			dulieu.add(tieuchi);
			dulieu.add(thangnay);
			dulieu.add(thangtruoc);
			dulieu.add(danhgia);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/xemtruoctokhaithue/{thangnam}")
	public @ResponseBody Baocaothue xemtruoctokhaithue(@PathVariable("thangnam") String thangnam) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			long giatrikitruoc = 0;
			long thueNokitruoc = 0;

			long doanhsoMua = 0;
			long thueMua = 0;

			long doanhsoBan = 0;
			long thueKinay = 0;
			long thuecantra = 0;
			long khachhangNo = 0;
			
			hoadonRepo.findAll().forEach(dshoadon::add);

			for (Hoadon hd : dshoadon) {
				System.out.println(hd.getNgayLap()+"__"+ thangnam);
				if (Subfunction.ngaycungthang(hd.getNgayLap(), thangnam)) {
					if (hd.getLoaiHoadon().contains("Mua")) {
						doanhsoMua += hd.getTongTien();
					} else {
						doanhsoBan += hd.getTongTien();
						if (hd.getDaThanhtoan().contains("Chưa")) {
							khachhangNo += hd.getTongTien();
						}
					}
				} else {
					if (Subfunction.ngaycungthang(hd.getNgayThanhtoan(), thangnam)) {
						giatrikitruoc += hd.getTongTien();
					}
				}
			}
			thueNokitruoc = giatrikitruoc / 10;

			thueMua = doanhsoMua / 10;
			thueKinay = doanhsoBan / 10;
			long thueNolai = 0;
			thuecantra = thueKinay - thueMua +thueNokitruoc;
			if(thuecantra<0) {
				thueNolai = Math.abs(thuecantra);
				thuecantra=0;
				
			}
			System.out.println(thueNolai +"_oo_"+ khachhangNo/10);
 			thueNolai =thueNolai+ khachhangNo / 10;
 			System.out.println(thueNolai +"_oo_"+ khachhangNo/10);
			Baocaothue bct = new Baocaothue(0, "1234", thangnam, thueNokitruoc, doanhsoMua, thueMua, doanhsoBan,
					thueKinay, thuecantra, thueNolai);

			return bct;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//// Danh sách hóa đơn mua hàng, chưa trả tiền (công nợ trả)
	@PostMapping("/baocaocongnotra")
	public @ResponseBody List<Object> congnotra(@RequestParam("thangnam") String thangnam) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonthang = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().toLowerCase().contains("mua")
						&& hd.getDaThanhtoan().toLowerCase().contains("chưa")
						&& Subfunction.ngaycungthang(hd.getNgayLap(), thangnam)) {
					dshoadonthang.add(hd);
					tonggiatri += hd.getTongTien();
				}
			}
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonthang);
			dulieu.add(tonggiatri);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

// Danh sách hóa đơn bán hàng, chưa thu tiền (công nợ thu)
	@PostMapping("/baocaocongnothu")
	public @ResponseBody List<Object> baocaocongnothu(@RequestParam("thangnam") String thangnam) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonthang = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().toLowerCase().contains("bán")
						&& hd.getDaThanhtoan().toLowerCase().contains("chưa")
						&& Subfunction.ngaycungthang(hd.getNgayLap(), thangnam)) {
					dshoadonthang.add(hd);
					tonggiatri += hd.getTongTien();
				}
			}
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonthang);
			dulieu.add(tonggiatri);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/tatcacongnothu")
	public @ResponseBody List<Object> tatcacongnothu() {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonthang = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().toLowerCase().contains("bán")
						&& hd.getDaThanhtoan().toLowerCase().contains("chưa")) {
					dshoadonthang.add(hd);
					tonggiatri += hd.getTongTien();
				}
			}
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonthang);
			dulieu.add(tonggiatri);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/tatcacongnotra")
	public @ResponseBody List<Object> tatcacongnotra() {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hoadon> dshoadonthang = new ArrayList<Hoadon>();
			long tonggiatri = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().toLowerCase().contains("mua")
						&& hd.getDaThanhtoan().toLowerCase().contains("chưa")) {
					dshoadonthang.add(hd);
					tonggiatri += hd.getTongTien();
				}
			}
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshoadonthang);
			dulieu.add(tonggiatri);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/giaynhacno/{maHoadon}")
	public @ResponseBody Hoadon giaynhacno(@PathVariable("maHoadon") int maHoadon) {
		try {
			return hoadonRepo.findById(maHoadon).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/danhdaudatra/{maHoadon}")
	public @ResponseBody Hoadon danhdaudatra(@PathVariable("maHoadon") int maHoadon,
			@RequestParam("ngayThanhtoan") String ngayThanhtoan) {
		try {
			Hoadon hdHoadon = hoadonRepo.findById(maHoadon).get();
			hdHoadon.setDaThanhtoan("Đã thanh toán");
			hdHoadon.setNgayThanhtoan(ngayThanhtoan);
			System.out.println("MaxHD "+maHoadon);
			return hoadonRepo.save(hdHoadon);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//Danh sách hàng hóa đã bán + số lượng + doanh thu theo tháng
	@PostMapping("/danhmuchangban")
	public @ResponseBody List<Object> danhmuchangban(@RequestParam("ngaydau") String ngaydau,
			@RequestParam("ngaycuoi") String ngaycuoi) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hanghoa> dshanghoa = new ArrayList<>();
			List<Integer> dssoluongban = new ArrayList<>();
			List<Long> dsthanhtien = new ArrayList<>();
			long tonggiatri = 0;
			long doanhthu = 0;

			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("án") && Subfunction.namgiua(ngaydau, hd.getNgayLap(), ngaycuoi)) {
					tonggiatri += hd.getTongTien();
					if (hd.getDaThanhtoan().contains("Đã"))
						doanhthu += hd.getTongTien();
					for (ChitietHoadon cthd : hd.getChitietHoadons()) {
						Hanghoa hh = cthd.getHanghoa();
						if (dshanghoa.isEmpty()) {
							dshanghoa.add(hh);
							dssoluongban.add(cthd.getSoluong());
							dsthanhtien.add(cthd.getDongia() * cthd.getSoluong());
						} else {
							boolean nf = true;
							for (int i = 0; i < dshanghoa.size(); i++) {
								if (dshanghoa.get(i).getMaHH().equals(hh.getMaHH())) {
									dssoluongban.set(i, dssoluongban.get(i) + cthd.getSoluong());
									dsthanhtien.set(i, cthd.getDongia() * cthd.getSoluong());
									nf = false;
								}
							}
							if (nf) {
								dshanghoa.add(hh);
								dssoluongban.add(cthd.getSoluong());
								dsthanhtien.add(cthd.getDongia() * cthd.getSoluong());
							}
						}
					}
				}
			}

			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshanghoa);
			dulieu.add(dssoluongban);
			dulieu.add(dsthanhtien);
			dulieu.add(tonggiatri);
			dulieu.add(doanhthu);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Danh sách hàng hóa đã mua + số lượng + chi phí theo tháng
	@PostMapping("/danhmuchangmua")
	public @ResponseBody List<Object> danhmuchangmua(@RequestParam("ngaydau") String ngaydau,
			@RequestParam("ngaycuoi") String ngaycuoi) {
		try {
			List<Hoadon> dshoadon = new ArrayList<Hoadon>();
			List<Hanghoa> dshanghoa = new ArrayList<>();
			List<Integer> dssoluongmua = new ArrayList<>();
			List<Long> dsthanhtien = new ArrayList<>();
			long tonggiatri = 0;
			hoadonRepo.findAll().forEach(dshoadon::add);
			for (Hoadon hd : dshoadon) {
				if (hd.getLoaiHoadon().contains("ua") && Subfunction.namgiua(ngaydau, hd.getNgayLap(), ngaycuoi)) {
					tonggiatri += hd.getTongTien();
					for (ChitietHoadon cthd : hd.getChitietHoadons()) {
						Hanghoa hh = cthd.getHanghoa();
						if (dshanghoa.isEmpty()) {
							dshanghoa.add(hh);
							dssoluongmua.add(cthd.getSoluong());
							dsthanhtien.add(cthd.getDongia() * cthd.getSoluong());
						} else {
							boolean nf = true;
							for (int i = 0; i < dshanghoa.size(); i++) {
								if (dshanghoa.get(i).getMaHH().equals(hh.getMaHH())) {
									dssoluongmua.set(i, dssoluongmua.get(i) + cthd.getSoluong());
									dsthanhtien.set(i, dsthanhtien.get(i) + cthd.getDongia() * cthd.getSoluong());
									nf = false;
								}
							}
							if (nf) {
								dshanghoa.add(hh);
								dssoluongmua.add(cthd.getSoluong());
								dsthanhtien.add(cthd.getDongia() * cthd.getSoluong());
							}
						}
					}
				}
			}
			ArrayList<Object> dulieu = new ArrayList<>();
			dulieu.add(dshanghoa);
			dulieu.add(dssoluongmua);
			dulieu.add(dsthanhtien);
			dulieu.add(tonggiatri);

			return dulieu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

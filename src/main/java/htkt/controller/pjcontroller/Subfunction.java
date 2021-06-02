package htkt.controller.pjcontroller;

public class Subfunction {
	public static boolean namgiua(String ngay1, String ngay2, String ngay3) {
		String arr1[]= ngay1.split("-");
		String arr2[]= ngay2.split("-");
		String arr3[]= ngay3.split("-");
		long d1= Integer.parseInt(arr1[0]+arr1[1]+arr1[2]);
		long d2= Integer.parseInt(arr2[0]+arr2[1]+arr2[2]);
		long d3= Integer.parseInt(arr3[0]+arr3[1]+arr3[2]);
		if((d2-d1)>=0 &&(d3-d2)>=0) return true;
		return false;
	}
	public static long ngaysangso(String ngay1) {
		String arr1[]= ngay1.split("-");
		long d1= Integer.parseInt(arr1[2]+arr1[1]+arr1[0]);
		return d1;
	}
	public static boolean ngaycungthang(String ngay,String thangnam) {
		String arr1[]= ngay.split("-");
		String arr2[]= thangnam.split("-");
		long d1= Integer.parseInt(arr1[0]+arr1[1]);
		long d2= Integer.parseInt(arr2[0]+arr2[1]);
		return (d1-d2)==0; 
	}
	public static boolean thangcungthang(String thang,String thangnam) {
		String arr1[]= thang.split("-");
		String arr2[]= thangnam.split("-");
		long d1= Integer.parseInt(arr1[0]+arr1[1]);
		long d2= Integer.parseInt(arr2[0]+arr2[1]);
		return (d1-d2)==0; 
	}
	
	
	public static boolean thangtruoc(String ngay,String thangnam) {
		String arr1[]= ngay.split("-");
		String arr2[]= thangnam.split("-");
		long d1= Integer.parseInt(arr1[0]+arr1[1]);
		
		long d2= Integer.parseInt(arr2[0]+arr2[1]);
		if((d2-1)%100==0) d2=d2-100+11;
		else d2=d2-1;
		return (d1-d2)==0; 
	}
	public static String danhgia(long a, long b) {
		if(a*b==0) {
			if(a >b) return "Tăng 100%";
			else if(a<b) return "Giảm 100%";
			else return "Không đổi";
		}
		else {
			long val= a-b;
			if(val>0) return "Tăng "+ String.format("%.2f", val*100.0/ b) +"%";
			else if(val<0) return "Giảm "+ String.format("%.2f", Math.abs(val*100.0/ b)) +"%";
			else return "Không đổi";
			
		}
	}
}
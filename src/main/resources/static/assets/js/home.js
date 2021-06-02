function loadTinhLuongDiv(){
		/*var new_url = "/tinhluong";
	    window.history.pushState('data', 't', new_url);*/
		$('#main').empty();
		$('#main').load("/bangluong/tinhluonghtml",function(data, statusTxt, jqXHR){
            if(statusTxt == "success"){
            	
            //	function loadPhongBan(){
            		$.ajax({
    					type : "GET",
    					dataType : 'json',
    					url : "phongban/getAll",
    					success : function(result) {
    						$("#selectBox1 #selectPhongBan").empty();
    						if (result != null) {
    							$.each(result,
    									function(i, list) {
    								var newOption =  "<option  value='" + list.maPhongban + "'>" + list.mota + "</option>";
        							$('#selectBox1 #selectPhongBan').append(newOption);
    							});
    							console.log("Success: ", result);
    						}
    						 else {
     							var result = "<h3 style='color:red'> No person found </h3>";
     							$("#feedback").html(result);	
            				}		
    					},
    					error : function(e) {
    						console.log("ERROR: ", e);
    					}
    				});
            //	}
            }
            if(statusTxt == "error"){
                alert("Error: " + jqXHR.status + " " + jqXHR.statusText);
            }
        });
		
	}
function xemHoaDon() {	
			var ngaydau = $('#ngaydau').val();
			var ngaycuoi = $('#ngaycuoi').val();
			
			
		$.ajax({
				type : "POST",
				url : "hoadon/tatcahoadonmot",
				data : {
					ngaydau:  $('#ngaydau').val() , 
					ngaycuoi: $('#ngaycuoi').val() ,
				},
				success : function(result) {
				
					if (result != null) {
						$('#listTable tbody').empty();
						$.each(result,
								function(i, list) {
						    var newRow = '<tr>' + '<td>' + list.maHoadon + '</td>' +  '<td>' + list.khachhang.tenKH + '</td>' +  '<td>' 
						      + list.loaiHoadon + '</td>'
						     + '<td>' + list.ngayLap + '</td>'
						     + '<td>' + list.daThanhtoan + '</td>'
						     + '<td>' + list.tongTien + '</td>'
						  	 +  "<td><a  class='edit' type='submit'  onclick='editNV("+ list.maHoadon + ")'>"+"<i  class='material-icons' title='Edit'>"+"&#xE254;</i></a>" + "<a class='delete' ><i class='material-icons' title='Delete'  onclick='deleteNV("+ list.maHoadon + ")'>&#xE872;</i></a></td>"+ '</tr>';
						      $('#listTable tbody').append(newRow);
								});
						var tongRow =  '<tr>'+ '<td>' + '</td>' + '<td>' + '</td>'+ '<td>' + '</td>'+'<td>'  +'</td>'+'<td style="color:red">' +'Tổng chi phí: ' + '</td>' +   '<td td style="color:red">'  + '90000' + '</td>'+'</tr>' 
						
						$('#listTable tbody').append(tongRow);
						console.log("Success: ", result);
					} else {
						var result = "<h3 style='color:red'> No person found </h3>";
						$("#feedback").html(result);
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
	
function loadDiv(){
			$('#main').empty();
			$('#main').load("/hoadon/hoadonhtml",function(data, statusTxt, jqXHR){
	            if(statusTxt == "success"){
	            	
	            }
	            if(statusTxt == "error"){
	                alert("Error: " + jqXHR.status + " " + jqXHR.statusText);
	            }
	        });
			
		}
function addNV(){
			var id = $("#id").val();
			var name = $("#name").val();
			var formData= {
				id : id ,
				name : name
			}
			$.ajax({
				type : 'POST',
				contentType : "application/json",
				url :  "accountant/add",
				data : JSON.stringify(formData),
				dataType : 'json',
				success : function(data) {
					Ajax();
					console.log("SUCCESS: ", data);
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
function searchNV() {
				var formData= {
						name : $('#search').val()
					}
			
			$.ajax({
					type : "GET",
					data : formData,
					dataType : 'json',
					contentType : "application/json",
					url : "accountant/search",
					success : function(result) {
						if (result != null) {
							$('#listTable tbody').empty();
							$.each(result,
									function(i, list) {
								
								/*var user = "<td>"+list.id+"</td>" +"<td>"+list.name+"</td>" ; 		
								$('#All .list').append(user);*/
							//	 $('#div_table tbody').remove();
							      var newRow = "<tr id ='" + list.id + "'>" + '<td>' + list.id + '</td>' +  '<td id = "namerow" >' + list.name + '</td>' + '</tr>';
							      $('#listTable tbody').append(newRow);
									});
							console.log("Success: ", result);
						} else {
							var result = "<h3 style='color:red'> No person found </h3>";
							$("#feedback").html(result);
						}
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});
			}
		
function deleteNV(id){
				if(confirm("Are you sure you want to delete?")){
				$.ajax({
					type : 'DELETE',
					contentType : "application/json",
					url :  "accountant/delete/"+id,
					success : function(data) {
						Ajax();
						console.log("SUCCESS: ", data);
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});
				}
				else return false ;
			}
			function editNV(id){
				$.ajax({
					type : 'GET',
					contentType : "application/json",
					url :  "accountant/edit/"+id,
					success : function(data) {
						$('#editAccountantModal #id').val(data.id);
						$('#editAccountantModal #name').val(data.name);
						$('#editAccountantModal').modal('show');
					;
						console.log("SUCCESS: ", data);
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});
			}
			function updateNV(id){
				var id = $('#editAccountantModal #id').val();
				var name = $('#editAccountantModal #name').val();
				var formData= {
						id : id ,
						name : name
					}
				$.ajax({
					type : 'PUT',
					contentType : "application/json",
					url :  "accountant/update/"+id,
					data : JSON.stringify(formData),
					dataType : 'json',
					success : function(data) {
						Ajax();
					console.log("SUCCESS: ", data);
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});
			}
 		 function loadDoiMatKhauDiv(){
			$('#main').empty();
			$('#main').load("/nhanvien/doimatkhau",function(data, statusTxt, jqXHR){
	            if(statusTxt == "success"){
	            	
	            }
	            if(statusTxt == "error"){
	                alert("Error: " + jqXHR.status + " " + jqXHR.statusText);
	            }
	        });
			
		}
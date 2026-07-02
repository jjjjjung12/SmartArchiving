
	function fn_timePicker(obj) {
		var id = $(obj).attr("id");
		$("#" + id).timepicker({
			//timeFormat : "HHmmss000",
			timeFormat : "HHmm",
			interval : 10,
			dynamic : false,
			dropdown : true,
			scrollbar : true
		});
		$("#" + id).timepicker("open");
		
	}

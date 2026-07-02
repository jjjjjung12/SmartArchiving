$(function() {
	$(document).on('click', '.tgf_btn', function() {
		$(this).toggleClass('on');
		$(this).siblings('.tgf_cont').fadeToggle('fast');
	});

	$(document).on('click', '.tgs_btn', function() {
		$(this).parent().toggleClass('on');
		$(this).parent().siblings('.tgs_cont').slideToggle('fast');
	});

	$(document).on('click', '.btn.toggle, .btn_basic.toggle', function() {
		$(this).toggleClass('select');
	});
});

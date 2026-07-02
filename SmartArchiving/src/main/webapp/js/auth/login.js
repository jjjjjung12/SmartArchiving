var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

(function($) {
	'use strict';

	var EXPIRE_WARN_DISMISS_PREFIX = 'smartArchiving.authExpireWarn.dismiss.';

	function getTodayYmd() {
		var d = new Date();
		return '' + d.getFullYear() + ('0' + (d.getMonth() + 1)).slice(-2) + ('0' + d.getDate()).slice(-2);
	}

	function isExpireWarnDismissedToday(userCd) {
		return userCd && window.localStorage && localStorage.getItem(EXPIRE_WARN_DISMISS_PREFIX + userCd) === getTodayYmd();
	}

	function setExpireWarnDismissedToday(userCd) {
		if (userCd && window.localStorage) {
			localStorage.setItem(EXPIRE_WARN_DISMISS_PREFIX + userCd, getTodayYmd());
		}
	}

	function resolveLoginUserCd(response) {
		try {
			if (response && response.data && response.data.length > 0) {
				return response.data[0].usercd || response.data[0].userCd || '';
			}
		} catch (e) { /* ignore */ }
		return $('#username').val() || (typeof sessionSsoId !== 'undefined' ? sessionSsoId : '') || '';
	}

	function showExpireWarnPopup(expireDate, remainDays, userCd, onDone) {
		var expirePretty = expireDate && expireDate.length === 8
			? expireDate.substr(0, 4) + '-' + expireDate.substr(4, 2) + '-' + expireDate.substr(6, 2)
			: (expireDate || '');
		var remainText = remainDays === 0 ? '오늘' : remainDays + '일';
		var body = '<div style="padding:14px 16px;line-height:1.6;">'
			+ '<div style="font-size:15px;font-weight:700;margin-bottom:8px;">권한 만료 예정 안내</div>'
			+ '<p>사용 권한 만료일: <strong>' + expirePretty + '</strong></p>'
			+ '<p>남은 기간: <strong>' + remainText + '</strong></p>'
			+ '<p style="margin-top:12px;"><label><input type="checkbox" id="chk-expire-warn-dismiss-today"/> 오늘 하루 보지 않기</label></p>'
			+ '</div>';
		if (typeof w2popup !== 'undefined') {
			w2popup.open({
				title: '권한 만료 안내',
				body: body,
				width: 420,
				height: 260,
				buttons: '<button class="w2ui-btn" onclick="w2popup.close()">확인</button>',
				onClose: function() {
					if ($('#chk-expire-warn-dismiss-today').is(':checked')) {
						setExpireWarnDismissedToday(userCd);
					}
					if (typeof onDone === 'function') onDone();
				}
			});
		} else {
			alert('권한 만료 예정: ' + expirePretty + ' (남은 ' + remainText + ')');
			if (typeof onDone === 'function') onDone();
		}
	}

	function handleLoginSuccess(response, redirectUrl) {
		try {
			if (response && (response.expireWarn === true || response.expireWarn === 'true')) {
				var userCd = resolveLoginUserCd(response);
				if (isExpireWarnDismissedToday(userCd)) {
					window.location.href = redirectUrl;
					return;
				}
				showExpireWarnPopup(response.expireDate || '', parseInt(response.remainDays, 10) || 0, userCd, function() {
					window.location.href = redirectUrl;
				});
				return;
			}
		} catch (e) { /* ignore */ }
		window.location.href = redirectUrl;
	}

	function showLoginError(msg) {
		var $err = $('.form-login-error');
		if (msg) $err.find('p').text(msg);
		$err.slideDown(200);
		$('#password').val('').focus();
	}

	function hideLoginError() {
		$('.form-login-error').slideUp(150);
	}

	function setLoading(on) {
		if (on) $('#loginLoading').addClass('on');
		else $('#loginLoading').removeClass('on');
	}

	function handleLoginResult(result, response) {
		var redirectUrl = archiveCtx + '/index';
		if (response && response.redirect_url && response.redirect_url.length) {
			redirectUrl = response.redirect_url;
		}
		switch (result) {
			case 'success':
				handleLoginSuccess(response, redirectUrl);
				break;
			case 'invalid':
			case 'NotFound':
				setLoading(false);
				showLoginError('아이디 또는 비밀번호를 확인하세요.');
				break;
			case 'failed':
				setLoading(false);
				alert('해당 SSO_ID는 인사원장에 존재하지 않습니다.');
				break;
			case 'NotInsaMaster':
				setLoading(false);
				alert('인사정보가 존재하지 않습니다.');
				break;
			case 'authFailed':
				setLoading(false);
				alert('권한 신청이 필요합니다.');
				window.location.href = archiveCtx + '/userAuthApplyLogin';
				break;
			case 'retired':
				setLoading(false);
				alert('재직 상태가 아닙니다. 사용이 불가능 합니다.');
				break;
			case 'brcFailed':
				setLoading(false);
				alert('등록된 사무소가 다릅니다.');
				window.location.href = archiveCtx + '/userAuthApplyLogin';
				break;
			case 'dayFailed':
				setLoading(false);
				alert('장기 미사용자 입니다. 다시 권한신청을 해주세요.');
				window.location.href = archiveCtx + '/userAuthApplyLogin';
				break;
			case 'useFailed':
				setLoading(false);
				alert('사용자가 아님.');
				break;
			case 'exportFailed':
				setLoading(false);
				alert('사용기한 만료일이 지났습니다. 다시 권한신청을 해주세요.');
				window.location.href = archiveCtx + '/userAuthApplyLogin';
				break;
			case 'ipFailed':
				setLoading(false);
				alert('등록된 IP가 다릅니다.');
				window.location.href = archiveCtx + '/userAuthApplyLogin';
				break;
			default:
				setLoading(false);
				showLoginError('로그인에 실패했습니다.');
		}
	}

	function doLogin(username, password) {
		setLoading(true);
		hideLoginError();
		$.ajax({
			url: archiveCtx + '/GetLogin',
			dataType: 'json',
			data: { username: username, password: password || '' },
			error: function() {
				setLoading(false);
				alert('로그인 처리 중 오류가 발생했습니다.');
			},
			success: function(response) {
				handleLoginResult(response.result, response);
			}
		});
	}

	$(function() {
		$('#form_login').on('submit', function(e) {
			e.preventDefault();
			var u = $('#username').val();
			var p = $('#password').val();
			if (!u) { showLoginError('아이디를 입력하세요.'); return; }
			if (!p) { showLoginError('비밀번호를 입력하세요.'); return; }
			doLogin(u, p);
		});

		$('#sso_login').on('click', function() {
			window.location.href = archiveCtx + '/sso/index.jsp';
		});

		if (typeof sessionSsoId !== 'undefined' && sessionSsoId && sessionSsoId !== 'null') {
			doLogin(sessionSsoId, '');
		}
	});
})(jQuery);

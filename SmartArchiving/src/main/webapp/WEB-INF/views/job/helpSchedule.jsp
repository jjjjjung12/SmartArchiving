<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "스케쥴(cron) 도움말"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">스케쥴(cron) 도움말</h2>
			<button type="button" class="close" title="창닫기" onclick="window.close(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="pop_body__scroll">
				<div class="tit mb16"><h3>cron 표현식</h3></div>
				<p class="mb16">문자열의 좌측부터 우측까지 아래 순서대로 의미가 있으며, 각 항목은 공백(space)으로 구분합니다.</p>
				<div class="tbl_box mb32">
					<table class="tbl02">
						<thead><tr><th style="width:80px">순서</th><th style="width:160px">필드명</th><th>사용 가능한 값</th></tr></thead>
						<tbody>
							<tr><td>1</td><td>seconds</td><td>0~59 , - * /</td></tr>
							<tr><td>2</td><td>minutes</td><td>0~59 , - * /</td></tr>
							<tr><td>3</td><td>hours</td><td>0~23 , - * /</td></tr>
							<tr><td>4</td><td>day of month</td><td>1~31 , - * ? / L W</td></tr>
							<tr><td>5</td><td>month</td><td>1~12 or JAN-DEC , - * /</td></tr>
							<tr><td>6</td><td>day of week</td><td>1-7 or SUN-SAT , - * ? / L #</td></tr>
							<tr><td>7</td><td>years (optional)</td><td>1970~2099 , - * /</td></tr>
						</tbody>
					</table>
				</div>
				<div class="tit mb16"><h3>특수문자의 의미</h3></div>
				<div class="tbl_box mb32">
					<table class="tbl02">
						<thead><tr><th style="width:80px">기호</th><th style="width:200px">의미</th><th>사용 예</th></tr></thead>
						<tbody>
							<tr><td>*</td><td>모든 수를 의미</td><td>seconds에서 사용하면 매초, minutes에서 사용하면 매분, hours에서 사용하면 매시간</td></tr>
							<tr><td>?</td><td>해당 항목을 사용하지 않음</td><td>day of month에서 사용하면 월중 날짜를 지정하지 않음. day of week에서 사용하면 주중 요일을 지정하지 않음</td></tr>
							<tr><td>-</td><td>기간을 설정</td><td>hours에서 10-12이면 10시, 11시, 12시에 동작 / minutes에서 58-59이면 58분, 59분에 동작</td></tr>
							<tr><td>,</td><td>특정 시간을 지정</td><td>day of week에서 2,4,6이면 월,수,금에만 동작함</td></tr>
							<tr><td>/</td><td>시작시간과 반복 간격 설정</td><td>seconds에 0/15이면 0초에 시작해 15초 간격으로 동작 / minutes에 5/10이면 5분에 시작해 10분 간격으로 동작</td></tr>
							<tr><td>L</td><td>마지막 기간에 동작 (day of month, day of week에서만 사용)</td><td>day of month에서 사용하면 해당월 마지막 날에 수행 / day of week에서 사용하면 토요일에 수행</td></tr>
							<tr><td>W</td><td>가장 가까운 평일 동작 (day of month에만 사용)</td><td>15W로 설정 시 15일이 토요일이면 가장 가까운 14일 금요일에 실행, 일요일이면 16일에 실행, 평일이면 15일에 실행</td></tr>
							<tr><td>LW</td><td>L과 W의 조합</td><td>그달의 마지막 평일에 동작</td></tr>
							<tr><td>#</td><td>몇 번째 주와 요일 설정 (day of week에 사용)</td><td>6#3이면 3번째 주 금요일에 동작 / 4#2이면 2번째 주 수요일에 동작</td></tr>
						</tbody>
					</table>
				</div>
				<div class="tit mb16"><h3>사용 예</h3></div>
				<div class="tbl_box">
					<table class="tbl02">
						<thead><tr><th style="width:220px">표현식</th><th>의미</th></tr></thead>
						<tbody>
							<tr><td>0 0 12 * * *</td><td>매일 12시에 실행</td></tr>
							<tr><td>0 15 10 * * *</td><td>매일 10시 15분에 실행</td></tr>
							<tr><td>0 * 14 * * *</td><td>매일 14시에 0분~59분까지 매분 실행</td></tr>
							<tr><td>0 0/5 14 * * *</td><td>매일 14시에 시작해서 5분 간격으로 실행</td></tr>
							<tr><td>0 0/5 14,18 * * *</td><td>매일 14시, 18시에 시작해서 5분 간격으로 실행</td></tr>
							<tr><td>0 0-5 14 * * *</td><td>매일 14시에 0분, 1분, 2분, 3분, 4분, 5분에 실행</td></tr>
							<tr><td>0 0 20 ? * MON-FRI</td><td>월~금 20시 0분 0초에 실행</td></tr>
							<tr><td>0 0/5 14 * * ?</td><td>아무요일, 매월, 매일 14:00부터 14:05까지 매분 0초 실행 (6번 실행됨)</td></tr>
							<tr><td>0 15 10 ? * 6L</td><td>매월 마지막 금요일 10:15:00에 실행</td></tr>
							<tr><td>0 15 10 15 * ?</td><td>아무요일, 매월 15일 10:15:00에 실행</td></tr>
							<tr><td>*/1 * * * * *</td><td>매 1분마다 실행</td></tr>
							<tr><td>*/10 * * * * *</td><td>매 10분마다 실행</td></tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="tac mt20 popup_btn_div">
				<button type="button" class="btn grey mid" onclick="window.close();">닫기</button>
			</div>
		</div>
	</div>
</div>

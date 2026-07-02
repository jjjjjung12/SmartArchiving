var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function() {
    if (typeof getServer === 'function') getServer($('#C_SERVER_ID'), 1);
    $('#btnQuery').on('click', function() {
        // 대시보드 데이터로드 (API 연동 시 확장)
        $('#chartAgent').html('<p class="tac" style="padding:40px;color:#888;">조회 버튼을 눌러 데이터를 불러옵니다.</p>');
    });
});

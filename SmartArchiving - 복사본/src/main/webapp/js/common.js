//빈값 체크
function isNull(value) {
	if(value != null && value != '' && value != undefined && value != "undefined") {
		return false;
	} else {
		return true;
	}
}

// 테이블 정렬 함수
function sortTable(tableId, column) {
    const table = document.getElementById(tableId);
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    const th = table.querySelectorAll('th')[column];
    
    // 현재 정렬 상태 확인
    const currentSort = th.classList.contains('sort-asc') ? 'asc' : 
                       th.classList.contains('sort-desc') ? 'desc' : 'none';
    
    // 모든 헤더에서 정렬 클래스 제거
    table.querySelectorAll('th').forEach(header => {
        header.classList.remove('sort-asc', 'sort-desc');
    });
    
    // 새로운 정렬 방향 결정
    let sortDirection;
    if (currentSort === 'none' || currentSort === 'desc') {
        sortDirection = 'asc';
        th.classList.add('sort-asc');
    } else {
        sortDirection = 'desc';
        th.classList.add('sort-desc');
    }
    
    // 행 정렬
    rows.sort((a, b) => {
        const aValue = a.cells[column].textContent.trim();
        const bValue = b.cells[column].textContent.trim();
        
        // 숫자인지 확인
        const aNum = parseFloat(aValue.replace(/[^0-9.-]/g, ''));
        const bNum = parseFloat(bValue.replace(/[^0-9.-]/g, ''));
        
        let comparison;
        if (!isNaN(aNum) && !isNaN(bNum)) {
            comparison = aNum - bNum;
        } else {
            comparison = aValue.localeCompare(bValue, 'ko-KR');
        }
        
        return sortDirection === 'asc' ? comparison : -comparison;
    });
    
    // 정렬된 행을 다시 추가
    rows.forEach(row => tbody.appendChild(row));
}
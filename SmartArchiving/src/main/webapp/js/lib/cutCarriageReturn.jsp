<!-- ticket 값을 생성하기 위한 페이지 -->
<%!
	public String cutCarriageReturn(String oriString) {
		if(oriString == null) {
			oriString = "";
		}
		int index = oriString.indexOf("\n");
		while(index != -1) {
			String head = oriString.substring(0, index);
			if (index != oriString.length() -1) {
				String tail = oriString.substring(index);
				oriString = head + tail;
			} else {
				oriString = head;
			}
			index = oriString.indexOf("\n");
		}
		return oriString;
	}
%>

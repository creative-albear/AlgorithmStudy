import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 카카오 2019 블라인드 테스트
 * 데이터베이스의 후보키의 수를 추출하라
 */
public class CandidateKey {
	public static void main(String[] args) {
		String[][] relation = {
		 	{"100","ryan","music","2"},
		 	{"200","apeach","math","2"},
		 	{"300","tube","computer","3"},
		 	{"400","con","computer","4"},
		 	{"500","muzi","music","3"},
		 	{"600","apeach","music","2"}
		};
		int answer = new CandidateKey.Solution().solution(relation);
		System.out.println("answer : " + answer);
	}

	public static class Solution {
	    public int solution(String[][] relation) {

	    	int candidateCount = 0;
	    	Table table = new Table(relation);

	    	// 단일 컬럼 유일성 체크
	    	for(int i=0; i<table.getColumnCount(); i++) {
	    		if(isUniqueSet(table.getColumnData(i))) {
	    			table.offBitmap(i);	// off bitmap
	    			candidateCount++;	// increase candidate key count
	        	}
	    	}

	        return candidateCount;
	    }

	    public boolean isUniqueSet(String[] arr) {
	    	return Arrays.stream(arr).distinct().count() == arr.length;
	    }

	    private double baseLog(double x, double base) {
	    	return Math.log(x) / Math.log(base);
	    }

	    private static class Table {
	    	private final int columnCount;
	    	private final String[][] relation;
	    	private int bitmap;

	    	public Table(String[][] relation) {
	    		this.relation = relation;
	    		this.columnCount = relation[0].length;
	    		this.bitmap = (int)Math.pow(2, this.columnCount)-1;
	    	}

	    	public int getColumnCount() {
	    		return columnCount;
	    	}

	    	public void offBitmap(int n) {
//	    		bitmap ^= 1<<n;
	    		bitmap &= ~(1 << n-1);
	    	}

	    	public String[] getColumnData(int i) {
	    		return toDataArray(relation, 1<<i);
	    	}

		    private String[] toDataArray(String[][] relation, int bitmap) {
		    	List<String> datas = new ArrayList<>();
		    	String bitmapString = Integer.toBinaryString(bitmap);
		    	System.out.println("Input Bitmap["+bitmapString+"]");
		    	StringBuilder sb = new StringBuilder();

		    	for(int row=0; row<relation.length; row++) {
		    		for(int col=0; col<bitmapString.length(); col++) {
			    		if(bitmapString.charAt(bitmapString.length()-col-1) == '1') {
			    			sb.append(relation[row][col]).append("|");
			    		}
		    		}
		    		datas.add(sb.toString());
		    		sb.setLength(0);
		    	}

		    	return datas.toArray(new String[0]);
		    }

	    }

	}
}
import java.util.*;

public class KakaoChat {

	public static void main(String[] args) {
		String[] record = { "Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan" };
		String[] result = new Solution().solution(record);
        System.out.println(Arrays.stream(result).toList());
	}

	static class Solution {
		public String[] solution(String[] records) {
			Map<String, String> nickMap = new HashMap<>();
			History history = new History(records.length);

			for (String record : records) {
				String[] column = record.split(" ");
				String userId = column[1];

                switch (column[0]) {
                    case "Enter" -> {
                        history.add(0, userId);
                        nickMap.put(userId, column[2]);
                    }
                    case "Leave" -> history.add(1, userId);
                    case "Change" -> {
                        history.add(2, userId);
                        nickMap.put(userId, column[2]);
                    }
                }
			}

			return history.toArray(nickMap);
		}

		static class History {
			private final int[] actionHistory;
			private final String[] userIds;
			private int index;

			public History(int length) {
				this.actionHistory = new int[length];
				this.userIds = new String[length];
				this.index = 0;
			}

			public void add(int action, String userId) {
				actionHistory[index] = action;
				userIds[index] = userId;
				index++;
			}

			public String[] toArray(Map<String, String> nickMap) {
				List<String> result = new ArrayList<>();

				for (int i = 0; i < index; i++) {
					if (actionHistory[i] < 2) {
						result.add(nickMap.get(userIds[i]) + "님이 " + toActionString(actionHistory[i]) + ".");
					}
				}
				return result.toArray(new String[0]);
			}

			private String toActionString(int action) {
                return switch (action) {
                    case 0 -> "들어왔습니다";
                    case 1 -> "나갔습니다";
                    default -> "";
                };
			}
		}
	}

}

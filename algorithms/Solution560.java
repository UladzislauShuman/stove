
import java.util.HashMap;
import java.util.Map;

class Solution560 {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        int currentSum = 0;
        Map<Integer, Integer> dp = new HashMap<>();
        // key -- это сама сумма
        // value -- это количество подпоследовательностей, что удовлетворяет этому
        dp.put(0,1);
        for (int i = 0; i < n; ++i) {
            currentSum += nums[i]; // текущая префиксная сумма от 0 до i
            if (dp.containsKey(currentSum - k)) { // существует ли префиксная сумма от 0 до j, что равна current - k?
                ans += dp.get(currentSum - k); // если да, то [0][i] - [0][j] = current - (current - k) = k -- то что нам нужно
            } 
            if (dp.containsKey(currentSum)) { // заносим нашу current
                dp.put(currentSum, dp.get(currentSum) + 1);
            } else {
                dp.put(currentSum, 1);
            }
            /*
            я долго думал, что из-за последнего условия появятся "дыры", что уже значит,
            что нет префикса 
            но -- dp.get(currentSum) значит, что есть 2+ префикса ([0][j1], ...) которые равны currentSum

            и все легально будет, никаких дыр, ведь
            [0][i] - [0][j1] = [0][i] - [0][j2] = ...
            */
        }
        return ans;
    }
}
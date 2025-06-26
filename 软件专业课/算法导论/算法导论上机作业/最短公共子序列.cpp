#include<iostream>
#include<string>
#include<algorithm>
using namespace std;
class Solution {
public:
    int** dp;
    string shortestCommonSupersequence(string str1, string str2) {
        dp = new int* [str1.size() + 1];
        for (int i = 0; i <= str1.size(); i++)
        {
            dp[i] = new int[str2.size()+1];
            for (int j = 0; j <= str2.size(); j++)
            {
                dp[i][j] = 0;
            }
        }
        findShortestCommonSupersequence(str1, str2);
        string output;
        int i=str1.size(), j=str2.size();
        while (!(i == 1 && j == 1))
        {
            if (dp[i][j] == 3) {
                output += str1[i - 1]; i--; j--;
            }
            if (dp[i][j] == 2) i--;
            if (dp[i][j] == 1) j--;
        }
        if (str1[0] == str2[0]) output += str1[0];
        reverse(output.begin(), output.end());
        return output;
    }
    void findShortestCommonSupersequence(string str1, string str2)
    {
        int** dp1 = new int* [str1.size() + 1];
        for (int i = 0; i <= str1.size(); i++)
        {
            dp1[i] = new int[str2.size() + 1];
            for (int j = 0; j <= str2.size(); j++)
            {
                dp1[i][j] = 0;
            }
        }
        for (int i = 1; i <= str1.size(); i++)
        {
            for (int j = 1; j <= str2.size(); j++)
            {
                if (str1[i-1] == str2[j-1]) {
                    dp1[i][j] = dp1[i - 1][j - 1] + 1;
                    dp[i][j] = 3;
                }
                else
                {
                    if (dp1[i][j - 1] > dp1[i - 1][j])
                    {
                        dp1[i][j] = dp1[i][j - 1];
                        dp[i][j] = 1;
                    }
                    else
                    {
                        dp1[i][j] = dp1[i - 1][j];
                        dp[i][j] = 2;
                    }
                }
            }
        }
    }
    
};
int main()
{
    string str1, str2;
    cin >> str1 >> str2;
    Solution so;
    cout << so.shortestCommonSupersequence(str1, str2);
}

#include<iostream>
#include<string>
#include<algorithm>
using namespace std;
int main()
{
	string str1, str2;
	cin >> str1 >> str2;
	int size1 = str1.size();
	int size2 = str2.size();
	int** dp = new int*[size1 + 1];
	for (int i = 0; i <= size1; i++)
	{
		dp[i] = new int[size2 + 1];
		for (int j = 0; j <= size2; j++)
		{
			dp[i][j] = 0;
		}
	}
	for (int i = 1; i <= size1; i++)
	{
		for (int j = 1; j <= size2; j++)
		{
			if (str1[i - 1] == str2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
			else
				dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
		}
	}
	cout << dp[size1][size2];
}
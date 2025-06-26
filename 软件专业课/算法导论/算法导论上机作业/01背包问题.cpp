#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
class item
{
public:
	int value;
	int volumn;
	item(int v1, int v2) :volumn(v1), value(v2) {};
	item() {};
};
istream& operator>>(istream& is, item it)
{
	is >> it.volumn >> it.value;
	return is;

}
bool operator<(item& t1, item& t2)
{
	return t1.volumn < t2.volumn;
}
int main()
{
	int n,maxvolumn;
	cin >> n >> maxvolumn;
	item* it = new item[n];
	for (int i = 0; i < n; i++)
	{
		int v1, v2;
		cin >> v1>>v2;
		it[i].volumn = v1;
		it[i].value = v2;
	}
	sort(it, it + n);
	int** dp = new int* [n+1];
	for (int i = 0; i <= n; i++)
		dp[i] = new int[maxvolumn + 1];
	for (int i = 0; i <= maxvolumn; i++)
	{
		if (i < it[0].volumn) dp[1][i] = 0;
		else dp[1][i] = it[0].value;
	}
	for (int i = 2; i <= n; i++)
	{
		for (int j = 0; j <= maxvolumn; j++)
		{
			if (j - it[i - 1].volumn >= 0) {
				dp[i][j] = max(dp[i-1][j - it[i - 1].volumn] + it[i - 1].value, dp[i-1][j]);
			}
			else
			{
				dp[i][j] = dp[i - 1][j];
			}
		}
	}
	cout << dp[n][maxvolumn];
}
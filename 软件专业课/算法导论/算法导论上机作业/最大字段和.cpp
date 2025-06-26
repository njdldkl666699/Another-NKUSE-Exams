#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
int main()
{
	int n;
	cin >> n;
	vector<int> input(n);
	for (int i = 0; i < n; i++)
	{
		cin >> input[i];
	}
	int max = INT_MIN;
	int now = 0;
	for (int i = 0; i < n; i++)
	{
		now += input[i];
		if (max < now) max = now;
		if (now < 0) now = 0;
	}
	cout << max;
	return 0;
}
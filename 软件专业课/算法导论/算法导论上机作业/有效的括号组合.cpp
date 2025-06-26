#include<iostream>
using namespace std;
int find(int left,int right,int now)
{
	int sum = 0;
	if (left == 0 && right == 0) return 1;
	else if (now == 0&& left>0)
	{
		sum += find(left - 1, right, now + 1);
	}
	else if (now > 0)
	{
		if (left > 0)sum += find(left - 1, right, now + 1);
		if (right > 0) sum += find(left, right - 1, now - 1);
	}
	return sum;
}
int main()
{
	int n;
	cin >> n;
	cout << find(n,n,0);
}
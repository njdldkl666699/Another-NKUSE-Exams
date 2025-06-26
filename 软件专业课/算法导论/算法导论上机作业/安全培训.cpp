#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
class safeStaff
{
public :
	int start;
	int end;
};
bool operator<(safeStaff& s1, safeStaff& s2)
{
	return s1.start < s2.start;
}

int main()
{
	int n;
	cin >> n;
	vector<safeStaff> safe(n);
	for (int i = 0; i < n; i++)
	{
		cin >> safe[i].start >> safe[i].end;
	}
	sort(safe.begin(), safe.end());
	int lefttime = safe[0].start, endtime = safe[0].end,sum=1;
	for (int i = 1; i < n; i++)
	{
		if (safe[i].start >= lefttime && safe[i].start <= endtime)
		{
			lefttime = max(safe[i].start, lefttime);
			endtime = min(safe[i].end, endtime);
		}
		else
		{
			lefttime = safe[i].start;
			endtime = safe[i].end;
			sum++;
		}
	}
	cout << sum;
}
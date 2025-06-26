#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
class action
{
public:
	int start;
	int end;
	action(int s, int e) :start(s), end(e) {};
};
bool operator<(action& a1, action& a2)
{
	return a1.end < a2.end;
}
int main()
{
	int n;
	cin >> n;
	vector<action> act;
	int s, e;
	for (int i = 0; i < n; i++)
	{
		cin >> s >> e;
		act.push_back(action(s, e));
	}
	sort(act.begin(), act.end());
	e = act[0].end;
	int sum = 1;
	for (int i = 1; i < n; i++)
	{
		if (act[i].start >= e)
		{
			e = act[i].end;
			sum++;
		}
	}
	cout << sum;
}
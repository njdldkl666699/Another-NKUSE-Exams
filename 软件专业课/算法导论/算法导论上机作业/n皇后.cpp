#include<iostream>
#include<vector>
#include<cmath>
using namespace std;
int n;
class Queen
{
public:
	int x;
	int y;
	Queen(int c, int d) :x(c), y(d) {};
};
int sum = 0;
void layqueen(vector<Queen>& queen, int line)
{
	for (int i = 0; i < n; i++)
	{
		Queen temp(line,i);
		bool flag = 0;
		for (Queen qu : queen)
		{
			if (qu.y == temp.y)
			{
				flag = 1;
				break;
			}
			if (fabs(qu.y - temp.y) == fabs(qu.x - temp.x))
			{
				flag = 1;
				break;
			}
		}
		if (flag) continue;
		if (!flag && line!=n-1)
		{
			queen.push_back(temp);
			layqueen(queen, line + 1);
		}
		if (!flag && line == n-1)
		{
			sum++;
			return;
		}
		queen.pop_back();
	}
}
int main()
{
	cin >> n;
	vector<Queen> queen;
	layqueen(queen, 0);
	cout << sum;
}
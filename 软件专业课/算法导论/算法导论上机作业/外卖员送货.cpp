#include<iostream>
#include<climits>
#include<algorithm>
using namespace std;
int main()
{
	int n;
	cin >> n;
	int** p = new int* [n];
	for (int i = 0; i < n; i++)
	{
		p[i] = new int[n];
		for (int j = 0; j < n; j++)
		{
			if (i == j) p[i][j] = 0;
			else
			{
				p[i][j] = INT_MAX / 4;
			}
		}
	}
	int m;
	cin >> m;
	for (int i = 0; i < m; i++)
	{
		int x, y, dis;
		cin >> x >> y >> dis;
		p[x][y] = dis;
		//p[y][x] = dis;
	}
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			for (int k = 0; k < n; k++)
			{
				p[i][j] = min(p[i][j], p[i][k] + p[k][j]);
			}
		}
	}
	int sum = 0;
	for (int i = 0; i < n; i++)
	{
		cout << p[0][i] << " ";
		sum += p[0][i];
	}
	cout << sum;
}
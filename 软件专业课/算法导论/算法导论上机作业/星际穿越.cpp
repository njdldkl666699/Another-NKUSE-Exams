#include<iostream>
#include<vector>
#include<climits>
using namespace std;
int main()
{
	int n;
	cin >> n;
	vector<int> input(n);
	int sum = 0;
	for (int i = 0; i < n; i++)
	{
		cin >> input[i];
	}
	int max = 0;
	int nextmax = input[0];
	for (int i = 0; i < n; i++)
	{
		
		if (i + input[i] > nextmax) nextmax = i + input[i];
		if (i >= max) {
			sum++;
			max = nextmax;
		}
		if (max >= n) break;
		
	}
	cout << sum;
}
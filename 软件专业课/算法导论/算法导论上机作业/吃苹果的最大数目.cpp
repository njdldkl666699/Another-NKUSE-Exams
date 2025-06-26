#include<iostream>
#include<vector>
#include<sstream>
#include<queue>
#include<algorithm>
using namespace std;
class apple
{
public:
	int day;
	int count;
	apple(int d, int c) :day(d), count(c) {};
};
bool operator<(apple& a1, apple& a2)
{
	return a1.day > a2.day;
}
int main()
{
	string input;
	getline(cin, input);
	stringstream ss(input);
	vector<int> apples;
	int x;
	while (ss >> x)
	{
		apples.push_back(x);
	}
	int size = apples.size();
	vector<int> days(size);
	for (int i = 0; i < size; i++)
		cin >> days[i];
	int i = 0, sum = 0;
	vector<apple> greedy;
	while (!greedy.empty() || i < size)
	{
		if (i < size && days[i]>0) {
			greedy.push_back(apple(days[i], apples[i]));
			i++;
			sort(greedy.begin(), greedy.end());
		}
		else if (i<size&&days[i] == 0) i++;
		if (!greedy.empty())
		{
			if (greedy.back().count > 0) {
				greedy.back().count--;
				sum++;
			}
		}
		for (auto i = greedy.begin(); i < greedy.end(); i++)
		{
			(*i).day--;
			if ((*i).day == 0 || (*i).count == 0) { greedy.erase(i); i = greedy.begin(); }
			if (i == greedy.end()) break;
		}
	}
	cout << sum;
}
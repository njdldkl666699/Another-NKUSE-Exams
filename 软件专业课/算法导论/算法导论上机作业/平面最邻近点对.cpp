#include<iostream>
#include<algorithm>
#include<iomanip>
#include<vector>
#include<cmath>
using namespace std;
class Point
{
public:
	double x;
	double y;
};
bool operator<(const Point& p1, const Point& p2)
{
	return p1.x < p2.x;
}
istream& operator>>(istream& is, Point& po)
{
	is >> po.x >> po.y;
	return is;
}
bool operator!=(const Point& p1, const Point& p2)
{
	return !(p1.x == p2.x && p1.y == p2.y);
}
double calculateDistance(const Point& p1,const Point& p2)
{
	return sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2));
}
double calculateDistance(const vector<Point>& p1, const vector<Point>& p2)
{
	double distance=99999999;
	for (Point p : p1)
	{
		for (Point p3 : p2)
		{
			if(p!=p3)
			distance = min(distance,calculateDistance(p, p3));
		}
	}
	return distance;
}
double findMinDistance(vector<Point> input,double x)
{
	if (x == 0) return 999999999;
	if (input.size() <= 3)
	{
		if (input.size() == 1 || input.size()==0) return 999999999;
		if (input.size() == 2) return calculateDistance(input[0], input[1]);
		if (input.size() == 3) return min(min(calculateDistance(input[0], input[1]),
			calculateDistance(input[1], input[2])),
			calculateDistance(input[0], input[2])
		);
	}
	sort(input.begin(), input.end());
	int size = input.size();
	vector<Point> left;
	vector<Point> right;
	double middle = (input[size / 2 - 1].x + input[size / 2 + 1].x) / 2;
	
	for (int i = 0; i < size; i++)
	{
		if (input[i].x < middle) left.push_back(input[i]);
		else right.push_back(input[i]);
	}
	double distance1=999999999999999;
	double distance2=999999999999999;
	if (left.size() != 0) {
		distance1 = findMinDistance(left, middle);
		distance2 = findMinDistance(right, middle);
	}
	double distance = min(distance1, distance2);
	vector<Point> mileft;
	vector<Point> miright;
	for (int i = 0; i < size; i++)
	{
		if (input[i].x >= middle - distance/2 && input[i].x <= middle)
			mileft.push_back(input[i]);
		if (input[i].x <= middle + distance / 2 && input[i].x > middle)
			miright.push_back(input[i]);
	}
	distance = min(distance, calculateDistance(mileft, miright));
	distance = min(distance, calculateDistance(mileft, mileft));
	distance = min(distance, calculateDistance(miright, miright));
	return distance;
}
int main()
{
	int n;
	cin >> n;
	vector<Point> input;
	for (int i = 0; i < n; i++)
	{
		Point po;
		cin >> po;
		input.push_back(po);
	}
	sort(input.begin(), input.end());
	int size = input.size();
	double middle;
	if (size >= 3)
		middle = (input[size / 2 - 1].x + input[size / 2 + 1].x) / 2;
	else  middle = input[0].x;
	cout << std::fixed << std::setprecision(4) << findMinDistance(input, middle)/*calculateDistance(input, input)*/;
}
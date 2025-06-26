#include<iostream>
#include<vector>
#include<sstream>
#include<algorithm>
using namespace std;
int q_sort(vector<int>& nums, int start, int end, int target)
{
	int low = start;
	int high = end;
	bool iffind = 0;
	while (low < high)
	{
		while (nums[low] <= target && low+1<=end)
		{
			if (nums[low] == target && !iffind) {
				swap(nums[start], nums[low]);
				iffind = 1;
			}
			low++;
		}
		while (nums[high] > target && high-1>=start) high--;
		if (low < high) swap(nums[low], nums[high]);
	}
	swap(nums[high], nums[start]);
	return high;
}
int findKMin(vector<int>& nums, int start, int end, int k)
{
	if (start == end) return nums[start];
	if (nums.size() == 1) return nums[0];
	if (k==-1)
	{
		sort(nums.begin() + start, nums.begin() + end+1);
		return nums[(start + end) / 2];
	}
	vector<int> middle;
	for (int i = start; i < end; i += 5)
	{
		middle.push_back(findKMin(nums, i, min(i + 5, end), -1));
	}
	int target = findKMin(middle, 0, middle.size() - 1, (middle.size() - 1) / 2);
	int findway = q_sort(nums, start, end, target);
	if (findway == k) return nums[findway];
	if (findway < k) return findKMin(nums, findway + 1, end, k);
	if (findway > k) return findKMin(nums, start, findway - 1, k);
}

int main()
{
	vector<int> ve;
	string input;
	getline(cin, input);
	stringstream ss(input);
	int x;
	while (ss >> x)
	{
		ve.push_back(x);
	}
	int k;
	cin >> k;
	cout << findKMin(ve, 0, ve.size() - 1, k - 1);
}



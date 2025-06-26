#include <iostream>
#include<vector>
#include<algorithm>
#include<Windows.h>
#include<fstream>
#include<sstream>
#include<random>
using namespace std;
class bst
{
public:
	int key;
	bst* left;
	bst* right;
	bst* parent;
	bst(int o) { key = o; left = right = parent = 0; }
	friend void display(bst* i)
	{
		if (!i)return;
		if (i->parent)
		{
			cout << i->key << "的父亲是" << i->parent->key;
			if (i->left)
				cout << "，左儿子是" << i->left->key;
			else
				cout << "，没有左儿子";
			if (i->right)
				cout << "，右儿子是" << i->right->key;
			else
				cout << "，没有右儿子";
		}
		if (!i->parent)
		{
			cout << i->key << "没有父亲";
			if (i->left)
				cout << "，左儿子是" << i->left->key;
			else
				cout << "，没有左儿子";
			if (i->right)
				cout << "，右儿子是" << i->right->key;
			else
				cout << "，没有右儿子";
		}
		cout << endl;
		display(i->left);
		display(i->right);
	}
};
class tree
{
public:
	bst* root;
	tree() { root = 0; }
	bst* search(int a, bst*& father)
	{
		if (!root)
		{
			father = nullptr;
			return nullptr;
		}
		bst* u = root;
		while (u)
		{
			if (u->key == a)
				return u;
			else
			{
				father = u;
				if (u->key > a)
					u = u->left;
				else
					u = u->right;
			}
		}
		return nullptr;
	}
	void insert(int a)
	{
		if (!root)
		{
			bst* cur = new bst(a);
			root = cur; return;
		}
		bst* father = root;
		bst* cur = search(a, father);
		if (cur)return;
		bst* curr = new bst(a);
		if (father->key < a)
		{
			father->right = curr;
			curr->parent = father;
			return;
		}
		else
		{
			father->left = curr; curr->parent = father;
			return;
		}

	}
	void del(int a)
	{
		/*if (root)
		{
			cout << endl; display(root);
		}*/
	re:bst* father = root;
		bst* cur = search(a, father);
		if (!cur)return;
		if (!cur->left && !cur->right)
		{
			if (cur != root)
			{
				if (father->left == cur)
				{
					father->left = 0;
				}
				else
				{
					father->right = 0;
				}
			}
			delete[] cur;
			return;
		}
		if (!cur->left && cur->right)
		{
			if (cur != root)
			{
				if (father->left == cur)
				{
					father->left = cur->right;
				}
				else
				{
					father->right = cur->right;
				}
			}
			else
				root = cur->right;
			delete[] cur;
			return;
		}
		if (cur->left && !cur->right)
		{
			if (cur != root)
			{
				if (father->left == cur)
				{
					father->left = cur->left;
				}
				else
				{
					father->right = cur->left;
				}
			}
			else
				root = cur->left;
			delete[] cur;
			return;
		}
		else
		{
			bst* min = cur->right;
			while (min->left)
			{
				min = min->left;
			}
			std::swap(min->key, cur->key);
			cur = min;
			goto re;
		}
	}
	bst* search(int a)
	{
		if (!root)
		{
			return nullptr;
		}
		bst* u = root;
		while (u)
		{
			if (u->key == a)
				return u;
			else
			{
				if (u->key > a)
					u = u->left;
				else
					u = u->right;
			}
		}
		return nullptr;
	}
};

int main()
{
	LARGE_INTEGER nFreq;
	LARGE_INTEGER nBeginTime;
	LARGE_INTEGER nEndTime;
	vector<int> in;
	std::mt19937 generator(0);
	fstream i("test.txt", ios::in);
	fstream o("BST.txt", ios::app);
	string inp;
	double t;
	while (getline(i, inp)) {
		stringstream ss(inp);
		int x;
		while (ss >> x)
		{
			in.push_back(x);
		}
	};
	//sort(in.begin(), in.end());
	tree tr;
	int size = in.size();
	std::uniform_int_distribution<int> distribution(0, size - 1);
	vector<int> find;
	for (int i = 0; i < 1000; i++)
	{
		find.push_back(distribution(generator));
	}
	//sort(number.begin(), number.end());
	o << size << " insert:" << endl;
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	for (int i = 0; i < size; i++)
	{
		tr.insert(in[i]);
	}
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	//计算程序执行时间单位为秒数  
	o << "gettimeofday  " << t * 1000 << "ms" << endl;
	o << "search:" << endl;
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	for (int i = 0; i < 1000; i++)
	{
		tr.search(in[find[i]]);
	}
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	o << "gettimeofday  " << t * 1000 << "ms" << endl;
	o << "delete:" << endl;
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	for (int i = 0; i < size; i++)
	{
		tr.del(in[i]);
	}
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	//计算程序执行时间单位为秒数  
	o << "gettimeofday  " << t * 1000 << "ms" << endl;
	return 0;
}

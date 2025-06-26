#include<iostream>
#include<cmath>
#include<queue>
#include<vector>
#include<exception>
#include<fstream>
#include<Windows.h>
#include<sstream>
#include<random>
using namespace std;
int cnt = 0;
class AVL;
static AVL* ro = nullptr;
class AVL
{
public:
	AVL* left;
	AVL* right;
	int root;
	int height;
	int pingheng;
	AVL* pare;
	AVL(int x)
	{
		left = nullptr;
		right = nullptr;
		root = x;
		height = 0;
		pingheng = 0;
		pare = nullptr;
	}
	AVL(int x, AVL* p)
	{
		left = nullptr;
		right = nullptr;
		root = x;
		height = 0;
		pingheng = 0;
		pare = p;
	}
	friend void RRotation(AVL* root)
	{
		AVL* temp = root->right;
		AVL* r = root->pare;
		root->right = temp->left;
		if (root->right != nullptr) root->right->pare = root;
		temp->left = root;
		temp->pare = r;
		root->pare = temp;
		if (r != nullptr)
		{
			if (r->left == root) r->left = temp;
			else if (r->right == root) r->right = temp;
		}
		else ro = temp;
	}
	AVL* search(int key)
	{
		AVL* p = this;
		while (p->root != key)
		{
			if (key < p->root)
			{
				if (p->left == nullptr) return nullptr;
				else {
					p = p->left; continue;
				}
			}
			else
			{
				if (p->right == nullptr) return nullptr;
				else {
					p = p->right; continue;
				}
			}
		}
		return p;
	}
	friend void LLotation(AVL* root)
	{
		AVL* temp = root->left;
		root->left = temp->right;
		if (root->left != nullptr) root->left->pare = root;
		temp->right = root;
		AVL* r = root->pare;
		temp->pare = r;
		root->pare = temp;
		if (r != nullptr)
		{
			if (r->left == root) r->left = temp;
			else if (r->right == root) r->right = temp;
		}
		else ro = temp;
	}
	friend int getheight(AVL* p)
	{
		if (p == nullptr) return 0;
		int leftheight = 0, rightheight = 0;
		if (p->left == nullptr) leftheight = 0;
		else if (p->left->height != 0) leftheight = p->left->height;
		else leftheight = getheight(p->left);
		if (p->right == nullptr) rightheight = 0;
		else if (p->right->height != 0) rightheight = p->right->height;
		else rightheight = getheight(p->right);
		p->height = max(leftheight, rightheight) + 1;
		p->pingheng = leftheight - rightheight;
		return p->height;
	}
	friend void rejust(AVL* p)
	{
		if (p->left != nullptr) if (p->left->height == 0) rejust(p->left);
		if (p->right != nullptr) if (p->right->height == 0) rejust(p->right);
		int leftheight = getheight(p->left);
		int rightheight = getheight(p->right);

		p->height = max(leftheight, rightheight) + 1;
		p->pingheng = leftheight - rightheight;
		if (p->pingheng == 2)
		{
			if (p->left->pingheng == 1)
			{
				LLotation(p);
			}
			else if (p->left->pingheng == -1)
			{
				RRotation(p->left);
				LLotation(p);
			}
			cnt = 1;
		}
		else if (p->pingheng == -2)
		{
			if (p->right->pingheng == -1)
			{
				RRotation(p);
			}
			else if (p->right->pingheng == 1)
			{
				LLotation(p->right);
				RRotation(p);

			}
			cnt = 1;
		}


	}
	friend void push(int x, AVL* p)
	{
		if (x < p->root)
		{
			if (p->left == nullptr)
			{
				p->left = new AVL(x, p);
			}
			else { p->left->height = 0; push(x, p->left); }
		}
		else if (x > p->root)
		{
			if (p->right == nullptr)
			{
				p->right = new AVL(x, p);
			}
			else { p->right->height = 0; push(x, p->right); }
		}
		else {
			return;
		}
		if (cnt == 0) {
			rejust(p);
		}
	}

	friend void del(AVL* input, int key)
	{
		AVL* p = input;
		while (p->root != key)
		{
			p->height = 0;
			if (key < p->root)
			{
				if (p->left == nullptr) return;
				else {
					p = p->left; continue;
				}
			}
			else
			{
				if (p->right == nullptr) return;
				else {
					p = p->right; continue;
				}
			}
		}
		AVL* index = p;
		AVL* dele;
		p->height = 0;
		if (index->right != nullptr) {
			dele = index->right;
			if (dele->left != nullptr)
			{
				while (dele->left != nullptr)
				{
					dele->height = 0;
					dele = dele->left;
				}
			}
			index->root = dele->root;
		}
		else
		{
			if (index->left == nullptr) dele = index;
			else
			{
				dele = index->left;
				if (dele->right != nullptr)
				{
					while (dele->right != nullptr)
					{
						dele->height = 0;
						dele = dele->right;
					}
				}
				index->root = dele->root;
			}
		}
		if (dele == ro) throw exception();
		AVL* par = dele->pare;
		if (par->left == dele) par->left = nullptr;
		else par->right = nullptr;
		//delete dele;
		rejust(input);
	}

	~AVL()
	{
		if (left != nullptr) delete left;
		if (right != nullptr) delete right;
	}
};

bool ifbinary(AVL* root)
{
	if (root == nullptr) return false;

	std::queue<AVL*> queue;
	queue.push(root);

	bool foundNull = false;
	while (!queue.empty()) {
		AVL* cur = queue.front();
		queue.pop();

		if (cur == nullptr) {
			foundNull = true;
		}
		else {
			if (foundNull) {
				// 一旦遇到空节点后的非空节点，说明不是完全二叉树
				return false;
			}
			queue.push(cur->left);
			queue.push(cur->right);
		}
	}

	// 无需再额外检查队列，因为在遇到空节点时就已经将`foundNull`设置为true，
	// 并且后续遇到非空节点会立即返回false。如果循环正常结束，说明是完全二叉树。
	return true;
}

ostream& operator<<(ostream& os, AVL& a)
{
	vector<int>output;
	queue<AVL*> find;
	find.push(&a);
	while (!find.empty())
	{
		AVL* temp = find.front();
		find.pop();
		if (temp->left != nullptr)find.push(temp->left);
		if (temp->right != nullptr)find.push(temp->right);
		output.push_back(temp->root);
	}
	for (int i = 0; i < output.size(); i++)
	{
		os << output[i];
		if (i != output.size() - 1)os << " ";
	}
	os << endl;
	if (ifbinary(ro)) os << "Yes";
	else os << "No";
	return os;
}

int main()
{
	LARGE_INTEGER nFreq;
	LARGE_INTEGER nBeginTime;
	LARGE_INTEGER nEndTime;
	vector<int> in;
	std::mt19937 generator(0);

	fstream i("test.txt", ios::in);
	fstream o("AVL.txt", ios::app);
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
	AVL input(in[0]);
	int size = in.size();
	std::uniform_int_distribution<int> distribution(0,size-1);
	vector<int> find;
	for (int i = 0; i < 1000; i++)
	{
		find.push_back(distribution(generator));
	}
	ro = &input;
	o << size << " insert:" << endl;
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	for (int i = 1; i < size; i++)
	{
		push(in[i], ro);
		cnt = 0;
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
		ro->search(in[find[i]]);
	}
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	o << "gettimeofday  " << t * 1000 << "ms" << endl;
	o << "delete:" << endl;
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	try {
		for (int i = 0; i < size; i++)
		{
			del(ro, in[i]);
			//cout << *ro << endl;
		}
		cout << *ro;
	}
	catch (exception& e) {
		cout << "Null";
	}
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	o << "gettimeofday  " << t * 1000 << "ms" << endl;
	return 0;
}

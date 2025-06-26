#include<iostream>
#include<vector>
#include<exception>
#include<fstream>
#include<Windows.h>
#include<sstream>
#include<random>
using namespace std;
class RBT;
static RBT* ro = nullptr;
class RBT
{
public:
	RBT* left;
	RBT* right;
	int root;
	RBT* pare;
	bool color;
	RBT(int x)
	{
		root = x;
		left = nullptr;
		right = nullptr;
		pare = nullptr;
		color = 1;
	}
	RBT(int x, RBT* p)
	{
		root = x;
		left = nullptr;
		right = nullptr;
		pare = p;
		color = 0;
	}
	friend void RRotation(RBT* root)
	{
		RBT* temp = root->right;
		RBT* r = root->pare;
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
	friend void LLotation(RBT* root)
	{
		RBT* temp = root->left;
		root->left = temp->right;
		if (root->left != nullptr) root->left->pare = root;
		temp->right = root;
		RBT* r = root->pare;
		temp->pare = r;
		root->pare = temp;
		if (r != nullptr)
		{
			if (r->left == root) r->left = temp;
			else if (r->right == root) r->right = temp;
		}
		else ro = temp;
	}
	RBT* search(int key)
	{
		RBT* p = this;
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
	friend void push(int x, RBT* p)
	{
		RBT* cur = p;
		RBT* pat = nullptr;
		RBT* grand = nullptr;
		while (1)
		{
			if (cur->root > x)
			{
				if (cur->left == nullptr)
				{
					grand = cur->pare;
					pat = cur;
					cur->left = new RBT(x, cur);
					cur = cur->left;
					break;
				}
				else cur = cur->left;
			}
			else if (cur->root < x)
			{
				if (cur->right == nullptr)
				{
					grand = cur->pare;
					pat = cur;
					cur->right = new RBT(x, cur);
					cur = cur->right;
					break;
				}
				else cur = cur->right;
			}
			else return;
		}
		while (1)
		{
			if (cur->pare == nullptr)
			{
				cur->color = 1;
				break;
			}
			if (cur->color or pat->color)
			{
				break;
			}
			if (cur->color == 0 and pat->color == 0)
			{
				RBT* uncle;
				int dir = 1;
				int dir1 = 1;
				if (grand->right == pat) uncle = grand->left;
				else {
					uncle = grand->right; dir = 0;
				}
				if (uncle == nullptr)
				{
					if (pat->left == cur) dir1 = 0;
					if (dir == 1 and dir1 == 1)
					{
						grand->color = !grand->color;
						pat->color = !pat->color;
						RRotation(grand);
					}
					else if (dir == 0 and dir1 == 1)
					{
						cur->color = !cur->color;
						grand->color = !grand->color;
						RRotation(pat);
						LLotation(grand);
					}
					else if (dir == 1 and dir1 == 0)
					{
						cur->color = !cur->color;
						grand->color = !grand->color;
						LLotation(pat);
						RRotation(grand);
					}
					else if (dir == 0 and dir1 == 0)
					{
						grand->color = !grand->color;
						pat->color = !pat->color;
						LLotation(grand);
					}
					//break;
				}
				else if (uncle->color)
				{
					if (pat->left == cur) dir1 = 0;
					if (dir == 1 and dir1 == 1)
					{
						grand->color = !grand->color;
						pat->color = !pat->color;
						RRotation(grand);
					}
					else if (dir == 0 and dir1 == 1)
					{
						cur->color = !cur->color;
						grand->color = !grand->color;
						RRotation(pat);
						LLotation(grand);
					}
					else if (dir == 1 and dir1 == 0)
					{
						cur->color = !cur->color;
						grand->color = !grand->color;
						LLotation(pat);
						RRotation(grand);
					}
					else if (dir == 0 and dir1 == 0)
					{
						grand->color = !grand->color;
						pat->color = !pat->color;
						LLotation(grand);
					}
					//break;
				}
				else {
					uncle->color = !uncle->color;
					pat->color = !pat->color;
					grand->color = !grand->color;
					cur = grand;
					pat = nullptr;
					grand = nullptr;
					if (cur->pare == nullptr) continue;
					if (cur->pare != nullptr) pat = cur->pare;
					if (pat->pare == nullptr) continue;
					else grand = pat->pare;
				}

			}
		}
	}
	friend void roundred(RBT* sibiling, int ifbroleft)
	{
		RBT* p = sibiling->pare;
		if (ifbroleft)
		{
			if (sibiling->left == nullptr)
			{
				sibiling->right->color = p->color;
				p->color = 1;
				RRotation(sibiling);
				LLotation(p);
			}
			else
			{
				if (sibiling->left->color == 1)
				{
					sibiling->right->color = p->color;
					p->color = 1;
					RRotation(sibiling);
					LLotation(p);
				}
				else
				{
					sibiling->left->color = sibiling->color;
					sibiling->color = p->color;
					p->color = 1;
					LLotation(p);
				}
			}
		}
		else
		{
			if (sibiling->right == nullptr)
			{
				sibiling->left->color = p->color;
				p->color = 1;
				LLotation(sibiling);
				RRotation(p);
			}
			else
			{
				if (sibiling->right->color == 1)
				{
					sibiling->left->color = p->color;
					p->color = 1;
					LLotation(sibiling);
					RRotation(p);
				}
				else
				{
					sibiling->right->color = sibiling->color;
					sibiling->color = p->color;
					p->color = 1;
					RRotation(p);
				}
			}
		}
	}

	friend void doubleblack(RBT* dele)
	{
		RBT* p = dele->pare;
		RBT* bro;
		bool broifleft = 0;
		if (p == nullptr) throw exception();
		if (p->right == dele) {
			bro = p->left; broifleft = 1;
		}
		else { bro = p->right; }
		if (bro->color == 1)
		{
			if (bro->left != nullptr) {
				if (!bro->left->color) {
					roundred(bro, broifleft); return;
				}
			}
			else if (bro->right != nullptr) {
				if (!bro->right->color) {
					roundred(bro, broifleft); return;
				}
			}
			bro->color = 0;
			if (!p->color or p == ro) {
				p->color = 1; return;
			}
			else {
				doubleblack(p);
			}
		}
		else {
			bool color = bro->color;
			bro->color = p->color;
			p->color = color;
			if (p->right == dele) LLotation(p);
			else RRotation(p);
			doubleblack(dele);
		}
	}
	void del(int key)
	{
		RBT* index = this->search(key);
		if (index == nullptr) return;
		RBT* dele;
		if (index->right != nullptr) {
			dele = index->right;
			if (dele->left != nullptr)
			{
				while (dele->left != nullptr)
				{
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
						dele = dele->right;
					}
				}
				index->root = dele->root;
			}
		}
		if (!(dele->left == nullptr and dele->right == nullptr))
		{
			if (dele->left != nullptr)
			{
				RBT* par = dele->pare;
				RBT* chi = dele->left;
				if (par->left == dele) par->left = chi;
				else par->right = chi;
				if (chi != nullptr) chi->pare = par;
				chi->color = 1;
				dele->left = nullptr;
				//delete dele;
			}
			if (dele->right != nullptr)
			{
				RBT* par = dele->pare;
				RBT* chi = dele->right;
				if (par->left == dele) par->left = chi;
				else par->right = chi;
				if (chi != nullptr) chi->pare = par;
				chi->color = 1;
				dele->right = nullptr;
				//delete dele;
			}
			return;
		}
		else
		{
			if (dele->color == 0) {
				if (dele->pare->left == dele) dele->pare->left = nullptr;
				else dele->pare->right = nullptr;
				//delete dele; 
				return;
			}
			else
			{
				doubleblack(dele);
				RBT* p = dele->pare;
				if (p != nullptr)
				{
					if (p->left == dele) p->left = nullptr;
					else p->right = nullptr;
				}
				//delete dele;
			}
		}
	}


};
ostream& operator<<(ostream& os, RBT& rb)
{
	os << rb.root;
	if (rb.color) os << "(B)";
	else os << "(R)";
	os << " ";
	if (rb.left != nullptr) os << *rb.left;
	if (rb.right != nullptr) os << *rb.right;
	return os;
}
int findMin(RBT* root) {
	if (root == nullptr) {
		throw invalid_argument("The tree is empty.");
	}
	RBT* current = root;
	while (current->left != nullptr) {
		current = current->left;
	}
	return current->root;
}

int findMax(RBT* root) {
	if (root == nullptr) {
		throw invalid_argument("The tree is empty.");
	}
	RBT* current = root;
	while (current->right != nullptr) {
		current = current->right;
	}
	return current->root;
}
int main()
{
	LARGE_INTEGER nFreq;
	LARGE_INTEGER nBeginTime;
	LARGE_INTEGER nEndTime;
	std::mt19937 generator(0);
	vector<int> in;
	fstream i("test.txt", ios::in);
	fstream o("RBT.txt", ios::app);
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
	bool ifcorr = 1;
	RBT input(in[0]);
	int size = in.size();
	std::uniform_int_distribution<int> distribution(0, size - 1);
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
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	try {
		for (int i = 0; i < size; i++)
		{
			ro->del(in[i]);
			//cout << *ro << endl;
		}

		//cout << *ro;
	}
	catch (...) {
		//cout << "Null";
	}
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	//计算程序执行时间单位为秒数  
	o << "delete:" << endl;
	o << "gettimeofday  " << t * 1000 << "ms" << endl;
	return 0;
}

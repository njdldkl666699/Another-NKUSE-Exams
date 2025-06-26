#include<iostream>
#include<random>
#include<Windows.h>
#include<fstream>
using namespace std;
int main()
{
	std::mt19937 generator(0);
	fstream out("../../BST/BST/test.txt", ios::out);
	fstream out1("../../AVL/AVL/test.txt", ios::out);
	fstream out2("../../RBT/RBT/test.txt", ios::out);
	fstream out3("test.txt", ios::out);
	fstream out4("../../BT/BT/test.txt", ios::out);
	int n;
	cin >> n;
	std::uniform_int_distribution<int> distribution(1,2*n);
	srand((unsigned)time(NULL));
	for(int j=0;j<n;j++)
	{
		int x = distribution(generator);
		out << x << " ";
		out1 << x << " ";
		out2 << x << " ";
		out4 << x << " ";
		out3 << x << " ";
	}
	out << endl;
	out1 << endl;
	out2 << endl;
	out4 << endl;
	out3 << endl;
	return 0;
}

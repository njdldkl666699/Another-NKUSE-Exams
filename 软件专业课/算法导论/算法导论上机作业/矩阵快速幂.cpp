#include<iostream>
using namespace std;
static const long MODEL = 1000000007;
class Matrix
{
public:
	long** p;
	long dimension;
	Matrix(long** matrix, long d) :p(matrix), dimension(d) {};
	Matrix(long d) :dimension(d) {
		p = new long* [d];
		for (long i = 0; i < d; i++)
		{
			p[i] = new long[d];
			for (long j = 0; j < d; j++)
			{
				p[i][j] = 0;
			}
		}
	}
	Matrix(const Matrix& copy)
	{
		dimension = copy.dimension;
		p = new long* [dimension];
		for (long i = 0; i < dimension; i++)
		{
			p[i] = new long[dimension];
			for (long j = 0; j < dimension; j++)
			{
				p[i][j] = copy.p[i][j];
			}
		}
	}
	void operator%(long x)
	{
		for (long i = 0; i < dimension; i++)
		{
			for (long j = 0; j < dimension; j++)
			{
				p[i][j] %= x;
			}
		}
	}
	~Matrix()
	{
		for (long i = 0; i < dimension; i++)
			delete p[i];
		delete p;
	}
};
Matrix* operator*(const Matrix& m1, const Matrix& m2)
{
	Matrix* consequence = new Matrix(m1.dimension);
	for (long i = 0; i < m1.dimension; i++)
	{
		for (long j = 0; j < m1.dimension; j++)
		{
			for (long k = 0; k < m1.dimension; k++)
			{
				consequence->p[i][k] += m1.p[i][j] * m2.p[j][k];
				consequence->p[i][k] %= MODEL;
			}

		}
	}
	return consequence;
}
Matrix* operator^(Matrix& m1, long n)
{
	Matrix* middle;
	if (n == 1) return &m1;
	if (n % 2 != 0) {
		middle = (m1 ^ (n - 1));
		return m1 * (*middle);
	}
	else
	{
		middle = (m1 ^ ((n / 1) / 2));
		return (*middle) * (*middle);
	}
}
istream& operator>>(istream& is, Matrix& ma)
{
	for (long i = 0; i < ma.dimension; i++)
	{
		for (long j = 0; j < ma.dimension; j++)
		{
			is >> ma.p[i][j];
		}
	}
	return is;
}
ostream& operator<<(ostream& os, Matrix& ma)
{
	for (long i = 0; i < ma.dimension; i++)
	{
		for (long j = 0; j < ma.dimension; j++)
		{
			os << ma.p[i][j];
			if (j != ma.dimension - 1) os << " ";
		}
		if (i != ma.dimension - 1) os << endl;
	}
	return os;
}
int main()
{
	long dimension, n;
	cin >> dimension >> n;
	Matrix* ma = new Matrix(dimension);
	cin >> (*ma);
	Matrix* consequence = *(ma) ^ n;
	cout << *consequence;
}

import numpy as np
import matplotlib.pyplot as plt
import scipy.optimize as op
data = np.loadtxt("ex3data.txt",delimiter=',')
X = data[:,0:2]
y = data[:,2]
m, n = X.shape
pos = X[data[:,2] == 1, :]
neg = X[data[:,2] == 0, :]
plt.xlabel('Exam 1 score')
plt.ylabel('Exam 2 score')
plt.plot(pos[:,0], pos[:,1], '+k', label='Admitted')
plt.plot(neg[:,0], neg[:,1], 'oy', label='Not admitted')
plt.legend()
plt.show()
X_ones=np.ones([X.shape[0],1])
X =   np.hstack((X_ones,X)).T#数组拼接方法可使用np.hstack或np.append
y = data[:, [2]].T

def sigmoid(z):
    g =   1/(1+np.exp(-z))          # 使用np.exp()进行自然指数运算
    return g

#print(sigmoid(0), sigmoid(-100),sigmoid(10))
tmp = np.array([-5, -2, 0, 1, 3])
#print(sigmoid(tmp))
# 因为代价函数和梯度需要提供给SciPy的最小化函数
# 按照最小化函数参数的要求，代价和梯度需要使用flatten()转为一维数组
def costFunction(w, X, y):
    fx = sigmoid(w.T@X)
    y_ones=np.ones(y.shape)
    print(y.shape)
    print(fx.shape)
    L= -y@np.log(fx)-(y_ones-y)@np.log(fx)
    cost =   1/m*L            # 使用np.log()计算对数。
    return cost

def gradient(w, X, y):
    fx = sigmoid(w.T@X)
    grad = 1/m*np.sum((fx-y)@X)
    return grad.flatten() # 返回一维数组

w = np.zeros((n + 1,1))
[cost, grad] = [costFunction(w, X, y),gradient(w, X, y)]

print('costFunction()应该为0.6931')
print('gradient()结果应该为[-0.1, -12.0092, -11.2628]')
print('对初始零向量w求得的cost为',cost,'\n梯度为',grad)
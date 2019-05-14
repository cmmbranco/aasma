import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

boards = pd.read_csv("board-Complex.csv", header=None)
boards_n = boards.values
boards_n.resize((2,23,23))


print(boards_n)

plt.matshow(boards_n[0])
for (i, j), z in np.ndenumerate(boards_n[0]):
    plt.text(j, i, '{:0.1f}'.format(z), ha='center', va='center')
plt.show()
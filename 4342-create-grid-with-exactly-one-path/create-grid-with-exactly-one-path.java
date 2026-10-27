class Solution {
public String[] createGrid(int m, int n) {
String[] grid = new String[m];
char[][] arr = new char[m][n];

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            arr[i][j] = '#';
        }
    }

    int i = 0, j = 0;
    arr[i][j] = '.';

    while (i < m - 1 || j < n - 1) {
        if (j < n - 1) {
            arr[i][++j] = '.';
        }
        if (i < m - 1) {
            arr[++i][j] = '.';
        }
    }

    for (int r = 0; r < m; r++) {
        grid[r] = new String(arr[r]);
    }

    return grid;
}
}

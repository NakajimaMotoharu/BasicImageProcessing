import java.awt.*;
import java.awt.image.BufferedImage;

public class Sample {
	// Simplest Use Of Moving Average Filter
	public static BufferedImage calcConv(BufferedImage input, int n){
		double[][] filter = ImgCalc.getFilter(n);
		BufferedImage ans = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < ans.getWidth(); x = x + 1){
			for (int y = 0; y < ans.getHeight(); y = y + 1){
				int[] colorInt = ImgCalc.conv(input, x, y, filter);
				Color color = new Color(colorInt[0], colorInt[1], colorInt[2]);
				ans.setRGB(x, y, color.getRGB());
			}
		}

		return ans;
	}

	// Processing With A Clear Center And Blur Towards the Edges
	public static BufferedImage myFunc(BufferedImage input, int n){
		double[][][] filterList = new double[(n / 2) + 1][][];
		for (int i = 0; i < filterList.length; i = i + 1){
			filterList[i] = ImgCalc.getFilter((i * 2) + 1);
		}
		int[][] map = makeMap(n);
		BufferedImage ans = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < input.getWidth(); x = x + 1){
			for (int y = 0; y < input.getHeight(); y = y + 1){
				int mapX = (int) (((double) x / input.getWidth()) * n);
				int mapY = (int) (((double) y / input.getHeight()) * n);

				int[] colorInt = ImgCalc.conv(input, x, y, filterList[map[mapY][mapX]]);
				Color color = new Color(colorInt[0], colorInt[1], colorInt[2]);
				ans.setRGB(x, y, color.getRGB());
			}
		}

		return ans;
	}

	// Used Only With "myFunc"
	private static int[][] makeMap(int n){
		if (n % 2 == 0){
			System.out.println("Error: makeMap");
			System.exit(1);
		}
		int[][] ans = new int[n][n];

		if (n == 1){
			ans[0][0] = 0;
			return ans;
		}

		int[][] cur = makeMap(n - 2);
		int val = n / 2;
		for (int i = 0; i < n; i = i + 1){
			for (int j = 0; j < n; j = j + 1){
				if (((i > 0) && (j > 0)) && ((i < n - 1) && (j < n - 1))){
					ans[i][j] = cur[i - 1][j - 1];
				} else {
					ans[i][j] = val;
				}
			}
		}

		return ans;
	}
}

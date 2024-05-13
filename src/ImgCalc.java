import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgCalc {
	// Loading Image File
	public static BufferedImage readImage(String fileName){
		try {
			return ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Output Of Image File
	public static void writeImage(BufferedImage image, String fileName){
		try {
			ImageIO.write(image, "jpg", new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Conv Calc Of Specified Pixel Val
	public static int[] conv(BufferedImage in, int x, int y, double[][] filter){
		int n = filter.length / 2;
		double[] tmp = new double[]{0.0d, 0.0d, 0.0d};

		// Calc Of The Sum Of Product
		for (int px = x - n; px <= x + n; px = px + 1){
			for (int py = y - n; py <= y + n; py = py + 1){
				int[] c = getColor(in, px, py);
				for (int i = 0; i < 3; i = i + 1){
					tmp[i] = tmp[i] + (c[i] * filter[py - (y - n)][px - (x - n)]);
				}
			}
		}

		// Round The Val To Be Sure
		int[] ans = new int[]{(int) tmp[0], (int) tmp[1], (int) tmp[2]};
		for (int i = 0; i < 3; i = i + 1){
			if (ans[i] < 0){
				ans[i] = 0;
			} else if (ans[i] >= 256){
				ans[i] = 255;
			}
		}

		return ans;
	}

	// Get The Color Of The Specified Pixel(R, G, B)
	public static int[] getColor(BufferedImage in, int x, int y){
		// Return Black If (x, y) Is Out Of Array
		if (((x < 0) || (y < 0)) || ((in.getWidth() <= x) || (in.getHeight() <= y))){
			return new int[]{0, 0, 0};
		}

		// Once Converted To The Color Class And Then Acquired (For Clarity)
		Color color = new Color(in.getRGB(x, y));
		return new int[]{color.getRed(), color.getGreen(), color.getBlue()};
	}

	// Get A Moving Average Filter Of The Specified Size
	public static double[][] getFilter(int n){
		// If "n" Is An Even Number,
		// Immediately Terminates As An Error.
		if (n % 2 == 0){
			System.out.println("Error: getFilter[1]");
			System.exit(1);
		}

		// Allocate An Array And Fill The Contents With "val"
		double[][] f = new double[n][n];
		double val = 1.0d / (n * n);
		for (int i = 0; i < n; i = i + 1){
			for (int j = 0; j < n; j = j + 1){
				f[i][j] = val;
			}
		}

		return f;
	}
}

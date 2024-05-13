import java.awt.image.BufferedImage;

public class Main {
	public static void main(String[] args) {
		BufferedImage input = ImgCalc.readImage("input.jpg");
		for (int i = 3; i < 30; i = i + 2){
			BufferedImage output = Sample.myFunc(input, i);
			ImgCalc.writeImage(output, String.format("ori%dx%d.jpg", i, i));
		}
	}
}

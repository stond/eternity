package cz.fourtwoone.eternity.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author: Antonín Šafránek <antonin.safranek@lmc.eu>
 */
public class PieceGen {

    public static final int WIDTH = 50;
    public static final int HALF_WIDTH = WIDTH / 2;
    private static Map<Integer, Color> colors = new HashMap<>();
    static {
        colors.put(-1, Color.white);
        colors.put(0, Color.gray);
        colors.put(1, Color.yellow);
        colors.put(2, Color.green);
        colors.put(3, Color.pink);
        colors.put(4, Color.blue);
        colors.put(5, Color.black);
        colors.put(6, Color.red);
        colors.put(7, Color.cyan);
        colors.put(8, Color.lightGray);
        colors.put(9, Color.magenta);
        colors.put(10, Color.orange);
        colors.put(11, new Color(0x000044));
        colors.put(12, new Color(0x000088));
        colors.put(13, new Color(0x0000cc));
        colors.put(14, new Color(0x0000ff));
        colors.put(15, new Color(0x004400));
        colors.put(16, new Color(0x008800));
        colors.put(17, new Color(0x00cc00));
        colors.put(18, new Color(0x00ff00));
        colors.put(19, new Color(0x440000));
        colors.put(20, new Color(0x880000));
        colors.put(21, new Color(0xcc0000));
        colors.put(22, new Color(0xff0000));
    }

	public static void main(String[] args) throws Exception {
//		generatePieces("C:\\data\\programming\\eternity\\36.csv");
		generatePieces("C:\\data\\programming\\eternity\\256.csv");
	}

    public static void generatePieces(String filename) throws Exception {
        File file = new File(filename);
        List<Piece> pieces = new LinkedList<Piece>();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        Integer line = 0;
        while ((st = br.readLine()) != null) {
        	line++;
            String[] strParts = st.split(",");
            if (strParts.length == 5) {
	            int[] parts = Arrays.stream(strParts).mapToInt(Integer::valueOf).toArray();
	            pieces.add(new Piece(parts[0], parts[1], parts[2], parts[3], parts[4]));
	            System.out.println(st);
            } else {
            	System.out.println("No pieces definition on line " + line);
            }
        }
        generatePieces(pieces.toArray(new Piece[]{}));
    }

    public static void generatePieces(Piece[] pieces) throws Exception {


        for (int i=0; i<pieces.length; i++) {
            Piece p = pieces[i];
            BufferedImage image = createImage(p, Orientation.N);
            ImageIO.write(image, "jpg", new File(String.format("piece_%03d_0.jpg", p.getId())));
            image = createImage(p, Orientation.W);
            ImageIO.write(image, "jpg", new File(String.format("piece_%03d_1.jpg", p.getId())));
            image = createImage(p, Orientation.S);
            ImageIO.write(image, "jpg", new File(String.format("piece_%03d_2.jpg", p.getId())));
            image = createImage(p, Orientation.E);
            ImageIO.write(image, "jpg", new File(String.format("piece_%03d_3.jpg", p.getId())));
        }

        BufferedImage image = new BufferedImage(WIDTH, WIDTH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, WIDTH);
        ImageIO.write(image, "jpg", new File("piece_empty.jpg"));
    }

    private static BufferedImage createImage(Piece p, Orientation orientation) {
        OrientedPiece op = new OrientedPiece(p, orientation);
        BufferedImage image = new BufferedImage(WIDTH, WIDTH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(colors.get(p.getColor(OrientedPiece.sumOrientation(orientation, Orientation.N))));
        g.fillPolygon(new int[]{0, HALF_WIDTH, WIDTH}, new int[]{0, HALF_WIDTH, 0}, 3);
        g.setColor(colors.get(p.getColor(OrientedPiece.sumOrientation(orientation, Orientation.E))));
        g.fillPolygon(new int[]{WIDTH, HALF_WIDTH, WIDTH}, new int[]{0, HALF_WIDTH, WIDTH}, 3);
        g.setColor(colors.get(p.getColor(OrientedPiece.sumOrientation(orientation, Orientation.S))));
        g.fillPolygon(new int[]{WIDTH, HALF_WIDTH, 0}, new int[]{WIDTH, HALF_WIDTH, WIDTH}, 3);
        g.setColor(colors.get(p.getColor(OrientedPiece.sumOrientation(orientation, Orientation.W))));
        g.fillPolygon(new int[]{0, HALF_WIDTH, 0}, new int[]{WIDTH, HALF_WIDTH, 0}, 3);
        return image;
    }

}

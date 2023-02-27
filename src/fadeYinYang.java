import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Fading Yin and Yang Fractal
 * <p>
 *      Summary of Program: Yin and Yang with transposed colours, that fade into the inverse colour
 *
 *      A Fractal starts as line that is then split into 3 lines; 2 of the lines are equidistant angles
 *      from the initial arm, the 3rd line is the middle shifted towards a terminal arm
 *
 *      Fractal recuses 11 times represented as depth. the line gets darker/lighter each level there are also  2 fish, being Yin and Yang.
 *
 *      Furthermore, there is another recursive element; the eyes of this fractal are in of itself a fractal. As inverted monochrome Black and white
 * </p>
 *
 * @author Jaavin
 * @date 2022/01/05
 */

public class fadeYinYang extends JComponent
{
    Graphics g = null;

    // Constructor
    public fadeYinYang()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exit program when you close it
        frame.setSize(600,400);  // set the size of the window to whatever width and height you like
        frame.add(this); // put an object we can draw on in the centre of the window
        frame.setVisible(true); //show the window
        // set colour as water blue
        frame.getContentPane().setBackground(new Color(39, 71, 144)); // R G B
    }

    /**
     * draw a fish - draws a fish by using a rotated fractal tree, with 3 lines
     *
     * @param x1 line start x
     * @param y1 line start y
     * @param angle angle offset starting line is on
     * @param depth dept of the tree - affects orientation of fish
     * @param length length of the each line - affects shape of  fish
     * @param c color of the fish, will get darker/lighter, depending whether it is yin
     * @param part what part of the fish it is
     */
    private void drawFish(int x1, int y1, double angle, int depth, double length, Color c, String part) {
        // base case depth is 0
        if (depth != 0){
            // calculate endpoint by finding the adjacent value for terminal arm
            int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * length);
            // calculate endpoint by finding the opposite value for terminal arm
            int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * length);

            // set line colour
            this.g.setColor(c);

            // if the fish is yin make line darker
            switch (part) {
                case "yin":
                    // draw line
                    g.drawLine(x1, y1, x2, y2);
                    // two equidistant lines from the initial arm, and decrease depth
                    drawFish(x2, y2, angle - 23, depth - 1, length, c.darker(), part);
                    drawFish(x2, y2, angle + 23, depth - 1, length, c.darker(), part);

                    // offset arm in the middle offset by a factor of 150% of theta from second arm,
                    drawFish(x2, y2, angle + 46, depth - 1, length, c.darker(), part);
                    // still my favourite Java statement 😉.
                    break;
                // if yang fractal goes lighter
                case "yang":
                    // draw line
                    g.drawLine(x1, y1, x2, y2);
                    // two equidistant lines from the initial arm, and decrease depth
                    drawFish(x2, y2, angle - 23, depth - 1, length, c.brighter(), part);
                    drawFish(x2, y2, angle + 23, depth - 1, length, c.brighter(), part);

                    // offset arm in the middle offset by a factor of 150% of theta from second arm,
                    drawFish(x2, y2, angle + 46, depth - 1, length, c.brighter(), part);
                    break;
                // if the fish part is eye, colour is solid
                case "eye":
                    // draw line
                    g.drawLine(x1, y1, x2, y2);

                    // two equidistant lines, colour does not change
                    drawFish(x2, y2, angle - 23, depth - 1, length, c, part);
                    drawFish(x2, y2, angle + 23, depth - 1, length, c, part);

                    // offset arm
                    drawFish(x2, y2, angle + 46, depth - 1, length, c, part);
                    break;
            }
        }
    }

    /**
     * draw eyes for both fish
     *
     * @param x1 starting point x of eye from middle out
     * @param y1 starting point x of eye from middle out
     * @param length length of each line in eye - affects shape of eye
     * @param depth dept of the tree - affects orientation of eye
     */
    public void drawEyes(int x1, int y1, double length, int depth) {
        // base case
        if (depth!=0) {
            // draw eye for the upper fish
            g.setColor(Color.BLACK);
            // draw yin
            this.drawFish(x1, y1, 10, 11, length, Color.WHITE, "eye");
            // draw yang
            g.setColor(Color.WHITE);
            this.drawFish(x1, y1, -170, 11, length, Color.BLACK, "eye");

            // draw eye for lower fish
            g.setColor(Color.BLACK);
            // draw yin
            this.drawFish(x1, y1+140, 10, 11, length, Color.WHITE, "eye");
            g.setColor(Color.WHITE);
            // draw yang
            this.drawFish(x1, y1+140, -170, 11, length, Color.BLACK, "eye");

            /* It's an 👁️ for an 👁️. Emoji documentation should be the new best practice */

            // recursively draw top eye for fish
            drawEyes(x1-10, y1-15, length*0.45, depth-1);
            // recursively draw bottom eye for fish
            drawEyes(x1+10, y1+15, length*0.45, depth-1);
        }
    }

    /**
     * paint called each time the screen is repainted
     * @param g rendering information
     */
    public void paint(Graphics g) {
        this.g = g;

        // draw yin/top fish
        g.setColor(Color.BLACK);
        // start at middle, 10 degree incline, hypotenuse of 2.8, stack 10 times
        this.drawFish(getWidth() / 2, getHeight() / 2, 10, 11, 2.8, Color.WHITE, "yin");

        // draw yang/bottom fish
        g.setColor(Color.WHITE);
        // start at middle, -170 degree incline, hypotenuse of 2.8, stack 10 times
        this.drawFish(getWidth() / 2, getHeight() / 2, -170, 11, 2.8, Color.BLACK, "yang");

        // draw eye for BOTH yin and yang
        drawEyes(290, 110, 0.6, 2);
    }
}

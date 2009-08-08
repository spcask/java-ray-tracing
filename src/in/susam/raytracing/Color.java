/* 
 * Color class.
 * Copyright (C) 2009 Susam Pal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package in.susam.raytracing;

/**
 * Represents the color of a pixel. This class provides methods to
 * manipulate a pixel in various ways useful in ray tracing.
 *
 * @author Susam Pal
 */
public class Color
{
    /**
     * Red color.
     */
    private double red;

    /**
     * Green color.
     */
    private double green;

    /**
     * Blue color.
     */
    private double blue;

    /**
     * Creates an instance of this class with the specified colors.
     * The value for each color should lie between 0 and 1. 0 indicates
     * that the color is absent while 1 indicates that the color is
     * present in its full intensity.
     *
     * @param red   Red color.
     * @param green Green color.
     * @param blue  Blue color.
     */
    public Color(double red, double green, double blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Adds the specified <color>color</color> to the colors stored in
     * this instance.
     *
     * @param color Color to be added.
     */
    public void add(Color color)
    {
        red += color.red;
        green += color.green;
        blue += color.blue;
    }

    /**
     * Alters the color as per the specified <code>exposure</code>.
     *
     * @param exposure Exposure.
     */
    public void alterAsPerExposure(double exposure) {
        blue = 1.0 - Math.exp(-blue * exposure);
        red = 1.0 - Math.exp(-red * exposure);
        green = 1.0 - Math.exp(-green * exposure);
    }

    /**
     * Encodes the color in sRGB format.
     */
    public void srgbEncode() {
        red = srgbEncode(red);
        green = srgbEncode(green);
        blue = srgbEncode(blue);
    }

    /**
     * Converts the color to bytes that can be rewritten into a BMP
     * image file.
     *
     * @return An array of three bytes containing the values for blue,
     *         green and red components, in that order.
     */
    public byte[] toBytesForBMP() {
        return new byte[] {
            // Naive clamping is used here to ensure that the value of a
            // color in the BMP representation does  not exceed 255 even
            // if the double value stored in this object exceeds 1.0.
            (byte) Math.min(blue * 255, 255),
            (byte) Math.min(green * 255, 255),
            (byte) Math.min(red * 255, 255)
        };
    }


    /**
     * Multiplies the red, green and blue components of the color with
     * <code>c</code>.
     *
     * @param c     Multiplier.
     * @param color Color to multiply.
     * @return The result as a color object.
     */
    public static Color multiply(double c, Color color)
    {
        return new Color(c * color.red, c * color.green, c * color.blue);
    }

    /**
     * Multiplies the red, green and blue components of <code>c1</code>
     * with the respective components of <code>c2</code>
     * 
     * @param c1 One color.
     * @param c2 Another color.
     * @return The result as a color object.
     */
    public static Color multiply(Color c1, Color c2)
    {
        return new Color(c1.red * c2.red,
                        c1.green * c2.green,
                        c1.blue * c2.blue);
    }

    /**
     * Calculates the sRGB encoded value for the specified color
     * component.
     *
     * @param c Value of a color component such as that of red, green or
     *          blue.
     * @return sRGB encoded value.
     */
    private static double srgbEncode(double c)
    {
        if (c <= 0.0031308) {
            return 12.92 * c;
        } else {
            return 1.055 * Math.pow(c, 0.4166667) - 0.055;
        }
    }
}


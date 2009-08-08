/* 
 * Point class.
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
 * Represents a point in 3 dimensional space.
 *
 * @author Susam Pal
 */
public class Point
{
    /**
     * X coordinate of the point.
     */
    public double x;

    /**
     * Y coordinate of the point.
     */
    public double y;

    /**
     * Z coordinate of the point.
     */
    public double z;

    /**
     * Creates an instance of this class with the specified coordinates.
     *
     * @param x X cordinate of the point.
     * @param y Y cordinate of the point.
     * @param z Z cordinate of the point.
     */
    public Point(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}


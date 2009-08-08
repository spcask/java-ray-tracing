/* 
 * A ray.
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
 * Represents a ray of light.
 */
public class Ray
{
    /**
     * Initial point of the ray.
     */
    public Point origin;

    /**
     * Direction of the ray.
     */
    public Vector direction;

    /**
     * Creates an instance of this class.
     *
     * @param origin    Initial point of the ray.
     * @param direction Direction vector of the ray.
     */
    public Ray(Point origin, Vector direction)
    {
        this.origin = origin;
        this.direction = direction;
    }
}


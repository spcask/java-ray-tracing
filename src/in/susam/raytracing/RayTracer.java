/*
 * Sample orthographic ray tracer
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import in.susam.util.Logger;

/**
 * This class performs the ray tracing for a scene configuration defined
 * in {@link Scene#sceneDefinition}. The ray tracing is done from an
 * orthographic perspective.
 *
 * @author Susam Pal
 */
public class RayTracer
{
    /**
     * {@link in.susam.util.Logger} object for this class.
     */
    private static Logger logger = new Logger();

    /**
     * Position of the camera is given by the coordinate
     * (0, 0, CAMERA_POSITION).
     */
    private static final int CAMERA_POSITION = -1000;

    /**
     * <code>main</code> method that starts the ray tracing. Output
     * image is saved in output.bmp file. Command line arguments are
     * ignored.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException
    {
        String outputFile = "output.bmp";

        Scene scene = new Scene();
        draw(outputFile, scene);
    }

    /**
     * Performs ray tracing and computes each pixel of the image that is
     * saved in BMP format in the output file.
     *
     * @param outputFile Name of the output BMP image file.
     * @param scene      Scene definition
     */
    private static void draw(String outputFile, Scene scene) throws IOException
    {
        FileOutputStream f = new FileOutputStream(outputFile);
        logger.log("Opened " + outputFile + " to write BMP output.");

        // Padding required if the number of bytes to draw pixels of one
        // row is not a multiple of 4. This padding is required by the
        // BMP format specification.
        int padding = (4 - scene.width * 3 % 4) % 4;

        logger.log(scene.width * 3 + " bytes required for pixels in a row.");
        logger.log("Padding of " + padding + " byte " +
                   (padding == 1 ? "s" : "") + "will be used at the end " +
                   "each row of pixels.");

        // Number of bytes in the raw BMP data.
        int rawBMPSize = (scene.width * 3 + padding) * scene.height;

        logger.log("Size of raw BMP data: " + rawBMPSize + " byte" +
                   (rawBMPSize == 1 ? "s" : ""));

        // BMP headers occupy 54 bytes. So, the total size of the file
        // is 54 + rawBMPSize.
        int size = 54 + rawBMPSize;

        logger.log("Total size of BMP image file: " + size + " byte" +
                   (size > 1 ? "s" : ""));

        // Write the BMP magic number \x42\x4D into the BMP header.
        f.write(new byte[] {'B', 'M'});

        // Write the BMP file size into the BMP header.
        f.write(size & 0x000000FF);
        f.write((size & 0x0000FF00) >>> 8);
        f.write((size & 0x00FF0000) >>> 16);
        f.write((size & 0xFF000000) >>> 24);

        // Unused bytes in the header
        f.write(new byte[] {0, 0, 0, 0});

        // Write the offset of BMP data pixels into the BMP header.
        f.write(new byte[] {0x36, 0, 0, 0});
        f.write(new byte[] {0x28, 0, 0, 0});

        // Write the width of the image (in pixels) into the BMP header.
        f.write(scene.width & 0x000000FF);
        f.write((scene.width & 0x0000FF00) >>> 8);
        f.write((scene.width & 0x00FF0000) >>> 16);
        f.write((scene.width & 0xFF000000) >>> 24);

        // Write the height of the image (in pixels) into the BMP header.
        f.write(scene.height & 0x000000FF);
        f.write((scene.height & 0x0000FF00) >>> 8);
        f.write((scene.height & 0x00FF0000) >>> 16);
        f.write((scene.height & 0xFF000000) >>> 24);

        // Write the color plane, color depth and compression related
        // information in the BMP header.
        f.write(new byte[] {1, 0});    // 1 color plane
        f.write(new byte[] {0x18, 0}); // 24 bits per pixel
        f.write(new byte[] {0, 0, 0, 0});  // No compression

        // Write the size of the raw BMP data into the BMP header.
        f.write(rawBMPSize & 0x000000FF);
        f.write((rawBMPSize & 0x0000FF00) >>> 8);
        f.write((rawBMPSize & 0x00FF0000) >>> 16);
        f.write((rawBMPSize & 0xFF000000) >>> 24);

        // Write the resolution information in the BMP header.
        f.write(new byte[] {0x13, 0x0B, 00, 00}); // Horizontal resolution
        f.write(new byte[] {0x13, 0x0B, 00, 00}); // Vertical resolution

        // Write color palette information in the BMP header.
        f.write(new byte[] {0, 0, 0, 0});  // Number of colors in the palette
        f.write(new byte[] {0, 0, 0, 0});  // All colors are important

        logger.log("Written 54 bytes of BMP header.");

        logger.log("Tracing ...");

        // Send a ray from each pixel of the camera and compute the
        // color of this pixel.
        for (int y = 0; y < scene.height; y++) {
            for (int x = 0; x < scene.width; x++) {

                // We start with a black pixel and add colors to this
                // pixel as find rays that determine the color of this
                // pixel.
                Color outputColor = new Color(0, 0, 0);

                // Antialiasing by 4x supersampling.
                for (double fx = x; fx < x + 1; fx += 0.5) {
                    for (double fy = y; fy < y + 1; fy += 0.5) {

                        // Each sample contributes to 1/4th of the
                        // color.
                        double sampleRatio = 0.25;

                        // Cast a ray from a point on the camera and
                        // perpendicular to the plane of the camera,
                        // i.e. XY plane.
                        Ray viewRay = new Ray(new Point(fx, fy,
                                                        CAMERA_POSITION),
                                              new Vector(0, 0, 1));
                        Color color = getColor(viewRay, scene);

                        // Alter the pixel color depending on exposure.
                        color.alterAsPerExposure(1.0);

                        outputColor.add(Color.multiply(sampleRatio, color));
                    }
                }

                // Gamma correction
                outputColor.srgbEncode();

                // Write the bytes for the pixel into the BMP file.
                f.write(outputColor.toBytesForBMP());
            }

            // After the bytes for each row of pixels is written into
            // the BMP file, pad it with null bytes so as to make the
            // number of bytes per row a multiple of 4.
            for (int i = 0; i < padding; i++) {
                f.write(0);
            }

        }

        logger.log("Trace complete. " + rawBMPSize + " bytes of raw " +
                   "BMP data written.");
        f.close();
        logger.log("Closed " + outputFile + ".");
    }

    /**
     * Get the color of the pixel on the camera from where the specified
     * <code>ray</code> originated.
     *
     * @param ray   Ray originating from the camera.
     * @param scene Scene defintion object.
     */
    public static Color getColor(Ray ray, Scene scene)
    {
        Color outputColor = new Color(0, 0, 0);

        double coef = 1;
        int level = 0;

        do {
            // This variable stores the first object that the ray hits.
            Scene.Sphere sphere = null;

            double distance = 2000.0f;

            // Find the first object with which the ray intersects.
            for (Scene.Sphere s : scene.spheres) {
                HitResult h = hitSphere(ray, s, distance);
                distance = h.distance;
                if (h.hit) {
                    sphere = s;
                }

            }

            // If the ray is not intersecting any object, stop processing
            // this ray.
            if (sphere == null) {
               break;
            }

            // Compute the point where the ray intersected the object.
            Point hitPoint =
                    Vector.add(ray.origin,
                               Vector.multiply(distance, ray.direction));

            // Calculate a vector normal to the surface of the object at
            // the point of intersection of the ray and the object.
            Vector normal = new Vector(sphere.center, hitPoint);

            // Normalize the normal vector.
            try {
                normal.normalize();
            } catch (ArithmeticException e) {
                break;
            }

            Scene.Material currentMaterial = sphere.material;

            for (Scene.Light light : scene.lights) {

                Ray lightRay = new Ray(hitPoint,
                        new Vector(hitPoint, light.origin));

                double lightProjection =
                        Vector.dotProduct(lightRay.direction, normal);

                if (lightProjection <= 0.0)
                    continue;

                double lightDistance = lightRay.direction.norm();
                lightRay.direction.normalize();
                lightProjection /= lightDistance;

                boolean inShadow = false;
                for (Scene.Sphere s : scene.spheres) {
                    HitResult h = hitSphere(lightRay, s, lightDistance);
                    if (h.hit) {
                        inShadow = true;
                        break;
                    }
                }

                if (!inShadow)
                {
                    // Lambertian coeffecient
                    double lambert = Vector.dotProduct(lightRay.direction,
                                                         normal) * coef;

                    outputColor.add(Color.multiply(lambert, Color.multiply(
                        light.intensity, currentMaterial.diffusion)));

                    // Blinn-Phong specular term
                    Vector v = Vector.subtract(lightRay.direction,
                                                 ray.direction);
                    double norm = v.norm();
                    if (norm != 0.0) {

                        double viewProjection =
                                Vector.dotProduct(ray.direction, normal);

                        double blinnPhongTerm =
                                Math.max(lightProjection - viewProjection, 0.0)
                                / norm;

                        blinnPhongTerm =
                            coef * Math.pow(blinnPhongTerm,
                                            currentMaterial.power);
                        Color c = Color.multiply(blinnPhongTerm,
                                                 currentMaterial.specularity);
                        c = Color.multiply(c, light.intensity);
                        outputColor.add(c);
                    }
                }
            }

            coef *= currentMaterial.reflection;

            // If i is incident ray vector and n is the normal vector
            // (both normalized) then the reflected ray direction is
            // given by i - 2 * d.n * n
            Vector projectionAlongNormal = Vector.multiply(
                    Vector.dotProduct(ray.direction, normal), normal);

            // Compute the reflected ray.
            ray.origin = hitPoint;
            ray.direction = Vector.subtract(ray.direction,
                    Vector.multiply(2, projectionAlongNormal));
            level++;
        } while ((coef > 0) && (level < 10));
        return outputColor;
    }

    /**
     * Represents the result of a ray intersecting with an object.
     */
    private static class HitResult
    {
        /**
         * Distance between the origin of the ray and the point of
         * intersection. This contains meaningful data only if
         * <code>hit</code> is <code>true</code>.
         */
        double distance;

        /**
         * Whether the ray intersects with the object or not.
         */
        boolean hit;

        /**
         * Creates an instance of this class.
         *
         * @param distance Distance between the origin of the ray and
         *                 the point of intersection with an object.
         * @param hit      Whether the ray intersects the object.
         */
        HitResult(double distance, boolean hit)
        {
            this.distance = distance;
            this.hit = hit;
        }
    }

    /**
     * Finds whether a ray intersects a sphere such that the distance
     * between the intersection point and the origin of the ray is less
     * than the specified <code>distance</code>.
     *
     * @param ray      A ray.
     * @param sphere   A sphere.
     * @param distance Distance between the intersection point and the
     *                 origin of the ray should be less than this
     *                 distance.
     */
    private static HitResult hitSphere(Ray ray, Scene.Sphere sphere,
                                       double distance)
    {
        Vector d = new Vector(ray.origin, sphere.center);

        double a = Vector.dotProduct(ray.direction, d);

        double delta = a * a - Vector.dotProduct(d, d) +
                       sphere.radius * sphere.radius;

        if (delta < 0.0) {
            return new HitResult(distance, false);
        }

        double root0 = a - Math.sqrt(delta);
        double root1 = a + Math.sqrt(delta);

        if (root0 > 0.1 && root0 < distance) {
            return new HitResult(root0, true);
        }

        if (root1 > 0.1 && root1 < distance) {
            return new HitResult(root1, true);
        }

        return new HitResult(distance, false);
    }

}

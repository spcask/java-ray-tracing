/* 
 * Scene.
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

import java.util.Collection;
import java.util.ArrayList;

/**
 * Represents a scene where ray tracing is to be performed.
 *
 * @author Susam Pal
 */
public class Scene
{
    /**
     * A collection of spheres used in the scene.
     */
    public Collection<Sphere> spheres;

    /**
     * A collection of light sources used in the scene.
     */
    public Collection<Light> lights;

    /**
     * Width of the image.
     */
    public int width;

    /**
     * Height of the image.
     */
    public int height;

    /**
     * Represents a material of an object in the scene.
     */
    public class Material
    {
        /**
         * Diffusion color.
         */
        public Color diffusion;

        /**
         * Reflection coefficient.
         */
        public double reflection;

        /**
         * Specularity color.
         */
        public Color specularity;

        /**
         * Specular power.
         */
        public double power;


       /**
        * Creates an instance of this class.
        *
        * @param diffusion   Diffusion color.
        * @param reflection  Reflection coeffecient.
        * @param specularity Specularity color.
        * @param power       Specular power.
        */
       public Material(Color diffusion, double reflection,
                       Color specularity, double power)
        {
            this.diffusion = diffusion;
            this.specularity = specularity;
            this.reflection = reflection;
            this.power = power;
        }
    }

    /**
     * Represents a spherical object in the scene.
     */
    public class Sphere
    {
        /**
         * Center of the sphere.
         */
        public Point center;

        /**
         * Radius of the sphere.
         */
        public double radius;

        /**
         * Material of the sphere.
         */
        public Material material;

        /**
         * Creates an instance of this class.
         *
         * @param center   Center of the sphere.
         * @param radius   Radius of the sphere.
         * @param material Material of the sphere.
         */
        public Sphere(Point center, double radius, Material material)
        {
            this.center = center;
            this.radius = radius;
            this.material = material;
        }
    }

    /**
     * Represents a light source in the scene.
     */
    public class Light
    {
        /**
         * Origin of the light source.
         */
        public Point origin;

        /**
         * Intensity of the light source.
         */
        public Color intensity;

        /**
         * Creates an instance of this class.
         *
         * @param origin    Origin of the light source.
         * @param intensity Intensity of the light source.
         */
        public Light(Point origin, Color intensity)
        {
            this.origin = origin;
            this.intensity = intensity;
        }
    }

    /**
     * Creates an instance of this class.
     */
    public Scene()
    {
        spheres = new ArrayList<Sphere>();
        lights = new ArrayList<Light>();

        sceneDefinition();
    }

    /**
     * Defines the scene.
     */
    private void sceneDefinition()
    {
        width = 800;
        height = 600;

        // Materials
        Material whiteMaterial = new Material(
            new Color(1.0, 1.0, 1.0),  // Diffusion
            1.0,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );

        Material redMaterial = new Material(
            new Color(1.0, 0.0, 0.0),  // Diffusion
            0.5,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );
                                      
        Material greenMaterial = new Material(
            new Color(0.0, 1.0, 0.0),  // Diffusion
            0.5,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );

        Material blueMaterial = new Material(
            new Color(0.0, 0.0, 1.0),  // Diffusion
            0.5,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );

        Material yellowMaterial = new Material(
            new Color(1.0, 1.0, 0.0),  // Diffusion
            0.5,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );

        Material cyanMaterial = new Material(
            new Color(0.0, 1.0, 1.0),  // Diffusion
            0.5,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );

        Material magentaMaterial = new Material(
            new Color(1.0, 0.0, 1.0),  // Diffusion
            0.5,                       // Reflection
            new Color(1.0, 1.0, 1.0),  // Specularity
            60                         // Specular power
        );

        Material blackMaterial = new Material(
            new Color(0.01, 0.01, .01), // Diffusion
            1.0,                        // Reflection
            new Color(1.0, 1.0, 1.0),   // Specularity
            60                          // Specular power
        );

        // White sphere
        spheres.add(new Sphere(
            new Point(400.0, 300.0, 0.0),    // Center
            200.0,                           // Radius
            whiteMaterial                    // Material
        ));

        // Red sphere
        spheres.add(new Sphere(
            new Point(300.0, 200.0, -350.0), // Center
            100.0,                           // Radius
            redMaterial                      // Material
        ));

        // Green sphere
        spheres.add(new Sphere(
            new Point(400.0, 240.0, -500.0), // Center
            50.0,                            // Radius
            greenMaterial                    // Material
        ));

        // Blue sphere
        spheres.add(new Sphere(
            new Point(600.0, 240.0, -350.0), // Center
            100.0,                           // Radius
            blueMaterial                     // Material
        ));

        // Yellow sphere
        spheres.add(new Sphere(
            new Point(600.0, 400.0, 200.0),  // Center
            75.0,                            // Radius
            yellowMaterial                   // Material
        ));

        // Cyan sphere
        spheres.add(new Sphere(
            new Point(100.0, 400.0, 0.0),    // Center
            75.0,                            // Radius
            cyanMaterial                     // Material
        ));

        // Magenta sphere
        spheres.add(new Sphere(
            new Point(300.0, 400.0, -600.0), // Center
            75.0,                            // Radius
            magentaMaterial                  // Material
        ));

        // Black sphere
        spheres.add(new Sphere(
            new Point(450.0, 300.0, -300.0), // Center
            50.0,                            // Radius
            blackMaterial                    // Material
        ));

        // Larger yellow sphere
        spheres.add(new Sphere(
            new Point(125.0, 200.0, -600.0), // Center
            120.0,                           // Radius
            yellowMaterial                   // Material
        ));

        // Larger green sphere
        spheres.add(new Sphere(
            new Point(600.0, 500.0, 0.0),   // Center
            80.0,                           // Radius
            greenMaterial                   // Material
        ));

        // Left light
        lights.add(new Light(
            new Point(0.0, 200.0, -100.0),     // Origin
            new Color(2.0, 2.0, 2.0)           // Intensity
        ));

        // Light behind the camera
        lights.add(new Light(
            new Point(640.0, 240.0, -10000.0), // Origin
            new Color(0.4, 0.4, 0.5)           // Intensity
        ));
        
        // Light behind the white sphere.
        lights.add(new Light(
            new Point(640.0, 240.0, 100.0),   // Origin
            new Color(0.2, 0.2, 0.5)          // Intensity
        ));
    }
}

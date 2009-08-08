/* 
 * Logger.
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
package in.susam.util;

/**
 * This class represents a simple logger.
 * 
 * @author Susam Pal
 */
public class Logger
{
    /**
     * Name of the logger.
     */
    private String name;

    /**
     * Creates a logger without name.
     */
    public Logger()
    {
    }

    /**
     * Creates a logger with the specified <code>name</code>.
     *
     * @param name Name of the logger.
     */
    public Logger(String name)
    {
        this.name = name;
    }

    /**
     * Logs the specified <code>message</code>. The message is prefixed
     * with the name of the logger, if a name was specified while
     * instantiating the logger object. The log message is followed by a
     * newline.
     *
     * @param message   Message to be logged.
     */
    public void log(String message)
    {
        if (name == null)
            System.out.println(message);
        else
            System.out.println(name + ": " + message);
    }

    /**
     * Logs the specified <code>message</code>. The message is prefixed
     * with the name of the logger, only if a name was specified while
     * instantiating the logger object and <code>printName</code> is
     * specified as <code>true</code>. The log message is not followed
     * by a newline.
     *
     * @param message   Message to be logged.
     * @param printName Whether to print the logger's name.
     */
    public void logPartial(String message, boolean printName)
    {
        if (name == null || printName == false)
            System.out.println(message);
        else
            System.out.println(name + ": " + message);
    }
}


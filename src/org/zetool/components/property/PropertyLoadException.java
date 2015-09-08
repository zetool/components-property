/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.components.property;

import java.io.IOException;
import java.io.Reader;

/**
 * A {@code PropertyLoadException} is thrown if an error during loading of a property file occured.
 * It is possible to submit the file that created the error.
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyLoadException extends IOException {

    /** The file that created the error. */
    private Reader file;

    /**
     * Creates a new {@code PropertyLoadException} with default error string.
     * @param t
     */
    public PropertyLoadException(Throwable t) {
        this("Error loading properties.", t);
    }

    /**
     * Creates a new {@code PropertyLoadException} with an error string.
     *
     * @param s the error string
     * @param t
     */
    public PropertyLoadException(String s, Throwable t) {
        super(s, t);
    }

    /**
     * Creates a new {@code PropertyLoadException}, sets the file and sets an error message containing the filename.
     *
     * @param f the file
     * @param t
     */
    public PropertyLoadException(Reader f, Throwable t) {
        this(f == null ? "" : "Error loading properties from '" + f + "'", t);
    }

    /**
     * Returns the file that created the error. Can be {@code null}.
     *
     * @return the file that created the error.
     */
    public Reader getFile() {
        return file;
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}

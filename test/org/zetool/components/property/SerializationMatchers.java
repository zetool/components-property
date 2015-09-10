/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.nio.ByteBuffer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.zetool.common.bit.ByteOutput;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@Ignore
public class SerializationMatchers {

    public static void assertClassNotDeserializable(Class<?> c) {
        try {
            ObjectStreamClass osc = ObjectStreamClass.lookup(c);
            String className = c.getName();
            long serialVersionUID = osc.getSerialVersionUID();

            byte[] objectBytes = getDeserializedBytes(className, serialVersionUID);

            ObjectInputStream in = new ObjectInputStream(new ByteInputStream(objectBytes, objectBytes.length));
            in.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            throw new AssertionError(c.getName(), ex);
        }
    }

    private static byte[] getDeserializedBytes(String className, long serialVersionUID) {
        byte[] initBytes = {(byte) 0xac, (byte) 0xed, 0, 5, 115, 114, 0};
        byte[] stringBytes = className.getBytes();
        byte[] length = new byte[]{(byte) stringBytes.length};
        byte[] serialVersionBytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(serialVersionUID).array();
        byte[] endBytes = {2, 0, 0, 120, 112};

        return ByteOutput.combineBytes(initBytes, length, stringBytes, serialVersionBytes, endBytes);
    }

    public static Matcher<Class<?>> notDeserializable() {
        return new TypeSafeMatcher<Class<?>>() {
            private String name = "";

            @Override
            public boolean matchesSafely(Class<?> item) {
                name = item.getName();
                try {
                    assertClassNotDeserializable(item);
                } catch (UnsupportedOperationException ex) {
                    return true;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("UnsupportedOperationException thrown by " + name);
            }

            @Override
            public void describeMismatchSafely(final Class<?> item, final Description mismatchDescription) {
                mismatchDescription.appendText(" was " + item.toString());
            }
        };
    }

    public static Matcher<Object> notSerializable() {
        return new TypeSafeMatcher<Object>() {
            private String name = "";

            @Override
            protected boolean matchesSafely(final Object item) {
                name = item.getClass().getName();
                ObjectOutputStream out;
                try {
                    out = new ObjectOutputStream(new ByteOutputStream());
                    out.writeObject(item);
                } catch (UnsupportedOperationException ex ) {
                    return true;
                } catch (IOException ex) {
                    throw new AssertionError(item.toString(), ex);
                }
                return false;
            }
            @Override
            public void describeTo(final Description description) {
                description.appendText("UnsupportedOperationException thrown by " + name);
            }

            @Override
            protected void describeMismatchSafely(final Object item, final Description mismatchDescription) {
                mismatchDescription.appendText(" was " + item.toString());
            }
        };
    }

}

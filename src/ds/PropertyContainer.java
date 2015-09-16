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
package ds;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 * Stores properties of arbitrary type accessible via a string.
 */
public class PropertyContainer implements Iterable<String> {

    private static final Localization LOC = CommonLocalization.LOC;
    private final Map<String, Object> properties;
    private final Map<String, Class<?>> propertyTypes;
    private static final String NOT_DEFINED = "ds.PropertyNotDefinedException";
    public PropertyContainer() {
        properties = new HashMap<>();
        propertyTypes = new HashMap<>();
    }

    /**
     * Returns the instance of the singleton {@code PropertyContainer}.
     *
     * @return the instance of the singleton {@code PropertyContainer}
     */
    public static PropertyContainer getGlobal() {
        return GlobalInstanceHolder.INSTANCE;
    }

    public <T> void define(String key, Class<T> type, T defaultValue) {
        if (propertyTypes.containsKey(key)) {
            throw new IllegalArgumentException(LOC.getString("ds.PropertyAlreadyDefinedException: " + key));
        } else {
            properties.put(key, defaultValue);
            propertyTypes.put(key, type);
        }
    }

    public Object get(String key) {
        if (!propertyTypes.containsKey(key)) {
            throw new IllegalArgumentException(LOC.getString(NOT_DEFINED) + ": " + key);
        }
        return properties.get(key);
    }

    public <T> T getAs(String key, Class<T> type) {
        if (!propertyTypes.containsKey(key)) {
            throw new IllegalArgumentException(LOC.getString(NOT_DEFINED) + ": " + key);
        } else if (!type.isAssignableFrom(propertyTypes.get(key))) {
            throw new IllegalArgumentException(LOC.getString("ds.PropertyTypeCastException: " + key + ", " + propertyTypes.get(key) + ", " + type));
        } else {
            return type.cast(properties.get(key));
        }
    }

    public boolean getAsBoolean(String key) {
        return getAs(key, Boolean.class);
    }

    public Color getAsColor(String key) {
        return getAs(key, Color.class);
    }

    public double getAsDouble(String key) {
        return getAs(key, Double.class);
    }

    public int getAsInt(String key) {
        return getAs(key, Integer.class);
    }

    public Font getAsFont(String key) {
        return getAs(key, Font.class);
    }

    public String getAsString(String key) {
        return getAs(key, String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAsStringList(String key) {
        return (List<String>) getAs(key, List.class);
    }

    /**
     * Toggles a boolean value and returns the new value.
     *
     * @param key
     * @return
     */
    public boolean toggle(String key) {
        final boolean temp = !getAsBoolean(key);
        set(key, temp);
        return temp;
    }

    public void set(String key, Object value) {
        if (!propertyTypes.containsKey(key)) {
            throw new IllegalArgumentException(LOC.getString(NOT_DEFINED) + ": " + key);
        } else if (!propertyTypes.get(key).isInstance(value)) {
            throw new IllegalArgumentException(LOC.getString("ds.PropertyValueException: " + key + ", " + propertyTypes.get(key) + ", " + value));
        } else {
            properties.put(key, value);
        }
    }

    public boolean isDefined(String key) {
        return propertyTypes.containsKey(key);
    }

    public Class<?> getType(String key) {
        return propertyTypes.get(key);
    }

    @Override
    public Iterator<String> iterator() {
        return this.properties.keySet().iterator();
    }
    
    public int size() {
        return this.properties.size();
    }


    /**
     * The instance of the single global instance of {@code PropertyContainer}. Initializes the global property
     * container lazily only if it is accessed.
     *
     * @see #getGlobal()
     */
    private static class GlobalInstanceHolder {
        private GlobalInstanceHolder() {
        }
        
        private static final PropertyContainer INSTANCE = new PropertyContainer();
    }
}

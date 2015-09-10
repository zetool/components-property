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

import gui.propertysheet.GenericProperty;
import org.zetool.components.property.PropertyLoadException;
import org.zetool.components.property.PropertyTreeModelLoader;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.abs.PropertyValue;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.IntegerProperty;
import gui.propertysheet.types.DoubleProperty;
import gui.propertysheet.types.StringProperty;
import gui.propertysheet.types.StringListProperty;
import java.awt.Color;
import java.awt.Font;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 * Stores properties of arbitrary type accessible via a string.
 */
public class PropertyContainer {

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
    public ArrayList<String> getAsStringList(String key) {
        return (ArrayList<String>) getAs(key, ArrayList.class);
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

    /**
     * Loads properties from an XML-file into the {@link PropertyContainer} and returns a {@link PropertyTreeModel} of
     * the properties. This can be used to store the same (maybe changed) data later.
     *
     * @param file the property XML-file
     * @return a model of the loaded properties
     * @throws PropertyLoadException if an error occurs during loading of the specified file
     */
    public PropertyTreeModel applyParameters(FileReader file) throws PropertyLoadException {
        PropertyTreeModelLoader loader = new PropertyTreeModelLoader();
        final PropertyTreeModel ptm = loader.loadConfigFile(file);
        applyParameters(ptm);
        return ptm;
    }

    /**
     * Loads properties from an {@link PropertyTreeModel} into the {@link PropertyContainer}.
     *
     * @param propertyTreeModel the tree model containing the properties
     */
    public void applyParameters(PropertyTreeModel propertyTreeModel) {
        applyParameters(propertyTreeModel.getRoot());
    }

    /**
     * Loads properties stored in an node of an {@link PropertyTreeModel} into the {@link PropertyContainer}.
     *
     * @param node the node at which recursive loading starts.
     */
    protected void applyParameters(PropertyTreeNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            applyParameters(node.getChildAt(i));
        }
        PropertyContainer pc = PropertyContainer.getGlobal();
        for (GenericProperty property : node.getProperties()) {
            if (property instanceof BooleanProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Boolean.class, (Boolean) property.getValue());
                } else {
                    pc.set(property.getName(), (Boolean) property.getValue());
                }
            } else if (property instanceof IntegerProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Integer.class, (Integer) property.getValue());
                } else {
                    pc.set(property.getName(), (Integer) property.getValue());
                }
            } else if (property instanceof DoubleProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Double.class, (Double) property.getValue());
                } else {
                    pc.set(property.getName(), (Double) property.getValue());
                }
            } else if (property instanceof StringProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), String.class, (String) property.getValue());
                } else {
                    pc.set(property.getName(), (String) property.getValue());
                }
            } else if (property instanceof StringListProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), ArrayList.class, (ArrayList) property.getValue());
                } else {
                    pc.set(property.getName(), (ArrayList) property.getValue());
                }
//            else if( property instanceof QualitySettingProperty ) {
//                if( !pc.isDefined( property.getName() ) )
//                    pc.define( property.getName(), QualityPreset.class, (QualityPreset)property.getValue() );
//                else
//                    pc.set( property.getName(), (QualityPreset)property.getValue() );
            } else if (property instanceof ColorProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Color.class, (Color) property.getValue());
                } else {
                    pc.set(property.getName(), (Color) property.getValue());
                }
            } else {
                throw new UnsupportedOperationException("Type " + property.getName() + " not supported.");
            }
        }
    }
    
    public <T> void store(PropertyValue<T> p) {
        try {
            if (p.getName() != null && isDefined(p.getName())) {
                set(p.getName(), p.getValue());
            } else {
                System.out.println(LOC.getString(NOT_DEFINED) + ": " + p.getName());
            }
        } catch( Exception e ) {
            System.out.println( "ERROR STORING THIS" );
        }
    }


    /**
     * The instance of the single global instance of {@code PropertyContainer}. Initializes the global property
     * container lazily only if it is accessed.
     *
     * @see #getGlobal()
     */
    private static class GlobalInstanceHolder {
        private static final PropertyContainer INSTANCE = new PropertyContainer();
    }
}

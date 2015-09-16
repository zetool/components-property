package org.zetool.common.datastructure;

import ds.PropertyContainer;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestPropertyContainer {
    PropertyContainer pc;
    
    @Before
    public void initPropertyContainer() {
        pc = new PropertyContainer();
    }
    
    @Test
    public void testPropertyContainer() {
        assertThat(pc.size(), is(equalTo(0)));
        pc.define("some parameter", Boolean.class, false);
        assertThat(pc.size(), is(equalTo(1)));
        pc.define("some other parameter", Boolean.class, true);
        assertThat(pc.size(), is(equalTo(2)));
        assertThat(pc.getAs("some parameter", Boolean.class), is(equalTo(false)));
        assertThat(pc.getAs("some other parameter", Boolean.class), is(equalTo(true)));
    }
    
    @Test
    public void testContainment() {
        assertThat(pc.isDefined("test parameter"), is(false));
        pc.define("test parameter", Integer.class, 2);
        assertThat(pc.isDefined("test parameter"), is(true));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDoubleDefineFails() {
        pc.define("some parameter", Integer.class, 1);
        pc.define("some parameter", Integer.class, 2);
        
    }
    
    @Test
    public void typeStoredCorrect() {
        pc.define("double parameter", Double.class, 1.0);
        pc.define("float parameter", Float.class, 1.0f);
        assertThat(pc.getType("double parameter"), is(equalTo(Double.class)));
        assertThat(pc.getType("float parameter"), is(equalTo(Float.class)));
    }
    
    @Test
    public void testSetting() {
        pc.define("param", Integer.class, -1);
        pc.set("param", 1);
        assertThat(pc.getAs("param", Integer.class), is(equalTo(1)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSettingFailsForUndefined() {
        pc.set("not defined", 2);
    }
    
    @Test
    public void testSettingFailsWrongType() {
        pc.define("intparam", Integer.class, 1);
        try {
            pc.set("intparam", 3.4);
        } catch (IllegalArgumentException ex ) {
            assertThat(ex.getMessage(), containsString("java.lang.Integer"));
            return;
        }
        throw new AssertionError("Should throw " + IllegalAccessException.class);
    }
    
    @Test
    public void testGlobalPropertyContainer() {
        PropertyContainer pc1 = PropertyContainer.getGlobal();
        PropertyContainer pc2 = PropertyContainer.getGlobal();
        assertThat(pc1, is(notNullValue()));
        assertThat(pc1, is(CoreMatchers.sameInstance(pc2)));
    }
}

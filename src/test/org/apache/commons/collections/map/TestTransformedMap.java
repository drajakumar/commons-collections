/*
 *  Copyright 2003-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.commons.collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.collections.collection.TestTransformedCollection;

/**
 * Extension of {@link TestMap} for exercising the {@link TransformedMap}
 * implementation.
 *
 * @since Commons Collections 3.0
 * @version $Revision: 1.5 $ $Date: 2004/02/18 01:20:38 $
 * 
 * @author Stephen Colebourne
 */
public class TestTransformedMap extends AbstractTestMap {
    
    public TestTransformedMap(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestTransformedMap.class);
    }

    public static void main(String args[]) {
        String[] testCaseName = { TestTransformedMap.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    public Map makeEmptyMap() {
        return TransformedMap.decorate(new HashMap(), TestTransformedCollection.NOOP_TRANSFORMER, TestTransformedCollection.NOOP_TRANSFORMER);
    }

    public void testTransformedMap() {
        Object[] els = new Object[] {"1", "3", "5", "7", "2", "4", "6"};

        Map map = TransformedMap.decorate(new HashMap(), TestTransformedCollection.STRING_TO_INTEGER_TRANSFORMER, null);
        assertEquals(0, map.size());
        for (int i = 0; i < els.length; i++) {
            map.put(els[i], els[i]);
            assertEquals(i + 1, map.size());
            assertEquals(true, map.containsKey(new Integer((String) els[i])));
            assertEquals(false, map.containsKey(els[i]));
            assertEquals(true, map.containsValue(els[i]));
            assertEquals(els[i], map.get(new Integer((String) els[i])));
        }
        
        assertEquals(null, map.remove(els[0]));
        assertEquals(els[0], map.remove(new Integer((String) els[0])));
        
        map = TransformedMap.decorate(new HashMap(), null, TestTransformedCollection.STRING_TO_INTEGER_TRANSFORMER);
        assertEquals(0, map.size());
        for (int i = 0; i < els.length; i++) {
            map.put(els[i], els[i]);
            assertEquals(i + 1, map.size());
            assertEquals(true, map.containsValue(new Integer((String) els[i])));
            assertEquals(false, map.containsValue(els[i]));
            assertEquals(true, map.containsKey(els[i]));
            assertEquals(new Integer((String) els[i]), map.get(els[i]));
        }

        assertEquals(new Integer((String) els[0]), map.remove(els[0]));
        
        Set entrySet = map.entrySet();
        Map.Entry[] array = (Map.Entry[]) entrySet.toArray(new Map.Entry[0]);
        array[0].setValue("66");
        assertEquals(new Integer(66), array[0].getValue());
        assertEquals(new Integer(66), map.get(array[0].getKey()));
        
        Map.Entry entry = (Map.Entry) entrySet.iterator().next();
        entry.setValue("88");
        assertEquals(new Integer(88), entry.getValue());
        assertEquals(new Integer(88), map.get(entry.getKey()));
    }
}

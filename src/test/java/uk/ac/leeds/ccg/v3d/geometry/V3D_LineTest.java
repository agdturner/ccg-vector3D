/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v3d.geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_LineTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineTest extends V3D_Test {

    public V3D_LineTest() throws Exception {
        super(new V3D_Environment(new Generic_Environment(
                new Generic_Defaults())));
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of toString method, of class V3D_Line.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Line instance = getLine(P0P0P0, P1P0P0);
        String expResult = "V3D_Line(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=0, z=0))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Basic method to get a line defined by {@code p} and {@code q}.
     *
     * @param p A point.
     * @param q A point.
     * @return A line or null if the points {@code p} and {@code q} are
     * coincident.
     */
    public V3D_Line getLine(V3D_Point p, V3D_Point q) {
        try {
            return new V3D_Line(p, q);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = getLine(P0P0P0, P1P0P0);
        V3D_Line instance = getLine(P0P0P0, P1P0P0);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        instance = getLine(P0P0P0, N1P0P0);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        instance = getLine(P0P0P0, N1P1P0);
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersects method, of class V3D_Line.
     */
    @Test
    public void testIsOnLine() {
        System.out.println("isOnLine");
        V3D_Line instance = getLine(P0P0P0, P1P0P0);
        boolean expResult = true;
        boolean result = instance.getIntersects(N1P0P0);
        assertEquals(expResult, result);
        // Test 2
        instance = getLine(P0P0P0, P1P0P0);
        expResult = false;
        result = instance.getIntersects(N1P1P0);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Line.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true);  // No test!
    }

    /**
     * Test of getIntersects method, of class V3D_Line.
     */
    @Test
    public void testGetIntersects_V3D_Point() {
        System.out.println("getIntersects");
        V3D_Point pt = P0P0P0;
        V3D_Line instance = new V3D_Line(N1N1N1, P1P1P1);
        boolean expResult = true;
        boolean result = instance.getIntersects(pt);
        assertEquals(expResult, result);
    }

    /**
     * Test of isParallel method, of class V3D_Line.
     */
    @Test
    public void testIsParallel() throws Exception {
        System.out.println("isParallel");
        V3D_Line l = new V3D_Line(P0N1N1, P1P0P0);
        V3D_Line instance = getLine(N1N1N1, P1P1P1);
        boolean expResult = true;
        boolean result = instance.isParallel(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersects method, of class V3D_Line.
     */
    @Test
    public void testGetIntersects_V3D_Plane() {
        System.out.println("getIntersects");
//        V3D_Plane pl = null;
//        V3D_Line instance = null;
//        boolean expResult = false;
//        boolean result = instance.getIntersects(pl);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_Line.
     */
    @Test
    public void testGetIntersects_V3D_Line() {
        System.out.println("getIntersects");
        // If there is an intersection then it inersects!
        V3D_Line l = getLine(N1N1N1, P1P1P1);
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = getLine(P1N1N1, N1P1P1);
        boolean expResult = true;
        boolean result = instance.getIntersects(l, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetIntersection_3args() {
        System.out.println("getIntersection");
        V3D_Line l = getLine(N1N1N1, P1P1P1);
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = getLine(P1P1P0, new V3D_Point(e, P1, P1, P2));
        V3D_Geometry expResult = P1P1P1;
        V3D_Geometry result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 2
        expResult = P0P0P0;
        instance = getLine(N1N1P0, P1P1P0);
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 3
        l = getLine(N1N1N1, P1P1P1);
        scale = 100;
        rm = RoundingMode.HALF_UP;
        instance = getLine(new V3D_Point(e, P3, P1, P1), new V3D_Point(e, P1, P3, P3));
        expResult = new V3D_Point(e, P2, P2, P2);
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 4
        l = getLine(N1N1P0, P1P1P0);
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getLine(new V3D_Point(e, P3, P3, P0), new V3D_Point(e, P3, P3, N1));
        expResult = new V3D_Point(e, P3, P3, P0);
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 5
        l = getLine(N1N1N1, P1P1P1);
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getLine(P1N1N1, N1P1P1);
        expResult = P0P0P0;
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 6
        l = getLine(P0P0P0, P1P1P1);
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getLine(P1N1N1, N1P1P1);
        expResult = P0P0P0;
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 7
        l = getLine(N1N1N1, P0P0P0);
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getLine(P1N1N1, N1P1P1);
        expResult = P0P0P0;
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 8
        l = getLine(new V3D_Point(e, N2,N2,N2), N1N1N1);
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getLine(P1N1N1, P0P0P0);
        expResult = P0P0P0;
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);        
        // Test 9
        l = getLine(new V3D_Point(e, N2,N2,N2), N1N1N1);
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getLine(P0P0P0, N1P1P1);
        expResult = P0P0P0;
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
        // Test 10
        l = getLine(N1N1N1, P1P1P1);
        scale = 1;
        expResult = getLine(N1N1N1, P1P1P1);
        instance = getLine(new V3D_Point(e, N3,N3, N3), new V3D_Point(e, N4,N4, N4));
        result = instance.getIntersection(l, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetIntersection_V3D_Plane() {
        System.out.println("getIntersection");
//        V3D_Plane pl = null;
//        V3D_Line instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(pl);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

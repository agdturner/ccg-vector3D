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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Plane class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PlaneTest extends V3D_Test {

    public V3D_PlaneTest() {
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

    //@Test
    public void run() {
        testToString();
//        testIsOnPlane_V3D_Point();
//        testIsOnPlane_V3D_Point();
//        testIsOnPlane_V3D_LineSegment();
//        testEquals();
//        testIsIntersectedBy_V3D_Point();
//        testIsIntersectedBy_V3D_Line();
//        testIsIntersectedBy_V3D_Plane();
//        testGetNormalVector();
//        testIsParallel_V3D_Plane();
//        testIsParallel_V3D_Line();
//
//        testGetIntersection_V3D_Plane();
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -1;
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P1P1, P1P0P0, oom);
        String expResult = "V3D_Plane(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=1, z=1), r=V3D_Point(x=1, y=0, z=0))";
        String result = instance.toString();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        int oom = -1;
        V3D_Plane instance;
        String expResult;
        String result;
        // Test 1
        instance = new V3D_Plane(P0P0P0, P1P1P1, P1P0P0, oom);
        expResult = "0 * x + 1 * y + -1 * z + 0 = 0";
        result = instance.getEquation();
        assertTrue(expResult.equalsIgnoreCase(result));
        // Test 2
        instance = new V3D_Plane(P1N2P1, new V3D_Point(P4, N2, N2),
                new V3D_Point(P4, P1, P4), oom);
        expResult = "9 * x + -18 * y + 9 * z + -54 = 0";
        result = instance.getEquation();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment() {
        System.out.println("isOnPlane");
        int oom = -1;
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Plane instance = V3D_Environment.x0;
        assertFalse(instance.isOnPlane(l, oom));
        // Test 2
        instance = V3D_Environment.y0;
        assertTrue(instance.isOnPlane(l, oom));
        // Test 3
        instance = V3D_Environment.z0;
        assertTrue(instance.isOnPlane(l, oom));
        // Test 4
        l = new V3D_LineSegment(P0P0P0, P0P1P0, oom);
        instance = V3D_Environment.x0;
        assertTrue(instance.isOnPlane(l, oom));
        // Test 5
        instance = V3D_Environment.y0;
        assertFalse(instance.isOnPlane(l, oom));
        // Test 6
        instance = V3D_Environment.z0;
        assertTrue(instance.isOnPlane(l, oom));
        // Test 7
        l = new V3D_LineSegment(P0P0P0, P0P0P1, oom);
        instance = V3D_Environment.x0;
        assertTrue(instance.isOnPlane(l, oom));
        // Test 8
        instance = V3D_Environment.y0;
        assertTrue(instance.isOnPlane(l, oom));
        // Test 9
        instance = V3D_Environment.z0;
        assertFalse(instance.isOnPlane(l, oom));
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        int oom = -1;
        Object o = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0, oom);
        V3D_Plane instance = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0, oom);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Plane(P1P1P0, P1P1P1, P1P0P0, oom);
        assertFalse(instance.equals(o));
        // Test 3
        instance = new V3D_Plane(P1P1P1, P1P0P0, P0P1P0, oom);
        assertTrue(instance.equals(o));
        // Test 4
        instance = new V3D_Plane(P1P0P0, P0P1P0, P1P1P1, oom);
        assertTrue(instance.equals(o));
        // Test 5
        o = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0, oom);
        instance = new V3D_Plane(P0P0P0, P2P0P0, P0P2P0, oom);
        assertTrue(instance.equals(o));
        // Test 6
        instance = new V3D_Triangle(P0P0P0, P2P0P0, P0P2P0, oom);
        assertTrue(instance.equals(o));
        // Test 7
        o = new V3D_Line(P0P0P0, P1P0P0, oom);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    @Disabled
    public void testIsIntersectedBy_V3D_Plane() {
        System.out.println("intersects");
        int oom = -1;
        V3D_Plane pl;
        V3D_Plane instance;
        // x=1
        pl = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // y=1
        pl = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // z=1
        pl = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=0
        pl = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // y=0
        pl = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // z=0
        pl = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=-1
        pl = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // y=-1
        pl = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // z=-1
        pl = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=y
        pl = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=y+1
        pl = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=y-1
        pl = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=z
        pl = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=z+1
        pl = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // x=z-1
        pl = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        // y=z
        pl = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        // y=z+1
        pl = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
//        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
        assertFalse(instance.isIntersectedBy(pl, oom));
        // y=z-1
        pl = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom); // x=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom); // y=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom); // z=1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom); // x=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom); // y=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom); // z=-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom); // x=y
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom); // x=z
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom); // y=z
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom); // y=z+1
        assertFalse(instance.isIntersectedBy(pl, oom));
        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom); // y=z-1
//        assertTrue(instance.isIntersectedBy(pl, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("intersects");
        int oom = -1;
        V3D_Point pt;
        V3D_Plane instance;
        // x=0
        instance = new V3D_Plane(P0P0P0, P0P1P0, P0P0P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertTrue(instance.isIntersectedBy(P0P2P2, oom));
        assertTrue(instance.isIntersectedBy(P0P2P1, oom));
        assertTrue(instance.isIntersectedBy(P0P2P0, oom));
        assertTrue(instance.isIntersectedBy(P0P2N1, oom));
        assertTrue(instance.isIntersectedBy(P0P2N2, oom));
        assertTrue(instance.isIntersectedBy(P0P1P2, oom));
        assertTrue(instance.isIntersectedBy(P0P1P1, oom));
        assertTrue(instance.isIntersectedBy(P0P1P0, oom));
        assertTrue(instance.isIntersectedBy(P0P1N1, oom));
        assertTrue(instance.isIntersectedBy(P0P1N2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertTrue(instance.isIntersectedBy(P0P0N1, oom));
        assertTrue(instance.isIntersectedBy(P0P0N2, oom));
        assertTrue(instance.isIntersectedBy(P0N1P2, oom));
        assertTrue(instance.isIntersectedBy(P0N1P1, oom));
        assertTrue(instance.isIntersectedBy(P0N1P0, oom));
        assertTrue(instance.isIntersectedBy(P0N1N1, oom));
        assertTrue(instance.isIntersectedBy(P0N1N2, oom));
        assertTrue(instance.isIntersectedBy(P0N2P2, oom));
        assertTrue(instance.isIntersectedBy(P0N2P1, oom));
        assertTrue(instance.isIntersectedBy(P0N2P0, oom));
        assertTrue(instance.isIntersectedBy(P0N2N1, oom));
        assertTrue(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));

        // y=0
        instance = new V3D_Plane(P1P0P0, P0P0P0, P0P0P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertTrue(instance.isIntersectedBy(P2P0P2, oom));
        assertTrue(instance.isIntersectedBy(P2P0P1, oom));
        assertTrue(instance.isIntersectedBy(P2P0P0, oom));
        assertTrue(instance.isIntersectedBy(P2P0N1, oom));
        assertTrue(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertTrue(instance.isIntersectedBy(P1P0P2, oom));
        assertTrue(instance.isIntersectedBy(P1P0P1, oom));
        assertTrue(instance.isIntersectedBy(P1P0P0, oom));
        assertTrue(instance.isIntersectedBy(P1P0N1, oom));
        assertTrue(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertTrue(instance.isIntersectedBy(P0P0N1, oom));
        assertTrue(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertTrue(instance.isIntersectedBy(N1P0P2, oom));
        assertTrue(instance.isIntersectedBy(N1P0P1, oom));
        assertTrue(instance.isIntersectedBy(N1P0P0, oom));
        assertTrue(instance.isIntersectedBy(N1P0N1, oom));
        assertTrue(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertTrue(instance.isIntersectedBy(N2P0P2, oom));
        assertTrue(instance.isIntersectedBy(N2P0P1, oom));
        assertTrue(instance.isIntersectedBy(N2P0P0, oom));
        assertTrue(instance.isIntersectedBy(N2P0N1, oom));
        assertTrue(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));

        // z=0
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P0, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertTrue(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertTrue(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertTrue(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertTrue(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertTrue(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertTrue(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertTrue(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertTrue(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertTrue(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertTrue(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertTrue(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertTrue(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertTrue(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertTrue(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertTrue(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertTrue(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertTrue(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertTrue(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertTrue(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertTrue(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertTrue(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertTrue(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertTrue(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertTrue(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));

        // x=y
        instance = new V3D_Plane(P0P0P0, P1P1P0, P0P0P1, oom);
        // P2
        assertTrue(instance.isIntersectedBy(P2P2P2, oom));
        assertTrue(instance.isIntersectedBy(P2P2P1, oom));
        assertTrue(instance.isIntersectedBy(P2P2P0, oom));
        assertTrue(instance.isIntersectedBy(P2P2N1, oom));
        assertTrue(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertTrue(instance.isIntersectedBy(P1P1P2, oom));
        assertTrue(instance.isIntersectedBy(P1P1P1, oom));
        assertTrue(instance.isIntersectedBy(P1P1P0, oom));
        assertTrue(instance.isIntersectedBy(P1P1N1, oom));
        assertTrue(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertTrue(instance.isIntersectedBy(P0P0N1, oom));
        assertTrue(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertTrue(instance.isIntersectedBy(N1N1P2, oom));
        assertTrue(instance.isIntersectedBy(N1N1P1, oom));
        assertTrue(instance.isIntersectedBy(N1N1P0, oom));
        assertTrue(instance.isIntersectedBy(N1N1N1, oom));
        assertTrue(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertTrue(instance.isIntersectedBy(N2N2P2, oom));
        assertTrue(instance.isIntersectedBy(N2N2P1, oom));
        assertTrue(instance.isIntersectedBy(N2N2P0, oom));
        assertTrue(instance.isIntersectedBy(N2N2N1, oom));
        assertTrue(instance.isIntersectedBy(N2N2N2, oom));

        // x=-y
        instance = new V3D_Plane(P0P0P0, N1P1P0, P0P0P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertTrue(instance.isIntersectedBy(P2N2P2, oom));
        assertTrue(instance.isIntersectedBy(P2N2P1, oom));
        assertTrue(instance.isIntersectedBy(P2N2P0, oom));
        assertTrue(instance.isIntersectedBy(P2N2N1, oom));
        assertTrue(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertTrue(instance.isIntersectedBy(P1N1P2, oom));
        assertTrue(instance.isIntersectedBy(P1N1P1, oom));
        assertTrue(instance.isIntersectedBy(P1N1P0, oom));
        assertTrue(instance.isIntersectedBy(P1N1N1, oom));
        assertTrue(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P2, oom));
        assertTrue(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertTrue(instance.isIntersectedBy(P0P0N1, oom));
        assertTrue(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertTrue(instance.isIntersectedBy(N1P1P2, oom));
        assertTrue(instance.isIntersectedBy(N1P1P1, oom));
        assertTrue(instance.isIntersectedBy(N1P1P0, oom));
        assertTrue(instance.isIntersectedBy(N1P1N1, oom));
        assertTrue(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertTrue(instance.isIntersectedBy(N2P2P2, oom));
        assertTrue(instance.isIntersectedBy(N2P2P1, oom));
        assertTrue(instance.isIntersectedBy(N2P2P0, oom));
        assertTrue(instance.isIntersectedBy(N2P2N1, oom));
        assertTrue(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));

        // x=z
        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom);
        // P2
        assertTrue(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertTrue(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertTrue(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertTrue(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertTrue(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertTrue(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertTrue(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertTrue(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertTrue(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertTrue(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertTrue(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertTrue(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertTrue(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertTrue(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertTrue(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertTrue(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertTrue(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertTrue(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertTrue(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertTrue(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertTrue(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertTrue(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertTrue(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertTrue(instance.isIntersectedBy(N2N2N2, oom));

        // x=-z
        instance = new V3D_Plane(P0P0P0, P0P1P0, N1P0P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertTrue(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertTrue(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertTrue(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertTrue(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertTrue(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertTrue(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertTrue(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertTrue(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertTrue(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertTrue(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertTrue(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertTrue(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertTrue(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertTrue(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertTrue(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertTrue(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertTrue(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertTrue(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertTrue(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertTrue(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertTrue(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertTrue(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertTrue(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertTrue(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));

        // y=z
        instance = new V3D_Plane(P1P0P0, P0P0P0, P0P1P1, oom);
        // P2
        assertTrue(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertTrue(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertTrue(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertTrue(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertTrue(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertTrue(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertTrue(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertTrue(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertTrue(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertTrue(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertTrue(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertTrue(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertTrue(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertTrue(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertTrue(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertTrue(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertTrue(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertTrue(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertTrue(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertTrue(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertTrue(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertTrue(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertTrue(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertTrue(instance.isIntersectedBy(N2N2N2, oom));

        // x=-z
        instance = new V3D_Plane(P1P0P0, P0P0P0, P0N1P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertTrue(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertTrue(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertTrue(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertTrue(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertTrue(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertTrue(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertTrue(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertTrue(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertTrue(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertTrue(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertTrue(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertTrue(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertTrue(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertTrue(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertTrue(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertTrue(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertTrue(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertTrue(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertTrue(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertTrue(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertTrue(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertTrue(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertTrue(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertTrue(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));
        // x=y-z
        instance = new V3D_Plane(P0P0P0, P1P1P0, N1P0P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertTrue(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertTrue(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertTrue(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertTrue(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertTrue(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertTrue(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertTrue(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertTrue(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertTrue(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertTrue(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertTrue(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertTrue(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertTrue(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertFalse(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertTrue(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertTrue(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertTrue(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertFalse(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertTrue(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertTrue(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));
        // x=z-y
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P0, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertFalse(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertTrue(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertTrue(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertTrue(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertFalse(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertTrue(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertFalse(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertTrue(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertTrue(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertTrue(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertTrue(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertFalse(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertTrue(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertFalse(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertTrue(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertTrue(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertTrue(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertTrue(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertFalse(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertTrue(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertFalse(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertTrue(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertTrue(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertTrue(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertTrue(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertFalse(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertFalse(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));
        // y=x-z
        instance = new V3D_Plane(P1P1P0, P0P0P0, P0N1P1, oom);
        // P2
        assertFalse(instance.isIntersectedBy(P2P2P2, oom));
        assertFalse(instance.isIntersectedBy(P2P2P1, oom));
        assertTrue(instance.isIntersectedBy(P2P2P0, oom));
        assertFalse(instance.isIntersectedBy(P2P2N1, oom));
        assertFalse(instance.isIntersectedBy(P2P2N2, oom));
        assertFalse(instance.isIntersectedBy(P2P1P2, oom));
        assertTrue(instance.isIntersectedBy(P2P1P1, oom));
        assertFalse(instance.isIntersectedBy(P2P1P0, oom));
        assertFalse(instance.isIntersectedBy(P2P1N1, oom));
        assertFalse(instance.isIntersectedBy(P2P1N2, oom));
        assertTrue(instance.isIntersectedBy(P2P0P2, oom));
        assertFalse(instance.isIntersectedBy(P2P0P1, oom));
        assertFalse(instance.isIntersectedBy(P2P0P0, oom));
        assertFalse(instance.isIntersectedBy(P2P0N1, oom));
        assertFalse(instance.isIntersectedBy(P2P0N2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P2, oom));
        assertFalse(instance.isIntersectedBy(P2N1P1, oom));
        assertFalse(instance.isIntersectedBy(P2N1P0, oom));
        assertFalse(instance.isIntersectedBy(P2N1N1, oom));
        assertFalse(instance.isIntersectedBy(P2N1N2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P2, oom));
        assertFalse(instance.isIntersectedBy(P2N2P1, oom));
        assertFalse(instance.isIntersectedBy(P2N2P0, oom));
        assertFalse(instance.isIntersectedBy(P2N2N1, oom));
        assertFalse(instance.isIntersectedBy(P2N2N2, oom));
        // P1
        assertFalse(instance.isIntersectedBy(P1P2P2, oom));
        assertFalse(instance.isIntersectedBy(P1P2P1, oom));
        assertFalse(instance.isIntersectedBy(P1P2P0, oom));
        assertTrue(instance.isIntersectedBy(P1P2N1, oom));
        assertFalse(instance.isIntersectedBy(P1P2N2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P2, oom));
        assertFalse(instance.isIntersectedBy(P1P1P1, oom));
        assertTrue(instance.isIntersectedBy(P1P1P0, oom));
        assertFalse(instance.isIntersectedBy(P1P1N1, oom));
        assertFalse(instance.isIntersectedBy(P1P1N2, oom));
        assertFalse(instance.isIntersectedBy(P1P0P2, oom));
        assertTrue(instance.isIntersectedBy(P1P0P1, oom));
        assertFalse(instance.isIntersectedBy(P1P0P0, oom));
        assertFalse(instance.isIntersectedBy(P1P0N1, oom));
        assertFalse(instance.isIntersectedBy(P1P0N2, oom));
        assertTrue(instance.isIntersectedBy(P1N1P2, oom));
        assertFalse(instance.isIntersectedBy(P1N1P1, oom));
        assertFalse(instance.isIntersectedBy(P1N1P0, oom));
        assertFalse(instance.isIntersectedBy(P1N1N1, oom));
        assertFalse(instance.isIntersectedBy(P1N1N2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P2, oom));
        assertFalse(instance.isIntersectedBy(P1N2P1, oom));
        assertFalse(instance.isIntersectedBy(P1N2P0, oom));
        assertFalse(instance.isIntersectedBy(P1N2N1, oom));
        assertFalse(instance.isIntersectedBy(P1N2N2, oom));
        // P0
        assertFalse(instance.isIntersectedBy(P0P2P2, oom));
        assertFalse(instance.isIntersectedBy(P0P2P1, oom));
        assertFalse(instance.isIntersectedBy(P0P2P0, oom));
        assertFalse(instance.isIntersectedBy(P0P2N1, oom));
        assertTrue(instance.isIntersectedBy(P0P2N2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P2, oom));
        assertFalse(instance.isIntersectedBy(P0P1P1, oom));
        assertFalse(instance.isIntersectedBy(P0P1P0, oom));
        assertTrue(instance.isIntersectedBy(P0P1N1, oom));
        assertFalse(instance.isIntersectedBy(P0P1N2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P2, oom));
        assertFalse(instance.isIntersectedBy(P0P0P1, oom));
        assertTrue(instance.isIntersectedBy(P0P0P0, oom));
        assertFalse(instance.isIntersectedBy(P0P0N1, oom));
        assertFalse(instance.isIntersectedBy(P0P0N2, oom));
        assertFalse(instance.isIntersectedBy(P0N1P2, oom));
        assertTrue(instance.isIntersectedBy(P0N1P1, oom));
        assertFalse(instance.isIntersectedBy(P0N1P0, oom));
        assertFalse(instance.isIntersectedBy(P0N1N1, oom));
        assertFalse(instance.isIntersectedBy(P0N1N2, oom));
        assertTrue(instance.isIntersectedBy(P0N2P2, oom));
        assertFalse(instance.isIntersectedBy(P0N2P1, oom));
        assertFalse(instance.isIntersectedBy(P0N2P0, oom));
        assertFalse(instance.isIntersectedBy(P0N2N1, oom));
        assertFalse(instance.isIntersectedBy(P0N2N2, oom));
        // N1
        assertFalse(instance.isIntersectedBy(N1P2P2, oom));
        assertFalse(instance.isIntersectedBy(N1P2P1, oom));
        assertFalse(instance.isIntersectedBy(N1P2P0, oom));
        assertFalse(instance.isIntersectedBy(N1P2N1, oom));
        assertFalse(instance.isIntersectedBy(N1P2N2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P2, oom));
        assertFalse(instance.isIntersectedBy(N1P1P1, oom));
        assertFalse(instance.isIntersectedBy(N1P1P0, oom));
        assertFalse(instance.isIntersectedBy(N1P1N1, oom));
        assertTrue(instance.isIntersectedBy(N1P1N2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P2, oom));
        assertFalse(instance.isIntersectedBy(N1P0P1, oom));
        assertFalse(instance.isIntersectedBy(N1P0P0, oom));
        assertTrue(instance.isIntersectedBy(N1P0N1, oom));
        assertFalse(instance.isIntersectedBy(N1P0N2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P2, oom));
        assertFalse(instance.isIntersectedBy(N1N1P1, oom));
        assertTrue(instance.isIntersectedBy(N1N1P0, oom));
        assertFalse(instance.isIntersectedBy(N1N1N1, oom));
        assertFalse(instance.isIntersectedBy(N1N1N2, oom));
        assertFalse(instance.isIntersectedBy(N1N2P2, oom));
        assertTrue(instance.isIntersectedBy(N1N2P1, oom));
        assertFalse(instance.isIntersectedBy(N1N2P0, oom));
        assertFalse(instance.isIntersectedBy(N1N2N1, oom));
        assertFalse(instance.isIntersectedBy(N1N2N2, oom));
        // N2
        assertFalse(instance.isIntersectedBy(N2P2P2, oom));
        assertFalse(instance.isIntersectedBy(N2P2P1, oom));
        assertFalse(instance.isIntersectedBy(N2P2P0, oom));
        assertFalse(instance.isIntersectedBy(N2P2N1, oom));
        assertFalse(instance.isIntersectedBy(N2P2N2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P2, oom));
        assertFalse(instance.isIntersectedBy(N2P1P1, oom));
        assertFalse(instance.isIntersectedBy(N2P1P0, oom));
        assertFalse(instance.isIntersectedBy(N2P1N1, oom));
        assertFalse(instance.isIntersectedBy(N2P1N2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P2, oom));
        assertFalse(instance.isIntersectedBy(N2P0P1, oom));
        assertFalse(instance.isIntersectedBy(N2P0P0, oom));
        assertFalse(instance.isIntersectedBy(N2P0N1, oom));
        assertTrue(instance.isIntersectedBy(N2P0N2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P2, oom));
        assertFalse(instance.isIntersectedBy(N2N1P1, oom));
        assertFalse(instance.isIntersectedBy(N2N1P0, oom));
        assertTrue(instance.isIntersectedBy(N2N1N1, oom));
        assertFalse(instance.isIntersectedBy(N2N1N2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P2, oom));
        assertFalse(instance.isIntersectedBy(N2N2P1, oom));
        assertTrue(instance.isIntersectedBy(N2N2P0, oom));
        assertFalse(instance.isIntersectedBy(N2N2N1, oom));
        assertFalse(instance.isIntersectedBy(N2N2N2, oom));
    }

    /**
     * Test of hashCode method, of class V3D_Plane.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true); // Not really a test - method does not need testing.
    }

    /**
     * Test of getNormalVector method, of class V3D_Plane.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        int oom = -1;
        // Z = 0
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0, oom);
        V3D_Vector expResult = new V3D_Vector(P0P0P1, oom);
        V3D_Vector result = instance.n;
        assertTrue(expResult.equals(result));
        // Z = -1
        instance = new V3D_Plane(P0P0N1, P1P0N1, P0P1N1, oom);
        expResult = new V3D_Vector(P0P0P1, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(P0P0P1, P1P0P1, P0P1P1, oom);
        expResult = new V3D_Vector(P0P0P1, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom);
        expResult = new V3D_Vector(P0P0P1, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(P0P1P1, P0P0P1, P1P0P1, oom);
        //expResult = new V3D_Vector(P0P0N1, oom);
        expResult = new V3D_Vector(P0P0P1, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane(P0P0P0, P0P1P0, P0P0N1, oom);
        //expResult = new V3D_Vector(P1P0P0, oom);
        expResult = new V3D_Vector(N1P0P0, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane(P0P0P0, P1P0P0, P0P0N1, oom);
        //expResult = new V3D_Vector(P0N1P0, oom);
        expResult = new V3D_Vector(P0P1P0, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane(P0P0P0, P1P0P0, N1P0P1, oom);
        expResult = new V3D_Vector(P0N1P0, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0, oom);
        //expResult = new V3D_Vector(N1N1P1, oom);
        expResult = new V3D_Vector(P1P1N1, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane(P0P0P0, P0P1P1, P0N1P0, oom);
        //expResult = new V3D_Vector(N1P0P0, oom);
        expResult = new V3D_Vector(P1P0P0, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane(P0P0P0, P1P1P1, P0N1N1, oom);
        //expResult = new V3D_Vector(P0N1P1, oom);
        expResult = new V3D_Vector(P0P1N1, oom);
        result = instance.n;
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("intersects");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P0P1P0, oom); // Y axis
        V3D_Plane instance = new V3D_Plane(P1P0P1, P0P0P1, N1P0N1, oom); // Y=0 plane
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 2
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1, oom); // z=-1 plane
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 2
        l = new V3D_Line(P0P0P0, P1P1P1, oom);
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1, oom); // z=-2 plane
        assertTrue(instance.isIntersectedBy(l, oom));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Plane() {
        System.out.println("isParallel");
        int oom = -1;
        V3D_Plane p = new V3D_Plane(P1P1P0, P1N1P0, N1P1P0, oom);
        V3D_Plane instance = new V3D_Plane(P1P1P1, P1N1P1, N1P1P1, oom);
        assertTrue(instance.isParallel(p, oom));
        // Test 2
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1, oom);
        assertTrue(instance.isParallel(p, oom));
        // Test 3
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1, oom);
        assertTrue(instance.isParallel(p, oom));
        // Test 4
        p = V3D_Environment.x0;
        instance = V3D_Environment.y0;
        assertFalse(instance.isParallel(p, oom));
        // Test 5
        p = V3D_Environment.x0;
        instance = V3D_Environment.z0;
        assertFalse(instance.isParallel(p, oom));
        // Test 6
        p = V3D_Environment.y0;
        instance = V3D_Environment.z0;
        assertFalse(instance.isParallel(p, oom));
        // Test 7
        p = new V3D_Plane(P0P0P0, P0P1P0, P1P1P1, oom);
        instance = new V3D_Plane(P1P0P0, P1P1P0, new V3D_Point(P2, P1, P1), oom);
        assertTrue(instance.isParallel(p, oom));
        // Test 8
        instance = new V3D_Plane(P1N1P0, P1P0P0, new V3D_Point(P2, P0, P1), oom);
        assertTrue(instance.isParallel(p, oom));
        // Test 9
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P1P1, oom);
        assertFalse(instance.isParallel(p, oom));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Line() {
        System.out.println("isParallel");
        V3D_Line l = V3D_Environment.xAxis;
        V3D_Plane instance = V3D_Environment.y0;
        assertTrue(instance.isParallel(l));
        // Test 2
        instance = V3D_Environment.z0;
        assertTrue(instance.isParallel(l));
        // Test 3
        instance = V3D_Environment.x0;
        assertFalse(instance.isParallel(l));
        // Test 4
        l = V3D_Environment.yAxis;
        instance = V3D_Environment.x0;
        assertTrue(instance.isParallel(l));
        // Test 5
        instance = V3D_Environment.y0;
        assertFalse(instance.isParallel(l));
        // Test 6
        instance = V3D_Environment.z0;
        assertTrue(instance.isParallel(l));
        // Test 7
        l = V3D_Environment.zAxis;
        instance = V3D_Environment.x0;
        assertTrue(instance.isParallel(l));
        // Test 8
        instance = V3D_Environment.y0;
        assertTrue(instance.isParallel(l));
        // Test 9
        instance = V3D_Environment.z0;
        assertFalse(instance.isParallel(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    @Disabled
    public void testGetIntersection_V3D_Plane() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Plane pl;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;

        // Two Planar patches
        pl = new V3D_Plane(new V3D_Point(
                Math_BigRational.valueOf(8).divide(3),
                Math_BigRational.valueOf(-2).divide(3), Math_BigRational.ZERO),
                new V3D_Vector(2, 8, 0, oom));
        instance = new V3D_Plane(new V3D_Point(
                Math_BigRational.valueOf(8).divide(3),
                        Math_BigRational.ZERO,
                        Math_BigRational.valueOf(-2).divide(3)),
                new V3D_Vector(2, 0, 8, oom));
        expResult = new V3D_Line(new V3D_Point(
                Math_BigRational.valueOf(68).divide(27),
                        Math_BigRational.valueOf(-17).divide(27),
                        Math_BigRational.valueOf(-17).divide(27)),
                new V3D_Vector(
                        Math_BigRational.valueOf(4).divide(16),
                        Math_BigRational.valueOf(-1).divide(16),
                        Math_BigRational.valueOf(-1).divide(16), oom));
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));

        // Simple
        pl = new V3D_Plane(new V3D_Point(7, 11, 0),
                new V3D_Vector(0, 0, 3, oom));
        instance = new V3D_Plane(new V3D_Point(1, 0, 0),
                new V3D_Vector(5, 5, 0, oom));
        expResult = new V3D_Line(new V3D_Point(0.5d, 0.5d, 0),
                new V3D_Vector(-15, 15, 0, oom));
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));

        // Test V3D_Environment.x0
        pl = V3D_Environment.x0;
        // Test 1 
        instance = V3D_Environment.x0;
        expResult = V3D_Environment.x0;
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = V3D_Environment.y0;
        expResult = V3D_Environment.zAxis;
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = V3D_Environment.z0;
        expResult = V3D_Environment.yAxis;
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1, oom); // y=1
        expResult = new V3D_Line(P0P1P0, P0P1P1, oom);    // x=0, y=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 5
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1, oom); // z=1
        expResult = new V3D_Line(P0P1P1, P0P0P1, oom);    // x=0, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 6 to 9
        pl = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        // Test 6
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        expResult = new V3D_Line(P0P0N1, P0P0P1, oom);          // x=0, y=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 7
        instance = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        expResult = new V3D_Line(P0P0P0, P1P0P0, oom);          // y=0, z=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 8
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom);       // x=1
        expResult = new V3D_Line(P1P0N1, P1P0P1, oom);          // x=1, y=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 9
        instance = new V3D_Plane(P0P1P1, P1P1P1, P0P0P1, oom);       // z=1
        expResult = new V3D_Line(P0P0P1, P1P0P1, oom);          // y=0, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 10 to 13
        pl = new V3D_Plane(V3D_Environment.xAxis.q, V3D_Environment.yAxis.q, P0P0P0, oom); // z=0
        // Test 10
        instance = new V3D_Plane(P0P0P0, V3D_Environment.yAxis.q, V3D_Environment.zAxis.q, oom); // x=0
        expResult = new V3D_Line(P0N1P0, P0P1P0, oom);          // x=0, z=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 11
        instance = new V3D_Plane(V3D_Environment.xAxis.q, P0P0P0, V3D_Environment.zAxis.q, oom); // y=0
        expResult = new V3D_Line(N1P0P0, P1P0P0, oom);          // y=0, z=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 12
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1, oom); // x=1
        expResult = new V3D_Line(P1N1P0, P1P1P0, oom);    // x=1, z=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 13
        instance = new V3D_Plane(P1P1P1, P0P1P0, P1P1P0, oom); // y=1
        expResult = new V3D_Line(N1P1P0, P1P1P0, oom);    // y=1, z=0
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 14 to 15
        pl = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1, oom); // x=1
        // Test 14
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1, oom); // y=1
        expResult = new V3D_Line(P1P1P0, P1P1P1, oom);    // x=1, y=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 15
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1, oom); // z=1
        expResult = new V3D_Line(P1P1P1, P1P0P1, oom);    // x=1, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 16 to 17
        pl = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1, oom); // y=1
        // Test 16
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1, oom); // x=1
        expResult = new V3D_Line(P1P1P0, P1P1P1, oom);    // x=1, y=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 17
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1, oom); // z=1
        expResult = new V3D_Line(P1P1P1, P0P1P1, oom);    // y=1, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 18 to 19
        pl = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1, oom); // z=1
        // Test 18
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1, oom); // x=1
        expResult = new V3D_Line(P1P0P1, P1P1P1, oom);    // x=1, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 19
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1, oom); // y=1
        expResult = new V3D_Line(P1P1P1, P0P1P1, oom);    // y=1, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 20 to 21
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1, oom); // x=-1
        // Test 20
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1, oom); // y=1
        expResult = new V3D_Line(N1P1P0, N1P1P1, oom);    // x=-1, y=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 21
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1, oom); // z=1
        expResult = new V3D_Line(N1P1P1, N1P0P1, oom);    // x=-1, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 22 to 23
        pl = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1, oom); // y=-1
        // Test 22
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1, oom); // x=1
        expResult = new V3D_Line(P1N1P0, P1N1P1, oom);    // x=1, y=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 23
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1, oom); // z=1
        expResult = new V3D_Line(P1N1P1, P0N1P1, oom);    // y=-1, z=1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 24 to 25
        pl = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1, oom); // z=-1
        // Test 24
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1, oom); // x=1
        expResult = new V3D_Line(P1P0N1, P1P1N1, oom);    // x=1, z=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 25
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1, oom); // y=1
        expResult = new V3D_Line(P1P1N1, P0P1N1, oom);    // y=1, z=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 26 to 27
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1, oom); // x=-1
        // Test 26
        instance = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1, oom); // y=-1
        expResult = new V3D_Line(N1N1P0, N1N1P1, oom);    // x=-1, y=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 27
        instance = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1, oom); // z=-1
        expResult = new V3D_Line(N1P1N1, N1P0N1, oom);    // x=-1, z=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 28 to 29
        pl = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1, oom); // y=-1
        // Test 28
        instance = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1, oom); // x=-1
        expResult = new V3D_Line(N1N1P0, N1N1P1, oom);    // x=-1, y=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 29
        instance = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1, oom); // z=-1
        expResult = new V3D_Line(P1N1N1, P0N1N1, oom);    // y=-1, z=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 30 to 31
        pl = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1, oom); // z=-1
        // Test 30
        instance = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1, oom); // x=-1
        expResult = new V3D_Line(N1P0N1, N1P1N1, oom);    // x=-1, z=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
        // Test 31
        instance = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1, oom); // y=-1
        expResult = new V3D_Line(P1N1N1, P0N1N1, oom);    // y=-1, z=-1
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));

        // Test 32 to ?
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1, oom); // x=-1
        // Test 32
        instance = new V3D_Plane(new V3D_Point(N1, N2, N1),
                new V3D_Point(P0, N2, P0),
                new V3D_Point(P1, N2, N1), oom); // y=-2
        expResult = new V3D_Line(new V3D_Point(N1, N2, P0),
                new V3D_Point(N1, N2, P1), oom);    // x=-1, y=-2
        result = instance.getIntersection(pl, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l = V3D_Environment.xAxis;
        instance = V3D_Environment.x0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l = V3D_Environment.yAxis;
        instance = V3D_Environment.y0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        l = V3D_Environment.zAxis;
        instance = V3D_Environment.z0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l = V3D_Environment.xAxis;
        instance = V3D_Environment.x0.apply(new V3D_Vector(P1, P0, P0, oom));
        expResult = P1P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 5
        l = V3D_Environment.yAxis;
        instance = V3D_Environment.y0.apply(new V3D_Vector(P0, P1, P0, oom));
        expResult = P0P1P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 6
        l = V3D_Environment.zAxis;
        instance = V3D_Environment.z0.apply(new V3D_Vector(P0, P0, P1, oom));
        expResult = P0P0P1;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1), oom);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(P0P0N1, new V3D_Point(P0, P4, P0),
                new V3D_Point(P2, P0, P0), oom);
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1), oom);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, N1),
                new V3D_Point(P0, P4, P0), new V3D_Point(P2, P0, P0), oom);
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_Line(new V3D_Point(P0, P0, P0), new V3D_Point(P0, P0, P1), oom);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, P2),
                new V3D_Point(P1, P0, P2), new V3D_Point(P0, P1, P2), oom);
        expResult = new V3D_Point(P0, P0, P2);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of apply method, of class V3D_Plane.
     */
    @Test
    @Disabled
    public void testApply() {
        System.out.println("apply");
        int oom = -1;
        V3D_Vector v = new V3D_Vector(1, 0, 0, oom);
        V3D_Plane instance = V3D_Environment.x0;
        V3D_Plane expResult = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom);
        V3D_Plane result = instance.apply(v);
        assertTrue(expResult.equals(result));
        // Test 2
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0, oom);
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P0P0, P1P1P0, oom);
        assertTrue(instance.isOnPlane(l, oom));
        // Test 2
        l = new V3D_Line(P0P0P0, P1P1P0, oom);
        assertTrue(instance.isOnPlane(l, oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    @Disabled
    public void testGetIntersection_V3D_Plane_V3D_Line() {
        System.out.println("getIntersection");
        // No test - These tests are covered by testGetIntersection_V3D_Line.
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = null;
        boolean flag = false;
        V3D_Plane instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result;
        // Test 1-3 part of axis with orthoganol plane through origin.
        // Test 1
        l = new V3D_LineSegment(N1P0P0, P1P0P0, oom);
        instance = V3D_Environment.x0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V3D_LineSegment(P0N1P0, P0P1P0, oom);
        instance = V3D_Environment.y0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 3
        l = new V3D_LineSegment(P0P0N1, P0P0P1, oom);
        instance = V3D_Environment.z0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 4-6 part of axis with orthoganol plane not through origin.
        // Test 4
        l = new V3D_LineSegment(N1P0P0, P1P0P0, oom);
        instance = V3D_Environment.x0.apply(new V3D_Vector(P1, P0, P0, oom));
        expResult = P1P0P0;
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(P0N1P0, P0P1P0, oom);
        instance = V3D_Environment.y0.apply(new V3D_Vector(P0, P1, P0, oom));
        expResult = P0P1P0;
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 6
        l = new V3D_LineSegment(P0P0N1, P0P0P1, oom);
        instance = V3D_Environment.z0.apply(new V3D_Vector(P0, P0, P1, oom));
        expResult = P0P0P1;
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1), oom);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(P0P0N1, new V3D_Point(P0, P4, P0),
                new V3D_Point(P2, P0, P0), oom);
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l, oom, flag);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P2, P8, P2), oom);
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1), oom);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, N1),
                new V3D_Point(P0, P4, P0), new V3D_Point(P2, P0, P0), oom);
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l, oom, flag);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P2, P8, P2), oom);
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineSegment(new V3D_Point(P0, P0, P0), new V3D_Point(P0, P0, P1), oom);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, P2),
                new V3D_Point(P1, P0, P2), new V3D_Point(P0, P1, P2), oom);
        expResult = new V3D_Point(P0, P0, P2);
        result = instance.getIntersection(l, oom, flag);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(new V3D_Point(P0, P0, P0), new V3D_Point(P0, P0, P4), oom);
        result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));

    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Plane.
     */
    @Test
    @Disabled
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test.
    }

    /**
     * Test of getAsMatrix method, of class V3D_Plane.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Plane instance = V3D_Environment.x0;
        Math_BigRational[][] m = new Math_BigRational[3][3];
        m[0][0] = Math_BigRational.ZERO;
        m[0][1] = Math_BigRational.ZERO;
        m[0][2] = Math_BigRational.ZERO;
        m[1][0] = Math_BigRational.ZERO;
        m[1][1] = Math_BigRational.ONE;
        m[1][2] = Math_BigRational.ZERO;
        m[2][0] = Math_BigRational.ZERO;
        m[2][1] = Math_BigRational.ZERO;
        m[2][2] = Math_BigRational.ONE;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix();
        assertTrue(expResult.equals(result));
        // Test 2
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        int oom = -2;
        V3D_Plane p = V3D_Environment.x0;
        V3D_Plane instance = V3D_Environment.x0;
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        V3D_Vector v = V3D_Environment.i;
        instance = V3D_Environment.x0.apply(v);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = V3D_Environment.x0.apply(v);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals_V3D_Plane() {
        System.out.println("equals");
        int oom = -1;
        V3D_Plane p;
        V3D_Plane instance;
        // Test 1
        p = V3D_Environment.x0;
        instance = V3D_Environment.x0;
        assertTrue(p.equals(instance, oom));
        // Test 2
        p = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0, oom);
        instance = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0, oom);
        assertTrue(instance.equals(p, oom));
        instance = new V3D_Plane(P1P1P0, P1P1P1, P1P0P0, oom);
        assertFalse(instance.equals(p, oom));
        instance = new V3D_Plane(P1P1P1, P1P0P0, P0P1P0, oom);
        assertTrue(instance.equals(p, oom));
        instance = new V3D_Plane(P1P0P0, P0P1P0, P1P1P1, oom);
        assertTrue(instance.equals(p, oom));
        // Test 3
        p = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0, oom);
        instance = new V3D_Plane(P0P0P0, P2P0P0, P0P2P0, oom);
        assertTrue(instance.equals(p, oom));
        // Test 6
        instance = new V3D_Triangle(P0P0P0, P2P0P0, P0P2P0, oom);
        assertTrue(instance.equals(p, oom));
    }

    /**
     * Test of isCoincident method, of class V3D_Plane.
     */
    @Test
    @Disabled
    public void testIsCoincident() {
        // No test as covered by other tests.
    }

    /**
     * Test of getDistance method, of class V3D_Plane.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        V3D_Point p = new V3D_Point(P5, P0, P0);
        int oom = 0;
        V3D_Plane instance = new V3D_Plane(P0P0P0, P0P0P1, P0P1P1, oom);
        BigDecimal expResult = BigDecimal.valueOf(5);
        BigDecimal result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        p = new V3D_Point(P5, P10, P0);
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }
}

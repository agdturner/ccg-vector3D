/*
 * Copyright 2021 Centre for Computational Geography.
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test class for V3D_Ray.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_RayTest extends V3D_Test {

    public V3D_RayTest() {
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
     * Test of equals method, of class V3D_Ray.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new V3D_Ray(pP0P0P0, pP1P1P1);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Ray(pP1P1P1, pP2P2P2);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of equals method, of class V3D_Ray.
     */
    @Test
    public void testEquals_V3D_Ray_int() {
        System.out.println("equals");
        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P1P1);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(r, e.oom));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP2P2P2);
        assertTrue(instance.equals(r, e.oom));
        // Test 2
        instance = new V3D_Ray(pP1P1P1, pP2P2P2);
        assertFalse(instance.equals(r, e.oom));
    }

    /**
     * Test of hashCode method, of class V3D_Ray.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P1P1);
        int result = r.hashCode();
        int expResult = 4801333;
        //System.out.println(result);
        assertTrue(result == expResult);
    }

    /**
     * Test of apply method, of class V3D_Ray.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = pP0P0P0;
        V3D_Ray instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 2
        pt = pP1P1P1;
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 3
        pt = pN2N2N2;
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 4
        pt = pP1P0P0;
        instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 5
        pt = pP2P0P0;
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 6
        pt = pN2P0P0;
        assertFalse(instance.isIntersectedBy(pt, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Ray_boolean() {
        System.out.println("isIntersectedBy");
        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P0P0);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(r, e.oom));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(r, e.oom));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0);
        assertFalse(instance.isIntersectedBy(r, e.oom));
        // Test 4
        r = new V3D_Ray(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        instance.getIntersection(r, e.oom);
        assertTrue(instance.isIntersectedBy(r, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        instance.getIntersection(l, e.oom);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_Ray(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 5
        l = new V3D_Line(e, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 6
        l = new V3D_Line(e, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P0P0P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 7
        l = new V3D_Line(e,P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e,P0P0P0, P0P0N1, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 8
        l = new V3D_Line(e,P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e,P0P0P0, P0P0P1, P0P0P2);
        assertFalse(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        V3D_Geometry expResult = new V3D_Ray(pP0P0P0, pP1P0P0);
        V3D_Geometry result = instance.getIntersection(l, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, e.oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0);
        result = instance.getIntersection(l, e.oom);
        assertNull(result);
        // Test 4
        l = new V3D_Line(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        result = instance.getIntersection(l, e.oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(l, e.oom);
        expResult = new V3D_Ray(pN1P0P0, pP1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_3args_2() {
        System.out.println("getIntersection");
        V3D_LineSegment l;
        int oom = V3D_Environment.DEFAULT_OOM;
        V3D_Ray instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        l = new V3D_LineSegment(e, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e,P0P0P0, P1P0P0, P2P0P0);
        result = instance.getIntersection(l, oom);
        expResult = pP1P0P0;
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0);
        result = instance.getIntersection(l, oom);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1);
        result = instance.getIntersection(l, oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(l, oom);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line_int() {
        System.out.println("getLineOfIntersection");
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP0P0P1);
        V3D_Line l = new V3D_Line(pP1P0P0, pP1P1P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry result = instance.getLineOfIntersection(l, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP0P1P0);
        result = instance.getLineOfIntersection(l, e.oom);
        assertNull(result);
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        V3D_Point pt;
        V3D_Ray instance;
        V3D_LineSegment expResult;
        V3D_Geometry result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Ray(pP1P0P0, pP1P1P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        result = instance.getLineOfIntersection(pt, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(pP1N1P0, pP1P1P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        result = instance.getLineOfIntersection(pt, e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Ray.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p;
        V3D_Ray instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = pP0P0P0;
        instance = new V3D_Ray(pP1P0P0, pP1P1P0);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Ray.
     */
    @Test
    public void testGetDistance_V3D_Ray_int() {
        System.out.println("getDistance");
        V3D_Ray p;
        V3D_Ray instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = new V3D_Ray(pP0P0P0, pP1P1P0);
        instance = new V3D_Ray(pP1P0P0, pP1P1P0);
        expResult = new Math_BigRationalSqrt(2L, e.oom).getSqrt(e.oom)
                .divide(2L).toBigDecimal(e.oom);
        result = instance.getDistance(p, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Ray_int() {
        System.out.println("isIntersectedBy");
        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P0P0);
        V3D_Ray instance = new V3D_Ray(pP1P1P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(r, e.oom));
        // Test 2
        instance = new V3D_Ray(pP1P1P0, pP1P2P0);
        assertFalse(instance.isIntersectedBy(r, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Ray instance = new V3D_Ray(pP1P1P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        instance = new V3D_Ray(pP1P1P0, pP1P2P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        V3D_Ray instance;
        V3D_Plane p;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        instance = new V3D_Ray(e,P0P0P0, N2P0P0, N1P0P0);
        p = V3D_Plane.X0;
//        p = new V3D_Plane(new V3D_Environment(),
//            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.J, V3D_Vector.K);
        //expResult = new V3D_Point(e, P0P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersection(p, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(e, P0P0P0, N1P0P0, N2P0P0);
        p = V3D_Plane.X0;
        assertNull(instance.getIntersection(p, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Ray_int() {
        System.out.println("getIntersection");
        V3D_Ray r;
        V3D_Ray instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1: Collinear Pointing the same way
        r = new V3D_Ray(e, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P1P0P0, P2P0P0);
        result = instance.getIntersection(r, e.oom);
        expResult = new V3D_Ray(e, P0P0P0, P1P0P0, P2P0P0);
        assertEquals(expResult, result);
        // Test 2: Collinear Pointing the same way 
        r = new V3D_Ray(e, P0P0P0, N2P0P0, N1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P1P0P0, P2P0P0);
        result = instance.getIntersection(r, e.oom);
        expResult = new V3D_Ray(e, P0P0P0, P1P0P0, P2P0P0);
        assertEquals(expResult, result);
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect and the start of the ray is the start
         * point of the ray that intersects both rays. If they point in opposite
         * directions, then they do not intersect unless the points they start
         * at intersect with the other ray and in this instance, the
         * intersection is the line segment between them.
         */
        // Test 3: Collinear pointing opposite ways overlapping in a line segment.
        r = new V3D_Ray(e, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P1P0P0, P0P0P0);
        expResult = new V3D_LineSegment(e, P0P0P0, P0P0P0, P1P0P0);
        result = instance.getIntersection(r, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4: Collinear pointing opposite ways overlapping at a point.
        r = new V3D_Ray(e, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P0P0P0, N1P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersection(r, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4: Collinear pointing opposite ways not overlapping.
        r = new V3D_Ray(e, P0P0P0, P1P0P0, P2P0P0);
        instance = new V3D_Ray(e, P0P0P0, P0P0P0, N1P0P0);
        result = instance.getIntersection(r, e.oom);
        assertNull(result);
        // Test 5: Intersecting at a point.
        r = new V3D_Ray(e, P0P0P0, N2P0P0, N1P0P0);
        instance = new V3D_Ray(e, P0P0P0, P1P0P0, P1P1P1);
        result = instance.getIntersection(r, e.oom);
        expResult = pP1P0P0;
        assertTrue(expResult.equals(result));
        // Test 6: Not intersecting.
        r = new V3D_Ray(e, P0P0P0, P1P0P0, P2P0P0);
        instance = new V3D_Ray(e, P0P0P0, P0P0P0, P1P1P1);
        result = instance.getIntersection(r, e.oom);
        assertNull(result);
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Point_int() {
        System.out.println("getLineOfIntersection");
        V3D_Point pt;
        int oom = V3D_Environment.DEFAULT_OOM;
        V3D_Ray instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Ray(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        result = instance.getLineOfIntersection(pt, oom);
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 2
        pt = pP0P0P1;
        instance = new V3D_Ray(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment(pP0P0P1, pP1P0P0);
        result = instance.getLineOfIntersection(pt, oom);
        assertEquals(expResult, result);
        // Test 3
        pt = pP2P0P0;
        instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        expResult = pP2P0P0;
        result = instance.getLineOfIntersection(pt, oom);
        assertEquals(expResult, result);
        // Test 4
        pt = pP2P1P0;
        instance = new V3D_Ray(pP0P0P0, pP1P0P0);
        expResult = new V3D_LineSegment(pP2P0P0, pP2P1P0);
        result = instance.getLineOfIntersection(pt, oom);
        assertEquals(expResult, result);
    }

}

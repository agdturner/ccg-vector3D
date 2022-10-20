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
//import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Point class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PointTest extends V3D_Test {

    public V3D_PointTest() {
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
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P0P0;
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(instance, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5.toBigDecimal(oom, rm);
        result = instance.getDistance(pP0P0P0, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getEnvelope method, of class V3D_Point.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope expResult = new V3D_Envelope(e, oom, rm, pP0P0P0, pP1P1P1);
        V3D_Envelope result = pP0P0P0.getEnvelope(oom, rm);
        result = result.union(pP1P1P1.getEnvelope(oom, rm), oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }
    
    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testEquals_V3D_Point_int_RoundingMode() {
        System.out.println("equals");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P0P0;
        Math_BigRational x = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Math_BigRational y = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Math_BigRational z = Math_BigRational.valueOf(new BigDecimal("0.000"));
        V3D_Point p = new V3D_Point(e, x, y, z);
        assertTrue(instance.equals(p, oom, rm));
        // Test 2
        x = P1;
        y = P10;
        z = P0;
        instance = new V3D_Point(e, x, y, z);
        x = Math_BigRational.valueOf(new BigDecimal("1.000"));
        y = Math_BigRational.valueOf(new BigDecimal("10.000"));
        z = Math_BigRational.valueOf(new BigDecimal("0.000"));
        p = new V3D_Point(e, x, y, z);
        assertTrue(instance.equals(p, oom, rm));
        // Test 3
        x = Math_BigRational.ONE;
        y = Math_BigRational.TEN;
        z = Math_BigRational.ZERO;
        instance = new V3D_Point(e, x, y, z);
        x = Math_BigRational.valueOf(new BigDecimal("0.000"));
        y = Math_BigRational.valueOf(new BigDecimal("1.000"));
        z = Math_BigRational.valueOf(new BigDecimal("10.000"));
        p = new V3D_Point(e, x, y, z);
        assertFalse(instance.equals(p, oom, rm));
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Point() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Point instance = V3D_Point.ORIGIN;
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ZERO;
        Math_BigRationalSqrt result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 2
        instance = pP1P0P0;
        expResult = Math_BigRationalSqrt.ONE;
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 3
        instance = pP1P1P0;
        expResult = new Math_BigRationalSqrt(2, oom, rm);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = new Math_BigRationalSqrt(25, oom, rm);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Point(e, P0, P3, P4);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Point(e, P3, P0, P4);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Point(e, N3, N4, P0);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Point(e, P0, N3, N4);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Point(e, N3, P0, N4);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Point(e, N3, P4, P0);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_Point(e, P0, P3, N4);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_Point(e, P3, P0, N4);
        result = instance.getDistance(oom, rm, p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = pP0P0P0;
        V3D_Point instance = pP1P0P0;
        BigDecimal expResult = P1.toBigDecimal(oom, rm);
        BigDecimal result = instance.getDistance(p, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5.toBigDecimal(oom, rm);
        result = instance.getDistance(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Point.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P0P0;
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getDistanceSquared(instance, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = Math_BigRational.valueOf(25);
        result = instance.getDistanceSquared(pP0P0P0, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        V3D_Point instance = pP0P1P0;
        BigDecimal expResult = P1.toBigDecimal(oom, rm);
        BigDecimal result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5.toBigDecimal(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(pP0P0P1, pP0P0P0, oom, rm);
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5.toBigDecimal(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
        // Test 4
        l = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        instance = new V3D_Point(e, P4, P3, P0);
        expResult = P5.toBigDecimal(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        instance = new V3D_Point(e, P4, P3, P10);
        expResult = P5.toBigDecimal(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Point instance = V3D_Point.ORIGIN;
        assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 2
        instance = pP0N1N1;
        assertFalse(instance.isIntersectedBy(p, oom, rm));
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        V3D_Point instance = pP1P1P0;
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = pN1N1P0;
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = pP2P2P0;
        expResult = BigDecimal.valueOf(2);
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = pP2P2P0;
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        expResult = new Math_BigRationalSqrt(5, oom, rm).toBigDecimal(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V3D_Point.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Point instance = pP0P1P2;
//        String expResult = "V3D_Point\n"
//                + "(\n"
//                + " oom=-3\n"
//                + " ,\n"
//                + " offset=V3D_Vector\n"
//                + " (\n"
//                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + " )\n"
//                + " ,\n"
//                + " rel=V3D_Vector\n"
//                + " (\n"
//                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
//                + "  dz=Math_BigRationalSqrt(x=4, sqrtx=2, oom=0)\n"
//                + " )\n"
//                + ")";
        String expResult = "V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=0, dy=1, dz=2))";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V3D_Point.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Point instance = pP0P1P2;
        String expResult = "V3D_Point\n"
                + "(\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " rel=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=4, sqrtx=2, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V3D_Point.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        V3D_Point instance = pP0P1P2;
        String expResult = "offset=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "rel=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=4, sqrtx=2, oom=0)\n"
                + ")";
        String result = instance.toStringFields(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }
//
//    /**
//     * Test of getRel method, of class V3D_Point.
//     */
//    @Test
//    public void testGetRel() {
//        System.out.println("getRel");
//        V3D_Point instance = new V3D_Point(0, 1, 2);
//        V3D_Vector expResult = new V3D_Vector(0, 1, 2);
//        V3D_Vector result = instance.getRel();
//        assertEquals(expResult, result);
//    }

    /**
     * Test of getVector method, of class V3D_Point.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P1P2;
        V3D_Vector expResult = new V3D_Vector(0, 1, 2);
        V3D_Vector result = instance.getVector(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getX method, of class V3D_Point.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P1P2;
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getX(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getY method, of class V3D_Point.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P1P2;
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getY(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZ method, of class V3D_Point.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P1P2;
        Math_BigRational expResult = Math_BigRational.TWO;
        Math_BigRational result = instance.getZ(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isOrigin method, of class V3D_Point.
     */
    @Test
    public void testIsOrigin() {
        System.out.println("isOrigin");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = pP0P1P2;
        assertFalse(instance.isOrigin(oom, rm));
        instance = pP0P0P0;
        assertTrue(instance.isOrigin(oom, rm));
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_int_V3D_Point() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = pP0P0P0;
        V3D_Point instance = pP1P0P0;
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ONE;
        Math_BigRationalSqrt result = instance.getDistance(oom, rm, p);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        p = pP0P0P0;
        instance = pP1P1P1;
        expResult = new Math_BigRationalSqrt(3, oom, rm);
        result = instance.getDistance(oom, rm, p);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Point.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = pP1P0P0;
        V3D_Point instance = pP1P0P0;
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        p = pP1P0P0;
        instance = pP2P0P0;
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        p = pP1P0P0;
        instance = pP0P1P0;
        expResult = Math_BigRational.valueOf(2);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        p = pP1P0P0;
        instance = pP1P1P0;
        expResult = Math_BigRational.valueOf(1);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        p = pP0P0P0;
        instance = pP1P1P1;
        expResult = Math_BigRational.valueOf(3);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Point covered by
     * {@link V3D_PlaneTest#testGetDistance_V3D_Point_int()}.
     *
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        System.out.println("getDistanceSquared");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point covered by
     * {@link V3D_PlaneTest#testIsIntersectedBy_V3D_Point_int()}.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point is covered by:
     * {@link V3D_LineTest#testIsIntersectedBy_V3D_Point()}.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
    }

    /**
     * Test of getIntersection method, of class V3D_Point is covered by:
     * {@link V3D_LineTest#testGetIntersection()}.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Point instance = pP0P0P0;
        V3D_Geometry expResult = instance;
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Point.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Point instance = pP0P0P0;
        V3D_Geometry expResult = pP0P0P0;
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        instance = pP1P0P0;
        expResult = pP1P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        instance = pN1P0P0;
        assertNull(instance.getIntersection(l, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     * {@link V3D_LineSegmentTest#testIsIntersectedBy_V3D_Point()}
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
    }

//    /**
//     * Test of getDistance method, of class V3D_Point.
//     */
//    @Test
//    public void testGetDistance_V3D_Ray_int() {
//        System.out.println("getDistance");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Ray r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
//        V3D_Point instance = V3D_Point.ORIGIN;
//        BigDecimal expResult = BigDecimal.ONE;
//        BigDecimal result = instance.getDistance(r, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//    }

    /**
     * Test of getLocation method, of class V3D_Point.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = V3D_Point.ORIGIN;
        assertEquals(0, instance.getLocation(oom, rm));
        // PPP
        instance = pP0P0P1;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP0P1P0;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP0P1P1;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP1P0P0;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP1P0P1;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP1P1P0;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP1P1P1;
        assertEquals(1, instance.getLocation(oom, rm));
        // PPN
        instance = pP0P0N1;
        assertEquals(2, instance.getLocation(oom, rm));
        // PNP
        instance = pP0N1P0;
        assertEquals(3, instance.getLocation(oom, rm));
        // PNN
        instance = pP0N1N1;
        assertEquals(4, instance.getLocation(oom, rm));
        // NPP
        instance = pN1P0P0;
        assertEquals(5, instance.getLocation(oom, rm));
        // NPN
        instance = pN1P0N1;
        assertEquals(6, instance.getLocation(oom, rm));
        // NNP
        instance = pN1N1P0;
        assertEquals(7, instance.getLocation(oom, rm));
        // NNN
        instance = pN1N1N1;
        assertEquals(8, instance.getLocation(oom, rm));
    }

    /**
     * Test of rotate method, of class V3D_Point.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        Math_BigDecimal bd = new Math_BigDecimal();
        int oom = -3;
        int oomt = oom - 2;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigRational Pi = Math_BigRational.valueOf(bd.getPi(oomt, rm));
        V3D_Vector axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        V3D_Point instance = new V3D_Point(pP1P0P0);
        Math_BigRational theta = Pi;
        V3D_Point result = instance.rotate(axisOfRotation, theta, oom, rm);
        V3D_Point expResult = pP0P1P0;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        instance = new V3D_Point(pP1P0P0);;
        theta = Pi;
        result = instance.rotate(axisOfRotation, theta, oom, rm);
        expResult = pP0P1P0;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        V3D_Vector offset = new V3D_Vector(2, 0, 0);
        V3D_Vector rel = new V3D_Vector(1, 0, 0);
        instance = new V3D_Point(e, offset, rel);
        theta = Pi;
        result = instance.rotate(axisOfRotation, theta, oom, rm);
        expResult = new V3D_Point(e, 2, 1, 0);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        offset = new V3D_Vector(1, 0, 0);
        rel = new V3D_Vector(2, 0, 0);
        instance = new V3D_Point(e, offset, rel);
        theta = Pi;
        result = instance.rotate(axisOfRotation, theta, oom, rm);
        expResult = new V3D_Point(e, 1, 2, 0);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getDistance method, of class V3D_Point covered by
     * {@link V3D_PlaneTest#testGetDistance_V3D_Point_int}.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        //System.out.println("getDistance");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point covered by
     * {@link V3D_PlaneTest#testIsIntersectedBy_V3D_Point_int}.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        //System.out.println("isIntersectedBy");
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Point covered by
     * {@link V3D_LineTest#testIsIntersectedBy_V3D_Point_int()}
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        //System.out.println("isEnvelopeIntersectedBy");
    }

    /**
     * Test of setOffset method, of class V3D_Point.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector offset = P0P0P1;
        V3D_Point instance = new V3D_Point(pP0P0P0);
        instance.setOffset(offset, oom, rm);
        assertTrue(instance.equals(pP0P0P0, oom, rm));
    }

    /**
     * Test of setRel method, of class V3D_Point.
     */
    @Test
    public void testSetRel() {
        System.out.println("setRel");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector rel = P0P0P1;
        V3D_Point instance = new V3D_Point(pP0P0P0);
        instance.setRel(rel, oom, rm);
        assertTrue(instance.equals(pP0P0P0, oom, rm));
    }

}

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
import uk.ac.leeds.ccg.math.Math_BigRational;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Triangle class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TriangleTest extends V3D_Test {

    public V3D_TriangleTest() {
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
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, P0P0P0, P1P0P0, P1P1P0);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = P0P0P0;
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 2
        pt = N1N1P0;
        assertFalse(instance.isIntersectedBy(pt));
        // Test 3
        pt = new V3D_Point(Math_BigRational.ONE.divide(2), P0, P0);
        assertTrue(instance.isIntersectedBy(pt));
    }

    /**
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, P0P0P0, P0P1P0, P1P0P0);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArea method, of class V3D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        BigDecimal expResult = new BigDecimal("0.5");
        BigDecimal result = instance.getArea(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of apply method, of class V3D_Triangle.
     */
    @Test
    @Disabled
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Point pt = P0P0P0;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(pt));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P2P2P2, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l));
        // Test 2
        l = new V3D_Line(P1P1P1, P1P1P0, oom);
        assertFalse(instance.isIntersectedBy(l));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P2P2P2, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l));
        // Test 2
        l = new V3D_LineSegment(P1P1P1, P1P1P0, oom);
        assertFalse(instance.isIntersectedBy(l));
    }

    /**
     * Test of getPerimeter method, of class V3D_Triangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = 0;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        BigDecimal expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, oom).toBigDecimal(oom));
        BigDecimal result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P1N1P0, new V3D_Point(P1, P2, P0), oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P1P0, P2P0P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P1P0P0, P1P1P0, oom);
        V3D_Geometry result = instance.getIntersection(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P2N2P0, new V3D_Point(P2, P1, P0), oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P2P2P0, new V3D_Point(P4, P0, P0), oom);
        V3D_Geometry expResult = new V3D_LineSegment(P2P0P0, new V3D_Point(P2, P1, P0), oom);
        V3D_Geometry result = instance.getIntersection(l, true);
        assertEquals(expResult, result);
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P1N1P0, new V3D_Point(P1, P2, P0), oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P1P0, P2P0P0, oom);
        assertTrue(instance.isEnvelopeIntersectedBy(l));
    }

}

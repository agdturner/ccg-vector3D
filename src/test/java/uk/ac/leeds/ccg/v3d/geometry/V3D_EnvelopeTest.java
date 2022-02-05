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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test class for V3D_Envelope.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

    public V3D_EnvelopeTest() {
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
     * Test of toString method, of class V3D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0);
        String expResult = "V3D_Envelope(xMin=0, xMax=0, yMin=0, yMax=0"
                + ", zMin=0, zMax=0)";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of envelop method, of class V3D_Envelope.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        V3D_Envelope e1 = new V3D_Envelope(e, pP0P0P0);
        V3D_Envelope instance = new V3D_Envelope(e, pP1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        V3D_Envelope result = instance.union(e1);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Envelope() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0, pP0P0P0);
        V3D_Envelope en = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(en));
        // Test 2
        instance = new V3D_Envelope(e, pN1N1N1, pP0P0P0);
        assertTrue(instance.isIntersectedBy(en));
        // Test 3
        en = new V3D_Envelope(e, pN2N2N2, pP2P2P2);
        assertTrue(instance.isIntersectedBy(en));
        // Test 4
        en = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        instance = new V3D_Envelope(e, pN1N1N1, pN1P0P0);
        assertFalse(instance.isIntersectedBy(en));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        // Test 1 the centre
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        // Test 2 to 9 the corners
        // Test 2
        assertTrue(instance.isIntersectedBy(pP1P1P1, e.oom));
        // Test 3
        assertTrue(instance.isIntersectedBy(pN1N1N1, e.oom));
        // Test 4
        assertTrue(instance.isIntersectedBy(pN1N1P1, e.oom));
        // Test 5
        assertTrue(instance.isIntersectedBy(pN1P1N1, e.oom));
        // Test 6
        assertTrue(instance.isIntersectedBy(pP1N1N1, e.oom));
        // Test 7
        assertTrue(instance.isIntersectedBy(pP1P1N1, e.oom));
        // Test 8
        assertTrue(instance.isIntersectedBy(pP1N1P1, e.oom));
        // Test 9
        assertTrue(instance.isIntersectedBy(pN1P1P1, e.oom));
        // Test 10 to 21 edge mid points
        // Test 10
        assertTrue(instance.isIntersectedBy(pN1N1P0, e.oom));
        // Test 11
        assertTrue(instance.isIntersectedBy(pN1P0N1, e.oom));
        // Test 12
        assertTrue(instance.isIntersectedBy(pP0N1N1, e.oom));
        // Test 13
        assertTrue(instance.isIntersectedBy(pP1P1P0, e.oom));
        // Test 14
        assertTrue(instance.isIntersectedBy(pP1P0P1, e.oom));
        // Test 15
        assertTrue(instance.isIntersectedBy(pP0P1P1, e.oom));
        // Test 16
        assertTrue(instance.isIntersectedBy(pP0N1P1, e.oom));
        // Test 17
        assertTrue(instance.isIntersectedBy(pN1P1P0, e.oom));
        // Test 18
        assertTrue(instance.isIntersectedBy(pP1P0N1, e.oom));
        // Test 19
        assertTrue(instance.isIntersectedBy(pP0P1N1, e.oom));
        // Test 20
        assertTrue(instance.isIntersectedBy(pP1N1P0, e.oom));
        // Test 21
        assertTrue(instance.isIntersectedBy(pN1P0P1, e.oom));
        // Test 22 to 28 face mid points
        // Test 22
        assertTrue(instance.isIntersectedBy(pP0P0P1, e.oom));
        // Test 23
        assertTrue(instance.isIntersectedBy(pP0P1P0, e.oom));
        // Test 24
        assertTrue(instance.isIntersectedBy(pP1P0P0, e.oom));
        // Test 25
        assertTrue(instance.isIntersectedBy(pP0P0N1, e.oom));
        // Test 26
        assertTrue(instance.isIntersectedBy(pP0N1P0, e.oom));
        // Test 27
        assertTrue(instance.isIntersectedBy(pN1P0P0, e.oom));
    }

    /**
     * Test of getEnvelope method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_Point p0 = pP0P0P0;
        V3D_Point p1 = pP1P1P1;
        V3D_Envelope instance = new V3D_Envelope(e, p0, p1);
        V3D_Envelope expResult = new V3D_Envelope(e, p0, p1);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        Object o = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(o));
        // Test 2
        o = new V3D_Envelope(e, pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(o));
        // Test 3
        o = new V3D_Envelope(e, pP1N1P1, pP0P0P0);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals_V3D_Envelope() {
        System.out.println("equals");
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        V3D_Envelope en = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(en));
        // Test 2
        en = new V3D_Envelope(e, pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(en));
        // Test 3
        en = new V3D_Envelope(e, pP1N1P1, pP0P0P0);
        assertFalse(instance.equals(en));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope. No need for a full
     * set of test here as this is covered by
     * {@link #testIsIntersectedBy_V3D_Point()}
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(P0, P0, P0));
    }

    /**
     * Test of hashCode method, of class V3D_Envelope.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Envelope en = new V3D_Envelope(e, pP0P0P0);
        int result = en.hashCode();
        int expResult = -1680672931;
        //System.out.println(result);
        assertTrue(result == expResult);
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Envelope() {
        System.out.println("getIntersection");
        V3D_Envelope en;
        V3D_Envelope instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        // Test 1
        en = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 2
        en = new V3D_Envelope(e, pN1N1N1, pP0P0P0);
        instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(e, pP0P0P0);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 3
        en = new V3D_Envelope(e, pN1N1N1, pP0P0P0);
        instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(e, pP0P0P0);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 4
        en = new V3D_Envelope(e, pN1N1N1, pP0P0P1);
        instance = new V3D_Envelope(e, pP0P0N1, pP1P1P1);
        expResult = new V3D_Envelope(e, pP0P0N1, pP0P0P1);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line li = new V3D_Line(pP0P0P0, pP0P0P1);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0N1, pP0P0P1);
        V3D_Geometry result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 2
        li = new V3D_Line(pP0P0P0, pP0P1P0);
        expResult = new V3D_LineSegment(pP0N1P0, pP0P1P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 3
        li = new V3D_Line(pP0P0P0, pP1P0P0);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 4
        li = new V3D_Line(pP1P1P1, pP0P2P1);
        expResult = pP1P1P1;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 5
        li = new V3D_Line(pP1P1P1, pP0P2P1);
        expResult = pP1P1P1;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 6 Intersection of a corner at a point
        li = new V3D_Line(pP1P1P1, pP0P2P1);
        expResult = pP1P1P1;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 6 Intersection of an edge at a point
        li = new V3D_Line(pP1P1P0, pP0P2P2);
        expResult = pP1P1P0;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // To do: add some expResult = null test cases
    }

    /**
     * Test of union method, of class V3D_Envelope.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        V3D_Envelope en = new V3D_Envelope(e, pN2N2N2, pP1P1P1);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP2P2P2);
        V3D_Envelope expResult = new V3D_Envelope(e, pN2N2N2, pP2P2P2);
        V3D_Envelope result = instance.union(en);
        assertEquals(expResult, result);
    }

    /**
     * Test of isContainedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        V3D_Envelope en = new V3D_Envelope(e, pN2N2N2, pP2P2P2);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isContainedBy(en));
        // Test 2
        instance = new V3D_Envelope(e, pN2N2N2, pP2P2P2);
        assertTrue(instance.isContainedBy(en));
        // Test 3
        en = new V3D_Envelope(e, pN1N1N1, pP2P2P2);
        assertFalse(instance.isContainedBy(en));
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        V3D_LineSegment li = new V3D_LineSegment(pN2N2N2, pP0P0P0);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        V3D_Geometry expResult = new V3D_LineSegment(pN1N1N1, pP0P0P0);
        V3D_Geometry result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 2
        li = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 3
        li = new V3D_LineSegment(pP0N1P0, pP0P1P0);
        expResult = new V3D_LineSegment(pP0N1P0, pP0P1P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 4
        li = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 5
        li = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 6
        li = new V3D_LineSegment(pN2P0P0, pP1P0P0);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 7
        li = new V3D_LineSegment(pP1P1P1, pP0P2P1);
        expResult = pP1P1P1;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 8
        li = new V3D_LineSegment(pP1P1P1, pP0P2P1);
        expResult = pP1P1P1;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 9 Intersection of a corner at a point
        li = new V3D_LineSegment(pP1P1P1, pP0P2P1);
        expResult = pP1P1P1;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // Test 10 Intersection of an edge at a point
        li = new V3D_LineSegment(pP1P1P0, pP2P2P0);
        expResult = pP1P1P0;
        result = instance.getIntersection(li, e.oom);
        assertEquals(expResult, result);
        // To do: add some expResult = null test cases.
    }

    /**
     * Test of getEnvelope method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0);
        V3D_Envelope expResult = new V3D_Envelope(e, pP0P0P0);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of getXMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetXMin() {
        System.out.println("getxMin");
        V3D_Envelope instance = new V3D_Envelope(e, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N2;
        Math_BigRational result = instance.getXMin(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getXMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetXMax() {
        System.out.println("getxMax");
        V3D_Envelope instance = new V3D_Envelope(e, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = P0;
        Math_BigRational result = instance.getXMax(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getYMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetYMin() {
        System.out.println("getyMin");
        V3D_Envelope instance = new V3D_Envelope(e, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N2;
        Math_BigRational result = instance.getYMin(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getYMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetYMax() {
        System.out.println("getyMax");
        V3D_Envelope instance = new V3D_Envelope(e, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N1;
        Math_BigRational result = instance.getYMax(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetZMin() {
        System.out.println("getzMin");
        V3D_Envelope instance = new V3D_Envelope(e, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N2;
        Math_BigRational result = instance.getZMin(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetZMax() {
        System.out.println("getzMax");
        V3D_Envelope instance = new V3D_Envelope(e, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = P0;
        Math_BigRational result = instance.getZMax(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        V3D_Line li = new V3D_Line(pP0P0P0, pP0P0P1);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 2
        li = new V3D_Line(pP0P0P0, pP0P1P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 3
        li = new V3D_Line(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 4
        li = new V3D_Line(pP1P1P1, pP0P2P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 5
        li = new V3D_Line(pP1P1P1, pP0P2P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 6 Intersection of a corner at a point
        li = new V3D_Line(pP1P1P1, pP0P2P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 6 Intersection of an edge at a point
        li = new V3D_Line(pP1P1P0, pP0P2P2);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 7 Internal
        instance = new V3D_Envelope(e, pN2N2N2, pP2P2P2);
        li = new V3D_Line(pP0P0P0, pP0P1P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // To do: add some false test cases.
        li = V3D_Line.X_AXIS;
        instance = new V3D_Envelope(e, pP0P0P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_3args_1() {
        System.out.println("isIntersectedBy");
        Math_BigRational x;
        Math_BigRational y;
        Math_BigRational z;
        V3D_Envelope instance;
        // Test 1
        x = P0;
        y = P0;
        z = P0;
        instance = new V3D_Envelope(e, pP0P0P0);
        assertTrue(instance.isIntersectedBy(x, y, z));
        // Test 2
        instance = new V3D_Envelope(e, pP1P0P0);
        assertFalse(instance.isIntersectedBy(x, y, z));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment li = new V3D_LineSegment(pN2N2N2, pP0P0P0);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 2
        li = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 3
        li = new V3D_LineSegment(pP0N1P0, pP0P1P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 4
        li = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 5
        li = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 6
        li = new V3D_LineSegment(pN2P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 7
        li = new V3D_LineSegment(pP1P1P1, pP0P2P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 8
        li = new V3D_LineSegment(pP1P1P1, pP0P2P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 9 Intersection of a corner at a point
        li = new V3D_LineSegment(pP1P1P1, pP0P2P1);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // Test 10 Intersection of an edge at a point
        li = new V3D_LineSegment(pP1P1P0, pP0P2P2);
        assertTrue(instance.isIntersectedBy(li, e.oom));
        // To do: add some false test cases.
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isEnvelopeIntersectedBy(l, e.oom));
        // Test 2
        l = new V3D_Line(pN1N1N1, pN1N1P0);
        instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        assertFalse(instance.isEnvelopeIntersectedBy(l, e.oom));
    }

    /**
     * Test of apply method, of class V3D_Envelope.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        V3D_Vector v = P1P1P1;
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(e, pP1P1P1, pP2P2P2);
        instance.apply(e.oom, v);
        assertTrue(expResult.equals(instance));
        // Test 2
        v = N1N1N1;
        instance = new V3D_Envelope(e, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(e, pN1N1N1, pP0P0P0);
        instance.apply(e.oom, v);
        assertTrue(expResult.equals(instance));
    }

    /**
     * Test of getDistance method, of class V3D_Envelope.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = pP0P0P0;
        V3D_Envelope instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        BigDecimal expResult = new Math_BigRationalSqrt(0, e.oom).toBigDecimal(e.oom);
        BigDecimal result = instance.getDistance(p, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test
        instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        // Corners
        // Test 2
        result = instance.getDistance(pN2N2N2, e.oom);
        expResult = new Math_BigRationalSqrt(3, e.oom).toBigDecimal(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        result = instance.getDistance(pN2N2P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        result = instance.getDistance(pN2P2N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        result = instance.getDistance(pN2P2P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        result = instance.getDistance(pP2N2N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 7
        result = instance.getDistance(pP2N2P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 8
        result = instance.getDistance(pP2P2N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 9
        result = instance.getDistance(pP2P2P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Edges
        // Test 10
        expResult = new Math_BigRationalSqrt(2, e.oom).toBigDecimal(e.oom);
        result = instance.getDistance(pN2N2P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 11
        result = instance.getDistance(pN2P2P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 12
        result = instance.getDistance(pN2P0N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 13
        result = instance.getDistance(pN2P0P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 14
        result = instance.getDistance(pP0N2P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 15
        result = instance.getDistance(pP0P2N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 16
        result = instance.getDistance(pP2P0N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 17
        result = instance.getDistance(pP2N2P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // 6 planes
        // Test 18
        expResult = new Math_BigRationalSqrt(1, e.oom).toBigDecimal(e.oom);
        result = instance.getDistance(pN2P0P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 19
        result = instance.getDistance(pP0N2P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 20
        result = instance.getDistance(pP0P0N2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 21
        result = instance.getDistance(pP2P0P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 22
        result = instance.getDistance(pP0P2P0, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 23
        result = instance.getDistance(pP0P0P2, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0);
        assertTrue(instance.isIntersectedBy(p, e.oom));
        // Test 2
        instance = new V3D_Envelope(e, pP1P0P0);
        assertFalse(instance.isIntersectedBy(p, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Triangle_int() {
        System.out.println("isIntersectedBy");
        V3D_Triangle t;
        V3D_Envelope instance;
        // Test 1
        t = new V3D_Triangle(pN2P2P0, pN2N2P0, pP2P0P0);
        instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(t, e.oom));
        // Test 2
        t = new V3D_Triangle(new V3D_Point(e, N10, P2, P0), new V3D_Point(e, N10, N2, P0), pP2P0P0);
        instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(t, e.oom));
        // Test 2
        t = new V3D_Triangle(new V3D_Point(e, N10, P10, P0), new V3D_Point(e, N10, N10, P0), new V3D_Point(e, P10, P0, P0));
        instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(t, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        System.out.println("isIntersectedBy");
        V3D_Plane p = V3D_Plane.X0;
        V3D_Envelope instance = new V3D_Envelope(e, pP0P0P0);
        assertTrue(instance.isIntersectedBy(p, e.oom));
        // Test 2
        instance = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(p, e.oom));
        // Test 3
        p = new V3D_Plane(pN2P2P0, pP2N2P0, pP0P0P2);
        assertTrue(instance.isIntersectedBy(p, e.oom));
    }
}

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

import uk.ac.leeds.ccg.v3d.V3D_Test;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test class for V3D_Line.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineTest extends V3D_Test {

    public V3D_LineTest() {
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
    public void testToString_0args() {
        System.out.println("toString");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        String expResult = "V3D_Line\n"
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
//                + " p=V3D_Point\n"
//                + " (\n"
//                + "  oom=-3\n"
//                + "  ,\n"
//                + "  offset=V3D_Vector\n"
//                + "  (\n"
//                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + "  )\n"
//                + "  ,\n"
//                + "  rel=V3D_Vector\n"
//                + "  (\n"
//                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + "  )\n"
//                + " )\n"
//                + " ,\n"
//                + " q=V3D_Point\n"
//                + " (\n"
//                + "  oom=-3\n"
//                + "  ,\n"
//                + "  offset=V3D_Vector\n"
//                + "  (\n"
//                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + "  )\n"
//                + "  ,\n"
//                + "  rel=V3D_Vector\n"
//                + "  (\n"
//                + "   dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
//                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + "  )\n"
//                + " )\n"
//                + " ,\n"
//                + " v=null\n"
//                + ")";
        String expResult = "V3D_Line\n"
                + "(\n"
                + " oom=-3, offset=V3D_Vector(dx=0, dy=0, dz=0),\n"
                + " p=V3D_Point(oom=-3, offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=0, dy=0, dz=0)),\n"
                + " q=V3D_Point(oom=-3, offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=1, dy=0, dz=0)),\n"
                + " v=null\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Line.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int_RoundingMode() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = pP0P0P0;
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        pt = new V3D_Point(e, P0_1E2, P0_1E2, P0_1E2);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3 works as the rounding puts pt on the line.
        pt = new V3D_Point(e, P0_1E12, P0_1E12, P0_1E12);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 4 works as the rounding puts pt on the line.
        pt = new V3D_Point(e, N0_1E12, N0_1E12, N0_1E12);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 5 works as the rounding puts pt on the line.
        Math_BigRational a = P0_1E2.add(P1E12);
        pt = new V3D_Point(e, a, a, a);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 6 works as the rounding puts pt on the line.
        a = N0_1E2.add(N1E12);
        pt = new V3D_Point(e, a, a, a);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 7
        instance = new V3D_Line(pP0N1N1, pP2P1P1, oom, rm);
        pt = new V3D_Point(e, N1, N2, N2);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 8 fails as the rounding does not put pt on the line.
        a = N0_1E2.add(N1E12);
        pt = new V3D_Point(e, a, a, a);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        pt = new V3D_Point(e, a.add(BigInteger.ONE), a, a);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
//        // Test 9 Results seem somewhat contrary. It would not matter if these 
//        // results came out differently. The point is that oom is not set 
//        // to be sufficiently sensitive, so we get intersections reported that 
//        // are not wanted.  
//        a = N0_1E12.add(N1E12);
//        pt = new V3D_Point(e, a, a, a);
//        assertTrue(instance.isIntersectedBy(pt, oom, rm)); // True as rounding in the cross product calculation is too general.
//        pt = new V3D_Point(e, a.add(BigInteger.ONE), a, a);
//        assertTrue(instance.isIntersectedBy(pt, oom, rm)); // True as rounding in the cross product calculation is too general, but it should be true anyway!
        // Test 10 This is like test 9, but the oom is set appropriately so the
        // coordinate and crossproduct rounding work fine.
        a = N0_1E12.add(N1E12);
        oom = -2; // In this case oom = -2 is sufficient.
        pt = new V3D_Point(e, a, a, a);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        pt = new V3D_Point(e, a.add(BigInteger.ONE), a, a);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
    }

    /**
     * Test of isParallel method, of class V3D_Line.
     */
    @Test
    public void testIsParallel() {
        System.out.println("isParallel");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = V3D_Line.X_AXIS;
        V3D_Line instance = V3D_Line.X_AXIS;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 2
        instance = V3D_Line.Y_AXIS;
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 3
        instance = V3D_Line.Z_AXIS;
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 4
        instance = new V3D_Line(pP0P1P0, pP1P1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 5
        instance = new V3D_Line(pP0P1P0, pP1P1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 6
        instance = new V3D_Line(pP0P0P1, pP1P0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 7
        instance = new V3D_Line(pP1P0P1, pP0P0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 8
        instance = new V3D_Line(pP1P0P1, pP0P1P1, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 9
        l = V3D_Line.Y_AXIS;
        instance = new V3D_Line(pP0P0P1, pP0P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 9
        instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 10
        instance = new V3D_Line(pP1P0P1, pP1P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 11
        instance = new V3D_Line(pP1P0P1, pP1P1P0, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 12
        l = V3D_Line.Z_AXIS;
        instance = new V3D_Line(pP1P0P0, pP1P0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 9
        instance = new V3D_Line(pP0P1P0, pP0P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 10
        instance = new V3D_Line(pP1P1P0, pP1P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 11
        instance = new V3D_Line(pP1P0P1, pP1P1P0, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 12
        l = new V3D_Line(pP1P1P1, pN1N1N1, oom, rm);
        instance = new V3D_Line(pP1N1P1, pN1P1N1, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 13
        Math_BigRational a = P0_1E12.add(P1E12);
        Math_BigRational b = N0_1E12.add(N1E12);
        Math_BigRational a1 = P0_1E12.add(P1E12).add(1);
        Math_BigRational b1 = N0_1E12.add(N1E12).add(1);
        l = new V3D_Line(new V3D_Point(e, a, a, a), new V3D_Point(e, b, b, b), oom, rm);
        instance = new V3D_Line(new V3D_Point(e, a1, a, a), new V3D_Point(e, b1, b, b), oom, rm);
        assertTrue(instance.isParallel(l, oom, rm)); // Right answer, but for the wrong reason!
        // Test 14
        a = P0_1E12.add(P1E12);
        b = N0_1E12.add(N1E12);
        a1 = P0_1E12.add(P1E12).add(10);
        b1 = N0_1E12.add(N1E12).add(10);
        l = new V3D_Line(new V3D_Point(e, a, a, a), new V3D_Point(e, b, b, b), oom, rm);
        instance = new V3D_Line(new V3D_Point(e, a1, a, a), 
                new V3D_Point(e, b1, b, b), oom, rm);
        assertTrue(instance.isParallel(l, oom, rm)); // Right answer, but for the wrong reason!
    }

    /**
     * Test of getIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Line instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        //l = new V3D_Line(N1N1N1, P1P1P1);
        l = new V3D_Line(pP1P1P1, pN1N1N1, oom, rm);
        //instance = new V3D_Line(N1P1N1, P1N1P1);
        instance = new V3D_Line(pP1N1P1, pN1P1N1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(pP1P1P0, pP1P1P2, oom, rm);
        expResult = pP1P1P1;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        expResult = pP0P0P0;
        instance = new V3D_Line(pN1N1P0, pP1P1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 4
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(e, new V3D_Vector(P3, P1, P1), new V3D_Vector(P1, P3, P3));
        expResult = pP2P2P2;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(pN1N1P0, pP1P1P0, oom, rm);
        instance = new V3D_Line(e, new V3D_Vector(P3, P3, P0), new V3D_Vector(P3, P3, N1));
        expResult = new V3D_Point(e, P3, P3, P0);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 6
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(pP1N1N1, pN1P1P1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 7
        l = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        instance = new V3D_Line(pP1N1N1, pN1P1P1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 8
        l = new V3D_Line(pN1N1N1, pP0P0P0, oom, rm);
        instance = new V3D_Line(pP1N1N1, pN1P1P1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 9
        l = new V3D_Line(pN2N2N2, pN1N1N1, oom, rm);
        instance = new V3D_Line(pP1N1N1, pP0P0P0, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 10
        l = new V3D_Line(pN2N2N2, pN1N1N1, oom, rm);
        instance = new V3D_Line(pP0P0P0, pN1P1P1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 11
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        expResult = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(e, new V3D_Vector(N3, N3, N3), new V3D_Vector(N4, N4, N4));
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 12 to 14
        // v.dx = 0, v.dy != 0, v.dz !=0
        // Test 11
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        expResult = pP0P0P0;
        instance = new V3D_Line(pP0P0P0, pP0P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 15
        l = new V3D_Line(pP0N1N1, pP2P1P1, oom, rm);
        expResult = pP1P0P0;
        instance = new V3D_Line(pP1P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 16
        l = new V3D_Line(e, P0N1P1, new V3D_Vector(P2, P1, P3));
        expResult = pP1P0P2;
        instance = new V3D_Line(e, new V3D_Vector(P1, P0, P2),
                new V3D_Vector(P1, P1, P3));
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 17 to 18
        // v.dx != 0, v.dy = 0, v.dz = 0
        // Test 17
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        expResult = pP0P0P0;
        instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 18
        l = new V3D_Line(pP0N1N1, pP2P1P1, oom, rm);
        expResult = pP1P0P0;
        instance = new V3D_Line(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 19
        l = new V3D_Line(pP0N1P0, pP2P1P2, oom, rm);
        expResult = pP1P0P1;
        instance = new V3D_Line(pP1P0P1, pP2P0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 20 to 21
        // v.dx != 0, v.dy = 0, v.dz != 0
        // Test 20
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        expResult = pP0P0P0;
        instance = new V3D_Line(pP0P0P0, pP1P0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 21
        l = new V3D_Line(pP0N1N1, pP2P1P1, oom, rm);
        expResult = pP1P0P0;
        instance = new V3D_Line(pP1P0P0, pP2P0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 22
        l = new V3D_Line(e, P0P1N1, new V3D_Vector(P2, P3, P1));
        expResult = pP1P2P0;
        instance = new V3D_Line(pP1P2P0, pP2P2P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Line.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Line instance;
        boolean result;
        // Test 1
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(pN1P1N1, pP1N1P1, oom, rm);
        result = instance.isIntersectedBy(l, oom, rm);
        assertTrue(result);
        // Test 2
        /**
         * This test fails, the lines don't intersect, but to be sure a further
         * test is needed! That further test might best involve calculating the
         * intersection and if it is not null, then the result is true (in other
         * words, there is an intersection as it has been computed)!
         */
//        l = new V3D_Line(P0N1N1, P1P1P1, oom, rm);
//        instance = new V3D_Line(N1P1N1, P1N1P1, oom, rm);
//        result = instance.isIntersectedBy(l, oom, rm);
//        assertFalse(result);
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals_V3D_Line() {
        System.out.println("equals");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        assertTrue(instance.equals(l));
        // Test 2
        instance = new V3D_Line(pP1P1P1, pP0P0P0, oom, rm);
        assertTrue(instance.equals(l));
        // Test 3
        l = V3D_Line.X_AXIS;
        instance = V3D_Line.X_AXIS;
        assertTrue(instance.equals(l));
        // Test 4
        instance = V3D_Line.Y_AXIS;
        assertFalse(instance.equals(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Line. A useful tool for
     * creating test cases: https://www.mathepower.com/en/lineintersection.php
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        V3D_Geometry expResult = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Line(pP1P1P1, pP0P0P0, oom, rm);
        expResult = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        //instance = new V3D_Line(P0P1P0, P0N1P0, oom, rm);
        instance = new V3D_Line(pP0N1P0, pP0P1P0, oom, rm);
        l = new V3D_Line(pP1P1P1, pP0P0P0, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Line(pN1P1P1, pP1N1P1, oom, rm);
        l = new V3D_Line(pP0P2P1, pP1P1P1, oom, rm);
        //expResult = null;
        result = instance.getIntersection(l, oom, rm);
        //System.out.println(result);
        assertNull(result);

        // Test 5
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(pN1P1P1, pP1N1N1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 6
        oom = -3;
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        instance = new V3D_Line(pN1P1P1, pP1N1N1, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 7
        l = new V3D_Line(new V3D_Point(e, N1.add(P0_1), N1.add(P0_1), N1), 
                new V3D_Point(e, P1.add(P0_1), P1.add(P0_1), P1), oom, rm);
        instance = new V3D_Line(new V3D_Point(e, N1.add(P0_1), P1.add(P0_1), P1),
                new V3D_Point(e, P1.add(P0_1), N1.add(P0_1), N1), oom, rm);
        expResult = new V3D_Point(e, P0.add(P0_1), P0.add(P0_1), P0);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isParallelToX0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 1
        instance = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 2
        instance = new V3D_Line(pP0P0P1, pP1P1P0, oom, rm);
        assertFalse(instance.isParallelToX0(oom, rm));
    }

    /**
     * Test of isParallelToY0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P1, pP0P0N1, oom, rm);
        assertTrue(instance.isParallelToY0(oom, rm));
    }

    /**
     * Test of isParallelToZ0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToZ0() {
        System.out.println("isParallelToZ0");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P1, pP1P1P1, oom, rm);
        assertTrue(instance.isParallelToZ0(oom, rm));
    }

//    /**
//     * Test of isEnvelopeIntersectedBy method, of class V3D_Line.
//     */
//    @Test
//    public void testIsEnvelopeIntersectedBy() {
//        System.out.println("isEnvelopeIntersectedBy");
//        V3D_Line l = new V3D_Line(pP0P0P0, pP1P1P1);
//        V3D_Line instance = new V3D_Line(pP0P0P1, pP0P0N1);
//        assertTrue(instance.isEnvelopeIntersectedBy(l, oom, rm));
//        // Test 2
//        l = new V3D_Line(pP0P0P1, pP0P1P1);
//        instance = new V3D_Line(pP0P0N1, pP0P1N1);
//        assertFalse(instance.isEnvelopeIntersectedBy(l, oom, rm));
//    }
//    /**
//     * Test of translate method, of class V3D_Line.
//     */
//    @Test
//    public void testApply() {
//        System.out.println("translate");
//        int oom, rm = -1;
//        V3D_Vector v = V3D_Vector.I;
//        V3D_Line instance = V3D_Line.X_AXIS;
//        V3D_Line expResult = V3D_Line.X_AXIS;
//        V3D_Line result = instance.translate(v, oom, rm);
//        assertTrue(expResult.equals(result));
//        // Test 2
//        instance = V3D_Line.Y_AXIS;
//        expResult = new V3D_Line(P1P0P0, P1P1P0, oom, rm);
//        result = instance.translate(v, oom, rm);
//        assertTrue(expResult.equals(result));
//        // Test 3
//        instance = V3D_Line.Z_AXIS;
//        expResult = new V3D_Line(P1P0P0, P1P0P1, oom, rm);
//        result = instance.translate(v, oom, rm);
//        assertTrue(expResult.equals(result));
//    }
    /**
     * Test of getAsMatrix method, of class V3D_Line.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = V3D_Line.X_AXIS;
        Math_BigRational[][] m = new Math_BigRational[2][3];
        m[0][0] = Math_BigRational.ZERO;
        m[0][1] = Math_BigRational.ZERO;
        m[0][2] = Math_BigRational.ZERO;
        m[1][0] = Math_BigRational.ONE;
        m[1][1] = Math_BigRational.ZERO;
        m[1][2] = Math_BigRational.ZERO;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p;
        V3D_Line instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = pP0P0P0;
        instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Line(pP0P1P0, pP1P1P0, oom, rm);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        p = pP1P1P1;
        instance = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        oom = - 4;
        p = pP0P1P0;
        instance = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        int ooms = Math_BigRationalSqrt.getOOM(Math_BigRational.TWO, oom);
        if (ooms > 0) {
            ooms = 0;
        }
        MathContext mc = new MathContext(-ooms);
        expResult = Math_BigRational.valueOf(new Math_BigRationalSqrt(
                Math_BigRational.TWO, oom, rm).toBigDecimal(oom, rm)).divide(2)
                .toBigDecimal(mc);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5 https://math.stackexchange.com/a/1658288/756049
        p = pP1P1P1;
        oom = -3;
        Math_BigRational third = Math_BigRational.valueOf(1, 3);
        instance = new V3D_Line(e, new V3D_Vector(N2, N4, P5), new V3D_Vector(N1, N2, P3));
        V3D_Point p2 = new V3D_Point(e, third, Math_BigRational.valueOf(2, 3), third);
        expResult = p2.getDistance(p, oom, rm);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Point_int() {
        System.out.println("getLineOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt;
        V3D_Line instance;
        V3D_LineSegment expResult;
        V3D_Geometry result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getLineOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Line(pP1N1P0, pP1P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        //result = instance.getLineOfIntersection(pt, oom, rm);
        //System.out.println(result);
        result = instance.getLineOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Line. No test: Test
     * covered by {@link #testGetLineOfIntersection_V3D_Point()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt = pP2P0P0;;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP0P2P2, oom, rm);
        V3D_Point expResult = pP0P0P0;
        V3D_Point result = instance.getPointOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        // ...
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line_int() {
        System.out.println("getLineOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l0 = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        V3D_Line l1 = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        l1 = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertNull(result);
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Ray_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(r, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Line(pP0P1P0, pP1P1P0, oom, rm);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(r, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Line(pP0P2P0, pP1P2P0, oom, rm);
        expResult = BigDecimal.valueOf(2);
        result = instance.getDistance(r, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(r, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(r, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        expResult = new Math_BigRationalSqrt(2L, oom, rm).getSqrt(oom, rm).divide(2L).toBigDecimal(oom, rm);
        //result = instance.getDistance(r, oom, rm);
        //System.out.println(result);
        result = instance.getDistance(r, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP1N2P0, pP1P2P0, oom, rm);
        V3D_Line instance = V3D_Line.Z_AXIS;
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V3D_LineSegment(pP1N2N2, pP1P2P2, oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Line instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1 
        // https://math.stackexchange.com/questions/2213165/find-shortest-distance-between-lines-in-3d
        //l = new V3D_Line(new V3D_Vector(P2, P6, N9), oom, rm, new V3D_Vector(P3, P4, N4));
        l = new V3D_Line(new V3D_Vector(P2, P6, N9), new V3D_Vector(P3, P4, N4), e);
        //instance = new V3D_Line(new V3D_Vector(N1, N2, P3), oom, rm, new V3D_Vector(P2, N6, P1));
        instance = new V3D_Line(new V3D_Vector(N1, N2, P3), new V3D_Vector(P2, N6, P1), e);
        expResult = new BigDecimal("4.7");
        result = instance.getDistance(l, -1, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        l = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        oom = -4;
        instance = new V3D_Line(e, P1N1P0, new V3D_Vector(P2, P0, P0));
        expResult = BigDecimal.valueOf(2).sqrt(new MathContext(1 - oom, rm));
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V3D_Line.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Line instance = V3D_Line.X_AXIS;
        String expResult = "V3D_Line\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_Point\n"
                + " (\n"
                + "  oom=-3\n"
                + "  ,\n"
                + "  offset=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + "  ,\n"
                + "  rel=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Point\n"
                + " (\n"
                + "  oom=-3\n"
                + "  ,\n"
                + "  offset=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + "  ,\n"
                + "  rel=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + " )\n"
                + " ,\n"
                + " v=null\n"
                + ")";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V3D_Line.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        V3D_Line instance = V3D_Line.X_AXIS;
        String expResult = "oom=-3\n"
                + ",\n"
                + "offset=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "p=V3D_Point\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
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
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + ")\n"
                + ",\n"
                + "q=V3D_Point\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " rel=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toStringFields(pad);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPV method, of class V3D_Line.
     */
    @Test
    public void testGetPV() {
        System.out.println("getPV");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.getPV(oom, rm);
        assertTrue(expResult.isScalarMultiple(result, oom, rm));
    }

    /**
     * Test of getP method, of class V3D_Line.
     */
    @Test
    public void testGetP_int() {
        System.out.println("getP");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Point expResult = pP0P0P0;
        V3D_Point result = instance.getP();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQV method, of class V3D_Line.
     */
    @Test
    public void testGetQV() {
        System.out.println("getQV");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Vector expResult = new V3D_Vector(1, 0, 0);
        V3D_Vector result = instance.getQV(oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getV method, of class V3D_Line.
     */
    @Test
    public void testGetV_int_RoundingMode() {
        System.out.println("getV");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Vector expResult = new V3D_Vector(1, 0, 0);
        V3D_Vector result = instance.getV(oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Line(pP0P0P0, new V3D_Point(e, P0_1E12, P0, P0), oom, rm);
        expResult = new V3D_Vector(P0_1E12, P0, P0);
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
        oom -= 1;
        result = instance.getV(oom, rm);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Line covered by
     * {@link V3D_RayTest#testIsIntersectedBy_V3D_Line()}.
     */
    @Test
    public void testIsIntersectedBy_V3D_Ray_int() {
        System.out.println("isIntersectedBy");
//        V3D_Ray r = null;
//        int oom = 0;
//        V3D_Line instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(r, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Line covered by null null
     * null null null null null null null null null null null null     {@link V3D_RayTest#testGetIntersection_V3D_Line().
     */
    @Test
    public void testGetIntersection_V3D_Ray_int() {
        System.out.println("getIntersection");
//        V3D_Ray r = null;
//        int oom = 0;
//        V3D_Line instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(r, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of rotate method, of class V3D_Line.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Vector axisOfRotation = new V3D_Vector(1, 0, 0);
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        int oomt = oom - 2;
        Math_BigRational Pi = Math_BigRational.valueOf(
                new Math_BigDecimal().getPi(oomt, RoundingMode.HALF_UP));
        Math_BigRational theta = Pi.divide(2);
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Line expResult = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        instance.rotate(axisOfRotation, theta, oom, rm);
        assertEquals(expResult, instance);
        // Test 2
        axisOfRotation = new V3D_Vector(0, 1, 0).getUnitVector(oom, rm);
        theta = Pi.divide(2);
        instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        expResult = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        instance.rotate(axisOfRotation, theta, oom, rm);
        assertEquals(expResult, instance);
        // Test 3
        axisOfRotation = new V3D_Vector(0, 1, 0).getUnitVector(oom, rm);
        theta = Pi.divide(2);
        instance = new V3D_Line(e, new V3D_Vector(0, 0, 0), new V3D_Vector(5, 0, 0));
        expResult = new V3D_Line(e, new V3D_Vector(0, 0, 0), new V3D_Vector(0, 0, 5));
        instance.rotate(axisOfRotation, theta, oom, rm);
        assertEquals(expResult, instance);
        // Test 4
        axisOfRotation = new V3D_Vector(0, 1, 0).getUnitVector(oom, rm);
        theta = Pi;
        instance = new V3D_Line(e, new V3D_Vector(3, 2, 0), new V3D_Vector(5, 0, 0));
        expResult = new V3D_Line(e, new V3D_Vector(-3, 2, 0), new V3D_Vector(-5, 0, 0));
        instance.rotate(axisOfRotation, theta, oom, rm);
        assertEquals(expResult, instance);
    }

    /**
     * Test of isCoincident method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoincident() {
        System.out.println("isCoincident");
        V3D_Point[] points = new V3D_Point[2];
        points[0] = pP0P0P0;
        points[1] = pP0P0P0;
        assertTrue(V3D_Point.isCoincident(points));
        points[1] = pP0P0P1;
        assertFalse(V3D_Point.isCoincident(points));
        points[0] = pP0P0P1;
        assertTrue(V3D_Point.isCoincident(points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_Line_V3D_PointArr() {
        System.out.println("isCollinear");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Point[] points = new V3D_Point[2];
        // Test 1
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, l, points));
        // Test 2
        points[1] = pN2N2N1;
        assertFalse(V3D_Line.isCollinear(e, oom, rm, l, points));
        // Test 3
        points[0] = pN1N2N1;
        assertFalse(V3D_Line.isCollinear(e, oom, rm, l, points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_PointArr() {
        System.out.println("isCollinear");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point[] points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pP2P2P1;
        points[2] = pP2P2P0;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2P2N1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2P2N2;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        // P2P1*
        points[0] = pP2P1P2;
        points[1] = pP2P1P1;
        points[2] = pP2P1P0;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2P1N1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2P1N2;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        // P2P0*
        points[0] = pP2P0P2;
        points[1] = pP2P0P1;
        points[2] = pP2P0P0;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2P0N1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2P0N2;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        // P2N1*
        points[0] = pP2N1P2;
        points[1] = pP2N1P1;
        points[2] = pP2N1P0;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2N1N1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2N1N2;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        // P2N2*
        points[0] = pP2N2P2;
        points[1] = pP2N2P1;
        points[2] = pP2N2P0;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2N2N1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[2] = pP2N2N2;
        // Others
        points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pN1N1N1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
        points[1] = pP1P1P0;
        assertFalse(V3D_Line.isCollinear(e, oom, rm, points));
        points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pP1P1P1;
        assertTrue(V3D_Line.isCollinear(e, oom, rm, points));
    }

    /**
     * Test of getLine method, of class V3D_Geometrics. No test needed.
     */
    @Test
    @Disabled
    public void testGetLine() {
        System.out.println("getLine");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point[] points = null;
        V3D_Line expResult = null;
        V3D_Line result = V3D_Line.getLine(e, oom, rm, points);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}

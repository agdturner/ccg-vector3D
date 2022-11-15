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
package uk.ac.leeds.ccg.v3d.geometry.d;

import uk.ac.leeds.ccg.v3d.geometry.*;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test of V3D_VectorDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VectorDoubleTest extends V3D_TestDouble {

    public V3D_VectorDoubleTest() {
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
     * Test of getDotProduct method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        V3D_VectorDouble v = P0P1P0;
        V3D_VectorDouble instance = P1P0P0;
        double expResult = 0d;
        double result = instance.getDotProduct(v);
        assertTrue(expResult == result);
        // Test 2
        v = P0P1P0;
        instance = P0P0N1;
        result = instance.getDotProduct(v);
        assertTrue(expResult == result);
        // Test 3
        v = P1P1P1;
        instance = N1N1N1;
        expResult = -3d;
        result = instance.getDotProduct(v);
        assertTrue(expResult == result);
    }

    /**
     * Test of isOrthogonal method, of class V3D_VectorDouble.
     */
    @Test
    public void testIsOrthogonal() {
        System.out.println("isOrthogonal");
        V3D_VectorDouble v;
        V3D_VectorDouble instance;
        // Test 1
        v = P1P0P0;
        instance = P0P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v));
        // Test 2
        v = P1P1P0;
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v));
        // Test 3
        v = P1P1P1;
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P1;
        assertTrue(instance.isOrthogonal(v));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P0N1;
        assertFalse(instance.isOrthogonal(v));
    }

    /**
     * Test of getMagnitude method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetMagnitude_0args() {
        System.out.println("getMagnitude");
        V3D_VectorDouble instance = P0P0P0;
        double expResult = 0d;
        double result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 1
        instance = P1P1P0;
        expResult = Math.sqrt(2d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 2
        instance = P1P1P1;
        expResult = Math.sqrt(3d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_VectorDouble(10d, 10d, 10d);
        expResult = Math.sqrt(300d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 4
        instance = new V3D_VectorDouble(3d, 4d, -4d);
        expResult = Math.sqrt(41d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 5
        instance = new V3D_VectorDouble(7d, 8d, -4d);
        expResult = Math.sqrt(49d + 64d + 16);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
    }

    /**
     * Test of initMagnitude method, of class V3D_VectorDouble.
     */
    @Test
    public void testInitMagnitude() {
        System.out.println("initMagnitude");
        assertTrue(true); // No need to test.
    }

    /**
     * Test of isScalarMultiple method, of class V3D_VectorDouble.
     */
    @Test
    public void testIsScalarMultiple() {
        System.out.println("isScalarMultiple");
        V3D_VectorDouble v = P0P0P0;
        V3D_VectorDouble instance = P1P1P1;
        assertFalse(instance.isScalarMultiple(v));
        // Test 2
        v = N1N1N1;
        instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v));
        // Test 3
        v = P1P0P0;
        instance = P0P1P1;
        assertFalse(instance.isScalarMultiple(v));
        // Test 4
        v = new V3D_VectorDouble(0d, 1d, 10000d);
        instance = new V3D_VectorDouble(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(v));
    }

    /**
     * Test of getCrossProduct method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        V3D_VectorDouble v = P1P1P1;
        V3D_VectorDouble instance = N1N1N1;
        V3D_VectorDouble expResult = P0P0P0;
        V3D_VectorDouble result = instance.getCrossProduct(v);
        assertTrue(expResult.equals(result));
        // Test 2
        v = P1P1P1;
        instance = P1P1P0;
        expResult = P1N1P0;
        result = instance.getCrossProduct(v);
        assertTrue(expResult.equals(result));
        // Test 3
        v = P1P1P0;
        instance = P1P1P1;
        expResult = N1P1P0;
        result = instance.getCrossProduct(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_VectorDouble.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = V3D_VectorDouble.I;
        V3D_VectorDouble instance = V3D_VectorDouble.I;
        assertTrue(instance.equals(o));
        // Test 2
        instance = V3D_VectorDouble.J;
        assertFalse(instance.equals(o));
        // Test 3
        o = P0P0P0;
        instance = P0P0P0;
        assertTrue(instance.equals(o));
        // Test 4
        o = P0P0P0;
        instance = P1P0P0;
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isReverse method, of class V3D_VectorDouble.
     */
    @Test
    public void testIsReverse() {
        System.out.println("isReverse");
        V3D_VectorDouble v = V3D_VectorDouble.I;
        V3D_VectorDouble instance = new V3D_VectorDouble(-1, 0, 0);
        assertTrue(instance.isReverse(v));
        // Test 2
    }

    /**
     * Test of isZero method, of class V3D_VectorDouble.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        int oom = -1;
        V3D_VectorDouble instance = new V3D_VectorDouble(0, 0, 0);
        assertTrue(instance.isZero());
        // Test 2
        instance = new V3D_VectorDouble(1, 0, 0);
        assertFalse(instance.isZero());
    }

    /**
     * Test of multiply method, of class V3D_VectorDouble.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        double s = 0d;
        V3D_VectorDouble instance = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble expResult = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble result = instance.multiply(s);
        assertTrue(expResult.equals(result));
        // Test 2
        s = 0d;
        instance = new V3D_VectorDouble(10, 10, 10);
        expResult = new V3D_VectorDouble(0, 0, 0);
        result = instance.multiply(s);
        assertTrue(expResult.equals(result));
        // Test 3
        s = 2d;
        instance = new V3D_VectorDouble(10, 10, 10);
        expResult = new V3D_VectorDouble(20, 20, 20);
        result = instance.multiply(s);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of add method, of class V3D_VectorDouble.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        V3D_VectorDouble instance = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble v = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble expResult = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_VectorDouble(0, 0, 0);
        v = new V3D_VectorDouble(1, 1, 1);
        expResult = new V3D_VectorDouble(1, 1, 1);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_VectorDouble(2, 3, 4);
        v = new V3D_VectorDouble(7, 1, 11);
        expResult = new V3D_VectorDouble(9, 4, 15);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_VectorDouble(-2, 3, -4);
        v = new V3D_VectorDouble(7, 1, 11);
        expResult = new V3D_VectorDouble(5, 4, 7);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class V3D_VectorDouble.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        V3D_VectorDouble instance = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble v = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble expResult = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble result = instance.subtract(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_VectorDouble(0, 0, 0);
        v = new V3D_VectorDouble(1, 1, 1);
        expResult = new V3D_VectorDouble(-1, -1, -1);
        result = instance.subtract(v);
        //assertTrue(expResult.compareTo(result) == 0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of reverse method, of class V3D_VectorDouble.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        V3D_VectorDouble instance = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble expResult = new V3D_VectorDouble(0, 0, 0);
        V3D_VectorDouble result = instance.reverse();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_VectorDouble(1, 1, 1);
        expResult = new V3D_VectorDouble(-1, -1, -1);
        result = instance.reverse();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMagnitudeSquared method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetMagnitudeSquared() {
        System.out.println("getMagnitudeSquared");
        V3D_VectorDouble instance = new V3D_VectorDouble(0, 0, 0);
        double expResult = 0d;
        double result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_VectorDouble(1, 1, 1);
        expResult = 3d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_VectorDouble(2, 2, 2);
        expResult = 12d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
    }

    /**
     * Test of getUnitVector method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        V3D_VectorDouble instance = V3D_VectorDouble.I;
        V3D_VectorDouble expResult = V3D_VectorDouble.I;
        V3D_VectorDouble result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared() == 1d);
        // Test 2
        instance = new V3D_VectorDouble(100d, 0d, 0d);
        expResult = V3D_VectorDouble.I;
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared() == 1d);
        // Test 3
        instance = new V3D_VectorDouble(100d, 100d, 0d);
        expResult = new V3D_VectorDouble(100d/Math.sqrt(20000d), 100d/Math.sqrt(20000d), 0d);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        //assertTrue(result.getMagnitudeSquared().compareTo(double.ONE) != 1);
        // Test 4
        instance = new V3D_VectorDouble(0d, 100d, 100d);
        expResult = new V3D_VectorDouble(0d, 100d/Math.sqrt(20000d), 100d/Math.sqrt(20000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 5
        instance = new V3D_VectorDouble(100, 100, 100);
        expResult = new V3D_VectorDouble(100d/Math.sqrt(30000d), 100d/Math.sqrt(30000d), 100d/Math.sqrt(30000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 6
        instance = new V3D_VectorDouble(-100, 0, 0);
        expResult = new V3D_VectorDouble(-1,0,0);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 7
        instance = new V3D_VectorDouble(-100, -100, 0);
        expResult = new V3D_VectorDouble(-100d/Math.sqrt(20000d), -100d/Math.sqrt(20000d), 0d);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 8
        instance = new V3D_VectorDouble(0, -100, -100);
        expResult = new V3D_VectorDouble(0d, -100d/Math.sqrt(20000d), -100d/Math.sqrt(20000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 9
        instance = new V3D_VectorDouble(-100, -100, -100);
        expResult = new V3D_VectorDouble(-100d/Math.sqrt(30000d), -100d/Math.sqrt(30000d), -100d/Math.sqrt(30000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDX method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDX() {
        System.out.println("getDX");
        V3D_VectorDouble instance = V3D_VectorDouble.I;
        double expResult = 1d;
        double result = instance.dx;
        assertTrue(expResult == result);
        // Test 2
        instance = V3D_VectorDouble.I.reverse();
        expResult = -1d;
        result = instance.dx;
        assertTrue(expResult == result);
    }

    /**
     * Test of getDY method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDY() {
        System.out.println("getDY");
        V3D_VectorDouble instance = V3D_VectorDouble.J;
        double expResult = 1d;
        double result = instance.dy;
        assertTrue(expResult==result);
        // Test 2
        instance = V3D_VectorDouble.J.reverse();
        expResult = -1d;
        result = instance.dy;
        assertTrue(expResult==result);
    }

    /**
     * Test of getDZ method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDZ() {
        System.out.println("getDZ");
        V3D_VectorDouble instance = V3D_VectorDouble.K;
        double expResult = 1d;
        double result = instance.dz;
        assertTrue(expResult==result);
        // Test 2
        instance = V3D_VectorDouble.K.reverse();
        expResult = -1d;
        result = instance.dz;
        assertTrue(expResult==result);
    }

    /**
     * Test of divide method, of class V3D_VectorDouble.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        double s = 2d;
        V3D_VectorDouble instance = V3D_VectorDouble.I;
        V3D_VectorDouble expResult = new V3D_VectorDouble(
                new V3D_PointDouble(0.5d, 0d, 0d));
        V3D_VectorDouble result = instance.divide(s);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAngle method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetAngle() {
        System.out.println("getAngle");
        V3D_VectorDouble v;
        V3D_VectorDouble instance;
        double expResult;
        double result;
        // Test 1
        v = V3D_VectorDouble.I;
        instance = V3D_VectorDouble.J;
        result = instance.getAngle(v);
        expResult = Math.PI / 2d;
        assertTrue(expResult== result);
    }

    /**
     * Test of getDirection method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        V3D_VectorDouble instance = V3D_VectorDouble.I;
        int expResult = 1;
        int result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 2
        instance = V3D_VectorDouble.J;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 3
        instance = V3D_VectorDouble.K;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 4
        instance = V3D_VectorDouble.I.reverse();
        expResult = 5;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 5
        instance = V3D_VectorDouble.J.reverse();
        expResult = 3;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 6
        instance = V3D_VectorDouble.K.reverse();
        expResult = 2;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 7
        //...
    }

    /**
     * Test of toString method, of class V3D_VectorDouble.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_VectorDouble v = V3D_VectorDouble.ZERO;
        String result = v.toString();
        String expResult = "V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0)";
//        String expResult = "V3D_VectorDouble\n"
//                + "(\n"
//                + " dx=double(x=0, sqrtx=0, oom=0),\n"
//                + " dy=double(x=0, sqrtx=0, oom=0),\n"
//                + " dz=double(x=0, sqrtx=0, oom=0)\n"
//                + ")";
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_VectorDouble.
     */
    @Test
    public void testEquals_V3D_VectorDouble() {
        System.out.println("equals");
        V3D_VectorDouble v = new V3D_VectorDouble(1, 1, 1);
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        assertTrue(instance.equals(v));
        instance = new V3D_VectorDouble(1, 1, 0);
        assertFalse(instance.equals(v));
    }

    /**
     * Test of getDX method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDX_int() {
        System.out.println("getDX");
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        double expResult = 1d;
        assertTrue(expResult == instance.dx);
    }

    /**
     * Test of getDY method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDY_int() {
        System.out.println("getDY");
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        double expResult = 1d;
        assertTrue(expResult==instance.dy);
    }

    /**
     * Test of getDZ method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDZ_int() {
        System.out.println("getDZ");
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        double expResult = 1d;
        assertTrue(expResult==instance.dz);
    }

    /**
     * Test of getDX method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDX_0args() {
        System.out.println("getDX");
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        double expResult = 1d;
        double result = instance.dx;
        assertTrue(expResult==result);
    }

    /**
     * Test of getDY method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDY_0args() {
        System.out.println("getDY");
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        double expResult = 1d;
        double result = instance.dy;
        assertTrue(expResult==result);
    }

    /**
     * Test of getDZ method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetDZ_0args() {
        System.out.println("getDZ");
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 1, 1);
        double expResult = 1d;
        double result = instance.dz;
        assertTrue(expResult==result);
    }

    /**
     * Test of getMagnitude0 method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetMagnitude0() {
        System.out.println("getMagnitude0");
    }

    /**
     * Test of getMagnitude method, of class V3D_VectorDouble.
     */
    @Test
    public void testGetMagnitude_int() {
        System.out.println("getMagnitude");
    }

    /**
     * Test of rotate method, of class V3D_VectorDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_VectorDouble axisOfRotation = new V3D_VectorDouble(0, 1, 0);
        double epsilon = 1/10000d;
        double Pi = Math.PI;
        double theta = Pi / 2d;
        V3D_VectorDouble instance = new V3D_VectorDouble(1, 0, 0);
        V3D_VectorDouble expResult = new V3D_VectorDouble(0, 0, -1);
        V3D_VectorDouble result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        // From Example 2: https://graphics.stanford.edu/courses/cs348a-17-winter/Papers/quaternion.pdf
        axisOfRotation = new V3D_VectorDouble(1, 1, 1).getUnitVector();
        theta = Pi * 2d / 3d;
        instance = new V3D_VectorDouble(1, 0, 0);
        expResult = new V3D_VectorDouble(0, 1, 0);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
//        // Test 3 Fails :(
//        // From Example 1: https://www.imsc.res.in/~knr/131129workshop/writeup_knr.pdf
//        axisOfRotation = new V3D_VectorDouble(0, 1, 0);
//        theta = Pi.divide(3);
//        instance = new V3D_VectorDouble(1, -1, 2);
//        double sqrt3 = new double(3).getSqrt();
//        double x = (double.TEN.add(
//                double.valueOf(4).multiply(sqrt3))).divide(8);
//        double y = (double.ONE.add(
//                double.valueOf(2).multiply(sqrt3))).divide(8);
//        double z = (double.valueOf(14).subtract(
//                double.valueOf(3).multiply(sqrt3))).divide(8);        
//        expResult = new V3D_VectorDouble(x, y, z);
//        result = instance.rotate(axisOfRotation, theta);
//        assertTrue(expResult.equals(result));
        // Test 4
        // From Case 1 https://www.tobynorris.com/work/prog/csharp/quatview/help/case_1.htm
        axisOfRotation = new V3D_VectorDouble(0, 0, 1);
        theta = -Pi / 6d;
        instance = new V3D_VectorDouble(0.6d, 0.8d, 0d);
        expResult = new V3D_VectorDouble(0.9196d, 0.3928d, 0d);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
//        // Test 5
//        // From Case 2 https://www.tobynorris.com/work/prog/csharp/quatview/help/case_1.htm
//        oom = -4;
//        axisOfRotation = new V3D_VectorDouble(0, 0, 1);
//        //theta = Pi.divide(6).negate().subtract(
//        theta = double.valueOf("53.13").divide(double.valueOf(180)).multiply(Pi).negate();
//        instance = new V3D_VectorDouble(0.6, 0.8, 0);
//        expResult = new V3D_VectorDouble(0.1196, 0.9928, 0);
//        result = instance.rotate(axisOfRotation, theta);
//        assertTrue(expResult.equals(result));
        // Test 6
        axisOfRotation = new V3D_VectorDouble(0, 0, 1);
        theta = Pi / 2d;
        instance = new V3D_VectorDouble(1, 0, 0);
        expResult = new V3D_VectorDouble(0, 1, 0);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
        // Test 7
        axisOfRotation = new V3D_VectorDouble(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_VectorDouble(1, 0, 0);
        expResult = new V3D_VectorDouble(0, 1, 0);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
        // Test 8
        axisOfRotation = new V3D_VectorDouble(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_VectorDouble(2, 0, 0);
        expResult = new V3D_VectorDouble(0, 2, 0);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
        // Test 9
        axisOfRotation = new V3D_VectorDouble(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_VectorDouble(3, 1, 0);
        expResult = new V3D_VectorDouble(1, 3, 0);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
        // Test 10
        axisOfRotation = new V3D_VectorDouble(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_VectorDouble(3, 2, 1);
        expResult = new V3D_VectorDouble(2, 3, -1);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
        // Test 11
        axisOfRotation = new V3D_VectorDouble(1, 1, 0).getUnitVector();
        theta = Pi * 2d;
        instance = new V3D_VectorDouble(3, 2, 1);
        expResult = new V3D_VectorDouble(3, 2, 1);
        result = instance.rotate(axisOfRotation, theta);
        assertTrue(expResult.equals(result, epsilon));
    }

}

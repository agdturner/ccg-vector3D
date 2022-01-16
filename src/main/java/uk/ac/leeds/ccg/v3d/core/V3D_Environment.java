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
package uk.ac.leeds.ccg.v3d.core;

import uk.ac.leeds.ccg.math.arithmetic.Math_BigInteger;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * V3D_Environment
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Environment {

    /**
     * 0
     */
    public static final Math_BigRational P0 = Math_BigRational.ZERO;
    
    /**
     * 1
     */
    public static final Math_BigRational P1 = Math_BigRational.ONE;
    
    /**
     * 2
     */
    public static final Math_BigRational P2 = Math_BigRational.TWO;
    
    /**
     * 3
     */
    public static final Math_BigRational P3 = Math_BigRational.valueOf(3);
    
    /**
     * -1
     */
    public static final Math_BigRational N1 = Math_BigRational.ONE.negate();

    /**
     * The default Order of Magnitude.
     */
    public static final int DEFAULT_OOM = -3;
    
    /**
     * An instance that helps with calculations involving Taylor series.
     */
    public final Math_BigInteger bI;

    /**
     * The Order of Magnitude for the precision.
     */
    public int oom;
    
    /**
     * Creates a new instance.
     */
    public V3D_Environment(){
        oom = V3D_Environment.DEFAULT_OOM;
        bI = new Math_BigInteger();
    }
    
    /**
     * Creates a new instance.
     *
     * @param oom What {@link #oom} is set to.
     * @param bI What {@link #bI} is set to.
     */
    public V3D_Environment(int oom, Math_BigInteger bI) {
        this.oom = oom;
        this.bI = bI;
    }
    
    /**
     * @param pad The padding.
     * @return A padded description.
     */
    public String toStringFields(String pad) {
        return pad + "oom=" + oom;
    }
    
}

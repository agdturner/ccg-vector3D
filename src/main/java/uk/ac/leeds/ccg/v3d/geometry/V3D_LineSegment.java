/*
 * Copyright 2019 Andy Turner, University of Leeds.
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

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;

/**
 * 3D representation of a finite length line (a line segment). The line begins
 * at the point {@link #p}, travels in the direction {@link #v} and ends at the
 * point {@link #q}. The "*" denotes a point in 3D and the line is shown with a
 * line of "e" symbols in the following depiction: {@code
 *                                       z
 *                          y           -
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |        /                e
 *                          |    z0-/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 * x - ---------------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |
 *                 /        |
 *                +         -
 *               z          y
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegment extends V3D_Line implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope of this.
     */
    protected V3D_Envelope en;

    /**
     * Stores the length of the line squared.
     */
    protected final BigRational len2;

    /**
     * Create a new instance.
     * 
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_LineSegment l) {
        super(l);
        len2 = p.getDistanceSquared(q);
    }

    /**
     * Create a new instance.
     * 
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_LineSegment(V3D_Point p, V3D_Point q, int oom) {
        super(p, q, oom);
        len2 = p.getDistanceSquared(q);
    }

    /**
     * Create a new instance.
     * 
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Line l) {
        super(l);
        len2 = p.getDistanceSquared(q);
    }

    /**
     * Create a new instance.
     * 
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Envelope.LineSegment l) {
        this(new V3D_Point(l.p), new V3D_Point(l.q), l.v.oom);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_LineSegment) {
            V3D_LineSegment l = (V3D_LineSegment) o;
            if (l.p.equals(p) && l.q.equals(q)) {
                return true;
            }
            if (l.p.equals(q) && l.q.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegment l) {
        return p.equals(l.p) && q.equals(l.q);
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @return {@code true} iff {@code this} is equal to {@code l} ignoring the
     * direction of {@link #v}.
     */
    public boolean equalsIgnoreDirection(V3D_LineSegment l) {
        if (equals(l)) {
            return true;
        } else {
            return p.equals(l.q) && q.equals(l.p);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.p);
        hash = 31 * hash + Objects.hashCode(this.q);
        return hash;
    }

    /**
     * @return {@code true} iff this line segment is effectively a point.
     */
    public boolean isPoint() {
        return p.equals(q);
    }

    /**
     * @param v The vector to apply to each coordinate of {@code this}.
     * @return a new V3D_LineSegment which is {@code this} with the {@code v}
     * applied.
     */
    @Override
    public V3D_LineSegment apply(V3D_Vector v) {
        return new V3D_LineSegment(p.apply(v), q.apply(v),
                Math.min(this.v.oom, v.oom));
    }

    /**
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength() {
        // The choice of -1 is arbitrary.
        return new Math_BigRationalSqrt(getLength2(), -1);
    }

    /**
     * @return The length of {@code this} squared.
     */
    public BigRational getLength2() {
        return p.getDistanceSquared(q);
    }

    /**
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(v.oom, p, q);
        }
        return en;
    }

    /**
     * @param p A point to test for intersection.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point p) {
        boolean ei = getEnvelope().isIntersectedBy(p.getEnvelope());
        if (ei) {
            if (super.isIntersectedBy(p)) {
                Math_BigRationalSqrt a = p.getDistance(this.p);
                if (a.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt b = p.getDistance(this.q);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt l = this.p.getDistance(this.q);
                if (a.add(b).compareTo(l) != 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param l A line segment to indicate intersection with this.
     * @param flag Used to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean flag) {
        boolean ei = getEnvelope().isIntersectedBy(l.getEnvelope());
        if (ei) {
            return super.isIntersectedBy(l);
        }
        return false;
    }

    /**
     * @param l A line to test for intersection within the specified tolerance.
     * @return true if p is within t of this given scale.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l) {
        V3D_Geometry i = super.getIntersection(l);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point) {
            return isIntersectedBy((V3D_Point) i);
        } else {
            return true;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        // Check if infinite lines intersect.
        V3D_Geometry i = new V3D_Line(this).getIntersection(new V3D_Line(l));
        if (i == null) {
            // There is no intersection.
            return i;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V3D_Point) {
            if (isIntersectedBy((V3D_Point) i)) {
                return i;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Line) {
            // If the lines are the same, then return this. 
            return this;
        } else {
            // There is no intersection.
            return null;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param flag To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean flag) {
        V3D_Envelope ren = this.getEnvelope().getIntersection(l.getEnvelope());
        if (ren == null) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_Geometry li = new V3D_Line(this).getIntersection(new V3D_Line(l));
        if (li == null) {
            return null;
        }
        if (li instanceof V3D_Point) {
            return li;
        }
        /**
         * Check the type of intersection. {@code
         * 1)   p ---------- q
         *         l.p ---------- l.q
         * 2)   p ------------------------ q
         *         l.p ---------- l.q
         * 3)        p ---------- q
         *    l.p ------------------------ l.q
         * 4)        p ---------- q
         *    l.p ---------- l.q
         * 5)   q ---------- p
         *         l.p ---------- l.q
         * 6)   q ------------------------ p
         *         l.p ---------- l.q
         * 7)        q ---------- p
         *    l.p ------------------------ l.q
         * 8)        q ---------- p
         *    l.p ---------- l.q
         * 9)   p ---------- q
         *         l.q ---------- l.p
         * 10)   p ------------------------ q
         *         l.q ---------- l.p
         * 11)       p ---------- q
         *    l.q ------------------------ l.p
         * 12)       p ---------- q
         *    l.q ---------- l.p
         * 13)  q ---------- p
         *         l.q ---------- l.p
         * 14)  q ------------------------ p
         *         l.q ---------- l.p
         * 15)       q ---------- p
         *    l.q ------------------------ l.p
         * 16)       q ---------- p
         *    l.q ---------- l.p
         * }
         */
        if (this.isIntersectedBy(l.p)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (this.isIntersectedBy(l.q)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                return l;
            } else {
                // Cases 1, 5, 16
                if (l.isIntersectedBy(this.p)) {
                    // Cases 5
                    return new V3D_LineSegment(l.p, this.p, v.oom);
                } else {
                    // Cases 1, 16
                    return new V3D_LineSegment(l.p, this.q, v.oom);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (this.isIntersectedBy(l.q)) {
                // Cases 4, 8, 9, 10, 11
                if (l.isIntersectedBy(this.p)) {
                    // Cases 4, 11, 13
                    if (l.isIntersectedBy(this.q)) {
                        // Cases 11
                        return this;
                    } else {
                        // Cases 4, 13
                        return new V3D_LineSegment(this.p, l.q, v.oom);
                    }
                } else {
                    // Cases 8, 9, 10
                    if (l.isIntersectedBy(this.q)) {
                        // Cases 8, 9
                        return new V3D_LineSegment(this.q, l.q, v.oom);
                    } else {
                        // Cases 10                      
                        return l;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                if (l.isIntersectedBy(this.p)) {
                    // Cases 3, 12, 15
                    if (l.isIntersectedBy(this.q)) {
                        // Cases 3, 15
                        return this;
                    } else {
                        // Cases 12                 
                        return new V3D_LineSegment(this.p, l.p, v.oom);
                    }
                } else {
                    // Cases 7
                    return this;
                }
            }
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return getEnvelope().isIntersectedBy(l);
    }
    
    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        BigDecimal d = super.getDistance(p, oom);
        BigDecimal l = this.getLength().toBigDecimal(oom);
        BigDecimal a = p.getDistance(this.p, oom);
        BigDecimal b = p.getDistance(this.q, oom);
        if (d.compareTo(a) == -1 && d.compareTo(b) == -1 && a.compareTo(l) == 1
                && b.compareTo(l) == 1) {
            return a.min(b);
        }
        return d;
    }
    
    /**
     * An implementation of this exists:
     * https://www.geometrictools.com/Documentation/DistanceLine3Line3.pdf
     * https://www.geometrictools.com/GTE/Mathematics/DistSegmentSegment.h
     *
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code this} to {@code l} at the {@code oom} 
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

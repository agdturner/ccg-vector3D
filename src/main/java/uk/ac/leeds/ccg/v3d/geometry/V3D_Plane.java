/*
 * Copyright 2020 CCG, University of Leeds.
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
import java.math.MathContext;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometrics.V3D_Geometrics;

/**
 * 3D representation of an infinite plane. The plane is defined by three points
 * {@link #p}, {@link #q} and {@link #r} that are not collinear, a normal vector
 * {@link #n} that is perpendicular to the plane, and two vectors; {@link #pq}
 * (which is the vector from {@link #p} to {@link #q}), and {@link #qr} (which
 * is the vector from {@link #q} to {@link #r}). The "*" denotes a point in 3D,
 * {@link #pq} is depicted with a line of "e" symbols, {@link #qr} is depicted
 * with a line of "f" symbols in the following depiction: {@code
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
 *             x2           |/         x1    e
 * x - --------/------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                    z2-/  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /  y2-|        e
 *                   /      | f f f * q=<x1,y1,z1>
 *                  f f f f f
 *          * f f f/        |
 *  r=<x2,y2,z2>  /         |
 *               +          -
 *              z           y
 * }
 *
 *
 * The equation of the plane is:
 * <ul>
 * <li>{@code A*(x-x0) + B*(y-y0) + C*(z-z0) = 0}</li>
 * <li>{@code A*(x) + B*(y) + C*(z) - D = 0 where D = -(A*x0 + B*y0 + C*z0)}</li>
 * </ul>
 * where:
 * <ul>
 * <li>{@code x}, {@code y}, and {@code z} are the coordinates from either
 * {@link #p}, {@link #q} or {@link #r}</li>
 * <li>{@code x0}, {@code y0} and {@code z0} represents any other point on the
 * plane</li>
 * <li>{@code A} is the {@code dx} of {@link #n}.</li>
 * <li>{@code B} is the {@code dy} of {@link #n}.</li>
 * <li>{@code C} is the {@code dz} of {@link #n}.</li>
 * </ul>
 *
 * <ol>
 * <li>{@code n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−q.getX(oom))+n.getDY(oom)(y(t)−q.getY(oom))+n.getDZ(oom)(z(t)−q.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−r.getX(oom))+n.getDY(oom)(y(t)−r.getY(oom))+n.getDZ(oom)(z(t)−r.getZ(oom)) = 0}</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * True iff q is at the origin.
     */
    protected final boolean qAtOrigin;

    /**
     * One of the points that defines the plane.
     */
    private final V3D_Point p;

    /**
     * One of the points that defines the plane.
     */
    private final V3D_Point q;

    /**
     * One of the points that defines the plane.
     */
    private final V3D_Point r;

    /**
     * The vector representing the move from {@link #p} to {@link #q}.
     */
    private final V3D_Vector pq;

    /**
     * The vector representing the move from {@link #q} to {@link #r}.
     */
    private final V3D_Vector qr;

    /**
     * The vector representing the move from {@link #r} to {@link #p}.
     */
    private final V3D_Vector rp;

    /**
     * The normal vector. (This is perpendicular to the plane and it's direction
     * is given by order in which the two vectors {@link #pq} and {@link #qr}
     * are used in a cross product calculation when the plane is constructed.
     */
    private final V3D_Vector n;

    /**
     * The Order of Magnitude for the precision of the plane.
     */
    protected final int oom;

    /**
     * Create a new instance.
     *
     * @param p The plane used to create this.
     */
    public V3D_Plane(V3D_Plane p) {
        super(V3D_Vector.ZERO);
        this.qAtOrigin = p.q.equals(V3D_Environment.P0P0P0);
        this.p = new V3D_Point(p.p);
        this.q = new V3D_Point(p.q);
        this.r = new V3D_Point(p.r);
        this.pq = new V3D_Vector(p.pq);
        this.qr = new V3D_Vector(p.qr);
        this.rp = new V3D_Vector(p.rp);
        this.n = new V3D_Vector(p.n);
        this.oom = p.oom;
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param pq What {@link #pq} is set to.
     * @param qr What {@link #qr} is set to.
     * @param rp What {@link #rp} is set to.
     * @param n What {@link #n} is set to.
     * @param oom What {@link #oom} is set to.
     * @throws RuntimeException If p, q and r are collinear and this is checked
     * for.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Vector pq,
            V3D_Vector qr, V3D_Vector rp, V3D_Vector n, int oom) {
        super(V3D_Vector.ZERO);
        this.qAtOrigin = q.equals(V3D_Environment.P0P0P0);
        this.p = p;
        this.q = q;
        this.r = r;
        this.pq = pq;
        this.qr = qr;
        this.rp = rp;
        this.n = n;
        this.oom = oom;
    }

    /**
     * Create a new instance. If {@code q} is at the origin, then swap this with
     * one of the other points as otherwise the cross product/normal vector
     * turns out to be the Zero vector.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom What {@link #oom} is set to.
     * @param checkCoplanar If {@code false} the there is no check that p, q and
     * are coplanar.
     * @throws RuntimeException If p, q and r are not coplanar and this is
     * checked for.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r, int oom,
            boolean checkCoplanar) {
        super(V3D_Vector.ZERO);
        this.qAtOrigin = q.equals(V3D_Environment.P0P0P0);
        this.p = p;
        this.q = q;
        this.r = r;
        this.pq = new V3D_Vector(this.p, this.q, oom);
        this.qr = new V3D_Vector(this.q, this.r, oom);
        this.rp = new V3D_Vector(this.r, this.p, oom);
        if (qAtOrigin) {
            this.n = this.qr.getCrossProduct(this.rp, oom);
        } else {
            this.n = this.pq.getCrossProduct(this.qr, oom);
        }
        if (checkCoplanar) {
            if (V3D_Geometrics.isCoplanar(oom, p, q, r)) {
                throw new RuntimeException("The points do not define a plane.");
            }
        }
        this.oom = oom;
    }

    /**
     * Create a new instance. This assumes that p, q and r are not collinear.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r, int oom) {
        this(p, q, r, oom, false);
    }

    /**
     * An equation of the plane containing the point {@code p} with normal
     * vector {@code n} is
     * {@code n.getDX(oom)(x) + n.getDY(oom)(y) + n.getDZ(oom)(z) = d}.
     *
     * @param p What {@link #p} is set to.
     * @param n What {@link #n} is set to. This cannot be the ZERO vector.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_Plane(V3D_Point p, V3D_Vector n, int oom) {
        super(V3D_Vector.ZERO);
        this.p = p;
        /**
         * Find a perpendicular vector using: user65203, How to find
         * perpendicular vector to another vector?, URL (version: 2020-10-01):
         * https://math.stackexchange.com/q/3821978 Tested in
         * testIsIntersectedBy_V3D_Point_int()
         */
        V3D_Vector pv;
        V3D_Vector v1 = new V3D_Vector(Math_BigRationalSqrt.ZERO, n.dz, n.dy.negate(), oom);
        V3D_Vector v2 = new V3D_Vector(n.dz.negate(), Math_BigRationalSqrt.ZERO, n.dx, oom);
        V3D_Vector v3 = new V3D_Vector(n.dy.negate(), n.dx, Math_BigRationalSqrt.ZERO, oom);
        Math_BigRational mv1 = v1.getMagnitudeSquared();
        Math_BigRational mv2 = v2.getMagnitudeSquared();
        Math_BigRational mv3 = v3.getMagnitudeSquared();
        if (mv1.compareTo(mv2) == 1) {
            if (mv1.compareTo(mv3) == 1) {
                pv = v1;
            } else {
                pv = v3;
            }
        } else {
            if (mv2.compareTo(mv3) == 1) {
                pv = v2;
            } else {
                pv = v3;
            }
        }
        this.q = p.apply(pv, oom);
        V3D_Vector pvx = pv.getCrossProduct(n, oom);
        this.r = p.apply(pvx, oom);
        pq = new V3D_Vector(p, q, oom);
        qr = new V3D_Vector(q, r, oom);
        rp = new V3D_Vector(r, p, oom);
        qAtOrigin = q.equals(V3D_Environment.P0P0P0);
        this.n = n;
        this.oom = oom;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + toString0() + ")";
    }

    protected String toString0() {
        return "p=" + p.toString() + ", q=" + q.toString() 
                + ", r=" + r.toString() + ", oom=" + oom;
    }

    public V3D_Point getP(int oom){
        return new V3D_Point(p).apply(offset, oom);
    }

    public V3D_Point getQ(int oom){
        return new V3D_Point(q).apply(offset, oom);
    }
    
    public V3D_Point getR(int oom){
        return new V3D_Point(r).apply(offset, oom);
    }
    
    public V3D_Vector getPq(int oom){
        return new V3D_Vector(pq).add(offset, oom);
    }
    
    public V3D_Vector getQr(int oom){
        return new V3D_Vector(qr).add(offset, oom);
    }
    
    public V3D_Vector getRp(int oom){
        return new V3D_Vector(rp).add(offset, oom);
    }
    
    public V3D_Vector getN(int oom){
        return new V3D_Vector(n).add(offset, oom);
    }
    
    /**
     * For getting the equation of the plane.
     *
     * @return The equation of the plane as a String.
     */
    public String getEquation() {
        Math_BigRational ndxsr = n.dx.getSqrt();
        Math_BigRational ndysr = n.dy.getSqrt();
        Math_BigRational ndzsr = n.dz.getSqrt();
        Math_BigRational k = (ndxsr.multiply(p.getX(oom))
                .add(ndysr.multiply(p.getY(oom)))
                .add(ndzsr.multiply(p.getZ(oom)))).negate();
//        Math_BigRational k = (ndxsr.multiply(p.getX(oom))
//                .subtract(ndysr.multiply(p.getY(oom)))
//                .subtract(ndzsr.multiply(p.getZ(oom))));
//        Math_BigRational k = ndxsr.multiply(p.getX(oom))
//                .add(ndysr.multiply(p.getY(oom)))
//                .add(ndzsr.multiply(p.getZ(oom)));
        return ndxsr.toRationalString() + " * x + "
                + ndysr.toRationalString() + " * y + "
                + ndzsr.toRationalString() + " * z + "
                + k.toRationalString() + " = 0";
    }

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the calculation.
     * @return a new plane.
     */
    @Override
    public V3D_Plane apply(V3D_Vector v, int oom) {
        return new V3D_Plane(p.apply(v, oom), q.apply(v, oom), r.apply(v, oom),
                oom);
    }

    /**
     * @param pl The plane to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If this and {@code pl} intersect.
     */
    public boolean isIntersectedBy(V3D_Plane pl, int oom) {
        if (isParallel(pl, oom)) {
            return equals(pl);
        }
        return true;
    }

    /**
     * @param l The line to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (isParallel(l, oom)) {
            if (!isOnPlane(l, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This uses matrices as per the answer to this:
     * <a href="https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio">https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio</a>
     *
     * @param pt The point to test if it is on the plane.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        Math_BigRational[][] m = new Math_BigRational[4][4];
        m[0][0] = p.getX(oom);
        m[1][0] = p.getY(oom);
        m[2][0] = p.getZ(oom);
        m[3][0] = Math_BigRational.ONE;
        m[0][1] = q.getX(oom);
        m[1][1] = q.getY(oom);
        m[2][1] = q.getZ(oom);
        m[3][1] = Math_BigRational.ONE;
        m[0][2] = r.getX(oom);
        m[1][2] = r.getY(oom);
        m[2][2] = r.getZ(oom);
        m[3][2] = Math_BigRational.ONE;
        m[0][3] = pt.getX(oom);
        m[1][3] = pt.getY(oom);
        m[2][3] = pt.getZ(oom);
        m[3][3] = Math_BigRational.ONE;
        return new Math_Matrix_BR(m).getDeterminant().compareTo(Math_BigRational.ZERO) == 0;
    }

    /**
     * @param l The line to test if it is on the plane.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l, int oom) {
        return isIntersectedBy(l.getP(oom), oom) && isIntersectedBy(l.getQ(oom), oom);
    }

    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection Weisstein,
     * Eric W. "Line-Plane Intersection." From MathWorld--A Wolfram Web
     * Resource. https://mathworld.wolfram.com/Line-PlaneIntersection.html
     *
     * @param l The line to intersect with the plane.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection of the line and the plane. This is either
     * {@code null} a line or a point.
     */
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        if (this.isParallel(l, oom)) {
            if (this.isOnPlane(l, oom)) {
                return l;
            } else {
                return null;
            }
        }
        // Are either of the points of l on the plane.
        V3D_Point lp = l.getP(oom);
        if (this.isIntersectedBy(lp, oom)) {
            return lp;
        }
        V3D_Point lq = l.getQ(oom);
        if (this.isIntersectedBy(lq, oom)) {
            return lq;
        }
        V3D_Vector lv = l.getV(oom);
        Math_BigRational[][] m = new Math_BigRational[4][4];
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ONE;
        m[0][2] = Math_BigRational.ONE;
        m[0][3] = Math_BigRational.ONE;
        m[1][0] = p.getX(oom);
        m[1][1] = q.getX(oom);
        m[1][2] = r.getX(oom);
        m[1][3] = lp.getX(oom);
        m[2][0] = p.getY(oom);
        m[2][1] = q.getY(oom);
        m[2][2] = r.getY(oom);
        m[2][3] = lp.getY(oom);
        m[3][0] = p.getZ(oom);
        m[3][1] = q.getZ(oom);
        m[3][2] = r.getZ(oom);
        m[3][3] = lp.getZ(oom);
        Math_Matrix_BR numm = new Math_Matrix_BR(m);
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ONE;
        m[0][2] = Math_BigRational.ONE;
        m[0][3] = Math_BigRational.ZERO;
        m[1][0] = p.getX(oom);
        m[1][1] = q.getX(oom);
        m[1][2] = r.getX(oom);
        m[1][3] = lv.getDX(oom);
        m[2][0] = p.getY(oom);
        m[2][1] = q.getY(oom);
        m[2][2] = r.getY(oom);
        m[2][3] = lv.getDY(oom);
        m[3][0] = p.getZ(oom);
        m[3][1] = q.getZ(oom);
        m[3][2] = r.getZ(oom);
        m[3][3] = lv.getDZ(oom);
        Math_Matrix_BR denm = new Math_Matrix_BR(m);
        Math_BigRational t = numm.getDeterminant().divide(denm.getDeterminant()).negate();
        return new V3D_Point(
                lp.getX(oom).add(lv.getDX(oom).multiply(t)),
                lp.getY(oom).add(lv.getDY(oom).multiply(t)),
                lp.getZ(oom).add(lv.getDZ(oom).multiply(t)));
//        V3D_Vector d = new V3D_Vector(this.p, l.p, n.oom);
//        //V3D_Vector d = new V3D_Vector(l.p, p, n.oom);
//        Math_BigRational num = d.getDotProduct(this.n);
//        Math_BigRational den = l.v.getDotProduct(this.n);
//        Math_BigRational t = num.divide(den);
//        return new V3D_Point(
//                l.p.getX(oom).subtract(l.v.getDX(oom).multiply(t)),
//                l.p.getY(oom).subtract(l.v.getDY(oom).multiply(t)),
//                l.p.getZ(oom).subtract(l.v.getDZ(oom).multiply(t)));

    }

    /**
     * @param l line segment to intersect with this.
     * @param oom The Order of Magnitude for the calculation.
     * @param flag Used to distinguish from {@link #getIntersection(V3D_Line, int)}.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        V3D_Geometry li = getIntersection(l, oom);
        if (li == null) {
            return null;
        }
        if (li instanceof V3D_Line) {
            return l;
        }
        V3D_Point pt = (V3D_Point) li;
        if (l.isIntersectedBy(pt, oom)) {
            return pt;
        }
        return null;
    }

    /**
     * https://www.microsoft.com/en-us/research/publication/intersection-of-two-planes/
     * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
     * https://math.stackexchange.com/questions/291289/find-the-point-on-the-line-of-intersection-of-the-two-planes-using-lagranges-me
     * http://tbirdal.blogspot.com/2016/10/a-better-approach-to-plane-intersection.html
     * https://mathworld.wolfram.com/Plane-PlaneIntersection.html
     *
     * @param pl The plane to intersect.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom) {
//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * But something is not right!
//         */
//        //function [p, n] = intersect_planes(p1, n1, p2, n2, p0)
//        //M = [2 0 0 n1(1) n2(1)
//        //     0 2 0 n1(2) n2(2)
//        //     0 0 2 n1(3) n2(3)
//        //     n1(1) n1(2) n1(3) 0 0
//        //     n2(1) n2(2) n2(3) 0 0];
//        Math_BigRational[][] m = new Math_BigRational[5][6];
//        m[0][0] = Math_BigRational.TWO;
//        m[0][1] = Math_BigRational.ZERO;
//        m[0][2] = Math_BigRational.ZERO;
//        m[0][3] = n.getDX(oom);
//        m[0][4] = pl.n.getDX(oom);
//        m[1][0] = Math_BigRational.ZERO;
//        m[1][1] = Math_BigRational.TWO;
//        m[1][2] = Math_BigRational.ZERO;
//        m[1][3] = n.getDY(oom);
//        m[1][4] = pl.n.getDY(oom);
//        m[2][0] = Math_BigRational.ZERO;
//        m[2][1] = Math_BigRational.ZERO;
//        m[2][2] = Math_BigRational.TWO;
//        m[2][3] = n.getDZ(oom);
//        m[2][4] = pl.n.getDZ(oom);
//        m[3][0] = n.getDX(oom);
//        m[3][1] = n.getDY(oom);
//        m[3][2] = n.getDZ(oom);
//        m[3][3] = Math_BigRational.ZERO;
//        m[3][4] = Math_BigRational.ZERO;
//        m[4][0] = pl.n.getDX(oom);
//        m[4][1] = pl.n.getDY(oom);
//        m[4][2] = pl.n.getDZ(oom);
//        m[4][3] = Math_BigRational.ZERO;
//        m[4][4] = Math_BigRational.ZERO;
//        // b4 = p1(1).*n1(1) + p1(2).*n1(2) + p1(3).*n1(3);
//        Math_BigRational b4 = p.getX(oom).multiply(n.getDX(oom))
//                .add(p.getY(oom).multiply(n.getDY(oom)))
//                .add(p.getZ(oom).multiply(n.getDZ(oom)));
//        System.out.println(b4);
//        // b5 = p2(1).*n2(1) + p2(2).*n2(2) + p2(3).*n2(3);
//        Math_BigRational b5 = pl.p.getX(oom).multiply(pl.n.getDX(oom))
//                .add(pl.p.getY(oom).multiply(pl.n.getDY(oom)))
//                .add(pl.p.getZ(oom).multiply(pl.n.getDZ(oom)));
//        System.out.println(b5);
//        // b = [2*p0(1) ; 2*p0(2) ; 2*p0(3); b4 ; b5];
//        m[0][5] = q.getX(oom).multiply(2);
//        m[1][5] = q.getY(oom).multiply(2);
//        m[2][5] = q.getZ(oom).multiply(2);
//        m[3][5] = b4;
//        m[4][5] = b5;
//        // x = M\b;
//        Math_Matrix_BR mm = new Math_Matrix_BR(m);
//        Math_Matrix_BR ref = mm.getReducedRowEchelonForm();
//        System.out.println(ref);
//        // p = x(1:3);
//        //???
//        // n = cross(n1, n2);
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        V3D_Vector v = n.getCrossProduct(pl.n, oom);
        /**
         * Check special cases.
         */
        if (v.isZeroVector()) {
            // The planes are parallel.
            if (pl.equals(this)) {
                // The planes are the same.
                return this;
            }
            // There is no intersection.
            return null;
        }
        /**
         * Find the intersection of a line in the plane that is not parallel to v.
         */
        V3D_Point pi;
        if (this.pq.isScalarMultiple(v, oom)) {
            pi = (V3D_Point) pl.getIntersection(new V3D_Line(q, r, oom), oom);
        } else {
            pi = (V3D_Point) pl.getIntersection(new V3D_Line(p, q, oom), oom);
        }
        return new V3D_Line(pi, v, oom);
    }
    
//    private V3D_Geometry getIntersectionOld(V3D_Plane pl, int oom) {
//        /**
//         * Calculate the cross product of the normal vectors to get the
//         * direction of the line.
//         */
//        V3D_Vector v = n.getCrossProduct(pl.n, oom);
//        /**
//         * To find a point on the line find a vector (that defines a line) from
//         * each plane that is not parallel to v, then find their intersection.
//         */
//        if (v.isZeroVector()) {
//            // The planes are parallel.
//            if (pl.equals(this)) {
//                // The planes are the same.
//                return this;
//            }
//            // There is no intersection.
//            return null;
//        }
//        // Calculate the line of intersection, where v is the line vector.
//        // What to do depends on which elements of v are non-zero.
//        Math_BigRational P0 = Math_BigRational.ZERO;
//        if (v.dx.isZero()) {
//            if (v.dy.isZero()) {
//                Math_BigRational z = pl.n.getDX(oom).multiply(pl.p.getX(oom)
//                        .subtract(pl.q.getX(oom))).add(n.getDY(oom).multiply(pl.p.getY(oom)
//                        .subtract(pl.q.getX(oom)))).divide(v.getDZ(oom)).add(pl.p.getZ(oom));
//                V3D_Point pt;
//                if (n.dx.isZero()) {
//                    pt = new V3D_Point(pl.p.getX(oom), p.getY(oom), z);
//                } else {
//                    pt = new V3D_Point(p.getX(oom), pl.p.getY(oom), z);
//                }
//                return new V3D_Line(pt, pt.apply(v, oom), oom);
//                // The intersection is at z=?
//
//                /**
//                 * n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom))
//                 */
//                // where:
//                // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                // pl.n.getDX(oom) = d; pl.n.getDY(oom) = e; pl.n.getDZ(oom) = f
//                // a(x−p.getX(oom)) + b(y−p.getY(oom)) + c(z−p.getZ(oom)) = 0
//                // x = p.getX(oom) + ((- b(y−p.getY(oom)) - c(z−p.getZ(oom))) / a)                     --- 1
//                // y = p.getY(oom) + ((- a(x−p.getX(oom)) - c(z−p.getZ(oom))) / b)                     --- 2
//                // z = p.getZ(oom) + ((- a(x−p.getX(oom)) - b(y−p.getY(oom))) / c)                     --- 3
//                // x = pl.p.getX(oom) + ((- pl.b(y − pl.p.getY(oom)) - pl.c(z − pl.p.getZ(oom))) / d)  --- 4
//                // y = pl.p.getY(oom) + ((- pl.a(x − pl.p.getX(oom)) - pl.c(z − pl.p.getZ(oom))) / e)  --- 5
//                // z = pl.p.getZ(oom) + ((- pl.a(x − pl.p.getX(oom)) - pl.b(y − pl.p.getY(oom))) / f)  --- 6
//                // Let:
//                // p.getX(oom) = k; p.getY(oom) = l; p.getZ(oom) = m
//                // x = k + ((b(l - y) - c(z − m)) / a)   --- 1t
//                // y = l + ((a(k - x) - c(z − l)) / b)   --- 2t
//                // z = m + ((a(k - x) - b(y − m)) / c)   --- 3t
//                // Let:
//                // pl.p.getX(oom) = k; pl.p.getY(oom) = l; pl.p.getZ(oom) = m
//                // x = k + ((e(l - y) - f(z - m)) / d)   --- 1p
//                // y = l + ((d(k - x) - f(z - l)) / e)   --- 2p
//                // z = m + ((d(k - x) - e(y - m)) / f)   --- 3p
////                if (n.getDX(oom).compareTo(e.P0) == 0) {
////                    //pl.b
////                } else if (n.getDY(oom).compareTo(e.P0) == 0) {
//////                    // y = l + ((- a(x − k) - c(z − l)) / b)
////                    BigDecimal y = p.getY(oom);
//////                    // x = k + ((e(l - y) - f(z - m)) / d)
//////                    // z = m + ((- d(x - k) - e(y - m)) / f)
//////                    BigDecimal x = p.getX(oom).apply(
//////                            Math_BigDecimal.divideRoundIfNecessary(
//////                                    (pl.n.getDY(oom).multiply(p.getY(oom).subtract(y))).subtract(pl.n.getDZ(oom).multiply(z.subtract(pl.p.getZ(oom)))),
//////                                    pl.n.getDX(oom), scale, rm));
////                } else {
////                    return e.zAxis;
////                }
////                //BigDecimal x = p.getX(oom)
////                BigDecimal numerator = p.getZ(oom).subtract(p.getY(oom))
////                        .subtract(n.getDZ(oom).multiply(p.getZ(oom)))
////                        .subtract(p.getY(oom).multiply(n.getDY(oom)));
////                BigDecimal denominator = n.getDY(oom).subtract(n.getDZ(oom));
////                if (denominator.compareTo(e.P0) == 0) {
////                    // Case 1: The z axis
////                    return null;
////                } else {
////                    // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1          
////                    // z = (p.getZ(oom) - b(y−p.getY(oom))) / c   --- 2
////                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
////                    // Let:
////                    // p.getX(oom) = k; p.getY(oom) = l; p.getZ(oom) = m
////                    // y = (l - c(z - m)) / b
////                    // z = (m - b(y - l)) / b
////                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
////                    // bz = m - b(((l - c(z - m)) / b) - l)
////                    // bz = m - (l - c(z - m)) - lb
////                    // bz = m - l + cz - cm - lb
////                    // bz - cz = m - l -cm - lb
////                    // z(b - c) = m - l -cm - lb
////                    // z = (m - l -cm - lb) / (b - c)
////                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(
////                            numerator, denominator, scale + 2, rm); // scale + 2 sensible?
////                    // Substitute into 1
////                    // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1
////                    if (n.getDY(oom).compareTo(e.P0) == 0) {
////                        // Another case to deal with
////                        return null;
////                    } else {
////                        BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(
////                                p.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(p.getZ(oom)))),
////                                n.getDY(oom), scale, rm);
////                        return new V3D_Line(new V3D_Point(e, p.getX(oom), y, z),
////                                new V3D_Point(e, p.getX(oom).multiply(e.N1), y.add(v.getDY(oom)), z.add(v.getDZ(oom))));
////                    }
////                }
//            } else {
//                if (v.dz.isZero()) {
//                    Math_BigRational y = n.getDX(oom).multiply(p.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(p.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(p.getY(oom));
//                    V3D_Point pt;
//                    if (n.getDX(oom).compareTo(P0) == 0) {
//                        pt = new V3D_Point(pl.p.getX(oom), y, p.getZ(oom));
//                    } else {
//                        pt = new V3D_Point(p.getX(oom), y, pl.p.getZ(oom));
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                } else {
//                    // Case 3
//                    // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1          
//                    // z = (p.getZ(oom) - b(y−p.getY(oom))) / c   --- 2
//                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                    // Let:
//                    // p.getX(oom) = k; p.getY(oom) = l; p.getZ(oom) = m
//                    // y = (l - c(z - m)) / b
//                    // z = (m - b(y - l)) / b
//                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
//                    // bz = m - b(((l - c(z - m)) / b) - l)
//                    // bz = m - (l - c(z - m)) - lb
//                    // bz = m - l + cz - cm - lb
//                    // bz - cz = m - l -cm - lb
//                    // z(b - c) = m - l -cm - lb
//                    // z = (m - l -cm - lb) / (b - c)
//                    Math_BigRational numerator = p.getZ(oom).subtract(p.getY(oom))
//                            .subtract(n.getDZ(oom).multiply(p.getZ(oom)))
//                            .subtract(p.getY(oom).multiply(n.getDY(oom)));
//                    Math_BigRational denominator = n.getDY(oom).subtract(n.getDZ(oom));
//                    if (denominator.compareTo(P0) == 0) {
//                        // Another case to deal with
//                        return null;
//                    } else {
//                        Math_BigRational z = numerator.divide(denominator);
//                        // Substitute into 1
//                        // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1
//                        if (n.dy.isZero()) {
//                            // Another case to deal with
//                            return null;
//                        } else {
//                            Math_BigRational y = p.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(p.getZ(oom))))
//                                    .divide(n.getDY(oom));
//                            return new V3D_Line(
//                                    new V3D_Point(P0, y, z),
//                                    new V3D_Point(P0, y.add(v.getDY(oom)), z.add(v.getDZ(oom))), oom);
//                        }
//                    }
//                }
//            }
//        } else {
//            if (v.dy.isZero()) {
//                if (v.dz.isZero()) {
//                    Math_BigRational x = pl.n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.p.getZ(oom).subtract(pl.q.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.p.getX(oom));
//                    V3D_Point pt;
//                    if (n.dy.isZero()) {
//                        if (n.dz.isZero()) {
//                            pt = new V3D_Point(x, p.getY(oom), pl.p.getZ(oom));
//                        } else {
//                            pt = new V3D_Point(x, pl.p.getY(oom), p.getZ(oom));
//                        }
//                    } else {
//                        if (n.dz.isZero()) {
//                            pt = new V3D_Point(x, p.getY(oom), pl.p.getZ(oom));
//                        } else {
//                            pt = new V3D_Point(x, p.getY(oom), pl.p.getZ(oom));
//                        }
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                } else {
//                    Math_BigRational z = pl.n.getDX(oom).multiply(pl.p.getX(oom).subtract(pl.q.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getX(oom))))
//                            .divide(v.getDZ(oom)).add(pl.p.getZ(oom));
//                    V3D_Point pt;
//                    if (n.dx.isZero()) {
//                        pt = new V3D_Point(pl.p.getX(oom), p.getY(oom), z);
//                    } else {
//                        pt = new V3D_Point(p.getX(oom), pl.p.getY(oom), z);
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                }
//            } else {
//                if (v.dz.isZero()) {
//                    // Case 6
//                    Math_BigRational y = n.getDX(oom).multiply(p.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(p.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(p.getY(oom));
//                    V3D_Point pt;
//                    if (p.getX(oom).compareTo(P0) == 0) {
//                        pt = new V3D_Point(p.getX(oom), y, pl.p.getZ(oom)); // x=1 z=0
//                    } else {
//                        pt = new V3D_Point(pl.p.getX(oom), y, p.getZ(oom));
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                } else {
//                    /**
//                     * Case 7: Neither plane aligns with any axis and they are
//                     * not orthogonal.
//                     *
//                     * n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom))
//                     */
//                    // where:
//                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                    // pl.n.getDX(oom) = d; pl.n.getDY(oom) = e; pl.n.getDZ(oom) = f
//                    // a(x−p.getX(oom))+b(y−p.getY(oom))+c(z−p.getZ(oom)) = 0
//                    // x = (ap.getX(oom)-by+bp.getY(oom)-cz+cp.getZ(oom))/a
//                    // x = p.getX(oom)+((b(p.getY(oom)−y)+c(p.getZ(oom)−z))/a)                    --- 1
//                    // y = p.getY(oom)+((a(p.getX(oom)−x)+c(p.getZ(oom)−z))/b)                    --- 2
//                    // z = p.getZ(oom)+((a(p.getX(oom)−x)+b(p.getY(oom)−y))/c)                    --- 3
//                    // x = pl.p.getX(oom)+((pl.b(pl.p.getY(oom)−y)+pl.c(pl.p.getZ(oom)−z))/pl.a)  --- 4
//                    // y = pl.p.getY(oom)+((pl.a(pl.p.getX(oom)−x)+pl.c(pl.p.getZ(oom)−z))/pl.b)  --- 5
//                    // z = pl.p.getZ(oom)+((pl.a(pl.p.getX(oom)−x)+pl.b(pl.p.getY(oom)−y))/pl.c)  --- 6
//                    // Let:
//                    // p.getX(oom) = g; p.getY(oom) = h; p.getZ(oom) = i
//                    // x = g+((b(h−y)+c(i−z))/a)                    --- 1p
//                    // y = h+((a(g−x)+c(i−z))/b)                    --- 2p
//                    // z = i+((a(g−x)+b(h−y))/c)                    --- 3p
//                    // Let:
//                    // pl.p.getX(oom) = j; pl.p.getY(oom) = k; pl.p.getZ(oom) = l
//                    // x = j+((e(k−y)+f(l−z))/d)                    --- 1pl
//                    // y = k+((d(j−x)+f(l−z))/e)                    --- 2pl
//                    // z = l+((d(j−x)+e(k−y))/f)                    --- 3pl
//                    // Stage 1: express each coordinate in terms of another
//                    // sub 1p into 2pl:
//                    // y = k+((d(j−(g+((b(h−y)+c(i−z))/a)))+f(l−z))/e)
//                    // ey-ek = d(j−(g+((b(h−y)+c(i−z))/a)))+f(l−z)
//                    // ey-ek = d(j−(g+((b(h−y)+c(i−z))/a)))+fl−fz
//                    // ey-ek = dj−dg-dbh/a+dby/a-dci/a+dcz/a+fl−fz                    
//                    // ey-dby/a = dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek
//                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
//                    // ey-dby/a = dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek
//                    // ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek = z(dc/a-f)
//                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
//                    // sub 1p into 3pl
//                    // z = l+((d(j−(g+((b(h−y)+c(i−z))/a)))+e(k−y))/f)
//                    // fz-fl = d(j−(g+((b(h−y)+c(i−z))/a)))+ek−ey
//                    // fz-dcz/a = dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl
//                    // z = (dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl)/(f-dc/a)  ---13 z ito y 
//                    // fz-dcz/a = dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl
//                    // fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl= dby/a−ey
//                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
//                    // sub 2p into 1pl:
//                    // x = j+((e(k−(h+((a(g−x)+c(i−z))/b)))+f(l−z))/d)
//                    // dx-dj = e(k−(h+((a(g−x)+c(i−z))/b)))+fl−fz
//                    // dx-dj = ek−eh-eag/b+eax/b-eci/b+ecz/b+fl−fz
//                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z    
//                    // dx-dj = ek−eh-eag/b+eax/b-eci/b+ecz/b+fl−fz
//                    // z = (dx-dj-ek+eh+eag/b-eax/b+eci/b-fl)/(ec/b−f)  ---21 z ito x
//                    // sub 2p into 3pl
//                    // z = l+((d(j−x)+e(k−(h+((a(g−x)+c(i−z))/b))))/f)
//                    // fz-fl = dj−dx+ek−eh-eag/b+eax/b-eci/b+ecz/b
//                    // z = (dj−dx+ek−eh-eag/b+eax/b-eci/b+fl)/(f-ec/b)  ---23 z ito x
//                    // fz-fl = dj−dx+ek−eh-eag/b+eax/b-eci/b+ecz/b
//                    // x = (fz-fl-dj-ek+eh+eag/b+eci/b-ecz/b)/(ea/b-d)  ---23 x ito z
//                    // sub 3p into 1pl
//                    // x = j+((e(k−y)+f(l−(i+((a(g−x)+b(h−y))/c))))/d)
//                    // dx-dj = ek−ey+f(l−(i+((a(g−x)+b(h−y))/c)))                    
//                    // dx-dj = ek−ey+fl−fi+fag/c−fax/c+fbh/c−fby/c
//                    // x = (dj+ek−ey+fl−fi+fag/c+fbh/c−fby/c)/(d+fa/c)
//                    // x = (ek−ey+fl-fi-fag/c-fbh/c+fb)/c+dj)/(d-fa/c)  ---31 x ito y
//                    // dx-dj = ek−ey+fl−fi+fag/c−fax/c+fbh/c−fby/c                    
//                    // y = (ek+fl−fi+fag/c−fax/c+fbh/c-dx+dj)/(e+fb/c)  ---31 y ito x 
//                    // sub 3p into 2pl
//                    // y = k+((d(j−x)+f(l−(i+((a(g−x)+b(h−y))/c))))/e)
//                    // ey-ek = dj−dx+fl−fi-fag/c+fax/c-fbh/c+fby/c
//                    // y = (ek+dj−dx+fl−fi-fag/c+fax/c-fbh/c)/(e-fb/c)  ---32 y ito x
//                    // ey-ek = dj−dx+fl−fi-fag/c+fax/c-fbh/c+fby/c
//                    // x = (ey-ek-dj-fl+fi+fag/c+fbh/c-fby/c)/(fa/c−d)  ---32 x ito y
//                    // Stage 2: Are any denominators 0?
//                    Math_BigRational x = n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.p.getZ(oom).subtract(pl.q.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.p.getX(oom));
//                    Math_BigRational y = n.getDX(oom).multiply(p.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(p.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(p.getY(oom));
//                    Math_BigRational z = n.getDX(oom).multiply(pl.p.getX(oom).subtract(pl.q.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getX(oom))))
//                            .divide(v.getDZ(oom)).add(pl.p.getZ(oom));
//                    V3D_Point pt = new V3D_Point(x, y, z);
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//
////                    BigDecimal a = n.getDX(oom);
////                    BigDecimal b = n.getDY(oom);
////                    BigDecimal c = n.getDZ(oom);
////                    BigDecimal d = pl.n.getDX(oom);
////                    BigDecimal e = pl.n.getDY(oom);
////                    BigDecimal f = pl.n.getDZ(oom);
////                    BigDecimal db = d.multiply(b);
////                    BigDecimal dc = d.multiply(c);
////                    BigDecimal ea = e.multiply(a);
////                    BigDecimal ec = e.multiply(c);
////                    BigDecimal ef = e.multiply(f);
////                    BigDecimal fa = f.multiply(a);
////                    BigDecimal fb = f.multiply(b);
////                    BigDecimal db_div_a = Math_BigDecimal.divideRoundIfNecessary(db, a, scale, rm);
////                    BigDecimal dc_div_a = Math_BigDecimal.divideRoundIfNecessary(dc, a, scale, rm);
////                    BigDecimal ea_div_b = Math_BigDecimal.divideRoundIfNecessary(ea, b, scale, rm);
////                    BigDecimal ec_div_b = Math_BigDecimal.divideRoundIfNecessary(ec, b, scale, rm);
////                    BigDecimal fa_div_c = Math_BigDecimal.divideRoundIfNecessary(fa, c, scale, rm);
////                    BigDecimal fb_div_c = Math_BigDecimal.divideRoundIfNecessary(fb, c, scale, rm);
////                    BigDecimal denom12yitoz = e.subtract(db_div_a);
////                    BigDecimal denom12zitoy = dc_div_a.subtract(f);
////                    BigDecimal denom13zitoy = f.subtract(dc_div_a);
////                    BigDecimal denom13yitoz = db_div_a.subtract(e);
////                    BigDecimal denom21xitoz = d.subtract(ea_div_b);
////                    BigDecimal denom21zitox = ec_div_b.subtract(f);
////                    BigDecimal denom23zitox = f.subtract(ec_div_b);
////                    BigDecimal denom23xitoz = ea_div_b.subtract(d);
////                    BigDecimal denom31xitoy = d.subtract(fa_div_c);
////                    BigDecimal denom31yitox = e.add(fb_div_c);
////                    BigDecimal denom32yitox = e.subtract(fb_div_c);
////                    BigDecimal denom32xitoy = fa_div_c.subtract(d);
////                    // Solve for z
////                    // Let; n = v.getDX(oom); o = v.getDY(oom); p = v.getDZ(oom)
////                    // x(t) = x + v.getDX(oom) 
////                    // y(t) = y(0) + v.getDY(oom) 
////                    // z-p = z(0)
////                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
////                    // z-p = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
////                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
////                    // z-p = (e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
////                    // (z-p)(dc/a-f) = e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek
////                    // (efz-edcz/a-edj+edg+edbh/a+edci/a-eek-efl)/(db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
////                    // efz/(db/a-e)-edcz/(db-ae)-edj/(db/a-e)+edg/(db/a-e)+edbh/(db-ae)+edci/(db-ae)-eek/(db/a-e)-efl/db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
////                    // efz/(db/a-e)-edcz/(db-ae)+dbfz/(db-ae)-dbdcz/(adb-aae) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
////                    // z(ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae)) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
////                    // z = num/den
////                    // den = ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae);
////                    // num = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
////                    // Let: q = db-ae; r = db/a-e; s=adb-aae
////                    BigDecimal q = db.subtract(a.multiply(e));
////                    BigDecimal r = db_div_a.subtract(e);
////                    BigDecimal s = db.multiply(a).subtract(a.multiply(ea));
////                    BigDecimal den = Math_BigDecimal.divideRoundIfNecessary(ef, r, scale, rm)
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dc), q, scale, rm))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(db.multiply(f), q, scale, rm))
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(db.multiply(c)), s, scale, rm));
////                    BigDecimal g = p.getX(oom);
////                    BigDecimal h = p.getY(oom);
////                    BigDecimal i = p.getZ(oom);
////                    BigDecimal j = pl.p.getX(oom);
////                    BigDecimal k = pl.p.getY(oom);
////                    BigDecimal l = pl.p.getZ(oom);
////                    BigDecimal ek = e.multiply(k);
////                    BigDecimal ci = c.multiply(i);
////                    BigDecimal dg = d.multiply(g);
////                    BigDecimal dbh = db.multiply(h);
////                    BigDecimal dbh_sub_dci = dbh.subtract(d.multiply(ci));
////                    BigDecimal fl = f.multiply(l);
////                    BigDecimal bh = b.multiply(h);
////                    BigDecimal dj = d.multiply(j);
////                    BigDecimal num = d.multiply(j.subtract(dg).add(ek).add(fl))
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dbh_sub_dci), q, scale, rm))
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(dbh_sub_dci), s, scale, rm))
////                            .subtract(dj.subtract(dg))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(d.multiply(bh.add(ci)), a, scale, rm))
////                            .subtract(fl.subtract(ek))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dj.subtract(dg).add(ek).add(fl)), r, scale, rm));
////                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
////                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
////                    // y = num/den
////                    num = dj.subtract(dg).subtract(db_div_a.multiply(h))
////                            .subtract(dc_div_a.multiply(i))
////                            .add(dc_div_a.multiply(z)).add(fl).subtract(f.multiply(z)).add(ek);
////                    den = e.subtract(db_div_a);
////                    BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
////                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z
////                    BigDecimal e_div_b = Math_BigDecimal.divideRoundIfNecessary(e, b, scale, rm);
////                    num = ek.subtract(e.multiply(h))
////                            .subtract(g.multiply(a).multiply(e_div_b))
////                            .subtract(ci.multiply(e_div_b))
////                            .add(dc_div_a.multiply(z))
////                            .add(c.multiply(z).multiply(e_div_b))
////                            .add(fl)
////                            .subtract(f.multiply(z))
////                            .subtract(dj);
////                    den = d.subtract(a.multiply(e_div_b));
////                    BigDecimal x = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
////                    V3D_Point aPoint = new V3D_Point(this.e, x, y, z);
////                    return new V3D_Line(aPoint, aPoint.apply(v));
//                }
//            }
//        }
//        // This is where the two equations of the plane are equal.
//        // n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom))
//        // where:
//        // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//        // a(x−p.getX(oom)) + b(y−p.getY(oom)) + c(z−p.getZ(oom)) = 0
//        // x = (p.getX(oom) - b(y−p.getY(oom)) - c(z−p.getZ(oom))) / a                     --- 1
//        // y = (p.getY(oom) - a(x−p.getX(oom)) - c(z−p.getZ(oom))) / b                     --- 2
//        // z = (p.getZ(oom) - a(x−p.getX(oom)) - b(y−p.getY(oom))) / c                     --- 3
//        // x = (pl.p.getX(oom) - pl.b(y−pl.p.getY(oom)) - pl.c(z−pl.p.getZ(oom))) / pl.a   --- 4
//        // y = (pl.p.getY(oom) - pl.a(x−pl.p.getX(oom)) - pl.c(z−pl.p.getZ(oom))) / pl.b   --- 5
//        // z = (pl.p.getZ(oom) - pl.a(x−pl.p.getX(oom)) - pl.b(y−pl.p.getY(oom))) / pl.c   --- 6
//        // Sub 2 and 3 into
//    }

    /**
     * @param p The plane to test if it is parallel to this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} if {@code this} is parallel to {@code p}.
     */
    public boolean isParallel(V3D_Plane p, int oom) {
        //return p.n.isScalarMultiple(n); // alternative - probably slower?
        return n.getCrossProduct(p.n, oom).isZeroVector();
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line l, int oom) {
        return n.getDotProduct(l.getV(oom), oom).isZero();
    }

//    @Override
//    public boolean equals(V3D_Geometry g) {
//        if (g instanceof V3D_Plane) {
//            return equals((V3D_Plane) g);
//        }
//        return false;
//    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane) {
            V3D_Plane pl = (V3D_Plane) o;
            return equals(pl, oom);
        }
        return false;
    }

    /**
     * Planes are equal if they are coincident and their normal perpendicular
     * vectors point in the same direction. They may be coincident and be
     * defined by the same three points, but may not be equal. They may be equal
     * even if they are defined by different points.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equals(V3D_Plane pl, int oom) {
        if (V3D_Geometrics.isCoplanar(oom, this, pl.getP(oom), pl.q, pl.r)) {
            return true;
        }
//        if (n.equals(pl.n)) {
//            if (isIntersectedBy(pl.p)) {
//                if (isIntersectedBy(pl.q)) {
//                    if (isIntersectedBy(pl.r)) {
//                        return true;
//                    }
//                }
//            }
//        }
        return false;
    }

    /**
     * Planes are equal if they are coincident and the {@link #n} is the same.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean isCoincident(V3D_Plane pl, int oom) {
        if (isIntersectedBy(pl.p, oom)) {
            if (isIntersectedBy(pl.q, oom)) {
                if (isIntersectedBy(pl.r, oom)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.p);
        hash = 47 * hash + Objects.hashCode(this.q);
        hash = 47 * hash + Objects.hashCode(this.r);
        return hash;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return true;
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        Math_BigRational[][] m = new Math_BigRational[3][3];
        m[0][0] = p.getX(oom);
        m[0][1] = p.getY(oom);
        m[0][2] = p.getZ(oom);
        m[1][0] = q.getX(oom);
        m[1][1] = q.getY(oom);
        m[1][2] = q.getZ(oom);
        m[2][0] = r.getX(oom);
        m[2][1] = r.getY(oom);
        m[2][2] = r.getZ(oom);
        return new Math_Matrix_BR(m);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the calculation.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p, oom)) {
            return BigDecimal.ZERO;
        }
        V3D_Vector v = new V3D_Vector(p, this.p, oom);
        V3D_Vector u = this.n.getUnitVector(oom);
//        MathContext mc = new MathContext(Math_Math_BigRationalSqrt
//                .getOOM(Math_BigRational.ONE, oom));
        MathContext mc = new MathContext(6 - oom);
        return v.getDotProduct(u, oom).abs().toBigDecimal(mc);
    }

    /**
     * Using {@link #p} and {@link #n} define a line and find the point of
     * intersection on {@code p} and then return the distance squared between it
     * and {@link #p}.
     *
     * @param p The other plane used to calculate the distance.
     * @param oom The Order of Magnitude for the calculation.
     * @return The shortest distance between {@code this} and {@code p}. Choose
     * {@link #p}
     */
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        if (isParallel(p, oom)) {
            return this.p.getDistanceSquared((V3D_Point) p.getIntersection(
                    new V3D_Line(this.p, n, oom), oom), oom);
        }
        return Math_BigRational.ZERO;
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return BigDecimal.ZERO;
        }
        return getDistance(l.getP(oom), oom);
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        BigDecimal lpd = getDistance(l.getP(oom), oom);
        BigDecimal lqd = getDistance(l.getQ(oom), oom);
        return lpd.min(lqd);
    }
}

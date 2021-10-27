/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain xxx copy of the License at
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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometrics.V3D_Geometrics;

/**
 * For representing and processing rectangles in 3D. A rectangle has a non-zero
 * area and does not have to align with any of the axes. The corner points
 * {@link #p}, {@link #q}, {@link #r}, {@link #s} are arranged in a rectangular
 * fashion so that {@link #pq} is orthogonal to {@link #qr}. The left of a
 * rectangle {@link #l} is the line segment from {@link #p} to {@link #q}. The
 * top of a rectangle {@link #t} is the line segment from {@link #q} to
 * {@link #r}. The right of a rectangle {@link #ri} is the line segment from
 * {@link #r} to {@link #s}. The bottom of a rectangle {@link #b} is the line
 * segment from {@link #s} to {@link #p}. The following depicts a generic
 * rectangle (no attempt has been made to draw this three dimensionally) {@code
 *          t
 * q *-------------* r
 *   |             |
 * l |             | ri
 *   |             |
 * p *-------------* s
 *          b
 * }
 * Although what is depicted can be imagined as aligning with the x and y axes.
 * A rectangle can be defined by any three coordinates in 3D space so long as
 * the three points are at right angles.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_Plane implements V3D_2DShape {

    private static final long serialVersionUID = 1L;

    /**
     * For storing if q is at the origin.
     */
    protected final boolean qAtOrigin;

    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q},
     * and {@link #r}.
     */
    protected final V3D_Point s;

    /**
     * Initialised when the q provided to initialise this is at the origin and p
     * and q have been swapped to define the plane. So, this represents the
     * vector from the point p input (which is now stored as {@link #q}) to
     * {@link #s}.
     */
    protected final V3D_Vector qs;

    /**
     * For storing the envelope
     */
    protected V3D_Envelope en;

    /**
     * For storing the vector from {@link #p} to {@link #q}.
     */
    protected final V3D_LineSegment l;

    /**
     * For storing the line segment from {@link #q} to {@link #r}.
     */
    protected final V3D_LineSegment t;

    /**
     * For storing the line segment from {@link #r} to {@link #s}.
     */
    protected final V3D_LineSegment ri;

    /**
     * For storing the line segment from {@link #s} to {@link #p}.
     */
    protected final V3D_LineSegment b;

    /**
     * Create a new instance.
     *
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s, 
            int oom) {
        super(p, q, r, oom);
        /**
         * p and q get swapped if q is at the origin in defining the plane, in
         * which case {@link #pq} is now representing the reverse, and
         * {@link qr} represents the vector from p to r.
         */
        qAtOrigin = q.equals(V3D_Environment.P0P0P0);
        if (qAtOrigin) {
            qs = new V3D_Vector(p, s, oom);
        } else {
            qs = null;
        }
        this.s = s;
        //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
        l = new V3D_LineSegment(p, q, oom);
        t = new V3D_LineSegment(q, r, oom);
        ri = new V3D_LineSegment(r, s, oom);
        b = new V3D_LineSegment(s, p, oom);
        // Check for rectangle.
        if (pq.isZeroVector()) {
            if (qr.isZeroVector()) {
                // Rectangle is a point.
            } else {
                // Rectangle is a line.
            }
        } else {
            if (qr.isZeroVector()) {
                // Rectangle is a line.
            } else {
                // Rectangle has area.
                if (qAtOrigin) {
                    if (!(pq.isOrthogonal(qs, oom))) {
                        throw new RuntimeException("The points do not define a rectangle.");
                    }
                } else {
                    if (!(pq.isOrthogonal(qr, oom))) {
                        throw new RuntimeException("The points do not define a rectangle.");
                    }
                }
            }
        }
    }

    /**
     * Create a new instance.
     *
     * @param r What {@code this} is created from.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Envelope.Rectangle r, int oom) {
        this(new V3D_Point(r.p), new V3D_Point(r.q), new V3D_Point(r.r),
                new V3D_Point(r.s), oom);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString()
                + ", s=" + s.toString() + ")";
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, p, q, r, s);
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @return a new rectangle.
     */
    @Override
    public V3D_Rectangle apply(V3D_Vector v, int oom) {
        return new V3D_Rectangle(p.apply(v, oom), q.apply(v, oom), 
                r.apply(v, oom), s.apply(v, oom), oom);
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (getEnvelope(oom).isIntersectedBy(pt, oom)) {
            if (super.isIntersectedBy(pt, oom)) {
                return isIntersectedBy0(pt, oom);
            }
        }
        return false;
    }

    private boolean isIntersectedBy0(V3D_Line ls, int oom) {
        return t.isIntersectedBy(ls, oom) || ri.isIntersectedBy(ls, oom)
                || b.isIntersectedBy(ls, oom) || l.isIntersectedBy(ls, oom);
    }

    private boolean isIntersectedBy0(V3D_Point pt, int oom) {
        // Special cases
        if (this.p.equals(pt)) {
            return true;
        }
        if (this.q.equals(pt)) {
            return true;
        }
        if (this.r.equals(pt)) {
            return true;
        }
        if (this.s.equals(pt)) {
            return true;
        }
        if (true){
        //if (V3D_Geometrics.isCoplanar(this, pt)) {
            // Check the areas
            // Area pqpt
            BigDecimal apqpt = new V3D_Triangle(p, q, pt, oom).getArea(oom);
            // Area qrpt
            BigDecimal aqrpt = new V3D_Triangle(q, r, pt, oom).getArea(oom);
            // Area rspt
            BigDecimal arspt = new V3D_Triangle(r, s, pt, oom).getArea(oom);
            // Area sppt
            BigDecimal asppt = new V3D_Triangle(s, p, pt, oom).getArea(oom);
            if (this.getArea(oom).compareTo(apqpt.add(aqrpt).add(arspt).add(asppt)) == 0) {
                return true;
                //return V3D_Geometrics.isCoplanar(this, pt);
            }
        }
        if (t.isIntersectedBy(pt, oom) || ri.isIntersectedBy(pt, oom)
                || b.isIntersectedBy(pt, oom) || l.isIntersectedBy(pt, oom)) {
            return true;
        }
        if (qAtOrigin) {
            V3D_Vector ppt = new V3D_Vector(q, pt, oom);
            V3D_Vector qpt = new V3D_Vector(p, pt, oom);
            V3D_Vector rpt = new V3D_Vector(r, pt, oom);
            V3D_Vector spt = new V3D_Vector(s, pt, oom);
            V3D_Vector rs = new V3D_Vector(r, s, oom);
            V3D_Vector sp = new V3D_Vector(s, q, oom);
            V3D_Vector cp = pq.reverse().getCrossProduct(ppt, oom);
            V3D_Vector cq = qr.getCrossProduct(qpt, oom);
            V3D_Vector cr = rs.getCrossProduct(rpt, oom);
            V3D_Vector cs = sp.getCrossProduct(spt, oom);
            /**
             * If cp, cq, cr, and cs are all in the same direction then pt
             * intersects.
             */
            Math_BigRational mp = cp.getMagnitudeSquared();
            Math_BigRational mq = cq.getMagnitudeSquared();
            V3D_Vector cpq = cp.add(cq, oom);
            Math_BigRational mpq = cpq.getMagnitudeSquared();
            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                Math_BigRational mr = cr.getMagnitudeSquared();
                V3D_Vector cpqr = cpq.add(cr, oom);
                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                    Math_BigRational ms = cs.getMagnitudeSquared();
                    Math_BigRational mpqrs = cpqr.add(cs, oom).getMagnitudeSquared();
                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
                        return true;
                    }
                }
            }
        } else {
            V3D_Vector ppt = new V3D_Vector(p, pt, oom);
            V3D_Vector qpt = new V3D_Vector(q, pt, oom);
            V3D_Vector rpt = new V3D_Vector(r, pt, oom);
            V3D_Vector spt = new V3D_Vector(s, pt, oom);
            V3D_Vector rs = new V3D_Vector(r, s, oom);
            V3D_Vector sp = new V3D_Vector(s, p, oom);
            V3D_Vector cp = pq.getCrossProduct(ppt, oom);
            V3D_Vector cq = qr.getCrossProduct(qpt, oom);
            V3D_Vector cr = rs.getCrossProduct(rpt, oom);
            V3D_Vector cs = sp.getCrossProduct(spt, oom);
            /**
             * If cp, cq, cr, and cs are all in the same direction then pt
             * intersects.
             */
            Math_BigRational mp = cp.getMagnitudeSquared();
            Math_BigRational mq = cq.getMagnitudeSquared();
            V3D_Vector cpq = cp.add(cq, oom);
            Math_BigRational mpq = cpq.getMagnitudeSquared();
            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                Math_BigRational mr = cr.getMagnitudeSquared();
                V3D_Vector cpqr = cpq.add(cr, oom);
                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                    Math_BigRational ms = cs.getMagnitudeSquared();
                    Math_BigRational mpqrs = cpqr.add(cs, oom).getMagnitudeSquared();
                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        V3D_Plane pl = new V3D_Plane(this);
        if (pl.isIntersectedBy(l, oom)) {
            V3D_Geometry g = pl.getIntersection(l, oom);
            if (g instanceof V3D_Point) {
                return isIntersectedBy0((V3D_Point) g, oom);
            } else {
                return isIntersectedBy0((V3D_Line) g, oom);
            }
        }
        return false;
    }

    /**
     * Intersection test.
     *
     * @param l The line segment to test for intersection.
     * @param b This is a flag to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return {@code true} iff this rectangle is intersected by {@code l}
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        if (getEnvelope(oom).isIntersectedBy(l.getEnvelope(oom))) {
            V3D_Plane pl = new V3D_Plane(this);
            if (pl.isIntersectedBy(l, oom)) {
                V3D_Geometry g = pl.getIntersection(l, oom, b);
                if (g instanceof V3D_Point) {
                    return isIntersectedBy((V3D_Point) g, oom);
                } else {
                    return isIntersectedBy((V3D_LineSegment) g, oom, b);
                }
            }
        }
        return false;
    }

    /**
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        V3D_Geometry g = new V3D_Plane(this).getIntersection(l, oom);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point) {
                if (this.isIntersectedBy((V3D_Point) g, oom)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_Line li = (V3D_Line) g;
                /**
                 * Whilst in theory a test of the envelope might seem a good
                 * idea (for speeding things up), timing experiments are wanted
                 * to be sure it does. Optimisation plans should bear in mind
                 * that some parts of intersection algorithms that can be done
                 * in parallel, so it might not be obvious what the quickest
                 * ways are! One of the reasons that V3D_Envelope has it's own
                 * geometry is because they needed their own special rectangles
                 * otherwise this "optimisation" resulted in a StackOverflow
                 * error.
                 */
                // Quick test of the envelope?!
                if (!getEnvelope(oom).isIntersectedBy(l, oom)) {
                    return null;
                }
                /**
                 * Get the intersection of the line and each edge of the
                 * rectangle.
                 */
                V3D_Geometry ti = t.getIntersection(li, oom);
                if (ti == null) {
                    // Check ri, b, l
                    V3D_Geometry rii = ri.getIntersection(li, oom);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = b.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li, oom);
                            if (tli == null) {
                                return null;
                            } else {
                                return tli;
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li, oom);
                            if (tli == null) {
                                return bi;
                            } else {
                                return new V3D_LineSegment(
                                        (V3D_Point) bi, (V3D_Point) tli, oom);
                            }
                        }
                    } else if (rii instanceof V3D_LineSegment) {
                        return rii;
                    } else {
                        // Check b, l
                        V3D_Geometry bi = b.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li, oom);
                            if (tli == null) {
                                return rii;
                            } else {
                                return new V3D_LineSegment((V3D_Point) rii,
                                        (V3D_Point) tli, oom);
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li, oom);
                            if (tli == null) {
                                V3D_Point riip = (V3D_Point) rii;
                                V3D_Point bip = (V3D_Point) bi;
                                if (riip.equals(bip)) {
                                    return bip;
                                } else {
                                    return new V3D_LineSegment(riip, bip, oom);
                                }
                            } else {
                                return new V3D_LineSegment((V3D_Point) bi,
                                        (V3D_Point) tli, oom);
                            }
                        }
                    }
                } else if (ti instanceof V3D_LineSegment) {
                    return ti;
                } else {
                    // Check ri, b, l
                    V3D_Geometry rii = ri.getIntersection(li, oom);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = b.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li, oom);
                            if (tli == null) {
                                return ti;
                            } else {
                                V3D_Point tlip = (V3D_Point) tli;
                                V3D_Point tip = (V3D_Point) ti;
                                if (tlip.equals(tip)) {
                                    return tlip;
                                } else {
                                    return new V3D_LineSegment(tlip, tip, oom);
                                }
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            return new V3D_LineSegment((V3D_Point) ti,
                                    (V3D_Point) bi, oom);
                        }
                    } else {
                        V3D_Point tip = (V3D_Point) ti;
                        V3D_Point riip = (V3D_Point) rii;
                        if (tip.equals(riip)) {
                            // Check b, l
                            V3D_Geometry sri = b.getIntersection(li, oom);
                            if (sri == null) {
                                // Check l
                                V3D_Geometry tli = this.l.getIntersection(li, oom);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new V3D_LineSegment(riip,
                                            (V3D_Point) tli, oom);
                                }
                            } else if (sri instanceof V3D_LineSegment) {
                                return sri;
                            } else {
                                return new V3D_LineSegment(riip, (V3D_Point) sri, oom);
                            }
                        } else {
                            return new V3D_LineSegment(riip, tip, oom);
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param flag This is to distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        boolean lip = isIntersectedBy(l.p, oom);
        boolean liq = isIntersectedBy(l.q, oom);
        if (lip) {
            if (liq) {
                return l;
            } else {
                V3D_Geometry li = getIntersection(l, oom);
                if (li instanceof V3D_Point) {
                    return l.p;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.p.equals(l.p)) {
                        return new V3D_LineSegment(l.p, lli.q, oom);
                    } else {
                        return new V3D_LineSegment(l.p, lli.p, oom);
                    }
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l, oom);
            if (liq) {
                if (li instanceof V3D_Point) {
                    return l.q;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.q.equals(l.q)) {
                        return new V3D_LineSegment(l.q, lli.p, oom);
                    } else {
                        return new V3D_LineSegment(l.q, lli.q, oom);
                    }
                }
            } else {
                if (li instanceof V3D_Point) {
                    V3D_Point pli = (V3D_Point) li;
                    if (l.isIntersectedBy(pli, oom)) {
                        return pli;
                    } else {
                        return null;
                    }
                } else {
                    return li;
                }
            }
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope(oom).isIntersectedBy(l, oom);
    }

    @Override
    public BigDecimal getPerimeter(int oom) {
        int oomn2 = oom - 2;
        return l.getLength().toBigDecimal(oomn2)
                .add(t.getLength().toBigDecimal(oomn2))
                .multiply(BigDecimal.valueOf(2));
    }

    @Override
    public BigDecimal getArea(int oom) {
//        return Math_BigDecimal.round(l.v.getMagnitude(oomn2)
//                .multiply(t.v.getMagnitude(oomn2)), oom);
        return l.v.getMagnitude().multiply(t.v.getMagnitude(), oom).toBigDecimal(oom);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The order of magnitude of the precision.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p, oom)) {
            return BigDecimal.ZERO;
        }
        BigDecimal dp = super.getDistance(p, oom);
        BigDecimal ld = l.getDistance(p, oom);
        BigDecimal td = t.getDistance(p, oom);
        BigDecimal rd = r.getDistance(p, oom);
        BigDecimal bd = b.getDistance(p, oom);
        if (dp.compareTo(ld) == 0 && dp.compareTo(td) == 0
                && dp.compareTo(rd) == 0 && dp.compareTo(bd) == 0) {
            return dp;
        } else {
            return Math_BigDecimal.min(ld, td, rd, bd);
        }
    }
}
